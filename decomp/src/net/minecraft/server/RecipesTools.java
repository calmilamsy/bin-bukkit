/*    */ package net.minecraft.server;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class RecipesTools
/*    */ {
/*  9 */   private String[][] a = { { "XXX", " # ", " # " }, { "X", "#", "#" }, { "XX", "X#", " #" }, { "XX", " #", " #" } };
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 27 */   private Object[][] b = { { Block.WOOD, Block.COBBLESTONE, Item.IRON_INGOT, Item.DIAMOND, Item.GOLD_INGOT }, { Item.WOOD_PICKAXE, Item.STONE_PICKAXE, Item.IRON_PICKAXE, Item.DIAMOND_PICKAXE, Item.GOLD_PICKAXE }, { Item.WOOD_SPADE, Item.STONE_SPADE, Item.IRON_SPADE, Item.DIAMOND_SPADE, Item.GOLD_SPADE }, { Item.WOOD_AXE, Item.STONE_AXE, Item.IRON_AXE, Item.DIAMOND_AXE, Item.GOLD_AXE }, { Item.WOOD_HOE, Item.STONE_HOE, Item.IRON_HOE, Item.DIAMOND_HOE, Item.GOLD_HOE } };
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void a(CraftingManager paramCraftingManager) {
/* 37 */     for (byte b1 = 0; b1 < this.b[0].length; b1++) {
/* 38 */       Object object = this.b[0][b1];
/*    */       
/* 40 */       for (byte b2 = 0; b2 < this.b.length - 1; b2++) {
/* 41 */         Item item = (Item)this.b[b2 + true][b1];
/* 42 */         paramCraftingManager.registerShapedRecipe(new ItemStack(item), new Object[] { this.a[b2], Character.valueOf('#'), Item.STICK, Character.valueOf('X'), object });
/*    */       } 
/*    */     } 
/*    */ 
/*    */ 
/*    */     
/* 48 */     paramCraftingManager.registerShapedRecipe(new ItemStack(Item.SHEARS), new Object[] { " #", "# ", Character.valueOf('#'), Item.IRON_INGOT });
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\RecipesTools.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */