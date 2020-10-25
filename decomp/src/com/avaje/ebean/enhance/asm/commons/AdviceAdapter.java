/*     */ package com.avaje.ebean.enhance.asm.commons;
/*     */ 
/*     */ import com.avaje.ebean.enhance.asm.Label;
/*     */ import com.avaje.ebean.enhance.asm.MethodVisitor;
/*     */ import com.avaje.ebean.enhance.asm.Opcodes;
/*     */ import com.avaje.ebean.enhance.asm.Type;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class AdviceAdapter
/*     */   extends GeneratorAdapter
/*     */   implements Opcodes
/*     */ {
/*  63 */   private static final Object THIS = new Object();
/*  64 */   private static final Object OTHER = new Object();
/*     */ 
/*     */ 
/*     */   
/*     */   protected int methodAccess;
/*     */ 
/*     */ 
/*     */   
/*     */   protected String methodDesc;
/*     */ 
/*     */   
/*     */   private boolean constructor;
/*     */ 
/*     */   
/*     */   private boolean superInitialized;
/*     */ 
/*     */   
/*     */   private List stackFrame;
/*     */ 
/*     */   
/*     */   private Map branches;
/*     */ 
/*     */ 
/*     */   
/*     */   protected AdviceAdapter(MethodVisitor mv, int access, String name, String desc) {
/*  89 */     super(mv, access, name, desc);
/*  90 */     this.methodAccess = access;
/*  91 */     this.methodDesc = desc;
/*     */     
/*  93 */     this.constructor = "<init>".equals(name);
/*     */   }
/*     */   
/*     */   public void visitCode() {
/*  97 */     this.mv.visitCode();
/*  98 */     if (this.constructor) {
/*  99 */       this.stackFrame = new ArrayList();
/* 100 */       this.branches = new HashMap();
/*     */     } else {
/* 102 */       this.superInitialized = true;
/* 103 */       onMethodEnter();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void visitLabel(Label label) {
/* 108 */     this.mv.visitLabel(label);
/*     */     
/* 110 */     if (this.constructor && this.branches != null) {
/* 111 */       List frame = (List)this.branches.get(label);
/* 112 */       if (frame != null) {
/* 113 */         this.stackFrame = frame;
/* 114 */         this.branches.remove(label);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void visitInsn(int opcode) {
/* 120 */     if (this.constructor) {
/*     */       int s;
/* 122 */       switch (opcode) {
/*     */         case 177:
/* 124 */           onMethodExit(opcode);
/*     */           break;
/*     */         
/*     */         case 172:
/*     */         case 174:
/*     */         case 176:
/*     */         case 191:
/* 131 */           popValue();
/* 132 */           onMethodExit(opcode);
/*     */           break;
/*     */         
/*     */         case 173:
/*     */         case 175:
/* 137 */           popValue();
/* 138 */           popValue();
/* 139 */           onMethodExit(opcode);
/*     */           break;
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
/*     */         case 1:
/*     */         case 2:
/*     */         case 3:
/*     */         case 4:
/*     */         case 5:
/*     */         case 6:
/*     */         case 7:
/*     */         case 8:
/*     */         case 11:
/*     */         case 12:
/*     */         case 13:
/*     */         case 133:
/*     */         case 135:
/*     */         case 140:
/*     */         case 141:
/* 174 */           pushValue(OTHER);
/*     */           break;
/*     */         
/*     */         case 9:
/*     */         case 10:
/*     */         case 14:
/*     */         case 15:
/* 181 */           pushValue(OTHER);
/* 182 */           pushValue(OTHER);
/*     */           break;
/*     */         
/*     */         case 46:
/*     */         case 48:
/*     */         case 50:
/*     */         case 51:
/*     */         case 52:
/*     */         case 53:
/*     */         case 87:
/*     */         case 96:
/*     */         case 98:
/*     */         case 100:
/*     */         case 102:
/*     */         case 104:
/*     */         case 106:
/*     */         case 108:
/*     */         case 110:
/*     */         case 112:
/*     */         case 114:
/*     */         case 120:
/*     */         case 121:
/*     */         case 122:
/*     */         case 123:
/*     */         case 124:
/*     */         case 125:
/*     */         case 126:
/*     */         case 128:
/*     */         case 130:
/*     */         case 136:
/*     */         case 137:
/*     */         case 142:
/*     */         case 144:
/*     */         case 149:
/*     */         case 150:
/*     */         case 194:
/*     */         case 195:
/* 219 */           popValue();
/*     */           break;
/*     */         
/*     */         case 88:
/*     */         case 97:
/*     */         case 99:
/*     */         case 101:
/*     */         case 103:
/*     */         case 105:
/*     */         case 107:
/*     */         case 109:
/*     */         case 111:
/*     */         case 113:
/*     */         case 115:
/*     */         case 127:
/*     */         case 129:
/*     */         case 131:
/* 236 */           popValue();
/* 237 */           popValue();
/*     */           break;
/*     */         
/*     */         case 79:
/*     */         case 81:
/*     */         case 83:
/*     */         case 84:
/*     */         case 85:
/*     */         case 86:
/*     */         case 148:
/*     */         case 151:
/*     */         case 152:
/* 249 */           popValue();
/* 250 */           popValue();
/* 251 */           popValue();
/*     */           break;
/*     */         
/*     */         case 80:
/*     */         case 82:
/* 256 */           popValue();
/* 257 */           popValue();
/* 258 */           popValue();
/* 259 */           popValue();
/*     */           break;
/*     */         
/*     */         case 89:
/* 263 */           pushValue(peekValue());
/*     */           break;
/*     */         
/*     */         case 90:
/* 267 */           s = this.stackFrame.size();
/* 268 */           this.stackFrame.add(s - 2, this.stackFrame.get(s - 1));
/*     */           break;
/*     */         
/*     */         case 91:
/* 272 */           s = this.stackFrame.size();
/* 273 */           this.stackFrame.add(s - 3, this.stackFrame.get(s - 1));
/*     */           break;
/*     */         
/*     */         case 92:
/* 277 */           s = this.stackFrame.size();
/* 278 */           this.stackFrame.add(s - 2, this.stackFrame.get(s - 1));
/* 279 */           this.stackFrame.add(s - 2, this.stackFrame.get(s - 1));
/*     */           break;
/*     */         
/*     */         case 93:
/* 283 */           s = this.stackFrame.size();
/* 284 */           this.stackFrame.add(s - 3, this.stackFrame.get(s - 1));
/* 285 */           this.stackFrame.add(s - 3, this.stackFrame.get(s - 1));
/*     */           break;
/*     */         
/*     */         case 94:
/* 289 */           s = this.stackFrame.size();
/* 290 */           this.stackFrame.add(s - 4, this.stackFrame.get(s - 1));
/* 291 */           this.stackFrame.add(s - 4, this.stackFrame.get(s - 1));
/*     */           break;
/*     */         
/*     */         case 95:
/* 295 */           s = this.stackFrame.size();
/* 296 */           this.stackFrame.add(s - 2, this.stackFrame.get(s - 1));
/* 297 */           this.stackFrame.remove(s);
/*     */           break;
/*     */       } 
/*     */     } else {
/* 301 */       switch (opcode) {
/*     */         case 172:
/*     */         case 173:
/*     */         case 174:
/*     */         case 175:
/*     */         case 176:
/*     */         case 177:
/*     */         case 191:
/* 309 */           onMethodExit(opcode);
/*     */           break;
/*     */       } 
/*     */     } 
/* 313 */     this.mv.visitInsn(opcode);
/*     */   }
/*     */   
/*     */   public void visitVarInsn(int opcode, int var) {
/* 317 */     super.visitVarInsn(opcode, var);
/*     */     
/* 319 */     if (this.constructor) {
/* 320 */       switch (opcode) {
/*     */         case 21:
/*     */         case 23:
/* 323 */           pushValue(OTHER);
/*     */           break;
/*     */         case 22:
/*     */         case 24:
/* 327 */           pushValue(OTHER);
/* 328 */           pushValue(OTHER);
/*     */           break;
/*     */         case 25:
/* 331 */           pushValue((var == 0) ? THIS : OTHER);
/*     */           break;
/*     */         case 54:
/*     */         case 56:
/*     */         case 58:
/* 336 */           popValue();
/*     */           break;
/*     */         case 55:
/*     */         case 57:
/* 340 */           popValue();
/* 341 */           popValue();
/*     */           break;
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void visitFieldInsn(int opcode, String owner, String name, String desc) {
/* 353 */     this.mv.visitFieldInsn(opcode, owner, name, desc);
/*     */     
/* 355 */     if (this.constructor) {
/* 356 */       char c = desc.charAt(0);
/* 357 */       boolean longOrDouble = (c == 'J' || c == 'D');
/* 358 */       switch (opcode) {
/*     */         case 178:
/* 360 */           pushValue(OTHER);
/* 361 */           if (longOrDouble) {
/* 362 */             pushValue(OTHER);
/*     */           }
/*     */           return;
/*     */         case 179:
/* 366 */           popValue();
/* 367 */           if (longOrDouble) {
/* 368 */             popValue();
/*     */           }
/*     */           return;
/*     */         case 181:
/* 372 */           popValue();
/* 373 */           if (longOrDouble) {
/* 374 */             popValue();
/* 375 */             popValue();
/*     */           } 
/*     */           return;
/*     */       } 
/*     */       
/* 380 */       if (longOrDouble) {
/* 381 */         pushValue(OTHER);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void visitIntInsn(int opcode, int operand) {
/* 388 */     this.mv.visitIntInsn(opcode, operand);
/*     */     
/* 390 */     if (this.constructor && opcode != 188) {
/* 391 */       pushValue(OTHER);
/*     */     }
/*     */   }
/*     */   
/*     */   public void visitLdcInsn(Object cst) {
/* 396 */     this.mv.visitLdcInsn(cst);
/*     */     
/* 398 */     if (this.constructor) {
/* 399 */       pushValue(OTHER);
/* 400 */       if (cst instanceof Double || cst instanceof Long) {
/* 401 */         pushValue(OTHER);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public void visitMultiANewArrayInsn(String desc, int dims) {
/* 407 */     this.mv.visitMultiANewArrayInsn(desc, dims);
/*     */     
/* 409 */     if (this.constructor) {
/* 410 */       for (int i = 0; i < dims; i++) {
/* 411 */         popValue();
/*     */       }
/* 413 */       pushValue(OTHER);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void visitTypeInsn(int opcode, String type) {
/* 418 */     this.mv.visitTypeInsn(opcode, type);
/*     */ 
/*     */     
/* 421 */     if (this.constructor && opcode == 187) {
/* 422 */       pushValue(OTHER);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void visitMethodInsn(int opcode, String owner, String name, String desc) {
/* 432 */     this.mv.visitMethodInsn(opcode, owner, name, desc);
/*     */     
/* 434 */     if (this.constructor) {
/* 435 */       Type[] types = Type.getArgumentTypes(desc);
/* 436 */       for (i = 0; i < types.length; i++) {
/* 437 */         popValue();
/* 438 */         if (types[i].getSize() == 2) {
/* 439 */           popValue();
/*     */         }
/*     */       } 
/* 442 */       switch (opcode) {
/*     */ 
/*     */ 
/*     */         
/*     */         case 182:
/*     */         case 185:
/* 448 */           popValue();
/*     */           break;
/*     */         
/*     */         case 183:
/* 452 */           type = popValue();
/* 453 */           if (type == THIS && !this.superInitialized) {
/* 454 */             onMethodEnter();
/* 455 */             this.superInitialized = true;
/*     */ 
/*     */             
/* 458 */             this.constructor = false;
/*     */           } 
/*     */           break;
/*     */       } 
/*     */       
/* 463 */       Type returnType = Type.getReturnType(desc);
/* 464 */       if (returnType != Type.VOID_TYPE) {
/* 465 */         pushValue(OTHER);
/* 466 */         if (returnType.getSize() == 2) {
/* 467 */           pushValue(OTHER);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void visitJumpInsn(int opcode, Label label) {
/* 474 */     this.mv.visitJumpInsn(opcode, label);
/*     */     
/* 476 */     if (this.constructor) {
/* 477 */       switch (opcode) {
/*     */         case 153:
/*     */         case 154:
/*     */         case 155:
/*     */         case 156:
/*     */         case 157:
/*     */         case 158:
/*     */         case 198:
/*     */         case 199:
/* 486 */           popValue();
/*     */           break;
/*     */         
/*     */         case 159:
/*     */         case 160:
/*     */         case 161:
/*     */         case 162:
/*     */         case 163:
/*     */         case 164:
/*     */         case 165:
/*     */         case 166:
/* 497 */           popValue();
/* 498 */           popValue();
/*     */           break;
/*     */         
/*     */         case 168:
/* 502 */           pushValue(OTHER);
/*     */           break;
/*     */       } 
/* 505 */       addBranch(label);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void visitLookupSwitchInsn(Label dflt, int[] keys, Label[] labels) {
/* 514 */     this.mv.visitLookupSwitchInsn(dflt, keys, labels);
/*     */     
/* 516 */     if (this.constructor) {
/* 517 */       popValue();
/* 518 */       addBranches(dflt, labels);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void visitTableSwitchInsn(int min, int max, Label dflt, Label[] labels) {
/* 528 */     this.mv.visitTableSwitchInsn(min, max, dflt, labels);
/*     */     
/* 530 */     if (this.constructor) {
/* 531 */       popValue();
/* 532 */       addBranches(dflt, labels);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void addBranches(Label dflt, Label[] labels) {
/* 537 */     addBranch(dflt);
/* 538 */     for (int i = 0; i < labels.length; i++) {
/* 539 */       addBranch(labels[i]);
/*     */     }
/*     */   }
/*     */   
/*     */   private void addBranch(Label label) {
/* 544 */     if (this.branches.containsKey(label)) {
/*     */       return;
/*     */     }
/* 547 */     this.branches.put(label, new ArrayList(this.stackFrame));
/*     */   }
/*     */ 
/*     */   
/* 551 */   private Object popValue() { return this.stackFrame.remove(this.stackFrame.size() - 1); }
/*     */ 
/*     */ 
/*     */   
/* 555 */   private Object peekValue() { return this.stackFrame.get(this.stackFrame.size() - 1); }
/*     */ 
/*     */ 
/*     */   
/* 559 */   private void pushValue(Object o) { this.stackFrame.add(o); }
/*     */   
/*     */   protected void onMethodEnter() {}
/*     */   
/*     */   protected void onMethodExit(int opcode) {}
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebean\enhance\asm\commons\AdviceAdapter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */