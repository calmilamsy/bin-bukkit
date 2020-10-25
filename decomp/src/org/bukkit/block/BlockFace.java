/*     */ package org.bukkit.block;
/*     */ 
/*     */ 
/*     */ 
/*     */ public static enum BlockFace
/*     */ {
/*   7 */   NORTH(-1, false, false),
/*   8 */   EAST(false, false, -1),
/*   9 */   SOUTH(true, false, false),
/*  10 */   WEST(false, false, true),
/*  11 */   UP(false, true, false),
/*  12 */   DOWN(false, -1, false),
/*  13 */   NORTH_EAST(NORTH, EAST),
/*  14 */   NORTH_WEST(NORTH, WEST),
/*  15 */   SOUTH_EAST(SOUTH, EAST),
/*  16 */   SOUTH_WEST(SOUTH, WEST),
/*  17 */   WEST_NORTH_WEST(WEST, NORTH_WEST),
/*  18 */   NORTH_NORTH_WEST(NORTH, NORTH_WEST),
/*  19 */   NORTH_NORTH_EAST(NORTH, NORTH_EAST),
/*  20 */   EAST_NORTH_EAST(EAST, NORTH_EAST),
/*  21 */   EAST_SOUTH_EAST(EAST, SOUTH_EAST),
/*  22 */   SOUTH_SOUTH_EAST(SOUTH, SOUTH_EAST),
/*  23 */   SOUTH_SOUTH_WEST(SOUTH, SOUTH_WEST),
/*  24 */   WEST_SOUTH_WEST(WEST, SOUTH_WEST),
/*  25 */   SELF(false, false, false);
/*     */   
/*     */   private final int modX;
/*     */   private final int modY;
/*     */   private final int modZ;
/*     */   
/*     */   BlockFace(int modX, int modY, int modZ) {
/*  32 */     this.modX = modX;
/*  33 */     this.modY = modY;
/*  34 */     this.modZ = modZ;
/*     */   }
/*     */   
/*     */   BlockFace(BlockFace face1, BlockFace face2) {
/*  38 */     this.modX = face1.getModX() + face2.getModX();
/*  39 */     this.modY = face1.getModY() + face2.getModY();
/*  40 */     this.modZ = face1.getModZ() + face2.getModZ();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  48 */   public int getModX() { return this.modX; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  56 */   public int getModY() { return this.modY; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  64 */   public int getModZ() { return this.modZ; }
/*     */ 
/*     */   
/*     */   public BlockFace getOppositeFace() {
/*  68 */     switch (this) {
/*     */       case NORTH:
/*  70 */         return SOUTH;
/*     */       
/*     */       case SOUTH:
/*  73 */         return NORTH;
/*     */       
/*     */       case EAST:
/*  76 */         return WEST;
/*     */       
/*     */       case WEST:
/*  79 */         return EAST;
/*     */       
/*     */       case UP:
/*  82 */         return DOWN;
/*     */       
/*     */       case DOWN:
/*  85 */         return UP;
/*     */       
/*     */       case NORTH_EAST:
/*  88 */         return SOUTH_WEST;
/*     */       
/*     */       case NORTH_WEST:
/*  91 */         return SOUTH_EAST;
/*     */       
/*     */       case SOUTH_EAST:
/*  94 */         return NORTH_WEST;
/*     */       
/*     */       case SOUTH_WEST:
/*  97 */         return NORTH_EAST;
/*     */       
/*     */       case WEST_NORTH_WEST:
/* 100 */         return EAST_SOUTH_EAST;
/*     */       
/*     */       case NORTH_NORTH_WEST:
/* 103 */         return SOUTH_SOUTH_EAST;
/*     */       
/*     */       case NORTH_NORTH_EAST:
/* 106 */         return SOUTH_SOUTH_WEST;
/*     */       
/*     */       case EAST_NORTH_EAST:
/* 109 */         return WEST_SOUTH_WEST;
/*     */       
/*     */       case EAST_SOUTH_EAST:
/* 112 */         return WEST_NORTH_WEST;
/*     */       
/*     */       case SOUTH_SOUTH_EAST:
/* 115 */         return NORTH_NORTH_WEST;
/*     */       
/*     */       case SOUTH_SOUTH_WEST:
/* 118 */         return NORTH_NORTH_EAST;
/*     */       
/*     */       case WEST_SOUTH_WEST:
/* 121 */         return EAST_NORTH_EAST;
/*     */       
/*     */       case SELF:
/* 124 */         return SELF;
/*     */     } 
/*     */     
/* 127 */     return SELF;
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\block\BlockFace.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */