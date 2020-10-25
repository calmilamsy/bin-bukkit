/*     */ package org.bukkit.inventory;
/*     */ 
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.material.MaterialData;
/*     */ 
/*     */ public class ShapedRecipe implements Recipe {
/*     */   private ItemStack output;
/*     */   private String[] rows;
/*     */   private HashMap<Character, MaterialData> ingredients;
/*     */   
/*     */   public ShapedRecipe(ItemStack result) {
/*  14 */     this.ingredients = new HashMap();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  26 */     this.output = result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ShapedRecipe shape(String... shape) {
/*  36 */     if (shape == null || shape.length > 3 || shape.length < 1) {
/*  37 */       throw new IllegalArgumentException("Crafting recipes should be 1, 2, or 3 rows.");
/*     */     }
/*  39 */     for (String row : shape) {
/*  40 */       if (row == null || row.length() > 3 || row.length() < 1) {
/*  41 */         throw new IllegalArgumentException("Crafting rows should be 1, 2, or 3 characters.");
/*     */       }
/*     */     } 
/*  44 */     this.rows = shape;
/*     */ 
/*     */     
/*  47 */     HashMap<Character, MaterialData> ingredientsTemp = this.ingredients;
/*     */     
/*  49 */     this.ingredients = new HashMap();
/*  50 */     for (Iterator i$ = ingredientsTemp.keySet().iterator(); i$.hasNext(); ) { char key = ((Character)i$.next()).charValue();
/*     */       try {
/*  52 */         setIngredient(key, (MaterialData)ingredientsTemp.get(Character.valueOf(key)));
/*  53 */       } catch (IllegalArgumentException e) {} }
/*     */     
/*  55 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ShapedRecipe setIngredient(char key, MaterialData ingredient) {
/*  65 */     if (!hasKey(key)) {
/*  66 */       throw new IllegalArgumentException("Symbol " + key + " does not appear in the shape.");
/*     */     }
/*  68 */     this.ingredients.put(Character.valueOf(key), ingredient);
/*  69 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  79 */   public ShapedRecipe setIngredient(char key, Material ingredient) { return setIngredient(key, ingredient, 0); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ShapedRecipe setIngredient(char key, Material ingredient, int raw) {
/*  90 */     MaterialData data = ingredient.getNewData((byte)raw);
/*     */     
/*  92 */     if (data == null) {
/*  93 */       data = new MaterialData(ingredient, (byte)raw);
/*     */     }
/*  95 */     return setIngredient(key, data);
/*     */   }
/*     */   
/*     */   private boolean hasKey(char c) {
/*  99 */     String key = Character.toString(c);
/*     */     
/* 101 */     for (String row : this.rows) {
/* 102 */       if (row.contains(key)) {
/* 103 */         return true;
/*     */       }
/*     */     } 
/* 106 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 114 */   public HashMap<Character, MaterialData> getIngredientMap() { return this.ingredients; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 122 */   public String[] getShape() { return this.rows; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 130 */   public ItemStack getResult() { return this.output; }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\inventory\ShapedRecipe.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */