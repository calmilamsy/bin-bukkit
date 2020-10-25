/*     */ package net.minecraft.server;
/*     */ 
/*     */ import java.util.Random;
/*     */ import org.bukkit.block.Block;
/*     */ import org.bukkit.craftbukkit.inventory.CraftItemStack;
/*     */ import org.bukkit.event.block.BlockDispenseEvent;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ import org.bukkit.util.Vector;
/*     */ 
/*     */ public class BlockDispenser
/*     */   extends BlockContainer
/*     */ {
/*  13 */   private Random a = new Random();
/*     */   
/*     */   protected BlockDispenser(int i) {
/*  16 */     super(i, Material.STONE);
/*  17 */     this.textureId = 45;
/*     */   }
/*     */ 
/*     */   
/*  21 */   public int c() { return 4; }
/*     */ 
/*     */ 
/*     */   
/*  25 */   public int a(int i, Random random) { return Block.DISPENSER.id; }
/*     */ 
/*     */   
/*     */   public void c(World world, int i, int j, int k) {
/*  29 */     super.c(world, i, j, k);
/*  30 */     g(world, i, j, k);
/*     */   }
/*     */   
/*     */   private void g(World world, int i, int j, int k) {
/*  34 */     if (!world.isStatic) {
/*  35 */       int l = world.getTypeId(i, j, k - 1);
/*  36 */       int i1 = world.getTypeId(i, j, k + 1);
/*  37 */       int j1 = world.getTypeId(i - 1, j, k);
/*  38 */       int k1 = world.getTypeId(i + 1, j, k);
/*  39 */       byte b0 = 3;
/*     */       
/*  41 */       if (Block.o[l] && !Block.o[i1]) {
/*  42 */         b0 = 3;
/*     */       }
/*     */       
/*  45 */       if (Block.o[i1] && !Block.o[l]) {
/*  46 */         b0 = 2;
/*     */       }
/*     */       
/*  49 */       if (Block.o[j1] && !Block.o[k1]) {
/*  50 */         b0 = 5;
/*     */       }
/*     */       
/*  53 */       if (Block.o[k1] && !Block.o[j1]) {
/*  54 */         b0 = 4;
/*     */       }
/*     */       
/*  57 */       world.setData(i, j, k, b0);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*  62 */   public int a(int i) { return (i == 1) ? (this.textureId + 17) : ((i == 0) ? (this.textureId + 17) : ((i == 3) ? (this.textureId + 1) : this.textureId)); }
/*     */ 
/*     */   
/*     */   public boolean interact(World world, int i, int j, int k, EntityHuman entityhuman) {
/*  66 */     if (world.isStatic) {
/*  67 */       return true;
/*     */     }
/*  69 */     TileEntityDispenser tileentitydispenser = (TileEntityDispenser)world.getTileEntity(i, j, k);
/*     */     
/*  71 */     entityhuman.a(tileentitydispenser);
/*  72 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void dispense(World world, int i, int j, int k, Random random) {
/*  78 */     int l = world.getData(i, j, k);
/*  79 */     byte b0 = 0;
/*  80 */     byte b1 = 0;
/*     */     
/*  82 */     if (l == 3) {
/*  83 */       b1 = 1;
/*  84 */     } else if (l == 2) {
/*  85 */       b1 = -1;
/*  86 */     } else if (l == 5) {
/*  87 */       b0 = 1;
/*     */     } else {
/*  89 */       b0 = -1;
/*     */     } 
/*     */     
/*  92 */     TileEntityDispenser tileentitydispenser = (TileEntityDispenser)world.getTileEntity(i, j, k);
/*     */     
/*  94 */     int dispenseSlot = tileentitydispenser.findDispenseSlot();
/*  95 */     ItemStack itemstack = null;
/*  96 */     if (dispenseSlot > -1) {
/*  97 */       itemstack = tileentitydispenser.getContents()[dispenseSlot];
/*     */ 
/*     */       
/* 100 */       itemstack = new ItemStack(itemstack.id, true, itemstack.damage);
/*     */     } 
/*     */ 
/*     */     
/* 104 */     double d0 = i + b0 * 0.6D + 0.5D;
/* 105 */     double d1 = j + 0.5D;
/* 106 */     double d2 = k + b1 * 0.6D + 0.5D;
/*     */     
/* 108 */     if (itemstack == null) {
/* 109 */       world.e(1001, i, j, k, 0);
/*     */     } else {
/*     */       
/* 112 */       double d3 = random.nextDouble() * 0.1D + 0.2D;
/* 113 */       double motX = b0 * d3;
/* 114 */       double motY = 0.20000000298023224D;
/* 115 */       double motZ = b1 * d3;
/* 116 */       motX += random.nextGaussian() * 0.007499999832361937D * 6.0D;
/* 117 */       motY += random.nextGaussian() * 0.007499999832361937D * 6.0D;
/* 118 */       motZ += random.nextGaussian() * 0.007499999832361937D * 6.0D;
/*     */       
/* 120 */       Block block = world.getWorld().getBlockAt(i, j, k);
/* 121 */       ItemStack bukkitItem = (new CraftItemStack(itemstack)).clone();
/*     */       
/* 123 */       BlockDispenseEvent event = new BlockDispenseEvent(block, bukkitItem, new Vector(motX, motY, motZ));
/* 124 */       world.getServer().getPluginManager().callEvent(event);
/*     */       
/* 126 */       if (event.isCancelled()) {
/*     */         return;
/*     */       }
/*     */ 
/*     */       
/* 131 */       tileentitydispenser.splitStack(dispenseSlot, 1);
/*     */       
/* 133 */       motX = event.getVelocity().getX();
/* 134 */       motY = event.getVelocity().getY();
/* 135 */       motZ = event.getVelocity().getZ();
/*     */       
/* 137 */       itemstack = new ItemStack(event.getItem().getTypeId(), event.getItem().getAmount(), event.getItem().getDurability());
/*     */ 
/*     */       
/* 140 */       if (itemstack.id == Item.ARROW.id) {
/* 141 */         EntityArrow entityarrow = new EntityArrow(world, d0, d1, d2);
/*     */         
/* 143 */         entityarrow.a(b0, 0.10000000149011612D, b1, 1.1F, 6.0F);
/* 144 */         entityarrow.fromPlayer = true;
/* 145 */         world.addEntity(entityarrow);
/* 146 */         world.e(1002, i, j, k, 0);
/* 147 */       } else if (itemstack.id == Item.EGG.id) {
/* 148 */         EntityEgg entityegg = new EntityEgg(world, d0, d1, d2);
/*     */         
/* 150 */         entityegg.a(b0, 0.10000000149011612D, b1, 1.1F, 6.0F);
/* 151 */         world.addEntity(entityegg);
/* 152 */         world.e(1002, i, j, k, 0);
/* 153 */       } else if (itemstack.id == Item.SNOW_BALL.id) {
/* 154 */         EntitySnowball entitysnowball = new EntitySnowball(world, d0, d1, d2);
/*     */         
/* 156 */         entitysnowball.a(b0, 0.10000000149011612D, b1, 1.1F, 6.0F);
/* 157 */         world.addEntity(entitysnowball);
/* 158 */         world.e(1002, i, j, k, 0);
/*     */       } else {
/* 160 */         EntityItem entityitem = new EntityItem(world, d0, d1 - 0.3D, d2, itemstack);
/*     */ 
/*     */         
/* 163 */         entityitem.motX = motX;
/* 164 */         entityitem.motY = motY;
/* 165 */         entityitem.motZ = motZ;
/*     */         
/* 167 */         world.addEntity(entityitem);
/* 168 */         world.e(1000, i, j, k, 0);
/*     */       } 
/*     */       
/* 171 */       world.e(2000, i, j, k, b0 + 1 + (b1 + 1) * 3);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void doPhysics(World world, int i, int j, int k, int l) {
/* 176 */     if (l > 0 && Block.byId[l].isPowerSource()) {
/* 177 */       boolean flag = (world.isBlockIndirectlyPowered(i, j, k) || world.isBlockIndirectlyPowered(i, j + 1, k));
/*     */       
/* 179 */       if (flag) {
/* 180 */         world.c(i, j, k, this.id, c());
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public void a(World world, int i, int j, int k, Random random) {
/* 186 */     if (world.isBlockIndirectlyPowered(i, j, k) || world.isBlockIndirectlyPowered(i, j + 1, k)) {
/* 187 */       dispense(world, i, j, k, random);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/* 192 */   protected TileEntity a_() { return new TileEntityDispenser(); }
/*     */ 
/*     */   
/*     */   public void postPlace(World world, int i, int j, int k, EntityLiving entityliving) {
/* 196 */     int l = MathHelper.floor((entityliving.yaw * 4.0F / 360.0F) + 0.5D) & 0x3;
/*     */     
/* 198 */     if (l == 0) {
/* 199 */       world.setData(i, j, k, 2);
/*     */     }
/*     */     
/* 202 */     if (l == 1) {
/* 203 */       world.setData(i, j, k, 5);
/*     */     }
/*     */     
/* 206 */     if (l == 2) {
/* 207 */       world.setData(i, j, k, 3);
/*     */     }
/*     */     
/* 210 */     if (l == 3) {
/* 211 */       world.setData(i, j, k, 4);
/*     */     }
/*     */   }
/*     */   
/*     */   public void remove(World world, int i, int j, int k) {
/* 216 */     TileEntityDispenser tileentitydispenser = (TileEntityDispenser)world.getTileEntity(i, j, k);
/*     */     
/* 218 */     for (int l = 0; l < tileentitydispenser.getSize(); l++) {
/* 219 */       ItemStack itemstack = tileentitydispenser.getItem(l);
/*     */       
/* 221 */       if (itemstack != null) {
/* 222 */         float f = this.a.nextFloat() * 0.8F + 0.1F;
/* 223 */         float f1 = this.a.nextFloat() * 0.8F + 0.1F;
/* 224 */         float f2 = this.a.nextFloat() * 0.8F + 0.1F;
/*     */         
/* 226 */         while (itemstack.count > 0) {
/* 227 */           int i1 = this.a.nextInt(21) + 10;
/*     */           
/* 229 */           if (i1 > itemstack.count) {
/* 230 */             i1 = itemstack.count;
/*     */           }
/*     */           
/* 233 */           itemstack.count -= i1;
/* 234 */           EntityItem entityitem = new EntityItem(world, (i + f), (j + f1), (k + f2), new ItemStack(itemstack.id, i1, itemstack.getData()));
/* 235 */           float f3 = 0.05F;
/*     */           
/* 237 */           entityitem.motX = ((float)this.a.nextGaussian() * f3);
/* 238 */           entityitem.motY = ((float)this.a.nextGaussian() * f3 + 0.2F);
/* 239 */           entityitem.motZ = ((float)this.a.nextGaussian() * f3);
/* 240 */           world.addEntity(entityitem);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 245 */     super.remove(world, i, j, k);
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\BlockDispenser.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */