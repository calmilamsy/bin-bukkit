/*     */ package net.minecraft.server;
/*     */ 
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ 
/*     */ 
/*     */ public class EmptyChunk
/*     */   extends Chunk
/*     */ {
/*     */   public EmptyChunk(World paramWorld, int paramInt1, int paramInt2) {
/*  12 */     super(paramWorld, paramInt1, paramInt2);
/*  13 */     this.p = true;
/*     */   }
/*     */   
/*     */   public EmptyChunk(World paramWorld, byte[] paramArrayOfByte, int paramInt1, int paramInt2) {
/*  17 */     super(paramWorld, paramArrayOfByte, paramInt1, paramInt2);
/*  18 */     this.p = true;
/*     */   }
/*     */ 
/*     */   
/*  22 */   public boolean a(int paramInt1, int paramInt2) { return (paramInt1 == this.x && paramInt2 == this.z); }
/*     */ 
/*     */ 
/*     */   
/*  26 */   public int b(int paramInt1, int paramInt2) { return 0; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void a() {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initLighting() {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void loadNOP() {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  46 */   public int getTypeId(int paramInt1, int paramInt2, int paramInt3) { return 0; }
/*     */ 
/*     */ 
/*     */   
/*  50 */   public boolean a(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5) { return true; }
/*     */ 
/*     */ 
/*     */   
/*  54 */   public boolean a(int paramInt1, int paramInt2, int paramInt3, int paramInt4) { return true; }
/*     */ 
/*     */ 
/*     */   
/*  58 */   public int getData(int paramInt1, int paramInt2, int paramInt3) { return 0; }
/*     */ 
/*     */ 
/*     */   
/*     */   public void b(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {}
/*     */ 
/*     */ 
/*     */   
/*  66 */   public int a(EnumSkyBlock paramEnumSkyBlock, int paramInt1, int paramInt2, int paramInt3) { return 0; }
/*     */ 
/*     */ 
/*     */   
/*     */   public void a(EnumSkyBlock paramEnumSkyBlock, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {}
/*     */ 
/*     */ 
/*     */   
/*  74 */   public int c(int paramInt1, int paramInt2, int paramInt3, int paramInt4) { return 0; }
/*     */ 
/*     */ 
/*     */   
/*     */   public void a(Entity paramEntity) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void b(Entity paramEntity) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void a(Entity paramEntity, int paramInt) {}
/*     */ 
/*     */ 
/*     */   
/*  90 */   public boolean c(int paramInt1, int paramInt2, int paramInt3) { return false; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  98 */   public TileEntity d(int paramInt1, int paramInt2, int paramInt3) { return null; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void a(TileEntity paramTileEntity) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void a(int paramInt1, int paramInt2, int paramInt3, TileEntity paramTileEntity) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void e(int paramInt1, int paramInt2, int paramInt3) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addEntities() {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeEntities() {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void f() {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void a(Entity paramEntity, AxisAlignedBB paramAxisAlignedBB, List paramList) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void a(Class paramClass, AxisAlignedBB paramAxisAlignedBB, List paramList) {}
/*     */ 
/*     */ 
/*     */   
/* 138 */   public boolean a(boolean paramBoolean) { return false; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getData(byte[] paramArrayOfByte, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7) {
/* 146 */     int i = paramInt4 - paramInt1;
/* 147 */     int j = paramInt5 - paramInt2;
/* 148 */     int k = paramInt6 - paramInt3;
/*     */     
/* 150 */     int m = i * j * k;
/* 151 */     int n = m + m / 2 * 3;
/*     */     
/* 153 */     Arrays.fill(paramArrayOfByte, paramInt7, paramInt7 + n, (byte)0);
/* 154 */     return n;
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
/* 167 */   public Random a(long paramLong) { return new Random(this.world.getSeed() + (this.x * this.x * 4987142) + (this.x * 5947611) + (this.z * this.z) * 4392871L + (this.z * 389711) ^ paramLong); }
/*     */ 
/*     */ 
/*     */   
/* 171 */   public boolean isEmpty() { return true; }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\EmptyChunk.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */