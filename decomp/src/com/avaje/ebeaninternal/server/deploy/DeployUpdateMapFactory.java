/*    */ package com.avaje.ebeaninternal.server.deploy;
/*    */ 
/*    */ import com.avaje.ebeaninternal.server.deploy.id.ImportedId;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ import java.util.logging.Level;
/*    */ import java.util.logging.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DeployUpdateMapFactory
/*    */ {
/* 16 */   private static final Logger logger = Logger.getLogger(DeployUpdateMapFactory.class.getName());
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static Map<String, String> build(BeanDescriptor<?> descriptor) {
/* 26 */     Map<String, String> deployMap = new HashMap<String, String>();
/*    */     
/* 28 */     String shortName = descriptor.getName();
/* 29 */     String beanName = shortName.toLowerCase();
/* 30 */     deployMap.put(beanName, descriptor.getBaseTable());
/*    */     
/* 32 */     BeanProperty[] baseScalar = descriptor.propertiesBaseScalar();
/* 33 */     for (BeanProperty baseProp : baseScalar) {
/*    */       
/* 35 */       if (baseProp.isDbInsertable() || baseProp.isDbUpdatable()) {
/* 36 */         deployMap.put(baseProp.getName().toLowerCase(), baseProp.getDbColumn());
/*    */       }
/*    */     } 
/*    */     
/* 40 */     BeanPropertyAssocOne[] oneImported = descriptor.propertiesOneImported();
/* 41 */     for (BeanPropertyAssocOne<?> assocOne : oneImported) {
/*    */       
/* 43 */       ImportedId importedId = assocOne.getImportedId();
/* 44 */       if (importedId == null) {
/* 45 */         String m = descriptor.getFullName() + " importedId is null for associated: " + assocOne.getFullBeanName();
/* 46 */         logger.log(Level.SEVERE, m);
/*    */       }
/* 48 */       else if (importedId.isScalar()) {
/* 49 */         deployMap.put(importedId.getLogicalName(), importedId.getDbColumn());
/*    */       } 
/*    */     } 
/*    */     
/* 53 */     return deployMap;
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\deploy\DeployUpdateMapFactory.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */