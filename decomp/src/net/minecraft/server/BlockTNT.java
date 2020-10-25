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
/*    */ 
/*    */ public class BlockTNT
/*    */   extends Block
/*    */ {
/* 16 */   public BlockTNT(int paramInt1, int paramInt2) { super(paramInt1, paramInt2, Material.TNT); }
/*    */ 
/*    */   
/*    */   public int a(int paramInt) {
/* 20 */     if (paramInt == 0) return this.textureId + 2; 
/* 21 */     if (paramInt == 1) return this.textureId + 1; 
/* 22 */     return this.textureId;
/*    */   }
/*    */   
/*    */   public void c(World paramWorld, int paramInt1, int paramInt2, int paramInt3) {
/* 26 */     super.c(paramWorld, paramInt1, paramInt2, paramInt3);
/* 27 */     if (paramWorld.isBlockIndirectlyPowered(paramInt1, paramInt2, paramInt3)) {
/* 28 */       postBreak(paramWorld, paramInt1, paramInt2, paramInt3, 1);
/* 29 */       paramWorld.setTypeId(paramInt1, paramInt2, paramInt3, 0);
/*    */     } 
/*    */   }
/*    */   
/*    */   public void doPhysics(World paramWorld, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 34 */     if (paramInt4 > 0 && Block.byId[paramInt4].isPowerSource() && 
/* 35 */       paramWorld.isBlockIndirectlyPowered(paramInt1, paramInt2, paramInt3)) {
/* 36 */       postBreak(paramWorld, paramInt1, paramInt2, paramInt3, 1);
/* 37 */       paramWorld.setTypeId(paramInt1, paramInt2, paramInt3, 0);
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/* 43 */   public int a(Random paramRandom) { return 0; }
/*    */ 
/*    */ 
/*    */   
/*    */   public void d(World paramWorld, int paramInt1, int paramInt2, int paramInt3) {
/* 48 */     EntityTNTPrimed entityTNTPrimed = new EntityTNTPrimed(paramWorld, (paramInt1 + 0.5F), (paramInt2 + 0.5F), (paramInt3 + 0.5F));
/* 49 */     entityTNTPrimed.fuseTicks = paramWorld.random.nextInt(entityTNTPrimed.fuseTicks / 4) + entityTNTPrimed.fuseTicks / 8;
/* 50 */     paramWorld.addEntity(entityTNTPrimed);
/*    */   }
/*    */   
/*    */   public void postBreak(World paramWorld, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 54 */     if (paramWorld.isStatic)
/*    */       return; 
/* 56 */     if ((paramInt4 & true) == 0) {
/* 57 */       a(paramWorld, paramInt1, paramInt2, paramInt3, new ItemStack(Block.TNT.id, true, false));
/*    */     } else {
/* 59 */       EntityTNTPrimed entityTNTPrimed = new EntityTNTPrimed(paramWorld, (paramInt1 + 0.5F), (paramInt2 + 0.5F), (paramInt3 + 0.5F));
/* 60 */       paramWorld.addEntity(entityTNTPrimed);
/* 61 */       paramWorld.makeSound(entityTNTPrimed, "random.fuse", 1.0F, 1.0F);
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void b(World paramWorld, int paramInt1, int paramInt2, int paramInt3, EntityHuman paramEntityHuman) {
/* 69 */     if (paramEntityHuman.G() != null && (paramEntityHuman.G()).id == Item.FLINT_AND_STEEL.id)
/*    */     {
/* 71 */       paramWorld.setRawData(paramInt1, paramInt2, paramInt3, 1);
/*    */     }
/* 73 */     super.b(paramWorld, paramInt1, paramInt2, paramInt3, paramEntityHuman);
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
/* 85 */   public boolean interact(World paramWorld, int paramInt1, int paramInt2, int paramInt3, EntityHuman paramEntityHuman) { return super.interact(paramWorld, paramInt1, paramInt2, paramInt3, paramEntityHuman); }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\BlockTNT.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */