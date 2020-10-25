package javax.persistence;

import java.util.Map;

public interface EntityManagerFactory {
  EntityManager createEntityManager();
  
  EntityManager createEntityManager(Map paramMap);
  
  void close();
  
  boolean isOpen();
}


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\javax\persistence\EntityManagerFactory.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */