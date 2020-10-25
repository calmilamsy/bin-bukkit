/*     */ package net.minecraft.server;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ public class Vec3D
/*     */ {
/*   9 */   private static List d = new ArrayList();
/*  10 */   private static int e = 0;
/*     */   public double a;
/*     */   
/*  13 */   public static Vec3D a(double paramDouble1, double paramDouble2, double paramDouble3) { return new Vec3D(paramDouble1, paramDouble2, paramDouble3); }
/*     */ 
/*     */ 
/*     */   
/*     */   public double b;
/*     */   
/*     */   public double c;
/*     */ 
/*     */   
/*  22 */   public static void a() { e = 0; }
/*     */ 
/*     */   
/*     */   public static Vec3D create(double paramDouble1, double paramDouble2, double paramDouble3) {
/*  26 */     if (e >= d.size()) {
/*  27 */       d.add(a(0.0D, 0.0D, 0.0D));
/*     */     }
/*  29 */     return ((Vec3D)d.get(e++)).e(paramDouble1, paramDouble2, paramDouble3);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private Vec3D(double paramDouble1, double paramDouble2, double paramDouble3) {
/*  35 */     if (paramDouble1 == -0.0D) paramDouble1 = 0.0D; 
/*  36 */     if (paramDouble2 == -0.0D) paramDouble2 = 0.0D; 
/*  37 */     if (paramDouble3 == -0.0D) paramDouble3 = 0.0D; 
/*  38 */     this.a = paramDouble1;
/*  39 */     this.b = paramDouble2;
/*  40 */     this.c = paramDouble3;
/*     */   }
/*     */   
/*     */   private Vec3D e(double paramDouble1, double paramDouble2, double paramDouble3) {
/*  44 */     this.a = paramDouble1;
/*  45 */     this.b = paramDouble2;
/*  46 */     this.c = paramDouble3;
/*  47 */     return this;
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
/*     */   public Vec3D b() {
/*  68 */     double d1 = MathHelper.a(this.a * this.a + this.b * this.b + this.c * this.c);
/*  69 */     if (d1 < 1.0E-4D) return create(0.0D, 0.0D, 0.0D); 
/*  70 */     return create(this.a / d1, this.b / d1, this.c / d1);
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
/*  82 */   public Vec3D add(double paramDouble1, double paramDouble2, double paramDouble3) { return create(this.a + paramDouble1, this.b + paramDouble2, this.c + paramDouble3); }
/*     */ 
/*     */   
/*     */   public double a(Vec3D paramVec3D) {
/*  86 */     double d1 = paramVec3D.a - this.a;
/*  87 */     double d2 = paramVec3D.b - this.b;
/*  88 */     double d3 = paramVec3D.c - this.c;
/*  89 */     return MathHelper.a(d1 * d1 + d2 * d2 + d3 * d3);
/*     */   }
/*     */   
/*     */   public double b(Vec3D paramVec3D) {
/*  93 */     double d1 = paramVec3D.a - this.a;
/*  94 */     double d2 = paramVec3D.b - this.b;
/*  95 */     double d3 = paramVec3D.c - this.c;
/*  96 */     return d1 * d1 + d2 * d2 + d3 * d3;
/*     */   }
/*     */   
/*     */   public double d(double paramDouble1, double paramDouble2, double paramDouble3) {
/* 100 */     double d1 = paramDouble1 - this.a;
/* 101 */     double d2 = paramDouble2 - this.b;
/* 102 */     double d3 = paramDouble3 - this.c;
/* 103 */     return d1 * d1 + d2 * d2 + d3 * d3;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 111 */   public double c() { return MathHelper.a(this.a * this.a + this.b * this.b + this.c * this.c); }
/*     */ 
/*     */   
/*     */   public Vec3D a(Vec3D paramVec3D, double paramDouble) {
/* 115 */     double d1 = paramVec3D.a - this.a;
/* 116 */     double d2 = paramVec3D.b - this.b;
/* 117 */     double d3 = paramVec3D.c - this.c;
/*     */     
/* 119 */     if (d1 * d1 < 1.0000000116860974E-7D) return null;
/*     */     
/* 121 */     double d4 = (paramDouble - this.a) / d1;
/* 122 */     if (d4 < 0.0D || d4 > 1.0D) return null; 
/* 123 */     return create(this.a + d1 * d4, this.b + d2 * d4, this.c + d3 * d4);
/*     */   }
/*     */   
/*     */   public Vec3D b(Vec3D paramVec3D, double paramDouble) {
/* 127 */     double d1 = paramVec3D.a - this.a;
/* 128 */     double d2 = paramVec3D.b - this.b;
/* 129 */     double d3 = paramVec3D.c - this.c;
/*     */     
/* 131 */     if (d2 * d2 < 1.0000000116860974E-7D) return null;
/*     */     
/* 133 */     double d4 = (paramDouble - this.b) / d2;
/* 134 */     if (d4 < 0.0D || d4 > 1.0D) return null; 
/* 135 */     return create(this.a + d1 * d4, this.b + d2 * d4, this.c + d3 * d4);
/*     */   }
/*     */   
/*     */   public Vec3D c(Vec3D paramVec3D, double paramDouble) {
/* 139 */     double d1 = paramVec3D.a - this.a;
/* 140 */     double d2 = paramVec3D.b - this.b;
/* 141 */     double d3 = paramVec3D.c - this.c;
/*     */     
/* 143 */     if (d3 * d3 < 1.0000000116860974E-7D) return null;
/*     */     
/* 145 */     double d4 = (paramDouble - this.c) / d3;
/* 146 */     if (d4 < 0.0D || d4 > 1.0D) return null; 
/* 147 */     return create(this.a + d1 * d4, this.b + d2 * d4, this.c + d3 * d4);
/*     */   }
/*     */ 
/*     */   
/* 151 */   public String toString() { return "(" + this.a + ", " + this.b + ", " + this.c + ")"; }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\Vec3D.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */