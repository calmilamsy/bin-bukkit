/*     */ package org.bukkit.util;
/*     */ 
/*     */ import java.util.Random;
/*     */ import org.bukkit.Location;
/*     */ import org.bukkit.World;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Vector
/*     */   implements Cloneable
/*     */ {
/*     */   private static final long serialVersionUID = -2657651106777219169L;
/*  18 */   private static Random random = new Random();
/*     */ 
/*     */   
/*     */   private static final double epsilon = 1.0E-6D;
/*     */ 
/*     */   
/*     */   protected double x;
/*     */ 
/*     */   
/*     */   protected double y;
/*     */   
/*     */   protected double z;
/*     */ 
/*     */   
/*     */   public Vector() {
/*  33 */     this.x = 0.0D;
/*  34 */     this.y = 0.0D;
/*  35 */     this.z = 0.0D;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector(int x, int y, int z) {
/*  46 */     this.x = x;
/*  47 */     this.y = y;
/*  48 */     this.z = z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector(double x, double y, double z) {
/*  59 */     this.x = x;
/*  60 */     this.y = y;
/*  61 */     this.z = z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector(float x, float y, float z) {
/*  72 */     this.x = x;
/*  73 */     this.y = y;
/*  74 */     this.z = z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector add(Vector vec) {
/*  84 */     this.x += vec.x;
/*  85 */     this.y += vec.y;
/*  86 */     this.z += vec.z;
/*  87 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector subtract(Vector vec) {
/*  97 */     this.x -= vec.x;
/*  98 */     this.y -= vec.y;
/*  99 */     this.z -= vec.z;
/* 100 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector multiply(Vector vec) {
/* 110 */     this.x *= vec.x;
/* 111 */     this.y *= vec.y;
/* 112 */     this.z *= vec.z;
/* 113 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector divide(Vector vec) {
/* 123 */     this.x /= vec.x;
/* 124 */     this.y /= vec.y;
/* 125 */     this.z /= vec.z;
/* 126 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector copy(Vector vec) {
/* 136 */     this.x = vec.x;
/* 137 */     this.y = vec.y;
/* 138 */     this.z = vec.z;
/* 139 */     return this;
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
/* 152 */   public double length() { return Math.sqrt(Math.pow(this.x, 2.0D) + Math.pow(this.y, 2.0D) + Math.pow(this.z, 2.0D)); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 161 */   public double lengthSquared() { return Math.pow(this.x, 2.0D) + Math.pow(this.y, 2.0D) + Math.pow(this.z, 2.0D); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 174 */   public double distance(Vector o) { return Math.sqrt(Math.pow(this.x - o.x, 2.0D) + Math.pow(this.y - o.y, 2.0D) + Math.pow(this.z - o.z, 2.0D)); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 183 */   public double distanceSquared(Vector o) { return Math.pow(this.x - o.x, 2.0D) + Math.pow(this.y - o.y, 2.0D) + Math.pow(this.z - o.z, 2.0D); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float angle(Vector other) {
/* 193 */     double dot = dot(other) / length() * other.length();
/*     */     
/* 195 */     return (float)Math.acos(dot);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector midpoint(Vector other) {
/* 205 */     this.x = (this.x + other.x) / 2.0D;
/* 206 */     this.y = (this.y + other.y) / 2.0D;
/* 207 */     this.z = (this.z + other.z) / 2.0D;
/* 208 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector getMidpoint(Vector other) {
/* 218 */     this.x = (this.x + other.x) / 2.0D;
/* 219 */     this.y = (this.y + other.y) / 2.0D;
/* 220 */     this.z = (this.z + other.z) / 2.0D;
/* 221 */     return new Vector(this.x, this.y, this.z);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector multiply(int m) {
/* 231 */     this.x *= m;
/* 232 */     this.y *= m;
/* 233 */     this.z *= m;
/* 234 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector multiply(double m) {
/* 244 */     this.x *= m;
/* 245 */     this.y *= m;
/* 246 */     this.z *= m;
/* 247 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector multiply(float m) {
/* 257 */     this.x *= m;
/* 258 */     this.y *= m;
/* 259 */     this.z *= m;
/* 260 */     return this;
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
/* 271 */   public double dot(Vector other) { return this.x * other.x + this.y * other.y + this.z * other.z; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector crossProduct(Vector o) {
/* 286 */     double newX = this.y * o.z - o.y * this.z;
/* 287 */     double newY = this.z * o.x - o.z * this.x;
/* 288 */     double newZ = this.x * o.y - o.x * this.y;
/*     */     
/* 290 */     this.x = newX;
/* 291 */     this.y = newY;
/* 292 */     this.z = newZ;
/* 293 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector normalize() {
/* 302 */     double length = length();
/*     */     
/* 304 */     this.x /= length;
/* 305 */     this.y /= length;
/* 306 */     this.z /= length;
/*     */     
/* 308 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector zero() {
/* 317 */     this.x = 0.0D;
/* 318 */     this.y = 0.0D;
/* 319 */     this.z = 0.0D;
/* 320 */     return this;
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
/* 333 */   public boolean isInAABB(Vector min, Vector max) { return (this.x >= min.x && this.x <= max.x && this.y >= min.y && this.y <= max.y && this.z >= min.z && this.z <= max.z); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 344 */   public boolean isInSphere(Vector origin, double radius) { return (Math.pow(origin.x - this.x, 2.0D) + Math.pow(origin.y - this.y, 2.0D) + Math.pow(origin.z - this.z, 2.0D) <= Math.pow(radius, 2.0D)); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 353 */   public double getX() { return this.x; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 363 */   public int getBlockX() { return (int)Math.floor(this.x); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 372 */   public double getY() { return this.y; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 382 */   public int getBlockY() { return (int)Math.floor(this.y); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 391 */   public double getZ() { return this.z; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 401 */   public int getBlockZ() { return (int)Math.floor(this.z); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector setX(int x) {
/* 411 */     this.x = x;
/* 412 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector setX(double x) {
/* 422 */     this.x = x;
/* 423 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector setX(float x) {
/* 433 */     this.x = x;
/* 434 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector setY(int y) {
/* 444 */     this.y = y;
/* 445 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector setY(double y) {
/* 455 */     this.y = y;
/* 456 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector setY(float y) {
/* 466 */     this.y = y;
/* 467 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector setZ(int z) {
/* 477 */     this.z = z;
/* 478 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector setZ(double z) {
/* 488 */     this.z = z;
/* 489 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector setZ(float z) {
/* 499 */     this.z = z;
/* 500 */     return this;
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
/*     */   public boolean equals(Object obj) {
/* 512 */     if (!(obj instanceof Vector)) {
/* 513 */       return false;
/*     */     }
/*     */     
/* 516 */     Vector other = (Vector)obj;
/*     */     
/* 518 */     return (Math.abs(this.x - other.x) < 1.0E-6D && Math.abs(this.y - other.y) < 1.0E-6D && Math.abs(this.z - other.z) < 1.0E-6D && getClass().equals(obj.getClass()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 528 */     hash = 7;
/*     */     
/* 530 */     hash = 79 * hash + (int)(Double.doubleToLongBits(this.x) ^ Double.doubleToLongBits(this.x) >>> 32);
/* 531 */     hash = 79 * hash + (int)(Double.doubleToLongBits(this.y) ^ Double.doubleToLongBits(this.y) >>> 32);
/* 532 */     return 79 * hash + (int)(Double.doubleToLongBits(this.z) ^ Double.doubleToLongBits(this.z) >>> 32);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector clone() {
/*     */     try {
/* 544 */       Vector v = (Vector)super.clone();
/*     */       
/* 546 */       v.x = this.x;
/* 547 */       v.y = this.y;
/* 548 */       v.z = this.z;
/* 549 */       return v;
/* 550 */     } catch (CloneNotSupportedException e) {
/* 551 */       e.printStackTrace();
/*     */       
/* 553 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 562 */   public String toString() { return this.x + "," + this.y + "," + this.z; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 572 */   public Location toLocation(World world) { return new Location(world, this.x, this.y, this.z); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 582 */   public Location toLocation(World world, float yaw, float pitch) { return new Location(world, this.x, this.y, this.z, yaw, pitch); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 591 */   public BlockVector toBlockVector() { return new BlockVector(this.x, this.y, this.z); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 600 */   public static double getEpsilon() { return 1.0E-6D; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 611 */   public static Vector getMinimum(Vector v1, Vector v2) { return new Vector(Math.min(v1.x, v2.x), Math.min(v1.y, v2.y), Math.min(v1.z, v2.z)); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 622 */   public static Vector getMaximum(Vector v1, Vector v2) { return new Vector(Math.max(v1.x, v2.x), Math.max(v1.y, v2.y), Math.max(v1.z, v2.z)); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 632 */   public static Vector getRandom() { return new Vector(random.nextDouble(), random.nextDouble(), random.nextDouble()); }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukki\\util\Vector.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */