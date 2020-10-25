/*     */ package jline;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
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
/*     */ public class FileNameCompletor
/*     */   implements Completor
/*     */ {
/*     */   public int complete(String buf, int cursor, List candidates) {
/*     */     File dir;
/*  40 */     String buffer = (buf == null) ? "" : buf;
/*     */     
/*  42 */     String translated = buffer;
/*     */ 
/*     */     
/*  45 */     if (translated.startsWith("~" + File.separator)) {
/*  46 */       translated = System.getProperty("user.home") + translated.substring(1);
/*     */     }
/*  48 */     else if (translated.startsWith("~")) {
/*  49 */       translated = (new File(System.getProperty("user.home"))).getParentFile().getAbsolutePath();
/*     */     }
/*  51 */     else if (!translated.startsWith(File.separator)) {
/*  52 */       translated = (new File("")).getAbsolutePath() + File.separator + translated;
/*     */     } 
/*     */ 
/*     */     
/*  56 */     File f = new File(translated);
/*     */ 
/*     */ 
/*     */     
/*  60 */     if (translated.endsWith(File.separator)) {
/*  61 */       dir = f;
/*     */     } else {
/*  63 */       dir = f.getParentFile();
/*     */     } 
/*     */     
/*  66 */     File[] entries = (dir == null) ? new File[0] : dir.listFiles();
/*     */     
/*     */     try {
/*  69 */       return matchFiles(buffer, translated, entries, candidates);
/*     */     } finally {
/*     */       
/*  72 */       sortFileNames(candidates);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*  77 */   protected void sortFileNames(List fileNames) { Collections.sort(fileNames); }
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
/*     */   public int matchFiles(String buffer, String translated, File[] entries, List candidates) {
/*  95 */     if (entries == null) {
/*  96 */       return -1;
/*     */     }
/*     */     
/*  99 */     int matches = 0;
/*     */ 
/*     */     
/* 102 */     for (i = 0; i < entries.length; i++) {
/* 103 */       if (entries[i].getAbsolutePath().startsWith(translated)) {
/* 104 */         matches++;
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 112 */     for (int i = 0; i < entries.length; i++) {
/* 113 */       if (entries[i].getAbsolutePath().startsWith(translated)) {
/* 114 */         String name = entries[i].getName() + ((matches == 1 && entries[i].isDirectory()) ? File.separator : " ");
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
/* 125 */         candidates.add(name);
/*     */       } 
/*     */     } 
/*     */     
/* 129 */     int index = buffer.lastIndexOf(File.separator);
/*     */     
/* 131 */     return index + File.separator.length();
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\jline\FileNameCompletor.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       1.0.4
 */