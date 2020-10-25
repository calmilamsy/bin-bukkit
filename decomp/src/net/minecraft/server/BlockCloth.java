/*    */ package net.minecraft.server;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BlockCloth
/*    */   extends Block
/*    */ {
/* 10 */   public BlockCloth() { super(35, 64, Material.CLOTH); }
/*    */ 
/*    */ 
/*    */   
/*    */   public int a(int paramInt1, int paramInt2) {
/* 15 */     if (paramInt2 == 0)
/*    */     {
/* 17 */       return this.textureId;
/*    */     }
/*    */ 
/*    */     
/* 21 */     paramInt2 = paramInt2 & 0xF ^ 0xFFFFFFFF;
/* 22 */     return 113 + ((paramInt2 & 0x8) >> 3) + (paramInt2 & 0x7) * 16;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/* 27 */   protected int a_(int paramInt) { return paramInt; }
/*    */ 
/*    */ 
/*    */   
/* 31 */   public static int c(int paramInt) { return (paramInt ^ 0xFFFFFFFF) & 0xF; }
/*    */ 
/*    */ 
/*    */   
/* 35 */   public static int d(int paramInt) { return (paramInt ^ 0xFFFFFFFF) & 0xF; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\BlockCloth.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */