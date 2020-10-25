/*    */ package net.minecraft.server;
/*    */ 
/*    */ import org.bukkit.craftbukkit.block.CraftBlockState;
/*    */ import org.bukkit.craftbukkit.event.CraftEventFactory;
/*    */ import org.bukkit.event.block.BlockPlaceEvent;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ItemRedstone
/*    */   extends Item
/*    */ {
/* 12 */   public ItemRedstone(int i) { super(i); }
/*    */ 
/*    */   
/*    */   public boolean a(ItemStack itemstack, EntityHuman entityhuman, World world, int i, int j, int k, int l) {
/* 16 */     int clickedX = i, clickedY = j, clickedZ = k;
/*    */     
/* 18 */     if (world.getTypeId(i, j, k) != Block.SNOW.id) {
/* 19 */       if (l == 0) {
/* 20 */         j--;
/*    */       }
/*    */       
/* 23 */       if (l == 1) {
/* 24 */         j++;
/*    */       }
/*    */       
/* 27 */       if (l == 2) {
/* 28 */         k--;
/*    */       }
/*    */       
/* 31 */       if (l == 3) {
/* 32 */         k++;
/*    */       }
/*    */       
/* 35 */       if (l == 4) {
/* 36 */         i--;
/*    */       }
/*    */       
/* 39 */       if (l == 5) {
/* 40 */         i++;
/*    */       }
/*    */       
/* 43 */       if (!world.isEmpty(i, j, k)) {
/* 44 */         return false;
/*    */       }
/*    */     } 
/*    */     
/* 48 */     if (Block.REDSTONE_WIRE.canPlace(world, i, j, k)) {
/* 49 */       CraftBlockState blockState = CraftBlockState.getBlockState(world, i, j, k);
/*    */       
/* 51 */       world.setRawTypeId(i, j, k, Block.REDSTONE_WIRE.id);
/*    */ 
/*    */       
/* 54 */       BlockPlaceEvent event = CraftEventFactory.callBlockPlaceEvent(world, entityhuman, blockState, clickedX, clickedY, clickedZ, Block.REDSTONE_WIRE);
/*    */       
/* 56 */       if (event.isCancelled() || !event.canBuild()) {
/* 57 */         event.getBlockPlaced().setTypeIdAndData(blockState.getTypeId(), blockState.getRawData(), false);
/* 58 */         return false;
/*    */       } 
/*    */       
/* 61 */       world.update(i, j, k, Block.REDSTONE_WIRE.id);
/*    */ 
/*    */       
/* 64 */       itemstack.count--;
/*    */     } 
/*    */     
/* 67 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\ItemRedstone.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */