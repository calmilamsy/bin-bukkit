/*    */ package net.minecraft.server;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class RecipesWeapons
/*    */ {
/*  7 */   private String[][] a = { { "X", "X", "#" } };
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 13 */   private Object[][] b = { { Block.WOOD, Block.COBBLESTONE, Item.IRON_INGOT, Item.DIAMOND, Item.GOLD_INGOT }, { Item.WOOD_SWORD, Item.STONE_SWORD, Item.IRON_SWORD, Item.DIAMOND_SWORD, Item.GOLD_SWORD } };
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void a(CraftingManager paramCraftingManager) {
/* 19 */     for (byte b1 = 0; b1 < this.b[0].length; b1++) {
/* 20 */       Object object = this.b[0][b1];
/*    */       
/* 22 */       for (byte b2 = 0; b2 < this.b.length - 1; b2++) {
/* 23 */         Item item = (Item)this.b[b2 + true][b1];
/* 24 */         paramCraftingManager.registerShapedRecipe(new ItemStack(item), new Object[] { this.a[b2], Character.valueOf('#'), Item.STICK, Character.valueOf('X'), object });
/*    */       } 
/*    */     } 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 32 */     paramCraftingManager.registerShapedRecipe(new ItemStack(Item.BOW, true), new Object[] { " #X", "# X", " #X", Character.valueOf('X'), Item.STRING, Character.valueOf('#'), Item.STICK });
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 40 */     paramCraftingManager.registerShapedRecipe(new ItemStack(Item.ARROW, 4), new Object[] { "X", "#", "Y", Character.valueOf('Y'), Item.FEATHER, Character.valueOf('X'), Item.FLINT, Character.valueOf('#'), Item.STICK });
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\RecipesWeapons.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */