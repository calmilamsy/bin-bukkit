/*    */ package com.avaje.ebeaninternal.server.query;
/*    */ 
/*    */ import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
/*    */ import com.avaje.ebeaninternal.server.lucene.LIndex;
/*    */ import com.avaje.ebeaninternal.server.lucene.LIndexField;
/*    */ import java.util.Set;
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
/*    */ public class LuceneResolvableRequest
/*    */ {
/*    */   private final BeanDescriptor<?> beanDescriptor;
/*    */   private final LIndex luceneIndex;
/*    */   private final Set<String> resolvePropertyNames;
/*    */   
/*    */   public LuceneResolvableRequest(BeanDescriptor<?> beanDescriptor, LIndex luceneIndex) {
/* 37 */     this.beanDescriptor = beanDescriptor;
/* 38 */     this.luceneIndex = luceneIndex;
/* 39 */     this.resolvePropertyNames = luceneIndex.getResolvePropertyNames();
/*    */   }
/*    */ 
/*    */   
/* 43 */   public boolean indexContains(String propertyName) { return this.resolvePropertyNames.contains(propertyName); }
/*    */ 
/*    */ 
/*    */   
/* 47 */   public LIndexField getSortableProperty(String propertyName) { return this.luceneIndex.getIndexFieldDefn().getSortableField(propertyName); }
/*    */ 
/*    */ 
/*    */   
/* 51 */   public BeanDescriptor<?> getBeanDescriptor() { return this.beanDescriptor; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\query\LuceneResolvableRequest.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */