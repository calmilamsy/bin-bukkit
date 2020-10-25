package javax.persistence;

public interface EntityTransaction {
  void begin();
  
  void commit();
  
  void rollback();
  
  void setRollbackOnly();
  
  boolean getRollbackOnly();
  
  boolean isActive();
}


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\javax\persistence\EntityTransaction.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */