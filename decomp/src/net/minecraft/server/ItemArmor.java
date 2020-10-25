/*    */ package net.minecraft.server;
/*    */ 
/*    */ public class ItemArmor extends Item {
/*  4 */   private static final int[] bn = { 3, 8, 6, 3 };
/*    */ 
/*    */   
/*  7 */   private static final int[] bo = { 11, 16, 15, 13 };
/*    */   
/*    */   public final int a;
/*    */   
/*    */   public final int bk;
/*    */   public final int bl;
/*    */   public final int bm;
/*    */   
/*    */   public ItemArmor(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 16 */     super(paramInt1);
/* 17 */     this.a = paramInt2;
/* 18 */     this.bk = paramInt4;
/* 19 */     this.bm = paramInt3;
/* 20 */     this.bl = bn[paramInt4];
/* 21 */     d(bo[paramInt4] * 3 << paramInt2);
/* 22 */     this.maxStackSize = 1;
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\ItemArmor.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */