/*     */ package net.minecraft.server;
/*     */ import org.bukkit.entity.Entity;
/*     */ import org.bukkit.event.entity.EntityDeathEvent;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ 
/*     */ public class EntitySquid extends EntityWaterAnimal {
/*   7 */   public float a = 0.0F;
/*   8 */   public float b = 0.0F;
/*   9 */   public float c = 0.0F;
/*  10 */   public float f = 0.0F;
/*  11 */   public float g = 0.0F;
/*  12 */   public float h = 0.0F;
/*  13 */   public float i = 0.0F;
/*  14 */   public float j = 0.0F;
/*  15 */   private float k = 0.0F;
/*  16 */   private float l = 0.0F;
/*  17 */   private float m = 0.0F;
/*  18 */   private float n = 0.0F;
/*  19 */   private float o = 0.0F;
/*  20 */   private float p = 0.0F;
/*     */   
/*     */   public EntitySquid(World world) {
/*  23 */     super(world);
/*  24 */     this.texture = "/mob/squid.png";
/*  25 */     b(0.95F, 0.95F);
/*  26 */     this.l = 1.0F / (this.random.nextFloat() + 1.0F) * 0.2F;
/*     */   }
/*     */ 
/*     */   
/*  30 */   public void b(NBTTagCompound nbttagcompound) { super.b(nbttagcompound); }
/*     */ 
/*     */ 
/*     */   
/*  34 */   public void a(NBTTagCompound nbttagcompound) { super.a(nbttagcompound); }
/*     */ 
/*     */ 
/*     */   
/*  38 */   protected String g() { return null; }
/*     */ 
/*     */ 
/*     */   
/*  42 */   protected String h() { return null; }
/*     */ 
/*     */ 
/*     */   
/*  46 */   protected String i() { return null; }
/*     */ 
/*     */ 
/*     */   
/*  50 */   protected float k() { return 0.4F; }
/*     */ 
/*     */ 
/*     */   
/*  54 */   protected int j() { return 0; }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void q() {
/*  59 */     List<ItemStack> loot = new ArrayList<ItemStack>();
/*     */     
/*  61 */     int count = this.random.nextInt(3) + 1;
/*  62 */     if (count > 0) {
/*  63 */       loot.add(new ItemStack(Material.INK_SACK, count));
/*     */     }
/*     */     
/*  66 */     CraftWorld craftWorld = this.world.getWorld();
/*  67 */     Entity entity = getBukkitEntity();
/*     */     
/*  69 */     EntityDeathEvent event = new EntityDeathEvent(entity, loot);
/*  70 */     this.world.getServer().getPluginManager().callEvent(event);
/*     */     
/*  72 */     for (ItemStack stack : event.getDrops()) {
/*  73 */       craftWorld.dropItemNaturally(entity.getLocation(), stack);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*  79 */   public boolean a(EntityHuman entityhuman) { return false; }
/*     */ 
/*     */ 
/*     */   
/*  83 */   public boolean ad() { return this.world.a(this.boundingBox.b(0.0D, -0.6000000238418579D, 0.0D), Material.WATER, this); }
/*     */ 
/*     */   
/*     */   public void v() {
/*  87 */     super.v();
/*  88 */     this.b = this.a;
/*  89 */     this.f = this.c;
/*  90 */     this.h = this.g;
/*  91 */     this.j = this.i;
/*  92 */     this.g += this.l;
/*  93 */     if (this.g > 6.2831855F) {
/*  94 */       this.g -= 6.2831855F;
/*  95 */       if (this.random.nextInt(10) == 0) {
/*  96 */         this.l = 1.0F / (this.random.nextFloat() + 1.0F) * 0.2F;
/*     */       }
/*     */     } 
/*     */     
/* 100 */     if (ad()) {
/*     */ 
/*     */       
/* 103 */       if (this.g < 3.1415927F) {
/* 104 */         float f = this.g / 3.1415927F;
/* 105 */         this.i = MathHelper.sin(f * f * 3.1415927F) * 3.1415927F * 0.25F;
/* 106 */         if (f > 0.75D) {
/* 107 */           this.k = 1.0F;
/* 108 */           this.m = 1.0F;
/*     */         } else {
/* 110 */           this.m *= 0.8F;
/*     */         } 
/*     */       } else {
/* 113 */         this.i = 0.0F;
/* 114 */         this.k *= 0.9F;
/* 115 */         this.m *= 0.99F;
/*     */       } 
/*     */       
/* 118 */       if (!this.Y) {
/* 119 */         this.motX = (this.n * this.k);
/* 120 */         this.motY = (this.o * this.k);
/* 121 */         this.motZ = (this.p * this.k);
/*     */       } 
/*     */       
/* 124 */       float f = MathHelper.a(this.motX * this.motX + this.motZ * this.motZ);
/* 125 */       this.K += (-((float)Math.atan2(this.motX, this.motZ)) * 180.0F / 3.1415927F - this.K) * 0.1F;
/* 126 */       this.yaw = this.K;
/* 127 */       this.c += 3.1415927F * this.m * 1.5F;
/* 128 */       this.a += (-((float)Math.atan2(f, this.motY)) * 180.0F / 3.1415927F - this.a) * 0.1F;
/*     */     } else {
/* 130 */       this.i = MathHelper.abs(MathHelper.sin(this.g)) * 3.1415927F * 0.25F;
/* 131 */       if (!this.Y) {
/* 132 */         this.motX = 0.0D;
/* 133 */         this.motY -= 0.08D;
/* 134 */         this.motY *= 0.9800000190734863D;
/* 135 */         this.motZ = 0.0D;
/*     */       } 
/*     */       
/* 138 */       this.a = (float)(this.a + (-90.0F - this.a) * 0.02D);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/* 143 */   public void a(float f, float f1) { move(this.motX, this.motY, this.motZ); }
/*     */ 
/*     */   
/*     */   protected void c_() {
/* 147 */     if (this.random.nextInt(50) == 0 || !this.bA || (this.n == 0.0F && this.o == 0.0F && this.p == 0.0F)) {
/* 148 */       float f = this.random.nextFloat() * 3.1415927F * 2.0F;
/*     */       
/* 150 */       this.n = MathHelper.cos(f) * 0.2F;
/* 151 */       this.o = -0.1F + this.random.nextFloat() * 0.2F;
/* 152 */       this.p = MathHelper.sin(f) * 0.2F;
/*     */     } 
/*     */     
/* 155 */     U();
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\EntitySquid.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */