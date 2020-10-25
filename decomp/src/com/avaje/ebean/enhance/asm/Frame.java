/*      */ package com.avaje.ebean.enhance.asm;
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
/*      */ final class Frame
/*      */ {
/*      */   static final int DIM = -268435456;
/*      */   static final int ARRAY_OF = 268435456;
/*      */   static final int ELEMENT_OF = -268435456;
/*      */   static final int KIND = 251658240;
/*      */   static final int VALUE = 16777215;
/*      */   static final int BASE_KIND = 267386880;
/*      */   static final int BASE_VALUE = 1048575;
/*      */   static final int BASE = 16777216;
/*      */   static final int OBJECT = 24117248;
/*      */   static final int UNINITIALIZED = 25165824;
/*      */   private static final int LOCAL = 33554432;
/*      */   private static final int STACK = 50331648;
/*      */   static final int TOP = 16777216;
/*      */   static final int BOOLEAN = 16777225;
/*      */   static final int BYTE = 16777226;
/*      */   static final int CHAR = 16777227;
/*      */   static final int SHORT = 16777228;
/*      */   static final int INTEGER = 16777217;
/*      */   static final int FLOAT = 16777218;
/*      */   static final int DOUBLE = 16777219;
/*      */   static final int LONG = 16777220;
/*      */   static final int NULL = 16777221;
/*      */   static final int UNINITIALIZED_THIS = 16777222;
/*      */   static final int[] SIZE;
/*      */   Label owner;
/*      */   int[] inputLocals;
/*      */   int[] inputStack;
/*      */   private int[] outputLocals;
/*      */   private int[] outputStack;
/*      */   private int outputStackTop;
/*      */   private int initializationCount;
/*      */   private int[] initializations;
/*      */   
/*      */   static  {
/*  230 */     int[] b = new int[202];
/*  231 */     String s = "EFFFFFFFFGGFFFGGFFFEEFGFGFEEEEEEEEEEEEEEEEEEEEDEDEDDDDDCDCDEEEEEEEEEEEEEEEEEEEEBABABBBBDCFFFGGGEDCDCDCDCDCDCDCDCDCDCEEEEDDDDDDDCDCDCEFEFDDEEFFDEDEEEBDDBBDDDDDDCCCCCCCCEFEDDDCDCDEEEEEEEEEEFEEEEEEDDEEDDEE";
/*      */ 
/*      */ 
/*      */     
/*  235 */     for (i = 0; i < b.length; i++) {
/*  236 */       b[i] = s.charAt(i) - 'E';
/*      */     }
/*  238 */     SIZE = b;
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
/*      */   private int get(int local) {
/*  521 */     if (this.outputLocals == null || local >= this.outputLocals.length)
/*      */     {
/*      */       
/*  524 */       return 0x2000000 | local;
/*      */     }
/*  526 */     int type = this.outputLocals[local];
/*  527 */     if (type == 0)
/*      */     {
/*      */       
/*  530 */       type = this.outputLocals[local] = 0x2000000 | local;
/*      */     }
/*  532 */     return type;
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
/*      */   private void set(int local, int type) {
/*  544 */     if (this.outputLocals == null) {
/*  545 */       this.outputLocals = new int[10];
/*      */     }
/*  547 */     int n = this.outputLocals.length;
/*  548 */     if (local >= n) {
/*  549 */       int[] t = new int[Math.max(local + 1, 2 * n)];
/*  550 */       System.arraycopy(this.outputLocals, 0, t, 0, n);
/*  551 */       this.outputLocals = t;
/*      */     } 
/*      */     
/*  554 */     this.outputLocals[local] = type;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void push(int type) {
/*  564 */     if (this.outputStack == null) {
/*  565 */       this.outputStack = new int[10];
/*      */     }
/*  567 */     int n = this.outputStack.length;
/*  568 */     if (this.outputStackTop >= n) {
/*  569 */       int[] t = new int[Math.max(this.outputStackTop + 1, 2 * n)];
/*  570 */       System.arraycopy(this.outputStack, 0, t, 0, n);
/*  571 */       this.outputStack = t;
/*      */     } 
/*      */     
/*  574 */     this.outputStack[this.outputStackTop++] = type;
/*      */     
/*  576 */     int top = this.owner.inputStackTop + this.outputStackTop;
/*  577 */     if (top > this.owner.outputStackMax) {
/*  578 */       this.owner.outputStackMax = top;
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
/*      */   private void push(ClassWriter cw, String desc) {
/*  591 */     int type = type(cw, desc);
/*  592 */     if (type != 0) {
/*  593 */       push(type);
/*  594 */       if (type == 16777220 || type == 16777219) {
/*  595 */         push(16777216);
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
/*      */   private static int type(ClassWriter cw, String desc) {
/*  609 */     int index = (desc.charAt(0) == '(') ? (desc.indexOf(')') + 1) : 0;
/*  610 */     switch (desc.charAt(index)) {
/*      */       case 'V':
/*  612 */         return 0;
/*      */       case 'B':
/*      */       case 'C':
/*      */       case 'I':
/*      */       case 'S':
/*      */       case 'Z':
/*  618 */         return 16777217;
/*      */       case 'F':
/*  620 */         return 16777218;
/*      */       case 'J':
/*  622 */         return 16777220;
/*      */       case 'D':
/*  624 */         return 16777219;
/*      */       
/*      */       case 'L':
/*  627 */         t = desc.substring(index + 1, desc.length() - 1);
/*  628 */         return 0x1700000 | cw.addType(t);
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  633 */     int dims = index + 1;
/*  634 */     while (desc.charAt(dims) == '[') {
/*  635 */       dims++;
/*      */     }
/*  637 */     switch (desc.charAt(dims))
/*      */     { case 'Z':
/*  639 */         data = 16777225;
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
/*  668 */         return dims - index << 28 | data;case 'C': data = 16777227; return dims - index << 28 | data;case 'B': data = 16777226; return dims - index << 28 | data;case 'S': data = 16777228; return dims - index << 28 | data;case 'I': data = 16777217; return dims - index << 28 | data;case 'F': data = 16777218; return dims - index << 28 | data;case 'J': data = 16777220; return dims - index << 28 | data;case 'D': data = 16777219; return dims - index << 28 | data; }  String t = desc.substring(dims + 1, desc.length() - 1); int data = 0x1700000 | cw.addType(t); return dims - index << 28 | data;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int pop() {
/*  678 */     if (this.outputStackTop > 0) {
/*  679 */       return this.outputStack[--this.outputStackTop];
/*      */     }
/*      */     
/*  682 */     return 0x3000000 | ---this.owner.inputStackTop;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void pop(int elements) {
/*  692 */     if (this.outputStackTop >= elements) {
/*  693 */       this.outputStackTop -= elements;
/*      */     
/*      */     }
/*      */     else {
/*      */       
/*  698 */       this.owner.inputStackTop -= elements - this.outputStackTop;
/*  699 */       this.outputStackTop = 0;
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
/*      */   private void pop(String desc) {
/*  711 */     char c = desc.charAt(0);
/*  712 */     if (c == '(') {
/*  713 */       pop((MethodWriter.getArgumentsAndReturnSizes(desc) >> 2) - 1);
/*  714 */     } else if (c == 'J' || c == 'D') {
/*  715 */       pop(2);
/*      */     } else {
/*  717 */       pop(1);
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
/*      */   private void init(int var) {
/*  729 */     if (this.initializations == null) {
/*  730 */       this.initializations = new int[2];
/*      */     }
/*  732 */     int n = this.initializations.length;
/*  733 */     if (this.initializationCount >= n) {
/*  734 */       int[] t = new int[Math.max(this.initializationCount + 1, 2 * n)];
/*  735 */       System.arraycopy(this.initializations, 0, t, 0, n);
/*  736 */       this.initializations = t;
/*      */     } 
/*      */     
/*  739 */     this.initializations[this.initializationCount++] = var;
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
/*      */   private int init(ClassWriter cw, int t) {
/*      */     int s;
/*  753 */     if (t == 16777222) {
/*  754 */       s = 0x1700000 | cw.addType(cw.thisName);
/*  755 */     } else if ((t & 0xFFF00000) == 25165824) {
/*  756 */       String type = (cw.typeTable[t & 0xFFFFF]).strVal1;
/*  757 */       s = 0x1700000 | cw.addType(type);
/*      */     } else {
/*  759 */       return t;
/*      */     } 
/*  761 */     for (int j = 0; j < this.initializationCount; j++) {
/*  762 */       int u = this.initializations[j];
/*  763 */       int dim = u & 0xF0000000;
/*  764 */       int kind = u & 0xF000000;
/*  765 */       if (kind == 33554432) {
/*  766 */         u = dim + this.inputLocals[u & 0xFFFFFF];
/*  767 */       } else if (kind == 50331648) {
/*  768 */         u = dim + this.inputStack[this.inputStack.length - (u & 0xFFFFFF)];
/*      */       } 
/*  770 */       if (t == u) {
/*  771 */         return s;
/*      */       }
/*      */     } 
/*  774 */     return t;
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
/*      */   void initInputFrame(ClassWriter cw, int access, Type[] args, int maxLocals) {
/*  792 */     this.inputLocals = new int[maxLocals];
/*  793 */     this.inputStack = new int[0];
/*  794 */     int i = 0;
/*  795 */     if ((access & 0x8) == 0) {
/*  796 */       if ((access & 0x40000) == 0) {
/*  797 */         this.inputLocals[i++] = 0x1700000 | cw.addType(cw.thisName);
/*      */       } else {
/*  799 */         this.inputLocals[i++] = 16777222;
/*      */       } 
/*      */     }
/*  802 */     for (int j = 0; j < args.length; j++) {
/*  803 */       int t = type(cw, args[j].getDescriptor());
/*  804 */       this.inputLocals[i++] = t;
/*  805 */       if (t == 16777220 || t == 16777219) {
/*  806 */         this.inputLocals[i++] = 16777216;
/*      */       }
/*      */     } 
/*  809 */     while (i < maxLocals) {
/*  810 */       this.inputLocals[i++] = 16777216;
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
/*      */   void execute(int opcode, int arg, ClassWriter cw, Item item) {
/*      */     String s;
/*      */     int t4;
/*      */     int t3;
/*      */     int i;
/*      */     int t1;
/*  829 */     switch (opcode) {
/*      */       case 0:
/*      */       case 116:
/*      */       case 117:
/*      */       case 118:
/*      */       case 119:
/*      */       case 145:
/*      */       case 146:
/*      */       case 147:
/*      */       case 167:
/*      */       case 177:
/*      */         break;
/*      */       case 1:
/*  842 */         push(16777221);
/*      */         break;
/*      */       case 2:
/*      */       case 3:
/*      */       case 4:
/*      */       case 5:
/*      */       case 6:
/*      */       case 7:
/*      */       case 8:
/*      */       case 16:
/*      */       case 17:
/*      */       case 21:
/*  854 */         push(16777217);
/*      */         break;
/*      */       case 9:
/*      */       case 10:
/*      */       case 22:
/*  859 */         push(16777220);
/*  860 */         push(16777216);
/*      */         break;
/*      */       case 11:
/*      */       case 12:
/*      */       case 13:
/*      */       case 23:
/*  866 */         push(16777218);
/*      */         break;
/*      */       case 14:
/*      */       case 15:
/*      */       case 24:
/*  871 */         push(16777219);
/*  872 */         push(16777216);
/*      */         break;
/*      */       case 18:
/*  875 */         switch (item.type) {
/*      */           case 3:
/*  877 */             push(16777217);
/*      */             break;
/*      */           case 5:
/*  880 */             push(16777220);
/*  881 */             push(16777216);
/*      */             break;
/*      */           case 4:
/*  884 */             push(16777218);
/*      */             break;
/*      */           case 6:
/*  887 */             push(16777219);
/*  888 */             push(16777216);
/*      */             break;
/*      */           case 7:
/*  891 */             push(0x1700000 | cw.addType("java/lang/Class"));
/*      */             break;
/*      */         } 
/*      */         
/*  895 */         push(0x1700000 | cw.addType("java/lang/String"));
/*      */         break;
/*      */       
/*      */       case 25:
/*  899 */         push(get(arg));
/*      */         break;
/*      */       case 46:
/*      */       case 51:
/*      */       case 52:
/*      */       case 53:
/*  905 */         pop(2);
/*  906 */         push(16777217);
/*      */         break;
/*      */       case 47:
/*      */       case 143:
/*  910 */         pop(2);
/*  911 */         push(16777220);
/*  912 */         push(16777216);
/*      */         break;
/*      */       case 48:
/*  915 */         pop(2);
/*  916 */         push(16777218);
/*      */         break;
/*      */       case 49:
/*      */       case 138:
/*  920 */         pop(2);
/*  921 */         push(16777219);
/*  922 */         push(16777216);
/*      */         break;
/*      */       case 50:
/*  925 */         pop(1);
/*  926 */         t1 = pop();
/*  927 */         push(-268435456 + t1);
/*      */         break;
/*      */       case 54:
/*      */       case 56:
/*      */       case 58:
/*  932 */         t1 = pop();
/*  933 */         set(arg, t1);
/*  934 */         if (arg > 0) {
/*  935 */           int t2 = get(arg - 1);
/*      */           
/*  937 */           if (t2 == 16777220 || t2 == 16777219) {
/*  938 */             set(arg - 1, 16777216);
/*      */           }
/*      */         } 
/*      */         break;
/*      */       case 55:
/*      */       case 57:
/*  944 */         pop(1);
/*  945 */         t1 = pop();
/*  946 */         set(arg, t1);
/*  947 */         set(arg + 1, 16777216);
/*  948 */         if (arg > 0) {
/*  949 */           int j = get(arg - 1);
/*      */           
/*  951 */           if (j == 16777220 || j == 16777219) {
/*  952 */             set(arg - 1, 16777216);
/*      */           }
/*      */         } 
/*      */         break;
/*      */       case 79:
/*      */       case 81:
/*      */       case 83:
/*      */       case 84:
/*      */       case 85:
/*      */       case 86:
/*  962 */         pop(3);
/*      */         break;
/*      */       case 80:
/*      */       case 82:
/*  966 */         pop(4);
/*      */         break;
/*      */       case 87:
/*      */       case 153:
/*      */       case 154:
/*      */       case 155:
/*      */       case 156:
/*      */       case 157:
/*      */       case 158:
/*      */       case 170:
/*      */       case 171:
/*      */       case 172:
/*      */       case 174:
/*      */       case 176:
/*      */       case 191:
/*      */       case 194:
/*      */       case 195:
/*      */       case 198:
/*      */       case 199:
/*  985 */         pop(1);
/*      */         break;
/*      */       case 88:
/*      */       case 159:
/*      */       case 160:
/*      */       case 161:
/*      */       case 162:
/*      */       case 163:
/*      */       case 164:
/*      */       case 165:
/*      */       case 166:
/*      */       case 173:
/*      */       case 175:
/*  998 */         pop(2);
/*      */         break;
/*      */       case 89:
/* 1001 */         t1 = pop();
/* 1002 */         push(t1);
/* 1003 */         push(t1);
/*      */         break;
/*      */       case 90:
/* 1006 */         t1 = pop();
/* 1007 */         i = pop();
/* 1008 */         push(t1);
/* 1009 */         push(i);
/* 1010 */         push(t1);
/*      */         break;
/*      */       case 91:
/* 1013 */         t1 = pop();
/* 1014 */         i = pop();
/* 1015 */         t3 = pop();
/* 1016 */         push(t1);
/* 1017 */         push(t3);
/* 1018 */         push(i);
/* 1019 */         push(t1);
/*      */         break;
/*      */       case 92:
/* 1022 */         t1 = pop();
/* 1023 */         i = pop();
/* 1024 */         push(i);
/* 1025 */         push(t1);
/* 1026 */         push(i);
/* 1027 */         push(t1);
/*      */         break;
/*      */       case 93:
/* 1030 */         t1 = pop();
/* 1031 */         i = pop();
/* 1032 */         t3 = pop();
/* 1033 */         push(i);
/* 1034 */         push(t1);
/* 1035 */         push(t3);
/* 1036 */         push(i);
/* 1037 */         push(t1);
/*      */         break;
/*      */       case 94:
/* 1040 */         t1 = pop();
/* 1041 */         i = pop();
/* 1042 */         t3 = pop();
/* 1043 */         t4 = pop();
/* 1044 */         push(i);
/* 1045 */         push(t1);
/* 1046 */         push(t4);
/* 1047 */         push(t3);
/* 1048 */         push(i);
/* 1049 */         push(t1);
/*      */         break;
/*      */       case 95:
/* 1052 */         t1 = pop();
/* 1053 */         i = pop();
/* 1054 */         push(t1);
/* 1055 */         push(i);
/*      */         break;
/*      */       case 96:
/*      */       case 100:
/*      */       case 104:
/*      */       case 108:
/*      */       case 112:
/*      */       case 120:
/*      */       case 122:
/*      */       case 124:
/*      */       case 126:
/*      */       case 128:
/*      */       case 130:
/*      */       case 136:
/*      */       case 142:
/*      */       case 149:
/*      */       case 150:
/* 1072 */         pop(2);
/* 1073 */         push(16777217);
/*      */         break;
/*      */       case 97:
/*      */       case 101:
/*      */       case 105:
/*      */       case 109:
/*      */       case 113:
/*      */       case 127:
/*      */       case 129:
/*      */       case 131:
/* 1083 */         pop(4);
/* 1084 */         push(16777220);
/* 1085 */         push(16777216);
/*      */         break;
/*      */       case 98:
/*      */       case 102:
/*      */       case 106:
/*      */       case 110:
/*      */       case 114:
/*      */       case 137:
/*      */       case 144:
/* 1094 */         pop(2);
/* 1095 */         push(16777218);
/*      */         break;
/*      */       case 99:
/*      */       case 103:
/*      */       case 107:
/*      */       case 111:
/*      */       case 115:
/* 1102 */         pop(4);
/* 1103 */         push(16777219);
/* 1104 */         push(16777216);
/*      */         break;
/*      */       case 121:
/*      */       case 123:
/*      */       case 125:
/* 1109 */         pop(3);
/* 1110 */         push(16777220);
/* 1111 */         push(16777216);
/*      */         break;
/*      */       case 132:
/* 1114 */         set(arg, 16777217);
/*      */         break;
/*      */       case 133:
/*      */       case 140:
/* 1118 */         pop(1);
/* 1119 */         push(16777220);
/* 1120 */         push(16777216);
/*      */         break;
/*      */       case 134:
/* 1123 */         pop(1);
/* 1124 */         push(16777218);
/*      */         break;
/*      */       case 135:
/*      */       case 141:
/* 1128 */         pop(1);
/* 1129 */         push(16777219);
/* 1130 */         push(16777216);
/*      */         break;
/*      */       case 139:
/*      */       case 190:
/*      */       case 193:
/* 1135 */         pop(1);
/* 1136 */         push(16777217);
/*      */         break;
/*      */       case 148:
/*      */       case 151:
/*      */       case 152:
/* 1141 */         pop(4);
/* 1142 */         push(16777217);
/*      */         break;
/*      */       case 168:
/*      */       case 169:
/* 1146 */         throw new RuntimeException("JSR/RET are not supported with computeFrames option");
/*      */       case 178:
/* 1148 */         push(cw, item.strVal3);
/*      */         break;
/*      */       case 179:
/* 1151 */         pop(item.strVal3);
/*      */         break;
/*      */       case 180:
/* 1154 */         pop(1);
/* 1155 */         push(cw, item.strVal3);
/*      */         break;
/*      */       case 181:
/* 1158 */         pop(item.strVal3);
/* 1159 */         pop();
/*      */         break;
/*      */       case 182:
/*      */       case 183:
/*      */       case 184:
/*      */       case 185:
/* 1165 */         pop(item.strVal3);
/* 1166 */         if (opcode != 184) {
/* 1167 */           t1 = pop();
/* 1168 */           if (opcode == 183 && item.strVal2.charAt(0) == '<')
/*      */           {
/*      */             
/* 1171 */             init(t1);
/*      */           }
/*      */         } 
/* 1174 */         push(cw, item.strVal3);
/*      */         break;
/*      */       case 187:
/* 1177 */         push(0x1800000 | cw.addUninitializedType(item.strVal1, arg));
/*      */         break;
/*      */       case 188:
/* 1180 */         pop();
/* 1181 */         switch (arg) {
/*      */           case 4:
/* 1183 */             push(285212681);
/*      */             break;
/*      */           case 5:
/* 1186 */             push(285212683);
/*      */             break;
/*      */           case 8:
/* 1189 */             push(285212682);
/*      */             break;
/*      */           case 9:
/* 1192 */             push(285212684);
/*      */             break;
/*      */           case 10:
/* 1195 */             push(285212673);
/*      */             break;
/*      */           case 6:
/* 1198 */             push(285212674);
/*      */             break;
/*      */           case 7:
/* 1201 */             push(285212675);
/*      */             break;
/*      */         } 
/*      */         
/* 1205 */         push(285212676);
/*      */         break;
/*      */ 
/*      */       
/*      */       case 189:
/* 1210 */         s = item.strVal1;
/* 1211 */         pop();
/* 1212 */         if (s.charAt(0) == '[') {
/* 1213 */           push(cw, '[' + s); break;
/*      */         } 
/* 1215 */         push(0x11700000 | cw.addType(s));
/*      */         break;
/*      */       
/*      */       case 192:
/* 1219 */         s = item.strVal1;
/* 1220 */         pop();
/* 1221 */         if (s.charAt(0) == '[') {
/* 1222 */           push(cw, s); break;
/*      */         } 
/* 1224 */         push(0x1700000 | cw.addType(s));
/*      */         break;
/*      */ 
/*      */       
/*      */       default:
/* 1229 */         pop(arg);
/* 1230 */         push(cw, item.strVal1);
/*      */         break;
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
/*      */   boolean merge(ClassWriter cw, Frame frame, int edge) {
/* 1248 */     changed = false;
/*      */ 
/*      */     
/* 1251 */     int nLocal = this.inputLocals.length;
/* 1252 */     int nStack = this.inputStack.length;
/* 1253 */     if (frame.inputLocals == null) {
/* 1254 */       frame.inputLocals = new int[nLocal];
/* 1255 */       changed = true;
/*      */     } 
/*      */     int i;
/* 1258 */     for (i = 0; i < nLocal; i++) {
/* 1259 */       int t; if (this.outputLocals != null && i < this.outputLocals.length) {
/* 1260 */         int s = this.outputLocals[i];
/* 1261 */         if (s == 0) {
/* 1262 */           t = this.inputLocals[i];
/*      */         } else {
/* 1264 */           int dim = s & 0xF0000000;
/* 1265 */           int kind = s & 0xF000000;
/* 1266 */           if (kind == 33554432) {
/* 1267 */             t = dim + this.inputLocals[s & 0xFFFFFF];
/* 1268 */           } else if (kind == 50331648) {
/* 1269 */             t = dim + this.inputStack[nStack - (s & 0xFFFFFF)];
/*      */           } else {
/* 1271 */             t = s;
/*      */           } 
/*      */         } 
/*      */       } else {
/* 1275 */         t = this.inputLocals[i];
/*      */       } 
/* 1277 */       if (this.initializations != null) {
/* 1278 */         t = init(cw, t);
/*      */       }
/* 1280 */       changed |= merge(cw, t, frame.inputLocals, i);
/*      */     } 
/*      */     
/* 1283 */     if (edge > 0) {
/* 1284 */       for (i = 0; i < nLocal; i++) {
/* 1285 */         int t = this.inputLocals[i];
/* 1286 */         changed |= merge(cw, t, frame.inputLocals, i);
/*      */       } 
/* 1288 */       if (frame.inputStack == null) {
/* 1289 */         frame.inputStack = new int[1];
/* 1290 */         changed = true;
/*      */       } 
/* 1292 */       return merge(cw, edge, frame.inputStack, 0);
/*      */     } 
/*      */ 
/*      */     
/* 1296 */     int nInputStack = this.inputStack.length + this.owner.inputStackTop;
/* 1297 */     if (frame.inputStack == null) {
/* 1298 */       frame.inputStack = new int[nInputStack + this.outputStackTop];
/* 1299 */       changed = true;
/*      */     } 
/*      */     
/* 1302 */     for (i = 0; i < nInputStack; i++) {
/* 1303 */       int t = this.inputStack[i];
/* 1304 */       if (this.initializations != null) {
/* 1305 */         t = init(cw, t);
/*      */       }
/* 1307 */       changed |= merge(cw, t, frame.inputStack, i);
/*      */     } 
/* 1309 */     for (i = 0; i < this.outputStackTop; i++) {
/* 1310 */       int t, s = this.outputStack[i];
/* 1311 */       int dim = s & 0xF0000000;
/* 1312 */       int kind = s & 0xF000000;
/* 1313 */       if (kind == 33554432) {
/* 1314 */         t = dim + this.inputLocals[s & 0xFFFFFF];
/* 1315 */       } else if (kind == 50331648) {
/* 1316 */         t = dim + this.inputStack[nStack - (s & 0xFFFFFF)];
/*      */       } else {
/* 1318 */         t = s;
/*      */       } 
/* 1320 */       if (this.initializations != null) {
/* 1321 */         t = init(cw, t);
/*      */       }
/* 1323 */       changed |= merge(cw, t, frame.inputStack, nInputStack + i);
/*      */     } 
/* 1325 */     return changed;
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
/*      */   private static boolean merge(ClassWriter cw, int t, int[] types, int index) {
/* 1346 */     int v, u = types[index];
/* 1347 */     if (u == t)
/*      */     {
/* 1349 */       return false;
/*      */     }
/* 1351 */     if ((t & 0xFFFFFFF) == 16777221) {
/* 1352 */       if (u == 16777221) {
/* 1353 */         return false;
/*      */       }
/* 1355 */       t = 16777221;
/*      */     } 
/* 1357 */     if (u == 0) {
/*      */       
/* 1359 */       types[index] = t;
/* 1360 */       return true;
/*      */     } 
/*      */     
/* 1363 */     if ((u & 0xFF00000) == 24117248 || (u & 0xF0000000) != 0) {
/*      */       
/* 1365 */       if (t == 16777221)
/*      */       {
/* 1367 */         return false; } 
/* 1368 */       if ((t & 0xFFF00000) == (u & 0xFFF00000)) {
/* 1369 */         if ((u & 0xFF00000) == 24117248) {
/*      */ 
/*      */ 
/*      */           
/* 1373 */           v = t & 0xF0000000 | 0x1700000 | cw.getMergedType(t & 0xFFFFF, u & 0xFFFFF);
/*      */         
/*      */         }
/*      */         else {
/*      */           
/* 1378 */           v = 0x1700000 | cw.addType("java/lang/Object");
/*      */         } 
/* 1380 */       } else if ((t & 0xFF00000) == 24117248 || (t & 0xF0000000) != 0) {
/*      */ 
/*      */         
/* 1383 */         v = 0x1700000 | cw.addType("java/lang/Object");
/*      */       } else {
/*      */         
/* 1386 */         v = 16777216;
/*      */       } 
/* 1388 */     } else if (u == 16777221) {
/*      */ 
/*      */       
/* 1391 */       v = ((t & 0xFF00000) == 24117248 || (t & 0xF0000000) != 0) ? t : 16777216;
/*      */     } else {
/*      */       
/* 1394 */       v = 16777216;
/*      */     } 
/* 1396 */     if (u != v) {
/* 1397 */       types[index] = v;
/* 1398 */       return true;
/*      */     } 
/* 1400 */     return false;
/*      */   }
/*      */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebean\enhance\asm\Frame.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */