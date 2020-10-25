package com.avaje.ebeaninternal.server.deploy;

import com.avaje.ebean.cache.ServerCacheManager;
import com.avaje.ebean.config.EncryptKey;
import com.avaje.ebeaninternal.server.deploy.id.IdBinder;

public interface BeanDescriptorMap {
  String getServerName();
  
  ServerCacheManager getCacheManager();
  
  <T> BeanDescriptor<T> getBeanDescriptor(Class<T> paramClass);
  
  EncryptKey getEncryptKey(String paramString1, String paramString2);
  
  IdBinder createIdBinder(BeanProperty[] paramArrayOfBeanProperty);
}


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\deploy\BeanDescriptorMap.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */