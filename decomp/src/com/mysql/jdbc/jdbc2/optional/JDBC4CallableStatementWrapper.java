/*      */ package com.mysql.jdbc.jdbc2.optional;
/*      */ 
/*      */ import com.mysql.jdbc.SQLError;
/*      */ import java.io.InputStream;
/*      */ import java.io.Reader;
/*      */ import java.lang.reflect.Proxy;
/*      */ import java.sql.Blob;
/*      */ import java.sql.CallableStatement;
/*      */ import java.sql.Clob;
/*      */ import java.sql.NClob;
/*      */ import java.sql.PreparedStatement;
/*      */ import java.sql.RowId;
/*      */ import java.sql.SQLException;
/*      */ import java.sql.SQLXML;
/*      */ import java.util.HashMap;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class JDBC4CallableStatementWrapper
/*      */   extends CallableStatementWrapper
/*      */ {
/*   63 */   public JDBC4CallableStatementWrapper(ConnectionWrapper c, MysqlPooledConnection conn, CallableStatement toWrap) { super(c, conn, toWrap); }
/*      */ 
/*      */   
/*      */   public void close() throws SQLException {
/*      */     try {
/*   68 */       super.close();
/*      */     } finally {
/*   70 */       this.unwrappedInterfaces = null;
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean isClosed() throws SQLException {
/*      */     try {
/*   76 */       if (this.wrappedStmt != null) {
/*   77 */         return this.wrappedStmt.isClosed();
/*      */       }
/*   79 */       throw SQLError.createSQLException("Statement already closed", "S1009", this.exceptionInterceptor);
/*      */     
/*      */     }
/*   82 */     catch (SQLException sqlEx) {
/*   83 */       checkAndFireConnectionError(sqlEx);
/*      */ 
/*      */       
/*   86 */       return false;
/*      */     } 
/*      */   }
/*      */   public void setPoolable(boolean poolable) throws SQLException {
/*      */     try {
/*   91 */       if (this.wrappedStmt != null) {
/*   92 */         this.wrappedStmt.setPoolable(poolable);
/*      */       } else {
/*   94 */         throw SQLError.createSQLException("Statement already closed", "S1009", this.exceptionInterceptor);
/*      */       }
/*      */     
/*   97 */     } catch (SQLException sqlEx) {
/*   98 */       checkAndFireConnectionError(sqlEx);
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean isPoolable() throws SQLException {
/*      */     try {
/*  104 */       if (this.wrappedStmt != null) {
/*  105 */         return this.wrappedStmt.isPoolable();
/*      */       }
/*  107 */       throw SQLError.createSQLException("Statement already closed", "S1009", this.exceptionInterceptor);
/*      */     
/*      */     }
/*  110 */     catch (SQLException sqlEx) {
/*  111 */       checkAndFireConnectionError(sqlEx);
/*      */ 
/*      */       
/*  114 */       return false;
/*      */     } 
/*      */   }
/*      */   public void setRowId(int parameterIndex, RowId x) throws SQLException {
/*      */     try {
/*  119 */       if (this.wrappedStmt != null) {
/*  120 */         ((PreparedStatement)this.wrappedStmt).setRowId(parameterIndex, x);
/*      */       } else {
/*      */         
/*  123 */         throw SQLError.createSQLException("No operations allowed after statement closed", "S1000", this.exceptionInterceptor);
/*      */       }
/*      */     
/*      */     }
/*  127 */     catch (SQLException sqlEx) {
/*  128 */       checkAndFireConnectionError(sqlEx);
/*      */     } 
/*      */   }
/*      */   
/*      */   public void setNClob(int parameterIndex, NClob value) throws SQLException {
/*      */     try {
/*  134 */       if (this.wrappedStmt != null) {
/*  135 */         ((PreparedStatement)this.wrappedStmt).setNClob(parameterIndex, value);
/*      */       } else {
/*      */         
/*  138 */         throw SQLError.createSQLException("No operations allowed after statement closed", "S1000", this.exceptionInterceptor);
/*      */       }
/*      */     
/*      */     }
/*  142 */     catch (SQLException sqlEx) {
/*  143 */       checkAndFireConnectionError(sqlEx);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void setSQLXML(int parameterIndex, SQLXML xmlObject) throws SQLException {
/*      */     try {
/*  150 */       if (this.wrappedStmt != null) {
/*  151 */         ((PreparedStatement)this.wrappedStmt).setSQLXML(parameterIndex, xmlObject);
/*      */       } else {
/*      */         
/*  154 */         throw SQLError.createSQLException("No operations allowed after statement closed", "S1000", this.exceptionInterceptor);
/*      */       }
/*      */     
/*      */     }
/*  158 */     catch (SQLException sqlEx) {
/*  159 */       checkAndFireConnectionError(sqlEx);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setNString(int parameterIndex, String value) throws SQLException {
/*      */     try {
/*  168 */       if (this.wrappedStmt != null) {
/*  169 */         ((PreparedStatement)this.wrappedStmt).setNString(parameterIndex, value);
/*      */       } else {
/*      */         
/*  172 */         throw SQLError.createSQLException("No operations allowed after statement closed", "S1000", this.exceptionInterceptor);
/*      */       }
/*      */     
/*      */     }
/*  176 */     catch (SQLException sqlEx) {
/*  177 */       checkAndFireConnectionError(sqlEx);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setNCharacterStream(int parameterIndex, Reader value, long length) throws SQLException {
/*      */     try {
/*  186 */       if (this.wrappedStmt != null) {
/*  187 */         ((PreparedStatement)this.wrappedStmt).setNCharacterStream(parameterIndex, value, length);
/*      */       } else {
/*      */         
/*  190 */         throw SQLError.createSQLException("No operations allowed after statement closed", "S1000", this.exceptionInterceptor);
/*      */       }
/*      */     
/*      */     }
/*  194 */     catch (SQLException sqlEx) {
/*  195 */       checkAndFireConnectionError(sqlEx);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setClob(int parameterIndex, Reader reader, long length) throws SQLException {
/*      */     try {
/*  204 */       if (this.wrappedStmt != null) {
/*  205 */         ((PreparedStatement)this.wrappedStmt).setClob(parameterIndex, reader, length);
/*      */       } else {
/*      */         
/*  208 */         throw SQLError.createSQLException("No operations allowed after statement closed", "S1000", this.exceptionInterceptor);
/*      */       }
/*      */     
/*      */     }
/*  212 */     catch (SQLException sqlEx) {
/*  213 */       checkAndFireConnectionError(sqlEx);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setBlob(int parameterIndex, InputStream inputStream, long length) throws SQLException {
/*      */     try {
/*  222 */       if (this.wrappedStmt != null) {
/*  223 */         ((PreparedStatement)this.wrappedStmt).setBlob(parameterIndex, inputStream, length);
/*      */       } else {
/*      */         
/*  226 */         throw SQLError.createSQLException("No operations allowed after statement closed", "S1000", this.exceptionInterceptor);
/*      */       }
/*      */     
/*      */     }
/*  230 */     catch (SQLException sqlEx) {
/*  231 */       checkAndFireConnectionError(sqlEx);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setNClob(int parameterIndex, Reader reader, long length) throws SQLException {
/*      */     try {
/*  240 */       if (this.wrappedStmt != null) {
/*  241 */         ((PreparedStatement)this.wrappedStmt).setNClob(parameterIndex, reader, length);
/*      */       } else {
/*      */         
/*  244 */         throw SQLError.createSQLException("No operations allowed after statement closed", "S1000", this.exceptionInterceptor);
/*      */       }
/*      */     
/*      */     }
/*  248 */     catch (SQLException sqlEx) {
/*  249 */       checkAndFireConnectionError(sqlEx);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setAsciiStream(int parameterIndex, InputStream x, long length) throws SQLException {
/*      */     try {
/*  258 */       if (this.wrappedStmt != null) {
/*  259 */         ((PreparedStatement)this.wrappedStmt).setAsciiStream(parameterIndex, x, length);
/*      */       } else {
/*      */         
/*  262 */         throw SQLError.createSQLException("No operations allowed after statement closed", "S1000", this.exceptionInterceptor);
/*      */       }
/*      */     
/*      */     }
/*  266 */     catch (SQLException sqlEx) {
/*  267 */       checkAndFireConnectionError(sqlEx);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setBinaryStream(int parameterIndex, InputStream x, long length) throws SQLException {
/*      */     try {
/*  276 */       if (this.wrappedStmt != null) {
/*  277 */         ((PreparedStatement)this.wrappedStmt).setBinaryStream(parameterIndex, x, length);
/*      */       } else {
/*      */         
/*  280 */         throw SQLError.createSQLException("No operations allowed after statement closed", "S1000", this.exceptionInterceptor);
/*      */       }
/*      */     
/*      */     }
/*  284 */     catch (SQLException sqlEx) {
/*  285 */       checkAndFireConnectionError(sqlEx);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setCharacterStream(int parameterIndex, Reader reader, long length) throws SQLException {
/*      */     try {
/*  294 */       if (this.wrappedStmt != null) {
/*  295 */         ((PreparedStatement)this.wrappedStmt).setCharacterStream(parameterIndex, reader, length);
/*      */       } else {
/*      */         
/*  298 */         throw SQLError.createSQLException("No operations allowed after statement closed", "S1000", this.exceptionInterceptor);
/*      */       }
/*      */     
/*      */     }
/*  302 */     catch (SQLException sqlEx) {
/*  303 */       checkAndFireConnectionError(sqlEx);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void setAsciiStream(int parameterIndex, InputStream x) throws SQLException {
/*      */     try {
/*  311 */       if (this.wrappedStmt != null) {
/*  312 */         ((PreparedStatement)this.wrappedStmt).setAsciiStream(parameterIndex, x);
/*      */       } else {
/*      */         
/*  315 */         throw SQLError.createSQLException("No operations allowed after statement closed", "S1000", this.exceptionInterceptor);
/*      */       }
/*      */     
/*      */     }
/*  319 */     catch (SQLException sqlEx) {
/*  320 */       checkAndFireConnectionError(sqlEx);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void setBinaryStream(int parameterIndex, InputStream x) throws SQLException {
/*      */     try {
/*  328 */       if (this.wrappedStmt != null) {
/*  329 */         ((PreparedStatement)this.wrappedStmt).setBinaryStream(parameterIndex, x);
/*      */       } else {
/*      */         
/*  332 */         throw SQLError.createSQLException("No operations allowed after statement closed", "S1000", this.exceptionInterceptor);
/*      */       }
/*      */     
/*      */     }
/*  336 */     catch (SQLException sqlEx) {
/*  337 */       checkAndFireConnectionError(sqlEx);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void setCharacterStream(int parameterIndex, Reader reader) throws SQLException {
/*      */     try {
/*  345 */       if (this.wrappedStmt != null) {
/*  346 */         ((PreparedStatement)this.wrappedStmt).setCharacterStream(parameterIndex, reader);
/*      */       } else {
/*      */         
/*  349 */         throw SQLError.createSQLException("No operations allowed after statement closed", "S1000", this.exceptionInterceptor);
/*      */       }
/*      */     
/*      */     }
/*  353 */     catch (SQLException sqlEx) {
/*  354 */       checkAndFireConnectionError(sqlEx);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setNCharacterStream(int parameterIndex, Reader value) throws SQLException {
/*      */     try {
/*  363 */       if (this.wrappedStmt != null) {
/*  364 */         ((PreparedStatement)this.wrappedStmt).setNCharacterStream(parameterIndex, value);
/*      */       } else {
/*      */         
/*  367 */         throw SQLError.createSQLException("No operations allowed after statement closed", "S1000", this.exceptionInterceptor);
/*      */       }
/*      */     
/*      */     }
/*  371 */     catch (SQLException sqlEx) {
/*  372 */       checkAndFireConnectionError(sqlEx);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setClob(int parameterIndex, Reader reader) throws SQLException {
/*      */     try {
/*  381 */       if (this.wrappedStmt != null) {
/*  382 */         ((PreparedStatement)this.wrappedStmt).setClob(parameterIndex, reader);
/*      */       } else {
/*      */         
/*  385 */         throw SQLError.createSQLException("No operations allowed after statement closed", "S1000", this.exceptionInterceptor);
/*      */       }
/*      */     
/*      */     }
/*  389 */     catch (SQLException sqlEx) {
/*  390 */       checkAndFireConnectionError(sqlEx);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setBlob(int parameterIndex, InputStream inputStream) throws SQLException {
/*      */     try {
/*  399 */       if (this.wrappedStmt != null) {
/*  400 */         ((PreparedStatement)this.wrappedStmt).setBlob(parameterIndex, inputStream);
/*      */       } else {
/*      */         
/*  403 */         throw SQLError.createSQLException("No operations allowed after statement closed", "S1000", this.exceptionInterceptor);
/*      */       }
/*      */     
/*      */     }
/*  407 */     catch (SQLException sqlEx) {
/*  408 */       checkAndFireConnectionError(sqlEx);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void setNClob(int parameterIndex, Reader reader) throws SQLException {
/*      */     try {
/*  416 */       if (this.wrappedStmt != null) {
/*  417 */         ((PreparedStatement)this.wrappedStmt).setNClob(parameterIndex, reader);
/*      */       } else {
/*      */         
/*  420 */         throw SQLError.createSQLException("No operations allowed after statement closed", "S1000", this.exceptionInterceptor);
/*      */       }
/*      */     
/*      */     }
/*  424 */     catch (SQLException sqlEx) {
/*  425 */       checkAndFireConnectionError(sqlEx);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isWrapperFor(Class<?> iface) throws SQLException {
/*  453 */     boolean isInstance = iface.isInstance(this);
/*      */     
/*  455 */     if (isInstance) {
/*  456 */       return true;
/*      */     }
/*      */     
/*  459 */     String interfaceClassName = iface.getName();
/*      */     
/*  461 */     return (interfaceClassName.equals("com.mysql.jdbc.Statement") || interfaceClassName.equals("java.sql.Statement") || interfaceClassName.equals("java.sql.PreparedStatement") || interfaceClassName.equals("java.sql.Wrapper"));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public <T> T unwrap(Class<T> iface) throws SQLException {
/*      */     try {
/*  489 */       if ("java.sql.Statement".equals(iface.getName()) || "java.sql.PreparedStatement".equals(iface.getName()) || "java.sql.Wrapper.class".equals(iface.getName()))
/*      */       {
/*      */         
/*  492 */         return (T)iface.cast(this);
/*      */       }
/*      */       
/*  495 */       if (this.unwrappedInterfaces == null) {
/*  496 */         this.unwrappedInterfaces = new HashMap();
/*      */       }
/*      */       
/*  499 */       Object cachedUnwrapped = this.unwrappedInterfaces.get(iface);
/*      */       
/*  501 */       if (cachedUnwrapped == null) {
/*  502 */         if (cachedUnwrapped == null) {
/*  503 */           cachedUnwrapped = Proxy.newProxyInstance(this.wrappedStmt.getClass().getClassLoader(), new Class[] { iface }, new WrapperBase.ConnectionErrorFiringInvocationHandler(this, this.wrappedStmt));
/*      */ 
/*      */ 
/*      */           
/*  507 */           this.unwrappedInterfaces.put(iface, cachedUnwrapped);
/*      */         } 
/*  509 */         this.unwrappedInterfaces.put(iface, cachedUnwrapped);
/*      */       } 
/*      */       
/*  512 */       return (T)iface.cast(cachedUnwrapped);
/*  513 */     } catch (ClassCastException cce) {
/*  514 */       throw SQLError.createSQLException("Unable to unwrap to " + iface.toString(), "S1009", this.exceptionInterceptor);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void setRowId(String parameterName, RowId x) throws SQLException {
/*      */     try {
/*  521 */       if (this.wrappedStmt != null) {
/*  522 */         ((CallableStatement)this.wrappedStmt).setRowId(parameterName, x);
/*      */       } else {
/*  524 */         throw SQLError.createSQLException("No operations allowed after statement closed", "S1000", this.exceptionInterceptor);
/*      */       }
/*      */     
/*      */     }
/*  528 */     catch (SQLException sqlEx) {
/*  529 */       checkAndFireConnectionError(sqlEx);
/*      */     } 
/*      */   }
/*      */   
/*      */   public void setSQLXML(String parameterName, SQLXML xmlObject) throws SQLException {
/*      */     try {
/*  535 */       if (this.wrappedStmt != null) {
/*  536 */         ((CallableStatement)this.wrappedStmt).setSQLXML(parameterName, xmlObject);
/*      */       } else {
/*  538 */         throw SQLError.createSQLException("No operations allowed after statement closed", "S1000", this.exceptionInterceptor);
/*      */       }
/*      */     
/*      */     }
/*  542 */     catch (SQLException sqlEx) {
/*  543 */       checkAndFireConnectionError(sqlEx);
/*      */     } 
/*      */   }
/*      */   
/*      */   public SQLXML getSQLXML(int parameterIndex) throws SQLException {
/*      */     try {
/*  549 */       if (this.wrappedStmt != null) {
/*  550 */         return ((CallableStatement)this.wrappedStmt).getSQLXML(parameterIndex);
/*      */       }
/*  552 */       throw SQLError.createSQLException("No operations allowed after statement closed", "S1000", this.exceptionInterceptor);
/*      */ 
/*      */     
/*      */     }
/*  556 */     catch (SQLException sqlEx) {
/*  557 */       checkAndFireConnectionError(sqlEx);
/*      */ 
/*      */       
/*  560 */       return null;
/*      */     } 
/*      */   }
/*      */   
/*      */   public SQLXML getSQLXML(String parameterName) throws SQLException {
/*      */     try {
/*  566 */       if (this.wrappedStmt != null) {
/*  567 */         return ((CallableStatement)this.wrappedStmt).getSQLXML(parameterName);
/*      */       }
/*  569 */       throw SQLError.createSQLException("No operations allowed after statement closed", "S1000", this.exceptionInterceptor);
/*      */ 
/*      */     
/*      */     }
/*  573 */     catch (SQLException sqlEx) {
/*  574 */       checkAndFireConnectionError(sqlEx);
/*      */ 
/*      */       
/*  577 */       return null;
/*      */     } 
/*      */   }
/*      */   public RowId getRowId(String parameterName) throws SQLException {
/*      */     try {
/*  582 */       if (this.wrappedStmt != null) {
/*  583 */         return ((CallableStatement)this.wrappedStmt).getRowId(parameterName);
/*      */       }
/*  585 */       throw SQLError.createSQLException("No operations allowed after statement closed", "S1000", this.exceptionInterceptor);
/*      */ 
/*      */     
/*      */     }
/*  589 */     catch (SQLException sqlEx) {
/*  590 */       checkAndFireConnectionError(sqlEx);
/*      */ 
/*      */       
/*  593 */       return null;
/*      */     } 
/*      */   }
/*      */   public void setNClob(String parameterName, NClob value) throws SQLException {
/*      */     try {
/*  598 */       if (this.wrappedStmt != null) {
/*  599 */         ((CallableStatement)this.wrappedStmt).setNClob(parameterName, value);
/*      */       } else {
/*  601 */         throw SQLError.createSQLException("No operations allowed after statement closed", "S1000", this.exceptionInterceptor);
/*      */       }
/*      */     
/*      */     }
/*  605 */     catch (SQLException sqlEx) {
/*  606 */       checkAndFireConnectionError(sqlEx);
/*      */     } 
/*      */   }
/*      */   
/*      */   public void setNClob(String parameterName, Reader reader) throws SQLException {
/*      */     try {
/*  612 */       if (this.wrappedStmt != null) {
/*  613 */         ((CallableStatement)this.wrappedStmt).setNClob(parameterName, reader);
/*      */       } else {
/*  615 */         throw SQLError.createSQLException("No operations allowed after statement closed", "S1000", this.exceptionInterceptor);
/*      */       }
/*      */     
/*      */     }
/*  619 */     catch (SQLException sqlEx) {
/*  620 */       checkAndFireConnectionError(sqlEx);
/*      */     } 
/*      */   }
/*      */   
/*      */   public void setNClob(String parameterName, Reader reader, long length) throws SQLException {
/*      */     try {
/*  626 */       if (this.wrappedStmt != null) {
/*  627 */         ((CallableStatement)this.wrappedStmt).setNClob(parameterName, reader, length);
/*      */       } else {
/*  629 */         throw SQLError.createSQLException("No operations allowed after statement closed", "S1000", this.exceptionInterceptor);
/*      */       }
/*      */     
/*      */     }
/*  633 */     catch (SQLException sqlEx) {
/*  634 */       checkAndFireConnectionError(sqlEx);
/*      */     } 
/*      */   }
/*      */   
/*      */   public void setNString(String parameterName, String value) throws SQLException {
/*      */     try {
/*  640 */       if (this.wrappedStmt != null) {
/*  641 */         ((CallableStatement)this.wrappedStmt).setNString(parameterName, value);
/*      */       } else {
/*  643 */         throw SQLError.createSQLException("No operations allowed after statement closed", "S1000", this.exceptionInterceptor);
/*      */       }
/*      */     
/*      */     }
/*  647 */     catch (SQLException sqlEx) {
/*  648 */       checkAndFireConnectionError(sqlEx);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Reader getCharacterStream(int parameterIndex) throws SQLException {
/*      */     try {
/*  657 */       if (this.wrappedStmt != null) {
/*  658 */         return ((CallableStatement)this.wrappedStmt).getCharacterStream(parameterIndex);
/*      */       }
/*  660 */       throw SQLError.createSQLException("No operations allowed after statement closed", "S1000", this.exceptionInterceptor);
/*      */ 
/*      */     
/*      */     }
/*  664 */     catch (SQLException sqlEx) {
/*  665 */       checkAndFireConnectionError(sqlEx);
/*      */ 
/*      */       
/*  668 */       return null;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Reader getCharacterStream(String parameterName) throws SQLException {
/*      */     try {
/*  676 */       if (this.wrappedStmt != null) {
/*  677 */         return ((CallableStatement)this.wrappedStmt).getCharacterStream(parameterName);
/*      */       }
/*  679 */       throw SQLError.createSQLException("No operations allowed after statement closed", "S1000", this.exceptionInterceptor);
/*      */ 
/*      */     
/*      */     }
/*  683 */     catch (SQLException sqlEx) {
/*  684 */       checkAndFireConnectionError(sqlEx);
/*      */ 
/*      */       
/*  687 */       return null;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Reader getNCharacterStream(int parameterIndex) throws SQLException {
/*      */     try {
/*  695 */       if (this.wrappedStmt != null) {
/*  696 */         return ((CallableStatement)this.wrappedStmt).getNCharacterStream(parameterIndex);
/*      */       }
/*  698 */       throw SQLError.createSQLException("No operations allowed after statement closed", "S1000", this.exceptionInterceptor);
/*      */ 
/*      */     
/*      */     }
/*  702 */     catch (SQLException sqlEx) {
/*  703 */       checkAndFireConnectionError(sqlEx);
/*      */ 
/*      */       
/*  706 */       return null;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Reader getNCharacterStream(String parameterName) throws SQLException {
/*      */     try {
/*  714 */       if (this.wrappedStmt != null) {
/*  715 */         return ((CallableStatement)this.wrappedStmt).getNCharacterStream(parameterName);
/*      */       }
/*  717 */       throw SQLError.createSQLException("No operations allowed after statement closed", "S1000", this.exceptionInterceptor);
/*      */ 
/*      */     
/*      */     }
/*  721 */     catch (SQLException sqlEx) {
/*  722 */       checkAndFireConnectionError(sqlEx);
/*      */ 
/*      */       
/*  725 */       return null;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public NClob getNClob(String parameterName) throws SQLException {
/*      */     try {
/*  733 */       if (this.wrappedStmt != null) {
/*  734 */         return ((CallableStatement)this.wrappedStmt).getNClob(parameterName);
/*      */       }
/*  736 */       throw SQLError.createSQLException("No operations allowed after statement closed", "S1000", this.exceptionInterceptor);
/*      */ 
/*      */     
/*      */     }
/*  740 */     catch (SQLException sqlEx) {
/*  741 */       checkAndFireConnectionError(sqlEx);
/*      */ 
/*      */       
/*  744 */       return null;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public String getNString(String parameterName) throws SQLException {
/*      */     try {
/*  752 */       if (this.wrappedStmt != null) {
/*  753 */         return ((CallableStatement)this.wrappedStmt).getNString(parameterName);
/*      */       }
/*  755 */       throw SQLError.createSQLException("No operations allowed after statement closed", "S1000", this.exceptionInterceptor);
/*      */ 
/*      */     
/*      */     }
/*  759 */     catch (SQLException sqlEx) {
/*  760 */       checkAndFireConnectionError(sqlEx);
/*      */ 
/*      */       
/*  763 */       return null;
/*      */     } 
/*      */   }
/*      */   public void setAsciiStream(String parameterName, InputStream x) throws SQLException {
/*      */     try {
/*  768 */       if (this.wrappedStmt != null) {
/*  769 */         ((CallableStatement)this.wrappedStmt).setAsciiStream(parameterName, x);
/*      */       } else {
/*  771 */         throw SQLError.createSQLException("No operations allowed after statement closed", "S1000", this.exceptionInterceptor);
/*      */       }
/*      */     
/*      */     }
/*  775 */     catch (SQLException sqlEx) {
/*  776 */       checkAndFireConnectionError(sqlEx);
/*      */     } 
/*      */   }
/*      */   
/*      */   public void setAsciiStream(String parameterName, InputStream x, long length) throws SQLException {
/*      */     try {
/*  782 */       if (this.wrappedStmt != null) {
/*  783 */         ((CallableStatement)this.wrappedStmt).setAsciiStream(parameterName, x, length);
/*      */       } else {
/*  785 */         throw SQLError.createSQLException("No operations allowed after statement closed", "S1000", this.exceptionInterceptor);
/*      */       }
/*      */     
/*      */     }
/*  789 */     catch (SQLException sqlEx) {
/*  790 */       checkAndFireConnectionError(sqlEx);
/*      */     } 
/*      */   }
/*      */   
/*      */   public void setBinaryStream(String parameterName, InputStream x) throws SQLException {
/*      */     try {
/*  796 */       if (this.wrappedStmt != null) {
/*  797 */         ((CallableStatement)this.wrappedStmt).setBinaryStream(parameterName, x);
/*      */       } else {
/*  799 */         throw SQLError.createSQLException("No operations allowed after statement closed", "S1000", this.exceptionInterceptor);
/*      */       }
/*      */     
/*      */     }
/*  803 */     catch (SQLException sqlEx) {
/*  804 */       checkAndFireConnectionError(sqlEx);
/*      */     } 
/*      */   }
/*      */   
/*      */   public void setBinaryStream(String parameterName, InputStream x, long length) throws SQLException {
/*      */     try {
/*  810 */       if (this.wrappedStmt != null) {
/*  811 */         ((CallableStatement)this.wrappedStmt).setBinaryStream(parameterName, x, length);
/*      */       } else {
/*  813 */         throw SQLError.createSQLException("No operations allowed after statement closed", "S1000", this.exceptionInterceptor);
/*      */       }
/*      */     
/*      */     }
/*  817 */     catch (SQLException sqlEx) {
/*  818 */       checkAndFireConnectionError(sqlEx);
/*      */     } 
/*      */   }
/*      */   
/*      */   public void setBlob(String parameterName, InputStream x) throws SQLException {
/*      */     try {
/*  824 */       if (this.wrappedStmt != null) {
/*  825 */         ((CallableStatement)this.wrappedStmt).setBlob(parameterName, x);
/*      */       } else {
/*  827 */         throw SQLError.createSQLException("No operations allowed after statement closed", "S1000", this.exceptionInterceptor);
/*      */       }
/*      */     
/*      */     }
/*  831 */     catch (SQLException sqlEx) {
/*  832 */       checkAndFireConnectionError(sqlEx);
/*      */     } 
/*      */   }
/*      */   
/*      */   public void setBlob(String parameterName, InputStream x, long length) throws SQLException {
/*      */     try {
/*  838 */       if (this.wrappedStmt != null) {
/*  839 */         ((CallableStatement)this.wrappedStmt).setBlob(parameterName, x, length);
/*      */       } else {
/*  841 */         throw SQLError.createSQLException("No operations allowed after statement closed", "S1000", this.exceptionInterceptor);
/*      */       }
/*      */     
/*      */     }
/*  845 */     catch (SQLException sqlEx) {
/*  846 */       checkAndFireConnectionError(sqlEx);
/*      */     } 
/*      */   }
/*      */   
/*      */   public void setBlob(String parameterName, Blob x) throws SQLException {
/*      */     try {
/*  852 */       if (this.wrappedStmt != null) {
/*  853 */         ((CallableStatement)this.wrappedStmt).setBlob(parameterName, x);
/*      */       } else {
/*  855 */         throw SQLError.createSQLException("No operations allowed after statement closed", "S1000", this.exceptionInterceptor);
/*      */       }
/*      */     
/*      */     }
/*  859 */     catch (SQLException sqlEx) {
/*  860 */       checkAndFireConnectionError(sqlEx);
/*      */     } 
/*      */   }
/*      */   
/*      */   public void setCharacterStream(String parameterName, Reader reader) throws SQLException {
/*      */     try {
/*  866 */       if (this.wrappedStmt != null) {
/*  867 */         ((CallableStatement)this.wrappedStmt).setCharacterStream(parameterName, reader);
/*      */       } else {
/*  869 */         throw SQLError.createSQLException("No operations allowed after statement closed", "S1000", this.exceptionInterceptor);
/*      */       }
/*      */     
/*      */     }
/*  873 */     catch (SQLException sqlEx) {
/*  874 */       checkAndFireConnectionError(sqlEx);
/*      */     } 
/*      */   }
/*      */   
/*      */   public void setCharacterStream(String parameterName, Reader reader, long length) throws SQLException {
/*      */     try {
/*  880 */       if (this.wrappedStmt != null) {
/*  881 */         ((CallableStatement)this.wrappedStmt).setCharacterStream(parameterName, reader, length);
/*      */       } else {
/*  883 */         throw SQLError.createSQLException("No operations allowed after statement closed", "S1000", this.exceptionInterceptor);
/*      */       }
/*      */     
/*      */     }
/*  887 */     catch (SQLException sqlEx) {
/*  888 */       checkAndFireConnectionError(sqlEx);
/*      */     } 
/*      */   }
/*      */   
/*      */   public void setClob(String parameterName, Clob x) throws SQLException {
/*      */     try {
/*  894 */       if (this.wrappedStmt != null) {
/*  895 */         ((CallableStatement)this.wrappedStmt).setClob(parameterName, x);
/*      */       } else {
/*  897 */         throw SQLError.createSQLException("No operations allowed after statement closed", "S1000", this.exceptionInterceptor);
/*      */       }
/*      */     
/*      */     }
/*  901 */     catch (SQLException sqlEx) {
/*  902 */       checkAndFireConnectionError(sqlEx);
/*      */     } 
/*      */   }
/*      */   
/*      */   public void setClob(String parameterName, Reader reader) throws SQLException {
/*      */     try {
/*  908 */       if (this.wrappedStmt != null) {
/*  909 */         ((CallableStatement)this.wrappedStmt).setClob(parameterName, reader);
/*      */       } else {
/*  911 */         throw SQLError.createSQLException("No operations allowed after statement closed", "S1000", this.exceptionInterceptor);
/*      */       }
/*      */     
/*      */     }
/*  915 */     catch (SQLException sqlEx) {
/*  916 */       checkAndFireConnectionError(sqlEx);
/*      */     } 
/*      */   }
/*      */   
/*      */   public void setClob(String parameterName, Reader reader, long length) throws SQLException {
/*      */     try {
/*  922 */       if (this.wrappedStmt != null) {
/*  923 */         ((CallableStatement)this.wrappedStmt).setClob(parameterName, reader, length);
/*      */       } else {
/*  925 */         throw SQLError.createSQLException("No operations allowed after statement closed", "S1000", this.exceptionInterceptor);
/*      */       }
/*      */     
/*      */     }
/*  929 */     catch (SQLException sqlEx) {
/*  930 */       checkAndFireConnectionError(sqlEx);
/*      */     } 
/*      */   }
/*      */   
/*      */   public void setNCharacterStream(String parameterName, Reader reader) throws SQLException {
/*      */     try {
/*  936 */       if (this.wrappedStmt != null) {
/*  937 */         ((CallableStatement)this.wrappedStmt).setNCharacterStream(parameterName, reader);
/*      */       } else {
/*  939 */         throw SQLError.createSQLException("No operations allowed after statement closed", "S1000", this.exceptionInterceptor);
/*      */       }
/*      */     
/*      */     }
/*  943 */     catch (SQLException sqlEx) {
/*  944 */       checkAndFireConnectionError(sqlEx);
/*      */     } 
/*      */   }
/*      */   
/*      */   public void setNCharacterStream(String parameterName, Reader reader, long length) throws SQLException {
/*      */     try {
/*  950 */       if (this.wrappedStmt != null) {
/*  951 */         ((CallableStatement)this.wrappedStmt).setNCharacterStream(parameterName, reader, length);
/*      */       } else {
/*  953 */         throw SQLError.createSQLException("No operations allowed after statement closed", "S1000", this.exceptionInterceptor);
/*      */       }
/*      */     
/*      */     }
/*  957 */     catch (SQLException sqlEx) {
/*  958 */       checkAndFireConnectionError(sqlEx);
/*      */     } 
/*      */   }
/*      */   
/*      */   public NClob getNClob(int parameterIndex) throws SQLException {
/*      */     try {
/*  964 */       if (this.wrappedStmt != null) {
/*  965 */         return ((CallableStatement)this.wrappedStmt).getNClob(parameterIndex);
/*      */       }
/*  967 */       throw SQLError.createSQLException("No operations allowed after statement closed", "S1000", this.exceptionInterceptor);
/*      */ 
/*      */     
/*      */     }
/*  971 */     catch (SQLException sqlEx) {
/*  972 */       checkAndFireConnectionError(sqlEx);
/*      */ 
/*      */       
/*  975 */       return null;
/*      */     } 
/*      */   }
/*      */   public String getNString(int parameterIndex) throws SQLException {
/*      */     try {
/*  980 */       if (this.wrappedStmt != null) {
/*  981 */         return ((CallableStatement)this.wrappedStmt).getNString(parameterIndex);
/*      */       }
/*  983 */       throw SQLError.createSQLException("No operations allowed after statement closed", "S1000", this.exceptionInterceptor);
/*      */ 
/*      */     
/*      */     }
/*  987 */     catch (SQLException sqlEx) {
/*  988 */       checkAndFireConnectionError(sqlEx);
/*      */ 
/*      */       
/*  991 */       return null;
/*      */     } 
/*      */   }
/*      */   public RowId getRowId(int parameterIndex) throws SQLException {
/*      */     try {
/*  996 */       if (this.wrappedStmt != null) {
/*  997 */         return ((CallableStatement)this.wrappedStmt).getRowId(parameterIndex);
/*      */       }
/*  999 */       throw SQLError.createSQLException("No operations allowed after statement closed", "S1000", this.exceptionInterceptor);
/*      */ 
/*      */     
/*      */     }
/* 1003 */     catch (SQLException sqlEx) {
/* 1004 */       checkAndFireConnectionError(sqlEx);
/*      */ 
/*      */       
/* 1007 */       return null;
/*      */     } 
/*      */   }
/*      */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\mysql\jdbc\jdbc2\optional\JDBC4CallableStatementWrapper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.4
 */