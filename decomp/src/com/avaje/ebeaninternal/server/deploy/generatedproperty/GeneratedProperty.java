package com.avaje.ebeaninternal.server.deploy.generatedproperty;

import com.avaje.ebeaninternal.server.deploy.BeanProperty;

public interface GeneratedProperty {
  Object getInsertValue(BeanProperty paramBeanProperty, Object paramObject);
  
  Object getUpdateValue(BeanProperty paramBeanProperty, Object paramObject);
  
  boolean includeInUpdate();
  
  boolean includeInInsert();
  
  boolean isDDLNotNullable();
}


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\deploy\generatedproperty\GeneratedProperty.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */