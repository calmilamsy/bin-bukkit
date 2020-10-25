/*      */ package com.avaje.ebean.enhance.asm.commons;
/*      */ 
/*      */ import com.avaje.ebean.enhance.asm.ClassVisitor;
/*      */ import com.avaje.ebean.enhance.asm.Label;
/*      */ import com.avaje.ebean.enhance.asm.MethodVisitor;
/*      */ import com.avaje.ebean.enhance.asm.Type;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Arrays;
/*      */ import java.util.List;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class GeneratorAdapter
/*      */   extends LocalVariablesSorter
/*      */ {
/*      */   private static final String CLDESC = "Ljava/lang/Class;";
/*   87 */   private static final Type BYTE_TYPE = Type.getObjectType("java/lang/Byte");
/*      */   
/*   89 */   private static final Type BOOLEAN_TYPE = Type.getObjectType("java/lang/Boolean");
/*      */   
/*   91 */   private static final Type SHORT_TYPE = Type.getObjectType("java/lang/Short");
/*      */   
/*   93 */   private static final Type CHARACTER_TYPE = Type.getObjectType("java/lang/Character");
/*      */   
/*   95 */   private static final Type INTEGER_TYPE = Type.getObjectType("java/lang/Integer");
/*      */   
/*   97 */   private static final Type FLOAT_TYPE = Type.getObjectType("java/lang/Float");
/*      */   
/*   99 */   private static final Type LONG_TYPE = Type.getObjectType("java/lang/Long");
/*      */   
/*  101 */   private static final Type DOUBLE_TYPE = Type.getObjectType("java/lang/Double");
/*      */   
/*  103 */   private static final Type NUMBER_TYPE = Type.getObjectType("java/lang/Number");
/*      */   
/*  105 */   private static final Type OBJECT_TYPE = Type.getObjectType("java/lang/Object");
/*      */   
/*  107 */   private static final Method BOOLEAN_VALUE = Method.getMethod("boolean booleanValue()");
/*      */   
/*  109 */   private static final Method CHAR_VALUE = Method.getMethod("char charValue()");
/*      */   
/*  111 */   private static final Method INT_VALUE = Method.getMethod("int intValue()");
/*      */   
/*  113 */   private static final Method FLOAT_VALUE = Method.getMethod("float floatValue()");
/*      */   
/*  115 */   private static final Method LONG_VALUE = Method.getMethod("long longValue()");
/*      */   
/*  117 */   private static final Method DOUBLE_VALUE = Method.getMethod("double doubleValue()");
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final int ADD = 96;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final int SUB = 100;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final int MUL = 104;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final int DIV = 108;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final int REM = 112;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final int NEG = 116;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final int SHL = 120;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final int SHR = 122;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final int USHR = 124;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final int AND = 126;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final int OR = 128;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final int XOR = 130;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final int EQ = 153;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final int NE = 154;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final int LT = 155;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final int GE = 156;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final int GT = 157;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final int LE = 158;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final int access;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final Type returnType;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final Type[] argumentTypes;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  227 */   private final List localTypes = new ArrayList();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public GeneratorAdapter(MethodVisitor mv, int access, String name, String desc) {
/*  243 */     super(access, desc, mv);
/*  244 */     this.access = access;
/*  245 */     this.returnType = Type.getReturnType(desc);
/*  246 */     this.argumentTypes = Type.getArgumentTypes(desc);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public GeneratorAdapter(int access, Method method, MethodVisitor mv) {
/*  261 */     super(access, method.getDescriptor(), mv);
/*  262 */     this.access = access;
/*  263 */     this.returnType = method.getReturnType();
/*  264 */     this.argumentTypes = method.getArgumentTypes();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  285 */   public GeneratorAdapter(int access, Method method, String signature, Type[] exceptions, ClassVisitor cv) { this(access, method, cv.visitMethod(access, method.getName(), method.getDescriptor(), signature, getInternalNames(exceptions))); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static String[] getInternalNames(Type[] types) {
/*  299 */     if (types == null) {
/*  300 */       return null;
/*      */     }
/*  302 */     String[] names = new String[types.length];
/*  303 */     for (int i = 0; i < names.length; i++) {
/*  304 */       names[i] = types[i].getInternalName();
/*      */     }
/*  306 */     return names;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  319 */   public void push(boolean value) { push(value ? 1 : 0); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void push(int value) {
/*  328 */     if (value >= -1 && value <= 5) {
/*  329 */       this.mv.visitInsn(3 + value);
/*  330 */     } else if (value >= -128 && value <= 127) {
/*  331 */       this.mv.visitIntInsn(16, value);
/*  332 */     } else if (value >= -32768 && value <= 32767) {
/*  333 */       this.mv.visitIntInsn(17, value);
/*      */     } else {
/*  335 */       this.mv.visitLdcInsn(Integer.valueOf(value));
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void push(long value) {
/*  345 */     if (value == 0L || value == 1L) {
/*  346 */       this.mv.visitInsn(9 + (int)value);
/*      */     } else {
/*  348 */       this.mv.visitLdcInsn(Long.valueOf(value));
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void push(float value) {
/*  358 */     int bits = Float.floatToIntBits(value);
/*  359 */     if (bits == 0L || bits == 1065353216 || bits == 1073741824) {
/*  360 */       this.mv.visitInsn(11 + (int)value);
/*      */     } else {
/*  362 */       this.mv.visitLdcInsn(new Float(value));
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void push(double value) {
/*  372 */     long bits = Double.doubleToLongBits(value);
/*  373 */     if (bits == 0L || bits == 4607182418800017408L) {
/*  374 */       this.mv.visitInsn(14 + (int)value);
/*      */     } else {
/*  376 */       this.mv.visitLdcInsn(new Double(value));
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void push(String value) {
/*  386 */     if (value == null) {
/*  387 */       this.mv.visitInsn(1);
/*      */     } else {
/*  389 */       this.mv.visitLdcInsn(value);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void push(Type value) {
/*  399 */     if (value == null) {
/*  400 */       this.mv.visitInsn(1);
/*      */     } else {
/*  402 */       switch (value.getSort()) {
/*      */         case 1:
/*  404 */           this.mv.visitFieldInsn(178, "java/lang/Boolean", "TYPE", "Ljava/lang/Class;");
/*      */           return;
/*      */ 
/*      */ 
/*      */         
/*      */         case 2:
/*  410 */           this.mv.visitFieldInsn(178, "java/lang/Char", "TYPE", "Ljava/lang/Class;");
/*      */           return;
/*      */ 
/*      */ 
/*      */         
/*      */         case 3:
/*  416 */           this.mv.visitFieldInsn(178, "java/lang/Byte", "TYPE", "Ljava/lang/Class;");
/*      */           return;
/*      */ 
/*      */ 
/*      */         
/*      */         case 4:
/*  422 */           this.mv.visitFieldInsn(178, "java/lang/Short", "TYPE", "Ljava/lang/Class;");
/*      */           return;
/*      */ 
/*      */ 
/*      */         
/*      */         case 5:
/*  428 */           this.mv.visitFieldInsn(178, "java/lang/Integer", "TYPE", "Ljava/lang/Class;");
/*      */           return;
/*      */ 
/*      */ 
/*      */         
/*      */         case 6:
/*  434 */           this.mv.visitFieldInsn(178, "java/lang/Float", "TYPE", "Ljava/lang/Class;");
/*      */           return;
/*      */ 
/*      */ 
/*      */         
/*      */         case 7:
/*  440 */           this.mv.visitFieldInsn(178, "java/lang/Long", "TYPE", "Ljava/lang/Class;");
/*      */           return;
/*      */ 
/*      */ 
/*      */         
/*      */         case 8:
/*  446 */           this.mv.visitFieldInsn(178, "java/lang/Double", "TYPE", "Ljava/lang/Class;");
/*      */           return;
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/*  452 */       this.mv.visitLdcInsn(value);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int getArgIndex(int arg) {
/*  470 */     int index = ((this.access & 0x8) == 0) ? 1 : 0;
/*  471 */     for (int i = 0; i < arg; i++) {
/*  472 */       index += this.argumentTypes[i].getSize();
/*      */     }
/*  474 */     return index;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  484 */   private void loadInsn(Type type, int index) { this.mv.visitVarInsn(type.getOpcode(21), index); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  495 */   private void storeInsn(Type type, int index) { this.mv.visitVarInsn(type.getOpcode(54), index); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void loadThis() {
/*  502 */     if ((this.access & 0x8) != 0) {
/*  503 */       throw new IllegalStateException("no 'this' pointer within static method");
/*      */     }
/*  505 */     this.mv.visitVarInsn(25, 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  514 */   public void loadArg(int arg) { loadInsn(this.argumentTypes[arg], getArgIndex(arg)); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void loadArgs(int arg, int count) {
/*  525 */     int index = getArgIndex(arg);
/*  526 */     for (int i = 0; i < count; i++) {
/*  527 */       Type t = this.argumentTypes[arg + i];
/*  528 */       loadInsn(t, index);
/*  529 */       index += t.getSize();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  537 */   public void loadArgs() { loadArgs(0, this.argumentTypes.length); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void loadArgArray() {
/*  545 */     push(this.argumentTypes.length);
/*  546 */     newArray(OBJECT_TYPE);
/*  547 */     for (int i = 0; i < this.argumentTypes.length; i++) {
/*  548 */       dup();
/*  549 */       push(i);
/*  550 */       loadArg(i);
/*  551 */       box(this.argumentTypes[i]);
/*  552 */       arrayStore(OBJECT_TYPE);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  563 */   public void storeArg(int arg) { storeInsn(this.argumentTypes[arg], getArgIndex(arg)); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  578 */   public Type getLocalType(int local) { return (Type)this.localTypes.get(local - this.firstLocal); }
/*      */ 
/*      */   
/*      */   protected void setLocalType(int local, Type type) {
/*  582 */     int index = local - this.firstLocal;
/*  583 */     while (this.localTypes.size() < index + 1) {
/*  584 */       this.localTypes.add(null);
/*      */     }
/*  586 */     this.localTypes.set(index, type);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  596 */   public void loadLocal(int local) { loadInsn(getLocalType(local), local); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void loadLocal(int local, Type type) {
/*  607 */     setLocalType(local, type);
/*  608 */     loadInsn(type, local);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  619 */   public void storeLocal(int local) { storeInsn(getLocalType(local), local); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void storeLocal(int local, Type type) {
/*  631 */     setLocalType(local, type);
/*  632 */     storeInsn(type, local);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  641 */   public void arrayLoad(Type type) { this.mv.visitInsn(type.getOpcode(46)); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  650 */   public void arrayStore(Type type) { this.mv.visitInsn(type.getOpcode(79)); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  661 */   public void pop() { this.mv.visitInsn(87); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  668 */   public void pop2() { this.mv.visitInsn(88); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  675 */   public void dup() { this.mv.visitInsn(89); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  682 */   public void dup2() { this.mv.visitInsn(92); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  689 */   public void dupX1() { this.mv.visitInsn(90); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  696 */   public void dupX2() { this.mv.visitInsn(91); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  703 */   public void dup2X1() { this.mv.visitInsn(93); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  710 */   public void dup2X2() { this.mv.visitInsn(94); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  717 */   public void swap() { this.mv.visitInsn(95); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void swap(Type prev, Type type) {
/*  727 */     if (type.getSize() == 1) {
/*  728 */       if (prev.getSize() == 1) {
/*  729 */         swap();
/*      */       } else {
/*  731 */         dupX2();
/*  732 */         pop();
/*      */       }
/*      */     
/*  735 */     } else if (prev.getSize() == 1) {
/*  736 */       dup2X1();
/*  737 */       pop2();
/*      */     } else {
/*  739 */       dup2X2();
/*  740 */       pop2();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  758 */   public void math(int op, Type type) { this.mv.visitInsn(type.getOpcode(op)); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void not() {
/*  766 */     this.mv.visitInsn(4);
/*  767 */     this.mv.visitInsn(130);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  777 */   public void iinc(int local, int amount) { this.mv.visitIincInsn(local, amount); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void cast(Type from, Type to) {
/*  788 */     if (from != to) {
/*  789 */       if (from == Type.DOUBLE_TYPE) {
/*  790 */         if (to == Type.FLOAT_TYPE) {
/*  791 */           this.mv.visitInsn(144);
/*  792 */         } else if (to == Type.LONG_TYPE) {
/*  793 */           this.mv.visitInsn(143);
/*      */         } else {
/*  795 */           this.mv.visitInsn(142);
/*  796 */           cast(Type.INT_TYPE, to);
/*      */         } 
/*  798 */       } else if (from == Type.FLOAT_TYPE) {
/*  799 */         if (to == Type.DOUBLE_TYPE) {
/*  800 */           this.mv.visitInsn(141);
/*  801 */         } else if (to == Type.LONG_TYPE) {
/*  802 */           this.mv.visitInsn(140);
/*      */         } else {
/*  804 */           this.mv.visitInsn(139);
/*  805 */           cast(Type.INT_TYPE, to);
/*      */         } 
/*  807 */       } else if (from == Type.LONG_TYPE) {
/*  808 */         if (to == Type.DOUBLE_TYPE) {
/*  809 */           this.mv.visitInsn(138);
/*  810 */         } else if (to == Type.FLOAT_TYPE) {
/*  811 */           this.mv.visitInsn(137);
/*      */         } else {
/*  813 */           this.mv.visitInsn(136);
/*  814 */           cast(Type.INT_TYPE, to);
/*      */         }
/*      */       
/*  817 */       } else if (to == Type.BYTE_TYPE) {
/*  818 */         this.mv.visitInsn(145);
/*  819 */       } else if (to == Type.CHAR_TYPE) {
/*  820 */         this.mv.visitInsn(146);
/*  821 */       } else if (to == Type.DOUBLE_TYPE) {
/*  822 */         this.mv.visitInsn(135);
/*  823 */       } else if (to == Type.FLOAT_TYPE) {
/*  824 */         this.mv.visitInsn(134);
/*  825 */       } else if (to == Type.LONG_TYPE) {
/*  826 */         this.mv.visitInsn(133);
/*  827 */       } else if (to == Type.SHORT_TYPE) {
/*  828 */         this.mv.visitInsn(147);
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void box(Type type) {
/*  845 */     if (type.getSort() == 10 || type.getSort() == 9) {
/*      */       return;
/*      */     }
/*  848 */     if (type == Type.VOID_TYPE) {
/*  849 */       push((String)null);
/*      */     } else {
/*  851 */       Type boxed = type;
/*  852 */       switch (type.getSort()) {
/*      */         case 3:
/*  854 */           boxed = BYTE_TYPE;
/*      */           break;
/*      */         case 1:
/*  857 */           boxed = BOOLEAN_TYPE;
/*      */           break;
/*      */         case 4:
/*  860 */           boxed = SHORT_TYPE;
/*      */           break;
/*      */         case 2:
/*  863 */           boxed = CHARACTER_TYPE;
/*      */           break;
/*      */         case 5:
/*  866 */           boxed = INTEGER_TYPE;
/*      */           break;
/*      */         case 6:
/*  869 */           boxed = FLOAT_TYPE;
/*      */           break;
/*      */         case 7:
/*  872 */           boxed = LONG_TYPE;
/*      */           break;
/*      */         case 8:
/*  875 */           boxed = DOUBLE_TYPE;
/*      */           break;
/*      */       } 
/*  878 */       newInstance(boxed);
/*  879 */       if (type.getSize() == 2) {
/*      */         
/*  881 */         dupX2();
/*  882 */         dupX2();
/*  883 */         pop();
/*      */       } else {
/*      */         
/*  886 */         dupX1();
/*  887 */         swap();
/*      */       } 
/*  889 */       invokeConstructor(boxed, new Method("<init>", Type.VOID_TYPE, new Type[] { type }));
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void unbox(Type type) {
/*  902 */     Type t = NUMBER_TYPE;
/*  903 */     Method sig = null;
/*  904 */     switch (type.getSort()) {
/*      */       case 0:
/*      */         return;
/*      */       case 2:
/*  908 */         t = CHARACTER_TYPE;
/*  909 */         sig = CHAR_VALUE;
/*      */         break;
/*      */       case 1:
/*  912 */         t = BOOLEAN_TYPE;
/*  913 */         sig = BOOLEAN_VALUE;
/*      */         break;
/*      */       case 8:
/*  916 */         sig = DOUBLE_VALUE;
/*      */         break;
/*      */       case 6:
/*  919 */         sig = FLOAT_VALUE;
/*      */         break;
/*      */       case 7:
/*  922 */         sig = LONG_VALUE;
/*      */         break;
/*      */       case 3:
/*      */       case 4:
/*      */       case 5:
/*  927 */         sig = INT_VALUE; break;
/*      */     } 
/*  929 */     if (sig == null) {
/*  930 */       checkCast(type);
/*      */     } else {
/*  932 */       checkCast(t);
/*  933 */       invokeVirtual(t, sig);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  947 */   public Label newLabel() { return new Label(); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  956 */   public void mark(Label label) { this.mv.visitLabel(label); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Label mark() {
/*  965 */     Label label = new Label();
/*  966 */     this.mv.visitLabel(label);
/*  967 */     return label;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void ifCmp(Type type, int mode, Label label) {
/*  980 */     int intOp = -1;
/*  981 */     switch (type.getSort()) {
/*      */       case 7:
/*  983 */         this.mv.visitInsn(148);
/*      */         break;
/*      */       case 8:
/*  986 */         this.mv.visitInsn(152);
/*      */         break;
/*      */       case 6:
/*  989 */         this.mv.visitInsn(150);
/*      */         break;
/*      */       case 9:
/*      */       case 10:
/*  993 */         switch (mode) {
/*      */           case 153:
/*  995 */             this.mv.visitJumpInsn(165, label);
/*      */             return;
/*      */           case 154:
/*  998 */             this.mv.visitJumpInsn(166, label);
/*      */             return;
/*      */         } 
/* 1001 */         throw new IllegalArgumentException("Bad comparison for type " + type);
/*      */       
/*      */       default:
/* 1004 */         switch (mode) {
/*      */           case 153:
/* 1006 */             intOp = 159;
/*      */             break;
/*      */           case 154:
/* 1009 */             intOp = 160;
/*      */             break;
/*      */           case 156:
/* 1012 */             intOp = 162;
/*      */             break;
/*      */           case 155:
/* 1015 */             intOp = 161;
/*      */             break;
/*      */           case 158:
/* 1018 */             intOp = 164;
/*      */             break;
/*      */           case 157:
/* 1021 */             intOp = 163;
/*      */             break;
/*      */         } 
/* 1024 */         this.mv.visitJumpInsn(intOp, label);
/*      */         return;
/*      */     } 
/* 1027 */     int jumpMode = mode;
/* 1028 */     switch (mode) {
/*      */       case 156:
/* 1030 */         jumpMode = 155;
/*      */         break;
/*      */       case 158:
/* 1033 */         jumpMode = 157;
/*      */         break;
/*      */     } 
/* 1036 */     this.mv.visitJumpInsn(jumpMode, label);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1048 */   public void ifICmp(int mode, Label label) { ifCmp(Type.INT_TYPE, mode, label); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1060 */   public void ifZCmp(int mode, Label label) { this.mv.visitJumpInsn(mode, label); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1070 */   public void ifNull(Label label) { this.mv.visitJumpInsn(198, label); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1080 */   public void ifNonNull(Label label) { this.mv.visitJumpInsn(199, label); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1089 */   public void goTo(Label label) { this.mv.visitJumpInsn(167, label); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1099 */   public void ret(int local) { this.mv.visitVarInsn(169, local); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void tableSwitch(int[] keys, TableSwitchGenerator generator) {
/*      */     float density;
/* 1113 */     if (keys.length == 0) {
/* 1114 */       density = 0.0F;
/*      */     } else {
/* 1116 */       density = keys.length / (keys[keys.length - 1] - keys[0] + 1);
/*      */     } 
/*      */     
/* 1119 */     tableSwitch(keys, generator, (density >= 0.5F));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void tableSwitch(int[] keys, TableSwitchGenerator generator, boolean useTable) {
/* 1135 */     for (i = 1; i < keys.length; i++) {
/* 1136 */       if (keys[i] < keys[i - 1]) {
/* 1137 */         throw new IllegalArgumentException("keys must be sorted ascending");
/*      */       }
/*      */     } 
/* 1140 */     Label def = newLabel();
/* 1141 */     Label end = newLabel();
/* 1142 */     if (keys.length > 0) {
/* 1143 */       int len = keys.length;
/* 1144 */       int min = keys[0];
/* 1145 */       int max = keys[len - 1];
/* 1146 */       int range = max - min + 1;
/* 1147 */       if (useTable) {
/* 1148 */         Label[] labels = new Label[range];
/* 1149 */         Arrays.fill(labels, def);
/* 1150 */         for (i = 0; i < len; i++) {
/* 1151 */           labels[keys[i] - min] = newLabel();
/*      */         }
/* 1153 */         this.mv.visitTableSwitchInsn(min, max, def, labels);
/* 1154 */         for (int i = 0; i < range; i++) {
/* 1155 */           Label label = labels[i];
/* 1156 */           if (label != def) {
/* 1157 */             mark(label);
/* 1158 */             generator.generateCase(i + min, end);
/*      */           } 
/*      */         } 
/*      */       } else {
/* 1162 */         Label[] labels = new Label[len];
/* 1163 */         for (i = 0; i < len; i++) {
/* 1164 */           labels[i] = newLabel();
/*      */         }
/* 1166 */         this.mv.visitLookupSwitchInsn(def, keys, labels);
/* 1167 */         for (int i = 0; i < len; i++) {
/* 1168 */           mark(labels[i]);
/* 1169 */           generator.generateCase(keys[i], end);
/*      */         } 
/*      */       } 
/*      */     } 
/* 1173 */     mark(def);
/* 1174 */     generator.generateDefault();
/* 1175 */     mark(end);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1182 */   public void returnValue() { this.mv.visitInsn(this.returnType.getOpcode(172)); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1203 */   private void fieldInsn(int opcode, Type ownerType, String name, Type fieldType) { this.mv.visitFieldInsn(opcode, ownerType.getInternalName(), name, fieldType.getDescriptor()); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1219 */   public void getStatic(Type owner, String name, Type type) { fieldInsn(178, owner, name, type); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1231 */   public void putStatic(Type owner, String name, Type type) { fieldInsn(179, owner, name, type); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1243 */   public void getField(Type owner, String name, Type type) { fieldInsn(180, owner, name, type); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1255 */   public void putField(Type owner, String name, Type type) { fieldInsn(181, owner, name, type); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void invokeInsn(int opcode, Type type, Method method) {
/* 1274 */     String owner = (type.getSort() == 9) ? type.getDescriptor() : type.getInternalName();
/*      */ 
/*      */     
/* 1277 */     this.mv.visitMethodInsn(opcode, owner, method.getName(), method.getDescriptor());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1290 */   public void invokeVirtual(Type owner, Method method) { invokeInsn(182, owner, method); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1300 */   public void invokeConstructor(Type type, Method method) { invokeInsn(183, type, method); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1310 */   public void invokeStatic(Type owner, Method method) { invokeInsn(184, owner, method); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1320 */   public void invokeInterface(Type owner, Method method) { invokeInsn(185, owner, method); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1334 */   private void typeInsn(int opcode, Type type) { this.mv.visitTypeInsn(opcode, type.getInternalName()); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1343 */   public void newInstance(Type type) { typeInsn(187, type); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void newArray(Type type) {
/*      */     int typ;
/* 1353 */     switch (type.getSort()) {
/*      */       case 1:
/* 1355 */         typ = 4;
/*      */         break;
/*      */       case 2:
/* 1358 */         typ = 5;
/*      */         break;
/*      */       case 3:
/* 1361 */         typ = 8;
/*      */         break;
/*      */       case 4:
/* 1364 */         typ = 9;
/*      */         break;
/*      */       case 5:
/* 1367 */         typ = 10;
/*      */         break;
/*      */       case 6:
/* 1370 */         typ = 6;
/*      */         break;
/*      */       case 7:
/* 1373 */         typ = 11;
/*      */         break;
/*      */       case 8:
/* 1376 */         typ = 7;
/*      */         break;
/*      */       default:
/* 1379 */         typeInsn(189, type);
/*      */         return;
/*      */     } 
/* 1382 */     this.mv.visitIntInsn(188, typ);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1393 */   public void arrayLength() { this.mv.visitInsn(190); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1400 */   public void throwException() { this.mv.visitInsn(191); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void throwException(Type type, String msg) {
/* 1411 */     newInstance(type);
/* 1412 */     dup();
/* 1413 */     push(msg);
/* 1414 */     invokeConstructor(type, Method.getMethod("void <init> (String)"));
/* 1415 */     throwException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void checkCast(Type type) {
/* 1425 */     if (!type.equals(OBJECT_TYPE)) {
/* 1426 */       typeInsn(192, type);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1437 */   public void instanceOf(Type type) { typeInsn(193, type); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1444 */   public void monitorEnter() { this.mv.visitInsn(194); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1451 */   public void monitorExit() { this.mv.visitInsn(195); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void endMethod() {
/* 1462 */     if ((this.access & 0x400) == 0) {
/* 1463 */       this.mv.visitMaxs(0, 0);
/*      */     }
/* 1465 */     this.mv.visitEnd();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1481 */   public void catchException(Label start, Label end, Type exception) { this.mv.visitTryCatchBlock(start, end, mark(), exception.getInternalName()); }
/*      */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebean\enhance\asm\commons\GeneratorAdapter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */