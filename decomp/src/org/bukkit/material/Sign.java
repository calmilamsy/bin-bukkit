/*     */ package org.bukkit.material;
/*     */ 
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.block.BlockFace;
/*     */ 
/*     */ 
/*     */ public class Sign
/*     */   extends MaterialData
/*     */   implements Attachable
/*     */ {
/*  11 */   public Sign() { super(Material.SIGN_POST); }
/*     */ 
/*     */ 
/*     */   
/*  15 */   public Sign(int type) { super(type); }
/*     */ 
/*     */ 
/*     */   
/*  19 */   public Sign(Material type) { super(type); }
/*     */ 
/*     */ 
/*     */   
/*  23 */   public Sign(int type, byte data) { super(type, data); }
/*     */ 
/*     */ 
/*     */   
/*  27 */   public Sign(Material type, byte data) { super(type, data); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  37 */   public boolean isWallSign() { return (getItemType() == Material.WALL_SIGN); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BlockFace getAttachedFace() {
/*  46 */     if (isWallSign()) {
/*  47 */       byte data = getData();
/*     */       
/*  49 */       switch (data) {
/*     */         case 2:
/*  51 */           return BlockFace.WEST;
/*     */         
/*     */         case 3:
/*  54 */           return BlockFace.EAST;
/*     */         
/*     */         case 4:
/*  57 */           return BlockFace.SOUTH;
/*     */         
/*     */         case 5:
/*  60 */           return BlockFace.NORTH;
/*     */       } 
/*     */       
/*  63 */       return null;
/*     */     } 
/*  65 */     return BlockFace.DOWN;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BlockFace getFacing() {
/*  75 */     byte data = getData();
/*     */     
/*  77 */     if (!isWallSign()) {
/*  78 */       switch (data) {
/*     */         case 0:
/*  80 */           return BlockFace.WEST;
/*     */         
/*     */         case 1:
/*  83 */           return BlockFace.WEST_NORTH_WEST;
/*     */         
/*     */         case 2:
/*  86 */           return BlockFace.NORTH_WEST;
/*     */         
/*     */         case 3:
/*  89 */           return BlockFace.NORTH_NORTH_WEST;
/*     */         
/*     */         case 4:
/*  92 */           return BlockFace.NORTH;
/*     */         
/*     */         case 5:
/*  95 */           return BlockFace.NORTH_NORTH_EAST;
/*     */         
/*     */         case 6:
/*  98 */           return BlockFace.NORTH_EAST;
/*     */         
/*     */         case 7:
/* 101 */           return BlockFace.EAST_NORTH_EAST;
/*     */         
/*     */         case 8:
/* 104 */           return BlockFace.EAST;
/*     */         
/*     */         case 9:
/* 107 */           return BlockFace.EAST_SOUTH_EAST;
/*     */         
/*     */         case 10:
/* 110 */           return BlockFace.SOUTH_EAST;
/*     */         
/*     */         case 11:
/* 113 */           return BlockFace.SOUTH_SOUTH_EAST;
/*     */         
/*     */         case 12:
/* 116 */           return BlockFace.SOUTH;
/*     */         
/*     */         case 13:
/* 119 */           return BlockFace.SOUTH_SOUTH_WEST;
/*     */         
/*     */         case 14:
/* 122 */           return BlockFace.SOUTH_WEST;
/*     */         
/*     */         case 15:
/* 125 */           return BlockFace.WEST_SOUTH_WEST;
/*     */       } 
/*     */       
/* 128 */       return null;
/*     */     } 
/* 130 */     return getAttachedFace().getOppositeFace();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setFacingDirection(BlockFace face) {
/*     */     byte data;
/* 137 */     if (isWallSign()) {
/* 138 */       switch (face) {
/*     */         case EAST:
/* 140 */           data = 2;
/*     */           break;
/*     */         
/*     */         case WEST:
/* 144 */           data = 3;
/*     */           break;
/*     */         
/*     */         case NORTH:
/* 148 */           data = 4;
/*     */           break;
/*     */ 
/*     */         
/*     */         default:
/* 153 */           data = 5; break;
/*     */       } 
/*     */     } else {
/* 156 */       switch (face) {
/*     */         case WEST:
/* 158 */           data = 0;
/*     */           break;
/*     */         
/*     */         case WEST_NORTH_WEST:
/* 162 */           data = 1;
/*     */           break;
/*     */         
/*     */         case NORTH_WEST:
/* 166 */           data = 2;
/*     */           break;
/*     */         
/*     */         case NORTH_NORTH_WEST:
/* 170 */           data = 3;
/*     */           break;
/*     */         
/*     */         case NORTH:
/* 174 */           data = 4;
/*     */           break;
/*     */         
/*     */         case NORTH_NORTH_EAST:
/* 178 */           data = 5;
/*     */           break;
/*     */         
/*     */         case NORTH_EAST:
/* 182 */           data = 6;
/*     */           break;
/*     */         
/*     */         case EAST_NORTH_EAST:
/* 186 */           data = 7;
/*     */           break;
/*     */         
/*     */         case EAST:
/* 190 */           data = 8;
/*     */           break;
/*     */         
/*     */         case EAST_SOUTH_EAST:
/* 194 */           data = 9;
/*     */           break;
/*     */         
/*     */         case SOUTH_EAST:
/* 198 */           data = 10;
/*     */           break;
/*     */         
/*     */         case SOUTH_SOUTH_EAST:
/* 202 */           data = 11;
/*     */           break;
/*     */         
/*     */         case SOUTH:
/* 206 */           data = 12;
/*     */           break;
/*     */         
/*     */         case SOUTH_SOUTH_WEST:
/* 210 */           data = 13;
/*     */           break;
/*     */         
/*     */         case WEST_SOUTH_WEST:
/* 214 */           data = 15;
/*     */           break;
/*     */ 
/*     */         
/*     */         default:
/* 219 */           data = 14;
/*     */           break;
/*     */       } 
/*     */     } 
/* 223 */     setData(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 228 */   public String toString() { return super.toString() + " facing " + getFacing(); }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\material\Sign.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */