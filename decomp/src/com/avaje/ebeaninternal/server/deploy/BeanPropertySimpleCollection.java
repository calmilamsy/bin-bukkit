/*     */ package com.avaje.ebeaninternal.server.deploy;
/*     */ 
/*     */ import com.avaje.ebean.bean.BeanCollectionAdd;
/*     */ import com.avaje.ebeaninternal.server.deploy.meta.DeployBeanPropertySimpleCollection;
/*     */ import com.avaje.ebeaninternal.server.ldap.LdapPersistenceException;
/*     */ import com.avaje.ebeaninternal.server.type.ScalarType;
/*     */ import java.util.Iterator;
/*     */ import javax.naming.NamingEnumeration;
/*     */ import javax.naming.NamingException;
/*     */ import javax.naming.directory.Attribute;
/*     */ import javax.naming.directory.BasicAttribute;
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
/*     */ public class BeanPropertySimpleCollection<T>
/*     */   extends BeanPropertyAssocMany<T>
/*     */ {
/*     */   private final ScalarType<T> collectionScalarType;
/*     */   
/*     */   public BeanPropertySimpleCollection(BeanDescriptorMap owner, BeanDescriptor<?> descriptor, DeployBeanPropertySimpleCollection<T> deploy) {
/*  39 */     super(owner, descriptor, deploy);
/*  40 */     this.collectionScalarType = deploy.getCollectionScalarType();
/*     */   }
/*     */ 
/*     */   
/*  44 */   public void initialise() { super.initialise(); }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void copyProperty(Object sourceBean, Object destBean, CopyContext ctx, int maxDepth) {
/*  50 */     Object srcValue = getValueUnderlying(sourceBean);
/*  51 */     Object dstValue = this.help.copyCollection(srcValue, ctx, maxDepth, destBean);
/*  52 */     setValue(destBean, dstValue);
/*     */   }
/*     */ 
/*     */   
/*     */   public Attribute createAttribute(Object bean) {
/*  57 */     Object v = getValue(bean);
/*  58 */     if (v == null) {
/*  59 */       return null;
/*     */     }
/*  61 */     if (this.ldapAttributeAdapter != null) {
/*  62 */       return this.ldapAttributeAdapter.createAttribute(v);
/*     */     }
/*     */     
/*  65 */     BasicAttribute attrs = new BasicAttribute(getDbColumn());
/*     */     
/*  67 */     Iterator<?> it = this.help.getIterator(v);
/*  68 */     if (it != null) {
/*  69 */       while (it.hasNext()) {
/*  70 */         Object beanValue = it.next();
/*  71 */         Object attrValue = this.collectionScalarType.toJdbcType(beanValue);
/*  72 */         attrs.add(attrValue);
/*     */       } 
/*     */     }
/*  75 */     return attrs;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setAttributeValue(Object bean, Attribute attr) {
/*     */     try {
/*  81 */       if (attr != null) {
/*     */         Object beanValue;
/*  83 */         if (this.ldapAttributeAdapter != null) {
/*  84 */           beanValue = this.ldapAttributeAdapter.readAttribute(attr);
/*     */         } else {
/*     */           
/*  87 */           boolean vanilla = true;
/*  88 */           beanValue = this.help.createEmpty(vanilla);
/*  89 */           BeanCollectionAdd collAdd = this.help.getBeanCollectionAdd(beanValue, this.mapKey);
/*     */           
/*  91 */           NamingEnumeration<?> en = attr.getAll();
/*  92 */           while (en.hasMoreElements()) {
/*  93 */             Object attrValue = en.nextElement();
/*  94 */             Object collValue = this.collectionScalarType.toBeanType(attrValue);
/*  95 */             collAdd.addBean(collValue);
/*     */           } 
/*     */         } 
/*     */         
/*  99 */         setValue(bean, beanValue);
/*     */       } 
/* 101 */     } catch (NamingException e) {
/* 102 */       throw new LdapPersistenceException(e);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\deploy\BeanPropertySimpleCollection.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */