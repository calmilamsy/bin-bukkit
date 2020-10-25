/*     */ package com.avaje.ebeaninternal.server.subclass;
/*     */ 
/*     */ import com.avaje.ebean.enhance.agent.ClassPathClassBytesReader;
/*     */ import com.avaje.ebean.enhance.agent.EnhanceConstants;
/*     */ import com.avaje.ebean.enhance.agent.EnhanceContext;
/*     */ import com.avaje.ebean.enhance.asm.ClassReader;
/*     */ import com.avaje.ebean.enhance.asm.ClassWriter;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
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
/*     */ public class SubClassFactory
/*     */   extends ClassLoader
/*     */   implements EnhanceConstants, GenSuffix
/*     */ {
/*  43 */   private static final Logger logger = Logger.getLogger(SubClassFactory.class.getName());
/*     */ 
/*     */   
/*     */   private static final int CLASS_WRITER_FLAGS = 3;
/*     */ 
/*     */   
/*     */   private final EnhanceContext enhanceContext;
/*     */ 
/*     */   
/*     */   private final ClassLoader parentClassLoader;
/*     */ 
/*     */   
/*     */   public SubClassFactory(ClassLoader parent, int logLevel) {
/*  56 */     super(parent);
/*  57 */     this.parentClassLoader = parent;
/*     */     
/*  59 */     ClassPathClassBytesReader reader = new ClassPathClassBytesReader(null);
/*  60 */     this.enhanceContext = new EnhanceContext(reader, true, "debug=" + logLevel);
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
/*     */   public Class<?> create(Class<?> normalClass, String serverName) throws IOException {
/*  73 */     String subClassSuffix = "$$EntityBean";
/*  74 */     if (serverName != null) {
/*  75 */       subClassSuffix = subClassSuffix + "$" + serverName;
/*     */     }
/*     */ 
/*     */     
/*  79 */     String clsName = normalClass.getName();
/*  80 */     String subClsName = clsName + subClassSuffix;
/*     */     
/*     */     try {
/*  83 */       byte[] newClsBytes = subclassBytes(clsName, subClassSuffix);
/*     */       
/*  85 */       return defineClass(subClsName, newClsBytes, 0, newClsBytes.length);
/*     */     
/*     */     }
/*  88 */     catch (IOException ex) {
/*  89 */       String m = "Error creating subclass for [" + clsName + "]";
/*  90 */       logger.log(Level.SEVERE, m, ex);
/*  91 */       throw ex;
/*     */     }
/*  93 */     catch (Throwable ex) {
/*  94 */       String m = "Error creating subclass for [" + clsName + "]";
/*  95 */       logger.log(Level.SEVERE, m, ex);
/*  96 */       throw new RuntimeException(ex);
/*     */     } 
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
/*     */   private byte[] subclassBytes(String className, String subClassSuffix) throws IOException {
/* 111 */     String resName = className.replace('.', '/') + ".class";
/*     */     
/* 113 */     InputStream is = getResourceAsStream(resName);
/*     */     
/* 115 */     ClassReader cr = new ClassReader(is);
/* 116 */     ClassWriter cw = new ClassWriter(3);
/*     */     
/* 118 */     SubClassClassAdpater ca = new SubClassClassAdpater(subClassSuffix, cw, this.parentClassLoader, this.enhanceContext);
/* 119 */     if (ca.isLog(1)) {
/* 120 */       ca.log(" enhancing " + className + subClassSuffix);
/*     */     }
/*     */     
/* 123 */     cr.accept(ca, 0);
/*     */     
/* 125 */     return cw.toByteArray();
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\subclass\SubClassFactory.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */