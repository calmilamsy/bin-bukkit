/*    */ package com.avaje.ebeaninternal.server.type;
/*    */ 
/*    */ import com.avaje.ebean.config.ScalarTypeConverter;
/*    */ import scala.Option;
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
/*    */ public class ScalaOptionTypeConverter<S>
/*    */   extends Object
/*    */   implements ScalarTypeConverter<Option<S>, S>
/*    */ {
/*    */   public Option<S> getNullValue() { // Byte code:
/*    */     //   0: getstatic scala/None$.MODULE$ : Lscala/None$;
/*    */     //   3: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #37	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	4	0	this	Lcom/avaje/ebeaninternal/server/type/ScalaOptionTypeConverter;
/*    */     // Local variable type table:
/*    */     //   start	length	slot	name	signature
/*    */     //   0	4	0	this	Lcom/avaje/ebeaninternal/server/type/ScalaOptionTypeConverter<TS;>; }
/*    */   
/*    */   public S unwrapValue(Option<S> beanType) {
/* 42 */     if (beanType.isEmpty()) {
/* 43 */       return null;
/*    */     }
/* 45 */     return (S)beanType.get();
/*    */   }
/*    */   
/*    */   public Option<S> wrapValue(S scalarType) { // Byte code:
/*    */     //   0: aload_1
/*    */     //   1: ifnonnull -> 8
/*    */     //   4: getstatic scala/None$.MODULE$ : Lscala/None$;
/*    */     //   7: areturn
/*    */     //   8: aload_1
/*    */     //   9: instanceof scala/Some
/*    */     //   12: ifeq -> 20
/*    */     //   15: aload_1
/*    */     //   16: checkcast scala/Option
/*    */     //   19: areturn
/*    */     //   20: new scala/Some
/*    */     //   23: dup
/*    */     //   24: aload_1
/*    */     //   25: invokespecial <init> : (Ljava/lang/Object;)V
/*    */     //   28: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #51	-> 0
/*    */     //   #52	-> 4
/*    */     //   #54	-> 8
/*    */     //   #55	-> 15
/*    */     //   #57	-> 20
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	29	0	this	Lcom/avaje/ebeaninternal/server/type/ScalaOptionTypeConverter;
/*    */     //   0	29	1	scalarType	Ljava/lang/Object;
/*    */     // Local variable type table:
/*    */     //   start	length	slot	name	signature
/*    */     //   0	29	0	this	Lcom/avaje/ebeaninternal/server/type/ScalaOptionTypeConverter<TS;>;
/*    */     //   0	29	1	scalarType	TS; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\type\ScalaOptionTypeConverter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */