/*    */ package com.avaje.ebeaninternal.server.deploy;
/*    */ 
/*    */ import java.util.List;
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
/*    */ public class ScalaBufferConverter
/*    */   implements CollectionTypeConverter
/*    */ {
/*    */   public Object toUnderlying(Object wrapped) {
/* 34 */     if (wrapped instanceof JavaConversions.JListWrapper) {
/* 35 */       return ((JavaConversions.JListWrapper)wrapped).underlying();
/*    */     }
/* 37 */     return null;
/*    */   }
/*    */   
/*    */   public Object toWrapped(Object wrapped) {
/* 41 */     if (wrapped instanceof List) {
/* 42 */       return JavaConversions.asBuffer((List)wrapped);
/*    */     }
/* 44 */     return wrapped;
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\deploy\ScalaBufferConverter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */