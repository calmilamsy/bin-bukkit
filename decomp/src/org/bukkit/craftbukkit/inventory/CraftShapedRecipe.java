/*    */ package org.bukkit.craftbukkit.inventory;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ import java.util.Iterator;
/*    */ import net.minecraft.server.CraftingManager;
/*    */ import net.minecraft.server.ItemStack;
/*    */ import org.bukkit.inventory.ItemStack;
/*    */ import org.bukkit.inventory.ShapedRecipe;
/*    */ import org.bukkit.material.MaterialData;
/*    */ 
/*    */ public class CraftShapedRecipe
/*    */   extends ShapedRecipe implements CraftRecipe {
/* 13 */   public CraftShapedRecipe(ItemStack result) { super(result); }
/*    */ 
/*    */   
/*    */   public static CraftShapedRecipe fromBukkitRecipe(ShapedRecipe recipe) {
/* 17 */     if (recipe instanceof CraftShapedRecipe) {
/* 18 */       return (CraftShapedRecipe)recipe;
/*    */     }
/* 20 */     CraftShapedRecipe ret = new CraftShapedRecipe(recipe.getResult());
/* 21 */     String[] shape = recipe.getShape();
/* 22 */     ret.shape(shape);
/* 23 */     for (Iterator i$ = recipe.getIngredientMap().keySet().iterator(); i$.hasNext(); ) { char c = ((Character)i$.next()).charValue();
/* 24 */       ret.setIngredient(c, (MaterialData)recipe.getIngredientMap().get(Character.valueOf(c))); }
/*    */     
/* 26 */     return ret;
/*    */   }
/*    */ 
/*    */   
/*    */   public void addToCraftingManager() {
/* 31 */     String[] shape = getShape();
/* 32 */     HashMap<Character, MaterialData> ingred = getIngredientMap();
/* 33 */     int datalen = shape.length;
/* 34 */     datalen += ingred.size() * 2;
/* 35 */     int i = 0;
/* 36 */     Object[] data = new Object[datalen];
/* 37 */     for (; i < shape.length; i++) {
/* 38 */       data[i] = shape[i];
/*    */     }
/* 40 */     for (Iterator i$ = ingred.keySet().iterator(); i$.hasNext(); ) { char c = ((Character)i$.next()).charValue();
/* 41 */       data[i] = Character.valueOf(c);
/* 42 */       i++;
/* 43 */       MaterialData mdata = (MaterialData)ingred.get(Character.valueOf(c));
/* 44 */       int id = mdata.getItemTypeId();
/* 45 */       byte dmg = mdata.getData();
/* 46 */       data[i] = new ItemStack(id, true, dmg);
/* 47 */       i++; }
/*    */     
/* 49 */     int id = getResult().getTypeId();
/* 50 */     int amount = getResult().getAmount();
/* 51 */     short durability = getResult().getDurability();
/* 52 */     CraftingManager.getInstance().registerShapedRecipe(new ItemStack(id, amount, durability), data);
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\craftbukkit\inventory\CraftShapedRecipe.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */