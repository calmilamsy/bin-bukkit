/*    */ package net.minecraft.server;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BlockSlowSand
/*    */   extends Block
/*    */ {
/* 10 */   public BlockSlowSand(int paramInt1, int paramInt2) { super(paramInt1, paramInt2, Material.SAND); }
/*    */ 
/*    */   
/*    */   public AxisAlignedBB e(World paramWorld, int paramInt1, int paramInt2, int paramInt3) {
/* 14 */     float f = 0.125F;
/* 15 */     return AxisAlignedBB.b(paramInt1, paramInt2, paramInt3, (paramInt1 + 1), ((paramInt2 + 1) - f), (paramInt3 + 1));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void a(World paramWorld, int paramInt1, int paramInt2, int paramInt3, Entity paramEntity) {
/* 24 */     paramEntity.motX *= 0.4D;
/* 25 */     paramEntity.motZ *= 0.4D;
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\BlockSlowSand.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */