package au.edu.sydney.brawndo.erp.spfea.ordering;

import au.edu.sydney.brawndo.erp.ordering.Order;
import au.edu.sydney.brawndo.erp.ordering.Product;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Part of the Bridge design pattern, fulfilling the role of RefinedAbstraction. Each OrderImpl
 * object delegates tasks to a DiscountImplementor and an InvoiceDataImplementor, in order to reduce
 * the number of Order classes that need to be created.
 * All public methods from the Order class are preserved with the existing functionality
 */
public class OrderImpl implements Order {
  private int id;
  private int customerID;
  private LocalDateTime date;
  private int numShipments;
  private DiscountImplementor discountImplementor;
  private InvoiceDataImplementor invoiceDataImplementor;
  private DescriptionImplementor descriptionImplementor;
  private Map<Product, Integer> products = new HashMap<>();
  private boolean isFinalised = false;

  public OrderImpl(int id, int customerID, LocalDateTime date, int numShipments,
                  DiscountImplementor discountImplementor,
                  InvoiceDataImplementor invoiceDataImplementor,
                  DescriptionImplementor descriptionImplementor) {
    this.id = id;
    this.customerID = customerID;
    this.date = date;
    this.numShipments = numShipments;

    this.discountImplementor = discountImplementor;
    this.invoiceDataImplementor = invoiceDataImplementor;
    this.descriptionImplementor = descriptionImplementor;
  }

  @Override
  public int getOrderID() {
    return this.id;
  }

  @Override
  public double getTotalCost() {
    return this.discountImplementor.getPerShipmentDiscountedCost(this.products) * this.numShipments;
  }

  @Override
  public LocalDateTime getOrderDate() {
    return this.date;
  }

  @Override
  public void setProduct(Product product, int qty) {
    if (isFinalised) throw new IllegalStateException("Order was already finalised.");

    // We can't rely on like products having the same object identity since they get
    // rebuilt over the network, so we had to check for presence and same values

    for (Product contained: products.keySet()) {
      if (product.equals(contained)) {
        product = contained;
        break;
      }
    }

    products.put(product, qty);
  }

  @Override
  public Set<Product> getAllProducts() {
    return this.products.keySet();
  }

  @Override
  public int getProductQty(Product product) {
    // We can't rely on like products having the same object identity since they get
    // rebuilt over the network, so we had to check for presence and same values

    for (Product contained: products.keySet()) {
      if (product.equals(contained)) {
        product = contained;
        break;
      }
    }

    Integer result = products.get(product);
    return null == result ? 0 : result;
  }

  @Override
  public String generateInvoiceData() {
    double perShipmentCost = this.discountImplementor.getPerShipmentDiscountedCost(products);
    double totalCost = this.getTotalCost();
    return this.invoiceDataImplementor.generateInvoiceData(perShipmentCost, totalCost, this.numShipments, products);
  }

  @Override
  public int getCustomer() {
    return this.customerID;
  }

  @Override
  public void finalise() {
    this.isFinalised = true;
  }

  @Override
  public Order copy() {
    return new OrderImpl(this.id, this.customerID, this.date, this.numShipments,
                        this.discountImplementor.copy(), this.invoiceDataImplementor.copy(),
                        this.descriptionImplementor.copy());
  }

  @Override
  public String shortDesc() {
    double perShipmentCost = this.discountImplementor.getPerShipmentDiscountedCost(this.products);
    return this.descriptionImplementor.shortDesc(this.id, perShipmentCost, this.numShipments);
  }

  @Override
  public String longDesc() {
    double perShipmentCost = this.discountImplementor.getPerShipmentDiscountedCost(this.products);
    return this.descriptionImplementor.longDesc(this.id, perShipmentCost, this.numShipments,
                                                this.getTotalCost(), this.date, this.products,
                                                this.isFinalised);
  }
}
