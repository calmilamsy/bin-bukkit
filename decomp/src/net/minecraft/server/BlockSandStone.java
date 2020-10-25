/*    */ package net.minecraft.server;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BlockSandStone
/*    */   extends Block
/*    */ {
/*  9 */   public BlockSandStone(int paramInt) { super(paramInt, 192, Material.STONE); }
/*    */ 
/*    */ 
/*    */   
/*    */   public int a(int paramInt) {
/* 14 */     if (paramInt == 1) {
/* 15 */       return this.textureId - 16;
/*    */     }
/* 17 */     if (paramInt == 0) {
/* 18 */       return this.textureId + 16;
/*    */     }
/* 20 */     return this.textureId;
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\BlockSandStone.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */