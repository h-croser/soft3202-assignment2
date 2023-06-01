package au.edu.sydney.brawndo.erp.spfea.ordering;

import au.edu.sydney.brawndo.erp.ordering.Product;
import java.util.Map;

/**
 * Part of the Bridge design pattern, fulfilling the role of ConcreteImplementor.
 */
public class BulkDiscountImplementor implements DiscountImplementor {
  private double discountRate;
  private int discountThreshold;


  public BulkDiscountImplementor(double discountedRate, int discountThreshold) {
    this.discountRate = discountedRate;
    this.discountThreshold = discountThreshold;
  }

  /**
   * Calculates the cost of all products that will be sent with each shipment, including discounts
   * @param products A map of products and their quantities for the given order
   * @return The cost per shipment
   */
  @Override
  public double getPerShipmentDiscountedCost(Map<Product, Integer> products) {
    double cost = 0.0;
    for (Product product: products.keySet()) {
      int count = products.get(product);
      if (count >= this.discountThreshold) {
        cost +=  count * product.getCost() * this.discountRate;
      } else {
        cost +=  count * product.getCost();
      }
    }
    return cost;
  }

  /**
   * Creates a deep-copied DiscountImplementor instance
   * @return The cloned DiscountImplementor instance
   */
  @Override
  public DiscountImplementor copy() {
    return new BulkDiscountImplementor(discountRate, discountThreshold);
  }
}
