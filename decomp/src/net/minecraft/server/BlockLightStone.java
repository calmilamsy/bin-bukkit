/*    */ package net.minecraft.server;
/*    */ 
/*    */ import java.util.Random;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BlockLightStone
/*    */   extends Block
/*    */ {
/* 10 */   public BlockLightStone(int paramInt1, int paramInt2, Material paramMaterial) { super(paramInt1, paramInt2, paramMaterial); }
/*    */ 
/*    */ 
/*    */   
/* 14 */   public int a(Random paramRandom) { return 2 + paramRandom.nextInt(3); }
/*    */ 
/*    */ 
/*    */   
/* 18 */   public int a(int paramInt, Random paramRandom) { return Item.GLOWSTONE_DUST.id; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\BlockLightStone.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */