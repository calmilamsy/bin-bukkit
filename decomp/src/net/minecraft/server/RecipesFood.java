/*    */ package net.minecraft.server;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class RecipesFood
/*    */ {
/*    */   public void a(CraftingManager paramCraftingManager) {
/* 11 */     paramCraftingManager.registerShapedRecipe(new ItemStack(Item.MUSHROOM_SOUP), new Object[] { "Y", "X", "#", Character.valueOf('X'), Block.BROWN_MUSHROOM, Character.valueOf('Y'), Block.RED_MUSHROOM, Character.valueOf('#'), Item.BOWL });
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 20 */     paramCraftingManager.registerShapedRecipe(new ItemStack(Item.MUSHROOM_SOUP), new Object[] { "Y", "X", "#", Character.valueOf('X'), Block.RED_MUSHROOM, Character.valueOf('Y'), Block.BROWN_MUSHROOM, Character.valueOf('#'), Item.BOWL });
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 29 */     paramCraftingManager.registerShapedRecipe(new ItemStack(Item.COOKIE, 8), new Object[] { "#X#", Character.valueOf('X'), new ItemStack(Item.INK_SACK, true, 3), Character.valueOf('#'), Item.WHEAT });
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\RecipesFood.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */