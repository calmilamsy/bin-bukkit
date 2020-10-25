/*     */ package net.minecraft.server;
/*     */ 
/*     */ import java.util.Random;
/*     */ import org.bukkit.block.Block;
/*     */ import org.bukkit.craftbukkit.CraftWorld;
/*     */ import org.bukkit.event.block.BlockRedstoneEvent;
/*     */ 
/*     */ public class BlockDoor extends Block {
/*     */   protected BlockDoor(int i, Material material) {
/*  10 */     super(i, material);
/*  11 */     this.textureId = 97;
/*  12 */     if (material == Material.ORE) {
/*  13 */       this.textureId++;
/*     */     }
/*     */     
/*  16 */     float f = 0.5F;
/*  17 */     float f1 = 1.0F;
/*     */     
/*  19 */     a(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, f1, 0.5F + f);
/*     */   }
/*     */   
/*     */   public int a(int i, int j) {
/*  23 */     if (i != 0 && i != 1) {
/*  24 */       int k = d(j);
/*     */       
/*  26 */       if (((k == 0 || k == 2) ? 1 : 0) ^ ((i <= 3) ? 1 : 0)) {
/*  27 */         return this.textureId;
/*     */       }
/*  29 */       int l = k / 2 + (i & true ^ k);
/*     */       
/*  31 */       l += (j & 0x4) / 4;
/*  32 */       int i1 = this.textureId - (j & 0x8) * 2;
/*     */       
/*  34 */       if ((l & true) != 0) {
/*  35 */         i1 = -i1;
/*     */       }
/*     */       
/*  38 */       return i1;
/*     */     } 
/*     */     
/*  41 */     return this.textureId;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*  46 */   public boolean a() { return false; }
/*     */ 
/*     */ 
/*     */   
/*  50 */   public boolean b() { return false; }
/*     */ 
/*     */   
/*     */   public AxisAlignedBB e(World world, int i, int j, int k) {
/*  54 */     a(world, i, j, k);
/*  55 */     return super.e(world, i, j, k);
/*     */   }
/*     */ 
/*     */   
/*  59 */   public void a(IBlockAccess iblockaccess, int i, int j, int k) { c(d(iblockaccess.getData(i, j, k))); }
/*     */ 
/*     */   
/*     */   public void c(int i) {
/*  63 */     float f = 0.1875F;
/*     */     
/*  65 */     a(0.0F, 0.0F, 0.0F, 1.0F, 2.0F, 1.0F);
/*  66 */     if (i == 0) {
/*  67 */       a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, f);
/*     */     }
/*     */     
/*  70 */     if (i == 1) {
/*  71 */       a(1.0F - f, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
/*     */     }
/*     */     
/*  74 */     if (i == 2) {
/*  75 */       a(0.0F, 0.0F, 1.0F - f, 1.0F, 1.0F, 1.0F);
/*     */     }
/*     */     
/*  78 */     if (i == 3) {
/*  79 */       a(0.0F, 0.0F, 0.0F, f, 1.0F, 1.0F);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*  84 */   public void b(World world, int i, int j, int k, EntityHuman entityhuman) { interact(world, i, j, k, entityhuman); }
/*     */ 
/*     */   
/*     */   public boolean interact(World world, int i, int j, int k, EntityHuman entityhuman) {
/*  88 */     if (this.material == Material.ORE) {
/*  89 */       return true;
/*     */     }
/*  91 */     int l = world.getData(i, j, k);
/*     */     
/*  93 */     if ((l & 0x8) != 0) {
/*  94 */       if (world.getTypeId(i, j - 1, k) == this.id) {
/*  95 */         interact(world, i, j - 1, k, entityhuman);
/*     */       }
/*     */       
/*  98 */       return true;
/*     */     } 
/* 100 */     if (world.getTypeId(i, j + 1, k) == this.id) {
/* 101 */       world.setData(i, j + 1, k, (l ^ 0x4) + 8);
/*     */     }
/*     */     
/* 104 */     world.setData(i, j, k, l ^ 0x4);
/* 105 */     world.b(i, j - 1, k, i, j, k);
/* 106 */     world.a(entityhuman, 1003, i, j, k, 0);
/* 107 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDoor(World world, int i, int j, int k, boolean flag) {
/* 113 */     int l = world.getData(i, j, k);
/*     */     
/* 115 */     if ((l & 0x8) != 0) {
/* 116 */       if (world.getTypeId(i, j - 1, k) == this.id) {
/* 117 */         setDoor(world, i, j - 1, k, flag);
/*     */       }
/*     */     } else {
/* 120 */       boolean flag1 = ((world.getData(i, j, k) & 0x4) > 0);
/*     */       
/* 122 */       if (flag1 != flag) {
/* 123 */         if (world.getTypeId(i, j + 1, k) == this.id) {
/* 124 */           world.setData(i, j + 1, k, (l ^ 0x4) + 8);
/*     */         }
/*     */         
/* 127 */         world.setData(i, j, k, l ^ 0x4);
/* 128 */         world.b(i, j - 1, k, i, j, k);
/* 129 */         world.a((EntityHuman)null, 1003, i, j, k, 0);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void doPhysics(World world, int i, int j, int k, int l) {
/* 135 */     int i1 = world.getData(i, j, k);
/*     */     
/* 137 */     if ((i1 & 0x8) != 0) {
/* 138 */       if (world.getTypeId(i, j - 1, k) != this.id) {
/* 139 */         world.setTypeId(i, j, k, 0);
/*     */       }
/*     */       
/* 142 */       if (l > 0 && Block.byId[l].isPowerSource()) {
/* 143 */         doPhysics(world, i, j - 1, k, l);
/*     */       }
/*     */     } else {
/* 146 */       boolean flag = false;
/*     */       
/* 148 */       if (world.getTypeId(i, j + 1, k) != this.id) {
/* 149 */         world.setTypeId(i, j, k, 0);
/* 150 */         flag = true;
/*     */       } 
/*     */       
/* 153 */       if (!world.e(i, j - 1, k)) {
/* 154 */         world.setTypeId(i, j, k, 0);
/* 155 */         flag = true;
/* 156 */         if (world.getTypeId(i, j + 1, k) == this.id) {
/* 157 */           world.setTypeId(i, j + 1, k, 0);
/*     */         }
/*     */       } 
/*     */       
/* 161 */       if (flag) {
/* 162 */         if (!world.isStatic) {
/* 163 */           g(world, i, j, k, i1);
/*     */         }
/* 165 */       } else if (l > 0 && Block.byId[l].isPowerSource()) {
/*     */         
/* 167 */         CraftWorld craftWorld = world.getWorld();
/* 168 */         Block block = craftWorld.getBlockAt(i, j, k);
/* 169 */         Block blockTop = craftWorld.getBlockAt(i, j + 1, k);
/*     */         
/* 171 */         int power = block.getBlockPower();
/* 172 */         int powerTop = blockTop.getBlockPower();
/* 173 */         if (powerTop > power) power = powerTop; 
/* 174 */         int oldPower = ((world.getData(i, j, k) & 0x4) > 0) ? 15 : 0;
/*     */         
/* 176 */         if (((oldPower == 0) ? 1 : 0) ^ ((power == 0) ? 1 : 0)) {
/* 177 */           BlockRedstoneEvent eventRedstone = new BlockRedstoneEvent(block, oldPower, power);
/* 178 */           world.getServer().getPluginManager().callEvent(eventRedstone);
/*     */           
/* 180 */           setDoor(world, i, j, k, (eventRedstone.getNewCurrent() > 0));
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 188 */   public int a(int i, Random random) { return ((i & 0x8) != 0) ? 0 : ((this.material == Material.ORE) ? Item.IRON_DOOR.id : Item.WOOD_DOOR.id); }
/*     */ 
/*     */   
/*     */   public MovingObjectPosition a(World world, int i, int j, int k, Vec3D vec3d, Vec3D vec3d1) {
/* 192 */     a(world, i, j, k);
/* 193 */     return super.a(world, i, j, k, vec3d, vec3d1);
/*     */   }
/*     */ 
/*     */   
/* 197 */   public int d(int i) { return ((i & 0x4) == 0) ? (i - 1 & 0x3) : (i & 0x3); }
/*     */ 
/*     */ 
/*     */   
/* 201 */   public boolean canPlace(World world, int i, int j, int k) { return (j >= 127) ? false : ((world.e(i, j - 1, k) && super.canPlace(world, i, j, k) && super.canPlace(world, i, j + 1, k))); }
/*     */ 
/*     */ 
/*     */   
/* 205 */   public static boolean e(int i) { return ((i & 0x4) != 0); }
/*     */ 
/*     */ 
/*     */   
/* 209 */   public int e() { return 1; }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\BlockDoor.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */