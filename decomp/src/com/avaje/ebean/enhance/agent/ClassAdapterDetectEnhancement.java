/*     */ package com.avaje.ebean.enhance.agent;
/*     */ 
/*     */ import com.avaje.ebean.enhance.asm.AnnotationVisitor;
/*     */ import com.avaje.ebean.enhance.asm.ClassAdapter;
/*     */ import com.avaje.ebean.enhance.asm.EmptyVisitor;
/*     */ import com.avaje.ebean.enhance.asm.FieldVisitor;
/*     */ import com.avaje.ebean.enhance.asm.MethodAdapter;
/*     */ import com.avaje.ebean.enhance.asm.MethodVisitor;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashSet;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ClassAdapterDetectEnhancement
/*     */   extends ClassAdapter
/*     */ {
/*     */   private final ClassLoader classLoader;
/*     */   private final EnhanceContext enhanceContext;
/*  24 */   private final HashSet<String> classAnnotation = new HashSet();
/*     */   
/*  26 */   private final ArrayList<DetectMethod> methods = new ArrayList();
/*     */   
/*     */   private String className;
/*     */   
/*     */   private boolean entity;
/*     */   
/*     */   private boolean entityInterface;
/*     */   
/*     */   private boolean entityField;
/*     */   
/*     */   private boolean transactional;
/*     */   
/*     */   private boolean enhancedTransactional;
/*     */   
/*     */   public ClassAdapterDetectEnhancement(ClassLoader classLoader, EnhanceContext context) {
/*  41 */     super(new EmptyVisitor());
/*  42 */     this.classLoader = classLoader;
/*  43 */     this.enhanceContext = context;
/*     */   }
/*     */ 
/*     */   
/*  47 */   public boolean isEntityOrTransactional() { return (this.entity || isTransactional()); }
/*     */ 
/*     */   
/*     */   public String getStatus() {
/*  51 */     String s = "class: " + this.className;
/*  52 */     if (isEntity()) {
/*  53 */       s = s + " entity:true  enhanced:" + this.entityField;
/*  54 */       s = "*" + s;
/*     */     }
/*  56 */     else if (isTransactional()) {
/*  57 */       s = s + " transactional:true  enhanced:" + this.enhancedTransactional;
/*  58 */       s = "*" + s;
/*     */     } else {
/*     */       
/*  61 */       s = " " + s;
/*     */     } 
/*  63 */     return s;
/*     */   }
/*     */ 
/*     */   
/*  67 */   public boolean isLog(int level) { return this.enhanceContext.isLog(level); }
/*     */ 
/*     */ 
/*     */   
/*  71 */   public void log(String msg) { this.enhanceContext.log(this.className, msg); }
/*     */ 
/*     */   
/*     */   public void log(int level, String msg) {
/*  75 */     if (isLog(level)) {
/*  76 */       log(msg);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*  81 */   public boolean isEnhancedEntity() { return this.entityField; }
/*     */ 
/*     */ 
/*     */   
/*  85 */   public boolean isEnhancedTransactional() { return this.enhancedTransactional; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  92 */   public boolean isEntity() { return this.entity; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isTransactional() {
/*  99 */     if (this.transactional)
/*     */     {
/*     */       
/* 102 */       return this.transactional;
/*     */     }
/*     */ 
/*     */     
/* 106 */     for (int i = 0; i < this.methods.size(); i++) {
/* 107 */       DetectMethod m = (DetectMethod)this.methods.get(i);
/* 108 */       if (m.isTransactional()) {
/* 109 */         return true;
/*     */       }
/*     */     } 
/* 112 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
/* 121 */     if ((access & 0x200) != 0) {
/* 122 */       throw new NoEnhancementRequiredException(name + " is an Interface");
/*     */     }
/*     */     
/* 125 */     this.className = name;
/*     */     
/* 127 */     for (int i = 0; i < interfaces.length; i++) {
/*     */       
/* 129 */       if (interfaces[i].equals("com/avaje/ebean/bean/EntityBean")) {
/* 130 */         this.entityInterface = true;
/* 131 */         this.entity = true;
/*     */       }
/* 133 */       else if (interfaces[i].equals("com/avaje/ebean/enhance/agent/EnhancedTransactional")) {
/* 134 */         this.enhancedTransactional = true;
/*     */       } else {
/*     */         
/* 137 */         ClassMeta intefaceMeta = this.enhanceContext.getInterfaceMeta(interfaces[i], this.classLoader);
/* 138 */         if (intefaceMeta != null && intefaceMeta.isTransactional()) {
/*     */           
/* 140 */           this.transactional = true;
/* 141 */           if (isLog(9)) {
/* 142 */             log("detected implements tranactional interface " + intefaceMeta);
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 148 */     if (isLog(2)) {
/* 149 */       log("interfaces:  entityInterface[" + this.entityInterface + "] transactional[" + this.enhancedTransactional + "]");
/*     */     }
/*     */     
/* 152 */     super.visit(version, access, name, signature, superName, interfaces);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
/* 160 */     if (isLog(8)) {
/* 161 */       log("visitAnnotation " + desc);
/*     */     }
/* 163 */     this.classAnnotation.add(desc);
/* 164 */     if (isEntityAnnotation(desc)) {
/*     */       
/* 166 */       if (isLog(5)) {
/* 167 */         log("found entity annotation " + desc);
/*     */       }
/* 169 */       this.entity = true;
/*     */     }
/* 171 */     else if (desc.equals("Lcom/avaje/ebean/annotation/Transactional;")) {
/*     */       
/* 173 */       if (isLog(5)) {
/* 174 */         log("found transactional annotation " + desc);
/*     */       }
/* 176 */       this.transactional = true;
/*     */     } 
/*     */     
/* 179 */     return super.visitAnnotation(desc, visible);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isEntityAnnotation(String desc) {
/* 187 */     if (desc.equals("Ljavax/persistence/Entity;")) {
/* 188 */       return true;
/*     */     }
/* 190 */     if (desc.equals("Ljavax/persistence/Embeddable;")) {
/* 191 */       return true;
/*     */     }
/* 193 */     if (desc.equals("Ljavax/persistence/MappedSuperclass;")) {
/* 194 */       return true;
/*     */     }
/*     */     
/* 197 */     return false;
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
/* 209 */     if (name.equals("_EBEAN_MARKER")) {
/* 210 */       if (!desc.equals("Ljava/lang/String;")) {
/* 211 */         String m = "Error: _EBEAN_MARKER field of wrong type? " + desc;
/* 212 */         log(m);
/*     */       } 
/* 214 */       return true;
/*     */     } 
/* 216 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FieldVisitor visitField(int access, String name, String desc, String signature, Object value) {
/* 223 */     if (isLog(8)) {
/* 224 */       log("visitField " + name + " " + value);
/*     */     }
/*     */     
/* 227 */     if ((access & 0x8) != 0)
/*     */     {
/* 229 */       if (isEbeanFieldMarker(name, desc, signature)) {
/* 230 */         this.entityField = true;
/* 231 */         if (isLog(1)) {
/* 232 */           log("Found ebean marker field " + name + " " + value);
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/* 237 */     return super.visitField(access, name, desc, signature, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
/* 247 */     if (isLog(9)) {
/* 248 */       log("visitMethod " + name + " " + desc);
/*     */     }
/*     */     
/* 251 */     MethodVisitor mv = super.visitMethod(access, name, desc, signature, exceptions);
/* 252 */     DetectMethod dmv = new DetectMethod(mv);
/*     */     
/* 254 */     this.methods.add(dmv);
/* 255 */     return dmv;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static class DetectMethod
/*     */     extends MethodAdapter
/*     */   {
/*     */     boolean transactional;
/*     */ 
/*     */     
/* 266 */     public DetectMethod(MethodVisitor mv) { super(mv); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 273 */     public boolean isTransactional() { return this.transactional; }
/*     */ 
/*     */ 
/*     */     
/*     */     public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
/* 278 */       if (desc.equals("Lcom/avaje/ebean/annotation/Transactional;")) {
/* 279 */         this.transactional = true;
/*     */       }
/* 281 */       return super.visitAnnotation(desc, visible);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebean\enhance\agent\ClassAdapterDetectEnhancement.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */