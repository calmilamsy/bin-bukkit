/*     */ package net.minecraft.server;
/*     */ 
/*     */ import java.util.Random;
/*     */ import org.bukkit.event.block.LeavesDecayEvent;
/*     */ 
/*     */ public class BlockLeaves
/*     */   extends BlockLeavesBase
/*     */ {
/*     */   private int c;
/*     */   int[] a;
/*     */   
/*     */   protected BlockLeaves(int i, int j) {
/*  13 */     super(i, j, Material.LEAVES, false);
/*  14 */     this.c = j;
/*  15 */     a(true);
/*     */   }
/*     */   
/*     */   public void remove(World world, int i, int j, int k) {
/*  19 */     byte b0 = 1;
/*  20 */     int l = b0 + 1;
/*     */     
/*  22 */     if (world.a(i - l, j - l, k - l, i + l, j + l, k + l)) {
/*  23 */       for (int i1 = -b0; i1 <= b0; i1++) {
/*  24 */         for (int j1 = -b0; j1 <= b0; j1++) {
/*  25 */           for (int k1 = -b0; k1 <= b0; k1++) {
/*  26 */             int l1 = world.getTypeId(i + i1, j + j1, k + k1);
/*     */             
/*  28 */             if (l1 == Block.LEAVES.id) {
/*  29 */               int i2 = world.getData(i + i1, j + j1, k + k1);
/*     */               
/*  31 */               world.setRawData(i + i1, j + j1, k + k1, i2 | 0x8);
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   public void a(World world, int i, int j, int k, Random random) {
/*  40 */     if (!world.isStatic) {
/*  41 */       int l = world.getData(i, j, k);
/*     */       
/*  43 */       if ((l & 0x8) != 0) {
/*  44 */         byte b0 = 4;
/*  45 */         int i1 = b0 + 1;
/*  46 */         byte b1 = 32;
/*  47 */         int j1 = b1 * b1;
/*  48 */         int k1 = b1 / 2;
/*     */         
/*  50 */         if (this.a == null) {
/*  51 */           this.a = new int[b1 * b1 * b1];
/*     */         }
/*     */ 
/*     */ 
/*     */         
/*  56 */         if (world.a(i - i1, j - i1, k - i1, i + i1, j + i1, k + i1)) {
/*     */           int l1;
/*     */ 
/*     */ 
/*     */           
/*  61 */           for (l1 = -b0; l1 <= b0; l1++) {
/*  62 */             for (int i2 = -b0; i2 <= b0; i2++) {
/*  63 */               for (int j2 = -b0; j2 <= b0; j2++) {
/*  64 */                 int k2 = world.getTypeId(i + l1, j + i2, k + j2);
/*  65 */                 if (k2 == Block.LOG.id) {
/*  66 */                   this.a[(l1 + k1) * j1 + (i2 + k1) * b1 + j2 + k1] = 0;
/*  67 */                 } else if (k2 == Block.LEAVES.id) {
/*  68 */                   this.a[(l1 + k1) * j1 + (i2 + k1) * b1 + j2 + k1] = -2;
/*     */                 } else {
/*  70 */                   this.a[(l1 + k1) * j1 + (i2 + k1) * b1 + j2 + k1] = -1;
/*     */                 } 
/*     */               } 
/*     */             } 
/*     */           } 
/*     */           
/*  76 */           for (l1 = 1; l1 <= 4; l1++) {
/*  77 */             for (int i2 = -b0; i2 <= b0; i2++) {
/*  78 */               for (int j2 = -b0; j2 <= b0; j2++) {
/*  79 */                 for (int k2 = -b0; k2 <= b0; k2++) {
/*  80 */                   if (this.a[(i2 + k1) * j1 + (j2 + k1) * b1 + k2 + k1] == l1 - 1) {
/*  81 */                     if (this.a[(i2 + k1 - 1) * j1 + (j2 + k1) * b1 + k2 + k1] == -2) {
/*  82 */                       this.a[(i2 + k1 - 1) * j1 + (j2 + k1) * b1 + k2 + k1] = l1;
/*     */                     }
/*     */                     
/*  85 */                     if (this.a[(i2 + k1 + 1) * j1 + (j2 + k1) * b1 + k2 + k1] == -2) {
/*  86 */                       this.a[(i2 + k1 + 1) * j1 + (j2 + k1) * b1 + k2 + k1] = l1;
/*     */                     }
/*     */                     
/*  89 */                     if (this.a[(i2 + k1) * j1 + (j2 + k1 - 1) * b1 + k2 + k1] == -2) {
/*  90 */                       this.a[(i2 + k1) * j1 + (j2 + k1 - 1) * b1 + k2 + k1] = l1;
/*     */                     }
/*     */                     
/*  93 */                     if (this.a[(i2 + k1) * j1 + (j2 + k1 + 1) * b1 + k2 + k1] == -2) {
/*  94 */                       this.a[(i2 + k1) * j1 + (j2 + k1 + 1) * b1 + k2 + k1] = l1;
/*     */                     }
/*     */                     
/*  97 */                     if (this.a[(i2 + k1) * j1 + (j2 + k1) * b1 + k2 + k1 - 1] == -2) {
/*  98 */                       this.a[(i2 + k1) * j1 + (j2 + k1) * b1 + k2 + k1 - 1] = l1;
/*     */                     }
/*     */                     
/* 101 */                     if (this.a[(i2 + k1) * j1 + (j2 + k1) * b1 + k2 + k1 + 1] == -2) {
/* 102 */                       this.a[(i2 + k1) * j1 + (j2 + k1) * b1 + k2 + k1 + 1] = l1;
/*     */                     }
/*     */                   } 
/*     */                 } 
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         } 
/*     */         
/* 111 */         int l1 = this.a[k1 * j1 + k1 * b1 + k1];
/* 112 */         if (l1 >= 0) {
/* 113 */           world.setRawData(i, j, k, l & 0xFFFFFFF7);
/*     */         } else {
/* 115 */           g(world, i, j, k);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void g(World world, int i, int j, int k) {
/* 123 */     LeavesDecayEvent event = new LeavesDecayEvent(world.getWorld().getBlockAt(i, j, k));
/* 124 */     world.getServer().getPluginManager().callEvent(event);
/*     */     
/* 126 */     if (event.isCancelled()) {
/*     */       return;
/*     */     }
/* 129 */     g(world, i, j, k, world.getData(i, j, k));
/* 130 */     world.setTypeId(i, j, k, 0);
/*     */   }
/*     */ 
/*     */   
/* 134 */   public int a(Random random) { return (random.nextInt(20) == 0) ? 1 : 0; }
/*     */ 
/*     */ 
/*     */   
/* 138 */   public int a(int i, Random random) { return Block.SAPLING.id; }
/*     */ 
/*     */   
/*     */   public void a(World world, EntityHuman entityhuman, int i, int j, int k, int l) {
/* 142 */     if (!world.isStatic && entityhuman.G() != null && (entityhuman.G()).id == Item.SHEARS.id) {
/* 143 */       entityhuman.a(StatisticList.C[this.id], 1);
/* 144 */       a(world, i, j, k, new ItemStack(Block.LEAVES.id, true, l & 0x3));
/*     */     } else {
/* 146 */       super.a(world, entityhuman, i, j, k, l);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/* 151 */   protected int a_(int i) { return i & 0x3; }
/*     */ 
/*     */ 
/*     */   
/* 155 */   public boolean a() { return !this.b; }
/*     */ 
/*     */ 
/*     */   
/* 159 */   public int a(int i, int j) { return ((j & 0x3) == 1) ? (this.textureId + 80) : this.textureId; }
/*     */ 
/*     */ 
/*     */   
/* 163 */   public void b(World world, int i, int j, int k, Entity entity) { super.b(world, i, j, k, entity); }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\BlockLeaves.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */