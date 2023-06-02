package au.edu.sydney.brawndo.erp.spfea;

import au.edu.sydney.brawndo.erp.auth.AuthToken;
import java.util.ArrayList;
import java.util.List;

public class ValueHolderCustomerImpl {
  private final List<CustomerImpl> customers;

  public ValueHolderCustomerImpl() {
    this.customers = new ArrayList<>();
  }

  /**
   * Retrieves the Customer object specified by the given customerID.
   * If the Customer is present in the Value Holder's Customer list, the Customer is retrieved
   * from the cache, otherwise a database query is made and the Customer is added to the cache.
   * @param token The authorisation token needed for Customer instantiation
   * @param customerID The ID of the Customer to be retrieved
   * @return The Customer object that has the given customerID
   */
  public CustomerImpl getCustomer(AuthToken token, int customerID) {
    for (CustomerImpl c : this.customers) {
      if (c.getId() == customerID) {
        // If the CustomerImpl object has been added to the list,
        // then its details have been queried and it can be returned
        ValueHolderCustomerImpl.displayGettingFieldMessage();
        return c;
      }
    }
    // If the method hasn't returned yet, the CustomerImpl object must be created and added to the list
    CustomerImpl customerToAdd = new CustomerImpl(token, customerID);
    this.customers.add(customerToAdd);

    return customerToAdd;
  }

  /**
   * Prints a message indicating Customer field access in order to maintain consistency between
   * first-time and subsequent Customer querying
   */
  private static void displayGettingFieldMessage() {
    String getFieldMessage = "Getting customer field.done!";
    int NUMFIELDS = 11;
    for (int i = 0; i < NUMFIELDS; i++) {
      System.out.println(getFieldMessage);
    }
  }
}