package com.avaje.ebeaninternal.server.deploy.id;

import com.avaje.ebeaninternal.api.SpiExpressionRequest;
import com.avaje.ebeaninternal.server.core.DefaultSqlUpdate;
import com.avaje.ebeaninternal.server.deploy.BeanProperty;
import com.avaje.ebeaninternal.server.deploy.DbReadContext;
import com.avaje.ebeaninternal.server.deploy.DbSqlContext;
import com.avaje.ebeaninternal.server.type.DataBind;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javax.naming.InvalidNameException;
import javax.naming.ldap.LdapName;

public interface IdBinder {
  void initialise();
  
  String writeTerm(Object paramObject);
  
  Object readTerm(String paramString);
  
  void writeData(DataOutput paramDataOutput, Object paramObject) throws IOException;
  
  Object readData(DataInput paramDataInput) throws IOException;
  
  void createLdapNameById(LdapName paramLdapName, Object paramObject) throws InvalidNameException;
  
  void createLdapNameByBean(LdapName paramLdapName, Object paramObject) throws InvalidNameException;
  
  String getIdProperty();
  
  BeanProperty findBeanProperty(String paramString);
  
  int getPropertyCount();
  
  boolean isComplexId();
  
  String getDefaultOrderBy();
  
  String getOrderBy(String paramString, boolean paramBoolean);
  
  Object[] getBindValues(Object paramObject);
  
  Object[] getIdValues(Object paramObject);
  
  String getAssocOneIdExpr(String paramString1, String paramString2);
  
  String getAssocIdInExpr(String paramString);
  
  void bindId(DataBind paramDataBind, Object paramObject) throws SQLException;
  
  void bindId(DefaultSqlUpdate paramDefaultSqlUpdate, Object paramObject);
  
  void addIdInBindValue(SpiExpressionRequest paramSpiExpressionRequest, Object paramObject);
  
  String getBindIdInSql(String paramString);
  
  String getIdInValueExpr(int paramInt);
  
  String getIdInValueExprDelete(int paramInt);
  
  void buildSelectExpressionChain(String paramString, List<String> paramList);
  
  Object readSet(DbReadContext paramDbReadContext, Object paramObject) throws SQLException;
  
  void loadIgnore(DbReadContext paramDbReadContext);
  
  Object read(DbReadContext paramDbReadContext) throws SQLException;
  
  void appendSelect(DbSqlContext paramDbSqlContext, boolean paramBoolean);
  
  String getBindIdSql(String paramString);
  
  BeanProperty[] getProperties();
  
  Object convertSetId(Object paramObject1, Object paramObject2);
}


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\deploy\id\IdBinder.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */