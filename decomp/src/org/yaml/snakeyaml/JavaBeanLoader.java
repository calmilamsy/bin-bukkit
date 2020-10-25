/*    */ package org.yaml.snakeyaml;
/*    */ 
/*    */ import java.io.InputStream;
/*    */ import java.io.Reader;
/*    */ import java.io.StringReader;
/*    */ import org.yaml.snakeyaml.constructor.Constructor;
/*    */ import org.yaml.snakeyaml.introspector.BeanAccess;
/*    */ import org.yaml.snakeyaml.reader.UnicodeReader;
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
/*    */ public class JavaBeanLoader<T>
/*    */   extends Object
/*    */ {
/*    */   private Yaml loader;
/*    */   
/* 38 */   public JavaBeanLoader(TypeDescription typeDescription) { this(typeDescription, BeanAccess.DEFAULT); }
/*    */ 
/*    */   
/*    */   public JavaBeanLoader(TypeDescription typeDescription, BeanAccess beanAccess) {
/* 42 */     if (typeDescription == null) {
/* 43 */       throw new NullPointerException("TypeDescription must be provided.");
/*    */     }
/* 45 */     Constructor constructor = new Constructor(typeDescription.getType());
/* 46 */     typeDescription.setRoot(true);
/* 47 */     constructor.addTypeDescription(typeDescription);
/* 48 */     this.loader = new Yaml(constructor);
/* 49 */     this.loader.setBeanAccess(beanAccess);
/*    */   }
/*    */ 
/*    */   
/* 53 */   public <S extends T> JavaBeanLoader(Class<S> clazz, BeanAccess beanAccess) { this(new TypeDescription(clazz), beanAccess); }
/*    */ 
/*    */ 
/*    */   
/* 57 */   public <S extends T> JavaBeanLoader(Class<S> clazz) { this(clazz, BeanAccess.DEFAULT); }
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
/* 70 */   public T load(String yaml) { return (T)this.loader.load(new StringReader(yaml)); }
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
/* 83 */   public T load(InputStream io) { return (T)this.loader.load(new UnicodeReader(io)); }
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
/* 96 */   public T load(Reader io) { return (T)this.loader.load(io); }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\yaml\snakeyaml\JavaBeanLoader.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */