/*    */ package org.bukkit.plugin.java;
/*    */ 
/*    */ import java.net.URL;
/*    */ import java.net.URLClassLoader;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ import java.util.Set;
/*    */ 
/*    */ 
/*    */ public class PluginClassLoader
/*    */   extends URLClassLoader
/*    */ {
/*    */   private final JavaPluginLoader loader;
/* 14 */   private final Map<String, Class<?>> classes = new HashMap();
/*    */   
/*    */   public PluginClassLoader(JavaPluginLoader loader, URL[] urls, ClassLoader parent) {
/* 17 */     super(urls, parent);
/*    */     
/* 19 */     this.loader = loader;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/* 24 */   protected Class<?> findClass(String name) throws ClassNotFoundException { return findClass(name, true); }
/*    */ 
/*    */   
/*    */   protected Class<?> findClass(String name, boolean checkGlobal) throws ClassNotFoundException {
/* 28 */     Class<?> result = (Class)this.classes.get(name);
/*    */     
/* 30 */     if (result == null) {
/* 31 */       if (checkGlobal) {
/* 32 */         result = this.loader.getClassByName(name);
/*    */       }
/*    */       
/* 35 */       if (result == null) {
/* 36 */         result = super.findClass(name);
/*    */         
/* 38 */         if (result != null) {
/* 39 */           this.loader.setClass(name, result);
/*    */         }
/*    */       } 
/*    */       
/* 43 */       this.classes.put(name, result);
/*    */     } 
/*    */     
/* 46 */     return result;
/*    */   }
/*    */ 
/*    */   
/* 50 */   public Set<String> getClasses() { return this.classes.keySet(); }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\plugin\java\PluginClassLoader.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */