/*    */ package org.bukkit.inventory;
/*    */ 
/*    */ import org.bukkit.Material;
/*    */ import org.bukkit.material.MaterialData;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class FurnaceRecipe
/*    */   implements Recipe
/*    */ {
/*    */   private ItemStack output;
/*    */   private MaterialData ingredient;
/*    */   
/*    */   public FurnaceRecipe(ItemStack result, Material source) {
/* 19 */     this(result, source.getNewData((byte)0));
/* 20 */     if (this.ingredient == null) {
/* 21 */       setInput(new MaterialData(source));
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public FurnaceRecipe(ItemStack result, MaterialData source) {
/* 31 */     this.output = result;
/* 32 */     this.ingredient = source;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public FurnaceRecipe setInput(MaterialData input) {
/* 41 */     this.ingredient = input;
/* 42 */     return this;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public FurnaceRecipe setInput(Material input) {
/* 51 */     setInput(input.getNewData((byte)0));
/* 52 */     if (this.ingredient == null) {
/* 53 */       setInput(new MaterialData(input));
/*    */     }
/* 55 */     return this;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 63 */   public MaterialData getInput() { return this.ingredient; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 71 */   public ItemStack getResult() { return this.output; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\inventory\FurnaceRecipe.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */