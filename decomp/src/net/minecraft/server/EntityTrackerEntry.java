/*     */ package net.minecraft.server;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.event.player.PlayerVelocityEvent;
/*     */ import org.bukkit.util.Vector;
/*     */ 
/*     */ public class EntityTrackerEntry {
/*     */   public Entity tracker;
/*     */   public int b;
/*     */   public int c;
/*     */   public int d;
/*     */   public int e;
/*     */   public int f;
/*     */   public int g;
/*     */   public int h;
/*     */   public double i;
/*     */   public double j;
/*     */   
/*     */   public EntityTrackerEntry(Entity entity, int i, int j, boolean flag) {
/*  21 */     this.l = 0;
/*     */ 
/*     */ 
/*     */     
/*  25 */     this.r = false;
/*     */     
/*  27 */     this.t = 0;
/*  28 */     this.m = false;
/*  29 */     this.trackedPlayers = new HashSet();
/*     */ 
/*     */     
/*  32 */     this.tracker = entity;
/*  33 */     this.b = i;
/*  34 */     this.c = j;
/*  35 */     this.isMoving = flag;
/*  36 */     this.d = MathHelper.floor(entity.locX * 32.0D);
/*  37 */     this.e = MathHelper.floor(entity.locY * 32.0D);
/*  38 */     this.f = MathHelper.floor(entity.locZ * 32.0D);
/*  39 */     this.g = MathHelper.d(entity.yaw * 256.0F / 360.0F);
/*  40 */     this.h = MathHelper.d(entity.pitch * 256.0F / 360.0F);
/*     */   }
/*     */   public double k; public int l; private double o; private double p; private double q; private boolean r; private boolean isMoving; private int t; public boolean m; public Set trackedPlayers;
/*     */   
/*  44 */   public boolean equals(Object object) { return (object instanceof EntityTrackerEntry) ? ((((EntityTrackerEntry)object).tracker.id == this.tracker.id)) : false; }
/*     */ 
/*     */ 
/*     */   
/*  48 */   public int hashCode() { return this.tracker.id; }
/*     */ 
/*     */   
/*     */   public void track(List list) {
/*  52 */     this.m = false;
/*  53 */     if (!this.r || this.tracker.e(this.o, this.p, this.q) > 16.0D) {
/*  54 */       this.o = this.tracker.locX;
/*  55 */       this.p = this.tracker.locY;
/*  56 */       this.q = this.tracker.locZ;
/*  57 */       this.r = true;
/*  58 */       this.m = true;
/*  59 */       scanPlayers(list);
/*     */     } 
/*     */     
/*  62 */     this.t++;
/*  63 */     if (++this.l % this.c == 0) {
/*  64 */       int i = MathHelper.floor(this.tracker.locX * 32.0D);
/*  65 */       int j = MathHelper.floor(this.tracker.locY * 32.0D);
/*  66 */       int k = MathHelper.floor(this.tracker.locZ * 32.0D);
/*  67 */       int l = MathHelper.d(this.tracker.yaw * 256.0F / 360.0F);
/*  68 */       int i1 = MathHelper.d(this.tracker.pitch * 256.0F / 360.0F);
/*  69 */       int j1 = i - this.d;
/*  70 */       int k1 = j - this.e;
/*  71 */       int l1 = k - this.f;
/*  72 */       Object object = null;
/*  73 */       boolean flag = (Math.abs(i) >= 8 || Math.abs(j) >= 8 || Math.abs(k) >= 8);
/*  74 */       boolean flag1 = (Math.abs(l - this.g) >= 8 || Math.abs(i1 - this.h) >= 8);
/*     */       
/*  76 */       if (j1 >= -128 && j1 < 128 && k1 >= -128 && k1 < 128 && l1 >= -128 && l1 < 128 && this.t <= 400) {
/*  77 */         if (flag && flag1) {
/*  78 */           object = new Packet33RelEntityMoveLook(this.tracker.id, (byte)j1, (byte)k1, (byte)l1, (byte)l, (byte)i1);
/*  79 */         } else if (flag) {
/*  80 */           object = new Packet31RelEntityMove(this.tracker.id, (byte)j1, (byte)k1, (byte)l1);
/*  81 */         } else if (flag1) {
/*  82 */           object = new Packet32EntityLook(this.tracker.id, (byte)l, (byte)i1);
/*     */         } 
/*     */       } else {
/*  85 */         this.t = 0;
/*  86 */         this.tracker.locX = i / 32.0D;
/*  87 */         this.tracker.locY = j / 32.0D;
/*  88 */         this.tracker.locZ = k / 32.0D;
/*  89 */         object = new Packet34EntityTeleport(this.tracker.id, i, j, k, (byte)l, (byte)i1);
/*     */       } 
/*     */       
/*  92 */       if (this.isMoving) {
/*  93 */         double d0 = this.tracker.motX - this.i;
/*  94 */         double d1 = this.tracker.motY - this.j;
/*  95 */         double d2 = this.tracker.motZ - this.k;
/*  96 */         double d3 = 0.02D;
/*  97 */         double d4 = d0 * d0 + d1 * d1 + d2 * d2;
/*     */         
/*  99 */         if (d4 > d3 * d3 || (d4 > 0.0D && this.tracker.motX == 0.0D && this.tracker.motY == 0.0D && this.tracker.motZ == 0.0D)) {
/* 100 */           this.i = this.tracker.motX;
/* 101 */           this.j = this.tracker.motY;
/* 102 */           this.k = this.tracker.motZ;
/* 103 */           a(new Packet28EntityVelocity(this.tracker.id, this.i, this.j, this.k));
/*     */         } 
/*     */       } 
/*     */       
/* 107 */       if (object != null) {
/* 108 */         a((Packet)object);
/*     */       }
/*     */       
/* 111 */       DataWatcher datawatcher = this.tracker.aa();
/*     */       
/* 113 */       if (datawatcher.a()) {
/* 114 */         b(new Packet40EntityMetadata(this.tracker.id, datawatcher));
/*     */       }
/*     */       
/* 117 */       if (flag) {
/* 118 */         this.d = i;
/* 119 */         this.e = j;
/* 120 */         this.f = k;
/*     */       } 
/*     */       
/* 123 */       if (flag1) {
/* 124 */         this.g = l;
/* 125 */         this.h = i1;
/*     */       } 
/*     */     } 
/*     */     
/* 129 */     if (this.tracker.velocityChanged) {
/*     */       
/* 131 */       boolean cancelled = false;
/*     */       
/* 133 */       if (this.tracker instanceof EntityPlayer) {
/* 134 */         Player player = (Player)this.tracker.getBukkitEntity();
/* 135 */         Vector velocity = player.getVelocity();
/*     */         
/* 137 */         PlayerVelocityEvent event = new PlayerVelocityEvent(player, velocity);
/* 138 */         this.tracker.world.getServer().getPluginManager().callEvent(event);
/*     */         
/* 140 */         if (event.isCancelled()) {
/* 141 */           cancelled = true;
/*     */         }
/* 143 */         else if (!velocity.equals(event.getVelocity())) {
/* 144 */           player.setVelocity(velocity);
/*     */         } 
/*     */       } 
/*     */       
/* 148 */       if (!cancelled) {
/* 149 */         b(new Packet28EntityVelocity(this.tracker));
/*     */       }
/*     */       
/* 152 */       this.tracker.velocityChanged = false;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void a(Packet packet) {
/* 157 */     Iterator iterator = this.trackedPlayers.iterator();
/*     */     
/* 159 */     while (iterator.hasNext()) {
/* 160 */       EntityPlayer entityplayer = (EntityPlayer)iterator.next();
/*     */       
/* 162 */       entityplayer.netServerHandler.sendPacket(packet);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void b(Packet packet) {
/* 167 */     a(packet);
/* 168 */     if (this.tracker instanceof EntityPlayer) {
/* 169 */       ((EntityPlayer)this.tracker).netServerHandler.sendPacket(packet);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/* 174 */   public void a() { a(new Packet29DestroyEntity(this.tracker.id)); }
/*     */ 
/*     */   
/*     */   public void a(EntityPlayer entityplayer) {
/* 178 */     if (this.trackedPlayers.contains(entityplayer)) {
/* 179 */       this.trackedPlayers.remove(entityplayer);
/*     */     }
/*     */   }
/*     */   
/*     */   public void b(EntityPlayer entityplayer) {
/* 184 */     if (entityplayer != this.tracker) {
/* 185 */       double d0 = entityplayer.locX - (this.d / 32);
/* 186 */       double d1 = entityplayer.locZ - (this.f / 32);
/*     */       
/* 188 */       if (d0 >= -this.b && d0 <= this.b && d1 >= -this.b && d1 <= this.b) {
/* 189 */         if (!this.trackedPlayers.contains(entityplayer)) {
/* 190 */           this.trackedPlayers.add(entityplayer);
/* 191 */           entityplayer.netServerHandler.sendPacket(b());
/* 192 */           if (this.isMoving) {
/* 193 */             entityplayer.netServerHandler.sendPacket(new Packet28EntityVelocity(this.tracker.id, this.tracker.motX, this.tracker.motY, this.tracker.motZ));
/*     */           }
/*     */           
/* 196 */           ItemStack[] aitemstack = this.tracker.getEquipment();
/*     */           
/* 198 */           if (aitemstack != null) {
/* 199 */             for (int i = 0; i < aitemstack.length; i++) {
/* 200 */               entityplayer.netServerHandler.sendPacket(new Packet5EntityEquipment(this.tracker.id, i, aitemstack[i]));
/*     */             }
/*     */           }
/*     */           
/* 204 */           if (this.tracker instanceof EntityHuman) {
/* 205 */             EntityHuman entityhuman = (EntityHuman)this.tracker;
/*     */             
/* 207 */             if (entityhuman.isSleeping()) {
/* 208 */               entityplayer.netServerHandler.sendPacket(new Packet17(this.tracker, false, MathHelper.floor(this.tracker.locX), MathHelper.floor(this.tracker.locY), MathHelper.floor(this.tracker.locZ)));
/*     */             }
/*     */           } 
/*     */         } 
/* 212 */       } else if (this.trackedPlayers.contains(entityplayer)) {
/* 213 */         this.trackedPlayers.remove(entityplayer);
/* 214 */         entityplayer.netServerHandler.sendPacket(new Packet29DestroyEntity(this.tracker.id));
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void scanPlayers(List list) {
/* 220 */     for (int i = 0; i < list.size(); i++) {
/* 221 */       b((EntityPlayer)list.get(i));
/*     */     }
/*     */   }
/*     */   
/*     */   private Packet b() {
/* 226 */     if (this.tracker instanceof EntityItem) {
/* 227 */       EntityItem entityitem = (EntityItem)this.tracker;
/* 228 */       Packet21PickupSpawn packet21pickupspawn = new Packet21PickupSpawn(entityitem);
/*     */       
/* 230 */       entityitem.locX = packet21pickupspawn.b / 32.0D;
/* 231 */       entityitem.locY = packet21pickupspawn.c / 32.0D;
/* 232 */       entityitem.locZ = packet21pickupspawn.d / 32.0D;
/* 233 */       return packet21pickupspawn;
/* 234 */     }  if (this.tracker instanceof EntityPlayer) {
/*     */       
/* 236 */       if (((EntityHuman)this.tracker).name.length() > 16) {
/* 237 */         ((EntityHuman)this.tracker).name = ((EntityHuman)this.tracker).name.substring(0, 16);
/*     */       }
/*     */       
/* 240 */       return new Packet20NamedEntitySpawn((EntityHuman)this.tracker);
/*     */     } 
/* 242 */     if (this.tracker instanceof EntityMinecart) {
/* 243 */       EntityMinecart entityminecart = (EntityMinecart)this.tracker;
/*     */       
/* 245 */       if (entityminecart.type == 0) {
/* 246 */         return new Packet23VehicleSpawn(this.tracker, 10);
/*     */       }
/*     */       
/* 249 */       if (entityminecart.type == 1) {
/* 250 */         return new Packet23VehicleSpawn(this.tracker, 11);
/*     */       }
/*     */       
/* 253 */       if (entityminecart.type == 2) {
/* 254 */         return new Packet23VehicleSpawn(this.tracker, 12);
/*     */       }
/*     */     } 
/*     */     
/* 258 */     if (this.tracker instanceof EntityBoat)
/* 259 */       return new Packet23VehicleSpawn(this.tracker, true); 
/* 260 */     if (this.tracker instanceof IAnimal)
/* 261 */       return new Packet24MobSpawn((EntityLiving)this.tracker); 
/* 262 */     if (this.tracker instanceof EntityFish)
/* 263 */       return new Packet23VehicleSpawn(this.tracker, 90); 
/* 264 */     if (this.tracker instanceof EntityArrow) {
/* 265 */       EntityLiving entityliving = ((EntityArrow)this.tracker).shooter;
/*     */       
/* 267 */       return new Packet23VehicleSpawn(this.tracker, 60, (entityliving != null) ? entityliving.id : this.tracker.id);
/* 268 */     }  if (this.tracker instanceof EntitySnowball)
/* 269 */       return new Packet23VehicleSpawn(this.tracker, 61); 
/* 270 */     if (this.tracker instanceof EntityFireball) {
/* 271 */       EntityFireball entityfireball = (EntityFireball)this.tracker;
/*     */       
/* 273 */       int shooter = (((EntityFireball)this.tracker).shooter != null) ? ((EntityFireball)this.tracker).shooter.id : 1;
/* 274 */       Packet23VehicleSpawn packet23vehiclespawn = new Packet23VehicleSpawn(this.tracker, 63, shooter);
/*     */ 
/*     */       
/* 277 */       packet23vehiclespawn.e = (int)(entityfireball.c * 8000.0D);
/* 278 */       packet23vehiclespawn.f = (int)(entityfireball.d * 8000.0D);
/* 279 */       packet23vehiclespawn.g = (int)(entityfireball.e * 8000.0D);
/* 280 */       return packet23vehiclespawn;
/* 281 */     }  if (this.tracker instanceof EntityEgg)
/* 282 */       return new Packet23VehicleSpawn(this.tracker, 62); 
/* 283 */     if (this.tracker instanceof EntityTNTPrimed) {
/* 284 */       return new Packet23VehicleSpawn(this.tracker, 50);
/*     */     }
/* 286 */     if (this.tracker instanceof EntityFallingSand) {
/* 287 */       EntityFallingSand entityfallingsand = (EntityFallingSand)this.tracker;
/*     */       
/* 289 */       if (entityfallingsand.a == Block.SAND.id) {
/* 290 */         return new Packet23VehicleSpawn(this.tracker, 70);
/*     */       }
/*     */       
/* 293 */       if (entityfallingsand.a == Block.GRAVEL.id) {
/* 294 */         return new Packet23VehicleSpawn(this.tracker, 71);
/*     */       }
/*     */     } 
/*     */     
/* 298 */     if (this.tracker instanceof EntityPainting) {
/* 299 */       return new Packet25EntityPainting((EntityPainting)this.tracker);
/*     */     }
/* 301 */     throw new IllegalArgumentException("Don't know how to add " + this.tracker.getClass() + "!");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void c(EntityPlayer entityplayer) {
/* 308 */     if (this.trackedPlayers.contains(entityplayer)) {
/* 309 */       this.trackedPlayers.remove(entityplayer);
/* 310 */       entityplayer.netServerHandler.sendPacket(new Packet29DestroyEntity(this.tracker.id));
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\EntityTrackerEntry.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */