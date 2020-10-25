/*    */ package net.minecraft.server;
/*    */ 
/*    */ import java.util.Random;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BlockClay
/*    */   extends Block
/*    */ {
/* 10 */   public BlockClay(int paramInt1, int paramInt2) { super(paramInt1, paramInt2, Material.CLAY); }
/*    */ 
/*    */ 
/*    */   
/* 14 */   public int a(int paramInt, Random paramRandom) { return Item.CLAY_BALL.id; }
/*    */ 
/*    */ 
/*    */   
/* 18 */   public int a(Random paramRandom) { return 4; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\BlockClay.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */