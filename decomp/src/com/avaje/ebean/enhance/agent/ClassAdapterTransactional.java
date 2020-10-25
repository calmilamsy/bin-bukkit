/*     */ package com.avaje.ebean.enhance.agent;
/*     */ 
/*     */ import com.avaje.ebean.enhance.asm.AnnotationVisitor;
/*     */ import com.avaje.ebean.enhance.asm.ClassAdapter;
/*     */ import com.avaje.ebean.enhance.asm.ClassVisitor;
/*     */ import com.avaje.ebean.enhance.asm.MethodVisitor;
/*     */ import java.util.ArrayList;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ClassAdapterTransactional
/*     */   extends ClassAdapter
/*     */ {
/*  17 */   static final Logger logger = Logger.getLogger(ClassAdapterTransactional.class.getName());
/*     */   
/*  19 */   final ArrayList<String> transactionalMethods = new ArrayList();
/*     */   
/*     */   final EnhanceContext enhanceContext;
/*     */   
/*     */   final ClassLoader classLoader;
/*     */   
/*  25 */   ArrayList<ClassMeta> transactionalInterfaces = new ArrayList();
/*     */ 
/*     */   
/*     */   AnnotationInfo classAnnotationInfo;
/*     */ 
/*     */   
/*     */   String className;
/*     */ 
/*     */   
/*     */   public ClassAdapterTransactional(ClassVisitor cv, ClassLoader classLoader, EnhanceContext context) {
/*  35 */     super(cv);
/*  36 */     this.classLoader = classLoader;
/*  37 */     this.enhanceContext = context;
/*     */   }
/*     */ 
/*     */   
/*  41 */   public boolean isLog(int level) { return this.enhanceContext.isLog(level); }
/*     */ 
/*     */ 
/*     */   
/*  45 */   public void log(String msg) { this.enhanceContext.log(this.className, msg); }
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
/*     */   public AnnotationInfo getInterfaceTransactionalInfo(String methodName, String methodDesc) {
/*  61 */     AnnotationInfo interfaceAnnotationInfo = null;
/*     */     
/*  63 */     for (int i = 0; i < this.transactionalInterfaces.size(); i++) {
/*  64 */       ClassMeta interfaceMeta = (ClassMeta)this.transactionalInterfaces.get(i);
/*  65 */       AnnotationInfo ai = interfaceMeta.getInterfaceTransactionalInfo(methodName, methodDesc);
/*  66 */       if (ai != null) {
/*  67 */         if (interfaceAnnotationInfo != null) {
/*  68 */           String msg = "Error in [" + this.className + "] searching the transactional interfaces [" + this.transactionalInterfaces + "] found more than one match for the transactional method:" + methodName + " " + methodDesc;
/*     */ 
/*     */ 
/*     */           
/*  72 */           logger.log(Level.SEVERE, msg);
/*     */         } else {
/*     */           
/*  75 */           interfaceAnnotationInfo = ai;
/*  76 */           if (isLog(2)) {
/*  77 */             log("inherit transactional from interface [" + interfaceMeta + "] method[" + methodName + " " + methodDesc + "]");
/*     */           }
/*     */         } 
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/*  84 */     return interfaceAnnotationInfo;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
/*  93 */     this.className = name;
/*     */ 
/*     */     
/*  96 */     int n = 1 + interfaces.length;
/*  97 */     String[] newInterfaces = new String[n];
/*  98 */     for (int i = 0; i < interfaces.length; i++) {
/*  99 */       newInterfaces[i] = interfaces[i];
/* 100 */       if (newInterfaces[i].equals("com/avaje/ebean/enhance/agent/EnhancedTransactional")) {
/* 101 */         throw new AlreadyEnhancedException(name);
/*     */       }
/* 103 */       ClassMeta interfaceMeta = this.enhanceContext.getInterfaceMeta(newInterfaces[i], this.classLoader);
/* 104 */       if (interfaceMeta != null && interfaceMeta.isTransactional()) {
/*     */ 
/*     */         
/* 107 */         this.transactionalInterfaces.add(interfaceMeta);
/*     */         
/* 109 */         if (isLog(6)) {
/* 110 */           log(" implements tranactional interface " + interfaceMeta.getDescription());
/*     */         }
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 116 */     newInterfaces[newInterfaces.length - 1] = "com/avaje/ebean/enhance/agent/EnhancedTransactional";
/*     */     
/* 118 */     super.visit(version, access, name, signature, superName, newInterfaces);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
/* 127 */     AnnotationVisitor av = super.visitAnnotation(desc, visible);
/*     */     
/* 129 */     if (desc.equals("Lcom/avaje/ebean/annotation/Transactional;")) {
/*     */ 
/*     */       
/* 132 */       this.classAnnotationInfo = new AnnotationInfo(null);
/* 133 */       return new AnnotationInfoVisitor(null, this.classAnnotationInfo, av);
/*     */     } 
/*     */     
/* 136 */     return av;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
/* 147 */     MethodVisitor mv = super.visitMethod(access, name, desc, signature, exceptions);
/* 148 */     if (name.equals("<init>"))
/*     */     {
/* 150 */       return mv;
/*     */     }
/* 152 */     return new ScopeTransAdapter(this, mv, access, name, desc);
/*     */   }
/*     */ 
/*     */   
/*     */   public void visitEnd() {
/* 157 */     if (!isLog(3))
/*     */     {
/* 159 */       if (isLog(2))
/* 160 */         log("methods:" + this.transactionalMethods); 
/*     */     }
/* 162 */     super.visitEnd();
/*     */   }
/*     */ 
/*     */   
/*     */   void transactionalMethod(String methodName, String methodDesc, AnnotationInfo annoInfo) {
/* 167 */     this.transactionalMethods.add(methodName);
/*     */     
/* 169 */     if (isLog(4)) {
/* 170 */       log("method:" + methodName + " " + methodDesc + " transactional " + annoInfo);
/* 171 */     } else if (isLog(3)) {
/* 172 */       log("method:" + methodName);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebean\enhance\agent\ClassAdapterTransactional.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */