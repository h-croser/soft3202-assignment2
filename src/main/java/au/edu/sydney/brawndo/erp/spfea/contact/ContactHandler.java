package au.edu.sydney.brawndo.erp.spfea.contact;

import au.edu.sydney.brawndo.erp.auth.AuthToken;
import au.edu.sydney.brawndo.erp.ordering.Customer;

/**
 * Part of the Chain of Responsibility pattern. All ContactHandler objects have a successor attribute,
 * which is the next ContactHandler that will handle the sendInvoice request if the first object is
 * unable to handle the request. The abstract method sendInvoice is implemented with details
 * specific to the given contact method.
 * The Chain of Responsibility pattern applied here abstracts away the priority list functionality
 * to this abstract class and encapsulates the details of contact methods to the extensions of this
 * class.
 */
public abstract class ContactHandler {
  protected ContactHandler successor;

  /**
   * Attempts to send the invoice. If the invoice cannot be sent, the successor is invoked to
   * handle the request. Each ContactHandler extension will have different implementations of this
   * method depending on the required information
   * @param token The authorisation token for the request
   * @param customer The customer who will be sent the order invoice
   * @param data The order invoice data to be sent to the customer
   * @return true if the invoice is successfully sent, false if none of the ContactHandlers in the
   * chain can fulfill the request.
   */
  public abstract boolean sendInvoice(AuthToken token, Customer customer, String data);

  /**
   * Assigns a successor to handle invoice requests if this ContactHandler cannot handle the request
   * @param successor The next ContactHandler object in the priority hierarchy
   */
  public void setSuccessor(ContactHandler successor) {
    this.successor = successor;
  }

  /**
   * If this point is reached, this ContactHandler cannot handle the invoice and must send the
   * request onto it's successor, if one exists.
   * Calls sendInvoice on the successor and returns the outcome of this call. If the successor is
   * null, the end of the chain has been reached and this method returns false.
   */
  protected boolean delegateToSuccessor(AuthToken token, Customer customer, String data) {
    if (null != this.successor) {
      return this.successor.sendInvoice(token, customer, data);
    }
    // If this ContactHandler is the last in the chain, then it will return false to bubble up the chain
    return false;
  }
}
