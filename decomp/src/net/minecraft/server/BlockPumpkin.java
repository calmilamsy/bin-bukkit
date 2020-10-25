/*    */ package net.minecraft.server;
/*    */ 
/*    */ import org.bukkit.block.Block;
/*    */ import org.bukkit.event.block.BlockRedstoneEvent;
/*    */ 
/*    */ public class BlockPumpkin extends Block {
/*    */   private boolean a;
/*    */   
/*    */   protected BlockPumpkin(int i, int j, boolean flag) {
/* 10 */     super(i, Material.PUMPKIN);
/* 11 */     this.textureId = j;
/* 12 */     a(true);
/* 13 */     this.a = flag;
/*    */   }
/*    */   
/*    */   public int a(int i, int j) {
/* 17 */     if (i == 1)
/* 18 */       return this.textureId; 
/* 19 */     if (i == 0) {
/* 20 */       return this.textureId;
/*    */     }
/* 22 */     int k = this.textureId + 1 + 16;
/*    */     
/* 24 */     if (this.a) {
/* 25 */       k++;
/*    */     }
/*    */     
/* 28 */     return (j == 2 && i == 2) ? k : ((j == 3 && i == 5) ? k : ((j == 0 && i == 3) ? k : ((j == 1 && i == 4) ? k : (this.textureId + 16))));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/* 33 */   public int a(int i) { return (i == 1) ? this.textureId : ((i == 0) ? this.textureId : ((i == 3) ? (this.textureId + 1 + 16) : (this.textureId + 16))); }
/*    */ 
/*    */ 
/*    */   
/* 37 */   public void c(World world, int i, int j, int k) { super.c(world, i, j, k); }
/*    */ 
/*    */   
/*    */   public boolean canPlace(World world, int i, int j, int k) {
/* 41 */     int l = world.getTypeId(i, j, k);
/*    */     
/* 43 */     return ((l == 0 || (Block.byId[l]).material.isReplacable()) && world.e(i, j - 1, k));
/*    */   }
/*    */   
/*    */   public void postPlace(World world, int i, int j, int k, EntityLiving entityliving) {
/* 47 */     int l = MathHelper.floor((entityliving.yaw * 4.0F / 360.0F) + 2.5D) & 0x3;
/*    */     
/* 49 */     world.setData(i, j, k, l);
/*    */   }
/*    */ 
/*    */   
/*    */   public void doPhysics(World world, int i, int j, int k, int l) {
/* 54 */     if (Block.byId[l] != null && Block.byId[l].isPowerSource()) {
/* 55 */       Block block = world.getWorld().getBlockAt(i, j, k);
/* 56 */       int power = block.getBlockPower();
/*    */       
/* 58 */       BlockRedstoneEvent eventRedstone = new BlockRedstoneEvent(block, power, power);
/* 59 */       world.getServer().getPluginManager().callEvent(eventRedstone);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\BlockPumpkin.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */