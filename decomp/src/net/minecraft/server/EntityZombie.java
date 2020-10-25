/*    */ package net.minecraft.server;
/*    */ 
/*    */ import org.bukkit.event.entity.EntityCombustEvent;
/*    */ 
/*    */ public class EntityZombie
/*    */   extends EntityMonster {
/*    */   public EntityZombie(World world) {
/*  8 */     super(world);
/*  9 */     this.texture = "/mob/zombie.png";
/* 10 */     this.aE = 0.5F;
/* 11 */     this.damage = 5;
/*    */   }
/*    */   
/*    */   public void v() {
/* 15 */     if (this.world.d()) {
/* 16 */       float f = c(1.0F);
/*    */       
/* 18 */       if (f > 0.5F && this.world.isChunkLoaded(MathHelper.floor(this.locX), MathHelper.floor(this.locY), MathHelper.floor(this.locZ)) && this.random.nextFloat() * 30.0F < (f - 0.4F) * 2.0F) {
/*    */         
/* 20 */         EntityCombustEvent event = new EntityCombustEvent(getBukkitEntity());
/* 21 */         this.world.getServer().getPluginManager().callEvent(event);
/*    */         
/* 23 */         if (!event.isCancelled()) {
/* 24 */           this.fireTicks = 300;
/*    */         }
/*    */       } 
/*    */     } 
/*    */ 
/*    */     
/* 30 */     super.v();
/*    */   }
/*    */ 
/*    */   
/* 34 */   protected String g() { return "mob.zombie"; }
/*    */ 
/*    */ 
/*    */   
/* 38 */   protected String h() { return "mob.zombiehurt"; }
/*    */ 
/*    */ 
/*    */   
/* 42 */   protected String i() { return "mob.zombiedeath"; }
/*    */ 
/*    */ 
/*    */   
/* 46 */   protected int j() { return Item.FEATHER.id; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\EntityZombie.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */