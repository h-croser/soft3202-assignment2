package au.edu.sydney.brawndo.erp.spfea.ordering;

import au.edu.sydney.brawndo.erp.ordering.Product;
import java.util.Map;

/**
 * Part of the Bridge design pattern, fulfilling the role of ConcreteImplementor.
 */
public class BusinessInvoiceDataImplementor implements InvoiceDataImplementor {
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
    if (numShipments > 1) {
      sb.append(String.format("Your business account will be charged: $%,.2f each week, with a total overall cost of: $%,.2f", perShipmentCost, totalCost));
    } else {
      sb.append(String.format("Your business account has been charged: $%,.2f", totalCost));
    }
    sb.append("\nPlease see your Brawndo© merchandising representative for itemised details.");

    return sb.toString();
  }

  /**
   * Creates a deep-copied InvoiceDataImplementor instance
   * @return The cloned InvoiceDataImplementor instance
   */
  @Override
  public InvoiceDataImplementor copy() {
    return new BusinessInvoiceDataImplementor();
  }
}
