/*     */ package com.avaje.ebeaninternal.server.subclass;
/*     */ 
/*     */ import com.avaje.ebean.config.ServerConfig;
/*     */ import com.avaje.ebean.enhance.agent.EnhanceConstants;
/*     */ import com.avaje.ebeaninternal.api.ClassUtil;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedActionException;
/*     */ import java.security.PrivilegedExceptionAction;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import java.util.logging.Logger;
/*     */ import javax.persistence.PersistenceException;
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
/*     */ public class SubClassManager
/*     */   implements EnhanceConstants
/*     */ {
/*  43 */   private static final Logger logger = Logger.getLogger(SubClassManager.class.getName());
/*     */ 
/*     */ 
/*     */   
/*     */   private final ConcurrentHashMap<String, Class<?>> clzMap;
/*     */ 
/*     */ 
/*     */   
/*     */   private final SubClassFactory subclassFactory;
/*     */ 
/*     */   
/*     */   private final String serverName;
/*     */ 
/*     */   
/*     */   private final int logLevel;
/*     */ 
/*     */ 
/*     */   
/*     */   public SubClassManager(ServerConfig serverConfig) {
/*  62 */     String s = serverConfig.getProperty("subClassManager.preferContextClassloader", "true");
/*  63 */     final boolean preferContext = "true".equalsIgnoreCase(s);
/*     */     
/*  65 */     this.serverName = serverConfig.getName();
/*  66 */     this.logLevel = serverConfig.getEnhanceLogLevel();
/*  67 */     this.clzMap = new ConcurrentHashMap();
/*     */     
/*     */     try {
/*  70 */       this.subclassFactory = (SubClassFactory)AccessController.doPrivileged(new PrivilegedExceptionAction()
/*     */           {
/*     */             public Object run() {
/*  73 */               ClassLoader cl = ClassUtil.getClassLoader(getClass(), preferContext);
/*  74 */               logger.info("SubClassFactory parent ClassLoader [" + cl.getClass().getName() + "]");
/*  75 */               return new SubClassFactory(cl, SubClassManager.this.logLevel);
/*     */             }
/*     */           });
/*  78 */     } catch (PrivilegedActionException e) {
/*  79 */       throw new PersistenceException(e);
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
/*     */ 
/*     */   
/*     */   public Class<?> resolve(String name) {
/*  96 */     synchronized (this) {
/*  97 */       String superName = SubClassUtil.getSuperClassName(name);
/*  98 */       Class<?> clz = (Class)this.clzMap.get(superName);
/*  99 */       if (clz == null) {
/* 100 */         clz = createClass(superName);
/* 101 */         this.clzMap.put(superName, clz);
/*     */       } 
/* 103 */       return clz;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private Class<?> createClass(String name) {
/*     */     try {
/* 111 */       Class<?> superClass = Class.forName(name, true, this.subclassFactory.getParent());
/*     */       
/* 113 */       return this.subclassFactory.create(superClass, this.serverName);
/*     */     }
/* 115 */     catch (Exception ex) {
/* 116 */       String m = "Error creating subclass for [" + name + "]";
/* 117 */       throw new PersistenceException(m, ex);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\subclass\SubClassManager.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */