package com.avaje.ebeaninternal.server.el;

import com.avaje.ebeaninternal.server.deploy.BeanProperty;

public interface ElPropertyDeploy {
  public static final String ROOT_ELPREFIX = "${}";
  
  boolean containsMany();
  
  boolean containsManySince(String paramString);
  
  String getElPrefix();
  
  String getElPlaceholder(boolean paramBoolean);
  
  String getName();
  
  String getElName();
  
  String getDbColumn();
  
  BeanProperty getBeanProperty();
}


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\el\ElPropertyDeploy.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */