/*    */ package net.minecraft.server;
/*    */ 
/*    */ import org.bukkit.craftbukkit.block.CraftBlockState;
/*    */ import org.bukkit.craftbukkit.event.CraftEventFactory;
/*    */ import org.bukkit.event.block.BlockPlaceEvent;
/*    */ 
/*    */ 
/*    */ public class ItemReed
/*    */   extends Item
/*    */ {
/*    */   private int id;
/*    */   
/*    */   public ItemReed(int i, Block block) {
/* 14 */     super(i);
/* 15 */     this.id = block.id;
/*    */   }
/*    */   
/*    */   public boolean a(ItemStack itemstack, EntityHuman entityhuman, World world, int i, int j, int k, int l) {
/* 19 */     int clickedX = i, clickedY = j, clickedZ = k;
/*    */     
/* 21 */     if (world.getTypeId(i, j, k) == Block.SNOW.id) {
/* 22 */       l = 0;
/*    */     } else {
/* 24 */       if (l == 0) {
/* 25 */         j--;
/*    */       }
/*    */       
/* 28 */       if (l == 1) {
/* 29 */         j++;
/*    */       }
/*    */       
/* 32 */       if (l == 2) {
/* 33 */         k--;
/*    */       }
/*    */       
/* 36 */       if (l == 3) {
/* 37 */         k++;
/*    */       }
/*    */       
/* 40 */       if (l == 4) {
/* 41 */         i--;
/*    */       }
/*    */       
/* 44 */       if (l == 5) {
/* 45 */         i++;
/*    */       }
/*    */     } 
/*    */     
/* 49 */     if (itemstack.count == 0) {
/* 50 */       return false;
/*    */     }
/* 52 */     if (world.a(this.id, i, j, k, false, l)) {
/* 53 */       Block block = Block.byId[this.id];
/*    */ 
/*    */       
/* 56 */       CraftBlockState replacedBlockState = CraftBlockState.getBlockState(world, i, j, k);
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */       
/* 67 */       if (world.setRawTypeId(i, j, k, this.id)) {
/* 68 */         BlockPlaceEvent event = CraftEventFactory.callBlockPlaceEvent(world, entityhuman, replacedBlockState, clickedX, clickedY, clickedZ, block);
/*    */         
/* 70 */         if (event.isCancelled() || !event.canBuild()) {
/*    */           
/* 72 */           world.setTypeIdAndData(i, j, k, replacedBlockState.getTypeId(), replacedBlockState.getRawData());
/*    */           
/* 74 */           return true;
/*    */         } 
/*    */         
/* 77 */         world.update(i, j, k, this.id);
/*    */ 
/*    */         
/* 80 */         Block.byId[this.id].postPlace(world, i, j, k, l);
/* 81 */         Block.byId[this.id].postPlace(world, i, j, k, entityhuman);
/* 82 */         world.makeSound((i + 0.5F), (j + 0.5F), (k + 0.5F), block.stepSound.getName(), (block.stepSound.getVolume1() + 1.0F) / 2.0F, block.stepSound.getVolume2() * 0.8F);
/* 83 */         itemstack.count--;
/*    */       } 
/*    */     } 
/*    */     
/* 87 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\ItemReed.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */