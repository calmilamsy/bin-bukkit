/*    */ package org.bukkit.material;
/*    */ 
/*    */ import org.bukkit.DyeColor;
/*    */ import org.bukkit.Material;
/*    */ 
/*    */ 
/*    */ public class Wool
/*    */   extends MaterialData
/*    */   implements Colorable
/*    */ {
/* 11 */   public Wool() { super(Material.WOOL); }
/*    */ 
/*    */   
/*    */   public Wool(DyeColor color) {
/* 15 */     this();
/* 16 */     setColor(color);
/*    */   }
/*    */ 
/*    */   
/* 20 */   public Wool(int type) { super(type); }
/*    */ 
/*    */ 
/*    */   
/* 24 */   public Wool(Material type) { super(type); }
/*    */ 
/*    */ 
/*    */   
/* 28 */   public Wool(int type, byte data) { super(type, data); }
/*    */ 
/*    */ 
/*    */   
/* 32 */   public Wool(Material type, byte data) { super(type, data); }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 41 */   public DyeColor getColor() { return DyeColor.getByData(getData()); }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 50 */   public void setColor(DyeColor color) { setData(color.getData()); }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 55 */   public String toString() { return getColor() + " " + super.toString(); }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\material\Wool.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */