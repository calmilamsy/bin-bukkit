/*    */ package net.minecraft.server;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ItemEgg
/*    */   extends Item
/*    */ {
/*    */   public ItemEgg(int paramInt) {
/*  9 */     super(paramInt);
/* 10 */     this.maxStackSize = 16;
/*    */   }
/*    */   
/*    */   public ItemStack a(ItemStack paramItemStack, World paramWorld, EntityHuman paramEntityHuman) {
/* 14 */     paramItemStack.count--;
/* 15 */     paramWorld.makeSound(paramEntityHuman, "random.bow", 0.5F, 0.4F / (b.nextFloat() * 0.4F + 0.8F));
/* 16 */     if (!paramWorld.isStatic) paramWorld.addEntity(new EntityEgg(paramWorld, paramEntityHuman)); 
/* 17 */     return paramItemStack;
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\ItemEgg.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */