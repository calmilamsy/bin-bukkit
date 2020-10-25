/*    */ package net.minecraft.server;
/*    */ 
/*    */ import org.bukkit.craftbukkit.block.CraftBlockState;
/*    */ import org.bukkit.craftbukkit.event.CraftEventFactory;
/*    */ import org.bukkit.event.block.BlockPlaceEvent;
/*    */ 
/*    */ 
/*    */ public class ItemHoe
/*    */   extends Item
/*    */ {
/*    */   public ItemHoe(int i, EnumToolMaterial enumtoolmaterial) {
/* 12 */     super(i);
/* 13 */     this.maxStackSize = 1;
/* 14 */     d(enumtoolmaterial.a());
/*    */   }
/*    */   
/*    */   public boolean a(ItemStack itemstack, EntityHuman entityhuman, World world, int i, int j, int k, int l) {
/* 18 */     int i1 = world.getTypeId(i, j, k);
/* 19 */     int j1 = world.getTypeId(i, j + 1, k);
/*    */     
/* 21 */     if ((l == 0 || j1 != 0 || i1 != Block.GRASS.id) && i1 != Block.DIRT.id) {
/* 22 */       return false;
/*    */     }
/* 24 */     Block block = Block.SOIL;
/*    */     
/* 26 */     world.makeSound((i + 0.5F), (j + 0.5F), (k + 0.5F), block.stepSound.getName(), (block.stepSound.getVolume1() + 1.0F) / 2.0F, block.stepSound.getVolume2() * 0.8F);
/* 27 */     if (world.isStatic) {
/* 28 */       return true;
/*    */     }
/* 30 */     CraftBlockState blockState = CraftBlockState.getBlockState(world, i, j, k);
/*    */     
/* 32 */     world.setTypeId(i, j, k, block.id);
/*    */ 
/*    */     
/* 35 */     BlockPlaceEvent event = CraftEventFactory.callBlockPlaceEvent(world, entityhuman, blockState, i, j, k, block);
/*    */     
/* 37 */     if (event.isCancelled() || !event.canBuild()) {
/* 38 */       event.getBlockPlaced().setTypeId(blockState.getTypeId());
/* 39 */       return false;
/*    */     } 
/*    */ 
/*    */     
/* 43 */     itemstack.damage(1, entityhuman);
/* 44 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\ItemHoe.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */