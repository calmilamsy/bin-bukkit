package javax.persistence.spi;

import java.util.Map;
import javax.persistence.EntityManagerFactory;

public interface PersistenceProvider {
  EntityManagerFactory createEntityManagerFactory(String paramString, Map paramMap);
  
  EntityManagerFactory createContainerEntityManagerFactory(PersistenceUnitInfo paramPersistenceUnitInfo, Map paramMap);
}


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\javax\persistence\spi\PersistenceProvider.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */