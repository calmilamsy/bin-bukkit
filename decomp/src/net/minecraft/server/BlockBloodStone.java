/*    */ package net.minecraft.server;
/*    */ 
/*    */ import org.bukkit.block.Block;
/*    */ import org.bukkit.event.block.BlockRedstoneEvent;
/*    */ 
/*    */ public class BlockBloodStone
/*    */   extends Block {
/*  8 */   public BlockBloodStone(int i, int j) { super(i, j, Material.STONE); }
/*    */ 
/*    */ 
/*    */   
/*    */   public void doPhysics(World world, int i, int j, int k, int l) {
/* 13 */     if (Block.byId[l] != null && Block.byId[l].isPowerSource()) {
/* 14 */       Block block = world.getWorld().getBlockAt(i, j, k);
/* 15 */       int power = block.getBlockPower();
/*    */       
/* 17 */       BlockRedstoneEvent event = new BlockRedstoneEvent(block, power, power);
/* 18 */       world.getServer().getPluginManager().callEvent(event);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\BlockBloodStone.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */