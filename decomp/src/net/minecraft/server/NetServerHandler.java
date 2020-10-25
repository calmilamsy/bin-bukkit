/*      */ package net.minecraft.server;
/*      */ 
/*      */ import java.util.ArrayList;
/*      */ import java.util.HashMap;
/*      */ import java.util.Map;
/*      */ import java.util.logging.Level;
/*      */ import java.util.logging.Logger;
/*      */ import org.bukkit.ChatColor;
/*      */ import org.bukkit.Location;
/*      */ import org.bukkit.command.CommandException;
/*      */ import org.bukkit.craftbukkit.ChunkCompressionThread;
/*      */ import org.bukkit.craftbukkit.CraftServer;
/*      */ import org.bukkit.craftbukkit.TextWrapper;
/*      */ import org.bukkit.craftbukkit.block.CraftBlock;
/*      */ import org.bukkit.craftbukkit.entity.CraftPlayer;
/*      */ import org.bukkit.craftbukkit.event.CraftEventFactory;
/*      */ import org.bukkit.entity.Player;
/*      */ import org.bukkit.event.Event;
/*      */ import org.bukkit.event.block.Action;
/*      */ import org.bukkit.event.block.SignChangeEvent;
/*      */ import org.bukkit.event.player.PlayerAnimationEvent;
/*      */ import org.bukkit.event.player.PlayerChatEvent;
/*      */ import org.bukkit.event.player.PlayerCommandPreprocessEvent;
/*      */ import org.bukkit.event.player.PlayerInteractEntityEvent;
/*      */ import org.bukkit.event.player.PlayerInteractEvent;
/*      */ import org.bukkit.event.player.PlayerItemHeldEvent;
/*      */ import org.bukkit.event.player.PlayerKickEvent;
/*      */ import org.bukkit.event.player.PlayerMoveEvent;
/*      */ import org.bukkit.event.player.PlayerTeleportEvent;
/*      */ import org.bukkit.event.player.PlayerToggleSneakEvent;
/*      */ 
/*      */ 
/*      */ public class NetServerHandler
/*      */   extends NetHandler
/*      */   implements ICommandListener
/*      */ {
/*   37 */   public static Logger a = Logger.getLogger("Minecraft"); public NetworkManager networkManager; public boolean disconnected; private MinecraftServer minecraftServer; public EntityPlayer player; private int f; private int g; private int h; private boolean i; private double x; private double y; private double z; private boolean checkMovement; private Map n;
/*      */   public NetServerHandler(MinecraftServer minecraftserver, NetworkManager networkmanager, EntityPlayer entityplayer) {
/*   39 */     this.disconnected = false;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*   49 */     this.checkMovement = true;
/*   50 */     this.n = new HashMap();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*   63 */     this.lastTick = MinecraftServer.currentTick;
/*   64 */     this.lastDropTick = MinecraftServer.currentTick;
/*   65 */     this.dropCount = 0;
/*      */ 
/*      */ 
/*      */     
/*   69 */     this.lastPosX = Double.MAX_VALUE;
/*   70 */     this.lastPosY = Double.MAX_VALUE;
/*   71 */     this.lastPosZ = Double.MAX_VALUE;
/*   72 */     this.lastPitch = Float.MAX_VALUE;
/*   73 */     this.lastYaw = Float.MAX_VALUE;
/*   74 */     this.justTeleported = false;
/*      */     this.minecraftServer = minecraftserver;
/*      */     this.networkManager = networkmanager;
/*      */     networkmanager.a(this);
/*      */     this.player = entityplayer;
/*      */     entityplayer.netServerHandler = this;
/*      */     this.server = minecraftserver.server;
/*      */   }
/*      */   private final CraftServer server; private int lastTick; private int lastDropTick; private int dropCount; private static final int PLACE_DISTANCE_SQUARED = 36; private double lastPosX; private double lastPosY; private double lastPosZ; private float lastPitch; private float lastYaw; private boolean justTeleported; Long lastPacket; private int lastMaterial;
/*   83 */   public CraftPlayer getPlayer() { return (this.player == null) ? null : (CraftPlayer)this.player.getBukkitEntity(); }
/*      */ 
/*      */ 
/*      */   
/*      */   public void a() {
/*   88 */     this.i = false;
/*   89 */     this.networkManager.b();
/*   90 */     if (this.f - this.g > 20) {
/*   91 */       sendPacket(new Packet0KeepAlive());
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public void disconnect(String s) {
/*   97 */     String leaveMessage = "§e" + this.player.name + " left the game.";
/*      */     
/*   99 */     PlayerKickEvent event = new PlayerKickEvent(this.server.getPlayer(this.player), s, leaveMessage);
/*  100 */     this.server.getPluginManager().callEvent(event);
/*      */     
/*  102 */     if (event.isCancelled()) {
/*      */       return;
/*      */     }
/*      */ 
/*      */     
/*  107 */     s = event.getReason();
/*      */ 
/*      */     
/*  110 */     this.player.B();
/*  111 */     sendPacket(new Packet255KickDisconnect(s));
/*  112 */     this.networkManager.d();
/*      */ 
/*      */     
/*  115 */     leaveMessage = event.getLeaveMessage();
/*  116 */     if (leaveMessage != null) {
/*  117 */       this.minecraftServer.serverConfigurationManager.sendAll(new Packet3Chat(leaveMessage));
/*      */     }
/*      */ 
/*      */     
/*  121 */     this.minecraftServer.serverConfigurationManager.disconnect(this.player);
/*  122 */     this.disconnected = true;
/*      */   }
/*      */ 
/*      */   
/*  126 */   public void a(Packet27 packet27) { this.player.a(packet27.c(), packet27.e(), packet27.g(), packet27.h(), packet27.d(), packet27.f()); }
/*      */ 
/*      */   
/*      */   public void a(Packet10Flying packet10flying) {
/*  130 */     WorldServer worldserver = this.minecraftServer.getWorldServer(this.player.dimension);
/*      */     
/*  132 */     this.i = true;
/*      */ 
/*      */     
/*  135 */     if (!this.checkMovement) {
/*  136 */       double d0 = packet10flying.y - this.y;
/*  137 */       if (packet10flying.x == this.x && d0 * d0 < 0.01D && packet10flying.z == this.z) {
/*  138 */         this.checkMovement = true;
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/*  143 */     CraftPlayer craftPlayer = getPlayer();
/*  144 */     Location from = new Location(craftPlayer.getWorld(), this.lastPosX, this.lastPosY, this.lastPosZ, this.lastYaw, this.lastPitch);
/*  145 */     Location to = craftPlayer.getLocation().clone();
/*      */ 
/*      */     
/*  148 */     if (packet10flying.h && (!packet10flying.h || packet10flying.y != -999.0D || packet10flying.stance != -999.0D)) {
/*  149 */       to.setX(packet10flying.x);
/*  150 */       to.setY(packet10flying.y);
/*  151 */       to.setZ(packet10flying.z);
/*      */     } 
/*      */ 
/*      */     
/*  155 */     if (packet10flying.hasLook) {
/*  156 */       to.setYaw(packet10flying.yaw);
/*  157 */       to.setPitch(packet10flying.pitch);
/*      */     } 
/*      */ 
/*      */     
/*  161 */     double delta = Math.pow(this.lastPosX - to.getX(), 2.0D) + Math.pow(this.lastPosY - to.getY(), 2.0D) + Math.pow(this.lastPosZ - to.getZ(), 2.0D);
/*  162 */     float deltaAngle = Math.abs(this.lastYaw - to.getYaw()) + Math.abs(this.lastPitch - to.getPitch());
/*      */     
/*  164 */     if ((delta > 0.00390625D || deltaAngle > 10.0F) && this.checkMovement && !this.player.dead) {
/*  165 */       this.lastPosX = to.getX();
/*  166 */       this.lastPosY = to.getY();
/*  167 */       this.lastPosZ = to.getZ();
/*  168 */       this.lastYaw = to.getYaw();
/*  169 */       this.lastPitch = to.getPitch();
/*      */ 
/*      */       
/*  172 */       if (from.getX() != Double.MAX_VALUE) {
/*  173 */         PlayerMoveEvent event = new PlayerMoveEvent(craftPlayer, from, to);
/*  174 */         this.server.getPluginManager().callEvent(event);
/*      */ 
/*      */         
/*  177 */         if (event.isCancelled()) {
/*  178 */           this.player.netServerHandler.sendPacket(new Packet13PlayerLookMove(from.getX(), from.getY() + 1.6200000047683716D, from.getY(), from.getZ(), from.getYaw(), from.getPitch(), false));
/*      */ 
/*      */           
/*      */           return;
/*      */         } 
/*      */ 
/*      */         
/*  185 */         if (!to.equals(event.getTo()) && !event.isCancelled()) {
/*  186 */           this.player.getBukkitEntity().teleport(event.getTo());
/*      */ 
/*      */           
/*      */           return;
/*      */         } 
/*      */         
/*  192 */         if (!from.equals(getPlayer().getLocation()) && this.justTeleported) {
/*  193 */           this.justTeleported = false;
/*      */           
/*      */           return;
/*      */         } 
/*      */       } 
/*      */     } 
/*  199 */     if (Double.isNaN(packet10flying.x) || Double.isNaN(packet10flying.y) || Double.isNaN(packet10flying.z) || Double.isNaN(packet10flying.stance)) {
/*  200 */       craftPlayer.teleport(craftPlayer.getWorld().getSpawnLocation());
/*  201 */       System.err.println(craftPlayer.getName() + " was caught trying to crash the server with an invalid position.");
/*  202 */       craftPlayer.kickPlayer("Nope!");
/*      */       
/*      */       return;
/*      */     } 
/*  206 */     if (this.checkMovement && !this.player.dead) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  213 */       if (this.player.vehicle != null) {
/*  214 */         float f = this.player.yaw;
/*  215 */         float f1 = this.player.pitch;
/*      */         
/*  217 */         this.player.vehicle.f();
/*  218 */         double d1 = this.player.locX;
/*  219 */         double d2 = this.player.locY;
/*  220 */         double d3 = this.player.locZ;
/*  221 */         double d5 = 0.0D;
/*      */         
/*  223 */         double d4 = 0.0D;
/*  224 */         if (packet10flying.hasLook) {
/*  225 */           f = packet10flying.yaw;
/*  226 */           f1 = packet10flying.pitch;
/*      */         } 
/*      */         
/*  229 */         if (packet10flying.h && packet10flying.y == -999.0D && packet10flying.stance == -999.0D) {
/*  230 */           d5 = packet10flying.x;
/*  231 */           d4 = packet10flying.z;
/*      */         } 
/*      */         
/*  234 */         this.player.onGround = packet10flying.g;
/*  235 */         this.player.a(true);
/*  236 */         this.player.move(d5, 0.0D, d4);
/*  237 */         this.player.setLocation(d1, d2, d3, f, f1);
/*  238 */         this.player.motX = d5;
/*  239 */         this.player.motZ = d4;
/*  240 */         if (this.player.vehicle != null) {
/*  241 */           worldserver.vehicleEnteredWorld(this.player.vehicle, true);
/*      */         }
/*      */         
/*  244 */         if (this.player.vehicle != null) {
/*  245 */           this.player.vehicle.f();
/*      */         }
/*      */         
/*  248 */         this.minecraftServer.serverConfigurationManager.d(this.player);
/*  249 */         this.x = this.player.locX;
/*  250 */         this.y = this.player.locY;
/*  251 */         this.z = this.player.locZ;
/*  252 */         worldserver.playerJoinedWorld(this.player);
/*      */         
/*      */         return;
/*      */       } 
/*  256 */       if (this.player.isSleeping()) {
/*  257 */         this.player.a(true);
/*  258 */         this.player.setLocation(this.x, this.y, this.z, this.player.yaw, this.player.pitch);
/*  259 */         worldserver.playerJoinedWorld(this.player);
/*      */         
/*      */         return;
/*      */       } 
/*  263 */       double d0 = this.player.locY;
/*  264 */       this.x = this.player.locX;
/*  265 */       this.y = this.player.locY;
/*  266 */       this.z = this.player.locZ;
/*  267 */       double d1 = this.player.locX;
/*  268 */       double d2 = this.player.locY;
/*  269 */       double d3 = this.player.locZ;
/*  270 */       float f2 = this.player.yaw;
/*  271 */       float f3 = this.player.pitch;
/*      */       
/*  273 */       if (packet10flying.h && packet10flying.y == -999.0D && packet10flying.stance == -999.0D) {
/*  274 */         packet10flying.h = false;
/*      */       }
/*      */       
/*  277 */       if (packet10flying.h) {
/*  278 */         d1 = packet10flying.x;
/*  279 */         d2 = packet10flying.y;
/*  280 */         d3 = packet10flying.z;
/*  281 */         double d4 = packet10flying.stance - packet10flying.y;
/*  282 */         if (!this.player.isSleeping() && (d4 > 1.65D || d4 < 0.1D)) {
/*  283 */           disconnect("Illegal stance");
/*  284 */           a.warning(this.player.name + " had an illegal stance: " + d4);
/*      */           
/*      */           return;
/*      */         } 
/*  288 */         if (Math.abs(packet10flying.x) > 3.2E7D || Math.abs(packet10flying.z) > 3.2E7D) {
/*  289 */           disconnect("Illegal position");
/*      */           
/*      */           return;
/*      */         } 
/*      */       } 
/*  294 */       if (packet10flying.hasLook) {
/*  295 */         f2 = packet10flying.yaw;
/*  296 */         f3 = packet10flying.pitch;
/*      */       } 
/*      */       
/*  299 */       this.player.a(true);
/*  300 */       this.player.br = 0.0F;
/*  301 */       this.player.setLocation(this.x, this.y, this.z, f2, f3);
/*  302 */       if (!this.checkMovement) {
/*      */         return;
/*      */       }
/*      */       
/*  306 */       double d4 = d1 - this.player.locX;
/*  307 */       double d6 = d2 - this.player.locY;
/*  308 */       double d7 = d3 - this.player.locZ;
/*  309 */       double d8 = d4 * d4 + d6 * d6 + d7 * d7;
/*      */       
/*  311 */       if (d8 > 200.0D && this.checkMovement) {
/*  312 */         a.warning(this.player.name + " moved too quickly!");
/*  313 */         disconnect("You moved too quickly :( (Hacking?)");
/*      */         
/*      */         return;
/*      */       } 
/*  317 */       float f4 = 0.0625F;
/*  318 */       boolean flag = (worldserver.getEntities(this.player, this.player.boundingBox.clone().shrink(f4, f4, f4)).size() == 0);
/*      */       
/*  320 */       this.player.move(d4, d6, d7);
/*  321 */       d4 = d1 - this.player.locX;
/*  322 */       d6 = d2 - this.player.locY;
/*  323 */       if (d6 > -0.5D || d6 < 0.5D) {
/*  324 */         d6 = 0.0D;
/*      */       }
/*      */       
/*  327 */       d7 = d3 - this.player.locZ;
/*  328 */       d8 = d4 * d4 + d6 * d6 + d7 * d7;
/*  329 */       boolean flag1 = false;
/*      */       
/*  331 */       if (d8 > 0.0625D && !this.player.isSleeping()) {
/*  332 */         flag1 = true;
/*  333 */         a.warning(this.player.name + " moved wrongly!");
/*  334 */         System.out.println("Got position " + d1 + ", " + d2 + ", " + d3);
/*  335 */         System.out.println("Expected " + this.player.locX + ", " + this.player.locY + ", " + this.player.locZ);
/*      */       } 
/*      */       
/*  338 */       this.player.setLocation(d1, d2, d3, f2, f3);
/*  339 */       boolean flag2 = (worldserver.getEntities(this.player, this.player.boundingBox.clone().shrink(f4, f4, f4)).size() == 0);
/*      */       
/*  341 */       if (flag && (flag1 || !flag2) && !this.player.isSleeping()) {
/*  342 */         a(this.x, this.y, this.z, f2, f3);
/*      */         
/*      */         return;
/*      */       } 
/*  346 */       AxisAlignedBB axisalignedbb = this.player.boundingBox.clone().b(f4, f4, f4).a(0.0D, -0.55D, 0.0D);
/*      */       
/*  348 */       if (!this.minecraftServer.allowFlight && !worldserver.b(axisalignedbb)) {
/*  349 */         if (d6 >= -0.03125D) {
/*  350 */           this.h++;
/*  351 */           if (this.h > 80) {
/*  352 */             a.warning(this.player.name + " was kicked for floating too long!");
/*  353 */             disconnect("Flying is not enabled on this server");
/*      */             return;
/*      */           } 
/*      */         } 
/*      */       } else {
/*  358 */         this.h = 0;
/*      */       } 
/*      */       
/*  361 */       this.player.onGround = packet10flying.g;
/*  362 */       this.minecraftServer.serverConfigurationManager.d(this.player);
/*  363 */       this.player.b(this.player.locY - d0, packet10flying.g);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void a(double d0, double d1, double d2, float f, float f1) {
/*  369 */     CraftPlayer craftPlayer = getPlayer();
/*  370 */     Location from = craftPlayer.getLocation();
/*  371 */     Location to = new Location(getPlayer().getWorld(), d0, d1, d2, f, f1);
/*  372 */     PlayerTeleportEvent event = new PlayerTeleportEvent(craftPlayer, from, to);
/*  373 */     this.server.getPluginManager().callEvent(event);
/*      */     
/*  375 */     from = event.getFrom();
/*  376 */     to = event.isCancelled() ? from : event.getTo();
/*      */     
/*  378 */     teleport(to);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void teleport(Location dest) {
/*  385 */     double d0 = dest.getX();
/*  386 */     double d1 = dest.getY();
/*  387 */     double d2 = dest.getZ();
/*  388 */     float f = dest.getYaw();
/*  389 */     float f1 = dest.getPitch();
/*      */ 
/*      */     
/*  392 */     if (Float.isNaN(f)) {
/*  393 */       f = 0.0F;
/*      */     }
/*      */     
/*  396 */     if (Float.isNaN(f1)) {
/*  397 */       f1 = 0.0F;
/*      */     }
/*      */     
/*  400 */     this.lastPosX = d0;
/*  401 */     this.lastPosY = d1;
/*  402 */     this.lastPosZ = d2;
/*  403 */     this.lastYaw = f;
/*  404 */     this.lastPitch = f1;
/*  405 */     this.justTeleported = true;
/*      */ 
/*      */     
/*  408 */     this.checkMovement = false;
/*  409 */     this.x = d0;
/*  410 */     this.y = d1;
/*  411 */     this.z = d2;
/*  412 */     this.player.setLocation(d0, d1, d2, f, f1);
/*  413 */     this.player.netServerHandler.sendPacket(new Packet13PlayerLookMove(d0, d1 + 1.6200000047683716D, d1, d2, f, f1, false));
/*      */   }
/*      */   
/*      */   public void a(Packet14BlockDig packet14blockdig) {
/*  417 */     if (this.player.dead)
/*      */       return; 
/*  419 */     WorldServer worldserver = this.minecraftServer.getWorldServer(this.player.dimension);
/*      */     
/*  421 */     if (packet14blockdig.e == 4) {
/*      */ 
/*      */       
/*  424 */       if (this.lastDropTick != MinecraftServer.currentTick) {
/*  425 */         this.dropCount = 0;
/*  426 */         this.lastDropTick = MinecraftServer.currentTick;
/*      */       } else {
/*      */         
/*  429 */         this.dropCount++;
/*  430 */         if (this.dropCount >= 20) {
/*  431 */           a.warning(this.player.name + " dropped their items too quickly!");
/*  432 */           disconnect("You dropped your items too quickly (Hacking?)");
/*      */         } 
/*      */       } 
/*      */       
/*  436 */       this.player.F();
/*      */     } else {
/*  438 */       boolean flag = worldserver.weirdIsOpCache = (worldserver.dimension != 0 || this.minecraftServer.serverConfigurationManager.isOp(this.player.name));
/*  439 */       boolean flag1 = false;
/*      */       
/*  441 */       if (packet14blockdig.e == 0) {
/*  442 */         flag1 = true;
/*      */       }
/*      */       
/*  445 */       if (packet14blockdig.e == 2) {
/*  446 */         flag1 = true;
/*      */       }
/*      */       
/*  449 */       int i = packet14blockdig.a;
/*  450 */       int j = packet14blockdig.b;
/*  451 */       int k = packet14blockdig.c;
/*      */       
/*  453 */       if (flag1) {
/*  454 */         double d0 = this.player.locX - i + 0.5D;
/*  455 */         double d1 = this.player.locY - j + 0.5D;
/*  456 */         double d2 = this.player.locZ - k + 0.5D;
/*  457 */         double d3 = d0 * d0 + d1 * d1 + d2 * d2;
/*      */         
/*  459 */         if (d3 > 36.0D) {
/*      */           return;
/*      */         }
/*      */       } 
/*      */       
/*  464 */       ChunkCoordinates chunkcoordinates = worldserver.getSpawn();
/*  465 */       int l = (int)MathHelper.abs((i - chunkcoordinates.x));
/*  466 */       int i1 = (int)MathHelper.abs((k - chunkcoordinates.z));
/*      */       
/*  468 */       if (l > i1) {
/*  469 */         i1 = l;
/*      */       }
/*      */       
/*  472 */       if (packet14blockdig.e == 0) {
/*      */         
/*  474 */         if (i1 < this.server.getSpawnRadius() && !flag) {
/*  475 */           this.player.netServerHandler.sendPacket(new Packet53BlockChange(i, j, k, worldserver));
/*      */         } else {
/*      */           
/*  478 */           this.player.itemInWorldManager.dig(i, j, k, packet14blockdig.face);
/*      */         } 
/*  480 */       } else if (packet14blockdig.e == 2) {
/*  481 */         this.player.itemInWorldManager.a(i, j, k);
/*  482 */         if (worldserver.getTypeId(i, j, k) != 0) {
/*  483 */           this.player.netServerHandler.sendPacket(new Packet53BlockChange(i, j, k, worldserver));
/*      */         }
/*  485 */       } else if (packet14blockdig.e == 3) {
/*  486 */         double d4 = this.player.locX - i + 0.5D;
/*  487 */         double d5 = this.player.locY - j + 0.5D;
/*  488 */         double d6 = this.player.locZ - k + 0.5D;
/*  489 */         double d7 = d4 * d4 + d5 * d5 + d6 * d6;
/*      */         
/*  491 */         if (d7 < 256.0D) {
/*  492 */           this.player.netServerHandler.sendPacket(new Packet53BlockChange(i, j, k, worldserver));
/*      */         }
/*      */       } 
/*      */       
/*  496 */       worldserver.weirdIsOpCache = false;
/*      */     } 
/*      */   }
/*      */   
/*      */   public void a(Packet15Place packet15place) {
/*  501 */     WorldServer worldserver = this.minecraftServer.getWorldServer(this.player.dimension);
/*      */ 
/*      */     
/*  504 */     if (this.player.dead) {
/*      */       return;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  514 */     if (packet15place.face == 255) {
/*  515 */       if (packet15place.itemstack != null && packet15place.itemstack.id == this.lastMaterial && this.lastPacket != null && packet15place.timestamp - this.lastPacket.longValue() < 100L) {
/*  516 */         this.lastPacket = null;
/*      */         return;
/*      */       } 
/*      */     } else {
/*  520 */       this.lastMaterial = (packet15place.itemstack == null) ? -1 : packet15place.itemstack.id;
/*  521 */       this.lastPacket = Long.valueOf(packet15place.timestamp);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  527 */     boolean always = false;
/*      */ 
/*      */ 
/*      */     
/*  531 */     ItemStack itemstack = this.player.inventory.getItemInHand();
/*  532 */     boolean flag = worldserver.weirdIsOpCache = (worldserver.dimension != 0 || this.minecraftServer.serverConfigurationManager.isOp(this.player.name));
/*      */     
/*  534 */     if (packet15place.face == 255) {
/*  535 */       if (itemstack == null) {
/*      */         return;
/*      */       }
/*      */ 
/*      */       
/*  540 */       int itemstackAmount = itemstack.count;
/*  541 */       PlayerInteractEvent event = CraftEventFactory.callPlayerInteractEvent(this.player, Action.RIGHT_CLICK_AIR, itemstack);
/*  542 */       if (event.useItemInHand() != Event.Result.DENY) {
/*  543 */         this.player.itemInWorldManager.useItem(this.player, this.player.world, itemstack);
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  549 */       always = (itemstack.count != itemstackAmount);
/*      */     } else {
/*      */       
/*  552 */       int i = packet15place.a;
/*  553 */       int j = packet15place.b;
/*  554 */       int k = packet15place.c;
/*  555 */       int l = packet15place.face;
/*  556 */       ChunkCoordinates chunkcoordinates = worldserver.getSpawn();
/*  557 */       int i1 = (int)MathHelper.abs((i - chunkcoordinates.x));
/*  558 */       int j1 = (int)MathHelper.abs((k - chunkcoordinates.z));
/*      */       
/*  560 */       if (i1 > j1) {
/*  561 */         j1 = i1;
/*      */       }
/*      */ 
/*      */       
/*  565 */       Location eyeLoc = getPlayer().getEyeLocation();
/*  566 */       if (Math.pow(eyeLoc.getX() - i, 2.0D) + Math.pow(eyeLoc.getY() - j, 2.0D) + Math.pow(eyeLoc.getZ() - k, 2.0D) > 36.0D) {
/*      */         return;
/*      */       }
/*  569 */       flag = true;
/*      */ 
/*      */       
/*  572 */       if (j1 > 16 || flag) {
/*  573 */         this.player.itemInWorldManager.interact(this.player, worldserver, itemstack, i, j, k, l);
/*      */       }
/*      */       
/*  576 */       this.player.netServerHandler.sendPacket(new Packet53BlockChange(i, j, k, worldserver));
/*  577 */       if (l == 0) {
/*  578 */         j--;
/*      */       }
/*      */       
/*  581 */       if (l == 1) {
/*  582 */         j++;
/*      */       }
/*      */       
/*  585 */       if (l == 2) {
/*  586 */         k--;
/*      */       }
/*      */       
/*  589 */       if (l == 3) {
/*  590 */         k++;
/*      */       }
/*      */       
/*  593 */       if (l == 4) {
/*  594 */         i--;
/*      */       }
/*      */       
/*  597 */       if (l == 5) {
/*  598 */         i++;
/*      */       }
/*      */       
/*  601 */       this.player.netServerHandler.sendPacket(new Packet53BlockChange(i, j, k, worldserver));
/*      */     } 
/*      */     
/*  604 */     itemstack = this.player.inventory.getItemInHand();
/*  605 */     if (itemstack != null && itemstack.count == 0) {
/*  606 */       this.player.inventory.items[this.player.inventory.itemInHandIndex] = null;
/*      */     }
/*      */     
/*  609 */     this.player.h = true;
/*  610 */     this.player.inventory.items[this.player.inventory.itemInHandIndex] = ItemStack.b(this.player.inventory.items[this.player.inventory.itemInHandIndex]);
/*  611 */     Slot slot = this.player.activeContainer.a(this.player.inventory, this.player.inventory.itemInHandIndex);
/*      */     
/*  613 */     this.player.activeContainer.a();
/*  614 */     this.player.h = false;
/*      */     
/*  616 */     if (!ItemStack.equals(this.player.inventory.getItemInHand(), packet15place.itemstack) || always) {
/*  617 */       sendPacket(new Packet103SetSlot(this.player.activeContainer.windowId, slot.a, this.player.inventory.getItemInHand()));
/*      */     }
/*      */     
/*  620 */     worldserver.weirdIsOpCache = false;
/*      */   }
/*      */   
/*      */   public void a(String s, Object[] aobject) {
/*  624 */     if (this.disconnected)
/*      */       return; 
/*  626 */     a.info(this.player.name + " lost connection: " + s);
/*      */     
/*  628 */     String quitMessage = this.minecraftServer.serverConfigurationManager.disconnect(this.player);
/*  629 */     if (quitMessage != null) {
/*  630 */       this.minecraftServer.serverConfigurationManager.sendAll(new Packet3Chat(quitMessage));
/*      */     }
/*      */     
/*  633 */     this.disconnected = true;
/*      */   }
/*      */   
/*      */   public void a(Packet packet) {
/*  637 */     a.warning(getClass() + " wasn't prepared to deal with a " + packet.getClass());
/*  638 */     disconnect("Protocol error, unexpected packet");
/*      */   }
/*      */ 
/*      */   
/*      */   public void sendPacket(Packet packet) {
/*  643 */     if (packet instanceof Packet6SpawnPosition) {
/*  644 */       Packet6SpawnPosition packet6 = (Packet6SpawnPosition)packet;
/*  645 */       this.player.compassTarget = new Location(getPlayer().getWorld(), packet6.x, packet6.y, packet6.z);
/*  646 */     } else if (packet instanceof Packet3Chat) {
/*  647 */       String message = ((Packet3Chat)packet).message;
/*  648 */       for (String line : TextWrapper.wrapText(message)) {
/*  649 */         this.networkManager.queue(new Packet3Chat(line));
/*      */       }
/*  651 */       packet = null;
/*  652 */     } else if (packet.k == true) {
/*      */       
/*  654 */       ChunkCompressionThread.sendPacket(this.player, packet);
/*  655 */       packet = null;
/*      */     } 
/*  657 */     if (packet != null) this.networkManager.queue(packet);
/*      */ 
/*      */     
/*  660 */     this.g = this.f;
/*      */   }
/*      */   
/*      */   public void a(Packet16BlockItemSwitch packet16blockitemswitch) {
/*  664 */     if (this.player.dead)
/*      */       return; 
/*  666 */     if (packet16blockitemswitch.itemInHandIndex >= 0 && packet16blockitemswitch.itemInHandIndex <= InventoryPlayer.e()) {
/*      */       
/*  668 */       PlayerItemHeldEvent event = new PlayerItemHeldEvent(getPlayer(), this.player.inventory.itemInHandIndex, packet16blockitemswitch.itemInHandIndex);
/*  669 */       this.server.getPluginManager().callEvent(event);
/*      */ 
/*      */       
/*  672 */       this.player.inventory.itemInHandIndex = packet16blockitemswitch.itemInHandIndex;
/*      */     } else {
/*  674 */       a.warning(this.player.name + " tried to set an invalid carried item");
/*      */     } 
/*      */   }
/*      */   
/*      */   public void a(Packet3Chat packet3chat) {
/*  679 */     String s = packet3chat.message;
/*      */     
/*  681 */     if (s.length() > 100) {
/*  682 */       disconnect("Chat message too long");
/*      */     } else {
/*  684 */       s = s.trim();
/*      */       
/*  686 */       for (int i = 0; i < s.length(); i++) {
/*  687 */         if (FontAllowedCharacters.allowedCharacters.indexOf(s.charAt(i)) < 0) {
/*  688 */           disconnect("Illegal characters in chat");
/*      */           
/*      */           return;
/*      */         } 
/*      */       } 
/*      */       
/*  694 */       chat(s);
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean chat(String s) {
/*  699 */     if (!this.player.dead) {
/*  700 */       if (s.startsWith("/")) {
/*  701 */         handleCommand(s);
/*  702 */         return true;
/*      */       } 
/*  704 */       CraftPlayer craftPlayer = getPlayer();
/*  705 */       PlayerChatEvent event = new PlayerChatEvent(craftPlayer, s);
/*  706 */       this.server.getPluginManager().callEvent(event);
/*      */       
/*  708 */       if (event.isCancelled()) {
/*  709 */         return true;
/*      */       }
/*      */       
/*  712 */       s = String.format(event.getFormat(), new Object[] { event.getPlayer().getDisplayName(), event.getMessage() });
/*  713 */       this.minecraftServer.console.sendMessage(s);
/*  714 */       for (Player recipient : event.getRecipients()) {
/*  715 */         recipient.sendMessage(s);
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/*  720 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void handleCommand(String s) {
/*  726 */     CraftPlayer player = getPlayer();
/*      */     
/*  728 */     PlayerCommandPreprocessEvent event = new PlayerCommandPreprocessEvent(player, s);
/*  729 */     this.server.getPluginManager().callEvent(event);
/*      */     
/*  731 */     if (event.isCancelled()) {
/*      */       return;
/*      */     }
/*      */     
/*      */     try {
/*  736 */       if (this.server.dispatchCommand(player, s.substring(1))) {
/*      */         return;
/*      */       }
/*  739 */     } catch (CommandException ex) {
/*  740 */       player.sendMessage(ChatColor.RED + "An internal error occurred while attempting to perform this command");
/*  741 */       Logger.getLogger(NetServerHandler.class.getName()).log(Level.SEVERE, null, ex);
/*      */       return;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void a(Packet18ArmAnimation packet18armanimation) {
/*  781 */     if (this.player.dead)
/*      */       return; 
/*  783 */     if (packet18armanimation.b == 1) {
/*      */       
/*  785 */       float f = 1.0F;
/*  786 */       float f1 = this.player.lastPitch + (this.player.pitch - this.player.lastPitch) * f;
/*  787 */       float f2 = this.player.lastYaw + (this.player.yaw - this.player.lastYaw) * f;
/*  788 */       double d0 = this.player.lastX + (this.player.locX - this.player.lastX) * f;
/*  789 */       double d1 = this.player.lastY + (this.player.locY - this.player.lastY) * f + 1.62D - this.player.height;
/*  790 */       double d2 = this.player.lastZ + (this.player.locZ - this.player.lastZ) * f;
/*  791 */       Vec3D vec3d = Vec3D.create(d0, d1, d2);
/*      */       
/*  793 */       float f3 = MathHelper.cos(-f2 * 0.017453292F - 3.1415927F);
/*  794 */       float f4 = MathHelper.sin(-f2 * 0.017453292F - 3.1415927F);
/*  795 */       float f5 = -MathHelper.cos(-f1 * 0.017453292F);
/*  796 */       float f6 = MathHelper.sin(-f1 * 0.017453292F);
/*  797 */       float f7 = f4 * f5;
/*  798 */       float f8 = f3 * f5;
/*  799 */       double d3 = 5.0D;
/*  800 */       Vec3D vec3d1 = vec3d.add(f7 * d3, f6 * d3, f8 * d3);
/*  801 */       MovingObjectPosition movingobjectposition = this.player.world.rayTrace(vec3d, vec3d1, true);
/*      */       
/*  803 */       if (movingobjectposition == null || movingobjectposition.type != EnumMovingObjectType.TILE) {
/*  804 */         CraftEventFactory.callPlayerInteractEvent(this.player, Action.LEFT_CLICK_AIR, this.player.inventory.getItemInHand());
/*      */       }
/*      */ 
/*      */       
/*  808 */       PlayerAnimationEvent event = new PlayerAnimationEvent(getPlayer());
/*  809 */       this.server.getPluginManager().callEvent(event);
/*      */       
/*  811 */       if (event.isCancelled()) {
/*      */         return;
/*      */       }
/*  814 */       this.player.w();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void a(Packet19EntityAction packet19entityaction) {
/*  820 */     if (this.player.dead)
/*      */       return; 
/*  822 */     if (packet19entityaction.animation == 1 || packet19entityaction.animation == 2) {
/*  823 */       PlayerToggleSneakEvent event = new PlayerToggleSneakEvent(getPlayer(), (packet19entityaction.animation == 1) ? 1 : 0);
/*  824 */       this.server.getPluginManager().callEvent(event);
/*      */       
/*  826 */       if (event.isCancelled()) {
/*      */         return;
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/*  832 */     if (packet19entityaction.animation == 1) {
/*  833 */       this.player.setSneak(true);
/*  834 */     } else if (packet19entityaction.animation == 2) {
/*  835 */       this.player.setSneak(false);
/*  836 */     } else if (packet19entityaction.animation == 3) {
/*  837 */       this.player.a(false, true, true);
/*  838 */       this.checkMovement = false;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*  843 */   public void a(Packet255KickDisconnect packet255kickdisconnect) { this.networkManager.a("disconnect.quitting", new Object[0]); }
/*      */ 
/*      */ 
/*      */   
/*  847 */   public int b() { return this.networkManager.e(); }
/*      */ 
/*      */ 
/*      */   
/*  851 */   public void sendMessage(String s) { sendPacket(new Packet3Chat("§7" + s)); }
/*      */ 
/*      */ 
/*      */   
/*  855 */   public String getName() { return this.player.name; }
/*      */ 
/*      */   
/*      */   public void a(Packet7UseEntity packet7useentity) {
/*  859 */     if (this.player.dead)
/*      */       return; 
/*  861 */     WorldServer worldserver = this.minecraftServer.getWorldServer(this.player.dimension);
/*  862 */     Entity entity = worldserver.getEntity(packet7useentity.target);
/*  863 */     ItemStack itemInHand = this.player.inventory.getItemInHand();
/*      */     
/*  865 */     if (entity != null && this.player.e(entity) && this.player.g(entity) < 36.0D) {
/*  866 */       if (packet7useentity.c == 0) {
/*      */         
/*  868 */         PlayerInteractEntityEvent event = new PlayerInteractEntityEvent(getPlayer(), entity.getBukkitEntity());
/*  869 */         this.server.getPluginManager().callEvent(event);
/*      */         
/*  871 */         if (event.isCancelled()) {
/*      */           return;
/*      */         }
/*      */         
/*  875 */         this.player.c(entity);
/*      */         
/*  877 */         if (itemInHand != null && itemInHand.count <= -1) {
/*  878 */           this.player.updateInventory(this.player.activeContainer);
/*      */         }
/*      */       }
/*  881 */       else if (packet7useentity.c == 1) {
/*  882 */         this.player.d(entity);
/*      */         
/*  884 */         if (itemInHand != null && itemInHand.count <= -1) {
/*  885 */           this.player.updateInventory(this.player.activeContainer);
/*      */         }
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public void a(Packet9Respawn packet9respawn) {
/*  893 */     if (this.player.health <= 0) {
/*  894 */       this.player = this.minecraftServer.serverConfigurationManager.moveToWorld(this.player, 0);
/*      */       
/*  896 */       getPlayer().setHandle(this.player);
/*      */     } 
/*      */   }
/*      */   
/*      */   public void a(Packet101CloseWindow packet101closewindow) {
/*  901 */     if (this.player.dead)
/*      */       return; 
/*  903 */     this.player.A();
/*      */   }
/*      */   
/*      */   public void a(Packet102WindowClick packet102windowclick) {
/*  907 */     if (this.player.dead)
/*      */       return; 
/*  909 */     if (this.player.activeContainer.windowId == packet102windowclick.a && this.player.activeContainer.c(this.player)) {
/*  910 */       ItemStack itemstack = this.player.activeContainer.a(packet102windowclick.b, packet102windowclick.c, packet102windowclick.f, this.player);
/*      */       
/*  912 */       if (ItemStack.equals(packet102windowclick.e, itemstack)) {
/*  913 */         this.player.netServerHandler.sendPacket(new Packet106Transaction(packet102windowclick.a, packet102windowclick.d, true));
/*  914 */         this.player.h = true;
/*  915 */         this.player.activeContainer.a();
/*  916 */         this.player.z();
/*  917 */         this.player.h = false;
/*      */       } else {
/*  919 */         this.n.put(Integer.valueOf(this.player.activeContainer.windowId), Short.valueOf(packet102windowclick.d));
/*  920 */         this.player.netServerHandler.sendPacket(new Packet106Transaction(packet102windowclick.a, packet102windowclick.d, false));
/*  921 */         this.player.activeContainer.a(this.player, false);
/*  922 */         ArrayList arraylist = new ArrayList();
/*      */         
/*  924 */         for (int i = 0; i < this.player.activeContainer.e.size(); i++) {
/*  925 */           arraylist.add(((Slot)this.player.activeContainer.e.get(i)).getItem());
/*      */         }
/*      */         
/*  928 */         this.player.a(this.player.activeContainer, arraylist);
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   public void a(Packet106Transaction packet106transaction) {
/*  934 */     if (this.player.dead)
/*      */       return; 
/*  936 */     Short oshort = (Short)this.n.get(Integer.valueOf(this.player.activeContainer.windowId));
/*      */     
/*  938 */     if (oshort != null && packet106transaction.b == oshort.shortValue() && this.player.activeContainer.windowId == packet106transaction.a && !this.player.activeContainer.c(this.player)) {
/*  939 */       this.player.activeContainer.a(this.player, true);
/*      */     }
/*      */   }
/*      */   
/*      */   public void a(Packet130UpdateSign packet130updatesign) {
/*  944 */     if (this.player.dead)
/*      */       return; 
/*  946 */     WorldServer worldserver = this.minecraftServer.getWorldServer(this.player.dimension);
/*      */     
/*  948 */     if (worldserver.isLoaded(packet130updatesign.x, packet130updatesign.y, packet130updatesign.z)) {
/*  949 */       TileEntity tileentity = worldserver.getTileEntity(packet130updatesign.x, packet130updatesign.y, packet130updatesign.z);
/*      */       
/*  951 */       if (tileentity instanceof TileEntitySign) {
/*  952 */         TileEntitySign tileentitysign = (TileEntitySign)tileentity;
/*      */         
/*  954 */         if (!tileentitysign.a()) {
/*  955 */           this.minecraftServer.c("Player " + this.player.name + " just tried to change non-editable sign");
/*      */           
/*  957 */           sendPacket(new Packet130UpdateSign(packet130updatesign.x, packet130updatesign.y, packet130updatesign.z, tileentitysign.lines));
/*      */           
/*      */           return;
/*      */         } 
/*      */       } 
/*      */       
/*      */       int j;
/*      */       
/*  965 */       for (j = 0; j < 4; j++) {
/*  966 */         boolean flag = true;
/*      */         
/*  968 */         if (packet130updatesign.lines[j].length() > 15) {
/*  969 */           flag = false;
/*      */         } else {
/*  971 */           for (int i = 0; i < packet130updatesign.lines[j].length(); i++) {
/*  972 */             if (FontAllowedCharacters.allowedCharacters.indexOf(packet130updatesign.lines[j].charAt(i)) < 0) {
/*  973 */               flag = false;
/*      */             }
/*      */           } 
/*      */         } 
/*      */         
/*  978 */         if (!flag) {
/*  979 */           packet130updatesign.lines[j] = "!?";
/*      */         }
/*      */       } 
/*      */       
/*  983 */       if (tileentity instanceof TileEntitySign) {
/*  984 */         j = packet130updatesign.x;
/*  985 */         int k = packet130updatesign.y;
/*      */         
/*  987 */         int i = packet130updatesign.z;
/*  988 */         TileEntitySign tileentitysign1 = (TileEntitySign)tileentity;
/*      */ 
/*      */         
/*  991 */         Player player = this.server.getPlayer(this.player);
/*  992 */         SignChangeEvent event = new SignChangeEvent((CraftBlock)player.getWorld().getBlockAt(j, k, i), this.server.getPlayer(this.player), packet130updatesign.lines);
/*  993 */         this.server.getPluginManager().callEvent(event);
/*      */         
/*  995 */         if (!event.isCancelled()) {
/*  996 */           for (int l = 0; l < 4; l++) {
/*  997 */             tileentitysign1.lines[l] = event.getLine(l);
/*      */           }
/*  999 */           tileentitysign1.a(false);
/*      */         } 
/*      */ 
/*      */         
/* 1003 */         tileentitysign1.update();
/* 1004 */         worldserver.notify(j, k, i);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/* 1010 */   public boolean c() { return true; }
/*      */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\NetServerHandler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */