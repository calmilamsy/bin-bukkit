/*    */ package com.avaje.ebeaninternal.server.deploy.parse;
/*    */ 
/*    */ import com.avaje.ebean.annotation.LdapAttribute;
/*    */ import com.avaje.ebean.config.ldap.LdapAttributeAdapter;
/*    */ import com.avaje.ebeaninternal.server.deploy.BeanCascadeInfo;
/*    */ import com.avaje.ebeaninternal.server.deploy.meta.DeployBeanDescriptor;
/*    */ import com.avaje.ebeaninternal.server.deploy.meta.DeployBeanProperty;
/*    */ import javax.persistence.CascadeType;
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
/*    */ public abstract class AnnotationParser
/*    */   extends AnnotationBase
/*    */ {
/*    */   protected final DeployBeanInfo<?> info;
/*    */   protected final DeployBeanDescriptor<?> descriptor;
/*    */   protected final Class<?> beanType;
/*    */   
/*    */   public AnnotationParser(DeployBeanInfo<?> info) {
/* 43 */     super(info.getUtil());
/* 44 */     this.info = info;
/* 45 */     this.beanType = info.getDescriptor().getBeanType();
/* 46 */     this.descriptor = info.getDescriptor();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public abstract void parse();
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void setCascadeTypes(CascadeType[] cascadeTypes, BeanCascadeInfo cascadeInfo) {
/* 58 */     if (cascadeTypes != null && cascadeTypes.length > 0) {
/* 59 */       cascadeInfo.setTypes(cascadeTypes);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   protected void readLdapAttribute(LdapAttribute ldapAttribute, DeployBeanProperty prop) {
/* 65 */     if (!isEmpty(ldapAttribute.name())) {
/* 66 */       prop.setDbColumn(ldapAttribute.name());
/*    */     }
/* 68 */     prop.setDbInsertable(ldapAttribute.insertable());
/* 69 */     prop.setDbUpdateable(ldapAttribute.updatable());
/*    */     
/* 71 */     Class<?> adapterCls = ldapAttribute.adapter();
/*    */     
/* 73 */     if (adapterCls != null && !void.class.equals(adapterCls))
/*    */       try {
/* 75 */         LdapAttributeAdapter adapter = (LdapAttributeAdapter)adapterCls.newInstance();
/* 76 */         prop.setLdapAttributeAdapter(adapter);
/* 77 */       } catch (Exception e) {
/* 78 */         String msg = "Error creating LdapAttributeAdapter for [" + prop.getFullBeanName() + "] " + "with class [" + adapterCls + "] using the default constructor.";
/*    */         
/* 80 */         throw new PersistenceException(msg, e);
/*    */       }  
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\deploy\parse\AnnotationParser.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */