/*    */ package net.minecraft.server;
/*    */ 
/*    */ import java.util.Random;
/*    */ import org.bukkit.block.Block;
/*    */ import org.bukkit.craftbukkit.CraftWorld;
/*    */ import org.bukkit.event.block.BlockIgniteEvent;
/*    */ 
/*    */ public class BlockStationary extends BlockFluids {
/*    */   protected BlockStationary(int i, Material material) {
/* 10 */     super(i, material);
/* 11 */     a(false);
/* 12 */     if (material == Material.LAVA) {
/* 13 */       a(true);
/*    */     }
/*    */   }
/*    */   
/*    */   public void doPhysics(World world, int i, int j, int k, int l) {
/* 18 */     super.doPhysics(world, i, j, k, l);
/* 19 */     if (world.getTypeId(i, j, k) == this.id) {
/* 20 */       i(world, i, j, k);
/*    */     }
/*    */   }
/*    */   
/*    */   private void i(World world, int i, int j, int k) {
/* 25 */     int l = world.getData(i, j, k);
/*    */     
/* 27 */     world.suppressPhysics = true;
/* 28 */     world.setRawTypeIdAndData(i, j, k, this.id - 1, l);
/* 29 */     world.b(i, j, k, i, j, k);
/* 30 */     world.c(i, j, k, this.id - 1, c());
/* 31 */     world.suppressPhysics = false;
/*    */   }
/*    */   
/*    */   public void a(World world, int i, int j, int k, Random random) {
/* 35 */     if (this.material == Material.LAVA) {
/* 36 */       int l = random.nextInt(3);
/*    */ 
/*    */       
/* 39 */       CraftWorld craftWorld = world.getWorld();
/* 40 */       BlockIgniteEvent.IgniteCause igniteCause = BlockIgniteEvent.IgniteCause.LAVA;
/*    */ 
/*    */       
/* 43 */       for (int i1 = 0; i1 < l; i1++) {
/* 44 */         i += random.nextInt(3) - 1;
/* 45 */         j++;
/* 46 */         k += random.nextInt(3) - 1;
/* 47 */         int j1 = world.getTypeId(i, j, k);
/*    */         
/* 49 */         if (j1 == 0) {
/* 50 */           if (j(world, i - 1, j, k) || j(world, i + 1, j, k) || j(world, i, j, k - 1) || j(world, i, j, k + 1) || j(world, i, j - 1, k) || j(world, i, j + 1, k)) {
/*    */             
/* 52 */             Block block = craftWorld.getBlockAt(i, j, k);
/*    */             
/* 54 */             if (block.getTypeId() != Block.FIRE.id) {
/* 55 */               BlockIgniteEvent event = new BlockIgniteEvent(block, igniteCause, null);
/* 56 */               world.getServer().getPluginManager().callEvent(event);
/*    */               
/* 58 */               if (event.isCancelled()) {
/*    */                 continue;
/*    */               }
/*    */             } 
/*    */ 
/*    */             
/* 64 */             world.setTypeId(i, j, k, Block.FIRE.id); return;
/*    */           }  continue;
/*    */         } 
/* 67 */         if ((Block.byId[j1]).material.isSolid()) {
/*    */           return;
/*    */         }
/*    */       } 
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/* 75 */   private boolean j(World world, int i, int j, int k) { return world.getMaterial(i, j, k).isBurnable(); }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\BlockStationary.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */