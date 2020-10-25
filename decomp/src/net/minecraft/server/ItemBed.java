/*    */ package net.minecraft.server;
/*    */ 
/*    */ import org.bukkit.craftbukkit.block.CraftBlockState;
/*    */ import org.bukkit.craftbukkit.event.CraftEventFactory;
/*    */ import org.bukkit.event.block.BlockPlaceEvent;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ItemBed
/*    */   extends Item
/*    */ {
/* 12 */   public ItemBed(int i) { super(i); }
/*    */ 
/*    */   
/*    */   public boolean a(ItemStack itemstack, EntityHuman entityhuman, World world, int i, int j, int k, int l) {
/* 16 */     if (l != 1) {
/* 17 */       return false;
/*    */     }
/* 19 */     int clickedX = i, clickedY = j, clickedZ = k;
/*    */     
/* 21 */     j++;
/* 22 */     BlockBed blockbed = (BlockBed)Block.BED;
/* 23 */     int i1 = MathHelper.floor((entityhuman.yaw * 4.0F / 360.0F) + 0.5D) & 0x3;
/* 24 */     byte b0 = 0;
/* 25 */     byte b1 = 0;
/*    */     
/* 27 */     if (i1 == 0) {
/* 28 */       b1 = 1;
/*    */     }
/*    */     
/* 31 */     if (i1 == 1) {
/* 32 */       b0 = -1;
/*    */     }
/*    */     
/* 35 */     if (i1 == 2) {
/* 36 */       b1 = -1;
/*    */     }
/*    */     
/* 39 */     if (i1 == 3) {
/* 40 */       b0 = 1;
/*    */     }
/*    */     
/* 43 */     if (world.isEmpty(i, j, k) && world.isEmpty(i + b0, j, k + b1) && world.e(i, j - 1, k) && world.e(i + b0, j - 1, k + b1)) {
/* 44 */       CraftBlockState blockState = CraftBlockState.getBlockState(world, i, j, k);
/*    */       
/* 46 */       world.setTypeIdAndData(i, j, k, blockbed.id, i1);
/*    */ 
/*    */       
/* 49 */       BlockPlaceEvent event = CraftEventFactory.callBlockPlaceEvent(world, entityhuman, blockState, clickedX, clickedY, clickedZ, blockbed);
/*    */       
/* 51 */       if (event.isCancelled() || !event.canBuild()) {
/* 52 */         event.getBlockPlaced().setTypeIdAndData(blockState.getTypeId(), blockState.getRawData(), false);
/* 53 */         return false;
/*    */       } 
/*    */ 
/*    */       
/* 57 */       world.setTypeIdAndData(i + b0, j, k + b1, blockbed.id, i1 + 8);
/* 58 */       itemstack.count--;
/* 59 */       return true;
/*    */     } 
/* 61 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\ItemBed.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */