/*     */ package net.minecraft.server;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import org.bukkit.block.Block;
/*     */ import org.bukkit.craftbukkit.CraftServer;
/*     */ import org.bukkit.craftbukkit.CraftWorld;
/*     */ import org.bukkit.entity.Entity;
/*     */ import org.bukkit.event.entity.EntityDamageByBlockEvent;
/*     */ import org.bukkit.event.entity.EntityDamageByEntityEvent;
/*     */ import org.bukkit.event.entity.EntityExplodeEvent;
/*     */ 
/*     */ public class Explosion {
/*     */   public boolean a;
/*     */   private Random h;
/*     */   private World world;
/*     */   public double posX;
/*     */   public double posY;
/*     */   
/*     */   public Explosion(World world, Entity entity, double d0, double d1, double d2, float f) {
/*  20 */     this.a = false;
/*  21 */     this.h = new Random();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  28 */     this.blocks = new HashSet();
/*     */     
/*  30 */     this.wasCanceled = false;
/*     */ 
/*     */     
/*  33 */     this.world = world;
/*  34 */     this.source = entity;
/*  35 */     this.size = f;
/*  36 */     this.posX = d0;
/*  37 */     this.posY = d1;
/*  38 */     this.posZ = d2;
/*     */   }
/*     */   public double posZ; public Entity source; public float size; public Set blocks; public boolean wasCanceled;
/*     */   public void a() {
/*  42 */     float f = this.size;
/*  43 */     byte b0 = 16;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     int i;
/*     */ 
/*     */ 
/*     */     
/*  52 */     for (i = 0; i < b0; i++) {
/*  53 */       for (int j = 0; j < b0; j++) {
/*  54 */         for (int k = 0; k < b0; k++) {
/*  55 */           if (i == 0 || i == b0 - 1 || j == 0 || j == b0 - 1 || k == 0 || k == b0 - 1) {
/*  56 */             double d3 = (i / (b0 - 1.0F) * 2.0F - 1.0F);
/*  57 */             double d4 = (j / (b0 - 1.0F) * 2.0F - 1.0F);
/*  58 */             double d5 = (k / (b0 - 1.0F) * 2.0F - 1.0F);
/*  59 */             double d6 = Math.sqrt(d3 * d3 + d4 * d4 + d5 * d5);
/*     */             
/*  61 */             d3 /= d6;
/*  62 */             d4 /= d6;
/*  63 */             d5 /= d6;
/*  64 */             float f1 = this.size * (0.7F + this.world.random.nextFloat() * 0.6F);
/*     */             
/*  66 */             double d0 = this.posX;
/*  67 */             double d1 = this.posY;
/*  68 */             double d2 = this.posZ;
/*     */             
/*  70 */             for (float f2 = 0.3F; f1 > 0.0F; f1 -= f2 * 0.75F) {
/*  71 */               int l = MathHelper.floor(d0);
/*  72 */               int i1 = MathHelper.floor(d1);
/*  73 */               int j1 = MathHelper.floor(d2);
/*  74 */               int k1 = this.world.getTypeId(l, i1, j1);
/*     */               
/*  76 */               if (k1 > 0) {
/*  77 */                 f1 -= (Block.byId[k1].a(this.source) + 0.3F) * f2;
/*     */               }
/*     */               
/*  80 */               if (f1 > 0.0F) {
/*  81 */                 this.blocks.add(new ChunkPosition(l, i1, j1));
/*     */               }
/*     */               
/*  84 */               d0 += d3 * f2;
/*  85 */               d1 += d4 * f2;
/*  86 */               d2 += d5 * f2;
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/*  93 */     this.size *= 2.0F;
/*  94 */     i = MathHelper.floor(this.posX - this.size - 1.0D);
/*  95 */     int j = MathHelper.floor(this.posX + this.size + 1.0D);
/*  96 */     int k = MathHelper.floor(this.posY - this.size - 1.0D);
/*  97 */     int l1 = MathHelper.floor(this.posY + this.size + 1.0D);
/*  98 */     int i2 = MathHelper.floor(this.posZ - this.size - 1.0D);
/*  99 */     int j2 = MathHelper.floor(this.posZ + this.size + 1.0D);
/* 100 */     List list = this.world.b(this.source, AxisAlignedBB.b(i, k, i2, j, l1, j2));
/* 101 */     Vec3D vec3d = Vec3D.create(this.posX, this.posY, this.posZ);
/*     */     
/* 103 */     for (k2 = 0; k2 < list.size(); k2++) {
/* 104 */       Entity entity = (Entity)list.get(k2);
/* 105 */       double d7 = entity.f(this.posX, this.posY, this.posZ) / this.size;
/*     */       
/* 107 */       if (d7 <= 1.0D) {
/* 108 */         double d0 = entity.locX - this.posX;
/* 109 */         double d1 = entity.locY - this.posY;
/* 110 */         double d2 = entity.locZ - this.posZ;
/* 111 */         double d8 = MathHelper.a(d0 * d0 + d1 * d1 + d2 * d2);
/*     */         
/* 113 */         d0 /= d8;
/* 114 */         d1 /= d8;
/* 115 */         d2 /= d8;
/* 116 */         double d9 = this.world.a(vec3d, entity.boundingBox);
/* 117 */         double d10 = (1.0D - d7) * d9;
/*     */ 
/*     */         
/* 120 */         CraftServer craftServer = this.world.getServer();
/* 121 */         Entity damagee = (entity == null) ? null : entity.getBukkitEntity();
/* 122 */         int damageDone = (int)((d10 * d10 + d10) / 2.0D * 8.0D * this.size + 1.0D);
/*     */         
/* 124 */         if (damagee != null)
/*     */         {
/* 126 */           if (this.source == null) {
/*     */ 
/*     */             
/* 129 */             EntityDamageByBlockEvent event = new EntityDamageByBlockEvent(null, damagee, EntityDamageEvent.DamageCause.BLOCK_EXPLOSION, damageDone);
/* 130 */             craftServer.getPluginManager().callEvent(event);
/*     */             
/* 132 */             if (!event.isCancelled()) {
/* 133 */               entity.damageEntity(this.source, event.getDamage());
/* 134 */               entity.motX += d0 * d10;
/* 135 */               entity.motY += d1 * d10;
/* 136 */               entity.motZ += d2 * d10;
/*     */             } 
/*     */           } else {
/* 139 */             EntityDamageByEntityEvent event = new EntityDamageByEntityEvent(this.source.getBukkitEntity(), damagee, EntityDamageEvent.DamageCause.ENTITY_EXPLOSION, damageDone);
/* 140 */             craftServer.getPluginManager().callEvent(event);
/*     */             
/* 142 */             if (!event.isCancelled()) {
/* 143 */               entity.damageEntity(this.source, event.getDamage());
/*     */               
/* 145 */               entity.motX += d0 * d10;
/* 146 */               entity.motY += d1 * d10;
/* 147 */               entity.motZ += d2 * d10;
/*     */             } 
/*     */           } 
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 154 */     this.size = f;
/* 155 */     ArrayList arraylist = new ArrayList();
/*     */     
/* 157 */     arraylist.addAll(this.blocks);
/* 158 */     if (this.a) {
/* 159 */       for (int l2 = arraylist.size() - 1; l2 >= 0; l2--) {
/* 160 */         ChunkPosition chunkposition = (ChunkPosition)arraylist.get(l2);
/* 161 */         int i3 = chunkposition.x;
/* 162 */         int j3 = chunkposition.y;
/* 163 */         int k3 = chunkposition.z;
/* 164 */         int l3 = this.world.getTypeId(i3, j3, k3);
/* 165 */         int i4 = this.world.getTypeId(i3, j3 - 1, k3);
/*     */         
/* 167 */         if (l3 == 0 && Block.o[i4] && this.h.nextInt(3) == 0) {
/* 168 */           this.world.setTypeId(i3, j3, k3, Block.FIRE.id);
/*     */         }
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   public void a(boolean flag) {
/* 175 */     this.world.makeSound(this.posX, this.posY, this.posZ, "random.explode", 4.0F, (1.0F + (this.world.random.nextFloat() - this.world.random.nextFloat()) * 0.2F) * 0.7F);
/* 176 */     ArrayList arraylist = new ArrayList();
/*     */     
/* 178 */     arraylist.addAll(this.blocks);
/*     */ 
/*     */     
/* 181 */     CraftWorld craftWorld = this.world.getWorld();
/* 182 */     Entity explode = (this.source == null) ? null : this.source.getBukkitEntity();
/* 183 */     Location location = new Location(craftWorld, this.posX, this.posY, this.posZ);
/*     */     
/* 185 */     List<Block> blockList = new ArrayList<Block>();
/* 186 */     for (j = arraylist.size() - 1; j >= 0; j--) {
/* 187 */       ChunkPosition cpos = (ChunkPosition)arraylist.get(j);
/* 188 */       Block block = craftWorld.getBlockAt(cpos.x, cpos.y, cpos.z);
/* 189 */       if (block.getType() != Material.AIR) {
/* 190 */         blockList.add(block);
/*     */       }
/*     */     } 
/*     */     
/* 194 */     EntityExplodeEvent event = new EntityExplodeEvent(explode, location, blockList);
/* 195 */     this.world.getServer().getPluginManager().callEvent(event);
/*     */     
/* 197 */     if (event.isCancelled()) {
/* 198 */       this.wasCanceled = true;
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 203 */     for (int i = arraylist.size() - 1; i >= 0; i--) {
/* 204 */       ChunkPosition chunkposition = (ChunkPosition)arraylist.get(i);
/* 205 */       int j = chunkposition.x;
/* 206 */       int k = chunkposition.y;
/* 207 */       int l = chunkposition.z;
/* 208 */       int i1 = this.world.getTypeId(j, k, l);
/*     */       
/* 210 */       if (flag) {
/* 211 */         double d0 = (j + this.world.random.nextFloat());
/* 212 */         double d1 = (k + this.world.random.nextFloat());
/* 213 */         double d2 = (l + this.world.random.nextFloat());
/* 214 */         double d3 = d0 - this.posX;
/* 215 */         double d4 = d1 - this.posY;
/* 216 */         double d5 = d2 - this.posZ;
/* 217 */         double d6 = MathHelper.a(d3 * d3 + d4 * d4 + d5 * d5);
/*     */         
/* 219 */         d3 /= d6;
/* 220 */         d4 /= d6;
/* 221 */         d5 /= d6;
/* 222 */         double d7 = 0.5D / (d6 / this.size + 0.1D);
/*     */         
/* 224 */         d7 *= (this.world.random.nextFloat() * this.world.random.nextFloat() + 0.3F);
/* 225 */         d3 *= d7;
/* 226 */         d4 *= d7;
/* 227 */         d5 *= d7;
/* 228 */         this.world.a("explode", (d0 + this.posX * 1.0D) / 2.0D, (d1 + this.posY * 1.0D) / 2.0D, (d2 + this.posZ * 1.0D) / 2.0D, d3, d4, d5);
/* 229 */         this.world.a("smoke", d0, d1, d2, d3, d4, d5);
/*     */       } 
/*     */ 
/*     */       
/* 233 */       if (i1 > 0 && i1 != Block.FIRE.id) {
/*     */         
/* 235 */         Block.byId[i1].dropNaturally(this.world, j, k, l, this.world.getData(j, k, l), event.getYield());
/* 236 */         this.world.setTypeId(j, k, l, 0);
/* 237 */         Block.byId[i1].d(this.world, j, k, l);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\Explosion.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */