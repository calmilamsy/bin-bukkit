/*    */ package org.bukkit.material;
/*    */ 
/*    */ import org.bukkit.Material;
/*    */ 
/*    */ 
/*    */ public class RedstoneTorch
/*    */   extends Torch
/*    */   implements Redstone
/*    */ {
/* 10 */   public RedstoneTorch() { super(Material.REDSTONE_TORCH_ON); }
/*    */ 
/*    */ 
/*    */   
/* 14 */   public RedstoneTorch(int type) { super(type); }
/*    */ 
/*    */ 
/*    */   
/* 18 */   public RedstoneTorch(Material type) { super(type); }
/*    */ 
/*    */ 
/*    */   
/* 22 */   public RedstoneTorch(int type, byte data) { super(type, data); }
/*    */ 
/*    */ 
/*    */   
/* 26 */   public RedstoneTorch(Material type, byte data) { super(type, data); }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 36 */   public boolean isPowered() { return (getItemType() == Material.REDSTONE_TORCH_ON); }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 41 */   public String toString() { return super.toString() + " " + (isPowered() ? "" : "NOT ") + "POWERED"; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\material\RedstoneTorch.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */