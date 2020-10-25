/*     */ package net.minecraft.server;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.Comparator;
/*     */ import java.util.List;
/*     */ 
/*     */ public class PlayerManager {
/*   8 */   public List managedPlayers = new ArrayList();
/*   9 */   private PlayerList b = new PlayerList();
/*  10 */   private List c = new ArrayList();
/*     */   private MinecraftServer server;
/*     */   private int e;
/*     */   private int f;
/*  14 */   private final int[][] g = { { 1, 0 }, { 0, 1 }, { -1, 0 }, { 0, -1 } };
/*     */   
/*     */   public PlayerManager(MinecraftServer minecraftserver, int i, int j) {
/*  17 */     if (j > 15)
/*  18 */       throw new IllegalArgumentException("Too big view radius!"); 
/*  19 */     if (j < 3) {
/*  20 */       throw new IllegalArgumentException("Too small view radius!");
/*     */     }
/*  22 */     this.f = j;
/*  23 */     this.server = minecraftserver;
/*  24 */     this.e = i;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*  29 */   public WorldServer a() { return this.server.getWorldServer(this.e); }
/*     */ 
/*     */   
/*     */   public void flush() {
/*  33 */     for (int i = 0; i < this.c.size(); i++) {
/*  34 */       ((PlayerInstance)this.c.get(i)).a();
/*     */     }
/*     */     
/*  37 */     this.c.clear();
/*     */   }
/*     */   
/*     */   private PlayerInstance a(int i, int j, boolean flag) {
/*  41 */     long k = i + 2147483647L | j + 2147483647L << 32;
/*  42 */     PlayerInstance playerinstance = (PlayerInstance)this.b.a(k);
/*     */     
/*  44 */     if (playerinstance == null && flag) {
/*  45 */       playerinstance = new PlayerInstance(this, i, j);
/*  46 */       this.b.a(k, playerinstance);
/*     */     } 
/*     */     
/*  49 */     return playerinstance;
/*     */   }
/*     */   
/*     */   public void flagDirty(int i, int j, int k) {
/*  53 */     int l = i >> 4;
/*  54 */     int i1 = k >> 4;
/*  55 */     PlayerInstance playerinstance = a(l, i1, false);
/*     */     
/*  57 */     if (playerinstance != null) {
/*  58 */       playerinstance.a(i & 0xF, j, k & 0xF);
/*     */     }
/*     */   }
/*     */   
/*     */   public void addPlayer(EntityPlayer entityplayer) {
/*  63 */     int i = (int)entityplayer.locX >> 4;
/*  64 */     int j = (int)entityplayer.locZ >> 4;
/*     */     
/*  66 */     entityplayer.d = entityplayer.locX;
/*  67 */     entityplayer.e = entityplayer.locZ;
/*  68 */     int k = 0;
/*  69 */     int l = this.f;
/*  70 */     int i1 = 0;
/*  71 */     int j1 = 0;
/*     */     
/*  73 */     a(i, j, true).a(entityplayer);
/*     */     
/*     */     int k1;
/*     */     
/*  77 */     for (k1 = 1; k1 <= l * 2; k1++) {
/*  78 */       for (int l1 = 0; l1 < 2; l1++) {
/*  79 */         int[] aint = this.g[k++ % 4];
/*     */         
/*  81 */         for (int i2 = 0; i2 < k1; i2++) {
/*  82 */           i1 += aint[0];
/*  83 */           j1 += aint[1];
/*  84 */           a(i + i1, j + j1, true).a(entityplayer);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/*  89 */     k %= 4;
/*     */     
/*  91 */     for (k1 = 0; k1 < l * 2; k1++) {
/*  92 */       i1 += this.g[k][0];
/*  93 */       j1 += this.g[k][1];
/*  94 */       a(i + i1, j + j1, true).a(entityplayer);
/*     */     } 
/*     */     
/*  97 */     this.managedPlayers.add(entityplayer);
/*     */   }
/*     */   
/*     */   public void removePlayer(EntityPlayer entityplayer) {
/* 101 */     int i = (int)entityplayer.d >> 4;
/* 102 */     int j = (int)entityplayer.e >> 4;
/*     */     
/* 104 */     for (int k = i - this.f; k <= i + this.f; k++) {
/* 105 */       for (int l = j - this.f; l <= j + this.f; l++) {
/* 106 */         PlayerInstance playerinstance = a(k, l, false);
/*     */         
/* 108 */         if (playerinstance != null) {
/* 109 */           playerinstance.b(entityplayer);
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 114 */     this.managedPlayers.remove(entityplayer);
/*     */   }
/*     */   
/*     */   private boolean a(int i, int j, int k, int l) {
/* 118 */     int i1 = i - k;
/* 119 */     int j1 = j - l;
/*     */     
/* 121 */     return (i1 >= -this.f && i1 <= this.f) ? ((j1 >= -this.f && j1 <= this.f)) : false;
/*     */   }
/*     */   
/*     */   public void movePlayer(EntityPlayer entityplayer) {
/* 125 */     int i = (int)entityplayer.locX >> 4;
/* 126 */     int j = (int)entityplayer.locZ >> 4;
/* 127 */     double d0 = entityplayer.d - entityplayer.locX;
/* 128 */     double d1 = entityplayer.e - entityplayer.locZ;
/* 129 */     double d2 = d0 * d0 + d1 * d1;
/*     */     
/* 131 */     if (d2 >= 64.0D) {
/* 132 */       int k = (int)entityplayer.d >> 4;
/* 133 */       int l = (int)entityplayer.e >> 4;
/* 134 */       int i1 = i - k;
/* 135 */       int j1 = j - l;
/*     */       
/* 137 */       if (i1 != 0 || j1 != 0) {
/* 138 */         for (int k1 = i - this.f; k1 <= i + this.f; k1++) {
/* 139 */           for (int l1 = j - this.f; l1 <= j + this.f; l1++) {
/* 140 */             if (!a(k1, l1, k, l)) {
/* 141 */               a(k1, l1, true).a(entityplayer);
/*     */             }
/*     */             
/* 144 */             if (!a(k1 - i1, l1 - j1, i, j)) {
/* 145 */               PlayerInstance playerinstance = a(k1 - i1, l1 - j1, false);
/*     */               
/* 147 */               if (playerinstance != null) {
/* 148 */                 playerinstance.b(entityplayer);
/*     */               }
/*     */             } 
/*     */           } 
/*     */         } 
/*     */         
/* 154 */         entityplayer.d = entityplayer.locX;
/* 155 */         entityplayer.e = entityplayer.locZ;
/*     */ 
/*     */         
/* 158 */         if (i1 > 1 || i1 < -1 || j1 > 1 || j1 < -1) {
/* 159 */           final int x = i;
/* 160 */           final int z = j;
/* 161 */           List<ChunkCoordIntPair> chunksToSend = entityplayer.chunkCoordIntPairQueue;
/*     */           
/* 163 */           Collections.sort(chunksToSend, new Comparator<ChunkCoordIntPair>()
/*     */               {
/* 165 */                 public int compare(ChunkCoordIntPair a, ChunkCoordIntPair b) { return Math.max(Math.abs(a.x - x), Math.abs(a.z - z)) - Math.max(Math.abs(b.x - x), Math.abs(b.z - z)); }
/*     */               });
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 175 */   public int getFurthestViewableBlock() { return this.f * 16 - 16; }
/*     */ 
/*     */ 
/*     */   
/* 179 */   static PlayerList a(PlayerManager playermanager) { return playermanager.b; }
/*     */ 
/*     */ 
/*     */   
/* 183 */   static List b(PlayerManager playermanager) { return playermanager.c; }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\PlayerManager.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */