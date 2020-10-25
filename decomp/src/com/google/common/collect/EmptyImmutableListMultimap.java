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
/*    */ class EmptyImmutableListMultimap
/*    */   extends ImmutableListMultimap<Object, Object>
/*    */ {
/* 28 */   static final EmptyImmutableListMultimap INSTANCE = new EmptyImmutableListMultimap();
/*    */   
/*    */   private static final long serialVersionUID = 0L;
/*    */   
/* 32 */   private EmptyImmutableListMultimap() { super(ImmutableMap.of(), 0); }
/*    */ 
/*    */ 
/*    */   
/* 36 */   private Object readResolve() { return INSTANCE; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\google\common\collect\EmptyImmutableListMultimap.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */