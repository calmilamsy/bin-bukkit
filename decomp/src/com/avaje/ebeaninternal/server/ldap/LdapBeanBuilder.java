/*    */ package com.avaje.ebeaninternal.server.ldap;
/*    */ 
/*    */ import com.avaje.ebean.bean.EntityBean;
/*    */ import com.avaje.ebean.bean.EntityBeanIntercept;
/*    */ import com.avaje.ebean.event.BeanPersistController;
/*    */ import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
/*    */ import com.avaje.ebeaninternal.server.deploy.BeanProperty;
/*    */ import java.util.LinkedHashSet;
/*    */ import java.util.Set;
/*    */ import java.util.logging.Logger;
/*    */ import javax.naming.NamingEnumeration;
/*    */ import javax.naming.NamingException;
/*    */ import javax.naming.directory.Attribute;
/*    */ import javax.naming.directory.Attributes;
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
/*    */ public class LdapBeanBuilder<T>
/*    */   extends Object
/*    */ {
/* 39 */   private static final Logger logger = Logger.getLogger(LdapBeanBuilder.class.getName());
/*    */   
/*    */   private final BeanDescriptor<T> beanDescriptor;
/*    */   
/*    */   private final boolean vanillaMode;
/*    */   
/*    */   private Set<String> loadedProps;
/*    */   
/*    */   public LdapBeanBuilder(BeanDescriptor<T> beanDescriptor, boolean vanillaMode) {
/* 48 */     this.beanDescriptor = beanDescriptor;
/* 49 */     this.vanillaMode = vanillaMode;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public T readAttributes(Attributes attributes) throws NamingException {
/* 55 */     Object bean = this.beanDescriptor.createBean(this.vanillaMode);
/*    */     
/* 57 */     NamingEnumeration<? extends Attribute> all = attributes.getAll();
/*    */     
/* 59 */     boolean setLoadedProps = false;
/* 60 */     if (this.loadedProps == null) {
/* 61 */       setLoadedProps = true;
/* 62 */       this.loadedProps = new LinkedHashSet();
/*    */     } 
/*    */     
/* 65 */     while (all.hasMoreElements()) {
/* 66 */       Attribute attr = (Attribute)all.nextElement();
/* 67 */       String attrName = attr.getID();
/*    */       
/* 69 */       BeanProperty prop = this.beanDescriptor.getBeanPropertyFromDbColumn(attrName);
/* 70 */       if (prop == null) {
/* 71 */         if ("objectclass".equalsIgnoreCase(attrName)) {
/*    */           continue;
/*    */         }
/* 74 */         logger.info("... hmm, no property to map to attribute[" + attrName + "] value[" + attr.get() + "]");
/*    */         
/*    */         continue;
/*    */       } 
/* 78 */       prop.setAttributeValue(bean, attr);
/* 79 */       if (setLoadedProps) {
/* 80 */         this.loadedProps.add(prop.getName());
/*    */       }
/*    */     } 
/*    */ 
/*    */     
/* 85 */     if (bean instanceof EntityBean) {
/* 86 */       EntityBeanIntercept ebi = ((EntityBean)bean)._ebean_getIntercept();
/* 87 */       ebi.setLoadedProps(this.loadedProps);
/* 88 */       ebi.setLoaded();
/*    */     } 
/*    */     
/* 91 */     BeanPersistController persistController = this.beanDescriptor.getPersistController();
/* 92 */     if (persistController != null) {
/* 93 */       persistController.postLoad(bean, this.loadedProps);
/*    */     }
/*    */     
/* 96 */     return (T)bean;
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\ldap\LdapBeanBuilder.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */