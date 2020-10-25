/*    */ package net.minecraft.server;
/*    */ 
/*    */ public class ItemSpade
/*    */   extends ItemTool
/*    */ {
/*  6 */   private static Block[] bk = { Block.GRASS, Block.DIRT, Block.SAND, Block.GRAVEL, Block.SNOW, Block.SNOW_BLOCK, Block.CLAY, Block.SOIL };
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 11 */   public ItemSpade(int paramInt, EnumToolMaterial paramEnumToolMaterial) { super(paramInt, 1, paramEnumToolMaterial, bk); }
/*    */ 
/*    */   
/*    */   public boolean a(Block paramBlock) {
/* 15 */     if (paramBlock == Block.SNOW) return true; 
/* 16 */     if (paramBlock == Block.SNOW_BLOCK) return true; 
/* 17 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\ItemSpade.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */