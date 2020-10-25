/*    */ package com.avaje.ebeaninternal.server.deploy.parse;
/*    */ 
/*    */ import com.avaje.ebeaninternal.api.ClassUtil;
/*    */ import java.util.logging.Logger;
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
/*    */ public class DetectScala
/*    */ {
/* 33 */   private static final Logger logger = Logger.getLogger(DetectScala.class.getName());
/*    */   
/* 35 */   private static Class<?> scalaOptionClass = initScalaOptionClass();
/*    */   
/* 37 */   private static boolean hasScalaSupport = (scalaOptionClass != null);
/*    */   
/*    */   private static Class<?> initScalaOptionClass() {
/*    */     try {
/* 41 */       return ClassUtil.forName("scala.Option");
/* 42 */     } catch (ClassNotFoundException e) {
/*    */       
/* 44 */       logger.fine("Scala type 'scala.Option' not found. Scala Support disabled.");
/* 45 */       return null;
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 53 */   public static boolean hasScalaSupport() { return hasScalaSupport; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 60 */   public static Class<?> getScalaOptionClass() { return scalaOptionClass; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\deploy\parse\DetectScala.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */