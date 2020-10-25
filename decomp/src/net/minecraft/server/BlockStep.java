/*    */ package net.minecraft.server;
/*    */ 
/*    */ import java.util.Random;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BlockStep
/*    */   extends Block
/*    */ {
/* 15 */   public static final String[] a = { "stone", "sand", "wood", "cobble" };
/*    */ 
/*    */   
/*    */   private boolean b;
/*    */ 
/*    */   
/*    */   public BlockStep(int paramInt, boolean paramBoolean) {
/* 22 */     super(paramInt, 6, Material.STONE);
/* 23 */     this.b = paramBoolean;
/*    */     
/* 25 */     if (!paramBoolean) {
/* 26 */       a(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
/*    */     }
/* 28 */     f(255);
/*    */   }
/*    */ 
/*    */   
/*    */   public int a(int paramInt1, int paramInt2) {
/* 33 */     if (paramInt2 == 0) {
/* 34 */       if (paramInt1 <= 1) return 6; 
/* 35 */       return 5;
/* 36 */     }  if (paramInt2 == 1) {
/* 37 */       if (paramInt1 == 0) return 208; 
/* 38 */       if (paramInt1 == 1) return 176; 
/* 39 */       return 192;
/* 40 */     }  if (paramInt2 == 2)
/* 41 */       return 4; 
/* 42 */     if (paramInt2 == 3) {
/* 43 */       return 16;
/*    */     }
/* 45 */     return 6;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/* 50 */   public int a(int paramInt) { return a(paramInt, 0); }
/*    */ 
/*    */ 
/*    */   
/* 54 */   public boolean a() { return this.b; }
/*    */ 
/*    */   
/*    */   public void c(World paramWorld, int paramInt1, int paramInt2, int paramInt3) {
/* 58 */     if (this != Block.STEP) super.c(paramWorld, paramInt1, paramInt2, paramInt3); 
/* 59 */     int i = paramWorld.getTypeId(paramInt1, paramInt2 - 1, paramInt3);
/*    */     
/* 61 */     int j = paramWorld.getData(paramInt1, paramInt2, paramInt3);
/* 62 */     int k = paramWorld.getData(paramInt1, paramInt2 - 1, paramInt3);
/*    */     
/* 64 */     if (j != k) {
/*    */       return;
/*    */     }
/*    */     
/* 68 */     if (i == STEP.id) {
/* 69 */       paramWorld.setTypeId(paramInt1, paramInt2, paramInt3, 0);
/* 70 */       paramWorld.setTypeIdAndData(paramInt1, paramInt2 - 1, paramInt3, Block.DOUBLE_STEP.id, j);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/* 75 */   public int a(int paramInt, Random paramRandom) { return Block.STEP.id; }
/*    */ 
/*    */ 
/*    */   
/*    */   public int a(Random paramRandom) {
/* 80 */     if (this.b) {
/* 81 */       return 2;
/*    */     }
/* 83 */     return 1;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/* 88 */   protected int a_(int paramInt) { return paramInt; }
/*    */ 
/*    */ 
/*    */   
/* 92 */   public boolean b() { return this.b; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\BlockStep.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */