/*     */ package net.minecraft.server;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ 
/*     */ public class AxisAlignedBB {
/*   7 */   private static List g = new ArrayList();
/*   8 */   private static int h = 0;
/*     */   public double a;
/*     */   
/*  11 */   public static AxisAlignedBB a(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, double paramDouble6) { return new AxisAlignedBB(paramDouble1, paramDouble2, paramDouble3, paramDouble4, paramDouble5, paramDouble6); }
/*     */ 
/*     */   
/*     */   public double b;
/*     */   public double c;
/*     */   public double d;
/*     */   public double e;
/*     */   public double f;
/*     */   
/*  20 */   public static void a() { h = 0; }
/*     */ 
/*     */   
/*     */   public static AxisAlignedBB b(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, double paramDouble6) {
/*  24 */     if (h >= g.size()) {
/*  25 */       g.add(a(0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D));
/*     */     }
/*  27 */     return ((AxisAlignedBB)g.get(h++)).c(paramDouble1, paramDouble2, paramDouble3, paramDouble4, paramDouble5, paramDouble6);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private AxisAlignedBB(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, double paramDouble6) {
/*  35 */     this.a = paramDouble1;
/*  36 */     this.b = paramDouble2;
/*  37 */     this.c = paramDouble3;
/*  38 */     this.d = paramDouble4;
/*  39 */     this.e = paramDouble5;
/*  40 */     this.f = paramDouble6;
/*     */   }
/*     */   
/*     */   public AxisAlignedBB c(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, double paramDouble6) {
/*  44 */     this.a = paramDouble1;
/*  45 */     this.b = paramDouble2;
/*  46 */     this.c = paramDouble3;
/*  47 */     this.d = paramDouble4;
/*  48 */     this.e = paramDouble5;
/*  49 */     this.f = paramDouble6;
/*  50 */     return this;
/*     */   }
/*     */   
/*     */   public AxisAlignedBB a(double paramDouble1, double paramDouble2, double paramDouble3) {
/*  54 */     double d1 = this.a;
/*  55 */     double d2 = this.b;
/*  56 */     double d3 = this.c;
/*  57 */     double d4 = this.d;
/*  58 */     double d5 = this.e;
/*  59 */     double d6 = this.f;
/*     */     
/*  61 */     if (paramDouble1 < 0.0D) d1 += paramDouble1; 
/*  62 */     if (paramDouble1 > 0.0D) d4 += paramDouble1;
/*     */     
/*  64 */     if (paramDouble2 < 0.0D) d2 += paramDouble2; 
/*  65 */     if (paramDouble2 > 0.0D) d5 += paramDouble2;
/*     */     
/*  67 */     if (paramDouble3 < 0.0D) d3 += paramDouble3; 
/*  68 */     if (paramDouble3 > 0.0D) d6 += paramDouble3;
/*     */     
/*  70 */     return b(d1, d2, d3, d4, d5, d6);
/*     */   }
/*     */   
/*     */   public AxisAlignedBB b(double paramDouble1, double paramDouble2, double paramDouble3) {
/*  74 */     double d1 = this.a - paramDouble1;
/*  75 */     double d2 = this.b - paramDouble2;
/*  76 */     double d3 = this.c - paramDouble3;
/*  77 */     double d4 = this.d + paramDouble1;
/*  78 */     double d5 = this.e + paramDouble2;
/*  79 */     double d6 = this.f + paramDouble3;
/*     */     
/*  81 */     return b(d1, d2, d3, d4, d5, d6);
/*     */   }
/*     */ 
/*     */   
/*  85 */   public AxisAlignedBB c(double paramDouble1, double paramDouble2, double paramDouble3) { return b(this.a + paramDouble1, this.b + paramDouble2, this.c + paramDouble3, this.d + paramDouble1, this.e + paramDouble2, this.f + paramDouble3); }
/*     */ 
/*     */   
/*     */   public double a(AxisAlignedBB paramAxisAlignedBB, double paramDouble) {
/*  89 */     if (paramAxisAlignedBB.e <= this.b || paramAxisAlignedBB.b >= this.e) return paramDouble; 
/*  90 */     if (paramAxisAlignedBB.f <= this.c || paramAxisAlignedBB.c >= this.f) return paramDouble;
/*     */     
/*  92 */     if (paramDouble > 0.0D && paramAxisAlignedBB.d <= this.a) {
/*  93 */       double d1 = this.a - paramAxisAlignedBB.d;
/*  94 */       if (d1 < paramDouble) paramDouble = d1; 
/*     */     } 
/*  96 */     if (paramDouble < 0.0D && paramAxisAlignedBB.a >= this.d) {
/*  97 */       double d1 = this.d - paramAxisAlignedBB.a;
/*  98 */       if (d1 > paramDouble) paramDouble = d1;
/*     */     
/*     */     } 
/* 101 */     return paramDouble;
/*     */   }
/*     */   
/*     */   public double b(AxisAlignedBB paramAxisAlignedBB, double paramDouble) {
/* 105 */     if (paramAxisAlignedBB.d <= this.a || paramAxisAlignedBB.a >= this.d) return paramDouble; 
/* 106 */     if (paramAxisAlignedBB.f <= this.c || paramAxisAlignedBB.c >= this.f) return paramDouble;
/*     */     
/* 108 */     if (paramDouble > 0.0D && paramAxisAlignedBB.e <= this.b) {
/* 109 */       double d1 = this.b - paramAxisAlignedBB.e;
/* 110 */       if (d1 < paramDouble) paramDouble = d1; 
/*     */     } 
/* 112 */     if (paramDouble < 0.0D && paramAxisAlignedBB.b >= this.e) {
/* 113 */       double d1 = this.e - paramAxisAlignedBB.b;
/* 114 */       if (d1 > paramDouble) paramDouble = d1;
/*     */     
/*     */     } 
/* 117 */     return paramDouble;
/*     */   }
/*     */   
/*     */   public double c(AxisAlignedBB paramAxisAlignedBB, double paramDouble) {
/* 121 */     if (paramAxisAlignedBB.d <= this.a || paramAxisAlignedBB.a >= this.d) return paramDouble; 
/* 122 */     if (paramAxisAlignedBB.e <= this.b || paramAxisAlignedBB.b >= this.e) return paramDouble;
/*     */     
/* 124 */     if (paramDouble > 0.0D && paramAxisAlignedBB.f <= this.c) {
/* 125 */       double d1 = this.c - paramAxisAlignedBB.f;
/* 126 */       if (d1 < paramDouble) paramDouble = d1; 
/*     */     } 
/* 128 */     if (paramDouble < 0.0D && paramAxisAlignedBB.c >= this.f) {
/* 129 */       double d1 = this.f - paramAxisAlignedBB.c;
/* 130 */       if (d1 > paramDouble) paramDouble = d1;
/*     */     
/*     */     } 
/* 133 */     return paramDouble;
/*     */   }
/*     */   
/*     */   public boolean a(AxisAlignedBB paramAxisAlignedBB) {
/* 137 */     if (paramAxisAlignedBB.d <= this.a || paramAxisAlignedBB.a >= this.d) return false; 
/* 138 */     if (paramAxisAlignedBB.e <= this.b || paramAxisAlignedBB.b >= this.e) return false; 
/* 139 */     if (paramAxisAlignedBB.f <= this.c || paramAxisAlignedBB.c >= this.f) return false; 
/* 140 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AxisAlignedBB d(double paramDouble1, double paramDouble2, double paramDouble3) {
/* 151 */     this.a += paramDouble1;
/* 152 */     this.b += paramDouble2;
/* 153 */     this.c += paramDouble3;
/* 154 */     this.d += paramDouble1;
/* 155 */     this.e += paramDouble2;
/* 156 */     this.f += paramDouble3;
/* 157 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean a(Vec3D paramVec3D) {
/* 168 */     if (paramVec3D.a <= this.a || paramVec3D.a >= this.d) return false; 
/* 169 */     if (paramVec3D.b <= this.b || paramVec3D.b >= this.e) return false; 
/* 170 */     if (paramVec3D.c <= this.c || paramVec3D.c >= this.f) return false; 
/* 171 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AxisAlignedBB shrink(double paramDouble1, double paramDouble2, double paramDouble3) {
/* 182 */     double d1 = this.a + paramDouble1;
/* 183 */     double d2 = this.b + paramDouble2;
/* 184 */     double d3 = this.c + paramDouble3;
/* 185 */     double d4 = this.d - paramDouble1;
/* 186 */     double d5 = this.e - paramDouble2;
/* 187 */     double d6 = this.f - paramDouble3;
/*     */     
/* 189 */     return b(d1, d2, d3, d4, d5, d6);
/*     */   }
/*     */ 
/*     */   
/* 193 */   public AxisAlignedBB clone() { return b(this.a, this.b, this.c, this.d, this.e, this.f); }
/*     */ 
/*     */   
/*     */   public MovingObjectPosition a(Vec3D paramVec3D1, Vec3D paramVec3D2) {
/* 197 */     Vec3D vec3D1 = paramVec3D1.a(paramVec3D2, this.a);
/* 198 */     Vec3D vec3D2 = paramVec3D1.a(paramVec3D2, this.d);
/*     */     
/* 200 */     Vec3D vec3D3 = paramVec3D1.b(paramVec3D2, this.b);
/* 201 */     Vec3D vec3D4 = paramVec3D1.b(paramVec3D2, this.e);
/*     */     
/* 203 */     Vec3D vec3D5 = paramVec3D1.c(paramVec3D2, this.c);
/* 204 */     Vec3D vec3D6 = paramVec3D1.c(paramVec3D2, this.f);
/*     */     
/* 206 */     if (!b(vec3D1)) vec3D1 = null; 
/* 207 */     if (!b(vec3D2)) vec3D2 = null; 
/* 208 */     if (!c(vec3D3)) vec3D3 = null; 
/* 209 */     if (!c(vec3D4)) vec3D4 = null; 
/* 210 */     if (!d(vec3D5)) vec3D5 = null; 
/* 211 */     if (!d(vec3D6)) vec3D6 = null;
/*     */     
/* 213 */     Vec3D vec3D7 = null;
/*     */     
/* 215 */     if (vec3D1 != null && (vec3D7 == null || paramVec3D1.b(vec3D1) < paramVec3D1.b(vec3D7))) vec3D7 = vec3D1; 
/* 216 */     if (vec3D2 != null && (vec3D7 == null || paramVec3D1.b(vec3D2) < paramVec3D1.b(vec3D7))) vec3D7 = vec3D2; 
/* 217 */     if (vec3D3 != null && (vec3D7 == null || paramVec3D1.b(vec3D3) < paramVec3D1.b(vec3D7))) vec3D7 = vec3D3; 
/* 218 */     if (vec3D4 != null && (vec3D7 == null || paramVec3D1.b(vec3D4) < paramVec3D1.b(vec3D7))) vec3D7 = vec3D4; 
/* 219 */     if (vec3D5 != null && (vec3D7 == null || paramVec3D1.b(vec3D5) < paramVec3D1.b(vec3D7))) vec3D7 = vec3D5; 
/* 220 */     if (vec3D6 != null && (vec3D7 == null || paramVec3D1.b(vec3D6) < paramVec3D1.b(vec3D7))) vec3D7 = vec3D6;
/*     */     
/* 222 */     if (vec3D7 == null) return null;
/*     */     
/* 224 */     byte b1 = -1;
/*     */     
/* 226 */     if (vec3D7 == vec3D1) b1 = 4; 
/* 227 */     if (vec3D7 == vec3D2) b1 = 5; 
/* 228 */     if (vec3D7 == vec3D3) b1 = 0; 
/* 229 */     if (vec3D7 == vec3D4) b1 = 1; 
/* 230 */     if (vec3D7 == vec3D5) b1 = 2; 
/* 231 */     if (vec3D7 == vec3D6) b1 = 3;
/*     */     
/* 233 */     return new MovingObjectPosition(false, false, false, b1, vec3D7);
/*     */   }
/*     */   
/*     */   private boolean b(Vec3D paramVec3D) {
/* 237 */     if (paramVec3D == null) return false; 
/* 238 */     return (paramVec3D.b >= this.b && paramVec3D.b <= this.e && paramVec3D.c >= this.c && paramVec3D.c <= this.f);
/*     */   }
/*     */   
/*     */   private boolean c(Vec3D paramVec3D) {
/* 242 */     if (paramVec3D == null) return false; 
/* 243 */     return (paramVec3D.a >= this.a && paramVec3D.a <= this.d && paramVec3D.c >= this.c && paramVec3D.c <= this.f);
/*     */   }
/*     */   
/*     */   private boolean d(Vec3D paramVec3D) {
/* 247 */     if (paramVec3D == null) return false; 
/* 248 */     return (paramVec3D.a >= this.a && paramVec3D.a <= this.d && paramVec3D.b >= this.b && paramVec3D.b <= this.e);
/*     */   }
/*     */   
/*     */   public void b(AxisAlignedBB paramAxisAlignedBB) {
/* 252 */     this.a = paramAxisAlignedBB.a;
/* 253 */     this.b = paramAxisAlignedBB.b;
/* 254 */     this.c = paramAxisAlignedBB.c;
/* 255 */     this.d = paramAxisAlignedBB.d;
/* 256 */     this.e = paramAxisAlignedBB.e;
/* 257 */     this.f = paramAxisAlignedBB.f;
/*     */   }
/*     */ 
/*     */   
/* 261 */   public String toString() { return "box[" + this.a + ", " + this.b + ", " + this.c + " -> " + this.d + ", " + this.e + ", " + this.f + "]"; }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\AxisAlignedBB.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */