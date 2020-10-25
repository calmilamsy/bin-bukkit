/*     */ package org.bukkit.inventory;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.material.MaterialData;
/*     */ 
/*     */ public class ShapelessRecipe
/*     */   implements Recipe
/*     */ {
/*     */   private ItemStack output;
/*     */   private ArrayList<MaterialData> ingredients;
/*     */   
/*     */   public ShapelessRecipe(ItemStack result) {
/*  14 */     this.ingredients = new ArrayList();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  24 */     this.output = result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  33 */   public ShapelessRecipe addIngredient(MaterialData ingredient) { return addIngredient(1, ingredient); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  42 */   public ShapelessRecipe addIngredient(Material ingredient) { return addIngredient(1, ingredient, 0); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  52 */   public ShapelessRecipe addIngredient(Material ingredient, int rawdata) { return addIngredient(1, ingredient, rawdata); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ShapelessRecipe addIngredient(int count, MaterialData ingredient) {
/*  62 */     if (this.ingredients.size() + count > 9) {
/*  63 */       throw new IllegalArgumentException("Shapeless recipes cannot have more than 9 ingredients");
/*     */     }
/*  65 */     while (count-- > 0) {
/*  66 */       this.ingredients.add(ingredient);
/*     */     }
/*  68 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  78 */   public ShapelessRecipe addIngredient(int count, Material ingredient) { return addIngredient(count, ingredient, 0); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ShapelessRecipe addIngredient(int count, Material ingredient, int rawdata) {
/*  89 */     MaterialData data = ingredient.getNewData((byte)rawdata);
/*     */     
/*  91 */     if (data == null) {
/*  92 */       data = new MaterialData(ingredient, (byte)rawdata);
/*     */     }
/*  94 */     return addIngredient(count, data);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ShapelessRecipe removeIngredient(MaterialData ingredient) {
/* 104 */     this.ingredients.remove(ingredient);
/* 105 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 113 */   public ItemStack getResult() { return this.output; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 121 */   public ArrayList<MaterialData> getIngredientList() { return this.ingredients; }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\inventory\ShapelessRecipe.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */