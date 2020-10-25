/*    */ package net.minecraft.server;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class SlotArmor
/*    */   extends Slot
/*    */ {
/* 41 */   SlotArmor(ContainerPlayer paramContainerPlayer, IInventory paramIInventory, int paramInt1, int paramInt2, int paramInt3, int paramInt4) { super(paramIInventory, paramInt1, paramInt2, paramInt3); }
/*    */   
/* 43 */   public int d() { return 1; }
/*    */ 
/*    */   
/*    */   public boolean isAllowed(ItemStack paramItemStack) {
/* 47 */     if (paramItemStack.getItem() instanceof ItemArmor) {
/* 48 */       return (((ItemArmor)paramItemStack.getItem()).bk == this.d);
/*    */     }
/* 50 */     if ((paramItemStack.getItem()).id == Block.PUMPKIN.id) {
/* 51 */       return (this.d == 0);
/*    */     }
/* 53 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\SlotArmor.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */