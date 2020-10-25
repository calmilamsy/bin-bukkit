/*    */ package net.minecraft.server;
/*    */ 
/*    */ import org.bukkit.craftbukkit.block.CraftBlockState;
/*    */ import org.bukkit.craftbukkit.event.CraftEventFactory;
/*    */ import org.bukkit.event.block.BlockPlaceEvent;
/*    */ 
/*    */ 
/*    */ public class ItemDoor
/*    */   extends Item
/*    */ {
/*    */   private Material a;
/*    */   
/*    */   public ItemDoor(int i, Material material) {
/* 14 */     super(i);
/* 15 */     this.a = material;
/* 16 */     this.maxStackSize = 1;
/*    */   }
/*    */   public boolean a(ItemStack itemstack, EntityHuman entityhuman, World world, int i, int j, int k, int l) {
/*    */     Block block;
/* 20 */     if (l != 1) {
/* 21 */       return false;
/*    */     }
/* 23 */     int clickedX = i, clickedY = j, clickedZ = k;
/*    */     
/* 25 */     j++;
/*    */ 
/*    */     
/* 28 */     if (this.a == Material.WOOD) {
/* 29 */       block = Block.WOODEN_DOOR;
/*    */     } else {
/* 31 */       block = Block.IRON_DOOR_BLOCK;
/*    */     } 
/*    */     
/* 34 */     if (!block.canPlace(world, i, j, k)) {
/* 35 */       return false;
/*    */     }
/* 37 */     int i1 = MathHelper.floor(((entityhuman.yaw + 180.0F) * 4.0F / 360.0F) - 0.5D) & 0x3;
/* 38 */     byte b0 = 0;
/* 39 */     byte b1 = 0;
/*    */     
/* 41 */     if (i1 == 0) {
/* 42 */       b1 = 1;
/*    */     }
/*    */     
/* 45 */     if (i1 == 1) {
/* 46 */       b0 = -1;
/*    */     }
/*    */     
/* 49 */     if (i1 == 2) {
/* 50 */       b1 = -1;
/*    */     }
/*    */     
/* 53 */     if (i1 == 3) {
/* 54 */       b0 = 1;
/*    */     }
/*    */     
/* 57 */     int j1 = (world.e(i - b0, j, k - b1) ? 1 : 0) + (world.e(i - b0, j + 1, k - b1) ? 1 : 0);
/* 58 */     int k1 = (world.e(i + b0, j, k + b1) ? 1 : 0) + (world.e(i + b0, j + 1, k + b1) ? 1 : 0);
/* 59 */     boolean flag = (world.getTypeId(i - b0, j, k - b1) == block.id || world.getTypeId(i - b0, j + 1, k - b1) == block.id);
/* 60 */     boolean flag1 = (world.getTypeId(i + b0, j, k + b1) == block.id || world.getTypeId(i + b0, j + 1, k + b1) == block.id);
/* 61 */     boolean flag2 = false;
/*    */     
/* 63 */     if (flag && !flag1) {
/* 64 */       flag2 = true;
/* 65 */     } else if (k1 > j1) {
/* 66 */       flag2 = true;
/*    */     } 
/*    */     
/* 69 */     if (flag2) {
/* 70 */       i1 = i1 - 1 & 0x3;
/* 71 */       i1 += 4;
/*    */     } 
/*    */     
/* 74 */     CraftBlockState blockState = CraftBlockState.getBlockState(world, i, j, k);
/*    */     
/* 76 */     world.suppressPhysics = true;
/* 77 */     world.setTypeIdAndData(i, j, k, block.id, i1);
/*    */ 
/*    */     
/* 80 */     world.suppressPhysics = false;
/* 81 */     world.applyPhysics(i, j, k, Block.REDSTONE_WIRE.id);
/* 82 */     BlockPlaceEvent event = CraftEventFactory.callBlockPlaceEvent(world, entityhuman, blockState, clickedX, clickedY, clickedZ, block);
/*    */     
/* 84 */     if (event.isCancelled() || !event.canBuild()) {
/* 85 */       event.getBlockPlaced().setTypeIdAndData(blockState.getTypeId(), blockState.getRawData(), false);
/* 86 */       return false;
/*    */     } 
/*    */     
/* 89 */     world.suppressPhysics = true;
/*    */     
/* 91 */     world.setTypeIdAndData(i, j + 1, k, block.id, i1 + 8);
/* 92 */     world.suppressPhysics = false;
/*    */     
/* 94 */     world.applyPhysics(i, j + 1, k, Block.REDSTONE_WIRE.id);
/* 95 */     itemstack.count--;
/* 96 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\ItemDoor.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */