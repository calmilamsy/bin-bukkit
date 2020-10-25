/*    */ package net.minecraft.server;
/*    */ 
/*    */ import java.util.Random;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BlockBookshelf
/*    */   extends Block
/*    */ {
/* 11 */   public BlockBookshelf(int paramInt1, int paramInt2) { super(paramInt1, paramInt2, Material.WOOD); }
/*    */ 
/*    */ 
/*    */   
/*    */   public int a(int paramInt) {
/* 16 */     if (paramInt <= 1) return 4; 
/* 17 */     return this.textureId;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/* 22 */   public int a(Random paramRandom) { return 0; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\BlockBookshelf.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */