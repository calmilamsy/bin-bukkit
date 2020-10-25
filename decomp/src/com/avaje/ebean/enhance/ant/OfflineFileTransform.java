/*     */ package com.avaje.ebean.enhance.ant;
/*     */ 
/*     */ import com.avaje.ebean.enhance.agent.InputStreamTransform;
/*     */ import com.avaje.ebean.enhance.agent.Transformer;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.lang.instrument.IllegalClassFormatException;
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
/*     */ public class OfflineFileTransform
/*     */ {
/*     */   final InputStreamTransform inputStreamTransform;
/*     */   final String inDir;
/*     */   final String outDir;
/*     */   private TransformationListener listener;
/*     */   
/*     */   public OfflineFileTransform(Transformer transformer, ClassLoader classLoader, String inDir, String outDir) {
/*  41 */     this.inputStreamTransform = new InputStreamTransform(transformer, classLoader);
/*  42 */     inDir = trimSlash(inDir);
/*  43 */     this.inDir = inDir;
/*  44 */     this.outDir = (outDir == null) ? inDir : outDir;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*  49 */   public void setListener(TransformationListener v) { this.listener = v; }
/*     */ 
/*     */   
/*     */   private String trimSlash(String dir) {
/*  53 */     if (dir.endsWith("/")) {
/*  54 */       return dir.substring(0, dir.length() - 1);
/*     */     }
/*  56 */     return dir;
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
/*     */   public void process(String packageNames) {
/*  69 */     if (packageNames == null) {
/*  70 */       processPackage("", true);
/*     */       
/*     */       return;
/*     */     } 
/*  74 */     String[] pkgs = packageNames.split(",");
/*  75 */     for (int i = 0; i < pkgs.length; i++) {
/*     */       
/*  77 */       String pkg = pkgs[i].trim().replace('.', '/');
/*     */       
/*  79 */       boolean recurse = false;
/*  80 */       if (pkg.endsWith("**")) {
/*  81 */         recurse = true;
/*  82 */         pkg = pkg.substring(0, pkg.length() - 2);
/*  83 */       } else if (pkg.endsWith("*")) {
/*  84 */         recurse = true;
/*  85 */         pkg = pkg.substring(0, pkg.length() - 1);
/*     */       } 
/*     */       
/*  88 */       pkg = trimSlash(pkg);
/*     */       
/*  90 */       processPackage(pkg, recurse);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void processPackage(String dir, boolean recurse) {
/*  96 */     this.inputStreamTransform.log(1, "transform> pkg: " + dir);
/*     */     
/*  98 */     String dirPath = this.inDir + "/" + dir;
/*  99 */     File d = new File(dirPath);
/* 100 */     if (!d.exists()) {
/* 101 */       String m = "File not found " + dirPath;
/* 102 */       throw new RuntimeException(m);
/*     */     } 
/*     */     
/* 105 */     File[] files = d.listFiles();
/*     */     
/* 107 */     File file = null;
/*     */     
/*     */     try {
/* 110 */       for (int i = 0; i < files.length; i++) {
/* 111 */         file = files[i];
/* 112 */         if (file.isDirectory()) {
/* 113 */           if (recurse) {
/* 114 */             String subdir = dir + "/" + file.getName();
/* 115 */             processPackage(subdir, recurse);
/*     */           } 
/*     */         } else {
/* 118 */           String fileName = file.getName();
/* 119 */           if (fileName.endsWith(".java")) {
/*     */             
/* 121 */             System.err.println("Expecting a .class file but got " + fileName + " ... ignoring");
/*     */           }
/* 123 */           else if (fileName.endsWith(".class")) {
/* 124 */             transformFile(file);
/*     */           }
/*     */         
/*     */         } 
/*     */       } 
/* 129 */     } catch (Exception e) {
/* 130 */       String fileName = (file == null) ? "null" : file.getName();
/* 131 */       String m = "Error transforming file " + fileName;
/* 132 */       throw new RuntimeException(m, e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void transformFile(File file) throws IOException, IllegalClassFormatException {
/* 139 */     String className = getClassName(file);
/*     */     
/* 141 */     byte[] result = this.inputStreamTransform.transform(className, file);
/*     */     
/* 143 */     if (result != null) {
/* 144 */       InputStreamTransform.writeBytes(result, file);
/* 145 */       if (this.listener != null) {
/* 146 */         this.listener.logEvent("Enhanced " + file);
/*     */       }
/*     */     }
/* 149 */     else if (this.listener != null) {
/* 150 */       this.listener.logError("Unable to enhance " + file);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private String getClassName(File file) {
/* 156 */     String path = file.getPath();
/* 157 */     path = path.substring(this.inDir.length() + 1);
/* 158 */     path = path.substring(0, path.length() - ".class".length());
/*     */     
/* 160 */     return StringReplace.replace(path, "\\", "/");
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebean\enhance\ant\OfflineFileTransform.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */