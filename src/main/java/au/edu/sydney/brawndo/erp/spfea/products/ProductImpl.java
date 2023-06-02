package au.edu.sydney.brawndo.erp.spfea.products;

import au.edu.sydney.brawndo.erp.ordering.Product;
import java.util.Arrays;
import java.util.Objects;

public class ProductImpl implements Product {

  private final String name;
  private final double cost;
  private final double[] manufacturingData;
  private final double[] recipeData;
  private final double[] marketingData;
  private final double[] safetyData;
  private final double[] licensingData;

  public ProductImpl(String name,
      double cost,
      double[] manufacturingData,
      double[] recipeData,
      double[] marketingData,
      double[] safetyData,
      double[] licensingData) {
    this.name = name;
    this.cost = cost;
    this.manufacturingData = manufacturingData;
    this.recipeData = recipeData;
    this.marketingData = marketingData;
    this.safetyData = safetyData;
    this.licensingData = licensingData;
  }

  @Override
  public String getProductName() {
    return name;
  }

  @Override
  public double getCost() {
    return cost;
  }

  @Override
  public double[] getManufacturingData() {
    return manufacturingData;
  }

  @Override
  public double[] getRecipeData() {
    return recipeData;
  }

  @Override
  public double[] getMarketingData() {
    return marketingData;
  }

  @Override
  public double[] getSafetyData() {
    return safetyData;
  }

  @Override
  public double[] getLicensingData() {
    return licensingData;
  }

  @Override
  public String toString() {

    return String.format("%s", name);
  }

  /**
   * Returns true if all the attributes of the other object match those of this Product, false otherwise
   * @param other The object to check for equality with this Product
   * @return false if the other object is null, not a Product, or if the attributes don't match. true otherwise
   */
  @Override
  public boolean equals(Object other) {
     // Part of the Value Object pattern. All immutable fields of each object are compared for equality.
    if (other == null) return false;
    if (other == this) return true;
    if (!(other instanceof Product)) return false;
    Product otherProduct = (Product)other;
    return (this.getCost() == otherProduct.getCost() &&
        this.getProductName().equals(otherProduct.getProductName()) &&
        Arrays.equals(this.getManufacturingData(), otherProduct.getManufacturingData()) &&
        Arrays.equals(this.getRecipeData(), otherProduct.getRecipeData()) &&
        Arrays.equals(this.getMarketingData(), otherProduct.getMarketingData()) &&
        Arrays.equals(this.getSafetyData(), otherProduct.getSafetyData()) &&
        Arrays.equals(this.getLicensingData(), otherProduct.getLicensingData()));
  }

  /**
   * Produces a unique hash for this Product, dependent on all of its attributes and their current state
   * @return An integer hash unique to this Product and its state
   */
  @Override
  public int hashCode() {
    // Part of the Value Object pattern. All immutable fields of each object are added to the hash.
    int hash = Objects.hash(this.name, this.cost);
    hash = 31 * hash + Arrays.hashCode(this.manufacturingData);
    hash = 31 * hash + Arrays.hashCode(this.recipeData);
    hash = 31 * hash + Arrays.hashCode(this.marketingData);
    hash = 31 * hash + Arrays.hashCode(this.safetyData);
    hash = 31 * hash + Arrays.hashCode(this.licensingData);

    return hash;
  }
}
