/*     */ package net.minecraft.server;
/*     */ 
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.event.server.MapInitializeEvent;
/*     */ 
/*     */ 
/*     */ public class ItemWorldMap
/*     */   extends ItemWorldMapBase
/*     */ {
/*     */   protected ItemWorldMap(int i) {
/*  11 */     super(i);
/*  12 */     c(1);
/*     */   }
/*     */ 
/*     */   
/*     */   public WorldMap a(ItemStack itemstack, World world) {
/*  17 */     WorldMap worldmap = (WorldMap)world.a(WorldMap.class, "map_" + itemstack.getData());
/*     */     
/*  19 */     if (worldmap == null) {
/*  20 */       itemstack.b(world.b("map"));
/*  21 */       String s = "map_" + itemstack.getData();
/*     */       
/*  23 */       worldmap = new WorldMap(s);
/*  24 */       worldmap.b = world.q().c();
/*  25 */       worldmap.c = world.q().e();
/*  26 */       worldmap.e = 3;
/*  27 */       worldmap.map = (byte)world.worldProvider.dimension;
/*  28 */       worldmap.a();
/*  29 */       world.a(s, worldmap);
/*     */ 
/*     */       
/*  32 */       MapInitializeEvent event = new MapInitializeEvent(worldmap.mapView);
/*  33 */       Bukkit.getServer().getPluginManager().callEvent(event);
/*     */     } 
/*     */ 
/*     */     
/*  37 */     return worldmap;
/*     */   }
/*     */   
/*     */   public void a(World world, Entity entity, WorldMap worldmap) {
/*  41 */     if (((WorldServer)world).dimension == worldmap.map) {
/*  42 */       short short1 = 128;
/*  43 */       short short2 = 128;
/*  44 */       int i = 1 << worldmap.e;
/*  45 */       int j = worldmap.b;
/*  46 */       int k = worldmap.c;
/*  47 */       int l = MathHelper.floor(entity.locX - j) / i + short1 / 2;
/*  48 */       int i1 = MathHelper.floor(entity.locZ - k) / i + short2 / 2;
/*  49 */       int j1 = 128 / i;
/*     */       
/*  51 */       if (world.worldProvider.e) {
/*  52 */         j1 /= 2;
/*     */       }
/*     */       
/*  55 */       worldmap.g++;
/*     */       
/*  57 */       for (int k1 = l - j1 + 1; k1 < l + j1; k1++) {
/*  58 */         if ((k1 & 0xF) == (worldmap.g & 0xF)) {
/*  59 */           int l1 = 255;
/*  60 */           int i2 = 0;
/*  61 */           double d0 = 0.0D;
/*     */           
/*  63 */           for (int j2 = i1 - j1 - 1; j2 < i1 + j1; j2++) {
/*  64 */             if (k1 >= 0 && j2 >= -1 && k1 < short1 && j2 < short2) {
/*  65 */               int k2 = k1 - l;
/*  66 */               int l2 = j2 - i1;
/*  67 */               boolean flag = (k2 * k2 + l2 * l2 > (j1 - 2) * (j1 - 2));
/*  68 */               int i3 = (j / i + k1 - short1 / 2) * i;
/*  69 */               int j3 = (k / i + j2 - short2 / 2) * i;
/*  70 */               byte b0 = 0;
/*  71 */               byte b1 = 0;
/*  72 */               byte b2 = 0;
/*  73 */               int[] aint = new int[256];
/*  74 */               Chunk chunk = world.getChunkAtWorldCoords(i3, j3);
/*  75 */               if (!chunk.isEmpty()) {
/*  76 */                 int k3 = i3 & 0xF;
/*  77 */                 int l3 = j3 & 0xF;
/*  78 */                 int i4 = 0;
/*  79 */                 double d1 = 0.0D;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */                 
/*  85 */                 if (world.worldProvider.e) {
/*  86 */                   int l4 = i3 + j3 * 231871;
/*  87 */                   l4 = l4 * l4 * 31287121 + l4 * 11;
/*  88 */                   if ((l4 >> 20 & true) == 0) {
/*  89 */                     aint[Block.DIRT.id] = aint[Block.DIRT.id] + 10;
/*     */                   } else {
/*  91 */                     aint[Block.STONE.id] = aint[Block.STONE.id] + 10;
/*     */                   } 
/*     */                   
/*  94 */                   d1 = 100.0D;
/*     */                 } else {
/*  96 */                   for (int l4 = 0; l4 < i; l4++) {
/*  97 */                     for (int j4 = 0; j4 < i; j4++) {
/*  98 */                       int k4 = chunk.b(l4 + k3, j4 + l3) + 1;
/*  99 */                       int j5 = 0;
/*     */                       
/* 101 */                       if (k4 > 1) {
/* 102 */                         boolean flag1 = false;
/*     */                         
/*     */                         do {
/* 105 */                           flag1 = true;
/* 106 */                           j5 = chunk.getTypeId(l4 + k3, k4 - 1, j4 + l3);
/* 107 */                           if (j5 == 0) {
/* 108 */                             flag1 = false;
/* 109 */                           } else if (k4 > 0 && j5 > 0 && (Block.byId[j5]).material.C == MaterialMapColor.b) {
/* 110 */                             flag1 = false;
/*     */                           } 
/*     */                           
/* 113 */                           if (flag1)
/* 114 */                             continue;  k4--;
/* 115 */                           if (k4 <= 0)
/* 116 */                             break;  j5 = chunk.getTypeId(l4 + k3, k4 - 1, j4 + l3);
/*     */                         }
/* 118 */                         while (!flag1);
/*     */                         
/* 120 */                         if (j5 != 0 && (Block.byId[j5]).material.isLiquid()) {
/* 121 */                           int k5, i5 = k4 - 1;
/* 122 */                           boolean flag2 = false;
/*     */ 
/*     */ 
/*     */                           
/*     */                           do {
/* 127 */                             k5 = chunk.getTypeId(l4 + k3, i5--, j4 + l3);
/* 128 */                             i4++;
/* 129 */                           } while (i5 > 0 && k5 != 0 && (Block.byId[k5]).material.isLiquid());
/*     */                         } 
/*     */                       } 
/*     */                       
/* 133 */                       d1 += k4 / (i * i);
/* 134 */                       aint[j5] = aint[j5] + 1;
/*     */                     } 
/*     */                   } 
/*     */                 } 
/*     */                 
/* 139 */                 i4 /= i * i;
/* 140 */                 int l5 = b0 / i * i;
/*     */                 
/* 142 */                 l5 = b1 / i * i;
/* 143 */                 l5 = b2 / i * i;
/* 144 */                 int m = 0;
/* 145 */                 int j4 = 0;
/*     */                 
/* 147 */                 for (int k4 = 0; k4 < 256; k4++) {
/* 148 */                   if (aint[k4] > m) {
/* 149 */                     j4 = k4;
/* 150 */                     m = aint[k4];
/*     */                   } 
/*     */                 } 
/*     */                 
/* 154 */                 double d2 = (d1 - d0) * 4.0D / (i + 4) + ((k1 + j2 & true) - 0.5D) * 0.4D;
/* 155 */                 byte b3 = 1;
/*     */                 
/* 157 */                 if (d2 > 0.6D) {
/* 158 */                   b3 = 2;
/*     */                 }
/*     */                 
/* 161 */                 if (d2 < -0.6D) {
/* 162 */                   b3 = 0;
/*     */                 }
/*     */                 
/* 165 */                 int i5 = 0;
/* 166 */                 if (j4 > 0) {
/* 167 */                   MaterialMapColor materialmapcolor = (Block.byId[j4]).material.C;
/*     */                   
/* 169 */                   if (materialmapcolor == MaterialMapColor.n) {
/* 170 */                     d2 = i4 * 0.1D + (k1 + j2 & true) * 0.2D;
/* 171 */                     b3 = 1;
/* 172 */                     if (d2 < 0.5D) {
/* 173 */                       b3 = 2;
/*     */                     }
/*     */                     
/* 176 */                     if (d2 > 0.9D) {
/* 177 */                       b3 = 0;
/*     */                     }
/*     */                   } 
/*     */                   
/* 181 */                   i5 = materialmapcolor.q;
/*     */                 } 
/*     */                 
/* 184 */                 d0 = d1;
/* 185 */                 if (j2 >= 0 && k2 * k2 + l2 * l2 < j1 * j1 && (!flag || (k1 + j2 & true) != 0)) {
/* 186 */                   byte b4 = worldmap.f[k1 + j2 * short1];
/* 187 */                   byte b5 = (byte)(i5 * 4 + b3);
/*     */                   
/* 189 */                   if (b4 != b5) {
/* 190 */                     if (l1 > j2) {
/* 191 */                       l1 = j2;
/*     */                     }
/*     */                     
/* 194 */                     if (i2 < j2) {
/* 195 */                       i2 = j2;
/*     */                     }
/*     */                     
/* 198 */                     worldmap.f[k1 + j2 * short1] = b5;
/*     */                   } 
/*     */                 } 
/*     */               } 
/*     */             } 
/*     */           } 
/* 204 */           if (l1 <= i2) {
/* 205 */             worldmap.a(k1, l1, i2);
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void a(ItemStack itemstack, World world, Entity entity, int i, boolean flag) {
/* 213 */     if (!world.isStatic) {
/* 214 */       WorldMap worldmap = a(itemstack, world);
/*     */       
/* 216 */       if (entity instanceof EntityHuman) {
/* 217 */         EntityHuman entityhuman = (EntityHuman)entity;
/*     */         
/* 219 */         worldmap.a(entityhuman, itemstack);
/*     */       } 
/*     */       
/* 222 */       if (flag) {
/* 223 */         a(world, entity, worldmap);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public void c(ItemStack itemstack, World world, EntityHuman entityhuman) {
/* 229 */     itemstack.b(world.b("map"));
/* 230 */     String s = "map_" + itemstack.getData();
/* 231 */     WorldMap worldmap = new WorldMap(s);
/*     */     
/* 233 */     world.a(s, worldmap);
/* 234 */     worldmap.b = MathHelper.floor(entityhuman.locX);
/* 235 */     worldmap.c = MathHelper.floor(entityhuman.locZ);
/* 236 */     worldmap.e = 3;
/* 237 */     worldmap.map = (byte)((WorldServer)world).dimension;
/* 238 */     worldmap.a();
/*     */   }
/*     */   
/*     */   public Packet b(ItemStack itemstack, World world, EntityHuman entityhuman) {
/* 242 */     byte[] abyte = a(itemstack, world).a(itemstack, world, entityhuman);
/*     */     
/* 244 */     return (abyte == null) ? null : new Packet131((short)Item.MAP.id, (short)itemstack.getData(), abyte);
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\ItemWorldMap.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */