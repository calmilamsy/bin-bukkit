/*    */ package net.minecraft.server;
/*    */ 
/*    */ public class InventoryLargeChest
/*    */   implements IInventory
/*    */ {
/*    */   private String a;
/*    */   private IInventory b;
/*    */   private IInventory c;
/*    */   
/*    */   public ItemStack[] getContents() {
/* 11 */     ItemStack[] result = new ItemStack[getSize()];
/* 12 */     for (int i = 0; i < result.length; i++) {
/* 13 */       result[i] = getItem(i);
/*    */     }
/* 15 */     return result;
/*    */   }
/*    */ 
/*    */   
/*    */   public InventoryLargeChest(String s, IInventory iinventory, IInventory iinventory1) {
/* 20 */     this.a = s;
/* 21 */     this.b = iinventory;
/* 22 */     this.c = iinventory1;
/*    */   }
/*    */ 
/*    */   
/* 26 */   public int getSize() { return this.b.getSize() + this.c.getSize(); }
/*    */ 
/*    */ 
/*    */   
/* 30 */   public String getName() { return this.a; }
/*    */ 
/*    */ 
/*    */   
/* 34 */   public ItemStack getItem(int i) { return (i >= this.b.getSize()) ? this.c.getItem(i - this.b.getSize()) : this.b.getItem(i); }
/*    */ 
/*    */ 
/*    */   
/* 38 */   public ItemStack splitStack(int i, int j) { return (i >= this.b.getSize()) ? this.c.splitStack(i - this.b.getSize(), j) : this.b.splitStack(i, j); }
/*    */ 
/*    */   
/*    */   public void setItem(int i, ItemStack itemstack) {
/* 42 */     if (i >= this.b.getSize()) {
/* 43 */       this.c.setItem(i - this.b.getSize(), itemstack);
/*    */     } else {
/* 45 */       this.b.setItem(i, itemstack);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/* 50 */   public int getMaxStackSize() { return this.b.getMaxStackSize(); }
/*    */ 
/*    */   
/*    */   public void update() {
/* 54 */     this.b.update();
/* 55 */     this.c.update();
/*    */   }
/*    */ 
/*    */   
/* 59 */   public boolean a_(EntityHuman entityhuman) { return (this.b.a_(entityhuman) && this.c.a_(entityhuman)); }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\InventoryLargeChest.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */