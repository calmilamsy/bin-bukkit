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
/*      */ class MethodWriter
/*      */   implements MethodVisitor
/*      */ {
/*      */   static final int ACC_CONSTRUCTOR = 262144;
/*      */   static final int SAME_FRAME = 0;
/*      */   static final int SAME_LOCALS_1_STACK_ITEM_FRAME = 64;
/*      */   static final int RESERVED = 128;
/*      */   static final int SAME_LOCALS_1_STACK_ITEM_FRAME_EXTENDED = 247;
/*      */   static final int CHOP_FRAME = 248;
/*      */   static final int SAME_FRAME_EXTENDED = 251;
/*      */   static final int APPEND_FRAME = 252;
/*      */   static final int FULL_FRAME = 255;
/*      */   private static final int FRAMES = 0;
/*      */   private static final int MAXS = 1;
/*      */   private static final int NOTHING = 2;
/*      */   MethodWriter next;
/*      */   final ClassWriter cw;
/*      */   private int access;
/*      */   private final int name;
/*      */   private final int desc;
/*      */   private final String descriptor;
/*      */   String signature;
/*      */   int classReaderOffset;
/*      */   int classReaderLength;
/*      */   int exceptionCount;
/*      */   int[] exceptions;
/*      */   private ByteVector annd;
/*      */   private AnnotationWriter anns;
/*      */   private AnnotationWriter ianns;
/*      */   private AnnotationWriter[] panns;
/*      */   private AnnotationWriter[] ipanns;
/*      */   private int synthetics;
/*      */   private Attribute attrs;
/*  224 */   private ByteVector code = new ByteVector();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int maxStack;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int maxLocals;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int frameCount;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private ByteVector stackMap;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int previousFrameOffset;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int[] previousFrame;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int frameIndex;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int[] frame;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int handlerCount;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Handler firstHandler;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Handler lastHandler;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int localVarCount;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private ByteVector localVar;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int localVarTypeCount;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private ByteVector localVarType;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int lineNumberCount;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private ByteVector lineNumber;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Attribute cattrs;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean resize;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int subroutines;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final int compute;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Label labels;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Label previousBlock;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Label currentBlock;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int stackSize;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int maxStackSize;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   MethodWriter(ClassWriter cw, int access, String name, String desc, String signature, String[] exceptions, boolean computeMaxs, boolean computeFrames) {
/*  421 */     if (cw.firstMethod == null) {
/*  422 */       cw.firstMethod = this;
/*      */     } else {
/*  424 */       cw.lastMethod.next = this;
/*      */     } 
/*  426 */     cw.lastMethod = this;
/*  427 */     this.cw = cw;
/*  428 */     this.access = access;
/*  429 */     this.name = cw.newUTF8(name);
/*  430 */     this.desc = cw.newUTF8(desc);
/*  431 */     this.descriptor = desc;
/*      */     
/*  433 */     this.signature = signature;
/*      */     
/*  435 */     if (exceptions != null && exceptions.length > 0) {
/*  436 */       this.exceptionCount = exceptions.length;
/*  437 */       this.exceptions = new int[this.exceptionCount];
/*  438 */       for (int i = 0; i < this.exceptionCount; i++) {
/*  439 */         this.exceptions[i] = cw.newClass(exceptions[i]);
/*      */       }
/*      */     } 
/*  442 */     this.compute = computeFrames ? 0 : (computeMaxs ? 1 : 2);
/*  443 */     if (computeMaxs || computeFrames) {
/*  444 */       if (computeFrames && "<init>".equals(name)) {
/*  445 */         this.access |= 0x40000;
/*      */       }
/*      */       
/*  448 */       int size = getArgumentsAndReturnSizes(this.descriptor) >> 2;
/*  449 */       if ((access & 0x8) != 0) {
/*  450 */         size--;
/*      */       }
/*  452 */       this.maxLocals = size;
/*      */       
/*  454 */       this.labels = new Label();
/*  455 */       this.labels.status |= 0x8;
/*  456 */       visitLabel(this.labels);
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
/*      */   public AnnotationVisitor visitAnnotationDefault() {
/*  468 */     this.annd = new ByteVector();
/*  469 */     return new AnnotationWriter(this.cw, false, this.annd, null, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
/*  479 */     ByteVector bv = new ByteVector();
/*      */     
/*  481 */     bv.putShort(this.cw.newUTF8(desc)).putShort(0);
/*  482 */     AnnotationWriter aw = new AnnotationWriter(this.cw, true, bv, bv, 2);
/*  483 */     if (visible) {
/*  484 */       aw.next = this.anns;
/*  485 */       this.anns = aw;
/*      */     } else {
/*  487 */       aw.next = this.ianns;
/*  488 */       this.ianns = aw;
/*      */     } 
/*  490 */     return aw;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public AnnotationVisitor visitParameterAnnotation(int parameter, String desc, boolean visible) {
/*  501 */     ByteVector bv = new ByteVector();
/*  502 */     if ("Ljava/lang/Synthetic;".equals(desc)) {
/*      */ 
/*      */       
/*  505 */       this.synthetics = Math.max(this.synthetics, parameter + 1);
/*  506 */       return new AnnotationWriter(this.cw, false, bv, null, false);
/*      */     } 
/*      */     
/*  509 */     bv.putShort(this.cw.newUTF8(desc)).putShort(0);
/*  510 */     AnnotationWriter aw = new AnnotationWriter(this.cw, true, bv, bv, 2);
/*  511 */     if (visible) {
/*  512 */       if (this.panns == null) {
/*  513 */         this.panns = new AnnotationWriter[Type.getArgumentTypes(this.descriptor).length];
/*      */       }
/*  515 */       aw.next = this.panns[parameter];
/*  516 */       this.panns[parameter] = aw;
/*      */     } else {
/*  518 */       if (this.ipanns == null) {
/*  519 */         this.ipanns = new AnnotationWriter[Type.getArgumentTypes(this.descriptor).length];
/*      */       }
/*  521 */       aw.next = this.ipanns[parameter];
/*  522 */       this.ipanns[parameter] = aw;
/*      */     } 
/*  524 */     return aw;
/*      */   }
/*      */   
/*      */   public void visitAttribute(Attribute attr) {
/*  528 */     if (attr.isCodeAttribute()) {
/*  529 */       attr.next = this.cattrs;
/*  530 */       this.cattrs = attr;
/*      */     } else {
/*  532 */       attr.next = this.attrs;
/*  533 */       this.attrs = attr;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void visitCode() {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void visitFrame(int type, int nLocal, Object[] local, int nStack, Object[] stack) {
/*  547 */     if (this.compute == 0) {
/*      */       return;
/*      */     }
/*      */     
/*  551 */     if (type == -1) {
/*  552 */       startFrame(this.code.length, nLocal, nStack);
/*  553 */       for (i = 0; i < nLocal; i++) {
/*  554 */         if (local[i] instanceof String) {
/*  555 */           this.frame[this.frameIndex++] = 0x1700000 | this.cw.addType((String)local[i]);
/*      */         }
/*  557 */         else if (local[i] instanceof Integer) {
/*  558 */           this.frame[this.frameIndex++] = ((Integer)local[i]).intValue();
/*      */         } else {
/*  560 */           this.frame[this.frameIndex++] = 0x1800000 | this.cw.addUninitializedType("", ((Label)local[i]).position);
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/*  565 */       for (int i = 0; i < nStack; i++) {
/*  566 */         if (stack[i] instanceof String) {
/*  567 */           this.frame[this.frameIndex++] = 0x1700000 | this.cw.addType((String)stack[i]);
/*      */         }
/*  569 */         else if (stack[i] instanceof Integer) {
/*  570 */           this.frame[this.frameIndex++] = ((Integer)stack[i]).intValue();
/*      */         } else {
/*  572 */           this.frame[this.frameIndex++] = 0x1800000 | this.cw.addUninitializedType("", ((Label)stack[i]).position);
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/*  577 */       endFrame();
/*      */     } else {
/*      */       int i, delta;
/*  580 */       if (this.stackMap == null) {
/*  581 */         this.stackMap = new ByteVector();
/*  582 */         delta = this.code.length;
/*      */       } else {
/*  584 */         delta = this.code.length - this.previousFrameOffset - 1;
/*      */       } 
/*      */       
/*  587 */       switch (type) {
/*      */         case 0:
/*  589 */           this.stackMap.putByte(255).putShort(delta).putShort(nLocal);
/*      */ 
/*      */           
/*  592 */           for (i = 0; i < nLocal; i++) {
/*  593 */             writeFrameType(local[i]);
/*      */           }
/*  595 */           this.stackMap.putShort(nStack);
/*  596 */           for (i = 0; i < nStack; i++) {
/*  597 */             writeFrameType(stack[i]);
/*      */           }
/*      */           break;
/*      */         case 1:
/*  601 */           this.stackMap.putByte(251 + nLocal).putShort(delta);
/*      */           
/*  603 */           for (i = 0; i < nLocal; i++) {
/*  604 */             writeFrameType(local[i]);
/*      */           }
/*      */           break;
/*      */         case 2:
/*  608 */           this.stackMap.putByte(251 - nLocal).putShort(delta);
/*      */           break;
/*      */         
/*      */         case 3:
/*  612 */           if (delta < 64) {
/*  613 */             this.stackMap.putByte(delta); break;
/*      */           } 
/*  615 */           this.stackMap.putByte(251).putShort(delta);
/*      */           break;
/*      */         
/*      */         case 4:
/*  619 */           if (delta < 64) {
/*  620 */             this.stackMap.putByte(64 + delta);
/*      */           } else {
/*  622 */             this.stackMap.putByte(247).putShort(delta);
/*      */           } 
/*      */           
/*  625 */           writeFrameType(stack[0]);
/*      */           break;
/*      */       } 
/*      */       
/*  629 */       this.previousFrameOffset = this.code.length;
/*  630 */       this.frameCount++;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void visitInsn(int opcode) {
/*  636 */     this.code.putByte(opcode);
/*      */ 
/*      */     
/*  639 */     if (this.currentBlock != null) {
/*  640 */       if (this.compute == 0) {
/*  641 */         this.currentBlock.frame.execute(opcode, 0, null, null);
/*      */       } else {
/*      */         
/*  644 */         int size = this.stackSize + Frame.SIZE[opcode];
/*  645 */         if (size > this.maxStackSize) {
/*  646 */           this.maxStackSize = size;
/*      */         }
/*  648 */         this.stackSize = size;
/*      */       } 
/*      */       
/*  651 */       if ((opcode >= 172 && opcode <= 177) || opcode == 191)
/*      */       {
/*      */         
/*  654 */         noSuccessor();
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void visitIntInsn(int opcode, int operand) {
/*  661 */     if (this.currentBlock != null) {
/*  662 */       if (this.compute == 0) {
/*  663 */         this.currentBlock.frame.execute(opcode, operand, null, null);
/*  664 */       } else if (opcode != 188) {
/*      */ 
/*      */         
/*  667 */         int size = this.stackSize + 1;
/*  668 */         if (size > this.maxStackSize) {
/*  669 */           this.maxStackSize = size;
/*      */         }
/*  671 */         this.stackSize = size;
/*      */       } 
/*      */     }
/*      */     
/*  675 */     if (opcode == 17) {
/*  676 */       this.code.put12(opcode, operand);
/*      */     } else {
/*  678 */       this.code.put11(opcode, operand);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void visitVarInsn(int opcode, int var) {
/*  684 */     if (this.currentBlock != null) {
/*  685 */       if (this.compute == 0) {
/*  686 */         this.currentBlock.frame.execute(opcode, var, null, null);
/*      */       
/*      */       }
/*  689 */       else if (opcode == 169) {
/*      */         
/*  691 */         this.currentBlock.status |= 0x100;
/*      */ 
/*      */         
/*  694 */         this.currentBlock.inputStackTop = this.stackSize;
/*  695 */         noSuccessor();
/*      */       } else {
/*  697 */         int size = this.stackSize + Frame.SIZE[opcode];
/*  698 */         if (size > this.maxStackSize) {
/*  699 */           this.maxStackSize = size;
/*      */         }
/*  701 */         this.stackSize = size;
/*      */       } 
/*      */     }
/*      */     
/*  705 */     if (this.compute != 2) {
/*      */       int n;
/*      */       
/*  708 */       if (opcode == 22 || opcode == 24 || opcode == 55 || opcode == 57) {
/*      */ 
/*      */         
/*  711 */         n = var + 2;
/*      */       } else {
/*  713 */         n = var + 1;
/*      */       } 
/*  715 */       if (n > this.maxLocals) {
/*  716 */         this.maxLocals = n;
/*      */       }
/*      */     } 
/*      */     
/*  720 */     if (var < 4 && opcode != 169) {
/*      */       int opt;
/*  722 */       if (opcode < 54) {
/*      */         
/*  724 */         opt = 26 + (opcode - 21 << 2) + var;
/*      */       } else {
/*      */         
/*  727 */         opt = 59 + (opcode - 54 << 2) + var;
/*      */       } 
/*  729 */       this.code.putByte(opt);
/*  730 */     } else if (var >= 256) {
/*  731 */       this.code.putByte(196).put12(opcode, var);
/*      */     } else {
/*  733 */       this.code.put11(opcode, var);
/*      */     } 
/*  735 */     if (opcode >= 54 && this.compute == 0 && this.handlerCount > 0) {
/*  736 */       visitLabel(new Label());
/*      */     }
/*      */   }
/*      */   
/*      */   public void visitTypeInsn(int opcode, String type) {
/*  741 */     Item i = this.cw.newClassItem(type);
/*      */     
/*  743 */     if (this.currentBlock != null) {
/*  744 */       if (this.compute == 0) {
/*  745 */         this.currentBlock.frame.execute(opcode, this.code.length, this.cw, i);
/*  746 */       } else if (opcode == 187) {
/*      */ 
/*      */         
/*  749 */         int size = this.stackSize + 1;
/*  750 */         if (size > this.maxStackSize) {
/*  751 */           this.maxStackSize = size;
/*      */         }
/*  753 */         this.stackSize = size;
/*      */       } 
/*      */     }
/*      */     
/*  757 */     this.code.put12(opcode, i.index);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void visitFieldInsn(int opcode, String owner, String name, String desc) {
/*  766 */     Item i = this.cw.newFieldItem(owner, name, desc);
/*      */     
/*  768 */     if (this.currentBlock != null) {
/*  769 */       if (this.compute == 0) {
/*  770 */         this.currentBlock.frame.execute(opcode, 0, this.cw, i);
/*      */       } else {
/*      */         int size;
/*      */         
/*  774 */         char c = desc.charAt(0);
/*  775 */         switch (opcode) {
/*      */           case 178:
/*  777 */             size = this.stackSize + ((c == 'D' || c == 'J') ? 2 : 1);
/*      */             break;
/*      */           case 179:
/*  780 */             size = this.stackSize + ((c == 'D' || c == 'J') ? -2 : -1);
/*      */             break;
/*      */           case 180:
/*  783 */             size = this.stackSize + ((c == 'D' || c == 'J') ? 1 : 0);
/*      */             break;
/*      */           
/*      */           default:
/*  787 */             size = this.stackSize + ((c == 'D' || c == 'J') ? -3 : -2);
/*      */             break;
/*      */         } 
/*      */         
/*  791 */         if (size > this.maxStackSize) {
/*  792 */           this.maxStackSize = size;
/*      */         }
/*  794 */         this.stackSize = size;
/*      */       } 
/*      */     }
/*      */     
/*  798 */     this.code.put12(opcode, i.index);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void visitMethodInsn(int opcode, String owner, String name, String desc) {
/*  807 */     boolean itf = (opcode == 185);
/*  808 */     Item i = this.cw.newMethodItem(owner, name, desc, itf);
/*  809 */     int argSize = i.intVal;
/*      */     
/*  811 */     if (this.currentBlock != null) {
/*  812 */       if (this.compute == 0) {
/*  813 */         this.currentBlock.frame.execute(opcode, 0, this.cw, i);
/*      */       } else {
/*      */         int size;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  823 */         if (argSize == 0) {
/*      */ 
/*      */           
/*  826 */           argSize = getArgumentsAndReturnSizes(desc);
/*      */ 
/*      */           
/*  829 */           i.intVal = argSize;
/*      */         } 
/*      */         
/*  832 */         if (opcode == 184) {
/*  833 */           size = this.stackSize - (argSize >> 2) + (argSize & 0x3) + 1;
/*      */         } else {
/*  835 */           size = this.stackSize - (argSize >> 2) + (argSize & 0x3);
/*      */         } 
/*      */         
/*  838 */         if (size > this.maxStackSize) {
/*  839 */           this.maxStackSize = size;
/*      */         }
/*  841 */         this.stackSize = size;
/*      */       } 
/*      */     }
/*      */     
/*  845 */     if (itf) {
/*  846 */       if (argSize == 0) {
/*  847 */         argSize = getArgumentsAndReturnSizes(desc);
/*  848 */         i.intVal = argSize;
/*      */       } 
/*  850 */       this.code.put12(185, i.index).put11(argSize >> 2, 0);
/*      */     } else {
/*  852 */       this.code.put12(opcode, i.index);
/*      */     } 
/*      */   }
/*      */   
/*      */   public void visitJumpInsn(int opcode, Label label) {
/*  857 */     Label nextInsn = null;
/*      */     
/*  859 */     if (this.currentBlock != null) {
/*  860 */       if (this.compute == 0) {
/*  861 */         this.currentBlock.frame.execute(opcode, 0, null, null);
/*      */         
/*  863 */         (label.getFirst()).status |= 0x10;
/*      */         
/*  865 */         addSuccessor(0, label);
/*  866 */         if (opcode != 167)
/*      */         {
/*  868 */           nextInsn = new Label();
/*      */         }
/*      */       }
/*  871 */       else if (opcode == 168) {
/*  872 */         if ((label.status & 0x200) == 0) {
/*  873 */           label.status |= 0x200;
/*  874 */           this.subroutines++;
/*      */         } 
/*  876 */         this.currentBlock.status |= 0x80;
/*  877 */         addSuccessor(this.stackSize + 1, label);
/*      */         
/*  879 */         nextInsn = new Label();
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       }
/*      */       else {
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  890 */         this.stackSize += Frame.SIZE[opcode];
/*  891 */         addSuccessor(this.stackSize, label);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*  896 */     if ((label.status & 0x2) != 0 && label.position - this.code.length < -32768) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  906 */       if (opcode == 167) {
/*  907 */         this.code.putByte(200);
/*  908 */       } else if (opcode == 168) {
/*  909 */         this.code.putByte(201);
/*      */       }
/*      */       else {
/*      */         
/*  913 */         if (nextInsn != null) {
/*  914 */           nextInsn.status |= 0x10;
/*      */         }
/*  916 */         this.code.putByte((opcode <= 166) ? ((opcode + 1 ^ true) - 1) : (opcode ^ true));
/*      */ 
/*      */         
/*  919 */         this.code.putShort(8);
/*  920 */         this.code.putByte(200);
/*      */       } 
/*  922 */       label.put(this, this.code, this.code.length - 1, true);
/*      */ 
/*      */     
/*      */     }
/*      */     else {
/*      */ 
/*      */ 
/*      */       
/*  930 */       this.code.putByte(opcode);
/*  931 */       label.put(this, this.code, this.code.length - 1, false);
/*      */     } 
/*  933 */     if (this.currentBlock != null) {
/*  934 */       if (nextInsn != null)
/*      */       {
/*      */ 
/*      */ 
/*      */         
/*  939 */         visitLabel(nextInsn);
/*      */       }
/*  941 */       if (opcode == 167) {
/*  942 */         noSuccessor();
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void visitLabel(Label label) {
/*  949 */     this.resize |= label.resolve(this, this.code.length, this.code.data);
/*      */     
/*  951 */     if ((label.status & true) != 0) {
/*      */       return;
/*      */     }
/*  954 */     if (this.compute == 0) {
/*  955 */       if (this.currentBlock != null) {
/*  956 */         if (label.position == this.currentBlock.position) {
/*      */           
/*  958 */           this.currentBlock.status |= label.status & 0x10;
/*  959 */           label.frame = this.currentBlock.frame;
/*      */           
/*      */           return;
/*      */         } 
/*  963 */         addSuccessor(0, label);
/*      */       } 
/*      */       
/*  966 */       this.currentBlock = label;
/*  967 */       if (label.frame == null) {
/*  968 */         label.frame = new Frame();
/*  969 */         label.frame.owner = label;
/*      */       } 
/*      */       
/*  972 */       if (this.previousBlock != null) {
/*  973 */         if (label.position == this.previousBlock.position) {
/*  974 */           this.previousBlock.status |= label.status & 0x10;
/*  975 */           label.frame = this.previousBlock.frame;
/*  976 */           this.currentBlock = this.previousBlock;
/*      */           return;
/*      */         } 
/*  979 */         this.previousBlock.successor = label;
/*      */       } 
/*  981 */       this.previousBlock = label;
/*  982 */     } else if (this.compute == 1) {
/*  983 */       if (this.currentBlock != null) {
/*      */         
/*  985 */         this.currentBlock.outputStackMax = this.maxStackSize;
/*  986 */         addSuccessor(this.stackSize, label);
/*      */       } 
/*      */       
/*  989 */       this.currentBlock = label;
/*      */       
/*  991 */       this.stackSize = 0;
/*  992 */       this.maxStackSize = 0;
/*      */       
/*  994 */       if (this.previousBlock != null) {
/*  995 */         this.previousBlock.successor = label;
/*      */       }
/*  997 */       this.previousBlock = label;
/*      */     } 
/*      */   }
/*      */   
/*      */   public void visitLdcInsn(Object cst) {
/* 1002 */     Item i = this.cw.newConstItem(cst);
/*      */     
/* 1004 */     if (this.currentBlock != null) {
/* 1005 */       if (this.compute == 0) {
/* 1006 */         this.currentBlock.frame.execute(18, 0, this.cw, i);
/*      */       } else {
/*      */         int size;
/*      */         
/* 1010 */         if (i.type == 5 || i.type == 6) {
/*      */           
/* 1012 */           size = this.stackSize + 2;
/*      */         } else {
/* 1014 */           size = this.stackSize + 1;
/*      */         } 
/*      */         
/* 1017 */         if (size > this.maxStackSize) {
/* 1018 */           this.maxStackSize = size;
/*      */         }
/* 1020 */         this.stackSize = size;
/*      */       } 
/*      */     }
/*      */     
/* 1024 */     int index = i.index;
/* 1025 */     if (i.type == 5 || i.type == 6) {
/* 1026 */       this.code.put12(20, index);
/* 1027 */     } else if (index >= 256) {
/* 1028 */       this.code.put12(19, index);
/*      */     } else {
/* 1030 */       this.code.put11(18, index);
/*      */     } 
/*      */   }
/*      */   
/*      */   public void visitIincInsn(int var, int increment) {
/* 1035 */     if (this.currentBlock != null && 
/* 1036 */       this.compute == 0) {
/* 1037 */       this.currentBlock.frame.execute(132, var, null, null);
/*      */     }
/*      */     
/* 1040 */     if (this.compute != 2) {
/*      */       
/* 1042 */       int n = var + 1;
/* 1043 */       if (n > this.maxLocals) {
/* 1044 */         this.maxLocals = n;
/*      */       }
/*      */     } 
/*      */     
/* 1048 */     if (var > 255 || increment > 127 || increment < -128) {
/* 1049 */       this.code.putByte(196).put12(132, var).putShort(increment);
/*      */     }
/*      */     else {
/*      */       
/* 1053 */       this.code.putByte(132).put11(var, increment);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void visitTableSwitchInsn(int min, int max, Label dflt, Label[] labels) {
/* 1064 */     int source = this.code.length;
/* 1065 */     this.code.putByte(170);
/* 1066 */     this.code.length += (4 - this.code.length % 4) % 4;
/* 1067 */     dflt.put(this, this.code, source, true);
/* 1068 */     this.code.putInt(min).putInt(max);
/* 1069 */     for (int i = 0; i < labels.length; i++) {
/* 1070 */       labels[i].put(this, this.code, source, true);
/*      */     }
/*      */     
/* 1073 */     visitSwitchInsn(dflt, labels);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void visitLookupSwitchInsn(Label dflt, int[] keys, Label[] labels) {
/* 1082 */     int source = this.code.length;
/* 1083 */     this.code.putByte(171);
/* 1084 */     this.code.length += (4 - this.code.length % 4) % 4;
/* 1085 */     dflt.put(this, this.code, source, true);
/* 1086 */     this.code.putInt(labels.length);
/* 1087 */     for (int i = 0; i < labels.length; i++) {
/* 1088 */       this.code.putInt(keys[i]);
/* 1089 */       labels[i].put(this, this.code, source, true);
/*      */     } 
/*      */     
/* 1092 */     visitSwitchInsn(dflt, labels);
/*      */   }
/*      */ 
/*      */   
/*      */   private void visitSwitchInsn(Label dflt, Label[] labels) {
/* 1097 */     if (this.currentBlock != null) {
/* 1098 */       if (this.compute == 0) {
/* 1099 */         this.currentBlock.frame.execute(171, 0, null, null);
/*      */         
/* 1101 */         addSuccessor(0, dflt);
/* 1102 */         (dflt.getFirst()).status |= 0x10;
/* 1103 */         for (int i = 0; i < labels.length; i++) {
/* 1104 */           addSuccessor(0, labels[i]);
/* 1105 */           (labels[i].getFirst()).status |= 0x10;
/*      */         } 
/*      */       } else {
/*      */         
/* 1109 */         this.stackSize--;
/*      */         
/* 1111 */         addSuccessor(this.stackSize, dflt);
/* 1112 */         for (int i = 0; i < labels.length; i++) {
/* 1113 */           addSuccessor(this.stackSize, labels[i]);
/*      */         }
/*      */       } 
/*      */       
/* 1117 */       noSuccessor();
/*      */     } 
/*      */   }
/*      */   
/*      */   public void visitMultiANewArrayInsn(String desc, int dims) {
/* 1122 */     Item i = this.cw.newClassItem(desc);
/*      */     
/* 1124 */     if (this.currentBlock != null) {
/* 1125 */       if (this.compute == 0) {
/* 1126 */         this.currentBlock.frame.execute(197, dims, this.cw, i);
/*      */       }
/*      */       else {
/*      */         
/* 1130 */         this.stackSize += 1 - dims;
/*      */       } 
/*      */     }
/*      */     
/* 1134 */     this.code.put12(197, i.index).putByte(dims);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void visitTryCatchBlock(Label start, Label end, Label handler, String type) {
/* 1143 */     this.handlerCount++;
/* 1144 */     Handler h = new Handler();
/* 1145 */     h.start = start;
/* 1146 */     h.end = end;
/* 1147 */     h.handler = handler;
/* 1148 */     h.desc = type;
/* 1149 */     h.type = (type != null) ? this.cw.newClass(type) : 0;
/* 1150 */     if (this.lastHandler == null) {
/* 1151 */       this.firstHandler = h;
/*      */     } else {
/* 1153 */       this.lastHandler.next = h;
/*      */     } 
/* 1155 */     this.lastHandler = h;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void visitLocalVariable(String name, String desc, String signature, Label start, Label end, int index) {
/* 1166 */     if (signature != null) {
/* 1167 */       if (this.localVarType == null) {
/* 1168 */         this.localVarType = new ByteVector();
/*      */       }
/* 1170 */       this.localVarTypeCount++;
/* 1171 */       this.localVarType.putShort(start.position).putShort(end.position - start.position).putShort(this.cw.newUTF8(name)).putShort(this.cw.newUTF8(signature)).putShort(index);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1177 */     if (this.localVar == null) {
/* 1178 */       this.localVar = new ByteVector();
/*      */     }
/* 1180 */     this.localVarCount++;
/* 1181 */     this.localVar.putShort(start.position).putShort(end.position - start.position).putShort(this.cw.newUTF8(name)).putShort(this.cw.newUTF8(desc)).putShort(index);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1186 */     if (this.compute != 2) {
/*      */       
/* 1188 */       char c = desc.charAt(0);
/* 1189 */       int n = index + ((c == 'J' || c == 'D') ? 2 : 1);
/* 1190 */       if (n > this.maxLocals) {
/* 1191 */         this.maxLocals = n;
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public void visitLineNumber(int line, Label start) {
/* 1197 */     if (this.lineNumber == null) {
/* 1198 */       this.lineNumber = new ByteVector();
/*      */     }
/* 1200 */     this.lineNumberCount++;
/* 1201 */     this.lineNumber.putShort(start.position);
/* 1202 */     this.lineNumber.putShort(line);
/*      */   }
/*      */   
/*      */   public void visitMaxs(int maxStack, int maxLocals) {
/* 1206 */     if (this.compute == 0) {
/*      */       
/* 1208 */       Handler handler = this.firstHandler;
/* 1209 */       while (handler != null) {
/* 1210 */         Label l = handler.start.getFirst();
/* 1211 */         Label h = handler.handler.getFirst();
/* 1212 */         Label e = handler.end.getFirst();
/*      */         
/* 1214 */         String t = (handler.desc == null) ? "java/lang/Throwable" : handler.desc;
/*      */ 
/*      */         
/* 1217 */         int kind = 0x1700000 | this.cw.addType(t);
/*      */         
/* 1219 */         h.status |= 0x10;
/*      */         
/* 1221 */         while (l != e) {
/*      */           
/* 1223 */           Edge b = new Edge();
/* 1224 */           b.info = kind;
/* 1225 */           b.successor = h;
/*      */           
/* 1227 */           b.next = l.successors;
/* 1228 */           l.successors = b;
/*      */           
/* 1230 */           l = l.successor;
/*      */         } 
/* 1232 */         handler = handler.next;
/*      */       } 
/*      */ 
/*      */       
/* 1236 */       Frame f = this.labels.frame;
/* 1237 */       Type[] args = Type.getArgumentTypes(this.descriptor);
/* 1238 */       f.initInputFrame(this.cw, this.access, args, this.maxLocals);
/* 1239 */       visitFrame(f);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1247 */       int max = 0;
/* 1248 */       Label changed = this.labels;
/* 1249 */       while (changed != null) {
/*      */         
/* 1251 */         Label l = changed;
/* 1252 */         changed = changed.next;
/* 1253 */         l.next = null;
/* 1254 */         f = l.frame;
/*      */         
/* 1256 */         if ((l.status & 0x10) != 0) {
/* 1257 */           l.status |= 0x20;
/*      */         }
/*      */         
/* 1260 */         l.status |= 0x40;
/*      */         
/* 1262 */         int blockMax = f.inputStack.length + l.outputStackMax;
/* 1263 */         if (blockMax > max) {
/* 1264 */           max = blockMax;
/*      */         }
/*      */         
/* 1267 */         Edge e = l.successors;
/* 1268 */         while (e != null) {
/* 1269 */           Label n = e.successor.getFirst();
/* 1270 */           boolean change = f.merge(this.cw, n.frame, e.info);
/* 1271 */           if (change && n.next == null) {
/*      */ 
/*      */             
/* 1274 */             n.next = changed;
/* 1275 */             changed = n;
/*      */           } 
/* 1277 */           e = e.next;
/*      */         } 
/*      */       } 
/* 1280 */       this.maxStack = max;
/*      */ 
/*      */       
/* 1283 */       Label l = this.labels;
/* 1284 */       while (l != null) {
/* 1285 */         f = l.frame;
/* 1286 */         if ((l.status & 0x20) != 0) {
/* 1287 */           visitFrame(f);
/*      */         }
/* 1289 */         if ((l.status & 0x40) == 0) {
/*      */           
/* 1291 */           Label k = l.successor;
/* 1292 */           int start = l.position;
/* 1293 */           int end = ((k == null) ? this.code.length : k.position) - 1;
/*      */           
/* 1295 */           if (end >= start) {
/*      */             
/* 1297 */             for (int i = start; i < end; i++) {
/* 1298 */               this.code.data[i] = 0;
/*      */             }
/* 1300 */             this.code.data[end] = -65;
/*      */             
/* 1302 */             startFrame(start, 0, 1);
/* 1303 */             this.frame[this.frameIndex++] = 0x1700000 | this.cw.addType("java/lang/Throwable");
/*      */             
/* 1305 */             endFrame();
/*      */           } 
/*      */         } 
/* 1308 */         l = l.successor;
/*      */       } 
/* 1310 */     } else if (this.compute == 1) {
/*      */       
/* 1312 */       Handler handler = this.firstHandler;
/* 1313 */       while (handler != null) {
/* 1314 */         Label l = handler.start;
/* 1315 */         Label h = handler.handler;
/* 1316 */         Label e = handler.end;
/*      */         
/* 1318 */         while (l != e) {
/*      */           
/* 1320 */           Edge b = new Edge();
/* 1321 */           b.info = Integer.MAX_VALUE;
/* 1322 */           b.successor = h;
/*      */           
/* 1324 */           if ((l.status & 0x80) == 0) {
/* 1325 */             b.next = l.successors;
/* 1326 */             l.successors = b;
/*      */           
/*      */           }
/*      */           else {
/*      */             
/* 1331 */             b.next = l.successors.next.next;
/* 1332 */             l.successors.next.next = b;
/*      */           } 
/*      */           
/* 1335 */           l = l.successor;
/*      */         } 
/* 1337 */         handler = handler.next;
/*      */       } 
/*      */       
/* 1340 */       if (this.subroutines > 0) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1347 */         int id = 0;
/* 1348 */         this.labels.visitSubroutine(null, 1L, this.subroutines);
/*      */         
/* 1350 */         Label l = this.labels;
/* 1351 */         while (l != null) {
/* 1352 */           if ((l.status & 0x80) != 0) {
/*      */             
/* 1354 */             Label subroutine = l.successors.next.successor;
/*      */             
/* 1356 */             if ((subroutine.status & 0x400) == 0) {
/*      */               
/* 1358 */               id++;
/* 1359 */               subroutine.visitSubroutine(null, id / 32L << 32 | 1L << id % 32, this.subroutines);
/*      */             } 
/*      */           } 
/*      */           
/* 1363 */           l = l.successor;
/*      */         } 
/*      */         
/* 1366 */         l = this.labels;
/* 1367 */         while (l != null) {
/* 1368 */           if ((l.status & 0x80) != 0) {
/* 1369 */             Label L = this.labels;
/* 1370 */             while (L != null) {
/* 1371 */               L.status &= 0xFFFFFBFF;
/* 1372 */               L = L.successor;
/*      */             } 
/*      */             
/* 1375 */             Label subroutine = l.successors.next.successor;
/* 1376 */             subroutine.visitSubroutine(l, 0L, this.subroutines);
/*      */           } 
/* 1378 */           l = l.successor;
/*      */         } 
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1392 */       int max = 0;
/* 1393 */       Label stack = this.labels;
/* 1394 */       while (stack != null) {
/*      */         
/* 1396 */         Label l = stack;
/* 1397 */         stack = stack.next;
/*      */         
/* 1399 */         int start = l.inputStackTop;
/* 1400 */         int blockMax = start + l.outputStackMax;
/*      */         
/* 1402 */         if (blockMax > max) {
/* 1403 */           max = blockMax;
/*      */         }
/*      */         
/* 1406 */         Edge b = l.successors;
/* 1407 */         if ((l.status & 0x80) != 0)
/*      */         {
/* 1409 */           b = b.next;
/*      */         }
/* 1411 */         while (b != null) {
/* 1412 */           l = b.successor;
/*      */           
/* 1414 */           if ((l.status & 0x8) == 0) {
/*      */             
/* 1416 */             l.inputStackTop = (b.info == Integer.MAX_VALUE) ? 1 : (start + b.info);
/*      */ 
/*      */             
/* 1419 */             l.status |= 0x8;
/* 1420 */             l.next = stack;
/* 1421 */             stack = l;
/*      */           } 
/* 1423 */           b = b.next;
/*      */         } 
/*      */       } 
/* 1426 */       this.maxStack = max;
/*      */     } else {
/* 1428 */       this.maxStack = maxStack;
/* 1429 */       this.maxLocals = maxLocals;
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
/*      */   public void visitEnd() {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static int getArgumentsAndReturnSizes(String desc) {
/* 1451 */     int n = 1;
/* 1452 */     int c = 1;
/*      */     while (true) {
/* 1454 */       char car = desc.charAt(c++);
/* 1455 */       if (car == ')') {
/* 1456 */         car = desc.charAt(c);
/* 1457 */         return n << 2 | ((car == 'V') ? 0 : ((car == 'D' || car == 'J') ? 2 : 1));
/*      */       } 
/* 1459 */       if (car == 'L') {
/* 1460 */         while (desc.charAt(c++) != ';');
/*      */         
/* 1462 */         n++; continue;
/* 1463 */       }  if (car == '[') {
/* 1464 */         while ((car = desc.charAt(c)) == '[') {
/* 1465 */           c++;
/*      */         }
/* 1467 */         if (car == 'D' || car == 'J')
/* 1468 */           n--;  continue;
/*      */       } 
/* 1470 */       if (car == 'D' || car == 'J') {
/* 1471 */         n += 2; continue;
/*      */       } 
/* 1473 */       n++;
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
/*      */   private void addSuccessor(int info, Label successor) {
/* 1486 */     Edge b = new Edge();
/* 1487 */     b.info = info;
/* 1488 */     b.successor = successor;
/*      */     
/* 1490 */     b.next = this.currentBlock.successors;
/* 1491 */     this.currentBlock.successors = b;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void noSuccessor() {
/* 1499 */     if (this.compute == 0) {
/* 1500 */       Label l = new Label();
/* 1501 */       l.frame = new Frame();
/* 1502 */       l.frame.owner = l;
/* 1503 */       l.resolve(this, this.code.length, this.code.data);
/* 1504 */       this.previousBlock.successor = l;
/* 1505 */       this.previousBlock = l;
/*      */     } else {
/* 1507 */       this.currentBlock.outputStackMax = this.maxStackSize;
/*      */     } 
/* 1509 */     this.currentBlock = null;
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
/*      */   private void visitFrame(Frame f) {
/* 1523 */     int nTop = 0;
/* 1524 */     int nLocal = 0;
/* 1525 */     int nStack = 0;
/* 1526 */     int[] locals = f.inputLocals;
/* 1527 */     int[] stacks = f.inputStack;
/*      */     
/*      */     int i;
/* 1530 */     for (i = 0; i < locals.length; i++) {
/* 1531 */       int t = locals[i];
/* 1532 */       if (t == 16777216) {
/* 1533 */         nTop++;
/*      */       } else {
/* 1535 */         nLocal += nTop + 1;
/* 1536 */         nTop = 0;
/*      */       } 
/* 1538 */       if (t == 16777220 || t == 16777219) {
/* 1539 */         i++;
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/* 1544 */     for (i = 0; i < stacks.length; i++) {
/* 1545 */       int t = stacks[i];
/* 1546 */       nStack++;
/* 1547 */       if (t == 16777220 || t == 16777219) {
/* 1548 */         i++;
/*      */       }
/*      */     } 
/*      */     
/* 1552 */     startFrame(f.owner.position, nLocal, nStack);
/* 1553 */     for (i = 0; nLocal > 0; i++, nLocal--) {
/* 1554 */       int t = locals[i];
/* 1555 */       this.frame[this.frameIndex++] = t;
/* 1556 */       if (t == 16777220 || t == 16777219) {
/* 1557 */         i++;
/*      */       }
/*      */     } 
/* 1560 */     for (i = 0; i < stacks.length; i++) {
/* 1561 */       int t = stacks[i];
/* 1562 */       this.frame[this.frameIndex++] = t;
/* 1563 */       if (t == 16777220 || t == 16777219) {
/* 1564 */         i++;
/*      */       }
/*      */     } 
/* 1567 */     endFrame();
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
/*      */   private void startFrame(int offset, int nLocal, int nStack) {
/* 1580 */     int n = 3 + nLocal + nStack;
/* 1581 */     if (this.frame == null || this.frame.length < n) {
/* 1582 */       this.frame = new int[n];
/*      */     }
/* 1584 */     this.frame[0] = offset;
/* 1585 */     this.frame[1] = nLocal;
/* 1586 */     this.frame[2] = nStack;
/* 1587 */     this.frameIndex = 3;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void endFrame() {
/* 1595 */     if (this.previousFrame != null) {
/* 1596 */       if (this.stackMap == null) {
/* 1597 */         this.stackMap = new ByteVector();
/*      */       }
/* 1599 */       writeFrame();
/* 1600 */       this.frameCount++;
/*      */     } 
/* 1602 */     this.previousFrame = this.frame;
/* 1603 */     this.frame = null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void writeFrame() {
/* 1611 */     int delta, clocalsSize = this.frame[1];
/* 1612 */     int cstackSize = this.frame[2];
/* 1613 */     if ((this.cw.version & 0xFFFF) < 50) {
/* 1614 */       this.stackMap.putShort(this.frame[0]).putShort(clocalsSize);
/* 1615 */       writeFrameTypes(3, 3 + clocalsSize);
/* 1616 */       this.stackMap.putShort(cstackSize);
/* 1617 */       writeFrameTypes(3 + clocalsSize, 3 + clocalsSize + cstackSize);
/*      */       return;
/*      */     } 
/* 1620 */     int localsSize = this.previousFrame[1];
/* 1621 */     int type = 255;
/* 1622 */     int k = 0;
/*      */     
/* 1624 */     if (this.frameCount == 0) {
/* 1625 */       delta = this.frame[0];
/*      */     } else {
/* 1627 */       delta = this.frame[0] - this.previousFrame[0] - 1;
/*      */     } 
/* 1629 */     if (cstackSize == 0) {
/* 1630 */       k = clocalsSize - localsSize;
/* 1631 */       switch (k) {
/*      */         case -3:
/*      */         case -2:
/*      */         case -1:
/* 1635 */           type = 248;
/* 1636 */           localsSize = clocalsSize;
/*      */           break;
/*      */         case 0:
/* 1639 */           type = (delta < 64) ? 0 : 251;
/*      */           break;
/*      */         case 1:
/*      */         case 2:
/*      */         case 3:
/* 1644 */           type = 252;
/*      */           break;
/*      */       } 
/* 1647 */     } else if (clocalsSize == localsSize && cstackSize == 1) {
/* 1648 */       type = (delta < 63) ? 64 : 247;
/*      */     } 
/*      */ 
/*      */     
/* 1652 */     if (type != 255) {
/*      */       
/* 1654 */       int l = 3;
/* 1655 */       for (int j = 0; j < localsSize; j++) {
/* 1656 */         if (this.frame[l] != this.previousFrame[l]) {
/* 1657 */           type = 255;
/*      */           break;
/*      */         } 
/* 1660 */         l++;
/*      */       } 
/*      */     } 
/* 1663 */     switch (type) {
/*      */       case 0:
/* 1665 */         this.stackMap.putByte(delta);
/*      */         return;
/*      */       case 64:
/* 1668 */         this.stackMap.putByte(64 + delta);
/* 1669 */         writeFrameTypes(3 + clocalsSize, 4 + clocalsSize);
/*      */         return;
/*      */       case 247:
/* 1672 */         this.stackMap.putByte(247).putShort(delta);
/*      */         
/* 1674 */         writeFrameTypes(3 + clocalsSize, 4 + clocalsSize);
/*      */         return;
/*      */       case 251:
/* 1677 */         this.stackMap.putByte(251).putShort(delta);
/*      */         return;
/*      */       case 248:
/* 1680 */         this.stackMap.putByte(251 + k).putShort(delta);
/*      */         return;
/*      */       case 252:
/* 1683 */         this.stackMap.putByte(251 + k).putShort(delta);
/* 1684 */         writeFrameTypes(3 + localsSize, 3 + clocalsSize);
/*      */         return;
/*      */     } 
/*      */     
/* 1688 */     this.stackMap.putByte(255).putShort(delta).putShort(clocalsSize);
/*      */ 
/*      */     
/* 1691 */     writeFrameTypes(3, 3 + clocalsSize);
/* 1692 */     this.stackMap.putShort(cstackSize);
/* 1693 */     writeFrameTypes(3 + clocalsSize, 3 + clocalsSize + cstackSize);
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
/*      */   private void writeFrameTypes(int start, int end) {
/* 1707 */     for (int i = start; i < end; i++) {
/* 1708 */       int t = this.frame[i];
/* 1709 */       int d = t & 0xF0000000;
/* 1710 */       if (d == 0) {
/* 1711 */         int v = t & 0xFFFFF;
/* 1712 */         switch (t & 0xFF00000) {
/*      */           case 24117248:
/* 1714 */             this.stackMap.putByte(7).putShort(this.cw.newClass((this.cw.typeTable[v]).strVal1));
/*      */             break;
/*      */           
/*      */           case 25165824:
/* 1718 */             this.stackMap.putByte(8).putShort((this.cw.typeTable[v]).intVal);
/*      */             break;
/*      */           default:
/* 1721 */             this.stackMap.putByte(v); break;
/*      */         } 
/*      */       } else {
/* 1724 */         StringBuffer buf = new StringBuffer();
/* 1725 */         d >>= 28;
/* 1726 */         while (d-- > 0) {
/* 1727 */           buf.append('[');
/*      */         }
/* 1729 */         if ((t & 0xFF00000) == 24117248) {
/* 1730 */           buf.append('L');
/* 1731 */           buf.append((this.cw.typeTable[t & 0xFFFFF]).strVal1);
/* 1732 */           buf.append(';');
/*      */         } else {
/* 1734 */           switch (t & 0xF) {
/*      */             case 1:
/* 1736 */               buf.append('I');
/*      */               break;
/*      */             case 2:
/* 1739 */               buf.append('F');
/*      */               break;
/*      */             case 3:
/* 1742 */               buf.append('D');
/*      */               break;
/*      */             case 9:
/* 1745 */               buf.append('Z');
/*      */               break;
/*      */             case 10:
/* 1748 */               buf.append('B');
/*      */               break;
/*      */             case 11:
/* 1751 */               buf.append('C');
/*      */               break;
/*      */             case 12:
/* 1754 */               buf.append('S');
/*      */               break;
/*      */             default:
/* 1757 */               buf.append('J'); break;
/*      */           } 
/*      */         } 
/* 1760 */         this.stackMap.putByte(7).putShort(this.cw.newClass(buf.toString()));
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   private void writeFrameType(Object type) {
/* 1766 */     if (type instanceof String) {
/* 1767 */       this.stackMap.putByte(7).putShort(this.cw.newClass((String)type));
/* 1768 */     } else if (type instanceof Integer) {
/* 1769 */       this.stackMap.putByte(((Integer)type).intValue());
/*      */     } else {
/* 1771 */       this.stackMap.putByte(8).putShort(((Label)type).position);
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
/*      */   final int getSize() {
/* 1785 */     if (this.classReaderOffset != 0) {
/* 1786 */       return 6 + this.classReaderLength;
/*      */     }
/* 1788 */     if (this.resize)
/*      */     {
/*      */       
/* 1791 */       resizeInstructions();
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 1796 */     int size = 8;
/* 1797 */     if (this.code.length > 0) {
/* 1798 */       this.cw.newUTF8("Code");
/* 1799 */       size += 18 + this.code.length + 8 * this.handlerCount;
/* 1800 */       if (this.localVar != null) {
/* 1801 */         this.cw.newUTF8("LocalVariableTable");
/* 1802 */         size += 8 + this.localVar.length;
/*      */       } 
/* 1804 */       if (this.localVarType != null) {
/* 1805 */         this.cw.newUTF8("LocalVariableTypeTable");
/* 1806 */         size += 8 + this.localVarType.length;
/*      */       } 
/* 1808 */       if (this.lineNumber != null) {
/* 1809 */         this.cw.newUTF8("LineNumberTable");
/* 1810 */         size += 8 + this.lineNumber.length;
/*      */       } 
/* 1812 */       if (this.stackMap != null) {
/* 1813 */         boolean zip = ((this.cw.version & 0xFFFF) >= 50);
/* 1814 */         this.cw.newUTF8(zip ? "StackMapTable" : "StackMap");
/* 1815 */         size += 8 + this.stackMap.length;
/*      */       } 
/* 1817 */       if (this.cattrs != null) {
/* 1818 */         size += this.cattrs.getSize(this.cw, this.code.data, this.code.length, this.maxStack, this.maxLocals);
/*      */       }
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1825 */     if (this.exceptionCount > 0) {
/* 1826 */       this.cw.newUTF8("Exceptions");
/* 1827 */       size += 8 + 2 * this.exceptionCount;
/*      */     } 
/* 1829 */     if ((this.access & 0x1000) != 0 && (this.cw.version & 0xFFFF) < 49) {
/*      */ 
/*      */       
/* 1832 */       this.cw.newUTF8("Synthetic");
/* 1833 */       size += 6;
/*      */     } 
/* 1835 */     if ((this.access & 0x20000) != 0) {
/* 1836 */       this.cw.newUTF8("Deprecated");
/* 1837 */       size += 6;
/*      */     } 
/* 1839 */     if (this.signature != null) {
/* 1840 */       this.cw.newUTF8("Signature");
/* 1841 */       this.cw.newUTF8(this.signature);
/* 1842 */       size += 8;
/*      */     } 
/* 1844 */     if (this.annd != null) {
/* 1845 */       this.cw.newUTF8("AnnotationDefault");
/* 1846 */       size += 6 + this.annd.length;
/*      */     } 
/* 1848 */     if (this.anns != null) {
/* 1849 */       this.cw.newUTF8("RuntimeVisibleAnnotations");
/* 1850 */       size += 8 + this.anns.getSize();
/*      */     } 
/* 1852 */     if (this.ianns != null) {
/* 1853 */       this.cw.newUTF8("RuntimeInvisibleAnnotations");
/* 1854 */       size += 8 + this.ianns.getSize();
/*      */     } 
/* 1856 */     if (this.panns != null) {
/* 1857 */       this.cw.newUTF8("RuntimeVisibleParameterAnnotations");
/* 1858 */       size += 7 + 2 * (this.panns.length - this.synthetics);
/* 1859 */       for (int i = this.panns.length - 1; i >= this.synthetics; i--) {
/* 1860 */         size += ((this.panns[i] == null) ? 0 : this.panns[i].getSize());
/*      */       }
/*      */     } 
/* 1863 */     if (this.ipanns != null) {
/* 1864 */       this.cw.newUTF8("RuntimeInvisibleParameterAnnotations");
/* 1865 */       size += 7 + 2 * (this.ipanns.length - this.synthetics);
/* 1866 */       for (int i = this.ipanns.length - 1; i >= this.synthetics; i--) {
/* 1867 */         size += ((this.ipanns[i] == null) ? 0 : this.ipanns[i].getSize());
/*      */       }
/*      */     } 
/* 1870 */     if (this.attrs != null) {
/* 1871 */       size += this.attrs.getSize(this.cw, null, 0, -1, -1);
/*      */     }
/* 1873 */     return size;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   final void put(ByteVector out) {
/* 1883 */     out.putShort(this.access).putShort(this.name).putShort(this.desc);
/* 1884 */     if (this.classReaderOffset != 0) {
/* 1885 */       out.putByteArray(this.cw.cr.b, this.classReaderOffset, this.classReaderLength);
/*      */       return;
/*      */     } 
/* 1888 */     int attributeCount = 0;
/* 1889 */     if (this.code.length > 0) {
/* 1890 */       attributeCount++;
/*      */     }
/* 1892 */     if (this.exceptionCount > 0) {
/* 1893 */       attributeCount++;
/*      */     }
/* 1895 */     if ((this.access & 0x1000) != 0 && (this.cw.version & 0xFFFF) < 49)
/*      */     {
/*      */       
/* 1898 */       attributeCount++;
/*      */     }
/* 1900 */     if ((this.access & 0x20000) != 0) {
/* 1901 */       attributeCount++;
/*      */     }
/* 1903 */     if (this.signature != null) {
/* 1904 */       attributeCount++;
/*      */     }
/* 1906 */     if (this.annd != null) {
/* 1907 */       attributeCount++;
/*      */     }
/* 1909 */     if (this.anns != null) {
/* 1910 */       attributeCount++;
/*      */     }
/* 1912 */     if (this.ianns != null) {
/* 1913 */       attributeCount++;
/*      */     }
/* 1915 */     if (this.panns != null) {
/* 1916 */       attributeCount++;
/*      */     }
/* 1918 */     if (this.ipanns != null) {
/* 1919 */       attributeCount++;
/*      */     }
/* 1921 */     if (this.attrs != null) {
/* 1922 */       attributeCount += this.attrs.getCount();
/*      */     }
/* 1924 */     out.putShort(attributeCount);
/* 1925 */     if (this.code.length > 0) {
/* 1926 */       int size = 12 + this.code.length + 8 * this.handlerCount;
/* 1927 */       if (this.localVar != null) {
/* 1928 */         size += 8 + this.localVar.length;
/*      */       }
/* 1930 */       if (this.localVarType != null) {
/* 1931 */         size += 8 + this.localVarType.length;
/*      */       }
/* 1933 */       if (this.lineNumber != null) {
/* 1934 */         size += 8 + this.lineNumber.length;
/*      */       }
/* 1936 */       if (this.stackMap != null) {
/* 1937 */         size += 8 + this.stackMap.length;
/*      */       }
/* 1939 */       if (this.cattrs != null) {
/* 1940 */         size += this.cattrs.getSize(this.cw, this.code.data, this.code.length, this.maxStack, this.maxLocals);
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1946 */       out.putShort(this.cw.newUTF8("Code")).putInt(size);
/* 1947 */       out.putShort(this.maxStack).putShort(this.maxLocals);
/* 1948 */       out.putInt(this.code.length).putByteArray(this.code.data, 0, this.code.length);
/* 1949 */       out.putShort(this.handlerCount);
/* 1950 */       if (this.handlerCount > 0) {
/* 1951 */         Handler h = this.firstHandler;
/* 1952 */         while (h != null) {
/* 1953 */           out.putShort(h.start.position).putShort(h.end.position).putShort(h.handler.position).putShort(h.type);
/*      */ 
/*      */ 
/*      */           
/* 1957 */           h = h.next;
/*      */         } 
/*      */       } 
/* 1960 */       attributeCount = 0;
/* 1961 */       if (this.localVar != null) {
/* 1962 */         attributeCount++;
/*      */       }
/* 1964 */       if (this.localVarType != null) {
/* 1965 */         attributeCount++;
/*      */       }
/* 1967 */       if (this.lineNumber != null) {
/* 1968 */         attributeCount++;
/*      */       }
/* 1970 */       if (this.stackMap != null) {
/* 1971 */         attributeCount++;
/*      */       }
/* 1973 */       if (this.cattrs != null) {
/* 1974 */         attributeCount += this.cattrs.getCount();
/*      */       }
/* 1976 */       out.putShort(attributeCount);
/* 1977 */       if (this.localVar != null) {
/* 1978 */         out.putShort(this.cw.newUTF8("LocalVariableTable"));
/* 1979 */         out.putInt(this.localVar.length + 2).putShort(this.localVarCount);
/* 1980 */         out.putByteArray(this.localVar.data, 0, this.localVar.length);
/*      */       } 
/* 1982 */       if (this.localVarType != null) {
/* 1983 */         out.putShort(this.cw.newUTF8("LocalVariableTypeTable"));
/* 1984 */         out.putInt(this.localVarType.length + 2).putShort(this.localVarTypeCount);
/* 1985 */         out.putByteArray(this.localVarType.data, 0, this.localVarType.length);
/*      */       } 
/* 1987 */       if (this.lineNumber != null) {
/* 1988 */         out.putShort(this.cw.newUTF8("LineNumberTable"));
/* 1989 */         out.putInt(this.lineNumber.length + 2).putShort(this.lineNumberCount);
/* 1990 */         out.putByteArray(this.lineNumber.data, 0, this.lineNumber.length);
/*      */       } 
/* 1992 */       if (this.stackMap != null) {
/* 1993 */         boolean zip = ((this.cw.version & 0xFFFF) >= 50);
/* 1994 */         out.putShort(this.cw.newUTF8(zip ? "StackMapTable" : "StackMap"));
/* 1995 */         out.putInt(this.stackMap.length + 2).putShort(this.frameCount);
/* 1996 */         out.putByteArray(this.stackMap.data, 0, this.stackMap.length);
/*      */       } 
/* 1998 */       if (this.cattrs != null) {
/* 1999 */         this.cattrs.put(this.cw, this.code.data, this.code.length, this.maxLocals, this.maxStack, out);
/*      */       }
/*      */     } 
/* 2002 */     if (this.exceptionCount > 0) {
/* 2003 */       out.putShort(this.cw.newUTF8("Exceptions")).putInt(2 * this.exceptionCount + 2);
/*      */       
/* 2005 */       out.putShort(this.exceptionCount);
/* 2006 */       for (int i = 0; i < this.exceptionCount; i++) {
/* 2007 */         out.putShort(this.exceptions[i]);
/*      */       }
/*      */     } 
/* 2010 */     if ((this.access & 0x1000) != 0 && (this.cw.version & 0xFFFF) < 49)
/*      */     {
/*      */       
/* 2013 */       out.putShort(this.cw.newUTF8("Synthetic")).putInt(0);
/*      */     }
/* 2015 */     if ((this.access & 0x20000) != 0) {
/* 2016 */       out.putShort(this.cw.newUTF8("Deprecated")).putInt(0);
/*      */     }
/* 2018 */     if (this.signature != null) {
/* 2019 */       out.putShort(this.cw.newUTF8("Signature")).putInt(2).putShort(this.cw.newUTF8(this.signature));
/*      */     }
/*      */ 
/*      */     
/* 2023 */     if (this.annd != null) {
/* 2024 */       out.putShort(this.cw.newUTF8("AnnotationDefault"));
/* 2025 */       out.putInt(this.annd.length);
/* 2026 */       out.putByteArray(this.annd.data, 0, this.annd.length);
/*      */     } 
/* 2028 */     if (this.anns != null) {
/* 2029 */       out.putShort(this.cw.newUTF8("RuntimeVisibleAnnotations"));
/* 2030 */       this.anns.put(out);
/*      */     } 
/* 2032 */     if (this.ianns != null) {
/* 2033 */       out.putShort(this.cw.newUTF8("RuntimeInvisibleAnnotations"));
/* 2034 */       this.ianns.put(out);
/*      */     } 
/* 2036 */     if (this.panns != null) {
/* 2037 */       out.putShort(this.cw.newUTF8("RuntimeVisibleParameterAnnotations"));
/* 2038 */       AnnotationWriter.put(this.panns, this.synthetics, out);
/*      */     } 
/* 2040 */     if (this.ipanns != null) {
/* 2041 */       out.putShort(this.cw.newUTF8("RuntimeInvisibleParameterAnnotations"));
/* 2042 */       AnnotationWriter.put(this.ipanns, this.synthetics, out);
/*      */     } 
/* 2044 */     if (this.attrs != null) {
/* 2045 */       this.attrs.put(this.cw, null, 0, -1, -1, out);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void resizeInstructions() {
/* 2069 */     byte[] b = this.code.data;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2097 */     int[] allIndexes = new int[0];
/* 2098 */     int[] allSizes = new int[0];
/*      */ 
/*      */ 
/*      */     
/* 2102 */     boolean[] resize = new boolean[this.code.length];
/*      */ 
/*      */     
/* 2105 */     int state = 3;
/*      */     do {
/* 2107 */       if (state == 3) {
/* 2108 */         state = 2;
/*      */       }
/* 2110 */       int u = 0;
/* 2111 */       while (u < b.length) {
/* 2112 */         int newOffset, label, opcode = b[u] & 0xFF;
/* 2113 */         int insert = 0;
/*      */         
/* 2115 */         switch (ClassWriter.TYPE[opcode]) {
/*      */           case 0:
/*      */           case 4:
/* 2118 */             u++;
/*      */             break;
/*      */           case 8:
/* 2121 */             if (opcode > 201) {
/*      */ 
/*      */ 
/*      */               
/* 2125 */               opcode = (opcode < 218) ? (opcode - 49) : (opcode - 20);
/* 2126 */               label = u + readUnsignedShort(b, u + 1);
/*      */             } else {
/* 2128 */               label = u + readShort(b, u + 1);
/*      */             } 
/* 2130 */             newOffset = getNewOffset(allIndexes, allSizes, u, label);
/* 2131 */             if (newOffset < -32768 || newOffset > 32767)
/*      */             {
/*      */               
/* 2134 */               if (!resize[u]) {
/* 2135 */                 if (opcode == 167 || opcode == 168) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/* 2141 */                   insert = 2;
/*      */ 
/*      */                 
/*      */                 }
/*      */                 else {
/*      */ 
/*      */ 
/*      */                   
/* 2149 */                   insert = 5;
/*      */                 } 
/* 2151 */                 resize[u] = true;
/*      */               } 
/*      */             }
/* 2154 */             u += 3;
/*      */             break;
/*      */           case 9:
/* 2157 */             u += 5;
/*      */             break;
/*      */           case 13:
/* 2160 */             if (state == 1) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */               
/* 2168 */               newOffset = getNewOffset(allIndexes, allSizes, 0, u);
/* 2169 */               insert = -(newOffset & 0x3);
/* 2170 */             } else if (!resize[u]) {
/*      */ 
/*      */ 
/*      */               
/* 2174 */               insert = u & 0x3;
/* 2175 */               resize[u] = true;
/*      */             } 
/*      */             
/* 2178 */             u = u + 4 - (u & 0x3);
/* 2179 */             u += 4 * (readInt(b, u + 8) - readInt(b, u + 4) + 1) + 12;
/*      */             break;
/*      */           case 14:
/* 2182 */             if (state == 1) {
/*      */               
/* 2184 */               int newOffset = getNewOffset(allIndexes, allSizes, 0, u);
/* 2185 */               insert = -(newOffset & 0x3);
/* 2186 */             } else if (!resize[u]) {
/*      */               
/* 2188 */               insert = u & 0x3;
/* 2189 */               resize[u] = true;
/*      */             } 
/*      */             
/* 2192 */             u = u + 4 - (u & 0x3);
/* 2193 */             u += 8 * readInt(b, u + 4) + 8;
/*      */             break;
/*      */           case 16:
/* 2196 */             opcode = b[u + 1] & 0xFF;
/* 2197 */             if (opcode == 132) {
/* 2198 */               u += 6; break;
/*      */             } 
/* 2200 */             u += 4;
/*      */             break;
/*      */           
/*      */           case 1:
/*      */           case 3:
/*      */           case 10:
/* 2206 */             u += 2;
/*      */             break;
/*      */           case 2:
/*      */           case 5:
/*      */           case 6:
/*      */           case 11:
/*      */           case 12:
/* 2213 */             u += 3;
/*      */             break;
/*      */           case 7:
/* 2216 */             u += 5;
/*      */             break;
/*      */           
/*      */           default:
/* 2220 */             u += 4;
/*      */             break;
/*      */         } 
/* 2223 */         if (insert != 0) {
/*      */ 
/*      */           
/* 2226 */           int[] newIndexes = new int[allIndexes.length + 1];
/* 2227 */           int[] newSizes = new int[allSizes.length + 1];
/* 2228 */           System.arraycopy(allIndexes, 0, newIndexes, 0, allIndexes.length);
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 2233 */           System.arraycopy(allSizes, 0, newSizes, 0, allSizes.length);
/* 2234 */           newIndexes[allIndexes.length] = u;
/* 2235 */           newSizes[allSizes.length] = insert;
/* 2236 */           allIndexes = newIndexes;
/* 2237 */           allSizes = newSizes;
/* 2238 */           if (insert > 0) {
/* 2239 */             state = 3;
/*      */           }
/*      */         } 
/*      */       } 
/* 2243 */       if (state >= 3)
/* 2244 */         continue;  state--;
/*      */     }
/* 2246 */     while (state != 0);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2252 */     ByteVector newCode = new ByteVector(this.code.length);
/*      */     
/* 2254 */     int k = 0;
/* 2255 */     while (k < this.code.length) {
/* 2256 */       int newOffset, j, label, v, opcode = b[k] & 0xFF;
/* 2257 */       switch (ClassWriter.TYPE[opcode]) {
/*      */         case 0:
/*      */         case 4:
/* 2260 */           newCode.putByte(opcode);
/* 2261 */           k++;
/*      */           continue;
/*      */         case 8:
/* 2264 */           if (opcode > 201) {
/*      */ 
/*      */ 
/*      */             
/* 2268 */             opcode = (opcode < 218) ? (opcode - 49) : (opcode - 20);
/* 2269 */             label = k + readUnsignedShort(b, k + 1);
/*      */           } else {
/* 2271 */             label = k + readShort(b, k + 1);
/*      */           } 
/* 2273 */           newOffset = getNewOffset(allIndexes, allSizes, k, label);
/* 2274 */           if (resize[k]) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 2280 */             if (opcode == 167) {
/* 2281 */               newCode.putByte(200);
/* 2282 */             } else if (opcode == 168) {
/* 2283 */               newCode.putByte(201);
/*      */             } else {
/* 2285 */               newCode.putByte((opcode <= 166) ? ((opcode + 1 ^ true) - 1) : (opcode ^ true));
/*      */ 
/*      */               
/* 2288 */               newCode.putShort(8);
/* 2289 */               newCode.putByte(200);
/*      */               
/* 2291 */               newOffset -= 3;
/*      */             } 
/* 2293 */             newCode.putInt(newOffset);
/*      */           } else {
/* 2295 */             newCode.putByte(opcode);
/* 2296 */             newCode.putShort(newOffset);
/*      */           } 
/* 2298 */           k += 3;
/*      */           continue;
/*      */         case 9:
/* 2301 */           label = k + readInt(b, k + 1);
/* 2302 */           newOffset = getNewOffset(allIndexes, allSizes, k, label);
/* 2303 */           newCode.putByte(opcode);
/* 2304 */           newCode.putInt(newOffset);
/* 2305 */           k += 5;
/*      */           continue;
/*      */         
/*      */         case 13:
/* 2309 */           v = k;
/* 2310 */           k = k + 4 - (v & 0x3);
/*      */           
/* 2312 */           newCode.putByte(170);
/* 2313 */           newCode.length += (4 - newCode.length % 4) % 4;
/* 2314 */           label = v + readInt(b, k);
/* 2315 */           k += 4;
/* 2316 */           newOffset = getNewOffset(allIndexes, allSizes, v, label);
/* 2317 */           newCode.putInt(newOffset);
/* 2318 */           j = readInt(b, k);
/* 2319 */           k += 4;
/* 2320 */           newCode.putInt(j);
/* 2321 */           j = readInt(b, k) - j + 1;
/* 2322 */           k += 4;
/* 2323 */           newCode.putInt(readInt(b, k - 4));
/* 2324 */           for (; j > 0; j--) {
/* 2325 */             label = v + readInt(b, k);
/* 2326 */             k += 4;
/* 2327 */             newOffset = getNewOffset(allIndexes, allSizes, v, label);
/* 2328 */             newCode.putInt(newOffset);
/*      */           } 
/*      */           continue;
/*      */         
/*      */         case 14:
/* 2333 */           v = k;
/* 2334 */           k = k + 4 - (v & 0x3);
/*      */           
/* 2336 */           newCode.putByte(171);
/* 2337 */           newCode.length += (4 - newCode.length % 4) % 4;
/* 2338 */           label = v + readInt(b, k);
/* 2339 */           k += 4;
/* 2340 */           newOffset = getNewOffset(allIndexes, allSizes, v, label);
/* 2341 */           newCode.putInt(newOffset);
/* 2342 */           j = readInt(b, k);
/* 2343 */           k += 4;
/* 2344 */           newCode.putInt(j);
/* 2345 */           for (; j > 0; j--) {
/* 2346 */             newCode.putInt(readInt(b, k));
/* 2347 */             k += 4;
/* 2348 */             label = v + readInt(b, k);
/* 2349 */             k += 4;
/* 2350 */             newOffset = getNewOffset(allIndexes, allSizes, v, label);
/* 2351 */             newCode.putInt(newOffset);
/*      */           } 
/*      */           continue;
/*      */         case 16:
/* 2355 */           opcode = b[k + 1] & 0xFF;
/* 2356 */           if (opcode == 132) {
/* 2357 */             newCode.putByteArray(b, k, 6);
/* 2358 */             k += 6; continue;
/*      */           } 
/* 2360 */           newCode.putByteArray(b, k, 4);
/* 2361 */           k += 4;
/*      */           continue;
/*      */         
/*      */         case 1:
/*      */         case 3:
/*      */         case 10:
/* 2367 */           newCode.putByteArray(b, k, 2);
/* 2368 */           k += 2;
/*      */           continue;
/*      */         case 2:
/*      */         case 5:
/*      */         case 6:
/*      */         case 11:
/*      */         case 12:
/* 2375 */           newCode.putByteArray(b, k, 3);
/* 2376 */           k += 3;
/*      */           continue;
/*      */         case 7:
/* 2379 */           newCode.putByteArray(b, k, 5);
/* 2380 */           k += 5;
/*      */           continue;
/*      */       } 
/*      */       
/* 2384 */       newCode.putByteArray(b, k, 4);
/* 2385 */       k += 4;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2391 */     if (this.frameCount > 0) {
/* 2392 */       if (this.compute == 0) {
/* 2393 */         this.frameCount = 0;
/* 2394 */         this.stackMap = null;
/* 2395 */         this.previousFrame = null;
/* 2396 */         this.frame = null;
/* 2397 */         Frame f = new Frame();
/* 2398 */         f.owner = this.labels;
/* 2399 */         Type[] args = Type.getArgumentTypes(this.descriptor);
/* 2400 */         f.initInputFrame(this.cw, this.access, args, this.maxLocals);
/* 2401 */         visitFrame(f);
/* 2402 */         Label l = this.labels;
/* 2403 */         while (l != null)
/*      */         {
/*      */ 
/*      */ 
/*      */           
/* 2408 */           k = l.position - 3;
/* 2409 */           if ((l.status & 0x20) != 0 || (k >= 0 && resize[k])) {
/*      */             
/* 2411 */             getNewOffset(allIndexes, allSizes, l);
/*      */             
/* 2413 */             visitFrame(l.frame);
/*      */           } 
/* 2415 */           l = l.successor;
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       }
/*      */       else {
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2432 */         this.cw.invalidFrames = true;
/*      */       } 
/*      */     }
/*      */     
/* 2436 */     Handler h = this.firstHandler;
/* 2437 */     while (h != null) {
/* 2438 */       getNewOffset(allIndexes, allSizes, h.start);
/* 2439 */       getNewOffset(allIndexes, allSizes, h.end);
/* 2440 */       getNewOffset(allIndexes, allSizes, h.handler);
/* 2441 */       h = h.next;
/*      */     } 
/*      */     
/*      */     int i;
/* 2445 */     for (i = 0; i < 2; i++) {
/* 2446 */       ByteVector bv = (i == 0) ? this.localVar : this.localVarType;
/* 2447 */       if (bv != null) {
/* 2448 */         b = bv.data;
/* 2449 */         k = 0;
/* 2450 */         while (k < bv.length) {
/* 2451 */           int m = readUnsignedShort(b, k);
/* 2452 */           int n = getNewOffset(allIndexes, allSizes, 0, m);
/* 2453 */           writeShort(b, k, n);
/* 2454 */           m += readUnsignedShort(b, k + 2);
/* 2455 */           n = getNewOffset(allIndexes, allSizes, 0, m) - n;
/*      */           
/* 2457 */           writeShort(b, k + 2, n);
/* 2458 */           k += 10;
/*      */         } 
/*      */       } 
/*      */     } 
/* 2462 */     if (this.lineNumber != null) {
/* 2463 */       b = this.lineNumber.data;
/* 2464 */       k = 0;
/* 2465 */       while (k < this.lineNumber.length) {
/* 2466 */         writeShort(b, k, getNewOffset(allIndexes, allSizes, 0, readUnsignedShort(b, k)));
/*      */ 
/*      */ 
/*      */         
/* 2470 */         k += 4;
/*      */       } 
/*      */     } 
/*      */     
/* 2474 */     Attribute attr = this.cattrs;
/* 2475 */     while (attr != null) {
/* 2476 */       Label[] labels = attr.getLabels();
/* 2477 */       if (labels != null) {
/* 2478 */         for (i = labels.length - 1; i >= 0; i--) {
/* 2479 */           getNewOffset(allIndexes, allSizes, labels[i]);
/*      */         }
/*      */       }
/* 2482 */       attr = attr.next;
/*      */     } 
/*      */ 
/*      */     
/* 2486 */     this.code = newCode;
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
/* 2497 */   static int readUnsignedShort(byte[] b, int index) { return (b[index] & 0xFF) << 8 | b[index + 1] & 0xFF; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2508 */   static short readShort(byte[] b, int index) { return (short)((b[index] & 0xFF) << 8 | b[index + 1] & 0xFF); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2519 */   static int readInt(byte[] b, int index) { return (b[index] & 0xFF) << 24 | (b[index + 1] & 0xFF) << 16 | (b[index + 2] & 0xFF) << 8 | b[index + 3] & 0xFF; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static void writeShort(byte[] b, int index, int s) {
/* 2531 */     b[index] = (byte)(s >>> 8);
/* 2532 */     b[index + 1] = (byte)s;
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
/*      */   static int getNewOffset(int[] indexes, int[] sizes, int begin, int end) {
/* 2562 */     int offset = end - begin;
/* 2563 */     for (int i = 0; i < indexes.length; i++) {
/* 2564 */       if (begin < indexes[i] && indexes[i] <= end) {
/*      */         
/* 2566 */         offset += sizes[i];
/* 2567 */       } else if (end < indexes[i] && indexes[i] <= begin) {
/*      */         
/* 2569 */         offset -= sizes[i];
/*      */       } 
/*      */     } 
/* 2572 */     return offset;
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
/*      */   static void getNewOffset(int[] indexes, int[] sizes, Label label) {
/* 2596 */     if ((label.status & 0x4) == 0) {
/* 2597 */       label.position = getNewOffset(indexes, sizes, 0, label.position);
/* 2598 */       label.status |= 0x4;
/*      */     } 
/*      */   }
/*      */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebean\enhance\asm\MethodWriter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */