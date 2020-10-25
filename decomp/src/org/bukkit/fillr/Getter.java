/*    */ package org.bukkit.fillr;
/*    */ 
/*    */ import java.io.File;
/*    */ import java.util.logging.Level;
/*    */ import org.bukkit.Server;
/*    */ import org.bukkit.command.CommandSender;
/*    */ import org.bukkit.plugin.InvalidDescriptionException;
/*    */ import org.bukkit.plugin.InvalidPluginException;
/*    */ import org.bukkit.plugin.UnknownDependencyException;
/*    */ 
/*    */ public class Getter
/*    */ {
/*    */   private Server server;
/* 14 */   private static String DIRECTORY = "plugins";
/*    */ 
/*    */   
/* 17 */   public Getter(Server server) { this.server = server; }
/*    */ 
/*    */   
/*    */   public void get(String string, CommandSender sender) {
/* 21 */     FillReader reader = new FillReader(string);
/*    */     
/* 23 */     sender.sendMessage("Downloading " + reader.getName() + " " + reader.getCurrVersion());
/*    */     try {
/* 25 */       Downloader.downloadJar(reader.getFile());
/* 26 */       if (reader.getNotes() != null && !reader.getNotes().equals("")) {
/* 27 */         sender.sendMessage("Notes: " + reader.getNotes());
/*    */       }
/* 29 */       sender.sendMessage("Finished Download!");
/* 30 */       enablePlugin(reader);
/* 31 */       sender.sendMessage("Loading " + reader.getName());
/* 32 */     } catch (Exception ex) {
/* 33 */       this.server.getLogger().log(Level.SEVERE, null, ex);
/*    */     } 
/*    */   }
/*    */   
/*    */   private void enablePlugin(FillReader update) {
/* 38 */     String name = update.getName();
/*    */ 
/*    */     
/* 41 */     File plugin = new File(DIRECTORY, name + ".jar");
/*    */     
/*    */     try {
/* 44 */       this.server.getPluginManager().loadPlugin(plugin);
/* 45 */     } catch (UnknownDependencyException ex) {
/* 46 */       this.server.getLogger().log(Level.SEVERE, null, ex);
/* 47 */     } catch (InvalidPluginException ex) {
/* 48 */       this.server.getLogger().log(Level.SEVERE, null, ex);
/* 49 */     } catch (InvalidDescriptionException ex) {
/* 50 */       this.server.getLogger().log(Level.SEVERE, null, ex);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\fillr\Getter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */