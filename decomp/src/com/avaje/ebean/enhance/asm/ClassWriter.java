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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class ClassWriter
/*      */   implements ClassVisitor
/*      */ {
/*      */   public static final int COMPUTE_MAXS = 1;
/*      */   public static final int COMPUTE_FRAMES = 2;
/*      */   static final int NOARG_INSN = 0;
/*      */   static final int SBYTE_INSN = 1;
/*      */   static final int SHORT_INSN = 2;
/*      */   static final int VAR_INSN = 3;
/*      */   static final int IMPLVAR_INSN = 4;
/*      */   static final int TYPE_INSN = 5;
/*      */   static final int FIELDORMETH_INSN = 6;
/*      */   static final int ITFMETH_INSN = 7;
/*      */   static final int LABEL_INSN = 8;
/*      */   static final int LABELW_INSN = 9;
/*      */   static final int LDC_INSN = 10;
/*      */   static final int LDCW_INSN = 11;
/*      */   static final int IINC_INSN = 12;
/*      */   static final int TABL_INSN = 13;
/*      */   static final int LOOK_INSN = 14;
/*      */   static final int MANA_INSN = 15;
/*      */   static final int WIDE_INSN = 16;
/*      */   static final byte[] TYPE;
/*      */   static final int CLASS = 7;
/*      */   static final int FIELD = 9;
/*      */   static final int METH = 10;
/*      */   static final int IMETH = 11;
/*      */   static final int STR = 8;
/*      */   static final int INT = 3;
/*      */   static final int FLOAT = 4;
/*      */   static final int LONG = 5;
/*      */   static final int DOUBLE = 6;
/*      */   static final int NAME_TYPE = 12;
/*      */   static final int UTF8 = 1;
/*      */   static final int TYPE_NORMAL = 13;
/*      */   static final int TYPE_UNINIT = 14;
/*      */   static final int TYPE_MERGED = 15;
/*      */   ClassReader cr;
/*      */   int version;
/*      */   int index;
/*      */   final ByteVector pool;
/*      */   Item[] items;
/*      */   int threshold;
/*      */   final Item key;
/*      */   final Item key2;
/*      */   final Item key3;
/*      */   Item[] typeTable;
/*      */   private short typeCount;
/*      */   private int access;
/*      */   private int name;
/*      */   String thisName;
/*      */   private int signature;
/*      */   private int superName;
/*      */   private int interfaceCount;
/*      */   private int[] interfaces;
/*      */   private int sourceFile;
/*      */   private ByteVector sourceDebug;
/*      */   private int enclosingMethodOwner;
/*      */   private int enclosingMethod;
/*      */   private AnnotationWriter anns;
/*      */   private AnnotationWriter ianns;
/*      */   private Attribute attrs;
/*      */   private int innerClassesCount;
/*      */   private ByteVector innerClasses;
/*      */   FieldWriter firstField;
/*      */   FieldWriter lastField;
/*      */   MethodWriter firstMethod;
/*      */   MethodWriter lastMethod;
/*      */   private final boolean computeMaxs;
/*      */   private final boolean computeFrames;
/*      */   boolean invalidFrames;
/*      */   
/*      */   static  {
/*  447 */     byte[] b = new byte[220];
/*  448 */     String s = "AAAAAAAAAAAAAAAABCKLLDDDDDEEEEEEEEEEEEEEEEEEEEAAAAAAAADDDDDEEEEEEEEEEEEEEEEEEEEAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAMAAAAAAAAAAAAAAAAAAAAIIIIIIIIIIIIIIIIDNOAAAAAAGGGGGGGHAFBFAAFFAAQPIIJJIIIIIIIIIIIIIIIIII";
/*      */ 
/*      */ 
/*      */     
/*  452 */     for (i = 0; i < b.length; i++) {
/*  453 */       b[i] = (byte)(s.charAt(i) - 'A');
/*      */     }
/*  455 */     TYPE = b;
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
/*      */   public ClassWriter(int flags) {
/*  536 */     this.index = 1;
/*  537 */     this.pool = new ByteVector();
/*  538 */     this.items = new Item[256];
/*  539 */     this.threshold = (int)(0.75D * this.items.length);
/*  540 */     this.key = new Item();
/*  541 */     this.key2 = new Item();
/*  542 */     this.key3 = new Item();
/*  543 */     this.computeMaxs = ((flags & true) != 0);
/*  544 */     this.computeFrames = ((flags & 0x2) != 0);
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
/*      */   public ClassWriter(ClassReader classReader, int flags) {
/*  572 */     this(flags);
/*  573 */     classReader.copyPool(this);
/*  574 */     this.cr = classReader;
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
/*      */   public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
/*  589 */     this.version = version;
/*  590 */     this.access = access;
/*  591 */     this.name = newClass(name);
/*  592 */     this.thisName = name;
/*  593 */     if (signature != null) {
/*  594 */       this.signature = newUTF8(signature);
/*      */     }
/*  596 */     this.superName = (superName == null) ? 0 : newClass(superName);
/*  597 */     if (interfaces != null && interfaces.length > 0) {
/*  598 */       this.interfaceCount = interfaces.length;
/*  599 */       this.interfaces = new int[this.interfaceCount];
/*  600 */       for (int i = 0; i < this.interfaceCount; i++) {
/*  601 */         this.interfaces[i] = newClass(interfaces[i]);
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public void visitSource(String file, String debug) {
/*  607 */     if (file != null) {
/*  608 */       this.sourceFile = newUTF8(file);
/*      */     }
/*  610 */     if (debug != null) {
/*  611 */       this.sourceDebug = (new ByteVector()).putUTF8(debug);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void visitOuterClass(String owner, String name, String desc) {
/*  620 */     this.enclosingMethodOwner = newClass(owner);
/*  621 */     if (name != null && desc != null) {
/*  622 */       this.enclosingMethod = newNameType(name, desc);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
/*  633 */     ByteVector bv = new ByteVector();
/*      */     
/*  635 */     bv.putShort(newUTF8(desc)).putShort(0);
/*  636 */     AnnotationWriter aw = new AnnotationWriter(this, true, bv, bv, 2);
/*  637 */     if (visible) {
/*  638 */       aw.next = this.anns;
/*  639 */       this.anns = aw;
/*      */     } else {
/*  641 */       aw.next = this.ianns;
/*  642 */       this.ianns = aw;
/*      */     } 
/*  644 */     return aw;
/*      */   }
/*      */   
/*      */   public void visitAttribute(Attribute attr) {
/*  648 */     attr.next = this.attrs;
/*  649 */     this.attrs = attr;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void visitInnerClass(String name, String outerName, String innerName, int access) {
/*  658 */     if (this.innerClasses == null) {
/*  659 */       this.innerClasses = new ByteVector();
/*      */     }
/*  661 */     this.innerClassesCount++;
/*  662 */     this.innerClasses.putShort((name == null) ? 0 : newClass(name));
/*  663 */     this.innerClasses.putShort((outerName == null) ? 0 : newClass(outerName));
/*  664 */     this.innerClasses.putShort((innerName == null) ? 0 : newUTF8(innerName));
/*  665 */     this.innerClasses.putShort(access);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  675 */   public FieldVisitor visitField(int access, String name, String desc, String signature, Object value) { return new FieldWriter(this, access, name, desc, signature, value); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  685 */   public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) { return new MethodWriter(this, access, name, desc, signature, exceptions, this.computeMaxs, this.computeFrames); }
/*      */ 
/*      */ 
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
/*      */ 
/*      */   
/*      */   public byte[] toByteArray() {
/*  709 */     int size = 24 + 2 * this.interfaceCount;
/*  710 */     int nbFields = 0;
/*  711 */     FieldWriter fb = this.firstField;
/*  712 */     while (fb != null) {
/*  713 */       nbFields++;
/*  714 */       size += fb.getSize();
/*  715 */       fb = fb.next;
/*      */     } 
/*  717 */     int nbMethods = 0;
/*  718 */     MethodWriter mb = this.firstMethod;
/*  719 */     while (mb != null) {
/*  720 */       nbMethods++;
/*  721 */       size += mb.getSize();
/*  722 */       mb = mb.next;
/*      */     } 
/*  724 */     int attributeCount = 0;
/*  725 */     if (this.signature != 0) {
/*  726 */       attributeCount++;
/*  727 */       size += 8;
/*  728 */       newUTF8("Signature");
/*      */     } 
/*  730 */     if (this.sourceFile != 0) {
/*  731 */       attributeCount++;
/*  732 */       size += 8;
/*  733 */       newUTF8("SourceFile");
/*      */     } 
/*  735 */     if (this.sourceDebug != null) {
/*  736 */       attributeCount++;
/*  737 */       size += this.sourceDebug.length + 4;
/*  738 */       newUTF8("SourceDebugExtension");
/*      */     } 
/*  740 */     if (this.enclosingMethodOwner != 0) {
/*  741 */       attributeCount++;
/*  742 */       size += 10;
/*  743 */       newUTF8("EnclosingMethod");
/*      */     } 
/*  745 */     if ((this.access & 0x20000) != 0) {
/*  746 */       attributeCount++;
/*  747 */       size += 6;
/*  748 */       newUTF8("Deprecated");
/*      */     } 
/*  750 */     if ((this.access & 0x1000) != 0 && (this.version & 0xFFFF) < 49) {
/*      */ 
/*      */       
/*  753 */       attributeCount++;
/*  754 */       size += 6;
/*  755 */       newUTF8("Synthetic");
/*      */     } 
/*  757 */     if (this.innerClasses != null) {
/*  758 */       attributeCount++;
/*  759 */       size += 8 + this.innerClasses.length;
/*  760 */       newUTF8("InnerClasses");
/*      */     } 
/*  762 */     if (this.anns != null) {
/*  763 */       attributeCount++;
/*  764 */       size += 8 + this.anns.getSize();
/*  765 */       newUTF8("RuntimeVisibleAnnotations");
/*      */     } 
/*  767 */     if (this.ianns != null) {
/*  768 */       attributeCount++;
/*  769 */       size += 8 + this.ianns.getSize();
/*  770 */       newUTF8("RuntimeInvisibleAnnotations");
/*      */     } 
/*  772 */     if (this.attrs != null) {
/*  773 */       attributeCount += this.attrs.getCount();
/*  774 */       size += this.attrs.getSize(this, null, 0, -1, -1);
/*      */     } 
/*  776 */     size += this.pool.length;
/*      */ 
/*      */     
/*  779 */     ByteVector out = new ByteVector(size);
/*  780 */     out.putInt(-889275714).putInt(this.version);
/*  781 */     out.putShort(this.index).putByteArray(this.pool.data, 0, this.pool.length);
/*  782 */     out.putShort(this.access).putShort(this.name).putShort(this.superName);
/*  783 */     out.putShort(this.interfaceCount);
/*  784 */     for (int i = 0; i < this.interfaceCount; i++) {
/*  785 */       out.putShort(this.interfaces[i]);
/*      */     }
/*  787 */     out.putShort(nbFields);
/*  788 */     fb = this.firstField;
/*  789 */     while (fb != null) {
/*  790 */       fb.put(out);
/*  791 */       fb = fb.next;
/*      */     } 
/*  793 */     out.putShort(nbMethods);
/*  794 */     mb = this.firstMethod;
/*  795 */     while (mb != null) {
/*  796 */       mb.put(out);
/*  797 */       mb = mb.next;
/*      */     } 
/*  799 */     out.putShort(attributeCount);
/*  800 */     if (this.signature != 0) {
/*  801 */       out.putShort(newUTF8("Signature")).putInt(2).putShort(this.signature);
/*      */     }
/*  803 */     if (this.sourceFile != 0) {
/*  804 */       out.putShort(newUTF8("SourceFile")).putInt(2).putShort(this.sourceFile);
/*      */     }
/*  806 */     if (this.sourceDebug != null) {
/*  807 */       int len = this.sourceDebug.length - 2;
/*  808 */       out.putShort(newUTF8("SourceDebugExtension")).putInt(len);
/*  809 */       out.putByteArray(this.sourceDebug.data, 2, len);
/*      */     } 
/*  811 */     if (this.enclosingMethodOwner != 0) {
/*  812 */       out.putShort(newUTF8("EnclosingMethod")).putInt(4);
/*  813 */       out.putShort(this.enclosingMethodOwner).putShort(this.enclosingMethod);
/*      */     } 
/*  815 */     if ((this.access & 0x20000) != 0) {
/*  816 */       out.putShort(newUTF8("Deprecated")).putInt(0);
/*      */     }
/*  818 */     if ((this.access & 0x1000) != 0 && (this.version & 0xFFFF) < 49)
/*      */     {
/*      */       
/*  821 */       out.putShort(newUTF8("Synthetic")).putInt(0);
/*      */     }
/*  823 */     if (this.innerClasses != null) {
/*  824 */       out.putShort(newUTF8("InnerClasses"));
/*  825 */       out.putInt(this.innerClasses.length + 2).putShort(this.innerClassesCount);
/*  826 */       out.putByteArray(this.innerClasses.data, 0, this.innerClasses.length);
/*      */     } 
/*  828 */     if (this.anns != null) {
/*  829 */       out.putShort(newUTF8("RuntimeVisibleAnnotations"));
/*  830 */       this.anns.put(out);
/*      */     } 
/*  832 */     if (this.ianns != null) {
/*  833 */       out.putShort(newUTF8("RuntimeInvisibleAnnotations"));
/*  834 */       this.ianns.put(out);
/*      */     } 
/*  836 */     if (this.attrs != null) {
/*  837 */       this.attrs.put(this, null, 0, -1, -1, out);
/*      */     }
/*  839 */     if (this.invalidFrames) {
/*  840 */       ClassWriter cw = new ClassWriter(2);
/*  841 */       (new ClassReader(out.data)).accept(cw, 4);
/*  842 */       return cw.toByteArray();
/*      */     } 
/*  844 */     return out.data;
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
/*      */   Item newConstItem(Object cst) {
/*  862 */     if (cst instanceof Integer) {
/*  863 */       int val = ((Integer)cst).intValue();
/*  864 */       return newInteger(val);
/*  865 */     }  if (cst instanceof Byte) {
/*  866 */       int val = ((Byte)cst).intValue();
/*  867 */       return newInteger(val);
/*  868 */     }  if (cst instanceof Character) {
/*  869 */       int val = ((Character)cst).charValue();
/*  870 */       return newInteger(val);
/*  871 */     }  if (cst instanceof Short) {
/*  872 */       int val = ((Short)cst).intValue();
/*  873 */       return newInteger(val);
/*  874 */     }  if (cst instanceof Boolean) {
/*  875 */       int val = ((Boolean)cst).booleanValue() ? 1 : 0;
/*  876 */       return newInteger(val);
/*  877 */     }  if (cst instanceof Float) {
/*  878 */       float val = ((Float)cst).floatValue();
/*  879 */       return newFloat(val);
/*  880 */     }  if (cst instanceof Long) {
/*  881 */       long val = ((Long)cst).longValue();
/*  882 */       return newLong(val);
/*  883 */     }  if (cst instanceof Double) {
/*  884 */       double val = ((Double)cst).doubleValue();
/*  885 */       return newDouble(val);
/*  886 */     }  if (cst instanceof String)
/*  887 */       return newString((String)cst); 
/*  888 */     if (cst instanceof Type) {
/*  889 */       Type t = (Type)cst;
/*  890 */       return newClassItem((t.getSort() == 10) ? t.getInternalName() : t.getDescriptor());
/*      */     } 
/*      */ 
/*      */     
/*  894 */     throw new IllegalArgumentException("value " + cst);
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
/*  911 */   public int newConst(Object cst) { return (newConstItem(cst)).index; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int newUTF8(String value) {
/*  924 */     this.key.set(1, value, null, null);
/*  925 */     Item result = get(this.key);
/*  926 */     if (result == null) {
/*  927 */       this.pool.putByte(1).putUTF8(value);
/*  928 */       result = new Item(this.index++, this.key);
/*  929 */       put(result);
/*      */     } 
/*  931 */     return result.index;
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
/*      */   Item newClassItem(String value) {
/*  944 */     this.key2.set(7, value, null, null);
/*  945 */     Item result = get(this.key2);
/*  946 */     if (result == null) {
/*  947 */       this.pool.put12(7, newUTF8(value));
/*  948 */       result = new Item(this.index++, this.key2);
/*  949 */       put(result);
/*      */     } 
/*  951 */     return result;
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
/*  964 */   public int newClass(String value) { return (newClassItem(value)).index; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   Item newFieldItem(String owner, String name, String desc) {
/*  978 */     this.key3.set(9, owner, name, desc);
/*  979 */     Item result = get(this.key3);
/*  980 */     if (result == null) {
/*  981 */       put122(9, newClass(owner), newNameType(name, desc));
/*  982 */       result = new Item(this.index++, this.key3);
/*  983 */       put(result);
/*      */     } 
/*  985 */     return result;
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
/* 1001 */   public int newField(String owner, String name, String desc) { return (newFieldItem(owner, name, desc)).index; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   Item newMethodItem(String owner, String name, String desc, boolean itf) {
/* 1020 */     int type = itf ? 11 : 10;
/* 1021 */     this.key3.set(type, owner, name, desc);
/* 1022 */     Item result = get(this.key3);
/* 1023 */     if (result == null) {
/* 1024 */       put122(type, newClass(owner), newNameType(name, desc));
/* 1025 */       result = new Item(this.index++, this.key3);
/* 1026 */       put(result);
/*      */     } 
/* 1028 */     return result;
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
/* 1049 */   public int newMethod(String owner, String name, String desc, boolean itf) { return (newMethodItem(owner, name, desc, itf)).index; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   Item newInteger(int value) {
/* 1060 */     this.key.set(value);
/* 1061 */     Item result = get(this.key);
/* 1062 */     if (result == null) {
/* 1063 */       this.pool.putByte(3).putInt(value);
/* 1064 */       result = new Item(this.index++, this.key);
/* 1065 */       put(result);
/*      */     } 
/* 1067 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   Item newFloat(float value) {
/* 1078 */     this.key.set(value);
/* 1079 */     Item result = get(this.key);
/* 1080 */     if (result == null) {
/* 1081 */       this.pool.putByte(4).putInt(this.key.intVal);
/* 1082 */       result = new Item(this.index++, this.key);
/* 1083 */       put(result);
/*      */     } 
/* 1085 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   Item newLong(long value) {
/* 1096 */     this.key.set(value);
/* 1097 */     Item result = get(this.key);
/* 1098 */     if (result == null) {
/* 1099 */       this.pool.putByte(5).putLong(value);
/* 1100 */       result = new Item(this.index, this.key);
/* 1101 */       put(result);
/* 1102 */       this.index += 2;
/*      */     } 
/* 1104 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   Item newDouble(double value) {
/* 1115 */     this.key.set(value);
/* 1116 */     Item result = get(this.key);
/* 1117 */     if (result == null) {
/* 1118 */       this.pool.putByte(6).putLong(this.key.longVal);
/* 1119 */       result = new Item(this.index, this.key);
/* 1120 */       put(result);
/* 1121 */       this.index += 2;
/*      */     } 
/* 1123 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Item newString(String value) {
/* 1134 */     this.key2.set(8, value, null, null);
/* 1135 */     Item result = get(this.key2);
/* 1136 */     if (result == null) {
/* 1137 */       this.pool.put12(8, newUTF8(value));
/* 1138 */       result = new Item(this.index++, this.key2);
/* 1139 */       put(result);
/*      */     } 
/* 1141 */     return result;
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
/*      */   public int newNameType(String name, String desc) {
/* 1155 */     this.key2.set(12, name, desc, null);
/* 1156 */     Item result = get(this.key2);
/* 1157 */     if (result == null) {
/* 1158 */       put122(12, newUTF8(name), newUTF8(desc));
/* 1159 */       result = new Item(this.index++, this.key2);
/* 1160 */       put(result);
/*      */     } 
/* 1162 */     return result.index;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   int addType(String type) {
/* 1173 */     this.key.set(13, type, null, null);
/* 1174 */     Item result = get(this.key);
/* 1175 */     if (result == null) {
/* 1176 */       result = addType(this.key);
/*      */     }
/* 1178 */     return result.index;
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
/*      */   int addUninitializedType(String type, int offset) {
/* 1192 */     this.key.type = 14;
/* 1193 */     this.key.intVal = offset;
/* 1194 */     this.key.strVal1 = type;
/* 1195 */     this.key.hashCode = 0x7FFFFFFF & 14 + type.hashCode() + offset;
/* 1196 */     Item result = get(this.key);
/* 1197 */     if (result == null) {
/* 1198 */       result = addType(this.key);
/*      */     }
/* 1200 */     return result.index;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Item addType(Item item) {
/* 1211 */     this.typeCount = (short)(this.typeCount + 1);
/* 1212 */     Item result = new Item(this.typeCount, this.key);
/* 1213 */     put(result);
/* 1214 */     if (this.typeTable == null) {
/* 1215 */       this.typeTable = new Item[16];
/*      */     }
/* 1217 */     if (this.typeCount == this.typeTable.length) {
/* 1218 */       Item[] newTable = new Item[2 * this.typeTable.length];
/* 1219 */       System.arraycopy(this.typeTable, 0, newTable, 0, this.typeTable.length);
/* 1220 */       this.typeTable = newTable;
/*      */     } 
/* 1222 */     this.typeTable[this.typeCount] = result;
/* 1223 */     return result;
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
/*      */   int getMergedType(int type1, int type2) {
/* 1237 */     this.key2.type = 15;
/* 1238 */     this.key2.longVal = type1 | type2 << 32;
/* 1239 */     this.key2.hashCode = 0x7FFFFFFF & 15 + type1 + type2;
/* 1240 */     Item result = get(this.key2);
/* 1241 */     if (result == null) {
/* 1242 */       String t = (this.typeTable[type1]).strVal1;
/* 1243 */       String u = (this.typeTable[type2]).strVal1;
/* 1244 */       this.key2.intVal = addType(getCommonSuperClass(t, u));
/* 1245 */       result = new Item(false, this.key2);
/* 1246 */       put(result);
/*      */     } 
/* 1248 */     return result.intVal;
/*      */   }
/*      */   
/*      */   protected Class<?> classForName(String name) throws ClassNotFoundException {
/*      */     try {
/* 1253 */       ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
/* 1254 */       if (contextClassLoader != null) {
/* 1255 */         return Class.forName(name, false, contextClassLoader);
/*      */       }
/* 1257 */     } catch (Throwable e) {}
/*      */ 
/*      */     
/* 1260 */     return Class.forName(name, false, getClass().getClassLoader());
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
/*      */   protected String getCommonSuperClass(String type1, String type2) {
/*      */     Class d;
/*      */     Class c;
/*      */     try {
/* 1281 */       c = classForName(type1.replace('/', '.'));
/* 1282 */       d = classForName(type2.replace('/', '.'));
/* 1283 */     } catch (Exception e) {
/* 1284 */       throw new RuntimeException(e.toString());
/*      */     } 
/* 1286 */     if (c.isAssignableFrom(d)) {
/* 1287 */       return type1;
/*      */     }
/* 1289 */     if (d.isAssignableFrom(c)) {
/* 1290 */       return type2;
/*      */     }
/* 1292 */     if (c.isInterface() || d.isInterface()) {
/* 1293 */       return "java/lang/Object";
/*      */     }
/*      */     do {
/* 1296 */       c = c.getSuperclass();
/* 1297 */     } while (!c.isAssignableFrom(d));
/* 1298 */     return c.getName().replace('.', '/');
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
/*      */   private Item get(Item key) {
/* 1311 */     Item i = this.items[key.hashCode % this.items.length];
/* 1312 */     while (i != null && !key.isEqualTo(i)) {
/* 1313 */       i = i.next;
/*      */     }
/* 1315 */     return i;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void put(Item i) {
/* 1325 */     if (this.index > this.threshold) {
/* 1326 */       int ll = this.items.length;
/* 1327 */       int nl = ll * 2 + 1;
/* 1328 */       Item[] newItems = new Item[nl];
/* 1329 */       for (int l = ll - 1; l >= 0; l--) {
/* 1330 */         Item j = this.items[l];
/* 1331 */         while (j != null) {
/* 1332 */           int index = j.hashCode % newItems.length;
/* 1333 */           Item k = j.next;
/* 1334 */           j.next = newItems[index];
/* 1335 */           newItems[index] = j;
/* 1336 */           j = k;
/*      */         } 
/*      */       } 
/* 1339 */       this.items = newItems;
/* 1340 */       this.threshold = (int)(nl * 0.75D);
/*      */     } 
/* 1342 */     int index = i.hashCode % this.items.length;
/* 1343 */     i.next = this.items[index];
/* 1344 */     this.items[index] = i;
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
/* 1355 */   private void put122(int b, int s1, int s2) { this.pool.put12(b, s1).putShort(s2); }
/*      */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebean\enhance\asm\ClassWriter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */