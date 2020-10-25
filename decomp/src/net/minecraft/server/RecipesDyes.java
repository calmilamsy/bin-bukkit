/*    */ package net.minecraft.server;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class RecipesDyes
/*    */ {
/*    */   public void a(CraftingManager paramCraftingManager) {
/* 14 */     for (byte b = 0; b < 16; b++) {
/*    */       
/* 16 */       paramCraftingManager.registerShapelessRecipe(new ItemStack(Block.WOOL, true, BlockCloth.d(b)), new Object[] { new ItemStack(Item.INK_SACK, true, b), new ItemStack(Item.byId[Block.WOOL.id], true, false) });
/*    */     } 
/*    */ 
/*    */ 
/*    */     
/* 21 */     paramCraftingManager.registerShapelessRecipe(new ItemStack(Item.INK_SACK, 2, 11), new Object[] { Block.YELLOW_FLOWER });
/* 22 */     paramCraftingManager.registerShapelessRecipe(new ItemStack(Item.INK_SACK, 2, true), new Object[] { Block.RED_ROSE });
/* 23 */     paramCraftingManager.registerShapelessRecipe(new ItemStack(Item.INK_SACK, 3, 15), new Object[] { Item.BONE });
/* 24 */     paramCraftingManager.registerShapelessRecipe(new ItemStack(Item.INK_SACK, 2, 9), new Object[] { new ItemStack(Item.INK_SACK, true, true), new ItemStack(Item.INK_SACK, true, 15) });
/*    */     
/* 26 */     paramCraftingManager.registerShapelessRecipe(new ItemStack(Item.INK_SACK, 2, 14), new Object[] { new ItemStack(Item.INK_SACK, true, true), new ItemStack(Item.INK_SACK, true, 11) });
/*    */     
/* 28 */     paramCraftingManager.registerShapelessRecipe(new ItemStack(Item.INK_SACK, 2, 10), new Object[] { new ItemStack(Item.INK_SACK, true, 2), new ItemStack(Item.INK_SACK, true, 15) });
/*    */     
/* 30 */     paramCraftingManager.registerShapelessRecipe(new ItemStack(Item.INK_SACK, 2, 8), new Object[] { new ItemStack(Item.INK_SACK, true, false), new ItemStack(Item.INK_SACK, true, 15) });
/*    */     
/* 32 */     paramCraftingManager.registerShapelessRecipe(new ItemStack(Item.INK_SACK, 2, 7), new Object[] { new ItemStack(Item.INK_SACK, true, 8), new ItemStack(Item.INK_SACK, true, 15) });
/*    */     
/* 34 */     paramCraftingManager.registerShapelessRecipe(new ItemStack(Item.INK_SACK, 3, 7), new Object[] { new ItemStack(Item.INK_SACK, true, false), new ItemStack(Item.INK_SACK, true, 15), new ItemStack(Item.INK_SACK, true, 15) });
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 40 */     paramCraftingManager.registerShapelessRecipe(new ItemStack(Item.INK_SACK, 2, 12), new Object[] { new ItemStack(Item.INK_SACK, true, 4), new ItemStack(Item.INK_SACK, true, 15) });
/*    */     
/* 42 */     paramCraftingManager.registerShapelessRecipe(new ItemStack(Item.INK_SACK, 2, 6), new Object[] { new ItemStack(Item.INK_SACK, true, 4), new ItemStack(Item.INK_SACK, true, 2) });
/*    */     
/* 44 */     paramCraftingManager.registerShapelessRecipe(new ItemStack(Item.INK_SACK, 2, 5), new Object[] { new ItemStack(Item.INK_SACK, true, 4), new ItemStack(Item.INK_SACK, true, true) });
/*    */     
/* 46 */     paramCraftingManager.registerShapelessRecipe(new ItemStack(Item.INK_SACK, 2, 13), new Object[] { new ItemStack(Item.INK_SACK, true, 5), new ItemStack(Item.INK_SACK, true, 9) });
/*    */     
/* 48 */     paramCraftingManager.registerShapelessRecipe(new ItemStack(Item.INK_SACK, 3, 13), new Object[] { new ItemStack(Item.INK_SACK, true, 4), new ItemStack(Item.INK_SACK, true, true), new ItemStack(Item.INK_SACK, true, 9) });
/*    */     
/* 50 */     paramCraftingManager.registerShapelessRecipe(new ItemStack(Item.INK_SACK, 4, 13), new Object[] { new ItemStack(Item.INK_SACK, true, 4), new ItemStack(Item.INK_SACK, true, true), new ItemStack(Item.INK_SACK, true, true), new ItemStack(Item.INK_SACK, true, 15) });
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\RecipesDyes.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */