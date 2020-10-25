/*     */ package net.minecraft.server;
/*     */ import org.bukkit.event.entity.EntityDeathEvent;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ 
/*     */ public class EntitySheep extends EntityAnimal {
/*     */   public static final float[][] a = { 
/*   7 */       { 1.0F, 1.0F, 1.0F }, { 0.95F, 0.7F, 0.2F }, { 0.9F, 0.5F, 0.85F }, { 0.6F, 0.7F, 0.95F }, { 0.9F, 0.9F, 0.2F }, { 0.5F, 0.8F, 0.1F }, { 0.95F, 0.7F, 0.8F }, { 0.3F, 0.3F, 0.3F }, { 0.6F, 0.6F, 0.6F }, { 0.3F, 0.6F, 0.7F }, { 0.7F, 0.4F, 0.9F }, { 0.2F, 0.4F, 0.8F }, { 0.5F, 0.4F, 0.3F }, { 0.4F, 0.5F, 0.2F }, { 0.8F, 0.3F, 0.3F }, { 0.1F, 0.1F, 0.1F } };
/*     */   
/*     */   public EntitySheep(World world) {
/*  10 */     super(world);
/*  11 */     this.texture = "/mob/sheep.png";
/*  12 */     b(0.9F, 1.3F);
/*     */   }
/*     */   
/*     */   protected void b() {
/*  16 */     super.b();
/*  17 */     this.datawatcher.a(16, new Byte(false));
/*     */   }
/*     */ 
/*     */   
/*  21 */   public boolean damageEntity(Entity entity, int i) { return super.damageEntity(entity, i); }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void q() {
/*  26 */     List<ItemStack> loot = new ArrayList<ItemStack>();
/*     */     
/*  28 */     if (!isSheared()) {
/*  29 */       loot.add(new ItemStack(Material.WOOL, true, false, Byte.valueOf((byte)getColor())));
/*     */     }
/*     */     
/*  32 */     CraftWorld craftWorld = this.world.getWorld();
/*  33 */     Entity entity = getBukkitEntity();
/*     */     
/*  35 */     EntityDeathEvent event = new EntityDeathEvent(entity, loot);
/*  36 */     this.world.getServer().getPluginManager().callEvent(event);
/*     */     
/*  38 */     for (ItemStack stack : event.getDrops()) {
/*  39 */       craftWorld.dropItemNaturally(entity.getLocation(), stack);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*  45 */   protected int j() { return Block.WOOL.id; }
/*     */ 
/*     */   
/*     */   public boolean a(EntityHuman entityhuman) {
/*  49 */     ItemStack itemstack = entityhuman.inventory.getItemInHand();
/*     */     
/*  51 */     if (itemstack != null && itemstack.id == Item.SHEARS.id && !isSheared()) {
/*  52 */       if (!this.world.isStatic) {
/*  53 */         setSheared(true);
/*  54 */         int i = 2 + this.random.nextInt(3);
/*     */         
/*  56 */         for (int j = 0; j < i; j++) {
/*  57 */           EntityItem entityitem = a(new ItemStack(Block.WOOL.id, true, getColor()), 1.0F);
/*     */           
/*  59 */           entityitem.motY += (this.random.nextFloat() * 0.05F);
/*  60 */           entityitem.motX += ((this.random.nextFloat() - this.random.nextFloat()) * 0.1F);
/*  61 */           entityitem.motZ += ((this.random.nextFloat() - this.random.nextFloat()) * 0.1F);
/*     */         } 
/*     */       } 
/*     */       
/*  65 */       itemstack.damage(1, entityhuman);
/*     */     } 
/*     */     
/*  68 */     return false;
/*     */   }
/*     */   
/*     */   public void b(NBTTagCompound nbttagcompound) {
/*  72 */     super.b(nbttagcompound);
/*  73 */     nbttagcompound.a("Sheared", isSheared());
/*  74 */     nbttagcompound.a("Color", (byte)getColor());
/*     */   }
/*     */   
/*     */   public void a(NBTTagCompound nbttagcompound) {
/*  78 */     super.a(nbttagcompound);
/*  79 */     setSheared(nbttagcompound.m("Sheared"));
/*  80 */     setColor(nbttagcompound.c("Color"));
/*     */   }
/*     */ 
/*     */   
/*  84 */   protected String g() { return "mob.sheep"; }
/*     */ 
/*     */ 
/*     */   
/*  88 */   protected String h() { return "mob.sheep"; }
/*     */ 
/*     */ 
/*     */   
/*  92 */   protected String i() { return "mob.sheep"; }
/*     */ 
/*     */ 
/*     */   
/*  96 */   public int getColor() { return this.datawatcher.a(16) & 0xF; }
/*     */ 
/*     */   
/*     */   public void setColor(int i) {
/* 100 */     byte b0 = this.datawatcher.a(16);
/*     */     
/* 102 */     this.datawatcher.watch(16, Byte.valueOf((byte)(b0 & 0xF0 | i & 0xF)));
/*     */   }
/*     */ 
/*     */   
/* 106 */   public boolean isSheared() { return ((this.datawatcher.a(16) & 0x10) != 0); }
/*     */ 
/*     */   
/*     */   public void setSheared(boolean flag) {
/* 110 */     byte b0 = this.datawatcher.a(16);
/*     */     
/* 112 */     if (flag) {
/* 113 */       this.datawatcher.watch(16, Byte.valueOf((byte)(b0 | 0x10)));
/*     */     } else {
/* 115 */       this.datawatcher.watch(16, Byte.valueOf((byte)(b0 & 0xFFFFFFEF)));
/*     */     } 
/*     */   }
/*     */   
/*     */   public static int a(Random random) {
/* 120 */     int i = random.nextInt(100);
/*     */     
/* 122 */     return (i < 5) ? 15 : ((i < 10) ? 7 : ((i < 15) ? 8 : ((i < 18) ? 12 : ((random.nextInt(500) == 0) ? 6 : 0))));
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\EntitySheep.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */