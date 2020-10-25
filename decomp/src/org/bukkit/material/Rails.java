/*     */ package org.bukkit.material;
/*     */ 
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.block.BlockFace;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Rails
/*     */   extends MaterialData
/*     */ {
/*  12 */   public Rails() { super(Material.RAILS); }
/*     */ 
/*     */ 
/*     */   
/*  16 */   public Rails(int type) { super(type); }
/*     */ 
/*     */ 
/*     */   
/*  20 */   public Rails(Material type) { super(type); }
/*     */ 
/*     */ 
/*     */   
/*  24 */   public Rails(int type, byte data) { super(type, data); }
/*     */ 
/*     */ 
/*     */   
/*  28 */   public Rails(Material type, byte data) { super(type, data); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isOnSlope() {
/*  35 */     byte d = getConvertedData();
/*     */     
/*  37 */     return (d == 2 || d == 3 || d == 4 || d == 5);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isCurve() {
/*  44 */     byte d = getConvertedData();
/*     */     
/*  46 */     return (d == 6 || d == 7 || d == 8 || d == 9);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BlockFace getDirection() {
/*  57 */     byte d = getConvertedData();
/*     */     
/*  59 */     switch (d) {
/*     */       
/*     */       default:
/*  62 */         return BlockFace.WEST;
/*     */       
/*     */       case 1:
/*  65 */         return BlockFace.SOUTH;
/*     */       
/*     */       case 2:
/*  68 */         return BlockFace.SOUTH;
/*     */       
/*     */       case 3:
/*  71 */         return BlockFace.NORTH;
/*     */       
/*     */       case 4:
/*  74 */         return BlockFace.EAST;
/*     */       
/*     */       case 5:
/*  77 */         return BlockFace.WEST;
/*     */       
/*     */       case 6:
/*  80 */         return BlockFace.NORTH_EAST;
/*     */       
/*     */       case 7:
/*  83 */         return BlockFace.SOUTH_EAST;
/*     */       
/*     */       case 8:
/*  86 */         return BlockFace.SOUTH_WEST;
/*     */       case 9:
/*     */         break;
/*  89 */     }  return BlockFace.NORTH_WEST;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  95 */   public String toString() { return super.toString() + " facing " + getDirection() + (isCurve() ? " on a curve" : (isOnSlope() ? " on a slope" : "")); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 103 */   protected byte getConvertedData() { return getData(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDirection(BlockFace face, boolean isOnSlope) {
/* 116 */     switch (face) {
/*     */       case SOUTH:
/* 118 */         setData((byte)(isOnSlope ? 2 : 1));
/*     */         break;
/*     */       
/*     */       case NORTH:
/* 122 */         setData((byte)(isOnSlope ? 3 : 1));
/*     */         break;
/*     */       
/*     */       case EAST:
/* 126 */         setData((byte)(isOnSlope ? 4 : 0));
/*     */         break;
/*     */       
/*     */       case WEST:
/* 130 */         setData((byte)(isOnSlope ? 5 : 0));
/*     */         break;
/*     */       
/*     */       case NORTH_EAST:
/* 134 */         setData((byte)6);
/*     */         break;
/*     */       
/*     */       case SOUTH_EAST:
/* 138 */         setData((byte)7);
/*     */         break;
/*     */       
/*     */       case SOUTH_WEST:
/* 142 */         setData((byte)8);
/*     */         break;
/*     */       
/*     */       case NORTH_WEST:
/* 146 */         setData((byte)9);
/*     */         break;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\material\Rails.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */