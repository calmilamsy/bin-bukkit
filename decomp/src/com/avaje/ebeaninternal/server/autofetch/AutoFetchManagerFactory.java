/*    */ package com.avaje.ebeaninternal.server.autofetch;
/*    */ 
/*    */ import com.avaje.ebean.config.GlobalProperties;
/*    */ import com.avaje.ebean.config.ServerConfig;
/*    */ import com.avaje.ebeaninternal.api.SpiEbeanServer;
/*    */ import com.avaje.ebeaninternal.server.resource.ResourceManager;
/*    */ import java.io.File;
/*    */ import java.io.FileInputStream;
/*    */ import java.io.ObjectInputStream;
/*    */ import java.util.logging.Level;
/*    */ import java.util.logging.Logger;
/*    */ import javax.persistence.PersistenceException;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AutoFetchManagerFactory
/*    */ {
/* 18 */   private static final Logger logger = Logger.getLogger(AutoFetchManagerFactory.class.getName());
/*    */ 
/*    */ 
/*    */   
/*    */   public static AutoFetchManager create(SpiEbeanServer server, ServerConfig serverConfig, ResourceManager resourceManager) {
/* 23 */     AutoFetchManagerFactory me = new AutoFetchManagerFactory();
/* 24 */     return me.createAutoFetchManager(server, serverConfig, resourceManager);
/*    */   }
/*    */ 
/*    */   
/*    */   private AutoFetchManager createAutoFetchManager(SpiEbeanServer server, ServerConfig serverConfig, ResourceManager resourceManager) {
/* 29 */     AutoFetchManager manager = createAutoFetchManager(server.getName(), resourceManager);
/* 30 */     manager.setOwner(server, serverConfig);
/*    */     
/* 32 */     return manager;
/*    */   }
/*    */ 
/*    */   
/*    */   private AutoFetchManager createAutoFetchManager(String serverName, ResourceManager resourceManager) {
/* 37 */     File autoFetchFile = getAutoFetchFile(serverName, resourceManager);
/*    */     
/* 39 */     AutoFetchManager autoFetchManager = null;
/*    */     
/* 41 */     boolean readFile = GlobalProperties.getBoolean("autofetch.readfromfile", true);
/* 42 */     if (readFile) {
/* 43 */       autoFetchManager = deserializeAutoFetch(autoFetchFile);
/*    */     }
/*    */     
/* 46 */     if (autoFetchManager == null)
/*    */     {
/*    */ 
/*    */       
/* 50 */       autoFetchManager = new DefaultAutoFetchManager(autoFetchFile.getAbsolutePath());
/*    */     }
/*    */     
/* 53 */     return autoFetchManager;
/*    */   }
/*    */ 
/*    */   
/*    */   private AutoFetchManager deserializeAutoFetch(File autoFetchFile) {
/*    */     try {
/* 59 */       if (!autoFetchFile.exists()) {
/* 60 */         return null;
/*    */       }
/* 62 */       FileInputStream fi = new FileInputStream(autoFetchFile);
/* 63 */       ObjectInputStream ois = new ObjectInputStream(fi);
/* 64 */       AutoFetchManager profListener = (AutoFetchManager)ois.readObject();
/*    */       
/* 66 */       logger.info("AutoFetch deserialized from file [" + autoFetchFile.getAbsolutePath() + "]");
/*    */       
/* 68 */       return profListener;
/*    */     }
/* 70 */     catch (Exception ex) {
/* 71 */       logger.log(Level.SEVERE, "Error loading autofetch file " + autoFetchFile.getAbsolutePath(), ex);
/* 72 */       return null;
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private File getAutoFetchFile(String serverName, ResourceManager resourceManager) {
/* 81 */     String fileName = ".ebean." + serverName + ".autofetch";
/*    */     
/* 83 */     File dir = resourceManager.getAutofetchDirectory();
/*    */     
/* 85 */     if (!dir.exists())
/*    */     {
/*    */       
/* 88 */       if (!dir.mkdirs()) {
/* 89 */         String m = "Unable to create directory [" + dir + "] for autofetch file [" + fileName + "]";
/* 90 */         throw new PersistenceException(m);
/*    */       } 
/*    */     }
/*    */     
/* 94 */     return new File(dir, fileName);
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\autofetch\AutoFetchManagerFactory.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */