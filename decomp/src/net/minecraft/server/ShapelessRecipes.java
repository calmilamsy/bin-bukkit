/*    */ package net.minecraft.server;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ public class ShapelessRecipes
/*    */   implements CraftingRecipe
/*    */ {
/*    */   private final ItemStack a;
/*    */   private final List b;
/*    */   
/*    */   public ShapelessRecipes(ItemStack paramItemStack, List paramList) {
/* 14 */     this.a = paramItemStack;
/* 15 */     this.b = paramList;
/*    */   }
/*    */ 
/*    */   
/* 19 */   public ItemStack b() { return this.a; }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean a(InventoryCrafting paramInventoryCrafting) {
/* 24 */     ArrayList arrayList = new ArrayList(this.b);
/*    */     
/* 26 */     for (byte b1 = 0; b1 < 3; b1++) {
/* 27 */       for (byte b2 = 0; b2 < 3; b2++) {
/* 28 */         ItemStack itemStack = paramInventoryCrafting.b(b2, b1);
/*    */         
/* 30 */         if (itemStack != null) {
/* 31 */           boolean bool = false;
/* 32 */           for (ItemStack itemStack1 : arrayList) {
/* 33 */             if (itemStack.id == itemStack1.id && (itemStack1.getData() == -1 || itemStack.getData() == itemStack1.getData())) {
/* 34 */               bool = true;
/* 35 */               arrayList.remove(itemStack1);
/*    */               break;
/*    */             } 
/*    */           } 
/* 39 */           if (!bool) {
/* 40 */             return false;
/*    */           }
/*    */         } 
/*    */       } 
/*    */     } 
/*    */     
/* 46 */     return arrayList.isEmpty();
/*    */   }
/*    */ 
/*    */   
/* 50 */   public ItemStack b(InventoryCrafting paramInventoryCrafting) { return this.a.cloneItemStack(); }
/*    */ 
/*    */ 
/*    */   
/* 54 */   public int a() { return this.b.size(); }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\ShapelessRecipes.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */