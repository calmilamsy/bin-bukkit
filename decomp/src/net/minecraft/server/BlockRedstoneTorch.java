/*     */ package net.minecraft.server;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import org.bukkit.block.Block;
/*     */ import org.bukkit.event.block.BlockRedstoneEvent;
/*     */ import org.bukkit.plugin.PluginManager;
/*     */ 
/*     */ public class BlockRedstoneTorch extends BlockTorch {
/*     */   private boolean isOn = false;
/*  12 */   private static List b = new ArrayList();
/*     */ 
/*     */   
/*  15 */   public int a(int i, int j) { return (i == 1) ? Block.REDSTONE_WIRE.a(i, j) : super.a(i, j); }
/*     */ 
/*     */   
/*     */   private boolean a(World world, int i, int j, int k, boolean flag) {
/*  19 */     if (flag) {
/*  20 */       b.add(new RedstoneUpdateInfo(i, j, k, world.getTime()));
/*     */     }
/*     */     
/*  23 */     int l = 0;
/*     */     
/*  25 */     for (int i1 = 0; i1 < b.size(); i1++) {
/*  26 */       RedstoneUpdateInfo redstoneupdateinfo = (RedstoneUpdateInfo)b.get(i1);
/*     */ 
/*     */       
/*  29 */       l++;
/*  30 */       if (redstoneupdateinfo.a == i && redstoneupdateinfo.b == j && redstoneupdateinfo.c == k && l >= 8) {
/*  31 */         return true;
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/*  36 */     return false;
/*     */   }
/*     */   
/*     */   protected BlockRedstoneTorch(int i, int j, boolean flag) {
/*  40 */     super(i, j);
/*  41 */     this.isOn = flag;
/*  42 */     a(true);
/*     */   }
/*     */ 
/*     */   
/*  46 */   public int c() { return 2; }
/*     */ 
/*     */   
/*     */   public void c(World world, int i, int j, int k) {
/*  50 */     if (world.getData(i, j, k) == 0) {
/*  51 */       super.c(world, i, j, k);
/*     */     }
/*     */     
/*  54 */     if (this.isOn) {
/*  55 */       world.applyPhysics(i, j - 1, k, this.id);
/*  56 */       world.applyPhysics(i, j + 1, k, this.id);
/*  57 */       world.applyPhysics(i - 1, j, k, this.id);
/*  58 */       world.applyPhysics(i + 1, j, k, this.id);
/*  59 */       world.applyPhysics(i, j, k - 1, this.id);
/*  60 */       world.applyPhysics(i, j, k + 1, this.id);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void remove(World world, int i, int j, int k) {
/*  65 */     if (this.isOn) {
/*  66 */       world.applyPhysics(i, j - 1, k, this.id);
/*  67 */       world.applyPhysics(i, j + 1, k, this.id);
/*  68 */       world.applyPhysics(i - 1, j, k, this.id);
/*  69 */       world.applyPhysics(i + 1, j, k, this.id);
/*  70 */       world.applyPhysics(i, j, k - 1, this.id);
/*  71 */       world.applyPhysics(i, j, k + 1, this.id);
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean a(IBlockAccess iblockaccess, int i, int j, int k, int l) {
/*  76 */     if (!this.isOn) {
/*  77 */       return false;
/*     */     }
/*  79 */     int i1 = iblockaccess.getData(i, j, k);
/*     */     
/*  81 */     return (i1 == 5 && l == 1) ? false : ((i1 == 3 && l == 3) ? false : ((i1 == 4 && l == 2) ? false : ((i1 == 1 && l == 5) ? false : ((i1 != 2 || l != 4)))));
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean g(World world, int i, int j, int k) {
/*  86 */     int l = world.getData(i, j, k);
/*     */     
/*  88 */     return (l == 5 && world.isBlockFaceIndirectlyPowered(i, j - 1, k, 0)) ? true : ((l == 3 && world.isBlockFaceIndirectlyPowered(i, j, k - 1, 2)) ? true : ((l == 4 && world.isBlockFaceIndirectlyPowered(i, j, k + 1, 3)) ? true : ((l == 1 && world.isBlockFaceIndirectlyPowered(i - 1, j, k, 4)) ? true : ((l == 2 && world.isBlockFaceIndirectlyPowered(i + 1, j, k, 5))))));
/*     */   }
/*     */   
/*     */   public void a(World world, int i, int j, int k, Random random) {
/*  92 */     boolean flag = g(world, i, j, k);
/*     */     
/*  94 */     while (b.size() > 0 && world.getTime() - ((RedstoneUpdateInfo)b.get(0)).d > 100L) {
/*  95 */       b.remove(0);
/*     */     }
/*     */ 
/*     */     
/*  99 */     PluginManager manager = world.getServer().getPluginManager();
/* 100 */     Block block = world.getWorld().getBlockAt(i, j, k);
/* 101 */     int oldCurrent = this.isOn ? 15 : 0;
/*     */     
/* 103 */     BlockRedstoneEvent event = new BlockRedstoneEvent(block, oldCurrent, oldCurrent);
/*     */ 
/*     */     
/* 106 */     if (this.isOn) {
/* 107 */       if (flag) {
/*     */         
/* 109 */         if (oldCurrent != 0) {
/* 110 */           event.setNewCurrent(0);
/* 111 */           manager.callEvent(event);
/* 112 */           if (event.getNewCurrent() != 0) {
/*     */             return;
/*     */           }
/*     */         } 
/*     */ 
/*     */         
/* 118 */         world.setTypeIdAndData(i, j, k, Block.REDSTONE_TORCH_OFF.id, world.getData(i, j, k));
/* 119 */         if (a(world, i, j, k, true)) {
/* 120 */           world.makeSound((i + 0.5F), (j + 0.5F), (k + 0.5F), "random.fizz", 0.5F, 2.6F + (world.random.nextFloat() - world.random.nextFloat()) * 0.8F);
/*     */           
/* 122 */           for (int l = 0; l < 5; l++) {
/* 123 */             double d0 = i + random.nextDouble() * 0.6D + 0.2D;
/* 124 */             double d1 = j + random.nextDouble() * 0.6D + 0.2D;
/* 125 */             double d2 = k + random.nextDouble() * 0.6D + 0.2D;
/*     */             
/* 127 */             world.a("smoke", d0, d1, d2, 0.0D, 0.0D, 0.0D);
/*     */           } 
/*     */         } 
/*     */       } 
/* 131 */     } else if (!flag && !a(world, i, j, k, false)) {
/*     */       
/* 133 */       if (oldCurrent != 15) {
/* 134 */         event.setNewCurrent(15);
/* 135 */         manager.callEvent(event);
/* 136 */         if (event.getNewCurrent() != 15) {
/*     */           return;
/*     */         }
/*     */       } 
/*     */ 
/*     */       
/* 142 */       world.setTypeIdAndData(i, j, k, Block.REDSTONE_TORCH_ON.id, world.getData(i, j, k));
/*     */     } 
/*     */   }
/*     */   
/*     */   public void doPhysics(World world, int i, int j, int k, int l) {
/* 147 */     super.doPhysics(world, i, j, k, l);
/* 148 */     world.c(i, j, k, this.id, c());
/*     */   }
/*     */ 
/*     */   
/* 152 */   public boolean d(World world, int i, int j, int k, int l) { return (l == 0) ? a(world, i, j, k, l) : 0; }
/*     */ 
/*     */ 
/*     */   
/* 156 */   public int a(int i, Random random) { return Block.REDSTONE_TORCH_ON.id; }
/*     */ 
/*     */ 
/*     */   
/* 160 */   public boolean isPowerSource() { return true; }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\BlockRedstoneTorch.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */