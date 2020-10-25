package com.avaje.ebean.config.dbplatform;

import com.avaje.ebean.Transaction;

public interface IdGenerator {
  public static final String AUTO_UUID = "auto.uuid";
  
  String getName();
  
  boolean isDbSequence();
  
  Object nextId(Transaction paramTransaction);
  
  void preAllocateIds(int paramInt);
}


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebean\config\dbplatform\IdGenerator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */