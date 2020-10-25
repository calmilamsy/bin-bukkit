/*     */ package net.minecraft.server;
/*     */ 
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import org.bukkit.event.entity.CreatureSpawnEvent;
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class SpawnerCreature
/*     */ {
/*  13 */   private static Set b = new HashSet();
/*  14 */   protected static final Class[] a = { EntitySpider.class, EntityZombie.class, EntitySkeleton.class };
/*     */ 
/*     */ 
/*     */   
/*     */   protected static ChunkPosition a(World world, int i, int j) {
/*  19 */     int k = i + world.random.nextInt(16);
/*  20 */     int l = world.random.nextInt(128);
/*  21 */     int i1 = j + world.random.nextInt(16);
/*     */     
/*  23 */     return new ChunkPosition(k, l, i1);
/*     */   }
/*     */   
/*     */   public static final int spawnEntities(World world, boolean flag, boolean flag1) {
/*  27 */     if (!flag && !flag1) {
/*  28 */       return 0;
/*     */     }
/*  30 */     b.clear();
/*     */ 
/*     */     
/*     */     int i;
/*     */     
/*  35 */     for (i = 0; i < world.players.size(); i++) {
/*  36 */       EntityHuman entityhuman = (EntityHuman)world.players.get(i);
/*  37 */       int k = MathHelper.floor(entityhuman.locX / 16.0D);
/*     */       
/*  39 */       int j = MathHelper.floor(entityhuman.locZ / 16.0D);
/*  40 */       byte b0 = 8;
/*     */       
/*  42 */       for (int l = -b0; l <= b0; l++) {
/*  43 */         for (int i1 = -b0; i1 <= b0; i1++) {
/*  44 */           b.add(new ChunkCoordIntPair(l + k, i1 + j));
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/*  49 */     i = 0;
/*  50 */     ChunkCoordinates chunkcoordinates = world.getSpawn();
/*  51 */     EnumCreatureType[] aenumcreaturetype = EnumCreatureType.values();
/*     */     
/*  53 */     int j = aenumcreaturetype.length;
/*     */     
/*  55 */     for (int j1 = 0; j1 < j; j1++) {
/*  56 */       EnumCreatureType enumcreaturetype = aenumcreaturetype[j1];
/*     */       
/*  58 */       if ((!enumcreaturetype.d() || flag1) && (enumcreaturetype.d() || flag) && world.a(enumcreaturetype.a()) <= enumcreaturetype.b() * b.size() / 256) {
/*  59 */         Iterator iterator = b.iterator();
/*     */ 
/*     */         
/*  62 */         label81: while (iterator.hasNext()) {
/*  63 */           ChunkCoordIntPair chunkcoordintpair = (ChunkCoordIntPair)iterator.next();
/*  64 */           BiomeBase biomebase = world.getWorldChunkManager().a(chunkcoordintpair);
/*  65 */           List list = biomebase.a(enumcreaturetype);
/*     */           
/*  67 */           if (list != null && !list.isEmpty()) {
/*  68 */             int k1 = 0;
/*     */ 
/*     */ 
/*     */             
/*  72 */             for (iterator1 = list.iterator(); iterator1.hasNext(); k1 += biomemeta.b) {
/*  73 */               BiomeMeta biomemeta = (BiomeMeta)iterator1.next();
/*     */             }
/*     */             
/*  76 */             int l1 = world.random.nextInt(k1);
/*     */             
/*  78 */             BiomeMeta biomemeta = (BiomeMeta)list.get(0);
/*  79 */             Iterator iterator2 = list.iterator();
/*     */             
/*  81 */             while (iterator2.hasNext()) {
/*  82 */               BiomeMeta biomemeta1 = (BiomeMeta)iterator2.next();
/*     */               
/*  84 */               l1 -= biomemeta1.b;
/*  85 */               if (l1 < 0) {
/*  86 */                 biomemeta = biomemeta1;
/*     */                 
/*     */                 break;
/*     */               } 
/*     */             } 
/*  91 */             ChunkPosition chunkposition = a(world, chunkcoordintpair.x * 16, chunkcoordintpair.z * 16);
/*  92 */             int i2 = chunkposition.x;
/*  93 */             int j2 = chunkposition.y;
/*  94 */             int k2 = chunkposition.z;
/*     */             
/*  96 */             if (!world.e(i2, j2, k2) && world.getMaterial(i2, j2, k2) == enumcreaturetype.c()) {
/*  97 */               int l2 = 0;
/*     */               
/*  99 */               for (int i3 = 0; i3 < 3; i3++) {
/* 100 */                 int j3 = i2;
/* 101 */                 int k3 = j2;
/* 102 */                 int l3 = k2;
/* 103 */                 byte b1 = 6;
/*     */                 
/* 105 */                 for (int i4 = 0; i4 < 4; i4++) {
/* 106 */                   j3 += world.random.nextInt(b1) - world.random.nextInt(b1);
/* 107 */                   k3 += world.random.nextInt(1) - world.random.nextInt(1);
/* 108 */                   l3 += world.random.nextInt(b1) - world.random.nextInt(b1);
/* 109 */                   if (a(enumcreaturetype, world, j3, k3, l3)) {
/* 110 */                     float f = j3 + 0.5F;
/* 111 */                     float f1 = k3;
/* 112 */                     float f2 = l3 + 0.5F;
/*     */                     
/* 114 */                     if (world.a(f, f1, f2, 24.0D) == null) {
/* 115 */                       float f3 = f - chunkcoordinates.x;
/* 116 */                       float f4 = f1 - chunkcoordinates.y;
/* 117 */                       float f5 = f2 - chunkcoordinates.z;
/* 118 */                       float f6 = f3 * f3 + f4 * f4 + f5 * f5;
/*     */                       
/* 120 */                       if (f6 >= 576.0F) {
/*     */                         EntityLiving entityliving;
/*     */                         
/*     */                         try {
/* 124 */                           entityliving = (EntityLiving)biomemeta.a.getConstructor(new Class[] { World.class }).newInstance(new Object[] { world });
/* 125 */                         } catch (Exception exception) {
/* 126 */                           exception.printStackTrace();
/* 127 */                           return i;
/*     */                         } 
/*     */                         
/* 130 */                         entityliving.setPositionRotation(f, f1, f2, world.random.nextFloat() * 360.0F, 0.0F);
/* 131 */                         if (entityliving.d()) {
/* 132 */                           l2++;
/*     */                           
/* 134 */                           world.addEntity(entityliving, CreatureSpawnEvent.SpawnReason.NATURAL);
/* 135 */                           a(entityliving, world, f, f1, f2);
/* 136 */                           if (l2 >= entityliving.l()) {
/*     */                             continue label81;
/*     */                           }
/*     */                         } 
/*     */                         
/* 141 */                         i += l2;
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
/*     */     
/* 153 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 158 */   private static boolean a(EnumCreatureType enumcreaturetype, World world, int i, int j, int k) { return (enumcreaturetype.c() == Material.WATER) ? ((world.getMaterial(i, j, k).isLiquid() && !world.e(i, j + 1, k))) : ((world.e(i, j - 1, k) && !world.e(i, j, k) && !world.getMaterial(i, j, k).isLiquid() && !world.e(i, j + 1, k))); }
/*     */ 
/*     */   
/*     */   private static void a(EntityLiving entityliving, World world, float f, float f1, float f2) {
/* 162 */     if (entityliving instanceof EntitySpider && world.random.nextInt(100) == 0) {
/* 163 */       EntitySkeleton entityskeleton = new EntitySkeleton(world);
/*     */       
/* 165 */       entityskeleton.setPositionRotation(f, f1, f2, entityliving.yaw, 0.0F);
/*     */       
/* 167 */       world.addEntity(entityskeleton, CreatureSpawnEvent.SpawnReason.NATURAL);
/* 168 */       entityskeleton.mount(entityliving);
/* 169 */     } else if (entityliving instanceof EntitySheep) {
/* 170 */       ((EntitySheep)entityliving).setColor(EntitySheep.a(world.random));
/*     */     } 
/*     */   }
/*     */   
/*     */   public static boolean a(World world, List list) {
/* 175 */     boolean flag = false;
/* 176 */     Pathfinder pathfinder = new Pathfinder(world);
/* 177 */     Iterator iterator = list.iterator();
/*     */     
/* 179 */     while (iterator.hasNext()) {
/* 180 */       EntityHuman entityhuman = (EntityHuman)iterator.next();
/* 181 */       Class[] aclass = a;
/*     */       
/* 183 */       if (aclass != null && aclass.length != 0) {
/* 184 */         boolean flag1 = false;
/*     */         
/* 186 */         for (int i = 0; i < 20 && !flag1; i++) {
/* 187 */           int j = MathHelper.floor(entityhuman.locX) + world.random.nextInt(32) - world.random.nextInt(32);
/* 188 */           int k = MathHelper.floor(entityhuman.locZ) + world.random.nextInt(32) - world.random.nextInt(32);
/* 189 */           int l = MathHelper.floor(entityhuman.locY) + world.random.nextInt(16) - world.random.nextInt(16);
/*     */           
/* 191 */           if (l < 1) {
/* 192 */             l = 1;
/* 193 */           } else if (l > 128) {
/* 194 */             l = 128;
/*     */           } 
/*     */           
/* 197 */           int i1 = world.random.nextInt(aclass.length);
/*     */           
/*     */           int j1;
/*     */           
/* 201 */           for (j1 = l; j1 > 2 && !world.e(j, j1 - 1, k); j1--);
/*     */ 
/*     */ 
/*     */           
/* 205 */           while (!a(EnumCreatureType.MONSTER, world, j, j1, k) && j1 < l + 16 && j1 < 128) {
/* 206 */             j1++;
/*     */           }
/*     */           
/* 209 */           if (j1 < l + 16 && j1 < 128) {
/* 210 */             EntityLiving entityliving; float f = j + 0.5F;
/* 211 */             float f1 = j1;
/* 212 */             float f2 = k + 0.5F;
/*     */ 
/*     */ 
/*     */             
/*     */             try {
/* 217 */               entityliving = (EntityLiving)aclass[i1].getConstructor(new Class[] { World.class }).newInstance(new Object[] { world });
/* 218 */             } catch (Exception exception) {
/* 219 */               exception.printStackTrace();
/* 220 */               return flag;
/*     */             } 
/*     */             
/* 223 */             entityliving.setPositionRotation(f, f1, f2, world.random.nextFloat() * 360.0F, 0.0F);
/* 224 */             if (entityliving.d()) {
/* 225 */               PathEntity pathentity = pathfinder.a(entityliving, entityhuman, 32.0F);
/*     */               
/* 227 */               if (pathentity != null && pathentity.a > 1) {
/* 228 */                 PathPoint pathpoint = pathentity.c();
/*     */                 
/* 230 */                 if (Math.abs(pathpoint.a - entityhuman.locX) < 1.5D && Math.abs(pathpoint.c - entityhuman.locZ) < 1.5D && Math.abs(pathpoint.b - entityhuman.locY) < 1.5D) {
/* 231 */                   ChunkCoordinates chunkcoordinates = BlockBed.f(world, MathHelper.floor(entityhuman.locX), MathHelper.floor(entityhuman.locY), MathHelper.floor(entityhuman.locZ), 1);
/*     */                   
/* 233 */                   if (chunkcoordinates == null) {
/* 234 */                     chunkcoordinates = new ChunkCoordinates(j, j1 + 1, k);
/*     */                   }
/*     */                   
/* 237 */                   entityliving.setPositionRotation((chunkcoordinates.x + 0.5F), chunkcoordinates.y, (chunkcoordinates.z + 0.5F), 0.0F, 0.0F);
/*     */                   
/* 239 */                   world.addEntity(entityliving, CreatureSpawnEvent.SpawnReason.BED);
/* 240 */                   a(entityliving, world, chunkcoordinates.x + 0.5F, chunkcoordinates.y, chunkcoordinates.z + 0.5F);
/* 241 */                   entityhuman.a(true, false, false);
/* 242 */                   entityliving.Q();
/* 243 */                   flag = true;
/* 244 */                   flag1 = true;
/*     */                 } 
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 253 */     return flag;
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\SpawnerCreature.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */