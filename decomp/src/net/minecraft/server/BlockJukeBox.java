/*    */ package net.minecraft.server;
/*    */ 
/*    */ public class BlockJukeBox
/*    */   extends BlockContainer
/*    */ {
/*  6 */   protected BlockJukeBox(int i, int j) { super(i, j, Material.WOOD); }
/*    */ 
/*    */ 
/*    */   
/* 10 */   public int a(int i) { return this.textureId + ((i == 1) ? 1 : 0); }
/*    */ 
/*    */   
/*    */   public boolean interact(World world, int i, int j, int k, EntityHuman entityhuman) {
/* 14 */     if (world.getData(i, j, k) == 0) {
/* 15 */       return false;
/*    */     }
/* 17 */     b_(world, i, j, k);
/* 18 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public void f(World world, int i, int j, int k, int l) {
/* 23 */     if (!world.isStatic) {
/* 24 */       TileEntityRecordPlayer tileentityrecordplayer = (TileEntityRecordPlayer)world.getTileEntity(i, j, k);
/*    */       
/* 26 */       tileentityrecordplayer.a = l;
/* 27 */       tileentityrecordplayer.update();
/* 28 */       world.setData(i, j, k, 1);
/*    */     } 
/*    */   }
/*    */   
/*    */   public void b_(World world, int i, int j, int k) {
/* 33 */     if (!world.isStatic) {
/* 34 */       TileEntityRecordPlayer tileentityrecordplayer = (TileEntityRecordPlayer)world.getTileEntity(i, j, k);
/* 35 */       if (tileentityrecordplayer == null)
/* 36 */         return;  int l = tileentityrecordplayer.a;
/*    */       
/* 38 */       if (l != 0) {
/* 39 */         world.e(1005, i, j, k, 0);
/* 40 */         world.a((String)null, i, j, k);
/* 41 */         tileentityrecordplayer.a = 0;
/* 42 */         tileentityrecordplayer.update();
/* 43 */         world.setData(i, j, k, 0);
/* 44 */         float f = 0.7F;
/* 45 */         double d0 = (world.random.nextFloat() * f) + (1.0F - f) * 0.5D;
/* 46 */         double d1 = (world.random.nextFloat() * f) + (1.0F - f) * 0.2D + 0.6D;
/* 47 */         double d2 = (world.random.nextFloat() * f) + (1.0F - f) * 0.5D;
/* 48 */         EntityItem entityitem = new EntityItem(world, i + d0, j + d1, k + d2, new ItemStack(l, true, false));
/*    */         
/* 50 */         entityitem.pickupDelay = 10;
/* 51 */         world.addEntity(entityitem);
/*    */       } 
/*    */     } 
/*    */   }
/*    */   
/*    */   public void remove(World world, int i, int j, int k) {
/* 57 */     b_(world, i, j, k);
/* 58 */     super.remove(world, i, j, k);
/*    */   }
/*    */   
/*    */   public void dropNaturally(World world, int i, int j, int k, int l, float f) {
/* 62 */     if (!world.isStatic) {
/* 63 */       super.dropNaturally(world, i, j, k, l, f);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/* 68 */   protected TileEntity a_() { return new TileEntityRecordPlayer(); }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\BlockJukeBox.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */