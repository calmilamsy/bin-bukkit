/*     */ package com.avaje.ebeaninternal.server.ldap;
/*     */ 
/*     */ import com.avaje.ebean.config.ldap.LdapContextFactory;
/*     */ import com.avaje.ebeaninternal.server.core.PersistRequest;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanProperty;
/*     */ import java.util.Iterator;
/*     */ import java.util.Set;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.naming.Name;
/*     */ import javax.naming.NamingException;
/*     */ import javax.naming.directory.Attribute;
/*     */ import javax.naming.directory.Attributes;
/*     */ import javax.naming.directory.BasicAttributes;
/*     */ import javax.naming.directory.DirContext;
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
/*     */ public class DefaultLdapPersister
/*     */ {
/*  40 */   private static final Logger logger = Logger.getLogger(DefaultLdapPersister.class.getName());
/*     */   
/*     */   private final LdapContextFactory contextFactory;
/*     */ 
/*     */   
/*  45 */   public DefaultLdapPersister(LdapContextFactory dirContextFactory) { this.contextFactory = dirContextFactory; }
/*     */ 
/*     */ 
/*     */   
/*     */   public int persist(LdapPersistBeanRequest<?> request) {
/*  50 */     switch (request.getType()) {
/*     */       case INSERT:
/*  52 */         return insert(request);
/*     */       case UPDATE:
/*  54 */         return update(request);
/*     */       case DELETE:
/*  56 */         return delete(request);
/*     */     } 
/*     */     
/*  59 */     throw new LdapPersistenceException("Invalid type " + request.getType());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private int insert(LdapPersistBeanRequest<?> request) {
/*  65 */     DirContext dc = this.contextFactory.createContext();
/*     */     
/*  67 */     Name name = request.createLdapName();
/*  68 */     Attributes attrs = createAttributes(request, false, request.getLoadedProperties());
/*     */     
/*  70 */     if (logger.isLoggable(Level.FINE)) {
/*  71 */       logger.fine("Ldap Insert Name:" + name + " Attrs:" + attrs);
/*     */     }
/*     */     try {
/*  74 */       dc.bind(name, null, attrs);
/*  75 */       return 1;
/*     */     }
/*  77 */     catch (NamingException e) {
/*  78 */       throw new LdapPersistenceException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private int delete(LdapPersistBeanRequest<?> request) {
/*  84 */     DirContext dc = this.contextFactory.createContext();
/*  85 */     Name name = request.createLdapName();
/*     */     
/*  87 */     if (logger.isLoggable(Level.FINE)) {
/*  88 */       logger.fine("Ldap Delete Name:" + name);
/*     */     }
/*     */     
/*     */     try {
/*  92 */       dc.unbind(name);
/*  93 */       return 1;
/*     */     }
/*  95 */     catch (NamingException e) {
/*  96 */       throw new LdapPersistenceException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private int update(LdapPersistBeanRequest<?> request) {
/* 102 */     Name name = request.createLdapName();
/*     */     
/* 104 */     Set<String> updatedProperties = request.getUpdatedProperties();
/* 105 */     if (updatedProperties == null || updatedProperties.isEmpty()) {
/* 106 */       logger.info("Ldap Update has no changed properties?  Name:" + name);
/* 107 */       return 0;
/*     */     } 
/*     */     
/* 110 */     DirContext dc = this.contextFactory.createContext();
/* 111 */     Attributes attrs = createAttributes(request, true, updatedProperties);
/*     */     
/* 113 */     if (logger.isLoggable(Level.FINE)) {
/* 114 */       logger.fine("Ldap Update Name:" + name + " Attrs:" + attrs);
/*     */     }
/*     */     
/*     */     try {
/* 118 */       dc.modifyAttributes(name, 2, attrs);
/* 119 */       return 1;
/*     */     }
/* 121 */     catch (NamingException e) {
/* 122 */       throw new LdapPersistenceException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private Attributes createAttributes(LdapPersistBeanRequest<?> request, boolean update, Set<String> props) {
/* 128 */     BeanDescriptor<?> desc = request.getBeanDescriptor();
/*     */     
/* 130 */     Attributes attrs = desc.createAttributes();
/* 131 */     if (update) {
/* 132 */       attrs = new BasicAttributes(true);
/*     */     } else {
/* 134 */       attrs = desc.createAttributes();
/*     */     } 
/*     */     
/* 137 */     Object bean = request.getBean();
/*     */     
/* 139 */     if (props != null) {
/* 140 */       for (String propName : props) {
/* 141 */         BeanProperty p = desc.getBeanPropertyFromPath(propName);
/* 142 */         Attribute attr = p.createAttribute(bean);
/* 143 */         if (attr != null) {
/* 144 */           attrs.put(attr);
/*     */         }
/*     */       } 
/*     */     } else {
/* 148 */       Iterator<BeanProperty> it = desc.propertiesAll();
/* 149 */       while (it.hasNext()) {
/* 150 */         BeanProperty p = (BeanProperty)it.next();
/* 151 */         Attribute attr = p.createAttribute(bean);
/* 152 */         if (attr != null) {
/* 153 */           attrs.put(attr);
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 158 */     return attrs;
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\ldap\DefaultLdapPersister.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */