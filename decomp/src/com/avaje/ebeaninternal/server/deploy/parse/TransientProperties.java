/*    */ package com.avaje.ebeaninternal.server.deploy.parse;
/*    */ 
/*    */ import com.avaje.ebeaninternal.server.deploy.meta.DeployBeanDescriptor;
/*    */ import com.avaje.ebeaninternal.server.deploy.meta.DeployBeanProperty;
/*    */ import com.avaje.ebeaninternal.server.deploy.meta.DeployBeanPropertyAssocMany;
/*    */ import com.avaje.ebeaninternal.server.deploy.meta.DeployBeanPropertyAssocOne;
/*    */ import java.util.List;
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
/*    */ public class TransientProperties
/*    */ {
/*    */   public void process(DeployBeanDescriptor<?> desc) {
/* 42 */     List<DeployBeanProperty> props = desc.propertiesBase();
/* 43 */     for (i = 0; i < props.size(); i++) {
/* 44 */       DeployBeanProperty prop = (DeployBeanProperty)props.get(i);
/* 45 */       if (!prop.isDbRead() && !prop.isDbInsertable() && !prop.isDbUpdateable())
/*    */       {
/* 47 */         prop.setTransient(true);
/*    */       }
/*    */     } 
/*    */     
/* 51 */     List<DeployBeanPropertyAssocOne<?>> ones = desc.propertiesAssocOne();
/* 52 */     for (i = 0; i < ones.size(); i++) {
/* 53 */       DeployBeanPropertyAssocOne<?> prop = (DeployBeanPropertyAssocOne)ones.get(i);
/* 54 */       if (prop.getBeanTable() == null && 
/* 55 */         !prop.isEmbedded()) {
/* 56 */         prop.setTransient(true);
/*    */       }
/*    */     } 
/*    */ 
/*    */     
/* 61 */     List<DeployBeanPropertyAssocMany<?>> manys = desc.propertiesAssocMany();
/* 62 */     for (int i = 0; i < manys.size(); i++) {
/* 63 */       DeployBeanPropertyAssocMany<?> prop = (DeployBeanPropertyAssocMany)manys.get(i);
/* 64 */       if (prop.getBeanTable() == null)
/* 65 */         prop.setTransient(true); 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\deploy\parse\TransientProperties.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */