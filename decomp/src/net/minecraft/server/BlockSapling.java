/*    */ package net.minecraft.server;
/*    */ 
/*    */ import java.util.Random;
/*    */ import org.bukkit.BlockChangeDelegate;
/*    */ 
/*    */ public class BlockSapling
/*    */   extends BlockFlower
/*    */ {
/*    */   protected BlockSapling(int i, int j) {
/* 10 */     super(i, j);
/* 11 */     float f = 0.4F;
/*    */     
/* 13 */     a(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, f * 2.0F, 0.5F + f);
/*    */   }
/*    */   
/*    */   public void a(World world, int i, int j, int k, Random random) {
/* 17 */     if (!world.isStatic) {
/* 18 */       super.a(world, i, j, k, random);
/* 19 */       if (world.getLightLevel(i, j + 1, k) >= 9 && random.nextInt(30) == 0) {
/* 20 */         int l = world.getData(i, j, k);
/*    */         
/* 22 */         if ((l & 0x8) == 0) {
/* 23 */           world.setData(i, j, k, l | 0x8);
/*    */         } else {
/* 25 */           b(world, i, j, k, random);
/*    */         } 
/*    */       } 
/*    */     } 
/*    */   }
/*    */   
/*    */   public int a(int i, int j) {
/* 32 */     j &= 0x3;
/* 33 */     return (j == 1) ? 63 : ((j == 2) ? 79 : super.a(i, j));
/*    */   }
/*    */   public void b(World world, int i, int j, int k, Random random) {
/*    */     boolean grownTree;
/* 37 */     int l = world.getData(i, j, k) & 0x3;
/*    */     
/* 39 */     world.setRawTypeId(i, j, k, 0);
/*    */ 
/*    */ 
/*    */     
/* 43 */     BlockChangeWithNotify delegate = new BlockChangeWithNotify(world);
/*    */     
/* 45 */     if (l == 1) {
/* 46 */       grownTree = (new WorldGenTaiga2()).generate(delegate, random, i, j, k);
/* 47 */     } else if (l == 2) {
/* 48 */       grownTree = (new WorldGenForest()).generate(delegate, random, i, j, k);
/*    */     }
/* 50 */     else if (random.nextInt(10) == 0) {
/* 51 */       grownTree = (new WorldGenBigTree()).generate(delegate, random, i, j, k);
/*    */     } else {
/* 53 */       grownTree = (new WorldGenTrees()).generate(delegate, random, i, j, k);
/*    */     } 
/*    */ 
/*    */     
/* 57 */     if (!grownTree) {
/* 58 */       world.setRawTypeIdAndData(i, j, k, this.id, l);
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */   
/* 64 */   protected int a_(int i) { return i & 0x3; }
/*    */   
/*    */   private class BlockChangeWithNotify
/*    */     implements BlockChangeDelegate
/*    */   {
/*    */     World world;
/*    */     
/* 71 */     BlockChangeWithNotify(World world) { this.world = world; }
/*    */ 
/*    */     
/* 74 */     public boolean setRawTypeId(int x, int y, int z, int type) { return this.world.setTypeId(x, y, z, type); }
/*    */ 
/*    */ 
/*    */     
/* 78 */     public boolean setRawTypeIdAndData(int x, int y, int z, int type, int data) { return this.world.setTypeIdAndData(x, y, z, type, data); }
/*    */ 
/*    */ 
/*    */     
/* 82 */     public int getTypeId(int x, int y, int z) { return this.world.getTypeId(x, y, z); }
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\BlockSapling.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */