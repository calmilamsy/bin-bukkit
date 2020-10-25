/*    */ package net.minecraft.server;
/*    */ 
/*    */ import java.util.Random;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MapGenBase
/*    */ {
/*  9 */   protected int a = 8;
/* 10 */   protected Random b = new Random();
/*    */   
/*    */   public void a(IChunkProvider paramIChunkProvider, World paramWorld, int paramInt1, int paramInt2, byte[] paramArrayOfByte) {
/* 13 */     int i = this.a;
/*    */     
/* 15 */     this.b.setSeed(paramWorld.getSeed());
/* 16 */     long l1 = this.b.nextLong() / 2L * 2L + 1L;
/* 17 */     long l2 = this.b.nextLong() / 2L * 2L + 1L;
/*    */     
/* 19 */     for (int j = paramInt1 - i; j <= paramInt1 + i; j++) {
/* 20 */       for (int k = paramInt2 - i; k <= paramInt2 + i; k++) {
/* 21 */         this.b.setSeed(j * l1 + k * l2 ^ paramWorld.getSeed());
/* 22 */         a(paramWorld, j, k, paramInt1, paramInt2, paramArrayOfByte);
/*    */       } 
/*    */     } 
/*    */   }
/*    */   
/*    */   protected void a(World paramWorld, int paramInt1, int paramInt2, int paramInt3, int paramInt4, byte[] paramArrayOfByte) {}
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\MapGenBase.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */