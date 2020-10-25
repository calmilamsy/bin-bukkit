/*     */ package net.minecraft.server;
/*     */ 
/*     */ import java.util.List;
/*     */ import org.bukkit.craftbukkit.CraftWorld;
/*     */ import org.bukkit.event.block.BlockIgniteEvent;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EntityWeatherStorm
/*     */   extends EntityWeather
/*     */ {
/*     */   private int lifeTicks;
/*  14 */   public long a = 0L;
/*     */   
/*     */   private int c;
/*     */   
/*     */   private CraftWorld cworld;
/*     */   
/*     */   public boolean isEffect = false;
/*     */   
/*  22 */   public EntityWeatherStorm(World world, double d0, double d1, double d2) { this(world, d0, d1, d2, false); }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EntityWeatherStorm(World world, double d0, double d1, double d2, boolean isEffect) {
/*  28 */     super(world);
/*     */ 
/*     */     
/*  31 */     this.isEffect = isEffect;
/*  32 */     this.cworld = world.getWorld();
/*     */ 
/*     */     
/*  35 */     setPositionRotation(d0, d1, d2, 0.0F, 0.0F);
/*  36 */     this.lifeTicks = 2;
/*  37 */     this.a = this.random.nextLong();
/*  38 */     this.c = this.random.nextInt(3) + 1;
/*     */     
/*  40 */     if (!isEffect && world.spawnMonsters >= 2 && world.areChunksLoaded(MathHelper.floor(d0), MathHelper.floor(d1), MathHelper.floor(d2), 10)) {
/*  41 */       int i = MathHelper.floor(d0);
/*  42 */       int j = MathHelper.floor(d1);
/*  43 */       int k = MathHelper.floor(d2);
/*     */       
/*  45 */       if (world.getTypeId(i, j, k) == 0 && Block.FIRE.canPlace(world, i, j, k)) {
/*     */         
/*  47 */         BlockIgniteEvent event = new BlockIgniteEvent(this.cworld.getBlockAt(i, j, k), BlockIgniteEvent.IgniteCause.LIGHTNING, null);
/*  48 */         world.getServer().getPluginManager().callEvent(event);
/*     */         
/*  50 */         if (!event.isCancelled()) {
/*  51 */           world.setTypeId(i, j, k, Block.FIRE.id);
/*     */         }
/*     */       } 
/*     */ 
/*     */       
/*  56 */       for (i = 0; i < 4; i++) {
/*  57 */         j = MathHelper.floor(d0) + this.random.nextInt(3) - 1;
/*  58 */         k = MathHelper.floor(d1) + this.random.nextInt(3) - 1;
/*  59 */         int l = MathHelper.floor(d2) + this.random.nextInt(3) - 1;
/*     */         
/*  61 */         if (world.getTypeId(j, k, l) == 0 && Block.FIRE.canPlace(world, j, k, l)) {
/*     */           
/*  63 */           BlockIgniteEvent event = new BlockIgniteEvent(this.cworld.getBlockAt(j, k, l), BlockIgniteEvent.IgniteCause.LIGHTNING, null);
/*  64 */           world.getServer().getPluginManager().callEvent(event);
/*     */           
/*  66 */           if (!event.isCancelled()) {
/*  67 */             world.setTypeId(j, k, l, Block.FIRE.id);
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void m_() {
/*  76 */     super.m_();
/*  77 */     if (this.lifeTicks == 2) {
/*  78 */       this.world.makeSound(this.locX, this.locY, this.locZ, "ambient.weather.thunder", 10000.0F, 0.8F + this.random.nextFloat() * 0.2F);
/*  79 */       this.world.makeSound(this.locX, this.locY, this.locZ, "random.explode", 2.0F, 0.5F + this.random.nextFloat() * 0.2F);
/*     */     } 
/*     */     
/*  82 */     this.lifeTicks--;
/*  83 */     if (this.lifeTicks < 0) {
/*  84 */       if (this.c == 0) {
/*  85 */         die();
/*  86 */       } else if (this.lifeTicks < -this.random.nextInt(10)) {
/*  87 */         this.c--;
/*  88 */         this.lifeTicks = 1;
/*  89 */         this.a = this.random.nextLong();
/*     */         
/*  91 */         if (!this.isEffect && this.world.areChunksLoaded(MathHelper.floor(this.locX), MathHelper.floor(this.locY), MathHelper.floor(this.locZ), 10)) {
/*  92 */           int i = MathHelper.floor(this.locX);
/*  93 */           int j = MathHelper.floor(this.locY);
/*  94 */           int k = MathHelper.floor(this.locZ);
/*     */           
/*  96 */           if (this.world.getTypeId(i, j, k) == 0 && Block.FIRE.canPlace(this.world, i, j, k)) {
/*     */             
/*  98 */             BlockIgniteEvent event = new BlockIgniteEvent(this.cworld.getBlockAt(i, j, k), BlockIgniteEvent.IgniteCause.LIGHTNING, null);
/*  99 */             this.world.getServer().getPluginManager().callEvent(event);
/*     */             
/* 101 */             if (!event.isCancelled()) {
/* 102 */               this.world.setTypeId(i, j, k, Block.FIRE.id);
/*     */             }
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/* 110 */     if (this.lifeTicks >= 0 && !this.isEffect) {
/* 111 */       double d0 = 3.0D;
/* 112 */       List list = this.world.b(this, AxisAlignedBB.b(this.locX - d0, this.locY - d0, this.locZ - d0, this.locX + d0, this.locY + 6.0D + d0, this.locZ + d0));
/*     */       
/* 114 */       for (int l = 0; l < list.size(); l++) {
/* 115 */         Entity entity = (Entity)list.get(l);
/*     */         
/* 117 */         entity.a(this);
/*     */       } 
/*     */       
/* 120 */       this.world.n = 2;
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void b() {}
/*     */   
/*     */   protected void a(NBTTagCompound nbttagcompound) {}
/*     */   
/*     */   protected void b(NBTTagCompound nbttagcompound) {}
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\EntityWeatherStorm.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */