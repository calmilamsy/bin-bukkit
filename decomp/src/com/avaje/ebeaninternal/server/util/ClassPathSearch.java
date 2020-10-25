/*     */ package com.avaje.ebeaninternal.server.util;
/*     */ 
/*     */ import com.avaje.ebean.config.GlobalProperties;
/*     */ import com.avaje.ebeaninternal.api.ClassUtil;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.net.MalformedURLException;
/*     */ import java.net.URL;
/*     */ import java.net.URLDecoder;
/*     */ import java.nio.charset.Charset;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collections;
/*     */ import java.util.Enumeration;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import java.util.jar.JarFile;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ClassPathSearch
/*     */ {
/*  54 */   private static final Logger logger = Logger.getLogger(ClassPathSearch.class.getName());
/*     */   
/*     */   ClassLoader classLoader;
/*     */   
/*     */   Object[] classPaths;
/*     */   
/*     */   ClassPathSearchFilter filter;
/*     */   ClassPathSearchMatcher matcher;
/*     */   
/*     */   public ClassPathSearch(ClassLoader classLoader, ClassPathSearchFilter filter, ClassPathSearchMatcher matcher) {
/*  64 */     this.matchList = new ArrayList();
/*     */     
/*  66 */     this.jarHits = new HashSet();
/*     */     
/*  68 */     this.packageHits = new HashSet();
/*     */     
/*  70 */     this.classPathReader = new DefaultClassPathReader();
/*     */ 
/*     */     
/*  73 */     this.classLoader = classLoader;
/*  74 */     this.filter = filter;
/*  75 */     this.matcher = matcher;
/*  76 */     initClassPaths();
/*     */   }
/*     */   ArrayList<Class<?>> matchList; HashSet<String> jarHits; HashSet<String> packageHits;
/*     */   ClassPathReader classPathReader;
/*     */   
/*     */   private void initClassPaths() {
/*     */     try {
/*  83 */       String cn = GlobalProperties.get("ebean.classpathreader", null);
/*  84 */       if (cn != null) {
/*     */         
/*  86 */         logger.info("Using [" + cn + "] to read the searchable class path");
/*  87 */         this.classPathReader = (ClassPathReader)ClassUtil.newInstance(cn, getClass());
/*     */       } 
/*     */       
/*  90 */       this.classPaths = this.classPathReader.readPath(this.classLoader);
/*     */       
/*  92 */       if (this.classPaths == null || this.classPaths.length == 0) {
/*  93 */         String msg = "ClassPath is EMPTY using ClassPathReader [" + this.classPathReader + "]";
/*  94 */         logger.warning(msg);
/*     */       } 
/*     */       
/*  97 */       boolean debug = GlobalProperties.getBoolean("ebean.debug.classpath", false);
/*  98 */       if (debug || logger.isLoggable(Level.FINER)) {
/*  99 */         String msg = "Classpath " + Arrays.toString(this.classPaths);
/* 100 */         logger.info(msg);
/*     */       }
/*     */     
/* 103 */     } catch (Exception e) {
/* 104 */       String msg = "Error trying to read the classpath entries";
/* 105 */       throw new RuntimeException(msg, e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 113 */   public Set<String> getJarHits() { return this.jarHits; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 120 */   public Set<String> getPackageHits() { return this.packageHits; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void registerHit(String jarFileName, Class<?> cls) {
/* 130 */     if (jarFileName != null) {
/* 131 */       this.jarHits.add(jarFileName);
/*     */     }
/* 133 */     Package pkg = cls.getPackage();
/* 134 */     if (pkg != null) {
/* 135 */       this.packageHits.add(pkg.getName());
/*     */     } else {
/* 137 */       this.packageHits.add("");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<Class<?>> findClasses() throws ClassNotFoundException {
/* 146 */     if (this.classPaths == null || this.classPaths.length == 0)
/*     */     {
/* 148 */       return this.matchList;
/*     */     }
/*     */     
/* 151 */     String charsetName = Charset.defaultCharset().name();
/*     */     
/* 153 */     for (int h = 0; h < this.classPaths.length; h++) {
/*     */       File classPath;
/* 155 */       String jarFileName = null;
/* 156 */       Enumeration<?> files = null;
/* 157 */       JarFile module = null;
/*     */ 
/*     */ 
/*     */       
/* 161 */       if (URL.class.isInstance(this.classPaths[h])) {
/* 162 */         classPath = new File(((URL)this.classPaths[h]).getFile());
/*     */       } else {
/* 164 */         classPath = new File(this.classPaths[h].toString());
/*     */       } 
/*     */ 
/*     */       
/*     */       try {
/* 169 */         String path = URLDecoder.decode(classPath.getAbsolutePath(), charsetName);
/* 170 */         classPath = new File(path);
/*     */       }
/* 172 */       catch (UnsupportedEncodingException e) {
/* 173 */         throw new RuntimeException(e);
/*     */       } 
/*     */       
/* 176 */       if (classPath.isDirectory()) {
/* 177 */         files = getDirectoryEnumeration(classPath);
/*     */       }
/* 179 */       else if (classPath.getName().endsWith(".jar")) {
/* 180 */         jarFileName = classPath.getName();
/* 181 */         if (!this.filter.isSearchJar(jarFileName)) {
/*     */           continue;
/*     */         }
/*     */ 
/*     */         
/*     */         try {
/* 187 */           module = new JarFile(classPath);
/* 188 */           files = module.entries();
/*     */         }
/* 190 */         catch (MalformedURLException ex) {
/* 191 */           throw new ClassNotFoundException("Bad classpath. Error: ", ex);
/*     */         }
/* 193 */         catch (IOException ex) {
/* 194 */           String msg = "jar file '" + classPath.getAbsolutePath() + "' could not be instantiate from file path. Error: ";
/*     */           
/* 196 */           throw new ClassNotFoundException(msg, ex);
/*     */         } 
/*     */       } else {
/*     */         
/* 200 */         String msg = "Error: expected classPath entry [" + classPath.getAbsolutePath() + "] to be a directory or a .jar file but it is not either of those?";
/*     */         
/* 202 */         logger.log(Level.SEVERE, msg);
/*     */       } 
/*     */       
/* 205 */       searchFiles(files, jarFileName);
/*     */       
/* 207 */       if (module != null) {
/*     */         
/*     */         try {
/* 210 */           module.close();
/* 211 */         } catch (IOException e) {
/* 212 */           String msg = "Error closing jar";
/* 213 */           throw new ClassNotFoundException(msg, e);
/*     */         } 
/*     */       }
/*     */     } 
/*     */     
/* 218 */     if (this.matchList.isEmpty()) {
/* 219 */       String msg = "No Entities found in ClassPath using ClassPathReader [" + this.classPathReader + "] Classpath Searched[" + Arrays.toString(this.classPaths) + "]";
/*     */       
/* 221 */       logger.warning(msg);
/*     */     } 
/*     */     
/* 224 */     return this.matchList;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private Enumeration<?> getDirectoryEnumeration(File classPath) {
/* 230 */     ArrayList<String> fileNameList = new ArrayList<String>();
/*     */     
/* 232 */     Set<String> includePkgs = this.filter.getIncludePackages();
/* 233 */     if (includePkgs.size() > 0) {
/*     */ 
/*     */       
/* 236 */       Iterator<String> it = includePkgs.iterator();
/* 237 */       while (it.hasNext()) {
/* 238 */         String pkg = (String)it.next();
/* 239 */         String relPath = pkg.replace('.', '/');
/* 240 */         File dir = new File(classPath, relPath);
/* 241 */         if (dir.exists()) {
/* 242 */           recursivelyListDir(fileNameList, dir, new StringBuilder(relPath));
/*     */         }
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/* 248 */       recursivelyListDir(fileNameList, classPath, new StringBuilder());
/*     */     } 
/*     */     
/* 251 */     return Collections.enumeration(fileNameList);
/*     */   }
/*     */ 
/*     */   
/*     */   private void searchFiles(Enumeration<?> files, String jarFileName) {
/* 256 */     while (files != null && files.hasMoreElements()) {
/*     */       
/* 258 */       String fileName = files.nextElement().toString();
/*     */ 
/*     */       
/* 261 */       if (fileName.endsWith(".class")) {
/*     */         
/* 263 */         String pckgName, className = fileName.replace('/', '.').substring(0, fileName.length() - 6);
/* 264 */         int lastPeriod = className.lastIndexOf(".");
/*     */ 
/*     */         
/* 267 */         if (lastPeriod > 0) {
/* 268 */           pckgName = className.substring(0, lastPeriod);
/*     */         } else {
/* 270 */           pckgName = "";
/*     */         } 
/*     */         
/* 273 */         if (!this.filter.isSearchPackage(pckgName)) {
/*     */           continue;
/*     */         }
/*     */ 
/*     */         
/* 278 */         Class<?> theClass = null;
/*     */         try {
/* 280 */           theClass = Class.forName(className, false, this.classLoader);
/*     */           
/* 282 */           if (this.matcher.isMatch(theClass)) {
/* 283 */             this.matchList.add(theClass);
/* 284 */             registerHit(jarFileName, theClass);
/*     */           }
/*     */         
/* 287 */         } catch (ClassNotFoundException e) {
/*     */           
/* 289 */           logger.finer("Error searching classpath" + e.getMessage());
/*     */         
/*     */         }
/* 292 */         catch (NoClassDefFoundError e) {
/*     */           
/* 294 */           logger.finer("Error searching classpath: " + e.getMessage());
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void recursivelyListDir(List<String> fileNameList, File dir, StringBuilder relativePath) {
/* 305 */     if (dir.isDirectory()) {
/* 306 */       File[] files = dir.listFiles();
/*     */       
/* 308 */       for (int i = 0; i < files.length; i++) {
/*     */         
/* 310 */         int prevLen = relativePath.length();
/* 311 */         relativePath.append((prevLen == 0) ? "" : "/").append(files[i].getName());
/*     */         
/* 313 */         recursivelyListDir(fileNameList, files[i], relativePath);
/*     */ 
/*     */         
/* 316 */         relativePath.delete(prevLen, relativePath.length());
/*     */       } 
/*     */     } else {
/*     */       
/* 320 */       fileNameList.add(relativePath.toString());
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\serve\\util\ClassPathSearch.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */