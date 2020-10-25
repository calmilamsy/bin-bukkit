/*    */ package net.minecraft.server;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ItemBow
/*    */   extends Item
/*    */ {
/*    */   public ItemBow(int paramInt) {
/*  9 */     super(paramInt);
/* 10 */     this.maxStackSize = 1;
/*    */   }
/*    */   
/*    */   public ItemStack a(ItemStack paramItemStack, World paramWorld, EntityHuman paramEntityHuman) {
/* 14 */     if (paramEntityHuman.inventory.b(Item.ARROW.id)) {
/* 15 */       paramWorld.makeSound(paramEntityHuman, "random.bow", 1.0F, 1.0F / (b.nextFloat() * 0.4F + 0.8F));
/* 16 */       if (!paramWorld.isStatic) paramWorld.addEntity(new EntityArrow(paramWorld, paramEntityHuman)); 
/*    */     } 
/* 18 */     return paramItemStack;
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\ItemBow.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */