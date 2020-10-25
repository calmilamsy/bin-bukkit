package com.avaje.ebeaninternal.server.el;

import com.avaje.ebean.text.StringFormatter;
import com.avaje.ebean.text.StringParser;

public interface ElPropertyValue extends ElPropertyDeploy {
  Object[] getAssocOneIdValues(Object paramObject);
  
  String getAssocOneIdExpr(String paramString1, String paramString2);
  
  String getAssocIdInValueExpr(int paramInt);
  
  String getAssocIdInExpr(String paramString);
  
  boolean isAssocId();
  
  boolean isAssocProperty();
  
  boolean isLocalEncrypted();
  
  boolean isDbEncrypted();
  
  int getDeployOrder();
  
  StringParser getStringParser();
  
  StringFormatter getStringFormatter();
  
  boolean isDateTimeCapable();
  
  int getJdbcType();
  
  Object parseDateTime(long paramLong);
  
  Object elGetValue(Object paramObject);
  
  Object elGetReference(Object paramObject);
  
  void elSetValue(Object paramObject1, Object paramObject2, boolean paramBoolean1, boolean paramBoolean2);
  
  void elSetReference(Object paramObject);
  
  Object elConvertType(Object paramObject);
}


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\el\ElPropertyValue.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */