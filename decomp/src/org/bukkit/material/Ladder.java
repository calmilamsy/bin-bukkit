/*    */ package org.bukkit.material;
/*    */ 
/*    */ import org.bukkit.Material;
/*    */ import org.bukkit.block.BlockFace;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Ladder
/*    */   extends SimpleAttachableMaterialData
/*    */ {
/* 11 */   public Ladder() { super(Material.LADDER); }
/*    */ 
/*    */ 
/*    */   
/* 15 */   public Ladder(int type) { super(type); }
/*    */ 
/*    */ 
/*    */   
/* 19 */   public Ladder(Material type) { super(type); }
/*    */ 
/*    */ 
/*    */   
/* 23 */   public Ladder(int type, byte data) { super(type, data); }
/*    */ 
/*    */ 
/*    */   
/* 27 */   public Ladder(Material type, byte data) { super(type, data); }
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
/*    */       case 2:
/* 40 */         return BlockFace.WEST;
/*    */       
/*    */       case 3:
/* 43 */         return BlockFace.EAST;
/*    */       
/*    */       case 4:
/* 46 */         return BlockFace.SOUTH;
/*    */       
/*    */       case 5:
/* 49 */         return BlockFace.NORTH;
/*    */     } 
/*    */     
/* 52 */     return null;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setFacingDirection(BlockFace face) {
/* 59 */     byte data = 0;
/*    */     
/* 61 */     switch (face) {
/*    */       case WEST:
/* 63 */         data = 2;
/*    */         break;
/*    */       
/*    */       case EAST:
/* 67 */         data = 3;
/*    */         break;
/*    */       
/*    */       case SOUTH:
/* 71 */         data = 4;
/*    */         break;
/*    */       
/*    */       case NORTH:
/* 75 */         data = 5;
/*    */         break;
/*    */     } 
/*    */     
/* 79 */     setData(data);
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\material\Ladder.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */