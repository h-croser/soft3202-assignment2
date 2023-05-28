# Assignment 2: Design Patterns

## Solutions to the Key Issues

### RAM Issue

### Too Many Orders

### Bulky Contact Method

### System Lag

#### Lazy Load - Value Holder

- Value Holder: ValueHolderCustomerImpl
  - Standard Value Holder pattern applied to CustomerImpl, with the modification that it contains a collection for cached CustomerImpl objects
  - SPFEEAFacade.getCustomer() now calls ValueHolderCustomerImpl.getCustomer() instead of creating an instance of CustomerImpl()

### Hard to Compare Products

#### Value Object

- Value Object: ProductImpl
  - equals() and hashcode() methods were overridden

### Slow Order Creation

## Notes About the Submission