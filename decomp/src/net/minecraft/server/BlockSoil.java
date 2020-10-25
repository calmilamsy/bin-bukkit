/*     */ package net.minecraft.server;
/*     */ 
/*     */ import java.util.Random;
/*     */ import org.bukkit.craftbukkit.event.CraftEventFactory;
/*     */ import org.bukkit.event.block.Action;
/*     */ import org.bukkit.event.entity.EntityInteractEvent;
/*     */ 
/*     */ 
/*     */ public class BlockSoil
/*     */   extends Block
/*     */ {
/*     */   protected BlockSoil(int i) {
/*  13 */     super(i, Material.EARTH);
/*  14 */     this.textureId = 87;
/*  15 */     a(true);
/*  16 */     a(0.0F, 0.0F, 0.0F, 1.0F, 0.9375F, 1.0F);
/*  17 */     f(255);
/*     */   }
/*     */ 
/*     */   
/*  21 */   public AxisAlignedBB e(World world, int i, int j, int k) { return AxisAlignedBB.b((i + 0), (j + 0), (k + 0), (i + 1), (j + 1), (k + 1)); }
/*     */ 
/*     */ 
/*     */   
/*  25 */   public boolean a() { return false; }
/*     */ 
/*     */ 
/*     */   
/*  29 */   public boolean b() { return false; }
/*     */ 
/*     */ 
/*     */   
/*  33 */   public int a(int i, int j) { return (i == 1 && j > 0) ? (this.textureId - 1) : ((i == 1) ? this.textureId : 2); }
/*     */ 
/*     */   
/*     */   public void a(World world, int i, int j, int k, Random random) {
/*  37 */     if (random.nextInt(5) == 0) {
/*  38 */       if (!h(world, i, j, k) && !world.s(i, j + 1, k)) {
/*  39 */         int l = world.getData(i, j, k);
/*     */         
/*  41 */         if (l > 0) {
/*  42 */           world.setData(i, j, k, l - 1);
/*  43 */         } else if (!g(world, i, j, k)) {
/*  44 */           world.setTypeId(i, j, k, Block.DIRT.id);
/*     */         } 
/*     */       } else {
/*  47 */         world.setData(i, j, k, 7);
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   public void b(World world, int i, int j, int k, Entity entity) {
/*  53 */     if (world.random.nextInt(4) == 0) {
/*     */       EntityInteractEvent entityInteractEvent;
/*     */       
/*  56 */       if (entity instanceof EntityHuman) {
/*  57 */         entityInteractEvent = CraftEventFactory.callPlayerInteractEvent((EntityHuman)entity, Action.PHYSICAL, i, j, k, -1, null);
/*     */       } else {
/*  59 */         entityInteractEvent = new EntityInteractEvent(entity.getBukkitEntity(), world.getWorld().getBlockAt(i, j, k));
/*  60 */         world.getServer().getPluginManager().callEvent((EntityInteractEvent)entityInteractEvent);
/*     */       } 
/*     */       
/*  63 */       if (entityInteractEvent.isCancelled()) {
/*     */         return;
/*     */       }
/*     */ 
/*     */       
/*  68 */       world.setTypeId(i, j, k, Block.DIRT.id);
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean g(World world, int i, int j, int k) {
/*  73 */     byte b0 = 0;
/*     */     
/*  75 */     for (int l = i - b0; l <= i + b0; l++) {
/*  76 */       for (int i1 = k - b0; i1 <= k + b0; i1++) {
/*  77 */         if (world.getTypeId(l, j + 1, i1) == Block.CROPS.id) {
/*  78 */           return true;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/*  83 */     return false;
/*     */   }
/*     */   
/*     */   private boolean h(World world, int i, int j, int k) {
/*  87 */     for (int l = i - 4; l <= i + 4; l++) {
/*  88 */       for (int i1 = j; i1 <= j + 1; i1++) {
/*  89 */         for (int j1 = k - 4; j1 <= k + 4; j1++) {
/*  90 */           if (world.getMaterial(l, i1, j1) == Material.WATER) {
/*  91 */             return true;
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/*  97 */     return false;
/*     */   }
/*     */   
/*     */   public void doPhysics(World world, int i, int j, int k, int l) {
/* 101 */     super.doPhysics(world, i, j, k, l);
/* 102 */     Material material = world.getMaterial(i, j + 1, k);
/*     */     
/* 104 */     if (material.isBuildable()) {
/* 105 */       world.setTypeId(i, j, k, Block.DIRT.id);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/* 110 */   public int a(int i, Random random) { return Block.DIRT.a(0, random); }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\BlockSoil.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */