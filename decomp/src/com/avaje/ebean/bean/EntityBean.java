package com.avaje.ebean.bean;

import java.beans.PropertyChangeListener;
import java.io.Serializable;

public interface EntityBean extends Serializable {
  String _ebean_getMarker();
  
  Object _ebean_newInstance();
  
  void addPropertyChangeListener(PropertyChangeListener paramPropertyChangeListener);
  
  void removePropertyChangeListener(PropertyChangeListener paramPropertyChangeListener);
  
  void _ebean_setEmbeddedLoaded();
  
  boolean _ebean_isEmbeddedNewOrDirty();
  
  EntityBeanIntercept _ebean_getIntercept();
  
  EntityBeanIntercept _ebean_intercept();
  
  Object _ebean_createCopy();
  
  String[] _ebean_getFieldNames();
  
  void _ebean_setField(int paramInt, Object paramObject1, Object paramObject2);
  
  void _ebean_setFieldIntercept(int paramInt, Object paramObject1, Object paramObject2);
  
  Object _ebean_getField(int paramInt, Object paramObject);
  
  Object _ebean_getFieldIntercept(int paramInt, Object paramObject);
}


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebean\bean\EntityBean.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */