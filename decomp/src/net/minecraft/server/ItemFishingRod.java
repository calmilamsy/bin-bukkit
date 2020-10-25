/*    */ package net.minecraft.server;
/*    */ 
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.event.player.PlayerFishEvent;
/*    */ 
/*    */ public class ItemFishingRod extends Item {
/*    */   public ItemFishingRod(int i) {
/*  8 */     super(i);
/*  9 */     d(64);
/* 10 */     c(1);
/*    */   }
/*    */   
/*    */   public ItemStack a(ItemStack itemstack, World world, EntityHuman entityhuman) {
/* 14 */     if (entityhuman.hookedFish != null) {
/* 15 */       int i = entityhuman.hookedFish.h();
/*    */       
/* 17 */       itemstack.damage(i, entityhuman);
/* 18 */       entityhuman.w();
/*    */     } else {
/*    */       
/* 21 */       PlayerFishEvent playerFishEvent = new PlayerFishEvent((Player)entityhuman.getBukkitEntity(), null, PlayerFishEvent.State.FISHING);
/* 22 */       world.getServer().getPluginManager().callEvent(playerFishEvent);
/*    */       
/* 24 */       if (playerFishEvent.isCancelled()) {
/* 25 */         return itemstack;
/*    */       }
/*    */       
/* 28 */       world.makeSound(entityhuman, "random.bow", 0.5F, 0.4F / (b.nextFloat() * 0.4F + 0.8F));
/* 29 */       if (!world.isStatic) {
/* 30 */         world.addEntity(new EntityFish(world, entityhuman));
/*    */       }
/*    */       
/* 33 */       entityhuman.w();
/*    */     } 
/*    */     
/* 36 */     return itemstack;
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\ItemFishingRod.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */