/*    */ package org.bukkit.material;
/*    */ 
/*    */ import org.bukkit.Material;
/*    */ import org.bukkit.block.BlockFace;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Torch
/*    */   extends SimpleAttachableMaterialData
/*    */ {
/* 11 */   public Torch() { super(Material.TORCH); }
/*    */ 
/*    */ 
/*    */   
/* 15 */   public Torch(int type) { super(type); }
/*    */ 
/*    */ 
/*    */   
/* 19 */   public Torch(Material type) { super(type); }
/*    */ 
/*    */ 
/*    */   
/* 23 */   public Torch(int type, byte data) { super(type, data); }
/*    */ 
/*    */ 
/*    */   
/* 27 */   public Torch(Material type, byte data) { super(type, data); }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public BlockFace getAttachedFace() {
/* 36 */     byte data = getData();
/*    */     
/* 38 */     switch (data) {
/*    */       case 1:
/* 40 */         return BlockFace.NORTH;
/*    */       
/*    */       case 2:
/* 43 */         return BlockFace.SOUTH;
/*    */       
/*    */       case 3:
/* 46 */         return BlockFace.EAST;
/*    */       
/*    */       case 4:
/* 49 */         return BlockFace.WEST;
/*    */       
/*    */       case 5:
/* 52 */         return BlockFace.DOWN;
/*    */     } 
/*    */     
/* 55 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setFacingDirection(BlockFace face) {
/*    */     byte data;
/* 61 */     switch (face) {
/*    */       case SOUTH:
/* 63 */         data = 1;
/*    */         break;
/*    */       
/*    */       case NORTH:
/* 67 */         data = 2;
/*    */         break;
/*    */       
/*    */       case WEST:
/* 71 */         data = 3;
/*    */         break;
/*    */       
/*    */       case EAST:
/* 75 */         data = 4;
/*    */         break;
/*    */ 
/*    */       
/*    */       default:
/* 80 */         data = 5;
/*    */         break;
/*    */     } 
/* 83 */     setData(data);
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\material\Torch.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */