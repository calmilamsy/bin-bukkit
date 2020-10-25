/*     */ package net.minecraft.server;
/*     */ 
/*     */ import java.util.Collection;
/*     */ import java.util.HashSet;
/*     */ import java.util.Random;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.block.Block;
/*     */ import org.bukkit.craftbukkit.CraftWorld;
/*     */ import org.bukkit.event.world.PortalCreateEvent;
/*     */ 
/*     */ public class PortalTravelAgent {
/*  12 */   private Random a = new Random();
/*     */ 
/*     */ 
/*     */   
/*     */   public void a(World world, Entity entity) {
/*  17 */     if (!b(world, entity)) {
/*  18 */       c(world, entity);
/*  19 */       b(world, entity);
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean b(World world, Entity entity) {
/*  24 */     short short1 = 128;
/*  25 */     double d0 = -1.0D;
/*  26 */     int i = 0;
/*  27 */     int j = 0;
/*  28 */     int k = 0;
/*  29 */     int l = MathHelper.floor(entity.locX);
/*  30 */     int i1 = MathHelper.floor(entity.locZ);
/*     */ 
/*     */ 
/*     */     
/*  34 */     for (int j1 = l - short1; j1 <= l + short1; j1++) {
/*  35 */       double d2 = j1 + 0.5D - entity.locX;
/*     */       
/*  37 */       for (int k1 = i1 - short1; k1 <= i1 + short1; k1++) {
/*  38 */         double d3 = k1 + 0.5D - entity.locZ;
/*     */         
/*  40 */         for (int l1 = 127; l1 >= 0; l1--) {
/*  41 */           if (world.getTypeId(j1, l1, k1) == Block.PORTAL.id) {
/*  42 */             while (world.getTypeId(j1, l1 - 1, k1) == Block.PORTAL.id) {
/*  43 */               l1--;
/*     */             }
/*     */             
/*  46 */             double d1 = l1 + 0.5D - entity.locY;
/*  47 */             double d4 = d2 * d2 + d1 * d1 + d3 * d3;
/*     */             
/*  49 */             if (d0 < 0.0D || d4 < d0) {
/*  50 */               d0 = d4;
/*  51 */               i = j1;
/*  52 */               j = l1;
/*  53 */               k = k1;
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/*  60 */     if (d0 >= 0.0D) {
/*  61 */       double d5 = i + 0.5D;
/*  62 */       double d6 = j + 0.5D;
/*     */       
/*  64 */       double d1 = k + 0.5D;
/*  65 */       if (world.getTypeId(i - 1, j, k) == Block.PORTAL.id) {
/*  66 */         d5 -= 0.5D;
/*     */       }
/*     */       
/*  69 */       if (world.getTypeId(i + 1, j, k) == Block.PORTAL.id) {
/*  70 */         d5 += 0.5D;
/*     */       }
/*     */       
/*  73 */       if (world.getTypeId(i, j, k - 1) == Block.PORTAL.id) {
/*  74 */         d1 -= 0.5D;
/*     */       }
/*     */       
/*  77 */       if (world.getTypeId(i, j, k + 1) == Block.PORTAL.id) {
/*  78 */         d1 += 0.5D;
/*     */       }
/*     */       
/*  81 */       entity.setPositionRotation(d5, d6, d1, entity.yaw, 0.0F);
/*  82 */       entity.motX = entity.motY = entity.motZ = 0.0D;
/*  83 */       return true;
/*     */     } 
/*  85 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean c(World world, Entity entity) {
/*  90 */     byte b0 = 16;
/*  91 */     double d0 = -1.0D;
/*  92 */     int i = MathHelper.floor(entity.locX);
/*  93 */     int j = MathHelper.floor(entity.locY);
/*  94 */     int k = MathHelper.floor(entity.locZ);
/*  95 */     int l = i;
/*  96 */     int i1 = j;
/*  97 */     int j1 = k;
/*  98 */     int k1 = 0;
/*  99 */     int l1 = this.a.nextInt(4);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     int i2;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 117 */     for (i2 = i - b0; i2 <= i + b0; i2++) {
/* 118 */       double d1 = i2 + 0.5D - entity.locX;
/*     */       
/* 120 */       for (int j2 = k - b0; j2 <= k + b0; j2++) {
/* 121 */         double d2 = j2 + 0.5D - entity.locZ;
/*     */         
/*     */         int l2;
/* 124 */         label214: for (l2 = 127; l2 >= 0; l2--) {
/* 125 */           if (world.isEmpty(i2, l2, j2)) {
/* 126 */             while (l2 > 0 && world.isEmpty(i2, l2 - 1, j2)) {
/* 127 */               l2--;
/*     */             }
/*     */             
/* 130 */             for (int k2 = l1; k2 < l1 + 4; k2++) {
/* 131 */               int j3 = k2 % 2;
/* 132 */               int i3 = 1 - j3;
/* 133 */               if (k2 % 4 >= 2) {
/* 134 */                 j3 = -j3;
/* 135 */                 i3 = -i3;
/*     */               } 
/*     */               
/* 138 */               for (int l3 = 0; l3 < 3; l3++) {
/* 139 */                 for (int k3 = 0; k3 < 4; k3++) {
/* 140 */                   for (int j4 = -1; j4 < 4; ) {
/* 141 */                     int i4 = i2 + (k3 - 1) * j3 + l3 * i3;
/* 142 */                     int k4 = l2 + j4;
/* 143 */                     int l4 = j2 + (k3 - 1) * i3 - l3 * j3;
/*     */                     
/* 145 */                     if (j4 >= 0 || world.getMaterial(i4, k4, l4).isBuildable()) { if (j4 >= 0 && !world.isEmpty(i4, k4, l4))
/*     */                         continue label214;  j4++; }
/*     */                     
/*     */                     continue label214;
/*     */                   } 
/*     */                 } 
/*     */               } 
/* 152 */               double d3 = l2 + 0.5D - entity.locY;
/* 153 */               double d4 = d1 * d1 + d3 * d3 + d2 * d2;
/* 154 */               if (d0 < 0.0D || d4 < d0) {
/* 155 */                 d0 = d4;
/* 156 */                 l = i2;
/* 157 */                 i1 = l2;
/* 158 */                 j1 = j2;
/* 159 */                 k1 = k2 % 4;
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 167 */     if (d0 < 0.0D) {
/* 168 */       for (i2 = i - b0; i2 <= i + b0; i2++) {
/* 169 */         double d1 = i2 + 0.5D - entity.locX;
/*     */         
/* 171 */         for (int j2 = k - b0; j2 <= k + b0; j2++) {
/* 172 */           double d2 = j2 + 0.5D - entity.locZ;
/*     */           
/*     */           int l2;
/* 175 */           label210: for (l2 = 127; l2 >= 0; l2--) {
/* 176 */             if (world.isEmpty(i2, l2, j2)) {
/* 177 */               while (world.isEmpty(i2, l2 - 1, j2)) {
/* 178 */                 l2--;
/*     */               }
/*     */               
/* 181 */               for (int k2 = l1; k2 < l1 + 2; k2++) {
/* 182 */                 int j3 = k2 % 2;
/* 183 */                 int i3 = 1 - j3;
/*     */                 
/* 185 */                 for (int l3 = 0; l3 < 4; l3++) {
/* 186 */                   for (int k3 = -1; k3 < 4; ) {
/* 187 */                     int j4 = i2 + (l3 - 1) * j3;
/* 188 */                     int i4 = l2 + k3;
/* 189 */                     int k4 = j2 + (l3 - 1) * i3;
/* 190 */                     if (k3 >= 0 || world.getMaterial(j4, i4, k4).isBuildable()) { if (k3 >= 0 && !world.isEmpty(j4, i4, k4))
/*     */                         continue label210;  k3++; }
/*     */                     
/*     */                     continue label210;
/*     */                   } 
/*     */                 } 
/* 196 */                 double d3 = l2 + 0.5D - entity.locY;
/* 197 */                 double d4 = d1 * d1 + d3 * d3 + d2 * d2;
/* 198 */                 if (d0 < 0.0D || d4 < d0) {
/* 199 */                   d0 = d4;
/* 200 */                   l = i2;
/* 201 */                   i1 = l2;
/* 202 */                   j1 = j2;
/* 203 */                   k1 = k2 % 2;
/*     */                 } 
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/* 212 */     int i5 = l;
/* 213 */     int j5 = i1;
/*     */     
/* 215 */     int j2 = j1;
/* 216 */     int k5 = k1 % 2;
/* 217 */     int l5 = 1 - k5;
/*     */     
/* 219 */     if (k1 % 4 >= 2) {
/* 220 */       k5 = -k5;
/* 221 */       l5 = -l5;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 227 */     Collection<Block> blocks = new HashSet<Block>();
/*     */     
/* 229 */     CraftWorld craftWorld = world.getWorld();
/*     */     
/* 231 */     if (d0 < 0.0D) {
/* 232 */       if (i1 < 70) {
/* 233 */         i1 = 70;
/*     */       }
/*     */       
/* 236 */       if (i1 > 118) {
/* 237 */         i1 = 118;
/*     */       }
/*     */       
/* 240 */       j5 = i1;
/*     */       
/* 242 */       for (int l2 = -1; l2 <= 1; l2++) {
/* 243 */         for (int k2 = 1; k2 < 3; k2++) {
/* 244 */           for (int j3 = -1; j3 < 3; j3++) {
/* 245 */             int i3 = i5 + (k2 - 1) * k5 + l2 * l5;
/* 246 */             int l3 = j5 + j3;
/* 247 */             int k3 = j2 + (k2 - 1) * l5 - l2 * k5;
/* 248 */             blocks.add(craftWorld.getBlockAt(i3, l3, k3));
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     int l2;
/* 254 */     for (l2 = 0; l2 < 4; l2++) {
/* 255 */       for (int k2 = 0; k2 < 4; k2++) {
/* 256 */         for (int j3 = -1; j3 < 4; j3++) {
/* 257 */           int i3 = i5 + (k2 - 1) * k5;
/* 258 */           int l3 = j5 + j3;
/* 259 */           int k3 = j2 + (k2 - 1) * l5;
/* 260 */           blocks.add(craftWorld.getBlockAt(i3, l3, k3));
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 265 */     PortalCreateEvent event = new PortalCreateEvent(blocks, craftWorld);
/* 266 */     Bukkit.getServer().getPluginManager().callEvent(event);
/*     */     
/* 268 */     if (event.isCancelled()) {
/* 269 */       return true;
/*     */     }
/*     */ 
/*     */     
/* 273 */     if (d0 < 0.0D) {
/* 274 */       if (i1 < 70) {
/* 275 */         i1 = 70;
/*     */       }
/*     */       
/* 278 */       if (i1 > 118) {
/* 279 */         i1 = 118;
/*     */       }
/*     */       
/* 282 */       j5 = i1;
/*     */       
/* 284 */       for (l2 = -1; l2 <= 1; l2++) {
/* 285 */         for (int k2 = 1; k2 < 3; k2++) {
/* 286 */           for (int j3 = -1; j3 < 3; j3++) {
/* 287 */             int i3 = i5 + (k2 - 1) * k5 + l2 * l5;
/* 288 */             int l3 = j5 + j3;
/* 289 */             int k3 = j2 + (k2 - 1) * l5 - l2 * k5;
/* 290 */             boolean flag = (j3 < 0);
/* 291 */             world.setTypeId(i3, l3, k3, flag ? Block.OBSIDIAN.id : 0);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 297 */     for (l2 = 0; l2 < 4; l2++) {
/* 298 */       world.suppressPhysics = true;
/*     */       int k2;
/* 300 */       for (k2 = 0; k2 < 4; k2++) {
/* 301 */         for (int j3 = -1; j3 < 4; j3++) {
/* 302 */           int i3 = i5 + (k2 - 1) * k5;
/* 303 */           int l3 = j5 + j3;
/* 304 */           int k3 = j2 + (k2 - 1) * l5;
/* 305 */           boolean flag = (k2 == 0 || k2 == 3 || j3 == -1 || j3 == 3);
/* 306 */           world.setTypeId(i3, l3, k3, flag ? Block.OBSIDIAN.id : Block.PORTAL.id);
/*     */         } 
/*     */       } 
/*     */       
/* 310 */       world.suppressPhysics = false;
/*     */       
/* 312 */       for (k2 = 0; k2 < 4; k2++) {
/* 313 */         for (int j3 = -1; j3 < 4; j3++) {
/* 314 */           int i3 = i5 + (k2 - 1) * k5;
/* 315 */           int l3 = j5 + j3;
/* 316 */           int k3 = j2 + (k2 - 1) * l5;
/* 317 */           world.applyPhysics(i3, l3, k3, world.getTypeId(i3, l3, k3));
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 322 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\PortalTravelAgent.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */