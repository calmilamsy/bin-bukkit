/*    */ package net.minecraft.server;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class RecipeIngots
/*    */ {
/*  9 */   private Object[][] a = { { Block.GOLD_BLOCK, new ItemStack(Item.GOLD_INGOT, 9) }, { Block.IRON_BLOCK, new ItemStack(Item.IRON_INGOT, 9) }, { Block.DIAMOND_BLOCK, new ItemStack(Item.DIAMOND, 9) }, { Block.LAPIS_BLOCK, new ItemStack(Item.INK_SACK, 9, 4) } };
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void a(CraftingManager paramCraftingManager) {
/* 22 */     for (byte b = 0; b < this.a.length; b++) {
/* 23 */       Block block = (Block)this.a[b][0];
/* 24 */       ItemStack itemStack = (ItemStack)this.a[b][1];
/* 25 */       paramCraftingManager.registerShapedRecipe(new ItemStack(block), new Object[] { "###", "###", "###", Character.valueOf('#'), itemStack });
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */       
/* 32 */       paramCraftingManager.registerShapedRecipe(itemStack, new Object[] { "#", Character.valueOf('#'), block });
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\RecipeIngots.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */