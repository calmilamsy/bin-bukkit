/*    */ package net.minecraft.server;
/*    */ 
/*    */ import java.util.Random;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BlockLog
/*    */   extends Block
/*    */ {
/*    */   protected BlockLog(int paramInt) {
/* 15 */     super(paramInt, Material.WOOD);
/* 16 */     this.textureId = 20;
/*    */   }
/*    */ 
/*    */   
/* 20 */   public int a(Random paramRandom) { return 1; }
/*    */ 
/*    */ 
/*    */   
/* 24 */   public int a(int paramInt, Random paramRandom) { return Block.LOG.id; }
/*    */ 
/*    */ 
/*    */   
/* 28 */   public void a(World paramWorld, EntityHuman paramEntityHuman, int paramInt1, int paramInt2, int paramInt3, int paramInt4) { super.a(paramWorld, paramEntityHuman, paramInt1, paramInt2, paramInt3, paramInt4); }
/*    */ 
/*    */   
/*    */   public void remove(World paramWorld, int paramInt1, int paramInt2, int paramInt3) {
/* 32 */     byte b = 4;
/* 33 */     int i = b + 1;
/*    */     
/* 35 */     if (paramWorld.a(paramInt1 - i, paramInt2 - i, paramInt3 - i, paramInt1 + i, paramInt2 + i, paramInt3 + i))
/* 36 */       for (int j = -b; j <= b; j++) {
/* 37 */         for (int k = -b; k <= b; k++) {
/* 38 */           for (int m = -b; m <= b; m++) {
/* 39 */             int n = paramWorld.getTypeId(paramInt1 + j, paramInt2 + k, paramInt3 + m);
/* 40 */             if (n == Block.LEAVES.id) {
/* 41 */               int i1 = paramWorld.getData(paramInt1 + j, paramInt2 + k, paramInt3 + m);
/* 42 */               if ((i1 & 0x8) == 0) {
/* 43 */                 paramWorld.setRawData(paramInt1 + j, paramInt2 + k, paramInt3 + m, i1 | 0x8);
/*    */               }
/*    */             } 
/*    */           } 
/*    */         } 
/*    */       }  
/*    */   }
/*    */   
/*    */   public int a(int paramInt1, int paramInt2) {
/* 52 */     if (paramInt1 == 1) return 21; 
/* 53 */     if (paramInt1 == 0) return 21; 
/* 54 */     if (paramInt2 == 1) return 116; 
/* 55 */     if (paramInt2 == 2) return 117; 
/* 56 */     return 20;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/* 61 */   protected int a_(int paramInt) { return paramInt; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\BlockLog.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */