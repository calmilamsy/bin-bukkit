/*    */ package org.bukkit.craftbukkit.entity;
/*    */ 
/*    */ import net.minecraft.server.EntityMinecart;
/*    */ import org.bukkit.craftbukkit.CraftServer;
/*    */ import org.bukkit.entity.Minecart;
/*    */ import org.bukkit.util.Vector;
/*    */ 
/*    */ public class CraftMinecart
/*    */   extends CraftVehicle
/*    */   implements Minecart {
/*    */   protected EntityMinecart minecart;
/*    */   
/*    */   public enum Type {
/* 14 */     Minecart(false),
/* 15 */     StorageMinecart(true),
/* 16 */     PoweredMinecart(2);
/*    */     
/*    */     private final int id;
/*    */ 
/*    */     
/* 21 */     Type(int id) { this.id = id; }
/*    */ 
/*    */ 
/*    */     
/* 25 */     public int getId() { return this.id; }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public CraftMinecart(CraftServer server, EntityMinecart entity) {
/* 32 */     super(server, entity);
/* 33 */     this.minecart = entity;
/*    */   }
/*    */ 
/*    */   
/* 37 */   public void setDamage(int damage) { this.minecart.damage = damage; }
/*    */ 
/*    */ 
/*    */   
/* 41 */   public int getDamage() { return this.minecart.damage; }
/*    */ 
/*    */ 
/*    */   
/* 45 */   public double getMaxSpeed() { return this.minecart.maxSpeed; }
/*    */ 
/*    */   
/*    */   public void setMaxSpeed(double speed) {
/* 49 */     if (speed >= 0.0D) {
/* 50 */       this.minecart.maxSpeed = speed;
/*    */     }
/*    */   }
/*    */ 
/*    */   
/* 55 */   public boolean isSlowWhenEmpty() { return this.minecart.slowWhenEmpty; }
/*    */ 
/*    */ 
/*    */   
/* 59 */   public void setSlowWhenEmpty(boolean slow) { this.minecart.slowWhenEmpty = slow; }
/*    */ 
/*    */ 
/*    */   
/* 63 */   public Vector getFlyingVelocityMod() { return new Vector(this.minecart.flyingX, this.minecart.flyingY, this.minecart.flyingZ); }
/*    */ 
/*    */   
/*    */   public void setFlyingVelocityMod(Vector flying) {
/* 67 */     this.minecart.flyingX = flying.getX();
/* 68 */     this.minecart.flyingY = flying.getY();
/* 69 */     this.minecart.flyingZ = flying.getZ();
/*    */   }
/*    */ 
/*    */   
/* 73 */   public Vector getDerailedVelocityMod() { return new Vector(this.minecart.derailedX, this.minecart.derailedY, this.minecart.derailedZ); }
/*    */ 
/*    */   
/*    */   public void setDerailedVelocityMod(Vector derailed) {
/* 77 */     this.minecart.derailedX = derailed.getX();
/* 78 */     this.minecart.derailedY = derailed.getY();
/* 79 */     this.minecart.derailedZ = derailed.getZ();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/* 84 */   public String toString() { return "CraftMinecart"; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\craftbukkit\entity\CraftMinecart.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */