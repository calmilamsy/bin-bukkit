/*     */ package org.bukkit.craftbukkit;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import joptsimple.OptionException;
/*     */ import joptsimple.OptionParser;
/*     */ import joptsimple.OptionSet;
/*     */ import net.minecraft.server.MinecraftServer;
/*     */ 
/*     */ public class Main
/*     */ {
/*     */   public static boolean useJline = true;
/*     */   
/*     */   public static void main(String[] args) {
/*  19 */     OptionParser parser = new OptionParser()
/*     */       {
/*     */       
/*     */       };
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
/* 101 */     OptionSet options = null;
/*     */     
/*     */     try {
/* 104 */       options = parser.parse(args);
/* 105 */     } catch (OptionException ex) {
/* 106 */       Logger.getLogger(Main.class.getName()).log(Level.SEVERE, ex.getLocalizedMessage());
/*     */     } 
/*     */     
/* 109 */     if (options == null || options.has("?")) {
/*     */       try {
/* 111 */         parser.printHelpOn(System.out);
/* 112 */       } catch (IOException ex) {
/* 113 */         Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
/*     */       } 
/* 115 */     } else if (options.has("v")) {
/* 116 */       System.out.println(CraftServer.class.getPackage().getImplementationVersion());
/*     */     } else {
/*     */       try {
/* 119 */         useJline = !"jline.UnsupportedTerminal".equals(System.getProperty("jline.terminal"));
/*     */         
/* 121 */         if (options.has("nojline")) {
/* 122 */           System.setProperty("jline.terminal", "jline.UnsupportedTerminal");
/* 123 */           System.setProperty("user.language", "en");
/* 124 */           useJline = false;
/*     */         } 
/*     */         
/* 127 */         MinecraftServer.main(options);
/* 128 */       } catch (Throwable t) {
/* 129 */         t.printStackTrace();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/* 135 */   private static List<String> asList(String... params) { return Arrays.asList(params); }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\craftbukkit\Main.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */