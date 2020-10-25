/*    */ package net.minecraft.server;
/*    */ 
/*    */ import java.util.Random;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BlockFlower
/*    */   extends Block
/*    */ {
/*    */   protected BlockFlower(int paramInt1, int paramInt2) {
/* 11 */     super(paramInt1, Material.PLANT);
/* 12 */     this.textureId = paramInt2;
/* 13 */     a(true);
/* 14 */     float f = 0.2F;
/* 15 */     a(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, f * 3.0F, 0.5F + f);
/*    */   }
/*    */ 
/*    */   
/* 19 */   public boolean canPlace(World paramWorld, int paramInt1, int paramInt2, int paramInt3) { return (super.canPlace(paramWorld, paramInt1, paramInt2, paramInt3) && c(paramWorld.getTypeId(paramInt1, paramInt2 - 1, paramInt3))); }
/*    */ 
/*    */ 
/*    */   
/* 23 */   protected boolean c(int paramInt) { return (paramInt == Block.GRASS.id || paramInt == Block.DIRT.id || paramInt == Block.SOIL.id); }
/*    */ 
/*    */   
/*    */   public void doPhysics(World paramWorld, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 27 */     super.doPhysics(paramWorld, paramInt1, paramInt2, paramInt3, paramInt4);
/* 28 */     g(paramWorld, paramInt1, paramInt2, paramInt3);
/*    */   }
/*    */ 
/*    */   
/* 32 */   public void a(World paramWorld, int paramInt1, int paramInt2, int paramInt3, Random paramRandom) { g(paramWorld, paramInt1, paramInt2, paramInt3); }
/*    */ 
/*    */   
/*    */   protected final void g(World paramWorld, int paramInt1, int paramInt2, int paramInt3) {
/* 36 */     if (!f(paramWorld, paramInt1, paramInt2, paramInt3)) {
/* 37 */       g(paramWorld, paramInt1, paramInt2, paramInt3, paramWorld.getData(paramInt1, paramInt2, paramInt3));
/* 38 */       paramWorld.setTypeId(paramInt1, paramInt2, paramInt3, 0);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/* 43 */   public boolean f(World paramWorld, int paramInt1, int paramInt2, int paramInt3) { return ((paramWorld.k(paramInt1, paramInt2, paramInt3) >= 8 || paramWorld.isChunkLoaded(paramInt1, paramInt2, paramInt3)) && c(paramWorld.getTypeId(paramInt1, paramInt2 - 1, paramInt3))); }
/*    */ 
/*    */ 
/*    */   
/* 47 */   public AxisAlignedBB e(World paramWorld, int paramInt1, int paramInt2, int paramInt3) { return null; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 55 */   public boolean a() { return false; }
/*    */ 
/*    */ 
/*    */   
/* 59 */   public boolean b() { return false; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\BlockFlower.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */