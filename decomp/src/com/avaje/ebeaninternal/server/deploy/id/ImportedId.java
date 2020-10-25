package com.avaje.ebeaninternal.server.deploy.id;

import com.avaje.ebeaninternal.server.deploy.BeanProperty;
import com.avaje.ebeaninternal.server.deploy.DbSqlContext;
import com.avaje.ebeaninternal.server.deploy.IntersectionRow;
import com.avaje.ebeaninternal.server.persist.dml.GenerateDmlRequest;
import com.avaje.ebeaninternal.server.persist.dmlbind.BindableRequest;
import java.sql.SQLException;

public interface ImportedId {
  void addFkeys(String paramString);
  
  boolean isScalar();
  
  String getLogicalName();
  
  String getDbColumn();
  
  void sqlAppend(DbSqlContext paramDbSqlContext);
  
  void dmlAppend(GenerateDmlRequest paramGenerateDmlRequest);
  
  void dmlWhere(GenerateDmlRequest paramGenerateDmlRequest, Object paramObject);
  
  boolean hasChanged(Object paramObject1, Object paramObject2);
  
  void bind(BindableRequest paramBindableRequest, Object paramObject, boolean paramBoolean) throws SQLException;
  
  void buildImport(IntersectionRow paramIntersectionRow, Object paramObject);
  
  BeanProperty findMatchImport(String paramString);
}


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\deploy\id\ImportedId.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */