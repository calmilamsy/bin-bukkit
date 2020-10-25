/*     */ package net.minecraft.server;
/*     */ 
/*     */ import java.util.Random;
/*     */ import org.bukkit.block.Block;
/*     */ import org.bukkit.event.block.BlockRedstoneEvent;
/*     */ 
/*     */ public class BlockSign
/*     */   extends BlockContainer {
/*     */   private Class a;
/*     */   private boolean b;
/*     */   
/*     */   protected BlockSign(int i, Class oclass, boolean flag) {
/*  13 */     super(i, Material.WOOD);
/*  14 */     this.b = flag;
/*  15 */     this.textureId = 4;
/*  16 */     this.a = oclass;
/*  17 */     float f = 0.25F;
/*  18 */     float f1 = 1.0F;
/*     */     
/*  20 */     a(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, f1, 0.5F + f);
/*     */   }
/*     */ 
/*     */   
/*  24 */   public AxisAlignedBB e(World world, int i, int j, int k) { return null; }
/*     */ 
/*     */   
/*     */   public void a(IBlockAccess iblockaccess, int i, int j, int k) {
/*  28 */     if (!this.b) {
/*  29 */       int l = iblockaccess.getData(i, j, k);
/*  30 */       float f = 0.28125F;
/*  31 */       float f1 = 0.78125F;
/*  32 */       float f2 = 0.0F;
/*  33 */       float f3 = 1.0F;
/*  34 */       float f4 = 0.125F;
/*     */       
/*  36 */       a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
/*  37 */       if (l == 2) {
/*  38 */         a(f2, f, 1.0F - f4, f3, f1, 1.0F);
/*     */       }
/*     */       
/*  41 */       if (l == 3) {
/*  42 */         a(f2, f, 0.0F, f3, f1, f4);
/*     */       }
/*     */       
/*  45 */       if (l == 4) {
/*  46 */         a(1.0F - f4, f, f2, 1.0F, f1, f3);
/*     */       }
/*     */       
/*  49 */       if (l == 5) {
/*  50 */         a(0.0F, f, f2, f4, f1, f3);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*  56 */   public boolean b() { return false; }
/*     */ 
/*     */ 
/*     */   
/*  60 */   public boolean a() { return false; }
/*     */ 
/*     */   
/*     */   protected TileEntity a_() {
/*     */     try {
/*  65 */       return (TileEntity)this.a.newInstance();
/*  66 */     } catch (Exception exception) {
/*  67 */       throw new RuntimeException(exception);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*  72 */   public int a(int i, Random random) { return Item.SIGN.id; }
/*     */ 
/*     */   
/*     */   public void doPhysics(World world, int i, int j, int k, int l) {
/*  76 */     boolean flag = false;
/*     */     
/*  78 */     if (this.b) {
/*  79 */       if (!world.getMaterial(i, j - 1, k).isBuildable()) {
/*  80 */         flag = true;
/*     */       }
/*     */     } else {
/*  83 */       int i1 = world.getData(i, j, k);
/*     */       
/*  85 */       flag = true;
/*  86 */       if (i1 == 2 && world.getMaterial(i, j, k + 1).isBuildable()) {
/*  87 */         flag = false;
/*     */       }
/*     */       
/*  90 */       if (i1 == 3 && world.getMaterial(i, j, k - 1).isBuildable()) {
/*  91 */         flag = false;
/*     */       }
/*     */       
/*  94 */       if (i1 == 4 && world.getMaterial(i + 1, j, k).isBuildable()) {
/*  95 */         flag = false;
/*     */       }
/*     */       
/*  98 */       if (i1 == 5 && world.getMaterial(i - 1, j, k).isBuildable()) {
/*  99 */         flag = false;
/*     */       }
/*     */     } 
/*     */     
/* 103 */     if (flag) {
/* 104 */       g(world, i, j, k, world.getData(i, j, k));
/* 105 */       world.setTypeId(i, j, k, 0);
/*     */     } 
/*     */     
/* 108 */     super.doPhysics(world, i, j, k, l);
/*     */ 
/*     */     
/* 111 */     if (Block.byId[l] != null && Block.byId[l].isPowerSource()) {
/* 112 */       Block block = world.getWorld().getBlockAt(i, j, k);
/* 113 */       int power = block.getBlockPower();
/*     */       
/* 115 */       BlockRedstoneEvent eventRedstone = new BlockRedstoneEvent(block, power, power);
/* 116 */       world.getServer().getPluginManager().callEvent(eventRedstone);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\BlockSign.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */