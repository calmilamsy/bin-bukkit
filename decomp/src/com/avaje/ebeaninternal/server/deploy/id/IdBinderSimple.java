/*     */ package com.avaje.ebeaninternal.server.deploy.id;
/*     */ 
/*     */ import com.avaje.ebeaninternal.api.SpiExpressionRequest;
/*     */ import com.avaje.ebeaninternal.server.core.DefaultSqlUpdate;
/*     */ import com.avaje.ebeaninternal.server.core.InternString;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanProperty;
/*     */ import com.avaje.ebeaninternal.server.deploy.DbReadContext;
/*     */ import com.avaje.ebeaninternal.server.deploy.DbSqlContext;
/*     */ import com.avaje.ebeaninternal.server.type.DataBind;
/*     */ import com.avaje.ebeaninternal.server.type.ScalarType;
/*     */ import java.io.DataInput;
/*     */ import java.io.DataOutput;
/*     */ import java.io.IOException;
/*     */ import java.sql.SQLException;
/*     */ import java.util.List;
/*     */ import javax.naming.InvalidNameException;
/*     */ import javax.naming.ldap.LdapName;
/*     */ import javax.naming.ldap.Rdn;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class IdBinderSimple
/*     */   implements IdBinder
/*     */ {
/*     */   private final BeanProperty idProperty;
/*     */   private final String bindIdSql;
/*     */   private final BeanProperty[] properties;
/*     */   private final Class<?> expectedType;
/*     */   private final ScalarType scalarType;
/*     */   
/*     */   public IdBinderSimple(BeanProperty idProperty) {
/*  38 */     this.idProperty = idProperty;
/*  39 */     this.scalarType = idProperty.getScalarType();
/*  40 */     this.expectedType = idProperty.getPropertyType();
/*  41 */     this.properties = new BeanProperty[1];
/*  42 */     this.properties[0] = idProperty;
/*  43 */     this.bindIdSql = InternString.intern(idProperty.getDbColumn() + " = ? ");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void initialise() {}
/*     */ 
/*     */   
/*  51 */   public Object readTerm(String idTermValue) { return this.scalarType.parse(idTermValue); }
/*     */ 
/*     */ 
/*     */   
/*  55 */   public String writeTerm(Object idValue) { return this.scalarType.format(idValue); }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getOrderBy(String pathPrefix, boolean ascending) {
/*  60 */     StringBuilder sb = new StringBuilder();
/*  61 */     if (pathPrefix != null) {
/*  62 */       sb.append(pathPrefix).append(".");
/*     */     }
/*  64 */     sb.append(this.idProperty.getName());
/*  65 */     if (!ascending) {
/*  66 */       sb.append(" desc");
/*     */     }
/*  68 */     return sb.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*  73 */   public void buildSelectExpressionChain(String prefix, List<String> selectChain) { this.idProperty.buildSelectExpressionChain(prefix, selectChain); }
/*     */ 
/*     */   
/*     */   public void createLdapNameById(LdapName name, Object id) throws InvalidNameException {
/*  77 */     Rdn rdn = new Rdn(this.idProperty.getDbColumn(), id);
/*  78 */     name.add(rdn);
/*     */   }
/*     */   
/*     */   public void createLdapNameByBean(LdapName name, Object bean) throws InvalidNameException {
/*  82 */     Object id = this.idProperty.getValue(bean);
/*  83 */     createLdapNameById(name, id);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  90 */   public int getPropertyCount() { return 1; }
/*     */ 
/*     */ 
/*     */   
/*  94 */   public String getIdProperty() { return this.idProperty.getName(); }
/*     */ 
/*     */   
/*     */   public BeanProperty findBeanProperty(String dbColumnName) {
/*  98 */     if (dbColumnName.equalsIgnoreCase(this.idProperty.getDbColumn())) {
/*  99 */       return this.idProperty;
/*     */     }
/* 101 */     return null;
/*     */   }
/*     */ 
/*     */   
/* 105 */   public boolean isComplexId() { return false; }
/*     */ 
/*     */ 
/*     */   
/* 109 */   public String getDefaultOrderBy() { return this.idProperty.getName(); }
/*     */ 
/*     */ 
/*     */   
/* 113 */   public BeanProperty[] getProperties() { return this.properties; }
/*     */ 
/*     */   
/*     */   public String getBindIdInSql(String baseTableAlias) {
/* 117 */     if (baseTableAlias == null) {
/* 118 */       return this.idProperty.getDbColumn();
/*     */     }
/* 120 */     return baseTableAlias + "." + this.idProperty.getDbColumn();
/*     */   }
/*     */ 
/*     */   
/*     */   public String getBindIdSql(String baseTableAlias) {
/* 125 */     if (baseTableAlias == null) {
/* 126 */       return this.bindIdSql;
/*     */     }
/* 128 */     return baseTableAlias + "." + this.bindIdSql;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 133 */   public Object[] getIdValues(Object bean) { return new Object[] { this.idProperty.getValue(bean) }; }
/*     */ 
/*     */ 
/*     */   
/* 137 */   public Object[] getBindValues(Object idValue) { return new Object[] { idValue }; }
/*     */ 
/*     */ 
/*     */   
/* 141 */   public String getIdInValueExprDelete(int size) { return getIdInValueExpr(size); }
/*     */ 
/*     */   
/*     */   public String getIdInValueExpr(int size) {
/* 145 */     StringBuilder sb = new StringBuilder(2 * size + 10);
/* 146 */     sb.append(" in");
/* 147 */     sb.append(" (?");
/* 148 */     for (int i = 1; i < size; i++) {
/* 149 */       sb.append(",?");
/*     */     }
/* 151 */     sb.append(") ");
/* 152 */     return sb.toString();
/*     */   }
/*     */   
/*     */   public void addIdInBindValue(SpiExpressionRequest request, Object value) {
/* 156 */     value = convertSetId(value, null);
/* 157 */     request.addBindValue(value);
/*     */   }
/*     */ 
/*     */   
/* 161 */   public void bindId(DefaultSqlUpdate sqlUpdate, Object value) { sqlUpdate.addParameter(value); }
/*     */ 
/*     */   
/*     */   public void bindId(DataBind dataBind, Object value) throws SQLException {
/* 165 */     value = this.idProperty.toBeanType(value);
/* 166 */     this.idProperty.bind(dataBind, value);
/*     */   }
/*     */ 
/*     */   
/* 170 */   public void writeData(DataOutput os, Object value) throws IOException { this.idProperty.writeData(os, value); }
/*     */ 
/*     */ 
/*     */   
/* 174 */   public Object readData(DataInput is) throws IOException { return this.idProperty.readData(is); }
/*     */ 
/*     */ 
/*     */   
/* 178 */   public void loadIgnore(DbReadContext ctx) { this.idProperty.loadIgnore(ctx); }
/*     */ 
/*     */   
/*     */   public Object readSet(DbReadContext ctx, Object bean) throws SQLException {
/* 182 */     Object id = this.idProperty.read(ctx);
/* 183 */     if (id != null) {
/* 184 */       this.idProperty.setValue(bean, id);
/*     */     }
/* 186 */     return id;
/*     */   }
/*     */ 
/*     */   
/* 190 */   public Object read(DbReadContext ctx) throws SQLException { return this.idProperty.read(ctx); }
/*     */ 
/*     */ 
/*     */   
/* 194 */   public void appendSelect(DbSqlContext ctx, boolean subQuery) { this.idProperty.appendSelect(ctx, subQuery); }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getAssocOneIdExpr(String prefix, String operator) {
/* 199 */     StringBuilder sb = new StringBuilder();
/* 200 */     if (prefix != null) {
/* 201 */       sb.append(prefix);
/* 202 */       sb.append(".");
/*     */     } 
/* 204 */     sb.append(this.idProperty.getName());
/* 205 */     sb.append(operator);
/* 206 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   public String getAssocIdInExpr(String prefix) {
/* 211 */     StringBuilder sb = new StringBuilder();
/* 212 */     if (prefix != null) {
/* 213 */       sb.append(prefix);
/* 214 */       sb.append(".");
/*     */     } 
/* 216 */     sb.append(this.idProperty.getName());
/* 217 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   public Object convertSetId(Object idValue, Object bean) {
/* 222 */     if (!idValue.getClass().equals(this.expectedType)) {
/* 223 */       idValue = this.scalarType.toBeanType(idValue);
/*     */     }
/*     */     
/* 226 */     if (bean != null)
/*     */     {
/* 228 */       this.idProperty.setValueIntercept(bean, idValue);
/*     */     }
/*     */     
/* 231 */     return idValue;
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\deploy\id\IdBinderSimple.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */