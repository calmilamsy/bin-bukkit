/*    */ package org.bukkit.material;
/*    */ 
/*    */ import org.bukkit.Material;
/*    */ import org.bukkit.block.BlockFace;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PistonBaseMaterial
/*    */   extends MaterialData
/*    */   implements Directional, Redstone
/*    */ {
/* 12 */   public PistonBaseMaterial(int type) { super(type); }
/*    */ 
/*    */ 
/*    */   
/* 16 */   public PistonBaseMaterial(Material type) { super(type); }
/*    */ 
/*    */ 
/*    */   
/* 20 */   public PistonBaseMaterial(int type, byte data) { super(type, data); }
/*    */ 
/*    */ 
/*    */   
/* 24 */   public PistonBaseMaterial(Material type, byte data) { super(type, data); }
/*    */ 
/*    */   
/*    */   public void setFacingDirection(BlockFace face) {
/* 28 */     byte data = (byte)(getData() & 0x8);
/*    */     
/* 30 */     switch (face) {
/*    */       case UP:
/* 32 */         data = (byte)(data | true);
/*    */         break;
/*    */       case EAST:
/* 35 */         data = (byte)(data | 0x2);
/*    */         break;
/*    */       case WEST:
/* 38 */         data = (byte)(data | 0x3);
/*    */         break;
/*    */       case NORTH:
/* 41 */         data = (byte)(data | 0x4);
/*    */         break;
/*    */       case SOUTH:
/* 44 */         data = (byte)(data | 0x5);
/*    */         break;
/*    */     } 
/* 47 */     setData(data);
/*    */   }
/*    */   
/*    */   public BlockFace getFacing() {
/* 51 */     byte dir = (byte)(getData() & 0x7);
/*    */     
/* 53 */     switch (dir) {
/*    */       case 0:
/* 55 */         return BlockFace.DOWN;
/*    */       case 1:
/* 57 */         return BlockFace.UP;
/*    */       case 2:
/* 59 */         return BlockFace.EAST;
/*    */       case 3:
/* 61 */         return BlockFace.WEST;
/*    */       case 4:
/* 63 */         return BlockFace.NORTH;
/*    */       case 5:
/* 65 */         return BlockFace.SOUTH;
/*    */     } 
/* 67 */     return BlockFace.SELF;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/* 72 */   public boolean isPowered() { return ((getData() & 0x8) == 8); }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 81 */   public void setPowered(boolean powered) { setData((byte)(powered ? (getData() | 0x8) : (getData() & 0xFFFFFFF7))); }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 90 */   public boolean isSticky() { return (getItemType() == Material.PISTON_STICKY_BASE); }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\material\PistonBaseMaterial.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */