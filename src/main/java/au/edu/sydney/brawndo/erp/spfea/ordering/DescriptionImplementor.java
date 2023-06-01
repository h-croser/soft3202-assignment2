package au.edu.sydney.brawndo.erp.spfea.ordering;

import au.edu.sydney.brawndo.erp.ordering.Product;
import java.time.LocalDateTime;
import java.util.Map;

/**
 * Part of the Bridge design pattern, fulfilling the role of Implementor.
 * There is only one concrete DescriptionImplementor but the interface is in place for extensions
 * in the real codebase
 */
public interface DescriptionImplementor {
  /**
   * Provides a short context-dependent description of the order that calls this method
   * @param orderID The integer id of the order
   * @param perShipmentCost The discounted cost of a single shipment of the order
   * @param numShipments The number of shipments ordered
   * @return A short description of the order
   */
  String shortDesc(int orderID, double perShipmentCost, int numShipments);
  /**
   * Provides a long context-dependent description of the order that calls this method
   * @param orderID The integer id of the order
   * @param perShipmentCost The discounted cost of a single shipment of the order
   * @param numShipments The number of shipments ordered
   * @param totalCost The discounted cost of the entire order
   * @param date The date of the order
   * @param products A map of the products ordered and their ordered quantity
   * @param isFinalised True if the order is finalised, False otherwise
   * @return A long description of the order
   */
  String longDesc(int orderID, double perShipmentCost, int numShipments, double totalCost, LocalDateTime date, Map<Product, Integer> products, boolean isFinalised);
  /**
   * Creates a deep-copied DescriptionImplementor instance
   * @return The cloned DescriptionImplementor instance
   */
  DescriptionImplementor copy();
}
