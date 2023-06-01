package au.edu.sydney.brawndo.erp.spfea.ordering;

import au.edu.sydney.brawndo.erp.ordering.Product;
import java.util.Map;

/**
 * Part of the Bridge design pattern, fulfilling the role of ConcreteImplementor.
 */
public class FlatDiscountImplementor implements DiscountImplementor {
  private double discountRate;

  public FlatDiscountImplementor(double discountedRate) {
    this.discountRate = discountedRate;
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
      cost +=  products.get(product) * product.getCost() * this.discountRate;
    }
    return cost;
  }

  /**
   * Creates a deep-copied DiscountImplementor instance
   * @return The cloned DiscountImplementor instance
   */
  @Override
  public DiscountImplementor copy() {
    return new FlatDiscountImplementor(this.discountRate);
  }
}
