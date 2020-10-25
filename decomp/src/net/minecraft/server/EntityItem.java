/*     */ package net.minecraft.server;
/*     */ 
/*     */ import org.bukkit.entity.Item;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.event.player.PlayerPickupItemEvent;
/*     */ 
/*     */ public class EntityItem extends Entity {
/*     */   public ItemStack itemStack;
/*   9 */   public int b = 0; private int e;
/*     */   public int pickupDelay;
/*  11 */   private int f = 5;
/*  12 */   public float d = (float)(Math.random() * Math.PI * 2.0D);
/*  13 */   private int lastTick = (int)(System.currentTimeMillis() / 50L);
/*     */   
/*     */   public EntityItem(World world, double d0, double d1, double d2, ItemStack itemstack) {
/*  16 */     super(world);
/*  17 */     b(0.25F, 0.25F);
/*  18 */     this.height = this.width / 2.0F;
/*  19 */     setPosition(d0, d1, d2);
/*  20 */     this.itemStack = itemstack;
/*     */     
/*  22 */     if (this.itemStack.count <= -1) {
/*  23 */       this.itemStack.count = 1;
/*     */     }
/*     */     
/*  26 */     this.yaw = (float)(Math.random() * 360.0D);
/*  27 */     this.motX = (float)(Math.random() * 0.20000000298023224D - 0.10000000149011612D);
/*  28 */     this.motY = 0.20000000298023224D;
/*  29 */     this.motZ = (float)(Math.random() * 0.20000000298023224D - 0.10000000149011612D);
/*     */   }
/*     */ 
/*     */   
/*  33 */   protected boolean n() { return false; }
/*     */ 
/*     */   
/*     */   public EntityItem(World world) {
/*  37 */     super(world);
/*  38 */     b(0.25F, 0.25F);
/*  39 */     this.height = this.width / 2.0F;
/*     */   }
/*     */   
/*     */   protected void b() {}
/*     */   
/*     */   public void m_() {
/*  45 */     super.m_();
/*     */     
/*  47 */     int currentTick = (int)(System.currentTimeMillis() / 50L);
/*  48 */     this.pickupDelay -= currentTick - this.lastTick;
/*  49 */     this.lastTick = currentTick;
/*     */ 
/*     */     
/*  52 */     this.lastX = this.locX;
/*  53 */     this.lastY = this.locY;
/*  54 */     this.lastZ = this.locZ;
/*  55 */     this.motY -= 0.03999999910593033D;
/*  56 */     if (this.world.getMaterial(MathHelper.floor(this.locX), MathHelper.floor(this.locY), MathHelper.floor(this.locZ)) == Material.LAVA) {
/*  57 */       this.motY = 0.20000000298023224D;
/*  58 */       this.motX = ((this.random.nextFloat() - this.random.nextFloat()) * 0.2F);
/*  59 */       this.motZ = ((this.random.nextFloat() - this.random.nextFloat()) * 0.2F);
/*  60 */       this.world.makeSound(this, "random.fizz", 0.4F, 2.0F + this.random.nextFloat() * 0.4F);
/*     */     } 
/*     */     
/*  63 */     g(this.locX, (this.boundingBox.b + this.boundingBox.e) / 2.0D, this.locZ);
/*  64 */     move(this.motX, this.motY, this.motZ);
/*  65 */     float f = 0.98F;
/*     */     
/*  67 */     if (this.onGround) {
/*  68 */       f = 0.58800006F;
/*  69 */       int i = this.world.getTypeId(MathHelper.floor(this.locX), MathHelper.floor(this.boundingBox.b) - 1, MathHelper.floor(this.locZ));
/*     */       
/*  71 */       if (i > 0) {
/*  72 */         f = (Block.byId[i]).frictionFactor * 0.98F;
/*     */       }
/*     */     } 
/*     */     
/*  76 */     this.motX *= f;
/*  77 */     this.motY *= 0.9800000190734863D;
/*  78 */     this.motZ *= f;
/*  79 */     if (this.onGround) {
/*  80 */       this.motY *= -0.5D;
/*     */     }
/*     */     
/*  83 */     this.e++;
/*  84 */     this.b++;
/*  85 */     if (this.b >= 6000) {
/*  86 */       die();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*  91 */   public boolean f_() { return this.world.a(this.boundingBox, Material.WATER, this); }
/*     */ 
/*     */ 
/*     */   
/*  95 */   protected void burn(int i) { damageEntity((Entity)null, i); }
/*     */ 
/*     */   
/*     */   public boolean damageEntity(Entity entity, int i) {
/*  99 */     af();
/* 100 */     this.f -= i;
/* 101 */     if (this.f <= 0) {
/* 102 */       die();
/*     */     }
/*     */     
/* 105 */     return false;
/*     */   }
/*     */   
/*     */   public void b(NBTTagCompound nbttagcompound) {
/* 109 */     nbttagcompound.a("Health", (short)(byte)this.f);
/* 110 */     nbttagcompound.a("Age", (short)this.b);
/* 111 */     nbttagcompound.a("Item", this.itemStack.a(new NBTTagCompound()));
/*     */   }
/*     */   
/*     */   public void a(NBTTagCompound nbttagcompound) {
/* 115 */     this.f = nbttagcompound.d("Health") & 0xFF;
/* 116 */     this.b = nbttagcompound.d("Age");
/* 117 */     NBTTagCompound nbttagcompound1 = nbttagcompound.k("Item");
/*     */     
/* 119 */     this.itemStack = new ItemStack(nbttagcompound1);
/*     */   }
/*     */   
/*     */   public void b(EntityHuman entityhuman) {
/* 123 */     if (!this.world.isStatic) {
/* 124 */       int i = this.itemStack.count;
/*     */ 
/*     */       
/* 127 */       int canHold = entityhuman.inventory.canHold(this.itemStack);
/* 128 */       int remaining = this.itemStack.count - canHold;
/* 129 */       if (this.pickupDelay <= 0 && canHold > 0) {
/* 130 */         this.itemStack.count = canHold;
/* 131 */         PlayerPickupItemEvent event = new PlayerPickupItemEvent((Player)entityhuman.getBukkitEntity(), (Item)getBukkitEntity(), remaining);
/* 132 */         this.world.getServer().getPluginManager().callEvent(event);
/* 133 */         this.itemStack.count = canHold + remaining;
/*     */         
/* 135 */         if (event.isCancelled()) {
/*     */           return;
/*     */         }
/*     */ 
/*     */         
/* 140 */         this.pickupDelay = 0;
/*     */       } 
/*     */ 
/*     */       
/* 144 */       if (this.pickupDelay == 0 && entityhuman.inventory.pickup(this.itemStack)) {
/* 145 */         if (this.itemStack.id == Block.LOG.id) {
/* 146 */           entityhuman.a(AchievementList.g);
/*     */         }
/*     */         
/* 149 */         if (this.itemStack.id == Item.LEATHER.id) {
/* 150 */           entityhuman.a(AchievementList.t);
/*     */         }
/*     */         
/* 153 */         this.world.makeSound(this, "random.pop", 0.2F, ((this.random.nextFloat() - this.random.nextFloat()) * 0.7F + 1.0F) * 2.0F);
/* 154 */         entityhuman.receive(this, i);
/* 155 */         if (this.itemStack.count <= 0)
/* 156 */           die(); 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\EntityItem.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */