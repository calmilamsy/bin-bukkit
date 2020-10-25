/*    */ package net.minecraft.server;
/*    */ 
/*    */ public class InventoryCraftResult
/*    */   implements IInventory {
/*  5 */   private ItemStack[] items = new ItemStack[1];
/*    */ 
/*    */ 
/*    */   
/*  9 */   public ItemStack[] getContents() { return this.items; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 16 */   public int getSize() { return 1; }
/*    */ 
/*    */ 
/*    */   
/* 20 */   public ItemStack getItem(int i) { return this.items[i]; }
/*    */ 
/*    */ 
/*    */   
/* 24 */   public String getName() { return "Result"; }
/*    */ 
/*    */   
/*    */   public ItemStack splitStack(int i, int j) {
/* 28 */     if (this.items[i] != null) {
/* 29 */       ItemStack itemstack = this.items[i];
/*    */       
/* 31 */       this.items[i] = null;
/* 32 */       return itemstack;
/*    */     } 
/* 34 */     return null;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/* 39 */   public void setItem(int i, ItemStack itemstack) { this.items[i] = itemstack; }
/*    */ 
/*    */ 
/*    */   
/* 43 */   public int getMaxStackSize() { return 64; }
/*    */ 
/*    */   
/*    */   public void update() {}
/*    */ 
/*    */   
/* 49 */   public boolean a_(EntityHuman entityhuman) { return true; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\InventoryCraftResult.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */