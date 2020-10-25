/*     */ package org.bukkit.material;
/*     */ 
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.block.BlockFace;
/*     */ 
/*     */ 
/*     */ public class Lever
/*     */   extends SimpleAttachableMaterialData
/*     */   implements Redstone
/*     */ {
/*  11 */   public Lever() { super(Material.LEVER); }
/*     */ 
/*     */ 
/*     */   
/*  15 */   public Lever(int type) { super(type); }
/*     */ 
/*     */ 
/*     */   
/*  19 */   public Lever(Material type) { super(type); }
/*     */ 
/*     */ 
/*     */   
/*  23 */   public Lever(int type, byte data) { super(type, data); }
/*     */ 
/*     */ 
/*     */   
/*  27 */   public Lever(Material type, byte data) { super(type, data); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  37 */   public boolean isPowered() { return ((getData() & 0x8) == 8); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  45 */   public void setPowered(boolean isPowered) { setData((byte)(isPowered ? (getData() | 0x8) : (getData() & 0xFFFFFFF7))); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BlockFace getAttachedFace() {
/*  54 */     byte data = (byte)(getData() & 0x7);
/*     */     
/*  56 */     switch (data) {
/*     */       case 1:
/*  58 */         return BlockFace.NORTH;
/*     */       
/*     */       case 2:
/*  61 */         return BlockFace.SOUTH;
/*     */       
/*     */       case 3:
/*  64 */         return BlockFace.EAST;
/*     */       
/*     */       case 4:
/*  67 */         return BlockFace.WEST;
/*     */       
/*     */       case 5:
/*     */       case 6:
/*  71 */         return BlockFace.DOWN;
/*     */     } 
/*     */     
/*  74 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setFacingDirection(BlockFace face) {
/*  81 */     byte data = (byte)(getData() & 0x8);
/*     */     
/*  83 */     if (getAttachedFace() == BlockFace.DOWN) {
/*  84 */       switch (face) {
/*     */         case WEST:
/*     */         case EAST:
/*  87 */           data = (byte)(data | 0x5);
/*     */           break;
/*     */         
/*     */         case SOUTH:
/*     */         case NORTH:
/*  92 */           data = (byte)(data | 0x6);
/*     */           break;
/*     */       } 
/*     */     } else {
/*  96 */       switch (face) {
/*     */         case SOUTH:
/*  98 */           data = (byte)(data | true);
/*     */           break;
/*     */         
/*     */         case NORTH:
/* 102 */           data = (byte)(data | 0x2);
/*     */           break;
/*     */         
/*     */         case WEST:
/* 106 */           data = (byte)(data | 0x3);
/*     */           break;
/*     */         
/*     */         case EAST:
/* 110 */           data = (byte)(data | 0x4);
/*     */           break;
/*     */       } 
/*     */     } 
/* 114 */     setData(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 119 */   public String toString() { return super.toString() + " facing " + getFacing() + " " + (isPowered() ? "" : "NOT ") + "POWERED"; }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\material\Lever.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */