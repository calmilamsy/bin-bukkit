/*     */ package net.minecraft.server;
/*     */ 
/*     */ 
/*     */ public class Pathfinder
/*     */ {
/*     */   private IBlockAccess a;
/*     */   private Path b;
/*     */   private EntityList c;
/*     */   private PathPoint[] d;
/*     */   
/*     */   public Pathfinder(IBlockAccess paramIBlockAccess) {
/*  12 */     this.b = new Path();
/*  13 */     this.c = new EntityList();
/*     */     
/*  15 */     this.d = new PathPoint[32];
/*     */ 
/*     */     
/*  18 */     this.a = paramIBlockAccess;
/*     */   }
/*     */ 
/*     */   
/*  22 */   public PathEntity a(Entity paramEntity1, Entity paramEntity2, float paramFloat) { return a(paramEntity1, paramEntity2.locX, paramEntity2.boundingBox.b, paramEntity2.locZ, paramFloat); }
/*     */ 
/*     */ 
/*     */   
/*  26 */   public PathEntity a(Entity paramEntity, int paramInt1, int paramInt2, int paramInt3, float paramFloat) { return a(paramEntity, (paramInt1 + 0.5F), (paramInt2 + 0.5F), (paramInt3 + 0.5F), paramFloat); }
/*     */ 
/*     */   
/*     */   private PathEntity a(Entity paramEntity, double paramDouble1, double paramDouble2, double paramDouble3, float paramFloat) {
/*  30 */     this.b.a();
/*  31 */     this.c.a();
/*     */     
/*  33 */     PathPoint pathPoint1 = a(MathHelper.floor(paramEntity.boundingBox.a), MathHelper.floor(paramEntity.boundingBox.b), MathHelper.floor(paramEntity.boundingBox.c));
/*  34 */     PathPoint pathPoint2 = a(MathHelper.floor(paramDouble1 - (paramEntity.length / 2.0F)), MathHelper.floor(paramDouble2), MathHelper.floor(paramDouble3 - (paramEntity.length / 2.0F)));
/*     */     
/*  36 */     PathPoint pathPoint3 = new PathPoint(MathHelper.d(paramEntity.length + 1.0F), MathHelper.d(paramEntity.width + 1.0F), MathHelper.d(paramEntity.length + 1.0F));
/*  37 */     return a(paramEntity, pathPoint1, pathPoint2, pathPoint3, paramFloat);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private PathEntity a(Entity paramEntity, PathPoint paramPathPoint1, PathPoint paramPathPoint2, PathPoint paramPathPoint3, float paramFloat) {
/*  43 */     paramPathPoint1.e = 0.0F;
/*  44 */     paramPathPoint1.f = paramPathPoint1.a(paramPathPoint2);
/*  45 */     paramPathPoint1.g = paramPathPoint1.f;
/*     */     
/*  47 */     this.b.a();
/*  48 */     this.b.a(paramPathPoint1);
/*     */     
/*  50 */     PathPoint pathPoint = paramPathPoint1;
/*     */     
/*  52 */     while (!this.b.c()) {
/*  53 */       PathPoint pathPoint1 = this.b.b();
/*     */       
/*  55 */       if (pathPoint1.equals(paramPathPoint2)) {
/*  56 */         return a(paramPathPoint1, paramPathPoint2);
/*     */       }
/*     */       
/*  59 */       if (pathPoint1.a(paramPathPoint2) < pathPoint.a(paramPathPoint2)) {
/*  60 */         pathPoint = pathPoint1;
/*     */       }
/*  62 */       pathPoint1.i = true;
/*     */       
/*  64 */       int i = b(paramEntity, pathPoint1, paramPathPoint3, paramPathPoint2, paramFloat);
/*  65 */       for (byte b1 = 0; b1 < i; b1++) {
/*  66 */         PathPoint pathPoint2 = this.d[b1];
/*     */         
/*  68 */         float f = pathPoint1.e + pathPoint1.a(pathPoint2);
/*  69 */         if (!pathPoint2.a() || f < pathPoint2.e) {
/*  70 */           pathPoint2.h = pathPoint1;
/*  71 */           pathPoint2.e = f;
/*  72 */           pathPoint2.f = pathPoint2.a(paramPathPoint2);
/*  73 */           if (pathPoint2.a()) {
/*  74 */             this.b.a(pathPoint2, pathPoint2.e + pathPoint2.f);
/*     */           } else {
/*  76 */             pathPoint2.g = pathPoint2.e + pathPoint2.f;
/*  77 */             this.b.a(pathPoint2);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/*  83 */     if (pathPoint == paramPathPoint1) return null; 
/*  84 */     return a(paramPathPoint1, pathPoint);
/*     */   }
/*     */   
/*     */   private int b(Entity paramEntity, PathPoint paramPathPoint1, PathPoint paramPathPoint2, PathPoint paramPathPoint3, float paramFloat) {
/*  88 */     byte b1 = 0;
/*     */     
/*  90 */     byte b2 = 0;
/*  91 */     if (a(paramEntity, paramPathPoint1.a, paramPathPoint1.b + 1, paramPathPoint1.c, paramPathPoint2) == 1) b2 = 1;
/*     */     
/*  93 */     PathPoint pathPoint1 = a(paramEntity, paramPathPoint1.a, paramPathPoint1.b, paramPathPoint1.c + 1, paramPathPoint2, b2);
/*  94 */     PathPoint pathPoint2 = a(paramEntity, paramPathPoint1.a - 1, paramPathPoint1.b, paramPathPoint1.c, paramPathPoint2, b2);
/*  95 */     PathPoint pathPoint3 = a(paramEntity, paramPathPoint1.a + 1, paramPathPoint1.b, paramPathPoint1.c, paramPathPoint2, b2);
/*  96 */     PathPoint pathPoint4 = a(paramEntity, paramPathPoint1.a, paramPathPoint1.b, paramPathPoint1.c - 1, paramPathPoint2, b2);
/*     */     
/*  98 */     if (pathPoint1 != null && !pathPoint1.i && pathPoint1.a(paramPathPoint3) < paramFloat) this.d[b1++] = pathPoint1; 
/*  99 */     if (pathPoint2 != null && !pathPoint2.i && pathPoint2.a(paramPathPoint3) < paramFloat) this.d[b1++] = pathPoint2; 
/* 100 */     if (pathPoint3 != null && !pathPoint3.i && pathPoint3.a(paramPathPoint3) < paramFloat) this.d[b1++] = pathPoint3; 
/* 101 */     if (pathPoint4 != null && !pathPoint4.i && pathPoint4.a(paramPathPoint3) < paramFloat) this.d[b1++] = pathPoint4;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 110 */     return b1;
/*     */   }
/*     */   
/*     */   private PathPoint a(Entity paramEntity, int paramInt1, int paramInt2, int paramInt3, PathPoint paramPathPoint, int paramInt4) {
/* 114 */     PathPoint pathPoint = null;
/* 115 */     if (a(paramEntity, paramInt1, paramInt2, paramInt3, paramPathPoint) == 1) pathPoint = a(paramInt1, paramInt2, paramInt3); 
/* 116 */     if (pathPoint == null && paramInt4 > 0 && a(paramEntity, paramInt1, paramInt2 + paramInt4, paramInt3, paramPathPoint) == 1) {
/* 117 */       pathPoint = a(paramInt1, paramInt2 + paramInt4, paramInt3);
/* 118 */       paramInt2 += paramInt4;
/*     */     } 
/*     */     
/* 121 */     if (pathPoint != null) {
/* 122 */       byte b1 = 0;
/* 123 */       int i = 0;
/* 124 */       while (paramInt2 > 0 && (i = a(paramEntity, paramInt1, paramInt2 - 1, paramInt3, paramPathPoint)) == 1) {
/*     */         
/* 126 */         if (++b1 >= 4) return null; 
/* 127 */         paramInt2--;
/*     */         
/* 129 */         if (paramInt2 > 0) pathPoint = a(paramInt1, paramInt2, paramInt3);
/*     */       
/*     */       } 
/* 132 */       if (i == -2) return null;
/*     */     
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 139 */     return pathPoint;
/*     */   }
/*     */   
/*     */   private final PathPoint a(int paramInt1, int paramInt2, int paramInt3) {
/* 143 */     int i = PathPoint.a(paramInt1, paramInt2, paramInt3);
/* 144 */     PathPoint pathPoint = (PathPoint)this.c.a(i);
/* 145 */     if (pathPoint == null) {
/* 146 */       pathPoint = new PathPoint(paramInt1, paramInt2, paramInt3);
/* 147 */       this.c.a(i, pathPoint);
/*     */     } 
/* 149 */     return pathPoint;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int a(Entity paramEntity, int paramInt1, int paramInt2, int paramInt3, PathPoint paramPathPoint) {
/* 158 */     for (int i = paramInt1; i < paramInt1 + paramPathPoint.a; i++) {
/* 159 */       for (int j = paramInt2; j < paramInt2 + paramPathPoint.b; j++) {
/* 160 */         for (int k = paramInt3; k < paramInt3 + paramPathPoint.c; k++) {
/* 161 */           int m = this.a.getTypeId(i, j, k);
/* 162 */           if (m > 0) {
/* 163 */             if (m == Block.IRON_DOOR_BLOCK.id || m == Block.WOODEN_DOOR.id) {
/* 164 */               int n = this.a.getData(i, j, k);
/* 165 */               if (!BlockDoor.e(n)) {
/* 166 */                 return 0;
/*     */               }
/*     */             } else {
/* 169 */               Material material = (Block.byId[m]).material;
/* 170 */               if (material.isSolid()) return 0; 
/* 171 */               if (material == Material.WATER) {
/* 172 */                 return -1;
/*     */               }
/* 174 */               if (material == Material.LAVA) {
/* 175 */                 return -2;
/*     */               }
/*     */             } 
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/* 182 */     return 1;
/*     */   }
/*     */ 
/*     */   
/*     */   private PathEntity a(PathPoint paramPathPoint1, PathPoint paramPathPoint2) {
/* 187 */     byte b1 = 1;
/* 188 */     PathPoint pathPoint = paramPathPoint2;
/* 189 */     while (pathPoint.h != null) {
/* 190 */       b1++;
/* 191 */       pathPoint = pathPoint.h;
/*     */     } 
/*     */     
/* 194 */     PathPoint[] arrayOfPathPoint = new PathPoint[b1];
/* 195 */     pathPoint = paramPathPoint2;
/* 196 */     arrayOfPathPoint[--b1] = pathPoint;
/* 197 */     while (pathPoint.h != null) {
/* 198 */       pathPoint = pathPoint.h;
/* 199 */       arrayOfPathPoint[--b1] = pathPoint;
/*     */     } 
/* 201 */     return new PathEntity(arrayOfPathPoint);
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\Pathfinder.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */