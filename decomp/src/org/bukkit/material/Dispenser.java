/*    */ package org.bukkit.material;
/*    */ 
/*    */ import org.bukkit.Material;
/*    */ import org.bukkit.block.BlockFace;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Dispenser
/*    */   extends FurnaceAndDispenser
/*    */ {
/* 12 */   public Dispenser() { super(Material.DISPENSER); }
/*    */ 
/*    */   
/*    */   public Dispenser(BlockFace direction) {
/* 16 */     this();
/* 17 */     setFacingDirection(direction);
/*    */   }
/*    */ 
/*    */   
/* 21 */   public Dispenser(int type) { super(type); }
/*    */ 
/*    */ 
/*    */   
/* 25 */   public Dispenser(Material type) { super(type); }
/*    */ 
/*    */ 
/*    */   
/* 29 */   public Dispenser(int type, byte data) { super(type, data); }
/*    */ 
/*    */ 
/*    */   
/* 33 */   public Dispenser(Material type, byte data) { super(type, data); }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\material\Dispenser.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */