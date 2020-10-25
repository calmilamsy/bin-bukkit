/*     */ package com.mysql.jdbc;
/*     */ 
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.RowIdLifetime;
/*     */ import java.sql.SQLException;
/*     */ import java.util.ArrayList;
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
/*     */ public class JDBC4DatabaseMetaData
/*     */   extends DatabaseMetaData
/*     */ {
/*  39 */   public JDBC4DatabaseMetaData(MySQLConnection connToSet, String databaseToSet) { super(connToSet, databaseToSet); }
/*     */ 
/*     */ 
/*     */   
/*  43 */   public RowIdLifetime getRowIdLifetime() throws SQLException { return RowIdLifetime.ROWID_UNSUPPORTED; }
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
/*  64 */   public boolean isWrapperFor(Class<?> iface) throws SQLException { return iface.isInstance(this); }
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
/*     */   public <T> T unwrap(Class<T> iface) throws SQLException {
/*     */     try {
/*  85 */       return (T)iface.cast(this);
/*  86 */     } catch (ClassCastException cce) {
/*  87 */       throw SQLError.createSQLException("Unable to unwrap to " + iface.toString(), "S1009", this.conn.getExceptionInterceptor());
/*     */     } 
/*     */   }
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
/*     */   public ResultSet getClientInfoProperties() throws SQLException {
/* 119 */     Field[] fields = new Field[4];
/* 120 */     fields[0] = new Field("", "NAME", 12, 'ÿ');
/* 121 */     fields[1] = new Field("", "MAX_LEN", 4, 10);
/* 122 */     fields[2] = new Field("", "DEFAULT_VALUE", 12, 'ÿ');
/* 123 */     fields[3] = new Field("", "DESCRIPTION", 12, 'ÿ');
/*     */     
/* 125 */     ArrayList tuples = new ArrayList();
/*     */     
/* 127 */     return buildResultSet(fields, tuples, this.conn);
/*     */   }
/*     */ 
/*     */   
/* 131 */   public boolean autoCommitFailureClosesAllResultSets() throws SQLException { return false; }
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
/*     */   public ResultSet getFunctions(String catalog, String schemaPattern, String functionNamePattern) throws SQLException {
/* 184 */     Field[] fields = new Field[6];
/*     */     
/* 186 */     fields[0] = new Field("", "FUNCTION_CAT", true, 'ÿ');
/* 187 */     fields[1] = new Field("", "FUNCTION_SCHEM", true, 'ÿ');
/* 188 */     fields[2] = new Field("", "FUNCTION_NAME", true, 'ÿ');
/* 189 */     fields[3] = new Field("", "REMARKS", true, 'ÿ');
/* 190 */     fields[4] = new Field("", "FUNCTION_TYPE", 5, 6);
/* 191 */     fields[5] = new Field("", "SPECIFIC_NAME", true, 'ÿ');
/*     */     
/* 193 */     return getProceduresAndOrFunctions(fields, catalog, schemaPattern, functionNamePattern, false, true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 203 */   protected int getJDBC4FunctionNoTableConstant() { return 1; }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\mysql\jdbc\JDBC4DatabaseMetaData.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.4
 */