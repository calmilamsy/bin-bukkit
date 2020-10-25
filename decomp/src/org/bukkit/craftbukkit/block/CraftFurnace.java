/*    */ package org.bukkit.craftbukkit.block;
/*    */ 
/*    */ import net.minecraft.server.TileEntityFurnace;
/*    */ import org.bukkit.block.Block;
/*    */ import org.bukkit.block.Furnace;
/*    */ import org.bukkit.craftbukkit.CraftWorld;
/*    */ import org.bukkit.craftbukkit.inventory.CraftInventory;
/*    */ import org.bukkit.inventory.Inventory;
/*    */ 
/*    */ public class CraftFurnace extends CraftBlockState implements Furnace {
/*    */   private final CraftWorld world;
/*    */   private final TileEntityFurnace furnace;
/*    */   
/*    */   public CraftFurnace(Block block) {
/* 15 */     super(block);
/*    */     
/* 17 */     this.world = (CraftWorld)block.getWorld();
/* 18 */     this.furnace = (TileEntityFurnace)this.world.getTileEntityAt(getX(), getY(), getZ());
/*    */   }
/*    */ 
/*    */   
/* 22 */   public Inventory getInventory() { return new CraftInventory(this.furnace); }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean update(boolean force) {
/* 27 */     boolean result = super.update(force);
/*    */     
/* 29 */     if (result) {
/* 30 */       this.furnace.update();
/*    */     }
/*    */     
/* 33 */     return result;
/*    */   }
/*    */ 
/*    */   
/* 37 */   public short getBurnTime() { return (short)this.furnace.burnTime; }
/*    */ 
/*    */ 
/*    */   
/* 41 */   public void setBurnTime(short burnTime) { this.furnace.burnTime = burnTime; }
/*    */ 
/*    */ 
/*    */   
/* 45 */   public short getCookTime() { return (short)this.furnace.cookTime; }
/*    */ 
/*    */ 
/*    */   
/* 49 */   public void setCookTime(short cookTime) { this.furnace.cookTime = cookTime; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\craftbukkit\block\CraftFurnace.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */