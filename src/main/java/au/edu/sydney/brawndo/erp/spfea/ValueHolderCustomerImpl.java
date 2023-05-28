package au.edu.sydney.brawndo.erp.spfea;

import au.edu.sydney.brawndo.erp.auth.AuthToken;
import java.util.ArrayList;
import java.util.List;

public class ValueHolderCustomerImpl {
  private final List<CustomerImpl> customers;

  public ValueHolderCustomerImpl() {
    this.customers = new ArrayList<>();
  }

  public CustomerImpl getCustomer(AuthToken token, int customerId) {
    for (CustomerImpl c : this.customers) {
      if (c.getId() == customerId) {
        // If the CustomerImpl object has been added to the list,
        // then its details have been queried and it can be returned
        ValueHolderCustomerImpl.displayGettingFieldMessage();
        return c;
      }
    }
    // If the method hasn't returned yet, the CustomerImpl object must be created and added to the list
    CustomerImpl customerToAdd = new CustomerImpl(token, customerId);
    this.customers.add(customerToAdd);

    return customerToAdd;
  }

  private static void displayGettingFieldMessage() {
    /*
     * The following message is printed to stdout in order to maintain consistency between
     * first-time and subsequent Customer querying
     */
    String getFieldMessage = "Getting customer field.done!";
    int NUMFIELDS = 11;
    for (int i = 0; i < NUMFIELDS; i++) {
      System.out.println(getFieldMessage);
    }
  }
}