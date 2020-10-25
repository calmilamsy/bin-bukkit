/*     */ package org.bukkit.material;
/*     */ 
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.block.BlockFace;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Bed
/*     */   extends MaterialData
/*     */   implements Directional
/*     */ {
/*  15 */   public Bed() { super(Material.BED_BLOCK); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Bed(BlockFace direction) {
/*  23 */     this();
/*  24 */     setFacingDirection(direction);
/*     */   }
/*     */ 
/*     */   
/*  28 */   public Bed(int type) { super(type); }
/*     */ 
/*     */ 
/*     */   
/*  32 */   public Bed(Material type) { super(type); }
/*     */ 
/*     */ 
/*     */   
/*  36 */   public Bed(int type, byte data) { super(type, data); }
/*     */ 
/*     */ 
/*     */   
/*  40 */   public Bed(Material type, byte data) { super(type, data); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  49 */   public boolean isHeadOfBed() { return ((getData() & 0x8) == 8); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  57 */   public void setHeadOfBed(boolean isHeadOfBed) { setData((byte)(isHeadOfBed ? (getData() | 0x8) : (getData() & 0xFFFFFFF7))); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setFacingDirection(BlockFace face) {
/*     */     byte data;
/*  67 */     switch (face) {
/*     */       case WEST:
/*  69 */         data = 0;
/*     */         break;
/*     */       
/*     */       case NORTH:
/*  73 */         data = 1;
/*     */         break;
/*     */       
/*     */       case EAST:
/*  77 */         data = 2;
/*     */         break;
/*     */ 
/*     */       
/*     */       default:
/*  82 */         data = 3;
/*     */         break;
/*     */     } 
/*  85 */     if (isHeadOfBed()) {
/*  86 */       data = (byte)(data | 0x8);
/*     */     }
/*     */     
/*  89 */     setData(data);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BlockFace getFacing() {
/*  98 */     byte data = (byte)(getData() & 0x7);
/*     */     
/* 100 */     switch (data) {
/*     */       case 0:
/* 102 */         return BlockFace.WEST;
/*     */       
/*     */       case 1:
/* 105 */         return BlockFace.NORTH;
/*     */       
/*     */       case 2:
/* 108 */         return BlockFace.EAST;
/*     */     } 
/*     */ 
/*     */     
/* 112 */     return BlockFace.SOUTH;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 118 */   public String toString() { return (isHeadOfBed() ? "HEAD" : "FOOT") + " of " + super.toString() + " facing " + getFacing(); }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\material\Bed.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */