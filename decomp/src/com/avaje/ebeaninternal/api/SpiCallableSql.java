package com.avaje.ebeaninternal.api;

import com.avaje.ebean.CallableSql;

public interface SpiCallableSql extends CallableSql {
  BindParams getBindParams();
  
  TransactionEventTable getTransactionEventTable();
}


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\api\SpiCallableSql.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */