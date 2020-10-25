/*    */ package org.bukkit.craftbukkit.block;
/*    */ 
/*    */ import net.minecraft.server.TileEntityMobSpawner;
/*    */ import org.bukkit.block.Block;
/*    */ import org.bukkit.block.CreatureSpawner;
/*    */ import org.bukkit.craftbukkit.CraftWorld;
/*    */ import org.bukkit.entity.CreatureType;
/*    */ 
/*    */ public class CraftCreatureSpawner extends CraftBlockState implements CreatureSpawner {
/*    */   private final CraftWorld world;
/*    */   private final TileEntityMobSpawner spawner;
/*    */   
/*    */   public CraftCreatureSpawner(Block block) {
/* 14 */     super(block);
/*    */     
/* 16 */     this.world = (CraftWorld)block.getWorld();
/* 17 */     this.spawner = (TileEntityMobSpawner)this.world.getTileEntityAt(getX(), getY(), getZ());
/*    */   }
/*    */ 
/*    */   
/* 21 */   public CreatureType getCreatureType() { return CreatureType.fromName(this.spawner.mobName); }
/*    */ 
/*    */ 
/*    */   
/* 25 */   public void setCreatureType(CreatureType creatureType) { this.spawner.mobName = creatureType.getName(); }
/*    */ 
/*    */ 
/*    */   
/* 29 */   public String getCreatureTypeId() { return this.spawner.mobName; }
/*    */ 
/*    */ 
/*    */   
/*    */   public void setCreatureTypeId(String creatureType) {
/* 34 */     CreatureType type = CreatureType.fromName(creatureType);
/* 35 */     if (type == null) {
/*    */       return;
/*    */     }
/* 38 */     this.spawner.mobName = type.getName();
/*    */   }
/*    */ 
/*    */   
/* 42 */   public int getDelay() { return this.spawner.spawnDelay; }
/*    */ 
/*    */ 
/*    */   
/* 46 */   public void setDelay(int delay) { this.spawner.spawnDelay = delay; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\craftbukkit\block\CraftCreatureSpawner.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */