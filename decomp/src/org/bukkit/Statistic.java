/*    */ package org.bukkit;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ 
/*    */ public static enum Statistic
/*    */ {
/* 10 */   DAMAGE_DEALT('ߤ'),
/* 11 */   DAMAGE_TAKEN('ߥ'),
/* 12 */   DEATHS('ߦ'),
/* 13 */   MOB_KILLS('ߧ'),
/* 14 */   PLAYER_KILLS('ߨ'),
/* 15 */   FISH_CAUGHT('ߩ'),
/* 16 */   MINE_BLOCK(16777216, true),
/* 17 */   USE_ITEM(6908288, false),
/* 18 */   BREAK_ITEM(16973824, true);
/*    */   static  {
/* 20 */     statistics = new HashMap();
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
/*    */ 
/*    */     
/* 79 */     for (Statistic stat : values())
/* 80 */       statistics.put(Integer.valueOf(stat.getId()), stat); 
/*    */   }
/*    */   
/*    */   private static final Map<Integer, Statistic> statistics;
/*    */   private final int id;
/*    */   private final boolean isSubstat;
/*    */   private final boolean isBlock;
/*    */   
/*    */   Statistic(int id, boolean isSubstat, boolean isBlock) {
/*    */     this.id = id;
/*    */     this.isSubstat = isSubstat;
/*    */     this.isBlock = isBlock;
/*    */   }
/*    */   
/*    */   public int getId() { return this.id; }
/*    */   
/*    */   public boolean isSubstatistic() { return this.isSubstat; }
/*    */   
/*    */   public boolean isBlock() { return (this.isSubstat && this.isBlock); }
/*    */   
/*    */   public static Statistic getStatistic(int id) { return (Statistic)statistics.get(Integer.valueOf(id)); }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\Statistic.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */