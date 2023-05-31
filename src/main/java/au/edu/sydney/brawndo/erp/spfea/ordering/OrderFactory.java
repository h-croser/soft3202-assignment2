package au.edu.sydney.brawndo.erp.spfea.ordering;

import au.edu.sydney.brawndo.erp.ordering.Order;
import java.time.LocalDateTime;

public interface OrderFactory {
  Order createOrder(int customerID,
                    LocalDateTime date,
                    boolean isBusiness,
                    int discountType,
                    int discountThreshold,
                    int discountRateRaw,
                    int numShipments);
}
