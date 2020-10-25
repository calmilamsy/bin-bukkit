/*     */ package com.mysql.jdbc.jdbc2.optional;
/*     */ 
/*     */ import com.mysql.jdbc.Connection;
/*     */ import com.mysql.jdbc.ExceptionInterceptor;
/*     */ import com.mysql.jdbc.SQLError;
/*     */ import com.mysql.jdbc.Util;
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.sql.Connection;
/*     */ import java.sql.SQLException;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import javax.sql.ConnectionEvent;
/*     */ import javax.sql.ConnectionEventListener;
/*     */ import javax.sql.PooledConnection;
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
/*     */ public class MysqlPooledConnection
/*     */   implements PooledConnection
/*     */ {
/*     */   private static final Constructor JDBC_4_POOLED_CONNECTION_WRAPPER_CTOR;
/*     */   public static final int CONNECTION_ERROR_EVENT = 1;
/*     */   public static final int CONNECTION_CLOSED_EVENT = 2;
/*     */   private Map connectionEventListeners;
/*     */   private Connection logicalHandle;
/*     */   private Connection physicalConn;
/*     */   private ExceptionInterceptor exceptionInterceptor;
/*     */   
/*     */   static  {
/*  57 */     if (Util.isJdbc4()) {
/*     */       try {
/*  59 */         JDBC_4_POOLED_CONNECTION_WRAPPER_CTOR = Class.forName("com.mysql.jdbc.jdbc2.optional.JDBC4MysqlPooledConnection").getConstructor(new Class[] { Connection.class });
/*     */ 
/*     */       
/*     */       }
/*  63 */       catch (SecurityException e) {
/*  64 */         throw new RuntimeException(e);
/*  65 */       } catch (NoSuchMethodException e) {
/*  66 */         throw new RuntimeException(e);
/*  67 */       } catch (ClassNotFoundException e) {
/*  68 */         throw new RuntimeException(e);
/*     */       } 
/*     */     } else {
/*  71 */       JDBC_4_POOLED_CONNECTION_WRAPPER_CTOR = null;
/*     */     } 
/*     */   }
/*     */   
/*     */   protected static MysqlPooledConnection getInstance(Connection connection) throws SQLException {
/*  76 */     if (!Util.isJdbc4()) {
/*  77 */       return new MysqlPooledConnection(connection);
/*     */     }
/*     */     
/*  80 */     return (MysqlPooledConnection)Util.handleNewInstance(JDBC_4_POOLED_CONNECTION_WRAPPER_CTOR, new Object[] { connection }, connection.getExceptionInterceptor());
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
/*     */ 
/*     */ 
/*     */   
/*     */   public MysqlPooledConnection(Connection connection) {
/* 114 */     this.logicalHandle = null;
/* 115 */     this.physicalConn = connection;
/* 116 */     this.connectionEventListeners = new HashMap();
/* 117 */     this.exceptionInterceptor = this.physicalConn.getExceptionInterceptor();
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
/*     */   public void addConnectionEventListener(ConnectionEventListener connectioneventlistener) {
/* 130 */     if (this.connectionEventListeners != null) {
/* 131 */       this.connectionEventListeners.put(connectioneventlistener, connectioneventlistener);
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
/*     */   public void removeConnectionEventListener(ConnectionEventListener connectioneventlistener) {
/* 146 */     if (this.connectionEventListeners != null) {
/* 147 */       this.connectionEventListeners.remove(connectioneventlistener);
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
/* 158 */   public Connection getConnection() throws SQLException { return getConnection(true, false); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Connection getConnection(boolean resetServerState, boolean forXa) throws SQLException {
/* 165 */     if (this.physicalConn == null) {
/*     */       
/* 167 */       SQLException sqlException = SQLError.createSQLException("Physical Connection doesn't exist", this.exceptionInterceptor);
/*     */       
/* 169 */       callConnectionEventListeners(1, sqlException);
/*     */       
/* 171 */       throw sqlException;
/*     */     } 
/*     */ 
/*     */     
/*     */     try {
/* 176 */       if (this.logicalHandle != null) {
/* 177 */         ((ConnectionWrapper)this.logicalHandle).close(false);
/*     */       }
/*     */       
/* 180 */       if (resetServerState) {
/* 181 */         this.physicalConn.resetServerState();
/*     */       }
/*     */       
/* 184 */       this.logicalHandle = ConnectionWrapper.getInstance(this, this.physicalConn, forXa);
/*     */     
/*     */     }
/* 187 */     catch (SQLException sqlException) {
/* 188 */       callConnectionEventListeners(1, sqlException);
/*     */       
/* 190 */       throw sqlException;
/*     */     } 
/*     */     
/* 193 */     return this.logicalHandle;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void close() throws SQLException {
/* 204 */     if (this.physicalConn != null) {
/* 205 */       this.physicalConn.close();
/*     */       
/* 207 */       this.physicalConn = null;
/*     */     } 
/*     */     
/* 210 */     if (this.connectionEventListeners != null) {
/* 211 */       this.connectionEventListeners.clear();
/*     */       
/* 213 */       this.connectionEventListeners = null;
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
/*     */   protected void callConnectionEventListeners(int eventType, SQLException sqlException) {
/* 232 */     if (this.connectionEventListeners == null) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 237 */     Iterator iterator = this.connectionEventListeners.entrySet().iterator();
/*     */     
/* 239 */     ConnectionEvent connectionevent = new ConnectionEvent(this, sqlException);
/*     */ 
/*     */     
/* 242 */     while (iterator.hasNext()) {
/*     */       
/* 244 */       ConnectionEventListener connectioneventlistener = (ConnectionEventListener)((Map.Entry)iterator.next()).getValue();
/*     */ 
/*     */       
/* 247 */       if (eventType == 2) {
/* 248 */         connectioneventlistener.connectionClosed(connectionevent); continue;
/* 249 */       }  if (eventType == 1) {
/* 250 */         connectioneventlistener.connectionErrorOccurred(connectionevent);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 257 */   protected ExceptionInterceptor getExceptionInterceptor() { return this.exceptionInterceptor; }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\mysql\jdbc\jdbc2\optional\MysqlPooledConnection.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */