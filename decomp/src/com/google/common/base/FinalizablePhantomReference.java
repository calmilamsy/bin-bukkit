/*    */ package com.google.common.base;
/*    */ 
/*    */ import java.lang.ref.PhantomReference;
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
/*    */ public abstract class FinalizablePhantomReference<T>
/*    */   extends PhantomReference<T>
/*    */   implements FinalizableReference
/*    */ {
/*    */   protected FinalizablePhantomReference(T referent, FinalizableReferenceQueue queue) {
/* 44 */     super(referent, queue.queue);
/* 45 */     queue.cleanUp();
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\google\common\base\FinalizablePhantomReference.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */