/*    */ package org.bukkit.craftbukkit.inventory;
/*    */ 
/*    */ import net.minecraft.server.IInventory;
/*    */ import net.minecraft.server.InventoryPlayer;
/*    */ import net.minecraft.server.ItemStack;
/*    */ import org.bukkit.inventory.ItemStack;
/*    */ import org.bukkit.inventory.PlayerInventory;
/*    */ 
/*    */ public class CraftInventoryPlayer extends CraftInventory implements PlayerInventory {
/* 10 */   public CraftInventoryPlayer(InventoryPlayer inventory) { super(inventory); }
/*    */ 
/*    */ 
/*    */   
/* 14 */   public InventoryPlayer getInventory() { return (InventoryPlayer)this.inventory; }
/*    */ 
/*    */ 
/*    */   
/* 18 */   public int getSize() { return super.getSize() - 4; }
/*    */ 
/*    */ 
/*    */   
/* 22 */   public ItemStack getItemInHand() { return new CraftItemStack(getInventory().getItemInHand()); }
/*    */ 
/*    */ 
/*    */   
/* 26 */   public void setItemInHand(ItemStack stack) { setItem(getHeldItemSlot(), stack); }
/*    */ 
/*    */ 
/*    */   
/* 30 */   public int getHeldItemSlot() { return (getInventory()).itemInHandIndex; }
/*    */ 
/*    */ 
/*    */   
/* 34 */   public ItemStack getHelmet() { return getItem(getSize() + 3); }
/*    */ 
/*    */ 
/*    */   
/* 38 */   public ItemStack getChestplate() { return getItem(getSize() + 2); }
/*    */ 
/*    */ 
/*    */   
/* 42 */   public ItemStack getLeggings() { return getItem(getSize() + 1); }
/*    */ 
/*    */ 
/*    */   
/* 46 */   public ItemStack getBoots() { return getItem(getSize() + 0); }
/*    */ 
/*    */ 
/*    */   
/* 50 */   public void setHelmet(ItemStack helmet) { setItem(getSize() + 3, helmet); }
/*    */ 
/*    */ 
/*    */   
/* 54 */   public void setChestplate(ItemStack chestplate) { setItem(getSize() + 2, chestplate); }
/*    */ 
/*    */ 
/*    */   
/* 58 */   public void setLeggings(ItemStack leggings) { setItem(getSize() + 1, leggings); }
/*    */ 
/*    */ 
/*    */   
/* 62 */   public void setBoots(ItemStack boots) { setItem(getSize() + 0, boots); }
/*    */ 
/*    */   
/*    */   public CraftItemStack[] getArmorContents() {
/* 66 */     ItemStack[] mcItems = getInventory().getArmorContents();
/* 67 */     CraftItemStack[] ret = new CraftItemStack[mcItems.length];
/*    */     
/* 69 */     for (int i = 0; i < mcItems.length; i++) {
/* 70 */       ret[i] = new CraftItemStack(mcItems[i]);
/*    */     }
/* 72 */     return ret;
/*    */   }
/*    */   
/*    */   public void setArmorContents(ItemStack[] items) {
/* 76 */     int cnt = getSize();
/*    */     
/* 78 */     if (items == null) {
/* 79 */       items = new ItemStack[4];
/*    */     }
/* 81 */     for (ItemStack item : items) {
/* 82 */       if (item == null || item.getTypeId() == 0) {
/* 83 */         clear(cnt++);
/*    */       } else {
/* 85 */         setItem(cnt++, item);
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\craftbukkit\inventory\CraftInventoryPlayer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */