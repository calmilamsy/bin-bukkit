package com.avaje.ebeaninternal.server.persist.dmlbind;

import com.avaje.ebeaninternal.server.core.PersistRequestBean;
import com.avaje.ebeaninternal.server.persist.dml.GenerateDmlRequest;
import java.sql.SQLException;
import java.util.List;

public interface Bindable {
  void addChanged(PersistRequestBean<?> paramPersistRequestBean, List<Bindable> paramList);
  
  void dmlInsert(GenerateDmlRequest paramGenerateDmlRequest, boolean paramBoolean);
  
  void dmlAppend(GenerateDmlRequest paramGenerateDmlRequest, boolean paramBoolean);
  
  void dmlWhere(GenerateDmlRequest paramGenerateDmlRequest, boolean paramBoolean, Object paramObject);
  
  void dmlBind(BindableRequest paramBindableRequest, boolean paramBoolean, Object paramObject) throws SQLException;
  
  void dmlBindWhere(BindableRequest paramBindableRequest, boolean paramBoolean, Object paramObject) throws SQLException;
}


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\persist\dmlbind\Bindable.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */