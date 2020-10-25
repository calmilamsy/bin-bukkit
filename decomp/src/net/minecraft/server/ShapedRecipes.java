/*    */ package net.minecraft.server;
/*    */ 
/*    */ public class ShapedRecipes
/*    */   implements CraftingRecipe
/*    */ {
/*    */   private int b;
/*    */   private int c;
/*    */   private ItemStack[] d;
/*    */   private ItemStack e;
/*    */   public final int a;
/*    */   
/*    */   public ShapedRecipes(int paramInt1, int paramInt2, ItemStack[] paramArrayOfItemStack, ItemStack paramItemStack) {
/* 13 */     this.a = paramItemStack.id;
/* 14 */     this.b = paramInt1;
/* 15 */     this.c = paramInt2;
/* 16 */     this.d = paramArrayOfItemStack;
/* 17 */     this.e = paramItemStack;
/*    */   }
/*    */ 
/*    */   
/* 21 */   public ItemStack b() { return this.e; }
/*    */ 
/*    */   
/*    */   public boolean a(InventoryCrafting paramInventoryCrafting) {
/* 25 */     for (byte b1 = 0; b1 <= 3 - this.b; b1++) {
/* 26 */       for (byte b2 = 0; b2 <= 3 - this.c; b2++) {
/* 27 */         if (a(paramInventoryCrafting, b1, b2, true)) return true; 
/* 28 */         if (a(paramInventoryCrafting, b1, b2, false)) return true; 
/*    */       } 
/*    */     } 
/* 31 */     return false;
/*    */   }
/*    */   
/*    */   private boolean a(InventoryCrafting paramInventoryCrafting, int paramInt1, int paramInt2, boolean paramBoolean) {
/* 35 */     for (int i = 0; i < 3; i++) {
/* 36 */       for (int j = 0; j < 3; j++) {
/* 37 */         int k = i - paramInt1;
/* 38 */         int m = j - paramInt2;
/* 39 */         ItemStack itemStack1 = null;
/* 40 */         if (k >= 0 && m >= 0 && k < this.b && m < this.c)
/* 41 */           if (paramBoolean) { itemStack1 = this.d[this.b - k - 1 + m * this.b]; }
/* 42 */           else { itemStack1 = this.d[k + m * this.b]; }
/*    */            
/* 44 */         ItemStack itemStack2 = paramInventoryCrafting.b(i, j);
/* 45 */         if (itemStack2 != null || itemStack1 != null) {
/*    */ 
/*    */           
/* 48 */           if ((itemStack2 == null && itemStack1 != null) || (itemStack2 != null && itemStack1 == null)) {
/* 49 */             return false;
/*    */           }
/* 51 */           if (itemStack1.id != itemStack2.id) {
/* 52 */             return false;
/*    */           }
/* 54 */           if (itemStack1.getData() != -1 && itemStack1.getData() != itemStack2.getData())
/* 55 */             return false; 
/*    */         } 
/*    */       } 
/*    */     } 
/* 59 */     return true;
/*    */   }
/*    */ 
/*    */   
/* 63 */   public ItemStack b(InventoryCrafting paramInventoryCrafting) { return new ItemStack(this.e.id, this.e.count, this.e.getData()); }
/*    */ 
/*    */ 
/*    */   
/* 67 */   public int a() { return this.b * this.c; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\ShapedRecipes.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */