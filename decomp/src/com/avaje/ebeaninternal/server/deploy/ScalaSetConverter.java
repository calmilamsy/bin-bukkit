/*    */ package com.avaje.ebeaninternal.server.deploy;
/*    */ 
/*    */ import java.util.Set;
/*    */ import scala.collection.JavaConversions;
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
/*    */ public class ScalaSetConverter
/*    */   implements CollectionTypeConverter
/*    */ {
/*    */   public Object toUnderlying(Object wrapped) {
/* 34 */     if (wrapped instanceof JavaConversions.JSetWrapper) {
/* 35 */       return ((JavaConversions.JSetWrapper)wrapped).underlying();
/*    */     }
/* 37 */     return null;
/*    */   }
/*    */   
/*    */   public Object toWrapped(Object wrapped) {
/* 41 */     if (wrapped instanceof Set) {
/* 42 */       return JavaConversions.asSet((Set)wrapped);
/*    */     }
/* 44 */     return wrapped;
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\deploy\ScalaSetConverter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */