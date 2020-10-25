/*    */ package com.avaje.ebeaninternal.util;
/*    */ 
/*    */ import com.avaje.ebeaninternal.api.SpiExpressionRequest;
/*    */ import com.avaje.ebeaninternal.server.core.SpiOrmQueryRequest;
/*    */ import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
/*    */ import com.avaje.ebeaninternal.server.deploy.DeployParser;
/*    */ import com.avaje.ebeaninternal.server.lucene.LIndex;
/*    */ import java.util.ArrayList;
/*    */ 
/*    */ public class DefaultExpressionRequest
/*    */   implements SpiExpressionRequest {
/*    */   private final SpiOrmQueryRequest<?> queryRequest;
/*    */   private final BeanDescriptor<?> beanDescriptor;
/*    */   private final StringBuilder sb;
/*    */   
/*    */   public DefaultExpressionRequest(SpiOrmQueryRequest<?> queryRequest, DeployParser deployParser) {
/* 17 */     this.sb = new StringBuilder();
/*    */     
/* 19 */     this.bindValues = new ArrayList();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 28 */     this.queryRequest = queryRequest;
/* 29 */     this.beanDescriptor = queryRequest.getBeanDescriptor();
/* 30 */     this.deployParser = deployParser;
/*    */   } private final ArrayList<Object> bindValues; private final DeployParser deployParser; private int paramIndex; private LIndex luceneIndex; public DefaultExpressionRequest(SpiOrmQueryRequest<?> queryRequest, LIndex index) {
/*    */     this.sb = new StringBuilder();
/*    */     this.bindValues = new ArrayList();
/* 34 */     this.queryRequest = queryRequest;
/* 35 */     this.beanDescriptor = queryRequest.getBeanDescriptor();
/* 36 */     this.deployParser = null;
/* 37 */     this.luceneIndex = index;
/*    */   } public DefaultExpressionRequest(BeanDescriptor<?> beanDescriptor) {
/*    */     this.sb = new StringBuilder();
/*    */     this.bindValues = new ArrayList();
/* 41 */     this.beanDescriptor = beanDescriptor;
/* 42 */     this.queryRequest = null;
/* 43 */     this.deployParser = null;
/*    */   }
/*    */ 
/*    */   
/* 47 */   public LIndex getLuceneIndex() { return this.luceneIndex; }
/*    */ 
/*    */ 
/*    */   
/*    */   public String parseDeploy(String logicalProp) {
/* 52 */     String s = this.deployParser.getDeployWord(logicalProp);
/* 53 */     return (s == null) ? logicalProp : s;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 60 */   public int nextParameter() { return ++this.paramIndex; }
/*    */ 
/*    */ 
/*    */   
/* 64 */   public BeanDescriptor<?> getBeanDescriptor() { return this.beanDescriptor; }
/*    */ 
/*    */ 
/*    */   
/* 68 */   public SpiOrmQueryRequest<?> getQueryRequest() { return this.queryRequest; }
/*    */ 
/*    */   
/*    */   public SpiExpressionRequest append(String sql) {
/* 72 */     this.sb.append(sql);
/* 73 */     return this;
/*    */   }
/*    */ 
/*    */   
/* 77 */   public void addBindValue(Object bindValue) { this.bindValues.add(bindValue); }
/*    */ 
/*    */ 
/*    */   
/* 81 */   public boolean includeProperty(String propertyName) { return true; }
/*    */ 
/*    */ 
/*    */   
/* 85 */   public String getSql() { return this.sb.toString(); }
/*    */ 
/*    */ 
/*    */   
/* 89 */   public ArrayList<Object> getBindValues() { return this.bindValues; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninterna\\util\DefaultExpressionRequest.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */