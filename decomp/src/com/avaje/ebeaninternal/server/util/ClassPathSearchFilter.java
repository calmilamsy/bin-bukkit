/*     */ package com.avaje.ebeaninternal.server.util;
/*     */ 
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.Set;
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
/*     */ public class ClassPathSearchFilter
/*     */ {
/*     */   private static final String COM_AVAJE_EBEANINTERNAL_SERVER_BEAN = "com.avaje.ebeaninternal.server.bean";
/*     */   private static final String COM_AVAJE_EBEAN_META = "com.avaje.ebean.meta";
/*     */   private boolean defaultPackageMatch;
/*     */   private boolean defaultJarMatch;
/*     */   private String ebeanJarPrefix;
/*     */   private HashSet<String> includePackageSet;
/*     */   private HashSet<String> excludePackageSet;
/*     */   private HashSet<String> includeJarSet;
/*     */   private HashSet<String> excludeJarSet;
/*     */   
/*     */   public ClassPathSearchFilter() {
/*  35 */     this.defaultPackageMatch = true;
/*     */     
/*  37 */     this.defaultJarMatch = false;
/*     */     
/*  39 */     this.ebeanJarPrefix = "ebean";
/*     */     
/*  41 */     this.includePackageSet = new HashSet();
/*     */     
/*  43 */     this.excludePackageSet = new HashSet();
/*     */     
/*  45 */     this.includeJarSet = new HashSet();
/*     */     
/*  47 */     this.excludeJarSet = new HashSet();
/*     */ 
/*     */     
/*  50 */     addDefaultExcludePackages();
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
/*  62 */   public void setEbeanJarPrefix(String ebeanJarPrefix) { this.ebeanJarPrefix = ebeanJarPrefix; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  69 */   public Set<String> getIncludePackages() { return this.includePackageSet; }
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
/*     */   public void addDefaultExcludePackages() {
/*  83 */     excludePackage("sun");
/*  84 */     excludePackage("com.sun");
/*  85 */     excludePackage("java");
/*  86 */     excludePackage("javax");
/*  87 */     excludePackage("junit");
/*  88 */     excludePackage("org.w3c");
/*  89 */     excludePackage("org.xml");
/*  90 */     excludePackage("org.apache");
/*  91 */     excludePackage("com.mysql");
/*  92 */     excludePackage("oracle.jdbc");
/*  93 */     excludePackage("com.microsoft.sqlserver");
/*  94 */     excludePackage("com.avaje.ebean");
/*  95 */     excludePackage("com.avaje.lib");
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
/* 106 */   public void clearExcludePackages() { this.excludePackageSet.clear(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 114 */   public void setDefaultJarMatch(boolean defaultJarMatch) { this.defaultJarMatch = defaultJarMatch; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 122 */   public void setDefaultPackageMatch(boolean defaultPackageMatch) { this.defaultPackageMatch = defaultPackageMatch; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 129 */   public void includePackage(String pckgName) { this.includePackageSet.add(pckgName); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 136 */   public void excludePackage(String pckgName) { this.excludePackageSet.add(pckgName); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 143 */   public void excludeJar(String jarName) { this.includeJarSet.add(jarName); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 150 */   public void includeJar(String jarName) { this.includeJarSet.add(jarName); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isSearchPackage(String packageName) {
/* 159 */     if ("com.avaje.ebean.meta".equals(packageName)) {
/* 160 */       return true;
/*     */     }
/*     */     
/* 163 */     if ("com.avaje.ebeaninternal.server.bean".equals(packageName)) {
/* 164 */       return true;
/*     */     }
/* 166 */     if (this.includePackageSet != null && !this.includePackageSet.isEmpty()) {
/* 167 */       return containedIn(this.includePackageSet, packageName);
/*     */     }
/* 169 */     if (containedIn(this.excludePackageSet, packageName)) {
/* 170 */       return false;
/*     */     }
/* 172 */     return this.defaultPackageMatch;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isSearchJar(String jarName) {
/* 179 */     if (jarName.startsWith(this.ebeanJarPrefix)) {
/* 180 */       return true;
/*     */     }
/*     */     
/* 183 */     if (containedIn(this.includeJarSet, jarName)) {
/* 184 */       return true;
/*     */     }
/*     */     
/* 187 */     if (containedIn(this.excludeJarSet, jarName)) {
/* 188 */       return false;
/*     */     }
/* 190 */     return this.defaultJarMatch;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean containedIn(HashSet<String> set, String match) {
/* 197 */     if (set.contains(match)) {
/* 198 */       return true;
/*     */     }
/* 200 */     Iterator<String> incIt = set.iterator();
/* 201 */     while (incIt.hasNext()) {
/* 202 */       String val = (String)incIt.next();
/* 203 */       if (match.startsWith(val)) {
/* 204 */         return true;
/*     */       }
/*     */     } 
/* 207 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\serve\\util\ClassPathSearchFilter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */