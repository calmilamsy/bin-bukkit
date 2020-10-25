/*    */ package com.avaje.ebeaninternal.server.lib.sql;
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
/*    */ public class TransactionIsolation
/*    */ {
/*    */   public static int getLevel(String level) {
/* 33 */     level = level.toUpperCase();
/* 34 */     if (level.startsWith("TRANSACTION")) {
/* 35 */       level = level.substring("TRANSACTION".length());
/*    */     }
/* 37 */     level = level.replace("_", "");
/* 38 */     if ("NONE".equalsIgnoreCase(level)) {
/* 39 */       return 0;
/*    */     }
/* 41 */     if ("READCOMMITTED".equalsIgnoreCase(level)) {
/* 42 */       return 2;
/*    */     }
/* 44 */     if ("READUNCOMMITTED".equalsIgnoreCase(level)) {
/* 45 */       return 1;
/*    */     }
/* 47 */     if ("REPEATABLEREAD".equalsIgnoreCase(level)) {
/* 48 */       return 4;
/*    */     }
/* 50 */     if ("SERIALIZABLE".equalsIgnoreCase(level)) {
/* 51 */       return 8;
/*    */     }
/*    */     
/* 54 */     throw new RuntimeException("Transaction Isolaction level [" + level + "] is not known.");
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static String getLevelDescription(int level) {
/* 66 */     switch (level) {
/*    */       case 0:
/* 68 */         return "NONE";
/*    */       case 2:
/* 70 */         return "READ_COMMITTED";
/*    */       case 1:
/* 72 */         return "READ_UNCOMMITTED";
/*    */       case 4:
/* 74 */         return "REPEATABLE_READ";
/*    */       case 8:
/* 76 */         return "SERIALIZABLE";
/*    */       case -1:
/* 78 */         return "NotSet";
/*    */     } 
/* 80 */     throw new RuntimeException("Transaction Isolaction level [" + level + "] is not defined.");
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\lib\sql\TransactionIsolation.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */