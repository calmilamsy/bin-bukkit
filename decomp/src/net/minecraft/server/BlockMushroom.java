/*    */ package net.minecraft.server;
/*    */ 
/*    */ import java.util.Random;
/*    */ import org.bukkit.block.BlockState;
/*    */ import org.bukkit.craftbukkit.CraftWorld;
/*    */ import org.bukkit.event.block.BlockSpreadEvent;
/*    */ 
/*    */ public class BlockMushroom extends BlockFlower {
/*    */   protected BlockMushroom(int i, int j) {
/* 10 */     super(i, j);
/* 11 */     float f = 0.2F;
/*    */     
/* 13 */     a(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, f * 2.0F, 0.5F + f);
/* 14 */     a(true);
/*    */   }
/*    */   
/*    */   public void a(World world, int i, int j, int k, Random random) {
/* 18 */     if (random.nextInt(100) == 0) {
/* 19 */       int l = i + random.nextInt(3) - 1;
/* 20 */       int i1 = j + random.nextInt(2) - random.nextInt(2);
/* 21 */       int j1 = k + random.nextInt(3) - 1;
/*    */       
/* 23 */       if (world.isEmpty(l, i1, j1) && f(world, l, i1, j1)) {
/* 24 */         int k1 = i + random.nextInt(3) - 1;
/*    */         
/* 26 */         k1 = k + random.nextInt(3) - 1;
/* 27 */         if (world.isEmpty(l, i1, j1) && f(world, l, i1, j1)) {
/*    */           
/* 29 */           CraftWorld craftWorld = world.getWorld();
/* 30 */           BlockState blockState = craftWorld.getBlockAt(l, i1, j1).getState();
/* 31 */           blockState.setTypeId(this.id);
/*    */           
/* 33 */           BlockSpreadEvent event = new BlockSpreadEvent(blockState.getBlock(), craftWorld.getBlockAt(i, j, k), blockState);
/* 34 */           world.getServer().getPluginManager().callEvent(event);
/*    */           
/* 36 */           if (!event.isCancelled()) {
/* 37 */             blockState.update(true);
/*    */           }
/*    */         } 
/*    */       } 
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/* 46 */   protected boolean c(int i) { return Block.o[i]; }
/*    */ 
/*    */ 
/*    */   
/* 50 */   public boolean f(World world, int i, int j, int k) { return (j >= 0 && j < 128) ? ((world.k(i, j, k) < 13 && c(world.getTypeId(i, j - 1, k)))) : false; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\BlockMushroom.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */