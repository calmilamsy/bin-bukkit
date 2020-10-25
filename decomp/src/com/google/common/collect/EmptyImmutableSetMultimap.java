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
/*    */ class EmptyImmutableSetMultimap
/*    */   extends ImmutableSetMultimap<Object, Object>
/*    */ {
/* 28 */   static final EmptyImmutableSetMultimap INSTANCE = new EmptyImmutableSetMultimap();
/*    */   
/*    */   private static final long serialVersionUID = 0L;
/*    */   
/* 32 */   private EmptyImmutableSetMultimap() { super(ImmutableMap.of(), 0); }
/*    */ 
/*    */ 
/*    */   
/* 36 */   private Object readResolve() { return INSTANCE; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\google\common\collect\EmptyImmutableSetMultimap.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */