/*     */ package net.minecraft.server;
/*     */ 
/*     */ public class Path
/*     */ {
/*   5 */   private PathPoint[] a = new PathPoint[1024];
/*     */   
/*   7 */   private int b = 0;
/*     */ 
/*     */   
/*     */   public PathPoint a(PathPoint paramPathPoint) {
/*  11 */     if (paramPathPoint.d >= 0) throw new IllegalStateException("OW KNOWS!");
/*     */     
/*  13 */     if (this.b == this.a.length) {
/*     */       
/*  15 */       PathPoint[] arrayOfPathPoint = new PathPoint[this.b << 1];
/*  16 */       System.arraycopy(this.a, 0, arrayOfPathPoint, 0, this.b);
/*  17 */       this.a = arrayOfPathPoint;
/*     */     } 
/*     */ 
/*     */     
/*  21 */     this.a[this.b] = paramPathPoint;
/*  22 */     paramPathPoint.d = this.b;
/*  23 */     a(this.b++);
/*     */     
/*  25 */     return paramPathPoint;
/*     */   }
/*     */ 
/*     */   
/*  29 */   public void a() { this.b = 0; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PathPoint b() {
/*  39 */     PathPoint pathPoint = this.a[0];
/*  40 */     this.a[0] = this.a[--this.b];
/*  41 */     this.a[this.b] = null;
/*  42 */     if (this.b > 0) b(0); 
/*  43 */     pathPoint.d = -1;
/*  44 */     return pathPoint;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void a(PathPoint paramPathPoint, float paramFloat) {
/*  69 */     float f = paramPathPoint.g;
/*  70 */     paramPathPoint.g = paramFloat;
/*  71 */     if (paramFloat < f) {
/*     */       
/*  73 */       a(paramPathPoint.d);
/*     */     }
/*     */     else {
/*     */       
/*  77 */       b(paramPathPoint.d);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void a(int paramInt) {
/*  88 */     PathPoint pathPoint = this.a[paramInt];
/*  89 */     float f = pathPoint.g;
/*  90 */     while (paramInt > 0) {
/*     */       
/*  92 */       int i = paramInt - 1 >> 1;
/*  93 */       PathPoint pathPoint1 = this.a[i];
/*  94 */       if (f < pathPoint1.g) {
/*     */         
/*  96 */         this.a[paramInt] = pathPoint1;
/*  97 */         pathPoint1.d = paramInt;
/*  98 */         paramInt = i;
/*     */       } 
/*     */     } 
/*     */     
/* 102 */     this.a[paramInt] = pathPoint;
/* 103 */     pathPoint.d = paramInt;
/*     */   }
/*     */ 
/*     */   
/*     */   private void b(int paramInt) {
/* 108 */     PathPoint pathPoint = this.a[paramInt];
/* 109 */     float f = pathPoint.g;
/*     */     while (true) {
/*     */       float f2;
/*     */       PathPoint pathPoint2;
/* 113 */       int i = 1 + (paramInt << 1);
/* 114 */       int j = i + 1;
/*     */       
/* 116 */       if (i >= this.b) {
/*     */         break;
/*     */       }
/* 119 */       PathPoint pathPoint1 = this.a[i];
/* 120 */       float f1 = pathPoint1.g;
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 125 */       if (j >= this.b) {
/*     */ 
/*     */         
/* 128 */         pathPoint2 = null;
/* 129 */         f2 = Float.POSITIVE_INFINITY;
/*     */       }
/*     */       else {
/*     */         
/* 133 */         pathPoint2 = this.a[j];
/* 134 */         f2 = pathPoint2.g;
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 139 */       if (f1 < f2) {
/*     */         
/* 141 */         if (f1 < f) {
/*     */           
/* 143 */           this.a[paramInt] = pathPoint1;
/* 144 */           pathPoint1.d = paramInt;
/* 145 */           paramInt = i;
/*     */           
/*     */           continue;
/*     */         } 
/*     */         break;
/*     */       } 
/* 151 */       if (f2 < f) {
/*     */         
/* 153 */         this.a[paramInt] = pathPoint2;
/* 154 */         pathPoint2.d = paramInt;
/* 155 */         paramInt = j;
/*     */         
/*     */         continue;
/*     */       } 
/*     */       break;
/*     */     } 
/* 161 */     this.a[paramInt] = pathPoint;
/* 162 */     pathPoint.d = paramInt;
/*     */   }
/*     */ 
/*     */   
/* 166 */   public boolean c() { return (this.b == 0); }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\Path.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */