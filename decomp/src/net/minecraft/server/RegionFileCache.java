/*    */ package net.minecraft.server;
/*    */ 
/*    */ import java.io.DataInputStream;
/*    */ import java.io.DataOutputStream;
/*    */ import java.io.File;
/*    */ import java.io.IOException;
/*    */ import java.lang.ref.Reference;
/*    */ import java.lang.ref.SoftReference;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class RegionFileCache
/*    */ {
/* 37 */   private static final Map a = new HashMap();
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static RegionFile a(File paramFile, int paramInt1, int paramInt2) {
/* 43 */     File file1 = new File(paramFile, "region");
/* 44 */     File file2 = new File(file1, "r." + (paramInt1 >> 5) + "." + (paramInt2 >> 5) + ".mcr");
/*    */     
/* 46 */     Reference reference = (Reference)a.get(file2);
/*    */     
/* 48 */     if (reference != null) {
/* 49 */       RegionFile regionFile1 = (RegionFile)reference.get();
/* 50 */       if (regionFile1 != null) {
/* 51 */         return regionFile1;
/*    */       }
/*    */     } 
/*    */     
/* 55 */     if (!file1.exists()) {
/* 56 */       file1.mkdirs();
/*    */     }
/*    */     
/* 59 */     if (a.size() >= 256) {
/* 60 */       a();
/*    */     }
/*    */     
/* 63 */     RegionFile regionFile = new RegionFile(file2);
/* 64 */     a.put(file2, new SoftReference(regionFile));
/* 65 */     return regionFile;
/*    */   }
/*    */   
/*    */   public static void a() {
/* 69 */     for (Reference reference : a.values()) {
/*    */       try {
/* 71 */         RegionFile regionFile = (RegionFile)reference.get();
/* 72 */         if (regionFile != null) {
/* 73 */           regionFile.b();
/*    */         }
/* 75 */       } catch (IOException iOException) {
/* 76 */         iOException.printStackTrace();
/*    */       } 
/*    */     } 
/* 79 */     a.clear();
/*    */   }
/*    */   
/*    */   public static int b(File paramFile, int paramInt1, int paramInt2) {
/* 83 */     RegionFile regionFile = a(paramFile, paramInt1, paramInt2);
/* 84 */     return regionFile.a();
/*    */   }
/*    */   
/*    */   public static DataInputStream c(File paramFile, int paramInt1, int paramInt2) {
/* 88 */     RegionFile regionFile = a(paramFile, paramInt1, paramInt2);
/* 89 */     return regionFile.a(paramInt1 & 0x1F, paramInt2 & 0x1F);
/*    */   }
/*    */   
/*    */   public static DataOutputStream d(File paramFile, int paramInt1, int paramInt2) {
/* 93 */     RegionFile regionFile = a(paramFile, paramInt1, paramInt2);
/* 94 */     return regionFile.b(paramInt1 & 0x1F, paramInt2 & 0x1F);
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\RegionFileCache.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */