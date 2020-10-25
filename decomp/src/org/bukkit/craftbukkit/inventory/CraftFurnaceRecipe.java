/*    */ package org.bukkit.craftbukkit.inventory;
/*    */ 
/*    */ import net.minecraft.server.FurnaceRecipes;
/*    */ import net.minecraft.server.ItemStack;
/*    */ import org.bukkit.Material;
/*    */ import org.bukkit.inventory.FurnaceRecipe;
/*    */ import org.bukkit.inventory.ItemStack;
/*    */ import org.bukkit.material.MaterialData;
/*    */ 
/*    */ public class CraftFurnaceRecipe
/*    */   extends FurnaceRecipe implements CraftRecipe {
/* 12 */   public CraftFurnaceRecipe(ItemStack result, Material source) { super(result, source); }
/*    */ 
/*    */ 
/*    */   
/* 16 */   public CraftFurnaceRecipe(ItemStack result, MaterialData source) { super(result, source); }
/*    */ 
/*    */   
/*    */   public static CraftFurnaceRecipe fromBukkitRecipe(FurnaceRecipe recipe) {
/* 20 */     if (recipe instanceof CraftFurnaceRecipe) {
/* 21 */       return (CraftFurnaceRecipe)recipe;
/*    */     }
/* 23 */     return new CraftFurnaceRecipe(recipe.getResult(), recipe.getInput());
/*    */   }
/*    */   
/*    */   public void addToCraftingManager() {
/* 27 */     ItemStack result = getResult();
/* 28 */     MaterialData input = getInput();
/* 29 */     int id = result.getTypeId();
/* 30 */     int amount = result.getAmount();
/* 31 */     int dmg = result.getDurability();
/* 32 */     FurnaceRecipes.getInstance().registerRecipe(input.getItemTypeId(), new ItemStack(id, amount, dmg));
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\craftbukkit\inventory\CraftFurnaceRecipe.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */