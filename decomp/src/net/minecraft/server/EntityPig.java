/*    */ package net.minecraft.server;
/*    */ 
/*    */ import org.bukkit.event.entity.CreatureSpawnEvent;
/*    */ import org.bukkit.event.entity.PigZapEvent;
/*    */ 
/*    */ 
/*    */ public class EntityPig
/*    */   extends EntityAnimal
/*    */ {
/*    */   public EntityPig(World world) {
/* 11 */     super(world);
/* 12 */     this.texture = "/mob/pig.png";
/* 13 */     b(0.9F, 0.9F);
/*    */   }
/*    */ 
/*    */   
/* 17 */   protected void b() { this.datawatcher.a(16, Byte.valueOf((byte)0)); }
/*    */ 
/*    */   
/*    */   public void b(NBTTagCompound nbttagcompound) {
/* 21 */     super.b(nbttagcompound);
/* 22 */     nbttagcompound.a("Saddle", hasSaddle());
/*    */   }
/*    */   
/*    */   public void a(NBTTagCompound nbttagcompound) {
/* 26 */     super.a(nbttagcompound);
/* 27 */     setSaddle(nbttagcompound.m("Saddle"));
/*    */   }
/*    */ 
/*    */   
/* 31 */   protected String g() { return "mob.pig"; }
/*    */ 
/*    */ 
/*    */   
/* 35 */   protected String h() { return "mob.pig"; }
/*    */ 
/*    */ 
/*    */   
/* 39 */   protected String i() { return "mob.pigdeath"; }
/*    */ 
/*    */   
/*    */   public boolean a(EntityHuman entityhuman) {
/* 43 */     if (hasSaddle() && !this.world.isStatic && (this.passenger == null || this.passenger == entityhuman)) {
/* 44 */       entityhuman.mount(this);
/* 45 */       return true;
/*    */     } 
/* 47 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/* 52 */   protected int j() { return (this.fireTicks > 0) ? Item.GRILLED_PORK.id : Item.PORK.id; }
/*    */ 
/*    */ 
/*    */   
/* 56 */   public boolean hasSaddle() { return ((this.datawatcher.a(16) & true) != 0); }
/*    */ 
/*    */   
/*    */   public void setSaddle(boolean flag) {
/* 60 */     if (flag) {
/* 61 */       this.datawatcher.watch(16, Byte.valueOf((byte)1));
/*    */     } else {
/* 63 */       this.datawatcher.watch(16, Byte.valueOf((byte)0));
/*    */     } 
/*    */   }
/*    */   
/*    */   public void a(EntityWeatherStorm entityweatherstorm) {
/* 68 */     if (!this.world.isStatic) {
/* 69 */       EntityPigZombie entitypigzombie = new EntityPigZombie(this.world);
/*    */ 
/*    */       
/* 72 */       PigZapEvent event = new PigZapEvent(getBukkitEntity(), entityweatherstorm.getBukkitEntity(), entitypigzombie.getBukkitEntity());
/* 73 */       this.world.getServer().getPluginManager().callEvent(event);
/*    */       
/* 75 */       if (event.isCancelled()) {
/*    */         return;
/*    */       }
/*    */ 
/*    */       
/* 80 */       entitypigzombie.setPositionRotation(this.locX, this.locY, this.locZ, this.yaw, this.pitch);
/*    */       
/* 82 */       this.world.addEntity(entitypigzombie, CreatureSpawnEvent.SpawnReason.LIGHTNING);
/* 83 */       die();
/*    */     } 
/*    */   }
/*    */   
/*    */   protected void a(float f) {
/* 88 */     super.a(f);
/* 89 */     if (f > 5.0F && this.passenger instanceof EntityHuman)
/* 90 */       ((EntityHuman)this.passenger).a(AchievementList.u); 
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\EntityPig.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */