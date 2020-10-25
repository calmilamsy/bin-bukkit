/*     */ package com.avaje.ebean.enhance.agent;
/*     */ 
/*     */ import com.avaje.ebean.enhance.asm.ClassReader;
/*     */ import com.avaje.ebean.enhance.asm.ClassWriter;
/*     */ import java.io.PrintStream;
/*     */ import java.lang.instrument.ClassFileTransformer;
/*     */ import java.lang.instrument.IllegalClassFormatException;
/*     */ import java.lang.instrument.Instrumentation;
/*     */ import java.net.URL;
/*     */ import java.security.ProtectionDomain;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Transformer
/*     */   implements ClassFileTransformer
/*     */ {
/*     */   private static final int CLASS_WRITER_COMPUTEFLAGS = 3;
/*     */   private final EnhanceContext enhanceContext;
/*     */   private boolean performDetect;
/*     */   private boolean transformTransactional;
/*     */   private boolean transformEntityBeans;
/*     */   
/*     */   public static void premain(String agentArgs, Instrumentation inst) {
/*  24 */     Transformer t = new Transformer("", agentArgs);
/*  25 */     inst.addTransformer(t);
/*     */     
/*  27 */     if (t.getLogLevel() > 0) {
/*  28 */       System.out.println("premain loading Transformer args:" + agentArgs);
/*     */     }
/*     */   }
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
/*  41 */   public Transformer(String extraClassPath, String agentArgs) { this(parseClassPaths(extraClassPath), agentArgs); }
/*     */ 
/*     */ 
/*     */   
/*  45 */   public Transformer(URL[] extraClassPath, String agentArgs) { this(new ClassPathClassBytesReader(extraClassPath), agentArgs); }
/*     */ 
/*     */   
/*     */   public Transformer(ClassBytesReader r, String agentArgs) {
/*  49 */     this.enhanceContext = new EnhanceContext(r, false, agentArgs);
/*  50 */     this.performDetect = this.enhanceContext.getPropertyBoolean("detect", true);
/*  51 */     this.transformTransactional = this.enhanceContext.getPropertyBoolean("transactional", true);
/*  52 */     this.transformEntityBeans = this.enhanceContext.getPropertyBoolean("entity", true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  62 */   protected ClassWriter createClassWriter() { return new ClassWriter(3); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  69 */   public void setLogout(PrintStream logout) { this.enhanceContext.setLogout(logout); }
/*     */ 
/*     */ 
/*     */   
/*  73 */   public void log(int level, String msg) { this.enhanceContext.log(level, msg); }
/*     */ 
/*     */ 
/*     */   
/*  77 */   public int getLogLevel() { return this.enhanceContext.getLogLevel(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
/*     */     try {
/*  86 */       if (this.enhanceContext.isIgnoreClass(className)) {
/*  87 */         return null;
/*     */       }
/*     */       
/*  90 */       ClassAdapterDetectEnhancement detect = null;
/*     */       
/*  92 */       if (this.performDetect) {
/*  93 */         this.enhanceContext.log(5, "performing detection on " + className);
/*  94 */         detect = detect(loader, classfileBuffer);
/*     */       } 
/*     */       
/*  97 */       if (detect == null) {
/*     */         
/*  99 */         this.enhanceContext.log(1, "no detection so enhancing entity " + className);
/* 100 */         return entityEnhancement(loader, classfileBuffer);
/*     */       } 
/*     */       
/* 103 */       if (this.transformEntityBeans && detect.isEntity())
/*     */       {
/* 105 */         if (detect.isEnhancedEntity()) {
/* 106 */           detect.log(1, "already enhanced entity");
/*     */         }
/*     */         else {
/*     */           
/* 110 */           detect.log(2, "performing entity transform");
/* 111 */           return entityEnhancement(loader, classfileBuffer);
/*     */         } 
/*     */       }
/*     */       
/* 115 */       if (this.transformTransactional && detect.isTransactional())
/*     */       {
/* 117 */         if (detect.isEnhancedTransactional()) {
/* 118 */           detect.log(1, "already enhanced transactional");
/*     */         } else {
/*     */           
/* 121 */           detect.log(2, "performing transactional transform");
/* 122 */           return transactionalEnhancement(loader, classfileBuffer);
/*     */         } 
/*     */       }
/*     */       
/* 126 */       return null;
/*     */     }
/* 128 */     catch (NoEnhancementRequiredException e) {
/*     */       
/* 130 */       log(8, "No Enhancement required " + e.getMessage());
/* 131 */       return null;
/*     */     }
/* 133 */     catch (Exception e) {
/*     */ 
/*     */       
/* 136 */       this.enhanceContext.log(e);
/* 137 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private byte[] entityEnhancement(ClassLoader loader, byte[] classfileBuffer) {
/* 146 */     ClassReader cr = new ClassReader(classfileBuffer);
/* 147 */     ClassWriter cw = createClassWriter();
/* 148 */     ClassAdpaterEntity ca = new ClassAdpaterEntity(cw, loader, this.enhanceContext);
/*     */     
/*     */     try {
/* 151 */       cr.accept(ca, 0);
/*     */       
/* 153 */       if (ca.isLog(1)) {
/* 154 */         ca.logEnhanced();
/*     */       }
/*     */       
/* 157 */       if (this.enhanceContext.isReadOnly()) {
/* 158 */         return null;
/*     */       }
/*     */       
/* 161 */       return cw.toByteArray();
/*     */     
/*     */     }
/* 164 */     catch (AlreadyEnhancedException e) {
/* 165 */       if (ca.isLog(1)) {
/* 166 */         ca.log("already enhanced entity");
/*     */       }
/* 168 */       return null;
/*     */     }
/* 170 */     catch (NoEnhancementRequiredException e) {
/* 171 */       if (ca.isLog(2)) {
/* 172 */         ca.log("skipping... no enhancement required");
/*     */       }
/* 174 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private byte[] transactionalEnhancement(ClassLoader loader, byte[] classfileBuffer) {
/* 183 */     ClassReader cr = new ClassReader(classfileBuffer);
/* 184 */     ClassWriter cw = createClassWriter();
/* 185 */     ClassAdapterTransactional ca = new ClassAdapterTransactional(cw, loader, this.enhanceContext);
/*     */ 
/*     */     
/*     */     try {
/* 189 */       cr.accept(ca, 0);
/*     */       
/* 191 */       if (ca.isLog(1)) {
/* 192 */         ca.log("enhanced");
/*     */       }
/*     */       
/* 195 */       if (this.enhanceContext.isReadOnly()) {
/* 196 */         return null;
/*     */       }
/*     */       
/* 199 */       return cw.toByteArray();
/*     */     
/*     */     }
/* 202 */     catch (AlreadyEnhancedException e) {
/* 203 */       if (ca.isLog(1)) {
/* 204 */         ca.log("already enhanced");
/*     */       }
/* 206 */       return null;
/*     */     }
/* 208 */     catch (NoEnhancementRequiredException e) {
/* 209 */       if (ca.isLog(0)) {
/* 210 */         ca.log("skipping... no enhancement required");
/*     */       }
/* 212 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static URL[] parseClassPaths(String extraClassPath) {
/* 221 */     if (extraClassPath == null) {
/* 222 */       return new URL[0];
/*     */     }
/*     */     
/* 225 */     String[] stringPaths = extraClassPath.split(";");
/* 226 */     return UrlPathHelper.convertToUrl(stringPaths);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private ClassAdapterDetectEnhancement detect(ClassLoader classLoader, byte[] classfileBuffer) {
/* 235 */     ClassAdapterDetectEnhancement detect = new ClassAdapterDetectEnhancement(classLoader, this.enhanceContext);
/*     */ 
/*     */     
/* 238 */     ClassReader cr = new ClassReader(classfileBuffer);
/* 239 */     cr.accept(detect, 7);
/*     */     
/* 241 */     return detect;
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebean\enhance\agent\Transformer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */