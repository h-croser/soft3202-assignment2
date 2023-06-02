package au.edu.sydney.brawndo.erp.spfea.database;

public interface DatabaseUnitOfWork {
    void registerNew(Object object);
    void registerDirty(Object object);
    void registerClean(Object object);
    void registerDeleted(Object object);

    void commit();
    void rollback();
}
