/*     */ package org.bukkit.fillr;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.util.jar.JarFile;
/*     */ import org.bukkit.command.CommandSender;
/*     */ import org.bukkit.plugin.PluginDescriptionFile;
/*     */ 
/*     */ public class Checker {
/*  10 */   private static String DIRECTORY = "plugins";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void check(CommandSender sender) {
/*  19 */     File folder = new File(DIRECTORY);
/*  20 */     File[] files = folder.listFiles(new PluginFilter());
/*     */     
/*  22 */     if (files.length == 0) {
/*  23 */       sender.sendMessage("No plugins to update.");
/*     */     } else {
/*  25 */       sender.sendMessage("Status for " + files.length + " plugins:");
/*  26 */       for (File file : files) {
/*  27 */         PluginDescriptionFile pdfFile = getPDF(file);
/*     */         
/*  29 */         if (pdfFile != null)
/*     */         {
/*     */           
/*  32 */           checkForUpdate(file, sender);
/*     */         }
/*     */       } 
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
/*     */   private void checkForUpdate(File file, CommandSender sender) {
/*  46 */     PluginDescriptionFile pdfFile = getPDF(file);
/*  47 */     FillReader reader = needsUpdate(pdfFile);
/*     */     
/*  49 */     if (reader != null) {
/*  50 */       sender.sendMessage(ChatColor.RED + reader.getName() + " " + pdfFile.getVersion() + " has an update to " + reader.getCurrVersion());
/*     */     } else {
/*  52 */       sender.sendMessage(pdfFile.getName() + " " + pdfFile.getVersion() + " is up to date!");
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
/*     */   static FillReader needsUpdate(PluginDescriptionFile file) {
/*  65 */     FillReader reader = new FillReader(file.getName());
/*  66 */     String version = file.getVersion();
/*  67 */     String currVersion = reader.getCurrVersion();
/*  68 */     String name = reader.getName();
/*     */     
/*  70 */     if (currVersion.equalsIgnoreCase(version) && (new File(DIRECTORY, name + ".jar")).exists()) {
/*  71 */       return null;
/*     */     }
/*  73 */     return reader;
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
/*     */   static PluginDescriptionFile getPDF(File file) {
/*  89 */     if (file.getName().endsWith(".jar")) {
/*     */       
/*     */       try {
/*     */         
/*  93 */         JarFile jarFile = new JarFile(file);
/*  94 */         JarEntry entry = jarFile.getJarEntry("plugin.yml");
/*  95 */         InputStream input = jarFile.getInputStream(entry);
/*     */         
/*  97 */         return new PluginDescriptionFile(input);
/*  98 */       } catch (IOException e) {
/*  99 */         e.printStackTrace();
/* 100 */         return null;
/* 101 */       } catch (InvalidDescriptionException e) {
/* 102 */         e.printStackTrace();
/* 103 */         return null;
/*     */       } 
/*     */     }
/* 106 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\fillr\Checker.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */