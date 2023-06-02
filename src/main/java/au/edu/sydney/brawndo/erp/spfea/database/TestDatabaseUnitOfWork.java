package au.edu.sydney.brawndo.erp.spfea.database;
import au.edu.sydney.brawndo.erp.auth.AuthToken;
import au.edu.sydney.brawndo.erp.database.TestDatabase;
import au.edu.sydney.brawndo.erp.ordering.Order;
import java.util.HashMap;
import java.util.Map;

public class TestDatabaseUnitOfWork implements DatabaseUnitOfWork {
    private final Map<Order, String> orderState = new HashMap<>();
    private final AuthToken token;

    public TestDatabaseUnitOfWork(AuthToken token) {
      this.token = token;
    }

    @Override
    public void registerNew(Object object) {
      if (null == object) {
        return;
      }
      Order orderObj = (Order) object;
      this.orderState.put(orderObj, "new");
    }

    @Override
    public void registerDirty(Object object) {
      if (null == object) {
        return;
      }
      Order orderObj = (Order) object;
      this.orderState.put(orderObj, "dirty");
    }

    @Override
    public void registerClean(Object object) {
      if (null == object) {
        return;
      }
      Order orderObj = (Order) object;
      this.orderState.put(orderObj, "clean");
    }

    @Override
    public void registerDeleted(Object object) {
      if (null == object) {
        return;
      }
      Order orderObj = (Order) object;
      this.orderState.put(orderObj, "deleted");
    }

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

    @Override
    public void rollback() {
      this.orderState.clear();
    }

    private void saveObject(Order order) {
      if (null == order) {
        return;
      }
      TestDatabase db = TestDatabase.getInstance();

      db.saveOrder(this.token, order);
    }
    private void updateObject(Order order) {
      if (null == order) {
        return;
      }
      TestDatabase db = TestDatabase.getInstance();

      db.saveOrder(this.token, order);
    }
    private void deleteObject(Order order) {
      if (null == order) {
        return;
      }
      TestDatabase db = TestDatabase.getInstance();

      db.removeOrder(this.token, order.getOrderID());
    }
}
