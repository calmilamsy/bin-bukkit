/*    */ package org.bukkit.craftbukkit.entity;
/*    */ 
/*    */ import net.minecraft.server.Entity;
/*    */ import org.bukkit.craftbukkit.CraftServer;
/*    */ import org.bukkit.entity.Projectile;
/*    */ 
/*    */ public abstract class AbstractProjectile extends CraftEntity implements Projectile {
/*    */   private boolean doesBounce;
/*    */   
/*    */   public AbstractProjectile(CraftServer server, Entity entity) {
/* 11 */     super(server, entity);
/* 12 */     this.doesBounce = false;
/*    */   }
/*    */ 
/*    */   
/* 16 */   public boolean doesBounce() { return this.doesBounce; }
/*    */ 
/*    */ 
/*    */   
/* 20 */   public void setBounce(boolean doesBounce) { this.doesBounce = doesBounce; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\craftbukkit\entity\AbstractProjectile.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */