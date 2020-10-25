/*    */ package com.google.common.collect;
/*    */ 
/*    */ import com.google.common.annotations.GwtCompatible;
/*    */ import java.io.Serializable;
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
/*    */ @GwtCompatible(serializable = true)
/*    */ final class UsingToStringOrdering
/*    */   extends Ordering<Object>
/*    */   implements Serializable
/*    */ {
/* 27 */   static final UsingToStringOrdering INSTANCE = new UsingToStringOrdering();
/*    */   private static final long serialVersionUID = 0L;
/*    */   
/* 30 */   public int compare(Object left, Object right) { return left.toString().compareTo(right.toString()); }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 35 */   private Object readResolve() { return INSTANCE; }
/*    */ 
/*    */ 
/*    */   
/* 39 */   public String toString() { return "Ordering.usingToString()"; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\google\common\collect\UsingToStringOrdering.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */