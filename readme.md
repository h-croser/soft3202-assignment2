# Assignment 2: Design Patterns

## Solutions to the Key Issues

### RAM Issue

#### Flyweight

- Client: spfea.SPFEAFacade
- ConcreteFlyweight: arrays of doubles
  - Only the Factory component of the Flyweight pattern is necessary. The Flyweight object itself is unnecessary because there is no extrinsic state within the data and there are no shared operations beyond the double array methods
- Flyweight Factory: spfea.products.ProductDataFlyweightFactory

### Too Many Orders

#### Bridge

- Client: spfea.SPFEAFacade
- Abstraction: ordering.Order
  - RefinedAbstraction: spfea.ordering.OrderImpl
- Implementor: spfea.ordering.DescriptionImplementor
  - ConcreteImplementor: spfea.ordering.BasicDescriptionImplementor
- Implementor: spfea.ordering.DiscountImplementor
  - ConcreteImplementor: spfea.ordering.BulkDiscountImplementor
  - ConcreteImplementor: spfea.ordering.FlatDiscountImplementor
- Implementor: spfea.ordering.InvoiceDataImplementor
  - ConcreteImplementor: spfea.ordering.BusinessInvoiceDataImplementor
  - ConcreteImplementor: spfea.ordering.PersonalInvoiceDataImplementor

All implementations of Order previously in the spfea.ordering package were removed as they are now redundant

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

The existing spfea.ContactMethod and spfea.ContactHandler were removed as they are now redundant

### System Lag

#### Lazy Load - Value Holder

- Value Holder: spfea.ValueHolderCustomerImpl
  - Standard Value Holder pattern applied to CustomerImpl, with the modification that it contains a collection for cached CustomerImpl objects

SPFEAFacade.getCustomer() now calls ValueHolderCustomerImpl.getCustomer() instead of creating an instance of CustomerImpl()

### Hard to Compare Products

#### Value Object

- Value Object: spfea.products.ProductImpl
  - equals() and hashcode() methods were overridden

### Slow Order Creation

#### Unit of Work

- Client: spfea.SPFEAFacade
- Database: database.TestDatabase
- Unit of Work: spfea.database.OrderDatabaseUnitOfWork
- Concrete Unit of Work: spfea.database.OrderDatabaseUnitOfWorkImpl
  - OrderDatabaseUnitOfWorkImpl also contains methods to provide the client with access to some order repository methods

#### Repository

- Repository: spfea.database.OrderRepository
- Concrete Repository: spfea.database.OrderRepositoryImpl

## Notes About the Submission

- Package references in this readme are relative to au.edu.sydney.brawndo.erp 
- The public methods in spfea.SPFEAFacade are not commented with javadocs as they should be identical externally to the old codebase