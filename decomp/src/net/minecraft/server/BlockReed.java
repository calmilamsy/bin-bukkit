/*    */ package net.minecraft.server;
/*    */ 
/*    */ import java.util.Random;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BlockReed
/*    */   extends Block
/*    */ {
/*    */   protected BlockReed(int paramInt1, int paramInt2) {
/* 12 */     super(paramInt1, Material.PLANT);
/* 13 */     this.textureId = paramInt2;
/* 14 */     float f = 0.375F;
/* 15 */     a(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, 1.0F, 0.5F + f);
/* 16 */     a(true);
/*    */   }
/*    */   
/*    */   public void a(World paramWorld, int paramInt1, int paramInt2, int paramInt3, Random paramRandom) {
/* 20 */     if (paramWorld.isEmpty(paramInt1, paramInt2 + 1, paramInt3)) {
/* 21 */       int i = 1;
/* 22 */       while (paramWorld.getTypeId(paramInt1, paramInt2 - i, paramInt3) == this.id) {
/* 23 */         i++;
/*    */       }
/* 25 */       if (i < 3) {
/* 26 */         int j = paramWorld.getData(paramInt1, paramInt2, paramInt3);
/* 27 */         if (j == 15) {
/* 28 */           paramWorld.setTypeId(paramInt1, paramInt2 + 1, paramInt3, this.id);
/* 29 */           paramWorld.setData(paramInt1, paramInt2, paramInt3, 0);
/*    */         } else {
/* 31 */           paramWorld.setData(paramInt1, paramInt2, paramInt3, j + 1);
/*    */         } 
/*    */       } 
/*    */     } 
/*    */   }
/*    */   
/*    */   public boolean canPlace(World paramWorld, int paramInt1, int paramInt2, int paramInt3) {
/* 38 */     int i = paramWorld.getTypeId(paramInt1, paramInt2 - 1, paramInt3);
/* 39 */     if (i == this.id) return true; 
/* 40 */     if (i != Block.GRASS.id && i != Block.DIRT.id) return false; 
/* 41 */     if (paramWorld.getMaterial(paramInt1 - true, paramInt2 - true, paramInt3) == Material.WATER) return true; 
/* 42 */     if (paramWorld.getMaterial(paramInt1 + true, paramInt2 - true, paramInt3) == Material.WATER) return true; 
/* 43 */     if (paramWorld.getMaterial(paramInt1, paramInt2 - true, paramInt3 - true) == Material.WATER) return true; 
/* 44 */     if (paramWorld.getMaterial(paramInt1, paramInt2 - true, paramInt3 + true) == Material.WATER) return true; 
/* 45 */     return false;
/*    */   }
/*    */ 
/*    */   
/* 49 */   public void doPhysics(World paramWorld, int paramInt1, int paramInt2, int paramInt3, int paramInt4) { g(paramWorld, paramInt1, paramInt2, paramInt3); }
/*    */ 
/*    */   
/*    */   protected final void g(World paramWorld, int paramInt1, int paramInt2, int paramInt3) {
/* 53 */     if (!f(paramWorld, paramInt1, paramInt2, paramInt3)) {
/* 54 */       g(paramWorld, paramInt1, paramInt2, paramInt3, paramWorld.getData(paramInt1, paramInt2, paramInt3));
/* 55 */       paramWorld.setTypeId(paramInt1, paramInt2, paramInt3, 0);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/* 60 */   public boolean f(World paramWorld, int paramInt1, int paramInt2, int paramInt3) { return canPlace(paramWorld, paramInt1, paramInt2, paramInt3); }
/*    */ 
/*    */ 
/*    */   
/* 64 */   public AxisAlignedBB e(World paramWorld, int paramInt1, int paramInt2, int paramInt3) { return null; }
/*    */ 
/*    */ 
/*    */   
/* 68 */   public int a(int paramInt, Random paramRandom) { return Item.SUGAR_CANE.id; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 76 */   public boolean a() { return false; }
/*    */ 
/*    */ 
/*    */   
/* 80 */   public boolean b() { return false; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\BlockReed.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */