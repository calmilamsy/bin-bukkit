/*     */ package org.bukkit.util;
/*     */ 
/*     */ import java.util.Iterator;
/*     */ import java.util.NoSuchElementException;
/*     */ import org.bukkit.Location;
/*     */ import org.bukkit.World;
/*     */ import org.bukkit.block.Block;
/*     */ import org.bukkit.block.BlockFace;
/*     */ import org.bukkit.entity.LivingEntity;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BlockIterator
/*     */   extends Object
/*     */   implements Iterator<Block>
/*     */ {
/*     */   private final World world;
/*     */   private final int maxDistance;
/*     */   private static final int gridSize = 16777216;
/*     */   private boolean end;
/*     */   private Block[] blockQueue;
/*     */   private int currentBlock;
/*     */   private int currentDistance;
/*     */   
/*     */   public BlockIterator(World world, Vector start, Vector direction, double yOffset, int maxDistance) {
/*  26 */     this.end = false;
/*     */     
/*  28 */     this.blockQueue = new Block[3];
/*  29 */     this.currentBlock = 0;
/*  30 */     this.currentDistance = 0;
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
/*     */ 
/*     */     
/*  55 */     this.world = world;
/*  56 */     this.maxDistance = maxDistance;
/*     */     
/*  58 */     Vector startClone = start.clone();
/*     */     
/*  60 */     startClone.setY(startClone.getY() + yOffset);
/*     */     
/*  62 */     this.currentDistance = 0;
/*     */     
/*  64 */     double mainDirection = 0.0D;
/*  65 */     double secondDirection = 0.0D;
/*  66 */     double thirdDirection = 0.0D;
/*     */     
/*  68 */     double mainPosition = 0.0D;
/*  69 */     double secondPosition = 0.0D;
/*  70 */     double thirdPosition = 0.0D;
/*     */     
/*  72 */     Block startBlock = world.getBlockAt((int)Math.floor(startClone.getX()), (int)Math.floor(startClone.getY()), (int)Math.floor(startClone.getZ()));
/*     */     
/*  74 */     if (getXLength(direction) > mainDirection) {
/*  75 */       this.mainFace = getXFace(direction);
/*  76 */       mainDirection = getXLength(direction);
/*  77 */       mainPosition = getXPosition(direction, startClone, startBlock);
/*     */       
/*  79 */       this.secondFace = getYFace(direction);
/*  80 */       secondDirection = getYLength(direction);
/*  81 */       secondPosition = getYPosition(direction, startClone, startBlock);
/*     */       
/*  83 */       this.thirdFace = getZFace(direction);
/*  84 */       thirdDirection = getZLength(direction);
/*  85 */       thirdPosition = getZPosition(direction, startClone, startBlock);
/*     */     } 
/*  87 */     if (getYLength(direction) > mainDirection) {
/*  88 */       this.mainFace = getYFace(direction);
/*  89 */       mainDirection = getYLength(direction);
/*  90 */       mainPosition = getYPosition(direction, startClone, startBlock);
/*     */       
/*  92 */       this.secondFace = getZFace(direction);
/*  93 */       secondDirection = getZLength(direction);
/*  94 */       secondPosition = getZPosition(direction, startClone, startBlock);
/*     */       
/*  96 */       this.thirdFace = getXFace(direction);
/*  97 */       thirdDirection = getXLength(direction);
/*  98 */       thirdPosition = getXPosition(direction, startClone, startBlock);
/*     */     } 
/* 100 */     if (getZLength(direction) > mainDirection) {
/* 101 */       this.mainFace = getZFace(direction);
/* 102 */       mainDirection = getZLength(direction);
/* 103 */       mainPosition = getZPosition(direction, startClone, startBlock);
/*     */       
/* 105 */       this.secondFace = getXFace(direction);
/* 106 */       secondDirection = getXLength(direction);
/* 107 */       secondPosition = getXPosition(direction, startClone, startBlock);
/*     */       
/* 109 */       this.thirdFace = getYFace(direction);
/* 110 */       thirdDirection = getYLength(direction);
/* 111 */       thirdPosition = getYPosition(direction, startClone, startBlock);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 116 */     double d = mainPosition / mainDirection;
/* 117 */     double secondd = secondPosition - secondDirection * d;
/* 118 */     double thirdd = thirdPosition - thirdDirection * d;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 123 */     this.secondError = (int)Math.floor(secondd * 1.6777216E7D);
/* 124 */     this.secondStep = (int)Math.round(secondDirection / mainDirection * 1.6777216E7D);
/* 125 */     this.thirdError = (int)Math.floor(thirdd * 1.6777216E7D);
/* 126 */     this.thirdStep = (int)Math.round(thirdDirection / mainDirection * 1.6777216E7D);
/*     */     
/* 128 */     if (this.secondError + this.secondStep <= 0) {
/* 129 */       this.secondError = -this.secondStep + 1;
/*     */     }
/*     */     
/* 132 */     if (this.thirdError + this.thirdStep <= 0) {
/* 133 */       this.thirdError = -this.thirdStep + 1;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 138 */     Block lastBlock = startBlock.getRelative(reverseFace(this.mainFace));
/*     */     
/* 140 */     if (this.secondError < 0) {
/* 141 */       this.secondError += 16777216;
/* 142 */       lastBlock = lastBlock.getRelative(reverseFace(this.secondFace));
/*     */     } 
/*     */     
/* 145 */     if (this.thirdError < 0) {
/* 146 */       this.thirdError += 16777216;
/* 147 */       lastBlock = lastBlock.getRelative(reverseFace(this.thirdFace));
/*     */     } 
/*     */ 
/*     */     
/* 151 */     this.secondError -= 16777216;
/* 152 */     this.thirdError -= 16777216;
/*     */     
/* 154 */     this.blockQueue[0] = lastBlock;
/* 155 */     this.currentBlock = -1;
/*     */     
/* 157 */     scan();
/*     */     
/* 159 */     boolean startBlockFound = false;
/*     */     
/* 161 */     for (int cnt = this.currentBlock; cnt >= 0; cnt--) {
/* 162 */       if (blockEquals(this.blockQueue[cnt], startBlock)) {
/* 163 */         this.currentBlock = cnt;
/* 164 */         startBlockFound = true;
/*     */         
/*     */         break;
/*     */       } 
/*     */     } 
/* 169 */     if (!startBlockFound) {
/* 170 */       throw new IllegalStateException("Start block missed in BlockIterator");
/*     */     }
/*     */ 
/*     */     
/* 174 */     this.maxDistanceInt = (int)Math.round(maxDistance / Math.sqrt(mainDirection * mainDirection + secondDirection * secondDirection + thirdDirection * thirdDirection) / mainDirection);
/*     */   }
/*     */   private int maxDistanceInt; private int secondError; private int thirdError; private int secondStep; private int thirdStep; private BlockFace mainFace; private BlockFace secondFace;
/*     */   private BlockFace thirdFace;
/*     */   
/* 179 */   private boolean blockEquals(Block a, Block b) { return (a.getX() == b.getX() && a.getY() == b.getY() && a.getZ() == b.getZ()); }
/*     */ 
/*     */   
/*     */   private BlockFace reverseFace(BlockFace face) {
/* 183 */     switch (face) {
/*     */       case UP:
/* 185 */         return BlockFace.DOWN;
/*     */       
/*     */       case DOWN:
/* 188 */         return BlockFace.UP;
/*     */       
/*     */       case NORTH:
/* 191 */         return BlockFace.SOUTH;
/*     */       
/*     */       case SOUTH:
/* 194 */         return BlockFace.NORTH;
/*     */       
/*     */       case EAST:
/* 197 */         return BlockFace.WEST;
/*     */       
/*     */       case WEST:
/* 200 */         return BlockFace.EAST;
/*     */     } 
/*     */     
/* 203 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 208 */   private BlockFace getXFace(Vector direction) { return (direction.getX() > 0.0D) ? BlockFace.SOUTH : BlockFace.NORTH; }
/*     */ 
/*     */ 
/*     */   
/* 212 */   private BlockFace getYFace(Vector direction) { return (direction.getY() > 0.0D) ? BlockFace.UP : BlockFace.DOWN; }
/*     */ 
/*     */ 
/*     */   
/* 216 */   private BlockFace getZFace(Vector direction) { return (direction.getZ() > 0.0D) ? BlockFace.WEST : BlockFace.EAST; }
/*     */ 
/*     */ 
/*     */   
/* 220 */   private double getXLength(Vector direction) { return Math.abs(direction.getX()); }
/*     */ 
/*     */ 
/*     */   
/* 224 */   private double getYLength(Vector direction) { return Math.abs(direction.getY()); }
/*     */ 
/*     */ 
/*     */   
/* 228 */   private double getZLength(Vector direction) { return Math.abs(direction.getZ()); }
/*     */ 
/*     */ 
/*     */   
/* 232 */   private double getPosition(double direction, double position, int blockPosition) { return (direction > 0.0D) ? (position - blockPosition) : ((blockPosition + 1) - position); }
/*     */ 
/*     */ 
/*     */   
/* 236 */   private double getXPosition(Vector direction, Vector position, Block block) { return getPosition(direction.getX(), position.getX(), block.getX()); }
/*     */ 
/*     */ 
/*     */   
/* 240 */   private double getYPosition(Vector direction, Vector position, Block block) { return getPosition(direction.getY(), position.getY(), block.getY()); }
/*     */ 
/*     */ 
/*     */   
/* 244 */   private double getZPosition(Vector direction, Vector position, Block block) { return getPosition(direction.getZ(), position.getZ(), block.getZ()); }
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
/*     */   
/* 257 */   public BlockIterator(Location loc, double yOffset, int maxDistance) { this(loc.getWorld(), loc.toVector(), loc.getDirection(), yOffset, maxDistance); }
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
/* 269 */   public BlockIterator(Location loc, double yOffset) { this(loc.getWorld(), loc.toVector(), loc.getDirection(), yOffset, 0); }
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
/* 280 */   public BlockIterator(Location loc) { this(loc, 0.0D); }
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
/* 292 */   public BlockIterator(LivingEntity entity, int maxDistance) { this(entity.getLocation(), entity.getEyeHeight(), maxDistance); }
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
/* 303 */   public BlockIterator(LivingEntity entity) { this(entity, 0); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasNext() {
/* 312 */     scan();
/* 313 */     return (this.currentBlock != -1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Block next() {
/* 323 */     scan();
/* 324 */     if (this.currentBlock <= -1) {
/* 325 */       throw new NoSuchElementException();
/*     */     }
/* 327 */     return this.blockQueue[this.currentBlock--];
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 332 */   public void remove() { throw new UnsupportedOperationException("[BlockIterator] doesn't support block removal"); }
/*     */ 
/*     */   
/*     */   private void scan() {
/* 336 */     if (this.currentBlock >= 0) {
/*     */       return;
/*     */     }
/* 339 */     if (this.maxDistance != 0 && this.currentDistance > this.maxDistanceInt) {
/* 340 */       this.end = true;
/*     */       return;
/*     */     } 
/* 343 */     if (this.end) {
/*     */       return;
/*     */     }
/*     */     
/* 347 */     this.currentDistance++;
/*     */     
/* 349 */     this.secondError += this.secondStep;
/* 350 */     this.thirdError += this.thirdStep;
/*     */     
/* 352 */     if (this.secondError > 0 && this.thirdError > 0) {
/* 353 */       this.blockQueue[2] = this.blockQueue[0].getRelative(this.mainFace);
/* 354 */       if (this.secondStep * this.thirdError < this.thirdStep * this.secondError) {
/* 355 */         this.blockQueue[1] = this.blockQueue[2].getRelative(this.secondFace);
/* 356 */         this.blockQueue[0] = this.blockQueue[1].getRelative(this.thirdFace);
/*     */       } else {
/* 358 */         this.blockQueue[1] = this.blockQueue[2].getRelative(this.thirdFace);
/* 359 */         this.blockQueue[0] = this.blockQueue[1].getRelative(this.secondFace);
/*     */       } 
/* 361 */       this.thirdError -= 16777216;
/* 362 */       this.secondError -= 16777216;
/* 363 */       this.currentBlock = 2; return;
/*     */     } 
/* 365 */     if (this.secondError > 0) {
/* 366 */       this.blockQueue[1] = this.blockQueue[0].getRelative(this.mainFace);
/* 367 */       this.blockQueue[0] = this.blockQueue[1].getRelative(this.secondFace);
/* 368 */       this.secondError -= 16777216;
/* 369 */       this.currentBlock = 1; return;
/*     */     } 
/* 371 */     if (this.thirdError > 0) {
/* 372 */       this.blockQueue[1] = this.blockQueue[0].getRelative(this.mainFace);
/* 373 */       this.blockQueue[0] = this.blockQueue[1].getRelative(this.thirdFace);
/* 374 */       this.thirdError -= 16777216;
/* 375 */       this.currentBlock = 1;
/*     */       return;
/*     */     } 
/* 378 */     this.blockQueue[0] = this.blockQueue[0].getRelative(this.mainFace);
/* 379 */     this.currentBlock = 0;
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukki\\util\BlockIterator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */