/*     */ package com.avaje.ebean.enhance.agent;
/*     */ 
/*     */ import com.avaje.ebean.enhance.asm.AnnotationVisitor;
/*     */ import com.avaje.ebean.enhance.asm.ClassVisitor;
/*     */ import com.avaje.ebean.enhance.asm.EmptyVisitor;
/*     */ import com.avaje.ebean.enhance.asm.FieldVisitor;
/*     */ import com.avaje.ebean.enhance.asm.MethodVisitor;
/*     */ import java.io.PrintStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ClassMeta
/*     */ {
/*  24 */   private static final Logger logger = Logger.getLogger(ClassMeta.class.getName());
/*     */   
/*  26 */   private static final String OBJECT_CLASS = Object.class.getName().replace('.', '/');
/*     */   
/*     */   private final PrintStream logout;
/*     */   
/*     */   private final int logLevel;
/*     */   
/*     */   private final boolean subclassing;
/*     */   
/*     */   private String className;
/*     */   
/*     */   private String superClassName;
/*     */   
/*     */   private ClassMeta superMeta;
/*     */   
/*     */   private boolean hasGroovyInterface;
/*     */   
/*     */   private boolean hasScalaInterface;
/*     */   
/*     */   private boolean hasEntityBeanInterface;
/*     */   
/*     */   private boolean alreadyEnhanced;
/*     */   
/*     */   private boolean hasEqualsOrHashcode;
/*     */   
/*     */   private boolean hasDefaultConstructor;
/*     */   
/*     */   private HashSet<String> existingMethods;
/*     */   private HashSet<String> existingSuperMethods;
/*     */   private LinkedHashMap<String, FieldMeta> fields;
/*     */   private HashSet<String> classAnnotation;
/*     */   private AnnotationInfo annotationInfo;
/*     */   private ArrayList<MethodMeta> methodMetaList;
/*     */   private final EnhanceContext enhanceContext;
/*     */   
/*     */   public ClassMeta(EnhanceContext enhanceContext, boolean subclassing, int logLevel, PrintStream logout) {
/*  61 */     this.existingMethods = new HashSet();
/*     */     
/*  63 */     this.existingSuperMethods = new HashSet();
/*     */     
/*  65 */     this.fields = new LinkedHashMap();
/*     */     
/*  67 */     this.classAnnotation = new HashSet();
/*     */     
/*  69 */     this.annotationInfo = new AnnotationInfo(null);
/*     */     
/*  71 */     this.methodMetaList = new ArrayList();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  76 */     this.enhanceContext = enhanceContext;
/*  77 */     this.subclassing = subclassing;
/*  78 */     this.logLevel = logLevel;
/*  79 */     this.logout = logout;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  86 */   public EnhanceContext getEnhanceContext() { return this.enhanceContext; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  93 */   public Set<String> getClassAnnotations() { return this.classAnnotation; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 101 */   public AnnotationInfo getAnnotationInfo() { return this.annotationInfo; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AnnotationInfo getInterfaceTransactionalInfo(String methodName, String methodDesc) {
/* 109 */     AnnotationInfo annotationInfo = null;
/*     */     
/* 111 */     for (int i = 0; i < this.methodMetaList.size(); i++) {
/* 112 */       MethodMeta meta = (MethodMeta)this.methodMetaList.get(i);
/* 113 */       if (meta.isMatch(methodName, methodDesc)) {
/* 114 */         if (annotationInfo != null) {
/* 115 */           String msg = "Error in [" + this.className + "] searching the transactional methods[" + this.methodMetaList + "] found more than one match for the transactional method:" + methodName + " " + methodDesc;
/*     */ 
/*     */ 
/*     */           
/* 119 */           logger.log(Level.SEVERE, msg);
/* 120 */           log(msg);
/*     */         } else {
/*     */           
/* 123 */           annotationInfo = meta.getAnnotationInfo();
/* 124 */           if (isLog(9)) {
/* 125 */             log("... found transactional info from interface " + this.className + " " + methodName + " " + methodDesc);
/*     */           }
/*     */         } 
/*     */       }
/*     */     } 
/*     */     
/* 131 */     return annotationInfo;
/*     */   }
/*     */   
/*     */   public boolean isCheckSuperClassForEntity() {
/* 135 */     if (isEntity())
/*     */     {
/* 137 */       return !this.superClassName.equals(OBJECT_CLASS);
/*     */     }
/* 139 */     return false;
/*     */   }
/*     */ 
/*     */   
/* 143 */   public String toString() { return this.className; }
/*     */ 
/*     */   
/*     */   public boolean isTransactional() {
/* 147 */     if (this.classAnnotation.contains("Lcom/avaje/ebean/annotation/Transactional;")) {
/* 148 */       return true;
/*     */     }
/* 150 */     return false;
/*     */   }
/*     */ 
/*     */   
/* 154 */   public ArrayList<MethodMeta> getMethodMeta() { return this.methodMetaList; }
/*     */ 
/*     */   
/*     */   public void setClassName(String className, String superClassName) {
/* 158 */     this.className = className;
/* 159 */     this.superClassName = superClassName;
/*     */   }
/*     */ 
/*     */   
/* 163 */   public String getSuperClassName() { return this.superClassName; }
/*     */ 
/*     */ 
/*     */   
/* 167 */   public boolean isSubclassing() { return this.subclassing; }
/*     */ 
/*     */ 
/*     */   
/* 171 */   public boolean isLog(int level) { return (level <= this.logLevel); }
/*     */ 
/*     */   
/*     */   public void log(String msg) {
/* 175 */     if (this.className != null) {
/* 176 */       msg = "cls: " + this.className + "  msg: " + msg;
/*     */     }
/* 178 */     this.logout.println("transform> " + msg);
/*     */   }
/*     */   
/*     */   public void logEnhanced() {
/* 182 */     String m = "enhanced ";
/* 183 */     if (hasScalaInterface()) {
/* 184 */       m = m + " (scala)";
/*     */     }
/* 186 */     if (hasGroovyInterface()) {
/* 187 */       m = m + " (groovy)";
/*     */     }
/* 189 */     log(m);
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
/* 201 */   public boolean isInheritEqualsFromSuper() { return (!this.subclassing && isSuperClassEntity()); }
/*     */ 
/*     */ 
/*     */   
/* 205 */   public ClassMeta getSuperMeta() { return this.superMeta; }
/*     */ 
/*     */ 
/*     */   
/* 209 */   public void setSuperMeta(ClassMeta superMeta) { this.superMeta = superMeta; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 216 */   public void setHasEqualsOrHashcode(boolean hasEqualsOrHashcode) { this.hasEqualsOrHashcode = hasEqualsOrHashcode; }
/*     */ 
/*     */ 
/*     */   
/* 220 */   public boolean hasEqualsOrHashCode() { return this.hasEqualsOrHashcode; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isFieldPersistent(String fieldName) {
/* 228 */     FieldMeta f = (FieldMeta)this.fields.get(fieldName);
/* 229 */     if (f != null) {
/* 230 */       return f.isPersistent();
/*     */     }
/* 232 */     if (this.superMeta == null)
/*     */     {
/* 234 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 238 */     return this.superMeta.isFieldPersistent(fieldName);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<FieldMeta> getLocalFields() {
/* 247 */     ArrayList<FieldMeta> list = new ArrayList<FieldMeta>();
/*     */     
/* 249 */     Iterator<FieldMeta> it = this.fields.values().iterator();
/* 250 */     while (it.hasNext()) {
/* 251 */       FieldMeta fm = (FieldMeta)it.next();
/* 252 */       if (!fm.isObjectArray())
/*     */       {
/* 254 */         list.add(fm);
/*     */       }
/*     */     } 
/*     */     
/* 258 */     return list;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 266 */   public List<FieldMeta> getInheritedFields() { return getInheritedFields(new ArrayList()); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private List<FieldMeta> getInheritedFields(List<FieldMeta> list) {
/* 274 */     if (list == null) {
/* 275 */       list = new ArrayList<FieldMeta>();
/*     */     }
/*     */     
/* 278 */     if (this.superMeta != null) {
/* 279 */       this.superMeta.addFieldsForInheritance(list);
/*     */     }
/* 281 */     return list;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void addFieldsForInheritance(List<FieldMeta> list) {
/* 288 */     if (isEntity()) {
/* 289 */       list.addAll(0, this.fields.values());
/* 290 */       if (this.superMeta != null) {
/* 291 */         this.superMeta.addFieldsForInheritance(list);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<FieldMeta> getAllFields() {
/* 302 */     List<FieldMeta> list = getLocalFields();
/* 303 */     getInheritedFields(list);
/*     */     
/* 305 */     return list;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addFieldGetSetMethods(ClassVisitor cv) {
/* 312 */     if (isEntityEnhancementRequired()) {
/*     */       
/* 314 */       Iterator<FieldMeta> it = this.fields.values().iterator();
/* 315 */       while (it.hasNext()) {
/* 316 */         FieldMeta fm = (FieldMeta)it.next();
/* 317 */         fm.addGetSetMethods(cv, this);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isEntity() {
/* 326 */     if (this.classAnnotation.contains("Ljavax/persistence/Entity;")) {
/* 327 */       return true;
/*     */     }
/* 329 */     if (this.classAnnotation.contains("Ljavax/persistence/Embeddable;")) {
/* 330 */       return true;
/*     */     }
/* 332 */     if (this.classAnnotation.contains("Ljavax/persistence/MappedSuperclass;")) {
/* 333 */       return true;
/*     */     }
/* 335 */     if (this.classAnnotation.contains("Lcom/avaje/ebean/annotation/LdapDomain;")) {
/* 336 */       return true;
/*     */     }
/* 338 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isEntityEnhancementRequired() {
/* 345 */     if (this.alreadyEnhanced) {
/* 346 */       return false;
/*     */     }
/* 348 */     if (isEntity()) {
/* 349 */       return true;
/*     */     }
/* 351 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 358 */   public String getClassName() { return this.className; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isSuperClassEntity() {
/* 365 */     if (this.superMeta == null) {
/* 366 */       return false;
/*     */     }
/* 368 */     return this.superMeta.isEntity();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 376 */   public void addClassAnnotation(String desc) { this.classAnnotation.add(desc); }
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
/* 387 */   public void addExistingSuperMethod(String methodName, String methodDesc) { this.existingSuperMethods.add(methodName + methodDesc); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 394 */   public void addExistingMethod(String methodName, String methodDesc) { this.existingMethods.add(methodName + methodDesc); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 401 */   public boolean isExistingMethod(String methodName, String methodDesc) { return this.existingMethods.contains(methodName + methodDesc); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 409 */   public boolean isExistingSuperMethod(String methodName, String methodDesc) { return this.existingSuperMethods.contains(methodName + methodDesc); }
/*     */ 
/*     */ 
/*     */   
/*     */   public MethodVisitor createMethodVisitor(MethodVisitor mv, int access, String name, String desc) {
/* 414 */     MethodMeta methodMeta = new MethodMeta(this.annotationInfo, access, name, desc);
/* 415 */     this.methodMetaList.add(methodMeta);
/*     */     
/* 417 */     return new MethodReader(mv, methodMeta);
/*     */   }
/*     */   
/*     */   private static final class MethodReader extends EmptyVisitor {
/*     */     final MethodVisitor mv;
/*     */     final MethodMeta methodMeta;
/*     */     
/*     */     MethodReader(MethodVisitor mv, MethodMeta methodMeta) {
/* 425 */       this.mv = mv;
/* 426 */       this.methodMeta = methodMeta;
/*     */     }
/*     */     
/*     */     public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
/* 430 */       AnnotationVisitor av = this.mv.visitAnnotation(desc, visible);
/*     */       
/* 432 */       return new AnnotationInfoVisitor(null, this.methodMeta.annotationInfo, av);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 441 */   public FieldVisitor createLocalFieldVisitor(String name, String desc) { return createLocalFieldVisitor(null, null, name, desc); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FieldVisitor createLocalFieldVisitor(ClassVisitor cv, FieldVisitor fv, String name, String desc) {
/* 449 */     String fieldClass = this.subclassing ? this.superClassName : this.className;
/* 450 */     FieldMeta fieldMeta = new FieldMeta(this, name, desc, fieldClass);
/* 451 */     LocalFieldVisitor localField = new LocalFieldVisitor(cv, fv, fieldMeta);
/* 452 */     if (name.startsWith("_ebean")) {
/*     */ 
/*     */       
/* 455 */       if (isLog(0)) {
/* 456 */         log("... ignore field " + name);
/*     */       }
/*     */     } else {
/* 459 */       this.fields.put(localField.getName(), fieldMeta);
/*     */     } 
/* 461 */     return localField;
/*     */   }
/*     */ 
/*     */   
/* 465 */   public boolean isAlreadyEnhanced() { return this.alreadyEnhanced; }
/*     */ 
/*     */ 
/*     */   
/* 469 */   public void setAlreadyEnhanced(boolean alreadyEnhanced) { this.alreadyEnhanced = alreadyEnhanced; }
/*     */ 
/*     */ 
/*     */   
/* 473 */   public boolean hasDefaultConstructor() { return this.hasDefaultConstructor; }
/*     */ 
/*     */ 
/*     */   
/* 477 */   public void setHasDefaultConstructor(boolean hasDefaultConstructor) { this.hasDefaultConstructor = hasDefaultConstructor; }
/*     */ 
/*     */   
/*     */   public String getDescription() {
/* 481 */     StringBuilder sb = new StringBuilder();
/* 482 */     appendDescription(sb);
/* 483 */     return sb.toString();
/*     */   }
/*     */   
/*     */   private void appendDescription(StringBuilder sb) {
/* 487 */     sb.append(this.className);
/* 488 */     if (this.superMeta != null) {
/* 489 */       sb.append(" : ");
/* 490 */       this.superMeta.appendDescription(sb);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/* 495 */   public boolean hasScalaInterface() { return this.hasScalaInterface; }
/*     */ 
/*     */ 
/*     */   
/* 499 */   public void setScalaInterface(boolean hasScalaInterface) { this.hasScalaInterface = hasScalaInterface; }
/*     */ 
/*     */ 
/*     */   
/* 503 */   public boolean hasEntityBeanInterface() { return this.hasEntityBeanInterface; }
/*     */ 
/*     */ 
/*     */   
/* 507 */   public void setEntityBeanInterface(boolean hasEntityBeanInterface) { this.hasEntityBeanInterface = hasEntityBeanInterface; }
/*     */ 
/*     */ 
/*     */   
/* 511 */   public boolean hasGroovyInterface() { return this.hasGroovyInterface; }
/*     */ 
/*     */ 
/*     */   
/* 515 */   public void setGroovyInterface(boolean hasGroovyInterface) { this.hasGroovyInterface = hasGroovyInterface; }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebean\enhance\agent\ClassMeta.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */