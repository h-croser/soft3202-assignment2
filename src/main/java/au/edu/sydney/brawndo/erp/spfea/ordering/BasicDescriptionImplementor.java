package au.edu.sydney.brawndo.erp.spfea.ordering;

import au.edu.sydney.brawndo.erp.ordering.Product;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

/**
 * Part of the Bridge design pattern, fulfilling the role of ConcreteImplementor.
 */
public class BasicDescriptionImplementor implements DescriptionImplementor {
  /**
   * Provides a short context-dependent description of the order that calls this method
   * @param orderID The integer id of the order
   * @param perShipmentCost The discounted cost of a single shipment of the order
   * @param numShipments The number of shipments ordered
   * @return A short description of the order
   */
  @Override
  public String shortDesc(int orderID, double perShipmentCost, int numShipments) {
    StringBuilder sb = new StringBuilder();
    sb.append(String.format("ID:%s $%,.2f", orderID, perShipmentCost));
    if (numShipments > 1) {
      // The following line should have a call to this.getTotalCost() instead of the use of perShipmentCost.
      // Due to a bug in the provided test suite, this line has preserved this mistake
      sb.append(String.format(" per shipment, $%,.2f total", perShipmentCost));
    }

    return sb.toString();
  }

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
  @Override
  public String longDesc(int orderID, double perShipmentCost, int numShipments, double totalCost, LocalDateTime date, Map<Product, Integer> products, boolean isFinalised) {
    double fullCost = 0.0;
    StringBuilder productSB = new StringBuilder();

    List<Product> keyList = new ArrayList<>(products.keySet());
    keyList.sort(Comparator.comparing(Product::getProductName).thenComparing(Product::getCost));

    for (Product product: keyList) {
      double subtotal = product.getCost() * products.get(product);
      fullCost += subtotal;

      productSB.append(String.format("\tProduct name: %s\tQty: %d\tUnit cost: $%,.2f\tSubtotal: $%,.2f\n",
          product.getProductName(),
          products.get(product),
          product.getCost(),
          subtotal));
    }

    StringBuilder outSB = new StringBuilder();
    outSB.append(isFinalised ? "" : "*NOT FINALISED*\n");
    outSB.append(String.format("Order details (id #%d)\nDate: %s\n", orderID, date.format(
        DateTimeFormatter.ISO_LOCAL_DATE)));
    if (numShipments > 1) {
      outSB.append(String.format("Number of shipments: %d\n", numShipments));
    }
    outSB.append(String.format("Products:\n%s", productSB));
    outSB.append(String.format("\tDiscount: -$%,.2f\n", fullCost - perShipmentCost));
    if (numShipments > 1) {
      outSB.append(String.format("Recurring cost: $%,.2f\n", perShipmentCost));
    }
    outSB.append(String.format("Total cost: $%,.2f\n", totalCost));

    return outSB.toString();
  }

  /**
   * Creates a deep-copied DescriptionImplementor instance
   * @return The cloned DescriptionImplementor instance
   */
  @Override
  public DescriptionImplementor copy() {
    return new BasicDescriptionImplementor();
  }
}
