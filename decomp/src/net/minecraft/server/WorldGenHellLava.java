/*    */ package net.minecraft.server;
/*    */ 
/*    */ import java.util.Random;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WorldGenHellLava
/*    */   extends WorldGenerator
/*    */ {
/*    */   private int a;
/*    */   
/* 12 */   public WorldGenHellLava(int paramInt) { this.a = paramInt; }
/*    */ 
/*    */   
/*    */   public boolean a(World paramWorld, Random paramRandom, int paramInt1, int paramInt2, int paramInt3) {
/* 16 */     if (paramWorld.getTypeId(paramInt1, paramInt2 + 1, paramInt3) != Block.NETHERRACK.id) return false; 
/* 17 */     if (paramWorld.getTypeId(paramInt1, paramInt2, paramInt3) != 0 && paramWorld.getTypeId(paramInt1, paramInt2, paramInt3) != Block.NETHERRACK.id) return false;
/*    */     
/* 19 */     byte b1 = 0;
/* 20 */     if (paramWorld.getTypeId(paramInt1 - 1, paramInt2, paramInt3) == Block.NETHERRACK.id) b1++; 
/* 21 */     if (paramWorld.getTypeId(paramInt1 + 1, paramInt2, paramInt3) == Block.NETHERRACK.id) b1++; 
/* 22 */     if (paramWorld.getTypeId(paramInt1, paramInt2, paramInt3 - 1) == Block.NETHERRACK.id) b1++; 
/* 23 */     if (paramWorld.getTypeId(paramInt1, paramInt2, paramInt3 + 1) == Block.NETHERRACK.id) b1++; 
/* 24 */     if (paramWorld.getTypeId(paramInt1, paramInt2 - 1, paramInt3) == Block.NETHERRACK.id) b1++;
/*    */     
/* 26 */     byte b2 = 0;
/* 27 */     if (paramWorld.isEmpty(paramInt1 - 1, paramInt2, paramInt3)) b2++; 
/* 28 */     if (paramWorld.isEmpty(paramInt1 + 1, paramInt2, paramInt3)) b2++; 
/* 29 */     if (paramWorld.isEmpty(paramInt1, paramInt2, paramInt3 - 1)) b2++; 
/* 30 */     if (paramWorld.isEmpty(paramInt1, paramInt2, paramInt3 + 1)) b2++; 
/* 31 */     if (paramWorld.isEmpty(paramInt1, paramInt2 - 1, paramInt3)) b2++;
/*    */     
/* 33 */     if (b1 == 4 && b2 == 1) {
/* 34 */       paramWorld.setTypeId(paramInt1, paramInt2, paramInt3, this.a);
/* 35 */       paramWorld.a = true;
/* 36 */       Block.byId[this.a].a(paramWorld, paramInt1, paramInt2, paramInt3, paramRandom);
/* 37 */       paramWorld.a = false;
/*    */     } 
/*    */     
/* 40 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\WorldGenHellLava.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */