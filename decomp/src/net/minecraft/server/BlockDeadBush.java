/*    */ package net.minecraft.server;
/*    */ 
/*    */ import java.util.Random;
/*    */ 
/*    */ public class BlockDeadBush
/*    */   extends BlockFlower {
/*    */   protected BlockDeadBush(int paramInt1, int paramInt2) {
/*  8 */     super(paramInt1, paramInt2);
/*  9 */     float f = 0.4F;
/* 10 */     a(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, 0.8F, 0.5F + f);
/*    */   }
/*    */ 
/*    */   
/* 14 */   protected boolean c(int paramInt) { return (paramInt == Block.SAND.id); }
/*    */ 
/*    */ 
/*    */   
/* 18 */   public int a(int paramInt1, int paramInt2) { return this.textureId; }
/*    */ 
/*    */ 
/*    */   
/* 22 */   public int a(int paramInt, Random paramRandom) { return -1; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\BlockDeadBush.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */