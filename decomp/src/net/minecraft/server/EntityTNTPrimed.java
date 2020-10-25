/*    */ package net.minecraft.server;
/*    */ 
/*    */ import org.bukkit.craftbukkit.CraftServer;
/*    */ import org.bukkit.craftbukkit.entity.CraftEntity;
/*    */ import org.bukkit.entity.Explosive;
/*    */ import org.bukkit.event.entity.ExplosionPrimeEvent;
/*    */ 
/*    */ 
/*    */ public class EntityTNTPrimed
/*    */   extends Entity
/*    */ {
/*    */   public int fuseTicks;
/* 13 */   public float yield = 4.0F;
/*    */   public boolean isIncendiary = false;
/*    */   
/*    */   public EntityTNTPrimed(World world) {
/* 17 */     super(world);
/* 18 */     this.fuseTicks = 0;
/* 19 */     this.aI = true;
/* 20 */     b(0.98F, 0.98F);
/* 21 */     this.height = this.width / 2.0F;
/*    */   }
/*    */   
/*    */   public EntityTNTPrimed(World world, double d0, double d1, double d2) {
/* 25 */     this(world);
/* 26 */     setPosition(d0, d1, d2);
/* 27 */     float f = (float)(Math.random() * 3.1415927410125732D * 2.0D);
/*    */     
/* 29 */     this.motX = (-MathHelper.sin(f * 3.1415927F / 180.0F) * 0.02F);
/* 30 */     this.motY = 0.20000000298023224D;
/* 31 */     this.motZ = (-MathHelper.cos(f * 3.1415927F / 180.0F) * 0.02F);
/* 32 */     this.fuseTicks = 80;
/* 33 */     this.lastX = d0;
/* 34 */     this.lastY = d1;
/* 35 */     this.lastZ = d2;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void b() {}
/*    */   
/* 41 */   protected boolean n() { return false; }
/*    */ 
/*    */ 
/*    */   
/* 45 */   public boolean l_() { return !this.dead; }
/*    */ 
/*    */   
/*    */   public void m_() {
/* 49 */     this.lastX = this.locX;
/* 50 */     this.lastY = this.locY;
/* 51 */     this.lastZ = this.locZ;
/* 52 */     this.motY -= 0.03999999910593033D;
/* 53 */     move(this.motX, this.motY, this.motZ);
/* 54 */     this.motX *= 0.9800000190734863D;
/* 55 */     this.motY *= 0.9800000190734863D;
/* 56 */     this.motZ *= 0.9800000190734863D;
/* 57 */     if (this.onGround) {
/* 58 */       this.motX *= 0.699999988079071D;
/* 59 */       this.motZ *= 0.699999988079071D;
/* 60 */       this.motY *= -0.5D;
/*    */     } 
/*    */     
/* 63 */     if (this.fuseTicks-- <= 0) {
/* 64 */       if (!this.world.isStatic) {
/*    */         
/* 66 */         explode();
/* 67 */         die();
/*    */       } else {
/*    */         
/* 70 */         die();
/*    */       } 
/*    */     } else {
/* 73 */       this.world.a("smoke", this.locX, this.locY + 0.5D, this.locZ, 0.0D, 0.0D, 0.0D);
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private void explode() {
/* 81 */     CraftServer server = this.world.getServer();
/*    */     
/* 83 */     ExplosionPrimeEvent event = new ExplosionPrimeEvent((Explosive)CraftEntity.getEntity(server, this));
/* 84 */     server.getPluginManager().callEvent(event);
/*    */     
/* 86 */     if (!event.isCancelled())
/*    */     {
/* 88 */       this.world.createExplosion(this, this.locX, this.locY, this.locZ, event.getRadius(), event.getFire());
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */   
/* 94 */   protected void b(NBTTagCompound nbttagcompound) { nbttagcompound.a("Fuse", (byte)this.fuseTicks); }
/*    */ 
/*    */ 
/*    */   
/* 98 */   protected void a(NBTTagCompound nbttagcompound) { this.fuseTicks = nbttagcompound.c("Fuse"); }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\EntityTNTPrimed.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */