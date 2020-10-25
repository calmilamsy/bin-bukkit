/*    */ package org.bukkit.material;
/*    */ 
/*    */ import org.bukkit.Material;
/*    */ import org.bukkit.block.BlockFace;
/*    */ 
/*    */ 
/*    */ public class FurnaceAndDispenser
/*    */   extends MaterialData
/*    */   implements Directional
/*    */ {
/* 11 */   public FurnaceAndDispenser(int type) { super(type); }
/*    */ 
/*    */ 
/*    */   
/* 15 */   public FurnaceAndDispenser(Material type) { super(type); }
/*    */ 
/*    */ 
/*    */   
/* 19 */   public FurnaceAndDispenser(int type, byte data) { super(type, data); }
/*    */ 
/*    */ 
/*    */   
/* 23 */   public FurnaceAndDispenser(Material type, byte data) { super(type, data); }
/*    */ 
/*    */ 
/*    */   
/*    */   public void setFacingDirection(BlockFace face) {
/*    */     byte data;
/* 29 */     switch (face) {
/*    */       case EAST:
/* 31 */         data = 2;
/*    */         break;
/*    */       
/*    */       case WEST:
/* 35 */         data = 3;
/*    */         break;
/*    */       
/*    */       case NORTH:
/* 39 */         data = 4;
/*    */         break;
/*    */ 
/*    */       
/*    */       default:
/* 44 */         data = 5;
/*    */         break;
/*    */     } 
/* 47 */     setData(data);
/*    */   }
/*    */   
/*    */   public BlockFace getFacing() {
/* 51 */     byte data = getData();
/*    */     
/* 53 */     switch (data) {
/*    */       case 2:
/* 55 */         return BlockFace.EAST;
/*    */       
/*    */       case 3:
/* 58 */         return BlockFace.WEST;
/*    */       
/*    */       case 4:
/* 61 */         return BlockFace.NORTH;
/*    */     } 
/*    */ 
/*    */     
/* 65 */     return BlockFace.SOUTH;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 71 */   public String toString() { return super.toString() + " facing " + getFacing(); }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\material\FurnaceAndDispenser.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */