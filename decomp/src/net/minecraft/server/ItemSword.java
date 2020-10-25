/*    */ package net.minecraft.server;
/*    */ 
/*    */ 
/*    */ public class ItemSword
/*    */   extends Item
/*    */ {
/*    */   private int a;
/*    */   
/*    */   public ItemSword(int paramInt, EnumToolMaterial paramEnumToolMaterial) {
/* 10 */     super(paramInt);
/* 11 */     this.maxStackSize = 1;
/* 12 */     d(paramEnumToolMaterial.a());
/*    */     
/* 14 */     this.a = 4 + paramEnumToolMaterial.c() * 2;
/*    */   }
/*    */ 
/*    */   
/*    */   public float a(ItemStack paramItemStack, Block paramBlock) {
/* 19 */     if (paramBlock.id == Block.WEB.id)
/*    */     {
/* 21 */       return 15.0F;
/*    */     }
/* 23 */     return 1.5F;
/*    */   }
/*    */   
/*    */   public boolean a(ItemStack paramItemStack, EntityLiving paramEntityLiving1, EntityLiving paramEntityLiving2) {
/* 27 */     paramItemStack.damage(1, paramEntityLiving2);
/* 28 */     return true;
/*    */   }
/*    */   
/*    */   public boolean a(ItemStack paramItemStack, int paramInt1, int paramInt2, int paramInt3, int paramInt4, EntityLiving paramEntityLiving) {
/* 32 */     paramItemStack.damage(2, paramEntityLiving);
/* 33 */     return true;
/*    */   }
/*    */ 
/*    */   
/* 37 */   public int a(Entity paramEntity) { return this.a; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 46 */   public boolean a(Block paramBlock) { return (paramBlock.id == Block.WEB.id); }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\ItemSword.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */