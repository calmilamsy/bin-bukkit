/*     */ package com.avaje.ebeaninternal.server.deploy.id;
/*     */ 
/*     */ import com.avaje.ebeaninternal.api.SpiExpressionRequest;
/*     */ import com.avaje.ebeaninternal.server.core.DefaultSqlUpdate;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanProperty;
/*     */ import com.avaje.ebeaninternal.server.deploy.DbReadContext;
/*     */ import com.avaje.ebeaninternal.server.deploy.DbSqlContext;
/*     */ import com.avaje.ebeaninternal.server.type.DataBind;
/*     */ import java.io.DataInput;
/*     */ import java.io.DataOutput;
/*     */ import java.io.IOException;
/*     */ import java.sql.SQLException;
/*     */ import java.util.List;
/*     */ import javax.naming.InvalidNameException;
/*     */ import javax.naming.ldap.LdapName;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class IdBinderEmpty
/*     */   implements IdBinder
/*     */ {
/*     */   private static final String bindIdSql = "";
/*  26 */   private static final BeanProperty[] properties = new BeanProperty[0];
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initialise() {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  36 */   public Object readTerm(String idTermValue) { return null; }
/*     */ 
/*     */ 
/*     */   
/*  40 */   public String writeTerm(Object idValue) { return null; }
/*     */ 
/*     */ 
/*     */   
/*  44 */   public String getOrderBy(String pathPrefix, boolean ascending) { return pathPrefix; }
/*     */ 
/*     */ 
/*     */   
/*     */   public void buildSelectExpressionChain(String prefix, List<String> selectChain) {}
/*     */ 
/*     */   
/*     */   public void createLdapNameById(LdapName name, Object id) throws InvalidNameException {}
/*     */ 
/*     */   
/*     */   public void createLdapNameByBean(LdapName name, Object bean) throws InvalidNameException {}
/*     */ 
/*     */   
/*  57 */   public int getPropertyCount() { return 0; }
/*     */ 
/*     */ 
/*     */   
/*  61 */   public String getIdProperty() { return null; }
/*     */ 
/*     */ 
/*     */   
/*  65 */   public BeanProperty findBeanProperty(String dbColumnName) { return null; }
/*     */ 
/*     */   
/*  68 */   public boolean isComplexId() { return true; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  74 */   public String getDefaultOrderBy() { return ""; }
/*     */ 
/*     */ 
/*     */   
/*  78 */   public BeanProperty[] getProperties() { return properties; }
/*     */ 
/*     */ 
/*     */   
/*  82 */   public String getBindIdSql(String baseTableAlias) { return ""; }
/*     */ 
/*     */ 
/*     */   
/*  86 */   public String getAssocOneIdExpr(String prefix, String operator) { return null; }
/*     */ 
/*     */ 
/*     */   
/*  90 */   public String getAssocIdInExpr(String prefix) { return null; }
/*     */ 
/*     */ 
/*     */   
/*     */   public void addIdInBindValue(SpiExpressionRequest request, Object value) {}
/*     */ 
/*     */ 
/*     */   
/*  98 */   public String getIdInValueExprDelete(int size) { return getIdInValueExpr(size); }
/*     */ 
/*     */ 
/*     */   
/* 102 */   public String getIdInValueExpr(int size) { return ""; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 107 */   public String getBindIdInSql(String baseTableAlias) { return null; }
/*     */ 
/*     */ 
/*     */   
/* 111 */   public Object[] getIdValues(Object bean) { return null; }
/*     */ 
/*     */ 
/*     */   
/* 115 */   public Object[] getBindValues(Object idValue) { return new Object[] { idValue }; }
/*     */ 
/*     */ 
/*     */   
/*     */   public void bindId(DefaultSqlUpdate sqlUpdate, Object value) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void bindId(DataBind dataBind, Object value) throws SQLException {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void loadIgnore(DbReadContext ctx) {}
/*     */ 
/*     */   
/* 130 */   public Object readSet(DbReadContext ctx, Object bean) throws SQLException { return null; }
/*     */ 
/*     */ 
/*     */   
/* 134 */   public Object read(DbReadContext ctx) throws SQLException { return null; }
/*     */ 
/*     */ 
/*     */   
/*     */   public void appendSelect(DbSqlContext ctx, boolean subQuery) {}
/*     */ 
/*     */   
/* 141 */   public Object convertSetId(Object idValue, Object bean) { return idValue; }
/*     */ 
/*     */ 
/*     */   
/* 145 */   public Object readData(DataInput dataOutput) throws IOException { return null; }
/*     */   
/*     */   public void writeData(DataOutput dataOutput, Object idValue) throws IOException {}
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\deploy\id\IdBinderEmpty.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */