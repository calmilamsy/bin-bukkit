/*     */ package com.avaje.ebeaninternal.server.lucene;
/*     */ 
/*     */ import com.avaje.ebean.OrderBy;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
/*     */ import com.avaje.ebeaninternal.server.query.LuceneResolvableRequest;
/*     */ import java.util.List;
/*     */ import org.apache.lucene.search.Sort;
/*     */ import org.apache.lucene.search.SortField;
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
/*     */ public class LLuceneSortResolve
/*     */ {
/*     */   private final LuceneResolvableRequest req;
/*     */   private final OrderBy<?> orderBy;
/*     */   private Sort sort;
/*     */   private boolean isResolved;
/*     */   private String unsortableField;
/*     */   
/*     */   public LLuceneSortResolve(LuceneResolvableRequest req, OrderBy<?> orderBy) {
/*  50 */     this.req = req;
/*  51 */     this.orderBy = orderBy;
/*  52 */     this.isResolved = resolve();
/*     */   }
/*     */ 
/*     */   
/*  56 */   public boolean isResolved() { return this.isResolved; }
/*     */ 
/*     */ 
/*     */   
/*  60 */   public Sort getSort() { return this.sort; }
/*     */ 
/*     */ 
/*     */   
/*  64 */   public String getUnsortableField() { return this.unsortableField; }
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean resolve() {
/*  69 */     BeanDescriptor<?> beanDescriptor = this.req.getBeanDescriptor();
/*     */     
/*  71 */     if (this.orderBy != null) {
/*     */       
/*  73 */       List<OrderBy.Property> properties = this.orderBy.getProperties();
/*     */       
/*  75 */       SortField[] sortFields = new SortField[properties.size()];
/*     */       
/*  77 */       for (int i = 0; i < properties.size(); i++) {
/*  78 */         OrderBy.Property property = (OrderBy.Property)properties.get(i);
/*  79 */         SortField sf = createSortField(property, beanDescriptor);
/*  80 */         if (sf == null) {
/*     */           
/*  82 */           this.unsortableField = property.getProperty();
/*  83 */           return false;
/*     */         } 
/*  85 */         sortFields[i] = sf;
/*     */       } 
/*     */       
/*  88 */       this.sort = new Sort(sortFields);
/*     */     } 
/*  90 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   private SortField createSortField(OrderBy.Property property, BeanDescriptor<?> beanDescriptor) {
/*  95 */     String propName = property.getProperty();
/*     */     
/*  97 */     LIndexField sortField = this.req.getSortableProperty(propName);
/*  98 */     if (sortField == null)
/*     */     {
/* 100 */       return null;
/*     */     }
/*     */     
/* 103 */     int sortType = sortField.getSortType();
/* 104 */     return (sortType == -1) ? null : new SortField(sortField.getName(), sortType, !property.isAscending() ? 1 : 0);
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\lucene\LLuceneSortResolve.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */