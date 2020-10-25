/*    */ package net.minecraft.server;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class RecipesCrafting
/*    */ {
/*    */   public void a(CraftingManager paramCraftingManager) {
/*  8 */     paramCraftingManager.registerShapedRecipe(new ItemStack(Block.CHEST), new Object[] { "###", "# #", "###", Character.valueOf('#'), Block.WOOD });
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 15 */     paramCraftingManager.registerShapedRecipe(new ItemStack(Block.FURNACE), new Object[] { "###", "# #", "###", Character.valueOf('#'), Block.COBBLESTONE });
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 22 */     paramCraftingManager.registerShapedRecipe(new ItemStack(Block.WORKBENCH), new Object[] { "##", "##", Character.valueOf('#'), Block.WOOD });
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 28 */     paramCraftingManager.registerShapedRecipe(new ItemStack(Block.SANDSTONE), new Object[] { "##", "##", Character.valueOf('#'), Block.SAND });
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\RecipesCrafting.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */