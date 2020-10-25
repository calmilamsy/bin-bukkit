/*    */ package org.bukkit.material;
/*    */ 
/*    */ import org.bukkit.Material;
/*    */ 
/*    */ 
/*    */ public class PoweredRail
/*    */   extends ExtendedRails
/*    */   implements Redstone
/*    */ {
/* 10 */   public PoweredRail() { super(Material.POWERED_RAIL); }
/*    */ 
/*    */ 
/*    */   
/* 14 */   public PoweredRail(int type) { super(type); }
/*    */ 
/*    */ 
/*    */   
/* 18 */   public PoweredRail(Material type) { super(type); }
/*    */ 
/*    */ 
/*    */   
/* 22 */   public PoweredRail(int type, byte data) { super(type, data); }
/*    */ 
/*    */ 
/*    */   
/* 26 */   public PoweredRail(Material type, byte data) { super(type, data); }
/*    */ 
/*    */ 
/*    */   
/* 30 */   public boolean isPowered() { return ((getData() & 0x8) == 8); }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 38 */   public void setPowered(boolean isPowered) { setData((byte)(isPowered ? (getData() | 0x8) : (getData() & 0xFFFFFFF7))); }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\material\PoweredRail.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */