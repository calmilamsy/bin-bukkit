/*    */ package com.google.common.collect;
/*    */ 
/*    */ import com.google.common.annotations.GwtCompatible;
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
/*    */ @GwtCompatible(serializable = true)
/*    */ final class EmptyImmutableMultiset
/*    */   extends ImmutableMultiset<Object>
/*    */ {
/* 28 */   static final EmptyImmutableMultiset INSTANCE = new EmptyImmutableMultiset();
/*    */   private static final long serialVersionUID = 0L;
/*    */   
/* 31 */   private EmptyImmutableMultiset() { super(ImmutableMap.of(), 0); }
/*    */ 
/*    */ 
/*    */   
/* 35 */   Object readResolve() { return INSTANCE; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\google\common\collect\EmptyImmutableMultiset.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */