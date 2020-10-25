/*    */ package net.minecraft.server;
/*    */ 
/*    */ public class Slot
/*    */ {
/*    */   public final int index;
/*    */   public final IInventory inventory;
/*    */   public int a;
/*    */   public int b;
/*    */   public int c;
/*    */   
/*    */   public Slot(IInventory iinventory, int i, int j, int k) {
/* 12 */     this.inventory = iinventory;
/* 13 */     this.index = i;
/* 14 */     this.b = j;
/* 15 */     this.c = k;
/*    */   }
/*    */ 
/*    */   
/* 19 */   public void a(ItemStack itemstack) { c(); }
/*    */ 
/*    */ 
/*    */   
/* 23 */   public boolean isAllowed(ItemStack itemstack) { return true; }
/*    */ 
/*    */ 
/*    */   
/* 27 */   public ItemStack getItem() { return this.inventory.getItem(this.index); }
/*    */ 
/*    */ 
/*    */   
/* 31 */   public boolean b() { return (getItem() != null); }
/*    */ 
/*    */   
/*    */   public void c(ItemStack itemstack) {
/* 35 */     this.inventory.setItem(this.index, itemstack);
/* 36 */     c();
/*    */   }
/*    */ 
/*    */   
/* 40 */   public void c() { this.inventory.update(); }
/*    */ 
/*    */ 
/*    */   
/* 44 */   public int d() { return this.inventory.getMaxStackSize(); }
/*    */ 
/*    */ 
/*    */   
/* 48 */   public ItemStack a(int i) { return this.inventory.splitStack(this.index, i); }
/*    */ 
/*    */ 
/*    */   
/* 52 */   public boolean a(IInventory iinventory, int i) { return (iinventory == this.inventory && i == this.index); }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\Slot.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */