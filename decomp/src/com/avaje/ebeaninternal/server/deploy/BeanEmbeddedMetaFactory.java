/*    */ package com.avaje.ebeaninternal.server.deploy;
/*    */ 
/*    */ import com.avaje.ebeaninternal.server.deploy.meta.DeployBeanPropertyAssocOne;
/*    */ import java.util.Map;
/*    */ import javax.persistence.PersistenceException;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BeanEmbeddedMetaFactory
/*    */ {
/*    */   public static BeanEmbeddedMeta create(BeanDescriptorMap owner, DeployBeanPropertyAssocOne<?> prop, BeanDescriptor<?> descriptor) {
/* 43 */     BeanDescriptor<?> targetDesc = owner.getBeanDescriptor(prop.getTargetType());
/* 44 */     if (targetDesc == null) {
/* 45 */       String msg = "Could not find BeanDescriptor for " + prop.getTargetType() + ". Perhaps the EmbeddedId class is not registered?";
/*    */       
/* 47 */       throw new PersistenceException(msg);
/*    */     } 
/*    */ 
/*    */     
/* 51 */     Map<String, String> propColMap = prop.getDeployEmbedded().getPropertyColumnMap();
/*    */     
/* 53 */     BeanProperty[] sourceProperties = targetDesc.propertiesBaseScalar();
/*    */     
/* 55 */     BeanProperty[] embeddedProperties = new BeanProperty[sourceProperties.length];
/*    */     
/* 57 */     for (int i = 0; i < sourceProperties.length; i++) {
/*    */       
/* 59 */       String propertyName = sourceProperties[i].getName();
/* 60 */       String dbColumn = (String)propColMap.get(propertyName);
/* 61 */       if (dbColumn == null)
/*    */       {
/* 63 */         dbColumn = sourceProperties[i].getDbColumn();
/*    */       }
/*    */       
/* 66 */       BeanPropertyOverride overrides = new BeanPropertyOverride(dbColumn);
/* 67 */       embeddedProperties[i] = new BeanProperty(sourceProperties[i], overrides);
/*    */     } 
/*    */     
/* 70 */     return new BeanEmbeddedMeta(embeddedProperties);
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\deploy\BeanEmbeddedMetaFactory.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */