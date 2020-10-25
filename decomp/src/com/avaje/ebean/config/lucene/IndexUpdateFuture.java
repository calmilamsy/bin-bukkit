package com.avaje.ebean.config.lucene;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public interface IndexUpdateFuture {
  Class<?> getBeanType();
  
  boolean isCancelled();
  
  boolean cancel(boolean paramBoolean);
  
  Integer get() throws InterruptedException, ExecutionException;
  
  Integer get(long paramLong, TimeUnit paramTimeUnit) throws InterruptedException, ExecutionException, TimeoutException;
  
  boolean isDone();
}


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebean\config\lucene\IndexUpdateFuture.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */