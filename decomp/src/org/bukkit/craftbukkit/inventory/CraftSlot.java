/*    */ package org.bukkit.craftbukkit.inventory;
/*    */ 
/*    */ import net.minecraft.server.Slot;
/*    */ import org.bukkit.inventory.Inventory;
/*    */ import org.bukkit.inventory.ItemStack;
/*    */ import org.bukkit.inventory.Slot;
/*    */ 
/*    */ public class CraftSlot implements Slot {
/*    */   private final Slot slot;
/*    */   
/* 11 */   public CraftSlot(Slot slot) { this.slot = slot; }
/*    */ 
/*    */ 
/*    */   
/* 15 */   public Inventory getInventory() { return new CraftInventory(this.slot.inventory); }
/*    */ 
/*    */ 
/*    */   
/* 19 */   public int getIndex() { return this.slot.index; }
/*    */ 
/*    */ 
/*    */   
/* 23 */   public ItemStack getItem() { return new CraftItemStack(this.slot.getItem()); }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\craftbukkit\inventory\CraftSlot.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */