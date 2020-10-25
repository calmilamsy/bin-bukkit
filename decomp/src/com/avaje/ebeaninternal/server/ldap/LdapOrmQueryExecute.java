/*     */ package com.avaje.ebeaninternal.server.ldap;
/*     */ 
/*     */ import com.avaje.ebeaninternal.api.SpiQuery;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.naming.NamingEnumeration;
/*     */ import javax.naming.NamingException;
/*     */ import javax.naming.directory.Attributes;
/*     */ import javax.naming.directory.DirContext;
/*     */ import javax.naming.directory.SearchControls;
/*     */ import javax.naming.directory.SearchResult;
/*     */ import javax.naming.ldap.LdapName;
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
/*     */ public class LdapOrmQueryExecute<T>
/*     */   extends Object
/*     */ {
/*  41 */   private static final Logger logger = Logger.getLogger(LdapOrmQueryExecute.class.getName());
/*     */   
/*     */   private final SpiQuery<?> query;
/*     */   
/*     */   private final BeanDescriptor<T> beanDescriptor;
/*     */   
/*     */   private final DirContext dc;
/*     */   
/*     */   private final LdapBeanBuilder<T> beanBuilder;
/*     */   
/*     */   private final String filterExpr;
/*     */   
/*     */   private final Object[] filterValues;
/*     */   
/*     */   private final String[] selectProps;
/*     */ 
/*     */   
/*     */   public LdapOrmQueryExecute(LdapOrmQueryRequest<T> request, boolean defaultVanillaMode, DirContext dc) {
/*  59 */     this.query = request.getQuery();
/*  60 */     this.beanDescriptor = request.getBeanDescriptor();
/*  61 */     this.dc = dc;
/*     */     
/*  63 */     boolean vanillaMode = this.query.isVanillaMode(defaultVanillaMode);
/*  64 */     this.beanBuilder = new LdapBeanBuilder(this.beanDescriptor, vanillaMode);
/*     */     
/*  66 */     LdapQueryDeployHelper deployHelper = new LdapQueryDeployHelper(request);
/*  67 */     this.selectProps = deployHelper.getSelectedProperties();
/*  68 */     this.filterExpr = deployHelper.getFilterExpr();
/*  69 */     this.filterValues = deployHelper.getFilterValues();
/*     */   }
/*     */ 
/*     */   
/*     */   public T findId() {
/*  74 */     Object id = this.query.getId();
/*     */     
/*     */     try {
/*  77 */       LdapName dn = this.beanDescriptor.createLdapNameById(id);
/*     */       
/*  79 */       String[] findAttrs = this.selectProps;
/*  80 */       if (findAttrs == null) {
/*  81 */         findAttrs = this.beanDescriptor.getDefaultSelectDbArray();
/*     */       }
/*     */ 
/*     */       
/*  85 */       String debugQuery = "Name:" + dn + " attrs:" + Arrays.toString(findAttrs);
/*     */       
/*  87 */       Attributes attrs = this.dc.getAttributes(dn, findAttrs);
/*     */       
/*  89 */       T bean = (T)this.beanBuilder.readAttributes(attrs);
/*     */       
/*  91 */       this.query.setGeneratedSql(debugQuery);
/*  92 */       return bean;
/*     */     }
/*  94 */     catch (NamingException e) {
/*  95 */       throw new LdapPersistenceException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public List<T> findList() {
/* 101 */     SearchControls sc = new SearchControls();
/* 102 */     sc.setSearchScope(1);
/*     */     
/* 104 */     List<T> list = new ArrayList<T>();
/*     */     try {
/*     */       NamingEnumeration<SearchResult> result;
/* 107 */       LdapName dn = this.beanDescriptor.createLdapName(null);
/*     */ 
/*     */       
/* 110 */       String debugQuery = "Name:" + dn;
/*     */       
/* 112 */       if (this.selectProps != null) {
/* 113 */         sc.setReturningAttributes(this.selectProps);
/* 114 */         debugQuery = debugQuery + " select:" + Arrays.toString(this.selectProps);
/*     */       } 
/*     */       
/* 117 */       if (logger.isLoggable(Level.INFO)) {
/* 118 */         logger.info("Ldap Query  Name:" + dn + " filterExpr:" + this.filterExpr);
/*     */       }
/*     */       
/* 121 */       debugQuery = debugQuery + " filterExpr:" + this.filterExpr;
/*     */ 
/*     */       
/* 124 */       if (this.filterValues == null || this.filterValues.length == 0) {
/* 125 */         result = this.dc.search(dn, this.filterExpr, sc);
/*     */       } else {
/* 127 */         debugQuery = debugQuery + " filterValues:" + Arrays.toString(this.filterValues);
/* 128 */         result = this.dc.search(dn, this.filterExpr, this.filterValues, sc);
/*     */       } 
/*     */       
/* 131 */       this.query.setGeneratedSql(debugQuery);
/*     */       
/* 133 */       if (result != null) {
/* 134 */         while (result.hasMoreElements()) {
/* 135 */           SearchResult row = (SearchResult)result.nextElement();
/* 136 */           T bean = (T)this.beanBuilder.readAttributes(row.getAttributes());
/* 137 */           list.add(bean);
/*     */         } 
/*     */       }
/*     */       
/* 141 */       return list;
/*     */     }
/* 143 */     catch (NamingException e) {
/* 144 */       throw new LdapPersistenceException(e);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\ldap\LdapOrmQueryExecute.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */