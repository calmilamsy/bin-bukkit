package com.avaje.ebean;

import java.beans.PropertyChangeListener;
import java.util.Set;

public interface BeanState {
  boolean isReference();
  
  boolean isNew();
  
  boolean isNewOrDirty();
  
  boolean isDirty();
  
  Set<String> getLoadedProps();
  
  Set<String> getChangedProps();
  
  boolean isReadOnly();
  
  void setReadOnly(boolean paramBoolean);
  
  boolean isSharedInstance();
  
  void addPropertyChangeListener(PropertyChangeListener paramPropertyChangeListener);
  
  void removePropertyChangeListener(PropertyChangeListener paramPropertyChangeListener);
  
  void setReference();
  
  void setLoaded(Set<String> paramSet);
}


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebean\BeanState.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */