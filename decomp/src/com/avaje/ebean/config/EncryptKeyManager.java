package com.avaje.ebean.config;

public interface EncryptKeyManager {
  void initialise();
  
  EncryptKey getEncryptKey(String paramString1, String paramString2);
}


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebean\config\EncryptKeyManager.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */