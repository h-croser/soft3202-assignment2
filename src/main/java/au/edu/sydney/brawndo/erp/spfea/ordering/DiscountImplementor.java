package au.edu.sydney.brawndo.erp.spfea.ordering;

import au.edu.sydney.brawndo.erp.ordering.Product;
import java.util.Map;

/**
 * Part of the Bridge design pattern, fulfilling the role of Implementor.
 */
public interface DiscountImplementor {
  /**
   * Calculates the cost of all products that will be sent with each shipment, including discounts
   * @param products A map of products and their quantities for the given order
   * @return The cost per shipment
   */
  double getPerShipmentDiscountedCost(Map<Product, Integer> products);
  /**
   * Creates a deep-copied DiscountImplementor instance
   * @return The cloned DiscountImplementor instance
   */
  DiscountImplementor copy();
}
