/*     */ package net.minecraft.server;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileOutputStream;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ChunkLoader
/*     */   implements IChunkLoader
/*     */ {
/*     */   private File a;
/*     */   private boolean b;
/*     */   
/*     */   public ChunkLoader(File paramFile, boolean paramBoolean) {
/*  18 */     this.a = paramFile;
/*  19 */     this.b = paramBoolean;
/*     */   }
/*     */   
/*     */   private File a(int paramInt1, int paramInt2) {
/*  23 */     String str1 = "c." + Integer.toString(paramInt1, 36) + "." + Integer.toString(paramInt2, 36) + ".dat";
/*  24 */     String str2 = Integer.toString(paramInt1 & 0x3F, 36);
/*  25 */     String str3 = Integer.toString(paramInt2 & 0x3F, 36);
/*  26 */     File file = new File(this.a, str2);
/*  27 */     if (!file.exists())
/*  28 */       if (this.b) { file.mkdir(); }
/*  29 */       else { return null; }
/*     */        
/*  31 */     file = new File(file, str3);
/*  32 */     if (!file.exists())
/*  33 */       if (this.b) { file.mkdir(); }
/*  34 */       else { return null; }
/*     */        
/*  36 */     file = new File(file, str1);
/*  37 */     if (!file.exists() && 
/*  38 */       !this.b) return null;
/*     */     
/*  40 */     return file;
/*     */   }
/*     */   
/*     */   public Chunk a(World paramWorld, int paramInt1, int paramInt2) {
/*  44 */     File file = a(paramInt1, paramInt2);
/*  45 */     if (file != null && file.exists()) {
/*     */       
/*     */       try {
/*  48 */         FileInputStream fileInputStream = new FileInputStream(file);
/*  49 */         NBTTagCompound nBTTagCompound = CompressedStreamTools.a(fileInputStream);
/*  50 */         if (!nBTTagCompound.hasKey("Level")) {
/*  51 */           System.out.println("Chunk file at " + paramInt1 + "," + paramInt2 + " is missing level data, skipping");
/*  52 */           return null;
/*     */         } 
/*  54 */         if (!nBTTagCompound.k("Level").hasKey("Blocks")) {
/*  55 */           System.out.println("Chunk file at " + paramInt1 + "," + paramInt2 + " is missing block data, skipping");
/*  56 */           return null;
/*     */         } 
/*  58 */         Chunk chunk = a(paramWorld, nBTTagCompound.k("Level"));
/*  59 */         if (!chunk.a(paramInt1, paramInt2)) {
/*  60 */           System.out.println("Chunk file at " + paramInt1 + "," + paramInt2 + " is in the wrong location; relocating. (Expected " + paramInt1 + ", " + paramInt2 + ", got " + chunk.x + ", " + chunk.z + ")");
/*  61 */           nBTTagCompound.a("xPos", paramInt1);
/*  62 */           nBTTagCompound.a("zPos", paramInt2);
/*  63 */           chunk = a(paramWorld, nBTTagCompound.k("Level"));
/*     */         } 
/*  65 */         chunk.h();
/*  66 */         return chunk;
/*  67 */       } catch (Exception exception) {
/*  68 */         exception.printStackTrace();
/*     */       } 
/*     */     }
/*  71 */     return null;
/*     */   }
/*     */   
/*     */   public void a(World paramWorld, Chunk paramChunk) {
/*  75 */     paramWorld.k();
/*  76 */     File file = a(paramChunk.x, paramChunk.z);
/*  77 */     if (file.exists()) {
/*  78 */       WorldData worldData = paramWorld.q();
/*  79 */       worldData.b(worldData.g() - file.length());
/*     */     } 
/*     */     
/*     */     try {
/*  83 */       File file1 = new File(this.a, "tmp_chunk.dat");
/*     */ 
/*     */       
/*  86 */       FileOutputStream fileOutputStream = new FileOutputStream(file1);
/*  87 */       NBTTagCompound nBTTagCompound1 = new NBTTagCompound();
/*  88 */       NBTTagCompound nBTTagCompound2 = new NBTTagCompound();
/*  89 */       nBTTagCompound1.a("Level", nBTTagCompound2);
/*  90 */       a(paramChunk, paramWorld, nBTTagCompound2);
/*  91 */       CompressedStreamTools.a(nBTTagCompound1, fileOutputStream);
/*  92 */       fileOutputStream.close();
/*     */       
/*  94 */       if (file.exists()) {
/*  95 */         file.delete();
/*     */       }
/*  97 */       file1.renameTo(file);
/*     */       
/*  99 */       WorldData worldData = paramWorld.q();
/* 100 */       worldData.b(worldData.g() + file.length());
/* 101 */     } catch (Exception exception) {
/* 102 */       exception.printStackTrace();
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void a(Chunk paramChunk, World paramWorld, NBTTagCompound paramNBTTagCompound) {
/* 107 */     paramWorld.k();
/* 108 */     paramNBTTagCompound.a("xPos", paramChunk.x);
/* 109 */     paramNBTTagCompound.a("zPos", paramChunk.z);
/* 110 */     paramNBTTagCompound.setLong("LastUpdate", paramWorld.getTime());
/* 111 */     paramNBTTagCompound.a("Blocks", paramChunk.b);
/* 112 */     paramNBTTagCompound.a("Data", paramChunk.e.a);
/* 113 */     paramNBTTagCompound.a("SkyLight", paramChunk.f.a);
/* 114 */     paramNBTTagCompound.a("BlockLight", paramChunk.g.a);
/* 115 */     paramNBTTagCompound.a("HeightMap", paramChunk.heightMap);
/* 116 */     paramNBTTagCompound.a("TerrainPopulated", paramChunk.done);
/*     */     
/* 118 */     paramChunk.q = false;
/* 119 */     NBTTagList nBTTagList1 = new NBTTagList();
/* 120 */     for (null = 0; null < paramChunk.entitySlices.length; null++) {
/* 121 */       for (Entity entity : paramChunk.entitySlices[null]) {
/* 122 */         paramChunk.q = true;
/* 123 */         NBTTagCompound nBTTagCompound = new NBTTagCompound();
/* 124 */         if (entity.c(nBTTagCompound)) {
/* 125 */           nBTTagList1.a(nBTTagCompound);
/*     */         }
/*     */       } 
/*     */     } 
/* 129 */     paramNBTTagCompound.a("Entities", nBTTagList1);
/*     */     
/* 131 */     NBTTagList nBTTagList2 = new NBTTagList();
/* 132 */     for (TileEntity tileEntity : paramChunk.tileEntities.values()) {
/* 133 */       NBTTagCompound nBTTagCompound = new NBTTagCompound();
/* 134 */       tileEntity.b(nBTTagCompound);
/* 135 */       nBTTagList2.a(nBTTagCompound);
/*     */     } 
/* 137 */     paramNBTTagCompound.a("TileEntities", nBTTagList2);
/*     */   }
/*     */ 
/*     */   
/*     */   public static Chunk a(World paramWorld, NBTTagCompound paramNBTTagCompound) {
/* 142 */     int i = paramNBTTagCompound.e("xPos");
/* 143 */     int j = paramNBTTagCompound.e("zPos");
/*     */     
/* 145 */     Chunk chunk = new Chunk(paramWorld, i, j);
/* 146 */     chunk.b = paramNBTTagCompound.j("Blocks");
/* 147 */     chunk.e = new NibbleArray(paramNBTTagCompound.j("Data"));
/* 148 */     chunk.f = new NibbleArray(paramNBTTagCompound.j("SkyLight"));
/* 149 */     chunk.g = new NibbleArray(paramNBTTagCompound.j("BlockLight"));
/* 150 */     chunk.heightMap = paramNBTTagCompound.j("HeightMap");
/* 151 */     chunk.done = paramNBTTagCompound.m("TerrainPopulated");
/*     */     
/* 153 */     if (!chunk.e.a()) {
/* 154 */       chunk.e = new NibbleArray(chunk.b.length);
/*     */     }
/*     */     
/* 157 */     if (chunk.heightMap == null || !chunk.f.a()) {
/* 158 */       chunk.heightMap = new byte[256];
/* 159 */       chunk.f = new NibbleArray(chunk.b.length);
/* 160 */       chunk.initLighting();
/*     */     } 
/*     */     
/* 163 */     if (!chunk.g.a()) {
/* 164 */       chunk.g = new NibbleArray(chunk.b.length);
/* 165 */       chunk.a();
/*     */     } 
/*     */     
/* 168 */     NBTTagList nBTTagList1 = paramNBTTagCompound.l("Entities");
/* 169 */     if (nBTTagList1 != null) {
/* 170 */       for (byte b1 = 0; b1 < nBTTagList1.c(); b1++) {
/* 171 */         NBTTagCompound nBTTagCompound = (NBTTagCompound)nBTTagList1.a(b1);
/* 172 */         Entity entity = EntityTypes.a(nBTTagCompound, paramWorld);
/* 173 */         chunk.q = true;
/* 174 */         if (entity != null) {
/* 175 */           chunk.a(entity);
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/* 180 */     NBTTagList nBTTagList2 = paramNBTTagCompound.l("TileEntities");
/* 181 */     if (nBTTagList2 != null) {
/* 182 */       for (byte b1 = 0; b1 < nBTTagList2.c(); b1++) {
/* 183 */         NBTTagCompound nBTTagCompound = (NBTTagCompound)nBTTagList2.a(b1);
/* 184 */         TileEntity tileEntity = TileEntity.c(nBTTagCompound);
/* 185 */         if (tileEntity != null) {
/* 186 */           chunk.a(tileEntity);
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/* 191 */     return chunk;
/*     */   }
/*     */   
/*     */   public void a() {}
/*     */   
/*     */   public void b() {}
/*     */   
/*     */   public void b(World paramWorld, Chunk paramChunk) {}
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\ChunkLoader.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */