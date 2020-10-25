package javax.persistence.spi;

import java.net.URL;
import java.util.List;
import java.util.Properties;
import javax.sql.DataSource;

public interface PersistenceUnitInfo {
  String getPersistenceUnitName();
  
  String getPersistenceProviderClassName();
  
  PersistenceUnitTransactionType getTransactionType();
  
  DataSource getJtaDataSource();
  
  DataSource getNonJtaDataSource();
  
  List<String> getMappingFileNames();
  
  List<URL> getJarFileUrls();
  
  URL getPersistenceUnitRootUrl();
  
  List<String> getManagedClassNames();
  
  boolean excludeUnlistedClasses();
  
  Properties getProperties();
  
  ClassLoader getClassLoader();
  
  void addTransformer(ClassTransformer paramClassTransformer);
  
  ClassLoader getNewTempClassLoader();
}


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\javax\persistence\spi\PersistenceUnitInfo.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */