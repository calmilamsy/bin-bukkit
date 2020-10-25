/*    */ package net.minecraft.server;
/*    */ 
/*    */ 
/*    */ public class ItemFood
/*    */   extends Item
/*    */ {
/*    */   private int a;
/*    */   private boolean bk;
/*    */   
/*    */   public ItemFood(int paramInt1, int paramInt2, boolean paramBoolean) {
/* 11 */     super(paramInt1);
/* 12 */     this.a = paramInt2;
/* 13 */     this.bk = paramBoolean;
/*    */     
/* 15 */     this.maxStackSize = 1;
/*    */   }
/*    */   
/*    */   public ItemStack a(ItemStack paramItemStack, World paramWorld, EntityHuman paramEntityHuman) {
/* 19 */     paramItemStack.count--;
/* 20 */     paramEntityHuman.b(this.a);
/* 21 */     return paramItemStack;
/*    */   }
/*    */ 
/*    */   
/* 25 */   public int k() { return this.a; }
/*    */ 
/*    */ 
/*    */   
/* 29 */   public boolean l() { return this.bk; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\ItemFood.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */