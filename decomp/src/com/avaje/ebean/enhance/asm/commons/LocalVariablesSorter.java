/*     */ package com.avaje.ebean.enhance.asm.commons;
/*     */ 
/*     */ import com.avaje.ebean.enhance.asm.Label;
/*     */ import com.avaje.ebean.enhance.asm.MethodAdapter;
/*     */ import com.avaje.ebean.enhance.asm.MethodVisitor;
/*     */ import com.avaje.ebean.enhance.asm.Opcodes;
/*     */ import com.avaje.ebean.enhance.asm.Type;
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
/*     */ public class LocalVariablesSorter
/*     */   extends MethodAdapter
/*     */ {
/*  52 */   private static final Type OBJECT_TYPE = Type.getObjectType("java/lang/Object");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  59 */   private int[] mapping = new int[40];
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  64 */   private Object[] newLocals = new Object[20];
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final int firstLocal;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int nextLocal;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean changed;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LocalVariablesSorter(int access, String desc, MethodVisitor mv) {
/*  93 */     super(mv);
/*  94 */     Type[] args = Type.getArgumentTypes(desc);
/*  95 */     this.nextLocal = ((0x8 & access) == 0) ? 1 : 0;
/*  96 */     for (int i = 0; i < args.length; i++) {
/*  97 */       this.nextLocal += args[i].getSize();
/*     */     }
/*  99 */     this.firstLocal = this.nextLocal;
/*     */   }
/*     */   
/*     */   public void visitVarInsn(int opcode, int var) {
/*     */     Type type;
/* 104 */     switch (opcode) {
/*     */       case 22:
/*     */       case 55:
/* 107 */         type = Type.LONG_TYPE;
/*     */         break;
/*     */       
/*     */       case 24:
/*     */       case 57:
/* 112 */         type = Type.DOUBLE_TYPE;
/*     */         break;
/*     */       
/*     */       case 23:
/*     */       case 56:
/* 117 */         type = Type.FLOAT_TYPE;
/*     */         break;
/*     */       
/*     */       case 21:
/*     */       case 54:
/* 122 */         type = Type.INT_TYPE;
/*     */         break;
/*     */       
/*     */       case 25:
/*     */       case 58:
/* 127 */         type = OBJECT_TYPE;
/*     */         break;
/*     */ 
/*     */       
/*     */       default:
/* 132 */         type = Type.VOID_TYPE; break;
/*     */     } 
/* 134 */     this.mv.visitVarInsn(opcode, remap(var, type));
/*     */   }
/*     */ 
/*     */   
/* 138 */   public void visitIincInsn(int var, int increment) { this.mv.visitIincInsn(remap(var, Type.INT_TYPE), increment); }
/*     */ 
/*     */ 
/*     */   
/* 142 */   public void visitMaxs(int maxStack, int maxLocals) { this.mv.visitMaxs(maxStack, this.nextLocal); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void visitLocalVariable(String name, String desc, String signature, Label start, Label end, int index) {
/* 153 */     int newIndex = remap(index, Type.getType(desc));
/* 154 */     this.mv.visitLocalVariable(name, desc, signature, start, end, newIndex);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void visitFrame(int type, int nLocal, Object[] local, int nStack, Object[] stack) {
/* 164 */     if (type != -1) {
/* 165 */       throw new IllegalStateException("ClassReader.accept() should be called with EXPAND_FRAMES flag");
/*     */     }
/*     */     
/* 168 */     if (!this.changed) {
/* 169 */       this.mv.visitFrame(type, nLocal, local, nStack, stack);
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 174 */     Object[] oldLocals = new Object[this.newLocals.length];
/* 175 */     System.arraycopy(this.newLocals, 0, oldLocals, 0, oldLocals.length);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 180 */     int index = 0;
/* 181 */     int number = 0;
/* 182 */     for (; number < nLocal; number++) {
/* 183 */       Object t = local[number];
/* 184 */       int size = (t == Opcodes.LONG || t == Opcodes.DOUBLE) ? 2 : 1;
/* 185 */       if (t != Opcodes.TOP) {
/* 186 */         setFrameLocal(remap(index, size), t);
/*     */       }
/* 188 */       index += size;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 193 */     index = 0;
/* 194 */     number = 0;
/* 195 */     for (int i = 0; index < this.newLocals.length; i++) {
/* 196 */       Object t = this.newLocals[index++];
/* 197 */       if (t != null && t != Opcodes.TOP) {
/* 198 */         this.newLocals[i] = t;
/* 199 */         number = i + 1;
/* 200 */         if (t == Opcodes.LONG || t == Opcodes.DOUBLE) {
/* 201 */           index++;
/*     */         }
/*     */       } else {
/* 204 */         this.newLocals[i] = Opcodes.TOP;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 209 */     this.mv.visitFrame(type, number, this.newLocals, nStack, stack);
/*     */ 
/*     */     
/* 212 */     this.newLocals = oldLocals;
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
/*     */   public int newLocal(Type type) {
/* 225 */     switch (type.getSort())
/*     */     { case 1:
/*     */       case 2:
/*     */       case 3:
/*     */       case 4:
/*     */       case 5:
/* 231 */         t = Opcodes.INTEGER;
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
/* 250 */         local = this.nextLocal;
/* 251 */         this.nextLocal += type.getSize();
/* 252 */         setLocalType(local, type);
/* 253 */         setFrameLocal(local, t);
/* 254 */         return local;case 6: t = Opcodes.FLOAT; local = this.nextLocal; this.nextLocal += type.getSize(); setLocalType(local, type); setFrameLocal(local, t); return local;case 7: t = Opcodes.LONG; local = this.nextLocal; this.nextLocal += type.getSize(); setLocalType(local, type); setFrameLocal(local, t); return local;case 8: t = Opcodes.DOUBLE; local = this.nextLocal; this.nextLocal += type.getSize(); setLocalType(local, type); setFrameLocal(local, t); return local;case 9: t = type.getDescriptor(); local = this.nextLocal; this.nextLocal += type.getSize(); setLocalType(local, type); setFrameLocal(local, t); return local; }  Object t = type.getInternalName(); int local = this.nextLocal; this.nextLocal += type.getSize(); setLocalType(local, type); setFrameLocal(local, t); return local;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void setLocalType(int local, Type type) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void setFrameLocal(int local, Object type) {
/* 269 */     int l = this.newLocals.length;
/* 270 */     if (local >= l) {
/* 271 */       Object[] a = new Object[Math.max(2 * l, local + 1)];
/* 272 */       System.arraycopy(this.newLocals, 0, a, 0, l);
/* 273 */       this.newLocals = a;
/*     */     } 
/* 275 */     this.newLocals[local] = type;
/*     */   }
/*     */   
/*     */   private int remap(int var, Type type) {
/* 279 */     if (var < this.firstLocal) {
/* 280 */       return var;
/*     */     }
/* 282 */     int key = 2 * var + type.getSize() - 1;
/* 283 */     int size = this.mapping.length;
/* 284 */     if (key >= size) {
/* 285 */       int[] newMapping = new int[Math.max(2 * size, key + 1)];
/* 286 */       System.arraycopy(this.mapping, 0, newMapping, 0, size);
/* 287 */       this.mapping = newMapping;
/*     */     } 
/* 289 */     int value = this.mapping[key];
/* 290 */     if (value == 0) {
/* 291 */       value = newLocalMapping(type);
/* 292 */       setLocalType(value, type);
/* 293 */       this.mapping[key] = value + 1;
/*     */     } else {
/* 295 */       value--;
/*     */     } 
/* 297 */     if (value != var) {
/* 298 */       this.changed = true;
/*     */     }
/* 300 */     return value;
/*     */   }
/*     */   
/*     */   protected int newLocalMapping(Type type) {
/* 304 */     int local = this.nextLocal;
/* 305 */     this.nextLocal += type.getSize();
/* 306 */     return local;
/*     */   }
/*     */   
/*     */   private int remap(int var, int size) {
/* 310 */     if (var < this.firstLocal || !this.changed) {
/* 311 */       return var;
/*     */     }
/* 313 */     int key = 2 * var + size - 1;
/* 314 */     int value = (key < this.mapping.length) ? this.mapping[key] : 0;
/* 315 */     if (value == 0) {
/* 316 */       throw new IllegalStateException("Unknown local variable " + var);
/*     */     }
/* 318 */     return value - 1;
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebean\enhance\asm\commons\LocalVariablesSorter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */