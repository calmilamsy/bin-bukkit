/*    */ package net.minecraft.server;
/*    */ 
/*    */ import org.bukkit.craftbukkit.block.CraftBlockState;
/*    */ import org.bukkit.craftbukkit.event.CraftEventFactory;
/*    */ import org.bukkit.event.block.BlockPlaceEvent;
/*    */ 
/*    */ 
/*    */ public class ItemSign
/*    */   extends Item
/*    */ {
/*    */   public ItemSign(int i) {
/* 12 */     super(i);
/* 13 */     this.maxStackSize = 1;
/*    */   }
/*    */   
/*    */   public boolean a(ItemStack itemstack, EntityHuman entityhuman, World world, int i, int j, int k, int l) {
/* 17 */     if (l == 0)
/* 18 */       return false; 
/* 19 */     if (!world.getMaterial(i, j, k).isBuildable()) {
/* 20 */       return false;
/*    */     }
/* 22 */     int clickedX = i, clickedY = j, clickedZ = k;
/*    */     
/* 24 */     if (l == 1) {
/* 25 */       j++;
/*    */     }
/*    */     
/* 28 */     if (l == 2) {
/* 29 */       k--;
/*    */     }
/*    */     
/* 32 */     if (l == 3) {
/* 33 */       k++;
/*    */     }
/*    */     
/* 36 */     if (l == 4) {
/* 37 */       i--;
/*    */     }
/*    */     
/* 40 */     if (l == 5) {
/* 41 */       i++;
/*    */     }
/*    */     
/* 44 */     if (!Block.SIGN_POST.canPlace(world, i, j, k)) {
/* 45 */       return false;
/*    */     }
/* 47 */     CraftBlockState blockState = CraftBlockState.getBlockState(world, i, j, k);
/*    */     
/* 49 */     if (l == 1) {
/* 50 */       world.setTypeIdAndData(i, j, k, Block.SIGN_POST.id, MathHelper.floor(((entityhuman.yaw + 180.0F) * 16.0F / 360.0F) + 0.5D) & 0xF);
/*    */     } else {
/* 52 */       world.setTypeIdAndData(i, j, k, Block.WALL_SIGN.id, l);
/*    */     } 
/*    */ 
/*    */     
/* 56 */     BlockPlaceEvent event = CraftEventFactory.callBlockPlaceEvent(world, entityhuman, blockState, clickedX, clickedY, clickedZ, (l == 1) ? Block.SIGN_POST : Block.WALL_SIGN);
/*    */     
/* 58 */     if (event.isCancelled() || !event.canBuild()) {
/* 59 */       event.getBlockPlaced().setTypeIdAndData(blockState.getTypeId(), blockState.getRawData(), false);
/* 60 */       return false;
/*    */     } 
/*    */ 
/*    */     
/* 64 */     itemstack.count--;
/* 65 */     TileEntitySign tileentitysign = (TileEntitySign)world.getTileEntity(i, j, k);
/*    */     
/* 67 */     if (tileentitysign != null) {
/* 68 */       entityhuman.a(tileentitysign);
/*    */     }
/*    */     
/* 71 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\ItemSign.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */