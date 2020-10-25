/*     */ package com.mysql.jdbc.util;
/*     */ 
/*     */ import com.mysql.jdbc.NonRegisteringDriver;
/*     */ import java.io.File;
/*     */ import java.io.FileOutputStream;
/*     */ import java.sql.Connection;
/*     */ import java.sql.ResultSet;
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
/*     */ public class VersionFSHierarchyMaker
/*     */ {
/*     */   public static void main(String[] args) throws Exception {
/*  43 */     if (args.length < 3) {
/*  44 */       usage();
/*  45 */       System.exit(1);
/*     */     } 
/*     */     
/*  48 */     String jdbcUrl = null;
/*     */     
/*  50 */     String jvmVersion = removeWhitespaceChars(System.getProperty("java.version"));
/*  51 */     String jvmVendor = removeWhitespaceChars(System.getProperty("java.vendor"));
/*  52 */     String osName = removeWhitespaceChars(System.getProperty("os.name"));
/*  53 */     String osArch = removeWhitespaceChars(System.getProperty("os.arch"));
/*  54 */     String osVersion = removeWhitespaceChars(System.getProperty("os.version"));
/*     */     
/*  56 */     jdbcUrl = System.getProperty("com.mysql.jdbc.testsuite.url");
/*     */     
/*  58 */     String mysqlVersion = "not-available";
/*     */     
/*     */     try {
/*  61 */       Connection conn = (new NonRegisteringDriver()).connect(jdbcUrl, null);
/*     */       
/*  63 */       ResultSet rs = conn.createStatement().executeQuery("SELECT VERSION()");
/*  64 */       rs.next();
/*  65 */       mysqlVersion = removeWhitespaceChars(rs.getString(1));
/*  66 */     } catch (Throwable t) {
/*  67 */       mysqlVersion = "no-server-running-on-" + removeWhitespaceChars(jdbcUrl);
/*     */     } 
/*     */     
/*  70 */     String jvmSubdirName = jvmVendor + "-" + jvmVersion;
/*  71 */     String osSubdirName = osName + "-" + osArch + "-" + osVersion;
/*     */     
/*  73 */     File baseDir = new File(args[1]);
/*  74 */     File mysqlVersionDir = new File(baseDir, mysqlVersion);
/*  75 */     File osVersionDir = new File(mysqlVersionDir, osSubdirName);
/*  76 */     File jvmVersionDir = new File(osVersionDir, jvmSubdirName);
/*     */     
/*  78 */     jvmVersionDir.mkdirs();
/*     */ 
/*     */     
/*  81 */     FileOutputStream pathOut = null;
/*     */     
/*     */     try {
/*  84 */       String propsOutputPath = args[2];
/*  85 */       pathOut = new FileOutputStream(propsOutputPath);
/*  86 */       String baseDirStr = baseDir.getAbsolutePath();
/*  87 */       String jvmVersionDirStr = jvmVersionDir.getAbsolutePath();
/*     */       
/*  89 */       if (jvmVersionDirStr.startsWith(baseDirStr)) {
/*  90 */         jvmVersionDirStr = jvmVersionDirStr.substring(baseDirStr.length() + 1);
/*     */       }
/*     */       
/*  93 */       pathOut.write(jvmVersionDirStr.getBytes());
/*     */     } finally {
/*  95 */       if (pathOut != null) {
/*  96 */         pathOut.flush();
/*  97 */         pathOut.close();
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public static String removeWhitespaceChars(String input) {
/* 103 */     if (input == null) {
/* 104 */       return input;
/*     */     }
/*     */     
/* 107 */     int strLen = input.length();
/*     */     
/* 109 */     StringBuffer output = new StringBuffer(strLen);
/*     */     
/* 111 */     for (int i = 0; i < strLen; i++) {
/* 112 */       char c = input.charAt(i);
/* 113 */       if (!Character.isDigit(c) && !Character.isLetter(c)) {
/* 114 */         if (Character.isWhitespace(c)) {
/* 115 */           output.append("_");
/*     */         } else {
/* 117 */           output.append(".");
/*     */         } 
/*     */       } else {
/* 120 */         output.append(c);
/*     */       } 
/*     */     } 
/*     */     
/* 124 */     return output.toString();
/*     */   }
/*     */   
/*     */   private static void usage() {
/* 128 */     System.err.println("Creates a fs hierarchy representing MySQL version, OS version and JVM version.");
/* 129 */     System.err.println("Stores the full path as 'outputDirectory' property in file 'directoryPropPath'");
/* 130 */     System.err.println();
/* 131 */     System.err.println("Usage: java VersionFSHierarchyMaker unit|compliance baseDirectory directoryPropPath");
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\mysql\jdb\\util\VersionFSHierarchyMaker.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */