/*     */ package com.avaje.ebeaninternal.server.lib.resource;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
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
/*     */ public class DirectoryFinder
/*     */ {
/*  30 */   private static final Logger logger = Logger.getLogger(DirectoryFinder.class.getName());
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
/*     */   public static File find(File startDir, String match, int maxDepth) {
/*  53 */     String matchSub = null;
/*  54 */     int slashPos = match.indexOf('/');
/*  55 */     if (slashPos > -1) {
/*     */       
/*  57 */       matchSub = match.substring(slashPos + 1);
/*  58 */       match = match.substring(0, slashPos);
/*     */     } 
/*     */ 
/*     */     
/*  62 */     File found = find(startDir, match, matchSub, 0, maxDepth);
/*     */     
/*  64 */     if (found != null && matchSub != null)
/*     */     {
/*  66 */       return new File(found, matchSub);
/*     */     }
/*  68 */     return found;
/*     */   }
/*     */ 
/*     */   
/*     */   private static File find(File dir, String match, String matchSub, int depth, int maxDepth) {
/*  73 */     if (dir == null) {
/*  74 */       String curDir = System.getProperty("user.dir");
/*  75 */       dir = new File(curDir);
/*     */     } 
/*     */     
/*  78 */     if (dir.exists()) {
/*  79 */       File[] list = dir.listFiles();
/*  80 */       if (list != null) {
/*  81 */         for (int i = 0; i < list.length; i++) {
/*  82 */           if (isMatch(list[i], match, matchSub)) {
/*  83 */             return list[i];
/*     */           }
/*     */         } 
/*     */ 
/*     */ 
/*     */         
/*  89 */         if (depth < maxDepth) {
/*  90 */           for (int i = 0; i < list.length; i++) {
/*  91 */             if (list[i].isDirectory()) {
/*  92 */               File found = find(list[i], match, matchSub, depth + 1, maxDepth);
/*  93 */               if (found != null) {
/*  94 */                 return found;
/*     */               }
/*     */             } 
/*     */           } 
/*     */         }
/*     */       } 
/*     */     } 
/* 101 */     return null;
/*     */   }
/*     */   
/*     */   private static boolean isMatch(File f, String match, String matchSub) {
/* 105 */     if (f == null) {
/* 106 */       return false;
/*     */     }
/* 108 */     if (!f.isDirectory()) {
/* 109 */       return false;
/*     */     }
/* 111 */     if (!f.getName().equalsIgnoreCase(match)) {
/* 112 */       return false;
/*     */     }
/* 114 */     if (matchSub == null) {
/* 115 */       return true;
/*     */     }
/* 117 */     File sub = new File(f, matchSub);
/* 118 */     if (logger.isLoggable(Level.FINEST)) {
/* 119 */       logger.finest("search; " + f.getPath());
/*     */     }
/* 121 */     return sub.exists();
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\lib\resource\DirectoryFinder.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */