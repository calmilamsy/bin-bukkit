/*     */ package com.avaje.ebeaninternal.server.core;
/*     */ 
/*     */ import com.avaje.ebean.EbeanServer;
/*     */ import com.avaje.ebean.bean.SerializeControl;
/*     */ import com.avaje.ebeaninternal.api.ClassUtil;
/*     */ import com.avaje.ebeaninternal.api.SpiEbeanServer;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
/*     */ import com.avaje.ebeaninternal.server.subclass.SubClassUtil;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectStreamClass;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ProxyBeanObjectInputStream
/*     */   extends ObjectInputStream
/*     */ {
/*     */   private final SpiEbeanServer ebeanServer;
/*     */   
/*     */   public ProxyBeanObjectInputStream(InputStream in, EbeanServer ebeanServer) throws IOException {
/*  60 */     super(in);
/*  61 */     this.ebeanServer = (SpiEbeanServer)ebeanServer;
/*  62 */     SerializeControl.setVanilla(false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void close() throws IOException {
/*  72 */     super.close();
/*  73 */     SerializeControl.resetToDefault();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Class<?> resolveGenerated(ObjectStreamClass desc) throws IOException, ClassNotFoundException {
/*  83 */     String className = desc.getName();
/*     */     
/*  85 */     String vanillaClassName = SubClassUtil.getSuperClassName(className);
/*  86 */     Class<?> vanillaClass = ClassUtil.forName(vanillaClassName, getClass());
/*     */     
/*  88 */     BeanDescriptor<?> d = this.ebeanServer.getBeanDescriptor(vanillaClass);
/*  89 */     if (d == null) {
/*  90 */       String msg = "Could not find BeanDescriptor for " + vanillaClassName;
/*  91 */       throw new IOException(msg);
/*     */     } 
/*  93 */     return d.getFactoryType();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Class<?> resolveClass(ObjectStreamClass desc) throws IOException, ClassNotFoundException {
/* 103 */     String className = desc.getName();
/* 104 */     if (SubClassUtil.isSubClass(className)) {
/* 105 */       return resolveGenerated(desc);
/*     */     }
/*     */     
/* 108 */     return super.resolveClass(desc);
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\core\ProxyBeanObjectInputStream.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */