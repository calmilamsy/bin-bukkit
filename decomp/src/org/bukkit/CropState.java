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
/*    */ public static enum CropState
/*    */ {
/* 15 */   SEEDED(false),
/*    */ 
/*    */ 
/*    */   
/* 19 */   GERMINATED(true),
/*    */ 
/*    */ 
/*    */   
/* 23 */   VERY_SMALL(2),
/*    */ 
/*    */ 
/*    */   
/* 27 */   SMALL(3),
/*    */ 
/*    */ 
/*    */   
/* 31 */   MEDIUM(4),
/*    */ 
/*    */ 
/*    */   
/* 35 */   TALL(5),
/*    */ 
/*    */ 
/*    */   
/* 39 */   VERY_TALL(6),
/*    */ 
/*    */ 
/*    */   
/* 43 */   RIPE(7);
/*    */   
/*    */   static  {
/* 46 */     states = new HashMap();
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
/* 74 */     for (CropState s : values())
/* 75 */       states.put(Byte.valueOf(s.getData()), s); 
/*    */   }
/*    */   
/*    */   private final byte data;
/*    */   private static final Map<Byte, CropState> states;
/*    */   
/*    */   CropState(byte data) { this.data = data; }
/*    */   
/*    */   public byte getData() { return this.data; }
/*    */   
/*    */   public static CropState getByData(byte data) { return (CropState)states.get(Byte.valueOf(data)); }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\CropState.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */