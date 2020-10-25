/*    */ package org.bukkit.material;
/*    */ 
/*    */ import org.bukkit.Material;
/*    */ 
/*    */ 
/*    */ public class PressurePlate
/*    */   extends MaterialData
/*    */   implements PressureSensor
/*    */ {
/* 10 */   public PressurePlate() { super(Material.WOOD_PLATE); }
/*    */ 
/*    */ 
/*    */   
/* 14 */   public PressurePlate(int type) { super(type); }
/*    */ 
/*    */ 
/*    */   
/* 18 */   public PressurePlate(Material type) { super(type); }
/*    */ 
/*    */ 
/*    */   
/* 22 */   public PressurePlate(int type, byte data) { super(type, data); }
/*    */ 
/*    */ 
/*    */   
/* 26 */   public PressurePlate(Material type, byte data) { super(type, data); }
/*    */ 
/*    */ 
/*    */   
/* 30 */   public boolean isPressed() { return (getData() == 1); }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 35 */   public String toString() { return super.toString() + (isPressed() ? " PRESSED" : ""); }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\material\PressurePlate.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */