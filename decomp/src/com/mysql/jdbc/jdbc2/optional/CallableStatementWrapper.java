/*      */ package com.mysql.jdbc.jdbc2.optional;
/*      */ 
/*      */ import com.mysql.jdbc.SQLError;
/*      */ import com.mysql.jdbc.Util;
/*      */ import java.io.InputStream;
/*      */ import java.io.Reader;
/*      */ import java.lang.reflect.Constructor;
/*      */ import java.math.BigDecimal;
/*      */ import java.net.URL;
/*      */ import java.sql.Array;
/*      */ import java.sql.Blob;
/*      */ import java.sql.CallableStatement;
/*      */ import java.sql.Clob;
/*      */ import java.sql.Date;
/*      */ import java.sql.Ref;
/*      */ import java.sql.SQLException;
/*      */ import java.sql.Time;
/*      */ import java.sql.Timestamp;
/*      */ import java.util.Calendar;
/*      */ import java.util.Map;
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
/*      */ public class CallableStatementWrapper
/*      */   extends PreparedStatementWrapper
/*      */   implements CallableStatement
/*      */ {
/*      */   private static final Constructor JDBC_4_CALLABLE_STATEMENT_WRAPPER_CTOR;
/*      */   
/*      */   static  {
/*   60 */     if (Util.isJdbc4()) {
/*      */       try {
/*   62 */         JDBC_4_CALLABLE_STATEMENT_WRAPPER_CTOR = Class.forName("com.mysql.jdbc.jdbc2.optional.JDBC4CallableStatementWrapper").getConstructor(new Class[] { ConnectionWrapper.class, MysqlPooledConnection.class, CallableStatement.class });
/*      */ 
/*      */ 
/*      */       
/*      */       }
/*   67 */       catch (SecurityException e) {
/*   68 */         throw new RuntimeException(e);
/*   69 */       } catch (NoSuchMethodException e) {
/*   70 */         throw new RuntimeException(e);
/*   71 */       } catch (ClassNotFoundException e) {
/*   72 */         throw new RuntimeException(e);
/*      */       } 
/*      */     } else {
/*   75 */       JDBC_4_CALLABLE_STATEMENT_WRAPPER_CTOR = null;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected static CallableStatementWrapper getInstance(ConnectionWrapper c, MysqlPooledConnection conn, CallableStatement toWrap) throws SQLException {
/*   82 */     if (!Util.isJdbc4()) {
/*   83 */       return new CallableStatementWrapper(c, conn, toWrap);
/*      */     }
/*      */ 
/*      */     
/*   87 */     return (CallableStatementWrapper)Util.handleNewInstance(JDBC_4_CALLABLE_STATEMENT_WRAPPER_CTOR, new Object[] { c, conn, toWrap }, conn.getExceptionInterceptor());
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
/*  102 */   public CallableStatementWrapper(ConnectionWrapper c, MysqlPooledConnection conn, CallableStatement toWrap) { super(c, conn, toWrap); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void registerOutParameter(int parameterIndex, int sqlType) throws SQLException {
/*      */     try {
/*  113 */       if (this.wrappedStmt != null) {
/*  114 */         ((CallableStatement)this.wrappedStmt).registerOutParameter(parameterIndex, sqlType);
/*      */       } else {
/*      */         
/*  117 */         throw SQLError.createSQLException("No operations allowed after statement closed", "S1000", this.exceptionInterceptor);
/*      */       }
/*      */     
/*      */     }
/*  121 */     catch (SQLException sqlEx) {
/*  122 */       checkAndFireConnectionError(sqlEx);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void registerOutParameter(int parameterIndex, int sqlType, int scale) throws SQLException {
/*      */     try {
/*  134 */       if (this.wrappedStmt != null) {
/*  135 */         ((CallableStatement)this.wrappedStmt).registerOutParameter(parameterIndex, sqlType, scale);
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
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean wasNull() throws SQLException {
/*      */     try {
/*  154 */       if (this.wrappedStmt != null) {
/*  155 */         return ((CallableStatement)this.wrappedStmt).wasNull();
/*      */       }
/*  157 */       throw SQLError.createSQLException("No operations allowed after statement closed", "S1000", this.exceptionInterceptor);
/*      */ 
/*      */     
/*      */     }
/*  161 */     catch (SQLException sqlEx) {
/*  162 */       checkAndFireConnectionError(sqlEx);
/*      */ 
/*      */       
/*  165 */       return false;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getString(int parameterIndex) throws SQLException {
/*      */     try {
/*  175 */       if (this.wrappedStmt != null) {
/*  176 */         return ((CallableStatement)this.wrappedStmt).getString(parameterIndex);
/*      */       }
/*      */       
/*  179 */       throw SQLError.createSQLException("No operations allowed after statement closed", "S1000", this.exceptionInterceptor);
/*      */ 
/*      */     
/*      */     }
/*  183 */     catch (SQLException sqlEx) {
/*  184 */       checkAndFireConnectionError(sqlEx);
/*      */       
/*  186 */       return null;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getBoolean(int parameterIndex) throws SQLException {
/*      */     try {
/*  196 */       if (this.wrappedStmt != null) {
/*  197 */         return ((CallableStatement)this.wrappedStmt).getBoolean(parameterIndex);
/*      */       }
/*      */       
/*  200 */       throw SQLError.createSQLException("No operations allowed after statement closed", "S1000", this.exceptionInterceptor);
/*      */ 
/*      */     
/*      */     }
/*  204 */     catch (SQLException sqlEx) {
/*  205 */       checkAndFireConnectionError(sqlEx);
/*      */ 
/*      */       
/*  208 */       return false;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public byte getByte(int parameterIndex) throws SQLException {
/*      */     try {
/*  218 */       if (this.wrappedStmt != null) {
/*  219 */         return ((CallableStatement)this.wrappedStmt).getByte(parameterIndex);
/*      */       }
/*      */       
/*  222 */       throw SQLError.createSQLException("No operations allowed after statement closed", "S1000", this.exceptionInterceptor);
/*      */ 
/*      */     
/*      */     }
/*  226 */     catch (SQLException sqlEx) {
/*  227 */       checkAndFireConnectionError(sqlEx);
/*      */ 
/*      */       
/*  230 */       return 0;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public short getShort(int parameterIndex) throws SQLException {
/*      */     try {
/*  240 */       if (this.wrappedStmt != null) {
/*  241 */         return ((CallableStatement)this.wrappedStmt).getShort(parameterIndex);
/*      */       }
/*      */       
/*  244 */       throw SQLError.createSQLException("No operations allowed after statement closed", "S1000", this.exceptionInterceptor);
/*      */ 
/*      */     
/*      */     }
/*  248 */     catch (SQLException sqlEx) {
/*  249 */       checkAndFireConnectionError(sqlEx);
/*      */ 
/*      */       
/*  252 */       return 0;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getInt(int parameterIndex) throws SQLException {
/*      */     try {
/*  262 */       if (this.wrappedStmt != null) {
/*  263 */         return ((CallableStatement)this.wrappedStmt).getInt(parameterIndex);
/*      */       }
/*      */       
/*  266 */       throw SQLError.createSQLException("No operations allowed after statement closed", "S1000", this.exceptionInterceptor);
/*      */ 
/*      */     
/*      */     }
/*  270 */     catch (SQLException sqlEx) {
/*  271 */       checkAndFireConnectionError(sqlEx);
/*      */ 
/*      */       
/*  274 */       return 0;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public long getLong(int parameterIndex) throws SQLException {
/*      */     try {
/*  284 */       if (this.wrappedStmt != null) {
/*  285 */         return ((CallableStatement)this.wrappedStmt).getLong(parameterIndex);
/*      */       }
/*      */       
/*  288 */       throw SQLError.createSQLException("No operations allowed after statement closed", "S1000", this.exceptionInterceptor);
/*      */ 
/*      */     
/*      */     }
/*  292 */     catch (SQLException sqlEx) {
/*  293 */       checkAndFireConnectionError(sqlEx);
/*      */ 
/*      */       
/*  296 */       return 0L;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float getFloat(int parameterIndex) throws SQLException {
/*      */     try {
/*  306 */       if (this.wrappedStmt != null) {
/*  307 */         return ((CallableStatement)this.wrappedStmt).getFloat(parameterIndex);
/*      */       }
/*      */       
/*  310 */       throw SQLError.createSQLException("No operations allowed after statement closed", "S1000", this.exceptionInterceptor);
/*      */ 
/*      */     
/*      */     }
/*  314 */     catch (SQLException sqlEx) {
/*  315 */       checkAndFireConnectionError(sqlEx);
/*      */ 
/*      */       
/*  318 */       return 0.0F;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double getDouble(int parameterIndex) throws SQLException {
/*      */     try {
/*  328 */       if (this.wrappedStmt != null) {
/*  329 */         return ((CallableStatement)this.wrappedStmt).getDouble(parameterIndex);
/*      */       }
/*      */       
/*  332 */       throw SQLError.createSQLException("No operations allowed after statement closed", "S1000", this.exceptionInterceptor);
/*      */ 
/*      */     
/*      */     }
/*  336 */     catch (SQLException sqlEx) {
/*  337 */       checkAndFireConnectionError(sqlEx);
/*      */ 
/*      */       
/*  340 */       return 0.0D;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public BigDecimal getBigDecimal(int parameterIndex, int scale) throws SQLException {
/*      */     try {
/*  351 */       if (this.wrappedStmt != null) {
/*  352 */         return ((CallableStatement)this.wrappedStmt).getBigDecimal(parameterIndex, scale);
/*      */       }
/*      */       
/*  355 */       throw SQLError.createSQLException("No operations allowed after statement closed", "S1000", this.exceptionInterceptor);
/*      */ 
/*      */     
/*      */     }
/*  359 */     catch (SQLException sqlEx) {
/*  360 */       checkAndFireConnectionError(sqlEx);
/*      */ 
/*      */       
/*  363 */       return null;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public byte[] getBytes(int parameterIndex) throws SQLException {
/*      */     try {
/*  373 */       if (this.wrappedStmt != null) {
/*  374 */         return ((CallableStatement)this.wrappedStmt).getBytes(parameterIndex);
/*      */       }
/*      */       
/*  377 */       throw SQLError.createSQLException("No operations allowed after statement closed", "S1000", this.exceptionInterceptor);
/*      */ 
/*      */     
/*      */     }
/*  381 */     catch (SQLException sqlEx) {
/*  382 */       checkAndFireConnectionError(sqlEx);
/*      */ 
/*      */       
/*  385 */       return null;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Date getDate(int parameterIndex) throws SQLException {
/*      */     try {
/*  395 */       if (this.wrappedStmt != null) {
/*  396 */         return ((CallableStatement)this.wrappedStmt).getDate(parameterIndex);
/*      */       }
/*      */       
/*  399 */       throw SQLError.createSQLException("No operations allowed after statement closed", "S1000", this.exceptionInterceptor);
/*      */ 
/*      */     
/*      */     }
/*  403 */     catch (SQLException sqlEx) {
/*  404 */       checkAndFireConnectionError(sqlEx);
/*      */ 
/*      */       
/*  407 */       return null;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Time getTime(int parameterIndex) throws SQLException {
/*      */     try {
/*  417 */       if (this.wrappedStmt != null) {
/*  418 */         return ((CallableStatement)this.wrappedStmt).getTime(parameterIndex);
/*      */       }
/*      */       
/*  421 */       throw SQLError.createSQLException("No operations allowed after statement closed", "S1000", this.exceptionInterceptor);
/*      */ 
/*      */     
/*      */     }
/*  425 */     catch (SQLException sqlEx) {
/*  426 */       checkAndFireConnectionError(sqlEx);
/*      */ 
/*      */       
/*  429 */       return null;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Timestamp getTimestamp(int parameterIndex) throws SQLException {
/*      */     try {
/*  439 */       if (this.wrappedStmt != null) {
/*  440 */         return ((CallableStatement)this.wrappedStmt).getTimestamp(parameterIndex);
/*      */       }
/*      */       
/*  443 */       throw SQLError.createSQLException("No operations allowed after statement closed", "S1000", this.exceptionInterceptor);
/*      */ 
/*      */     
/*      */     }
/*  447 */     catch (SQLException sqlEx) {
/*  448 */       checkAndFireConnectionError(sqlEx);
/*      */ 
/*      */       
/*  451 */       return null;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object getObject(int parameterIndex) throws SQLException {
/*      */     try {
/*  461 */       if (this.wrappedStmt != null) {
/*  462 */         return ((CallableStatement)this.wrappedStmt).getObject(parameterIndex);
/*      */       }
/*      */       
/*  465 */       throw SQLError.createSQLException("No operations allowed after statement closed", "S1000", this.exceptionInterceptor);
/*      */ 
/*      */     
/*      */     }
/*  469 */     catch (SQLException sqlEx) {
/*  470 */       checkAndFireConnectionError(sqlEx);
/*      */ 
/*      */       
/*  473 */       return null;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public BigDecimal getBigDecimal(int parameterIndex) throws SQLException {
/*      */     try {
/*  483 */       if (this.wrappedStmt != null) {
/*  484 */         return ((CallableStatement)this.wrappedStmt).getBigDecimal(parameterIndex);
/*      */       }
/*      */       
/*  487 */       throw SQLError.createSQLException("No operations allowed after statement closed", "S1000", this.exceptionInterceptor);
/*      */ 
/*      */     
/*      */     }
/*  491 */     catch (SQLException sqlEx) {
/*  492 */       checkAndFireConnectionError(sqlEx);
/*      */ 
/*      */       
/*  495 */       return null;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object getObject(int parameterIndex, Map typeMap) throws SQLException {
/*      */     try {
/*  506 */       if (this.wrappedStmt != null) {
/*  507 */         return ((CallableStatement)this.wrappedStmt).getObject(parameterIndex, typeMap);
/*      */       }
/*      */       
/*  510 */       throw SQLError.createSQLException("No operations allowed after statement closed", "S1000", this.exceptionInterceptor);
/*      */ 
/*      */     
/*      */     }
/*  514 */     catch (SQLException sqlEx) {
/*  515 */       checkAndFireConnectionError(sqlEx);
/*      */       
/*  517 */       return null;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Ref getRef(int parameterIndex) throws SQLException {
/*      */     try {
/*  527 */       if (this.wrappedStmt != null) {
/*  528 */         return ((CallableStatement)this.wrappedStmt).getRef(parameterIndex);
/*      */       }
/*      */       
/*  531 */       throw SQLError.createSQLException("No operations allowed after statement closed", "S1000", this.exceptionInterceptor);
/*      */ 
/*      */     
/*      */     }
/*  535 */     catch (SQLException sqlEx) {
/*  536 */       checkAndFireConnectionError(sqlEx);
/*      */ 
/*      */       
/*  539 */       return null;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Blob getBlob(int parameterIndex) throws SQLException {
/*      */     try {
/*  549 */       if (this.wrappedStmt != null) {
/*  550 */         return ((CallableStatement)this.wrappedStmt).getBlob(parameterIndex);
/*      */       }
/*      */       
/*  553 */       throw SQLError.createSQLException("No operations allowed after statement closed", "S1000", this.exceptionInterceptor);
/*      */ 
/*      */     
/*      */     }
/*  557 */     catch (SQLException sqlEx) {
/*  558 */       checkAndFireConnectionError(sqlEx);
/*      */ 
/*      */       
/*  561 */       return null;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Clob getClob(int parameterIndex) throws SQLException {
/*      */     try {
/*  571 */       if (this.wrappedStmt != null) {
/*  572 */         return ((CallableStatement)this.wrappedStmt).getClob(parameterIndex);
/*      */       }
/*      */       
/*  575 */       throw SQLError.createSQLException("No operations allowed after statement closed", "S1000", this.exceptionInterceptor);
/*      */ 
/*      */     
/*      */     }
/*  579 */     catch (SQLException sqlEx) {
/*  580 */       checkAndFireConnectionError(sqlEx);
/*      */       
/*  582 */       return null;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Array getArray(int parameterIndex) throws SQLException {
/*      */     try {
/*  592 */       if (this.wrappedStmt != null) {
/*  593 */         return ((CallableStatement)this.wrappedStmt).getArray(parameterIndex);
/*      */       }
/*      */       
/*  596 */       throw SQLError.createSQLException("No operations allowed after statement closed", "S1000", this.exceptionInterceptor);
/*      */ 
/*      */     
/*      */     }
/*  600 */     catch (SQLException sqlEx) {
/*  601 */       checkAndFireConnectionError(sqlEx);
/*      */       
/*  603 */       return null;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Date getDate(int parameterIndex, Calendar cal) throws SQLException {
/*      */     try {
/*  613 */       if (this.wrappedStmt != null) {
/*  614 */         return ((CallableStatement)this.wrappedStmt).getDate(parameterIndex, cal);
/*      */       }
/*      */       
/*  617 */       throw SQLError.createSQLException("No operations allowed after statement closed", "S1000", this.exceptionInterceptor);
/*      */ 
/*      */     
/*      */     }
/*  621 */     catch (SQLException sqlEx) {
/*  622 */       checkAndFireConnectionError(sqlEx);
/*      */       
/*  624 */       return null;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Time getTime(int parameterIndex, Calendar cal) throws SQLException {
/*      */     try {
/*  634 */       if (this.wrappedStmt != null) {
/*  635 */         return ((CallableStatement)this.wrappedStmt).getTime(parameterIndex, cal);
/*      */       }
/*      */       
/*  638 */       throw SQLError.createSQLException("No operations allowed after statement closed", "S1000", this.exceptionInterceptor);
/*      */ 
/*      */     
/*      */     }
/*  642 */     catch (SQLException sqlEx) {
/*  643 */       checkAndFireConnectionError(sqlEx);
/*      */       
/*  645 */       return null;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Timestamp getTimestamp(int parameterIndex, Calendar cal) throws SQLException {
/*      */     try {
/*  656 */       if (this.wrappedStmt != null) {
/*  657 */         return ((CallableStatement)this.wrappedStmt).getTimestamp(parameterIndex, cal);
/*      */       }
/*      */       
/*  660 */       throw SQLError.createSQLException("No operations allowed after statement closed", "S1000", this.exceptionInterceptor);
/*      */ 
/*      */     
/*      */     }
/*  664 */     catch (SQLException sqlEx) {
/*  665 */       checkAndFireConnectionError(sqlEx);
/*      */       
/*  667 */       return null;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void registerOutParameter(int paramIndex, int sqlType, String typeName) throws SQLException {
/*      */     try {
/*  679 */       if (this.wrappedStmt != null) {
/*  680 */         ((CallableStatement)this.wrappedStmt).registerOutParameter(paramIndex, sqlType, typeName);
/*      */       } else {
/*      */         
/*  683 */         throw SQLError.createSQLException("No operations allowed after statement closed", "S1000", this.exceptionInterceptor);
/*      */       }
/*      */     
/*      */     }
/*  687 */     catch (SQLException sqlEx) {
/*  688 */       checkAndFireConnectionError(sqlEx);
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
/*      */   public void registerOutParameter(String parameterName, int sqlType) throws SQLException {
/*      */     try {
/*  701 */       if (this.wrappedStmt != null) {
/*  702 */         ((CallableStatement)this.wrappedStmt).registerOutParameter(parameterName, sqlType);
/*      */       } else {
/*      */         
/*  705 */         throw SQLError.createSQLException("No operations allowed after statement closed", "S1000", this.exceptionInterceptor);
/*      */       }
/*      */     
/*      */     }
/*  709 */     catch (SQLException sqlEx) {
/*  710 */       checkAndFireConnectionError(sqlEx);
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
/*      */   public void registerOutParameter(String parameterName, int sqlType, int scale) throws SQLException {
/*      */     try {
/*  723 */       if (this.wrappedStmt != null) {
/*  724 */         ((CallableStatement)this.wrappedStmt).registerOutParameter(parameterName, sqlType, scale);
/*      */       } else {
/*      */         
/*  727 */         throw SQLError.createSQLException("No operations allowed after statement closed", "S1000", this.exceptionInterceptor);
/*      */       }
/*      */     
/*      */     }
/*  731 */     catch (SQLException sqlEx) {
/*  732 */       checkAndFireConnectionError(sqlEx);
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
/*      */   public void registerOutParameter(String parameterName, int sqlType, String typeName) throws SQLException {
/*      */     try {
/*  745 */       if (this.wrappedStmt != null) {
/*  746 */         ((CallableStatement)this.wrappedStmt).registerOutParameter(parameterName, sqlType, typeName);
/*      */       } else {
/*      */         
/*  749 */         throw SQLError.createSQLException("No operations allowed after statement closed", "S1000", this.exceptionInterceptor);
/*      */       }
/*      */     
/*      */     }
/*  753 */     catch (SQLException sqlEx) {
/*  754 */       checkAndFireConnectionError(sqlEx);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public URL getURL(int parameterIndex) throws SQLException {
/*      */     try {
/*  765 */       if (this.wrappedStmt != null) {
/*  766 */         return ((CallableStatement)this.wrappedStmt).getURL(parameterIndex);
/*      */       }
/*      */       
/*  769 */       throw SQLError.createSQLException("No operations allowed after statement closed", "S1000", this.exceptionInterceptor);
/*      */ 
/*      */     
/*      */     }
/*  773 */     catch (SQLException sqlEx) {
/*  774 */       checkAndFireConnectionError(sqlEx);
/*      */ 
/*      */       
/*  777 */       return null;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setURL(String parameterName, URL val) throws SQLException {
/*      */     try {
/*  787 */       if (this.wrappedStmt != null) {
/*  788 */         ((CallableStatement)this.wrappedStmt).setURL(parameterName, val);
/*      */       } else {
/*      */         
/*  791 */         throw SQLError.createSQLException("No operations allowed after statement closed", "S1000", this.exceptionInterceptor);
/*      */       }
/*      */     
/*      */     }
/*  795 */     catch (SQLException sqlEx) {
/*  796 */       checkAndFireConnectionError(sqlEx);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setNull(String parameterName, int sqlType) throws SQLException {
/*      */     try {
/*  807 */       if (this.wrappedStmt != null) {
/*  808 */         ((CallableStatement)this.wrappedStmt).setNull(parameterName, sqlType);
/*      */       } else {
/*      */         
/*  811 */         throw SQLError.createSQLException("No operations allowed after statement closed", "S1000", this.exceptionInterceptor);
/*      */       }
/*      */     
/*      */     }
/*  815 */     catch (SQLException sqlEx) {
/*  816 */       checkAndFireConnectionError(sqlEx);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setBoolean(String parameterName, boolean x) throws SQLException {
/*      */     try {
/*  827 */       if (this.wrappedStmt != null) {
/*  828 */         ((CallableStatement)this.wrappedStmt).setBoolean(parameterName, x);
/*      */       } else {
/*      */         
/*  831 */         throw SQLError.createSQLException("No operations allowed after statement closed", "S1000", this.exceptionInterceptor);
/*      */       }
/*      */     
/*      */     }
/*  835 */     catch (SQLException sqlEx) {
/*  836 */       checkAndFireConnectionError(sqlEx);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setByte(String parameterName, byte x) throws SQLException {
/*      */     try {
/*  847 */       if (this.wrappedStmt != null) {
/*  848 */         ((CallableStatement)this.wrappedStmt).setByte(parameterName, x);
/*      */       } else {
/*      */         
/*  851 */         throw SQLError.createSQLException("No operations allowed after statement closed", "S1000", this.exceptionInterceptor);
/*      */       }
/*      */     
/*      */     }
/*  855 */     catch (SQLException sqlEx) {
/*  856 */       checkAndFireConnectionError(sqlEx);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setShort(String parameterName, short x) throws SQLException {
/*      */     try {
/*  867 */       if (this.wrappedStmt != null) {
/*  868 */         ((CallableStatement)this.wrappedStmt).setShort(parameterName, x);
/*      */       } else {
/*      */         
/*  871 */         throw SQLError.createSQLException("No operations allowed after statement closed", "S1000", this.exceptionInterceptor);
/*      */       }
/*      */     
/*      */     }
/*  875 */     catch (SQLException sqlEx) {
/*  876 */       checkAndFireConnectionError(sqlEx);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setInt(String parameterName, int x) throws SQLException {
/*      */     try {
/*  887 */       if (this.wrappedStmt != null) {
/*  888 */         ((CallableStatement)this.wrappedStmt).setInt(parameterName, x);
/*      */       } else {
/*  890 */         throw SQLError.createSQLException("No operations allowed after statement closed", "S1000", this.exceptionInterceptor);
/*      */       }
/*      */     
/*      */     }
/*  894 */     catch (SQLException sqlEx) {
/*  895 */       checkAndFireConnectionError(sqlEx);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setLong(String parameterName, long x) throws SQLException {
/*      */     try {
/*  906 */       if (this.wrappedStmt != null) {
/*  907 */         ((CallableStatement)this.wrappedStmt).setLong(parameterName, x);
/*      */       } else {
/*      */         
/*  910 */         throw SQLError.createSQLException("No operations allowed after statement closed", "S1000", this.exceptionInterceptor);
/*      */       }
/*      */     
/*      */     }
/*  914 */     catch (SQLException sqlEx) {
/*  915 */       checkAndFireConnectionError(sqlEx);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setFloat(String parameterName, float x) throws SQLException {
/*      */     try {
/*  926 */       if (this.wrappedStmt != null) {
/*  927 */         ((CallableStatement)this.wrappedStmt).setFloat(parameterName, x);
/*      */       } else {
/*      */         
/*  930 */         throw SQLError.createSQLException("No operations allowed after statement closed", "S1000", this.exceptionInterceptor);
/*      */       }
/*      */     
/*      */     }
/*  934 */     catch (SQLException sqlEx) {
/*  935 */       checkAndFireConnectionError(sqlEx);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setDouble(String parameterName, double x) throws SQLException {
/*      */     try {
/*  946 */       if (this.wrappedStmt != null) {
/*  947 */         ((CallableStatement)this.wrappedStmt).setDouble(parameterName, x);
/*      */       } else {
/*      */         
/*  950 */         throw SQLError.createSQLException("No operations allowed after statement closed", "S1000", this.exceptionInterceptor);
/*      */       }
/*      */     
/*      */     }
/*  954 */     catch (SQLException sqlEx) {
/*  955 */       checkAndFireConnectionError(sqlEx);
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
/*      */   public void setBigDecimal(String parameterName, BigDecimal x) throws SQLException {
/*      */     try {
/*  968 */       if (this.wrappedStmt != null) {
/*  969 */         ((CallableStatement)this.wrappedStmt).setBigDecimal(parameterName, x);
/*      */       } else {
/*      */         
/*  972 */         throw SQLError.createSQLException("No operations allowed after statement closed", "S1000", this.exceptionInterceptor);
/*      */       }
/*      */     
/*      */     }
/*  976 */     catch (SQLException sqlEx) {
/*  977 */       checkAndFireConnectionError(sqlEx);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setString(String parameterName, String x) throws SQLException {
/*      */     try {
/*  989 */       if (this.wrappedStmt != null) {
/*  990 */         ((CallableStatement)this.wrappedStmt).setString(parameterName, x);
/*      */       } else {
/*      */         
/*  993 */         throw SQLError.createSQLException("No operations allowed after statement closed", "S1000", this.exceptionInterceptor);
/*      */       }
/*      */     
/*      */     }
/*  997 */     catch (SQLException sqlEx) {
/*  998 */       checkAndFireConnectionError(sqlEx);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setBytes(String parameterName, byte[] x) throws SQLException {
/*      */     try {
/* 1009 */       if (this.wrappedStmt != null) {
/* 1010 */         ((CallableStatement)this.wrappedStmt).setBytes(parameterName, x);
/*      */       } else {
/*      */         
/* 1013 */         throw SQLError.createSQLException("No operations allowed after statement closed", "S1000", this.exceptionInterceptor);
/*      */       }
/*      */     
/*      */     }
/* 1017 */     catch (SQLException sqlEx) {
/* 1018 */       checkAndFireConnectionError(sqlEx);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setDate(String parameterName, Date x) throws SQLException {
/*      */     try {
/* 1029 */       if (this.wrappedStmt != null) {
/* 1030 */         ((CallableStatement)this.wrappedStmt).setDate(parameterName, x);
/*      */       } else {
/*      */         
/* 1033 */         throw SQLError.createSQLException("No operations allowed after statement closed", "S1000", this.exceptionInterceptor);
/*      */       }
/*      */     
/*      */     }
/* 1037 */     catch (SQLException sqlEx) {
/* 1038 */       checkAndFireConnectionError(sqlEx);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setTime(String parameterName, Time x) throws SQLException {
/*      */     try {
/* 1049 */       if (this.wrappedStmt != null) {
/* 1050 */         ((CallableStatement)this.wrappedStmt).setTime(parameterName, x);
/*      */       } else {
/*      */         
/* 1053 */         throw SQLError.createSQLException("No operations allowed after statement closed", "S1000", this.exceptionInterceptor);
/*      */       }
/*      */     
/*      */     }
/* 1057 */     catch (SQLException sqlEx) {
/* 1058 */       checkAndFireConnectionError(sqlEx);
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
/*      */   public void setTimestamp(String parameterName, Timestamp x) throws SQLException {
/*      */     try {
/* 1071 */       if (this.wrappedStmt != null) {
/* 1072 */         ((CallableStatement)this.wrappedStmt).setTimestamp(parameterName, x);
/*      */       } else {
/*      */         
/* 1075 */         throw SQLError.createSQLException("No operations allowed after statement closed", "S1000", this.exceptionInterceptor);
/*      */       }
/*      */     
/*      */     }
/* 1079 */     catch (SQLException sqlEx) {
/* 1080 */       checkAndFireConnectionError(sqlEx);
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
/*      */   public void setAsciiStream(String parameterName, InputStream x, int length) throws SQLException {
/*      */     try {
/* 1093 */       if (this.wrappedStmt != null) {
/* 1094 */         ((CallableStatement)this.wrappedStmt).setAsciiStream(parameterName, x, length);
/*      */       } else {
/*      */         
/* 1097 */         throw SQLError.createSQLException("No operations allowed after statement closed", "S1000", this.exceptionInterceptor);
/*      */       }
/*      */     
/*      */     }
/* 1101 */     catch (SQLException sqlEx) {
/* 1102 */       checkAndFireConnectionError(sqlEx);
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
/*      */   public void setBinaryStream(String parameterName, InputStream x, int length) throws SQLException {
/*      */     try {
/* 1116 */       if (this.wrappedStmt != null) {
/* 1117 */         ((CallableStatement)this.wrappedStmt).setBinaryStream(parameterName, x, length);
/*      */       } else {
/*      */         
/* 1120 */         throw SQLError.createSQLException("No operations allowed after statement closed", "S1000", this.exceptionInterceptor);
/*      */       }
/*      */     
/*      */     }
/* 1124 */     catch (SQLException sqlEx) {
/* 1125 */       checkAndFireConnectionError(sqlEx);
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
/*      */   public void setObject(String parameterName, Object x, int targetSqlType, int scale) throws SQLException {
/*      */     try {
/* 1138 */       if (this.wrappedStmt != null) {
/* 1139 */         ((CallableStatement)this.wrappedStmt).setObject(parameterName, x, targetSqlType, scale);
/*      */       } else {
/*      */         
/* 1142 */         throw SQLError.createSQLException("No operations allowed after statement closed", "S1000", this.exceptionInterceptor);
/*      */       }
/*      */     
/*      */     }
/* 1146 */     catch (SQLException sqlEx) {
/* 1147 */       checkAndFireConnectionError(sqlEx);
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
/*      */   public void setObject(String parameterName, Object x, int targetSqlType) throws SQLException {
/*      */     try {
/* 1160 */       if (this.wrappedStmt != null) {
/* 1161 */         ((CallableStatement)this.wrappedStmt).setObject(parameterName, x, targetSqlType);
/*      */       } else {
/*      */         
/* 1164 */         throw SQLError.createSQLException("No operations allowed after statement closed", "S1000", this.exceptionInterceptor);
/*      */       }
/*      */     
/*      */     }
/* 1168 */     catch (SQLException sqlEx) {
/* 1169 */       checkAndFireConnectionError(sqlEx);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setObject(String parameterName, Object x) throws SQLException {
/*      */     try {
/* 1181 */       if (this.wrappedStmt != null) {
/* 1182 */         ((CallableStatement)this.wrappedStmt).setObject(parameterName, x);
/*      */       } else {
/*      */         
/* 1185 */         throw SQLError.createSQLException("No operations allowed after statement closed", "S1000", this.exceptionInterceptor);
/*      */       }
/*      */     
/*      */     }
/* 1189 */     catch (SQLException sqlEx) {
/* 1190 */       checkAndFireConnectionError(sqlEx);
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
/*      */   public void setCharacterStream(String parameterName, Reader reader, int length) throws SQLException {
/*      */     try {
/* 1203 */       if (this.wrappedStmt != null) {
/* 1204 */         ((CallableStatement)this.wrappedStmt).setCharacterStream(parameterName, reader, length);
/*      */       } else {
/*      */         
/* 1207 */         throw SQLError.createSQLException("No operations allowed after statement closed", "S1000", this.exceptionInterceptor);
/*      */       }
/*      */     
/*      */     }
/* 1211 */     catch (SQLException sqlEx) {
/* 1212 */       checkAndFireConnectionError(sqlEx);
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
/*      */   public void setDate(String parameterName, Date x, Calendar cal) throws SQLException {
/*      */     try {
/* 1225 */       if (this.wrappedStmt != null) {
/* 1226 */         ((CallableStatement)this.wrappedStmt).setDate(parameterName, x, cal);
/*      */       } else {
/*      */         
/* 1229 */         throw SQLError.createSQLException("No operations allowed after statement closed", "S1000", this.exceptionInterceptor);
/*      */       }
/*      */     
/*      */     }
/* 1233 */     catch (SQLException sqlEx) {
/* 1234 */       checkAndFireConnectionError(sqlEx);
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
/*      */   public void setTime(String parameterName, Time x, Calendar cal) throws SQLException {
/*      */     try {
/* 1247 */       if (this.wrappedStmt != null) {
/* 1248 */         ((CallableStatement)this.wrappedStmt).setTime(parameterName, x, cal);
/*      */       } else {
/*      */         
/* 1251 */         throw SQLError.createSQLException("No operations allowed after statement closed", "S1000", this.exceptionInterceptor);
/*      */       }
/*      */     
/*      */     }
/* 1255 */     catch (SQLException sqlEx) {
/* 1256 */       checkAndFireConnectionError(sqlEx);
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
/*      */   public void setTimestamp(String parameterName, Timestamp x, Calendar cal) throws SQLException {
/*      */     try {
/* 1269 */       if (this.wrappedStmt != null) {
/* 1270 */         ((CallableStatement)this.wrappedStmt).setTimestamp(parameterName, x, cal);
/*      */       } else {
/*      */         
/* 1273 */         throw SQLError.createSQLException("No operations allowed after statement closed", "S1000", this.exceptionInterceptor);
/*      */       }
/*      */     
/*      */     }
/* 1277 */     catch (SQLException sqlEx) {
/* 1278 */       checkAndFireConnectionError(sqlEx);
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
/*      */   public void setNull(String parameterName, int sqlType, String typeName) throws SQLException {
/*      */     try {
/* 1291 */       if (this.wrappedStmt != null) {
/* 1292 */         ((CallableStatement)this.wrappedStmt).setNull(parameterName, sqlType, typeName);
/*      */       } else {
/*      */         
/* 1295 */         throw SQLError.createSQLException("No operations allowed after statement closed", "S1000", this.exceptionInterceptor);
/*      */       }
/*      */     
/*      */     }
/* 1299 */     catch (SQLException sqlEx) {
/* 1300 */       checkAndFireConnectionError(sqlEx);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getString(String parameterName) throws SQLException {
/*      */     try {
/* 1311 */       if (this.wrappedStmt != null) {
/* 1312 */         return ((CallableStatement)this.wrappedStmt).getString(parameterName);
/*      */       }
/*      */       
/* 1315 */       throw SQLError.createSQLException("No operations allowed after statement closed", "S1000", this.exceptionInterceptor);
/*      */ 
/*      */     
/*      */     }
/* 1319 */     catch (SQLException sqlEx) {
/* 1320 */       checkAndFireConnectionError(sqlEx);
/*      */       
/* 1322 */       return null;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getBoolean(String parameterName) throws SQLException {
/*      */     try {
/* 1332 */       if (this.wrappedStmt != null) {
/* 1333 */         return ((CallableStatement)this.wrappedStmt).getBoolean(parameterName);
/*      */       }
/*      */       
/* 1336 */       throw SQLError.createSQLException("No operations allowed after statement closed", "S1000", this.exceptionInterceptor);
/*      */ 
/*      */     
/*      */     }
/* 1340 */     catch (SQLException sqlEx) {
/* 1341 */       checkAndFireConnectionError(sqlEx);
/*      */ 
/*      */       
/* 1344 */       return false;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public byte getByte(String parameterName) throws SQLException {
/*      */     try {
/* 1354 */       if (this.wrappedStmt != null) {
/* 1355 */         return ((CallableStatement)this.wrappedStmt).getByte(parameterName);
/*      */       }
/*      */       
/* 1358 */       throw SQLError.createSQLException("No operations allowed after statement closed", "S1000", this.exceptionInterceptor);
/*      */ 
/*      */     
/*      */     }
/* 1362 */     catch (SQLException sqlEx) {
/* 1363 */       checkAndFireConnectionError(sqlEx);
/*      */ 
/*      */       
/* 1366 */       return 0;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public short getShort(String parameterName) throws SQLException {
/*      */     try {
/* 1376 */       if (this.wrappedStmt != null) {
/* 1377 */         return ((CallableStatement)this.wrappedStmt).getShort(parameterName);
/*      */       }
/*      */       
/* 1380 */       throw SQLError.createSQLException("No operations allowed after statement closed", "S1000", this.exceptionInterceptor);
/*      */ 
/*      */     
/*      */     }
/* 1384 */     catch (SQLException sqlEx) {
/* 1385 */       checkAndFireConnectionError(sqlEx);
/*      */ 
/*      */       
/* 1388 */       return 0;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getInt(String parameterName) throws SQLException {
/*      */     try {
/* 1398 */       if (this.wrappedStmt != null) {
/* 1399 */         return ((CallableStatement)this.wrappedStmt).getInt(parameterName);
/*      */       }
/*      */       
/* 1402 */       throw SQLError.createSQLException("No operations allowed after statement closed", "S1000", this.exceptionInterceptor);
/*      */ 
/*      */     
/*      */     }
/* 1406 */     catch (SQLException sqlEx) {
/* 1407 */       checkAndFireConnectionError(sqlEx);
/*      */ 
/*      */       
/* 1410 */       return 0;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public long getLong(String parameterName) throws SQLException {
/*      */     try {
/* 1420 */       if (this.wrappedStmt != null) {
/* 1421 */         return ((CallableStatement)this.wrappedStmt).getLong(parameterName);
/*      */       }
/*      */       
/* 1424 */       throw SQLError.createSQLException("No operations allowed after statement closed", "S1000", this.exceptionInterceptor);
/*      */ 
/*      */     
/*      */     }
/* 1428 */     catch (SQLException sqlEx) {
/* 1429 */       checkAndFireConnectionError(sqlEx);
/*      */ 
/*      */       
/* 1432 */       return 0L;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float getFloat(String parameterName) throws SQLException {
/*      */     try {
/* 1442 */       if (this.wrappedStmt != null) {
/* 1443 */         return ((CallableStatement)this.wrappedStmt).getFloat(parameterName);
/*      */       }
/*      */       
/* 1446 */       throw SQLError.createSQLException("No operations allowed after statement closed", "S1000", this.exceptionInterceptor);
/*      */ 
/*      */     
/*      */     }
/* 1450 */     catch (SQLException sqlEx) {
/* 1451 */       checkAndFireConnectionError(sqlEx);
/*      */ 
/*      */       
/* 1454 */       return 0.0F;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double getDouble(String parameterName) throws SQLException {
/*      */     try {
/* 1464 */       if (this.wrappedStmt != null) {
/* 1465 */         return ((CallableStatement)this.wrappedStmt).getDouble(parameterName);
/*      */       }
/*      */       
/* 1468 */       throw SQLError.createSQLException("No operations allowed after statement closed", "S1000", this.exceptionInterceptor);
/*      */ 
/*      */     
/*      */     }
/* 1472 */     catch (SQLException sqlEx) {
/* 1473 */       checkAndFireConnectionError(sqlEx);
/*      */ 
/*      */       
/* 1476 */       return 0.0D;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public byte[] getBytes(String parameterName) throws SQLException {
/*      */     try {
/* 1486 */       if (this.wrappedStmt != null) {
/* 1487 */         return ((CallableStatement)this.wrappedStmt).getBytes(parameterName);
/*      */       }
/*      */       
/* 1490 */       throw SQLError.createSQLException("No operations allowed after statement closed", "S1000", this.exceptionInterceptor);
/*      */ 
/*      */     
/*      */     }
/* 1494 */     catch (SQLException sqlEx) {
/* 1495 */       checkAndFireConnectionError(sqlEx);
/*      */ 
/*      */       
/* 1498 */       return null;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Date getDate(String parameterName) throws SQLException {
/*      */     try {
/* 1508 */       if (this.wrappedStmt != null) {
/* 1509 */         return ((CallableStatement)this.wrappedStmt).getDate(parameterName);
/*      */       }
/*      */       
/* 1512 */       throw SQLError.createSQLException("No operations allowed after statement closed", "S1000", this.exceptionInterceptor);
/*      */ 
/*      */     
/*      */     }
/* 1516 */     catch (SQLException sqlEx) {
/* 1517 */       checkAndFireConnectionError(sqlEx);
/*      */ 
/*      */       
/* 1520 */       return null;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Time getTime(String parameterName) throws SQLException {
/*      */     try {
/* 1530 */       if (this.wrappedStmt != null) {
/* 1531 */         return ((CallableStatement)this.wrappedStmt).getTime(parameterName);
/*      */       }
/*      */       
/* 1534 */       throw SQLError.createSQLException("No operations allowed after statement closed", "S1000", this.exceptionInterceptor);
/*      */ 
/*      */     
/*      */     }
/* 1538 */     catch (SQLException sqlEx) {
/* 1539 */       checkAndFireConnectionError(sqlEx);
/*      */ 
/*      */       
/* 1542 */       return null;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Timestamp getTimestamp(String parameterName) throws SQLException {
/*      */     try {
/* 1552 */       if (this.wrappedStmt != null) {
/* 1553 */         return ((CallableStatement)this.wrappedStmt).getTimestamp(parameterName);
/*      */       }
/*      */       
/* 1556 */       throw SQLError.createSQLException("No operations allowed after statement closed", "S1000", this.exceptionInterceptor);
/*      */ 
/*      */     
/*      */     }
/* 1560 */     catch (SQLException sqlEx) {
/* 1561 */       checkAndFireConnectionError(sqlEx);
/*      */ 
/*      */       
/* 1564 */       return null;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object getObject(String parameterName) throws SQLException {
/*      */     try {
/* 1574 */       if (this.wrappedStmt != null) {
/* 1575 */         return ((CallableStatement)this.wrappedStmt).getObject(parameterName);
/*      */       }
/*      */       
/* 1578 */       throw SQLError.createSQLException("No operations allowed after statement closed", "S1000", this.exceptionInterceptor);
/*      */ 
/*      */     
/*      */     }
/* 1582 */     catch (SQLException sqlEx) {
/* 1583 */       checkAndFireConnectionError(sqlEx);
/*      */ 
/*      */       
/* 1586 */       return null;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public BigDecimal getBigDecimal(String parameterName) throws SQLException {
/*      */     try {
/* 1596 */       if (this.wrappedStmt != null) {
/* 1597 */         return ((CallableStatement)this.wrappedStmt).getBigDecimal(parameterName);
/*      */       }
/*      */       
/* 1600 */       throw SQLError.createSQLException("No operations allowed after statement closed", "S1000", this.exceptionInterceptor);
/*      */ 
/*      */     
/*      */     }
/* 1604 */     catch (SQLException sqlEx) {
/* 1605 */       checkAndFireConnectionError(sqlEx);
/*      */ 
/*      */       
/* 1608 */       return null;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object getObject(String parameterName, Map typeMap) throws SQLException {
/*      */     try {
/* 1619 */       if (this.wrappedStmt != null) {
/* 1620 */         return ((CallableStatement)this.wrappedStmt).getObject(parameterName, typeMap);
/*      */       }
/*      */       
/* 1623 */       throw SQLError.createSQLException("No operations allowed after statement closed", "S1000", this.exceptionInterceptor);
/*      */ 
/*      */     
/*      */     }
/* 1627 */     catch (SQLException sqlEx) {
/* 1628 */       checkAndFireConnectionError(sqlEx);
/*      */       
/* 1630 */       return null;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Ref getRef(String parameterName) throws SQLException {
/*      */     try {
/* 1640 */       if (this.wrappedStmt != null) {
/* 1641 */         return ((CallableStatement)this.wrappedStmt).getRef(parameterName);
/*      */       }
/*      */       
/* 1644 */       throw SQLError.createSQLException("No operations allowed after statement closed", "S1000", this.exceptionInterceptor);
/*      */ 
/*      */     
/*      */     }
/* 1648 */     catch (SQLException sqlEx) {
/* 1649 */       checkAndFireConnectionError(sqlEx);
/*      */ 
/*      */       
/* 1652 */       return null;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Blob getBlob(String parameterName) throws SQLException {
/*      */     try {
/* 1662 */       if (this.wrappedStmt != null) {
/* 1663 */         return ((CallableStatement)this.wrappedStmt).getBlob(parameterName);
/*      */       }
/*      */       
/* 1666 */       throw SQLError.createSQLException("No operations allowed after statement closed", "S1000", this.exceptionInterceptor);
/*      */ 
/*      */     
/*      */     }
/* 1670 */     catch (SQLException sqlEx) {
/* 1671 */       checkAndFireConnectionError(sqlEx);
/*      */ 
/*      */       
/* 1674 */       return null;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Clob getClob(String parameterName) throws SQLException {
/*      */     try {
/* 1684 */       if (this.wrappedStmt != null) {
/* 1685 */         return ((CallableStatement)this.wrappedStmt).getClob(parameterName);
/*      */       }
/*      */       
/* 1688 */       throw SQLError.createSQLException("No operations allowed after statement closed", "S1000", this.exceptionInterceptor);
/*      */ 
/*      */     
/*      */     }
/* 1692 */     catch (SQLException sqlEx) {
/* 1693 */       checkAndFireConnectionError(sqlEx);
/*      */       
/* 1695 */       return null;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Array getArray(String parameterName) throws SQLException {
/*      */     try {
/* 1705 */       if (this.wrappedStmt != null) {
/* 1706 */         return ((CallableStatement)this.wrappedStmt).getArray(parameterName);
/*      */       }
/*      */       
/* 1709 */       throw SQLError.createSQLException("No operations allowed after statement closed", "S1000", this.exceptionInterceptor);
/*      */ 
/*      */     
/*      */     }
/* 1713 */     catch (SQLException sqlEx) {
/* 1714 */       checkAndFireConnectionError(sqlEx);
/*      */       
/* 1716 */       return null;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Date getDate(String parameterName, Calendar cal) throws SQLException {
/*      */     try {
/* 1726 */       if (this.wrappedStmt != null) {
/* 1727 */         return ((CallableStatement)this.wrappedStmt).getDate(parameterName, cal);
/*      */       }
/*      */       
/* 1730 */       throw SQLError.createSQLException("No operations allowed after statement closed", "S1000", this.exceptionInterceptor);
/*      */ 
/*      */     
/*      */     }
/* 1734 */     catch (SQLException sqlEx) {
/* 1735 */       checkAndFireConnectionError(sqlEx);
/*      */       
/* 1737 */       return null;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Time getTime(String parameterName, Calendar cal) throws SQLException {
/*      */     try {
/* 1747 */       if (this.wrappedStmt != null) {
/* 1748 */         return ((CallableStatement)this.wrappedStmt).getTime(parameterName, cal);
/*      */       }
/*      */       
/* 1751 */       throw SQLError.createSQLException("No operations allowed after statement closed", "S1000", this.exceptionInterceptor);
/*      */ 
/*      */     
/*      */     }
/* 1755 */     catch (SQLException sqlEx) {
/* 1756 */       checkAndFireConnectionError(sqlEx);
/*      */       
/* 1758 */       return null;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Timestamp getTimestamp(String parameterName, Calendar cal) throws SQLException {
/*      */     try {
/* 1769 */       if (this.wrappedStmt != null) {
/* 1770 */         return ((CallableStatement)this.wrappedStmt).getTimestamp(parameterName, cal);
/*      */       }
/*      */       
/* 1773 */       throw SQLError.createSQLException("No operations allowed after statement closed", "S1000", this.exceptionInterceptor);
/*      */ 
/*      */     
/*      */     }
/* 1777 */     catch (SQLException sqlEx) {
/* 1778 */       checkAndFireConnectionError(sqlEx);
/*      */       
/* 1780 */       return null;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public URL getURL(String parameterName) throws SQLException {
/*      */     try {
/* 1790 */       if (this.wrappedStmt != null) {
/* 1791 */         return ((CallableStatement)this.wrappedStmt).getURL(parameterName);
/*      */       }
/*      */       
/* 1794 */       throw SQLError.createSQLException("No operations allowed after statement closed", "S1000", this.exceptionInterceptor);
/*      */ 
/*      */     
/*      */     }
/* 1798 */     catch (SQLException sqlEx) {
/* 1799 */       checkAndFireConnectionError(sqlEx);
/*      */ 
/*      */       
/* 1802 */       return null;
/*      */     } 
/*      */   }
/*      */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\mysql\jdbc\jdbc2\optional\CallableStatementWrapper.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */