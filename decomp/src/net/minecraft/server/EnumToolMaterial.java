/*    */ package net.minecraft.server;
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
/*    */ public static enum EnumToolMaterial
/*    */ {
/* 22 */   WOOD(false, 59, 2.0F, false),
/* 23 */   STONE(true, '', 4.0F, true),
/* 24 */   IRON(2, 'ú', 6.0F, 2),
/* 25 */   DIAMOND(3, 'ؙ', 8.0F, 3),
/* 26 */   GOLD(false, 32, 12.0F, false);
/*    */   
/*    */   private final int f;
/*    */   private final int g;
/*    */   private final float h;
/*    */   private final int i;
/*    */   
/*    */   EnumToolMaterial(int paramInt1, int paramInt2, float paramFloat, int paramInt3) {
/* 34 */     this.f = paramInt1;
/* 35 */     this.g = paramInt2;
/* 36 */     this.h = paramFloat;
/* 37 */     this.i = paramInt3;
/*    */   }
/*    */ 
/*    */   
/* 41 */   public int a() { return this.g; }
/*    */ 
/*    */ 
/*    */   
/* 45 */   public float b() { return this.h; }
/*    */ 
/*    */ 
/*    */   
/* 49 */   public int c() { return this.i; }
/*    */ 
/*    */ 
/*    */   
/* 53 */   public int d() { return this.f; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\EnumToolMaterial.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */