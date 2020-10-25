/*    */ package org.bukkit.material;
/*    */ 
/*    */ import org.bukkit.CoalType;
/*    */ import org.bukkit.Material;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Coal
/*    */   extends MaterialData
/*    */ {
/* 11 */   public Coal() { super(Material.COAL); }
/*    */ 
/*    */   
/*    */   public Coal(CoalType type) {
/* 15 */     this();
/* 16 */     setType(type);
/*    */   }
/*    */ 
/*    */   
/* 20 */   public Coal(int type) { super(type); }
/*    */ 
/*    */ 
/*    */   
/* 24 */   public Coal(Material type) { super(type); }
/*    */ 
/*    */ 
/*    */   
/* 28 */   public Coal(int type, byte data) { super(type, data); }
/*    */ 
/*    */ 
/*    */   
/* 32 */   public Coal(Material type, byte data) { super(type, data); }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 41 */   public CoalType getType() { return CoalType.getByData(getData()); }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 50 */   public void setType(CoalType type) { setData(type.getData()); }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 55 */   public String toString() { return getType() + " " + super.toString(); }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\material\Coal.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */