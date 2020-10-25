/*     */ package com.mysql.jdbc.jdbc2.optional;
/*     */ 
/*     */ import com.mysql.jdbc.SQLError;
/*     */ import java.io.InputStream;
/*     */ import java.io.Reader;
/*     */ import java.lang.reflect.Proxy;
/*     */ import java.sql.NClob;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.RowId;
/*     */ import java.sql.SQLException;
/*     */ import java.sql.SQLXML;
/*     */ import java.util.HashMap;
/*     */ import javax.sql.StatementEvent;
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
/*     */ public class JDBC4PreparedStatementWrapper
/*     */   extends PreparedStatementWrapper
/*     */ {
/*  63 */   public JDBC4PreparedStatementWrapper(ConnectionWrapper c, MysqlPooledConnection conn, PreparedStatement toWrap) { super(c, conn, toWrap); }
/*     */ 
/*     */   
/*     */   public void close() throws SQLException {
/*  67 */     if (this.pooledConnection == null) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/*  72 */     con = this.pooledConnection;
/*     */ 
/*     */     
/*     */     try {
/*  76 */       super.close();
/*     */     } finally {
/*     */       try {
/*  79 */         StatementEvent e = new StatementEvent(con, this);
/*     */         
/*  81 */         if (con instanceof JDBC4MysqlPooledConnection) {
/*  82 */           ((JDBC4MysqlPooledConnection)con).fireStatementEvent(e);
/*  83 */         } else if (con instanceof JDBC4MysqlXAConnection) {
/*  84 */           ((JDBC4MysqlXAConnection)con).fireStatementEvent(e);
/*  85 */         } else if (con instanceof JDBC4SuspendableXAConnection) {
/*  86 */           ((JDBC4SuspendableXAConnection)con).fireStatementEvent(e);
/*     */         } 
/*     */       } finally {
/*  89 */         this.unwrappedInterfaces = null;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean isClosed() throws SQLException {
/*     */     try {
/*  96 */       if (this.wrappedStmt != null) {
/*  97 */         return this.wrappedStmt.isClosed();
/*     */       }
/*  99 */       throw SQLError.createSQLException("Statement already closed", "S1009", this.exceptionInterceptor);
/*     */     
/*     */     }
/* 102 */     catch (SQLException sqlEx) {
/* 103 */       checkAndFireConnectionError(sqlEx);
/*     */ 
/*     */       
/* 106 */       return false;
/*     */     } 
/*     */   }
/*     */   public void setPoolable(boolean poolable) throws SQLException {
/*     */     try {
/* 111 */       if (this.wrappedStmt != null) {
/* 112 */         this.wrappedStmt.setPoolable(poolable);
/*     */       } else {
/* 114 */         throw SQLError.createSQLException("Statement already closed", "S1009", this.exceptionInterceptor);
/*     */       }
/*     */     
/* 117 */     } catch (SQLException sqlEx) {
/* 118 */       checkAndFireConnectionError(sqlEx);
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean isPoolable() throws SQLException {
/*     */     try {
/* 124 */       if (this.wrappedStmt != null) {
/* 125 */         return this.wrappedStmt.isPoolable();
/*     */       }
/* 127 */       throw SQLError.createSQLException("Statement already closed", "S1009", this.exceptionInterceptor);
/*     */     
/*     */     }
/* 130 */     catch (SQLException sqlEx) {
/* 131 */       checkAndFireConnectionError(sqlEx);
/*     */ 
/*     */       
/* 134 */       return false;
/*     */     } 
/*     */   }
/*     */   public void setRowId(int parameterIndex, RowId x) throws SQLException {
/*     */     try {
/* 139 */       if (this.wrappedStmt != null) {
/* 140 */         ((PreparedStatement)this.wrappedStmt).setRowId(parameterIndex, x);
/*     */       } else {
/*     */         
/* 143 */         throw SQLError.createSQLException("No operations allowed after statement closed", "S1000", this.exceptionInterceptor);
/*     */       }
/*     */     
/*     */     }
/* 147 */     catch (SQLException sqlEx) {
/* 148 */       checkAndFireConnectionError(sqlEx);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setNClob(int parameterIndex, NClob value) throws SQLException {
/*     */     try {
/* 154 */       if (this.wrappedStmt != null) {
/* 155 */         ((PreparedStatement)this.wrappedStmt).setNClob(parameterIndex, value);
/*     */       } else {
/*     */         
/* 158 */         throw SQLError.createSQLException("No operations allowed after statement closed", "S1000", this.exceptionInterceptor);
/*     */       }
/*     */     
/*     */     }
/* 162 */     catch (SQLException sqlEx) {
/* 163 */       checkAndFireConnectionError(sqlEx);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void setSQLXML(int parameterIndex, SQLXML xmlObject) throws SQLException {
/*     */     try {
/* 170 */       if (this.wrappedStmt != null) {
/* 171 */         ((PreparedStatement)this.wrappedStmt).setSQLXML(parameterIndex, xmlObject);
/*     */       } else {
/*     */         
/* 174 */         throw SQLError.createSQLException("No operations allowed after statement closed", "S1000", this.exceptionInterceptor);
/*     */       }
/*     */     
/*     */     }
/* 178 */     catch (SQLException sqlEx) {
/* 179 */       checkAndFireConnectionError(sqlEx);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setNString(int parameterIndex, String value) throws SQLException {
/*     */     try {
/* 188 */       if (this.wrappedStmt != null) {
/* 189 */         ((PreparedStatement)this.wrappedStmt).setNString(parameterIndex, value);
/*     */       } else {
/*     */         
/* 192 */         throw SQLError.createSQLException("No operations allowed after statement closed", "S1000", this.exceptionInterceptor);
/*     */       }
/*     */     
/*     */     }
/* 196 */     catch (SQLException sqlEx) {
/* 197 */       checkAndFireConnectionError(sqlEx);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setNCharacterStream(int parameterIndex, Reader value, long length) throws SQLException {
/*     */     try {
/* 206 */       if (this.wrappedStmt != null) {
/* 207 */         ((PreparedStatement)this.wrappedStmt).setNCharacterStream(parameterIndex, value, length);
/*     */       } else {
/*     */         
/* 210 */         throw SQLError.createSQLException("No operations allowed after statement closed", "S1000", this.exceptionInterceptor);
/*     */       }
/*     */     
/*     */     }
/* 214 */     catch (SQLException sqlEx) {
/* 215 */       checkAndFireConnectionError(sqlEx);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setClob(int parameterIndex, Reader reader, long length) throws SQLException {
/*     */     try {
/* 224 */       if (this.wrappedStmt != null) {
/* 225 */         ((PreparedStatement)this.wrappedStmt).setClob(parameterIndex, reader, length);
/*     */       } else {
/*     */         
/* 228 */         throw SQLError.createSQLException("No operations allowed after statement closed", "S1000", this.exceptionInterceptor);
/*     */       }
/*     */     
/*     */     }
/* 232 */     catch (SQLException sqlEx) {
/* 233 */       checkAndFireConnectionError(sqlEx);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setBlob(int parameterIndex, InputStream inputStream, long length) throws SQLException {
/*     */     try {
/* 242 */       if (this.wrappedStmt != null) {
/* 243 */         ((PreparedStatement)this.wrappedStmt).setBlob(parameterIndex, inputStream, length);
/*     */       } else {
/*     */         
/* 246 */         throw SQLError.createSQLException("No operations allowed after statement closed", "S1000", this.exceptionInterceptor);
/*     */       }
/*     */     
/*     */     }
/* 250 */     catch (SQLException sqlEx) {
/* 251 */       checkAndFireConnectionError(sqlEx);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setNClob(int parameterIndex, Reader reader, long length) throws SQLException {
/*     */     try {
/* 260 */       if (this.wrappedStmt != null) {
/* 261 */         ((PreparedStatement)this.wrappedStmt).setNClob(parameterIndex, reader, length);
/*     */       } else {
/*     */         
/* 264 */         throw SQLError.createSQLException("No operations allowed after statement closed", "S1000", this.exceptionInterceptor);
/*     */       }
/*     */     
/*     */     }
/* 268 */     catch (SQLException sqlEx) {
/* 269 */       checkAndFireConnectionError(sqlEx);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setAsciiStream(int parameterIndex, InputStream x, long length) throws SQLException {
/*     */     try {
/* 278 */       if (this.wrappedStmt != null) {
/* 279 */         ((PreparedStatement)this.wrappedStmt).setAsciiStream(parameterIndex, x, length);
/*     */       } else {
/*     */         
/* 282 */         throw SQLError.createSQLException("No operations allowed after statement closed", "S1000", this.exceptionInterceptor);
/*     */       }
/*     */     
/*     */     }
/* 286 */     catch (SQLException sqlEx) {
/* 287 */       checkAndFireConnectionError(sqlEx);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setBinaryStream(int parameterIndex, InputStream x, long length) throws SQLException {
/*     */     try {
/* 296 */       if (this.wrappedStmt != null) {
/* 297 */         ((PreparedStatement)this.wrappedStmt).setBinaryStream(parameterIndex, x, length);
/*     */       } else {
/*     */         
/* 300 */         throw SQLError.createSQLException("No operations allowed after statement closed", "S1000", this.exceptionInterceptor);
/*     */       }
/*     */     
/*     */     }
/* 304 */     catch (SQLException sqlEx) {
/* 305 */       checkAndFireConnectionError(sqlEx);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setCharacterStream(int parameterIndex, Reader reader, long length) throws SQLException {
/*     */     try {
/* 314 */       if (this.wrappedStmt != null) {
/* 315 */         ((PreparedStatement)this.wrappedStmt).setCharacterStream(parameterIndex, reader, length);
/*     */       } else {
/*     */         
/* 318 */         throw SQLError.createSQLException("No operations allowed after statement closed", "S1000", this.exceptionInterceptor);
/*     */       }
/*     */     
/*     */     }
/* 322 */     catch (SQLException sqlEx) {
/* 323 */       checkAndFireConnectionError(sqlEx);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setAsciiStream(int parameterIndex, InputStream x) throws SQLException {
/*     */     try {
/* 331 */       if (this.wrappedStmt != null) {
/* 332 */         ((PreparedStatement)this.wrappedStmt).setAsciiStream(parameterIndex, x);
/*     */       } else {
/*     */         
/* 335 */         throw SQLError.createSQLException("No operations allowed after statement closed", "S1000", this.exceptionInterceptor);
/*     */       }
/*     */     
/*     */     }
/* 339 */     catch (SQLException sqlEx) {
/* 340 */       checkAndFireConnectionError(sqlEx);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setBinaryStream(int parameterIndex, InputStream x) throws SQLException {
/*     */     try {
/* 348 */       if (this.wrappedStmt != null) {
/* 349 */         ((PreparedStatement)this.wrappedStmt).setBinaryStream(parameterIndex, x);
/*     */       } else {
/*     */         
/* 352 */         throw SQLError.createSQLException("No operations allowed after statement closed", "S1000", this.exceptionInterceptor);
/*     */       }
/*     */     
/*     */     }
/* 356 */     catch (SQLException sqlEx) {
/* 357 */       checkAndFireConnectionError(sqlEx);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setCharacterStream(int parameterIndex, Reader reader) throws SQLException {
/*     */     try {
/* 365 */       if (this.wrappedStmt != null) {
/* 366 */         ((PreparedStatement)this.wrappedStmt).setCharacterStream(parameterIndex, reader);
/*     */       } else {
/*     */         
/* 369 */         throw SQLError.createSQLException("No operations allowed after statement closed", "S1000", this.exceptionInterceptor);
/*     */       }
/*     */     
/*     */     }
/* 373 */     catch (SQLException sqlEx) {
/* 374 */       checkAndFireConnectionError(sqlEx);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setNCharacterStream(int parameterIndex, Reader value) throws SQLException {
/*     */     try {
/* 383 */       if (this.wrappedStmt != null) {
/* 384 */         ((PreparedStatement)this.wrappedStmt).setNCharacterStream(parameterIndex, value);
/*     */       } else {
/*     */         
/* 387 */         throw SQLError.createSQLException("No operations allowed after statement closed", "S1000", this.exceptionInterceptor);
/*     */       }
/*     */     
/*     */     }
/* 391 */     catch (SQLException sqlEx) {
/* 392 */       checkAndFireConnectionError(sqlEx);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setClob(int parameterIndex, Reader reader) throws SQLException {
/*     */     try {
/* 401 */       if (this.wrappedStmt != null) {
/* 402 */         ((PreparedStatement)this.wrappedStmt).setClob(parameterIndex, reader);
/*     */       } else {
/*     */         
/* 405 */         throw SQLError.createSQLException("No operations allowed after statement closed", "S1000", this.exceptionInterceptor);
/*     */       }
/*     */     
/*     */     }
/* 409 */     catch (SQLException sqlEx) {
/* 410 */       checkAndFireConnectionError(sqlEx);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setBlob(int parameterIndex, InputStream inputStream) throws SQLException {
/*     */     try {
/* 419 */       if (this.wrappedStmt != null) {
/* 420 */         ((PreparedStatement)this.wrappedStmt).setBlob(parameterIndex, inputStream);
/*     */       } else {
/*     */         
/* 423 */         throw SQLError.createSQLException("No operations allowed after statement closed", "S1000", this.exceptionInterceptor);
/*     */       }
/*     */     
/*     */     }
/* 427 */     catch (SQLException sqlEx) {
/* 428 */       checkAndFireConnectionError(sqlEx);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setNClob(int parameterIndex, Reader reader) throws SQLException {
/*     */     try {
/* 436 */       if (this.wrappedStmt != null) {
/* 437 */         ((PreparedStatement)this.wrappedStmt).setNClob(parameterIndex, reader);
/*     */       } else {
/*     */         
/* 440 */         throw SQLError.createSQLException("No operations allowed after statement closed", "S1000", this.exceptionInterceptor);
/*     */       }
/*     */     
/*     */     }
/* 444 */     catch (SQLException sqlEx) {
/* 445 */       checkAndFireConnectionError(sqlEx);
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
/*     */   public boolean isWrapperFor(Class<?> iface) throws SQLException {
/* 473 */     boolean isInstance = iface.isInstance(this);
/*     */     
/* 475 */     if (isInstance) {
/* 476 */       return true;
/*     */     }
/*     */     
/* 479 */     String interfaceClassName = iface.getName();
/*     */     
/* 481 */     return (interfaceClassName.equals("com.mysql.jdbc.Statement") || interfaceClassName.equals("java.sql.Statement") || interfaceClassName.equals("java.sql.PreparedStatement") || interfaceClassName.equals("java.sql.Wrapper"));
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
/*     */   public <T> T unwrap(Class<T> iface) throws SQLException {
/*     */     try {
/* 509 */       if ("java.sql.Statement".equals(iface.getName()) || "java.sql.PreparedStatement".equals(iface.getName()) || "java.sql.Wrapper.class".equals(iface.getName()))
/*     */       {
/*     */         
/* 512 */         return (T)iface.cast(this);
/*     */       }
/*     */       
/* 515 */       if (this.unwrappedInterfaces == null) {
/* 516 */         this.unwrappedInterfaces = new HashMap();
/*     */       }
/*     */       
/* 519 */       Object cachedUnwrapped = this.unwrappedInterfaces.get(iface);
/*     */       
/* 521 */       if (cachedUnwrapped == null) {
/* 522 */         if (cachedUnwrapped == null) {
/* 523 */           cachedUnwrapped = Proxy.newProxyInstance(this.wrappedStmt.getClass().getClassLoader(), new Class[] { iface }, new WrapperBase.ConnectionErrorFiringInvocationHandler(this, this.wrappedStmt));
/*     */ 
/*     */ 
/*     */           
/* 527 */           this.unwrappedInterfaces.put(iface, cachedUnwrapped);
/*     */         } 
/* 529 */         this.unwrappedInterfaces.put(iface, cachedUnwrapped);
/*     */       } 
/*     */       
/* 532 */       return (T)iface.cast(cachedUnwrapped);
/* 533 */     } catch (ClassCastException cce) {
/* 534 */       throw SQLError.createSQLException("Unable to unwrap to " + iface.toString(), "S1009", this.exceptionInterceptor);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\mysql\jdbc\jdbc2\optional\JDBC4PreparedStatementWrapper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.4
 */