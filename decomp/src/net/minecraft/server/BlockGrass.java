/*    */ package net.minecraft.server;
/*    */ 
/*    */ import java.util.Random;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BlockGrass
/*    */   extends Block
/*    */ {
/*    */   protected BlockGrass(int paramInt) {
/* 14 */     super(paramInt, Material.GRASS);
/* 15 */     this.textureId = 3;
/* 16 */     a(true);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void a(World paramWorld, int paramInt1, int paramInt2, int paramInt3, Random paramRandom) {
/* 43 */     if (paramWorld.isStatic)
/*    */       return; 
/* 45 */     if (paramWorld.getLightLevel(paramInt1, paramInt2 + 1, paramInt3) < 4 && Block.q[paramWorld.getTypeId(paramInt1, paramInt2 + 1, paramInt3)] > 2) {
/* 46 */       if (paramRandom.nextInt(4) != 0)
/* 47 */         return;  paramWorld.setTypeId(paramInt1, paramInt2, paramInt3, Block.DIRT.id);
/*    */     }
/* 49 */     else if (paramWorld.getLightLevel(paramInt1, paramInt2 + 1, paramInt3) >= 9) {
/* 50 */       int i = paramInt1 + paramRandom.nextInt(3) - 1;
/* 51 */       int j = paramInt2 + paramRandom.nextInt(5) - 3;
/* 52 */       int k = paramInt3 + paramRandom.nextInt(3) - 1;
/* 53 */       int m = paramWorld.getTypeId(i, j + 1, k);
/* 54 */       if (paramWorld.getTypeId(i, j, k) == Block.DIRT.id && paramWorld.getLightLevel(i, j + 1, k) >= 4 && Block.q[m] <= 2) {
/* 55 */         paramWorld.setTypeId(i, j, k, Block.GRASS.id);
/*    */       }
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/* 62 */   public int a(int paramInt, Random paramRandom) { return Block.DIRT.a(0, paramRandom); }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\BlockGrass.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */