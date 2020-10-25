/*    */ package net.minecraft.server;
/*    */ 
/*    */ import java.util.Random;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WorldGenLiquids
/*    */   extends WorldGenerator
/*    */ {
/*    */   private int a;
/*    */   
/* 12 */   public WorldGenLiquids(int paramInt) { this.a = paramInt; }
/*    */ 
/*    */   
/*    */   public boolean a(World paramWorld, Random paramRandom, int paramInt1, int paramInt2, int paramInt3) {
/* 16 */     if (paramWorld.getTypeId(paramInt1, paramInt2 + 1, paramInt3) != Block.STONE.id) return false; 
/* 17 */     if (paramWorld.getTypeId(paramInt1, paramInt2 - 1, paramInt3) != Block.STONE.id) return false;
/*    */     
/* 19 */     if (paramWorld.getTypeId(paramInt1, paramInt2, paramInt3) != 0 && paramWorld.getTypeId(paramInt1, paramInt2, paramInt3) != Block.STONE.id) return false;
/*    */     
/* 21 */     byte b1 = 0;
/* 22 */     if (paramWorld.getTypeId(paramInt1 - 1, paramInt2, paramInt3) == Block.STONE.id) b1++; 
/* 23 */     if (paramWorld.getTypeId(paramInt1 + 1, paramInt2, paramInt3) == Block.STONE.id) b1++; 
/* 24 */     if (paramWorld.getTypeId(paramInt1, paramInt2, paramInt3 - 1) == Block.STONE.id) b1++; 
/* 25 */     if (paramWorld.getTypeId(paramInt1, paramInt2, paramInt3 + 1) == Block.STONE.id) b1++;
/*    */     
/* 27 */     byte b2 = 0;
/* 28 */     if (paramWorld.isEmpty(paramInt1 - 1, paramInt2, paramInt3)) b2++; 
/* 29 */     if (paramWorld.isEmpty(paramInt1 + 1, paramInt2, paramInt3)) b2++; 
/* 30 */     if (paramWorld.isEmpty(paramInt1, paramInt2, paramInt3 - 1)) b2++; 
/* 31 */     if (paramWorld.isEmpty(paramInt1, paramInt2, paramInt3 + 1)) b2++;
/*    */     
/* 33 */     if (b1 == 3 && b2 == 1) {
/* 34 */       paramWorld.setTypeId(paramInt1, paramInt2, paramInt3, this.a);
/* 35 */       paramWorld.a = true;
/* 36 */       Block.byId[this.a].a(paramWorld, paramInt1, paramInt2, paramInt3, paramRandom);
/* 37 */       paramWorld.a = false;
/*    */     } 
/*    */     
/* 40 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\WorldGenLiquids.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */