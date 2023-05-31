package au.edu.sydney.brawndo.erp.spfea.contact;

import java.util.Arrays;
import java.util.List;

/**
 * Part of the static Factory Method pattern. Given a contact method String, ContactHandlerFactory.createHandlerInstance()
 * will provide the appropriate ContactHandler object.
 * This class facilitates extending the Contact methods beyond the existing methods,
 * hiding the specifics of the implementations from the SPFEAFacade
 */
public class ContactHandlerFactory {
  private static final List<String> knownMethods = Arrays.asList("Carrier Pigeon", "Email", "Mail", "Merchandiser", "Phone call", "SMS");

  /**
   * @return A List of all the known contact method names, as Strings
   */
  public static List<String> getKnownMethods() {
    return knownMethods;
  }

  /**
   * Returns a corresponding ContactHandler object based on the method String,
   * defaulting to null for invalid method Strings
   * @param method The contact method String that determines the ContactHandler object
   * @return The ContactHandler object that corresponds to the method String. null if method is not a valid contact method
   */
  public static ContactHandler createHandlerInstance(String method) {
    if (!isKnownMethod(method)) {
      return null;
    }
    ContactHandler handler;
    handler = switch (method.toLowerCase()) {
      case "merchandiser" -> new MerchandiserContactHandler();
      case "email" -> new EmailContactHandler();
      case "carrier pigeon" -> new CarrierPigeonContactHandler();
      case "mail" -> new MailContactHandler();
      case "phone call" -> new PhoneCallContactHandler();
      case "sms" -> new SMSContactHandler();
      default -> null;
    };

    return handler;
  }

  /**
   * Checks if the method String is in the knownMethods List. The comparison is case-insensitive.
   * Returns false if method is null.
   * @param method The contact method String to check for existence
   * @return true if the String method is in the knownMethods List, false otherwise
   */
  private static boolean isKnownMethod(String method) {
    if (null == method) {
      return false;
    }
    for (String known : knownMethods) {
      if (method.equalsIgnoreCase(known)) {
        return true;
      }
    }
    return false;
  }
}
