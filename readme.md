# Assignment 2: Design Patterns

## Solutions to the Key Issues

### RAM Issue

### Too Many Orders

### Bulky Contact Method

### System Lag

### Hard to Compare Products

#### Solution Summary
The ProductImpl class has had the equals() method overloaded and this method has replaced the original comparisons throughout the codebase.
- ProductImpl: The equals() method has been overridden with a new implementation that compares each attribute of both objects to determine equality
- BusinessBulkDiscountOrder, FirstOrder, NewOrderImpl, Order66: setProduct() and getProductQty() now include calls to ProductImpl.equals() instead of the original equality check

#### Solution Benefit
This solution is beneficial to the design as it simplifies the equality comparison of ProductImpl objects to a single method call. 
This solution also encapsulates the functionality of equality comparison to the ProductImpl object,
which makes changes to equality comparison functionality centralised and far easier to change in future.

### Slow Order Creation

## Notes About the Submission