/*    */ package net.minecraft.server;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ItemSoup
/*    */   extends ItemFood
/*    */ {
/*  8 */   public ItemSoup(int paramInt1, int paramInt2) { super(paramInt1, paramInt2, false); }
/*    */ 
/*    */   
/*    */   public ItemStack a(ItemStack paramItemStack, World paramWorld, EntityHuman paramEntityHuman) {
/* 12 */     super.a(paramItemStack, paramWorld, paramEntityHuman);
/* 13 */     return new ItemStack(Item.BOWL);
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\ItemSoup.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */