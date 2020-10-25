/*    */ package net.minecraft.server;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.util.Properties;
/*    */ 
/*    */ public class StatisticStorage
/*    */ {
/*  8 */   private static StatisticStorage a = new StatisticStorage();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 15 */   private Properties b = new Properties(); private StatisticStorage() {
/*    */     try {
/* 17 */       this.b.load(StatisticStorage.class.getResourceAsStream("/lang/en_US.lang"));
/* 18 */       this.b.load(StatisticStorage.class.getResourceAsStream("/lang/stats_US.lang"));
/* 19 */     } catch (IOException iOException) {
/* 20 */       iOException.printStackTrace();
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/* 26 */   public static StatisticStorage a() { return a; }
/*    */ 
/*    */ 
/*    */   
/* 30 */   public String a(String paramString) { return this.b.getProperty(paramString, paramString); }
/*    */ 
/*    */   
/*    */   public String a(String paramString, Object... paramVarArgs) {
/* 34 */     String str = this.b.getProperty(paramString, paramString);
/* 35 */     return String.format(str, paramVarArgs);
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\StatisticStorage.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */