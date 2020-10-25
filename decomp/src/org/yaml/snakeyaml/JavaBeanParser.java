/*    */ package org.yaml.snakeyaml;
/*    */ 
/*    */ import java.io.InputStream;
/*    */ import java.io.Reader;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class JavaBeanParser
/*    */ {
/*    */   public static <T> T load(String yaml, Class<T> javabean) {
/* 46 */     JavaBeanLoader<T> loader = new JavaBeanLoader<T>(javabean);
/* 47 */     return (T)loader.load(yaml);
/*    */   }
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
/*    */   public static <T> T load(InputStream io, Class<T> javabean) {
/* 61 */     JavaBeanLoader<T> loader = new JavaBeanLoader<T>(javabean);
/* 62 */     return (T)loader.load(io);
/*    */   }
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
/*    */   public static <T> T load(Reader io, Class<T> javabean) {
/* 76 */     JavaBeanLoader<T> loader = new JavaBeanLoader<T>(javabean);
/* 77 */     return (T)loader.load(io);
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\yaml\snakeyaml\JavaBeanParser.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */