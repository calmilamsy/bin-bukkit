/*    */ package net.minecraft.server;
/*    */ 
/*    */ import org.bukkit.block.Block;
/*    */ import org.bukkit.block.BlockFace;
/*    */ import org.bukkit.craftbukkit.block.CraftBlock;
/*    */ import org.bukkit.entity.Painting;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.event.painting.PaintingPlaceEvent;
/*    */ 
/*    */ public class ItemPainting
/*    */   extends Item {
/* 12 */   public ItemPainting(int i) { super(i); }
/*    */ 
/*    */   
/*    */   public boolean a(ItemStack itemstack, EntityHuman entityhuman, World world, int i, int j, int k, int l) {
/* 16 */     if (l == 0)
/* 17 */       return false; 
/* 18 */     if (l == 1) {
/* 19 */       return false;
/*    */     }
/* 21 */     byte b0 = 0;
/*    */     
/* 23 */     if (l == 4) {
/* 24 */       b0 = 1;
/*    */     }
/*    */     
/* 27 */     if (l == 3) {
/* 28 */       b0 = 2;
/*    */     }
/*    */     
/* 31 */     if (l == 5) {
/* 32 */       b0 = 3;
/*    */     }
/*    */     
/* 35 */     EntityPainting entitypainting = new EntityPainting(world, i, j, k, b0);
/*    */     
/* 37 */     if (entitypainting.h()) {
/* 38 */       if (!world.isStatic) {
/*    */         
/* 40 */         Player who = (entityhuman == null) ? null : (Player)entityhuman.getBukkitEntity();
/*    */         
/* 42 */         Block blockClicked = world.getWorld().getBlockAt(i, j, k);
/* 43 */         BlockFace blockFace = CraftBlock.notchToBlockFace(l);
/*    */         
/* 45 */         PaintingPlaceEvent event = new PaintingPlaceEvent((Painting)entitypainting.getBukkitEntity(), who, blockClicked, blockFace);
/* 46 */         world.getServer().getPluginManager().callEvent(event);
/*    */         
/* 48 */         if (event.isCancelled()) {
/* 49 */           return false;
/*    */         }
/*    */         
/* 52 */         world.addEntity(entitypainting);
/*    */       } 
/*    */       
/* 55 */       itemstack.count--;
/*    */     } 
/*    */     
/* 58 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\ItemPainting.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */