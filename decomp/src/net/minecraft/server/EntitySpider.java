/*    */ package net.minecraft.server;
/*    */ 
/*    */ import org.bukkit.craftbukkit.entity.CraftEntity;
/*    */ import org.bukkit.event.entity.EntityTargetEvent;
/*    */ 
/*    */ 
/*    */ public class EntitySpider
/*    */   extends EntityMonster
/*    */ {
/*    */   public EntitySpider(World world) {
/* 11 */     super(world);
/* 12 */     this.texture = "/mob/spider.png";
/* 13 */     b(1.4F, 0.9F);
/* 14 */     this.aE = 0.8F;
/*    */   }
/*    */ 
/*    */   
/* 18 */   public double m() { return this.width * 0.75D - 0.5D; }
/*    */ 
/*    */ 
/*    */   
/* 22 */   protected boolean n() { return false; }
/*    */ 
/*    */   
/*    */   protected Entity findTarget() {
/* 26 */     float f = c(1.0F);
/*    */     
/* 28 */     if (f < 0.5F) {
/* 29 */       double d0 = 16.0D;
/*    */       
/* 31 */       return this.world.findNearbyPlayer(this, d0);
/*    */     } 
/* 33 */     return null;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/* 38 */   protected String g() { return "mob.spider"; }
/*    */ 
/*    */ 
/*    */   
/* 42 */   protected String h() { return "mob.spider"; }
/*    */ 
/*    */ 
/*    */   
/* 46 */   protected String i() { return "mob.spiderdeath"; }
/*    */ 
/*    */   
/*    */   protected void a(Entity entity, float f) {
/* 50 */     float f1 = c(1.0F);
/*    */     
/* 52 */     if (f1 > 0.5F && this.random.nextInt(100) == 0) {
/*    */       
/* 54 */       EntityTargetEvent event = new EntityTargetEvent(getBukkitEntity(), null, EntityTargetEvent.TargetReason.FORGOT_TARGET);
/* 55 */       this.world.getServer().getPluginManager().callEvent(event);
/*    */       
/* 57 */       if (!event.isCancelled()) {
/* 58 */         if (event.getTarget() == null) {
/* 59 */           this.target = null;
/*    */         } else {
/* 61 */           this.target = ((CraftEntity)event.getTarget()).getHandle();
/*    */         } 
/*    */ 
/*    */         
/*    */         return;
/*    */       } 
/* 67 */     } else if (f > 2.0F && f < 6.0F && this.random.nextInt(10) == 0) {
/* 68 */       if (this.onGround) {
/* 69 */         double d0 = entity.locX - this.locX;
/* 70 */         double d1 = entity.locZ - this.locZ;
/* 71 */         float f2 = MathHelper.a(d0 * d0 + d1 * d1);
/*    */         
/* 73 */         this.motX = d0 / f2 * 0.5D * 0.800000011920929D + this.motX * 0.20000000298023224D;
/* 74 */         this.motZ = d1 / f2 * 0.5D * 0.800000011920929D + this.motZ * 0.20000000298023224D;
/* 75 */         this.motY = 0.4000000059604645D;
/*    */       } 
/*    */     } else {
/* 78 */       super.a(entity, f);
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/* 84 */   public void b(NBTTagCompound nbttagcompound) { super.b(nbttagcompound); }
/*    */ 
/*    */ 
/*    */   
/* 88 */   public void a(NBTTagCompound nbttagcompound) { super.a(nbttagcompound); }
/*    */ 
/*    */ 
/*    */   
/* 92 */   protected int j() { return Item.STRING.id; }
/*    */ 
/*    */ 
/*    */   
/* 96 */   public boolean p() { return this.positionChanged; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\EntitySpider.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */