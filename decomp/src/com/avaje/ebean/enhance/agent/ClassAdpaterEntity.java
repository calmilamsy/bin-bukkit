/*     */ package com.avaje.ebean.enhance.agent;
/*     */ 
/*     */ import com.avaje.ebean.enhance.asm.AnnotationVisitor;
/*     */ import com.avaje.ebean.enhance.asm.ClassAdapter;
/*     */ import com.avaje.ebean.enhance.asm.ClassVisitor;
/*     */ import com.avaje.ebean.enhance.asm.FieldVisitor;
/*     */ import com.avaje.ebean.enhance.asm.MethodVisitor;
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
/*     */ public class ClassAdpaterEntity
/*     */   extends ClassAdapter
/*     */   implements EnhanceConstants
/*     */ {
/*     */   private final EnhanceContext enhanceContext;
/*     */   private final ClassLoader classLoader;
/*     */   private final ClassMeta classMeta;
/*     */   private boolean firstMethod = true;
/*     */   
/*     */   public ClassAdpaterEntity(ClassVisitor cv, ClassLoader classLoader, EnhanceContext context) {
/*  30 */     super(cv);
/*  31 */     this.classLoader = classLoader;
/*  32 */     this.enhanceContext = context;
/*  33 */     this.classMeta = context.createClassMeta();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  40 */   public void logEnhanced() { this.classMeta.logEnhanced(); }
/*     */ 
/*     */ 
/*     */   
/*  44 */   public boolean isLog(int level) { return this.classMeta.isLog(level); }
/*     */ 
/*     */ 
/*     */   
/*  48 */   public void log(String msg) { this.classMeta.log(msg); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
/*  57 */     this.classMeta.setClassName(name, superName);
/*     */     
/*  59 */     int n = 1 + interfaces.length;
/*  60 */     String[] c = new String[n];
/*  61 */     for (int i = 0; i < interfaces.length; i++) {
/*  62 */       c[i] = interfaces[i];
/*  63 */       if (c[i].equals("com/avaje/ebean/bean/EntityBean")) {
/*  64 */         this.classMeta.setEntityBeanInterface(true);
/*     */       }
/*  66 */       if (c[i].equals("scala/ScalaObject")) {
/*  67 */         this.classMeta.setScalaInterface(true);
/*     */       }
/*  69 */       if (c[i].equals("groovy/lang/GroovyObject")) {
/*  70 */         this.classMeta.setGroovyInterface(true);
/*     */       }
/*     */     } 
/*     */     
/*  74 */     if (this.classMeta.hasEntityBeanInterface()) {
/*     */       
/*  76 */       c = interfaces;
/*     */     } else {
/*     */       
/*  79 */       c[c.length - 1] = "com/avaje/ebean/bean/EntityBean";
/*     */     } 
/*     */     
/*  82 */     if (!superName.equals("java/lang/Object")) {
/*     */       
/*  84 */       if (this.classMeta.isLog(7)) {
/*  85 */         this.classMeta.log("read information about superClasses " + superName + " to see if it is entity/embedded/mappedSuperclass");
/*     */       }
/*     */       
/*  88 */       ClassMeta superMeta = this.enhanceContext.getSuperMeta(superName, this.classLoader);
/*  89 */       if (superMeta != null && superMeta.isEntity()) {
/*     */         
/*  91 */         this.classMeta.setSuperMeta(superMeta);
/*  92 */         if (this.classMeta.isLog(1)) {
/*  93 */           this.classMeta.log("entity extends " + superMeta.getDescription());
/*     */         }
/*     */       }
/*  96 */       else if (this.classMeta.isLog(7)) {
/*  97 */         if (superMeta == null) {
/*  98 */           this.classMeta.log("unable to read superMeta for " + superName);
/*     */         } else {
/* 100 */           this.classMeta.log("superMeta " + superName + " is not an entity/embedded/mappedsuperclass " + superMeta.getClassAnnotations());
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 106 */     super.visit(version, access, name, signature, superName, c);
/*     */   }
/*     */ 
/*     */   
/*     */   public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
/* 111 */     this.classMeta.addClassAnnotation(desc);
/* 112 */     return super.visitAnnotation(desc, visible);
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
/*     */   private boolean isEbeanFieldMarker(String name, String desc, String signature) {
/* 124 */     if (name.equals("_EBEAN_MARKER")) {
/* 125 */       if (!desc.equals("Ljava/lang/String;")) {
/* 126 */         String m = "Error: _EBEAN_MARKER field of wrong type? " + desc;
/* 127 */         this.classMeta.log(m);
/*     */       } 
/* 129 */       return true;
/*     */     } 
/* 131 */     return false;
/*     */   }
/*     */   
/*     */   private boolean isPropertyChangeListenerField(String name, String desc, String signature) {
/* 135 */     if (desc.equals("Ljava/beans/PropertyChangeSupport;")) {
/* 136 */       return true;
/*     */     }
/* 138 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FieldVisitor visitField(int access, String name, String desc, String signature, Object value) {
/* 148 */     if ((access & 0x8) != 0) {
/*     */       
/* 150 */       if (isEbeanFieldMarker(name, desc, signature)) {
/* 151 */         this.classMeta.setAlreadyEnhanced(true);
/* 152 */         if (isLog(2)) {
/* 153 */           log("Found ebean marker field " + name + " " + value);
/*     */         }
/*     */       }
/* 156 */       else if (isLog(2)) {
/* 157 */         log("Skip intercepting static field " + name);
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 162 */       return super.visitField(access, name, desc, signature, value);
/*     */     } 
/*     */     
/* 165 */     if (isPropertyChangeListenerField(name, desc, signature)) {
/*     */       
/* 167 */       if (isLog(1)) {
/* 168 */         this.classMeta.log("Found existing PropertyChangeSupport field " + name);
/*     */       }
/*     */       
/* 171 */       return super.visitField(access, name, desc, signature, value);
/*     */     } 
/*     */     
/* 174 */     if ((access & 0x80) != 0) {
/* 175 */       if (isLog(2)) {
/* 176 */         log("Skip intercepting transient field " + name);
/*     */       }
/*     */       
/* 179 */       return super.visitField(access, name, desc, signature, value);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 185 */     FieldVisitor fv = super.visitField(access, name, desc, signature, value);
/*     */     
/* 187 */     return this.classMeta.createLocalFieldVisitor(this.cv, fv, name, desc);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
/* 196 */     if (this.firstMethod) {
/* 197 */       if (!this.classMeta.isEntityEnhancementRequired())
/*     */       {
/* 199 */         throw new NoEnhancementRequiredException();
/*     */       }
/*     */       
/* 202 */       if (this.classMeta.hasEntityBeanInterface()) {
/* 203 */         log("Enhancing when EntityBean interface already exists!");
/*     */       }
/*     */ 
/*     */       
/* 207 */       String marker = MarkerField.addField(this.cv, this.classMeta.getClassName());
/* 208 */       if (isLog(4)) {
/* 209 */         log("... add marker field \"" + marker + "\"");
/*     */       }
/*     */       
/* 212 */       if (!this.classMeta.isSuperClassEntity()) {
/*     */ 
/*     */         
/* 215 */         if (isLog(4)) {
/* 216 */           log("... add intercept and identity fields");
/*     */         }
/* 218 */         InterceptField.addField(this.cv, this.enhanceContext.isTransientInternalFields());
/* 219 */         MethodEquals.addIdentityField(this.cv);
/*     */       } 
/*     */       
/* 222 */       this.firstMethod = false;
/*     */     } 
/*     */ 
/*     */     
/* 226 */     this.classMeta.addExistingMethod(name, desc);
/*     */     
/* 228 */     if (isDefaultConstructor(name, desc)) {
/*     */       
/* 230 */       MethodVisitor mv = super.visitMethod(1, name, desc, signature, exceptions);
/*     */       
/* 232 */       return new ConstructorAdapter(mv, this.classMeta, desc);
/*     */     } 
/*     */     
/* 235 */     MethodVisitor mv = super.visitMethod(access, name, desc, signature, exceptions);
/*     */     
/* 237 */     if (interceptEntityMethod(access, name, desc, signature, exceptions))
/*     */     {
/*     */       
/* 240 */       return new MethodFieldAdapter(mv, this.classMeta, name + " " + desc);
/*     */     }
/*     */ 
/*     */     
/* 244 */     return mv;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void visitEnd() {
/* 253 */     if (!this.classMeta.isEntityEnhancementRequired()) {
/* 254 */       throw new NoEnhancementRequiredException();
/*     */     }
/*     */     
/* 257 */     if (!this.classMeta.hasDefaultConstructor()) {
/* 258 */       DefaultConstructor.add(this.cv, this.classMeta);
/*     */     }
/*     */     
/* 261 */     MarkerField.addGetMarker(this.cv, this.classMeta.getClassName());
/*     */     
/* 263 */     if (!this.classMeta.isSuperClassEntity()) {
/*     */       
/* 265 */       InterceptField.addGetterSetter(this.cv, this.classMeta.getClassName());
/*     */ 
/*     */       
/* 268 */       MethodPropertyChangeListener.addMethod(this.cv, this.classMeta);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 273 */     this.classMeta.addFieldGetSetMethods(this.cv);
/*     */ 
/*     */     
/* 276 */     IndexFieldWeaver.addMethods(this.cv, this.classMeta);
/*     */     
/* 278 */     MethodSetEmbeddedLoaded.addMethod(this.cv, this.classMeta);
/* 279 */     MethodIsEmbeddedNewOrDirty.addMethod(this.cv, this.classMeta);
/* 280 */     MethodNewInstance.addMethod(this.cv, this.classMeta);
/*     */ 
/*     */     
/* 283 */     this.enhanceContext.addClassMeta(this.classMeta);
/*     */     
/* 285 */     super.visitEnd();
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean isDefaultConstructor(String name, String desc) {
/* 290 */     if (name.equals("<init>")) {
/* 291 */       if (desc.equals("()V")) {
/* 292 */         this.classMeta.setHasDefaultConstructor(true);
/*     */       }
/* 294 */       return true;
/*     */     } 
/*     */     
/* 297 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean interceptEntityMethod(int access, String name, String desc, String signature, String[] exceptions) {
/* 303 */     if ((access & 0x8) != 0) {
/*     */       
/* 305 */       if (isLog(2)) {
/* 306 */         log("Skip intercepting static method " + name);
/*     */       }
/* 308 */       return false;
/*     */     } 
/*     */     
/* 311 */     if (name.equals("hashCode") && desc.equals("()I")) {
/* 312 */       this.classMeta.setHasEqualsOrHashcode(true);
/* 313 */       return true;
/*     */     } 
/*     */     
/* 316 */     if (name.equals("equals") && desc.equals("(Ljava/lang/Object;)Z")) {
/* 317 */       this.classMeta.setHasEqualsOrHashcode(true);
/* 318 */       return true;
/*     */     } 
/*     */     
/* 321 */     if (name.equals("toString") && desc.equals("()Ljava/lang/String;"))
/*     */     {
/*     */       
/* 324 */       return false;
/*     */     }
/*     */     
/* 327 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebean\enhance\agent\ClassAdpaterEntity.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */