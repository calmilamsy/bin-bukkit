/*    */ package org.bukkit.craftbukkit.entity;
/*    */ 
/*    */ import net.minecraft.server.EntityMinecart;
/*    */ import org.bukkit.craftbukkit.CraftServer;
/*    */ import org.bukkit.craftbukkit.inventory.CraftInventory;
/*    */ import org.bukkit.entity.StorageMinecart;
/*    */ import org.bukkit.inventory.Inventory;
/*    */ 
/*    */ public class CraftStorageMinecart
/*    */   extends CraftMinecart implements StorageMinecart {
/*    */   private CraftInventory inventory;
/*    */   
/*    */   public CraftStorageMinecart(CraftServer server, EntityMinecart entity) {
/* 14 */     super(server, entity);
/* 15 */     this.inventory = new CraftInventory(entity);
/*    */   }
/*    */ 
/*    */   
/* 19 */   public Inventory getInventory() { return this.inventory; }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 24 */   public String toString() { return "CraftStorageMinecart{inventory=" + this.inventory + '}'; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\craftbukkit\entity\CraftStorageMinecart.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */