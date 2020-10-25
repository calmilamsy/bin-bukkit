/*     */ package com.mysql.jdbc.jdbc2.optional;
/*     */ 
/*     */ import com.mysql.jdbc.ConnectionImpl;
/*     */ import com.mysql.jdbc.Util;
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.sql.Connection;
/*     */ import java.sql.SQLException;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import javax.sql.XAConnection;
/*     */ import javax.transaction.xa.XAException;
/*     */ import javax.transaction.xa.XAResource;
/*     */ import javax.transaction.xa.Xid;
/*     */ 
/*     */ public class SuspendableXAConnection
/*     */   extends MysqlPooledConnection
/*     */   implements XAConnection, XAResource {
/*     */   private static final Constructor JDBC_4_XA_CONNECTION_WRAPPER_CTOR;
/*     */   private static final Map XIDS_TO_PHYSICAL_CONNECTIONS;
/*     */   private Xid currentXid;
/*     */   
/*     */   static  {
/*  23 */     if (Util.isJdbc4()) {
/*     */       try {
/*  25 */         JDBC_4_XA_CONNECTION_WRAPPER_CTOR = Class.forName("com.mysql.jdbc.jdbc2.optional.JDBC4SuspendableXAConnection").getConstructor(new Class[] { ConnectionImpl.class });
/*     */ 
/*     */       
/*     */       }
/*  29 */       catch (SecurityException e) {
/*  30 */         throw new RuntimeException(e);
/*  31 */       } catch (NoSuchMethodException e) {
/*  32 */         throw new RuntimeException(e);
/*  33 */       } catch (ClassNotFoundException e) {
/*  34 */         throw new RuntimeException(e);
/*     */       } 
/*     */     } else {
/*  37 */       JDBC_4_XA_CONNECTION_WRAPPER_CTOR = null;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  56 */     XIDS_TO_PHYSICAL_CONNECTIONS = new HashMap();
/*     */   }
/*     */   private XAConnection currentXAConnection; private XAResource currentXAResource;
/*     */   private ConnectionImpl underlyingConnection;
/*     */   
/*     */   protected static SuspendableXAConnection getInstance(ConnectionImpl mysqlConnection) throws SQLException {
/*     */     if (!Util.isJdbc4())
/*     */       return new SuspendableXAConnection(mysqlConnection); 
/*     */     return (SuspendableXAConnection)Util.handleNewInstance(JDBC_4_XA_CONNECTION_WRAPPER_CTOR, new Object[] { mysqlConnection }, mysqlConnection.getExceptionInterceptor());
/*     */   }
/*     */   
/*     */   public SuspendableXAConnection(ConnectionImpl connection) {
/*     */     super(connection);
/*     */     this.underlyingConnection = connection;
/*     */   }
/*     */   
/*     */   private static XAConnection findConnectionForXid(ConnectionImpl connectionToWrap, Xid xid) throws SQLException {
/*  73 */     XAConnection conn = (XAConnection)XIDS_TO_PHYSICAL_CONNECTIONS.get(xid);
/*     */     
/*  75 */     if (conn == null) {
/*  76 */       conn = new MysqlXAConnection(connectionToWrap, connectionToWrap.getLogXaCommands());
/*     */       
/*  78 */       XIDS_TO_PHYSICAL_CONNECTIONS.put(xid, conn);
/*     */     } 
/*     */     
/*  81 */     return conn;
/*     */   }
/*     */ 
/*     */   
/*  85 */   private static void removeXAConnectionMapping(Xid xid) { XIDS_TO_PHYSICAL_CONNECTIONS.remove(xid); }
/*     */ 
/*     */   
/*     */   private void switchToXid(Xid xid) {
/*  89 */     if (xid == null) {
/*  90 */       throw new XAException();
/*     */     }
/*     */     
/*     */     try {
/*  94 */       if (!xid.equals(this.currentXid)) {
/*  95 */         XAConnection toSwitchTo = findConnectionForXid(this.underlyingConnection, xid);
/*  96 */         this.currentXAConnection = toSwitchTo;
/*  97 */         this.currentXid = xid;
/*  98 */         this.currentXAResource = toSwitchTo.getXAResource();
/*     */       } 
/* 100 */     } catch (SQLException sqlEx) {
/* 101 */       throw new XAException();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/* 106 */   public XAResource getXAResource() throws SQLException { return this; }
/*     */ 
/*     */   
/*     */   public void commit(Xid xid, boolean arg1) throws XAException {
/* 110 */     switchToXid(xid);
/* 111 */     this.currentXAResource.commit(xid, arg1);
/* 112 */     removeXAConnectionMapping(xid);
/*     */   }
/*     */   
/*     */   public void end(Xid xid, int arg1) throws XAException {
/* 116 */     switchToXid(xid);
/* 117 */     this.currentXAResource.end(xid, arg1);
/*     */   }
/*     */   
/*     */   public void forget(Xid xid) {
/* 121 */     switchToXid(xid);
/* 122 */     this.currentXAResource.forget(xid);
/*     */     
/* 124 */     removeXAConnectionMapping(xid);
/*     */   }
/*     */ 
/*     */   
/* 128 */   public int getTransactionTimeout() throws XAException { return 0; }
/*     */ 
/*     */ 
/*     */   
/* 132 */   public boolean isSameRM(XAResource xaRes) throws XAException { return (xaRes == this); }
/*     */ 
/*     */   
/*     */   public int prepare(Xid xid) throws XAException {
/* 136 */     switchToXid(xid);
/* 137 */     return this.currentXAResource.prepare(xid);
/*     */   }
/*     */ 
/*     */   
/* 141 */   public Xid[] recover(int flag) throws XAException { return MysqlXAConnection.recover(this.underlyingConnection, flag); }
/*     */ 
/*     */   
/*     */   public void rollback(Xid xid) {
/* 145 */     switchToXid(xid);
/* 146 */     this.currentXAResource.rollback(xid);
/* 147 */     removeXAConnectionMapping(xid);
/*     */   }
/*     */ 
/*     */   
/* 151 */   public boolean setTransactionTimeout(int arg0) throws XAException { return false; }
/*     */ 
/*     */   
/*     */   public void start(Xid xid, int arg1) throws XAException {
/* 155 */     switchToXid(xid);
/*     */     
/* 157 */     if (arg1 != 2097152) {
/* 158 */       this.currentXAResource.start(xid, arg1);
/*     */ 
/*     */ 
/*     */       
/*     */       return;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 167 */     this.currentXAResource.start(xid, 134217728);
/*     */   }
/*     */   
/*     */   public Connection getConnection() throws SQLException {
/* 171 */     if (this.currentXAConnection == null) {
/* 172 */       return getConnection(false, true);
/*     */     }
/*     */     
/* 175 */     return this.currentXAConnection.getConnection();
/*     */   }
/*     */   
/*     */   public void close() throws SQLException {
/* 179 */     if (this.currentXAConnection == null) {
/* 180 */       super.close();
/*     */     } else {
/* 182 */       removeXAConnectionMapping(this.currentXid);
/* 183 */       this.currentXAConnection.close();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\mysql\jdbc\jdbc2\optional\SuspendableXAConnection.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */