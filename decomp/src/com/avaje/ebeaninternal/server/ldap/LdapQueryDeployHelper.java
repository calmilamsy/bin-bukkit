/*     */ package com.avaje.ebeaninternal.server.ldap;
/*     */ 
/*     */ import com.avaje.ebeaninternal.api.SpiExpressionList;
/*     */ import com.avaje.ebeaninternal.api.SpiQuery;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanProperty;
/*     */ import com.avaje.ebeaninternal.server.deploy.DeployPropertyParser;
/*     */ import com.avaje.ebeaninternal.server.querydefn.OrmQueryProperties;
/*     */ import com.avaje.ebeaninternal.util.DefaultExpressionRequest;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
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
/*     */ 
/*     */ public class LdapQueryDeployHelper
/*     */ {
/*     */   private final LdapOrmQueryRequest<?> request;
/*     */   private final SpiQuery<?> query;
/*     */   private final BeanDescriptor<?> desc;
/*     */   private String filterExpr;
/*     */   private Object[] filterValues;
/*     */   
/*     */   public LdapQueryDeployHelper(LdapOrmQueryRequest<?> request) {
/*  43 */     this.request = request;
/*  44 */     this.query = request.getQuery();
/*  45 */     this.desc = request.getBeanDescriptor();
/*     */     
/*  47 */     parse();
/*     */   }
/*     */ 
/*     */   
/*     */   public String[] getSelectedProperties() {
/*  52 */     OrmQueryProperties chunk = this.query.getDetail().getChunk(null, false);
/*  53 */     if (chunk.allProperties()) {
/*  54 */       return null;
/*     */     }
/*     */ 
/*     */     
/*  58 */     ArrayList<String> ldapSelectProps = new ArrayList<String>();
/*     */     
/*  60 */     Iterator<String> selectProperties = chunk.getSelectProperties();
/*  61 */     while (selectProperties.hasNext()) {
/*  62 */       String propName = (String)selectProperties.next();
/*  63 */       BeanProperty p = this.desc.getBeanProperty(propName);
/*  64 */       if (p != null) {
/*  65 */         propName = p.getDbColumn();
/*     */       }
/*  67 */       ldapSelectProps.add(propName);
/*     */     } 
/*  69 */     return (String[])ldapSelectProps.toArray(new String[ldapSelectProps.size()]);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void parse() {
/*  75 */     DeployPropertyParser deployParser = this.desc.createDeployPropertyParser();
/*     */     
/*  77 */     String baseWhere = this.query.getAdditionalWhere();
/*  78 */     if (baseWhere != null) {
/*  79 */       baseWhere = deployParser.parse(baseWhere);
/*     */     }
/*     */ 
/*     */     
/*  83 */     SpiExpressionList<?> whereExp = this.query.getWhereExpressions();
/*  84 */     if (whereExp != null) {
/*     */       
/*  86 */       DefaultExpressionRequest expReq = new DefaultExpressionRequest(this.request, deployParser);
/*     */       
/*  88 */       ArrayList<?> bindValues = whereExp.buildBindValues(expReq);
/*  89 */       this.filterValues = bindValues.toArray(new Object[bindValues.size()]);
/*  90 */       String exprWhere = whereExp.buildSql(expReq);
/*     */       
/*  92 */       if (baseWhere != null) {
/*  93 */         this.filterExpr = "(&" + baseWhere + exprWhere + ")";
/*     */       } else {
/*  95 */         this.filterExpr = exprWhere;
/*     */       } 
/*     */     } else {
/*  98 */       this.filterExpr = baseWhere;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/* 103 */   public String getFilterExpr() { return this.filterExpr; }
/*     */ 
/*     */ 
/*     */   
/* 107 */   public Object[] getFilterValues() { return this.filterValues; }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\ldap\LdapQueryDeployHelper.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */