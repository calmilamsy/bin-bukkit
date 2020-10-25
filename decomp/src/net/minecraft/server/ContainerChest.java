/*    */ package net.minecraft.server;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ContainerChest
/*    */   extends Container
/*    */ {
/*    */   private IInventory a;
/*    */   private int b;
/*    */   
/*    */   public ContainerChest(IInventory paramIInventory1, IInventory paramIInventory2) {
/* 12 */     this.a = paramIInventory2;
/* 13 */     this.b = paramIInventory2.getSize() / 9;
/*    */     
/* 15 */     int i = (this.b - 4) * 18;
/*    */     byte b1;
/* 17 */     for (b1 = 0; b1 < this.b; b1++) {
/* 18 */       for (byte b2 = 0; b2 < 9; b2++) {
/* 19 */         a(new Slot(paramIInventory2, b2 + b1 * 9, 8 + b2 * 18, 18 + b1 * 18));
/*    */       }
/*    */     } 
/*    */     
/* 23 */     for (b1 = 0; b1 < 3; b1++) {
/* 24 */       for (byte b2 = 0; b2 < 9; b2++) {
/* 25 */         a(new Slot(paramIInventory1, b2 + b1 * 9 + 9, 8 + b2 * 18, 103 + b1 * 18 + i));
/*    */       }
/*    */     } 
/* 28 */     for (b1 = 0; b1 < 9; b1++) {
/* 29 */       a(new Slot(paramIInventory1, b1, 8 + b1 * 18, 161 + i));
/*    */     }
/*    */   }
/*    */ 
/*    */   
/* 34 */   public boolean b(EntityHuman paramEntityHuman) { return this.a.a_(paramEntityHuman); }
/*    */ 
/*    */ 
/*    */   
/*    */   public ItemStack a(int paramInt) {
/* 39 */     ItemStack itemStack = null;
/* 40 */     Slot slot = (Slot)this.e.get(paramInt);
/* 41 */     if (slot != null && slot.b()) {
/* 42 */       ItemStack itemStack1 = slot.getItem();
/* 43 */       itemStack = itemStack1.cloneItemStack();
/*    */       
/* 45 */       if (paramInt < this.b * 9) {
/* 46 */         a(itemStack1, this.b * 9, this.e.size(), true);
/*    */       } else {
/* 48 */         a(itemStack1, 0, this.b * 9, false);
/*    */       } 
/* 50 */       if (itemStack1.count == 0) {
/* 51 */         slot.c(null);
/*    */       } else {
/* 53 */         slot.c();
/*    */       } 
/*    */     } 
/* 56 */     return itemStack;
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\ContainerChest.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */