/*     */ package net.minecraft.server;
/*     */ 
/*     */ import java.io.BufferedReader;
/*     */ import java.io.File;
/*     */ import java.io.FileReader;
/*     */ import java.io.FileWriter;
/*     */ import java.io.PrintWriter;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import java.util.logging.Logger;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.Location;
/*     */ import org.bukkit.craftbukkit.CraftServer;
/*     */ import org.bukkit.craftbukkit.CraftWorld;
/*     */ import org.bukkit.craftbukkit.PortalTravelAgent;
/*     */ import org.bukkit.craftbukkit.command.ColouredConsoleSender;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.event.player.PlayerJoinEvent;
/*     */ import org.bukkit.event.player.PlayerLoginEvent;
/*     */ import org.bukkit.event.player.PlayerPortalEvent;
/*     */ import org.bukkit.event.player.PlayerQuitEvent;
/*     */ import org.bukkit.event.player.PlayerRespawnEvent;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ServerConfigurationManager
/*     */ {
/*  31 */   public static Logger a = Logger.getLogger("Minecraft"); public List players; public MinecraftServer server; public int maxPlayers; public Set banByName; public Set banByIP; private Set h; private Set i; public ServerConfigurationManager(MinecraftServer minecraftserver) {
/*  32 */     this.players = new ArrayList();
/*     */ 
/*     */ 
/*     */     
/*  36 */     this.banByName = new HashSet();
/*  37 */     this.banByIP = new HashSet();
/*  38 */     this.h = new HashSet();
/*  39 */     this.i = new HashSet();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  51 */     minecraftserver.server = new CraftServer(minecraftserver, this);
/*  52 */     minecraftserver.console = new ColouredConsoleSender(minecraftserver.server);
/*  53 */     this.cserver = minecraftserver.server;
/*     */ 
/*     */     
/*  56 */     this.server = minecraftserver;
/*  57 */     this.j = minecraftserver.a("banned-players.txt");
/*  58 */     this.k = minecraftserver.a("banned-ips.txt");
/*  59 */     this.l = minecraftserver.a("ops.txt");
/*  60 */     this.m = minecraftserver.a("white-list.txt");
/*  61 */     int i = minecraftserver.propertyManager.getInt("view-distance", 10);
/*     */ 
/*     */     
/*  64 */     this.maxPlayers = minecraftserver.propertyManager.getInt("max-players", 20);
/*  65 */     this.o = minecraftserver.propertyManager.getBoolean("white-list", false);
/*  66 */     g();
/*  67 */     i();
/*  68 */     k();
/*  69 */     m();
/*  70 */     h();
/*  71 */     j();
/*  72 */     l();
/*  73 */     n();
/*     */   }
/*     */   private File j; private File k; private File l; private File m; public PlayerFileData playerFileData; public boolean o; private CraftServer cserver;
/*     */   public void setPlayerFileData(WorldServer[] aworldserver) {
/*  77 */     if (this.playerFileData != null)
/*  78 */       return;  this.playerFileData = aworldserver[0].p().d();
/*     */   }
/*     */ 
/*     */   
/*     */   public void a(EntityPlayer entityplayer) {
/*  83 */     for (WorldServer world : this.server.worlds) {
/*  84 */       if (world.manager.managedPlayers.contains(entityplayer)) {
/*  85 */         world.manager.removePlayer(entityplayer);
/*     */         break;
/*     */       } 
/*     */     } 
/*  89 */     getPlayerManager(entityplayer.dimension).addPlayer(entityplayer);
/*  90 */     WorldServer worldserver = this.server.getWorldServer(entityplayer.dimension);
/*     */     
/*  92 */     worldserver.chunkProviderServer.getChunkAt((int)entityplayer.locX >> 4, (int)entityplayer.locZ >> 4);
/*     */   }
/*     */ 
/*     */   
/*     */   public int a() {
/*  97 */     if (this.server.worlds.size() == 0) {
/*  98 */       return this.server.propertyManager.getInt("view-distance", 10) * 16 - 16;
/*     */     }
/* 100 */     return ((WorldServer)this.server.worlds.get(0)).manager.getFurthestViewableBlock();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 105 */   private PlayerManager getPlayerManager(int i) { return (this.server.getWorldServer(i)).manager; }
/*     */ 
/*     */ 
/*     */   
/* 109 */   public void b(EntityPlayer entityplayer) { this.playerFileData.b(entityplayer); }
/*     */ 
/*     */   
/*     */   public void c(EntityPlayer entityplayer) {
/* 113 */     this.players.add(entityplayer);
/* 114 */     WorldServer worldserver = this.server.getWorldServer(entityplayer.dimension);
/*     */     
/* 116 */     worldserver.chunkProviderServer.getChunkAt((int)entityplayer.locX >> 4, (int)entityplayer.locZ >> 4);
/*     */     
/* 118 */     while (worldserver.getEntities(entityplayer, entityplayer.boundingBox).size() != 0) {
/* 119 */       entityplayer.setPosition(entityplayer.locX, entityplayer.locY + 1.0D, entityplayer.locZ);
/*     */     }
/*     */ 
/*     */     
/* 123 */     PlayerJoinEvent playerJoinEvent = new PlayerJoinEvent(this.cserver.getPlayer(entityplayer), "§e" + entityplayer.name + " joined the game.");
/* 124 */     this.cserver.getPluginManager().callEvent(playerJoinEvent);
/*     */     
/* 126 */     String joinMessage = playerJoinEvent.getJoinMessage();
/*     */     
/* 128 */     if (joinMessage != null) {
/* 129 */       this.server.serverConfigurationManager.sendAll(new Packet3Chat(joinMessage));
/*     */     }
/*     */ 
/*     */     
/* 133 */     worldserver.addEntity(entityplayer);
/* 134 */     getPlayerManager(entityplayer.dimension).addPlayer(entityplayer);
/*     */   }
/*     */ 
/*     */   
/* 138 */   public void d(EntityPlayer entityplayer) { getPlayerManager(entityplayer.dimension).movePlayer(entityplayer); }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String disconnect(EntityPlayer entityplayer) {
/* 144 */     getPlayerManager(entityplayer.dimension).removePlayer(entityplayer);
/* 145 */     PlayerQuitEvent playerQuitEvent = new PlayerQuitEvent(this.cserver.getPlayer(entityplayer), "§e" + entityplayer.name + " left the game.");
/* 146 */     this.cserver.getPluginManager().callEvent(playerQuitEvent);
/*     */ 
/*     */     
/* 149 */     this.playerFileData.a(entityplayer);
/* 150 */     this.server.getWorldServer(entityplayer.dimension).kill(entityplayer);
/* 151 */     this.players.remove(entityplayer);
/* 152 */     getPlayerManager(entityplayer.dimension).removePlayer(entityplayer);
/*     */     
/* 154 */     return playerQuitEvent.getQuitMessage();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EntityPlayer a(NetLoginHandler netloginhandler, String s) {
/* 162 */     EntityPlayer entity = new EntityPlayer(this.server, this.server.getWorldServer(0), s, new ItemInWorldManager(this.server.getWorldServer(0)));
/* 163 */     Player player = (entity == null) ? null : (Player)entity.getBukkitEntity();
/* 164 */     PlayerLoginEvent event = new PlayerLoginEvent(player);
/*     */     
/* 166 */     String s1 = netloginhandler.networkManager.getSocketAddress().toString();
/*     */     
/* 168 */     s1 = s1.substring(s1.indexOf("/") + 1);
/* 169 */     s1 = s1.substring(0, s1.indexOf(":"));
/*     */     
/* 171 */     if (this.banByName.contains(s.trim().toLowerCase())) {
/* 172 */       event.disallow(PlayerLoginEvent.Result.KICK_BANNED, "You are banned from this server!");
/*     */     }
/* 174 */     else if (!isWhitelisted(s)) {
/* 175 */       event.disallow(PlayerLoginEvent.Result.KICK_WHITELIST, "You are not white-listed on this server!");
/* 176 */     } else if (this.banByIP.contains(s1)) {
/* 177 */       event.disallow(PlayerLoginEvent.Result.KICK_BANNED, "Your IP address is banned from this server!");
/* 178 */     } else if (this.players.size() >= this.maxPlayers) {
/* 179 */       event.disallow(PlayerLoginEvent.Result.KICK_FULL, "The server is full!");
/*     */     } else {
/* 181 */       event.disallow(PlayerLoginEvent.Result.ALLOWED, s1);
/*     */     } 
/*     */     
/* 184 */     this.cserver.getPluginManager().callEvent(event);
/* 185 */     if (event.getResult() != PlayerLoginEvent.Result.ALLOWED) {
/* 186 */       netloginhandler.disconnect(event.getKickMessage());
/* 187 */       return null;
/*     */     } 
/*     */     
/* 190 */     for (int i = 0; i < this.players.size(); i++) {
/* 191 */       EntityPlayer entityplayer = (EntityPlayer)this.players.get(i);
/*     */       
/* 193 */       if (entityplayer.name.equalsIgnoreCase(s)) {
/* 194 */         entityplayer.netServerHandler.disconnect("You logged in from another location");
/*     */       }
/*     */     } 
/*     */     
/* 198 */     return entity;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 204 */   public EntityPlayer moveToWorld(EntityPlayer entityplayer, int i) { return moveToWorld(entityplayer, i, null); }
/*     */ 
/*     */   
/*     */   public EntityPlayer moveToWorld(EntityPlayer entityplayer, int i, Location location) {
/* 208 */     this.server.getTracker(entityplayer.dimension).untrackPlayer(entityplayer);
/*     */     
/* 210 */     getPlayerManager(entityplayer.dimension).removePlayer(entityplayer);
/* 211 */     this.players.remove(entityplayer);
/* 212 */     this.server.getWorldServer(entityplayer.dimension).removeEntity(entityplayer);
/* 213 */     ChunkCoordinates chunkcoordinates = entityplayer.getBed();
/*     */ 
/*     */     
/* 216 */     EntityPlayer entityplayer1 = entityplayer;
/*     */     
/* 218 */     if (location == null) {
/* 219 */       boolean isBedSpawn = false;
/* 220 */       CraftWorld cworld = (CraftWorld)this.server.server.getWorld(entityplayer.spawnWorld);
/* 221 */       if (cworld != null && chunkcoordinates != null) {
/* 222 */         ChunkCoordinates chunkcoordinates1 = EntityHuman.getBed(cworld.getHandle(), chunkcoordinates);
/* 223 */         if (chunkcoordinates1 != null) {
/* 224 */           isBedSpawn = true;
/* 225 */           location = new Location(cworld, chunkcoordinates1.x + 0.5D, chunkcoordinates1.y, chunkcoordinates1.z + 0.5D);
/*     */         } else {
/* 227 */           entityplayer1.netServerHandler.sendPacket(new Packet70Bed(false));
/*     */         } 
/*     */       } 
/*     */       
/* 231 */       if (location == null) {
/* 232 */         cworld = (CraftWorld)this.server.server.getWorlds().get(0);
/* 233 */         chunkcoordinates = cworld.getHandle().getSpawn();
/* 234 */         location = new Location(cworld, chunkcoordinates.x + 0.5D, chunkcoordinates.y, chunkcoordinates.z + 0.5D);
/*     */       } 
/*     */       
/* 237 */       Player respawnPlayer = this.cserver.getPlayer(entityplayer);
/* 238 */       PlayerRespawnEvent respawnEvent = new PlayerRespawnEvent(respawnPlayer, location, isBedSpawn);
/* 239 */       this.cserver.getPluginManager().callEvent(respawnEvent);
/*     */       
/* 241 */       location = respawnEvent.getRespawnLocation();
/* 242 */       entityplayer.health = 20;
/* 243 */       entityplayer.fireTicks = 0;
/* 244 */       entityplayer.fallDistance = 0.0F;
/*     */     } else {
/* 246 */       location.setWorld(this.server.getWorldServer(i).getWorld());
/*     */     } 
/* 248 */     WorldServer worldserver = ((CraftWorld)location.getWorld()).getHandle();
/* 249 */     entityplayer1.setLocation(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
/*     */ 
/*     */     
/* 252 */     worldserver.chunkProviderServer.getChunkAt((int)entityplayer1.locX >> 4, (int)entityplayer1.locZ >> 4);
/*     */     
/* 254 */     while (worldserver.getEntities(entityplayer1, entityplayer1.boundingBox).size() != 0) {
/* 255 */       entityplayer1.setPosition(entityplayer1.locX, entityplayer1.locY + 1.0D, entityplayer1.locZ);
/*     */     }
/*     */ 
/*     */     
/* 259 */     byte actualDimension = (byte)worldserver.getWorld().getEnvironment().getId();
/* 260 */     entityplayer1.netServerHandler.sendPacket(new Packet9Respawn((byte)((actualDimension >= 0) ? -1 : 0)));
/* 261 */     entityplayer1.netServerHandler.sendPacket(new Packet9Respawn(actualDimension));
/* 262 */     entityplayer1.spawnIn(worldserver);
/* 263 */     entityplayer1.dead = false;
/* 264 */     entityplayer1.netServerHandler.teleport(new Location(worldserver.getWorld(), entityplayer1.locX, entityplayer1.locY, entityplayer1.locZ, entityplayer1.yaw, entityplayer1.pitch));
/*     */     
/* 266 */     a(entityplayer1, worldserver);
/* 267 */     getPlayerManager(entityplayer1.dimension).addPlayer(entityplayer1);
/* 268 */     worldserver.addEntity(entityplayer1);
/* 269 */     this.players.add(entityplayer1);
/* 270 */     updateClient(entityplayer1);
/* 271 */     entityplayer1.x();
/* 272 */     return entityplayer1;
/*     */   }
/*     */ 
/*     */   
/*     */   public void f(EntityPlayer entityplayer) {
/* 277 */     int dimension = entityplayer.dimension;
/* 278 */     WorldServer fromWorld = this.server.getWorldServer(dimension);
/* 279 */     WorldServer toWorld = null;
/* 280 */     if (dimension < 10) {
/* 281 */       int toDimension = (dimension == -1) ? 0 : -1;
/* 282 */       for (WorldServer world : this.server.worlds) {
/* 283 */         if (world.dimension == toDimension) {
/* 284 */           toWorld = world;
/*     */         }
/*     */       } 
/*     */     } 
/* 288 */     double blockRatio = (dimension == -1) ? 8.0D : 0.125D;
/*     */     
/* 290 */     Location fromLocation = new Location(fromWorld.getWorld(), entityplayer.locX, entityplayer.locY, entityplayer.locZ, entityplayer.yaw, entityplayer.pitch);
/* 291 */     Location toLocation = (toWorld == null) ? null : new Location(toWorld.getWorld(), entityplayer.locX * blockRatio, entityplayer.locY, entityplayer.locZ * blockRatio, entityplayer.yaw, entityplayer.pitch);
/*     */     
/* 293 */     PortalTravelAgent pta = new PortalTravelAgent();
/* 294 */     PlayerPortalEvent event = new PlayerPortalEvent((Player)entityplayer.getBukkitEntity(), fromLocation, toLocation, pta);
/* 295 */     Bukkit.getServer().getPluginManager().callEvent(event);
/* 296 */     if (event.isCancelled() || event.getTo() == null) {
/*     */       return;
/*     */     }
/*     */     
/* 300 */     Location finalLocation = event.getTo();
/* 301 */     if (event.useTravelAgent()) {
/* 302 */       finalLocation = event.getPortalTravelAgent().findOrCreate(finalLocation);
/*     */     }
/* 304 */     toWorld = ((CraftWorld)finalLocation.getWorld()).getHandle();
/* 305 */     moveToWorld(entityplayer, toWorld.dimension, finalLocation);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void b() {
/* 311 */     for (int i = 0; i < this.server.worlds.size(); i++) {
/* 312 */       ((WorldServer)this.server.worlds.get(i)).manager.flush();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 318 */   public void flagDirty(int i, int j, int k, int l) { getPlayerManager(l).flagDirty(i, j, k); }
/*     */ 
/*     */   
/*     */   public void sendAll(Packet packet) {
/* 322 */     for (int i = 0; i < this.players.size(); i++) {
/* 323 */       EntityPlayer entityplayer = (EntityPlayer)this.players.get(i);
/*     */       
/* 325 */       entityplayer.netServerHandler.sendPacket(packet);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void a(Packet packet, int i) {
/* 330 */     for (int j = 0; j < this.players.size(); j++) {
/* 331 */       EntityPlayer entityplayer = (EntityPlayer)this.players.get(j);
/*     */       
/* 333 */       if (entityplayer.dimension == i) {
/* 334 */         entityplayer.netServerHandler.sendPacket(packet);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public String c() {
/* 340 */     String s = "";
/*     */     
/* 342 */     for (int i = 0; i < this.players.size(); i++) {
/* 343 */       if (i > 0) {
/* 344 */         s = s + ", ";
/*     */       }
/*     */       
/* 347 */       s = s + ((EntityPlayer)this.players.get(i)).name;
/*     */     } 
/*     */     
/* 350 */     return s;
/*     */   }
/*     */   
/*     */   public void a(String s) {
/* 354 */     this.banByName.add(s.toLowerCase());
/* 355 */     h();
/*     */   }
/*     */   
/*     */   public void b(String s) {
/* 359 */     this.banByName.remove(s.toLowerCase());
/* 360 */     h();
/*     */   }
/*     */   
/*     */   private void g() {
/*     */     try {
/* 365 */       this.banByName.clear();
/* 366 */       BufferedReader bufferedreader = new BufferedReader(new FileReader(this.j));
/* 367 */       String s = "";
/*     */       
/* 369 */       while ((s = bufferedreader.readLine()) != null) {
/* 370 */         this.banByName.add(s.trim().toLowerCase());
/*     */       }
/*     */       
/* 373 */       bufferedreader.close();
/* 374 */     } catch (Exception exception) {
/* 375 */       a.warning("Failed to load ban list: " + exception);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void h() {
/*     */     try {
/* 381 */       PrintWriter printwriter = new PrintWriter(new FileWriter(this.j, false));
/* 382 */       Iterator iterator = this.banByName.iterator();
/*     */       
/* 384 */       while (iterator.hasNext()) {
/* 385 */         String s = (String)iterator.next();
/*     */         
/* 387 */         printwriter.println(s);
/*     */       } 
/*     */       
/* 390 */       printwriter.close();
/* 391 */     } catch (Exception exception) {
/* 392 */       a.warning("Failed to save ban list: " + exception);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void c(String s) {
/* 397 */     this.banByIP.add(s.toLowerCase());
/* 398 */     j();
/*     */   }
/*     */   
/*     */   public void d(String s) {
/* 402 */     this.banByIP.remove(s.toLowerCase());
/* 403 */     j();
/*     */   }
/*     */   
/*     */   private void i() {
/*     */     try {
/* 408 */       this.banByIP.clear();
/* 409 */       BufferedReader bufferedreader = new BufferedReader(new FileReader(this.k));
/* 410 */       String s = "";
/*     */       
/* 412 */       while ((s = bufferedreader.readLine()) != null) {
/* 413 */         this.banByIP.add(s.trim().toLowerCase());
/*     */       }
/*     */       
/* 416 */       bufferedreader.close();
/* 417 */     } catch (Exception exception) {
/* 418 */       a.warning("Failed to load ip ban list: " + exception);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void j() {
/*     */     try {
/* 424 */       PrintWriter printwriter = new PrintWriter(new FileWriter(this.k, false));
/* 425 */       Iterator iterator = this.banByIP.iterator();
/*     */       
/* 427 */       while (iterator.hasNext()) {
/* 428 */         String s = (String)iterator.next();
/*     */         
/* 430 */         printwriter.println(s);
/*     */       } 
/*     */       
/* 433 */       printwriter.close();
/* 434 */     } catch (Exception exception) {
/* 435 */       a.warning("Failed to save ip ban list: " + exception);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void e(String s) {
/* 440 */     this.h.add(s.toLowerCase());
/* 441 */     l();
/*     */ 
/*     */     
/* 444 */     Player player = this.server.server.getPlayer(s);
/* 445 */     if (player != null) {
/* 446 */       player.recalculatePermissions();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void f(String s) {
/* 452 */     this.h.remove(s.toLowerCase());
/* 453 */     l();
/*     */ 
/*     */     
/* 456 */     Player player = this.server.server.getPlayer(s);
/* 457 */     if (player != null) {
/* 458 */       player.recalculatePermissions();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private void k() {
/*     */     try {
/* 465 */       this.h.clear();
/* 466 */       BufferedReader bufferedreader = new BufferedReader(new FileReader(this.l));
/* 467 */       String s = "";
/*     */       
/* 469 */       while ((s = bufferedreader.readLine()) != null) {
/* 470 */         this.h.add(s.trim().toLowerCase());
/*     */       }
/*     */       
/* 473 */       bufferedreader.close();
/* 474 */     } catch (Exception exception) {
/*     */       
/* 476 */       a.warning("Failed to load ops: " + exception);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void l() {
/*     */     try {
/* 482 */       PrintWriter printwriter = new PrintWriter(new FileWriter(this.l, false));
/* 483 */       Iterator iterator = this.h.iterator();
/*     */       
/* 485 */       while (iterator.hasNext()) {
/* 486 */         String s = (String)iterator.next();
/*     */         
/* 488 */         printwriter.println(s);
/*     */       } 
/*     */       
/* 491 */       printwriter.close();
/* 492 */     } catch (Exception exception) {
/*     */       
/* 494 */       a.warning("Failed to save ops: " + exception);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void m() {
/*     */     try {
/* 500 */       this.i.clear();
/* 501 */       BufferedReader bufferedreader = new BufferedReader(new FileReader(this.m));
/* 502 */       String s = "";
/*     */       
/* 504 */       while ((s = bufferedreader.readLine()) != null) {
/* 505 */         this.i.add(s.trim().toLowerCase());
/*     */       }
/*     */       
/* 508 */       bufferedreader.close();
/* 509 */     } catch (Exception exception) {
/* 510 */       a.warning("Failed to load white-list: " + exception);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void n() {
/*     */     try {
/* 516 */       PrintWriter printwriter = new PrintWriter(new FileWriter(this.m, false));
/* 517 */       Iterator iterator = this.i.iterator();
/*     */       
/* 519 */       while (iterator.hasNext()) {
/* 520 */         String s = (String)iterator.next();
/*     */         
/* 522 */         printwriter.println(s);
/*     */       } 
/*     */       
/* 525 */       printwriter.close();
/* 526 */     } catch (Exception exception) {
/* 527 */       a.warning("Failed to save white-list: " + exception);
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean isWhitelisted(String s) {
/* 532 */     s = s.trim().toLowerCase();
/* 533 */     return (!this.o || this.h.contains(s) || this.i.contains(s));
/*     */   }
/*     */ 
/*     */   
/* 537 */   public boolean isOp(String s) { return this.h.contains(s.trim().toLowerCase()); }
/*     */ 
/*     */   
/*     */   public EntityPlayer i(String s) {
/* 541 */     for (int i = 0; i < this.players.size(); i++) {
/* 542 */       EntityPlayer entityplayer = (EntityPlayer)this.players.get(i);
/*     */       
/* 544 */       if (entityplayer.name.equalsIgnoreCase(s)) {
/* 545 */         return entityplayer;
/*     */       }
/*     */     } 
/*     */     
/* 549 */     return null;
/*     */   }
/*     */   
/*     */   public void a(String s, String s1) {
/* 553 */     EntityPlayer entityplayer = i(s);
/*     */     
/* 555 */     if (entityplayer != null) {
/* 556 */       entityplayer.netServerHandler.sendPacket(new Packet3Chat(s1));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/* 561 */   public void sendPacketNearby(double d0, double d1, double d2, double d3, int i, Packet packet) { sendPacketNearby((EntityHuman)null, d0, d1, d2, d3, i, packet); }
/*     */ 
/*     */   
/*     */   public void sendPacketNearby(EntityHuman entityhuman, double d0, double d1, double d2, double d3, int i, Packet packet) {
/* 565 */     for (int j = 0; j < this.players.size(); j++) {
/* 566 */       EntityPlayer entityplayer = (EntityPlayer)this.players.get(j);
/*     */       
/* 568 */       if (entityplayer != entityhuman && entityplayer.dimension == i) {
/* 569 */         double d4 = d0 - entityplayer.locX;
/* 570 */         double d5 = d1 - entityplayer.locY;
/* 571 */         double d6 = d2 - entityplayer.locZ;
/*     */         
/* 573 */         if (d4 * d4 + d5 * d5 + d6 * d6 < d3 * d3) {
/* 574 */           entityplayer.netServerHandler.sendPacket(packet);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void j(String s) {
/* 581 */     Packet3Chat packet3chat = new Packet3Chat(s);
/*     */     
/* 583 */     for (int i = 0; i < this.players.size(); i++) {
/* 584 */       EntityPlayer entityplayer = (EntityPlayer)this.players.get(i);
/*     */       
/* 586 */       if (isOp(entityplayer.name)) {
/* 587 */         entityplayer.netServerHandler.sendPacket(packet3chat);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean a(String s, Packet packet) {
/* 593 */     EntityPlayer entityplayer = i(s);
/*     */     
/* 595 */     if (entityplayer != null) {
/* 596 */       entityplayer.netServerHandler.sendPacket(packet);
/* 597 */       return true;
/*     */     } 
/* 599 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void savePlayers() {
/* 604 */     for (int i = 0; i < this.players.size(); i++) {
/* 605 */       this.playerFileData.a((EntityHuman)this.players.get(i));
/*     */     }
/*     */   }
/*     */   
/*     */   public void a(int i, int j, int k, TileEntity tileentity) {}
/*     */   
/*     */   public void k(String s) {
/* 612 */     this.i.add(s);
/* 613 */     n();
/*     */   }
/*     */   
/*     */   public void l(String s) {
/* 617 */     this.i.remove(s);
/* 618 */     n();
/*     */   }
/*     */ 
/*     */   
/* 622 */   public Set e() { return this.i; }
/*     */ 
/*     */ 
/*     */   
/* 626 */   public void f() { m(); }
/*     */ 
/*     */   
/*     */   public void a(EntityPlayer entityplayer, WorldServer worldserver) {
/* 630 */     entityplayer.netServerHandler.sendPacket(new Packet4UpdateTime(worldserver.getTime()));
/* 631 */     if (worldserver.v()) {
/* 632 */       entityplayer.netServerHandler.sendPacket(new Packet70Bed(true));
/*     */     }
/*     */   }
/*     */   
/*     */   public void updateClient(EntityPlayer entityplayer) {
/* 637 */     entityplayer.updateInventory(entityplayer.defaultContainer);
/* 638 */     entityplayer.C();
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\ServerConfigurationManager.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */