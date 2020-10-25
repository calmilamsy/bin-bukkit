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
/*    */ public static enum GrassSpecies
/*    */ {
/* 14 */   DEAD(false),
/*    */ 
/*    */ 
/*    */   
/* 18 */   NORMAL(true),
/*    */ 
/*    */ 
/*    */   
/* 22 */   FERN_LIKE(2);
/*    */   
/*    */   static  {
/* 25 */     species = new HashMap();
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
/* 53 */     for (GrassSpecies s : values())
/* 54 */       species.put(Byte.valueOf(s.getData()), s); 
/*    */   }
/*    */   
/*    */   private final byte data;
/*    */   private static final Map<Byte, GrassSpecies> species;
/*    */   
/*    */   GrassSpecies(byte data) { this.data = data; }
/*    */   
/*    */   public byte getData() { return this.data; }
/*    */   
/*    */   public static GrassSpecies getByData(byte data) { return (GrassSpecies)species.get(Byte.valueOf(data)); }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\GrassSpecies.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */