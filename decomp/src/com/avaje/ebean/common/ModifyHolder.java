/*    */ package com.avaje.ebean.common;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ import java.util.Collection;
/*    */ import java.util.LinkedHashSet;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class ModifyHolder<E>
/*    */   extends Object
/*    */   implements Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 2572572897923801083L;
/* 43 */   private Set<E> modifyDeletions = new LinkedHashSet();
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 48 */   private Set<E> modifyAdditions = new LinkedHashSet();
/*    */   
/*    */   void reset() {
/* 51 */     this.modifyDeletions = new LinkedHashSet();
/* 52 */     this.modifyAdditions = new LinkedHashSet();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   void modifyAdditionAll(Collection<? extends E> c) {
/* 59 */     if (c != null) {
/* 60 */       this.modifyAdditions.addAll(c);
/*    */     }
/*    */   }
/*    */   
/*    */   void modifyAddition(E bean) {
/* 65 */     if (bean != null) {
/* 66 */       this.modifyAdditions.add(bean);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   void modifyRemoval(Object bean) {
/* 72 */     if (bean != null) {
/* 73 */       this.modifyDeletions.add(bean);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/* 78 */   Set<E> getModifyAdditions() { return this.modifyAdditions; }
/*    */ 
/*    */ 
/*    */   
/* 82 */   Set<E> getModifyRemovals() { return this.modifyDeletions; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebean\common\ModifyHolder.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */