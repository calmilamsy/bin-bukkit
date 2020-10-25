/*     */ package com.avaje.ebean.enhance.asm;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class AnnotationWriter
/*     */   implements AnnotationVisitor
/*     */ {
/*     */   private final ClassWriter cw;
/*     */   private int size;
/*     */   private final boolean named;
/*     */   private final ByteVector bv;
/*     */   private final ByteVector parent;
/*     */   private final int offset;
/*     */   AnnotationWriter next;
/*     */   AnnotationWriter prev;
/*     */   
/*     */   AnnotationWriter(ClassWriter cw, boolean named, ByteVector bv, ByteVector parent, int offset) {
/* 107 */     this.cw = cw;
/* 108 */     this.named = named;
/* 109 */     this.bv = bv;
/* 110 */     this.parent = parent;
/* 111 */     this.offset = offset;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void visit(String name, Object value) {
/* 119 */     this.size++;
/* 120 */     if (this.named) {
/* 121 */       this.bv.putShort(this.cw.newUTF8(name));
/*     */     }
/* 123 */     if (value instanceof String) {
/* 124 */       this.bv.put12(115, this.cw.newUTF8((String)value));
/* 125 */     } else if (value instanceof Byte) {
/* 126 */       this.bv.put12(66, (this.cw.newInteger(((Byte)value).byteValue())).index);
/* 127 */     } else if (value instanceof Boolean) {
/* 128 */       int v = ((Boolean)value).booleanValue() ? 1 : 0;
/* 129 */       this.bv.put12(90, (this.cw.newInteger(v)).index);
/* 130 */     } else if (value instanceof Character) {
/* 131 */       this.bv.put12(67, (this.cw.newInteger(((Character)value).charValue())).index);
/* 132 */     } else if (value instanceof Short) {
/* 133 */       this.bv.put12(83, (this.cw.newInteger(((Short)value).shortValue())).index);
/* 134 */     } else if (value instanceof Type) {
/* 135 */       this.bv.put12(99, this.cw.newUTF8(((Type)value).getDescriptor()));
/* 136 */     } else if (value instanceof byte[]) {
/* 137 */       byte[] v = (byte[])value;
/* 138 */       this.bv.put12(91, v.length);
/* 139 */       for (int i = 0; i < v.length; i++) {
/* 140 */         this.bv.put12(66, (this.cw.newInteger(v[i])).index);
/*     */       }
/* 142 */     } else if (value instanceof boolean[]) {
/* 143 */       boolean[] v = (boolean[])value;
/* 144 */       this.bv.put12(91, v.length);
/* 145 */       for (int i = 0; i < v.length; i++) {
/* 146 */         this.bv.put12(90, (this.cw.newInteger(v[i] ? 1 : 0)).index);
/*     */       }
/* 148 */     } else if (value instanceof short[]) {
/* 149 */       short[] v = (short[])value;
/* 150 */       this.bv.put12(91, v.length);
/* 151 */       for (int i = 0; i < v.length; i++) {
/* 152 */         this.bv.put12(83, (this.cw.newInteger(v[i])).index);
/*     */       }
/* 154 */     } else if (value instanceof char[]) {
/* 155 */       char[] v = (char[])value;
/* 156 */       this.bv.put12(91, v.length);
/* 157 */       for (int i = 0; i < v.length; i++) {
/* 158 */         this.bv.put12(67, (this.cw.newInteger(v[i])).index);
/*     */       }
/* 160 */     } else if (value instanceof int[]) {
/* 161 */       int[] v = (int[])value;
/* 162 */       this.bv.put12(91, v.length);
/* 163 */       for (int i = 0; i < v.length; i++) {
/* 164 */         this.bv.put12(73, (this.cw.newInteger(v[i])).index);
/*     */       }
/* 166 */     } else if (value instanceof long[]) {
/* 167 */       long[] v = (long[])value;
/* 168 */       this.bv.put12(91, v.length);
/* 169 */       for (int i = 0; i < v.length; i++) {
/* 170 */         this.bv.put12(74, (this.cw.newLong(v[i])).index);
/*     */       }
/* 172 */     } else if (value instanceof float[]) {
/* 173 */       float[] v = (float[])value;
/* 174 */       this.bv.put12(91, v.length);
/* 175 */       for (int i = 0; i < v.length; i++) {
/* 176 */         this.bv.put12(70, (this.cw.newFloat(v[i])).index);
/*     */       }
/* 178 */     } else if (value instanceof double[]) {
/* 179 */       double[] v = (double[])value;
/* 180 */       this.bv.put12(91, v.length);
/* 181 */       for (int i = 0; i < v.length; i++) {
/* 182 */         this.bv.put12(68, (this.cw.newDouble(v[i])).index);
/*     */       }
/*     */     } else {
/* 185 */       Item i = this.cw.newConstItem(value);
/* 186 */       this.bv.put12(".s.IFJDCS".charAt(i.type), i.index);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void visitEnum(String name, String desc, String value) {
/* 195 */     this.size++;
/* 196 */     if (this.named) {
/* 197 */       this.bv.putShort(this.cw.newUTF8(name));
/*     */     }
/* 199 */     this.bv.put12(101, this.cw.newUTF8(desc)).putShort(this.cw.newUTF8(value));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AnnotationVisitor visitAnnotation(String name, String desc) {
/* 206 */     this.size++;
/* 207 */     if (this.named) {
/* 208 */       this.bv.putShort(this.cw.newUTF8(name));
/*     */     }
/*     */     
/* 211 */     this.bv.put12(64, this.cw.newUTF8(desc)).putShort(0);
/* 212 */     return new AnnotationWriter(this.cw, true, this.bv, this.bv, this.bv.length - 2);
/*     */   }
/*     */   
/*     */   public AnnotationVisitor visitArray(String name) {
/* 216 */     this.size++;
/* 217 */     if (this.named) {
/* 218 */       this.bv.putShort(this.cw.newUTF8(name));
/*     */     }
/*     */     
/* 221 */     this.bv.put12(91, 0);
/* 222 */     return new AnnotationWriter(this.cw, false, this.bv, this.bv, this.bv.length - 2);
/*     */   }
/*     */   
/*     */   public void visitEnd() {
/* 226 */     if (this.parent != null) {
/* 227 */       byte[] data = this.parent.data;
/* 228 */       data[this.offset] = (byte)(this.size >>> 8);
/* 229 */       data[this.offset + 1] = (byte)this.size;
/*     */     } 
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
/*     */   int getSize() {
/* 243 */     int size = 0;
/* 244 */     AnnotationWriter aw = this;
/* 245 */     while (aw != null) {
/* 246 */       size += aw.bv.length;
/* 247 */       aw = aw.next;
/*     */     } 
/* 249 */     return size;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void put(ByteVector out) {
/* 259 */     int n = 0;
/* 260 */     int size = 2;
/* 261 */     AnnotationWriter aw = this;
/* 262 */     AnnotationWriter last = null;
/* 263 */     while (aw != null) {
/* 264 */       n++;
/* 265 */       size += aw.bv.length;
/* 266 */       aw.visitEnd();
/* 267 */       aw.prev = last;
/* 268 */       last = aw;
/* 269 */       aw = aw.next;
/*     */     } 
/* 271 */     out.putInt(size);
/* 272 */     out.putShort(n);
/* 273 */     aw = last;
/* 274 */     while (aw != null) {
/* 275 */       out.putByteArray(aw.bv.data, 0, aw.bv.length);
/* 276 */       aw = aw.prev;
/*     */     } 
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
/*     */   static void put(AnnotationWriter[] panns, int off, ByteVector out) {
/* 292 */     int size = 1 + 2 * (panns.length - off);
/* 293 */     for (i = off; i < panns.length; i++) {
/* 294 */       size += ((panns[i] == null) ? 0 : panns[i].getSize());
/*     */     }
/* 296 */     out.putInt(size).putByte(panns.length - off);
/* 297 */     for (int i = off; i < panns.length; i++) {
/* 298 */       AnnotationWriter aw = panns[i];
/* 299 */       AnnotationWriter last = null;
/* 300 */       int n = 0;
/* 301 */       while (aw != null) {
/* 302 */         n++;
/* 303 */         aw.visitEnd();
/* 304 */         aw.prev = last;
/* 305 */         last = aw;
/* 306 */         aw = aw.next;
/*     */       } 
/* 308 */       out.putShort(n);
/* 309 */       aw = last;
/* 310 */       while (aw != null) {
/* 311 */         out.putByteArray(aw.bv.data, 0, aw.bv.length);
/* 312 */         aw = aw.prev;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebean\enhance\asm\AnnotationWriter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */