/*    */ package com.avaje.ebeaninternal.server.core;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ReferenceOptions
/*    */ {
/*    */   private final boolean readOnly;
/*    */   private final boolean useCache;
/*    */   private final String warmingQuery;
/*    */   
/*    */   public ReferenceOptions(boolean useCache, boolean readOnly, String warmingQuery) {
/* 18 */     this.useCache = useCache;
/* 19 */     this.readOnly = readOnly;
/* 20 */     this.warmingQuery = warmingQuery;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 27 */   public boolean isUseCache() { return this.useCache; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 34 */   public boolean isReadOnly() { return this.readOnly; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 41 */   public String getWarmingQuery() { return this.warmingQuery; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\core\ReferenceOptions.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */