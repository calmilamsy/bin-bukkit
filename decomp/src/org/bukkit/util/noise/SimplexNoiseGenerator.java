/*     */ package org.bukkit.util.noise;
/*     */ 
/*     */ import java.util.Random;
/*     */ import org.bukkit.World;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SimplexNoiseGenerator
/*     */   extends PerlinNoiseGenerator
/*     */ {
/*  13 */   protected static final double SQRT_3 = Math.sqrt(3.0D);
/*  14 */   protected static final double SQRT_5 = Math.sqrt(5.0D);
/*  15 */   protected static final double F2 = 0.5D * (SQRT_3 - 1.0D);
/*  16 */   protected static final double G2 = (3.0D - SQRT_3) / 6.0D;
/*  17 */   protected static final double G22 = G2 * 2.0D - 1.0D;
/*     */   protected static final double F3 = 0.3333333333333333D;
/*     */   protected static final double G3 = 0.16666666666666666D;
/*  20 */   protected static final double F4 = (SQRT_5 - 1.0D) / 4.0D;
/*  21 */   protected static final double G4 = (5.0D - SQRT_5) / 20.0D;
/*  22 */   protected static final double G42 = G4 * 2.0D;
/*  23 */   protected static final double G43 = G4 * 3.0D;
/*  24 */   protected static final double G44 = G4 * 4.0D - 1.0D; protected static final int[][] grad4 = { 
/*  25 */       { 0, 1, 1, 1 }, { 0, 1, 1, -1 }, { 0, 1, -1, 1 }, { 0, 1, -1, -1 }, { 0, -1, 1, 1 }, { 0, -1, 1, -1 }, { 0, -1, -1, 1 }, { 0, -1, -1, -1 }, { 1, 0, 1, 1 }, { 1, 0, 1, -1 }, { 1, 0, -1, 1 }, { 1, 0, -1, -1 }, { -1, 0, 1, 1 }, { -1, 0, 1, -1 }, { -1, 0, -1, 1 }, { -1, 0, -1, -1 }, { 1, 1, 0, 1 }, { 1, 1, 0, -1 }, { 1, -1, 0, 1 }, { 1, -1, 0, -1 }, { -1, 1, 0, 1 }, { -1, 1, 0, -1 }, { -1, -1, 0, 1 }, { -1, -1, 0, -1 }, { 1, 1, 1, 0 }, { 1, 1, -1, 0 }, { 1, -1, 1, 0 }, { 1, -1, -1, 0 }, { -1, 1, 1, 0 }, { -1, 1, -1, 0 }, { -1, -1, 1, 0 }, { -1, -1, -1, 0 } };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static final int[][] simplex = { 
/*  33 */       { 0, 1, 2, 3 }, { 0, 1, 3, 2 }, { 0, 0, 0, 0 }, { 0, 2, 3, 1 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 }, { 1, 2, 3, 0 }, { 0, 2, 1, 3 }, { 0, 0, 0, 0 }, { 0, 3, 1, 2 }, { 0, 3, 2, 1 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 }, { 1, 3, 2, 0 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 }, { 1, 2, 0, 3 }, { 0, 0, 0, 0 }, { 1, 3, 0, 2 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 }, { 2, 3, 0, 1 }, { 2, 3, 1, 0 }, { 1, 0, 2, 3 }, { 1, 0, 3, 2 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 }, { 2, 0, 3, 1 }, { 0, 0, 0, 0 }, { 2, 1, 3, 0 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 }, { 2, 0, 1, 3 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 }, { 3, 0, 1, 2 }, { 3, 0, 2, 1 }, { 0, 0, 0, 0 }, { 3, 1, 2, 0 }, { 2, 1, 0, 3 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 }, { 3, 1, 0, 2 }, { 0, 0, 0, 0 }, { 3, 2, 0, 1 }, { 3, 2, 1, 0 } };
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static double offsetW;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  43 */   private static final SimplexNoiseGenerator instance = new SimplexNoiseGenerator();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected SimplexNoiseGenerator() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  55 */   public SimplexNoiseGenerator(World world) { this(new Random(world.getSeed())); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  64 */   public SimplexNoiseGenerator(long seed) { this(new Random(seed)); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SimplexNoiseGenerator(Random rand) {
/*  73 */     super(rand);
/*  74 */     offsetW = rand.nextDouble() * 256.0D;
/*     */   }
/*     */ 
/*     */   
/*  78 */   protected static double dot(int[] g, double x, double y) { return g[0] * x + g[1] * y; }
/*     */ 
/*     */ 
/*     */   
/*  82 */   protected static double dot(int[] g, double x, double y, double z) { return g[0] * x + g[1] * y + g[2] * z; }
/*     */ 
/*     */ 
/*     */   
/*  86 */   protected static double dot(int[] g, double x, double y, double z, double w) { return g[0] * x + g[1] * y + g[2] * z + g[3] * w; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  96 */   public static double getNoise(double xin) { return instance.noise(xin); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 107 */   public static double getNoise(double xin, double yin) { return instance.noise(xin, yin); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 119 */   public static double getNoise(double xin, double yin, double zin) { return instance.noise(xin, yin, zin); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 132 */   public static double getNoise(double x, double y, double z, double w) { return instance.noise(x, y, z, w); }
/*     */   
/*     */   public double noise(double xin, double yin, double zin) {
/*     */     int k2, j2, i2, k1, j1, i1;
/*     */     double n3, n2, n1, n0;
/* 137 */     xin += this.offsetX;
/* 138 */     yin += this.offsetY;
/* 139 */     zin += this.offsetZ;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 144 */     double s = (xin + yin + zin) * 0.3333333333333333D;
/* 145 */     int i = floor(xin + s);
/* 146 */     int j = floor(yin + s);
/* 147 */     int k = floor(zin + s);
/* 148 */     double t = (i + j + k) * 0.16666666666666666D;
/* 149 */     double X0 = i - t;
/* 150 */     double Y0 = j - t;
/* 151 */     double Z0 = k - t;
/* 152 */     double x0 = xin - X0;
/* 153 */     double y0 = yin - Y0;
/* 154 */     double z0 = zin - Z0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 161 */     if (x0 >= y0) {
/* 162 */       if (y0 >= z0) {
/* 163 */         i1 = 1;
/* 164 */         j1 = 0;
/* 165 */         k1 = 0;
/* 166 */         i2 = 1;
/* 167 */         j2 = 1;
/* 168 */         k2 = 0;
/*     */       }
/* 170 */       else if (x0 >= z0) {
/* 171 */         i1 = 1;
/* 172 */         j1 = 0;
/* 173 */         k1 = 0;
/* 174 */         i2 = 1;
/* 175 */         j2 = 0;
/* 176 */         k2 = 1;
/*     */       } else {
/*     */         
/* 179 */         i1 = 0;
/* 180 */         j1 = 0;
/* 181 */         k1 = 1;
/* 182 */         i2 = 1;
/* 183 */         j2 = 0;
/* 184 */         k2 = 1;
/*     */       }
/*     */     
/* 187 */     } else if (y0 < z0) {
/* 188 */       i1 = 0;
/* 189 */       j1 = 0;
/* 190 */       k1 = 1;
/* 191 */       i2 = 0;
/* 192 */       j2 = 1;
/* 193 */       k2 = 1;
/*     */     }
/* 195 */     else if (x0 < z0) {
/* 196 */       i1 = 0;
/* 197 */       j1 = 1;
/* 198 */       k1 = 0;
/* 199 */       i2 = 0;
/* 200 */       j2 = 1;
/* 201 */       k2 = 1;
/*     */     } else {
/*     */       
/* 204 */       i1 = 0;
/* 205 */       j1 = 1;
/* 206 */       k1 = 0;
/* 207 */       i2 = 1;
/* 208 */       j2 = 1;
/* 209 */       k2 = 0;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 217 */     double x1 = x0 - i1 + 0.16666666666666666D;
/* 218 */     double y1 = y0 - j1 + 0.16666666666666666D;
/* 219 */     double z1 = z0 - k1 + 0.16666666666666666D;
/* 220 */     double x2 = x0 - i2 + 0.3333333333333333D;
/* 221 */     double y2 = y0 - j2 + 0.3333333333333333D;
/* 222 */     double z2 = z0 - k2 + 0.3333333333333333D;
/* 223 */     double x3 = x0 - 1.0D + 0.5D;
/* 224 */     double y3 = y0 - 1.0D + 0.5D;
/* 225 */     double z3 = z0 - 1.0D + 0.5D;
/*     */ 
/*     */     
/* 228 */     int ii = i & 0xFF;
/* 229 */     int jj = j & 0xFF;
/* 230 */     int kk = k & 0xFF;
/* 231 */     int gi0 = this.perm[ii + this.perm[jj + this.perm[kk]]] % 12;
/* 232 */     int gi1 = this.perm[ii + i1 + this.perm[jj + j1 + this.perm[kk + k1]]] % 12;
/* 233 */     int gi2 = this.perm[ii + i2 + this.perm[jj + j2 + this.perm[kk + k2]]] % 12;
/* 234 */     int gi3 = this.perm[ii + 1 + this.perm[jj + 1 + this.perm[kk + 1]]] % 12;
/*     */ 
/*     */     
/* 237 */     double t0 = 0.6D - x0 * x0 - y0 * y0 - z0 * z0;
/* 238 */     if (t0 < 0.0D) {
/* 239 */       n0 = 0.0D;
/*     */     } else {
/* 241 */       t0 *= t0;
/* 242 */       n0 = t0 * t0 * dot(grad3[gi0], x0, y0, z0);
/*     */     } 
/*     */     
/* 245 */     double t1 = 0.6D - x1 * x1 - y1 * y1 - z1 * z1;
/* 246 */     if (t1 < 0.0D) {
/* 247 */       n1 = 0.0D;
/*     */     } else {
/* 249 */       t1 *= t1;
/* 250 */       n1 = t1 * t1 * dot(grad3[gi1], x1, y1, z1);
/*     */     } 
/*     */     
/* 253 */     double t2 = 0.6D - x2 * x2 - y2 * y2 - z2 * z2;
/* 254 */     if (t2 < 0.0D) {
/* 255 */       n2 = 0.0D;
/*     */     } else {
/* 257 */       t2 *= t2;
/* 258 */       n2 = t2 * t2 * dot(grad3[gi2], x2, y2, z2);
/*     */     } 
/*     */     
/* 261 */     double t3 = 0.6D - x3 * x3 - y3 * y3 - z3 * z3;
/* 262 */     if (t3 < 0.0D) {
/* 263 */       n3 = 0.0D;
/*     */     } else {
/* 265 */       t3 *= t3;
/* 266 */       n3 = t3 * t3 * dot(grad3[gi3], x3, y3, z3);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 271 */     return 32.0D * (n0 + n1 + n2 + n3);
/*     */   }
/*     */   public double noise(double xin, double yin) {
/*     */     int j1, i1;
/*     */     double n2, n1, n0;
/* 276 */     xin += this.offsetX;
/* 277 */     yin += this.offsetY;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 282 */     double s = (xin + yin) * F2;
/* 283 */     int i = floor(xin + s);
/* 284 */     int j = floor(yin + s);
/* 285 */     double t = (i + j) * G2;
/* 286 */     double X0 = i - t;
/* 287 */     double Y0 = j - t;
/* 288 */     double x0 = xin - X0;
/* 289 */     double y0 = yin - Y0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 295 */     if (x0 > y0) {
/* 296 */       i1 = 1;
/* 297 */       j1 = 0;
/*     */     } else {
/*     */       
/* 300 */       i1 = 0;
/* 301 */       j1 = 1;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 308 */     double x1 = x0 - i1 + G2;
/* 309 */     double y1 = y0 - j1 + G2;
/* 310 */     double x2 = x0 + G22;
/* 311 */     double y2 = y0 + G22;
/*     */ 
/*     */     
/* 314 */     int ii = i & 0xFF;
/* 315 */     int jj = j & 0xFF;
/* 316 */     int gi0 = this.perm[ii + this.perm[jj]] % 12;
/* 317 */     int gi1 = this.perm[ii + i1 + this.perm[jj + j1]] % 12;
/* 318 */     int gi2 = this.perm[ii + 1 + this.perm[jj + 1]] % 12;
/*     */ 
/*     */     
/* 321 */     double t0 = 0.5D - x0 * x0 - y0 * y0;
/* 322 */     if (t0 < 0.0D) {
/* 323 */       n0 = 0.0D;
/*     */     } else {
/* 325 */       t0 *= t0;
/* 326 */       n0 = t0 * t0 * dot(grad3[gi0], x0, y0);
/*     */     } 
/*     */     
/* 329 */     double t1 = 0.5D - x1 * x1 - y1 * y1;
/* 330 */     if (t1 < 0.0D) {
/* 331 */       n1 = 0.0D;
/*     */     } else {
/* 333 */       t1 *= t1;
/* 334 */       n1 = t1 * t1 * dot(grad3[gi1], x1, y1);
/*     */     } 
/*     */     
/* 337 */     double t2 = 0.5D - x2 * x2 - y2 * y2;
/* 338 */     if (t2 < 0.0D) {
/* 339 */       n2 = 0.0D;
/*     */     } else {
/* 341 */       t2 *= t2;
/* 342 */       n2 = t2 * t2 * dot(grad3[gi2], x2, y2);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 347 */     return 70.0D * (n0 + n1 + n2);
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
/*     */   public double noise(double x, double y, double z, double w) {
/*     */     double n4, n3, n2, n1, n0;
/* 360 */     x += this.offsetX;
/* 361 */     y += this.offsetY;
/* 362 */     z += this.offsetZ;
/* 363 */     w += offsetW;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 368 */     double s = (x + y + z + w) * F4;
/* 369 */     int i = floor(x + s);
/* 370 */     int j = floor(y + s);
/* 371 */     int k = floor(z + s);
/* 372 */     int l = floor(w + s);
/*     */     
/* 374 */     double t = (i + j + k + l) * G4;
/* 375 */     double X0 = i - t;
/* 376 */     double Y0 = j - t;
/* 377 */     double Z0 = k - t;
/* 378 */     double W0 = l - t;
/* 379 */     double x0 = x - X0;
/* 380 */     double y0 = y - Y0;
/* 381 */     double z0 = z - Z0;
/* 382 */     double w0 = w - W0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 392 */     int c1 = (x0 > y0) ? 32 : 0;
/* 393 */     int c2 = (x0 > z0) ? 16 : 0;
/* 394 */     int c3 = (y0 > z0) ? 8 : 0;
/* 395 */     int c4 = (x0 > w0) ? 4 : 0;
/* 396 */     int c5 = (y0 > w0) ? 2 : 0;
/* 397 */     int c6 = (z0 > w0) ? 1 : 0;
/* 398 */     int c = c1 + c2 + c3 + c4 + c5 + c6;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 409 */     int i1 = (simplex[c][0] >= 3) ? 1 : 0;
/* 410 */     int j1 = (simplex[c][1] >= 3) ? 1 : 0;
/* 411 */     int k1 = (simplex[c][2] >= 3) ? 1 : 0;
/* 412 */     int l1 = (simplex[c][3] >= 3) ? 1 : 0;
/*     */ 
/*     */     
/* 415 */     int i2 = (simplex[c][0] >= 2) ? 1 : 0;
/* 416 */     int j2 = (simplex[c][1] >= 2) ? 1 : 0;
/* 417 */     int k2 = (simplex[c][2] >= 2) ? 1 : 0;
/* 418 */     int l2 = (simplex[c][3] >= 2) ? 1 : 0;
/*     */ 
/*     */     
/* 421 */     int i3 = (simplex[c][0] >= 1) ? 1 : 0;
/* 422 */     int j3 = (simplex[c][1] >= 1) ? 1 : 0;
/* 423 */     int k3 = (simplex[c][2] >= 1) ? 1 : 0;
/* 424 */     int l3 = (simplex[c][3] >= 1) ? 1 : 0;
/*     */ 
/*     */ 
/*     */     
/* 428 */     double x1 = x0 - i1 + G4;
/* 429 */     double y1 = y0 - j1 + G4;
/* 430 */     double z1 = z0 - k1 + G4;
/* 431 */     double w1 = w0 - l1 + G4;
/*     */     
/* 433 */     double x2 = x0 - i2 + G42;
/* 434 */     double y2 = y0 - j2 + G42;
/* 435 */     double z2 = z0 - k2 + G42;
/* 436 */     double w2 = w0 - l2 + G42;
/*     */     
/* 438 */     double x3 = x0 - i3 + G43;
/* 439 */     double y3 = y0 - j3 + G43;
/* 440 */     double z3 = z0 - k3 + G43;
/* 441 */     double w3 = w0 - l3 + G43;
/*     */     
/* 443 */     double x4 = x0 + G44;
/* 444 */     double y4 = y0 + G44;
/* 445 */     double z4 = z0 + G44;
/* 446 */     double w4 = w0 + G44;
/*     */ 
/*     */     
/* 449 */     int ii = i & 0xFF;
/* 450 */     int jj = j & 0xFF;
/* 451 */     int kk = k & 0xFF;
/* 452 */     int ll = l & 0xFF;
/*     */     
/* 454 */     int gi0 = this.perm[ii + this.perm[jj + this.perm[kk + this.perm[ll]]]] % 32;
/* 455 */     int gi1 = this.perm[ii + i1 + this.perm[jj + j1 + this.perm[kk + k1 + this.perm[ll + l1]]]] % 32;
/* 456 */     int gi2 = this.perm[ii + i2 + this.perm[jj + j2 + this.perm[kk + k2 + this.perm[ll + l2]]]] % 32;
/* 457 */     int gi3 = this.perm[ii + i3 + this.perm[jj + j3 + this.perm[kk + k3 + this.perm[ll + l3]]]] % 32;
/* 458 */     int gi4 = this.perm[ii + 1 + this.perm[jj + 1 + this.perm[kk + 1 + this.perm[ll + 1]]]] % 32;
/*     */ 
/*     */     
/* 461 */     double t0 = 0.6D - x0 * x0 - y0 * y0 - z0 * z0 - w0 * w0;
/* 462 */     if (t0 < 0.0D) {
/* 463 */       n0 = 0.0D;
/*     */     } else {
/* 465 */       t0 *= t0;
/* 466 */       n0 = t0 * t0 * dot(grad4[gi0], x0, y0, z0, w0);
/*     */     } 
/*     */     
/* 469 */     double t1 = 0.6D - x1 * x1 - y1 * y1 - z1 * z1 - w1 * w1;
/* 470 */     if (t1 < 0.0D) {
/* 471 */       n1 = 0.0D;
/*     */     } else {
/* 473 */       t1 *= t1;
/* 474 */       n1 = t1 * t1 * dot(grad4[gi1], x1, y1, z1, w1);
/*     */     } 
/*     */     
/* 477 */     double t2 = 0.6D - x2 * x2 - y2 * y2 - z2 * z2 - w2 * w2;
/* 478 */     if (t2 < 0.0D) {
/* 479 */       n2 = 0.0D;
/*     */     } else {
/* 481 */       t2 *= t2;
/* 482 */       n2 = t2 * t2 * dot(grad4[gi2], x2, y2, z2, w2);
/*     */     } 
/*     */     
/* 485 */     double t3 = 0.6D - x3 * x3 - y3 * y3 - z3 * z3 - w3 * w3;
/* 486 */     if (t3 < 0.0D) {
/* 487 */       n3 = 0.0D;
/*     */     } else {
/* 489 */       t3 *= t3;
/* 490 */       n3 = t3 * t3 * dot(grad4[gi3], x3, y3, z3, w3);
/*     */     } 
/*     */     
/* 493 */     double t4 = 0.6D - x4 * x4 - y4 * y4 - z4 * z4 - w4 * w4;
/* 494 */     if (t4 < 0.0D) {
/* 495 */       n4 = 0.0D;
/*     */     } else {
/* 497 */       t4 *= t4;
/* 498 */       n4 = t4 * t4 * dot(grad4[gi4], x4, y4, z4, w4);
/*     */     } 
/*     */ 
/*     */     
/* 502 */     return 27.0D * (n0 + n1 + n2 + n3 + n4);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 511 */   public static SimplexNoiseGenerator getInstance() { return instance; }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukki\\util\noise\SimplexNoiseGenerator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */