/*    */ package org.bukkit.event.entity;
/*    */ 
/*    */ import java.util.List;
/*    */ import org.bukkit.entity.Entity;
/*    */ import org.bukkit.event.Event;
/*    */ import org.bukkit.inventory.ItemStack;
/*    */ 
/*    */ public class EntityDeathEvent
/*    */   extends EntityEvent
/*    */ {
/*    */   private List<ItemStack> drops;
/*    */   
/*    */   public EntityDeathEvent(Entity what, List<ItemStack> drops) {
/* 14 */     super(Event.Type.ENTITY_DEATH, what);
/* 15 */     this.drops = drops;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 24 */   public List<ItemStack> getDrops() { return this.drops; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\event\entity\EntityDeathEvent.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */