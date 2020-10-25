/*    */ package org.bukkit.material;
/*    */ 
/*    */ import org.bukkit.DyeColor;
/*    */ import org.bukkit.Material;
/*    */ 
/*    */ 
/*    */ public class Dye
/*    */   extends MaterialData
/*    */   implements Colorable
/*    */ {
/* 11 */   public Dye() { super(Material.INK_SACK); }
/*    */ 
/*    */ 
/*    */   
/* 15 */   public Dye(int type) { super(type); }
/*    */ 
/*    */ 
/*    */   
/* 19 */   public Dye(Material type) { super(type); }
/*    */ 
/*    */ 
/*    */   
/* 23 */   public Dye(int type, byte data) { super(type, data); }
/*    */ 
/*    */ 
/*    */   
/* 27 */   public Dye(Material type, byte data) { super(type, data); }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 36 */   public DyeColor getColor() { return DyeColor.getByData((byte)(15 - getData())); }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 45 */   public void setColor(DyeColor color) { setData((byte)(15 - color.getData())); }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 50 */   public String toString() { return getColor() + " DYE(" + getData() + ")"; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\material\Dye.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */