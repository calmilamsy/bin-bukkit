/*    */ package net.minecraft.server;
/*    */ 
/*    */ import org.bukkit.craftbukkit.event.CraftEventFactory;
/*    */ import org.bukkit.event.block.Action;
/*    */ import org.bukkit.event.player.PlayerInteractEvent;
/*    */ 
/*    */ 
/*    */ public class ItemBoat
/*    */   extends Item
/*    */ {
/*    */   public ItemBoat(int i) {
/* 12 */     super(i);
/* 13 */     this.maxStackSize = 1;
/*    */   }
/*    */   
/*    */   public ItemStack a(ItemStack itemstack, World world, EntityHuman entityhuman) {
/* 17 */     float f = 1.0F;
/* 18 */     float f1 = entityhuman.lastPitch + (entityhuman.pitch - entityhuman.lastPitch) * f;
/* 19 */     float f2 = entityhuman.lastYaw + (entityhuman.yaw - entityhuman.lastYaw) * f;
/* 20 */     double d0 = entityhuman.lastX + (entityhuman.locX - entityhuman.lastX) * f;
/* 21 */     double d1 = entityhuman.lastY + (entityhuman.locY - entityhuman.lastY) * f + 1.62D - entityhuman.height;
/* 22 */     double d2 = entityhuman.lastZ + (entityhuman.locZ - entityhuman.lastZ) * f;
/* 23 */     Vec3D vec3d = Vec3D.create(d0, d1, d2);
/* 24 */     float f3 = MathHelper.cos(-f2 * 0.017453292F - 3.1415927F);
/* 25 */     float f4 = MathHelper.sin(-f2 * 0.017453292F - 3.1415927F);
/* 26 */     float f5 = -MathHelper.cos(-f1 * 0.017453292F);
/* 27 */     float f6 = MathHelper.sin(-f1 * 0.017453292F);
/* 28 */     float f7 = f4 * f5;
/* 29 */     float f8 = f3 * f5;
/* 30 */     double d3 = 5.0D;
/* 31 */     Vec3D vec3d1 = vec3d.add(f7 * d3, f6 * d3, f8 * d3);
/* 32 */     MovingObjectPosition movingobjectposition = world.rayTrace(vec3d, vec3d1, true);
/*    */     
/* 34 */     if (movingobjectposition == null) {
/* 35 */       return itemstack;
/*    */     }
/* 37 */     if (movingobjectposition.type == EnumMovingObjectType.TILE) {
/* 38 */       int i = movingobjectposition.b;
/* 39 */       int j = movingobjectposition.c;
/* 40 */       int k = movingobjectposition.d;
/*    */       
/* 42 */       if (!world.isStatic) {
/*    */         
/* 44 */         PlayerInteractEvent event = CraftEventFactory.callPlayerInteractEvent(entityhuman, Action.RIGHT_CLICK_BLOCK, i, j, k, movingobjectposition.face, itemstack);
/*    */         
/* 46 */         if (event.isCancelled()) {
/* 47 */           return itemstack;
/*    */         }
/*    */ 
/*    */         
/* 51 */         if (world.getTypeId(i, j, k) == Block.SNOW.id) {
/* 52 */           j--;
/*    */         }
/*    */         
/* 55 */         world.addEntity(new EntityBoat(world, (i + 0.5F), (j + 1.0F), (k + 0.5F)));
/*    */       } 
/*    */       
/* 58 */       itemstack.count--;
/*    */     } 
/*    */     
/* 61 */     return itemstack;
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\ItemBoat.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */