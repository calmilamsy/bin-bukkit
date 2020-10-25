/*    */ package com.avaje.ebeaninternal.server.lucene;
/*    */ 
/*    */ import javax.persistence.PersistenceException;
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
/*    */ public class PersistenceLuceneException
/*    */   extends PersistenceException
/*    */ {
/*    */   private static final long serialVersionUID = 1495423311592521260L;
/*    */   
/* 29 */   public PersistenceLuceneException(Throwable e) { super(e); }
/*    */ 
/*    */ 
/*    */   
/* 33 */   public PersistenceLuceneException(String msg, Throwable e) { super(msg, e); }
/*    */ 
/*    */ 
/*    */   
/* 37 */   public PersistenceLuceneException(String msg) { super(msg); }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\lucene\PersistenceLuceneException.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */