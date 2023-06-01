package au.edu.sydney.brawndo.erp.spfea;

import au.edu.sydney.brawndo.erp.auth.AuthModule;
import au.edu.sydney.brawndo.erp.auth.AuthToken;
import au.edu.sydney.brawndo.erp.database.TestDatabase;
import au.edu.sydney.brawndo.erp.ordering.Customer;
import au.edu.sydney.brawndo.erp.ordering.Order;
import au.edu.sydney.brawndo.erp.ordering.Product;
import au.edu.sydney.brawndo.erp.spfea.contact.*;
import au.edu.sydney.brawndo.erp.spfea.ordering.*;
import au.edu.sydney.brawndo.erp.spfea.products.ProductDataFlyweightFactory;
import au.edu.sydney.brawndo.erp.spfea.products.ProductDatabase;

import au.edu.sydney.brawndo.erp.spfea.products.ProductImpl;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SuppressWarnings("Duplicates")
public class SPFEAFacade {
    private AuthToken token;
    private final ValueHolderCustomerImpl customerHolder = new ValueHolderCustomerImpl();
    private final ProductDataFlyweightFactory dataFlyweightFactory = new ProductDataFlyweightFactory();

    public boolean login(String userName, String password) {
        token = AuthModule.login(userName, password);

        return null != token;
    }

    public List<Integer> getAllOrders() {
        if (null == token) {
            throw new SecurityException();
        }

        TestDatabase database = TestDatabase.getInstance();

        List<Order> orders = database.getOrders(token);

        List<Integer> result = new ArrayList<>();

        for (Order order: orders) {
            result.add(order.getOrderID());
        }

        return result;
    }

    public Integer createOrder(int customerID, LocalDateTime date, boolean isBusiness, boolean isSubscription, int discountType, int discountThreshold, int discountRateRaw, int numShipments) {
        if (null == token) {
            throw new SecurityException();
        }

        if (discountRateRaw < 0 || discountRateRaw > 100) {
            throw new IllegalArgumentException("Discount rate not a percentage");
        }

        double discountRate = 1.0 - (discountRateRaw / 100.0);

        if (!TestDatabase.getInstance().getCustomerIDs(token).contains(customerID)) {
            throw new IllegalArgumentException("Invalid customer ID");
        }

        int id = TestDatabase.getInstance().getNextOrderID();

        Order order;
        DiscountImplementor discountImplementor;
        InvoiceDataImplementor invoiceDataImplementor;
        DescriptionImplementor descriptionImplementor = new BasicDescriptionImplementor();
        if (1 == discountType) { // 1 is flat rate
            discountImplementor = new FlatDiscountImplementor(discountRate);
        } else if (2 == discountType) { // 2 is bulk discount
            discountImplementor = new BulkDiscountImplementor(discountRate, discountThreshold);
        } else {
            return null;
        }

        if (isBusiness) {
            invoiceDataImplementor = new BusinessInvoiceDataImplementor();
        } else {
            invoiceDataImplementor = new PersonalInvoiceDataImplementor();
        }

        // If the order isn't a subscription, there must only be one shipment
        if (!isSubscription) {
            numShipments = 1;
        }

        order = new OrderImpl(id, customerID, date, numShipments, discountImplementor, invoiceDataImplementor, descriptionImplementor);

        TestDatabase.getInstance().saveOrder(token, order);
        return order.getOrderID();
    }

    public List<Integer> getAllCustomerIDs() {
        if (null == token) {
            throw new SecurityException();
        }

        TestDatabase database = TestDatabase.getInstance();
        return database.getCustomerIDs(token);
    }

    public Customer getCustomer(int id) {
        if (null == token) {
            throw new SecurityException();
        }

        return this.customerHolder.getCustomer(token, id);
    }

    public boolean removeOrder(int id) {
        if (null == token) {
            throw new SecurityException();
        }

        TestDatabase database = TestDatabase.getInstance();
        return database.removeOrder(token, id);
    }

    public List<Product> getAllProducts() {
        if (null == token) {
            throw new SecurityException();
        }

        // The memory inefficient list of Products supplied by the database will be garbage-collected when the method returns.
        List<Product> dbProducts = new ArrayList<>(ProductDatabase.getTestProducts());

        List<Product> products = new ArrayList<>();
        String productName;
        double productCost;
        double[] manufacturingData, recipeData, marketingData, safetyData, licensingData;
        for (Product dbProduct : dbProducts) {
            // The dataFlyweightFactory is queried for Product data flyweights for each of the
            // large-in-memory double arrays that are attributes of ProductImpl
            productName = dbProduct.getProductName();
            productCost = dbProduct.getCost();
            manufacturingData = this.dataFlyweightFactory.getProductDataFlyweight(dbProduct.getManufacturingData());
            recipeData = this.dataFlyweightFactory.getProductDataFlyweight(dbProduct.getRecipeData());
            marketingData = this.dataFlyweightFactory.getProductDataFlyweight(dbProduct.getMarketingData());
            safetyData = this.dataFlyweightFactory.getProductDataFlyweight(dbProduct.getSafetyData());
            licensingData = this.dataFlyweightFactory.getProductDataFlyweight(dbProduct.getLicensingData());

            products.add(new ProductImpl(productName, productCost, manufacturingData, recipeData, marketingData, safetyData, licensingData));
        }

        return products;
    }

    public boolean finaliseOrder(int orderID, List<String> contactPriority) {
        if (null == token) {
            throw new SecurityException();
        }

        List<ContactHandler> contactHandlerPriority = new ArrayList<>();
        // Sets a priority order according to the given list of Strings
        if (null != contactPriority) {
            ContactHandler currHandler;
            for (String method: contactPriority) {
                currHandler = ContactHandlerFactory.createHandlerInstance(method);
                // Only valid ContactHandlers should be added to the priority list
                if (null != currHandler) {
                    contactHandlerPriority.add(currHandler);
                }
            }
        }
        // Sets a default priority order if none were provided or if no valid methods were provided
        if (contactHandlerPriority.size() == 0) {
            contactHandlerPriority = Arrays.asList(
                ContactHandlerFactory.createHandlerInstance("Merchandiser"),
                ContactHandlerFactory.createHandlerInstance("Email"),
                ContactHandlerFactory.createHandlerInstance("Carrier Pigeon"),
                ContactHandlerFactory.createHandlerInstance("Mail"),
                ContactHandlerFactory.createHandlerInstance("Phone call")
            );
        }

        // Sets successors for each contact methods in the list, setting the successor of the selected
        // ContactHandler to the previous handler in the list
        ContactHandler prevHandler;
        for (int i = 1; i < contactHandlerPriority.size(); i++) {
            prevHandler = contactHandlerPriority.get(i-1);
            contactHandlerPriority.get(i).setSuccessor(prevHandler);
        }

        // Mark the order as finalised in the database
        Order order = TestDatabase.getInstance().getOrder(token, orderID);
        order.finalise();
        TestDatabase.getInstance().saveOrder(token, order);

        // The first priority ContactHandler object is the only object required for the chain of responsibility to be invoked
        ContactHandler rootHandler = contactHandlerPriority.get(0);
        return rootHandler.sendInvoice(token, getCustomer(order.getCustomer()), order.generateInvoiceData());
    }

    public void logout() {
        AuthModule.logout(token);
        token = null;
    }

    public double getOrderTotalCost(int orderID) {
        if (null == token) {
            throw new SecurityException();
        }

        Order order = TestDatabase.getInstance().getOrder(token, orderID);
        if (null == order) {
            return 0.0;
        }

        return order.getTotalCost();
    }

    public void orderLineSet(int orderID, Product product, int qty) {
        if (null == token) {
            throw new SecurityException();
        }

        Order order = TestDatabase.getInstance().getOrder(token, orderID);

        if (null == order) {
            System.out.println("got here");
            return;
        }

        order.setProduct(product, qty);

        TestDatabase.getInstance().saveOrder(token, order);
    }

    public String getOrderLongDesc(int orderID) {
        if (null == token) {
            throw new SecurityException();
        }

        Order order = TestDatabase.getInstance().getOrder(token, orderID);

        if (null == order) {
            return null;
        }

        return order.longDesc();
    }

    public String getOrderShortDesc(int orderID) {
        if (null == token) {
            throw new SecurityException();
        }

        Order order = TestDatabase.getInstance().getOrder(token, orderID);

        if (null == order) {
            return null;
        }

        return order.shortDesc();
    }

    public List<String> getKnownContactMethods() {if (null == token) {
        throw new SecurityException();
    }

        return ContactHandlerFactory.getKnownMethods();
    }
}
