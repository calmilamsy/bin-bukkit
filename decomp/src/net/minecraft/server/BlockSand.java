/*    */ package net.minecraft.server;
/*    */ 
/*    */ import java.util.Random;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BlockSand
/*    */   extends Block
/*    */ {
/*    */   public static boolean instaFall = false;
/*    */   
/* 13 */   public BlockSand(int paramInt1, int paramInt2) { super(paramInt1, paramInt2, Material.SAND); }
/*    */ 
/*    */ 
/*    */   
/* 17 */   public void c(World paramWorld, int paramInt1, int paramInt2, int paramInt3) { paramWorld.c(paramInt1, paramInt2, paramInt3, this.id, c()); }
/*    */ 
/*    */ 
/*    */   
/* 21 */   public void doPhysics(World paramWorld, int paramInt1, int paramInt2, int paramInt3, int paramInt4) { paramWorld.c(paramInt1, paramInt2, paramInt3, this.id, c()); }
/*    */ 
/*    */ 
/*    */   
/* 25 */   public void a(World paramWorld, int paramInt1, int paramInt2, int paramInt3, Random paramRandom) { g(paramWorld, paramInt1, paramInt2, paramInt3); }
/*    */ 
/*    */   
/*    */   private void g(World paramWorld, int paramInt1, int paramInt2, int paramInt3) {
/* 29 */     int i = paramInt1;
/* 30 */     int j = paramInt2;
/* 31 */     int k = paramInt3;
/* 32 */     if (c_(paramWorld, i, j - 1, k) && j >= 0) {
/* 33 */       int m = 32;
/* 34 */       if (instaFall || !paramWorld.a(paramInt1 - m, paramInt2 - m, paramInt3 - m, paramInt1 + m, paramInt2 + m, paramInt3 + m)) {
/* 35 */         paramWorld.setTypeId(paramInt1, paramInt2, paramInt3, 0);
/* 36 */         while (c_(paramWorld, paramInt1, paramInt2 - 1, paramInt3) && paramInt2 > 0)
/* 37 */           paramInt2--; 
/* 38 */         if (paramInt2 > 0) {
/* 39 */           paramWorld.setTypeId(paramInt1, paramInt2, paramInt3, this.id);
/*    */         }
/*    */       } else {
/* 42 */         EntityFallingSand entityFallingSand = new EntityFallingSand(paramWorld, (paramInt1 + 0.5F), (paramInt2 + 0.5F), (paramInt3 + 0.5F), this.id);
/* 43 */         paramWorld.addEntity(entityFallingSand);
/*    */       } 
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/* 49 */   public int c() { return 3; }
/*    */ 
/*    */   
/*    */   public static boolean c_(World paramWorld, int paramInt1, int paramInt2, int paramInt3) {
/* 53 */     int i = paramWorld.getTypeId(paramInt1, paramInt2, paramInt3);
/* 54 */     if (i == 0) return true; 
/* 55 */     if (i == Block.FIRE.id) return true; 
/* 56 */     Material material = (Block.byId[i]).material;
/* 57 */     if (material == Material.WATER) return true; 
/* 58 */     if (material == Material.LAVA) return true; 
/* 59 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\BlockSand.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */