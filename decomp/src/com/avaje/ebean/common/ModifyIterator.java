/*    */ package com.avaje.ebean.common;
/*    */ 
/*    */ import com.avaje.ebean.bean.BeanCollection;
/*    */ import java.util.Iterator;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class ModifyIterator<E>
/*    */   extends Object
/*    */   implements Iterator<E>
/*    */ {
/*    */   private final BeanCollection<E> owner;
/*    */   private final Iterator<E> it;
/*    */   private E last;
/*    */   
/*    */   ModifyIterator(BeanCollection<E> owner, Iterator<E> it) {
/* 49 */     this.owner = owner;
/* 50 */     this.it = it;
/*    */   }
/*    */ 
/*    */   
/* 54 */   public boolean hasNext() { return this.it.hasNext(); }
/*    */ 
/*    */   
/*    */   public E next() {
/* 58 */     this.last = this.it.next();
/* 59 */     return (E)this.last;
/*    */   }
/*    */   
/*    */   public void remove() {
/* 63 */     this.owner.modifyRemoval(this.last);
/* 64 */     this.it.remove();
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebean\common\ModifyIterator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */