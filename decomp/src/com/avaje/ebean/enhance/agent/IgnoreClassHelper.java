/*     */ package com.avaje.ebean.enhance.agent;
/*     */ 
/*     */ import java.util.HashMap;
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
/*     */ public class IgnoreClassHelper
/*     */ {
/*     */   private final String[] processPackages;
/*     */   
/*     */   public IgnoreClassHelper(String agentArgs) {
/*  24 */     HashMap<String, String> args = ArgParser.parse(agentArgs);
/*  25 */     String packages = (String)args.get("packages");
/*  26 */     if (packages != null) {
/*  27 */       String[] pkgs = packages.split(",");
/*  28 */       this.processPackages = new String[pkgs.length];
/*  29 */       for (int i = 0; i < pkgs.length; i++) {
/*  30 */         this.processPackages[i] = convertPackage(pkgs[i]);
/*     */       }
/*     */     } else {
/*  33 */       this.processPackages = new String[0];
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String convertPackage(String pkg) {
/*  42 */     pkg = pkg.trim().replace('.', '/');
/*     */     
/*  44 */     if (pkg.endsWith("*"))
/*     */     {
/*     */       
/*  47 */       return pkg.substring(0, pkg.length() - 1);
/*     */     }
/*  49 */     if (pkg.endsWith("/"))
/*     */     {
/*  51 */       return pkg;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*  56 */     return pkg + "/";
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
/*     */   private boolean specificMatching(String className) {
/*  72 */     for (int i = 0; i < this.processPackages.length; i++) {
/*  73 */       if (className.startsWith(this.processPackages[i]))
/*     */       {
/*  75 */         return false;
/*     */       }
/*     */     } 
/*     */     
/*  79 */     return true;
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
/*     */   public boolean isIgnoreClass(String className) {
/*  96 */     className = className.replace('.', '/');
/*     */ 
/*     */     
/*  99 */     if (className.startsWith("com/avaje/ebean/meta/")) {
/* 100 */       return false;
/*     */     }
/*     */     
/* 103 */     if (this.processPackages.length > 0)
/*     */     {
/* 105 */       return specificMatching(className);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 113 */     if (className.startsWith("com/avaje/ebean")) {
/* 114 */       return true;
/*     */     }
/*     */     
/* 117 */     if (className.startsWith("java/") || className.startsWith("javax/")) {
/* 118 */       return true;
/*     */     }
/* 120 */     if (className.startsWith("sun/") || className.startsWith("sunw/") || className.startsWith("com/sun/")) {
/* 121 */       return true;
/*     */     }
/* 123 */     if (className.startsWith("org/wc3/") || className.startsWith("org/xml/")) {
/* 124 */       return true;
/*     */     }
/* 126 */     if (className.startsWith("org/junit/") || className.startsWith("junit/")) {
/* 127 */       return true;
/*     */     }
/*     */     
/* 130 */     if (className.startsWith("org/apache/")) {
/* 131 */       return true;
/*     */     }
/* 133 */     if (className.startsWith("org/eclipse/")) {
/* 134 */       return true;
/*     */     }
/* 136 */     if (className.startsWith("org/joda/")) {
/* 137 */       return true;
/*     */     }
/*     */     
/* 140 */     if (className.startsWith("com/mysql/jdbc")) {
/* 141 */       return true;
/*     */     }
/*     */     
/* 144 */     if (className.startsWith("org/postgresql/")) {
/* 145 */       return true;
/*     */     }
/*     */     
/* 148 */     if (className.startsWith("org/h2/")) {
/* 149 */       return true;
/*     */     }
/*     */     
/* 152 */     if (className.startsWith("oracle/")) {
/* 153 */       return true;
/*     */     }
/*     */     
/* 156 */     if (className.startsWith("groovy/")) {
/* 157 */       return true;
/*     */     }
/*     */     
/* 160 */     if (className.startsWith("$")) {
/* 161 */       return true;
/*     */     }
/* 163 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebean\enhance\agent\IgnoreClassHelper.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */