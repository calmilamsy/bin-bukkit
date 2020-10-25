/*    */ package net.minecraft.server;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class RecipesArmor
/*    */ {
/*  7 */   private String[][] a = { { "XXX", "X X" }, { "X X", "XXX", "XXX" }, { "XXX", "X X", "X X" }, { "X X", "X X" } };
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
/* 23 */   private Object[][] b = { { Item.LEATHER, Block.FIRE, Item.IRON_INGOT, Item.DIAMOND, Item.GOLD_INGOT }, { Item.LEATHER_HELMET, Item.CHAINMAIL_HELMET, Item.IRON_HELMET, Item.DIAMOND_HELMET, Item.GOLD_HELMET }, { Item.LEATHER_CHESTPLATE, Item.CHAINMAIL_CHESTPLATE, Item.IRON_CHESTPLATE, Item.DIAMOND_CHESTPLATE, Item.GOLD_CHESTPLATE }, { Item.LEATHER_LEGGINGS, Item.CHAINMAIL_LEGGINGS, Item.IRON_LEGGINGS, Item.DIAMOND_LEGGINGS, Item.GOLD_LEGGINGS }, { Item.LEATHER_BOOTS, Item.CHAINMAIL_BOOTS, Item.IRON_BOOTS, Item.DIAMOND_BOOTS, Item.GOLD_BOOTS } };
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void a(CraftingManager paramCraftingManager) {
/* 32 */     for (byte b1 = 0; b1 < this.b[0].length; b1++) {
/* 33 */       Object object = this.b[0][b1];
/*    */       
/* 35 */       for (byte b2 = 0; b2 < this.b.length - 1; b2++) {
/* 36 */         Item item = (Item)this.b[b2 + true][b1];
/*    */         
/* 38 */         paramCraftingManager.registerShapedRecipe(new ItemStack(item), new Object[] { this.a[b2], Character.valueOf('X'), object });
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\RecipesArmor.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */