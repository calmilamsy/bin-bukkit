/*    */ package org.bukkit.material;
/*    */ 
/*    */ import org.bukkit.Material;
/*    */ 
/*    */ 
/*    */ public class DetectorRail
/*    */   extends ExtendedRails
/*    */   implements PressureSensor
/*    */ {
/* 10 */   public DetectorRail() { super(Material.DETECTOR_RAIL); }
/*    */ 
/*    */ 
/*    */   
/* 14 */   public DetectorRail(int type) { super(type); }
/*    */ 
/*    */ 
/*    */   
/* 18 */   public DetectorRail(Material type) { super(type); }
/*    */ 
/*    */ 
/*    */   
/* 22 */   public DetectorRail(int type, byte data) { super(type, data); }
/*    */ 
/*    */ 
/*    */   
/* 26 */   public DetectorRail(Material type, byte data) { super(type, data); }
/*    */ 
/*    */ 
/*    */   
/* 30 */   public boolean isPressed() { return ((getData() & 0x8) == 8); }
/*    */ 
/*    */ 
/*    */   
/* 34 */   public void setPressed(boolean isPressed) { setData((byte)(isPressed ? (getData() | 0x8) : (getData() & 0xFFFFFFF7))); }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\material\DetectorRail.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */