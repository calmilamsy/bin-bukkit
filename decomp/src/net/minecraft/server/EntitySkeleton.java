/*     */ package net.minecraft.server;
/*     */ import java.util.List;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.entity.Entity;
/*     */ import org.bukkit.event.entity.EntityCombustEvent;
/*     */ import org.bukkit.event.entity.EntityDeathEvent;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ 
/*     */ public class EntitySkeleton extends EntityMonster {
/*  10 */   private static final ItemStack a = new ItemStack(Item.BOW, true);
/*     */   
/*     */   public EntitySkeleton(World world) {
/*  13 */     super(world);
/*  14 */     this.texture = "/mob/skeleton.png";
/*     */   }
/*     */ 
/*     */   
/*  18 */   protected String g() { return "mob.skeleton"; }
/*     */ 
/*     */ 
/*     */   
/*  22 */   protected String h() { return "mob.skeletonhurt"; }
/*     */ 
/*     */ 
/*     */   
/*  26 */   protected String i() { return "mob.skeletonhurt"; }
/*     */ 
/*     */   
/*     */   public void v() {
/*  30 */     if (this.world.d()) {
/*  31 */       float f = c(1.0F);
/*     */       
/*  33 */       if (f > 0.5F && this.world.isChunkLoaded(MathHelper.floor(this.locX), MathHelper.floor(this.locY), MathHelper.floor(this.locZ)) && this.random.nextFloat() * 30.0F < (f - 0.4F) * 2.0F) {
/*     */         
/*  35 */         EntityCombustEvent event = new EntityCombustEvent(getBukkitEntity());
/*  36 */         this.world.getServer().getPluginManager().callEvent(event);
/*     */         
/*  38 */         if (!event.isCancelled()) {
/*  39 */           this.fireTicks = 300;
/*     */         }
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/*  45 */     super.v();
/*     */   }
/*     */   
/*     */   protected void a(Entity entity, float f) {
/*  49 */     if (f < 10.0F) {
/*  50 */       double d0 = entity.locX - this.locX;
/*  51 */       double d1 = entity.locZ - this.locZ;
/*     */       
/*  53 */       if (this.attackTicks == 0) {
/*  54 */         EntityArrow entityarrow = new EntityArrow(this.world, this);
/*     */         
/*  56 */         entityarrow.locY++;
/*  57 */         double d2 = entity.locY + entity.t() - 0.20000000298023224D - entityarrow.locY;
/*  58 */         float f1 = MathHelper.a(d0 * d0 + d1 * d1) * 0.2F;
/*     */         
/*  60 */         this.world.makeSound(this, "random.bow", 1.0F, 1.0F / (this.random.nextFloat() * 0.4F + 0.8F));
/*  61 */         this.world.addEntity(entityarrow);
/*  62 */         entityarrow.a(d0, d2 + f1, d1, 0.6F, 12.0F);
/*  63 */         this.attackTicks = 30;
/*     */       } 
/*     */       
/*  66 */       this.yaw = (float)(Math.atan2(d1, d0) * 180.0D / 3.1415927410125732D) - 90.0F;
/*  67 */       this.e = true;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*  72 */   public void b(NBTTagCompound nbttagcompound) { super.b(nbttagcompound); }
/*     */ 
/*     */ 
/*     */   
/*  76 */   public void a(NBTTagCompound nbttagcompound) { super.a(nbttagcompound); }
/*     */ 
/*     */ 
/*     */   
/*  80 */   protected int j() { return Item.ARROW.id; }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void q() {
/*  85 */     List<ItemStack> loot = new ArrayList<ItemStack>();
/*     */     
/*  87 */     int count = this.random.nextInt(3);
/*  88 */     if (count > 0) {
/*  89 */       loot.add(new ItemStack(Material.ARROW, count));
/*     */     }
/*     */     
/*  92 */     count = this.random.nextInt(3);
/*  93 */     if (count > 0) {
/*  94 */       loot.add(new ItemStack(Material.BONE, count));
/*     */     }
/*     */     
/*  97 */     CraftWorld craftWorld = this.world.getWorld();
/*  98 */     Entity entity = getBukkitEntity();
/*     */     
/* 100 */     EntityDeathEvent event = new EntityDeathEvent(entity, loot);
/* 101 */     this.world.getServer().getPluginManager().callEvent(event);
/*     */     
/* 103 */     for (ItemStack stack : event.getDrops())
/* 104 */       craftWorld.dropItemNaturally(entity.getLocation(), stack); 
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\EntitySkeleton.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */