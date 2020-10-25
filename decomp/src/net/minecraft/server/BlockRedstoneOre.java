/*    */ package net.minecraft.server;
/*    */ 
/*    */ import java.util.Random;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BlockRedstoneOre
/*    */   extends Block
/*    */ {
/*    */   private boolean a;
/*    */   
/*    */   public BlockRedstoneOre(int paramInt1, int paramInt2, boolean paramBoolean) {
/* 15 */     super(paramInt1, paramInt2, Material.STONE);
/* 16 */     if (paramBoolean) {
/* 17 */       a(true);
/*    */     }
/* 19 */     this.a = paramBoolean;
/*    */   }
/*    */ 
/*    */   
/* 23 */   public int c() { return 30; }
/*    */ 
/*    */   
/*    */   public void b(World paramWorld, int paramInt1, int paramInt2, int paramInt3, EntityHuman paramEntityHuman) {
/* 27 */     g(paramWorld, paramInt1, paramInt2, paramInt3);
/* 28 */     super.b(paramWorld, paramInt1, paramInt2, paramInt3, paramEntityHuman);
/*    */   }
/*    */   
/*    */   public void b(World paramWorld, int paramInt1, int paramInt2, int paramInt3, Entity paramEntity) {
/* 32 */     g(paramWorld, paramInt1, paramInt2, paramInt3);
/* 33 */     super.b(paramWorld, paramInt1, paramInt2, paramInt3, paramEntity);
/*    */   }
/*    */   
/*    */   public boolean interact(World paramWorld, int paramInt1, int paramInt2, int paramInt3, EntityHuman paramEntityHuman) {
/* 37 */     g(paramWorld, paramInt1, paramInt2, paramInt3);
/* 38 */     return super.interact(paramWorld, paramInt1, paramInt2, paramInt3, paramEntityHuman);
/*    */   }
/*    */   
/*    */   private void g(World paramWorld, int paramInt1, int paramInt2, int paramInt3) {
/* 42 */     h(paramWorld, paramInt1, paramInt2, paramInt3);
/* 43 */     if (this.id == Block.REDSTONE_ORE.id) {
/* 44 */       paramWorld.setTypeId(paramInt1, paramInt2, paramInt3, Block.GLOWING_REDSTONE_ORE.id);
/*    */     }
/*    */   }
/*    */   
/*    */   public void a(World paramWorld, int paramInt1, int paramInt2, int paramInt3, Random paramRandom) {
/* 49 */     if (this.id == Block.GLOWING_REDSTONE_ORE.id) {
/* 50 */       paramWorld.setTypeId(paramInt1, paramInt2, paramInt3, Block.REDSTONE_ORE.id);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/* 55 */   public int a(int paramInt, Random paramRandom) { return Item.REDSTONE.id; }
/*    */ 
/*    */ 
/*    */   
/* 59 */   public int a(Random paramRandom) { return 4 + paramRandom.nextInt(2); }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private void h(World paramWorld, int paramInt1, int paramInt2, int paramInt3) {
/* 69 */     Random random = paramWorld.random;
/* 70 */     double d = 0.0625D;
/* 71 */     for (byte b = 0; b < 6; b++) {
/* 72 */       double d1 = (paramInt1 + random.nextFloat());
/* 73 */       double d2 = (paramInt2 + random.nextFloat());
/* 74 */       double d3 = (paramInt3 + random.nextFloat());
/* 75 */       if (!b && !paramWorld.p(paramInt1, paramInt2 + 1, paramInt3)) d2 = (paramInt2 + 1) + d; 
/* 76 */       if (b == 1 && !paramWorld.p(paramInt1, paramInt2 - 1, paramInt3)) d2 = (paramInt2 + 0) - d; 
/* 77 */       if (b == 2 && !paramWorld.p(paramInt1, paramInt2, paramInt3 + 1)) d3 = (paramInt3 + 1) + d; 
/* 78 */       if (b == 3 && !paramWorld.p(paramInt1, paramInt2, paramInt3 - 1)) d3 = (paramInt3 + 0) - d; 
/* 79 */       if (b == 4 && !paramWorld.p(paramInt1 + 1, paramInt2, paramInt3)) d1 = (paramInt1 + 1) + d; 
/* 80 */       if (b == 5 && !paramWorld.p(paramInt1 - 1, paramInt2, paramInt3)) d1 = (paramInt1 + 0) - d; 
/* 81 */       if (d1 < paramInt1 || d1 > (paramInt1 + 1) || d2 < 0.0D || d2 > (paramInt2 + 1) || d3 < paramInt3 || d3 > (paramInt3 + 1))
/* 82 */         paramWorld.a("reddust", d1, d2, d3, 0.0D, 0.0D, 0.0D); 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\BlockRedstoneOre.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */