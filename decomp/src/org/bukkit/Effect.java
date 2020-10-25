/*    */ package org.bukkit;
/*    */ 
/*    */ 
/*    */ 
/*    */ public static enum Effect
/*    */ {
/*  7 */   BOW_FIRE('Ϫ'),
/*  8 */   CLICK1('ϩ'),
/*  9 */   CLICK2('Ϩ'),
/* 10 */   DOOR_TOGGLE('ϫ'),
/* 11 */   EXTINGUISH('Ϭ'),
/* 12 */   RECORD_PLAY('ϭ'),
/* 13 */   SMOKE('ߐ'),
/* 14 */   STEP_SOUND('ߑ');
/*    */   
/*    */   private final int id;
/*    */ 
/*    */   
/* 19 */   Effect(int id) { this.id = id; }
/*    */ 
/*    */ 
/*    */   
/* 23 */   public int getId() { return this.id; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\Effect.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */