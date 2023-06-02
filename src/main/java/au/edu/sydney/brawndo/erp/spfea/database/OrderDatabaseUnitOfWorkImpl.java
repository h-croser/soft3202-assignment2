package au.edu.sydney.brawndo.erp.spfea.database;
import au.edu.sydney.brawndo.erp.auth.AuthToken;
import au.edu.sydney.brawndo.erp.database.TestDatabase;
import au.edu.sydney.brawndo.erp.ordering.Order;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderDatabaseUnitOfWorkImpl implements OrderDatabaseUnitOfWork {
  private final Map<Order, String> orderState = new HashMap<>();
  private final OrderRepository orderRepository;
  private final AuthToken token;

  public OrderDatabaseUnitOfWorkImpl(AuthToken token) {
    this.token = token;
    this.orderRepository = new OrderRepositoryImpl();
  }

  /**
   * Sets an Order as a new Order, meaning it exists in the cache but not the database
   * @param order The Order to be set as new
   */
  @Override
  public void registerNew(Order order) {
    if (null == order) {
      return;
    }
    this.orderState.put(order, "new");
    this.orderRepository.add(order);
  }

  /**
   * Sets an Order as a dirty Order, meaning it exists in both the database and cache but the
   * cache has the latest state.
   * @param order The Order to be set as dirty
   */
  @Override
  public void registerDirty(Order order) {
    if (null == order) {
      return;
    }
    this.orderState.put(order, "dirty");
  }

  /**
   * Sets an Order as a clean Order, meaning the database and cache agree on the state of the Order
   * @param order The Order to be set as clean
   */
  @Override
  public void registerClean(Order order) {
    if (null == order) {
      return;
    }
    this.orderState.put(order, "clean");
  }

  /**
   * Sets an Order as a deleted Order, meaning the Order exists in the database but is deleted
   * from the cache
   * @param order The Order to be set as deleted
   */
  @Override
  public void registerDeleted(Order order) {
    if (null == order) {
      return;
    }
    this.orderState.put(order, "deleted");
  }

  /**
   * Provides an interface to the client for the findOrder method in the Order repository
   * @param orderID The integer ID of the Order to be retrieved
   * @return The Order object in the repository, or null if not found in the repository or database
   */
  @Override
  public Order findOrder(int orderID) {
    Order found = this.orderRepository.findOrder(orderID);
    if (null == found) {
      TestDatabase db = TestDatabase.getInstance();
      found = db.getOrder(this.token, orderID);
      this.orderRepository.add(found);
      this.orderState.put(found, "clean");
    }
    return found;
  }

  /**
   * Provides an interface to the client for the findAllOrders method in the Order repository
   * @return A list of Orders that currently exist in the repository
   */
  @Override
  public List<Order> findAllOrders() {
    return this.orderRepository.findAllOrders();
  }

  /**
   * After this query is complete, the database should match the state of the cache before the
   * query is called. Additionally, the state of each Order will be cleared, while the cache will
   * remain in tact
   */
  @Override
  public void commit() {
    for (Map.Entry<Order, String> entry : this.orderState.entrySet()) {
      Order order = entry.getKey();
      String state = entry.getValue();
      switch (state) {
        case "new" -> saveObject(order);
        case "dirty" -> updateObject(order);
        case "deleted" -> deleteObject(order);
      }
    }
    this.orderState.clear();
  }

  /**
   * Removes all tracked states from cached Orders, and resets the cache (repository) to an empty state
   */
  @Override
  public void rollback() {
    for (Order oldOrder : this.orderRepository.findAllOrders()) {
      this.orderRepository.delete(oldOrder);
    }

    this.orderState.clear();
  }

  /**
   * Saves an Order as new to the database
   * @param order The Order to save
   */
  private void saveObject(Order order) {
      if (null == order) {
        return;
      }
      TestDatabase db = TestDatabase.getInstance();
      db.saveOrder(this.token, order);
    }

  /**
   * Updates an Order in the database
   * @param order The Order to update
   */
  private void updateObject(Order order) {
    if (null == order) {
      return;
    }
    TestDatabase db = TestDatabase.getInstance();
    db.saveOrder(this.token, order);
  }

  /**
   * Deletes an Order from the database
   * @param order The Order to delete
   */
  private void deleteObject(Order order) {
      if (null == order) {
        return;
      }
      TestDatabase db = TestDatabase.getInstance();
      db.removeOrder(this.token, order.getOrderID());
    }
}
