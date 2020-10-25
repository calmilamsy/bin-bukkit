/*     */ package org.bukkit.material;
/*     */ 
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.block.BlockFace;
/*     */ 
/*     */ 
/*     */ public class Door
/*     */   extends MaterialData
/*     */   implements Directional
/*     */ {
/*  11 */   public Door() { super(Material.WOODEN_DOOR); }
/*     */ 
/*     */ 
/*     */   
/*  15 */   public Door(int type) { super(type); }
/*     */ 
/*     */ 
/*     */   
/*  19 */   public Door(Material type) { super(type); }
/*     */ 
/*     */ 
/*     */   
/*  23 */   public Door(int type, byte data) { super(type, data); }
/*     */ 
/*     */ 
/*     */   
/*  27 */   public Door(Material type, byte data) { super(type, data); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  35 */   public boolean isOpen() { return ((getData() & 0x4) == 4); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  43 */   public void setOpen(boolean isOpen) { setData((byte)(isOpen ? (getData() | 0x4) : (getData() & 0xFFFFFFFB))); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  50 */   public boolean isTopHalf() { return ((getData() & 0x8) == 8); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  58 */   public void setTopHalf(boolean isTopHalf) { setData((byte)(isTopHalf ? (getData() | 0x8) : (getData() & 0xFFFFFFF7))); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BlockFace getHingeCorner() {
/*  65 */     byte d = getData();
/*     */     
/*  67 */     if ((d & 0x3) == 3)
/*  68 */       return BlockFace.NORTH_WEST; 
/*  69 */     if ((d & true) == 1)
/*  70 */       return BlockFace.SOUTH_EAST; 
/*  71 */     if ((d & 0x2) == 2) {
/*  72 */       return BlockFace.SOUTH_WEST;
/*     */     }
/*     */     
/*  75 */     return BlockFace.NORTH_EAST;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*  80 */   public String toString() { return (isTopHalf() ? "TOP" : "BOTTOM") + " half of " + (isOpen() ? "an OPEN " : "a CLOSED ") + super.toString() + " with hinges " + getHingeCorner(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setFacingDirection(BlockFace face) {
/*  88 */     byte data = (byte)(getData() & 0x12);
/*  89 */     switch (face) {
/*     */       case EAST:
/*  91 */         data = (byte)(data | true);
/*     */         break;
/*     */       
/*     */       case SOUTH:
/*  95 */         data = (byte)(data | 0x2);
/*     */         break;
/*     */       
/*     */       case WEST:
/*  99 */         data = (byte)(data | 0x3);
/*     */         break;
/*     */     } 
/* 102 */     setData(data);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BlockFace getFacing() {
/* 110 */     byte data = (byte)(getData() & 0x3);
/* 111 */     switch (data) {
/*     */       case 0:
/* 113 */         return BlockFace.NORTH;
/*     */       
/*     */       case 1:
/* 116 */         return BlockFace.EAST;
/*     */       
/*     */       case 2:
/* 119 */         return BlockFace.SOUTH;
/*     */       
/*     */       case 3:
/* 122 */         return BlockFace.WEST;
/*     */     } 
/* 124 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\material\Door.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */