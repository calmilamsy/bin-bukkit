/*     */ package net.minecraft.server;
/*     */ 
/*     */ import java.util.List;
/*     */ import org.bukkit.craftbukkit.entity.CraftEntity;
/*     */ import org.bukkit.entity.Entity;
/*     */ import org.bukkit.event.entity.EntityTargetEvent;
/*     */ 
/*     */ 
/*     */ public class EntityPigZombie
/*     */   extends EntityZombie
/*     */ {
/*  12 */   public int angerLevel = 0;
/*  13 */   private int soundDelay = 0;
/*  14 */   private static final ItemStack f = new ItemStack(Item.GOLD_SWORD, true);
/*     */   
/*     */   public EntityPigZombie(World world) {
/*  17 */     super(world);
/*  18 */     this.texture = "/mob/pigzombie.png";
/*  19 */     this.aE = 0.5F;
/*  20 */     this.damage = 5;
/*  21 */     this.fireProof = true;
/*     */   }
/*     */   
/*     */   public void m_() {
/*  25 */     this.aE = (this.target != null) ? 0.95F : 0.5F;
/*  26 */     if (this.soundDelay > 0 && --this.soundDelay == 0) {
/*  27 */       this.world.makeSound(this, "mob.zombiepig.zpigangry", k() * 2.0F, ((this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F) * 1.8F);
/*     */     }
/*     */     
/*  30 */     super.m_();
/*     */   }
/*     */ 
/*     */   
/*  34 */   public boolean d() { return (this.world.spawnMonsters > 0 && this.world.containsEntity(this.boundingBox) && this.world.getEntities(this, this.boundingBox).size() == 0 && !this.world.c(this.boundingBox)); }
/*     */ 
/*     */   
/*     */   public void b(NBTTagCompound nbttagcompound) {
/*  38 */     super.b(nbttagcompound);
/*  39 */     nbttagcompound.a("Anger", (short)this.angerLevel);
/*     */   }
/*     */   
/*     */   public void a(NBTTagCompound nbttagcompound) {
/*  43 */     super.a(nbttagcompound);
/*  44 */     this.angerLevel = nbttagcompound.d("Anger");
/*     */   }
/*     */ 
/*     */   
/*  48 */   protected Entity findTarget() { return (this.angerLevel == 0) ? null : super.findTarget(); }
/*     */ 
/*     */ 
/*     */   
/*  52 */   public void v() { super.v(); }
/*     */ 
/*     */   
/*     */   public boolean damageEntity(Entity entity, int i) {
/*  56 */     if (entity instanceof EntityHuman) {
/*  57 */       List list = this.world.b(this, this.boundingBox.b(32.0D, 32.0D, 32.0D));
/*     */       
/*  59 */       for (int j = 0; j < list.size(); j++) {
/*  60 */         Entity entity1 = (Entity)list.get(j);
/*     */         
/*  62 */         if (entity1 instanceof EntityPigZombie) {
/*  63 */           EntityPigZombie entitypigzombie = (EntityPigZombie)entity1;
/*     */           
/*  65 */           entitypigzombie.d(entity);
/*     */         } 
/*     */       } 
/*     */       
/*  69 */       d(entity);
/*     */     } 
/*     */     
/*  72 */     return super.damageEntity(entity, i);
/*     */   }
/*     */ 
/*     */   
/*     */   private void d(Entity entity) {
/*  77 */     Entity bukkitTarget = (entity == null) ? null : entity.getBukkitEntity();
/*     */     
/*  79 */     EntityTargetEvent event = new EntityTargetEvent(getBukkitEntity(), bukkitTarget, EntityTargetEvent.TargetReason.PIG_ZOMBIE_TARGET);
/*  80 */     this.world.getServer().getPluginManager().callEvent(event);
/*     */     
/*  82 */     if (event.isCancelled()) {
/*     */       return;
/*     */     }
/*     */     
/*  86 */     if (event.getTarget() == null) {
/*  87 */       this.target = null;
/*     */       return;
/*     */     } 
/*  90 */     entity = ((CraftEntity)event.getTarget()).getHandle();
/*     */ 
/*     */     
/*  93 */     this.target = entity;
/*  94 */     this.angerLevel = 400 + this.random.nextInt(400);
/*  95 */     this.soundDelay = this.random.nextInt(40);
/*     */   }
/*     */ 
/*     */   
/*  99 */   protected String g() { return "mob.zombiepig.zpig"; }
/*     */ 
/*     */ 
/*     */   
/* 103 */   protected String h() { return "mob.zombiepig.zpighurt"; }
/*     */ 
/*     */ 
/*     */   
/* 107 */   protected String i() { return "mob.zombiepig.zpigdeath"; }
/*     */ 
/*     */ 
/*     */   
/* 111 */   protected int j() { return Item.GRILLED_PORK.id; }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\EntityPigZombie.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */