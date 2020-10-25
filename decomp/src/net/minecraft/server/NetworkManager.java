/*     */ package net.minecraft.server;
/*     */ 
/*     */ import java.io.BufferedOutputStream;
/*     */ import java.io.DataInputStream;
/*     */ import java.io.DataOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.net.Socket;
/*     */ import java.net.SocketAddress;
/*     */ import java.net.SocketException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ 
/*     */ public class NetworkManager {
/*  15 */   public static final Object a = new Object();
/*     */   public static int b;
/*     */   public static int c;
/*     */   private Object g;
/*     */   public Socket socket;
/*     */   private final SocketAddress i;
/*     */   private DataInputStream input;
/*     */   private DataOutputStream output;
/*     */   private boolean l;
/*     */   private List m;
/*     */   private List highPriorityQueue;
/*     */   private List lowPriorityQueue;
/*     */   private NetHandler p;
/*     */   private boolean q;
/*     */   private Thread r;
/*     */   private Thread s;
/*     */   private boolean t;
/*     */   private String u;
/*     */   private Object[] v;
/*     */   private int w;
/*     */   private int x;
/*  36 */   public static int[] d = new int[256];
/*  37 */   public static int[] e = new int[256]; public int f; public NetworkManager(Socket socket, String s, NetHandler nethandler) { this.g = new Object(); this.l = true; this.m = Collections.synchronizedList(new ArrayList()); this.highPriorityQueue = Collections.synchronizedList(new ArrayList()); this.lowPriorityQueue = Collections.synchronizedList(new ArrayList()); this.q = false; this.t = false; this.u = ""; this.w = 0; this.x = 0;
/*  38 */     this.f = 0;
/*  39 */     this.lowPriorityQueueDelay = 50;
/*     */ 
/*     */     
/*  42 */     this.socket = socket;
/*  43 */     this.i = socket.getRemoteSocketAddress();
/*  44 */     this.p = nethandler;
/*     */ 
/*     */     
/*     */     try {
/*  48 */       socket.setTrafficClass(24);
/*  49 */     } catch (SocketException e) {}
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/*  54 */       socket.setSoTimeout(30000);
/*  55 */       this.input = new DataInputStream(socket.getInputStream());
/*  56 */       this.output = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream(), '᐀'));
/*  57 */     } catch (IOException socketexception) {
/*     */       
/*  59 */       System.err.println(socketexception.getMessage());
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  66 */     this.s = new NetworkReaderThread(this, s + " read thread");
/*  67 */     this.r = new NetworkWriterThread(this, s + " write thread");
/*  68 */     this.s.start();
/*  69 */     this.r.start(); }
/*     */   
/*     */   private int lowPriorityQueueDelay;
/*     */   
/*  73 */   public void a(NetHandler nethandler) { this.p = nethandler; }
/*     */ 
/*     */   
/*     */   public void queue(Packet packet) {
/*  77 */     if (!this.q) {
/*  78 */       Object object = this.g;
/*     */       
/*  80 */       synchronized (this.g) {
/*  81 */         this.x += packet.a() + 1;
/*  82 */         if (packet.k) {
/*  83 */           this.lowPriorityQueue.add(packet);
/*     */         } else {
/*  85 */           this.highPriorityQueue.add(packet);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean f() {
/*  92 */     boolean flag = false;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 100 */       if (!this.highPriorityQueue.isEmpty() && (this.f == 0 || System.currentTimeMillis() - ((Packet)this.highPriorityQueue.get(0)).timestamp >= this.f)) {
/* 101 */         Packet packet; Object object = this.g;
/* 102 */         synchronized (this.g) {
/* 103 */           packet = (Packet)this.highPriorityQueue.remove(0);
/* 104 */           this.x -= packet.a() + 1;
/*     */         } 
/*     */         
/* 107 */         Packet.a(packet, this.output);
/* 108 */         int[] aint = e;
/* 109 */         int i = packet.b();
/* 110 */         aint[i] = aint[i] + packet.a() + 1;
/* 111 */         flag = true;
/*     */       } 
/*     */ 
/*     */       
/* 115 */       if ((flag || this.lowPriorityQueueDelay-- <= 0) && !this.lowPriorityQueue.isEmpty() && (this.highPriorityQueue.isEmpty() || ((Packet)this.highPriorityQueue.get(0)).timestamp > ((Packet)this.lowPriorityQueue.get(0)).timestamp)) {
/* 116 */         Packet packet; Object object = this.g;
/* 117 */         synchronized (this.g) {
/* 118 */           packet = (Packet)this.lowPriorityQueue.remove(0);
/* 119 */           this.x -= packet.a() + 1;
/*     */         } 
/*     */         
/* 122 */         Packet.a(packet, this.output);
/* 123 */         int[] aint = e;
/* 124 */         int i = packet.b();
/* 125 */         aint[i] = aint[i] + packet.a() + 1;
/* 126 */         this.lowPriorityQueueDelay = 0;
/* 127 */         flag = true;
/*     */       } 
/*     */       
/* 130 */       return flag;
/* 131 */     } catch (Exception exception) {
/* 132 */       if (!this.t) {
/* 133 */         a(exception);
/*     */       }
/*     */       
/* 136 */       return false;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void a() {
/* 141 */     this.s.interrupt();
/* 142 */     this.r.interrupt();
/*     */   }
/*     */   
/*     */   private boolean g() {
/* 146 */     boolean flag = false;
/*     */     
/*     */     try {
/* 149 */       Packet packet = Packet.a(this.input, this.p.c());
/*     */       
/* 151 */       if (packet != null) {
/* 152 */         int[] aint = d;
/* 153 */         int i = packet.b();
/*     */         
/* 155 */         aint[i] = aint[i] + packet.a() + 1;
/* 156 */         this.m.add(packet);
/* 157 */         flag = true;
/*     */       } else {
/* 159 */         a("disconnect.endOfStream", new Object[0]);
/*     */       } 
/*     */       
/* 162 */       return flag;
/* 163 */     } catch (Exception exception) {
/* 164 */       if (!this.t) {
/* 165 */         a(exception);
/*     */       }
/*     */       
/* 168 */       return false;
/*     */     } 
/*     */   }
/*     */   
/*     */   private void a(Exception exception) {
/* 173 */     exception.printStackTrace();
/* 174 */     a("disconnect.genericReason", new Object[] { "Internal exception: " + exception.toString() });
/*     */   }
/*     */   
/*     */   public void a(String s, Object... aobject) {
/* 178 */     if (this.l) {
/* 179 */       this.t = true;
/* 180 */       this.u = s;
/* 181 */       this.v = aobject;
/* 182 */       (new NetworkMasterThread(this)).start();
/* 183 */       this.l = false;
/*     */       
/*     */       try {
/* 186 */         this.input.close();
/* 187 */         this.input = null;
/* 188 */       } catch (Throwable throwable) {}
/*     */ 
/*     */ 
/*     */       
/*     */       try {
/* 193 */         this.output.close();
/* 194 */         this.output = null;
/* 195 */       } catch (Throwable throwable1) {}
/*     */ 
/*     */ 
/*     */       
/*     */       try {
/* 200 */         this.socket.close();
/* 201 */         this.socket = null;
/* 202 */       } catch (Throwable throwable2) {}
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void b() {
/* 209 */     if (this.x > 1048576) {
/* 210 */       a("disconnect.overflow", new Object[0]);
/*     */     }
/*     */     
/* 213 */     if (this.m.isEmpty()) {
/* 214 */       if (this.w++ == 1200) {
/* 215 */         a("disconnect.timeout", new Object[0]);
/*     */       }
/*     */     } else {
/* 218 */       this.w = 0;
/*     */     } 
/*     */     
/* 221 */     int i = 100;
/*     */     
/* 223 */     while (!this.m.isEmpty() && i-- >= 0) {
/* 224 */       Packet packet = (Packet)this.m.remove(0);
/*     */       
/* 226 */       packet.a(this.p);
/*     */     } 
/*     */     
/* 229 */     a();
/* 230 */     if (this.t && this.m.isEmpty()) {
/* 231 */       this.p.a(this.u, this.v);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/* 236 */   public SocketAddress getSocketAddress() { return this.i; }
/*     */ 
/*     */   
/*     */   public void d() {
/* 240 */     a();
/* 241 */     this.q = true;
/* 242 */     this.s.interrupt();
/* 243 */     (new ThreadMonitorConnection(this)).start();
/*     */   }
/*     */ 
/*     */   
/* 247 */   public int e() { return this.lowPriorityQueue.size(); }
/*     */ 
/*     */ 
/*     */   
/* 251 */   static boolean a(NetworkManager networkmanager) { return networkmanager.l; }
/*     */ 
/*     */ 
/*     */   
/* 255 */   static boolean b(NetworkManager networkmanager) { return networkmanager.q; }
/*     */ 
/*     */ 
/*     */   
/* 259 */   static boolean c(NetworkManager networkmanager) { return networkmanager.g(); }
/*     */ 
/*     */ 
/*     */   
/* 263 */   static boolean d(NetworkManager networkmanager) { return networkmanager.f(); }
/*     */ 
/*     */ 
/*     */   
/* 267 */   static DataOutputStream e(NetworkManager networkmanager) { return networkmanager.output; }
/*     */ 
/*     */ 
/*     */   
/* 271 */   static boolean f(NetworkManager networkmanager) { return networkmanager.t; }
/*     */ 
/*     */ 
/*     */   
/* 275 */   static void a(NetworkManager networkmanager, Exception exception) { networkmanager.a(exception); }
/*     */ 
/*     */ 
/*     */   
/* 279 */   static Thread g(NetworkManager networkmanager) { return networkmanager.s; }
/*     */ 
/*     */ 
/*     */   
/* 283 */   static Thread h(NetworkManager networkmanager) { return networkmanager.r; }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\NetworkManager.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */