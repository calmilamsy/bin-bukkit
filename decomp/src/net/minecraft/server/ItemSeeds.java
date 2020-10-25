/*    */ package net.minecraft.server;
/*    */ 
/*    */ import org.bukkit.craftbukkit.block.CraftBlockState;
/*    */ import org.bukkit.craftbukkit.event.CraftEventFactory;
/*    */ import org.bukkit.event.block.BlockPlaceEvent;
/*    */ 
/*    */ 
/*    */ public class ItemSeeds
/*    */   extends Item
/*    */ {
/*    */   private int id;
/*    */   
/*    */   public ItemSeeds(int i, int j) {
/* 14 */     super(i);
/* 15 */     this.id = j;
/*    */   }
/*    */   
/*    */   public boolean a(ItemStack itemstack, EntityHuman entityhuman, World world, int i, int j, int k, int l) {
/* 19 */     if (l != 1) {
/* 20 */       return false;
/*    */     }
/* 22 */     int i1 = world.getTypeId(i, j, k);
/*    */     
/* 24 */     if (i1 == Block.SOIL.id && world.isEmpty(i, j + 1, k)) {
/* 25 */       CraftBlockState blockState = CraftBlockState.getBlockState(world, i, j + 1, k);
/*    */       
/* 27 */       world.setTypeId(i, j + 1, k, this.id);
/*    */ 
/*    */       
/* 30 */       BlockPlaceEvent event = CraftEventFactory.callBlockPlaceEvent(world, entityhuman, blockState, i, j, k, this.id);
/*    */       
/* 32 */       if (event.isCancelled() || !event.canBuild()) {
/* 33 */         event.getBlockPlaced().setTypeId(0);
/* 34 */         return false;
/*    */       } 
/*    */ 
/*    */       
/* 38 */       itemstack.count--;
/* 39 */       return true;
/*    */     } 
/* 41 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\ItemSeeds.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */