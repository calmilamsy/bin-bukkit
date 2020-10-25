/*     */ package net.minecraft.server;
/*     */ 
/*     */ import org.bukkit.Location;
/*     */ import org.bukkit.craftbukkit.event.CraftEventFactory;
/*     */ import org.bukkit.craftbukkit.inventory.CraftItemStack;
/*     */ import org.bukkit.event.player.PlayerBucketEmptyEvent;
/*     */ import org.bukkit.event.player.PlayerBucketFillEvent;
/*     */ 
/*     */ 
/*     */ public class ItemBucket
/*     */   extends Item
/*     */ {
/*     */   private int a;
/*     */   
/*     */   public ItemBucket(int i, int j) {
/*  16 */     super(i);
/*  17 */     this.maxStackSize = 1;
/*  18 */     this.a = j;
/*     */   }
/*     */   
/*     */   public ItemStack a(ItemStack itemstack, World world, EntityHuman entityhuman) {
/*  22 */     float f = 1.0F;
/*  23 */     float f1 = entityhuman.lastPitch + (entityhuman.pitch - entityhuman.lastPitch) * f;
/*  24 */     float f2 = entityhuman.lastYaw + (entityhuman.yaw - entityhuman.lastYaw) * f;
/*  25 */     double d0 = entityhuman.lastX + (entityhuman.locX - entityhuman.lastX) * f;
/*  26 */     double d1 = entityhuman.lastY + (entityhuman.locY - entityhuman.lastY) * f + 1.62D - entityhuman.height;
/*  27 */     double d2 = entityhuman.lastZ + (entityhuman.locZ - entityhuman.lastZ) * f;
/*  28 */     Vec3D vec3d = Vec3D.create(d0, d1, d2);
/*  29 */     float f3 = MathHelper.cos(-f2 * 0.017453292F - 3.1415927F);
/*  30 */     float f4 = MathHelper.sin(-f2 * 0.017453292F - 3.1415927F);
/*  31 */     float f5 = -MathHelper.cos(-f1 * 0.017453292F);
/*  32 */     float f6 = MathHelper.sin(-f1 * 0.017453292F);
/*  33 */     float f7 = f4 * f5;
/*  34 */     float f8 = f3 * f5;
/*  35 */     double d3 = 5.0D;
/*  36 */     Vec3D vec3d1 = vec3d.add(f7 * d3, f6 * d3, f8 * d3);
/*  37 */     MovingObjectPosition movingobjectposition = world.rayTrace(vec3d, vec3d1, (this.a == 0));
/*     */     
/*  39 */     if (movingobjectposition == null) {
/*  40 */       return itemstack;
/*     */     }
/*  42 */     if (movingobjectposition.type == EnumMovingObjectType.TILE) {
/*  43 */       int i = movingobjectposition.b;
/*  44 */       int j = movingobjectposition.c;
/*  45 */       int k = movingobjectposition.d;
/*     */       
/*  47 */       if (!world.a(entityhuman, i, j, k)) {
/*  48 */         return itemstack;
/*     */       }
/*     */       
/*  51 */       if (this.a == 0) {
/*  52 */         if (world.getMaterial(i, j, k) == Material.WATER && world.getData(i, j, k) == 0) {
/*     */           
/*  54 */           PlayerBucketFillEvent event = CraftEventFactory.callPlayerBucketFillEvent(entityhuman, i, j, k, -1, itemstack, Item.WATER_BUCKET);
/*     */           
/*  56 */           if (event.isCancelled()) {
/*  57 */             return itemstack;
/*     */           }
/*     */           
/*  60 */           CraftItemStack itemInHand = (CraftItemStack)event.getItemStack();
/*  61 */           byte data = (itemInHand.getData() == null) ? 0 : itemInHand.getData().getData();
/*     */ 
/*     */           
/*  64 */           world.setTypeId(i, j, k, 0);
/*  65 */           return new ItemStack(itemInHand.getTypeId(), itemInHand.getAmount(), data);
/*     */         } 
/*     */         
/*  68 */         if (world.getMaterial(i, j, k) == Material.LAVA && world.getData(i, j, k) == 0) {
/*     */           
/*  70 */           PlayerBucketFillEvent event = CraftEventFactory.callPlayerBucketFillEvent(entityhuman, i, j, k, -1, itemstack, Item.LAVA_BUCKET);
/*     */           
/*  72 */           if (event.isCancelled()) {
/*  73 */             return itemstack;
/*     */           }
/*     */           
/*  76 */           CraftItemStack itemInHand = (CraftItemStack)event.getItemStack();
/*  77 */           byte data = (itemInHand.getData() == null) ? 0 : itemInHand.getData().getData();
/*     */ 
/*     */           
/*  80 */           world.setTypeId(i, j, k, 0);
/*  81 */           return new ItemStack(itemInHand.getTypeId(), itemInHand.getAmount(), data);
/*     */         } 
/*     */       } else {
/*  84 */         if (this.a < 0) {
/*     */           
/*  86 */           PlayerBucketEmptyEvent event = CraftEventFactory.callPlayerBucketEmptyEvent(entityhuman, i, j, k, movingobjectposition.face, itemstack);
/*     */           
/*  88 */           if (event.isCancelled()) {
/*  89 */             return itemstack;
/*     */           }
/*     */           
/*  92 */           CraftItemStack itemInHand = (CraftItemStack)event.getItemStack();
/*  93 */           byte data = (itemInHand.getData() == null) ? 0 : itemInHand.getData().getData();
/*  94 */           return new ItemStack(itemInHand.getTypeId(), itemInHand.getAmount(), data);
/*     */         } 
/*     */         
/*  97 */         int clickedX = i, clickedY = j, clickedZ = k;
/*     */ 
/*     */         
/* 100 */         if (movingobjectposition.face == 0) {
/* 101 */           j--;
/*     */         }
/*     */         
/* 104 */         if (movingobjectposition.face == 1) {
/* 105 */           j++;
/*     */         }
/*     */         
/* 108 */         if (movingobjectposition.face == 2) {
/* 109 */           k--;
/*     */         }
/*     */         
/* 112 */         if (movingobjectposition.face == 3) {
/* 113 */           k++;
/*     */         }
/*     */         
/* 116 */         if (movingobjectposition.face == 4) {
/* 117 */           i--;
/*     */         }
/*     */         
/* 120 */         if (movingobjectposition.face == 5) {
/* 121 */           i++;
/*     */         }
/*     */         
/* 124 */         if (world.isEmpty(i, j, k) || !world.getMaterial(i, j, k).isBuildable())
/*     */         {
/* 126 */           PlayerBucketEmptyEvent event = CraftEventFactory.callPlayerBucketEmptyEvent(entityhuman, clickedX, clickedY, clickedZ, movingobjectposition.face, itemstack);
/*     */           
/* 128 */           if (event.isCancelled()) {
/* 129 */             return itemstack;
/*     */           }
/*     */ 
/*     */           
/* 133 */           if (world.worldProvider.d && this.a == Block.WATER.id) {
/* 134 */             world.makeSound(d0 + 0.5D, d1 + 0.5D, d2 + 0.5D, "random.fizz", 0.5F, 2.6F + (world.random.nextFloat() - world.random.nextFloat()) * 0.8F);
/*     */             
/* 136 */             for (int l = 0; l < 8; l++) {
/* 137 */               world.a("largesmoke", i + Math.random(), j + Math.random(), k + Math.random(), 0.0D, 0.0D, 0.0D);
/*     */             }
/*     */           } else {
/* 140 */             world.setTypeIdAndData(i, j, k, this.a, 0);
/*     */           } 
/*     */ 
/*     */           
/* 144 */           CraftItemStack itemInHand = (CraftItemStack)event.getItemStack();
/* 145 */           byte data = (itemInHand.getData() == null) ? 0 : itemInHand.getData().getData();
/*     */           
/* 147 */           return new ItemStack(itemInHand.getTypeId(), itemInHand.getAmount(), data);
/*     */         }
/*     */       
/*     */       } 
/* 151 */     } else if (this.a == 0 && movingobjectposition.entity instanceof EntityCow) {
/*     */       
/* 153 */       Location loc = movingobjectposition.entity.getBukkitEntity().getLocation();
/* 154 */       PlayerBucketFillEvent event = CraftEventFactory.callPlayerBucketFillEvent(entityhuman, loc.getBlockX(), loc.getBlockY(), loc.getBlockZ(), -1, itemstack, Item.MILK_BUCKET);
/*     */       
/* 156 */       if (event.isCancelled()) {
/* 157 */         return itemstack;
/*     */       }
/*     */       
/* 160 */       CraftItemStack itemInHand = (CraftItemStack)event.getItemStack();
/* 161 */       byte data = (itemInHand.getData() == null) ? 0 : itemInHand.getData().getData();
/* 162 */       return new ItemStack(itemInHand.getTypeId(), itemInHand.getAmount(), data);
/*     */     } 
/*     */ 
/*     */     
/* 166 */     return itemstack;
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\ItemBucket.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */