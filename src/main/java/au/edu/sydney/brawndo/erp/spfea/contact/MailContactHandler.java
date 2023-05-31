package au.edu.sydney.brawndo.erp.spfea.contact;

import au.edu.sydney.brawndo.erp.auth.AuthToken;
import au.edu.sydney.brawndo.erp.contact.Mail;
import au.edu.sydney.brawndo.erp.ordering.Customer;

/**
 * Part of the Chain of Responsibility pattern. This class extends ContactHandler to
 * act as a Concrete Handler as part of the pattern.
 *
 * Part of the static Factory Method pattern. This class acts as the Product that is produced by
 * the Factory
 */
public class MailContactHandler extends ContactHandler {
  @Override
  public boolean sendInvoice(AuthToken token, Customer customer, String data) {
    if (null != customer) {
      String address = customer.getAddress();
      String suburb = customer.getSuburb();
      String state = customer.getState();
      String postcode = customer.getPostCode();
      if (null != address && null != suburb &&
          null != state && null != postcode) {
        Mail.sendInvoice(token, customer.getfName(), customer.getlName(), data, address, suburb, state, postcode);
        return true;
      }
    }

    return this.delegateToSuccessor(token, customer, data);
  }
}
