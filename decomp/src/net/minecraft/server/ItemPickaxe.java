/*    */ package net.minecraft.server;
/*    */ 
/*    */ public class ItemPickaxe
/*    */   extends ItemTool
/*    */ {
/*    */   private static Block[] bk = { 
/*  7 */       Block.COBBLESTONE, Block.DOUBLE_STEP, Block.STEP, Block.STONE, Block.SANDSTONE, Block.MOSSY_COBBLESTONE, Block.IRON_ORE, Block.IRON_BLOCK, Block.COAL_ORE, Block.GOLD_BLOCK, Block.GOLD_ORE, Block.DIAMOND_ORE, Block.DIAMOND_BLOCK, Block.ICE, Block.NETHERRACK, Block.LAPIS_ORE, Block.LAPIS_BLOCK };
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 14 */   protected ItemPickaxe(int paramInt, EnumToolMaterial paramEnumToolMaterial) { super(paramInt, 2, paramEnumToolMaterial, bk); }
/*    */ 
/*    */   
/*    */   public boolean a(Block paramBlock) {
/* 18 */     if (paramBlock == Block.OBSIDIAN) return (this.a.d() == 3); 
/* 19 */     if (paramBlock == Block.DIAMOND_BLOCK || paramBlock == Block.DIAMOND_ORE) return (this.a.d() >= 2); 
/* 20 */     if (paramBlock == Block.GOLD_BLOCK || paramBlock == Block.GOLD_ORE) return (this.a.d() >= 2); 
/* 21 */     if (paramBlock == Block.IRON_BLOCK || paramBlock == Block.IRON_ORE) return (this.a.d() >= 1); 
/* 22 */     if (paramBlock == Block.LAPIS_BLOCK || paramBlock == Block.LAPIS_ORE) return (this.a.d() >= 1); 
/* 23 */     if (paramBlock == Block.REDSTONE_ORE || paramBlock == Block.GLOWING_REDSTONE_ORE) return (this.a.d() >= 2); 
/* 24 */     if (paramBlock.material == Material.STONE) return true; 
/* 25 */     if (paramBlock.material == Material.ORE) return true; 
/* 26 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\ItemPickaxe.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */