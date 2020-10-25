/*    */ package com.google.common.collect;
/*    */ 
/*    */ import com.google.common.primitives.Primitives;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
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
/*    */ public final class MutableClassToInstanceMap<B>
/*    */   extends MapConstraints.ConstrainedMap<Class<? extends B>, B>
/*    */   implements ClassToInstanceMap<B>
/*    */ {
/* 41 */   public static <B> MutableClassToInstanceMap<B> create() { return new MutableClassToInstanceMap(new HashMap()); }
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
/* 52 */   public static <B> MutableClassToInstanceMap<B> create(Map<Class<? extends B>, B> backingMap) { return new MutableClassToInstanceMap(backingMap); }
/*    */ 
/*    */ 
/*    */   
/* 56 */   private MutableClassToInstanceMap(Map<Class<? extends B>, B> delegate) { super(delegate, VALUE_CAN_BE_CAST_TO_KEY); }
/*    */ 
/*    */   
/* 59 */   private static final MapConstraint<Class<?>, Object> VALUE_CAN_BE_CAST_TO_KEY = new MapConstraint<Class<?>, Object>()
/*    */     {
/*    */       public void checkKeyValue(Class<?> key, Object value) {
/* 62 */         MutableClassToInstanceMap.cast(key, value); }
/*    */     };
/*    */   
/*    */   private static final long serialVersionUID = 0L;
/*    */   
/* 67 */   public <T extends B> T putInstance(Class<T> type, T value) { return (T)cast(type, put(type, value)); }
/*    */ 
/*    */ 
/*    */   
/* 71 */   public <T extends B> T getInstance(Class<T> type) { return (T)cast(type, get(type)); }
/*    */ 
/*    */ 
/*    */   
/* 75 */   private static <B, T extends B> T cast(Class<T> type, B value) { return (T)Primitives.wrap(type).cast(value); }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\google\common\collect\MutableClassToInstanceMap.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */