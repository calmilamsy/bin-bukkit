/*    */ package org.bukkit.craftbukkit.entity;
/*    */ 
/*    */ import net.minecraft.server.EntityItem;
/*    */ import net.minecraft.server.ItemStack;
/*    */ import org.bukkit.craftbukkit.CraftServer;
/*    */ import org.bukkit.craftbukkit.inventory.CraftItemStack;
/*    */ import org.bukkit.entity.Item;
/*    */ import org.bukkit.inventory.ItemStack;
/*    */ 
/*    */ public class CraftItem
/*    */   extends CraftEntity implements Item {
/*    */   public CraftItem(CraftServer server, EntityItem entity) {
/* 13 */     super(server, entity);
/* 14 */     this.item = entity;
/*    */   }
/*    */   private EntityItem item;
/*    */   
/* 18 */   public ItemStack getItemStack() { return new CraftItemStack(this.item.itemStack); }
/*    */ 
/*    */ 
/*    */   
/* 22 */   public void setItemStack(ItemStack stack) { this.item.itemStack = new ItemStack(stack.getTypeId(), stack.getAmount(), stack.getDurability()); }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 27 */   public String toString() { return "CraftItem"; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\craftbukkit\entity\CraftItem.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */