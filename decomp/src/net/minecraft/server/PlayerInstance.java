/*     */ package net.minecraft.server;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ class PlayerInstance
/*     */ {
/*     */   private List b;
/*     */   private int chunkX;
/*     */   private int chunkZ;
/*     */   private ChunkCoordIntPair location;
/*     */   private short[] dirtyBlocks;
/*     */   private int dirtyCount;
/*     */   private int h;
/*     */   private int i;
/*     */   private int j;
/*     */   private int k;
/*     */   private int l;
/*     */   private int m;
/*     */   final PlayerManager playerManager;
/*     */   
/*     */   public PlayerInstance(PlayerManager playermanager, int i, int j) {
/*  24 */     this.playerManager = playermanager;
/*  25 */     this.b = new ArrayList();
/*  26 */     this.dirtyBlocks = new short[10];
/*  27 */     this.dirtyCount = 0;
/*  28 */     this.chunkX = i;
/*  29 */     this.chunkZ = j;
/*  30 */     this.location = new ChunkCoordIntPair(i, j);
/*  31 */     (playermanager.a()).chunkProviderServer.getChunkAt(i, j);
/*     */   }
/*     */   
/*     */   public void a(EntityPlayer entityplayer) {
/*  35 */     if (this.b.contains(entityplayer)) {
/*  36 */       throw new IllegalStateException("Failed to add player. " + entityplayer + " already is in chunk " + this.chunkX + ", " + this.chunkZ);
/*     */     }
/*     */     
/*  39 */     if (entityplayer.playerChunkCoordIntPairs.add(this.location)) {
/*  40 */       entityplayer.netServerHandler.sendPacket(new Packet50PreChunk(this.location.x, this.location.z, true));
/*     */     }
/*     */ 
/*     */     
/*  44 */     this.b.add(entityplayer);
/*  45 */     entityplayer.chunkCoordIntPairQueue.add(this.location);
/*     */   }
/*     */ 
/*     */   
/*     */   public void b(EntityPlayer entityplayer) {
/*  50 */     if (this.b.contains(entityplayer)) {
/*  51 */       this.b.remove(entityplayer);
/*  52 */       if (this.b.size() == 0) {
/*  53 */         long i = this.chunkX + 2147483647L | this.chunkZ + 2147483647L << 32;
/*     */         
/*  55 */         PlayerManager.a(this.playerManager).b(i);
/*  56 */         if (this.dirtyCount > 0) {
/*  57 */           PlayerManager.b(this.playerManager).remove(this);
/*     */         }
/*     */         
/*  60 */         (this.playerManager.a()).chunkProviderServer.queueUnload(this.chunkX, this.chunkZ);
/*     */       } 
/*     */       
/*  63 */       entityplayer.chunkCoordIntPairQueue.remove(this.location);
/*     */       
/*  65 */       if (entityplayer.playerChunkCoordIntPairs.remove(this.location)) {
/*  66 */         entityplayer.netServerHandler.sendPacket(new Packet50PreChunk(this.chunkX, this.chunkZ, false));
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public void a(int i, int j, int k) {
/*  72 */     if (this.dirtyCount == 0) {
/*  73 */       PlayerManager.b(this.playerManager).add(this);
/*  74 */       this.h = this.i = i;
/*  75 */       this.j = this.k = j;
/*  76 */       this.l = this.m = k;
/*     */     } 
/*     */     
/*  79 */     if (this.h > i) {
/*  80 */       this.h = i;
/*     */     }
/*     */     
/*  83 */     if (this.i < i) {
/*  84 */       this.i = i;
/*     */     }
/*     */     
/*  87 */     if (this.j > j) {
/*  88 */       this.j = j;
/*     */     }
/*     */     
/*  91 */     if (this.k < j) {
/*  92 */       this.k = j;
/*     */     }
/*     */     
/*  95 */     if (this.l > k) {
/*  96 */       this.l = k;
/*     */     }
/*     */     
/*  99 */     if (this.m < k) {
/* 100 */       this.m = k;
/*     */     }
/*     */     
/* 103 */     if (this.dirtyCount < 10) {
/* 104 */       short short1 = (short)(i << 12 | k << 8 | j);
/*     */       
/* 106 */       for (int l = 0; l < this.dirtyCount; l++) {
/* 107 */         if (this.dirtyBlocks[l] == short1) {
/*     */           return;
/*     */         }
/*     */       } 
/*     */       
/* 112 */       this.dirtyBlocks[this.dirtyCount++] = short1;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void sendAll(Packet packet) {
/* 117 */     for (int i = 0; i < this.b.size(); i++) {
/* 118 */       EntityPlayer entityplayer = (EntityPlayer)this.b.get(i);
/*     */       
/* 120 */       if (entityplayer.playerChunkCoordIntPairs.contains(this.location)) {
/* 121 */         entityplayer.netServerHandler.sendPacket(packet);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public void a() {
/* 127 */     WorldServer worldserver = this.playerManager.a();
/*     */     
/* 129 */     if (this.dirtyCount != 0) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 134 */       if (this.dirtyCount == 1) {
/* 135 */         int i = this.chunkX * 16 + this.h;
/* 136 */         int j = this.j;
/* 137 */         int k = this.chunkZ * 16 + this.l;
/* 138 */         sendAll(new Packet53BlockChange(i, j, k, worldserver));
/* 139 */         if (Block.isTileEntity[worldserver.getTypeId(i, j, k)]) {
/* 140 */           sendTileEntity(worldserver.getTileEntity(i, j, k));
/*     */         
/*     */         }
/*     */       
/*     */       }
/* 145 */       else if (this.dirtyCount == 10) {
/* 146 */         this.j = this.j / 2 * 2;
/* 147 */         this.k = (this.k / 2 + 1) * 2;
/* 148 */         int i = this.h + this.chunkX * 16;
/* 149 */         int j = this.j;
/* 150 */         int k = this.l + this.chunkZ * 16;
/* 151 */         int l = this.i - this.h + 1;
/* 152 */         int i1 = this.k - this.j + 2;
/* 153 */         int j1 = this.m - this.l + 1;
/*     */         
/* 155 */         sendAll(new Packet51MapChunk(i, j, k, l, i1, j1, worldserver));
/* 156 */         List list = worldserver.getTileEntities(i, j, k, i + l, j + i1, k + j1);
/*     */         
/* 158 */         for (int k1 = 0; k1 < list.size(); k1++) {
/* 159 */           sendTileEntity((TileEntity)list.get(k1));
/*     */         }
/*     */       } else {
/* 162 */         sendAll(new Packet52MultiBlockChange(this.chunkX, this.chunkZ, this.dirtyBlocks, this.dirtyCount, worldserver));
/*     */         
/* 164 */         for (int i = 0; i < this.dirtyCount; i++) {
/*     */           
/* 166 */           int j = this.chunkX * 16 + (this.dirtyBlocks[i] >> 12 & 0xF);
/* 167 */           int k = this.dirtyBlocks[i] & 0xFF;
/* 168 */           int l = this.chunkZ * 16 + (this.dirtyBlocks[i] >> 8 & 0xF);
/*     */ 
/*     */           
/* 171 */           if (Block.isTileEntity[worldserver.getTypeId(j, k, l)])
/*     */           {
/* 173 */             sendTileEntity(worldserver.getTileEntity(j, k, l));
/*     */           }
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 179 */       this.dirtyCount = 0;
/*     */     } 
/*     */   }
/*     */   
/*     */   private void sendTileEntity(TileEntity tileentity) {
/* 184 */     if (tileentity != null) {
/* 185 */       Packet packet = tileentity.f();
/*     */       
/* 187 */       if (packet != null)
/* 188 */         sendAll(packet); 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\PlayerInstance.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */