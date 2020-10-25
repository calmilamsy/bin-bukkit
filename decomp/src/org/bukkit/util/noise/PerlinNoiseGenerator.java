/*     */ package org.bukkit.util.noise;
/*     */ 
/*     */ import java.util.Random;
/*     */ import org.bukkit.World;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PerlinNoiseGenerator
/*     */   extends NoiseGenerator
/*     */ {
/*     */   protected static final int[][] grad3 = { 
/*  12 */       { 1, 1, 0 }, { -1, 1, 0 }, { 1, -1, 0 }, { -1, -1, 0 }, { 1, 0, 1 }, { -1, 0, 1 }, { 1, 0, -1 }, { -1, 0, -1 }, { 0, 1, 1 }, { 0, -1, 1 }, { 0, 1, -1 }, { 0, -1, -1 } };
/*     */ 
/*     */   
/*  15 */   private static final PerlinNoiseGenerator instance = new PerlinNoiseGenerator();
/*     */   
/*     */   protected PerlinNoiseGenerator() {
/*  18 */     int[] p = { 151, 160, 137, 91, 90, 15, 131, 13, 201, 95, 96, 53, 194, 233, 7, 225, 140, 36, 103, 30, 69, 142, 8, 99, 37, 240, 21, 10, 23, 190, 6, 148, 247, 120, 234, 75, 0, 26, 197, 62, 94, 252, 219, 203, 117, 35, 11, 32, 57, 177, 33, 88, 237, 149, 56, 87, 174, 20, 125, 136, 171, 168, 68, 175, 74, 165, 71, 134, 139, 48, 27, 166, 77, 146, 158, 231, 83, 111, 229, 122, 60, 211, 133, 230, 220, 105, 92, 41, 55, 46, 245, 40, 244, 102, 143, 54, 65, 25, 63, 161, 1, 216, 80, 73, 209, 76, 132, 187, 208, 89, 18, 169, 200, 196, 135, 130, 116, 188, 159, 86, 164, 100, 109, 198, 173, 186, 3, 64, 52, 217, 226, 250, 124, 123, 5, 202, 38, 147, 118, 126, 255, 82, 85, 212, 207, 206, 59, 227, 47, 16, 58, 17, 182, 189, 28, 42, 223, 183, 170, 213, 119, 248, 152, 2, 44, 154, 163, 70, 221, 153, 101, 155, 167, 43, 172, 9, 129, 22, 39, 253, 19, 98, 108, 110, 79, 113, 224, 232, 178, 185, 112, 104, 218, 246, 97, 228, 251, 34, 242, 193, 238, 210, 144, 12, 191, 179, 162, 241, 81, 51, 145, 235, 249, 14, 239, 107, 49, 192, 214, 31, 181, 199, 106, 157, 184, 84, 204, 176, 115, 121, 50, 45, 127, 4, 150, 254, 138, 236, 205, 93, 222, 114, 67, 29, 24, 72, 243, 141, 128, 195, 78, 66, 215, 61, 156, 180 };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  37 */     for (int i = 0; i < 512; i++) {
/*  38 */       this.perm[i] = p[i & 0xFF];
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  48 */   public PerlinNoiseGenerator(World world) { this(new Random(world.getSeed())); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  57 */   public PerlinNoiseGenerator(long seed) { this(new Random(seed)); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PerlinNoiseGenerator(Random rand) {
/*  66 */     this.offsetX = rand.nextDouble() * 256.0D;
/*  67 */     this.offsetY = rand.nextDouble() * 256.0D;
/*  68 */     this.offsetZ = rand.nextDouble() * 256.0D;
/*     */     
/*  70 */     for (i = 0; i < 256; i++) {
/*  71 */       this.perm[i] = rand.nextInt(256);
/*     */     }
/*     */     
/*  74 */     for (int i = 0; i < 256; i++) {
/*  75 */       int pos = rand.nextInt(256 - i) + i;
/*  76 */       int old = this.perm[i];
/*     */       
/*  78 */       this.perm[i] = this.perm[pos];
/*  79 */       this.perm[pos] = old;
/*  80 */       this.perm[i + 256] = this.perm[i];
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  91 */   public static double getNoise(double x) { return instance.noise(x); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 102 */   public static double getNoise(double x, double y) { return instance.noise(x, y); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 114 */   public static double getNoise(double x, double y, double z) { return instance.noise(x, y, z); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 123 */   public static PerlinNoiseGenerator getInstance() { return instance; }
/*     */ 
/*     */ 
/*     */   
/*     */   public double noise(double x, double y, double z) {
/* 128 */     x += this.offsetX;
/* 129 */     y += this.offsetY;
/* 130 */     z += this.offsetZ;
/*     */     
/* 132 */     int floorX = floor(x);
/* 133 */     int floorY = floor(y);
/* 134 */     int floorZ = floor(z);
/*     */ 
/*     */     
/* 137 */     int X = floorX & 0xFF;
/* 138 */     int Y = floorY & 0xFF;
/* 139 */     int Z = floorZ & 0xFF;
/*     */ 
/*     */     
/* 142 */     x -= floorX;
/* 143 */     y -= floorY;
/* 144 */     z -= floorZ;
/*     */ 
/*     */     
/* 147 */     double fX = fade(x);
/* 148 */     double fY = fade(y);
/* 149 */     double fZ = fade(z);
/*     */ 
/*     */     
/* 152 */     int A = this.perm[X] + Y;
/* 153 */     int AA = this.perm[A] + Z;
/* 154 */     int AB = this.perm[A + 1] + Z;
/* 155 */     int B = this.perm[X + 1] + Y;
/* 156 */     int BA = this.perm[B] + Z;
/* 157 */     int BB = this.perm[B + 1] + Z;
/*     */     
/* 159 */     return lerp(fZ, lerp(fY, lerp(fX, grad(this.perm[AA], x, y, z), grad(this.perm[BA], x - 1.0D, y, z)), lerp(fX, grad(this.perm[AB], x, y - 1.0D, z), grad(this.perm[BB], x - 1.0D, y - 1.0D, z))), lerp(fY, lerp(fX, grad(this.perm[AA + 1], x, y, z - 1.0D), grad(this.perm[BA + 1], x - 1.0D, y, z - 1.0D)), lerp(fX, grad(this.perm[AB + 1], x, y - 1.0D, z - 1.0D), grad(this.perm[BB + 1], x - 1.0D, y - 1.0D, z - 1.0D))));
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
/* 179 */   public static double getNoise(double x, int octaves, double frequency, double amplitude) { return instance.noise(x, octaves, frequency, amplitude); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 193 */   public static double getNoise(double x, double y, int octaves, double frequency, double amplitude) { return instance.noise(x, y, octaves, frequency, amplitude); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 208 */   public static double getNoise(double x, double y, double z, int octaves, double frequency, double amplitude) { return instance.noise(x, y, z, octaves, frequency, amplitude); }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukki\\util\noise\PerlinNoiseGenerator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */