/*    */ package net.minecraft.server;
/*    */ 
/*    */ import java.util.Random;
/*    */ import org.bukkit.block.Block;
/*    */ import org.bukkit.entity.Entity;
/*    */ import org.bukkit.event.entity.EntityDamageByBlockEvent;
/*    */ import org.bukkit.event.entity.EntityDamageEvent;
/*    */ 
/*    */ public class BlockCactus
/*    */   extends Block
/*    */ {
/*    */   protected BlockCactus(int i, int j) {
/* 13 */     super(i, j, Material.CACTUS);
/* 14 */     a(true);
/*    */   }
/*    */   
/*    */   public void a(World world, int i, int j, int k, Random random) {
/* 18 */     if (world.isEmpty(i, j + 1, k)) {
/*    */       int l;
/*    */       
/* 21 */       for (l = 1; world.getTypeId(i, j - l, k) == this.id; l++);
/*    */ 
/*    */ 
/*    */       
/* 25 */       if (l < 3) {
/* 26 */         int i1 = world.getData(i, j, k);
/*    */         
/* 28 */         if (i1 == 15) {
/* 29 */           world.setTypeId(i, j + 1, k, this.id);
/* 30 */           world.setData(i, j, k, 0);
/*    */         } else {
/* 32 */           world.setData(i, j, k, i1 + 1);
/*    */         } 
/*    */       } 
/*    */     } 
/*    */   }
/*    */   
/*    */   public AxisAlignedBB e(World world, int i, int j, int k) {
/* 39 */     float f = 0.0625F;
/*    */     
/* 41 */     return AxisAlignedBB.b((i + f), j, (k + f), ((i + 1) - f), ((j + 1) - f), ((k + 1) - f));
/*    */   }
/*    */ 
/*    */   
/* 45 */   public int a(int i) { return (i == 1) ? (this.textureId - 1) : ((i == 0) ? (this.textureId + 1) : this.textureId); }
/*    */ 
/*    */ 
/*    */   
/* 49 */   public boolean b() { return false; }
/*    */ 
/*    */ 
/*    */   
/* 53 */   public boolean a() { return false; }
/*    */ 
/*    */ 
/*    */   
/* 57 */   public boolean canPlace(World world, int i, int j, int k) { return !super.canPlace(world, i, j, k) ? false : f(world, i, j, k); }
/*    */ 
/*    */   
/*    */   public void doPhysics(World world, int i, int j, int k, int l) {
/* 61 */     if (!f(world, i, j, k)) {
/* 62 */       g(world, i, j, k, world.getData(i, j, k));
/* 63 */       world.setTypeId(i, j, k, 0);
/*    */     } 
/*    */   }
/*    */   
/*    */   public boolean f(World world, int i, int j, int k) {
/* 68 */     if (world.getMaterial(i - 1, j, k).isBuildable())
/* 69 */       return false; 
/* 70 */     if (world.getMaterial(i + 1, j, k).isBuildable())
/* 71 */       return false; 
/* 72 */     if (world.getMaterial(i, j, k - 1).isBuildable())
/* 73 */       return false; 
/* 74 */     if (world.getMaterial(i, j, k + 1).isBuildable()) {
/* 75 */       return false;
/*    */     }
/* 77 */     int l = world.getTypeId(i, j - 1, k);
/*    */     
/* 79 */     return (l == Block.CACTUS.id || l == Block.SAND.id);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void a(World world, int i, int j, int k, Entity entity) {
/* 85 */     if (entity instanceof EntityLiving) {
/* 86 */       Block damager = world.getWorld().getBlockAt(i, j, k);
/* 87 */       Entity damagee = (entity == null) ? null : entity.getBukkitEntity();
/*    */       
/* 89 */       EntityDamageByBlockEvent event = new EntityDamageByBlockEvent(damager, damagee, EntityDamageEvent.DamageCause.CONTACT, true);
/* 90 */       world.getServer().getPluginManager().callEvent(event);
/*    */       
/* 92 */       if (!event.isCancelled()) {
/* 93 */         entity.damageEntity((Entity)null, event.getDamage());
/*    */       }
/*    */       
/*    */       return;
/*    */     } 
/*    */     
/* 99 */     entity.damageEntity((Entity)null, 1);
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\BlockCactus.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */