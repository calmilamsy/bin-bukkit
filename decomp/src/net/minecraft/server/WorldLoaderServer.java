/*     */ package net.minecraft.server;
/*     */ 
/*     */ import java.io.DataInputStream;
/*     */ import java.io.DataOutputStream;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.zip.GZIPInputStream;
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
/*     */ public class WorldLoaderServer
/*     */   extends WorldLoader
/*     */ {
/*  25 */   public WorldLoaderServer(File paramFile) { super(paramFile); }
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
/*  67 */   public IDataManager a(String paramString, boolean paramBoolean) { return new ServerNBTManager(this.a, paramString, paramBoolean); }
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
/*     */   public boolean isConvertable(String paramString) {
/*  84 */     WorldData worldData = b(paramString);
/*  85 */     if (worldData == null || worldData.i() != 0) {
/*  86 */       return false;
/*     */     }
/*  88 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean convert(String paramString, IProgressUpdate paramIProgressUpdate) {
/*  94 */     paramIProgressUpdate.a(0);
/*     */     
/*  96 */     ArrayList arrayList1 = new ArrayList();
/*  97 */     ArrayList arrayList2 = new ArrayList();
/*  98 */     ArrayList arrayList3 = new ArrayList();
/*  99 */     ArrayList arrayList4 = new ArrayList();
/*     */     
/* 101 */     File file1 = new File(this.a, paramString);
/* 102 */     File file2 = new File(file1, "DIM-1");
/*     */     
/* 104 */     System.out.println("Scanning folders...");
/*     */ 
/*     */     
/* 107 */     a(file1, arrayList1, arrayList2);
/*     */ 
/*     */     
/* 110 */     if (file2.exists()) {
/* 111 */       a(file2, arrayList3, arrayList4);
/*     */     }
/*     */     
/* 114 */     int i = arrayList1.size() + arrayList3.size() + arrayList2.size() + arrayList4.size();
/* 115 */     System.out.println("Total conversion count is " + i);
/*     */ 
/*     */     
/* 118 */     a(file1, arrayList1, 0, i, paramIProgressUpdate);
/*     */     
/* 120 */     a(file2, arrayList3, arrayList1.size(), i, paramIProgressUpdate);
/*     */     
/* 122 */     WorldData worldData = b(paramString);
/* 123 */     worldData.a(19132);
/*     */     
/* 125 */     IDataManager iDataManager = a(paramString, false);
/* 126 */     iDataManager.a(worldData);
/*     */ 
/*     */     
/* 129 */     a(arrayList2, arrayList1.size() + arrayList3.size(), i, paramIProgressUpdate);
/* 130 */     if (file2.exists()) {
/* 131 */       a(arrayList4, arrayList1.size() + arrayList3.size() + arrayList2.size(), i, paramIProgressUpdate);
/*     */     }
/*     */     
/* 134 */     return true;
/*     */   }
/*     */   
/*     */   private void a(File paramFile, ArrayList paramArrayList1, ArrayList paramArrayList2) {
/* 138 */     ChunkFileFilter chunkFileFilter = new ChunkFileFilter(null);
/* 139 */     ChunkFilenameFilter chunkFilenameFilter = new ChunkFilenameFilter(null);
/*     */     
/* 141 */     File[] arrayOfFile = paramFile.listFiles(chunkFileFilter);
/* 142 */     for (File file : arrayOfFile) {
/*     */       
/* 144 */       paramArrayList2.add(file);
/*     */       
/* 146 */       File[] arrayOfFile1 = file.listFiles(chunkFileFilter);
/* 147 */       for (File file1 : arrayOfFile1) {
/* 148 */         File[] arrayOfFile2 = file1.listFiles(chunkFilenameFilter);
/*     */         
/* 150 */         for (File file2 : arrayOfFile2) {
/* 151 */           paramArrayList1.add(new ChunkFile(file2));
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void a(File paramFile, ArrayList paramArrayList, int paramInt1, int paramInt2, IProgressUpdate paramIProgressUpdate) {
/* 159 */     Collections.sort(paramArrayList);
/*     */     
/* 161 */     byte[] arrayOfByte = new byte[4096];
/*     */     
/* 163 */     for (ChunkFile chunkFile : paramArrayList) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 172 */       int i = chunkFile.b();
/* 173 */       int j = chunkFile.c();
/*     */       
/* 175 */       RegionFile regionFile = RegionFileCache.a(paramFile, i, j);
/* 176 */       if (!regionFile.c(i & 0x1F, j & 0x1F)) {
/*     */         try {
/* 178 */           DataInputStream dataInputStream = new DataInputStream(new GZIPInputStream(new FileInputStream(chunkFile.a())));
/*     */           
/* 180 */           DataOutputStream dataOutputStream = regionFile.b(i & 0x1F, j & 0x1F);
/*     */           
/* 182 */           int m = 0;
/* 183 */           while ((m = dataInputStream.read(arrayOfByte)) != -1) {
/* 184 */             dataOutputStream.write(arrayOfByte, 0, m);
/*     */           }
/*     */           
/* 187 */           dataOutputStream.close();
/* 188 */           dataInputStream.close();
/* 189 */         } catch (IOException iOException) {
/* 190 */           iOException.printStackTrace();
/*     */         } 
/*     */       }
/*     */       
/* 194 */       paramInt1++;
/* 195 */       int k = (int)Math.round(100.0D * paramInt1 / paramInt2);
/* 196 */       paramIProgressUpdate.a(k);
/*     */     } 
/* 198 */     RegionFileCache.a();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void a(ArrayList paramArrayList, int paramInt1, int paramInt2, IProgressUpdate paramIProgressUpdate) {
/* 204 */     for (File file : paramArrayList) {
/*     */       
/* 206 */       File[] arrayOfFile = file.listFiles();
/* 207 */       a(arrayOfFile);
/* 208 */       file.delete();
/*     */       
/* 210 */       paramInt1++;
/* 211 */       int i = (int)Math.round(100.0D * paramInt1 / paramInt2);
/* 212 */       paramIProgressUpdate.a(i);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\WorldLoaderServer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */