/*     */ package com.avaje.ebean.enhance.asm;
/*     */ 
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.lang.reflect.Method;
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
/*     */ public class Type
/*     */ {
/*     */   public static final int VOID = 0;
/*     */   public static final int BOOLEAN = 1;
/*     */   public static final int CHAR = 2;
/*     */   public static final int BYTE = 3;
/*     */   public static final int SHORT = 4;
/*     */   public static final int INT = 5;
/*     */   public static final int FLOAT = 6;
/*     */   public static final int LONG = 7;
/*     */   public static final int DOUBLE = 8;
/*     */   public static final int ARRAY = 9;
/*     */   public static final int OBJECT = 10;
/* 103 */   public static final Type VOID_TYPE = new Type(false);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 108 */   public static final Type BOOLEAN_TYPE = new Type(true);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 113 */   public static final Type CHAR_TYPE = new Type(2);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 118 */   public static final Type BYTE_TYPE = new Type(3);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 123 */   public static final Type SHORT_TYPE = new Type(4);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 128 */   public static final Type INT_TYPE = new Type(5);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 133 */   public static final Type FLOAT_TYPE = new Type(6);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 138 */   public static final Type LONG_TYPE = new Type(7);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 143 */   public static final Type DOUBLE_TYPE = new Type(8);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final int sort;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final char[] buf;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final int off;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final int len;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 182 */   private Type(int sort) { this(sort, null, 0, 1); }
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
/*     */   private Type(int sort, char[] buf, int off, int len) {
/* 195 */     this.sort = sort;
/* 196 */     this.buf = buf;
/* 197 */     this.off = off;
/* 198 */     this.len = len;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 208 */   public static Type getType(String typeDescriptor) { return getType(typeDescriptor.toCharArray(), 0); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Type getObjectType(String internalName) {
/* 218 */     char[] buf = internalName.toCharArray();
/* 219 */     return new Type((buf[0] == '[') ? 9 : 10, buf, false, buf.length);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Type getType(Class c) {
/* 229 */     if (c.isPrimitive()) {
/* 230 */       if (c == int.class)
/* 231 */         return INT_TYPE; 
/* 232 */       if (c == void.class)
/* 233 */         return VOID_TYPE; 
/* 234 */       if (c == boolean.class)
/* 235 */         return BOOLEAN_TYPE; 
/* 236 */       if (c == byte.class)
/* 237 */         return BYTE_TYPE; 
/* 238 */       if (c == char.class)
/* 239 */         return CHAR_TYPE; 
/* 240 */       if (c == short.class)
/* 241 */         return SHORT_TYPE; 
/* 242 */       if (c == double.class)
/* 243 */         return DOUBLE_TYPE; 
/* 244 */       if (c == float.class) {
/* 245 */         return FLOAT_TYPE;
/*     */       }
/* 247 */       return LONG_TYPE;
/*     */     } 
/*     */     
/* 250 */     return getType(getDescriptor(c));
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
/*     */   public static Type[] getArgumentTypes(String methodDescriptor) {
/* 263 */     char[] buf = methodDescriptor.toCharArray();
/* 264 */     int off = 1;
/* 265 */     int size = 0;
/*     */     while (true) {
/* 267 */       char car = buf[off++];
/* 268 */       if (car == ')')
/*     */         break; 
/* 270 */       if (car == 'L') {
/* 271 */         while (buf[off++] != ';');
/*     */         
/* 273 */         size++; continue;
/* 274 */       }  if (car != '[') {
/* 275 */         size++;
/*     */       }
/*     */     } 
/* 278 */     Type[] args = new Type[size];
/* 279 */     off = 1;
/* 280 */     size = 0;
/* 281 */     while (buf[off] != ')') {
/* 282 */       args[size] = getType(buf, off);
/* 283 */       off += (args[size]).len + (((args[size]).sort == 10) ? 2 : 0);
/* 284 */       size++;
/*     */     } 
/* 286 */     return args;
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
/*     */   public static Type[] getArgumentTypes(Method method) {
/* 298 */     Class[] classes = method.getParameterTypes();
/* 299 */     Type[] types = new Type[classes.length];
/* 300 */     for (int i = classes.length - 1; i >= 0; i--) {
/* 301 */       types[i] = getType(classes[i]);
/*     */     }
/* 303 */     return types;
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
/*     */   public static Type getReturnType(String methodDescriptor) {
/* 315 */     char[] buf = methodDescriptor.toCharArray();
/* 316 */     return getType(buf, methodDescriptor.indexOf(')') + 1);
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
/* 328 */   public static Type getReturnType(Method method) { return getType(method.getReturnType()); }
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
/*     */   private static Type getType(char[] buf, int off) {
/* 340 */     switch (buf[off]) {
/*     */       case 'V':
/* 342 */         return VOID_TYPE;
/*     */       case 'Z':
/* 344 */         return BOOLEAN_TYPE;
/*     */       case 'C':
/* 346 */         return CHAR_TYPE;
/*     */       case 'B':
/* 348 */         return BYTE_TYPE;
/*     */       case 'S':
/* 350 */         return SHORT_TYPE;
/*     */       case 'I':
/* 352 */         return INT_TYPE;
/*     */       case 'F':
/* 354 */         return FLOAT_TYPE;
/*     */       case 'J':
/* 356 */         return LONG_TYPE;
/*     */       case 'D':
/* 358 */         return DOUBLE_TYPE;
/*     */       case '[':
/* 360 */         len = 1;
/* 361 */         while (buf[off + len] == '[') {
/* 362 */           len++;
/*     */         }
/* 364 */         if (buf[off + len] == 'L') {
/* 365 */           len++;
/* 366 */           while (buf[off + len] != ';') {
/* 367 */             len++;
/*     */           }
/*     */         } 
/* 370 */         return new Type(9, buf, off, len + 1);
/*     */     } 
/*     */     
/* 373 */     int len = 1;
/* 374 */     while (buf[off + len] != ';') {
/* 375 */       len++;
/*     */     }
/* 377 */     return new Type(10, buf, off + 1, len - 1);
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
/* 395 */   public int getSort() { return this.sort; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getDimensions() {
/* 405 */     int i = 1;
/* 406 */     while (this.buf[this.off + i] == '[') {
/* 407 */       i++;
/*     */     }
/* 409 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 419 */   public Type getElementType() { return getType(this.buf, this.off + getDimensions()); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getClassName() {
/*     */     int i;
/*     */     StringBuffer b;
/* 428 */     switch (this.sort) {
/*     */       case 0:
/* 430 */         return "void";
/*     */       case 1:
/* 432 */         return "boolean";
/*     */       case 2:
/* 434 */         return "char";
/*     */       case 3:
/* 436 */         return "byte";
/*     */       case 4:
/* 438 */         return "short";
/*     */       case 5:
/* 440 */         return "int";
/*     */       case 6:
/* 442 */         return "float";
/*     */       case 7:
/* 444 */         return "long";
/*     */       case 8:
/* 446 */         return "double";
/*     */       case 9:
/* 448 */         b = new StringBuffer(getElementType().getClassName());
/* 449 */         for (i = getDimensions(); i > 0; i--) {
/* 450 */           b.append("[]");
/*     */         }
/* 452 */         return b.toString();
/*     */     } 
/*     */     
/* 455 */     return (new String(this.buf, this.off, this.len)).replace('/', '.');
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
/* 468 */   public String getInternalName() { return new String(this.buf, this.off, this.len); }
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
/*     */   public String getDescriptor() {
/* 481 */     StringBuffer buf = new StringBuffer();
/* 482 */     getDescriptor(buf);
/* 483 */     return buf.toString();
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
/*     */   public static String getMethodDescriptor(Type returnType, Type[] argumentTypes) {
/* 499 */     StringBuffer buf = new StringBuffer();
/* 500 */     buf.append('(');
/* 501 */     for (int i = 0; i < argumentTypes.length; i++) {
/* 502 */       argumentTypes[i].getDescriptor(buf);
/*     */     }
/* 504 */     buf.append(')');
/* 505 */     returnType.getDescriptor(buf);
/* 506 */     return buf.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void getDescriptor(StringBuffer buf) {
/* 516 */     switch (this.sort) {
/*     */       case 0:
/* 518 */         buf.append('V');
/*     */         return;
/*     */       case 1:
/* 521 */         buf.append('Z');
/*     */         return;
/*     */       case 2:
/* 524 */         buf.append('C');
/*     */         return;
/*     */       case 3:
/* 527 */         buf.append('B');
/*     */         return;
/*     */       case 4:
/* 530 */         buf.append('S');
/*     */         return;
/*     */       case 5:
/* 533 */         buf.append('I');
/*     */         return;
/*     */       case 6:
/* 536 */         buf.append('F');
/*     */         return;
/*     */       case 7:
/* 539 */         buf.append('J');
/*     */         return;
/*     */       case 8:
/* 542 */         buf.append('D');
/*     */         return;
/*     */       case 9:
/* 545 */         buf.append(this.buf, this.off, this.len);
/*     */         return;
/*     */     } 
/*     */     
/* 549 */     buf.append('L');
/* 550 */     buf.append(this.buf, this.off, this.len);
/* 551 */     buf.append(';');
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
/* 569 */   public static String getInternalName(Class c) { return c.getName().replace('.', '/'); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getDescriptor(Class c) {
/* 579 */     StringBuffer buf = new StringBuffer();
/* 580 */     getDescriptor(buf, c);
/* 581 */     return buf.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getConstructorDescriptor(Constructor c) {
/* 591 */     Class[] parameters = c.getParameterTypes();
/* 592 */     StringBuffer buf = new StringBuffer();
/* 593 */     buf.append('(');
/* 594 */     for (int i = 0; i < parameters.length; i++) {
/* 595 */       getDescriptor(buf, parameters[i]);
/*     */     }
/* 597 */     return buf.append(")V").toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getMethodDescriptor(Method m) {
/* 607 */     Class[] parameters = m.getParameterTypes();
/* 608 */     StringBuffer buf = new StringBuffer();
/* 609 */     buf.append('(');
/* 610 */     for (int i = 0; i < parameters.length; i++) {
/* 611 */       getDescriptor(buf, parameters[i]);
/*     */     }
/* 613 */     buf.append(')');
/* 614 */     getDescriptor(buf, m.getReturnType());
/* 615 */     return buf.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void getDescriptor(StringBuffer buf, Class c) {
/* 625 */     Class d = c;
/*     */     while (true) {
/* 627 */       if (d.isPrimitive()) {
/*     */         char car;
/* 629 */         if (d == int.class) {
/* 630 */           car = 'I';
/* 631 */         } else if (d == void.class) {
/* 632 */           car = 'V';
/* 633 */         } else if (d == boolean.class) {
/* 634 */           car = 'Z';
/* 635 */         } else if (d == byte.class) {
/* 636 */           car = 'B';
/* 637 */         } else if (d == char.class) {
/* 638 */           car = 'C';
/* 639 */         } else if (d == short.class) {
/* 640 */           car = 'S';
/* 641 */         } else if (d == double.class) {
/* 642 */           car = 'D';
/* 643 */         } else if (d == float.class) {
/* 644 */           car = 'F';
/*     */         } else {
/* 646 */           car = 'J';
/*     */         } 
/* 648 */         buf.append(car); return;
/*     */       } 
/* 650 */       if (d.isArray()) {
/* 651 */         buf.append('[');
/* 652 */         d = d.getComponentType(); continue;
/*     */       }  break;
/* 654 */     }  buf.append('L');
/* 655 */     String name = d.getName();
/* 656 */     int len = name.length();
/* 657 */     for (int i = 0; i < len; i++) {
/* 658 */       char car = name.charAt(i);
/* 659 */       buf.append((car == '.') ? 47 : car);
/*     */     } 
/* 661 */     buf.append(';');
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
/* 678 */   public int getSize() { return (this.sort == 7 || this.sort == 8) ? 2 : 1; }
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
/*     */   public int getOpcode(int opcode) {
/* 692 */     if (opcode == 46 || opcode == 79) {
/* 693 */       switch (this.sort) {
/*     */         case 1:
/*     */         case 3:
/* 696 */           return opcode + 5;
/*     */         case 2:
/* 698 */           return opcode + 6;
/*     */         case 4:
/* 700 */           return opcode + 7;
/*     */         case 5:
/* 702 */           return opcode;
/*     */         case 6:
/* 704 */           return opcode + 2;
/*     */         case 7:
/* 706 */           return opcode + 1;
/*     */         case 8:
/* 708 */           return opcode + 3;
/*     */       } 
/*     */ 
/*     */       
/* 712 */       return opcode + 4;
/*     */     } 
/*     */     
/* 715 */     switch (this.sort) {
/*     */       case 0:
/* 717 */         return opcode + 5;
/*     */       case 1:
/*     */       case 2:
/*     */       case 3:
/*     */       case 4:
/*     */       case 5:
/* 723 */         return opcode;
/*     */       case 6:
/* 725 */         return opcode + 2;
/*     */       case 7:
/* 727 */         return opcode + 1;
/*     */       case 8:
/* 729 */         return opcode + 3;
/*     */     } 
/*     */ 
/*     */     
/* 733 */     return opcode + 4;
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
/*     */   public boolean equals(Object o) {
/* 749 */     if (this == o) {
/* 750 */       return true;
/*     */     }
/* 752 */     if (!(o instanceof Type)) {
/* 753 */       return false;
/*     */     }
/* 755 */     Type t = (Type)o;
/* 756 */     if (this.sort != t.sort) {
/* 757 */       return false;
/*     */     }
/* 759 */     if (this.sort == 10 || this.sort == 9) {
/* 760 */       if (this.len != t.len) {
/* 761 */         return false;
/*     */       }
/* 763 */       for (int i = this.off, j = t.off, end = i + this.len; i < end; i++, j++) {
/* 764 */         if (this.buf[i] != t.buf[j]) {
/* 765 */           return false;
/*     */         }
/*     */       } 
/*     */     } 
/* 769 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 778 */     int hc = 13 * this.sort;
/* 779 */     if (this.sort == 10 || this.sort == 9) {
/* 780 */       for (int i = this.off, end = i + this.len; i < end; i++) {
/* 781 */         hc = 17 * (hc + this.buf[i]);
/*     */       }
/*     */     }
/* 784 */     return hc;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 793 */   public String toString() { return getDescriptor(); }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebean\enhance\asm\Type.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */