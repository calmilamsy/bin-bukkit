/*     */ package joptsimple.internal;
/*     */ 
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.lang.reflect.Method;
/*     */ import java.lang.reflect.Modifier;
/*     */ import joptsimple.ValueConverter;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class Reflection
/*     */ {
/*     */   static  {
/*  43 */     new Reflection();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <V> ValueConverter<V> findConverter(Class<V> clazz) {
/*  58 */     ValueConverter<V> valueOf = valueOfConverter(clazz);
/*  59 */     if (valueOf != null) {
/*  60 */       return valueOf;
/*     */     }
/*  62 */     ValueConverter<V> constructor = constructorConverter(clazz);
/*  63 */     if (constructor != null) {
/*  64 */       return constructor;
/*     */     }
/*  66 */     throw new IllegalArgumentException(clazz + " is not a value type");
/*     */   }
/*     */   
/*     */   private static <V> ValueConverter<V> valueOfConverter(Class<V> clazz) {
/*     */     try {
/*  71 */       Method valueOf = clazz.getDeclaredMethod("valueOf", new Class[] { String.class });
/*  72 */       if (!meetsConverterRequirements(valueOf, clazz)) {
/*  73 */         return null;
/*     */       }
/*  75 */       return new MethodInvokingValueConverter(valueOf, clazz);
/*     */     }
/*  77 */     catch (NoSuchMethodException ignored) {
/*  78 */       return null;
/*     */     } 
/*     */   }
/*     */   
/*     */   private static <V> ValueConverter<V> constructorConverter(Class<V> clazz) {
/*     */     try {
/*  84 */       return new ConstructorInvokingValueConverter(clazz.getConstructor(new Class[] { String.class }));
/*     */     
/*     */     }
/*  87 */     catch (NoSuchMethodException ignored) {
/*  88 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <T> T instantiate(Constructor<T> constructor, Object... args) {
/*     */     try {
/* 103 */       return (T)constructor.newInstance(args);
/*     */     }
/* 105 */     catch (Exception ex) {
/* 106 */       throw reflectionException(ex);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Object invoke(Method method, Object... args) {
/*     */     try {
/* 120 */       return method.invoke(null, args);
/*     */     }
/* 122 */     catch (Exception ex) {
/* 123 */       throw reflectionException(ex);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static boolean meetsConverterRequirements(Method method, Class<?> expectedReturnType) {
/* 128 */     int modifiers = method.getModifiers();
/* 129 */     return (Modifier.isPublic(modifiers) && Modifier.isStatic(modifiers) && expectedReturnType.equals(method.getReturnType()));
/*     */   }
/*     */   
/*     */   private static RuntimeException reflectionException(Exception ex) {
/* 133 */     if (ex instanceof IllegalArgumentException)
/* 134 */       return new ReflectionException(ex); 
/* 135 */     if (ex instanceof java.lang.reflect.InvocationTargetException)
/* 136 */       return new ReflectionException(ex.getCause()); 
/* 137 */     if (ex instanceof RuntimeException) {
/* 138 */       return (RuntimeException)ex;
/*     */     }
/* 140 */     return new ReflectionException(ex);
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\joptsimple\internal\Reflection.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */