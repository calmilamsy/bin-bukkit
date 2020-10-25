package com.avaje.ebeaninternal.api;

import com.avaje.ebeaninternal.server.core.ConcurrencyMode;
import com.avaje.ebeaninternal.server.persist.dml.DmlHandler;
import com.avaje.ebeaninternal.server.persist.dmlbind.Bindable;
import java.sql.SQLException;
import java.util.Set;

public interface SpiUpdatePlan {
  boolean isEmptySetClause();
  
  void bindSet(DmlHandler paramDmlHandler, Object paramObject) throws SQLException;
  
  long getTimeCreated();
  
  Long getTimeLastUsed();
  
  Integer getKey();
  
  ConcurrencyMode getMode();
  
  String getSql();
  
  Bindable getSet();
  
  Set<String> getProperties();
}


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\api\SpiUpdatePlan.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */