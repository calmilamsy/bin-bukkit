/*     */ package com.avaje.ebeaninternal.server.jdbc;
/*     */ 
/*     */ import com.avaje.ebean.config.PstmtDelegate;
/*     */ import com.avaje.ebeaninternal.api.ClassUtil;
/*     */ import com.avaje.ebeaninternal.server.core.PstmtBatch;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.lang.reflect.Method;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.SQLException;
/*     */ import javax.persistence.OptimisticLockException;
/*     */ import javax.persistence.PersistenceException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class OraclePstmtBatch
/*     */   implements PstmtBatch
/*     */ {
/*     */   private final PstmtDelegate pstmtDelegate;
/*     */   private static final Method METHOD_SET_EXECUTE_BATCH;
/*     */   private static final Method METHOD_SEND_BATCH;
/*     */   private static final RuntimeException INIT_EXCEPTION;
/*     */   
/*     */   static  {
/*  61 */     initException = null;
/*  62 */     Method mSetExecuteBatch = null;
/*  63 */     Method mSendBatch = null;
/*     */     
/*     */     try {
/*  66 */       Class<?> ops = ClassUtil.forName("oracle.jdbc.OraclePreparedStatement");
/*     */       
/*  68 */       mSetExecuteBatch = ops.getMethod("setExecuteBatch", new Class[] { int.class });
/*  69 */       mSendBatch = ops.getMethod("sendBatch", new Class[0]);
/*     */     }
/*  71 */     catch (NoSuchMethodException e) {
/*  72 */       initException = new RuntimeException("problems initializing oracle reflection", e);
/*  73 */       initException.fillInStackTrace();
/*     */     }
/*  75 */     catch (ClassNotFoundException e) {
/*  76 */       initException = new RuntimeException("problems initializing oracle reflection", e);
/*  77 */       initException.fillInStackTrace();
/*     */     } 
/*     */     
/*  80 */     INIT_EXCEPTION = initException;
/*  81 */     METHOD_SET_EXECUTE_BATCH = mSetExecuteBatch;
/*  82 */     METHOD_SEND_BATCH = mSendBatch;
/*     */   }
/*     */ 
/*     */   
/*  86 */   public OraclePstmtBatch(PstmtDelegate pstmtDelegate) { this.pstmtDelegate = pstmtDelegate; }
/*     */ 
/*     */   
/*     */   public void setBatchSize(PreparedStatement pstmt, int batchSize) {
/*  90 */     if (INIT_EXCEPTION != null) {
/*  91 */       throw INIT_EXCEPTION;
/*     */     }
/*     */ 
/*     */     
/*     */     try {
/*  96 */       METHOD_SET_EXECUTE_BATCH.invoke(this.pstmtDelegate.unwrap(pstmt), new Object[] { Integer.valueOf(batchSize + 1) });
/*  97 */     } catch (IllegalAccessException e) {
/*  98 */       String m = "Error with Oracle setExecuteBatch " + (batchSize + 1);
/*  99 */       throw new RuntimeException(m, e);
/* 100 */     } catch (InvocationTargetException e) {
/* 101 */       String m = "Error with Oracle setExecuteBatch " + (batchSize + 1);
/* 102 */       throw new RuntimeException(m, e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 110 */   public void addBatch(PreparedStatement pstmt) throws SQLException { pstmt.executeUpdate(); }
/*     */ 
/*     */   
/*     */   public int executeBatch(PreparedStatement pstmt, int expectedRows, String sql, boolean occCheck) throws SQLException {
/*     */     int rows;
/* 115 */     if (INIT_EXCEPTION != null) {
/* 116 */       throw INIT_EXCEPTION;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 122 */       rows = ((Integer)METHOD_SEND_BATCH.invoke(this.pstmtDelegate.unwrap(pstmt), new Object[0])).intValue();
/*     */     }
/* 124 */     catch (IllegalAccessException e) {
/* 125 */       String msg = "Error invoking Oracle sendBatch method via reflection";
/* 126 */       throw new PersistenceException(msg, e);
/*     */     }
/* 128 */     catch (InvocationTargetException e) {
/* 129 */       String msg = "Error invoking Oracle sendBatch method via reflection";
/* 130 */       throw new PersistenceException(msg, e);
/*     */     } 
/* 132 */     if (occCheck && rows != expectedRows) {
/* 133 */       String msg = "Batch execution expected " + expectedRows + " but got " + rows + "  sql:" + sql;
/* 134 */       throw new OptimisticLockException(msg);
/*     */     } 
/*     */     
/* 137 */     return rows;
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\jdbc\OraclePstmtBatch.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */