/*    */ package net.minecraft.server;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SlotResult
/*    */   extends Slot
/*    */ {
/*    */   private final IInventory d;
/*    */   private EntityHuman e;
/*    */   
/*    */   public SlotResult(EntityHuman paramEntityHuman, IInventory paramIInventory1, IInventory paramIInventory2, int paramInt1, int paramInt2, int paramInt3) {
/* 15 */     super(paramIInventory2, paramInt1, paramInt2, paramInt3);
/* 16 */     this.e = paramEntityHuman;
/* 17 */     this.d = paramIInventory1;
/*    */   }
/*    */ 
/*    */   
/* 21 */   public boolean isAllowed(ItemStack paramItemStack) { return false; }
/*    */ 
/*    */   
/*    */   public void a(ItemStack paramItemStack) {
/* 25 */     paramItemStack.b(this.e.world, this.e);
/*    */     
/* 27 */     if (paramItemStack.id == Block.WORKBENCH.id) { this.e.a(AchievementList.h, 1); }
/* 28 */     else if (paramItemStack.id == Item.WOOD_PICKAXE.id) { this.e.a(AchievementList.i, 1); }
/* 29 */     else if (paramItemStack.id == Block.FURNACE.id) { this.e.a(AchievementList.j, 1); }
/* 30 */     else if (paramItemStack.id == Item.WOOD_HOE.id) { this.e.a(AchievementList.l, 1); }
/* 31 */     else if (paramItemStack.id == Item.BREAD.id) { this.e.a(AchievementList.m, 1); }
/* 32 */     else if (paramItemStack.id == Item.CAKE.id) { this.e.a(AchievementList.n, 1); }
/* 33 */     else if (paramItemStack.id == Item.STONE_PICKAXE.id) { this.e.a(AchievementList.o, 1); }
/* 34 */     else if (paramItemStack.id == Item.WOOD_SWORD.id) { this.e.a(AchievementList.r, 1); }
/*    */     
/* 36 */     for (byte b = 0; b < this.d.getSize(); b++) {
/* 37 */       ItemStack itemStack = this.d.getItem(b);
/* 38 */       if (itemStack != null) {
/* 39 */         this.d.splitStack(b, 1);
/*    */         
/* 41 */         if (itemStack.getItem().i())
/*    */         {
/* 43 */           this.d.setItem(b, new ItemStack(itemStack.getItem().h()));
/*    */         }
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\SlotResult.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */