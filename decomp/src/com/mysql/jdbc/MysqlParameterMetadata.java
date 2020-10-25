/*     */ package com.mysql.jdbc;
/*     */ 
/*     */ import java.sql.ParameterMetaData;
/*     */ import java.sql.SQLException;
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
/*     */ public class MysqlParameterMetadata
/*     */   implements ParameterMetaData
/*     */ {
/*     */   boolean returnSimpleMetadata;
/*     */   ResultSetMetaData metadata;
/*     */   int parameterCount;
/*     */   private ExceptionInterceptor exceptionInterceptor;
/*     */   
/*     */   MysqlParameterMetadata(Field[] fieldInfo, int parameterCount, ExceptionInterceptor exceptionInterceptor) {
/*  32 */     this.returnSimpleMetadata = false;
/*     */     
/*  34 */     this.metadata = null;
/*     */     
/*  36 */     this.parameterCount = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  41 */     this.metadata = new ResultSetMetaData(fieldInfo, false, exceptionInterceptor);
/*     */     
/*  43 */     this.parameterCount = parameterCount;
/*  44 */     this.exceptionInterceptor = exceptionInterceptor;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   MysqlParameterMetadata(int count) {
/*     */     this.returnSimpleMetadata = false;
/*     */     this.metadata = null;
/*     */     this.parameterCount = 0;
/*  54 */     this.parameterCount = count;
/*  55 */     this.returnSimpleMetadata = true;
/*     */   }
/*     */ 
/*     */   
/*  59 */   public int getParameterCount() throws SQLException { return this.parameterCount; }
/*     */ 
/*     */   
/*     */   public int isNullable(int arg0) throws SQLException {
/*  63 */     checkAvailable();
/*     */     
/*  65 */     return this.metadata.isNullable(arg0);
/*     */   }
/*     */   
/*     */   private void checkAvailable() throws SQLException {
/*  69 */     if (this.metadata == null || this.metadata.fields == null) {
/*  70 */       throw SQLError.createSQLException("Parameter metadata not available for the given statement", "S1C00", this.exceptionInterceptor);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isSigned(int arg0) throws SQLException {
/*  77 */     if (this.returnSimpleMetadata) {
/*  78 */       checkBounds(arg0);
/*     */       
/*  80 */       return false;
/*     */     } 
/*     */     
/*  83 */     checkAvailable();
/*     */     
/*  85 */     return this.metadata.isSigned(arg0);
/*     */   }
/*     */   
/*     */   public int getPrecision(int arg0) throws SQLException {
/*  89 */     if (this.returnSimpleMetadata) {
/*  90 */       checkBounds(arg0);
/*     */       
/*  92 */       return 0;
/*     */     } 
/*     */     
/*  95 */     checkAvailable();
/*     */     
/*  97 */     return this.metadata.getPrecision(arg0);
/*     */   }
/*     */   
/*     */   public int getScale(int arg0) throws SQLException {
/* 101 */     if (this.returnSimpleMetadata) {
/* 102 */       checkBounds(arg0);
/*     */       
/* 104 */       return 0;
/*     */     } 
/*     */     
/* 107 */     checkAvailable();
/*     */     
/* 109 */     return this.metadata.getScale(arg0);
/*     */   }
/*     */   
/*     */   public int getParameterType(int arg0) throws SQLException {
/* 113 */     if (this.returnSimpleMetadata) {
/* 114 */       checkBounds(arg0);
/*     */       
/* 116 */       return 12;
/*     */     } 
/*     */     
/* 119 */     checkAvailable();
/*     */     
/* 121 */     return this.metadata.getColumnType(arg0);
/*     */   }
/*     */   
/*     */   public String getParameterTypeName(int arg0) throws SQLException {
/* 125 */     if (this.returnSimpleMetadata) {
/* 126 */       checkBounds(arg0);
/*     */       
/* 128 */       return "VARCHAR";
/*     */     } 
/*     */     
/* 131 */     checkAvailable();
/*     */     
/* 133 */     return this.metadata.getColumnTypeName(arg0);
/*     */   }
/*     */   
/*     */   public String getParameterClassName(int arg0) throws SQLException {
/* 137 */     if (this.returnSimpleMetadata) {
/* 138 */       checkBounds(arg0);
/*     */       
/* 140 */       return "java.lang.String";
/*     */     } 
/*     */     
/* 143 */     checkAvailable();
/*     */     
/* 145 */     return this.metadata.getColumnClassName(arg0);
/*     */   }
/*     */ 
/*     */   
/* 149 */   public int getParameterMode(int arg0) throws SQLException { return 1; }
/*     */ 
/*     */   
/*     */   private void checkBounds(int paramNumber) {
/* 153 */     if (paramNumber < 1) {
/* 154 */       throw SQLError.createSQLException("Parameter index of '" + paramNumber + "' is invalid.", "S1009", this.exceptionInterceptor);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 159 */     if (paramNumber > this.parameterCount) {
/* 160 */       throw SQLError.createSQLException("Parameter index of '" + paramNumber + "' is greater than number of parameters, which is '" + this.parameterCount + "'.", "S1009", this.exceptionInterceptor);
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
/* 188 */   public boolean isWrapperFor(Class iface) throws SQLException { return iface.isInstance(this); }
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
/*     */   public Object unwrap(Class iface) throws SQLException {
/*     */     try {
/* 209 */       return Util.cast(iface, this);
/* 210 */     } catch (ClassCastException cce) {
/* 211 */       throw SQLError.createSQLException("Unable to unwrap to " + iface.toString(), "S1009", this.exceptionInterceptor);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\mysql\jdbc\MysqlParameterMetadata.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */