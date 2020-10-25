/*    */ package net.minecraft.server;
/*    */ 
/*    */ import org.bukkit.craftbukkit.event.CraftEventFactory;
/*    */ import org.bukkit.event.block.Action;
/*    */ import org.bukkit.event.player.PlayerInteractEvent;
/*    */ 
/*    */ 
/*    */ public class ItemMinecart
/*    */   extends Item
/*    */ {
/*    */   public int a;
/*    */   
/*    */   public ItemMinecart(int i, int j) {
/* 14 */     super(i);
/* 15 */     this.maxStackSize = 1;
/* 16 */     this.a = j;
/*    */   }
/*    */   
/*    */   public boolean a(ItemStack itemstack, EntityHuman entityhuman, World world, int i, int j, int k, int l) {
/* 20 */     int i1 = world.getTypeId(i, j, k);
/*    */     
/* 22 */     if (BlockMinecartTrack.c(i1)) {
/* 23 */       if (!world.isStatic) {
/*    */         
/* 25 */         PlayerInteractEvent event = CraftEventFactory.callPlayerInteractEvent(entityhuman, Action.RIGHT_CLICK_BLOCK, i, j, k, l, itemstack);
/*    */         
/* 27 */         if (event.isCancelled()) {
/* 28 */           return false;
/*    */         }
/*    */ 
/*    */         
/* 32 */         world.addEntity(new EntityMinecart(world, (i + 0.5F), (j + 0.5F), (k + 0.5F), this.a));
/*    */       } 
/*    */       
/* 35 */       itemstack.count--;
/* 36 */       return true;
/*    */     } 
/* 38 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\ItemMinecart.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */