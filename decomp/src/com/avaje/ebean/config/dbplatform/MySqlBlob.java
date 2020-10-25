/*    */ package com.avaje.ebean.config.dbplatform;
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
/*    */ 
/*    */ public class MySqlBlob
/*    */   extends DbType
/*    */ {
/*    */   private static final int POWER_2_16 = 65536;
/*    */   private static final int POWER_2_24 = 16777216;
/*    */   
/* 36 */   public MySqlBlob() { super("blob"); }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String renderType(int deployLength, int deployScale) {
/* 42 */     if (deployLength >= 16777216) {
/* 43 */       return "longblob";
/*    */     }
/* 45 */     if (deployLength >= 65536) {
/* 46 */       return "mediumblob";
/*    */     }
/* 48 */     if (deployLength < 1)
/*    */     {
/* 50 */       return "longblob";
/*    */     }
/* 52 */     return "blob";
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebean\config\dbplatform\MySqlBlob.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */