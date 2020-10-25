/*    */ package org.bukkit.material;
/*    */ 
/*    */ import org.bukkit.Material;
/*    */ import org.bukkit.block.BlockFace;
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class SimpleAttachableMaterialData
/*    */   extends MaterialData
/*    */   implements Attachable
/*    */ {
/* 12 */   public SimpleAttachableMaterialData(int type) { super(type); }
/*    */ 
/*    */   
/*    */   public SimpleAttachableMaterialData(int type, BlockFace direction) {
/* 16 */     this(type);
/* 17 */     setFacingDirection(direction);
/*    */   }
/*    */   
/*    */   public SimpleAttachableMaterialData(Material type, BlockFace direction) {
/* 21 */     this(type);
/* 22 */     setFacingDirection(direction);
/*    */   }
/*    */ 
/*    */   
/* 26 */   public SimpleAttachableMaterialData(Material type) { super(type); }
/*    */ 
/*    */ 
/*    */   
/* 30 */   public SimpleAttachableMaterialData(int type, byte data) { super(type, data); }
/*    */ 
/*    */ 
/*    */   
/* 34 */   public SimpleAttachableMaterialData(Material type, byte data) { super(type, data); }
/*    */ 
/*    */ 
/*    */   
/* 38 */   public BlockFace getFacing() { return getAttachedFace().getOppositeFace(); }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 43 */   public String toString() { return super.toString() + " facing " + getFacing(); }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\material\SimpleAttachableMaterialData.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */