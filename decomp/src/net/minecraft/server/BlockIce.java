/*    */ package net.minecraft.server;
/*    */ 
/*    */ import java.util.Random;
/*    */ import org.bukkit.craftbukkit.event.CraftEventFactory;
/*    */ 
/*    */ public class BlockIce
/*    */   extends BlockBreakable {
/*    */   public BlockIce(int i, int j) {
/*  9 */     super(i, j, Material.ICE, false);
/* 10 */     this.frictionFactor = 0.98F;
/* 11 */     a(true);
/*    */   }
/*    */   
/*    */   public void a(World world, EntityHuman entityhuman, int i, int j, int k, int l) {
/* 15 */     super.a(world, entityhuman, i, j, k, l);
/* 16 */     Material material = world.getMaterial(i, j - 1, k);
/*    */     
/* 18 */     if (material.isSolid() || material.isLiquid()) {
/* 19 */       world.setTypeId(i, j, k, Block.WATER.id);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/* 24 */   public int a(Random random) { return 0; }
/*    */ 
/*    */   
/*    */   public void a(World world, int i, int j, int k, Random random) {
/* 28 */     if (world.a(EnumSkyBlock.BLOCK, i, j, k) > 11 - Block.q[this.id]) {
/*    */       
/* 30 */       if (CraftEventFactory.callBlockFadeEvent(world.getWorld().getBlockAt(i, j, k), Block.STATIONARY_WATER.id).isCancelled()) {
/*    */         return;
/*    */       }
/*    */ 
/*    */       
/* 35 */       g(world, i, j, k, world.getData(i, j, k));
/* 36 */       world.setTypeId(i, j, k, Block.STATIONARY_WATER.id);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/* 41 */   public int e() { return 0; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\BlockIce.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */