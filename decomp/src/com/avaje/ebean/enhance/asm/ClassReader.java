/*      */ package com.avaje.ebean.enhance.asm;
/*      */ 
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class ClassReader
/*      */ {
/*      */   static final boolean SIGNATURES = true;
/*      */   static final boolean ANNOTATIONS = true;
/*      */   static final boolean FRAMES = true;
/*      */   static final boolean WRITER = true;
/*      */   static final boolean RESIZE = true;
/*      */   public static final int SKIP_CODE = 1;
/*      */   public static final int SKIP_DEBUG = 2;
/*      */   public static final int SKIP_FRAMES = 4;
/*      */   public static final int EXPAND_FRAMES = 8;
/*      */   public final byte[] b;
/*      */   private final int[] items;
/*      */   private final String[] strings;
/*      */   private final int maxStringLength;
/*      */   public final int header;
/*      */   
/*  153 */   public ClassReader(byte[] b) { this(b, 0, b.length); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ClassReader(byte[] b, int off, int len) {
/*  164 */     this.b = b;
/*      */     
/*  166 */     this.items = new int[readUnsignedShort(off + 8)];
/*  167 */     int n = this.items.length;
/*  168 */     this.strings = new String[n];
/*  169 */     int max = 0;
/*  170 */     int index = off + 10;
/*  171 */     for (int i = 1; i < n; i++) {
/*  172 */       int size; this.items[i] = index + 1;
/*      */       
/*  174 */       switch (b[index]) {
/*      */         case 3:
/*      */         case 4:
/*      */         case 9:
/*      */         case 10:
/*      */         case 11:
/*      */         case 12:
/*  181 */           size = 5;
/*      */           break;
/*      */         case 5:
/*      */         case 6:
/*  185 */           size = 9;
/*  186 */           i++;
/*      */           break;
/*      */         case 1:
/*  189 */           size = 3 + readUnsignedShort(index + 1);
/*  190 */           if (size > max) {
/*  191 */             max = size;
/*      */           }
/*      */           break;
/*      */ 
/*      */         
/*      */         default:
/*  197 */           size = 3;
/*      */           break;
/*      */       } 
/*  200 */       index += size;
/*      */     } 
/*  202 */     this.maxStringLength = max;
/*      */     
/*  204 */     this.header = index;
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
/*  217 */   public int getAccess() { return readUnsignedShort(this.header); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  229 */   public String getClassName() { return readClass(this.header + 2, new char[this.maxStringLength]); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getSuperName() {
/*  243 */     int n = this.items[readUnsignedShort(this.header + 4)];
/*  244 */     return (n == 0) ? null : readUTF8(n, new char[this.maxStringLength]);
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
/*      */   public String[] getInterfaces() {
/*  257 */     int index = this.header + 6;
/*  258 */     int n = readUnsignedShort(index);
/*  259 */     String[] interfaces = new String[n];
/*  260 */     if (n > 0) {
/*  261 */       char[] buf = new char[this.maxStringLength];
/*  262 */       for (int i = 0; i < n; i++) {
/*  263 */         index += 2;
/*  264 */         interfaces[i] = readClass(index, buf);
/*      */       } 
/*      */     } 
/*  267 */     return interfaces;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void copyPool(ClassWriter classWriter) {
/*  277 */     char[] buf = new char[this.maxStringLength];
/*  278 */     int ll = this.items.length;
/*  279 */     Item[] items2 = new Item[ll];
/*  280 */     for (int i = 1; i < ll; i++) {
/*  281 */       String s; int nameType, index = this.items[i];
/*  282 */       int tag = this.b[index - 1];
/*  283 */       Item item = new Item(i);
/*      */       
/*  285 */       switch (tag) {
/*      */         case 9:
/*      */         case 10:
/*      */         case 11:
/*  289 */           nameType = this.items[readUnsignedShort(index + 2)];
/*  290 */           item.set(tag, readClass(index, buf), readUTF8(nameType, buf), readUTF8(nameType + 2, buf));
/*      */           break;
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         case 3:
/*  297 */           item.set(readInt(index));
/*      */           break;
/*      */         
/*      */         case 4:
/*  301 */           item.set(Float.intBitsToFloat(readInt(index)));
/*      */           break;
/*      */         
/*      */         case 12:
/*  305 */           item.set(tag, readUTF8(index, buf), readUTF8(index + 2, buf), null);
/*      */           break;
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         case 5:
/*  312 */           item.set(readLong(index));
/*  313 */           i++;
/*      */           break;
/*      */         
/*      */         case 6:
/*  317 */           item.set(Double.longBitsToDouble(readLong(index)));
/*  318 */           i++;
/*      */           break;
/*      */         
/*      */         case 1:
/*  322 */           s = this.strings[i];
/*  323 */           if (s == null) {
/*  324 */             index = this.items[i];
/*  325 */             s = this.strings[i] = readUTF(index + 2, readUnsignedShort(index), buf);
/*      */           } 
/*      */ 
/*      */           
/*  329 */           item.set(tag, s, null, null);
/*      */           break;
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         default:
/*  336 */           item.set(tag, readUTF8(index, buf), null, null);
/*      */           break;
/*      */       } 
/*      */       
/*  340 */       int index2 = item.hashCode % items2.length;
/*  341 */       item.next = items2[index2];
/*  342 */       items2[index2] = item;
/*      */     } 
/*      */     
/*  345 */     int off = this.items[1] - 1;
/*  346 */     classWriter.pool.putByteArray(this.b, off, this.header - off);
/*  347 */     classWriter.items = items2;
/*  348 */     classWriter.threshold = (int)(0.75D * ll);
/*  349 */     classWriter.index = ll;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  359 */   public ClassReader(InputStream is) throws IOException { this(readClass(is)); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  369 */   public ClassReader(String name) throws IOException { this(ClassLoader.getSystemResourceAsStream(name.replace('.', '/') + ".class")); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static byte[] readClass(InputStream is) throws IOException {
/*  381 */     if (is == null) {
/*  382 */       throw new IOException("Class not found");
/*      */     }
/*  384 */     byte[] b = new byte[is.available()];
/*  385 */     int len = 0;
/*      */     while (true) {
/*  387 */       int n = is.read(b, len, b.length - len);
/*  388 */       if (n == -1) {
/*  389 */         if (len < b.length) {
/*  390 */           byte[] c = new byte[len];
/*  391 */           System.arraycopy(b, 0, c, 0, len);
/*  392 */           b = c;
/*      */         } 
/*  394 */         return b;
/*      */       } 
/*  396 */       len += n;
/*  397 */       if (len == b.length) {
/*  398 */         byte[] c = new byte[b.length + 1000];
/*  399 */         System.arraycopy(b, 0, c, 0, len);
/*  400 */         b = c;
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
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  420 */   public void accept(ClassVisitor classVisitor, int flags) { accept(classVisitor, new Attribute[0], flags); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void accept(ClassVisitor classVisitor, Attribute[] attrs, int flags) {
/*  446 */     byte[] b = this.b;
/*  447 */     char[] c = new char[this.maxStringLength];
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  457 */     int anns = 0;
/*  458 */     int ianns = 0;
/*  459 */     Attribute cattrs = null;
/*      */ 
/*      */     
/*  462 */     int u = this.header;
/*  463 */     int access = readUnsignedShort(u);
/*  464 */     String name = readClass(u + 2, c);
/*  465 */     int v = this.items[readUnsignedShort(u + 4)];
/*  466 */     String superClassName = (v == 0) ? null : readUTF8(v, c);
/*  467 */     String[] implementedItfs = new String[readUnsignedShort(u + 6)];
/*  468 */     int w = 0;
/*  469 */     u += 8; int i;
/*  470 */     for (i = 0; i < implementedItfs.length; i++) {
/*  471 */       implementedItfs[i] = readClass(u, c);
/*  472 */       u += 2;
/*      */     } 
/*      */     
/*  475 */     boolean skipCode = ((flags & true) != 0);
/*  476 */     boolean skipDebug = ((flags & 0x2) != 0);
/*  477 */     boolean unzip = ((flags & 0x8) != 0);
/*      */ 
/*      */     
/*  480 */     v = u;
/*  481 */     i = readUnsignedShort(v);
/*  482 */     v += 2;
/*  483 */     for (; i > 0; i--) {
/*  484 */       int j = readUnsignedShort(v + 6);
/*  485 */       v += 8;
/*  486 */       for (; j > 0; j--) {
/*  487 */         v += 6 + readInt(v + 2);
/*      */       }
/*      */     } 
/*  490 */     i = readUnsignedShort(v);
/*  491 */     v += 2;
/*  492 */     for (; i > 0; i--) {
/*  493 */       int j = readUnsignedShort(v + 6);
/*  494 */       v += 8;
/*  495 */       for (; j > 0; j--) {
/*  496 */         v += 6 + readInt(v + 2);
/*      */       }
/*      */     } 
/*      */     
/*  500 */     String signature = null;
/*  501 */     String sourceFile = null;
/*  502 */     String sourceDebug = null;
/*  503 */     String enclosingOwner = null;
/*  504 */     String enclosingName = null;
/*  505 */     String enclosingDesc = null;
/*      */     
/*  507 */     i = readUnsignedShort(v);
/*  508 */     v += 2;
/*  509 */     for (; i > 0; i--) {
/*  510 */       String attrName = readUTF8(v, c);
/*      */ 
/*      */       
/*  513 */       if ("SourceFile".equals(attrName)) {
/*  514 */         sourceFile = readUTF8(v + 6, c);
/*  515 */       } else if ("InnerClasses".equals(attrName)) {
/*  516 */         w = v + 6;
/*  517 */       } else if ("EnclosingMethod".equals(attrName)) {
/*  518 */         enclosingOwner = readClass(v + 6, c);
/*  519 */         int item = readUnsignedShort(v + 8);
/*  520 */         if (item != 0) {
/*  521 */           enclosingName = readUTF8(this.items[item], c);
/*  522 */           enclosingDesc = readUTF8(this.items[item] + 2, c);
/*      */         } 
/*  524 */       } else if ("Signature".equals(attrName)) {
/*  525 */         signature = readUTF8(v + 6, c);
/*  526 */       } else if ("RuntimeVisibleAnnotations".equals(attrName)) {
/*  527 */         anns = v + 6;
/*  528 */       } else if ("Deprecated".equals(attrName)) {
/*  529 */         access |= 0x20000;
/*  530 */       } else if ("Synthetic".equals(attrName)) {
/*  531 */         access |= 0x1000;
/*  532 */       } else if ("SourceDebugExtension".equals(attrName)) {
/*  533 */         int len = readInt(v + 2);
/*  534 */         sourceDebug = readUTF(v + 6, len, new char[len]);
/*  535 */       } else if ("RuntimeInvisibleAnnotations".equals(attrName)) {
/*  536 */         ianns = v + 6;
/*      */       } else {
/*  538 */         Attribute attr = readAttribute(attrs, attrName, v + 6, readInt(v + 2), c, -1, null);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  545 */         if (attr != null) {
/*  546 */           attr.next = cattrs;
/*  547 */           cattrs = attr;
/*      */         } 
/*      */       } 
/*  550 */       v += 6 + readInt(v + 2);
/*      */     } 
/*      */     
/*  553 */     classVisitor.visit(readInt(4), access, name, signature, superClassName, implementedItfs);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  561 */     if (!skipDebug && (sourceFile != null || sourceDebug != null)) {
/*  562 */       classVisitor.visitSource(sourceFile, sourceDebug);
/*      */     }
/*      */ 
/*      */     
/*  566 */     if (enclosingOwner != null) {
/*  567 */       classVisitor.visitOuterClass(enclosingOwner, enclosingName, enclosingDesc);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  574 */     for (i = 1; i >= 0; i--) {
/*  575 */       v = (i == 0) ? ianns : anns;
/*  576 */       if (v != 0) {
/*  577 */         int j = readUnsignedShort(v);
/*  578 */         v += 2;
/*  579 */         for (; j > 0; j--) {
/*  580 */           v = readAnnotationValues(v + 2, c, true, classVisitor.visitAnnotation(readUTF8(v, c), (i != 0)));
/*      */         }
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  590 */     while (cattrs != null) {
/*  591 */       Attribute attr = cattrs.next;
/*  592 */       cattrs.next = null;
/*  593 */       classVisitor.visitAttribute(cattrs);
/*  594 */       cattrs = attr;
/*      */     } 
/*      */ 
/*      */     
/*  598 */     if (w != 0) {
/*  599 */       i = readUnsignedShort(w);
/*  600 */       w += 2;
/*  601 */       for (; i > 0; i--) {
/*  602 */         classVisitor.visitInnerClass((readUnsignedShort(w) == 0) ? null : readClass(w, c), (readUnsignedShort(w + 2) == 0) ? null : readClass(w + 2, c), (readUnsignedShort(w + 4) == 0) ? null : readUTF8(w + 4, c), readUnsignedShort(w + 6));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  609 */         w += 8;
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  614 */     i = readUnsignedShort(u);
/*  615 */     u += 2;
/*  616 */     for (; i > 0; i--) {
/*  617 */       access = readUnsignedShort(u);
/*  618 */       name = readUTF8(u + 2, c);
/*  619 */       String desc = readUTF8(u + 4, c);
/*      */ 
/*      */       
/*  622 */       int fieldValueItem = 0;
/*  623 */       signature = null;
/*  624 */       anns = 0;
/*  625 */       ianns = 0;
/*  626 */       cattrs = null;
/*      */       
/*  628 */       int j = readUnsignedShort(u + 6);
/*  629 */       u += 8;
/*  630 */       for (; j > 0; j--) {
/*  631 */         String attrName = readUTF8(u, c);
/*      */ 
/*      */         
/*  634 */         if ("ConstantValue".equals(attrName)) {
/*  635 */           fieldValueItem = readUnsignedShort(u + 6);
/*  636 */         } else if ("Signature".equals(attrName)) {
/*  637 */           signature = readUTF8(u + 6, c);
/*  638 */         } else if ("Deprecated".equals(attrName)) {
/*  639 */           access |= 0x20000;
/*  640 */         } else if ("Synthetic".equals(attrName)) {
/*  641 */           access |= 0x1000;
/*  642 */         } else if ("RuntimeVisibleAnnotations".equals(attrName)) {
/*  643 */           anns = u + 6;
/*  644 */         } else if ("RuntimeInvisibleAnnotations".equals(attrName)) {
/*  645 */           ianns = u + 6;
/*      */         } else {
/*  647 */           Attribute attr = readAttribute(attrs, attrName, u + 6, readInt(u + 2), c, -1, null);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  654 */           if (attr != null) {
/*  655 */             attr.next = cattrs;
/*  656 */             cattrs = attr;
/*      */           } 
/*      */         } 
/*  659 */         u += 6 + readInt(u + 2);
/*      */       } 
/*      */       
/*  662 */       FieldVisitor fv = classVisitor.visitField(access, name, desc, signature, (fieldValueItem == 0) ? null : readConst(fieldValueItem, c));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  668 */       if (fv != null) {
/*      */         
/*  670 */         for (j = 1; j >= 0; j--) {
/*  671 */           v = (j == 0) ? ianns : anns;
/*  672 */           if (v != 0) {
/*  673 */             int k = readUnsignedShort(v);
/*  674 */             v += 2;
/*  675 */             for (; k > 0; k--) {
/*  676 */               v = readAnnotationValues(v + 2, c, true, fv.visitAnnotation(readUTF8(v, c), (j != 0)));
/*      */             }
/*      */           } 
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  684 */         while (cattrs != null) {
/*  685 */           Attribute attr = cattrs.next;
/*  686 */           cattrs.next = null;
/*  687 */           fv.visitAttribute(cattrs);
/*  688 */           cattrs = attr;
/*      */         } 
/*  690 */         fv.visitEnd();
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  695 */     i = readUnsignedShort(u);
/*  696 */     u += 2;
/*  697 */     for (; i > 0; i--) {
/*  698 */       String[] exceptions; int u0 = u + 6;
/*  699 */       access = readUnsignedShort(u);
/*  700 */       name = readUTF8(u + 2, c);
/*  701 */       String desc = readUTF8(u + 4, c);
/*  702 */       signature = null;
/*  703 */       anns = 0;
/*  704 */       ianns = 0;
/*  705 */       int dann = 0;
/*  706 */       int mpanns = 0;
/*  707 */       int impanns = 0;
/*  708 */       cattrs = null;
/*  709 */       v = 0;
/*  710 */       w = 0;
/*      */ 
/*      */       
/*  713 */       int j = readUnsignedShort(u + 6);
/*  714 */       u += 8;
/*  715 */       for (; j > 0; j--) {
/*  716 */         String attrName = readUTF8(u, c);
/*  717 */         int attrSize = readInt(u + 2);
/*  718 */         u += 6;
/*      */ 
/*      */         
/*  721 */         if ("Code".equals(attrName)) {
/*  722 */           if (!skipCode) {
/*  723 */             v = u;
/*      */           }
/*  725 */         } else if ("Exceptions".equals(attrName)) {
/*  726 */           w = u;
/*  727 */         } else if ("Signature".equals(attrName)) {
/*  728 */           signature = readUTF8(u, c);
/*  729 */         } else if ("Deprecated".equals(attrName)) {
/*  730 */           access |= 0x20000;
/*  731 */         } else if ("RuntimeVisibleAnnotations".equals(attrName)) {
/*  732 */           anns = u;
/*  733 */         } else if ("AnnotationDefault".equals(attrName)) {
/*  734 */           dann = u;
/*  735 */         } else if ("Synthetic".equals(attrName)) {
/*  736 */           access |= 0x1000;
/*  737 */         } else if ("RuntimeInvisibleAnnotations".equals(attrName)) {
/*  738 */           ianns = u;
/*  739 */         } else if ("RuntimeVisibleParameterAnnotations".equals(attrName)) {
/*      */           
/*  741 */           mpanns = u;
/*  742 */         } else if ("RuntimeInvisibleParameterAnnotations".equals(attrName)) {
/*      */           
/*  744 */           impanns = u;
/*      */         } else {
/*  746 */           Attribute attr = readAttribute(attrs, attrName, u, attrSize, c, -1, null);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  753 */           if (attr != null) {
/*  754 */             attr.next = cattrs;
/*  755 */             cattrs = attr;
/*      */           } 
/*      */         } 
/*  758 */         u += attrSize;
/*      */       } 
/*      */ 
/*      */       
/*  762 */       if (w == 0) {
/*  763 */         exceptions = null;
/*      */       } else {
/*  765 */         exceptions = new String[readUnsignedShort(w)];
/*  766 */         w += 2;
/*  767 */         for (j = 0; j < exceptions.length; j++) {
/*  768 */           exceptions[j] = readClass(w, c);
/*  769 */           w += 2;
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/*  774 */       MethodVisitor mv = classVisitor.visitMethod(access, name, desc, signature, exceptions);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  780 */       if (mv != null) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  792 */         if (mv instanceof MethodWriter) {
/*  793 */           MethodWriter mw = (MethodWriter)mv;
/*  794 */           if (mw.cw.cr == this && 
/*  795 */             signature == mw.signature) {
/*  796 */             boolean sameExceptions = false;
/*  797 */             if (exceptions == null) {
/*  798 */               sameExceptions = (mw.exceptionCount == 0);
/*      */             }
/*  800 */             else if (exceptions.length == mw.exceptionCount) {
/*  801 */               sameExceptions = true;
/*  802 */               for (j = exceptions.length - 1; j >= 0; j--) {
/*      */                 
/*  804 */                 w -= 2;
/*  805 */                 if (mw.exceptions[j] != readUnsignedShort(w)) {
/*      */                   
/*  807 */                   sameExceptions = false;
/*      */                   
/*      */                   break;
/*      */                 } 
/*      */               } 
/*      */             } 
/*  813 */             if (sameExceptions) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */               
/*  820 */               mw.classReaderOffset = u0;
/*  821 */               mw.classReaderLength = u - u0;
/*      */               
/*      */               continue;
/*      */             } 
/*      */           } 
/*      */         } 
/*      */         
/*  828 */         if (dann != 0) {
/*  829 */           AnnotationVisitor dv = mv.visitAnnotationDefault();
/*  830 */           readAnnotationValue(dann, c, null, dv);
/*  831 */           if (dv != null) {
/*  832 */             dv.visitEnd();
/*      */           }
/*      */         } 
/*      */         
/*  836 */         for (j = 1; j >= 0; j--) {
/*  837 */           w = (j == 0) ? ianns : anns;
/*  838 */           if (w != 0) {
/*  839 */             int k = readUnsignedShort(w);
/*  840 */             w += 2;
/*  841 */             for (; k > 0; k--) {
/*  842 */               w = readAnnotationValues(w + 2, c, true, mv.visitAnnotation(readUTF8(w, c), (j != 0)));
/*      */             }
/*      */           } 
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  850 */         if (mpanns != 0) {
/*  851 */           readParameterAnnotations(mpanns, desc, c, true, mv);
/*      */         }
/*  853 */         if (impanns != 0) {
/*  854 */           readParameterAnnotations(impanns, desc, c, false, mv);
/*      */         }
/*  856 */         while (cattrs != null) {
/*  857 */           Attribute attr = cattrs.next;
/*  858 */           cattrs.next = null;
/*  859 */           mv.visitAttribute(cattrs);
/*  860 */           cattrs = attr;
/*      */         } 
/*      */       } 
/*      */       
/*  864 */       if (mv != null && v != 0) {
/*  865 */         int maxStack = readUnsignedShort(v);
/*  866 */         int maxLocals = readUnsignedShort(v + 2);
/*  867 */         int codeLength = readInt(v + 4);
/*  868 */         v += 8;
/*      */         
/*  870 */         int codeStart = v;
/*  871 */         int codeEnd = v + codeLength;
/*      */         
/*  873 */         mv.visitCode();
/*      */ 
/*      */ 
/*      */         
/*  877 */         Label[] labels = new Label[codeLength + 2];
/*  878 */         readLabel(codeLength + 1, labels);
/*  879 */         while (v < codeEnd) {
/*  880 */           w = v - codeStart;
/*  881 */           int opcode = b[v] & 0xFF;
/*  882 */           switch (ClassWriter.TYPE[opcode]) {
/*      */             case 0:
/*      */             case 4:
/*  885 */               v++;
/*      */               continue;
/*      */             case 8:
/*  888 */               readLabel(w + readShort(v + 1), labels);
/*  889 */               v += 3;
/*      */               continue;
/*      */             case 9:
/*  892 */               readLabel(w + readInt(v + 1), labels);
/*  893 */               v += 5;
/*      */               continue;
/*      */             case 16:
/*  896 */               opcode = b[v + 1] & 0xFF;
/*  897 */               if (opcode == 132) {
/*  898 */                 v += 6; continue;
/*      */               } 
/*  900 */               v += 4;
/*      */               continue;
/*      */ 
/*      */             
/*      */             case 13:
/*  905 */               v = v + 4 - (w & 0x3);
/*      */               
/*  907 */               readLabel(w + readInt(v), labels);
/*  908 */               j = readInt(v + 8) - readInt(v + 4) + 1;
/*  909 */               v += 12;
/*  910 */               for (; j > 0; j--) {
/*  911 */                 readLabel(w + readInt(v), labels);
/*  912 */                 v += 4;
/*      */               } 
/*      */               continue;
/*      */             
/*      */             case 14:
/*  917 */               v = v + 4 - (w & 0x3);
/*      */               
/*  919 */               readLabel(w + readInt(v), labels);
/*  920 */               j = readInt(v + 4);
/*  921 */               v += 8;
/*  922 */               for (; j > 0; j--) {
/*  923 */                 readLabel(w + readInt(v + 4), labels);
/*  924 */                 v += 8;
/*      */               } 
/*      */               continue;
/*      */             case 1:
/*      */             case 3:
/*      */             case 10:
/*  930 */               v += 2;
/*      */               continue;
/*      */             case 2:
/*      */             case 5:
/*      */             case 6:
/*      */             case 11:
/*      */             case 12:
/*  937 */               v += 3;
/*      */               continue;
/*      */             case 7:
/*  940 */               v += 5;
/*      */               continue;
/*      */           } 
/*      */           
/*  944 */           v += 4;
/*      */         } 
/*      */ 
/*      */ 
/*      */         
/*  949 */         j = readUnsignedShort(v);
/*  950 */         v += 2;
/*  951 */         for (; j > 0; j--) {
/*  952 */           Label start = readLabel(readUnsignedShort(v), labels);
/*  953 */           Label end = readLabel(readUnsignedShort(v + 2), labels);
/*  954 */           Label handler = readLabel(readUnsignedShort(v + 4), labels);
/*  955 */           int type = readUnsignedShort(v + 6);
/*  956 */           if (type == 0) {
/*  957 */             mv.visitTryCatchBlock(start, end, handler, null);
/*      */           } else {
/*  959 */             mv.visitTryCatchBlock(start, end, handler, readUTF8(this.items[type], c));
/*      */           } 
/*      */ 
/*      */ 
/*      */           
/*  964 */           v += 8;
/*      */         } 
/*      */ 
/*      */         
/*  968 */         int varTable = 0;
/*  969 */         int varTypeTable = 0;
/*  970 */         int stackMap = 0;
/*  971 */         int frameCount = 0;
/*  972 */         int frameMode = 0;
/*  973 */         int frameOffset = 0;
/*  974 */         int frameLocalCount = 0;
/*  975 */         int frameLocalDiff = 0;
/*  976 */         int frameStackCount = 0;
/*  977 */         Object[] frameLocal = null;
/*  978 */         Object[] frameStack = null;
/*  979 */         boolean zip = true;
/*  980 */         cattrs = null;
/*  981 */         j = readUnsignedShort(v);
/*  982 */         v += 2;
/*  983 */         for (; j > 0; j--) {
/*  984 */           String attrName = readUTF8(v, c);
/*  985 */           if ("LocalVariableTable".equals(attrName)) {
/*  986 */             if (!skipDebug) {
/*  987 */               varTable = v + 6;
/*  988 */               int k = readUnsignedShort(v + 6);
/*  989 */               w = v + 8;
/*  990 */               for (; k > 0; k--) {
/*  991 */                 int label = readUnsignedShort(w);
/*  992 */                 if (labels[label] == null) {
/*  993 */                   (readLabel(label, labels)).status |= 0x1;
/*      */                 }
/*  995 */                 label += readUnsignedShort(w + 2);
/*  996 */                 if (labels[label] == null) {
/*  997 */                   (readLabel(label, labels)).status |= 0x1;
/*      */                 }
/*  999 */                 w += 10;
/*      */               } 
/*      */             } 
/* 1002 */           } else if ("LocalVariableTypeTable".equals(attrName)) {
/* 1003 */             varTypeTable = v + 6;
/* 1004 */           } else if ("LineNumberTable".equals(attrName)) {
/* 1005 */             if (!skipDebug) {
/* 1006 */               int k = readUnsignedShort(v + 6);
/* 1007 */               w = v + 8;
/* 1008 */               for (; k > 0; k--) {
/* 1009 */                 int label = readUnsignedShort(w);
/* 1010 */                 if (labels[label] == null) {
/* 1011 */                   (readLabel(label, labels)).status |= 0x1;
/*      */                 }
/* 1013 */                 (labels[label]).line = readUnsignedShort(w + 2);
/* 1014 */                 w += 4;
/*      */               } 
/*      */             } 
/* 1017 */           } else if ("StackMapTable".equals(attrName)) {
/* 1018 */             if ((flags & 0x4) == 0) {
/* 1019 */               stackMap = v + 8;
/* 1020 */               frameCount = readUnsignedShort(v + 6);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*      */             }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*      */           }
/* 1038 */           else if ("StackMap".equals(attrName)) {
/* 1039 */             if ((flags & 0x4) == 0) {
/* 1040 */               stackMap = v + 8;
/* 1041 */               frameCount = readUnsignedShort(v + 6);
/* 1042 */               zip = false;
/*      */             
/*      */             }
/*      */ 
/*      */           
/*      */           }
/*      */           else {
/*      */             
/* 1050 */             for (int k = 0; k < attrs.length; k++) {
/* 1051 */               if ((attrs[k]).type.equals(attrName)) {
/* 1052 */                 Attribute attr = attrs[k].read(this, v + 6, readInt(v + 2), c, codeStart - 8, labels);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                 
/* 1058 */                 if (attr != null) {
/* 1059 */                   attr.next = cattrs;
/* 1060 */                   cattrs = attr;
/*      */                 } 
/*      */               } 
/*      */             } 
/*      */           } 
/* 1065 */           v += 6 + readInt(v + 2);
/*      */         } 
/*      */ 
/*      */         
/* 1069 */         if (stackMap != 0) {
/*      */ 
/*      */           
/* 1072 */           frameLocal = new Object[maxLocals];
/* 1073 */           frameStack = new Object[maxStack];
/* 1074 */           if (unzip) {
/* 1075 */             int local = 0;
/* 1076 */             if ((access & 0x8) == 0) {
/* 1077 */               if ("<init>".equals(name)) {
/* 1078 */                 frameLocal[local++] = Opcodes.UNINITIALIZED_THIS;
/*      */               } else {
/* 1080 */                 frameLocal[local++] = readClass(this.header + 2, c);
/*      */               } 
/*      */             }
/* 1083 */             j = 1;
/*      */             while (true) {
/* 1085 */               int k = j;
/* 1086 */               switch (desc.charAt(j++))
/*      */               { case 'B':
/*      */                 case 'C':
/*      */                 case 'I':
/*      */                 case 'S':
/*      */                 case 'Z':
/* 1092 */                   frameLocal[local++] = Opcodes.INTEGER;
/*      */                   continue;
/*      */                 case 'F':
/* 1095 */                   frameLocal[local++] = Opcodes.FLOAT;
/*      */                   continue;
/*      */                 case 'J':
/* 1098 */                   frameLocal[local++] = Opcodes.LONG;
/*      */                   continue;
/*      */                 case 'D':
/* 1101 */                   frameLocal[local++] = Opcodes.DOUBLE;
/*      */                   continue;
/*      */                 case '[':
/* 1104 */                   while (desc.charAt(j) == '[') {
/* 1105 */                     j++;
/*      */                   }
/* 1107 */                   if (desc.charAt(j) == 'L') {
/* 1108 */                     j++;
/* 1109 */                     while (desc.charAt(j) != ';') {
/* 1110 */                       j++;
/*      */                     }
/*      */                   } 
/* 1113 */                   frameLocal[local++] = desc.substring(k, ++j); continue;
/*      */                 case 'L': break;
/*      */                 default:
/* 1116 */                   break; }  while (desc.charAt(j) != ';') {
/* 1117 */                 j++;
/*      */               }
/* 1119 */               frameLocal[local++] = desc.substring(k + 1, j++);
/*      */             } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 1126 */             frameLocalCount = local;
/*      */           } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 1134 */           frameOffset = -1;
/*      */         } 
/* 1136 */         v = codeStart;
/*      */         
/* 1138 */         while (v < codeEnd) {
/* 1139 */           String idesc, iname, iowner; int cpIndex; Label[] values; int[] keys; Label[] table; int max, min, label; w = v - codeStart;
/*      */           
/* 1141 */           Label l = labels[w];
/* 1142 */           if (l != null) {
/* 1143 */             mv.visitLabel(l);
/* 1144 */             if (!skipDebug && l.line > 0) {
/* 1145 */               mv.visitLineNumber(l.line, l);
/*      */             }
/*      */           } 
/*      */ 
/*      */           
/* 1150 */           while (frameLocal != null && (frameOffset == w || frameOffset == -1)) {
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 1155 */             if (!zip || unzip) {
/* 1156 */               mv.visitFrame(-1, frameLocalCount, frameLocal, frameStackCount, frameStack);
/*      */ 
/*      */ 
/*      */             
/*      */             }
/* 1161 */             else if (frameOffset != -1) {
/* 1162 */               mv.visitFrame(frameMode, frameLocalDiff, frameLocal, frameStackCount, frameStack);
/*      */             } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 1169 */             if (frameCount > 0) {
/*      */               int delta, tag;
/* 1171 */               if (zip) {
/* 1172 */                 tag = b[stackMap++] & 0xFF;
/*      */               } else {
/* 1174 */                 tag = 255;
/* 1175 */                 frameOffset = -1;
/*      */               } 
/* 1177 */               frameLocalDiff = 0;
/* 1178 */               if (tag < 64) {
/*      */                 
/* 1180 */                 delta = tag;
/* 1181 */                 frameMode = 3;
/* 1182 */                 frameStackCount = 0;
/* 1183 */               } else if (tag < 128) {
/* 1184 */                 delta = tag - 64;
/*      */                 
/* 1186 */                 stackMap = readFrameType(frameStack, 0, stackMap, c, labels);
/*      */ 
/*      */ 
/*      */ 
/*      */                 
/* 1191 */                 frameMode = 4;
/* 1192 */                 frameStackCount = 1;
/*      */               } else {
/* 1194 */                 delta = readUnsignedShort(stackMap);
/* 1195 */                 stackMap += 2;
/* 1196 */                 if (tag == 247) {
/*      */                   
/* 1198 */                   stackMap = readFrameType(frameStack, 0, stackMap, c, labels);
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/* 1203 */                   frameMode = 4;
/* 1204 */                   frameStackCount = 1;
/* 1205 */                 } else if (tag >= 248 && tag < 251) {
/*      */ 
/*      */                   
/* 1208 */                   frameMode = 2;
/* 1209 */                   frameLocalDiff = 251 - tag;
/*      */                   
/* 1211 */                   frameLocalCount -= frameLocalDiff;
/* 1212 */                   frameStackCount = 0;
/* 1213 */                 } else if (tag == 251) {
/*      */                   
/* 1215 */                   frameMode = 3;
/* 1216 */                   frameStackCount = 0;
/* 1217 */                 } else if (tag < 255) {
/* 1218 */                   j = unzip ? frameLocalCount : 0;
/* 1219 */                   int k = tag - 251;
/* 1220 */                   for (; k > 0; k--)
/*      */                   {
/* 1222 */                     stackMap = readFrameType(frameLocal, j++, stackMap, c, labels);
/*      */                   }
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/* 1228 */                   frameMode = 1;
/* 1229 */                   frameLocalDiff = tag - 251;
/*      */                   
/* 1231 */                   frameLocalCount += frameLocalDiff;
/* 1232 */                   frameStackCount = 0;
/*      */                 } else {
/* 1234 */                   frameMode = 0;
/* 1235 */                   int n = frameLocalDiff = frameLocalCount = readUnsignedShort(stackMap);
/* 1236 */                   stackMap += 2;
/* 1237 */                   for (j = 0; n > 0; n--) {
/* 1238 */                     stackMap = readFrameType(frameLocal, j++, stackMap, c, labels);
/*      */                   }
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/* 1244 */                   n = frameStackCount = readUnsignedShort(stackMap);
/* 1245 */                   stackMap += 2;
/* 1246 */                   for (j = 0; n > 0; n--) {
/* 1247 */                     stackMap = readFrameType(frameStack, j++, stackMap, c, labels);
/*      */                   }
/*      */                 } 
/*      */               } 
/*      */ 
/*      */ 
/*      */ 
/*      */               
/* 1255 */               frameOffset += delta + 1;
/* 1256 */               readLabel(frameOffset, labels);
/*      */               
/* 1258 */               frameCount--; continue;
/*      */             } 
/* 1260 */             frameLocal = null;
/*      */           } 
/*      */ 
/*      */           
/* 1264 */           int opcode = b[v] & 0xFF;
/* 1265 */           switch (ClassWriter.TYPE[opcode]) {
/*      */             case 0:
/* 1267 */               mv.visitInsn(opcode);
/* 1268 */               v++;
/*      */               continue;
/*      */             case 4:
/* 1271 */               if (opcode > 54) {
/* 1272 */                 opcode -= 59;
/* 1273 */                 mv.visitVarInsn(54 + (opcode >> 2), opcode & 0x3);
/*      */               } else {
/*      */                 
/* 1276 */                 opcode -= 26;
/* 1277 */                 mv.visitVarInsn(21 + (opcode >> 2), opcode & 0x3);
/*      */               } 
/*      */               
/* 1280 */               v++;
/*      */               continue;
/*      */             case 8:
/* 1283 */               mv.visitJumpInsn(opcode, labels[w + readShort(v + 1)]);
/*      */               
/* 1285 */               v += 3;
/*      */               continue;
/*      */             case 9:
/* 1288 */               mv.visitJumpInsn(opcode - 33, labels[w + readInt(v + 1)]);
/*      */               
/* 1290 */               v += 5;
/*      */               continue;
/*      */             case 16:
/* 1293 */               opcode = b[v + 1] & 0xFF;
/* 1294 */               if (opcode == 132) {
/* 1295 */                 mv.visitIincInsn(readUnsignedShort(v + 2), readShort(v + 4));
/*      */                 
/* 1297 */                 v += 6; continue;
/*      */               } 
/* 1299 */               mv.visitVarInsn(opcode, readUnsignedShort(v + 2));
/*      */               
/* 1301 */               v += 4;
/*      */               continue;
/*      */ 
/*      */             
/*      */             case 13:
/* 1306 */               v = v + 4 - (w & 0x3);
/*      */               
/* 1308 */               label = w + readInt(v);
/* 1309 */               min = readInt(v + 4);
/* 1310 */               max = readInt(v + 8);
/* 1311 */               v += 12;
/* 1312 */               table = new Label[max - min + 1];
/* 1313 */               for (j = 0; j < table.length; j++) {
/* 1314 */                 table[j] = labels[w + readInt(v)];
/* 1315 */                 v += 4;
/*      */               } 
/* 1317 */               mv.visitTableSwitchInsn(min, max, labels[label], table);
/*      */               continue;
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*      */             case 14:
/* 1324 */               v = v + 4 - (w & 0x3);
/*      */               
/* 1326 */               label = w + readInt(v);
/* 1327 */               j = readInt(v + 4);
/* 1328 */               v += 8;
/* 1329 */               keys = new int[j];
/* 1330 */               values = new Label[j];
/* 1331 */               for (j = 0; j < keys.length; j++) {
/* 1332 */                 keys[j] = readInt(v);
/* 1333 */                 values[j] = labels[w + readInt(v + 4)];
/* 1334 */                 v += 8;
/*      */               } 
/* 1336 */               mv.visitLookupSwitchInsn(labels[label], keys, values);
/*      */               continue;
/*      */ 
/*      */             
/*      */             case 3:
/* 1341 */               mv.visitVarInsn(opcode, b[v + 1] & 0xFF);
/* 1342 */               v += 2;
/*      */               continue;
/*      */             case 1:
/* 1345 */               mv.visitIntInsn(opcode, b[v + 1]);
/* 1346 */               v += 2;
/*      */               continue;
/*      */             case 2:
/* 1349 */               mv.visitIntInsn(opcode, readShort(v + 1));
/* 1350 */               v += 3;
/*      */               continue;
/*      */             case 10:
/* 1353 */               mv.visitLdcInsn(readConst(b[v + 1] & 0xFF, c));
/* 1354 */               v += 2;
/*      */               continue;
/*      */             case 11:
/* 1357 */               mv.visitLdcInsn(readConst(readUnsignedShort(v + 1), c));
/*      */               
/* 1359 */               v += 3;
/*      */               continue;
/*      */             case 6:
/*      */             case 7:
/* 1363 */               cpIndex = this.items[readUnsignedShort(v + 1)];
/* 1364 */               iowner = readClass(cpIndex, c);
/* 1365 */               cpIndex = this.items[readUnsignedShort(cpIndex + 2)];
/* 1366 */               iname = readUTF8(cpIndex, c);
/* 1367 */               idesc = readUTF8(cpIndex + 2, c);
/* 1368 */               if (opcode < 182) {
/* 1369 */                 mv.visitFieldInsn(opcode, iowner, iname, idesc);
/*      */               } else {
/* 1371 */                 mv.visitMethodInsn(opcode, iowner, iname, idesc);
/*      */               } 
/* 1373 */               if (opcode == 185) {
/* 1374 */                 v += 5; continue;
/*      */               } 
/* 1376 */               v += 3;
/*      */               continue;
/*      */             
/*      */             case 5:
/* 1380 */               mv.visitTypeInsn(opcode, readClass(v + 1, c));
/* 1381 */               v += 3;
/*      */               continue;
/*      */             case 12:
/* 1384 */               mv.visitIincInsn(b[v + 1] & 0xFF, b[v + 2]);
/* 1385 */               v += 3;
/*      */               continue;
/*      */           } 
/*      */           
/* 1389 */           mv.visitMultiANewArrayInsn(readClass(v + 1, c), b[v + 3] & 0xFF);
/*      */           
/* 1391 */           v += 4;
/*      */         } 
/*      */ 
/*      */         
/* 1395 */         Label l = labels[codeEnd - codeStart];
/* 1396 */         if (l != null) {
/* 1397 */           mv.visitLabel(l);
/*      */         }
/*      */         
/* 1400 */         if (!skipDebug && varTable != 0) {
/* 1401 */           int[] typeTable = null;
/* 1402 */           if (varTypeTable != 0) {
/* 1403 */             int k = readUnsignedShort(varTypeTable) * 3;
/* 1404 */             w = varTypeTable + 2;
/* 1405 */             typeTable = new int[k];
/* 1406 */             while (k > 0) {
/* 1407 */               typeTable[--k] = w + 6;
/* 1408 */               typeTable[--k] = readUnsignedShort(w + 8);
/* 1409 */               typeTable[--k] = readUnsignedShort(w);
/* 1410 */               w += 10;
/*      */             } 
/*      */           } 
/* 1413 */           int k = readUnsignedShort(varTable);
/* 1414 */           w = varTable + 2;
/* 1415 */           for (; k > 0; k--) {
/* 1416 */             int start = readUnsignedShort(w);
/* 1417 */             int length = readUnsignedShort(w + 2);
/* 1418 */             int index = readUnsignedShort(w + 8);
/* 1419 */             String vsignature = null;
/* 1420 */             if (typeTable != null) {
/* 1421 */               for (int a = 0; a < typeTable.length; a += 3) {
/* 1422 */                 if (typeTable[a] == start && typeTable[a + 1] == index) {
/*      */ 
/*      */                   
/* 1425 */                   vsignature = readUTF8(typeTable[a + 2], c);
/*      */                   break;
/*      */                 } 
/*      */               } 
/*      */             }
/* 1430 */             mv.visitLocalVariable(readUTF8(w + 4, c), readUTF8(w + 6, c), vsignature, labels[start], labels[start + length], index);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 1436 */             w += 10;
/*      */           } 
/*      */         } 
/*      */         
/* 1440 */         while (cattrs != null) {
/* 1441 */           Attribute attr = cattrs.next;
/* 1442 */           cattrs.next = null;
/* 1443 */           mv.visitAttribute(cattrs);
/* 1444 */           cattrs = attr;
/*      */         } 
/*      */         
/* 1447 */         mv.visitMaxs(maxStack, maxLocals);
/*      */       } 
/*      */       
/* 1450 */       if (mv != null) {
/* 1451 */         mv.visitEnd();
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/* 1456 */     classVisitor.visitEnd();
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
/*      */   private void readParameterAnnotations(int v, String desc, char[] buf, boolean visible, MethodVisitor mv) {
/* 1479 */     int n = this.b[v++] & 0xFF;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1486 */     int synthetics = Type.getArgumentTypes(desc).length - n;
/*      */     int i;
/* 1488 */     for (i = 0; i < synthetics; i++) {
/*      */       
/* 1490 */       AnnotationVisitor av = mv.visitParameterAnnotation(i, "Ljava/lang/Synthetic;", false);
/* 1491 */       if (av != null) {
/* 1492 */         av.visitEnd();
/*      */       }
/*      */     } 
/* 1495 */     for (; i < n + synthetics; i++) {
/* 1496 */       int j = readUnsignedShort(v);
/* 1497 */       v += 2;
/* 1498 */       for (; j > 0; j--) {
/* 1499 */         AnnotationVisitor av = mv.visitParameterAnnotation(i, readUTF8(v, buf), visible);
/* 1500 */         v = readAnnotationValues(v + 2, buf, true, av);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int readAnnotationValues(int v, char[] buf, boolean named, AnnotationVisitor av) {
/* 1523 */     int i = readUnsignedShort(v);
/* 1524 */     v += 2;
/* 1525 */     if (named) {
/* 1526 */       for (; i > 0; i--) {
/* 1527 */         v = readAnnotationValue(v + 2, buf, readUTF8(v, buf), av);
/*      */       }
/*      */     } else {
/* 1530 */       for (; i > 0; i--) {
/* 1531 */         v = readAnnotationValue(v, buf, null, av);
/*      */       }
/*      */     } 
/* 1534 */     if (av != null) {
/* 1535 */       av.visitEnd();
/*      */     }
/* 1537 */     return v;
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
/*      */   private int readAnnotationValue(int v, char[] buf, String name, AnnotationVisitor av) {
/*      */     double[] dv;
/*      */     float[] fv;
/*      */     long[] lv;
/*      */     int[] iv;
/*      */     char[] cv;
/*      */     short[] sv;
/*      */     boolean[] zv;
/*      */     byte[] bv;
/*      */     int size;
/*      */     int i;
/* 1559 */     if (av == null) {
/* 1560 */       switch (this.b[v] & 0xFF) {
/*      */         case 101:
/* 1562 */           return v + 5;
/*      */         case 64:
/* 1564 */           return readAnnotationValues(v + 3, buf, true, null);
/*      */         case 91:
/* 1566 */           return readAnnotationValues(v + 1, buf, false, null);
/*      */       } 
/* 1568 */       return v + 3;
/*      */     } 
/*      */     
/* 1571 */     switch (this.b[v++] & 0xFF) {
/*      */       case 68:
/*      */       case 70:
/*      */       case 73:
/*      */       case 74:
/* 1576 */         av.visit(name, readConst(readUnsignedShort(v), buf));
/* 1577 */         v += 2;
/*      */         break;
/*      */       case 66:
/* 1580 */         av.visit(name, Byte.valueOf((byte)readInt(this.items[readUnsignedShort(v)])));
/*      */         
/* 1582 */         v += 2;
/*      */         break;
/*      */       case 90:
/* 1585 */         av.visit(name, (readInt(this.items[readUnsignedShort(v)]) == 0) ? Boolean.FALSE : Boolean.TRUE);
/*      */ 
/*      */         
/* 1588 */         v += 2;
/*      */         break;
/*      */       case 83:
/* 1591 */         av.visit(name, Short.valueOf((short)readInt(this.items[readUnsignedShort(v)])));
/*      */         
/* 1593 */         v += 2;
/*      */         break;
/*      */       case 67:
/* 1596 */         av.visit(name, Character.valueOf((char)readInt(this.items[readUnsignedShort(v)])));
/*      */         
/* 1598 */         v += 2;
/*      */         break;
/*      */       case 115:
/* 1601 */         av.visit(name, readUTF8(v, buf));
/* 1602 */         v += 2;
/*      */         break;
/*      */       case 101:
/* 1605 */         av.visitEnum(name, readUTF8(v, buf), readUTF8(v + 2, buf));
/* 1606 */         v += 4;
/*      */         break;
/*      */       case 99:
/* 1609 */         av.visit(name, Type.getType(readUTF8(v, buf)));
/* 1610 */         v += 2;
/*      */         break;
/*      */       case 64:
/* 1613 */         v = readAnnotationValues(v + 2, buf, true, av.visitAnnotation(name, readUTF8(v, buf)));
/*      */         break;
/*      */ 
/*      */ 
/*      */       
/*      */       case 91:
/* 1619 */         size = readUnsignedShort(v);
/* 1620 */         v += 2;
/* 1621 */         if (size == 0) {
/* 1622 */           return readAnnotationValues(v - 2, buf, false, av.visitArray(name));
/*      */         }
/*      */ 
/*      */ 
/*      */         
/* 1627 */         switch (this.b[v++] & 0xFF) {
/*      */           case 66:
/* 1629 */             bv = new byte[size];
/* 1630 */             for (i = 0; i < size; i++) {
/* 1631 */               bv[i] = (byte)readInt(this.items[readUnsignedShort(v)]);
/* 1632 */               v += 3;
/*      */             } 
/* 1634 */             av.visit(name, bv);
/* 1635 */             v--;
/*      */             break;
/*      */           case 90:
/* 1638 */             zv = new boolean[size];
/* 1639 */             for (i = 0; i < size; i++) {
/* 1640 */               zv[i] = (readInt(this.items[readUnsignedShort(v)]) != 0);
/* 1641 */               v += 3;
/*      */             } 
/* 1643 */             av.visit(name, zv);
/* 1644 */             v--;
/*      */             break;
/*      */           case 83:
/* 1647 */             sv = new short[size];
/* 1648 */             for (i = 0; i < size; i++) {
/* 1649 */               sv[i] = (short)readInt(this.items[readUnsignedShort(v)]);
/* 1650 */               v += 3;
/*      */             } 
/* 1652 */             av.visit(name, sv);
/* 1653 */             v--;
/*      */             break;
/*      */           case 67:
/* 1656 */             cv = new char[size];
/* 1657 */             for (i = 0; i < size; i++) {
/* 1658 */               cv[i] = (char)readInt(this.items[readUnsignedShort(v)]);
/* 1659 */               v += 3;
/*      */             } 
/* 1661 */             av.visit(name, cv);
/* 1662 */             v--;
/*      */             break;
/*      */           case 73:
/* 1665 */             iv = new int[size];
/* 1666 */             for (i = 0; i < size; i++) {
/* 1667 */               iv[i] = readInt(this.items[readUnsignedShort(v)]);
/* 1668 */               v += 3;
/*      */             } 
/* 1670 */             av.visit(name, iv);
/* 1671 */             v--;
/*      */             break;
/*      */           case 74:
/* 1674 */             lv = new long[size];
/* 1675 */             for (i = 0; i < size; i++) {
/* 1676 */               lv[i] = readLong(this.items[readUnsignedShort(v)]);
/* 1677 */               v += 3;
/*      */             } 
/* 1679 */             av.visit(name, lv);
/* 1680 */             v--;
/*      */             break;
/*      */           case 70:
/* 1683 */             fv = new float[size];
/* 1684 */             for (i = 0; i < size; i++) {
/* 1685 */               fv[i] = Float.intBitsToFloat(readInt(this.items[readUnsignedShort(v)]));
/* 1686 */               v += 3;
/*      */             } 
/* 1688 */             av.visit(name, fv);
/* 1689 */             v--;
/*      */             break;
/*      */           case 68:
/* 1692 */             dv = new double[size];
/* 1693 */             for (i = 0; i < size; i++) {
/* 1694 */               dv[i] = Double.longBitsToDouble(readLong(this.items[readUnsignedShort(v)]));
/* 1695 */               v += 3;
/*      */             } 
/* 1697 */             av.visit(name, dv);
/* 1698 */             v--;
/*      */             break;
/*      */         } 
/* 1701 */         v = readAnnotationValues(v - 3, buf, false, av.visitArray(name));
/*      */         break;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1707 */     return v;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int readFrameType(Object[] frame, int index, int v, char[] buf, Label[] labels) {
/* 1717 */     int type = this.b[v++] & 0xFF;
/* 1718 */     switch (type) {
/*      */       case 0:
/* 1720 */         frame[index] = Opcodes.TOP;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1748 */         return v;case 1: frame[index] = Opcodes.INTEGER; return v;case 2: frame[index] = Opcodes.FLOAT; return v;case 3: frame[index] = Opcodes.DOUBLE; return v;case 4: frame[index] = Opcodes.LONG; return v;case 5: frame[index] = Opcodes.NULL; return v;case 6: frame[index] = Opcodes.UNINITIALIZED_THIS; return v;
/*      */       case 7:
/*      */         frame[index] = readClass(v, buf);
/*      */         return 2;
/*      */     } 
/*      */     frame[index] = readLabel(readUnsignedShort(v), labels);
/*      */     return 2;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Label readLabel(int offset, Label[] labels) {
/* 1763 */     if (labels[offset] == null) {
/* 1764 */       labels[offset] = new Label();
/*      */     }
/* 1766 */     return labels[offset];
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
/*      */   private Attribute readAttribute(Attribute[] attrs, String type, int off, int len, char[] buf, int codeOff, Label[] labels) {
/* 1803 */     for (int i = 0; i < attrs.length; i++) {
/* 1804 */       if ((attrs[i]).type.equals(type)) {
/* 1805 */         return attrs[i].read(this, off, len, buf, codeOff, labels);
/*      */       }
/*      */     } 
/* 1808 */     return (new Attribute(type)).read(this, off, len, null, -1, null);
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
/* 1825 */   public int getItem(int item) { return this.items[item]; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1837 */   public int readByte(int index) { return this.b[index] & 0xFF; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int readUnsignedShort(int index) {
/* 1849 */     byte[] b = this.b;
/* 1850 */     return (b[index] & 0xFF) << 8 | b[index + 1] & 0xFF;
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
/*      */   public short readShort(int index) {
/* 1862 */     byte[] b = this.b;
/* 1863 */     return (short)((b[index] & 0xFF) << 8 | b[index + 1] & 0xFF);
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
/*      */   public int readInt(int index) {
/* 1875 */     byte[] b = this.b;
/* 1876 */     return (b[index] & 0xFF) << 24 | (b[index + 1] & 0xFF) << 16 | (b[index + 2] & 0xFF) << 8 | b[index + 3] & 0xFF;
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
/*      */   public long readLong(int index) {
/* 1889 */     long l1 = readInt(index);
/* 1890 */     long l0 = readInt(index + 4) & 0xFFFFFFFFL;
/* 1891 */     return l1 << 32 | l0;
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
/*      */   public String readUTF8(int index, char[] buf) {
/* 1906 */     int item = readUnsignedShort(index);
/* 1907 */     String s = this.strings[item];
/* 1908 */     if (s != null) {
/* 1909 */       return s;
/*      */     }
/* 1911 */     index = this.items[item];
/* 1912 */     this.strings[item] = readUTF(index + 2, readUnsignedShort(index), buf); return readUTF(index + 2, readUnsignedShort(index), buf);
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
/*      */   private String readUTF(int index, int utfLen, char[] buf) {
/* 1925 */     int endIndex = index + utfLen;
/* 1926 */     byte[] b = this.b;
/* 1927 */     int strLen = 0;
/*      */     
/* 1929 */     while (index < endIndex) {
/* 1930 */       int c = b[index++] & 0xFF;
/* 1931 */       switch (c >> 4) {
/*      */         
/*      */         case 0:
/*      */         case 1:
/*      */         case 2:
/*      */         case 3:
/*      */         case 4:
/*      */         case 5:
/*      */         case 6:
/*      */         case 7:
/* 1941 */           buf[strLen++] = (char)c;
/*      */           continue;
/*      */         
/*      */         case 12:
/*      */         case 13:
/* 1946 */           d = b[index++];
/* 1947 */           buf[strLen++] = (char)((c & 0x1F) << 6 | d & 0x3F);
/*      */           continue;
/*      */       } 
/*      */       
/* 1951 */       int d = b[index++];
/* 1952 */       int e = b[index++];
/* 1953 */       buf[strLen++] = (char)((c & 0xF) << 12 | (d & 0x3F) << 6 | e & 0x3F);
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1958 */     return new String(buf, false, strLen);
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
/* 1976 */   public String readClass(int index, char[] buf) { return readUTF8(this.items[readUnsignedShort(index)], buf); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object readConst(int item, char[] buf) {
/* 1992 */     int index = this.items[item];
/* 1993 */     switch (this.b[index - 1]) {
/*      */       case 3:
/* 1995 */         return Integer.valueOf(readInt(index));
/*      */       case 4:
/* 1997 */         return new Float(Float.intBitsToFloat(readInt(index)));
/*      */       case 5:
/* 1999 */         return Long.valueOf(readLong(index));
/*      */       case 6:
/* 2001 */         return new Double(Double.longBitsToDouble(readLong(index)));
/*      */       case 7:
/* 2003 */         return Type.getObjectType(readUTF8(index, buf));
/*      */     } 
/*      */     
/* 2006 */     return readUTF8(index, buf);
/*      */   }
/*      */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebean\enhance\asm\ClassReader.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */