/*     */ package org.bukkit;
/*     */ 
/*     */ import org.bukkit.block.Block;
/*     */ import org.bukkit.util.Vector;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Location
/*     */   implements Cloneable
/*     */ {
/*     */   private World world;
/*     */   private double x;
/*     */   private double y;
/*     */   private double z;
/*     */   private float pitch;
/*     */   private float yaw;
/*     */   
/*  26 */   public Location(World world, double x, double y, double z) { this(world, x, y, z, 0.0F, 0.0F); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Location(World world, double x, double y, double z, float yaw, float pitch) {
/*  40 */     this.world = world;
/*  41 */     this.x = x;
/*  42 */     this.y = y;
/*  43 */     this.z = z;
/*  44 */     this.pitch = pitch;
/*  45 */     this.yaw = yaw;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  54 */   public void setWorld(World world) { this.world = world; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  63 */   public World getWorld() { return this.world; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  72 */   public Block getBlock() { return this.world.getBlockAt(this); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  81 */   public void setX(double x) { this.x = x; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  90 */   public double getX() { return this.x; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 100 */   public int getBlockX() { return locToBlock(this.x); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 109 */   public void setY(double y) { this.y = y; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 118 */   public double getY() { return this.y; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 128 */   public int getBlockY() { return locToBlock(this.y); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 137 */   public void setZ(double z) { this.z = z; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 146 */   public double getZ() { return this.z; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 156 */   public int getBlockZ() { return locToBlock(this.z); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 165 */   public void setYaw(float yaw) { this.yaw = yaw; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 174 */   public float getYaw() { return this.yaw; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 183 */   public void setPitch(float pitch) { this.pitch = pitch; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 192 */   public float getPitch() { return this.pitch; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector getDirection() {
/* 201 */     Vector vector = new Vector();
/*     */     
/* 203 */     double rotX = getYaw();
/* 204 */     double rotY = getPitch();
/*     */     
/* 206 */     vector.setY(-Math.sin(Math.toRadians(rotY)));
/*     */     
/* 208 */     double h = Math.cos(Math.toRadians(rotY));
/*     */     
/* 210 */     vector.setX(-h * Math.sin(Math.toRadians(rotX)));
/* 211 */     vector.setZ(h * Math.cos(Math.toRadians(rotX)));
/*     */     
/* 213 */     return vector;
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
/*     */   public Location add(Location vec) {
/* 225 */     if (vec == null || vec.getWorld() != getWorld()) {
/* 226 */       throw new IllegalArgumentException("Cannot add Locations of differing worlds");
/*     */     }
/*     */     
/* 229 */     this.x += vec.x;
/* 230 */     this.y += vec.y;
/* 231 */     this.z += vec.z;
/* 232 */     return this;
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
/*     */   public Location add(double x, double y, double z) {
/* 245 */     this.x += x;
/* 246 */     this.y += y;
/* 247 */     this.z += z;
/* 248 */     return this;
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
/*     */   public Location subtract(Location vec) {
/* 260 */     if (vec == null || vec.getWorld() != getWorld()) {
/* 261 */       throw new IllegalArgumentException("Cannot add Locations of differing worlds");
/*     */     }
/*     */     
/* 264 */     this.x -= vec.x;
/* 265 */     this.y -= vec.y;
/* 266 */     this.z -= vec.z;
/* 267 */     return this;
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
/*     */   public Location subtract(double x, double y, double z) {
/* 281 */     this.x -= x;
/* 282 */     this.y -= y;
/* 283 */     this.z -= z;
/* 284 */     return this;
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
/* 299 */   public double length() { return Math.sqrt(Math.pow(this.x, 2.0D) + Math.pow(this.y, 2.0D) + Math.pow(this.z, 2.0D)); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 310 */   public double lengthSquared() { return Math.pow(this.x, 2.0D) + Math.pow(this.y, 2.0D) + Math.pow(this.z, 2.0D); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double distance(Location o) {
/* 326 */     if (o == null || o.getWorld() != getWorld()) {
/* 327 */       throw new IllegalArgumentException("Cannot measure distance between worlds or to null");
/*     */     }
/*     */     
/* 330 */     return Math.sqrt(Math.pow(this.x - o.x, 2.0D) + Math.pow(this.y - o.y, 2.0D) + Math.pow(this.z - o.z, 2.0D));
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
/*     */   public double distanceSquared(Location o) {
/* 342 */     if (o == null || o.getWorld() != getWorld()) {
/* 343 */       throw new IllegalArgumentException("Cannot measure distance between worlds or to null");
/*     */     }
/*     */     
/* 346 */     return Math.pow(this.x - o.x, 2.0D) + Math.pow(this.y - o.y, 2.0D) + Math.pow(this.z - o.z, 2.0D);
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
/*     */   public Location multiply(double m) {
/* 358 */     this.x *= m;
/* 359 */     this.y *= m;
/* 360 */     this.z *= m;
/* 361 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Location zero() {
/* 371 */     this.x = 0.0D;
/* 372 */     this.y = 0.0D;
/* 373 */     this.z = 0.0D;
/* 374 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/* 379 */     if (obj == null) {
/* 380 */       return false;
/*     */     }
/* 382 */     if (getClass() != obj.getClass()) {
/* 383 */       return false;
/*     */     }
/* 385 */     Location other = (Location)obj;
/*     */     
/* 387 */     if (this.world != other.world && (this.world == null || !this.world.equals(other.world))) {
/* 388 */       return false;
/*     */     }
/* 390 */     if (Double.doubleToLongBits(this.x) != Double.doubleToLongBits(other.x)) {
/* 391 */       return false;
/*     */     }
/* 393 */     if (Double.doubleToLongBits(this.y) != Double.doubleToLongBits(other.y)) {
/* 394 */       return false;
/*     */     }
/* 396 */     if (Double.doubleToLongBits(this.z) != Double.doubleToLongBits(other.z)) {
/* 397 */       return false;
/*     */     }
/* 399 */     if (Float.floatToIntBits(this.pitch) != Float.floatToIntBits(other.pitch)) {
/* 400 */       return false;
/*     */     }
/* 402 */     if (Float.floatToIntBits(this.yaw) != Float.floatToIntBits(other.yaw)) {
/* 403 */       return false;
/*     */     }
/* 405 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 410 */     hash = 3;
/*     */     
/* 412 */     hash = 19 * hash + ((this.world != null) ? this.world.hashCode() : 0);
/* 413 */     hash = 19 * hash + (int)(Double.doubleToLongBits(this.x) ^ Double.doubleToLongBits(this.x) >>> 32);
/* 414 */     hash = 19 * hash + (int)(Double.doubleToLongBits(this.y) ^ Double.doubleToLongBits(this.y) >>> 32);
/* 415 */     hash = 19 * hash + (int)(Double.doubleToLongBits(this.z) ^ Double.doubleToLongBits(this.z) >>> 32);
/* 416 */     hash = 19 * hash + Float.floatToIntBits(this.pitch);
/* 417 */     return 19 * hash + Float.floatToIntBits(this.yaw);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 423 */   public String toString() { return "Location{world=" + this.world + "x=" + this.x + "y=" + this.y + "z=" + this.z + "pitch=" + this.pitch + "yaw=" + this.yaw + '}'; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 432 */   public Vector toVector() { return new Vector(this.x, this.y, this.z); }
/*     */ 
/*     */ 
/*     */   
/*     */   public Location clone() {
/*     */     try {
/* 438 */       Location l = (Location)super.clone();
/*     */       
/* 440 */       l.world = this.world;
/* 441 */       l.x = this.x;
/* 442 */       l.y = this.y;
/* 443 */       l.z = this.z;
/* 444 */       l.yaw = this.yaw;
/* 445 */       l.pitch = this.pitch;
/* 446 */       return l;
/* 447 */     } catch (CloneNotSupportedException e) {
/* 448 */       e.printStackTrace();
/*     */       
/* 450 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 460 */   public static int locToBlock(double loc) { return (int)Math.floor(loc); }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\Location.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */