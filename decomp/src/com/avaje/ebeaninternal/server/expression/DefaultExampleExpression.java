/*     */ package com.avaje.ebeaninternal.server.expression;
/*     */ 
/*     */ import com.avaje.ebean.ExampleExpression;
/*     */ import com.avaje.ebean.LikeType;
/*     */ import com.avaje.ebean.event.BeanQueryRequest;
/*     */ import com.avaje.ebeaninternal.api.ManyWhereJoins;
/*     */ import com.avaje.ebeaninternal.api.SpiExpression;
/*     */ import com.avaje.ebeaninternal.api.SpiExpressionRequest;
/*     */ import com.avaje.ebeaninternal.api.SpiLuceneExpr;
/*     */ import com.avaje.ebeaninternal.server.core.OrmQueryRequest;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanProperty;
/*     */ import com.avaje.ebeaninternal.server.query.LuceneResolvableRequest;
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
/*     */ public class DefaultExampleExpression
/*     */   implements SpiExpression, ExampleExpression
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   private final Object entity;
/*     */   private boolean caseInsensitive;
/*     */   private LikeType likeType;
/*     */   private boolean includeZeros;
/*     */   private ArrayList<SpiExpression> list;
/*     */   private final FilterExprPath pathPrefix;
/*     */   
/*     */   public DefaultExampleExpression(FilterExprPath pathPrefix, Object entity, boolean caseInsensitive, LikeType likeType) {
/*  85 */     this.pathPrefix = pathPrefix;
/*  86 */     this.entity = entity;
/*  87 */     this.caseInsensitive = caseInsensitive;
/*  88 */     this.likeType = likeType;
/*     */   }
/*     */ 
/*     */   
/*  92 */   public boolean isLuceneResolvable(LuceneResolvableRequest req) { return false; }
/*     */ 
/*     */ 
/*     */   
/*  96 */   public SpiLuceneExpr createLuceneExpr(SpiExpressionRequest request) { return null; }
/*     */ 
/*     */   
/*     */   public void containsMany(BeanDescriptor<?> desc, ManyWhereJoins whereManyJoins) {
/* 100 */     if (this.list != null) {
/* 101 */       for (int i = 0; i < this.list.size(); i++) {
/* 102 */         ((SpiExpression)this.list.get(i)).containsMany(desc, whereManyJoins);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public ExampleExpression includeZeros() {
/* 108 */     this.includeZeros = true;
/* 109 */     return this;
/*     */   }
/*     */   
/*     */   public ExampleExpression caseInsensitive() {
/* 113 */     this.caseInsensitive = true;
/* 114 */     return this;
/*     */   }
/*     */   
/*     */   public ExampleExpression useStartsWith() {
/* 118 */     this.likeType = LikeType.STARTS_WITH;
/* 119 */     return this;
/*     */   }
/*     */   
/*     */   public ExampleExpression useContains() {
/* 123 */     this.likeType = LikeType.CONTAINS;
/* 124 */     return this;
/*     */   }
/*     */   
/*     */   public ExampleExpression useEndsWith() {
/* 128 */     this.likeType = LikeType.ENDS_WITH;
/* 129 */     return this;
/*     */   }
/*     */   
/*     */   public ExampleExpression useEqualTo() {
/* 133 */     this.likeType = LikeType.EQUAL_TO;
/* 134 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 141 */   public String getPropertyName() { return null; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addBindValues(SpiExpressionRequest request) {
/* 149 */     for (int i = 0; i < this.list.size(); i++) {
/* 150 */       SpiExpression item = (SpiExpression)this.list.get(i);
/* 151 */       item.addBindValues(request);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addSql(SpiExpressionRequest request) {
/* 160 */     if (!this.list.isEmpty()) {
/* 161 */       request.append("(");
/*     */       
/* 163 */       for (int i = 0; i < this.list.size(); i++) {
/* 164 */         SpiExpression item = (SpiExpression)this.list.get(i);
/* 165 */         if (i > 0) {
/* 166 */           request.append(" and ");
/*     */         }
/* 168 */         item.addSql(request);
/*     */       } 
/*     */       
/* 171 */       request.append(") ");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 181 */   public int queryAutoFetchHash() { return DefaultExampleExpression.class.getName().hashCode(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int queryPlanHash(BeanQueryRequest<?> request) {
/* 191 */     this.list = buildExpressions(request);
/*     */     
/* 193 */     int hc = DefaultExampleExpression.class.getName().hashCode();
/*     */     
/* 195 */     for (int i = 0; i < this.list.size(); i++) {
/* 196 */       hc = hc * 31 + ((SpiExpression)this.list.get(i)).queryPlanHash(request);
/*     */     }
/*     */     
/* 199 */     return hc;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int queryBindHash() {
/* 206 */     int hc = DefaultExampleExpression.class.getName().hashCode();
/* 207 */     for (int i = 0; i < this.list.size(); i++) {
/* 208 */       hc = hc * 31 + ((SpiExpression)this.list.get(i)).queryBindHash();
/*     */     }
/*     */     
/* 211 */     return hc;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private ArrayList<SpiExpression> buildExpressions(BeanQueryRequest<?> request) {
/* 219 */     ArrayList<SpiExpression> list = new ArrayList<SpiExpression>();
/*     */     
/* 221 */     OrmQueryRequest<?> r = (OrmQueryRequest)request;
/* 222 */     BeanDescriptor<?> beanDescriptor = r.getBeanDescriptor();
/*     */     
/* 224 */     Iterator<BeanProperty> propIter = beanDescriptor.propertiesAll();
/*     */     
/* 226 */     while (propIter.hasNext()) {
/* 227 */       BeanProperty beanProperty = (BeanProperty)propIter.next();
/* 228 */       String propName = beanProperty.getName();
/* 229 */       Object value = beanProperty.getValue(this.entity);
/*     */       
/* 231 */       if (beanProperty.isScalar() && value != null) {
/* 232 */         if (value instanceof String) {
/* 233 */           list.add(new LikeExpression(this.pathPrefix, propName, (String)value, this.caseInsensitive, this.likeType)); continue;
/*     */         } 
/* 235 */         if (!this.includeZeros && isZero(value)) {
/*     */           continue;
/*     */         }
/*     */         
/* 239 */         list.add(new SimpleExpression(this.pathPrefix, propName, SimpleExpression.Op.EQ, value));
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 245 */     return list;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isZero(Object value) {
/* 252 */     if (value instanceof Number) {
/* 253 */       Number num = (Number)value;
/* 254 */       double doubleValue = num.doubleValue();
/* 255 */       if (doubleValue == 0.0D) {
/* 256 */         return true;
/*     */       }
/*     */     } 
/* 259 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\expression\DefaultExampleExpression.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */