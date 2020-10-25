/*    */ package net.minecraft.server;
/*    */ 
/*    */ public class CounterStatistic
/*    */   extends Statistic
/*    */ {
/*  6 */   public CounterStatistic(int paramInt, String paramString, Counter paramCounter) { super(paramInt, paramString, paramCounter); }
/*    */ 
/*    */ 
/*    */   
/* 10 */   public CounterStatistic(int paramInt, String paramString) { super(paramInt, paramString); }
/*    */ 
/*    */ 
/*    */   
/*    */   public Statistic d() {
/* 15 */     super.d();
/*    */     
/* 17 */     StatisticList.c.add(this);
/*    */     
/* 19 */     return this;
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\CounterStatistic.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */