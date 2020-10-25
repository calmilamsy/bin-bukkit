/*    */ package org.bukkit;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public static enum CoalType
/*    */ {
/* 11 */   COAL(false),
/* 12 */   CHARCOAL(true);
/*    */   
/*    */   static  {
/* 15 */     types = new HashMap();
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
/* 43 */     for (CoalType type : values())
/* 44 */       types.put(Byte.valueOf(type.getData()), type); 
/*    */   }
/*    */   
/*    */   private final byte data;
/*    */   private static final Map<Byte, CoalType> types;
/*    */   
/*    */   CoalType(byte data) { this.data = data; }
/*    */   
/*    */   public byte getData() { return this.data; }
/*    */   
/*    */   public static CoalType getByData(byte data) { return (CoalType)types.get(Byte.valueOf(data)); }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\CoalType.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */