/*    */ package net.minecraft.server;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class FurnaceRecipes
/*    */ {
/* 10 */   private static final FurnaceRecipes a = new FurnaceRecipes();
/*    */   
/*    */   private Map b;
/*    */ 
/*    */   
/* 15 */   public static final FurnaceRecipes getInstance() { return a; }
/*    */ 
/*    */   
/*    */   private FurnaceRecipes() {
/*    */     this.b = new HashMap();
/* 20 */     registerRecipe(Block.IRON_ORE.id, new ItemStack(Item.IRON_INGOT));
/* 21 */     registerRecipe(Block.GOLD_ORE.id, new ItemStack(Item.GOLD_INGOT));
/* 22 */     registerRecipe(Block.DIAMOND_ORE.id, new ItemStack(Item.DIAMOND));
/* 23 */     registerRecipe(Block.SAND.id, new ItemStack(Block.GLASS));
/* 24 */     registerRecipe(Item.PORK.id, new ItemStack(Item.GRILLED_PORK));
/* 25 */     registerRecipe(Item.RAW_FISH.id, new ItemStack(Item.COOKED_FISH));
/* 26 */     registerRecipe(Block.COBBLESTONE.id, new ItemStack(Block.STONE));
/* 27 */     registerRecipe(Item.CLAY_BALL.id, new ItemStack(Item.CLAY_BRICK));
/* 28 */     registerRecipe(Block.CACTUS.id, new ItemStack(Item.INK_SACK, true, 2));
/* 29 */     registerRecipe(Block.LOG.id, new ItemStack(Item.COAL, true, true));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 35 */   public void registerRecipe(int paramInt, ItemStack paramItemStack) { this.b.put(Integer.valueOf(paramInt), paramItemStack); }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 43 */   public ItemStack a(int paramInt) { return (ItemStack)this.b.get(Integer.valueOf(paramInt)); }
/*    */ 
/*    */ 
/*    */   
/* 47 */   public Map b() { return this.b; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\FurnaceRecipes.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */