package com.avaje.ebeaninternal.server.transaction;

public interface TransactionLogWriter {
  void log(TransactionLogBuffer paramTransactionLogBuffer);
  
  void shutdown();
}


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\transaction\TransactionLogWriter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */