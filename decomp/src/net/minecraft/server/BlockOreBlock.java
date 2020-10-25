/*    */ package net.minecraft.server;
/*    */ 
/*    */ public class BlockOreBlock
/*    */   extends Block
/*    */ {
/*    */   public BlockOreBlock(int paramInt1, int paramInt2) {
/*  7 */     super(paramInt1, Material.ORE);
/*  8 */     this.textureId = paramInt2;
/*    */   }
/*    */ 
/*    */   
/* 12 */   public int a(int paramInt) { return this.textureId; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\BlockOreBlock.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */