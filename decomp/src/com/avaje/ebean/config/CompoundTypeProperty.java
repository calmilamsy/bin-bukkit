package com.avaje.ebean.config;

public interface CompoundTypeProperty<V, P> {
  String getName();
  
  P getValue(V paramV);
  
  int getDbType();
}


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebean\config\CompoundTypeProperty.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */