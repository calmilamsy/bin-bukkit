/*     */ package com.avaje.ebeaninternal.server.subclass;
/*     */ 
/*     */ import com.avaje.ebean.enhance.agent.AlreadyEnhancedException;
/*     */ import com.avaje.ebean.enhance.agent.ClassMeta;
/*     */ import com.avaje.ebean.enhance.agent.EnhanceConstants;
/*     */ import com.avaje.ebean.enhance.agent.EnhanceContext;
/*     */ import com.avaje.ebean.enhance.agent.IndexFieldWeaver;
/*     */ import com.avaje.ebean.enhance.agent.InterceptField;
/*     */ import com.avaje.ebean.enhance.agent.MarkerField;
/*     */ import com.avaje.ebean.enhance.agent.MethodEquals;
/*     */ import com.avaje.ebean.enhance.agent.MethodIsEmbeddedNewOrDirty;
/*     */ import com.avaje.ebean.enhance.agent.MethodNewInstance;
/*     */ import com.avaje.ebean.enhance.agent.MethodPropertyChangeListener;
/*     */ import com.avaje.ebean.enhance.agent.MethodSetEmbeddedLoaded;
/*     */ import com.avaje.ebean.enhance.agent.NoEnhancementRequiredException;
/*     */ import com.avaje.ebean.enhance.agent.VisitMethodParams;
/*     */ import com.avaje.ebean.enhance.asm.AnnotationVisitor;
/*     */ import com.avaje.ebean.enhance.asm.ClassAdapter;
/*     */ import com.avaje.ebean.enhance.asm.ClassVisitor;
/*     */ import com.avaje.ebean.enhance.asm.FieldVisitor;
/*     */ import com.avaje.ebean.enhance.asm.MethodVisitor;
/*     */ import java.util.logging.Logger;
/*     */ 
/*     */ public class SubClassClassAdpater
/*     */   extends ClassAdapter
/*     */   implements EnhanceConstants
/*     */ {
/*  28 */   static final Logger logger = Logger.getLogger(SubClassClassAdpater.class.getName());
/*     */   
/*     */   final EnhanceContext enhanceContext;
/*     */   
/*     */   final ClassLoader classLoader;
/*     */   
/*     */   final ClassMeta classMeta;
/*     */   
/*     */   final String subClassSuffix;
/*     */   
/*     */   boolean firstMethod = true;
/*     */   
/*     */   public SubClassClassAdpater(String subClassSuffix, ClassVisitor cv, ClassLoader classLoader, EnhanceContext context) {
/*  41 */     super(cv);
/*  42 */     this.subClassSuffix = subClassSuffix;
/*  43 */     this.classLoader = classLoader;
/*  44 */     this.enhanceContext = context;
/*  45 */     this.classMeta = context.createClassMeta();
/*     */   }
/*     */ 
/*     */   
/*  49 */   public boolean isLog(int level) { return this.classMeta.isLog(level); }
/*     */ 
/*     */ 
/*     */   
/*  53 */   public void log(String msg) { this.classMeta.log(msg); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
/*  63 */     int n = 1 + interfaces.length;
/*  64 */     String[] c = new String[n];
/*  65 */     for (int i = 0; i < interfaces.length; i++) {
/*  66 */       c[i] = interfaces[i];
/*  67 */       if (c[i].equals("com/avaje/ebean/bean/EntityBean")) {
/*  68 */         throw new AlreadyEnhancedException(name);
/*     */       }
/*  70 */       if (c[i].equals("scala/ScalaObject")) {
/*  71 */         this.classMeta.setScalaInterface(true);
/*     */       }
/*  73 */       if (c[i].equals("groovy/lang/GroovyObject")) {
/*  74 */         this.classMeta.setGroovyInterface(true);
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/*  79 */     c[c.length - 1] = "com/avaje/ebean/bean/EntityBean";
/*     */     
/*  81 */     if (!superName.equals("java/lang/Object")) {
/*     */       
/*  83 */       ClassMeta superMeta = this.enhanceContext.getSuperMeta(superName, this.classLoader);
/*  84 */       if (superMeta != null) {
/*  85 */         this.classMeta.setSuperMeta(superMeta);
/*  86 */         if (this.classMeta.isLog(2)) {
/*  87 */           this.classMeta.log("entity inheritance " + superMeta.getDescription());
/*     */         }
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  95 */     superName = name;
/*  96 */     name = name + this.subClassSuffix;
/*     */     
/*  98 */     this.classMeta.setClassName(name, superName);
/*     */     
/* 100 */     super.visit(version, access, name, signature, superName, c);
/*     */   }
/*     */ 
/*     */   
/*     */   public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
/* 105 */     this.classMeta.addClassAnnotation(desc);
/* 106 */     return super.visitAnnotation(desc, visible);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FieldVisitor visitField(int access, String name, String desc, String signature, Object value) {
/* 116 */     if ((access & 0x8) != 0) {
/*     */       
/* 118 */       if (isLog(2)) {
/* 119 */         log("Skip intercepting static field " + name);
/*     */       }
/* 121 */       return null;
/*     */     } 
/*     */     
/* 124 */     if ((access & 0x80) != 0) {
/*     */       
/* 126 */       if (this.classMeta.isLog(2)) {
/* 127 */         this.classMeta.log("Skip intercepting transient field " + name);
/*     */       }
/* 129 */       return null;
/*     */     } 
/*     */ 
/*     */     
/* 133 */     if (this.classMeta.isLog(5)) {
/* 134 */       this.classMeta.log(" ... reading field:" + name + " desc:" + desc);
/*     */     }
/*     */     
/* 137 */     return this.classMeta.createLocalFieldVisitor(name, desc);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
/* 147 */     if (this.firstMethod) {
/* 148 */       if (!this.classMeta.isEntityEnhancementRequired())
/*     */       {
/* 150 */         throw new NoEnhancementRequiredException();
/*     */       }
/*     */ 
/*     */       
/* 154 */       String marker = MarkerField.addField(this.cv, this.classMeta.getClassName());
/* 155 */       if (isLog(4)) {
/* 156 */         log("... add marker field \"" + marker + "\"");
/* 157 */         log("... add intercept and identity fields");
/*     */       } 
/*     */ 
/*     */       
/* 161 */       InterceptField.addField(this.cv, this.enhanceContext.isTransientInternalFields());
/* 162 */       MethodEquals.addIdentityField(this.cv);
/* 163 */       this.firstMethod = false;
/*     */     } 
/*     */ 
/*     */     
/* 167 */     VisitMethodParams params = new VisitMethodParams(this.cv, access, name, desc, signature, exceptions);
/*     */     
/* 169 */     if (isDefaultConstructor(access, name, desc, signature, exceptions)) {
/* 170 */       SubClassConstructor.add(params, this.classMeta);
/* 171 */       return null;
/*     */     } 
/*     */     
/* 174 */     if (isSpecialMethod(access, name, desc)) {
/* 175 */       return null;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 182 */     this.classMeta.addExistingSuperMethod(name, desc);
/*     */     
/* 184 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void visitEnd() {
/* 193 */     if (!this.classMeta.isEntityEnhancementRequired()) {
/* 194 */       throw new NoEnhancementRequiredException();
/*     */     }
/*     */     
/* 197 */     if (!this.classMeta.hasDefaultConstructor()) {
/* 198 */       if (isLog(2)) {
/* 199 */         log("... adding default constructor");
/*     */       }
/* 201 */       SubClassConstructor.addDefault(this.cv, this.classMeta);
/*     */     } 
/*     */     
/* 204 */     MarkerField.addGetMarker(this.cv, this.classMeta.getClassName());
/*     */ 
/*     */     
/* 207 */     InterceptField.addGetterSetter(this.cv, this.classMeta.getClassName());
/*     */ 
/*     */     
/* 210 */     MethodPropertyChangeListener.addMethod(this.cv, this.classMeta);
/*     */ 
/*     */ 
/*     */     
/* 214 */     GetterSetterMethods.add(this.cv, this.classMeta);
/*     */ 
/*     */     
/* 217 */     IndexFieldWeaver.addMethods(this.cv, this.classMeta);
/*     */     
/* 219 */     MethodSetEmbeddedLoaded.addMethod(this.cv, this.classMeta);
/* 220 */     MethodIsEmbeddedNewOrDirty.addMethod(this.cv, this.classMeta);
/* 221 */     MethodNewInstance.addMethod(this.cv, this.classMeta);
/*     */ 
/*     */     
/* 224 */     MethodWriteReplace.add(this.cv, this.classMeta);
/*     */ 
/*     */     
/* 227 */     this.enhanceContext.addClassMeta(this.classMeta);
/*     */     
/* 229 */     super.visitEnd();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isDefaultConstructor(int access, String name, String desc, String signature, String[] exceptions) {
/* 238 */     if (name.equals("<init>") && desc.equals("()V")) {
/* 239 */       this.classMeta.setHasDefaultConstructor(true);
/* 240 */       return true;
/*     */     } 
/*     */     
/* 243 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isSpecialMethod(int access, String name, String desc) {
/* 251 */     if (name.equals("hashCode") && desc.equals("()I")) {
/* 252 */       this.classMeta.setHasEqualsOrHashcode(true);
/* 253 */       return true;
/*     */     } 
/*     */     
/* 256 */     if (name.equals("equals") && desc.equals("(Ljava/lang/Object;)Z")) {
/* 257 */       this.classMeta.setHasEqualsOrHashcode(true);
/* 258 */       return true;
/*     */     } 
/*     */     
/* 261 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\subclass\SubClassClassAdpater.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */