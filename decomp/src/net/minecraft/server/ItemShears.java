/*    */ package net.minecraft.server;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ItemShears
/*    */   extends Item
/*    */ {
/*    */   public ItemShears(int paramInt) {
/*  9 */     super(paramInt);
/*    */     
/* 11 */     c(1);
/* 12 */     d(238);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean a(ItemStack paramItemStack, int paramInt1, int paramInt2, int paramInt3, int paramInt4, EntityLiving paramEntityLiving) {
/* 17 */     if (paramInt1 == Block.LEAVES.id || paramInt1 == Block.WEB.id) {
/* 18 */       paramItemStack.damage(1, paramEntityLiving);
/*    */     }
/* 20 */     return super.a(paramItemStack, paramInt1, paramInt2, paramInt3, paramInt4, paramEntityLiving);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/* 25 */   public boolean a(Block paramBlock) { return (paramBlock.id == Block.WEB.id); }
/*    */ 
/*    */ 
/*    */   
/*    */   public float a(ItemStack paramItemStack, Block paramBlock) {
/* 30 */     if (paramBlock.id == Block.WEB.id || paramBlock.id == Block.LEAVES.id) {
/* 31 */       return 15.0F;
/*    */     }
/* 33 */     if (paramBlock.id == Block.WOOL.id) {
/* 34 */       return 5.0F;
/*    */     }
/* 36 */     return super.a(paramItemStack, paramBlock);
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\ItemShears.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */