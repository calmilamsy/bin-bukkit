/*    */ package org.bukkit.block;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ 
/*    */ public static enum PistonMoveReaction {
/*  7 */   MOVE(false),
/*  8 */   BREAK(true),
/*  9 */   BLOCK(2);
/*    */   
/*    */   static  {
/* 12 */     byId = new HashMap();
/*    */     
/* 14 */     for (PistonMoveReaction reaction : values())
/* 15 */       byId.put(Integer.valueOf(reaction.id), reaction); 
/*    */   }
/*    */   private int id;
/*    */   private static Map<Integer, PistonMoveReaction> byId;
/*    */   
/* 20 */   PistonMoveReaction(int id) { this.id = id; }
/*    */ 
/*    */ 
/*    */   
/* 24 */   public int getId() { return this.id; }
/*    */ 
/*    */ 
/*    */   
/* 28 */   public static PistonMoveReaction getById(int id) { return (PistonMoveReaction)byId.get(Integer.valueOf(id)); }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\block\PistonMoveReaction.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */