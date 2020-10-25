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
/*     */ public class Attribute
/*     */ {
/*     */   public final String type;
/*     */   byte[] value;
/*     */   Attribute next;
/*     */   
/*  61 */   protected Attribute(String type) { this.type = type; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  71 */   public boolean isUnknown() { return true; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  80 */   public boolean isCodeAttribute() { return false; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  90 */   protected Label[] getLabels() { return null; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Attribute read(ClassReader cr, int off, int len, char[] buf, int codeOff, Label[] labels) {
/* 127 */     Attribute attr = new Attribute(this.type);
/* 128 */     attr.value = new byte[len];
/* 129 */     System.arraycopy(cr.b, off, attr.value, 0, len);
/* 130 */     return attr;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected ByteVector write(ClassWriter cw, byte[] code, int len, int maxStack, int maxLocals) {
/* 160 */     ByteVector v = new ByteVector();
/* 161 */     v.data = this.value;
/* 162 */     v.length = this.value.length;
/* 163 */     return v;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   final int getCount() {
/* 172 */     int count = 0;
/* 173 */     Attribute attr = this;
/* 174 */     while (attr != null) {
/* 175 */       count++;
/* 176 */       attr = attr.next;
/*     */     } 
/* 178 */     return count;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   final int getSize(ClassWriter cw, byte[] code, int len, int maxStack, int maxLocals) {
/* 208 */     Attribute attr = this;
/* 209 */     int size = 0;
/* 210 */     while (attr != null) {
/* 211 */       cw.newUTF8(attr.type);
/* 212 */       size += (attr.write(cw, code, len, maxStack, maxLocals)).length + 6;
/* 213 */       attr = attr.next;
/*     */     } 
/* 215 */     return size;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   final void put(ClassWriter cw, byte[] code, int len, int maxStack, int maxLocals, ByteVector out) {
/* 246 */     Attribute attr = this;
/* 247 */     while (attr != null) {
/* 248 */       ByteVector b = attr.write(cw, code, len, maxStack, maxLocals);
/* 249 */       out.putShort(cw.newUTF8(attr.type)).putInt(b.length);
/* 250 */       out.putByteArray(b.data, 0, b.length);
/* 251 */       attr = attr.next;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebean\enhance\asm\Attribute.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */