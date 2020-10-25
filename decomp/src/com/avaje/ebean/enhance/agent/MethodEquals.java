/*     */ package com.avaje.ebean.enhance.agent;
/*     */ 
/*     */ import com.avaje.ebean.enhance.asm.ClassVisitor;
/*     */ import com.avaje.ebean.enhance.asm.FieldVisitor;
/*     */ import com.avaje.ebean.enhance.asm.Label;
/*     */ import com.avaje.ebean.enhance.asm.MethodVisitor;
/*     */ import com.avaje.ebean.enhance.asm.Opcodes;
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
/*     */ public class MethodEquals
/*     */   implements Opcodes, EnhanceConstants
/*     */ {
/*     */   private static final String _EBEAN_GET_IDENTITY = "_ebean_getIdentity";
/*     */   
/*     */   public static void addMethods(ClassVisitor cv, ClassMeta meta, int idFieldIndex, FieldMeta idFieldMeta) {
/*  51 */     if (meta.hasEqualsOrHashCode()) {
/*     */ 
/*     */       
/*  54 */       if (meta.isLog(1)) {
/*  55 */         meta.log("already has a equals() or hashCode() method. Not adding the identity based one.");
/*     */       }
/*     */     } else {
/*  58 */       if (meta.isLog(2)) {
/*  59 */         meta.log("adding equals() hashCode() and _ebean_getIdentity() with Id field " + idFieldMeta.getName() + " index:" + idFieldIndex + " primative:" + idFieldMeta.isPrimativeType());
/*     */       }
/*     */       
/*  62 */       if (idFieldMeta.isPrimativeType()) {
/*  63 */         addGetIdentityPrimitive(cv, meta, idFieldIndex, idFieldMeta);
/*     */       } else {
/*  65 */         addGetIdentityObject(cv, meta, idFieldIndex);
/*     */       } 
/*  67 */       addEquals(cv, meta);
/*  68 */       addHashCode(cv, meta);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void addIdentityField(ClassVisitor cv) {
/*  79 */     int access = 132;
/*  80 */     FieldVisitor f0 = cv.visitField(access, "_ebean_identity", "Ljava/lang/Object;", null, null);
/*  81 */     f0.visitEnd();
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
/*     */   private static void addGetIdentityPrimitive(ClassVisitor cv, ClassMeta classMeta, int idFieldIndex, FieldMeta idFieldMeta) {
/* 113 */     String className = classMeta.getClassName();
/*     */ 
/*     */ 
/*     */     
/* 117 */     MethodVisitor mv = cv.visitMethod(2, "_ebean_getIdentity", "()Ljava/lang/Object;", null, null);
/* 118 */     mv.visitCode();
/*     */     
/* 120 */     Label l0 = new Label();
/* 121 */     Label l1 = new Label();
/* 122 */     Label l2 = new Label();
/* 123 */     mv.visitTryCatchBlock(l0, l1, l2, null);
/* 124 */     Label l3 = new Label();
/* 125 */     Label l4 = new Label();
/* 126 */     mv.visitTryCatchBlock(l3, l4, l2, null);
/* 127 */     Label l5 = new Label();
/* 128 */     mv.visitTryCatchBlock(l2, l5, l2, null);
/* 129 */     Label l6 = new Label();
/* 130 */     mv.visitLabel(l6);
/* 131 */     mv.visitLineNumber(1, l6);
/* 132 */     mv.visitVarInsn(25, 0);
/* 133 */     mv.visitInsn(89);
/* 134 */     mv.visitVarInsn(58, 1);
/* 135 */     mv.visitInsn(194);
/* 136 */     mv.visitLabel(l0);
/* 137 */     mv.visitLineNumber(1, l0);
/* 138 */     mv.visitVarInsn(25, 0);
/* 139 */     mv.visitFieldInsn(180, className, "_ebean_identity", "Ljava/lang/Object;");
/* 140 */     mv.visitJumpInsn(198, l3);
/* 141 */     Label l7 = new Label();
/* 142 */     mv.visitLabel(l7);
/* 143 */     mv.visitLineNumber(1, l7);
/* 144 */     mv.visitVarInsn(25, 0);
/* 145 */     mv.visitFieldInsn(180, className, "_ebean_identity", "Ljava/lang/Object;");
/* 146 */     mv.visitVarInsn(25, 1);
/* 147 */     mv.visitInsn(195);
/* 148 */     mv.visitLabel(l1);
/* 149 */     mv.visitInsn(176);
/* 150 */     mv.visitLabel(l3);
/* 151 */     mv.visitLineNumber(1, l3);
/* 152 */     mv.visitVarInsn(25, 0);
/* 153 */     idFieldMeta.appendGetPrimitiveIdValue(mv, classMeta);
/* 154 */     idFieldMeta.appendCompare(mv, classMeta);
/*     */     
/* 156 */     Label l8 = new Label();
/* 157 */     mv.visitJumpInsn(153, l8);
/* 158 */     Label l9 = new Label();
/* 159 */     mv.visitLabel(l9);
/* 160 */     mv.visitLineNumber(1, l9);
/* 161 */     mv.visitVarInsn(25, 0);
/* 162 */     mv.visitVarInsn(25, 0);
/* 163 */     idFieldMeta.appendGetPrimitiveIdValue(mv, classMeta);
/* 164 */     idFieldMeta.appendValueOf(mv, classMeta);
/* 165 */     mv.visitFieldInsn(181, className, "_ebean_identity", "Ljava/lang/Object;");
/* 166 */     Label l10 = new Label();
/* 167 */     mv.visitJumpInsn(167, l10);
/* 168 */     mv.visitLabel(l8);
/* 169 */     mv.visitLineNumber(1, l8);
/* 170 */     mv.visitVarInsn(25, 0);
/* 171 */     mv.visitTypeInsn(187, "java/lang/Object");
/* 172 */     mv.visitInsn(89);
/* 173 */     mv.visitMethodInsn(183, "java/lang/Object", "<init>", "()V");
/* 174 */     mv.visitFieldInsn(181, className, "_ebean_identity", "Ljava/lang/Object;");
/* 175 */     mv.visitLabel(l10);
/* 176 */     mv.visitLineNumber(1, l10);
/* 177 */     mv.visitVarInsn(25, 0);
/* 178 */     mv.visitFieldInsn(180, className, "_ebean_identity", "Ljava/lang/Object;");
/* 179 */     mv.visitVarInsn(25, 1);
/* 180 */     mv.visitInsn(195);
/* 181 */     mv.visitLabel(l4);
/* 182 */     mv.visitInsn(176);
/* 183 */     mv.visitLabel(l2);
/* 184 */     mv.visitLineNumber(1, l2);
/* 185 */     mv.visitVarInsn(25, 1);
/* 186 */     mv.visitInsn(195);
/* 187 */     mv.visitLabel(l5);
/* 188 */     mv.visitInsn(191);
/* 189 */     Label l11 = new Label();
/* 190 */     mv.visitLabel(l11);
/* 191 */     mv.visitLocalVariable("this", "L" + className + ";", null, l6, l11, 0);
/* 192 */     mv.visitMaxs(3, 2);
/* 193 */     mv.visitEnd();
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
/*     */   private static void addGetIdentityObject(ClassVisitor cv, ClassMeta classMeta, int idFieldIndex) {
/* 220 */     String className = classMeta.getClassName();
/*     */ 
/*     */ 
/*     */     
/* 224 */     MethodVisitor mv = cv.visitMethod(2, "_ebean_getIdentity", "()Ljava/lang/Object;", null, null);
/* 225 */     mv.visitCode();
/*     */     
/* 227 */     Label l0 = new Label();
/* 228 */     Label l1 = new Label();
/* 229 */     Label l2 = new Label();
/* 230 */     mv.visitTryCatchBlock(l0, l1, l2, null);
/* 231 */     Label l3 = new Label();
/* 232 */     Label l4 = new Label();
/* 233 */     mv.visitTryCatchBlock(l3, l4, l2, null);
/* 234 */     Label l5 = new Label();
/* 235 */     mv.visitTryCatchBlock(l2, l5, l2, null);
/* 236 */     Label l6 = new Label();
/* 237 */     mv.visitLabel(l6);
/* 238 */     mv.visitLineNumber(1, l6);
/* 239 */     mv.visitVarInsn(25, 0);
/* 240 */     mv.visitInsn(89);
/* 241 */     mv.visitVarInsn(58, 1);
/* 242 */     mv.visitInsn(194);
/* 243 */     mv.visitLabel(l0);
/* 244 */     mv.visitLineNumber(1, l0);
/* 245 */     mv.visitVarInsn(25, 0);
/* 246 */     mv.visitFieldInsn(180, className, "_ebean_identity", "Ljava/lang/Object;");
/* 247 */     mv.visitJumpInsn(198, l3);
/* 248 */     Label l7 = new Label();
/* 249 */     mv.visitLabel(l7);
/* 250 */     mv.visitLineNumber(1, l7);
/* 251 */     mv.visitVarInsn(25, 0);
/* 252 */     mv.visitFieldInsn(180, className, "_ebean_identity", "Ljava/lang/Object;");
/* 253 */     mv.visitVarInsn(25, 1);
/* 254 */     mv.visitInsn(195);
/* 255 */     mv.visitLabel(l1);
/* 256 */     mv.visitInsn(176);
/*     */     
/* 258 */     mv.visitLabel(l3);
/* 259 */     mv.visitLineNumber(1, l3);
/* 260 */     mv.visitVarInsn(25, 0);
/* 261 */     IndexFieldWeaver.visitIntInsn(mv, idFieldIndex);
/* 262 */     mv.visitVarInsn(25, 0);
/* 263 */     mv.visitMethodInsn(183, className, "_ebean_getField", "(ILjava/lang/Object;)Ljava/lang/Object;");
/* 264 */     mv.visitVarInsn(58, 2);
/*     */     
/* 266 */     Label l8 = new Label();
/* 267 */     mv.visitLabel(l8);
/* 268 */     mv.visitLineNumber(1, l8);
/* 269 */     mv.visitVarInsn(25, 2);
/* 270 */     Label l9 = new Label();
/* 271 */     mv.visitJumpInsn(198, l9);
/* 272 */     Label l10 = new Label();
/* 273 */     mv.visitLabel(l10);
/* 274 */     mv.visitLineNumber(1, l10);
/* 275 */     mv.visitVarInsn(25, 0);
/* 276 */     mv.visitVarInsn(25, 2);
/* 277 */     mv.visitFieldInsn(181, className, "_ebean_identity", "Ljava/lang/Object;");
/* 278 */     Label l11 = new Label();
/* 279 */     mv.visitJumpInsn(167, l11);
/* 280 */     mv.visitLabel(l9);
/* 281 */     mv.visitLineNumber(1, l9);
/* 282 */     mv.visitVarInsn(25, 0);
/* 283 */     mv.visitTypeInsn(187, "java/lang/Object");
/* 284 */     mv.visitInsn(89);
/* 285 */     mv.visitMethodInsn(183, "java/lang/Object", "<init>", "()V");
/* 286 */     mv.visitFieldInsn(181, className, "_ebean_identity", "Ljava/lang/Object;");
/* 287 */     mv.visitLabel(l11);
/* 288 */     mv.visitLineNumber(1, l11);
/* 289 */     mv.visitVarInsn(25, 0);
/* 290 */     mv.visitFieldInsn(180, className, "_ebean_identity", "Ljava/lang/Object;");
/* 291 */     mv.visitVarInsn(25, 1);
/* 292 */     mv.visitInsn(195);
/* 293 */     mv.visitLabel(l4);
/* 294 */     mv.visitInsn(176);
/* 295 */     mv.visitLabel(l2);
/* 296 */     mv.visitLineNumber(1, l2);
/* 297 */     mv.visitVarInsn(25, 1);
/* 298 */     mv.visitInsn(195);
/* 299 */     mv.visitLabel(l5);
/* 300 */     mv.visitInsn(191);
/* 301 */     Label l12 = new Label();
/* 302 */     mv.visitLabel(l12);
/* 303 */     mv.visitLocalVariable("this", "L" + className + ";", null, l6, l12, 0);
/* 304 */     mv.visitLocalVariable("tmpId", "Ljava/lang/Object;", null, l8, l2, 2);
/* 305 */     mv.visitMaxs(3, 3);
/* 306 */     mv.visitEnd();
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
/*     */   private static void addEquals(ClassVisitor cv, ClassMeta classMeta) {
/* 332 */     MethodVisitor mv = cv.visitMethod(1, "equals", "(Ljava/lang/Object;)Z", null, null);
/* 333 */     mv.visitCode();
/* 334 */     Label l0 = new Label();
/* 335 */     mv.visitLabel(l0);
/* 336 */     mv.visitLineNumber(1, l0);
/* 337 */     mv.visitVarInsn(25, 1);
/* 338 */     Label l1 = new Label();
/* 339 */     mv.visitJumpInsn(199, l1);
/* 340 */     Label l2 = new Label();
/* 341 */     mv.visitLabel(l2);
/* 342 */     mv.visitLineNumber(2, l2);
/* 343 */     mv.visitInsn(3);
/* 344 */     mv.visitInsn(172);
/* 345 */     mv.visitLabel(l1);
/* 346 */     mv.visitLineNumber(3, l1);
/* 347 */     mv.visitFrame(3, 0, null, 0, null);
/* 348 */     mv.visitVarInsn(25, 0);
/* 349 */     mv.visitMethodInsn(182, "java/lang/Object", "getClass", "()Ljava/lang/Class;");
/* 350 */     mv.visitVarInsn(25, 1);
/* 351 */     mv.visitMethodInsn(182, "java/lang/Object", "getClass", "()Ljava/lang/Class;");
/* 352 */     mv.visitMethodInsn(182, "java/lang/Object", "equals", "(Ljava/lang/Object;)Z");
/* 353 */     Label l3 = new Label();
/* 354 */     mv.visitJumpInsn(154, l3);
/* 355 */     Label l4 = new Label();
/* 356 */     mv.visitLabel(l4);
/* 357 */     mv.visitLineNumber(4, l4);
/* 358 */     mv.visitInsn(3);
/* 359 */     mv.visitInsn(172);
/* 360 */     mv.visitLabel(l3);
/* 361 */     mv.visitLineNumber(5, l3);
/* 362 */     mv.visitFrame(3, 0, null, 0, null);
/* 363 */     mv.visitVarInsn(25, 1);
/* 364 */     mv.visitVarInsn(25, 0);
/* 365 */     Label l5 = new Label();
/* 366 */     mv.visitJumpInsn(166, l5);
/* 367 */     Label l6 = new Label();
/* 368 */     mv.visitLabel(l6);
/* 369 */     mv.visitLineNumber(6, l6);
/* 370 */     mv.visitInsn(4);
/* 371 */     mv.visitInsn(172);
/* 372 */     mv.visitLabel(l5);
/* 373 */     mv.visitLineNumber(7, l5);
/* 374 */     mv.visitFrame(3, 0, null, 0, null);
/* 375 */     mv.visitVarInsn(25, 0);
/* 376 */     mv.visitMethodInsn(182, classMeta.getClassName(), "_ebean_getIdentity", "()Ljava/lang/Object;");
/* 377 */     mv.visitVarInsn(25, 1);
/* 378 */     mv.visitTypeInsn(192, classMeta.getClassName());
/* 379 */     mv.visitMethodInsn(182, classMeta.getClassName(), "_ebean_getIdentity", "()Ljava/lang/Object;");
/* 380 */     mv.visitMethodInsn(182, "java/lang/Object", "equals", "(Ljava/lang/Object;)Z");
/* 381 */     mv.visitInsn(172);
/* 382 */     Label l7 = new Label();
/* 383 */     mv.visitLabel(l7);
/* 384 */     mv.visitLocalVariable("this", "L" + classMeta.getClassName() + ";", null, l0, l7, 0);
/* 385 */     mv.visitLocalVariable("obj", "Ljava/lang/Object;", null, l0, l7, 1);
/* 386 */     mv.visitMaxs(2, 2);
/* 387 */     mv.visitEnd();
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
/*     */   private static void addHashCode(ClassVisitor cv, ClassMeta meta) {
/* 403 */     MethodVisitor mv = cv.visitMethod(1, "hashCode", "()I", null, null);
/* 404 */     mv.visitCode();
/* 405 */     Label l0 = new Label();
/* 406 */     mv.visitLabel(l0);
/* 407 */     mv.visitLineNumber(1, l0);
/* 408 */     mv.visitVarInsn(25, 0);
/* 409 */     mv.visitMethodInsn(183, meta.getClassName(), "_ebean_getIdentity", "()Ljava/lang/Object;");
/* 410 */     mv.visitMethodInsn(182, "java/lang/Object", "hashCode", "()I");
/* 411 */     mv.visitInsn(172);
/* 412 */     Label l1 = new Label();
/* 413 */     mv.visitLabel(l1);
/* 414 */     mv.visitLocalVariable("this", "L" + meta.getClassName() + ";", null, l0, l1, 0);
/* 415 */     mv.visitMaxs(1, 1);
/* 416 */     mv.visitEnd();
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebean\enhance\agent\MethodEquals.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */