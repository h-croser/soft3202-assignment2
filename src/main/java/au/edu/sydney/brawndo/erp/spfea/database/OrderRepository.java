package au.edu.sydney.brawndo.erp.spfea.database;

import au.edu.sydney.brawndo.erp.ordering.Order;
import java.util.List;

/**
 * Part of the Repository design pattern, fulfilling the role of Repository.
 * Implementations of this interface provide access to a cache of Order objects for faster access
 * and modification.
 * The Repository is managed by a Unit of Work implementation.
 */
public interface OrderRepository {
  /**
   * Adds the given Order object to the repository, unless the order is null or has already been added
   * @param order The Order object to add
   */
  void add(Order order);
  /**
   * Removes the given Order object from the repository
   * @param order The Order object to remove
   */
  void delete(Order order);
  /**
   * Retrieves the cached Order object from the repository
   * @param orderID The integer ID of the Order being queried
   * @return The cached Order object if found, null otherwise
   */
  Order findOrder(int orderID);
  /**
   * Performs a shallow copy of the Order list and returns this copy
   * @return A shallow copy of the Order list
   */
  List<Order> findAllOrders();
}
