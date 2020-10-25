/*    */ package net.minecraft.server;
/*    */ 
/*    */ import org.bukkit.Location;
/*    */ import org.bukkit.craftbukkit.event.CraftEventFactory;
/*    */ import org.bukkit.craftbukkit.inventory.CraftItemStack;
/*    */ import org.bukkit.event.player.PlayerBucketFillEvent;
/*    */ 
/*    */ 
/*    */ public class EntityCow
/*    */   extends EntityAnimal
/*    */ {
/*    */   public EntityCow(World world) {
/* 13 */     super(world);
/* 14 */     this.texture = "/mob/cow.png";
/* 15 */     b(0.9F, 1.3F);
/*    */   }
/*    */ 
/*    */   
/* 19 */   public void b(NBTTagCompound nbttagcompound) { super.b(nbttagcompound); }
/*    */ 
/*    */ 
/*    */   
/* 23 */   public void a(NBTTagCompound nbttagcompound) { super.a(nbttagcompound); }
/*    */ 
/*    */ 
/*    */   
/* 27 */   protected String g() { return "mob.cow"; }
/*    */ 
/*    */ 
/*    */   
/* 31 */   protected String h() { return "mob.cowhurt"; }
/*    */ 
/*    */ 
/*    */   
/* 35 */   protected String i() { return "mob.cowhurt"; }
/*    */ 
/*    */ 
/*    */   
/* 39 */   protected float k() { return 0.4F; }
/*    */ 
/*    */ 
/*    */   
/* 43 */   protected int j() { return Item.LEATHER.id; }
/*    */ 
/*    */   
/*    */   public boolean a(EntityHuman entityhuman) {
/* 47 */     ItemStack itemstack = entityhuman.inventory.getItemInHand();
/*    */     
/* 49 */     if (itemstack != null && itemstack.id == Item.BUCKET.id) {
/*    */       
/* 51 */       Location loc = getBukkitEntity().getLocation();
/* 52 */       PlayerBucketFillEvent event = CraftEventFactory.callPlayerBucketFillEvent(entityhuman, loc.getBlockX(), loc.getBlockY(), loc.getBlockZ(), -1, itemstack, Item.MILK_BUCKET);
/*    */       
/* 54 */       if (event.isCancelled()) {
/* 55 */         return false;
/*    */       }
/*    */       
/* 58 */       CraftItemStack itemInHand = (CraftItemStack)event.getItemStack();
/* 59 */       byte data = (itemInHand.getData() == null) ? 0 : itemInHand.getData().getData();
/* 60 */       itemstack = new ItemStack(itemInHand.getTypeId(), itemInHand.getAmount(), data);
/*    */       
/* 62 */       entityhuman.inventory.setItem(entityhuman.inventory.itemInHandIndex, itemstack);
/*    */ 
/*    */       
/* 65 */       return true;
/*    */     } 
/* 67 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\EntityCow.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */