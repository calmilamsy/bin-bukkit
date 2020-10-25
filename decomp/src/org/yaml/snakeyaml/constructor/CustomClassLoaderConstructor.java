/*    */ package org.yaml.snakeyaml.constructor;
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
/*    */ public class CustomClassLoaderConstructor
/*    */   extends Constructor
/*    */ {
/* 23 */   private ClassLoader loader = CustomClassLoaderConstructor.class.getClassLoader();
/*    */ 
/*    */   
/* 26 */   public CustomClassLoaderConstructor(ClassLoader cLoader) { this(Object.class, cLoader); }
/*    */ 
/*    */   
/*    */   public CustomClassLoaderConstructor(Class<? extends Object> theRoot, ClassLoader theLoader) {
/* 30 */     super(theRoot);
/* 31 */     if (theLoader == null) {
/* 32 */       throw new NullPointerException("Loader must be provided.");
/*    */     }
/* 34 */     this.loader = theLoader;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/* 39 */   protected Class<?> getClassForName(String name) throws ClassNotFoundException { return Class.forName(name, true, this.loader); }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\yaml\snakeyaml\constructor\CustomClassLoaderConstructor.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */