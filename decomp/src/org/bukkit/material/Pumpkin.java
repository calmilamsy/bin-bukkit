/*    */ package org.bukkit.material;
/*    */ 
/*    */ import org.bukkit.Material;
/*    */ import org.bukkit.block.BlockFace;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Pumpkin
/*    */   extends MaterialData
/*    */   implements Directional
/*    */ {
/* 12 */   public Pumpkin() { super(Material.PUMPKIN); }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Pumpkin(BlockFace direction) {
/* 20 */     this();
/* 21 */     setFacingDirection(direction);
/*    */   }
/*    */ 
/*    */   
/* 25 */   public Pumpkin(int type) { super(type); }
/*    */ 
/*    */ 
/*    */   
/* 29 */   public Pumpkin(Material type) { super(type); }
/*    */ 
/*    */ 
/*    */   
/* 33 */   public Pumpkin(int type, byte data) { super(type, data); }
/*    */ 
/*    */ 
/*    */   
/* 37 */   public Pumpkin(Material type, byte data) { super(type, data); }
/*    */ 
/*    */ 
/*    */   
/* 41 */   public boolean isLit() { return (getItemType() == Material.JACK_O_LANTERN); }
/*    */ 
/*    */ 
/*    */   
/*    */   public void setFacingDirection(BlockFace face) {
/*    */     byte data;
/* 47 */     switch (face) {
/*    */       case EAST:
/* 49 */         data = 0;
/*    */         break;
/*    */       
/*    */       case SOUTH:
/* 53 */         data = 1;
/*    */         break;
/*    */       
/*    */       case WEST:
/* 57 */         data = 2;
/*    */         break;
/*    */ 
/*    */       
/*    */       default:
/* 62 */         data = 3;
/*    */         break;
/*    */     } 
/* 65 */     setData(data);
/*    */   }
/*    */   
/*    */   public BlockFace getFacing() {
/* 69 */     byte data = getData();
/*    */     
/* 71 */     switch (data) {
/*    */       case 0:
/* 73 */         return BlockFace.EAST;
/*    */       
/*    */       case 1:
/* 76 */         return BlockFace.SOUTH;
/*    */       
/*    */       case 2:
/* 79 */         return BlockFace.WEST;
/*    */     } 
/*    */ 
/*    */     
/* 83 */     return BlockFace.SOUTH;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 89 */   public String toString() { return super.toString() + " facing " + getFacing() + " " + (isLit() ? "" : "NOT ") + "LIT"; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\material\Pumpkin.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */