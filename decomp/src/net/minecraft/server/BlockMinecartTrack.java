/*     */ package net.minecraft.server;
/*     */ 
/*     */ import java.util.Random;
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
/*     */ public class BlockMinecartTrack
/*     */   extends Block
/*     */ {
/*     */   private final boolean a;
/*     */   
/*     */   public static final boolean g(World paramWorld, int paramInt1, int paramInt2, int paramInt3) {
/* 270 */     int i = paramWorld.getTypeId(paramInt1, paramInt2, paramInt3);
/* 271 */     return (i == Block.RAILS.id || i == Block.GOLDEN_RAIL.id || i == Block.DETECTOR_RAIL.id);
/*     */   }
/*     */ 
/*     */   
/* 275 */   public static final boolean c(int paramInt) { return (paramInt == Block.RAILS.id || paramInt == Block.GOLDEN_RAIL.id || paramInt == Block.DETECTOR_RAIL.id); }
/*     */ 
/*     */   
/*     */   protected BlockMinecartTrack(int paramInt1, int paramInt2, boolean paramBoolean) {
/* 279 */     super(paramInt1, paramInt2, Material.ORIENTABLE);
/* 280 */     this.a = paramBoolean;
/* 281 */     a(0.0F, 0.0F, 0.0F, 1.0F, 0.125F, 1.0F);
/*     */   }
/*     */ 
/*     */   
/* 285 */   public boolean f() { return this.a; }
/*     */ 
/*     */ 
/*     */   
/* 289 */   public AxisAlignedBB e(World paramWorld, int paramInt1, int paramInt2, int paramInt3) { return null; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 297 */   public boolean a() { return false; }
/*     */ 
/*     */   
/*     */   public MovingObjectPosition a(World paramWorld, int paramInt1, int paramInt2, int paramInt3, Vec3D paramVec3D1, Vec3D paramVec3D2) {
/* 301 */     a(paramWorld, paramInt1, paramInt2, paramInt3);
/* 302 */     return super.a(paramWorld, paramInt1, paramInt2, paramInt3, paramVec3D1, paramVec3D2);
/*     */   }
/*     */   
/*     */   public void a(IBlockAccess paramIBlockAccess, int paramInt1, int paramInt2, int paramInt3) {
/* 306 */     int i = paramIBlockAccess.getData(paramInt1, paramInt2, paramInt3);
/* 307 */     if (i >= 2 && i <= 5) {
/* 308 */       a(0.0F, 0.0F, 0.0F, 1.0F, 0.625F, 1.0F);
/*     */     } else {
/* 310 */       a(0.0F, 0.0F, 0.0F, 1.0F, 0.125F, 1.0F);
/*     */     } 
/*     */   }
/*     */   
/*     */   public int a(int paramInt1, int paramInt2) {
/* 315 */     if (this.a)
/* 316 */     { if (this.id == Block.GOLDEN_RAIL.id && (
/* 317 */         paramInt2 & 0x8) == 0) {
/* 318 */         return this.textureId - 16;
/*     */       } }
/*     */     
/* 321 */     else if (paramInt2 >= 6) { return this.textureId - 16; }
/* 322 */      return this.textureId;
/*     */   }
/*     */ 
/*     */   
/* 326 */   public boolean b() { return false; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 334 */   public int a(Random paramRandom) { return 1; }
/*     */ 
/*     */   
/*     */   public boolean canPlace(World paramWorld, int paramInt1, int paramInt2, int paramInt3) {
/* 338 */     if (paramWorld.e(paramInt1, paramInt2 - 1, paramInt3)) {
/* 339 */       return true;
/*     */     }
/* 341 */     return false;
/*     */   }
/*     */   
/*     */   public void c(World paramWorld, int paramInt1, int paramInt2, int paramInt3) {
/* 345 */     if (!paramWorld.isStatic)
/*     */     {
/* 347 */       a(paramWorld, paramInt1, paramInt2, paramInt3, true);
/*     */     }
/*     */   }
/*     */   
/*     */   public void doPhysics(World paramWorld, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 352 */     if (paramWorld.isStatic)
/*     */       return; 
/* 354 */     int i = paramWorld.getData(paramInt1, paramInt2, paramInt3);
/* 355 */     int j = i;
/* 356 */     if (this.a) {
/* 357 */       j &= 0x7;
/*     */     }
/* 359 */     boolean bool = false;
/*     */     
/* 361 */     if (!paramWorld.e(paramInt1, paramInt2 - 1, paramInt3)) bool = true; 
/* 362 */     if (j == 2 && !paramWorld.e(paramInt1 + 1, paramInt2, paramInt3)) bool = true; 
/* 363 */     if (j == 3 && !paramWorld.e(paramInt1 - 1, paramInt2, paramInt3)) bool = true; 
/* 364 */     if (j == 4 && !paramWorld.e(paramInt1, paramInt2, paramInt3 - 1)) bool = true; 
/* 365 */     if (j == 5 && !paramWorld.e(paramInt1, paramInt2, paramInt3 + 1)) bool = true;
/*     */     
/* 367 */     if (bool) {
/* 368 */       g(paramWorld, paramInt1, paramInt2, paramInt3, paramWorld.getData(paramInt1, paramInt2, paramInt3));
/* 369 */       paramWorld.setTypeId(paramInt1, paramInt2, paramInt3, 0);
/*     */     }
/* 371 */     else if (this.id == Block.GOLDEN_RAIL.id) {
/* 372 */       boolean bool1 = (paramWorld.isBlockIndirectlyPowered(paramInt1, paramInt2, paramInt3) || paramWorld.isBlockIndirectlyPowered(paramInt1, paramInt2 + 1, paramInt3)) ? 1 : 0;
/* 373 */       bool1 = (bool1 || a(paramWorld, paramInt1, paramInt2, paramInt3, i, true, 0) || a(paramWorld, paramInt1, paramInt2, paramInt3, i, false, 0)) ? 1 : 0;
/*     */       
/* 375 */       boolean bool2 = false;
/* 376 */       if (bool1 && (i & 0x8) == 0) {
/* 377 */         paramWorld.setData(paramInt1, paramInt2, paramInt3, j | 0x8);
/* 378 */         bool2 = true;
/* 379 */       } else if (!bool1 && (i & 0x8) != 0) {
/* 380 */         paramWorld.setData(paramInt1, paramInt2, paramInt3, j);
/* 381 */         bool2 = true;
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 387 */       if (bool2) {
/* 388 */         paramWorld.applyPhysics(paramInt1, paramInt2 - 1, paramInt3, this.id);
/* 389 */         if (j == 2 || j == 3 || j == 4 || j == 5) {
/* 390 */           paramWorld.applyPhysics(paramInt1, paramInt2 + 1, paramInt3, this.id);
/*     */         }
/*     */       }
/*     */     
/*     */     }
/* 395 */     else if (paramInt4 > 0 && Block.byId[paramInt4].isPowerSource() && !this.a && 
/* 396 */       MinecartTrackLogic.a(new MinecartTrackLogic(this, paramWorld, paramInt1, paramInt2, paramInt3)) == 3) {
/* 397 */       a(paramWorld, paramInt1, paramInt2, paramInt3, false);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void a(World paramWorld, int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean) {
/* 404 */     if (paramWorld.isStatic)
/* 405 */       return;  (new MinecartTrackLogic(this, paramWorld, paramInt1, paramInt2, paramInt3)).a(paramWorld.isBlockIndirectlyPowered(paramInt1, paramInt2, paramInt3), paramBoolean);
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean a(World paramWorld, int paramInt1, int paramInt2, int paramInt3, int paramInt4, boolean paramBoolean, int paramInt5) {
/* 410 */     if (paramInt5 >= 8) {
/* 411 */       return false;
/*     */     }
/*     */     
/* 414 */     int i = paramInt4 & 0x7;
/*     */     
/* 416 */     boolean bool = true;
/* 417 */     switch (i) {
/*     */       case 0:
/* 419 */         if (paramBoolean) {
/* 420 */           paramInt3++; break;
/*     */         } 
/* 422 */         paramInt3--;
/*     */         break;
/*     */       
/*     */       case 1:
/* 426 */         if (paramBoolean) {
/* 427 */           paramInt1--; break;
/*     */         } 
/* 429 */         paramInt1++;
/*     */         break;
/*     */       
/*     */       case 2:
/* 433 */         if (paramBoolean) {
/* 434 */           paramInt1--;
/*     */         } else {
/* 436 */           paramInt1++;
/* 437 */           paramInt2++;
/* 438 */           bool = false;
/*     */         } 
/* 440 */         i = 1;
/*     */         break;
/*     */       case 3:
/* 443 */         if (paramBoolean) {
/* 444 */           paramInt1--;
/* 445 */           paramInt2++;
/* 446 */           bool = false;
/*     */         } else {
/* 448 */           paramInt1++;
/*     */         } 
/* 450 */         i = 1;
/*     */         break;
/*     */       case 4:
/* 453 */         if (paramBoolean) {
/* 454 */           paramInt3++;
/*     */         } else {
/* 456 */           paramInt3--;
/* 457 */           paramInt2++;
/* 458 */           bool = false;
/*     */         } 
/* 460 */         i = 0;
/*     */         break;
/*     */       case 5:
/* 463 */         if (paramBoolean) {
/* 464 */           paramInt3++;
/* 465 */           paramInt2++;
/* 466 */           bool = false;
/*     */         } else {
/* 468 */           paramInt3--;
/*     */         } 
/* 470 */         i = 0;
/*     */         break;
/*     */     } 
/*     */     
/* 474 */     if (a(paramWorld, paramInt1, paramInt2, paramInt3, paramBoolean, paramInt5, i)) {
/* 475 */       return true;
/*     */     }
/* 477 */     if (bool && a(paramWorld, paramInt1, paramInt2 - 1, paramInt3, paramBoolean, paramInt5, i)) {
/* 478 */       return true;
/*     */     }
/* 480 */     return false;
/*     */   }
/*     */   
/*     */   private boolean a(World paramWorld, int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean, int paramInt4, int paramInt5) {
/* 484 */     int i = paramWorld.getTypeId(paramInt1, paramInt2, paramInt3);
/* 485 */     if (i == Block.GOLDEN_RAIL.id) {
/* 486 */       int j = paramWorld.getData(paramInt1, paramInt2, paramInt3);
/* 487 */       int k = j & 0x7;
/*     */       
/* 489 */       if (paramInt5 == 1 && (k == 0 || k == 4 || k == 5)) {
/* 490 */         return false;
/*     */       }
/* 492 */       if (paramInt5 == 0 && (k == 1 || k == 2 || k == 3)) {
/* 493 */         return false;
/*     */       }
/*     */       
/* 496 */       if ((j & 0x8) != 0) {
/* 497 */         if (paramWorld.isBlockIndirectlyPowered(paramInt1, paramInt2, paramInt3) || paramWorld.isBlockIndirectlyPowered(paramInt1, paramInt2 + 1, paramInt3)) {
/* 498 */           return true;
/*     */         }
/* 500 */         return a(paramWorld, paramInt1, paramInt2, paramInt3, j, paramBoolean, paramInt4 + 1);
/*     */       } 
/*     */     } 
/*     */     
/* 504 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 510 */   public int e() { return 0; }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\BlockMinecartTrack.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */