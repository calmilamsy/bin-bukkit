/*    */ package com.avaje.ebeaninternal.server.type.reflect;
/*    */ 
/*    */ import java.lang.reflect.Constructor;
/*    */ import java.lang.reflect.Method;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ImmutableMeta
/*    */ {
/*    */   private final Constructor<?> constructor;
/*    */   private final Method[] readers;
/*    */   
/*    */   public ImmutableMeta(Constructor<?> constructor, Method[] readers) {
/* 14 */     this.constructor = constructor;
/* 15 */     this.readers = readers;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/* 20 */   public Constructor<?> getConstructor() { return this.constructor; }
/*    */ 
/*    */ 
/*    */   
/* 24 */   public Method[] getReaders() { return this.readers; }
/*    */ 
/*    */ 
/*    */   
/* 28 */   public boolean isCompoundType() { return (this.readers.length > 1); }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\type\reflect\ImmutableMeta.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */