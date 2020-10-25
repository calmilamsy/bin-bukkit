/*    */ package org.bukkit;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ 
/*    */ public static enum Achievement
/*    */ {
/* 10 */   OPEN_INVENTORY(false),
/* 11 */   MINE_WOOD(true),
/* 12 */   BUILD_WORKBENCH(2),
/* 13 */   BUILD_PICKAXE(3),
/* 14 */   BUILD_FURNACE(4),
/* 15 */   ACQUIRE_IRON(5),
/* 16 */   BUILD_HOE(6),
/* 17 */   MAKE_BREAD(7),
/* 18 */   BAKE_CAKE(8),
/* 19 */   BUILD_BETTER_PICKAXE(9),
/* 20 */   COOK_FISH(10),
/* 21 */   ON_A_RAIL(11),
/* 22 */   BUILD_SWORD(12),
/* 23 */   KILL_ENEMY(13),
/* 24 */   KILL_COW(14),
/* 25 */   FLY_PIG(15);
/*    */   public static final int STATISTIC_OFFSET = 5242880;
/*    */   private static final Map<Integer, Achievement> achievements;
/*    */   private final int id;
/*    */   
/*    */   static  {
/* 31 */     achievements = new HashMap();
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
/* 62 */     for (Achievement ach : values())
/* 63 */       achievements.put(Integer.valueOf(ach.getId()), ach); 
/*    */   }
/*    */   
/*    */   Achievement(int id) { this.id = 5242880 + id; }
/*    */   
/*    */   public int getId() { return this.id; }
/*    */   
/*    */   public static Achievement getAchievement(int id) { return (Achievement)achievements.get(Integer.valueOf(id)); }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\Achievement.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */