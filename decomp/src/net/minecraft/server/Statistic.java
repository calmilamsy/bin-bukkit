/*    */ package net.minecraft.server;
/*    */ import java.text.DecimalFormat;
/*    */ import java.text.NumberFormat;
/*    */ import java.util.Locale;
/*    */ 
/*    */ public class Statistic {
/*    */   public final int e;
/*    */   public final String f;
/*    */   
/*    */   public Statistic(int paramInt, String paramString, Counter paramCounter) {
/* 11 */     this.g = false;
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 16 */     this.e = paramInt;
/* 17 */     this.f = paramString;
/* 18 */     this.a = paramCounter;
/*    */   }
/*    */   public boolean g; public String h; private final Counter a;
/*    */   
/* 22 */   public Statistic(int paramInt, String paramString) { this(paramInt, paramString, i); }
/*    */ 
/*    */   
/*    */   public Statistic e() {
/* 26 */     this.g = true;
/* 27 */     return this;
/*    */   }
/*    */   
/*    */   public Statistic d() {
/* 31 */     if (StatisticList.a.containsKey(Integer.valueOf(this.e))) {
/* 32 */       throw new RuntimeException("Duplicate stat id: \"" + ((Statistic)StatisticList.a.get(Integer.valueOf(this.e))).f + "\" and \"" + this.f + "\" at id " + this.e);
/*    */     }
/* 34 */     StatisticList.b.add(this);
/* 35 */     StatisticList.a.put(Integer.valueOf(this.e), this);
/*    */     
/* 37 */     this.h = AchievementMap.a(this.e);
/*    */     
/* 39 */     return this;
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
/* 50 */   private static NumberFormat b = NumberFormat.getIntegerInstance(Locale.US);
/* 51 */   public static Counter i = new UnknownCounter();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 57 */   private static DecimalFormat c = new DecimalFormat("########0.00");
/* 58 */   public static Counter j = new TimeCounter();
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
/* 80 */   public static Counter k = new DistancesCounter();
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
/* 97 */   public String toString() { return this.f; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\Statistic.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */