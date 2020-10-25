/*    */ package com.avaje.ebeaninternal.server.type.reflect;
/*    */ 
/*    */ import com.avaje.ebeaninternal.server.type.ScalarType;
/*    */ import com.avaje.ebeaninternal.server.type.ScalarTypeWrapper;
/*    */ import com.avaje.ebeaninternal.server.type.TypeManager;
/*    */ import java.lang.reflect.Constructor;
/*    */ import java.lang.reflect.Method;
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
/*    */ public class ReflectionBasedTypeBuilder
/*    */ {
/*    */   private final TypeManager typeManager;
/*    */   
/* 34 */   public ReflectionBasedTypeBuilder(TypeManager typeManager) { this.typeManager = typeManager; }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ScalarType<?> buildScalarType(ImmutableMeta meta) {
/* 40 */     if (meta.isCompoundType()) {
/* 41 */       throw new RuntimeException("Must be scalar");
/*    */     }
/*    */     
/* 44 */     Constructor<?> constructor = meta.getConstructor();
/*    */     
/* 46 */     Class<?> logicalType = constructor.getDeclaringClass();
/*    */     
/* 48 */     Method[] readers = meta.getReaders();
/* 49 */     Class<?> returnType = readers[0].getReturnType();
/*    */     
/* 51 */     ScalarType<?> scalarType = this.typeManager.recursiveCreateScalarTypes(returnType);
/*    */     
/* 53 */     ReflectionBasedScalarTypeConverter r = new ReflectionBasedScalarTypeConverter(constructor, readers[0]);
/*    */     
/* 55 */     return new ScalarTypeWrapper(logicalType, scalarType, r);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ReflectionBasedCompoundType buildCompound(ImmutableMeta meta) {
/* 62 */     Constructor<?> constructor = meta.getConstructor();
/*    */     
/* 64 */     Method[] readers = meta.getReaders();
/*    */     
/* 66 */     ReflectionBasedCompoundTypeProperty[] props = new ReflectionBasedCompoundTypeProperty[readers.length];
/*    */ 
/*    */     
/* 69 */     for (int i = 0; i < readers.length; i++) {
/* 70 */       Class<?> returnType = readers[i].getReturnType();
/*    */ 
/*    */       
/* 73 */       this.typeManager.recursiveCreateScalarDataReader(returnType);
/*    */       
/* 75 */       String name = getPropertyName(readers[i]);
/*    */       
/* 77 */       props[i] = new ReflectionBasedCompoundTypeProperty(name, readers[i], returnType);
/*    */     } 
/*    */     
/* 80 */     return new ReflectionBasedCompoundType(constructor, props);
/*    */   }
/*    */ 
/*    */   
/*    */   private String getPropertyName(Method method) {
/* 85 */     String name = method.getName();
/* 86 */     if (name.startsWith("is"))
/* 87 */       return lowerFirstChar(name.substring(2)); 
/* 88 */     if (name.startsWith("get")) {
/* 89 */       return lowerFirstChar(name.substring(3));
/*    */     }
/* 91 */     String msg = "Expecting method " + name + " to start with is or get " + " so as to follow bean specification?";
/*    */     
/* 93 */     throw new RuntimeException(msg);
/*    */   }
/*    */ 
/*    */   
/* 97 */   private String lowerFirstChar(String name) { return Character.toLowerCase(name.charAt(0)) + name.substring(1); }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\type\reflect\ReflectionBasedTypeBuilder.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */