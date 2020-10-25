/*    */ package org.bukkit.craftbukkit.block;
/*    */ 
/*    */ import java.util.Random;
/*    */ import net.minecraft.server.Block;
/*    */ import net.minecraft.server.BlockDispenser;
/*    */ import net.minecraft.server.TileEntityDispenser;
/*    */ import org.bukkit.Material;
/*    */ import org.bukkit.block.Block;
/*    */ import org.bukkit.block.Dispenser;
/*    */ import org.bukkit.craftbukkit.CraftWorld;
/*    */ import org.bukkit.craftbukkit.inventory.CraftInventory;
/*    */ import org.bukkit.inventory.Inventory;
/*    */ 
/*    */ public class CraftDispenser extends CraftBlockState implements Dispenser {
/*    */   private final CraftWorld world;
/*    */   
/*    */   public CraftDispenser(Block block) {
/* 18 */     super(block);
/*    */     
/* 20 */     this.world = (CraftWorld)block.getWorld();
/* 21 */     this.dispenser = (TileEntityDispenser)this.world.getTileEntityAt(getX(), getY(), getZ());
/*    */   }
/*    */   private final TileEntityDispenser dispenser;
/*    */   
/* 25 */   public Inventory getInventory() { return new CraftInventory(this.dispenser); }
/*    */ 
/*    */   
/*    */   public boolean dispense() {
/* 29 */     Block block = getBlock();
/*    */     
/* 31 */     synchronized (block) {
/* 32 */       if (block.getType() == Material.DISPENSER) {
/* 33 */         BlockDispenser dispense = (BlockDispenser)Block.DISPENSER;
/*    */         
/* 35 */         dispense.dispense(this.world.getHandle(), getX(), getY(), getZ(), new Random());
/* 36 */         return true;
/*    */       } 
/* 38 */       return false;
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean update(boolean force) {
/* 45 */     boolean result = super.update(force);
/*    */     
/* 47 */     if (result) {
/* 48 */       this.dispenser.update();
/*    */     }
/*    */     
/* 51 */     return result;
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\craftbukkit\block\CraftDispenser.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */