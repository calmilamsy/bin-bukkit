/*     */ package net.minecraft.server;
/*     */ 
/*     */ import java.net.InetAddress;
/*     */ import java.net.ServerSocket;
/*     */ import java.util.ArrayList;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class NetworkListenThread
/*     */ {
/*     */   private ServerSocket d;
/*     */   private Thread e;
/*  15 */   public static Logger a = Logger.getLogger("Minecraft");
/*     */   private int f;
/*     */   
/*     */   public NetworkListenThread(MinecraftServer paramMinecraftServer, InetAddress paramInetAddress, int paramInt) {
/*  19 */     this.b = false;
/*  20 */     this.f = 0;
/*     */     
/*  22 */     this.g = new ArrayList();
/*  23 */     this.h = new ArrayList();
/*     */ 
/*     */ 
/*     */     
/*  27 */     this.c = paramMinecraftServer;
/*  28 */     this.d = new ServerSocket(paramInt, false, paramInetAddress);
/*  29 */     this.d.setPerformancePreferences(0, 2, 1);
/*     */     
/*  31 */     this.b = true;
/*  32 */     this.e = new NetworkAcceptThread(this, "Listen thread", paramMinecraftServer);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  63 */     this.e.start();
/*     */   }
/*     */   
/*     */   private ArrayList g;
/*     */   
/*  68 */   public void a(NetServerHandler paramNetServerHandler) { this.h.add(paramNetServerHandler); }
/*     */   private ArrayList h; public MinecraftServer c;
/*     */   
/*     */   private void a(NetLoginHandler paramNetLoginHandler) {
/*  72 */     if (paramNetLoginHandler == null) {
/*  73 */       throw new IllegalArgumentException("Got null pendingconnection!");
/*     */     }
/*  75 */     this.g.add(paramNetLoginHandler);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void a() {
/*     */     byte b1;
/*  88 */     for (b1 = 0; b1 < this.g.size(); b1++) {
/*  89 */       NetLoginHandler netLoginHandler = (NetLoginHandler)this.g.get(b1);
/*     */       try {
/*  91 */         netLoginHandler.a();
/*  92 */       } catch (Exception exception) {
/*  93 */         netLoginHandler.disconnect("Internal server error");
/*  94 */         a.log(Level.WARNING, "Failed to handle packet: " + exception, exception);
/*     */       } 
/*  96 */       if (netLoginHandler.c) {
/*  97 */         this.g.remove(b1--);
/*     */       }
/*  99 */       netLoginHandler.networkManager.a();
/*     */     } 
/*     */     
/* 102 */     for (b1 = 0; b1 < this.h.size(); b1++) {
/* 103 */       NetServerHandler netServerHandler = (NetServerHandler)this.h.get(b1);
/*     */       try {
/* 105 */         netServerHandler.a();
/* 106 */       } catch (Exception exception) {
/* 107 */         a.log(Level.WARNING, "Failed to handle packet: " + exception, exception);
/* 108 */         netServerHandler.disconnect("Internal server error");
/*     */       } 
/* 110 */       if (netServerHandler.disconnected) {
/* 111 */         this.h.remove(b1--);
/*     */       }
/* 113 */       netServerHandler.networkManager.a();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\NetworkListenThread.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */