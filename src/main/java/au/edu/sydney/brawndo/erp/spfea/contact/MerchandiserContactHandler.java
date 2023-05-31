package au.edu.sydney.brawndo.erp.spfea.contact;

import au.edu.sydney.brawndo.erp.auth.AuthToken;
import au.edu.sydney.brawndo.erp.contact.Merchandiser;
import au.edu.sydney.brawndo.erp.ordering.Customer;

/**
 * Part of the Chain of Responsibility pattern. This class extends ContactHandler to
 * act as a Concrete Handler as part of the pattern.
 *
 * Part of the static Factory Method pattern. This class acts as the Product that is produced by
 * the Factory
 */
public class MerchandiserContactHandler extends ContactHandler {
  @Override
  public boolean sendInvoice(AuthToken token, Customer customer, String data) {
    if (null != customer) {
      String merchandiser = customer.getMerchandiser();
      String businessName = customer.getBusinessName();
      if (null != merchandiser && null != businessName) {
        Merchandiser.sendInvoice(token, customer.getfName(), customer.getlName(), data, merchandiser, businessName);
        return true;
      }
    }

    return this.delegateToSuccessor(token, customer, data);
  }
}
