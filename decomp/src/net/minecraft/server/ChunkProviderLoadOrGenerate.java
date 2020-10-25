/*     */ package net.minecraft.server;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ 
/*     */ public class ChunkProviderLoadOrGenerate
/*     */   implements IChunkProvider
/*     */ {
/*     */   private Set a;
/*     */   private Chunk b;
/*     */   private IChunkProvider c;
/*     */   
/*     */   public ChunkProviderLoadOrGenerate(World paramWorld, IChunkLoader paramIChunkLoader, IChunkProvider paramIChunkProvider) {
/*  19 */     this.a = new HashSet();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  24 */     this.e = new HashMap();
/*  25 */     this.f = new ArrayList();
/*     */ 
/*     */ 
/*     */     
/*  29 */     this.b = new EmptyChunk(paramWorld, new byte[32768], false, false);
/*     */     
/*  31 */     this.g = paramWorld;
/*  32 */     this.d = paramIChunkLoader;
/*  33 */     this.c = paramIChunkProvider;
/*     */   }
/*     */   private IChunkLoader d; private Map e; private List f; private World g;
/*     */   
/*  37 */   public boolean isChunkLoaded(int paramInt1, int paramInt2) { return this.e.containsKey(Integer.valueOf(ChunkCoordIntPair.a(paramInt1, paramInt2))); }
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
/*     */   public Chunk getChunkAt(int paramInt1, int paramInt2) {
/*  51 */     int i = ChunkCoordIntPair.a(paramInt1, paramInt2);
/*  52 */     this.a.remove(Integer.valueOf(i));
/*     */     
/*  54 */     Chunk chunk = (Chunk)this.e.get(Integer.valueOf(i));
/*  55 */     if (chunk == null) {
/*  56 */       chunk = d(paramInt1, paramInt2);
/*  57 */       if (chunk == null) {
/*  58 */         if (this.c == null) {
/*  59 */           chunk = this.b;
/*     */         } else {
/*  61 */           chunk = this.c.getOrCreateChunk(paramInt1, paramInt2);
/*     */         } 
/*     */       }
/*     */ 
/*     */       
/*  66 */       this.e.put(Integer.valueOf(i), chunk);
/*  67 */       this.f.add(chunk);
/*     */       
/*  69 */       if (chunk != null) {
/*  70 */         chunk.loadNOP();
/*  71 */         chunk.addEntities();
/*     */       } 
/*     */       
/*  74 */       if (!chunk.done && isChunkLoaded(paramInt1 + 1, paramInt2 + 1) && isChunkLoaded(paramInt1, paramInt2 + 1) && isChunkLoaded(paramInt1 + 1, paramInt2)) getChunkAt(this, paramInt1, paramInt2); 
/*  75 */       if (isChunkLoaded(paramInt1 - 1, paramInt2) && !(getOrCreateChunk(paramInt1 - 1, paramInt2)).done && isChunkLoaded(paramInt1 - 1, paramInt2 + 1) && isChunkLoaded(paramInt1, paramInt2 + 1) && isChunkLoaded(paramInt1 - 1, paramInt2)) getChunkAt(this, paramInt1 - 1, paramInt2); 
/*  76 */       if (isChunkLoaded(paramInt1, paramInt2 - 1) && !(getOrCreateChunk(paramInt1, paramInt2 - 1)).done && isChunkLoaded(paramInt1 + 1, paramInt2 - 1) && isChunkLoaded(paramInt1, paramInt2 - 1) && isChunkLoaded(paramInt1 + 1, paramInt2)) getChunkAt(this, paramInt1, paramInt2 - 1); 
/*  77 */       if (isChunkLoaded(paramInt1 - 1, paramInt2 - 1) && !(getOrCreateChunk(paramInt1 - 1, paramInt2 - 1)).done && isChunkLoaded(paramInt1 - 1, paramInt2 - 1) && isChunkLoaded(paramInt1, paramInt2 - 1) && isChunkLoaded(paramInt1 - 1, paramInt2)) getChunkAt(this, paramInt1 - 1, paramInt2 - 1);
/*     */     
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*  83 */     return chunk;
/*     */   }
/*     */ 
/*     */   
/*     */   public Chunk getOrCreateChunk(int paramInt1, int paramInt2) {
/*  88 */     Chunk chunk = (Chunk)this.e.get(Integer.valueOf(ChunkCoordIntPair.a(paramInt1, paramInt2)));
/*     */     
/*  90 */     if (chunk == null)
/*     */     {
/*  92 */       return getChunkAt(paramInt1, paramInt2);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*  97 */     return chunk;
/*     */   }
/*     */   
/*     */   private Chunk d(int paramInt1, int paramInt2) {
/* 101 */     if (this.d == null) return null; 
/*     */     try {
/* 103 */       Chunk chunk = this.d.a(this.g, paramInt1, paramInt2);
/* 104 */       if (chunk != null) {
/* 105 */         chunk.r = this.g.getTime();
/*     */       }
/* 107 */       return chunk;
/* 108 */     } catch (Exception exception) {
/* 109 */       exception.printStackTrace();
/* 110 */       return null;
/*     */     } 
/*     */   }
/*     */   
/*     */   private void a(Chunk paramChunk) {
/* 115 */     if (this.d == null)
/*     */       return;  try {
/* 117 */       this.d.b(this.g, paramChunk);
/* 118 */     } catch (Exception exception) {
/* 119 */       exception.printStackTrace();
/*     */     } 
/*     */   }
/*     */   
/*     */   private void b(Chunk paramChunk) {
/* 124 */     if (this.d == null)
/*     */       return;  try {
/* 126 */       paramChunk.r = this.g.getTime();
/* 127 */       this.d.a(this.g, paramChunk);
/* 128 */     } catch (IOException iOException) {
/* 129 */       iOException.printStackTrace();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void getChunkAt(IChunkProvider paramIChunkProvider, int paramInt1, int paramInt2) {
/* 134 */     Chunk chunk = getOrCreateChunk(paramInt1, paramInt2);
/* 135 */     if (!chunk.done) {
/* 136 */       chunk.done = true;
/* 137 */       if (this.c != null) {
/* 138 */         this.c.getChunkAt(paramIChunkProvider, paramInt1, paramInt2);
/* 139 */         chunk.f();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean saveChunks(boolean paramBoolean, IProgressUpdate paramIProgressUpdate) {
/* 147 */     byte b1 = 0;
/* 148 */     for (byte b2 = 0; b2 < this.f.size(); b2++) {
/* 149 */       Chunk chunk = (Chunk)this.f.get(b2);
/* 150 */       if (paramBoolean && !chunk.p) a(chunk); 
/* 151 */       if (chunk.a(paramBoolean)) {
/* 152 */         b(chunk);
/* 153 */         chunk.o = false;
/* 154 */         if (++b1 == 24 && !paramBoolean) return false;
/*     */       
/*     */       } 
/*     */     } 
/* 158 */     if (paramBoolean) {
/* 159 */       if (this.d == null) return true; 
/* 160 */       this.d.b();
/*     */     } 
/* 162 */     return true;
/*     */   }
/*     */   
/*     */   public boolean unloadChunks() {
/* 166 */     for (byte b1 = 0; b1 < 100; b1++) {
/* 167 */       if (!this.a.isEmpty()) {
/* 168 */         Integer integer = (Integer)this.a.iterator().next();
/*     */         
/* 170 */         Chunk chunk = (Chunk)this.e.get(integer);
/* 171 */         chunk.removeEntities();
/* 172 */         b(chunk);
/* 173 */         a(chunk);
/* 174 */         this.a.remove(integer);
/*     */         
/* 176 */         this.e.remove(integer);
/* 177 */         this.f.remove(chunk);
/*     */       } 
/*     */     } 
/*     */     
/* 181 */     if (this.d != null) this.d.a();
/*     */     
/* 183 */     return this.c.unloadChunks();
/*     */   }
/*     */ 
/*     */   
/* 187 */   public boolean canSave() { return true; }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\ChunkProviderLoadOrGenerate.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */