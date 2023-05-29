package au.edu.sydney.brawndo.erp.spfea.products;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Part of the Flyweight design pattern. This class is responsible for managing flyweight objects.
 * This Flyweight Factory implementation does not need to manage Flyweight creation because there
 * is no Flyweight interface in this implementation of the pattern.
 * The ProductDataFlyweightFactory maintains a cache of double arrays, and provides access to them
 * through its only public method, getProductDataFlyweight
 */
public class ProductDataFlyweightFactory {

  /**
   * The Product data flyweights are stored as double arrays. The standard Flyweight pattern
   * implementation would include a Flyweight interface and concrete Flyweight objects. In this
   * case, there is no need for these because there is no extrinsic state within the data,
   * and there are no shared operations beyond the double array methods
   */
  private final Map<Integer, double[]> flyweights;

  public ProductDataFlyweightFactory() {
    this.flyweights = new HashMap<>();
  }

  /**
   * Accepts a double array, checks if the array exists within the flyweight cache, adds it to the
   * cache (keyed by its hash) if it doesn't, and returns the stored array.
   * @param data The data to be tested for existence within the flyweight cache
   * @return The data from the flyweight cache
   */
  public double[] getProductDataFlyweight(double[] data) {
    int dataHash = Arrays.hashCode(data);
    double[] flyweight = this.flyweights.get(dataHash);

    if (flyweight == null) {
      this.flyweights.put(dataHash, data);
      flyweight = this.flyweights.get(dataHash);
    }

    return flyweight;
  }
}
