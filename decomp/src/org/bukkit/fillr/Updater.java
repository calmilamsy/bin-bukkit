/*     */ package org.bukkit.fillr;
/*     */ import java.io.File;
/*     */ import java.util.logging.Level;
/*     */ import org.bukkit.Server;
/*     */ import org.bukkit.command.CommandSender;
/*     */ import org.bukkit.plugin.InvalidDescriptionException;
/*     */ import org.bukkit.plugin.InvalidPluginException;
/*     */ import org.bukkit.plugin.PluginDescriptionFile;
/*     */ 
/*     */ public class Updater {
/*  11 */   public static String DIRECTORY = "plugins";
/*     */   
/*     */   private final Server server;
/*     */   
/*  15 */   Updater(Server server) { this.server = server; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void updateAll(CommandSender sender) {
/*  25 */     File folder = new File(DIRECTORY);
/*  26 */     File[] files = folder.listFiles(new PluginFilter());
/*     */     
/*  28 */     if (files.length == 0) {
/*  29 */       sender.sendMessage("No plugins to update.");
/*     */     } else {
/*  31 */       sender.sendMessage("Updating " + files.length + " plugins:");
/*  32 */       for (File file : files) {
/*  33 */         PluginDescriptionFile pdfFile = Checker.getPDF(file);
/*     */         
/*  35 */         if (pdfFile != null) {
/*     */ 
/*     */           
/*  38 */           FillReader reader = Checker.needsUpdate(pdfFile);
/*     */           
/*  40 */           if (reader != null) {
/*  41 */             update(reader, sender);
/*     */           }
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
/*     */ 
/*     */   
/*     */   void update(String string, CommandSender player) {
/*  58 */     File file = new File(DIRECTORY, string + ".jar");
/*     */     
/*  60 */     if (file.exists()) {
/*  61 */       PluginDescriptionFile pdfFile = Checker.getPDF(file);
/*  62 */       FillReader reader = Checker.needsUpdate(pdfFile);
/*     */       
/*  64 */       if (reader != null) {
/*  65 */         update(reader, player);
/*     */       } else {
/*  67 */         player.sendMessage(string + " is up to date");
/*     */       } 
/*     */     } else {
/*  70 */       player.sendMessage("Can't find " + string);
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
/*     */   private void update(FillReader update, CommandSender sender) {
/*  82 */     disablePlugin(update);
/*  83 */     sender.sendMessage("Disabling " + update.getName() + " for update");
/*  84 */     sender.sendMessage("Downloading " + update.getName() + " " + update.getCurrVersion());
/*     */     try {
/*  86 */       Downloader.downloadJar(update.getFile());
/*  87 */       if (update.getNotes() != null && !update.getNotes().equals("")) {
/*  88 */         sender.sendMessage("Notes: " + update.getNotes());
/*     */       }
/*  90 */       sender.sendMessage("Finished Download!");
/*  91 */       enablePlugin(update);
/*  92 */       sender.sendMessage("Loading " + update.getName());
/*  93 */     } catch (Exception e) {
/*  94 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */   
/*     */   void enablePlugin(FillReader update) {
/*  99 */     String name = update.getName();
/*     */     
/* 101 */     File plugin = new File(DIRECTORY, name + ".jar");
/*     */     
/*     */     try {
/* 104 */       this.server.getPluginManager().loadPlugin(plugin);
/* 105 */     } catch (UnknownDependencyException ex) {
/* 106 */       this.server.getLogger().log(Level.SEVERE, null, ex);
/* 107 */     } catch (InvalidPluginException ex) {
/* 108 */       this.server.getLogger().log(Level.SEVERE, null, ex);
/* 109 */     } catch (InvalidDescriptionException ex) {
/* 110 */       this.server.getLogger().log(Level.SEVERE, null, ex);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void disablePlugin(FillReader update) {
/* 115 */     String name = update.getName();
/* 116 */     Plugin plugin = this.server.getPluginManager().getPlugin(name);
/*     */     
/* 118 */     this.server.getPluginManager().disablePlugin(plugin);
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\fillr\Updater.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */