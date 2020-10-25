/*     */ package org.bukkit.fillr;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.net.URL;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.plugin.PluginDescriptionFile;
/*     */ 
/*     */ public class Downloader {
/*  11 */   private static final String DOWNLOAD_DIR = "plugins" + File.separator + "downloads"; private static final String DIRECTORY = "plugins";
/*  12 */   private static final String BACKUP = "plugins" + File.separator + "backups";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static void downloadJar(String url) throws Exception {
/*  22 */     int index = url.lastIndexOf('/');
/*  23 */     String name = url.substring(index + 1);
/*     */     
/*  25 */     File file = new File("plugins", name);
/*     */     
/*  27 */     if (url.endsWith(".jar") && file.exists()) {
/*  28 */       backupFile(file);
/*     */     }
/*     */     
/*  31 */     download(new URL(url), name, "plugins");
/*  32 */     file = new File("plugins", name);
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
/*     */   void downloadFile(String name, Player player) throws Exception {
/*  44 */     File file = new File("plugins", name + ".jar");
/*     */     
/*  46 */     if (file.exists()) {
/*  47 */       player.sendMessage("Downloading " + name + "'s file");
/*  48 */       PluginDescriptionFile pdfFile = Checker.getPDF(file);
/*  49 */       FillReader reader = Checker.needsUpdate(pdfFile);
/*     */       
/*  51 */       downloadFile(new URL(reader.getFile()));
/*  52 */       player.sendMessage("Finished download");
/*     */     } else {
/*  54 */       System.out.println("Can't find " + name);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void downloadFile(URL u) throws Exception {
/*  65 */     String name = u.getFile();
/*  66 */     int index = name.lastIndexOf('/');
/*     */     
/*  68 */     name = name.substring(index + 1);
/*  69 */     download(u, name, DOWNLOAD_DIR);
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
/*     */   private static void download(URL u, String name, String directory) throws Exception {
/*  83 */     InputStream inputStream = null;
/*     */     
/*  85 */     inputStream = u.openStream();
/*     */     
/*  87 */     if (!(new File(directory)).exists()) {
/*  88 */       (new File(directory)).mkdir();
/*     */     }
/*     */     
/*  91 */     File f = new File(directory, name);
/*     */     
/*  93 */     if (f.exists()) {
/*  94 */       f.delete();
/*     */     }
/*  96 */     f.createNewFile();
/*     */     
/*  98 */     copyInputStream(inputStream, new BufferedOutputStream(new FileOutputStream(f)));
/*     */     
/*     */     try {
/* 101 */       if (inputStream != null) {
/* 102 */         inputStream.close();
/*     */       }
/* 104 */     } catch (IOException ioe) {
/* 105 */       System.out.println("[UPDATR]: Error closing inputStream");
/*     */     } 
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
/*     */   private static final void copyInputStream(InputStream in, OutputStream out) throws IOException {
/* 119 */     byte[] buffer = new byte[1024];
/*     */     
/*     */     int len;
/* 122 */     while ((len = in.read(buffer)) >= 0) {
/* 123 */       out.write(buffer, 0, len);
/*     */     }
/*     */     
/* 126 */     in.close();
/* 127 */     out.close();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void backupFile(File file) {
/* 137 */     if (file != null && file.exists()) {
/* 138 */       System.out.println("Backing up old file: " + file.getName());
/* 139 */       if (!(new File(BACKUP)).exists()) {
/* 140 */         (new File(BACKUP)).mkdir();
/*     */       }
/* 142 */       file.renameTo(new File(BACKUP, file.getName() + ".bak"));
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\fillr\Downloader.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */