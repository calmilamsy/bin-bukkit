/*     */ package net.minecraft.server;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class MinecartTrackLogic
/*     */ {
/*     */   private World b;
/*     */   private int c;
/*     */   private int d;
/*     */   private int e;
/*     */   private final boolean f;
/*     */   private List g;
/*     */   
/*     */   public MinecartTrackLogic(BlockMinecartTrack paramBlockMinecartTrack, World paramWorld, int paramInt1, int paramInt2, int paramInt3) {
/*  23 */     this.g = new ArrayList();
/*     */ 
/*     */     
/*  26 */     this.b = paramWorld;
/*  27 */     this.c = paramInt1;
/*  28 */     this.d = paramInt2;
/*  29 */     this.e = paramInt3;
/*     */     
/*  31 */     int i = paramWorld.getTypeId(paramInt1, paramInt2, paramInt3);
/*  32 */     int j = paramWorld.getData(paramInt1, paramInt2, paramInt3);
/*  33 */     if (BlockMinecartTrack.a((BlockMinecartTrack)Block.byId[i])) {
/*  34 */       this.f = true;
/*  35 */       j &= 0xFFFFFFF7;
/*     */     } else {
/*  37 */       this.f = false;
/*     */     } 
/*  39 */     a(j);
/*     */   }
/*     */ 
/*     */   
/*     */   private void a(int paramInt) {
/*  44 */     this.g.clear();
/*  45 */     if (paramInt == 0) {
/*  46 */       this.g.add(new ChunkPosition(this.c, this.d, this.e - 1));
/*  47 */       this.g.add(new ChunkPosition(this.c, this.d, this.e + 1));
/*  48 */     } else if (paramInt == 1) {
/*  49 */       this.g.add(new ChunkPosition(this.c - 1, this.d, this.e));
/*  50 */       this.g.add(new ChunkPosition(this.c + 1, this.d, this.e));
/*  51 */     } else if (paramInt == 2) {
/*  52 */       this.g.add(new ChunkPosition(this.c - 1, this.d, this.e));
/*  53 */       this.g.add(new ChunkPosition(this.c + 1, this.d + 1, this.e));
/*  54 */     } else if (paramInt == 3) {
/*  55 */       this.g.add(new ChunkPosition(this.c - 1, this.d + 1, this.e));
/*  56 */       this.g.add(new ChunkPosition(this.c + 1, this.d, this.e));
/*  57 */     } else if (paramInt == 4) {
/*  58 */       this.g.add(new ChunkPosition(this.c, this.d + 1, this.e - 1));
/*  59 */       this.g.add(new ChunkPosition(this.c, this.d, this.e + 1));
/*  60 */     } else if (paramInt == 5) {
/*  61 */       this.g.add(new ChunkPosition(this.c, this.d, this.e - 1));
/*  62 */       this.g.add(new ChunkPosition(this.c, this.d + 1, this.e + 1));
/*  63 */     } else if (paramInt == 6) {
/*  64 */       this.g.add(new ChunkPosition(this.c + 1, this.d, this.e));
/*  65 */       this.g.add(new ChunkPosition(this.c, this.d, this.e + 1));
/*  66 */     } else if (paramInt == 7) {
/*  67 */       this.g.add(new ChunkPosition(this.c - 1, this.d, this.e));
/*  68 */       this.g.add(new ChunkPosition(this.c, this.d, this.e + 1));
/*  69 */     } else if (paramInt == 8) {
/*  70 */       this.g.add(new ChunkPosition(this.c - 1, this.d, this.e));
/*  71 */       this.g.add(new ChunkPosition(this.c, this.d, this.e - 1));
/*  72 */     } else if (paramInt == 9) {
/*  73 */       this.g.add(new ChunkPosition(this.c + 1, this.d, this.e));
/*  74 */       this.g.add(new ChunkPosition(this.c, this.d, this.e - 1));
/*     */     } 
/*     */   }
/*     */   
/*     */   private void a() {
/*  79 */     for (byte b1 = 0; b1 < this.g.size(); b1++) {
/*  80 */       MinecartTrackLogic minecartTrackLogic = a((ChunkPosition)this.g.get(b1));
/*  81 */       if (minecartTrackLogic == null || !minecartTrackLogic.b(this)) {
/*  82 */         this.g.remove(b1--);
/*     */       } else {
/*  84 */         this.g.set(b1, new ChunkPosition(minecartTrackLogic.c, minecartTrackLogic.d, minecartTrackLogic.e));
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean a(int paramInt1, int paramInt2, int paramInt3) {
/*  90 */     if (BlockMinecartTrack.g(this.b, paramInt1, paramInt2, paramInt3)) return true; 
/*  91 */     if (BlockMinecartTrack.g(this.b, paramInt1, paramInt2 + 1, paramInt3)) return true; 
/*  92 */     if (BlockMinecartTrack.g(this.b, paramInt1, paramInt2 - 1, paramInt3)) return true; 
/*  93 */     return false;
/*     */   }
/*     */   
/*     */   private MinecartTrackLogic a(ChunkPosition paramChunkPosition) {
/*  97 */     if (BlockMinecartTrack.g(this.b, paramChunkPosition.x, paramChunkPosition.y, paramChunkPosition.z)) return new MinecartTrackLogic(this.a, this.b, paramChunkPosition.x, paramChunkPosition.y, paramChunkPosition.z); 
/*  98 */     if (BlockMinecartTrack.g(this.b, paramChunkPosition.x, paramChunkPosition.y + 1, paramChunkPosition.z)) return new MinecartTrackLogic(this.a, this.b, paramChunkPosition.x, paramChunkPosition.y + 1, paramChunkPosition.z); 
/*  99 */     if (BlockMinecartTrack.g(this.b, paramChunkPosition.x, paramChunkPosition.y - 1, paramChunkPosition.z)) return new MinecartTrackLogic(this.a, this.b, paramChunkPosition.x, paramChunkPosition.y - 1, paramChunkPosition.z); 
/* 100 */     return null;
/*     */   }
/*     */   
/*     */   private boolean b(MinecartTrackLogic paramMinecartTrackLogic) {
/* 104 */     for (byte b1 = 0; b1 < this.g.size(); b1++) {
/* 105 */       ChunkPosition chunkPosition = (ChunkPosition)this.g.get(b1);
/* 106 */       if (chunkPosition.x == paramMinecartTrackLogic.c && chunkPosition.z == paramMinecartTrackLogic.e) {
/* 107 */         return true;
/*     */       }
/*     */     } 
/* 110 */     return false;
/*     */   }
/*     */   
/*     */   private boolean b(int paramInt1, int paramInt2, int paramInt3) {
/* 114 */     for (byte b1 = 0; b1 < this.g.size(); b1++) {
/* 115 */       ChunkPosition chunkPosition = (ChunkPosition)this.g.get(b1);
/*     */       
/* 117 */       if (chunkPosition.x == paramInt1 && chunkPosition.z == paramInt3) {
/* 118 */         return true;
/*     */       }
/*     */     } 
/* 121 */     return false;
/*     */   }
/*     */   
/*     */   private int b() {
/* 125 */     byte b1 = 0;
/*     */     
/* 127 */     if (a(this.c, this.d, this.e - 1)) b1++; 
/* 128 */     if (a(this.c, this.d, this.e + 1)) b1++; 
/* 129 */     if (a(this.c - 1, this.d, this.e)) b1++; 
/* 130 */     if (a(this.c + 1, this.d, this.e)) b1++;
/*     */     
/* 132 */     return b1;
/*     */   }
/*     */   
/*     */   private boolean c(MinecartTrackLogic paramMinecartTrackLogic) {
/* 136 */     if (b(paramMinecartTrackLogic)) return true; 
/* 137 */     if (this.g.size() == 2) {
/* 138 */       return false;
/*     */     }
/* 140 */     if (this.g.size() == 0) {
/* 141 */       return true;
/*     */     }
/*     */     
/* 144 */     ChunkPosition chunkPosition = (ChunkPosition)this.g.get(0);
/* 145 */     if (paramMinecartTrackLogic.d == this.d && chunkPosition.y == this.d) return true;
/*     */     
/* 147 */     return true;
/*     */   }
/*     */   
/*     */   private void d(MinecartTrackLogic paramMinecartTrackLogic) {
/* 151 */     this.g.add(new ChunkPosition(paramMinecartTrackLogic.c, paramMinecartTrackLogic.d, paramMinecartTrackLogic.e));
/*     */     
/* 153 */     boolean bool1 = b(this.c, this.d, this.e - 1);
/* 154 */     boolean bool2 = b(this.c, this.d, this.e + 1);
/* 155 */     boolean bool3 = b(this.c - 1, this.d, this.e);
/* 156 */     boolean bool4 = b(this.c + 1, this.d, this.e);
/*     */     
/* 158 */     int i = -1;
/*     */     
/* 160 */     if (bool1 || bool2) i = 0; 
/* 161 */     if (bool3 || bool4) i = 1;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 167 */     if (!this.f) {
/* 168 */       if (bool2 && bool4 && !bool1 && !bool3) i = 6; 
/* 169 */       if (bool2 && bool3 && !bool1 && !bool4) i = 7; 
/* 170 */       if (bool1 && bool3 && !bool2 && !bool4) i = 8; 
/* 171 */       if (bool1 && bool4 && !bool2 && !bool3) i = 9; 
/*     */     } 
/* 173 */     if (i == 0) {
/* 174 */       if (BlockMinecartTrack.g(this.b, this.c, this.d + 1, this.e - 1)) i = 4; 
/* 175 */       if (BlockMinecartTrack.g(this.b, this.c, this.d + 1, this.e + 1)) i = 5; 
/*     */     } 
/* 177 */     if (i == 1) {
/* 178 */       if (BlockMinecartTrack.g(this.b, this.c + 1, this.d + 1, this.e)) i = 2; 
/* 179 */       if (BlockMinecartTrack.g(this.b, this.c - 1, this.d + 1, this.e)) i = 3;
/*     */     
/*     */     } 
/* 182 */     if (i < 0) i = 0;
/*     */     
/* 184 */     int j = i;
/* 185 */     if (this.f) {
/* 186 */       j = this.b.getData(this.c, this.d, this.e) & 0x8 | i;
/*     */     }
/*     */     
/* 189 */     this.b.setData(this.c, this.d, this.e, j);
/*     */   }
/*     */   
/*     */   private boolean c(int paramInt1, int paramInt2, int paramInt3) {
/* 193 */     MinecartTrackLogic minecartTrackLogic = a(new ChunkPosition(paramInt1, paramInt2, paramInt3));
/* 194 */     if (minecartTrackLogic == null) return false; 
/* 195 */     minecartTrackLogic.a();
/* 196 */     return minecartTrackLogic.c(this);
/*     */   }
/*     */   
/*     */   public void a(boolean paramBoolean1, boolean paramBoolean2) {
/* 200 */     boolean bool1 = c(this.c, this.d, this.e - 1);
/* 201 */     boolean bool2 = c(this.c, this.d, this.e + 1);
/* 202 */     boolean bool3 = c(this.c - 1, this.d, this.e);
/* 203 */     boolean bool4 = c(this.c + 1, this.d, this.e);
/*     */     
/* 205 */     int i = -1;
/*     */     
/* 207 */     if ((bool1 || bool2) && !bool3 && !bool4) i = 0; 
/* 208 */     if ((bool3 || bool4) && !bool1 && !bool2) i = 1;
/*     */     
/* 210 */     if (!this.f) {
/* 211 */       if (bool2 && bool4 && !bool1 && !bool3) i = 6; 
/* 212 */       if (bool2 && bool3 && !bool1 && !bool4) i = 7; 
/* 213 */       if (bool1 && bool3 && !bool2 && !bool4) i = 8; 
/* 214 */       if (bool1 && bool4 && !bool2 && !bool3) i = 9; 
/*     */     } 
/* 216 */     if (i == -1) {
/* 217 */       if (bool1 || bool2) i = 0; 
/* 218 */       if (bool3 || bool4) i = 1;
/*     */       
/* 220 */       if (!this.f) {
/* 221 */         if (paramBoolean1) {
/* 222 */           if (bool2 && bool4) i = 6; 
/* 223 */           if (bool3 && bool2) i = 7; 
/* 224 */           if (bool4 && bool1) i = 9; 
/* 225 */           if (bool1 && bool3) i = 8; 
/*     */         } else {
/* 227 */           if (bool1 && bool3) i = 8; 
/* 228 */           if (bool4 && bool1) i = 9; 
/* 229 */           if (bool3 && bool2) i = 7; 
/* 230 */           if (bool2 && bool4) i = 6;
/*     */         
/*     */         } 
/*     */       }
/*     */     } 
/* 235 */     if (i == 0) {
/* 236 */       if (BlockMinecartTrack.g(this.b, this.c, this.d + 1, this.e - 1)) i = 4; 
/* 237 */       if (BlockMinecartTrack.g(this.b, this.c, this.d + 1, this.e + 1)) i = 5; 
/*     */     } 
/* 239 */     if (i == 1) {
/* 240 */       if (BlockMinecartTrack.g(this.b, this.c + 1, this.d + 1, this.e)) i = 2; 
/* 241 */       if (BlockMinecartTrack.g(this.b, this.c - 1, this.d + 1, this.e)) i = 3;
/*     */     
/*     */     } 
/* 244 */     if (i < 0) i = 0;
/*     */     
/* 246 */     a(i);
/*     */     
/* 248 */     int j = i;
/* 249 */     if (this.f) {
/* 250 */       j = this.b.getData(this.c, this.d, this.e) & 0x8 | i;
/*     */     }
/*     */     
/* 253 */     if (paramBoolean2 || this.b.getData(this.c, this.d, this.e) != j) {
/* 254 */       this.b.setData(this.c, this.d, this.e, j);
/* 255 */       for (byte b1 = 0; b1 < this.g.size(); b1++) {
/* 256 */         MinecartTrackLogic minecartTrackLogic = a((ChunkPosition)this.g.get(b1));
/* 257 */         if (minecartTrackLogic != null) {
/* 258 */           minecartTrackLogic.a();
/*     */           
/* 260 */           if (minecartTrackLogic.c(this))
/* 261 */             minecartTrackLogic.d(this); 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\MinecartTrackLogic.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */