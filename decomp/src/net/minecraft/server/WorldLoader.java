/*     */ package net.minecraft.server;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WorldLoader
/*     */   implements Convertable
/*     */ {
/*     */   protected final File a;
/*     */   
/*     */   public WorldLoader(File paramFile) {
/*  19 */     if (!paramFile.exists()) paramFile.mkdirs(); 
/*  20 */     this.a = paramFile;
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
/*     */   public WorldData b(String paramString) {
/*  48 */     File file1 = new File(this.a, paramString);
/*  49 */     if (!file1.exists()) return null;
/*     */     
/*  51 */     File file2 = new File(file1, "level.dat");
/*  52 */     if (file2.exists()) {
/*     */       try {
/*  54 */         NBTTagCompound nBTTagCompound1 = CompressedStreamTools.a(new FileInputStream(file2));
/*  55 */         NBTTagCompound nBTTagCompound2 = nBTTagCompound1.k("Data");
/*  56 */         return new WorldData(nBTTagCompound2);
/*  57 */       } catch (Exception exception) {
/*  58 */         exception.printStackTrace();
/*     */       } 
/*     */     }
/*     */     
/*  62 */     file2 = new File(file1, "level.dat_old");
/*  63 */     if (file2.exists()) {
/*     */       try {
/*  65 */         NBTTagCompound nBTTagCompound1 = CompressedStreamTools.a(new FileInputStream(file2));
/*  66 */         NBTTagCompound nBTTagCompound2 = nBTTagCompound1.k("Data");
/*  67 */         return new WorldData(nBTTagCompound2);
/*  68 */       } catch (Exception exception) {
/*  69 */         exception.printStackTrace();
/*     */       } 
/*     */     }
/*  72 */     return null;
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
/*     */   protected static void a(File[] paramArrayOfFile) {
/* 120 */     for (byte b = 0; b < paramArrayOfFile.length; b++) {
/* 121 */       if (paramArrayOfFile[b].isDirectory()) {
/* 122 */         a(paramArrayOfFile[b].listFiles());
/*     */       }
/* 124 */       paramArrayOfFile[b].delete();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/* 129 */   public IDataManager a(String paramString, boolean paramBoolean) { return new PlayerNBTManager(this.a, paramString, paramBoolean); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 137 */   public boolean isConvertable(String paramString) { return false; }
/*     */ 
/*     */ 
/*     */   
/* 141 */   public boolean convert(String paramString, IProgressUpdate paramIProgressUpdate) { return false; }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\WorldLoader.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */