/*     */ package com.avaje.ebeaninternal.server.type;
/*     */ 
/*     */ import com.avaje.ebean.text.StringFormatter;
/*     */ import com.avaje.ebean.text.StringParser;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanProperty;
/*     */ import com.avaje.ebeaninternal.server.el.ElPropertyValue;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CtCompoundPropertyElAdapter
/*     */   implements ElPropertyValue
/*     */ {
/*     */   private final CtCompoundProperty prop;
/*     */   private int deployOrder;
/*     */   
/*  43 */   public CtCompoundPropertyElAdapter(CtCompoundProperty prop) { this.prop = prop; }
/*     */ 
/*     */ 
/*     */   
/*  47 */   public void setDeployOrder(int deployOrder) { this.deployOrder = deployOrder; }
/*     */ 
/*     */ 
/*     */   
/*  51 */   public Object elConvertType(Object value) { return value; }
/*     */ 
/*     */ 
/*     */   
/*  55 */   public Object elGetReference(Object bean) { return bean; }
/*     */ 
/*     */ 
/*     */   
/*  59 */   public Object elGetValue(Object bean) { return this.prop.getValue(bean); }
/*     */ 
/*     */ 
/*     */   
/*     */   public void elSetReference(Object bean) {}
/*     */ 
/*     */ 
/*     */   
/*  67 */   public void elSetValue(Object bean, Object value, boolean populate, boolean reference) { this.prop.setValue(bean, value); }
/*     */ 
/*     */ 
/*     */   
/*  71 */   public int getDeployOrder() { return this.deployOrder; }
/*     */ 
/*     */ 
/*     */   
/*  75 */   public String getAssocOneIdExpr(String prefix, String operator) { throw new RuntimeException("Not Supported or Expected"); }
/*     */ 
/*     */ 
/*     */   
/*  79 */   public Object[] getAssocOneIdValues(Object bean) { throw new RuntimeException("Not Supported or Expected"); }
/*     */ 
/*     */ 
/*     */   
/*  83 */   public String getAssocIdInExpr(String prefix) { throw new RuntimeException("Not Supported or Expected"); }
/*     */ 
/*     */ 
/*     */   
/*  87 */   public String getAssocIdInValueExpr(int size) { throw new RuntimeException("Not Supported or Expected"); }
/*     */ 
/*     */ 
/*     */   
/*  91 */   public BeanProperty getBeanProperty() { return null; }
/*     */ 
/*     */ 
/*     */   
/*  95 */   public StringFormatter getStringFormatter() { return null; }
/*     */ 
/*     */ 
/*     */   
/*  99 */   public StringParser getStringParser() { return null; }
/*     */ 
/*     */ 
/*     */   
/* 103 */   public boolean isDbEncrypted() { return false; }
/*     */ 
/*     */ 
/*     */   
/* 107 */   public boolean isLocalEncrypted() { return false; }
/*     */ 
/*     */ 
/*     */   
/* 111 */   public boolean isAssocId() { return false; }
/*     */ 
/*     */ 
/*     */   
/* 115 */   public boolean isAssocProperty() { return false; }
/*     */ 
/*     */ 
/*     */   
/* 119 */   public boolean isDateTimeCapable() { return false; }
/*     */ 
/*     */ 
/*     */   
/* 123 */   public int getJdbcType() { return 0; }
/*     */ 
/*     */ 
/*     */   
/* 127 */   public Object parseDateTime(long systemTimeMillis) { throw new RuntimeException("Not Supported or Expected"); }
/*     */ 
/*     */ 
/*     */   
/* 131 */   public boolean containsMany() { return false; }
/*     */ 
/*     */ 
/*     */   
/* 135 */   public boolean containsManySince(String sinceProperty) { return containsMany(); }
/*     */ 
/*     */ 
/*     */   
/* 139 */   public String getDbColumn() { return null; }
/*     */ 
/*     */ 
/*     */   
/* 143 */   public String getElPlaceholder(boolean encrypted) { return null; }
/*     */ 
/*     */ 
/*     */   
/* 147 */   public String getElPrefix() { return null; }
/*     */ 
/*     */ 
/*     */   
/* 151 */   public String getName() { return this.prop.getPropertyName(); }
/*     */ 
/*     */ 
/*     */   
/* 155 */   public String getElName() { return this.prop.getPropertyName(); }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\type\CtCompoundPropertyElAdapter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */