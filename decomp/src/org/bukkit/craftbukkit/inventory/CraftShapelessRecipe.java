/*    */ package org.bukkit.craftbukkit.inventory;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import net.minecraft.server.CraftingManager;
/*    */ import net.minecraft.server.ItemStack;
/*    */ import org.bukkit.inventory.ItemStack;
/*    */ import org.bukkit.inventory.ShapelessRecipe;
/*    */ import org.bukkit.material.MaterialData;
/*    */ 
/*    */ public class CraftShapelessRecipe
/*    */   extends ShapelessRecipe
/*    */   implements CraftRecipe {
/* 13 */   public CraftShapelessRecipe(ItemStack result) { super(result); }
/*    */ 
/*    */   
/*    */   public static CraftShapelessRecipe fromBukkitRecipe(ShapelessRecipe recipe) {
/* 17 */     if (recipe instanceof CraftShapelessRecipe) {
/* 18 */       return (CraftShapelessRecipe)recipe;
/*    */     }
/* 20 */     CraftShapelessRecipe ret = new CraftShapelessRecipe(recipe.getResult());
/* 21 */     for (MaterialData ingred : recipe.getIngredientList()) {
/* 22 */       ret.addIngredient(ingred);
/*    */     }
/* 24 */     return ret;
/*    */   }
/*    */   
/*    */   public void addToCraftingManager() {
/* 28 */     ArrayList<MaterialData> ingred = getIngredientList();
/* 29 */     Object[] data = new Object[ingred.size()];
/* 30 */     int i = 0;
/* 31 */     for (MaterialData mdata : ingred) {
/* 32 */       int id = mdata.getItemTypeId();
/* 33 */       byte dmg = mdata.getData();
/* 34 */       data[i] = new ItemStack(id, true, dmg);
/* 35 */       i++;
/*    */     } 
/* 37 */     int id = getResult().getTypeId();
/* 38 */     int amount = getResult().getAmount();
/* 39 */     short durability = getResult().getDurability();
/* 40 */     CraftingManager.getInstance().registerShapelessRecipe(new ItemStack(id, amount, durability), data);
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\craftbukkit\inventory\CraftShapelessRecipe.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */