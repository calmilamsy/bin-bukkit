/*    */ package net.minecraft.server;
/*    */ 
/*    */ import java.util.Random;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BlockLadder
/*    */   extends Block
/*    */ {
/* 11 */   protected BlockLadder(int paramInt1, int paramInt2) { super(paramInt1, paramInt2, Material.ORIENTABLE); }
/*    */ 
/*    */   
/*    */   public AxisAlignedBB e(World paramWorld, int paramInt1, int paramInt2, int paramInt3) {
/* 15 */     int i = paramWorld.getData(paramInt1, paramInt2, paramInt3);
/* 16 */     float f = 0.125F;
/*    */     
/* 18 */     if (i == 2) a(0.0F, 0.0F, 1.0F - f, 1.0F, 1.0F, 1.0F); 
/* 19 */     if (i == 3) a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, f); 
/* 20 */     if (i == 4) a(1.0F - f, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F); 
/* 21 */     if (i == 5) a(0.0F, 0.0F, 0.0F, f, 1.0F, 1.0F);
/*    */     
/* 23 */     return super.e(paramWorld, paramInt1, paramInt2, paramInt3);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 43 */   public boolean a() { return false; }
/*    */ 
/*    */ 
/*    */   
/* 47 */   public boolean b() { return false; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean canPlace(World paramWorld, int paramInt1, int paramInt2, int paramInt3) {
/* 55 */     if (paramWorld.e(paramInt1 - 1, paramInt2, paramInt3))
/* 56 */       return true; 
/* 57 */     if (paramWorld.e(paramInt1 + 1, paramInt2, paramInt3))
/* 58 */       return true; 
/* 59 */     if (paramWorld.e(paramInt1, paramInt2, paramInt3 - 1))
/* 60 */       return true; 
/* 61 */     if (paramWorld.e(paramInt1, paramInt2, paramInt3 + 1)) {
/* 62 */       return true;
/*    */     }
/* 64 */     return false;
/*    */   }
/*    */   
/*    */   public void postPlace(World paramWorld, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 68 */     int i = paramWorld.getData(paramInt1, paramInt2, paramInt3);
/*    */     
/* 70 */     if ((i == 0 || paramInt4 == 2) && paramWorld.e(paramInt1, paramInt2, paramInt3 + 1)) i = 2; 
/* 71 */     if ((i == 0 || paramInt4 == 3) && paramWorld.e(paramInt1, paramInt2, paramInt3 - 1)) i = 3; 
/* 72 */     if ((i == 0 || paramInt4 == 4) && paramWorld.e(paramInt1 + 1, paramInt2, paramInt3)) i = 4; 
/* 73 */     if ((i == 0 || paramInt4 == 5) && paramWorld.e(paramInt1 - 1, paramInt2, paramInt3)) i = 5;
/*    */     
/* 75 */     paramWorld.setData(paramInt1, paramInt2, paramInt3, i);
/*    */   }
/*    */   
/*    */   public void doPhysics(World paramWorld, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 79 */     int i = paramWorld.getData(paramInt1, paramInt2, paramInt3);
/* 80 */     boolean bool = false;
/*    */     
/* 82 */     if (i == 2 && paramWorld.e(paramInt1, paramInt2, paramInt3 + 1)) bool = true; 
/* 83 */     if (i == 3 && paramWorld.e(paramInt1, paramInt2, paramInt3 - 1)) bool = true; 
/* 84 */     if (i == 4 && paramWorld.e(paramInt1 + 1, paramInt2, paramInt3)) bool = true; 
/* 85 */     if (i == 5 && paramWorld.e(paramInt1 - 1, paramInt2, paramInt3)) bool = true; 
/* 86 */     if (!bool) {
/* 87 */       g(paramWorld, paramInt1, paramInt2, paramInt3, i);
/* 88 */       paramWorld.setTypeId(paramInt1, paramInt2, paramInt3, 0);
/*    */     } 
/*    */     
/* 91 */     super.doPhysics(paramWorld, paramInt1, paramInt2, paramInt3, paramInt4);
/*    */   }
/*    */ 
/*    */   
/* 95 */   public int a(Random paramRandom) { return 1; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\BlockLadder.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */