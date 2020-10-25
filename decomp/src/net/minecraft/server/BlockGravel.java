/*    */ package net.minecraft.server;
/*    */ 
/*    */ import java.util.Random;
/*    */ 
/*    */ 
/*    */ public class BlockGravel
/*    */   extends BlockSand
/*    */ {
/*  9 */   public BlockGravel(int paramInt1, int paramInt2) { super(paramInt1, paramInt2); }
/*    */ 
/*    */   
/*    */   public int a(int paramInt, Random paramRandom) {
/* 13 */     if (paramRandom.nextInt(10) == 0) return Item.FLINT.id; 
/* 14 */     return this.id;
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\BlockGravel.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */