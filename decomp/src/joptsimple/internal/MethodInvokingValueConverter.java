/*    */ package joptsimple.internal;
/*    */ 
/*    */ import java.lang.reflect.Method;
/*    */ import joptsimple.ValueConverter;
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
/*    */ class MethodInvokingValueConverter<V>
/*    */   extends Object
/*    */   implements ValueConverter<V>
/*    */ {
/*    */   private final Method method;
/*    */   private final Class<V> clazz;
/*    */   
/*    */   MethodInvokingValueConverter(Method method, Class<V> clazz) {
/* 43 */     this.method = method;
/* 44 */     this.clazz = clazz;
/*    */   }
/*    */ 
/*    */   
/* 48 */   public V convert(String value) { return (V)this.clazz.cast(Reflection.invoke(this.method, new Object[] { value })); }
/*    */ 
/*    */ 
/*    */   
/* 52 */   public Class<V> valueType() { return this.clazz; }
/*    */ 
/*    */ 
/*    */   
/* 56 */   public String valuePattern() { return null; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\joptsimple\internal\MethodInvokingValueConverter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */