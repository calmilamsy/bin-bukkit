/*    */ package net.minecraft.server;
/*    */ 
/*    */ import org.bukkit.block.Block;
/*    */ import org.bukkit.craftbukkit.block.CraftBlockState;
/*    */ import org.bukkit.craftbukkit.event.CraftEventFactory;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.event.block.BlockIgniteEvent;
/*    */ import org.bukkit.event.block.BlockPlaceEvent;
/*    */ 
/*    */ public class ItemFlintAndSteel
/*    */   extends Item
/*    */ {
/*    */   public ItemFlintAndSteel(int i) {
/* 14 */     super(i);
/* 15 */     this.maxStackSize = 1;
/* 16 */     d(64);
/*    */   }
/*    */   
/*    */   public boolean a(ItemStack itemstack, EntityHuman entityhuman, World world, int i, int j, int k, int l) {
/* 20 */     int clickedX = i, clickedY = j, clickedZ = k;
/*    */     
/* 22 */     if (l == 0) {
/* 23 */       j--;
/*    */     }
/*    */     
/* 26 */     if (l == 1) {
/* 27 */       j++;
/*    */     }
/*    */     
/* 30 */     if (l == 2) {
/* 31 */       k--;
/*    */     }
/*    */     
/* 34 */     if (l == 3) {
/* 35 */       k++;
/*    */     }
/*    */     
/* 38 */     if (l == 4) {
/* 39 */       i--;
/*    */     }
/*    */     
/* 42 */     if (l == 5) {
/* 43 */       i++;
/*    */     }
/*    */     
/* 46 */     int i1 = world.getTypeId(i, j, k);
/*    */     
/* 48 */     if (i1 == 0) {
/*    */       
/* 50 */       Block blockClicked = world.getWorld().getBlockAt(i, j, k);
/* 51 */       Player thePlayer = (Player)entityhuman.getBukkitEntity();
/*    */       
/* 53 */       BlockIgniteEvent eventIgnite = new BlockIgniteEvent(blockClicked, BlockIgniteEvent.IgniteCause.FLINT_AND_STEEL, thePlayer);
/* 54 */       world.getServer().getPluginManager().callEvent(eventIgnite);
/*    */       
/* 56 */       if (eventIgnite.isCancelled()) {
/* 57 */         itemstack.damage(1, entityhuman);
/* 58 */         return false;
/*    */       } 
/*    */       
/* 61 */       CraftBlockState blockState = CraftBlockState.getBlockState(world, i, j, k);
/*    */ 
/*    */       
/* 64 */       world.makeSound(i + 0.5D, j + 0.5D, k + 0.5D, "fire.ignite", 1.0F, b.nextFloat() * 0.4F + 0.8F);
/* 65 */       world.setTypeId(i, j, k, Block.FIRE.id);
/*    */ 
/*    */       
/* 68 */       BlockPlaceEvent placeEvent = CraftEventFactory.callBlockPlaceEvent(world, entityhuman, blockState, clickedX, clickedY, clickedZ, Block.FIRE.id);
/*    */       
/* 70 */       if (placeEvent.isCancelled() || !placeEvent.canBuild()) {
/* 71 */         placeEvent.getBlockPlaced().setTypeIdAndData(0, (byte)0, false);
/* 72 */         return false;
/*    */       } 
/*    */     } 
/*    */ 
/*    */     
/* 77 */     itemstack.damage(1, entityhuman);
/* 78 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\ItemFlintAndSteel.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */