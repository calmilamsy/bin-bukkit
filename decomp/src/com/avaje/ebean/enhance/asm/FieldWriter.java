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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class FieldWriter
/*     */   implements FieldVisitor
/*     */ {
/*     */   FieldWriter next;
/*     */   private final ClassWriter cw;
/*     */   private final int access;
/*     */   private final int name;
/*     */   private final int desc;
/*     */   private int signature;
/*     */   private int value;
/*     */   private AnnotationWriter anns;
/*     */   private AnnotationWriter ianns;
/*     */   private Attribute attrs;
/*     */   
/*     */   FieldWriter(ClassWriter cw, int access, String name, String desc, String signature, Object value) {
/* 115 */     if (cw.firstField == null) {
/* 116 */       cw.firstField = this;
/*     */     } else {
/* 118 */       cw.lastField.next = this;
/*     */     } 
/* 120 */     cw.lastField = this;
/* 121 */     this.cw = cw;
/* 122 */     this.access = access;
/* 123 */     this.name = cw.newUTF8(name);
/* 124 */     this.desc = cw.newUTF8(desc);
/* 125 */     if (signature != null) {
/* 126 */       this.signature = cw.newUTF8(signature);
/*     */     }
/* 128 */     if (value != null) {
/* 129 */       this.value = (cw.newConstItem(value)).index;
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
/*     */   public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
/* 144 */     ByteVector bv = new ByteVector();
/*     */     
/* 146 */     bv.putShort(this.cw.newUTF8(desc)).putShort(0);
/* 147 */     AnnotationWriter aw = new AnnotationWriter(this.cw, true, bv, bv, 2);
/* 148 */     if (visible) {
/* 149 */       aw.next = this.anns;
/* 150 */       this.anns = aw;
/*     */     } else {
/* 152 */       aw.next = this.ianns;
/* 153 */       this.ianns = aw;
/*     */     } 
/* 155 */     return aw;
/*     */   }
/*     */   
/*     */   public void visitAttribute(Attribute attr) {
/* 159 */     attr.next = this.attrs;
/* 160 */     this.attrs = attr;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void visitEnd() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   int getSize() {
/* 176 */     int size = 8;
/* 177 */     if (this.value != 0) {
/* 178 */       this.cw.newUTF8("ConstantValue");
/* 179 */       size += 8;
/*     */     } 
/* 181 */     if ((this.access & 0x1000) != 0 && (this.cw.version & 0xFFFF) < 49) {
/*     */ 
/*     */       
/* 184 */       this.cw.newUTF8("Synthetic");
/* 185 */       size += 6;
/*     */     } 
/* 187 */     if ((this.access & 0x20000) != 0) {
/* 188 */       this.cw.newUTF8("Deprecated");
/* 189 */       size += 6;
/*     */     } 
/* 191 */     if (this.signature != 0) {
/* 192 */       this.cw.newUTF8("Signature");
/* 193 */       size += 8;
/*     */     } 
/* 195 */     if (this.anns != null) {
/* 196 */       this.cw.newUTF8("RuntimeVisibleAnnotations");
/* 197 */       size += 8 + this.anns.getSize();
/*     */     } 
/* 199 */     if (this.ianns != null) {
/* 200 */       this.cw.newUTF8("RuntimeInvisibleAnnotations");
/* 201 */       size += 8 + this.ianns.getSize();
/*     */     } 
/* 203 */     if (this.attrs != null) {
/* 204 */       size += this.attrs.getSize(this.cw, null, 0, -1, -1);
/*     */     }
/* 206 */     return size;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void put(ByteVector out) {
/* 215 */     out.putShort(this.access).putShort(this.name).putShort(this.desc);
/* 216 */     int attributeCount = 0;
/* 217 */     if (this.value != 0) {
/* 218 */       attributeCount++;
/*     */     }
/* 220 */     if ((this.access & 0x1000) != 0 && (this.cw.version & 0xFFFF) < 49)
/*     */     {
/*     */       
/* 223 */       attributeCount++;
/*     */     }
/* 225 */     if ((this.access & 0x20000) != 0) {
/* 226 */       attributeCount++;
/*     */     }
/* 228 */     if (this.signature != 0) {
/* 229 */       attributeCount++;
/*     */     }
/* 231 */     if (this.anns != null) {
/* 232 */       attributeCount++;
/*     */     }
/* 234 */     if (this.ianns != null) {
/* 235 */       attributeCount++;
/*     */     }
/* 237 */     if (this.attrs != null) {
/* 238 */       attributeCount += this.attrs.getCount();
/*     */     }
/* 240 */     out.putShort(attributeCount);
/* 241 */     if (this.value != 0) {
/* 242 */       out.putShort(this.cw.newUTF8("ConstantValue"));
/* 243 */       out.putInt(2).putShort(this.value);
/*     */     } 
/* 245 */     if ((this.access & 0x1000) != 0 && (this.cw.version & 0xFFFF) < 49)
/*     */     {
/*     */       
/* 248 */       out.putShort(this.cw.newUTF8("Synthetic")).putInt(0);
/*     */     }
/* 250 */     if ((this.access & 0x20000) != 0) {
/* 251 */       out.putShort(this.cw.newUTF8("Deprecated")).putInt(0);
/*     */     }
/* 253 */     if (this.signature != 0) {
/* 254 */       out.putShort(this.cw.newUTF8("Signature"));
/* 255 */       out.putInt(2).putShort(this.signature);
/*     */     } 
/* 257 */     if (this.anns != null) {
/* 258 */       out.putShort(this.cw.newUTF8("RuntimeVisibleAnnotations"));
/* 259 */       this.anns.put(out);
/*     */     } 
/* 261 */     if (this.ianns != null) {
/* 262 */       out.putShort(this.cw.newUTF8("RuntimeInvisibleAnnotations"));
/* 263 */       this.ianns.put(out);
/*     */     } 
/* 265 */     if (this.attrs != null)
/* 266 */       this.attrs.put(this.cw, null, 0, -1, -1, out); 
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebean\enhance\asm\FieldWriter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */