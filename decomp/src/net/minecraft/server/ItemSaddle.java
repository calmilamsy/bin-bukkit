/*    */ package net.minecraft.server;
/*    */ 
/*    */ 
/*    */ public class ItemSaddle
/*    */   extends Item
/*    */ {
/*    */   public ItemSaddle(int paramInt) {
/*  8 */     super(paramInt);
/*  9 */     this.maxStackSize = 1;
/*    */   }
/*    */   
/*    */   public void a(ItemStack paramItemStack, EntityLiving paramEntityLiving) {
/* 13 */     if (paramEntityLiving instanceof EntityPig) {
/* 14 */       EntityPig entityPig = (EntityPig)paramEntityLiving;
/* 15 */       if (!entityPig.hasSaddle()) {
/* 16 */         entityPig.setSaddle(true);
/* 17 */         paramItemStack.count--;
/*    */       } 
/*    */     } 
/*    */   }
/*    */   
/*    */   public boolean a(ItemStack paramItemStack, EntityLiving paramEntityLiving1, EntityLiving paramEntityLiving2) {
/* 23 */     a(paramItemStack, paramEntityLiving1);
/* 24 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\ItemSaddle.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */