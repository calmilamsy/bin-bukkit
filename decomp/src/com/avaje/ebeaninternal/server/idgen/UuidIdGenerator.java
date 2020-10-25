/*    */ package com.avaje.ebeaninternal.server.idgen;
/*    */ 
/*    */ import com.avaje.ebean.Transaction;
/*    */ import com.avaje.ebean.config.dbplatform.IdGenerator;
/*    */ import java.util.UUID;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class UuidIdGenerator
/*    */   implements IdGenerator
/*    */ {
/* 17 */   public Object nextId(Transaction t) { return UUID.randomUUID(); }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 24 */   public String getName() { return "uuid"; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 31 */   public boolean isDbSequence() { return false; }
/*    */   
/*    */   public void preAllocateIds(int allocateSize) {}
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\idgen\UuidIdGenerator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */