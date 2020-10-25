/*    */ package net.minecraft.server;
/*    */ 
/*    */ import java.util.Random;
/*    */ import org.bukkit.craftbukkit.event.CraftEventFactory;
/*    */ 
/*    */ public class BlockSnow
/*    */   extends Block {
/*    */   protected BlockSnow(int i, int j) {
/*  9 */     super(i, j, Material.SNOW_LAYER);
/* 10 */     a(0.0F, 0.0F, 0.0F, 1.0F, 0.125F, 1.0F);
/* 11 */     a(true);
/*    */   }
/*    */   
/*    */   public AxisAlignedBB e(World world, int i, int j, int k) {
/* 15 */     int l = world.getData(i, j, k) & 0x7;
/*    */     
/* 17 */     return (l >= 3) ? AxisAlignedBB.b(i + this.minX, j + this.minY, k + this.minZ, i + this.maxX, (j + 0.5F), k + this.maxZ) : null;
/*    */   }
/*    */ 
/*    */   
/* 21 */   public boolean a() { return false; }
/*    */ 
/*    */ 
/*    */   
/* 25 */   public boolean b() { return false; }
/*    */ 
/*    */   
/*    */   public void a(IBlockAccess iblockaccess, int i, int j, int k) {
/* 29 */     int l = iblockaccess.getData(i, j, k) & 0x7;
/* 30 */     float f = (2 * (1 + l)) / 16.0F;
/*    */     
/* 32 */     a(0.0F, 0.0F, 0.0F, 1.0F, f, 1.0F);
/*    */   }
/*    */   
/*    */   public boolean canPlace(World world, int i, int j, int k) {
/* 36 */     int l = world.getTypeId(i, j - 1, k);
/*    */     
/* 38 */     return (l != 0 && Block.byId[l].a()) ? world.getMaterial(i, j - 1, k).isSolid() : 0;
/*    */   }
/*    */ 
/*    */   
/* 42 */   public void doPhysics(World world, int i, int j, int k, int l) { g(world, i, j, k); }
/*    */ 
/*    */   
/*    */   private boolean g(World world, int i, int j, int k) {
/* 46 */     if (!canPlace(world, i, j, k)) {
/* 47 */       g(world, i, j, k, world.getData(i, j, k));
/* 48 */       world.setTypeId(i, j, k, 0);
/* 49 */       return false;
/*    */     } 
/* 51 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public void a(World world, EntityHuman entityhuman, int i, int j, int k, int l) {
/* 56 */     int i1 = Item.SNOW_BALL.id;
/* 57 */     float f = 0.7F;
/* 58 */     double d0 = (world.random.nextFloat() * f) + (1.0F - f) * 0.5D;
/* 59 */     double d1 = (world.random.nextFloat() * f) + (1.0F - f) * 0.5D;
/* 60 */     double d2 = (world.random.nextFloat() * f) + (1.0F - f) * 0.5D;
/* 61 */     EntityItem entityitem = new EntityItem(world, i + d0, j + d1, k + d2, new ItemStack(i1, true, false));
/*    */     
/* 63 */     entityitem.pickupDelay = 10;
/* 64 */     world.addEntity(entityitem);
/* 65 */     world.setTypeId(i, j, k, 0);
/* 66 */     entityhuman.a(StatisticList.C[this.id], 1);
/*    */   }
/*    */ 
/*    */   
/* 70 */   public int a(int i, Random random) { return Item.SNOW_BALL.id; }
/*    */ 
/*    */ 
/*    */   
/* 74 */   public int a(Random random) { return 0; }
/*    */ 
/*    */   
/*    */   public void a(World world, int i, int j, int k, Random random) {
/* 78 */     if (world.a(EnumSkyBlock.BLOCK, i, j, k) > 11) {
/*    */       
/* 80 */       if (CraftEventFactory.callBlockFadeEvent(world.getWorld().getBlockAt(i, j, k), 0).isCancelled()) {
/*    */         return;
/*    */       }
/*    */ 
/*    */       
/* 85 */       g(world, i, j, k, world.getData(i, j, k));
/* 86 */       world.setTypeId(i, j, k, 0);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\BlockSnow.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */