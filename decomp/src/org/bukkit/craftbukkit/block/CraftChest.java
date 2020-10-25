/*    */ package org.bukkit.craftbukkit.block;
/*    */ 
/*    */ import net.minecraft.server.TileEntityChest;
/*    */ import org.bukkit.block.Block;
/*    */ import org.bukkit.block.Chest;
/*    */ import org.bukkit.craftbukkit.CraftWorld;
/*    */ import org.bukkit.craftbukkit.inventory.CraftInventory;
/*    */ import org.bukkit.inventory.Inventory;
/*    */ 
/*    */ public class CraftChest extends CraftBlockState implements Chest {
/*    */   private final CraftWorld world;
/*    */   private final TileEntityChest chest;
/*    */   
/*    */   public CraftChest(Block block) {
/* 15 */     super(block);
/*    */     
/* 17 */     this.world = (CraftWorld)block.getWorld();
/* 18 */     this.chest = (TileEntityChest)this.world.getTileEntityAt(getX(), getY(), getZ());
/*    */   }
/*    */ 
/*    */   
/* 22 */   public Inventory getInventory() { return new CraftInventory(this.chest); }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean update(boolean force) {
/* 27 */     boolean result = super.update(force);
/*    */     
/* 29 */     if (result) {
/* 30 */       this.chest.update();
/*    */     }
/*    */     
/* 33 */     return result;
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\craftbukkit\block\CraftChest.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */