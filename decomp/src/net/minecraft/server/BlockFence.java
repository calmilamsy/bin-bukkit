/*    */ package net.minecraft.server;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BlockFence
/*    */   extends Block
/*    */ {
/*  9 */   public BlockFence(int paramInt1, int paramInt2) { super(paramInt1, paramInt2, Material.WOOD); }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean canPlace(World paramWorld, int paramInt1, int paramInt2, int paramInt3) {
/* 14 */     if (paramWorld.getTypeId(paramInt1, paramInt2 - 1, paramInt3) == this.id) return true; 
/* 15 */     if (!paramWorld.getMaterial(paramInt1, paramInt2 - 1, paramInt3).isBuildable()) return false; 
/* 16 */     return super.canPlace(paramWorld, paramInt1, paramInt2, paramInt3);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/* 21 */   public AxisAlignedBB e(World paramWorld, int paramInt1, int paramInt2, int paramInt3) { return AxisAlignedBB.b(paramInt1, paramInt2, paramInt3, (paramInt1 + 1), (paramInt2 + 1.5F), (paramInt3 + 1)); }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 29 */   public boolean a() { return false; }
/*    */ 
/*    */ 
/*    */   
/* 33 */   public boolean b() { return false; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\BlockFence.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */