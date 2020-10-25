/*     */ package net.minecraft.server;
/*     */ 
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import org.bukkit.craftbukkit.CraftWorld;
/*     */ import org.bukkit.craftbukkit.event.CraftEventFactory;
/*     */ import org.bukkit.event.block.Action;
/*     */ import org.bukkit.event.block.BlockRedstoneEvent;
/*     */ import org.bukkit.event.entity.EntityInteractEvent;
/*     */ import org.bukkit.plugin.PluginManager;
/*     */ 
/*     */ public class BlockPressurePlate
/*     */   extends Block {
/*     */   private EnumMobType a;
/*     */   
/*     */   protected BlockPressurePlate(int i, int j, EnumMobType enummobtype, Material material) {
/*  17 */     super(i, j, material);
/*  18 */     this.a = enummobtype;
/*  19 */     a(true);
/*  20 */     float f = 0.0625F;
/*     */     
/*  22 */     a(f, 0.0F, f, 1.0F - f, 0.03125F, 1.0F - f);
/*     */   }
/*     */ 
/*     */   
/*  26 */   public int c() { return 20; }
/*     */ 
/*     */ 
/*     */   
/*  30 */   public AxisAlignedBB e(World world, int i, int j, int k) { return null; }
/*     */ 
/*     */ 
/*     */   
/*  34 */   public boolean a() { return false; }
/*     */ 
/*     */ 
/*     */   
/*  38 */   public boolean b() { return false; }
/*     */ 
/*     */ 
/*     */   
/*  42 */   public boolean canPlace(World world, int i, int j, int k) { return world.e(i, j - 1, k); }
/*     */ 
/*     */   
/*     */   public void c(World world, int i, int j, int k) {}
/*     */   
/*     */   public void doPhysics(World world, int i, int j, int k, int l) {
/*  48 */     boolean flag = false;
/*     */     
/*  50 */     if (!world.e(i, j - 1, k)) {
/*  51 */       flag = true;
/*     */     }
/*     */     
/*  54 */     if (flag) {
/*  55 */       g(world, i, j, k, world.getData(i, j, k));
/*  56 */       world.setTypeId(i, j, k, 0);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void a(World world, int i, int j, int k, Random random) {
/*  61 */     if (!world.isStatic && 
/*  62 */       world.getData(i, j, k) != 0) {
/*  63 */       g(world, i, j, k);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void a(World world, int i, int j, int k, Entity entity) {
/*  69 */     if (!world.isStatic && 
/*  70 */       world.getData(i, j, k) != 1) {
/*  71 */       g(world, i, j, k);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private void g(World world, int i, int j, int k) {
/*  77 */     boolean flag = (world.getData(i, j, k) == 1);
/*  78 */     boolean flag1 = false;
/*  79 */     float f = 0.125F;
/*  80 */     List list = null;
/*     */     
/*  82 */     if (this.a == EnumMobType.EVERYTHING) {
/*  83 */       list = world.b((Entity)null, AxisAlignedBB.b((i + f), j, (k + f), ((i + 1) - f), j + 0.25D, ((k + 1) - f)));
/*     */     }
/*     */     
/*  86 */     if (this.a == EnumMobType.MOBS) {
/*  87 */       list = world.a(EntityLiving.class, AxisAlignedBB.b((i + f), j, (k + f), ((i + 1) - f), j + 0.25D, ((k + 1) - f)));
/*     */     }
/*     */     
/*  90 */     if (this.a == EnumMobType.PLAYERS) {
/*  91 */       list = world.a(EntityHuman.class, AxisAlignedBB.b((i + f), j, (k + f), ((i + 1) - f), j + 0.25D, ((k + 1) - f)));
/*     */     }
/*     */     
/*  94 */     if (list.size() > 0) {
/*  95 */       flag1 = true;
/*     */     }
/*     */ 
/*     */     
/*  99 */     CraftWorld craftWorld = world.getWorld();
/* 100 */     PluginManager manager = world.getServer().getPluginManager();
/*     */     
/* 102 */     if (flag != flag1) {
/* 103 */       if (flag1) {
/* 104 */         for (Object object : list) {
/* 105 */           if (object != null) {
/*     */             EntityInteractEvent entityInteractEvent;
/*     */             
/* 108 */             if (object instanceof EntityHuman) {
/* 109 */               entityInteractEvent = CraftEventFactory.callPlayerInteractEvent((EntityHuman)object, Action.PHYSICAL, i, j, k, -1, null);
/* 110 */             } else if (object instanceof Entity) {
/* 111 */               entityInteractEvent = new EntityInteractEvent(((Entity)object).getBukkitEntity(), craftWorld.getBlockAt(i, j, k));
/* 112 */               manager.callEvent((EntityInteractEvent)entityInteractEvent);
/*     */             } else {
/*     */               continue;
/*     */             } 
/* 116 */             if (entityInteractEvent.isCancelled()) {
/*     */               return;
/*     */             }
/*     */           } 
/*     */         } 
/*     */       }
/*     */       
/* 123 */       BlockRedstoneEvent eventRedstone = new BlockRedstoneEvent(craftWorld.getBlockAt(i, j, k), flag ? 1 : 0, flag1 ? 1 : 0);
/* 124 */       manager.callEvent(eventRedstone);
/*     */       
/* 126 */       flag1 = (eventRedstone.getNewCurrent() > 0);
/*     */     } 
/*     */ 
/*     */     
/* 130 */     if (flag1 && !flag) {
/* 131 */       world.setData(i, j, k, 1);
/* 132 */       world.applyPhysics(i, j, k, this.id);
/* 133 */       world.applyPhysics(i, j - 1, k, this.id);
/* 134 */       world.b(i, j, k, i, j, k);
/* 135 */       world.makeSound(i + 0.5D, j + 0.1D, k + 0.5D, "random.click", 0.3F, 0.6F);
/*     */     } 
/*     */     
/* 138 */     if (!flag1 && flag) {
/* 139 */       world.setData(i, j, k, 0);
/* 140 */       world.applyPhysics(i, j, k, this.id);
/* 141 */       world.applyPhysics(i, j - 1, k, this.id);
/* 142 */       world.b(i, j, k, i, j, k);
/* 143 */       world.makeSound(i + 0.5D, j + 0.1D, k + 0.5D, "random.click", 0.3F, 0.5F);
/*     */     } 
/*     */     
/* 146 */     if (flag1) {
/* 147 */       world.c(i, j, k, this.id, c());
/*     */     }
/*     */   }
/*     */   
/*     */   public void remove(World world, int i, int j, int k) {
/* 152 */     int l = world.getData(i, j, k);
/*     */     
/* 154 */     if (l > 0) {
/* 155 */       world.applyPhysics(i, j, k, this.id);
/* 156 */       world.applyPhysics(i, j - 1, k, this.id);
/*     */     } 
/*     */     
/* 159 */     super.remove(world, i, j, k);
/*     */   }
/*     */   
/*     */   public void a(IBlockAccess iblockaccess, int i, int j, int k) {
/* 163 */     boolean flag = (iblockaccess.getData(i, j, k) == 1);
/* 164 */     float f = 0.0625F;
/*     */     
/* 166 */     if (flag) {
/* 167 */       a(f, 0.0F, f, 1.0F - f, 0.03125F, 1.0F - f);
/*     */     } else {
/* 169 */       a(f, 0.0F, f, 1.0F - f, 0.0625F, 1.0F - f);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/* 174 */   public boolean a(IBlockAccess iblockaccess, int i, int j, int k, int l) { return (iblockaccess.getData(i, j, k) > 0); }
/*     */ 
/*     */ 
/*     */   
/* 178 */   public boolean d(World world, int i, int j, int k, int l) { return (world.getData(i, j, k) == 0) ? false : ((l == 1)); }
/*     */ 
/*     */ 
/*     */   
/* 182 */   public boolean isPowerSource() { return true; }
/*     */ 
/*     */ 
/*     */   
/* 186 */   public int e() { return 1; }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\BlockPressurePlate.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */