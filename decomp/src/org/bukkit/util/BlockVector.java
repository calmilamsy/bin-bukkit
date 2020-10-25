/*     */ package org.bukkit.util;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BlockVector
/*     */   extends Vector
/*     */ {
/*     */   public BlockVector() {
/*  17 */     this.x = 0.0D;
/*  18 */     this.y = 0.0D;
/*  19 */     this.z = 0.0D;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BlockVector(Vector vec) {
/*  26 */     this.x = vec.getX();
/*  27 */     this.y = vec.getY();
/*  28 */     this.z = vec.getZ();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BlockVector(int x, int y, int z) {
/*  39 */     this.x = x;
/*  40 */     this.y = y;
/*  41 */     this.z = z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BlockVector(double x, double y, double z) {
/*  52 */     this.x = x;
/*  53 */     this.y = y;
/*  54 */     this.z = z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BlockVector(float x, float y, float z) {
/*  65 */     this.x = x;
/*  66 */     this.y = y;
/*  67 */     this.z = z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*  78 */     if (!(obj instanceof BlockVector)) {
/*  79 */       return false;
/*     */     }
/*  81 */     BlockVector other = (BlockVector)obj;
/*     */     
/*  83 */     return ((int)other.getX() == (int)this.x && (int)other.getY() == (int)this.y && (int)other.getZ() == (int)this.z);
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
/*  94 */   public int hashCode() { return Integer.valueOf((int)this.x).hashCode() >> 13 ^ Integer.valueOf((int)this.y).hashCode() >> 7 ^ Integer.valueOf((int)this.z).hashCode(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BlockVector clone() {
/* 104 */     BlockVector v = (BlockVector)super.clone();
/*     */     
/* 106 */     v.x = this.x;
/* 107 */     v.y = this.y;
/* 108 */     v.z = this.z;
/* 109 */     return v;
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukki\\util\BlockVector.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */