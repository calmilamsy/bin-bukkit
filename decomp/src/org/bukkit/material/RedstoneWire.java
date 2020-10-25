/*    */ package org.bukkit.material;
/*    */ 
/*    */ import org.bukkit.Material;
/*    */ 
/*    */ 
/*    */ public class RedstoneWire
/*    */   extends MaterialData
/*    */   implements Redstone
/*    */ {
/* 10 */   public RedstoneWire() { super(Material.REDSTONE_WIRE); }
/*    */ 
/*    */ 
/*    */   
/* 14 */   public RedstoneWire(int type) { super(type); }
/*    */ 
/*    */ 
/*    */   
/* 18 */   public RedstoneWire(Material type) { super(type); }
/*    */ 
/*    */ 
/*    */   
/* 22 */   public RedstoneWire(int type, byte data) { super(type, data); }
/*    */ 
/*    */ 
/*    */   
/* 26 */   public RedstoneWire(Material type, byte data) { super(type, data); }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 36 */   public boolean isPowered() { return (getData() > 0); }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 41 */   public String toString() { return super.toString() + " " + (isPowered() ? "" : "NOT ") + "POWERED"; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\material\RedstoneWire.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */