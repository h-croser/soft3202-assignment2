package au.edu.sydney.brawndo.erp.spfea.database;

import au.edu.sydney.brawndo.erp.ordering.Order;
import java.util.List;

/**
 * Part of the Unit of Work design pattern, fulfilling the role of Unit of Work.
 * Provides methods for tracking the state of the Order in the cache with regard to the state of
 * the Order in the database. Also acts as the mediator between the database and the client.
 */
public interface OrderDatabaseUnitOfWork {
  /**
   * Sets an Order as a new Order, meaning it exists in the cache but not the database
   * @param order The Order to be set as new
   */
  void registerNew(Order order);
  /**
   * Sets an Order as a dirty Order, meaning it exists in both the database and cache but the
   * cache has the latest state.
   * @param order The Order to be set as dirty
   */
  void registerDirty(Order order);
  /**
   * Sets an Order as a clean Order, meaning the database and cache agree on the state of the Order
   * @param order The Order to be set as clean
   */
  void registerClean(Order order);
  /**
   * Sets an Order as a deleted Order, meaning the Order exists in the database but is deleted
   * from the cache
   * @param order The Order to be set as deleted
   */
  void registerDeleted(Order order);
  /**
   * Provides an interface to the client for the findOrder method in the Order repository
   * @param orderID The integer ID of the Order to be retrieved
   * @return The Order object in the repository, or null if not found in the repository or database
   */
  Order findOrder(int orderID);
  /**
   * Provides an interface to the client for the findAllOrders method in the Order repository
   * @return A list of Orders that currently exist in the repository
   */
  List<Order> findAllOrders();

  /**
   * After this query is complete, the database should match the state of the cache before the
   * query is called. Additionally, the state of each Order will be cleared, while the cache will
   * remain in tact
   */
  void commit();

  /**
   * Removes all tracked states from cached Orders, and resets the cache (repository) to an empty state
   */
  void rollback();
}
