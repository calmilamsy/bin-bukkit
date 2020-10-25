/*     */ package com.avaje.ebean.enhance.agent;
/*     */ 
/*     */ import com.avaje.ebean.enhance.asm.ClassVisitor;
/*     */ import com.avaje.ebean.enhance.asm.Label;
/*     */ import com.avaje.ebean.enhance.asm.MethodVisitor;
/*     */ import com.avaje.ebean.enhance.asm.Opcodes;
/*     */ import java.util.List;
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
/*     */ public class IndexFieldWeaver
/*     */   implements Opcodes
/*     */ {
/*     */   public static void addMethods(ClassVisitor cv, ClassMeta classMeta) {
/*  39 */     List<FieldMeta> fields = classMeta.getAllFields();
/*  40 */     if (fields.size() == 0) {
/*  41 */       classMeta.log("Has no fields?");
/*     */       
/*     */       return;
/*     */     } 
/*  45 */     if (classMeta.isLog(3)) {
/*  46 */       classMeta.log("fields size:" + fields.size() + " " + fields.toString());
/*     */     }
/*     */     
/*  49 */     generateCreateCopy(cv, classMeta, fields);
/*  50 */     generateGetField(cv, classMeta, fields, false);
/*  51 */     generateGetField(cv, classMeta, fields, true);
/*     */     
/*  53 */     generateSetField(cv, classMeta, fields, false);
/*  54 */     generateSetField(cv, classMeta, fields, true);
/*     */     
/*  56 */     generateGetDesc(cv, classMeta, fields);
/*     */     
/*  58 */     if (classMeta.hasEqualsOrHashCode()) {
/*     */       
/*  60 */       if (classMeta.isLog(1)) {
/*  61 */         classMeta.log("... skipping add equals() ... already has equals() hashcode() methods");
/*     */       }
/*     */       return;
/*     */     } 
/*  65 */     if (classMeta.isInheritEqualsFromSuper()) {
/*     */       
/*  67 */       if (classMeta.isLog(1)) {
/*  68 */         classMeta.log("... skipping add equals() ... will inherit this from super class");
/*     */       }
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/*  74 */     int idIndex = -1;
/*  75 */     FieldMeta idFieldMeta = null;
/*     */     
/*  77 */     for (int i = 0; i < fields.size(); i++) {
/*  78 */       FieldMeta fieldMeta = (FieldMeta)fields.get(i);
/*  79 */       if (fieldMeta.isId()) {
/*  80 */         if (idIndex == -1) {
/*     */           
/*  82 */           idIndex = i;
/*  83 */           idFieldMeta = fieldMeta;
/*     */         } else {
/*     */           
/*  86 */           idIndex = -2;
/*     */         } 
/*     */       }
/*     */     } 
/*     */     
/*  91 */     if (idIndex == -2) {
/*     */       
/*  93 */       if (classMeta.isLog(1)) {
/*  94 */         classMeta.log("has 2 or more id fields. Not adding equals() method.");
/*     */       }
/*     */     }
/*  97 */     else if (idIndex == -1) {
/*     */       
/*  99 */       if (classMeta.isLog(1)) {
/* 100 */         classMeta.log("has no id fields. Not adding equals() method.");
/*     */       }
/*     */     }
/*     */     else {
/*     */       
/* 105 */       MethodEquals.addMethods(cv, classMeta, idIndex, idFieldMeta);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void generateGetField(ClassVisitor cv, ClassMeta classMeta, List<FieldMeta> fields, boolean intercept) {
/* 115 */     String className = classMeta.getClassName();
/*     */     
/* 117 */     MethodVisitor mv = null;
/* 118 */     if (intercept) {
/* 119 */       mv = cv.visitMethod(1, "_ebean_getFieldIntercept", "(ILjava/lang/Object;)Ljava/lang/Object;", null, null);
/*     */     } else {
/* 121 */       mv = cv.visitMethod(1, "_ebean_getField", "(ILjava/lang/Object;)Ljava/lang/Object;", null, null);
/*     */     } 
/*     */     
/* 124 */     mv.visitCode();
/* 125 */     Label l0 = new Label();
/* 126 */     mv.visitLabel(l0);
/* 127 */     mv.visitLineNumber(1, l0);
/* 128 */     mv.visitVarInsn(25, 2);
/* 129 */     mv.visitTypeInsn(192, className);
/* 130 */     mv.visitVarInsn(58, 3);
/* 131 */     Label l1 = new Label();
/* 132 */     mv.visitLabel(l1);
/*     */     
/* 134 */     mv.visitLineNumber(1, l1);
/* 135 */     mv.visitVarInsn(21, 1);
/*     */     
/* 137 */     Label[] switchLabels = new Label[fields.size()];
/* 138 */     for (int i = 0; i < switchLabels.length; i++) {
/* 139 */       switchLabels[i] = new Label();
/*     */     }
/*     */     
/* 142 */     int maxIndex = switchLabels.length - 1;
/*     */     
/* 144 */     Label labelException = new Label();
/* 145 */     mv.visitTableSwitchInsn(0, maxIndex, labelException, switchLabels);
/*     */     
/* 147 */     for (int i = 0; i < fields.size(); i++) {
/*     */       
/* 149 */       FieldMeta fieldMeta = (FieldMeta)fields.get(i);
/*     */       
/* 151 */       mv.visitLabel(switchLabels[i]);
/* 152 */       mv.visitLineNumber(1, switchLabels[i]);
/* 153 */       mv.visitVarInsn(25, 3);
/*     */       
/* 155 */       fieldMeta.appendSwitchGet(mv, classMeta, intercept);
/*     */       
/* 157 */       mv.visitInsn(176);
/*     */     } 
/*     */     
/* 160 */     mv.visitLabel(labelException);
/* 161 */     mv.visitLineNumber(1, labelException);
/* 162 */     mv.visitTypeInsn(187, "java/lang/RuntimeException");
/* 163 */     mv.visitInsn(89);
/* 164 */     mv.visitTypeInsn(187, "java/lang/StringBuilder");
/* 165 */     mv.visitInsn(89);
/* 166 */     mv.visitLdcInsn("Invalid index ");
/* 167 */     mv.visitMethodInsn(183, "java/lang/StringBuilder", "<init>", "(Ljava/lang/String;)V");
/* 168 */     mv.visitVarInsn(21, 1);
/* 169 */     mv.visitMethodInsn(182, "java/lang/StringBuilder", "append", "(I)Ljava/lang/StringBuilder;");
/* 170 */     mv.visitMethodInsn(182, "java/lang/StringBuilder", "toString", "()Ljava/lang/String;");
/* 171 */     mv.visitMethodInsn(183, "java/lang/RuntimeException", "<init>", "(Ljava/lang/String;)V");
/* 172 */     mv.visitInsn(191);
/* 173 */     Label l5 = new Label();
/* 174 */     mv.visitLabel(l5);
/* 175 */     mv.visitLocalVariable("this", "L" + className + ";", null, l0, l5, 0);
/* 176 */     mv.visitLocalVariable("index", "I", null, l0, l5, 1);
/* 177 */     mv.visitLocalVariable("o", "Ljava/lang/Object;", null, l0, l5, 2);
/* 178 */     mv.visitLocalVariable("p", "L" + className + ";", null, l1, l5, 3);
/* 179 */     mv.visitMaxs(5, 4);
/* 180 */     mv.visitEnd();
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
/*     */   private static void generateSetField(ClassVisitor cv, ClassMeta classMeta, List<FieldMeta> fields, boolean intercept) {
/* 194 */     String className = classMeta.getClassName();
/*     */     
/* 196 */     MethodVisitor mv = null;
/* 197 */     if (intercept) {
/* 198 */       mv = cv.visitMethod(1, "_ebean_setFieldIntercept", "(ILjava/lang/Object;Ljava/lang/Object;)V", null, null);
/*     */     } else {
/*     */       
/* 201 */       mv = cv.visitMethod(1, "_ebean_setField", "(ILjava/lang/Object;Ljava/lang/Object;)V", null, null);
/*     */     } 
/*     */     
/* 204 */     mv.visitCode();
/* 205 */     Label l0 = new Label();
/* 206 */     mv.visitLabel(l0);
/* 207 */     mv.visitLineNumber(1, l0);
/* 208 */     mv.visitVarInsn(25, 2);
/* 209 */     mv.visitTypeInsn(192, className);
/* 210 */     mv.visitVarInsn(58, 4);
/* 211 */     Label l1 = new Label();
/* 212 */     mv.visitLabel(l1);
/* 213 */     mv.visitLineNumber(1, l1);
/* 214 */     mv.visitVarInsn(21, 1);
/*     */     
/* 216 */     Label[] switchLabels = new Label[fields.size()];
/* 217 */     for (int i = 0; i < switchLabels.length; i++) {
/* 218 */       switchLabels[i] = new Label();
/*     */     }
/*     */     
/* 221 */     Label labelException = new Label();
/*     */     
/* 223 */     int maxIndex = switchLabels.length - 1;
/*     */     
/* 225 */     mv.visitTableSwitchInsn(0, maxIndex, labelException, switchLabels);
/*     */     
/* 227 */     for (int i = 0; i < fields.size(); i++) {
/* 228 */       FieldMeta fieldMeta = (FieldMeta)fields.get(i);
/*     */       
/* 230 */       mv.visitLabel(switchLabels[i]);
/* 231 */       mv.visitLineNumber(1, switchLabels[i]);
/* 232 */       mv.visitVarInsn(25, 4);
/* 233 */       mv.visitVarInsn(25, 3);
/*     */       
/* 235 */       fieldMeta.appendSwitchSet(mv, classMeta, intercept);
/*     */       
/* 237 */       Label l6 = new Label();
/* 238 */       mv.visitLabel(l6);
/* 239 */       mv.visitLineNumber(1, l6);
/* 240 */       mv.visitInsn(177);
/*     */     } 
/*     */ 
/*     */     
/* 244 */     mv.visitLabel(labelException);
/* 245 */     mv.visitLineNumber(1, labelException);
/* 246 */     mv.visitTypeInsn(187, "java/lang/RuntimeException");
/* 247 */     mv.visitInsn(89);
/* 248 */     mv.visitTypeInsn(187, "java/lang/StringBuilder");
/* 249 */     mv.visitInsn(89);
/* 250 */     mv.visitLdcInsn("Invalid index ");
/* 251 */     mv.visitMethodInsn(183, "java/lang/StringBuilder", "<init>", "(Ljava/lang/String;)V");
/* 252 */     mv.visitVarInsn(21, 1);
/* 253 */     mv.visitMethodInsn(182, "java/lang/StringBuilder", "append", "(I)Ljava/lang/StringBuilder;");
/* 254 */     mv.visitMethodInsn(182, "java/lang/StringBuilder", "toString", "()Ljava/lang/String;");
/* 255 */     mv.visitMethodInsn(183, "java/lang/RuntimeException", "<init>", "(Ljava/lang/String;)V");
/* 256 */     mv.visitInsn(191);
/* 257 */     Label l9 = new Label();
/* 258 */     mv.visitLabel(l9);
/* 259 */     mv.visitLocalVariable("this", "L" + className + ";", null, l0, l9, 0);
/* 260 */     mv.visitLocalVariable("index", "I", null, l0, l9, 1);
/* 261 */     mv.visitLocalVariable("o", "Ljava/lang/Object;", null, l0, l9, 2);
/* 262 */     mv.visitLocalVariable("arg", "Ljava/lang/Object;", null, l0, l9, 3);
/* 263 */     mv.visitLocalVariable("p", "L" + className + ";", null, l1, l9, 4);
/* 264 */     mv.visitMaxs(5, 5);
/* 265 */     mv.visitEnd();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void generateCreateCopy(ClassVisitor cv, ClassMeta classMeta, List<FieldMeta> fields) {
/* 273 */     String className = classMeta.getClassName();
/*     */     
/* 275 */     String copyClassName = className;
/* 276 */     if (classMeta.isSubclassing()) {
/* 277 */       copyClassName = classMeta.getSuperClassName();
/*     */     }
/*     */     
/* 280 */     MethodVisitor mv = cv.visitMethod(1, "_ebean_createCopy", "()Ljava/lang/Object;", null, null);
/* 281 */     mv.visitCode();
/* 282 */     Label l0 = new Label();
/* 283 */     mv.visitLabel(l0);
/* 284 */     mv.visitLineNumber(1, l0);
/* 285 */     mv.visitTypeInsn(187, copyClassName);
/* 286 */     mv.visitInsn(89);
/* 287 */     mv.visitMethodInsn(183, copyClassName, "<init>", "()V");
/* 288 */     mv.visitVarInsn(58, 1);
/*     */     
/* 290 */     Label l1 = null;
/* 291 */     for (i = 0; i < fields.size(); i++) {
/*     */       
/* 293 */       FieldMeta fieldMeta = (FieldMeta)fields.get(i);
/* 294 */       if (fieldMeta.isPersistent()) {
/*     */         
/* 296 */         Label label = new Label();
/* 297 */         if (i == 0) {
/* 298 */           l1 = label;
/*     */         }
/* 300 */         mv.visitLabel(label);
/* 301 */         mv.visitLineNumber(1, label);
/* 302 */         mv.visitVarInsn(25, 1);
/* 303 */         mv.visitVarInsn(25, 0);
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 308 */         fieldMeta.addFieldCopy(mv, classMeta);
/*     */       } 
/*     */     } 
/*     */     
/* 312 */     Label l4 = new Label();
/* 313 */     mv.visitLabel(l4);
/* 314 */     mv.visitLineNumber(1, l4);
/* 315 */     mv.visitVarInsn(25, 1);
/* 316 */     mv.visitInsn(176);
/* 317 */     Label l5 = new Label();
/* 318 */     mv.visitLabel(l5);
/* 319 */     if (l1 == null) {
/* 320 */       l1 = l4;
/*     */     }
/* 322 */     mv.visitLocalVariable("this", "L" + className + ";", null, l0, l5, 0);
/* 323 */     mv.visitLocalVariable("p", "L" + copyClassName + ";", null, l1, l5, 1);
/* 324 */     mv.visitMaxs(2, 2);
/* 325 */     mv.visitEnd();
/*     */   }
/*     */ 
/*     */   
/*     */   private static void generateGetDesc(ClassVisitor cv, ClassMeta classMeta, List<FieldMeta> fields) {
/* 330 */     String className = classMeta.getClassName();
/*     */     
/* 332 */     int size = fields.size();
/*     */     
/* 334 */     MethodVisitor mv = cv.visitMethod(1, "_ebean_getFieldNames", "()[Ljava/lang/String;", null, null);
/* 335 */     mv.visitCode();
/* 336 */     Label l0 = new Label();
/* 337 */     mv.visitLabel(l0);
/* 338 */     mv.visitLineNumber(1, l0);
/* 339 */     visitIntInsn(mv, size);
/* 340 */     mv.visitTypeInsn(189, "java/lang/String");
/*     */     
/* 342 */     for (int i = 0; i < size; i++) {
/* 343 */       FieldMeta fieldMeta = (FieldMeta)fields.get(i);
/* 344 */       mv.visitInsn(89);
/* 345 */       visitIntInsn(mv, i);
/* 346 */       mv.visitLdcInsn(fieldMeta.getName());
/* 347 */       mv.visitInsn(83);
/*     */     } 
/*     */     
/* 350 */     mv.visitInsn(176);
/* 351 */     Label l1 = new Label();
/* 352 */     mv.visitLabel(l1);
/* 353 */     mv.visitLocalVariable("this", "L" + className + ";", null, l0, l1, 0);
/* 354 */     mv.visitMaxs(4, 1);
/* 355 */     mv.visitEnd();
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
/*     */   public static void visitIntInsn(MethodVisitor mv, int value) {
/* 367 */     switch (value) {
/*     */       case 0:
/* 369 */         mv.visitInsn(3);
/*     */         return;
/*     */       case 1:
/* 372 */         mv.visitInsn(4);
/*     */         return;
/*     */       case 2:
/* 375 */         mv.visitInsn(5);
/*     */         return;
/*     */       case 3:
/* 378 */         mv.visitInsn(6);
/*     */         return;
/*     */       case 4:
/* 381 */         mv.visitInsn(7);
/*     */         return;
/*     */       case 5:
/* 384 */         mv.visitInsn(8);
/*     */         return;
/*     */     } 
/* 387 */     if (value <= 127) {
/* 388 */       mv.visitIntInsn(16, value);
/*     */     } else {
/* 390 */       mv.visitIntInsn(17, value);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebean\enhance\agent\IndexFieldWeaver.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */