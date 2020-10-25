/*    */ package net.minecraft.server;
/*    */ 
/*    */ 
/*    */ public class ItemTool
/*    */   extends Item
/*    */ {
/*    */   private Block[] bk;
/*  8 */   private float bl = 4.0F;
/*    */   
/*    */   private int bm;
/*    */   
/*    */   protected EnumToolMaterial a;
/*    */   
/*    */   protected ItemTool(int paramInt1, int paramInt2, EnumToolMaterial paramEnumToolMaterial, Block[] paramArrayOfBlock) {
/* 15 */     super(paramInt1);
/* 16 */     this.a = paramEnumToolMaterial;
/* 17 */     this.bk = paramArrayOfBlock;
/* 18 */     this.maxStackSize = 1;
/* 19 */     d(paramEnumToolMaterial.a());
/* 20 */     this.bl = paramEnumToolMaterial.b();
/* 21 */     this.bm = paramInt2 + paramEnumToolMaterial.c();
/*    */   }
/*    */   
/*    */   public float a(ItemStack paramItemStack, Block paramBlock) {
/* 25 */     for (byte b = 0; b < this.bk.length; b++) {
/* 26 */       if (this.bk[b] == paramBlock) return this.bl; 
/* 27 */     }  return 1.0F;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean a(ItemStack paramItemStack, EntityLiving paramEntityLiving1, EntityLiving paramEntityLiving2) {
/* 32 */     paramItemStack.damage(2, paramEntityLiving2);
/* 33 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean a(ItemStack paramItemStack, int paramInt1, int paramInt2, int paramInt3, int paramInt4, EntityLiving paramEntityLiving) {
/* 38 */     paramItemStack.damage(1, paramEntityLiving);
/* 39 */     return true;
/*    */   }
/*    */ 
/*    */   
/* 43 */   public int a(Entity paramEntity) { return this.bm; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\ItemTool.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */