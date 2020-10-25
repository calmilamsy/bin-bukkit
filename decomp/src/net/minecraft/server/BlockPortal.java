/*     */ package net.minecraft.server;
/*     */ 
/*     */ import java.util.Collection;
/*     */ import java.util.HashSet;
/*     */ import java.util.Random;
/*     */ import org.bukkit.Location;
/*     */ import org.bukkit.block.Block;
/*     */ import org.bukkit.craftbukkit.CraftWorld;
/*     */ import org.bukkit.event.entity.EntityPortalEnterEvent;
/*     */ import org.bukkit.event.world.PortalCreateEvent;
/*     */ 
/*     */ public class BlockPortal extends BlockBreakable {
/*  13 */   public BlockPortal(int i, int j) { super(i, j, Material.PORTAL, false); }
/*     */ 
/*     */ 
/*     */   
/*  17 */   public AxisAlignedBB e(World world, int i, int j, int k) { return null; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void a(IBlockAccess iblockaccess, int i, int j, int k) {
/*  24 */     if (iblockaccess.getTypeId(i - 1, j, k) != this.id && iblockaccess.getTypeId(i + 1, j, k) != this.id) {
/*  25 */       float f = 0.125F;
/*  26 */       float f1 = 0.5F;
/*  27 */       a(0.5F - f, 0.0F, 0.5F - f1, 0.5F + f, 1.0F, 0.5F + f1);
/*     */     } else {
/*  29 */       float f = 0.5F;
/*  30 */       float f1 = 0.125F;
/*  31 */       a(0.5F - f, 0.0F, 0.5F - f1, 0.5F + f, 1.0F, 0.5F + f1);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*  36 */   public boolean a() { return false; }
/*     */ 
/*     */ 
/*     */   
/*  40 */   public boolean b() { return false; }
/*     */ 
/*     */   
/*     */   public boolean a_(World world, int i, int j, int k) {
/*  44 */     byte b0 = 0;
/*  45 */     byte b1 = 0;
/*     */     
/*  47 */     if (world.getTypeId(i - 1, j, k) == Block.OBSIDIAN.id || world.getTypeId(i + 1, j, k) == Block.OBSIDIAN.id) {
/*  48 */       b0 = 1;
/*     */     }
/*     */     
/*  51 */     if (world.getTypeId(i, j, k - 1) == Block.OBSIDIAN.id || world.getTypeId(i, j, k + 1) == Block.OBSIDIAN.id) {
/*  52 */       b1 = 1;
/*     */     }
/*     */     
/*  55 */     if (b0 == b1) {
/*  56 */       return false;
/*     */     }
/*     */     
/*  59 */     Collection<Block> blocks = new HashSet<Block>();
/*  60 */     CraftWorld craftWorld = world.getWorld();
/*     */ 
/*     */     
/*  63 */     if (world.getTypeId(i - b0, j, k - b1) == 0) {
/*  64 */       i -= b0;
/*  65 */       k -= b1;
/*     */     } 
/*     */ 
/*     */     
/*     */     int l;
/*     */     
/*  71 */     for (l = -1; l <= 2; l++) {
/*  72 */       for (int i1 = -1; i1 <= 3; i1++) {
/*  73 */         boolean flag = (l == -1 || l == 2 || i1 == -1 || i1 == 3);
/*     */         
/*  75 */         if ((l != -1 && l != 2) || (i1 != -1 && i1 != 3)) {
/*  76 */           int j1 = world.getTypeId(i + b0 * l, j + i1, k + b1 * l);
/*     */           
/*  78 */           if (flag) {
/*  79 */             if (j1 != Block.OBSIDIAN.id) {
/*  80 */               return false;
/*     */             }
/*  82 */             blocks.add(craftWorld.getBlockAt(i + b0 * l, j + i1, k + b1 * l));
/*     */           }
/*  84 */           else if (j1 != 0 && j1 != Block.FIRE.id) {
/*  85 */             return false;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/*  92 */     for (l = 0; l < 2; l++) {
/*  93 */       for (int i1 = 0; i1 < 3; i1++) {
/*  94 */         blocks.add(craftWorld.getBlockAt(i + b0 * l, j + i1, k + b1 * l));
/*     */       }
/*     */     } 
/*     */     
/*  98 */     PortalCreateEvent event = new PortalCreateEvent(blocks, craftWorld);
/*  99 */     world.getServer().getPluginManager().callEvent(event);
/*     */     
/* 101 */     if (event.isCancelled()) {
/* 102 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 106 */     world.suppressPhysics = true;
/*     */     
/* 108 */     for (l = 0; l < 2; l++) {
/* 109 */       for (int i1 = 0; i1 < 3; i1++) {
/* 110 */         world.setTypeId(i + b0 * l, j + i1, k + b1 * l, Block.PORTAL.id);
/*     */       }
/*     */     } 
/*     */     
/* 114 */     world.suppressPhysics = false;
/* 115 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void doPhysics(World world, int i, int j, int k, int l) {
/* 120 */     byte b0 = 0;
/* 121 */     byte b1 = 1;
/*     */     
/* 123 */     if (world.getTypeId(i - 1, j, k) == this.id || world.getTypeId(i + 1, j, k) == this.id) {
/* 124 */       b0 = 1;
/* 125 */       b1 = 0;
/*     */     } 
/*     */     
/*     */     int i1;
/*     */     
/* 130 */     for (i1 = j; world.getTypeId(i, i1 - 1, k) == this.id; i1--);
/*     */ 
/*     */ 
/*     */     
/* 134 */     if (world.getTypeId(i, i1 - 1, k) != Block.OBSIDIAN.id) {
/* 135 */       world.setTypeId(i, j, k, 0);
/*     */     } else {
/*     */       int j1;
/*     */       
/* 139 */       for (j1 = 1; j1 < 4 && world.getTypeId(i, i1 + j1, k) == this.id; j1++);
/*     */ 
/*     */ 
/*     */       
/* 143 */       if (j1 == 3 && world.getTypeId(i, i1 + j1, k) == Block.OBSIDIAN.id) {
/* 144 */         boolean flag = (world.getTypeId(i - 1, j, k) == this.id || world.getTypeId(i + 1, j, k) == this.id);
/* 145 */         boolean flag1 = (world.getTypeId(i, j, k - 1) == this.id || world.getTypeId(i, j, k + 1) == this.id);
/*     */         
/* 147 */         if (flag && flag1) {
/* 148 */           world.setTypeId(i, j, k, 0);
/* 149 */         } else if ((world.getTypeId(i + b0, j, k + b1) != Block.OBSIDIAN.id || world.getTypeId(i - b0, j, k - b1) != this.id) && (world.getTypeId(i - b0, j, k - b1) != Block.OBSIDIAN.id || world.getTypeId(i + b0, j, k + b1) != this.id)) {
/* 150 */           world.setTypeId(i, j, k, 0);
/*     */         } 
/*     */       } else {
/* 153 */         world.setTypeId(i, j, k, 0);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/* 159 */   public int a(Random random) { return 0; }
/*     */ 
/*     */   
/*     */   public void a(World world, int i, int j, int k, Entity entity) {
/* 163 */     if (entity.vehicle == null && entity.passenger == null) {
/*     */       
/* 165 */       EntityPortalEnterEvent event = new EntityPortalEnterEvent(entity.getBukkitEntity(), new Location(world.getWorld(), i, j, k));
/* 166 */       world.getServer().getPluginManager().callEvent(event);
/*     */ 
/*     */       
/* 169 */       entity.P();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\BlockPortal.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */