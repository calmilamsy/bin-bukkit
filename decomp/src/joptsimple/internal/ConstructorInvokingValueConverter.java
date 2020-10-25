/*    */ package joptsimple.internal;
/*    */ 
/*    */ import java.lang.reflect.Constructor;
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
/*    */ 
/*    */ class ConstructorInvokingValueConverter<V>
/*    */   extends Object
/*    */   implements ValueConverter<V>
/*    */ {
/*    */   private final Constructor<V> ctor;
/*    */   
/* 42 */   ConstructorInvokingValueConverter(Constructor<V> ctor) { this.ctor = ctor; }
/*    */ 
/*    */ 
/*    */   
/* 46 */   public V convert(String value) { return (V)Reflection.instantiate(this.ctor, new Object[] { value }); }
/*    */ 
/*    */ 
/*    */   
/* 50 */   public Class<V> valueType() { return this.ctor.getDeclaringClass(); }
/*    */ 
/*    */ 
/*    */   
/* 54 */   public String valuePattern() { return null; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\joptsimple\internal\ConstructorInvokingValueConverter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */