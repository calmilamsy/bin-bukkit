package com.avaje.ebeaninternal.server.persist.dmlbind;

import com.avaje.ebeaninternal.server.core.PersistRequestBean;

public interface BindableId extends Bindable {
  boolean isConcatenated();
  
  String getIdentityColumn();
  
  boolean deriveConcatenatedId(PersistRequestBean<?> paramPersistRequestBean);
}


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\persist\dmlbind\BindableId.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */