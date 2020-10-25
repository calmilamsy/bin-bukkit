/*    */ package net.minecraft.server;
/*    */ 
/*    */ 
/*    */ public class InventoryCrafting
/*    */   implements IInventory
/*    */ {
/*    */   private ItemStack[] items;
/*    */   private int b;
/*    */   private Container c;
/*    */   
/* 11 */   public ItemStack[] getContents() { return this.items; }
/*    */ 
/*    */ 
/*    */   
/*    */   public InventoryCrafting(Container container, int i, int j) {
/* 16 */     int k = i * j;
/*    */     
/* 18 */     this.items = new ItemStack[k];
/* 19 */     this.c = container;
/* 20 */     this.b = i;
/*    */   }
/*    */ 
/*    */   
/* 24 */   public int getSize() { return this.items.length; }
/*    */ 
/*    */ 
/*    */   
/* 28 */   public ItemStack getItem(int i) { return (i >= getSize()) ? null : this.items[i]; }
/*    */ 
/*    */   
/*    */   public ItemStack b(int i, int j) {
/* 32 */     if (i >= 0 && i < this.b) {
/* 33 */       int k = i + j * this.b;
/*    */       
/* 35 */       return getItem(k);
/*    */     } 
/* 37 */     return null;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/* 42 */   public String getName() { return "Crafting"; }
/*    */ 
/*    */   
/*    */   public ItemStack splitStack(int i, int j) {
/* 46 */     if (this.items[i] != null) {
/*    */ 
/*    */       
/* 49 */       if ((this.items[i]).count <= j) {
/* 50 */         ItemStack itemstack = this.items[i];
/* 51 */         this.items[i] = null;
/* 52 */         this.c.a(this);
/* 53 */         return itemstack;
/*    */       } 
/* 55 */       ItemStack itemstack = this.items[i].a(j);
/* 56 */       if ((this.items[i]).count == 0) {
/* 57 */         this.items[i] = null;
/*    */       }
/*    */       
/* 60 */       this.c.a(this);
/* 61 */       return itemstack;
/*    */     } 
/*    */     
/* 64 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setItem(int i, ItemStack itemstack) {
/* 69 */     this.items[i] = itemstack;
/* 70 */     this.c.a(this);
/*    */   }
/*    */ 
/*    */   
/* 74 */   public int getMaxStackSize() { return 64; }
/*    */ 
/*    */   
/*    */   public void update() {}
/*    */ 
/*    */   
/* 80 */   public boolean a_(EntityHuman entityhuman) { return true; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\InventoryCrafting.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */