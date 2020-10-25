/*     */ package com.avaje.ebeaninternal.server.deploy;
/*     */ 
/*     */ import com.avaje.ebean.text.StringFormatter;
/*     */ import com.avaje.ebean.text.StringParser;
/*     */ import com.avaje.ebeaninternal.server.el.ElPropertyValue;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class BeanFkeyProperty
/*     */   implements ElPropertyValue
/*     */ {
/*     */   private final String placeHolder;
/*     */   private final String prefix;
/*     */   private final String name;
/*     */   private final String dbColumn;
/*     */   private int deployOrder;
/*     */   
/*     */   public BeanFkeyProperty(String prefix, String name, String dbColumn, int deployOrder) {
/*  20 */     this.prefix = prefix;
/*  21 */     this.name = name;
/*  22 */     this.dbColumn = dbColumn;
/*  23 */     this.deployOrder = deployOrder;
/*  24 */     this.placeHolder = calcPlaceHolder(prefix, dbColumn);
/*     */   }
/*     */ 
/*     */   
/*  28 */   public int getDeployOrder() { return this.deployOrder; }
/*     */ 
/*     */   
/*     */   private String calcPlaceHolder(String prefix, String dbColumn) {
/*  32 */     if (prefix != null) {
/*  33 */       return "${" + prefix + "}" + dbColumn;
/*     */     }
/*  35 */     return "${}" + dbColumn;
/*     */   }
/*     */ 
/*     */   
/*     */   public BeanFkeyProperty create(String expression) {
/*  40 */     int len = expression.length() - this.name.length() - 1;
/*  41 */     String prefix = expression.substring(0, len);
/*     */     
/*  43 */     return new BeanFkeyProperty(prefix, this.name, this.dbColumn, this.deployOrder);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  50 */   public boolean isDbEncrypted() { return false; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  57 */   public boolean isLocalEncrypted() { return false; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  64 */   public boolean isDeployOnly() { return true; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  71 */   public boolean containsMany() { return false; }
/*     */ 
/*     */ 
/*     */   
/*  75 */   public boolean containsManySince(String sinceProperty) { return containsMany(); }
/*     */ 
/*     */ 
/*     */   
/*  79 */   public String getDbColumn() { return this.dbColumn; }
/*     */ 
/*     */ 
/*     */   
/*  83 */   public String getName() { return this.name; }
/*     */ 
/*     */ 
/*     */   
/*  87 */   public String getElName() { return this.name; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  94 */   public Object[] getAssocOneIdValues(Object value) { return null; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 101 */   public String getAssocOneIdExpr(String prefix, String operator) { return null; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 108 */   public String getAssocIdInExpr(String prefix) { return null; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 115 */   public String getAssocIdInValueExpr(int size) { return null; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 122 */   public boolean isAssocId() { return false; }
/*     */ 
/*     */ 
/*     */   
/* 126 */   public boolean isAssocProperty() { return false; }
/*     */ 
/*     */ 
/*     */   
/* 130 */   public String getElPlaceholder(boolean encrypted) { return this.placeHolder; }
/*     */ 
/*     */ 
/*     */   
/* 134 */   public String getElPrefix() { return this.prefix; }
/*     */ 
/*     */ 
/*     */   
/* 138 */   public boolean isDateTimeCapable() { return false; }
/*     */ 
/*     */ 
/*     */   
/* 142 */   public int getJdbcType() { return 0; }
/*     */ 
/*     */ 
/*     */   
/* 146 */   public Object parseDateTime(long systemTimeMillis) { throw new RuntimeException("ElPropertyDeploy only - not implemented"); }
/*     */ 
/*     */ 
/*     */   
/* 150 */   public StringFormatter getStringFormatter() { throw new RuntimeException("ElPropertyDeploy only - not implemented"); }
/*     */ 
/*     */ 
/*     */   
/* 154 */   public StringParser getStringParser() { throw new RuntimeException("ElPropertyDeploy only - not implemented"); }
/*     */ 
/*     */ 
/*     */   
/* 158 */   public void elSetReference(Object bean) { throw new RuntimeException("ElPropertyDeploy only - not implemented"); }
/*     */ 
/*     */ 
/*     */   
/* 162 */   public Object elConvertType(Object value) { throw new RuntimeException("ElPropertyDeploy only - not implemented"); }
/*     */ 
/*     */ 
/*     */   
/* 166 */   public void elSetValue(Object bean, Object value, boolean populate, boolean reference) { throw new RuntimeException("ElPropertyDeploy only - not implemented"); }
/*     */ 
/*     */ 
/*     */   
/* 170 */   public Object elGetValue(Object bean) { throw new RuntimeException("ElPropertyDeploy only - not implemented"); }
/*     */ 
/*     */ 
/*     */   
/* 174 */   public Object elGetReference(Object bean) { throw new RuntimeException("ElPropertyDeploy only - not implemented"); }
/*     */ 
/*     */ 
/*     */   
/* 178 */   public BeanProperty getBeanProperty() { throw new RuntimeException("ElPropertyDeploy only - not implemented"); }
/*     */ 
/*     */ 
/*     */   
/* 182 */   public String getDeployProperty() { throw new RuntimeException("ElPropertyDeploy only - not implemented"); }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\deploy\BeanFkeyProperty.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */