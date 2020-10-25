/*     */ package net.minecraft.server;
/*     */ 
/*     */ import java.util.Random;
/*     */ import org.bukkit.block.Block;
/*     */ import org.bukkit.block.BlockState;
/*     */ import org.bukkit.craftbukkit.CraftServer;
/*     */ import org.bukkit.craftbukkit.CraftWorld;
/*     */ import org.bukkit.event.block.BlockBurnEvent;
/*     */ import org.bukkit.event.block.BlockIgniteEvent;
/*     */ import org.bukkit.event.block.BlockSpreadEvent;
/*     */ import org.bukkit.material.MaterialData;
/*     */ 
/*     */ public class BlockFire
/*     */   extends Block {
/*  15 */   private int[] a = new int[256];
/*  16 */   private int[] b = new int[256];
/*     */   
/*     */   protected BlockFire(int i, int j) {
/*  19 */     super(i, j, Material.FIRE);
/*  20 */     a(true);
/*     */   }
/*     */   
/*     */   public void h() {
/*  24 */     a(Block.WOOD.id, 5, 20);
/*  25 */     a(Block.FENCE.id, 5, 20);
/*  26 */     a(Block.WOOD_STAIRS.id, 5, 20);
/*  27 */     a(Block.LOG.id, 5, 5);
/*  28 */     a(Block.LEAVES.id, 30, 60);
/*  29 */     a(Block.BOOKSHELF.id, 30, 20);
/*  30 */     a(Block.TNT.id, 15, 100);
/*  31 */     a(Block.LONG_GRASS.id, 60, 100);
/*  32 */     a(Block.WOOL.id, 30, 60);
/*     */   }
/*     */   
/*     */   private void a(int i, int j, int k) {
/*  36 */     this.a[i] = j;
/*  37 */     this.b[i] = k;
/*     */   }
/*     */ 
/*     */   
/*  41 */   public AxisAlignedBB e(World world, int i, int j, int k) { return null; }
/*     */ 
/*     */ 
/*     */   
/*  45 */   public boolean a() { return false; }
/*     */ 
/*     */ 
/*     */   
/*  49 */   public boolean b() { return false; }
/*     */ 
/*     */ 
/*     */   
/*  53 */   public int a(Random random) { return 0; }
/*     */ 
/*     */ 
/*     */   
/*  57 */   public int c() { return 40; }
/*     */ 
/*     */   
/*     */   public void a(World world, int i, int j, int k, Random random) {
/*  61 */     boolean flag = (world.getTypeId(i, j - 1, k) == Block.NETHERRACK.id);
/*     */     
/*  63 */     if (!canPlace(world, i, j, k)) {
/*  64 */       world.setTypeId(i, j, k, 0);
/*     */     }
/*     */     
/*  67 */     if (!flag && world.v() && (world.s(i, j, k) || world.s(i - 1, j, k) || world.s(i + 1, j, k) || world.s(i, j, k - 1) || world.s(i, j, k + 1))) {
/*  68 */       world.setTypeId(i, j, k, 0);
/*     */     } else {
/*  70 */       int l = world.getData(i, j, k);
/*     */       
/*  72 */       if (l < 15) {
/*  73 */         world.setRawData(i, j, k, l + random.nextInt(3) / 2);
/*     */       }
/*     */       
/*  76 */       world.c(i, j, k, this.id, c());
/*  77 */       if (!flag && !g(world, i, j, k)) {
/*  78 */         if (!world.e(i, j - 1, k) || l > 3) {
/*  79 */           world.setTypeId(i, j, k, 0);
/*     */         }
/*  81 */       } else if (!flag && !b(world, i, j - 1, k) && l == 15 && random.nextInt(4) == 0) {
/*  82 */         world.setTypeId(i, j, k, 0);
/*     */       } else {
/*  84 */         a(world, i + 1, j, k, 300, random, l);
/*  85 */         a(world, i - 1, j, k, 300, random, l);
/*  86 */         a(world, i, j - 1, k, 250, random, l);
/*  87 */         a(world, i, j + 1, k, 250, random, l);
/*  88 */         a(world, i, j, k - 1, 300, random, l);
/*  89 */         a(world, i, j, k + 1, 300, random, l);
/*     */ 
/*     */         
/*  92 */         CraftServer craftServer = world.getServer();
/*  93 */         CraftWorld craftWorld = world.getWorld();
/*     */         
/*  95 */         BlockIgniteEvent.IgniteCause igniteCause = BlockIgniteEvent.IgniteCause.SPREAD;
/*  96 */         Block fromBlock = craftWorld.getBlockAt(i, j, k);
/*     */ 
/*     */         
/*  99 */         for (int i1 = i - 1; i1 <= i + 1; i1++) {
/* 100 */           for (int j1 = k - 1; j1 <= k + 1; j1++) {
/* 101 */             for (int k1 = j - 1; k1 <= j + 4; k1++) {
/* 102 */               if (i1 != i || k1 != j || j1 != k) {
/* 103 */                 int l1 = 100;
/*     */                 
/* 105 */                 if (k1 > j + 1) {
/* 106 */                   l1 += (k1 - j + 1) * 100;
/*     */                 }
/*     */                 
/* 109 */                 int i2 = h(world, i1, k1, j1);
/*     */                 
/* 111 */                 if (i2 > 0) {
/* 112 */                   int j2 = (i2 + 40) / (l + 30);
/*     */                   
/* 114 */                   if (j2 > 0 && random.nextInt(l1) <= j2 && (!world.v() || !world.s(i1, k1, j1)) && !world.s(i1 - 1, k1, k) && !world.s(i1 + 1, k1, j1) && !world.s(i1, k1, j1 - 1) && !world.s(i1, k1, j1 + 1)) {
/* 115 */                     int k2 = l + random.nextInt(5) / 4;
/*     */                     
/* 117 */                     if (k2 > 15) {
/* 118 */                       k2 = 15;
/*     */                     }
/*     */                     
/* 121 */                     Block block = craftWorld.getBlockAt(i1, k1, j1);
/*     */                     
/* 123 */                     if (block.getTypeId() != Block.FIRE.id) {
/* 124 */                       BlockIgniteEvent event = new BlockIgniteEvent(block, igniteCause, null);
/* 125 */                       craftServer.getPluginManager().callEvent(event);
/*     */                       
/* 127 */                       if (!event.isCancelled()) {
/*     */ 
/*     */ 
/*     */                         
/* 131 */                         BlockState blockState = craftWorld.getBlockAt(i1, k1, j1).getState();
/* 132 */                         blockState.setTypeId(this.id);
/* 133 */                         blockState.setData(new MaterialData(this.id, (byte)k2));
/*     */                         
/* 135 */                         BlockSpreadEvent spreadEvent = new BlockSpreadEvent(blockState.getBlock(), fromBlock, blockState);
/* 136 */                         craftServer.getPluginManager().callEvent(spreadEvent);
/*     */                         
/* 138 */                         if (!spreadEvent.isCancelled()) {
/* 139 */                           blockState.update(true);
/*     */                         }
/*     */                       } 
/*     */                     } 
/*     */                   } 
/*     */                 } 
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void a(World world, int i, int j, int k, int l, Random random, int i1) {
/* 154 */     int j1 = this.b[world.getTypeId(i, j, k)];
/*     */     
/* 156 */     if (random.nextInt(l) < j1) {
/* 157 */       boolean flag = (world.getTypeId(i, j, k) == Block.TNT.id);
/*     */       
/* 159 */       Block theBlock = world.getWorld().getBlockAt(i, j, k);
/*     */       
/* 161 */       BlockBurnEvent event = new BlockBurnEvent(theBlock);
/* 162 */       world.getServer().getPluginManager().callEvent(event);
/*     */       
/* 164 */       if (event.isCancelled()) {
/*     */         return;
/*     */       }
/*     */ 
/*     */       
/* 169 */       if (random.nextInt(i1 + 10) < 5 && !world.s(i, j, k)) {
/* 170 */         int k1 = i1 + random.nextInt(5) / 4;
/*     */         
/* 172 */         if (k1 > 15) {
/* 173 */           k1 = 15;
/*     */         }
/*     */         
/* 176 */         world.setTypeIdAndData(i, j, k, this.id, k1);
/*     */       } else {
/* 178 */         world.setTypeId(i, j, k, 0);
/*     */       } 
/*     */       
/* 181 */       if (flag) {
/* 182 */         Block.TNT.postBreak(world, i, j, k, 1);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/* 188 */   private boolean g(World world, int i, int j, int k) { return b(world, i + 1, j, k) ? true : (b(world, i - 1, j, k) ? true : (b(world, i, j - 1, k) ? true : (b(world, i, j + 1, k) ? true : (b(world, i, j, k - 1) ? true : b(world, i, j, k + 1))))); }
/*     */ 
/*     */   
/*     */   private int h(World world, int i, int j, int k) {
/* 192 */     byte b0 = 0;
/*     */     
/* 194 */     if (!world.isEmpty(i, j, k)) {
/* 195 */       return 0;
/*     */     }
/* 197 */     l = f(world, i + 1, j, k, b0);
/*     */     
/* 199 */     l = f(world, i - 1, j, k, l);
/* 200 */     l = f(world, i, j - 1, k, l);
/* 201 */     l = f(world, i, j + 1, k, l);
/* 202 */     l = f(world, i, j, k - 1, l);
/* 203 */     return f(world, i, j, k + 1, l);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 209 */   public boolean k_() { return false; }
/*     */ 
/*     */ 
/*     */   
/* 213 */   public boolean b(IBlockAccess iblockaccess, int i, int j, int k) { return (this.a[iblockaccess.getTypeId(i, j, k)] > 0); }
/*     */ 
/*     */   
/*     */   public int f(World world, int i, int j, int k, int l) {
/* 217 */     int i1 = this.a[world.getTypeId(i, j, k)];
/*     */     
/* 219 */     return (i1 > l) ? i1 : l;
/*     */   }
/*     */ 
/*     */   
/* 223 */   public boolean canPlace(World world, int i, int j, int k) { return (world.e(i, j - 1, k) || g(world, i, j, k)); }
/*     */ 
/*     */   
/*     */   public void doPhysics(World world, int i, int j, int k, int l) {
/* 227 */     if (!world.e(i, j - 1, k) && !g(world, i, j, k)) {
/* 228 */       world.setTypeId(i, j, k, 0);
/*     */     }
/*     */   }
/*     */   
/*     */   public void c(World world, int i, int j, int k) {
/* 233 */     if (world.getTypeId(i, j - 1, k) != Block.OBSIDIAN.id || !Block.PORTAL.a_(world, i, j, k))
/* 234 */       if (!world.e(i, j - 1, k) && !g(world, i, j, k)) {
/* 235 */         world.setTypeId(i, j, k, 0);
/*     */       } else {
/* 237 */         world.c(i, j, k, this.id, c());
/*     */       }  
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\BlockFire.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */