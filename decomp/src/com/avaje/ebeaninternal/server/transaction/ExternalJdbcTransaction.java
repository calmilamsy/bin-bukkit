/*    */ package com.avaje.ebeaninternal.server.transaction;
/*    */ 
/*    */ import com.avaje.ebean.LogLevel;
/*    */ import java.sql.Connection;
/*    */ import javax.persistence.PersistenceException;
/*    */ import javax.persistence.RollbackException;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ExternalJdbcTransaction
/*    */   extends JdbcTransaction
/*    */ {
/* 32 */   public ExternalJdbcTransaction(Connection connection) { super(null, true, LogLevel.NONE, connection, null); }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 39 */   public ExternalJdbcTransaction(String id, boolean explicit, Connection connection, TransactionManager manager) { super(id, explicit, manager.getTransactionLogLevel(), connection, manager); }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 46 */   public ExternalJdbcTransaction(String id, boolean explicit, LogLevel logLevel, Connection connection, TransactionManager manager) { super(id, explicit, logLevel, connection, manager); }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 57 */   public void commit() throws RollbackException { throw new PersistenceException("This is an external transaction so must be committed externally"); }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 68 */   public void end() throws RollbackException { throw new PersistenceException("This is an external transaction so must be committed externally"); }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 79 */   public void rollback() throws RollbackException { throw new PersistenceException("This is an external transaction so must be rolled back externally"); }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 90 */   public void rollback(Throwable e) throws PersistenceException { throw new PersistenceException("This is an external transaction so must be rolled back externally"); }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\transaction\ExternalJdbcTransaction.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */