/*    */ package com.avaje.ebeaninternal.server.ldap;
/*    */ 
/*    */ import com.avaje.ebeaninternal.api.SpiEbeanServer;
/*    */ import com.avaje.ebeaninternal.server.core.ConcurrencyMode;
/*    */ import com.avaje.ebeaninternal.server.core.PersistRequestBean;
/*    */ import com.avaje.ebeaninternal.server.deploy.BeanManager;
/*    */ import java.util.Set;
/*    */ import javax.naming.ldap.LdapName;
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
/*    */ public class LdapPersistBeanRequest<T>
/*    */   extends PersistRequestBean<T>
/*    */ {
/*    */   private final DefaultLdapPersister persister;
/*    */   
/*    */   public LdapPersistBeanRequest(SpiEbeanServer server, T bean, Object parentBean, BeanManager<T> mgr, DefaultLdapPersister persister) {
/* 37 */     super(server, bean, parentBean, mgr, null, null);
/* 38 */     this.persister = persister;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public LdapPersistBeanRequest(SpiEbeanServer server, T bean, Object parentBean, BeanManager<T> mgr, DefaultLdapPersister persister, Set<String> updateProps, ConcurrencyMode concurrencyMode) {
/* 44 */     super(server, bean, parentBean, mgr, null, null, updateProps, concurrencyMode);
/* 45 */     this.persister = persister;
/*    */   }
/*    */ 
/*    */   
/* 49 */   public LdapName createLdapName() { return this.beanDescriptor.createLdapName(this.bean); }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 55 */   public int executeNow() { return this.persister.persist(this); }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 60 */   public int executeOrQueue() { return executeNow(); }
/*    */   
/*    */   public void initTransIfRequired() {}
/*    */   
/*    */   public void commitTransIfRequired() {}
/*    */   
/*    */   public void rollbackTransIfRequired() {}
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\ldap\LdapPersistBeanRequest.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */