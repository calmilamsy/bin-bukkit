/*     */ package net.minecraft.server;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.UUID;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.World;
/*     */ import org.bukkit.craftbukkit.CraftServer;
/*     */ import org.bukkit.craftbukkit.CraftWorld;
/*     */ import org.bukkit.craftbukkit.map.CraftMapView;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WorldMap
/*     */   extends WorldMapBase
/*     */ {
/*     */   public int b;
/*     */   public int c;
/*     */   public byte map;
/*     */   public byte e;
/*  23 */   public byte[] f = new byte[16384];
/*     */   public int g;
/*  25 */   public List h = new ArrayList();
/*  26 */   private Map j = new HashMap();
/*  27 */   public List i = new ArrayList();
/*     */   
/*     */   public final CraftMapView mapView;
/*     */   
/*     */   private CraftServer server;
/*  32 */   private UUID uniqueId = null;
/*     */ 
/*     */   
/*     */   public WorldMap(String s) {
/*  36 */     super(s);
/*     */     
/*  38 */     this.mapView = new CraftMapView(this);
/*  39 */     this.server = (CraftServer)Bukkit.getServer();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void a(NBTTagCompound nbttagcompound) {
/*  45 */     byte dimension = nbttagcompound.c("dimension");
/*     */     
/*  47 */     if (dimension >= 10) {
/*  48 */       long least = nbttagcompound.getLong("UUIDLeast");
/*  49 */       long most = nbttagcompound.getLong("UUIDMost");
/*     */       
/*  51 */       if (least != 0L && most != 0L) {
/*  52 */         this.uniqueId = new UUID(most, least);
/*     */         
/*  54 */         CraftWorld world = (CraftWorld)this.server.getWorld(this.uniqueId);
/*     */         
/*  56 */         if (world == null) {
/*     */ 
/*     */           
/*  59 */           dimension = Byte.MAX_VALUE;
/*     */         } else {
/*  61 */           dimension = (byte)(world.getHandle()).dimension;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/*  66 */     this.map = dimension;
/*     */     
/*  68 */     this.b = nbttagcompound.e("xCenter");
/*  69 */     this.c = nbttagcompound.e("zCenter");
/*  70 */     this.e = nbttagcompound.c("scale");
/*  71 */     if (this.e < 0) {
/*  72 */       this.e = 0;
/*     */     }
/*     */     
/*  75 */     if (this.e > 4) {
/*  76 */       this.e = 4;
/*     */     }
/*     */     
/*  79 */     short short1 = nbttagcompound.d("width");
/*  80 */     short short2 = nbttagcompound.d("height");
/*     */     
/*  82 */     if (short1 == 128 && short2 == 128) {
/*  83 */       this.f = nbttagcompound.j("colors");
/*     */     } else {
/*  85 */       byte[] abyte = nbttagcompound.j("colors");
/*     */       
/*  87 */       this.f = new byte[16384];
/*  88 */       int i = (128 - short1) / 2;
/*  89 */       int j = (128 - short2) / 2;
/*     */       
/*  91 */       for (int k = 0; k < short2; k++) {
/*  92 */         int l = k + j;
/*     */         
/*  94 */         if (l >= 0 || l < 128) {
/*  95 */           for (int i1 = 0; i1 < short1; i1++) {
/*  96 */             int j1 = i1 + i;
/*     */             
/*  98 */             if (j1 >= 0 || j1 < 128) {
/*  99 */               this.f[j1 + l * 128] = abyte[i1 + k * short1];
/*     */             }
/*     */           } 
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void b(NBTTagCompound nbttagcompound) {
/* 109 */     if (this.map >= 10) {
/* 110 */       if (this.uniqueId == null) {
/* 111 */         for (World world : this.server.getWorlds()) {
/* 112 */           CraftWorld cWorld = (CraftWorld)world;
/* 113 */           if ((cWorld.getHandle()).dimension == this.map) {
/* 114 */             this.uniqueId = cWorld.getUID();
/*     */             
/*     */             break;
/*     */           } 
/*     */         } 
/*     */       }
/*     */       
/* 121 */       if (this.uniqueId != null) {
/* 122 */         nbttagcompound.setLong("UUIDLeast", this.uniqueId.getLeastSignificantBits());
/* 123 */         nbttagcompound.setLong("UUIDMost", this.uniqueId.getMostSignificantBits());
/*     */       } 
/*     */     } 
/*     */     
/* 127 */     nbttagcompound.a("dimension", this.map);
/* 128 */     nbttagcompound.a("xCenter", this.b);
/* 129 */     nbttagcompound.a("zCenter", this.c);
/* 130 */     nbttagcompound.a("scale", this.e);
/* 131 */     nbttagcompound.a("width", (short)128);
/* 132 */     nbttagcompound.a("height", (short)128);
/* 133 */     nbttagcompound.a("colors", this.f);
/*     */   }
/*     */   
/*     */   public void a(EntityHuman entityhuman, ItemStack itemstack) {
/* 137 */     if (!this.j.containsKey(entityhuman)) {
/* 138 */       WorldMapHumanTracker worldmaphumantracker = new WorldMapHumanTracker(this, entityhuman);
/*     */       
/* 140 */       this.j.put(entityhuman, worldmaphumantracker);
/* 141 */       this.h.add(worldmaphumantracker);
/*     */     } 
/*     */     
/* 144 */     this.i.clear();
/*     */     
/* 146 */     for (int i = 0; i < this.h.size(); i++) {
/* 147 */       WorldMapHumanTracker worldmaphumantracker1 = (WorldMapHumanTracker)this.h.get(i);
/*     */       
/* 149 */       if (!worldmaphumantracker1.trackee.dead && worldmaphumantracker1.trackee.inventory.c(itemstack)) {
/* 150 */         float f = (float)(worldmaphumantracker1.trackee.locX - this.b) / (1 << this.e);
/* 151 */         float f1 = (float)(worldmaphumantracker1.trackee.locZ - this.c) / (1 << this.e);
/* 152 */         byte b0 = 64;
/* 153 */         byte b1 = 64;
/*     */         
/* 155 */         if (f >= -b0 && f1 >= -b1 && f <= b0 && f1 <= b1) {
/* 156 */           byte b2 = 0;
/* 157 */           byte b3 = (byte)(int)((f * 2.0F) + 0.5D);
/* 158 */           byte b4 = (byte)(int)((f1 * 2.0F) + 0.5D);
/*     */           
/* 160 */           byte b5 = (byte)(int)((worldmaphumantracker1.trackee.yaw * 16.0F / 360.0F) + 0.5D);
/*     */           
/* 162 */           if (this.map < 0) {
/* 163 */             int j = this.g / 10;
/*     */             
/* 165 */             b5 = (byte)(j * j * 34187121 + j * 121 >> 15 & 0xF);
/*     */           } 
/*     */           
/* 168 */           if (worldmaphumantracker1.trackee.dimension == this.map) {
/* 169 */             this.i.add(new WorldMapOrienter(this, b2, b3, b4, b5));
/*     */           }
/*     */         } 
/*     */       } else {
/* 173 */         this.j.remove(worldmaphumantracker1.trackee);
/* 174 */         this.h.remove(worldmaphumantracker1);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public byte[] a(ItemStack itemstack, World world, EntityHuman entityhuman) {
/* 180 */     WorldMapHumanTracker worldmaphumantracker = (WorldMapHumanTracker)this.j.get(entityhuman);
/*     */     
/* 182 */     if (worldmaphumantracker == null) {
/* 183 */       return null;
/*     */     }
/* 185 */     return worldmaphumantracker.a(itemstack);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void a(int i, int j, int k) {
/* 192 */     a();
/*     */     
/* 194 */     for (int l = 0; l < this.h.size(); l++) {
/* 195 */       WorldMapHumanTracker worldmaphumantracker = (WorldMapHumanTracker)this.h.get(l);
/*     */       
/* 197 */       if (worldmaphumantracker.b[i] < 0 || worldmaphumantracker.b[i] > j) {
/* 198 */         worldmaphumantracker.b[i] = j;
/*     */       }
/*     */       
/* 201 */       if (worldmaphumantracker.c[i] < 0 || worldmaphumantracker.c[i] < k)
/* 202 */         worldmaphumantracker.c[i] = k; 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\WorldMap.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */