/*    */ package org.bukkit;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public static enum TreeSpecies
/*    */ {
/* 15 */   GENERIC(false),
/*    */ 
/*    */ 
/*    */   
/* 19 */   REDWOOD(true),
/*    */ 
/*    */ 
/*    */   
/* 23 */   BIRCH(2);
/*    */   
/*    */   static  {
/* 26 */     species = new HashMap();
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
/* 54 */     for (TreeSpecies s : values())
/* 55 */       species.put(Byte.valueOf(s.getData()), s); 
/*    */   }
/*    */   
/*    */   private final byte data;
/*    */   private static final Map<Byte, TreeSpecies> species;
/*    */   
/*    */   TreeSpecies(byte data) { this.data = data; }
/*    */   
/*    */   public byte getData() { return this.data; }
/*    */   
/*    */   public static TreeSpecies getByData(byte data) { return (TreeSpecies)species.get(Byte.valueOf(data)); }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\TreeSpecies.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */