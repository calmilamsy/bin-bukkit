/*    */ package net.minecraft.server;
/*    */ 
/*    */ import java.util.List;
/*    */ import java.util.Random;
/*    */ import org.bukkit.block.Block;
/*    */ import org.bukkit.event.block.BlockRedstoneEvent;
/*    */ 
/*    */ public class BlockMinecartDetector
/*    */   extends BlockMinecartTrack {
/*    */   public BlockMinecartDetector(int i, int j) {
/* 11 */     super(i, j, true);
/* 12 */     a(true);
/*    */   }
/*    */ 
/*    */   
/* 16 */   public int c() { return 20; }
/*    */ 
/*    */ 
/*    */   
/* 20 */   public boolean isPowerSource() { return true; }
/*    */ 
/*    */   
/*    */   public void a(World world, int i, int j, int k, Entity entity) {
/* 24 */     if (!world.isStatic) {
/* 25 */       int l = world.getData(i, j, k);
/*    */       
/* 27 */       if ((l & 0x8) == 0) {
/* 28 */         f(world, i, j, k, l);
/*    */       }
/*    */     } 
/*    */   }
/*    */   
/*    */   public void a(World world, int i, int j, int k, Random random) {
/* 34 */     if (!world.isStatic) {
/* 35 */       int l = world.getData(i, j, k);
/*    */       
/* 37 */       if ((l & 0x8) != 0) {
/* 38 */         f(world, i, j, k, l);
/*    */       }
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/* 44 */   public boolean a(IBlockAccess iblockaccess, int i, int j, int k, int l) { return ((iblockaccess.getData(i, j, k) & 0x8) != 0); }
/*    */ 
/*    */ 
/*    */   
/* 48 */   public boolean d(World world, int i, int j, int k, int l) { return ((world.getData(i, j, k) & 0x8) == 0) ? false : ((l == 1)); }
/*    */ 
/*    */   
/*    */   private void f(World world, int i, int j, int k, int l) {
/* 52 */     boolean flag = ((l & 0x8) != 0);
/* 53 */     boolean flag1 = false;
/* 54 */     float f = 0.125F;
/* 55 */     List list = world.a(EntityMinecart.class, AxisAlignedBB.b((i + f), j, (k + f), ((i + 1) - f), j + 0.25D, ((k + 1) - f)));
/*    */     
/* 57 */     if (list.size() > 0) {
/* 58 */       flag1 = true;
/*    */     }
/*    */ 
/*    */     
/* 62 */     if (flag != flag1) {
/* 63 */       Block block = world.getWorld().getBlockAt(i, j, k);
/*    */       
/* 65 */       BlockRedstoneEvent eventRedstone = new BlockRedstoneEvent(block, flag ? 1 : 0, flag1 ? 1 : 0);
/* 66 */       world.getServer().getPluginManager().callEvent(eventRedstone);
/*    */       
/* 68 */       flag1 = (eventRedstone.getNewCurrent() > 0);
/*    */     } 
/*    */ 
/*    */     
/* 72 */     if (flag1 && !flag) {
/* 73 */       world.setData(i, j, k, l | 0x8);
/* 74 */       world.applyPhysics(i, j, k, this.id);
/* 75 */       world.applyPhysics(i, j - 1, k, this.id);
/* 76 */       world.b(i, j, k, i, j, k);
/*    */     } 
/*    */     
/* 79 */     if (!flag1 && flag) {
/* 80 */       world.setData(i, j, k, l & 0x7);
/* 81 */       world.applyPhysics(i, j, k, this.id);
/* 82 */       world.applyPhysics(i, j - 1, k, this.id);
/* 83 */       world.b(i, j, k, i, j, k);
/*    */     } 
/*    */     
/* 86 */     if (flag1)
/* 87 */       world.c(i, j, k, this.id, c()); 
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\BlockMinecartDetector.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */