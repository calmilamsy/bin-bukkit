package com.avaje.ebean.config;

public interface CompoundType<V> {
  V create(Object[] paramArrayOfObject);
  
  CompoundTypeProperty<V, ?>[] getProperties();
}


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebean\config\CompoundType.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */