/*    */ package com.avaje.ebeaninternal.server.util;
/*    */ 
/*    */ import com.avaje.ebean.Query;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BeanCollectionParams
/*    */ {
/*    */   private final Query.Type manyType;
/*    */   
/* 35 */   public BeanCollectionParams(Query.Type manyType) { this.manyType = manyType; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 42 */   public Query.Type getManyType() { return this.manyType; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\serve\\util\BeanCollectionParams.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */