/*    */ package net.minecraft.server;
/*    */ 
/*    */ import java.util.Random;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BlockLockedChest
/*    */   extends Block
/*    */ {
/*    */   protected BlockLockedChest(int paramInt) {
/* 11 */     super(paramInt, Material.WOOD);
/* 12 */     this.textureId = 26;
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
/*    */   public int a(int paramInt) {
/* 33 */     if (paramInt == 1) return this.textureId - 1; 
/* 34 */     if (paramInt == 0) return this.textureId - 1; 
/* 35 */     if (paramInt == 3) return this.textureId + 1; 
/* 36 */     return this.textureId;
/*    */   }
/*    */ 
/*    */   
/* 40 */   public boolean canPlace(World paramWorld, int paramInt1, int paramInt2, int paramInt3) { return true; }
/*    */ 
/*    */ 
/*    */   
/* 44 */   public void a(World paramWorld, int paramInt1, int paramInt2, int paramInt3, Random paramRandom) { paramWorld.setTypeId(paramInt1, paramInt2, paramInt3, 0); }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\BlockLockedChest.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */