package au.edu.sydney.brawndo.erp.spfea.ordering;

import au.edu.sydney.brawndo.erp.ordering.Product;
import java.util.Map;

/**
 * Part of the Bridge design pattern, fulfilling the role of Implementor.
 */
public interface InvoiceDataImplementor {
  /**
   * Provides a context-dependent invoice for the order calling this method.
   * @param perShipmentCost The discounted cost of a single shipment of the order
   * @param totalCost The discounted cost of the entire order
   * @param numShipments The number of shipments ordered
   * @param products A map of the products ordered and their ordered quantity
   * @return An invoice for the order
   */
  String generateInvoiceData(double perShipmentCost, double totalCost, int numShipments, Map<Product, Integer> products);
  /**
   * Creates a deep-copied InvoiceDataImplementor instance
   * @return The cloned InvoiceDataImplementor instance
   */
  InvoiceDataImplementor copy();
}
