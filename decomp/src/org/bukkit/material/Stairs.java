/*    */ package org.bukkit.material;
/*    */ 
/*    */ import org.bukkit.Material;
/*    */ import org.bukkit.block.BlockFace;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Stairs
/*    */   extends MaterialData
/*    */   implements Directional
/*    */ {
/* 12 */   public Stairs(int type) { super(type); }
/*    */ 
/*    */ 
/*    */   
/* 16 */   public Stairs(Material type) { super(type); }
/*    */ 
/*    */ 
/*    */   
/* 20 */   public Stairs(int type, byte data) { super(type, data); }
/*    */ 
/*    */ 
/*    */   
/* 24 */   public Stairs(Material type, byte data) { super(type, data); }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public BlockFace getAscendingDirection() {
/* 31 */     byte data = getData();
/*    */     
/* 33 */     switch (data) {
/*    */       
/*    */       default:
/* 36 */         return BlockFace.SOUTH;
/*    */       
/*    */       case 1:
/* 39 */         return BlockFace.NORTH;
/*    */       
/*    */       case 2:
/* 42 */         return BlockFace.WEST;
/*    */       case 3:
/*    */         break;
/* 45 */     }  return BlockFace.EAST;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 53 */   public BlockFace getDescendingDirection() { return getAscendingDirection().getOppositeFace(); }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setFacingDirection(BlockFace face) {
/*    */     byte data;
/* 62 */     switch (face) {
/*    */       
/*    */       default:
/* 65 */         data = 0;
/*    */         break;
/*    */       
/*    */       case SOUTH:
/* 69 */         data = 1;
/*    */         break;
/*    */       
/*    */       case EAST:
/* 73 */         data = 2;
/*    */         break;
/*    */       
/*    */       case WEST:
/* 77 */         data = 3;
/*    */         break;
/*    */     } 
/*    */     
/* 81 */     setData(data);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 88 */   public BlockFace getFacing() { return getDescendingDirection(); }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 93 */   public String toString() { return super.toString() + " facing " + getFacing(); }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\material\Stairs.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */