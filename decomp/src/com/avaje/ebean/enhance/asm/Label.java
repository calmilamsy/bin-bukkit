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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Label
/*     */ {
/*     */   static final int DEBUG = 1;
/*     */   static final int RESOLVED = 2;
/*     */   static final int RESIZED = 4;
/*     */   static final int PUSHED = 8;
/*     */   static final int TARGET = 16;
/*     */   static final int STORE = 32;
/*     */   static final int REACHABLE = 64;
/*     */   static final int JSR = 128;
/*     */   static final int RET = 256;
/*     */   static final int SUBROUTINE = 512;
/*     */   static final int VISITED = 1024;
/*     */   public Object info;
/*     */   int status;
/*     */   int line;
/*     */   int position;
/*     */   private int referenceCount;
/*     */   private int[] srcAndRefPositions;
/*     */   int inputStackTop;
/*     */   int outputStackMax;
/*     */   Frame frame;
/*     */   Label successor;
/*     */   Edge successors;
/*     */   Label next;
/*     */   
/*     */   public int getOffset() {
/* 261 */     if ((this.status & 0x2) == 0) {
/* 262 */       throw new IllegalStateException("Label offset position has not been resolved yet");
/*     */     }
/* 264 */     return this.position;
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
/*     */   void put(MethodWriter owner, ByteVector out, int source, boolean wideOffset) {
/* 288 */     if ((this.status & 0x2) == 0) {
/* 289 */       if (wideOffset) {
/* 290 */         addReference(-1 - source, out.length);
/* 291 */         out.putInt(-1);
/*     */       } else {
/* 293 */         addReference(source, out.length);
/* 294 */         out.putShort(-1);
/*     */       }
/*     */     
/* 297 */     } else if (wideOffset) {
/* 298 */       out.putInt(this.position - source);
/*     */     } else {
/* 300 */       out.putShort(this.position - source);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void addReference(int sourcePosition, int referencePosition) {
/* 321 */     if (this.srcAndRefPositions == null) {
/* 322 */       this.srcAndRefPositions = new int[6];
/*     */     }
/* 324 */     if (this.referenceCount >= this.srcAndRefPositions.length) {
/* 325 */       int[] a = new int[this.srcAndRefPositions.length + 6];
/* 326 */       System.arraycopy(this.srcAndRefPositions, 0, a, 0, this.srcAndRefPositions.length);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 331 */       this.srcAndRefPositions = a;
/*     */     } 
/* 333 */     this.srcAndRefPositions[this.referenceCount++] = sourcePosition;
/* 334 */     this.srcAndRefPositions[this.referenceCount++] = referencePosition;
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
/*     */   boolean resolve(MethodWriter owner, int position, byte[] data) {
/* 361 */     boolean needUpdate = false;
/* 362 */     this.status |= 0x2;
/* 363 */     this.position = position;
/* 364 */     int i = 0;
/* 365 */     while (i < this.referenceCount) {
/* 366 */       int source = this.srcAndRefPositions[i++];
/* 367 */       int reference = this.srcAndRefPositions[i++];
/*     */       
/* 369 */       if (source >= 0) {
/* 370 */         int offset = position - source;
/* 371 */         if (offset < -32768 || offset > 32767) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 381 */           int opcode = data[reference - 1] & 0xFF;
/* 382 */           if (opcode <= 168) {
/*     */             
/* 384 */             data[reference - 1] = (byte)(opcode + 49);
/*     */           } else {
/*     */             
/* 387 */             data[reference - 1] = (byte)(opcode + 20);
/*     */           } 
/* 389 */           needUpdate = true;
/*     */         } 
/* 391 */         data[reference++] = (byte)(offset >>> 8);
/* 392 */         data[reference] = (byte)offset; continue;
/*     */       } 
/* 394 */       int offset = position + source + 1;
/* 395 */       data[reference++] = (byte)(offset >>> 24);
/* 396 */       data[reference++] = (byte)(offset >>> 16);
/* 397 */       data[reference++] = (byte)(offset >>> 8);
/* 398 */       data[reference] = (byte)offset;
/*     */     } 
/*     */     
/* 401 */     return needUpdate;
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
/* 413 */   Label getFirst() { return (this.frame == null) ? this : this.frame.owner; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   boolean inSubroutine(long id) {
/* 427 */     if ((this.status & 0x400) != 0) {
/* 428 */       return ((this.srcAndRefPositions[(int)(id >>> 32)] & (int)id) != 0);
/*     */     }
/* 430 */     return false;
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
/*     */   boolean inSameSubroutine(Label block) {
/* 442 */     for (int i = 0; i < this.srcAndRefPositions.length; i++) {
/* 443 */       if ((this.srcAndRefPositions[i] & block.srcAndRefPositions[i]) != 0) {
/* 444 */         return true;
/*     */       }
/*     */     } 
/* 447 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void addToSubroutine(long id, int nbSubroutines) {
/* 457 */     if ((this.status & 0x400) == 0) {
/* 458 */       this.status |= 0x400;
/* 459 */       this.srcAndRefPositions = new int[(nbSubroutines - 1) / 32 + 1];
/*     */     } 
/* 461 */     this.srcAndRefPositions[(int)(id >>> 32)] = this.srcAndRefPositions[(int)(id >>> 32)] | (int)id;
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
/*     */   void visitSubroutine(Label JSR, long id, int nbSubroutines) {
/* 478 */     if (JSR != null) {
/* 479 */       if ((this.status & 0x400) != 0) {
/*     */         return;
/*     */       }
/* 482 */       this.status |= 0x400;
/*     */       
/* 484 */       if ((this.status & 0x100) != 0 && 
/* 485 */         !inSameSubroutine(JSR)) {
/* 486 */         Edge e = new Edge();
/* 487 */         e.info = this.inputStackTop;
/* 488 */         e.successor = JSR.successors.successor;
/* 489 */         e.next = this.successors;
/* 490 */         this.successors = e;
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/* 495 */       if (inSubroutine(id)) {
/*     */         return;
/*     */       }
/*     */       
/* 499 */       addToSubroutine(id, nbSubroutines);
/*     */     } 
/*     */     
/* 502 */     Edge e = this.successors;
/* 503 */     while (e != null) {
/*     */ 
/*     */ 
/*     */       
/* 507 */       if ((this.status & 0x80) == 0 || e != this.successors.next) {
/* 508 */         e.successor.visitSubroutine(JSR, id, nbSubroutines);
/*     */       }
/* 510 */       e = e.next;
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
/* 524 */   public String toString() { return "L" + System.identityHashCode(this); }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebean\enhance\asm\Label.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */