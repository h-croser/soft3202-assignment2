package au.edu.sydney.brawndo.erp.spfea.ordering;

import au.edu.sydney.brawndo.erp.ordering.Product;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

/**
 * Part of the Bridge design pattern, fulfilling the role of ConcreteImplementor.
 */
public class PersonalInvoiceDataImplementor implements InvoiceDataImplementor {
  /**
   * Provides a context-dependent invoice for the order calling this method.
   * @param perShipmentCost The discounted cost of a single shipment of the order
   * @param totalCost The discounted cost of the entire order
   * @param numShipments The number of shipments ordered
   * @param products A map of the products ordered and their ordered quantity
   * @return An invoice for the order
   */
  @Override
  public String generateInvoiceData(double perShipmentCost, double totalCost, int numShipments, Map<Product, Integer> products) {
    StringBuilder sb = new StringBuilder();

    sb.append("Thank you for your Brawndo© order!\n");
    sb.append("Your order comes to: $");
    sb.append(String.format("%,.2f", perShipmentCost));
    if (numShipments > 1) {
      sb.append(" each week, with a total overall cost of: $");
      sb.append(String.format("%,.2f", totalCost));
    }
    sb.append("\nPlease see below for details:\n");
    List<Product> keyList = new ArrayList<>(products.keySet());
    keyList.sort(Comparator.comparing(Product::getProductName).thenComparing(Product::getCost));

    for (Product product: keyList) {
      sb.append("\tProduct name: ");
      sb.append(product.getProductName());
      sb.append("\tQty: ");
      sb.append(products.get(product));
      sb.append("\tCost per unit: ");
      sb.append(String.format("$%,.2f", product.getCost()));
      sb.append("\tSubtotal: ");
      sb.append(String.format("$%,.2f\n", product.getCost() * products.get(product)));
    }

    return sb.toString();
  }

  /**
   * Creates a deep-copied InvoiceDataImplementor instance
   * @return The cloned InvoiceDataImplementor instance
   */
  @Override
  public InvoiceDataImplementor copy() {
    return new PersonalInvoiceDataImplementor();
  }
}
