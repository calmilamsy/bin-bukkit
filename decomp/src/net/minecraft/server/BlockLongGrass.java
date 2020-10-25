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
/*    */ 
/*    */ public class BlockLongGrass
/*    */   extends BlockFlower
/*    */ {
/*    */   protected BlockLongGrass(int paramInt1, int paramInt2) {
/* 16 */     super(paramInt1, paramInt2);
/* 17 */     float f = 0.4F;
/* 18 */     a(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, 0.8F, 0.5F + f);
/*    */   }
/*    */   
/*    */   public int a(int paramInt1, int paramInt2) {
/* 22 */     if (paramInt2 == 1) return this.textureId; 
/* 23 */     if (paramInt2 == 2) return this.textureId + 16 + 1; 
/* 24 */     if (paramInt2 == 0) return this.textureId + 16; 
/* 25 */     return this.textureId;
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
/*    */ 
/*    */   
/*    */   public int a(int paramInt, Random paramRandom) {
/* 54 */     if (paramRandom.nextInt(8) == 0) {
/* 55 */       return Item.SEEDS.id;
/*    */     }
/*    */     
/* 58 */     return -1;
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\BlockLongGrass.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */