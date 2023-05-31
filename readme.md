# Assignment 2: Design Patterns

## Solutions to the Key Issues

### RAM Issue

#### Flyweight

- Flyweight Factory: spfea.products.ProductDataFlyweightFactory
  - Only the Factory component of the Flyweight pattern is necessary. The Flyweight object itself is unnecessary because there is no extrinsic state within the data and there are no shared operations beyond the double array methods
  - SPFEAFacade.getAllProducts() now calls ProductDataFlyweightFactory.getProductDataFlyweight() for each ProductImpl attribute

### Too Many Orders

### Bulky Contact Method

#### Chain of Responsibility

- Sender: spfea.SPFEAFacade
- Handler: spfea.contact.ContactHandler
- Concrete Handler: spfea.contact.CarrierPigeonContactHandler
- Concrete Handler: spfea.contact.EmailContactHandler
- Concrete Handler: spfea.contact.MailContactHandler
- Concrete Handler: spfea.contact.MerchandiserContactHandler
- Concrete Handler: spfea.contact.PhoneCallContactHandler
- Concrete Handler: spfea.contact.SMSContactHandler

#### Abstract Factory (Variant: Concrete Factory)

- Client: spfea.SPFEAFacade
- Factory: spfea.contact.ContactHandlerFactory
  - This Factory acts as both interface and implementation in this variant because there are not multiple families of objects to create in this case (as outlined in GoF).
    Additionally, the ContactHandler objects can be created without any unique information passed to their constructors, so a single Factory can create all objects
  - The existing spfea.ContactMethod and spfea.ContactHandler were removed as they are now redundant

### System Lag

#### Lazy Load - Value Holder

- Value Holder: spfea.ValueHolderCustomerImpl
  - Standard Value Holder pattern applied to CustomerImpl, with the modification that it contains a collection for cached CustomerImpl objects
  - SPFEAFacade.getCustomer() now calls ValueHolderCustomerImpl.getCustomer() instead of creating an instance of CustomerImpl()

### Hard to Compare Products

#### Value Object

- Value Object: spfea.products.ProductImpl
  - equals() and hashcode() methods were overridden

### Slow Order Creation

## Notes About the Submission

