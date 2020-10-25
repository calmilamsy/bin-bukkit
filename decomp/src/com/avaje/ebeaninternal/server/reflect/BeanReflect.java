package com.avaje.ebeaninternal.server.reflect;

public interface BeanReflect {
  Object createEntityBean();
  
  Object createVanillaBean();
  
  boolean isVanillaOnly();
  
  BeanReflectGetter getGetter(String paramString);
  
  BeanReflectSetter getSetter(String paramString);
}


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\reflect\BeanReflect.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */