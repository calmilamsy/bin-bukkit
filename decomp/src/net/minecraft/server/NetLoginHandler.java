/*     */ package net.minecraft.server;
/*     */ 
/*     */ import java.net.Socket;
/*     */ import java.util.Random;
/*     */ import java.util.logging.Logger;
/*     */ 
/*     */ public class NetLoginHandler
/*     */   extends NetHandler {
/*   9 */   public static Logger a = Logger.getLogger("Minecraft"); public NetworkManager networkManager; public boolean c;
/*  10 */   private static Random d = new Random(); private MinecraftServer server;
/*     */   public NetLoginHandler(MinecraftServer minecraftserver, Socket socket, String s) {
/*  12 */     this.c = false;
/*     */     
/*  14 */     this.f = 0;
/*  15 */     this.g = null;
/*  16 */     this.h = null;
/*  17 */     this.i = "";
/*     */ 
/*     */     
/*  20 */     this.server = minecraftserver;
/*  21 */     this.networkManager = new NetworkManager(socket, s, this);
/*  22 */     this.networkManager.f = 0;
/*     */   }
/*     */   private int f; private String g; private Packet1Login h;
/*     */   private String i;
/*     */   
/*  27 */   public Socket getSocket() { return this.networkManager.socket; }
/*     */ 
/*     */ 
/*     */   
/*     */   public void a() {
/*  32 */     if (this.h != null) {
/*  33 */       b(this.h);
/*  34 */       this.h = null;
/*     */     } 
/*     */     
/*  37 */     if (this.f++ == 600) {
/*  38 */       disconnect("Took too long to log in");
/*     */     } else {
/*  40 */       this.networkManager.b();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void disconnect(String s) {
/*     */     try {
/*  46 */       a.info("Disconnecting " + b() + ": " + s);
/*  47 */       this.networkManager.queue(new Packet255KickDisconnect(s));
/*  48 */       this.networkManager.d();
/*  49 */       this.c = true;
/*  50 */     } catch (Exception exception) {
/*  51 */       exception.printStackTrace();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void a(Packet2Handshake packet2handshake) {
/*  56 */     if (this.server.onlineMode) {
/*  57 */       this.i = Long.toHexString(d.nextLong());
/*  58 */       this.networkManager.queue(new Packet2Handshake(this.i));
/*     */     } else {
/*  60 */       this.networkManager.queue(new Packet2Handshake("-"));
/*     */     } 
/*     */   }
/*     */   
/*     */   public void a(Packet1Login packet1login) {
/*  65 */     this.g = packet1login.name;
/*  66 */     if (packet1login.a != 14) {
/*  67 */       if (packet1login.a > 14) {
/*  68 */         disconnect("Outdated server!");
/*     */       } else {
/*  70 */         disconnect("Outdated client!");
/*     */       }
/*     */     
/*  73 */     } else if (!this.server.onlineMode) {
/*  74 */       b(packet1login);
/*     */     } else {
/*  76 */       (new ThreadLoginVerifier(this, packet1login, this.server.server)).start();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void b(Packet1Login packet1login) {
/*  82 */     EntityPlayer entityplayer = this.server.serverConfigurationManager.a(this, packet1login.name);
/*     */     
/*  84 */     if (entityplayer != null) {
/*  85 */       this.server.serverConfigurationManager.b(entityplayer);
/*     */ 
/*     */       
/*  88 */       a.info(b() + " logged in with entity id " + entityplayer.id + " at ([" + entityplayer.world.worldData.name + "] " + entityplayer.locX + ", " + entityplayer.locY + ", " + entityplayer.locZ + ")");
/*  89 */       WorldServer worldserver = (WorldServer)entityplayer.world;
/*  90 */       ChunkCoordinates chunkcoordinates = worldserver.getSpawn();
/*  91 */       NetServerHandler netserverhandler = new NetServerHandler(this.server, this.networkManager, entityplayer);
/*     */       
/*  93 */       netserverhandler.sendPacket(new Packet1Login("", entityplayer.id, worldserver.getSeed(), (byte)worldserver.worldProvider.dimension));
/*  94 */       netserverhandler.sendPacket(new Packet6SpawnPosition(chunkcoordinates.x, chunkcoordinates.y, chunkcoordinates.z));
/*  95 */       this.server.serverConfigurationManager.a(entityplayer, worldserver);
/*     */       
/*  97 */       this.server.serverConfigurationManager.c(entityplayer);
/*  98 */       netserverhandler.a(entityplayer.locX, entityplayer.locY, entityplayer.locZ, entityplayer.yaw, entityplayer.pitch);
/*  99 */       this.server.networkListenThread.a(netserverhandler);
/* 100 */       netserverhandler.sendPacket(new Packet4UpdateTime(entityplayer.getPlayerTime()));
/* 101 */       entityplayer.syncInventory();
/*     */     } 
/*     */     
/* 104 */     this.c = true;
/*     */   }
/*     */   
/*     */   public void a(String s, Object[] aobject) {
/* 108 */     a.info(b() + " lost connection");
/* 109 */     this.c = true;
/*     */   }
/*     */ 
/*     */   
/* 113 */   public void a(Packet packet) { disconnect("Protocol error"); }
/*     */ 
/*     */ 
/*     */   
/* 117 */   public String b() { return (this.g != null) ? (this.g + " [" + this.networkManager.getSocketAddress().toString() + "]") : this.networkManager.getSocketAddress().toString(); }
/*     */ 
/*     */ 
/*     */   
/* 121 */   public boolean c() { return true; }
/*     */ 
/*     */ 
/*     */   
/* 125 */   static String a(NetLoginHandler netloginhandler) { return netloginhandler.i; }
/*     */ 
/*     */ 
/*     */   
/* 129 */   static Packet1Login a(NetLoginHandler netloginhandler, Packet1Login packet1login) { return netloginhandler.h = packet1login; }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\NetLoginHandler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */