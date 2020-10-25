/*    */ package org.bukkit.material;
/*    */ 
/*    */ import org.bukkit.Material;
/*    */ import org.bukkit.block.BlockFace;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Furnace
/*    */   extends FurnaceAndDispenser
/*    */ {
/* 12 */   public Furnace() { super(Material.FURNACE); }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Furnace(BlockFace direction) {
/* 20 */     this();
/* 21 */     setFacingDirection(direction);
/*    */   }
/*    */ 
/*    */   
/* 25 */   public Furnace(int type) { super(type); }
/*    */ 
/*    */ 
/*    */   
/* 29 */   public Furnace(Material type) { super(type); }
/*    */ 
/*    */ 
/*    */   
/* 33 */   public Furnace(int type, byte data) { super(type, data); }
/*    */ 
/*    */ 
/*    */   
/* 37 */   public Furnace(Material type, byte data) { super(type, data); }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\material\Furnace.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */