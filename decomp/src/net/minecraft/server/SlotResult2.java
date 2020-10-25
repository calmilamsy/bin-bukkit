/*    */ package net.minecraft.server;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SlotResult2
/*    */   extends Slot
/*    */ {
/*    */   private EntityHuman d;
/*    */   
/*    */   public SlotResult2(EntityHuman paramEntityHuman, IInventory paramIInventory, int paramInt1, int paramInt2, int paramInt3) {
/* 14 */     super(paramIInventory, paramInt1, paramInt2, paramInt3);
/*    */     
/* 16 */     this.d = paramEntityHuman;
/*    */   }
/*    */ 
/*    */   
/* 20 */   public boolean isAllowed(ItemStack paramItemStack) { return false; }
/*    */ 
/*    */ 
/*    */   
/*    */   public void a(ItemStack paramItemStack) {
/* 25 */     paramItemStack.b(this.d.world, this.d);
/* 26 */     if (paramItemStack.id == Item.IRON_INGOT.id) this.d.a(AchievementList.k, 1); 
/* 27 */     if (paramItemStack.id == Item.COOKED_FISH.id) this.d.a(AchievementList.p, 1); 
/* 28 */     super.a(paramItemStack);
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\SlotResult2.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */