/*     */ package net.minecraft.server;
/*     */ 
/*     */ import java.util.Random;
/*     */ import org.bukkit.BlockChangeDelegate;
/*     */ 
/*     */ public class WorldGenBigTree
/*     */   extends WorldGenerator
/*     */ {
/*   9 */   static final byte[] a = { 2, 0, 0, 1, 2, 1 };
/*  10 */   Random b = new Random();
/*     */   BlockChangeDelegate c;
/*  12 */   int[] d = { 0, 0, 0 };
/*  13 */   int e = 0;
/*     */   int f;
/*  15 */   double g = 0.618D;
/*  16 */   double h = 1.0D;
/*  17 */   double i = 0.381D;
/*  18 */   double j = 1.0D;
/*  19 */   double k = 1.0D;
/*  20 */   int l = 1;
/*  21 */   int m = 12;
/*  22 */   int n = 4;
/*     */   
/*     */   int[][] o;
/*     */ 
/*     */   
/*     */   void a() {
/*  28 */     this.f = (int)(this.e * this.g);
/*  29 */     if (this.f >= this.e) {
/*  30 */       this.f = this.e - 1;
/*     */     }
/*     */     
/*  33 */     int i = (int)(1.382D + Math.pow(this.k * this.e / 13.0D, 2.0D));
/*     */     
/*  35 */     if (i < 1) {
/*  36 */       i = 1;
/*     */     }
/*     */     
/*  39 */     int[][] aint = new int[i * this.e][4];
/*  40 */     int j = this.d[1] + this.e - this.n;
/*  41 */     int k = 1;
/*  42 */     int l = this.d[1] + this.f;
/*  43 */     int i1 = j - this.d[1];
/*     */     
/*  45 */     aint[0][0] = this.d[0];
/*  46 */     aint[0][1] = j;
/*  47 */     aint[0][2] = this.d[2];
/*  48 */     aint[0][3] = l;
/*  49 */     j--;
/*     */     
/*  51 */     while (i1 >= 0) {
/*  52 */       int j1 = 0;
/*  53 */       float f = a(i1);
/*     */       
/*  55 */       if (f < 0.0F) {
/*  56 */         j--;
/*  57 */         i1--; continue;
/*     */       } 
/*  59 */       for (double d0 = 0.5D; j1 < i; j1++) {
/*  60 */         double d1 = this.j * f * (this.b.nextFloat() + 0.328D);
/*  61 */         double d2 = this.b.nextFloat() * 2.0D * 3.14159D;
/*  62 */         int k1 = MathHelper.floor(d1 * Math.sin(d2) + this.d[0] + d0);
/*  63 */         int l1 = MathHelper.floor(d1 * Math.cos(d2) + this.d[2] + d0);
/*  64 */         int[] aint1 = { k1, j, l1 };
/*  65 */         int[] aint2 = { k1, j + this.n, l1 };
/*     */         
/*  67 */         if (a(aint1, aint2) == -1) {
/*  68 */           int[] aint3 = { this.d[0], this.d[1], this.d[2] };
/*  69 */           double d3 = Math.sqrt(Math.pow(Math.abs(this.d[0] - aint1[0]), 2.0D) + Math.pow(Math.abs(this.d[2] - aint1[2]), 2.0D));
/*  70 */           double d4 = d3 * this.i;
/*     */           
/*  72 */           if (aint1[1] - d4 > l) {
/*  73 */             aint3[1] = l;
/*     */           } else {
/*  75 */             aint3[1] = (int)(aint1[1] - d4);
/*     */           } 
/*     */           
/*  78 */           if (a(aint3, aint1) == -1) {
/*  79 */             aint[k][0] = k1;
/*  80 */             aint[k][1] = j;
/*  81 */             aint[k][2] = l1;
/*  82 */             aint[k][3] = aint3[1];
/*  83 */             k++;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/*  88 */       j--;
/*  89 */       i1--;
/*     */     } 
/*     */ 
/*     */     
/*  93 */     this.o = new int[k][4];
/*  94 */     System.arraycopy(aint, 0, this.o, 0, k);
/*     */   }
/*     */   
/*     */   void a(int i, int j, int k, float f, byte b0, int l) {
/*  98 */     int i1 = (int)(f + 0.618D);
/*  99 */     byte b1 = a[b0];
/* 100 */     byte b2 = a[b0 + 3];
/* 101 */     int[] aint = { i, j, k };
/* 102 */     int[] aint1 = { 0, 0, 0 };
/* 103 */     int j1 = -i1;
/* 104 */     int k1 = -i1;
/*     */     
/* 106 */     for (aint1[b0] = aint[b0]; j1 <= i1; j1++) {
/* 107 */       aint1[b1] = aint[b1] + j1;
/* 108 */       k1 = -i1;
/*     */       
/* 110 */       while (k1 <= i1) {
/* 111 */         double d0 = Math.sqrt(Math.pow(Math.abs(j1) + 0.5D, 2.0D) + Math.pow(Math.abs(k1) + 0.5D, 2.0D));
/*     */         
/* 113 */         if (d0 > f) {
/* 114 */           k1++; continue;
/*     */         } 
/* 116 */         aint1[b2] = aint[b2] + k1;
/* 117 */         int l1 = this.c.getTypeId(aint1[0], aint1[1], aint1[2]);
/*     */         
/* 119 */         if (l1 != 0 && l1 != 18) {
/* 120 */           k1++; continue;
/*     */         } 
/* 122 */         this.c.setRawTypeId(aint1[0], aint1[1], aint1[2], l);
/* 123 */         k1++;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   float a(int i) {
/* 131 */     if (i < this.e * 0.3D) {
/* 132 */       return -1.618F;
/*     */     }
/* 134 */     float f = this.e / 2.0F;
/* 135 */     float f1 = this.e / 2.0F - i;
/*     */ 
/*     */     
/* 138 */     if (f1 == 0.0F) {
/* 139 */       f2 = f;
/* 140 */     } else if (Math.abs(f1) >= f) {
/* 141 */       f2 = 0.0F;
/*     */     } else {
/* 143 */       float f2; f2 = (float)Math.sqrt(Math.pow(Math.abs(f), 2.0D) - Math.pow(Math.abs(f1), 2.0D));
/*     */     } 
/*     */     
/* 146 */     return 0.5F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 152 */   float b(int i) { return (i >= 0 && i < this.n) ? ((i != 0 && i != this.n - 1) ? 3.0F : 2.0F) : -1.0F; }
/*     */ 
/*     */   
/*     */   void a(int i, int j, int k) {
/* 156 */     int l = j;
/*     */     
/* 158 */     for (int i1 = j + this.n; l < i1; l++) {
/* 159 */       float f = b(l - j);
/*     */       
/* 161 */       a(i, l, k, f, (byte)1, 18);
/*     */     } 
/*     */   }
/*     */   
/*     */   void a(int[] aint, int[] aint1, int i) {
/* 166 */     int[] aint2 = { 0, 0, 0 };
/* 167 */     byte b0 = 0;
/*     */     
/*     */     byte b1;
/*     */     
/* 171 */     for (b1 = 0; b0 < 3; b0 = (byte)(b0 + 1)) {
/* 172 */       aint2[b0] = aint1[b0] - aint[b0];
/* 173 */       if (Math.abs(aint2[b0]) > Math.abs(aint2[b1])) {
/* 174 */         b1 = b0;
/*     */       }
/*     */     } 
/*     */     
/* 178 */     if (aint2[b1] != 0) {
/* 179 */       byte b4, b2 = a[b1];
/* 180 */       byte b3 = a[b1 + 3];
/*     */ 
/*     */       
/* 183 */       if (aint2[b1] > 0) {
/* 184 */         b4 = 1;
/*     */       } else {
/* 186 */         b4 = -1;
/*     */       } 
/*     */       
/* 189 */       double d0 = aint2[b2] / aint2[b1];
/* 190 */       double d1 = aint2[b3] / aint2[b1];
/* 191 */       int[] aint3 = { 0, 0, 0 };
/* 192 */       int j = 0;
/*     */       
/* 194 */       for (int k = aint2[b1] + b4; j != k; j += b4) {
/* 195 */         aint3[b1] = MathHelper.floor((aint[b1] + j) + 0.5D);
/* 196 */         aint3[b2] = MathHelper.floor(aint[b2] + j * d0 + 0.5D);
/* 197 */         aint3[b3] = MathHelper.floor(aint[b3] + j * d1 + 0.5D);
/* 198 */         this.c.setRawTypeId(aint3[0], aint3[1], aint3[2], i);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   void b() {
/* 204 */     int i = 0;
/*     */     
/* 206 */     for (int j = this.o.length; i < j; i++) {
/* 207 */       int k = this.o[i][0];
/* 208 */       int l = this.o[i][1];
/* 209 */       int i1 = this.o[i][2];
/*     */       
/* 211 */       a(k, l, i1);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/* 216 */   boolean c(int i) { return (i >= this.e * 0.2D); }
/*     */ 
/*     */   
/*     */   void c() {
/* 220 */     int i = this.d[0];
/* 221 */     int j = this.d[1];
/* 222 */     int k = this.d[1] + this.f;
/* 223 */     int l = this.d[2];
/* 224 */     int[] aint = { i, j, l };
/* 225 */     int[] aint1 = { i, k, l };
/*     */     
/* 227 */     a(aint, aint1, 17);
/* 228 */     if (this.l == 2) {
/* 229 */       aint[0] = aint[0] + 1;
/* 230 */       aint1[0] = aint1[0] + 1;
/* 231 */       a(aint, aint1, 17);
/* 232 */       aint[2] = aint[2] + 1;
/* 233 */       aint1[2] = aint1[2] + 1;
/* 234 */       a(aint, aint1, 17);
/* 235 */       aint[0] = aint[0] + -1;
/* 236 */       aint1[0] = aint1[0] + -1;
/* 237 */       a(aint, aint1, 17);
/*     */     } 
/*     */   }
/*     */   
/*     */   void d() {
/* 242 */     int i = 0;
/* 243 */     int j = this.o.length;
/*     */     
/* 245 */     for (int[] aint = { this.d[0], this.d[1], this.d[2] }; i < j; i++) {
/* 246 */       int[] aint1 = this.o[i];
/* 247 */       int[] aint2 = { aint1[0], aint1[1], aint1[2] };
/*     */       
/* 249 */       aint[1] = aint1[3];
/* 250 */       int k = aint[1] - this.d[1];
/*     */       
/* 252 */       if (c(k))
/* 253 */         a(aint, aint2, 17); 
/*     */     } 
/*     */   }
/*     */   
/*     */   int a(int[] aint, int[] aint1) {
/*     */     byte b4;
/* 259 */     int[] aint2 = { 0, 0, 0 };
/* 260 */     byte b0 = 0;
/*     */     
/*     */     byte b1;
/*     */     
/* 264 */     for (b1 = 0; b0 < 3; b0 = (byte)(b0 + 1)) {
/* 265 */       aint2[b0] = aint1[b0] - aint[b0];
/* 266 */       if (Math.abs(aint2[b0]) > Math.abs(aint2[b1])) {
/* 267 */         b1 = b0;
/*     */       }
/*     */     } 
/*     */     
/* 271 */     if (aint2[b1] == 0) {
/* 272 */       return -1;
/*     */     }
/* 274 */     byte b2 = a[b1];
/* 275 */     byte b3 = a[b1 + 3];
/*     */ 
/*     */     
/* 278 */     if (aint2[b1] > 0) {
/* 279 */       b4 = 1;
/*     */     } else {
/* 281 */       b4 = -1;
/*     */     } 
/*     */     
/* 284 */     double d0 = aint2[b2] / aint2[b1];
/* 285 */     double d1 = aint2[b3] / aint2[b1];
/* 286 */     int[] aint3 = { 0, 0, 0 };
/* 287 */     int i = 0;
/*     */     
/*     */     int j;
/*     */     
/* 291 */     for (j = aint2[b1] + b4; i != j; i += b4) {
/* 292 */       aint3[b1] = aint[b1] + i;
/* 293 */       aint3[b2] = MathHelper.floor(aint[b2] + i * d0);
/* 294 */       aint3[b3] = MathHelper.floor(aint[b3] + i * d1);
/* 295 */       int k = this.c.getTypeId(aint3[0], aint3[1], aint3[2]);
/*     */       
/* 297 */       if (k != 0 && k != 18) {
/*     */         break;
/*     */       }
/*     */     } 
/*     */     
/* 302 */     return (i == j) ? -1 : Math.abs(i);
/*     */   }
/*     */ 
/*     */   
/*     */   boolean e() {
/* 307 */     int[] aint = { this.d[0], this.d[1], this.d[2] };
/* 308 */     int[] aint1 = { this.d[0], this.d[1] + this.e - 1, this.d[2] };
/* 309 */     int i = this.c.getTypeId(this.d[0], this.d[1] - 1, this.d[2]);
/*     */     
/* 311 */     if (i != 2 && i != 3) {
/* 312 */       return false;
/*     */     }
/* 314 */     int j = a(aint, aint1);
/*     */     
/* 316 */     if (j == -1)
/* 317 */       return true; 
/* 318 */     if (j < 6) {
/* 319 */       return false;
/*     */     }
/* 321 */     this.e = j;
/* 322 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void a(double d0, double d1, double d2) {
/* 328 */     this.m = (int)(d0 * 12.0D);
/* 329 */     if (d0 > 0.5D) {
/* 330 */       this.n = 5;
/*     */     }
/*     */     
/* 333 */     this.j = d1;
/* 334 */     this.k = d2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 343 */   public boolean a(World world, Random random, int i, int j, int k) { return generate((BlockChangeDelegate)world, random, i, j, k); }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean generate(BlockChangeDelegate world, Random random, int i, int j, int k) {
/* 348 */     this.c = world;
/* 349 */     long l = random.nextLong();
/*     */     
/* 351 */     this.b.setSeed(l);
/* 352 */     this.d[0] = i;
/* 353 */     this.d[1] = j;
/* 354 */     this.d[2] = k;
/* 355 */     if (this.e == 0) {
/* 356 */       this.e = 5 + this.b.nextInt(this.m);
/*     */     }
/*     */     
/* 359 */     if (!e()) {
/* 360 */       return false;
/*     */     }
/* 362 */     a();
/* 363 */     b();
/* 364 */     c();
/* 365 */     d();
/* 366 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\WorldGenBigTree.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */