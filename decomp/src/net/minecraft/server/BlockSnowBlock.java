/*    */ package net.minecraft.server;
/*    */ 
/*    */ import java.util.Random;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BlockSnowBlock
/*    */   extends Block
/*    */ {
/*    */   protected BlockSnowBlock(int paramInt1, int paramInt2) {
/* 11 */     super(paramInt1, paramInt2, Material.SNOW_BLOCK);
/* 12 */     a(true);
/*    */   }
/*    */ 
/*    */   
/* 16 */   public int a(int paramInt, Random paramRandom) { return Item.SNOW_BALL.id; }
/*    */ 
/*    */ 
/*    */   
/* 20 */   public int a(Random paramRandom) { return 4; }
/*    */ 
/*    */   
/*    */   public void a(World paramWorld, int paramInt1, int paramInt2, int paramInt3, Random paramRandom) {
/* 24 */     if (paramWorld.a(EnumSkyBlock.BLOCK, paramInt1, paramInt2, paramInt3) > 11) {
/* 25 */       g(paramWorld, paramInt1, paramInt2, paramInt3, paramWorld.getData(paramInt1, paramInt2, paramInt3));
/* 26 */       paramWorld.setTypeId(paramInt1, paramInt2, paramInt3, 0);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\BlockSnowBlock.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */