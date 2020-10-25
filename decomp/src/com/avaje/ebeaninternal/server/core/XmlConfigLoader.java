/*     */ package com.avaje.ebeaninternal.server.core;
/*     */ 
/*     */ import com.avaje.ebean.config.GlobalProperties;
/*     */ import com.avaje.ebeaninternal.api.ClassUtil;
/*     */ import com.avaje.ebeaninternal.server.lib.util.Dnode;
/*     */ import com.avaje.ebeaninternal.server.lib.util.DnodeReader;
/*     */ import com.avaje.ebeaninternal.server.util.ClassPathReader;
/*     */ import com.avaje.ebeaninternal.server.util.DefaultClassPathReader;
/*     */ import java.io.BufferedInputStream;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.net.URL;
/*     */ import java.net.URLDecoder;
/*     */ import java.nio.charset.Charset;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.jar.JarFile;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import java.util.zip.ZipEntry;
/*     */ import java.util.zip.ZipException;
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
/*     */ public class XmlConfigLoader
/*     */ {
/*  52 */   private static final Logger logger = Logger.getLogger(XmlConfigLoader.class.getName());
/*     */ 
/*     */   
/*     */   private final ClassPathReader classPathReader;
/*     */   
/*     */   private final Object[] classPaths;
/*     */ 
/*     */   
/*     */   public XmlConfigLoader(ClassLoader classLoader) {
/*  61 */     if (classLoader == null) {
/*  62 */       classLoader = getClass().getClassLoader();
/*     */     }
/*     */     
/*  65 */     String cn = GlobalProperties.get("ebean.classpathreader", null);
/*  66 */     if (cn != null) {
/*     */       
/*  68 */       logger.info("Using [" + cn + "] to read the searchable class path");
/*  69 */       this.classPathReader = (ClassPathReader)ClassUtil.newInstance(cn, getClass());
/*     */     } else {
/*  71 */       this.classPathReader = new DefaultClassPathReader();
/*     */     } 
/*     */     
/*  74 */     this.classPaths = this.classPathReader.readPath(classLoader);
/*     */   }
/*     */   
/*     */   public XmlConfig load() {
/*  78 */     List<Dnode> ormXml = search("META-INF/orm.xml");
/*  79 */     List<Dnode> ebeanOrmXml = search("META-INF/ebean-orm.xml");
/*     */     
/*  81 */     return new XmlConfig(ormXml, ebeanOrmXml);
/*     */   }
/*     */ 
/*     */   
/*     */   public List<Dnode> search(String searchFor) {
/*  86 */     ArrayList<Dnode> xmlList = new ArrayList<Dnode>();
/*     */     
/*  88 */     String charsetName = Charset.defaultCharset().name();
/*     */     
/*  90 */     for (int h = 0; h < this.classPaths.length; h++) {
/*     */ 
/*     */       
/*     */       try {
/*     */         
/*  95 */         if (URL.class.isInstance(this.classPaths[h])) {
/*  96 */           classPath = new File(((URL)this.classPaths[h]).getFile());
/*     */         } else {
/*  98 */           classPath = new File(this.classPaths[h].toString());
/*     */         } 
/*     */ 
/*     */         
/* 102 */         String path = URLDecoder.decode(classPath.getAbsolutePath(), charsetName);
/*     */         
/* 104 */         File classPath = new File(path);
/*     */         
/* 106 */         if (classPath.isDirectory()) {
/* 107 */           checkDir(searchFor, xmlList, classPath);
/*     */         }
/* 109 */         else if (classPath.getName().endsWith(".jar")) {
/* 110 */           checkJar(searchFor, xmlList, classPath);
/*     */         }
/*     */         else {
/*     */           
/* 114 */           String msg = "Not a Jar or Directory? " + classPath.getAbsolutePath();
/* 115 */           logger.log(Level.SEVERE, msg);
/*     */         }
/*     */       
/* 118 */       } catch (UnsupportedEncodingException e) {
/* 119 */         throw new RuntimeException(e);
/* 120 */       } catch (IOException e) {
/* 121 */         throw new RuntimeException(e);
/*     */       } 
/*     */     } 
/*     */     
/* 125 */     return xmlList;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void processInputStream(ArrayList<Dnode> xmlList, InputStream is) throws IOException {
/* 131 */     DnodeReader reader = new DnodeReader();
/* 132 */     Dnode xmlDoc = reader.parseXml(is);
/* 133 */     is.close();
/*     */     
/* 135 */     xmlList.add(xmlDoc);
/*     */   }
/*     */ 
/*     */   
/*     */   private void checkFile(String searchFor, ArrayList<Dnode> xmlList, File dir) throws IOException {
/* 140 */     File f = new File(dir, searchFor);
/* 141 */     if (f.exists()) {
/* 142 */       FileInputStream fis = new FileInputStream(f);
/* 143 */       BufferedInputStream is = new BufferedInputStream(fis);
/* 144 */       processInputStream(xmlList, is);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void checkDir(String searchFor, ArrayList<Dnode> xmlList, File dir) throws IOException {
/* 150 */     checkFile(searchFor, xmlList, dir);
/*     */     
/* 152 */     if (dir.getPath().endsWith("classes")) {
/*     */ 
/*     */       
/* 155 */       File parent = dir.getParentFile();
/* 156 */       if (parent != null && parent.getPath().endsWith("WEB-INF")) {
/* 157 */         parent = parent.getParentFile();
/* 158 */         if (parent != null) {
/* 159 */           File metaInf = new File(parent, "META-INF");
/* 160 */           if (metaInf.exists()) {
/* 161 */             checkFile(searchFor, xmlList, metaInf);
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void checkJar(String searchFor, ArrayList<Dnode> xmlList, File classPath) throws IOException {
/* 170 */     String fileName = classPath.getName();
/* 171 */     if (fileName.toLowerCase().startsWith("surefire")) {
/*     */       return;
/*     */     }
/* 174 */     module = null;
/*     */     try {
/* 176 */       module = new JarFile(classPath);
/* 177 */       ZipEntry entry = module.getEntry(searchFor);
/* 178 */       if (entry != null) {
/* 179 */         InputStream is = module.getInputStream(entry);
/* 180 */         processInputStream(xmlList, is);
/*     */       } 
/* 182 */     } catch (ZipException e) {
/* 183 */       logger.info("Unable to check jar file " + fileName + " for ebean-orm.xml");
/*     */     } finally {
/* 185 */       if (module != null)
/* 186 */         module.close(); 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\core\XmlConfigLoader.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */