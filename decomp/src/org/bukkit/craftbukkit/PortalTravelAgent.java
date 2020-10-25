/*     */ package org.bukkit.craftbukkit;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Random;
/*     */ import net.minecraft.server.Block;
/*     */ import net.minecraft.server.WorldServer;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.Location;
/*     */ import org.bukkit.TravelAgent;
/*     */ import org.bukkit.block.Block;
/*     */ import org.bukkit.event.world.PortalCreateEvent;
/*     */ 
/*     */ public class PortalTravelAgent
/*     */   implements TravelAgent {
/*  15 */   private Random random = new Random();
/*     */   
/*  17 */   private int searchRadius = 128;
/*  18 */   private int creationRadius = 14;
/*     */   
/*     */   private boolean canCreatePortal = true;
/*     */ 
/*     */   
/*     */   public Location findOrCreate(Location location) {
/*  24 */     WorldServer worldServer = ((CraftWorld)location.getWorld()).getHandle();
/*  25 */     worldServer.chunkProviderServer.forceChunkLoad = true;
/*     */     
/*  27 */     Location resultLocation = findPortal(location);
/*     */     
/*  29 */     if (resultLocation == null)
/*     */     {
/*  31 */       if (this.canCreatePortal && createPortal(location)) {
/*     */         
/*  33 */         resultLocation = findPortal(location);
/*     */       } else {
/*     */         
/*  36 */         resultLocation = location;
/*     */       } 
/*     */     }
/*  39 */     worldServer.chunkProviderServer.forceChunkLoad = false;
/*     */     
/*  41 */     return resultLocation;
/*     */   }
/*     */   
/*     */   public Location findPortal(Location location) {
/*  45 */     WorldServer worldServer = ((CraftWorld)location.getWorld()).getHandle();
/*     */     
/*  47 */     double d0 = -1.0D;
/*  48 */     int i = 0;
/*  49 */     int j = 0;
/*  50 */     int k = 0;
/*  51 */     int l = location.getBlockX();
/*  52 */     int i1 = location.getBlockZ();
/*     */ 
/*     */ 
/*     */     
/*  56 */     for (int j1 = l - this.searchRadius; j1 <= l + this.searchRadius; j1++) {
/*  57 */       double d2 = j1 + 0.5D - location.getX();
/*     */       
/*  59 */       for (int k1 = i1 - this.searchRadius; k1 <= i1 + this.searchRadius; k1++) {
/*  60 */         double d3 = k1 + 0.5D - location.getZ();
/*     */         
/*  62 */         for (int l1 = 127; l1 >= 0; l1--) {
/*  63 */           if (worldServer.getTypeId(j1, l1, k1) == Block.PORTAL.id) {
/*  64 */             while (worldServer.getTypeId(j1, l1 - 1, k1) == Block.PORTAL.id) {
/*  65 */               l1--;
/*     */             }
/*     */             
/*  68 */             double d1 = l1 + 0.5D - location.getY();
/*  69 */             double d4 = d2 * d2 + d1 * d1 + d3 * d3;
/*     */             
/*  71 */             if (d0 < 0.0D || d4 < d0) {
/*  72 */               d0 = d4;
/*  73 */               i = j1;
/*  74 */               j = l1;
/*  75 */               k = k1;
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/*  82 */     if (d0 >= 0.0D) {
/*  83 */       double d5 = i + 0.5D;
/*  84 */       double d6 = j + 0.5D;
/*     */       
/*  86 */       double d1 = k + 0.5D;
/*  87 */       if (worldServer.getTypeId(i - 1, j, k) == Block.PORTAL.id) {
/*  88 */         d5 -= 0.5D;
/*     */       }
/*     */       
/*  91 */       if (worldServer.getTypeId(i + 1, j, k) == Block.PORTAL.id) {
/*  92 */         d5 += 0.5D;
/*     */       }
/*     */       
/*  95 */       if (worldServer.getTypeId(i, j, k - 1) == Block.PORTAL.id) {
/*  96 */         d1 -= 0.5D;
/*     */       }
/*     */       
/*  99 */       if (worldServer.getTypeId(i, j, k + 1) == Block.PORTAL.id) {
/* 100 */         d1 += 0.5D;
/*     */       }
/*     */       
/* 103 */       return new Location(location.getWorld(), d5, d6, d1, location.getYaw(), location.getPitch());
/*     */     } 
/* 105 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean createPortal(Location location) {
/* 110 */     WorldServer worldServer = ((CraftWorld)location.getWorld()).getHandle();
/*     */     
/* 112 */     double d0 = -1.0D;
/* 113 */     int i = location.getBlockX();
/* 114 */     int j = location.getBlockY();
/* 115 */     int k = location.getBlockZ();
/* 116 */     int l = i;
/* 117 */     int i1 = j;
/* 118 */     int j1 = k;
/* 119 */     int k1 = 0;
/* 120 */     int l1 = this.random.nextInt(4);
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
/* 138 */     for (i2 = i - this.creationRadius; i2 <= i + this.creationRadius; i2++) {
/* 139 */       double d1 = i2 + 0.5D - location.getX();
/*     */       
/* 141 */       for (int j2 = k - this.creationRadius; j2 <= k + this.creationRadius; j2++) {
/* 142 */         double d2 = j2 + 0.5D - location.getZ();
/*     */         
/*     */         int l2;
/* 145 */         label218: for (l2 = 127; l2 >= 0; l2--) {
/* 146 */           if (worldServer.isEmpty(i2, l2, j2)) {
/* 147 */             while (l2 > 0 && worldServer.isEmpty(i2, l2 - 1, j2)) {
/* 148 */               l2--;
/*     */             }
/*     */             
/* 151 */             for (int k2 = l1; k2 < l1 + 4; k2++) {
/* 152 */               int j3 = k2 % 2;
/* 153 */               int i3 = 1 - j3;
/* 154 */               if (k2 % 4 >= 2) {
/* 155 */                 j3 = -j3;
/* 156 */                 i3 = -i3;
/*     */               } 
/*     */               
/* 159 */               for (int l3 = 0; l3 < 3; l3++) {
/* 160 */                 for (int k3 = 0; k3 < 4; k3++) {
/* 161 */                   for (int j4 = -1; j4 < 5; ) {
/* 162 */                     int i4 = i2 + (k3 - 1) * j3 + l3 * i3;
/* 163 */                     int k4 = l2 + j4;
/* 164 */                     int l4 = j2 + (k3 - 1) * i3 - l3 * j3;
/*     */                     
/* 166 */                     if (j4 >= 0 || worldServer.getMaterial(i4, k4, l4).isBuildable()) { if (j4 >= 0 && !worldServer.isEmpty(i4, k4, l4))
/*     */                         continue label218;  j4++; }
/*     */                     
/*     */                     continue label218;
/*     */                   } 
/*     */                 } 
/*     */               } 
/* 173 */               double d3 = l2 + 0.5D - location.getY();
/* 174 */               double d4 = d1 * d1 + d3 * d3 + d2 * d2;
/* 175 */               if (d0 < 0.0D || d4 < d0) {
/* 176 */                 d0 = d4;
/* 177 */                 l = i2;
/* 178 */                 i1 = l2 + 1;
/* 179 */                 j1 = j2;
/* 180 */                 k1 = k2 % 4;
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 188 */     if (d0 < 0.0D) {
/* 189 */       for (i2 = i - this.creationRadius; i2 <= i + this.creationRadius; i2++) {
/* 190 */         double d1 = i2 + 0.5D - location.getX();
/*     */         
/* 192 */         for (int j2 = k - this.creationRadius; j2 <= k + this.creationRadius; j2++) {
/* 193 */           double d2 = j2 + 0.5D - location.getZ();
/*     */           
/*     */           int l2;
/* 196 */           label215: for (l2 = 127; l2 >= 0; l2--) {
/* 197 */             if (worldServer.isEmpty(i2, l2, j2)) {
/* 198 */               while (l2 > 0 && worldServer.isEmpty(i2, l2 - 1, j2)) {
/* 199 */                 l2--;
/*     */               }
/*     */               
/* 202 */               for (int k2 = l1; k2 < l1 + 2; k2++) {
/* 203 */                 int j3 = k2 % 2;
/* 204 */                 int i3 = 1 - j3;
/*     */                 
/* 206 */                 for (int l3 = 0; l3 < 4; l3++) {
/* 207 */                   for (int k3 = -1; k3 < 5; ) {
/* 208 */                     int j4 = i2 + (l3 - 1) * j3;
/* 209 */                     int i4 = l2 + k3;
/* 210 */                     int k4 = j2 + (l3 - 1) * i3;
/* 211 */                     if (k3 >= 0 || worldServer.getMaterial(j4, i4, k4).isBuildable()) { if (k3 >= 0 && !worldServer.isEmpty(j4, i4, k4))
/*     */                         continue label215;  k3++; }
/*     */                     
/*     */                     continue label215;
/*     */                   } 
/*     */                 } 
/* 217 */                 double d3 = l2 + 0.5D - location.getY();
/* 218 */                 double d4 = d1 * d1 + d3 * d3 + d2 * d2;
/* 219 */                 if (d0 < 0.0D || d4 < d0) {
/* 220 */                   d0 = d4;
/* 221 */                   l = i2;
/* 222 */                   i1 = l2 + 1;
/* 223 */                   j1 = j2;
/* 224 */                   k1 = k2 % 2;
/*     */                 } 
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/* 233 */     int i5 = l;
/* 234 */     int j5 = i1;
/*     */     
/* 236 */     int j2 = j1;
/* 237 */     int k5 = k1 % 2;
/* 238 */     int l5 = 1 - k5;
/*     */     
/* 240 */     if (k1 % 4 >= 2) {
/* 241 */       k5 = -k5;
/* 242 */       l5 = -l5;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 248 */     ArrayList<Block> blocks = new ArrayList<Block>();
/*     */     
/* 250 */     CraftWorld craftWorld = ((WorldServer)worldServer).getWorld();
/*     */     
/* 252 */     if (d0 < 0.0D) {
/* 253 */       if (i1 < 70) {
/* 254 */         i1 = 70;
/*     */       }
/*     */       
/* 257 */       if (i1 > 118) {
/* 258 */         i1 = 118;
/*     */       }
/*     */       
/* 261 */       j5 = i1;
/*     */       
/* 263 */       for (int l2 = -1; l2 <= 1; l2++) {
/* 264 */         for (int k2 = 1; k2 < 3; k2++) {
/* 265 */           for (int j3 = -1; j3 < 3; j3++) {
/* 266 */             int i3 = i5 + (k2 - 1) * k5 + l2 * l5;
/* 267 */             int l3 = j5 + j3;
/* 268 */             int k3 = j2 + (k2 - 1) * l5 - l2 * k5;
/* 269 */             Block b = craftWorld.getBlockAt(i3, l3, k3);
/* 270 */             if (!blocks.contains(b)) {
/* 271 */               blocks.add(b);
/*     */             }
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     int l2;
/* 278 */     for (l2 = 0; l2 < 4; l2++) {
/* 279 */       for (int k2 = 0; k2 < 4; k2++) {
/* 280 */         for (int j3 = -1; j3 < 4; j3++) {
/* 281 */           int i3 = i5 + (k2 - 1) * k5;
/* 282 */           int l3 = j5 + j3;
/* 283 */           int k3 = j2 + (k2 - 1) * l5;
/* 284 */           Block b = craftWorld.getBlockAt(i3, l3, k3);
/* 285 */           if (!blocks.contains(b)) {
/* 286 */             blocks.add(b);
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 292 */     PortalCreateEvent event = new PortalCreateEvent(blocks, craftWorld);
/* 293 */     Bukkit.getServer().getPluginManager().callEvent(event);
/* 294 */     if (event.isCancelled()) {
/* 295 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 299 */     if (d0 < 0.0D) {
/* 300 */       if (i1 < 70) {
/* 301 */         i1 = 70;
/*     */       }
/*     */       
/* 304 */       if (i1 > 118) {
/* 305 */         i1 = 118;
/*     */       }
/*     */       
/* 308 */       j5 = i1;
/*     */       
/* 310 */       for (l2 = -1; l2 <= 1; l2++) {
/* 311 */         for (int k2 = 1; k2 < 3; k2++) {
/* 312 */           for (int j3 = -1; j3 < 3; j3++) {
/* 313 */             int i3 = i5 + (k2 - 1) * k5 + l2 * l5;
/* 314 */             int l3 = j5 + j3;
/* 315 */             int k3 = j2 + (k2 - 1) * l5 - l2 * k5;
/* 316 */             boolean flag = (j3 < 0);
/* 317 */             worldServer.setTypeId(i3, l3, k3, flag ? Block.OBSIDIAN.id : 0);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 323 */     for (l2 = 0; l2 < 4; l2++) {
/* 324 */       worldServer.suppressPhysics = true;
/*     */       int k2;
/* 326 */       for (k2 = 0; k2 < 4; k2++) {
/* 327 */         for (int j3 = -1; j3 < 4; j3++) {
/* 328 */           int i3 = i5 + (k2 - 1) * k5;
/* 329 */           int l3 = j5 + j3;
/* 330 */           int k3 = j2 + (k2 - 1) * l5;
/* 331 */           boolean flag = (k2 == 0 || k2 == 3 || j3 == -1 || j3 == 3);
/* 332 */           worldServer.setTypeId(i3, l3, k3, flag ? Block.OBSIDIAN.id : Block.PORTAL.id);
/*     */         } 
/*     */       } 
/*     */       
/* 336 */       worldServer.suppressPhysics = false;
/*     */       
/* 338 */       for (k2 = 0; k2 < 4; k2++) {
/* 339 */         for (int j3 = -1; j3 < 4; j3++) {
/* 340 */           int i3 = i5 + (k2 - 1) * k5;
/* 341 */           int l3 = j5 + j3;
/* 342 */           int k3 = j2 + (k2 - 1) * l5;
/* 343 */           worldServer.applyPhysics(i3, l3, k3, worldServer.getTypeId(i3, l3, k3));
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 348 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TravelAgent setSearchRadius(int radius) {
/* 355 */     this.searchRadius = radius;
/* 356 */     return this;
/*     */   }
/*     */ 
/*     */   
/* 360 */   public int getSearchRadius() { return this.searchRadius; }
/*     */ 
/*     */   
/*     */   public TravelAgent setCreationRadius(int radius) {
/* 364 */     this.creationRadius = (radius < 2) ? 0 : (radius - 2);
/* 365 */     return this;
/*     */   }
/*     */ 
/*     */   
/* 369 */   public int getCreationRadius() { return this.creationRadius; }
/*     */ 
/*     */ 
/*     */   
/* 373 */   public boolean getCanCreatePortal() { return this.canCreatePortal; }
/*     */ 
/*     */ 
/*     */   
/* 377 */   public void setCanCreatePortal(boolean create) { this.canCreatePortal = create; }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\craftbukkit\PortalTravelAgent.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */