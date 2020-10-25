/*     */ package com.avaje.ebean.enhance.agent;
/*     */ 
/*     */ import com.avaje.ebean.enhance.asm.ClassVisitor;
/*     */ import com.avaje.ebean.enhance.asm.Label;
/*     */ import com.avaje.ebean.enhance.asm.MethodVisitor;
/*     */ import com.avaje.ebean.enhance.asm.Opcodes;
/*     */ import com.avaje.ebean.enhance.asm.Type;
/*     */ import java.util.HashSet;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class FieldMeta
/*     */   implements Opcodes, EnhanceConstants
/*     */ {
/*  19 */   private static final Type BOOLEAN_OBJECT_TYPE = Type.getType(Boolean.class); private final ClassMeta classMeta; private final String fieldClass; private final String fieldName; private final String fieldDesc;
/*     */   private final HashSet<String> annotations;
/*     */   private final Type asmType;
/*     */   private final boolean primativeType;
/*     */   private final boolean objectType;
/*     */   
/*     */   public FieldMeta(ClassMeta classMeta, String name, String desc, String fieldClass) {
/*  26 */     this.annotations = new HashSet();
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
/*  50 */     this.classMeta = classMeta;
/*  51 */     this.fieldName = name;
/*  52 */     this.fieldDesc = desc;
/*  53 */     this.fieldClass = fieldClass;
/*     */     
/*  55 */     this.asmType = Type.getType(desc);
/*     */     
/*  57 */     int sort = this.asmType.getSort();
/*  58 */     this.primativeType = (sort > 0 && sort <= 8);
/*  59 */     this.objectType = (sort == 10);
/*     */     
/*  61 */     this.getMethodName = "_ebean_get_" + name;
/*  62 */     this.getMethodDesc = "()" + desc;
/*     */     
/*  64 */     this.setMethodName = "_ebean_set_" + name;
/*  65 */     this.setMethodDesc = "(" + desc + ")V";
/*     */     
/*  67 */     this.getNoInterceptMethodName = "_ebean_getni_" + name;
/*  68 */     this.setNoInterceptMethodName = "_ebean_setni_" + name;
/*     */     
/*  70 */     if (classMeta != null && classMeta.hasScalaInterface()) {
/*     */       
/*  72 */       this.publicSetterName = name + "_$eq";
/*  73 */       this.publicGetterName = name;
/*     */     } else {
/*     */       
/*  76 */       String publicFieldName = getFieldName(name, this.asmType);
/*     */       
/*  78 */       String initCap = Character.toUpperCase(publicFieldName.charAt(0)) + publicFieldName.substring(1);
/*  79 */       this.publicSetterName = "set" + initCap;
/*     */       
/*  81 */       if (this.fieldDesc.equals("Z")) {
/*  82 */         this.publicGetterName = "is" + initCap;
/*     */       } else {
/*  84 */         this.publicGetterName = "get" + initCap;
/*     */       } 
/*     */     } 
/*     */     
/*  88 */     if (classMeta != null && classMeta.isLog(6)) {
/*  89 */       classMeta.log(" ... public getter [" + this.publicGetterName + "]");
/*  90 */       classMeta.log(" ... public setter [" + this.publicSetterName + "]");
/*     */     } 
/*     */   }
/*     */   private final String getMethodName; private final String getMethodDesc;
/*     */   private final String setMethodName;
/*     */   private final String setMethodDesc;
/*     */   
/*     */   private String getFieldName(String name, Type asmType) {
/*  98 */     if ((BOOLEAN_OBJECT_TYPE.equals(asmType) || Type.BOOLEAN_TYPE.equals(asmType)) && name.startsWith("is") && name.length() > 2) {
/*     */ 
/*     */ 
/*     */       
/* 102 */       char c = name.charAt(2);
/* 103 */       if (Character.isUpperCase(c)) {
/* 104 */         if (this.classMeta.isLog(6)) {
/* 105 */           this.classMeta.log("trimming off \"is\" from boolean field name " + name + "]");
/*     */         }
/* 107 */         return name.substring(2);
/*     */       } 
/*     */     } 
/* 110 */     return name;
/*     */   }
/*     */   private final String getNoInterceptMethodName; private final String setNoInterceptMethodName; private final String publicSetterName; private final String publicGetterName;
/*     */   
/* 114 */   public String toString() { return this.fieldName; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 121 */   public String getFieldName() { return this.fieldName; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 128 */   public boolean isPrimativeType() { return this.primativeType; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 138 */   public String getPublicGetterName() { return this.publicGetterName; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 148 */   public String getPublicSetterName() { return this.publicSetterName; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 156 */   public boolean isPersistentSetter(String methodDesc) { return (this.setMethodDesc.equals(methodDesc) && isInterceptSet()); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 164 */   public boolean isPersistentGetter(String methodDesc) { return (this.getMethodDesc.equals(methodDesc) && isInterceptGet()); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 171 */   protected void addAnnotationDesc(String desc) { this.annotations.add(desc); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 178 */   public String getName() { return this.fieldName; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 185 */   public String getDesc() { return this.fieldDesc; }
/*     */ 
/*     */   
/*     */   private boolean isInterceptGet() {
/* 189 */     if (isId()) {
/* 190 */       return false;
/*     */     }
/* 192 */     if (isTransient()) {
/* 193 */       return false;
/*     */     }
/* 195 */     if (isMany()) {
/* 196 */       return true;
/*     */     }
/* 198 */     return true;
/*     */   }
/*     */   
/*     */   private boolean isInterceptSet() {
/* 202 */     if (isId()) {
/* 203 */       return false;
/*     */     }
/* 205 */     if (isTransient()) {
/* 206 */       return false;
/*     */     }
/* 208 */     if (isMany()) {
/* 209 */       return false;
/*     */     }
/* 211 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isObjectArray() {
/* 221 */     if (this.fieldDesc.charAt(0) == '[' && 
/* 222 */       this.fieldDesc.length() > 2) {
/* 223 */       if (!isTransient()) {
/* 224 */         System.err.println("ERROR: We can not support Object Arrays... for field: " + this.fieldName);
/*     */       }
/* 226 */       return true;
/*     */     } 
/*     */     
/* 229 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 236 */   public boolean isPersistent() { return !isTransient(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 243 */   public boolean isTransient() { return this.annotations.contains("Ljavax/persistence/Transient;"); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 253 */   public boolean isId() { return (this.annotations.contains("Ljavax/persistence/Id;") || this.annotations.contains("Ljavax/persistence/EmbeddedId;")); }
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
/* 264 */   public boolean isMany() { return (this.annotations.contains("Ljavax/persistence/OneToMany;") || this.annotations.contains("Ljavax/persistence/ManyToMany;")); }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 269 */   public boolean isManyToMany() { return this.annotations.contains("Ljavax/persistence/ManyToMany;"); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 276 */   public boolean isEmbedded() { return (this.annotations.contains("Ljavax/persistence/Embedded;") || this.annotations.contains("Lcom/avaje/ebean/annotation/EmbeddedColumns;")); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 285 */   private boolean isLocalField(ClassMeta classMeta) { return this.fieldClass.equals(classMeta.getClassName()); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void appendGetPrimitiveIdValue(MethodVisitor mv, ClassMeta classMeta) {
/* 292 */     if (classMeta.isSubclassing()) {
/* 293 */       mv.visitMethodInsn(182, classMeta.getSuperClassName(), this.publicGetterName, this.getMethodDesc);
/*     */     } else {
/* 295 */       mv.visitMethodInsn(182, classMeta.getClassName(), this.getMethodName, this.getMethodDesc);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void appendCompare(MethodVisitor mv, ClassMeta classMeta) {
/* 303 */     if (this.primativeType) {
/* 304 */       if (classMeta.isLog(4)) {
/* 305 */         classMeta.log(" ... getIdentity compare primitive field[" + this.fieldName + "] type[" + this.fieldDesc + "]");
/*     */       }
/* 307 */       if (this.fieldDesc.equals("J")) {
/*     */         
/* 309 */         mv.visitInsn(9);
/* 310 */         mv.visitInsn(148);
/*     */       }
/* 312 */       else if (this.fieldDesc.equals("D")) {
/*     */         
/* 314 */         mv.visitInsn(14);
/* 315 */         mv.visitInsn(151);
/*     */       }
/* 317 */       else if (this.fieldDesc.equals("F")) {
/*     */         
/* 319 */         mv.visitInsn(11);
/* 320 */         mv.visitInsn(149);
/*     */       } 
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
/*     */   
/*     */   public void appendValueOf(MethodVisitor mv, ClassMeta classMeta) {
/* 336 */     if (this.primativeType) {
/*     */       
/* 338 */       Type objectWrapperType = PrimitiveHelper.getObjectWrapper(this.asmType);
/*     */       
/* 340 */       String objDesc = objectWrapperType.getInternalName();
/* 341 */       String primDesc = this.asmType.getDescriptor();
/*     */       
/* 343 */       mv.visitMethodInsn(184, objDesc, "valueOf", "(" + primDesc + ")L" + objDesc + ";");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addFieldCopy(MethodVisitor mv, ClassMeta classMeta) {
/* 352 */     if (classMeta.isSubclassing()) {
/* 353 */       String copyClassName = classMeta.getSuperClassName();
/* 354 */       mv.visitMethodInsn(183, copyClassName, this.publicGetterName, this.getMethodDesc);
/* 355 */       mv.visitMethodInsn(182, copyClassName, this.publicSetterName, this.setMethodDesc);
/*     */     }
/* 357 */     else if (isLocalField(classMeta)) {
/* 358 */       mv.visitFieldInsn(180, this.fieldClass, this.fieldName, this.fieldDesc);
/* 359 */       mv.visitFieldInsn(181, this.fieldClass, this.fieldName, this.fieldDesc);
/*     */     } else {
/* 361 */       if (classMeta.isLog(4)) {
/* 362 */         classMeta.log(" ... addFieldCopy on non-local field [" + this.fieldName + "] type[" + this.fieldDesc + "]");
/*     */       }
/* 364 */       mv.visitMethodInsn(182, classMeta.getClassName(), this.getNoInterceptMethodName, this.getMethodDesc);
/* 365 */       mv.visitMethodInsn(182, classMeta.getClassName(), this.setNoInterceptMethodName, this.setMethodDesc);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void appendSwitchGet(MethodVisitor mv, ClassMeta classMeta, boolean intercept) {
/* 375 */     if (classMeta.isSubclassing()) {
/*     */       
/* 377 */       if (intercept) {
/*     */         
/* 379 */         mv.visitMethodInsn(182, classMeta.getClassName(), this.publicGetterName, this.getMethodDesc);
/*     */       } else {
/*     */         
/* 382 */         mv.visitMethodInsn(183, classMeta.getSuperClassName(), this.publicGetterName, this.getMethodDesc);
/*     */       }
/*     */     
/* 385 */     } else if (intercept) {
/*     */       
/* 387 */       mv.visitMethodInsn(182, classMeta.getClassName(), this.getMethodName, this.getMethodDesc);
/*     */     }
/* 389 */     else if (isLocalField(classMeta)) {
/* 390 */       mv.visitFieldInsn(180, classMeta.getClassName(), this.fieldName, this.fieldDesc);
/*     */     } else {
/*     */       
/* 393 */       mv.visitMethodInsn(182, classMeta.getClassName(), this.getNoInterceptMethodName, this.getMethodDesc);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 398 */     if (this.primativeType) {
/* 399 */       appendValueOf(mv, classMeta);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void appendSwitchSet(MethodVisitor mv, ClassMeta classMeta, boolean intercept) {
/* 405 */     if (this.primativeType) {
/*     */       
/* 407 */       Type objectWrapperType = PrimitiveHelper.getObjectWrapper(this.asmType);
/*     */       
/* 409 */       String primDesc = this.asmType.getDescriptor();
/* 410 */       String primType = this.asmType.getClassName();
/* 411 */       String objInt = objectWrapperType.getInternalName();
/* 412 */       mv.visitTypeInsn(192, objInt);
/*     */       
/* 414 */       mv.visitMethodInsn(182, objInt, primType + "Value", "()" + primDesc);
/*     */     } else {
/*     */       
/* 417 */       mv.visitTypeInsn(192, this.asmType.getInternalName());
/*     */     } 
/*     */     
/* 420 */     if (classMeta.isSubclassing()) {
/* 421 */       if (intercept) {
/*     */         
/* 423 */         mv.visitMethodInsn(182, classMeta.getClassName(), this.publicSetterName, this.setMethodDesc);
/*     */       } else {
/*     */         
/* 426 */         mv.visitMethodInsn(183, classMeta.getSuperClassName(), this.publicSetterName, this.setMethodDesc);
/*     */       }
/*     */     
/*     */     }
/* 430 */     else if (intercept) {
/*     */       
/* 432 */       mv.visitMethodInsn(182, classMeta.getClassName(), this.setMethodName, this.setMethodDesc);
/*     */     
/*     */     }
/* 435 */     else if (isLocalField(classMeta)) {
/* 436 */       mv.visitFieldInsn(181, this.fieldClass, this.fieldName, this.fieldDesc);
/*     */     } else {
/* 438 */       mv.visitMethodInsn(182, classMeta.getClassName(), this.setNoInterceptMethodName, this.setMethodDesc);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addPublicGetSetMethods(ClassVisitor cv, ClassMeta classMeta, boolean checkExisting) {
/* 449 */     if (isPersistent())
/*     */     {
/* 451 */       if (isId()) {
/*     */ 
/*     */         
/* 454 */         addPublicSetMethod(cv, classMeta, checkExisting);
/*     */       } else {
/*     */         
/* 457 */         addPublicGetMethod(cv, classMeta, checkExisting);
/* 458 */         addPublicSetMethod(cv, classMeta, checkExisting);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private void addPublicGetMethod(ClassVisitor cv, ClassMeta classMeta, boolean checkExisting) {
/* 465 */     if (checkExisting && !classMeta.isExistingSuperMethod(this.publicGetterName, this.getMethodDesc)) {
/* 466 */       if (classMeta.isLog(1)) {
/* 467 */         classMeta.log("excluding " + this.publicGetterName + " as not on super object");
/*     */       }
/*     */       
/*     */       return;
/*     */     } 
/* 472 */     addPublicGetMethod(new VisitMethodParams(cv, true, this.publicGetterName, this.getMethodDesc, null, null), classMeta);
/*     */   }
/*     */ 
/*     */   
/*     */   private void addPublicGetMethod(VisitMethodParams params, ClassMeta classMeta) {
/* 477 */     MethodVisitor mv = params.visitMethod();
/* 478 */     int iReturnOpcode = this.asmType.getOpcode(172);
/*     */     
/* 480 */     mv.visitCode();
/* 481 */     Label l0 = new Label();
/* 482 */     mv.visitLabel(l0);
/* 483 */     mv.visitLineNumber(1, l0);
/* 484 */     mv.visitVarInsn(25, 0);
/* 485 */     mv.visitFieldInsn(180, classMeta.getClassName(), "_ebean_intercept", "Lcom/avaje/ebean/bean/EntityBeanIntercept;");
/* 486 */     mv.visitLdcInsn(this.fieldName);
/* 487 */     mv.visitMethodInsn(182, "com/avaje/ebean/bean/EntityBeanIntercept", "preGetter", "(Ljava/lang/String;)V");
/* 488 */     Label l1 = new Label();
/* 489 */     mv.visitLabel(l1);
/* 490 */     mv.visitLineNumber(1, l1);
/* 491 */     mv.visitVarInsn(25, 0);
/* 492 */     mv.visitMethodInsn(183, classMeta.getSuperClassName(), params.getName(), params.getDesc());
/* 493 */     mv.visitInsn(iReturnOpcode);
/* 494 */     Label l2 = new Label();
/* 495 */     mv.visitLabel(l2);
/* 496 */     mv.visitLocalVariable("this", "L" + classMeta.getClassName() + ";", null, l0, l2, 0);
/* 497 */     mv.visitMaxs(2, 1);
/* 498 */     mv.visitEnd();
/*     */   }
/*     */ 
/*     */   
/*     */   private void addPublicSetMethod(ClassVisitor cv, ClassMeta classMeta, boolean checkExisting) {
/* 503 */     if (checkExisting && !classMeta.isExistingSuperMethod(this.publicSetterName, this.setMethodDesc)) {
/* 504 */       if (classMeta.isLog(1)) {
/* 505 */         classMeta.log("excluding " + this.publicSetterName + " as not on super object");
/*     */       }
/*     */       
/*     */       return;
/*     */     } 
/* 510 */     addPublicSetMethod(new VisitMethodParams(cv, true, this.publicSetterName, this.setMethodDesc, null, null), classMeta);
/*     */   }
/*     */ 
/*     */   
/*     */   private void addPublicSetMethod(VisitMethodParams params, ClassMeta classMeta) {
/* 515 */     MethodVisitor mv = params.visitMethod();
/*     */     
/* 517 */     String publicGetterName = getPublicGetterName();
/*     */     
/* 519 */     String preSetterArgTypes = "Ljava/lang/Object;Ljava/lang/Object;";
/* 520 */     if (this.primativeType)
/*     */     {
/* 522 */       preSetterArgTypes = this.fieldDesc + this.fieldDesc;
/*     */     }
/*     */ 
/*     */     
/* 526 */     int iLoadOpcode = this.asmType.getOpcode(21);
/*     */ 
/*     */     
/* 529 */     int iPosition = this.asmType.getSize();
/*     */ 
/*     */     
/* 532 */     String className = classMeta.getClassName();
/* 533 */     String superClassName = classMeta.getSuperClassName();
/*     */     
/* 535 */     mv.visitCode();
/* 536 */     Label l0 = new Label();
/* 537 */     mv.visitLabel(l0);
/* 538 */     mv.visitLineNumber(1, l0);
/* 539 */     mv.visitVarInsn(25, 0);
/* 540 */     mv.visitFieldInsn(180, className, "_ebean_intercept", "Lcom/avaje/ebean/bean/EntityBeanIntercept;");
/* 541 */     if (isInterceptSet()) {
/* 542 */       mv.visitInsn(4);
/*     */     } else {
/*     */       
/* 545 */       mv.visitInsn(3);
/*     */     } 
/*     */     
/* 548 */     String preSetterMethod = "preSetter";
/* 549 */     if (isMany()) {
/* 550 */       preSetterMethod = "preSetterMany";
/*     */     }
/*     */     
/* 553 */     mv.visitLdcInsn(this.fieldName);
/* 554 */     mv.visitVarInsn(25, 0);
/* 555 */     mv.visitMethodInsn(182, className, publicGetterName, this.getMethodDesc);
/* 556 */     mv.visitVarInsn(iLoadOpcode, 1);
/* 557 */     mv.visitMethodInsn(182, "com/avaje/ebean/bean/EntityBeanIntercept", preSetterMethod, "(ZLjava/lang/String;" + preSetterArgTypes + ")Ljava/beans/PropertyChangeEvent;");
/* 558 */     mv.visitVarInsn(58, 1 + iPosition);
/*     */     
/* 560 */     Label l1 = new Label();
/* 561 */     mv.visitLabel(l1);
/* 562 */     mv.visitLineNumber(1, l1);
/* 563 */     mv.visitVarInsn(25, 0);
/* 564 */     mv.visitVarInsn(iLoadOpcode, 1);
/* 565 */     mv.visitMethodInsn(183, superClassName, params.getName(), params.getDesc());
/*     */     
/* 567 */     Label levt = new Label();
/* 568 */     mv.visitLabel(levt);
/* 569 */     mv.visitLineNumber(3, levt);
/*     */     
/* 571 */     mv.visitVarInsn(25, 0);
/* 572 */     mv.visitFieldInsn(180, className, "_ebean_intercept", "Lcom/avaje/ebean/bean/EntityBeanIntercept;");
/* 573 */     mv.visitVarInsn(25, 1 + iPosition);
/* 574 */     mv.visitVarInsn(25, 0);
/* 575 */     mv.visitMethodInsn(183, superClassName, publicGetterName, this.getMethodDesc);
/* 576 */     if (this.primativeType) {
/* 577 */       appendValueOf(mv, classMeta);
/*     */     }
/* 579 */     mv.visitMethodInsn(182, "com/avaje/ebean/bean/EntityBeanIntercept", "postSetter", "(Ljava/beans/PropertyChangeEvent;Ljava/lang/Object;)V");
/*     */     
/* 581 */     Label l2 = new Label();
/* 582 */     mv.visitLabel(l2);
/* 583 */     mv.visitLineNumber(1, l2);
/* 584 */     mv.visitInsn(177);
/*     */     
/* 586 */     Label l3 = new Label();
/* 587 */     mv.visitLabel(l3);
/* 588 */     mv.visitLocalVariable("this", "L" + className + ";", null, l0, l3, 0);
/* 589 */     mv.visitLocalVariable("newValue", this.fieldDesc, null, l0, l3, 1);
/* 590 */     mv.visitLocalVariable("evt", "Ljava/beans/PropertyChangeEvent;", null, l1, l3, 2);
/* 591 */     mv.visitMaxs(5, 3);
/* 592 */     mv.visitEnd();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addGetSetMethods(ClassVisitor cv, ClassMeta classMeta) {
/* 601 */     if (!isLocalField(classMeta)) {
/* 602 */       String msg = "ERROR: " + this.fieldClass + " != " + classMeta.getClassName() + " for field " + this.fieldName + " " + this.fieldDesc;
/* 603 */       throw new RuntimeException(msg);
/*     */     } 
/*     */ 
/*     */     
/* 607 */     addGet(cv, classMeta);
/* 608 */     addSet(cv, classMeta);
/*     */ 
/*     */ 
/*     */     
/* 612 */     addGetNoIntercept(cv, classMeta);
/* 613 */     addSetNoIntercept(cv, classMeta);
/*     */   }
/*     */   
/*     */   private String getEbeanCollectionClass() {
/* 617 */     if (this.fieldDesc.equals("Ljava/util/List;")) {
/* 618 */       return "com/avaje/ebean/common/BeanList";
/*     */     }
/* 620 */     if (this.fieldDesc.equals("Ljava/util/Set;")) {
/* 621 */       return "com/avaje/ebean/common/BeanSet";
/*     */     }
/* 623 */     if (this.fieldDesc.equals("Ljava/util/Map;")) {
/* 624 */       return "com/avaje/ebean/common/BeanMap";
/*     */     }
/* 626 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isInterceptMany() {
/* 634 */     if (isMany() && !isTransient()) {
/*     */       
/* 636 */       String ebCollection = getEbeanCollectionClass();
/* 637 */       if (ebCollection != null) {
/* 638 */         return true;
/*     */       }
/* 640 */       this.classMeta.log("Error unepxected many type " + this.fieldDesc);
/*     */     } 
/*     */     
/* 643 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void addGet(ClassVisitor cw, ClassMeta classMeta) {
/* 651 */     if (classMeta.isLog(3)) {
/* 652 */       classMeta.log(this.getMethodName + " " + this.getMethodDesc + " intercept:" + isInterceptGet() + " " + this.annotations);
/*     */     }
/*     */     
/* 655 */     MethodVisitor mv = cw.visitMethod(4, this.getMethodName, this.getMethodDesc, null, null);
/* 656 */     mv.visitCode();
/*     */     
/* 658 */     if (isInterceptMany()) {
/* 659 */       addGetForMany(mv);
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 664 */     int iReturnOpcode = this.asmType.getOpcode(172);
/*     */     
/* 666 */     String className = classMeta.getClassName();
/*     */     
/* 668 */     Label labelEnd = new Label();
/* 669 */     Label labelStart = null;
/*     */     
/* 671 */     if (isInterceptGet()) {
/* 672 */       labelStart = new Label();
/* 673 */       mv.visitLabel(labelStart);
/* 674 */       mv.visitLineNumber(4, labelStart);
/* 675 */       mv.visitFrame(3, 0, null, 0, null);
/* 676 */       mv.visitVarInsn(25, 0);
/* 677 */       mv.visitFieldInsn(180, className, "_ebean_intercept", "Lcom/avaje/ebean/bean/EntityBeanIntercept;");
/* 678 */       mv.visitLdcInsn(this.fieldName);
/* 679 */       mv.visitMethodInsn(182, "com/avaje/ebean/bean/EntityBeanIntercept", "preGetter", "(Ljava/lang/String;)V");
/*     */     } 
/* 681 */     if (labelStart == null) {
/* 682 */       labelStart = labelEnd;
/*     */     }
/* 684 */     mv.visitLabel(labelEnd);
/* 685 */     mv.visitLineNumber(5, labelEnd);
/* 686 */     mv.visitFrame(3, 0, null, 0, null);
/* 687 */     mv.visitVarInsn(25, 0);
/* 688 */     mv.visitFieldInsn(180, className, this.fieldName, this.fieldDesc);
/* 689 */     mv.visitInsn(iReturnOpcode);
/* 690 */     Label labelEnd1 = new Label();
/* 691 */     mv.visitLabel(labelEnd1);
/* 692 */     mv.visitLocalVariable("this", "L" + className + ";", null, labelStart, labelEnd1, 0);
/* 693 */     mv.visitMaxs(2, 1);
/* 694 */     mv.visitEnd();
/*     */   }
/*     */ 
/*     */   
/*     */   private void addGetForMany(MethodVisitor mv) {
/* 699 */     String className = this.classMeta.getClassName();
/* 700 */     String ebCollection = getEbeanCollectionClass();
/*     */     
/* 702 */     Label l0 = new Label();
/* 703 */     mv.visitLabel(l0);
/* 704 */     mv.visitLineNumber(1, l0);
/* 705 */     mv.visitVarInsn(25, 0);
/* 706 */     mv.visitFieldInsn(180, className, "_ebean_intercept", "Lcom/avaje/ebean/bean/EntityBeanIntercept;");
/* 707 */     mv.visitLdcInsn(this.fieldName);
/* 708 */     mv.visitMethodInsn(182, "com/avaje/ebean/bean/EntityBeanIntercept", "preGetter", "(Ljava/lang/String;)V");
/*     */     
/* 710 */     Label l4 = new Label();
/* 711 */     if (this.classMeta.getEnhanceContext().isCheckNullManyFields()) {
/*     */       
/* 713 */       if (this.classMeta.isLog(3)) {
/* 714 */         this.classMeta.log("... add Many null check on " + this.fieldName + " ebtype:" + ebCollection);
/*     */       }
/*     */       
/* 717 */       Label l3 = new Label();
/* 718 */       mv.visitLabel(l3);
/* 719 */       mv.visitLineNumber(2, l3);
/* 720 */       mv.visitVarInsn(25, 0);
/* 721 */       mv.visitFieldInsn(180, className, this.fieldName, this.fieldDesc);
/*     */       
/* 723 */       mv.visitJumpInsn(199, l4);
/* 724 */       Label l5 = new Label();
/* 725 */       mv.visitLabel(l5);
/* 726 */       mv.visitLineNumber(3, l5);
/* 727 */       mv.visitVarInsn(25, 0);
/* 728 */       mv.visitTypeInsn(187, ebCollection);
/* 729 */       mv.visitInsn(89);
/* 730 */       mv.visitMethodInsn(183, ebCollection, "<init>", "()V");
/* 731 */       mv.visitFieldInsn(181, className, this.fieldName, this.fieldDesc);
/*     */       
/* 733 */       if (isManyToMany()) {
/*     */         
/* 735 */         if (this.classMeta.isLog(3)) {
/* 736 */           this.classMeta.log("... add ManyToMany modify listening to " + this.fieldName);
/*     */         }
/*     */         
/* 739 */         Label l6 = new Label();
/* 740 */         mv.visitLabel(l6);
/* 741 */         mv.visitLineNumber(4, l6);
/* 742 */         mv.visitVarInsn(25, 0);
/* 743 */         mv.visitFieldInsn(180, className, this.fieldName, this.fieldDesc);
/* 744 */         mv.visitTypeInsn(192, "com/avaje/ebean/bean/BeanCollection");
/* 745 */         mv.visitFieldInsn(178, "com/avaje/ebean/bean/BeanCollection$ModifyListenMode", "ALL", "Lcom/avaje/ebean/bean/BeanCollection$ModifyListenMode;");
/* 746 */         mv.visitMethodInsn(185, "com/avaje/ebean/bean/BeanCollection", "setModifyListening", "(Lcom/avaje/ebean/bean/BeanCollection$ModifyListenMode;)V");
/*     */       } 
/*     */     } 
/*     */     
/* 750 */     mv.visitLabel(l4);
/* 751 */     mv.visitLineNumber(5, l4);
/* 752 */     mv.visitFrame(3, 0, null, 0, null);
/* 753 */     mv.visitVarInsn(25, 0);
/* 754 */     mv.visitFieldInsn(180, className, this.fieldName, this.fieldDesc);
/* 755 */     mv.visitInsn(176);
/* 756 */     Label l7 = new Label();
/* 757 */     mv.visitLabel(l7);
/* 758 */     mv.visitLocalVariable("this", "L" + className + ";", null, l0, l7, 0);
/* 759 */     mv.visitMaxs(3, 1);
/* 760 */     mv.visitEnd();
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
/*     */   private void addGetNoIntercept(ClassVisitor cw, ClassMeta classMeta) {
/* 772 */     int iReturnOpcode = this.asmType.getOpcode(172);
/*     */     
/* 774 */     if (classMeta.isLog(3)) {
/* 775 */       classMeta.log(this.getNoInterceptMethodName + " " + this.getMethodDesc);
/*     */     }
/*     */     
/* 778 */     MethodVisitor mv = cw.visitMethod(4, this.getNoInterceptMethodName, this.getMethodDesc, null, null);
/* 779 */     mv.visitCode();
/*     */     
/* 781 */     Label l0 = new Label();
/* 782 */     mv.visitLabel(l0);
/* 783 */     mv.visitLineNumber(1, l0);
/* 784 */     mv.visitVarInsn(25, 0);
/* 785 */     mv.visitFieldInsn(180, this.fieldClass, this.fieldName, this.fieldDesc);
/* 786 */     mv.visitInsn(iReturnOpcode);
/* 787 */     Label l2 = new Label();
/* 788 */     mv.visitLabel(l2);
/* 789 */     mv.visitLocalVariable("this", "L" + this.fieldClass + ";", null, l0, l2, 0);
/* 790 */     mv.visitMaxs(2, 1);
/* 791 */     mv.visitEnd();
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
/*     */ 
/*     */ 
/*     */   
/*     */   private void addSet(ClassVisitor cw, ClassMeta classMeta) {
/* 807 */     String preSetterArgTypes = "Ljava/lang/Object;Ljava/lang/Object;";
/* 808 */     if (!this.objectType)
/*     */     {
/* 810 */       preSetterArgTypes = this.fieldDesc + this.fieldDesc;
/*     */     }
/*     */ 
/*     */     
/* 814 */     int iLoadOpcode = this.asmType.getOpcode(21);
/*     */ 
/*     */     
/* 817 */     int iPosition = this.asmType.getSize();
/*     */     
/* 819 */     if (classMeta.isLog(3)) {
/* 820 */       classMeta.log(this.setMethodName + " " + this.setMethodDesc + " intercept:" + isInterceptSet() + " opCode:" + iLoadOpcode + "," + iPosition + " preSetterArgTypes" + preSetterArgTypes);
/*     */     }
/*     */ 
/*     */     
/* 824 */     MethodVisitor mv = cw.visitMethod(4, this.setMethodName, this.setMethodDesc, null, null);
/* 825 */     mv.visitCode();
/*     */     
/* 827 */     Label l0 = new Label();
/* 828 */     mv.visitLabel(l0);
/* 829 */     mv.visitLineNumber(1, l0);
/* 830 */     mv.visitVarInsn(25, 0);
/* 831 */     mv.visitFieldInsn(180, this.fieldClass, "_ebean_intercept", "Lcom/avaje/ebean/bean/EntityBeanIntercept;");
/* 832 */     if (isInterceptSet()) {
/* 833 */       mv.visitInsn(4);
/*     */     } else {
/*     */       
/* 836 */       mv.visitInsn(3);
/*     */     } 
/* 838 */     mv.visitLdcInsn(this.fieldName);
/* 839 */     mv.visitVarInsn(25, 0);
/* 840 */     mv.visitMethodInsn(182, this.fieldClass, this.getMethodName, this.getMethodDesc);
/* 841 */     mv.visitVarInsn(iLoadOpcode, 1);
/* 842 */     String preSetterMethod = "preSetter";
/* 843 */     if (isMany()) {
/* 844 */       preSetterMethod = "preSetterMany";
/*     */     }
/* 846 */     mv.visitMethodInsn(182, "com/avaje/ebean/bean/EntityBeanIntercept", preSetterMethod, "(ZLjava/lang/String;" + preSetterArgTypes + ")Ljava/beans/PropertyChangeEvent;");
/* 847 */     mv.visitVarInsn(58, 1 + iPosition);
/* 848 */     Label l1 = new Label();
/* 849 */     mv.visitLabel(l1);
/* 850 */     mv.visitLineNumber(2, l1);
/* 851 */     mv.visitVarInsn(25, 0);
/* 852 */     mv.visitVarInsn(iLoadOpcode, 1);
/* 853 */     mv.visitFieldInsn(181, this.fieldClass, this.fieldName, this.fieldDesc);
/*     */     
/* 855 */     Label l2 = new Label();
/* 856 */     mv.visitLabel(l2);
/* 857 */     mv.visitLineNumber(3, l2);
/* 858 */     mv.visitVarInsn(25, 0);
/* 859 */     mv.visitFieldInsn(180, this.fieldClass, "_ebean_intercept", "Lcom/avaje/ebean/bean/EntityBeanIntercept;");
/* 860 */     mv.visitVarInsn(25, 1 + iPosition);
/* 861 */     mv.visitMethodInsn(182, "com/avaje/ebean/bean/EntityBeanIntercept", "postSetter", "(Ljava/beans/PropertyChangeEvent;)V");
/*     */     
/* 863 */     Label l3 = new Label();
/* 864 */     mv.visitLabel(l3);
/* 865 */     mv.visitLineNumber(4, l3);
/* 866 */     mv.visitInsn(177);
/* 867 */     Label l4 = new Label();
/* 868 */     mv.visitLabel(l4);
/* 869 */     mv.visitLocalVariable("this", "L" + this.fieldClass + ";", null, l0, l4, 0);
/* 870 */     mv.visitLocalVariable("newValue", this.fieldDesc, null, l0, l4, 1);
/* 871 */     mv.visitLocalVariable("evt", "Ljava/beans/PropertyChangeEvent;", null, l1, l4, 2);
/* 872 */     mv.visitMaxs(5, 3);
/* 873 */     mv.visitEnd();
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
/*     */   private void addSetNoIntercept(ClassVisitor cw, ClassMeta classMeta) {
/* 886 */     int iLoadOpcode = this.asmType.getOpcode(21);
/*     */ 
/*     */     
/* 889 */     int iPosition = this.asmType.getSize();
/*     */     
/* 891 */     if (classMeta.isLog(3)) {
/* 892 */       classMeta.log(this.setNoInterceptMethodName + " " + this.setMethodDesc + " opCode:" + iLoadOpcode + "," + iPosition);
/*     */     }
/*     */     
/* 895 */     MethodVisitor mv = cw.visitMethod(4, this.setNoInterceptMethodName, this.setMethodDesc, null, null);
/* 896 */     mv.visitCode();
/* 897 */     Label l0 = new Label();
/*     */ 
/*     */     
/* 900 */     mv.visitLabel(l0);
/* 901 */     mv.visitLineNumber(1, l0);
/* 902 */     mv.visitVarInsn(25, 0);
/* 903 */     mv.visitVarInsn(iLoadOpcode, 1);
/*     */     
/* 905 */     mv.visitFieldInsn(181, this.fieldClass, this.fieldName, this.fieldDesc);
/*     */     
/* 907 */     Label l2 = new Label();
/* 908 */     mv.visitLabel(l2);
/* 909 */     mv.visitLineNumber(1, l2);
/* 910 */     mv.visitInsn(177);
/* 911 */     Label l3 = new Label();
/* 912 */     mv.visitLabel(l3);
/* 913 */     mv.visitLocalVariable("this", "L" + this.fieldClass + ";", null, l0, l3, 0);
/* 914 */     mv.visitLocalVariable("_newValue", this.fieldDesc, null, l0, l3, 1);
/* 915 */     mv.visitMaxs(4, 2);
/* 916 */     mv.visitEnd();
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebean\enhance\agent\FieldMeta.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */