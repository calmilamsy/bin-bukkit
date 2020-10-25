/*    */ package net.minecraft.server;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ItemLeaves
/*    */   extends ItemBlock
/*    */ {
/*    */   public ItemLeaves(int paramInt) {
/*  9 */     super(paramInt);
/*    */     
/* 11 */     d(0);
/* 12 */     a(true);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/* 17 */   public int filterData(int paramInt) { return paramInt | 0x8; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\ItemLeaves.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */