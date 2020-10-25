/*     */ package com.avaje.ebeaninternal.server.resource;
/*     */ 
/*     */ import com.avaje.ebean.config.GlobalProperties;
/*     */ import com.avaje.ebean.config.ServerConfig;
/*     */ import com.avaje.ebeaninternal.server.lib.resource.DirectoryFinder;
/*     */ import com.avaje.ebeaninternal.server.lib.resource.FileResourceSource;
/*     */ import com.avaje.ebeaninternal.server.lib.resource.ResourceSource;
/*     */ import com.avaje.ebeaninternal.server.lib.resource.UrlResourceSource;
/*     */ import com.avaje.ebeaninternal.server.lib.util.NotFoundException;
/*     */ import java.io.File;
/*     */ import java.util.logging.Logger;
/*     */ import javax.servlet.ServletContext;
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
/*     */ public class ResourceManagerFactory
/*     */ {
/*  44 */   private static final Logger logger = Logger.getLogger(ResourceManagerFactory.class.getName());
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
/*     */   public static ResourceManager createResourceManager(ServerConfig serverConfig) {
/*  57 */     ResourceSource resourceSource = createResourceSource(serverConfig);
/*  58 */     File autofetchDir = getAutofetchDir(serverConfig, resourceSource);
/*     */     
/*  60 */     return new ResourceManager(resourceSource, autofetchDir);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static File getAutofetchDir(ServerConfig serverConfig, ResourceSource resourceSource) {
/*  69 */     String dir = null;
/*  70 */     if (serverConfig.getAutofetchConfig() != null) {
/*  71 */       dir = serverConfig.getAutofetchConfig().getLogDirectoryWithEval();
/*     */     }
/*  73 */     if (dir != null) {
/*  74 */       return new File(dir);
/*     */     }
/*     */     
/*  77 */     String realPath = resourceSource.getRealPath();
/*  78 */     if (realPath != null) {
/*  79 */       return new File(realPath);
/*     */     }
/*     */     
/*  82 */     throw new RuntimeException("No autofetch directory set?");
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
/*     */   protected static ResourceSource createResourceSource(ServerConfig serverConfig) {
/*  95 */     String defaultDir = serverConfig.getResourceDirectory();
/*     */ 
/*     */ 
/*     */     
/*  99 */     ServletContext sc = GlobalProperties.getServletContext();
/* 100 */     if (sc != null) {
/*     */       
/* 102 */       if (defaultDir == null) {
/* 103 */         defaultDir = "WEB-INF/ebean";
/*     */       }
/* 105 */       return new UrlResourceSource(sc, defaultDir);
/*     */     } 
/*     */ 
/*     */     
/* 109 */     return createFileSource(defaultDir);
/*     */   }
/*     */ 
/*     */   
/*     */   private static ResourceSource createFileSource(String fileDir) {
/* 114 */     if (fileDir != null) {
/*     */       
/* 116 */       File dir = new File(fileDir);
/* 117 */       if (dir.exists()) {
/* 118 */         logger.info("ResourceManager initialised: type[file] [" + fileDir + "]");
/* 119 */         return new FileResourceSource(fileDir);
/*     */       } 
/* 121 */       String msg = "ResourceManager could not find directory [" + fileDir + "]";
/* 122 */       throw new NotFoundException(msg);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 128 */     File guessDir = DirectoryFinder.find(null, "WEB-INF", 3);
/* 129 */     if (guessDir != null) {
/*     */ 
/*     */       
/* 132 */       logger.info("ResourceManager initialised: type[file] [" + guessDir.getPath() + "]");
/* 133 */       return new FileResourceSource(guessDir.getPath());
/*     */     } 
/*     */ 
/*     */     
/* 137 */     File workingDir = new File(".");
/* 138 */     return new FileResourceSource(workingDir);
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\resource\ResourceManagerFactory.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */