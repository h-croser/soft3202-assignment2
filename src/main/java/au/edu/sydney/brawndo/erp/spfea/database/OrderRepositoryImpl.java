package au.edu.sydney.brawndo.erp.spfea.database;

import au.edu.sydney.brawndo.erp.database.TestDatabase;
import au.edu.sydney.brawndo.erp.ordering.Order;
import java.util.ArrayList;
import java.util.List;

/**
 * Part of the Repository design pattern, fulfilling the role of Concrete Repository.
 * This provides access to a cache of Order objects for faster access and modification.
 * The Repository is managed by a Unit of Work implementation.
 */
public class OrderRepositoryImpl implements OrderRepository {
  private final List<Order> orders;

  public OrderRepositoryImpl() {
    this.orders = new ArrayList<>();
  }

  /**
   * Adds the given Order object to the Order list, unless the order is null or has already been added
   * @param order The Order object to add
   */
  @Override
  public void add(Order order) {
    if ((null != order) && (!this.orders.contains(order))) {
      this.orders.add(order);
    }
  }

  /**
   * Removes the given Order object from the Order list
   * @param order The Order object to remove
   */
  @Override
  public void delete(Order order) {
    this.orders.remove(order);
  }

  /**
   * Retrieves the cached Order object from the Order list
   * @param orderID The integer ID of the Order being queried
   * @return The cached Order object if found, null otherwise
   */
  @Override
  public Order findOrder(int orderID) {
    for (Order cached : this.orders) {
      if (orderID == cached.getOrderID()) {
        return cached;
      }
    }
    return null;
  }

  /**
   * Performs a shallow copy of the Order list and returns this copy
   * @return A shallow copy of the Order list
   */
  @Override
  public List<Order> findAllOrders() {
    return new ArrayList<>(this.orders);
  }
}
