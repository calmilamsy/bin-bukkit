/*     */ package org.ibex.nestedvm;
/*     */ 
/*     */ import java.io.BufferedReader;
/*     */ import java.io.DataInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.util.HashMap;
/*     */ import org.ibex.nestedvm.util.ELF;
/*     */ import org.ibex.nestedvm.util.Seekable;
/*     */ 
/*     */ public class Interpreter
/*     */   extends UnixRuntime
/*     */   implements Cloneable
/*     */ {
/*  16 */   private int[] registers = new int[32];
/*     */   
/*     */   private int hi;
/*     */   private int lo;
/*  20 */   private int[] fpregs = new int[32];
/*     */   
/*     */   private int fcsr;
/*     */   
/*     */   private int pc;
/*     */   
/*     */   public String image;
/*     */   
/*     */   private ELF.Symtab symtab;
/*     */   
/*     */   private int gp;
/*     */   
/*     */   private ELF.Symbol userInfo;
/*     */   private int entryPoint;
/*     */   private int heapStart;
/*     */   private HashMap sourceLineCache;
/*     */   
/*  37 */   private final void setFC(boolean paramBoolean) { this.fcsr = this.fcsr & 0xFF7FFFFF | (paramBoolean ? 8388608 : 0); }
/*  38 */   private final int roundingMode() { return this.fcsr & 0x3; }
/*     */   
/*  40 */   private final double getDouble(int paramInt) { return Double.longBitsToDouble((this.fpregs[paramInt + 1] & 0xFFFFFFFFL) << 32 | this.fpregs[paramInt] & 0xFFFFFFFFL); }
/*     */   
/*     */   private final void setDouble(int paramInt, double paramDouble) {
/*  43 */     long l = Double.doubleToLongBits(paramDouble);
/*  44 */     this.fpregs[paramInt + 1] = (int)(l >>> 32); this.fpregs[paramInt] = (int)l;
/*     */   }
/*  46 */   private final float getFloat(int paramInt) { return Float.intBitsToFloat(this.fpregs[paramInt]); }
/*  47 */   private final void setFloat(int paramInt, float paramFloat) { this.fpregs[paramInt] = Float.floatToRawIntBits(paramFloat); }
/*     */   
/*     */   protected void _execute() throws Runtime.ExecutionException {
/*     */     try {
/*  51 */       runSome();
/*  52 */     } catch (ExecutionException executionException) {
/*  53 */       executionException.setLocation(toHex(this.pc) + ": " + sourceLine(this.pc));
/*  54 */       throw executionException;
/*     */     } 
/*     */   }
/*     */   
/*     */   protected Object clone() throws CloneNotSupportedException {
/*  59 */     Interpreter interpreter = (Interpreter)super.clone();
/*  60 */     interpreter.registers = (int[])this.registers.clone();
/*  61 */     interpreter.fpregs = (int[])this.fpregs.clone();
/*  62 */     return interpreter;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private final int runSome() {
/*  68 */     int i = 1 << this.pageShift >> 2;
/*  69 */     int[] arrayOfInt1 = this.registers;
/*  70 */     int[] arrayOfInt2 = this.fpregs;
/*  71 */     int j = this.pc;
/*  72 */     int k = j + 4; try {
/*     */       while (true) {
/*     */         boolean bool2; String str; long l;
/*     */         int i14, i13, m;
/*     */         try {
/*  77 */           m = this.readPages[j >>> this.pageShift][j >>> 2 & i - 1];
/*  78 */         } catch (RuntimeException runtimeException) {
/*  79 */           if (j == -559038737) throw new Error("fell off cpu: r2: " + arrayOfInt1[2]); 
/*  80 */           m = memRead(j);
/*     */         } 
/*     */         
/*  83 */         int n = m >>> 26 & 0xFF;
/*  84 */         int i1 = m >>> 21 & 0x1F;
/*  85 */         int i2 = m >>> 16 & 0x1F;
/*  86 */         int i3 = m >>> 16 & 0x1F;
/*  87 */         int i4 = m >>> 11 & 0x1F;
/*  88 */         int i5 = m >>> 11 & 0x1F;
/*  89 */         int i6 = m >>> 6 & 0x1F;
/*  90 */         int i7 = m >>> 6 & 0x1F;
/*  91 */         int i8 = m & 0x3F;
/*     */         
/*  93 */         int i9 = m & 0x3FFFFFF;
/*  94 */         int i10 = m & 0xFFFF;
/*  95 */         int i11 = m << 16 >> 16;
/*  96 */         int i12 = i11;
/*     */ 
/*     */ 
/*     */         
/* 100 */         arrayOfInt1[0] = 0;
/*     */         
/* 102 */         switch (n) {
/*     */           case 0:
/* 104 */             switch (i8) {
/*     */               case 0:
/* 106 */                 if (m == 0)
/* 107 */                   break;  arrayOfInt1[i4] = arrayOfInt1[i2] << i6;
/*     */                 break;
/*     */               case 2:
/* 110 */                 arrayOfInt1[i4] = arrayOfInt1[i2] >>> i6;
/*     */                 break;
/*     */               case 3:
/* 113 */                 arrayOfInt1[i4] = arrayOfInt1[i2] >> i6;
/*     */                 break;
/*     */               case 4:
/* 116 */                 arrayOfInt1[i4] = arrayOfInt1[i2] << (arrayOfInt1[i1] & 0x1F);
/*     */                 break;
/*     */               case 6:
/* 119 */                 arrayOfInt1[i4] = arrayOfInt1[i2] >>> (arrayOfInt1[i1] & 0x1F);
/*     */                 break;
/*     */               case 7:
/* 122 */                 arrayOfInt1[i4] = arrayOfInt1[i2] >> (arrayOfInt1[i1] & 0x1F);
/*     */                 break;
/*     */               case 8:
/* 125 */                 i13 = arrayOfInt1[i1]; j += 4; k = i13;
/*     */                 continue;
/*     */               case 9:
/* 128 */                 i13 = arrayOfInt1[i1]; j += 4; arrayOfInt1[i4] = j + 4; k = i13;
/*     */                 continue;
/*     */               case 12:
/* 131 */                 this.pc = j;
/* 132 */                 arrayOfInt1[2] = syscall(arrayOfInt1[2], arrayOfInt1[4], arrayOfInt1[5], arrayOfInt1[6], arrayOfInt1[7], arrayOfInt1[8], arrayOfInt1[9]);
/* 133 */                 if (this.state != 0) { this.pc = k; break; }
/*     */                  break;
/*     */               case 13:
/* 136 */                 throw new Runtime.ExecutionException("Break");
/*     */               case 16:
/* 138 */                 arrayOfInt1[i4] = this.hi;
/*     */                 break;
/*     */               case 17:
/* 141 */                 this.hi = arrayOfInt1[i1];
/*     */                 break;
/*     */               case 18:
/* 144 */                 arrayOfInt1[i4] = this.lo;
/*     */                 break;
/*     */               case 19:
/* 147 */                 this.lo = arrayOfInt1[i1];
/*     */                 break;
/*     */               case 24:
/* 150 */                 l = arrayOfInt1[i1] * arrayOfInt1[i2];
/* 151 */                 this.hi = (int)(l >>> 32);
/* 152 */                 this.lo = (int)l;
/*     */                 break;
/*     */               
/*     */               case 25:
/* 156 */                 l = (arrayOfInt1[i1] & 0xFFFFFFFFL) * (arrayOfInt1[i2] & 0xFFFFFFFFL);
/* 157 */                 this.hi = (int)(l >>> 32);
/* 158 */                 this.lo = (int)l;
/*     */                 break;
/*     */               
/*     */               case 26:
/* 162 */                 this.hi = arrayOfInt1[i1] % arrayOfInt1[i2];
/* 163 */                 this.lo = arrayOfInt1[i1] / arrayOfInt1[i2];
/*     */                 break;
/*     */               case 27:
/* 166 */                 if (i2 != 0) {
/* 167 */                   this.hi = (int)((arrayOfInt1[i1] & 0xFFFFFFFFL) % (arrayOfInt1[i2] & 0xFFFFFFFFL));
/* 168 */                   this.lo = (int)((arrayOfInt1[i1] & 0xFFFFFFFFL) / (arrayOfInt1[i2] & 0xFFFFFFFFL));
/*     */                 } 
/*     */                 break;
/*     */               case 32:
/* 172 */                 throw new Runtime.ExecutionException("ADD (add with oveflow trap) not suported");
/*     */ 
/*     */ 
/*     */               
/*     */               case 33:
/* 177 */                 arrayOfInt1[i4] = arrayOfInt1[i1] + arrayOfInt1[i2];
/*     */                 break;
/*     */               case 34:
/* 180 */                 throw new Runtime.ExecutionException("SUB (sub with oveflow trap) not suported");
/*     */ 
/*     */ 
/*     */               
/*     */               case 35:
/* 185 */                 arrayOfInt1[i4] = arrayOfInt1[i1] - arrayOfInt1[i2];
/*     */                 break;
/*     */               case 36:
/* 188 */                 arrayOfInt1[i4] = arrayOfInt1[i1] & arrayOfInt1[i2];
/*     */                 break;
/*     */               case 37:
/* 191 */                 arrayOfInt1[i4] = arrayOfInt1[i1] | arrayOfInt1[i2];
/*     */                 break;
/*     */               case 38:
/* 194 */                 arrayOfInt1[i4] = arrayOfInt1[i1] ^ arrayOfInt1[i2];
/*     */                 break;
/*     */               case 39:
/* 197 */                 arrayOfInt1[i4] = (arrayOfInt1[i1] | arrayOfInt1[i2]) ^ 0xFFFFFFFF;
/*     */                 break;
/*     */               case 42:
/* 200 */                 arrayOfInt1[i4] = (arrayOfInt1[i1] < arrayOfInt1[i2]) ? 1 : 0;
/*     */                 break;
/*     */               case 43:
/* 203 */                 arrayOfInt1[i4] = ((arrayOfInt1[i1] & 0xFFFFFFFFL) < (arrayOfInt1[i2] & 0xFFFFFFFFL)) ? 1 : 0;
/*     */                 break;
/*     */             } 
/* 206 */             throw new Runtime.ExecutionException("Illegal instruction 0/" + i8);
/*     */ 
/*     */ 
/*     */           
/*     */           case 1:
/* 211 */             switch (i2) {
/*     */               case 0:
/* 213 */                 if (arrayOfInt1[i1] < 0) {
/* 214 */                   j += 4; k = i13 = j + i12 * 4;
/*     */                   continue;
/*     */                 } 
/*     */                 break;
/*     */               case 1:
/* 219 */                 if (arrayOfInt1[i1] >= 0) {
/* 220 */                   j += 4; k = i13 = j + i12 * 4;
/*     */                   continue;
/*     */                 } 
/*     */                 break;
/*     */               case 16:
/* 225 */                 if (arrayOfInt1[i1] < 0) {
/* 226 */                   j += 4; arrayOfInt1[31] = j + 4; k = i13 = j + i12 * 4;
/*     */                   continue;
/*     */                 } 
/*     */                 break;
/*     */               case 17:
/* 231 */                 if (arrayOfInt1[i1] >= 0) {
/* 232 */                   j += 4; arrayOfInt1[31] = j + 4; k = i13 = j + i12 * 4;
/*     */                   continue;
/*     */                 } 
/*     */                 break;
/*     */             } 
/* 237 */             throw new Runtime.ExecutionException("Illegal Instruction");
/*     */ 
/*     */ 
/*     */           
/*     */           case 2:
/* 242 */             i13 = j & 0xF0000000 | i9 << 2;
/* 243 */             j += 4; k = i13;
/*     */             continue;
/*     */           
/*     */           case 3:
/* 247 */             i13 = j & 0xF0000000 | i9 << 2;
/* 248 */             j += 4; arrayOfInt1[31] = j + 4; k = i13;
/*     */             continue;
/*     */           
/*     */           case 4:
/* 252 */             if (arrayOfInt1[i1] == arrayOfInt1[i2]) {
/* 253 */               j += 4; k = i13 = j + i12 * 4;
/*     */               continue;
/*     */             } 
/*     */             break;
/*     */           case 5:
/* 258 */             if (arrayOfInt1[i1] != arrayOfInt1[i2]) {
/* 259 */               j += 4; k = i13 = j + i12 * 4;
/*     */               continue;
/*     */             } 
/*     */             break;
/*     */           case 6:
/* 264 */             if (arrayOfInt1[i1] <= 0) {
/* 265 */               j += 4; k = i13 = j + i12 * 4;
/*     */               continue;
/*     */             } 
/*     */             break;
/*     */           case 7:
/* 270 */             if (arrayOfInt1[i1] > 0) {
/* 271 */               j += 4; k = i13 = j + i12 * 4;
/*     */               continue;
/*     */             } 
/*     */             break;
/*     */           case 8:
/* 276 */             arrayOfInt1[i2] = arrayOfInt1[i1] + i11;
/*     */             break;
/*     */           case 9:
/* 279 */             arrayOfInt1[i2] = arrayOfInt1[i1] + i11;
/*     */             break;
/*     */           case 10:
/* 282 */             arrayOfInt1[i2] = (arrayOfInt1[i1] < i11) ? 1 : 0;
/*     */             break;
/*     */           case 11:
/* 285 */             arrayOfInt1[i2] = ((arrayOfInt1[i1] & 0xFFFFFFFFL) < (i11 & 0xFFFFFFFFL)) ? 1 : 0;
/*     */             break;
/*     */           case 12:
/* 288 */             arrayOfInt1[i2] = arrayOfInt1[i1] & i10;
/*     */             break;
/*     */           case 13:
/* 291 */             arrayOfInt1[i2] = arrayOfInt1[i1] | i10;
/*     */             break;
/*     */           case 14:
/* 294 */             arrayOfInt1[i2] = arrayOfInt1[i1] ^ i10;
/*     */             break;
/*     */           case 15:
/* 297 */             arrayOfInt1[i2] = i10 << 16;
/*     */             break;
/*     */           case 16:
/* 300 */             throw new Runtime.ExecutionException("TLB/Exception support not implemented");
/*     */           case 17:
/* 302 */             bool1 = false;
/* 303 */             str = bool1 ? sourceLine(j) : "";
/* 304 */             bool2 = (bool1 && (str.indexOf("dtoa.c:51") >= 0 || str.indexOf("dtoa.c:52") >= 0 || str.indexOf("test.c") >= 0)) ? 1 : 0;
/* 305 */             if (i1 > 8 && bool2)
/* 306 */               System.out.println("               FP Op: " + n + "/" + i1 + "/" + i8 + " " + str); 
/* 307 */             if (roundingMode() != 0 && i1 != 6 && ((i1 != 16 && i1 != 17) || i8 != 36))
/* 308 */               throw new Runtime.ExecutionException("Non-cvt.w.z operation attempted with roundingMode != round to nearest"); 
/* 309 */             switch (i1) {
/*     */               case 0:
/* 311 */                 arrayOfInt1[i2] = arrayOfInt2[i4];
/*     */                 break;
/*     */               case 2:
/* 314 */                 if (i5 != 31) throw new Runtime.ExecutionException("FCR " + i5 + " unavailable"); 
/* 315 */                 arrayOfInt1[i2] = this.fcsr;
/*     */                 break;
/*     */               case 4:
/* 318 */                 arrayOfInt2[i4] = arrayOfInt1[i2];
/*     */                 break;
/*     */               case 6:
/* 321 */                 if (i5 != 31) throw new Runtime.ExecutionException("FCR " + i5 + " unavailable"); 
/* 322 */                 this.fcsr = arrayOfInt1[i2];
/*     */                 break;
/*     */               case 8:
/* 325 */                 if ((((this.fcsr & 0x800000) != 0) ? 1 : 0) == (((m >>> 16 & true) != 0) ? 1 : 0)) {
/* 326 */                   j += 4; k = i13 = j + i12 * 4;
/*     */                   continue;
/*     */                 } 
/*     */                 break;
/*     */               case 16:
/* 331 */                 switch (i8) {
/*     */                   case 0:
/* 333 */                     setFloat(i7, getFloat(i5) + getFloat(i3));
/*     */                     break;
/*     */                   case 1:
/* 336 */                     setFloat(i7, getFloat(i5) - getFloat(i3));
/*     */                     break;
/*     */                   case 2:
/* 339 */                     setFloat(i7, getFloat(i5) * getFloat(i3));
/*     */                     break;
/*     */                   case 3:
/* 342 */                     setFloat(i7, getFloat(i5) / getFloat(i3));
/*     */                     break;
/*     */                   case 5:
/* 345 */                     setFloat(i7, Math.abs(getFloat(i5)));
/*     */                     break;
/*     */                   case 6:
/* 348 */                     arrayOfInt2[i7] = arrayOfInt2[i5];
/*     */                     break;
/*     */                   case 7:
/* 351 */                     setFloat(i7, -getFloat(i5));
/*     */                     break;
/*     */                   case 33:
/* 354 */                     setDouble(i7, getFloat(i5));
/*     */                     break;
/*     */                   case 36:
/* 357 */                     switch (roundingMode()) { case 0:
/* 358 */                         arrayOfInt2[i7] = (int)Math.floor((getFloat(i5) + 0.5F)); break;
/* 359 */                       case 1: arrayOfInt2[i7] = (int)getFloat(i5); break;
/* 360 */                       case 2: arrayOfInt2[i7] = (int)Math.ceil(getFloat(i5)); break;
/* 361 */                       case 3: case 0: break; }  arrayOfInt2[i7] = (int)Math.floor(getFloat(i5));
/*     */                     break;
/*     */                   
/*     */                   case 50:
/* 365 */                     setFC((getFloat(i5) == getFloat(i3)));
/*     */                     break;
/*     */                   case 60:
/* 368 */                     setFC((getFloat(i5) < getFloat(i3)));
/*     */                     break;
/*     */                   case 62:
/* 371 */                     setFC((getFloat(i5) <= getFloat(i3))); break;
/*     */                 } 
/* 373 */                 throw new Runtime.ExecutionException("Invalid Instruction 17/" + i1 + "/" + i8 + " at " + sourceLine(j));
/*     */ 
/*     */ 
/*     */               
/*     */               case 17:
/* 378 */                 switch (i8) {
/*     */                   case 0:
/* 380 */                     setDouble(i7, getDouble(i5) + getDouble(i3));
/*     */                     break;
/*     */                   case 1:
/* 383 */                     if (bool2) System.out.println("f" + i7 + " = f" + i5 + " (" + getDouble(i5) + ") - f" + i3 + " (" + getDouble(i3) + ")"); 
/* 384 */                     setDouble(i7, getDouble(i5) - getDouble(i3));
/*     */                     break;
/*     */                   case 2:
/* 387 */                     if (bool2) System.out.println("f" + i7 + " = f" + i5 + " (" + getDouble(i5) + ") * f" + i3 + " (" + getDouble(i3) + ")"); 
/* 388 */                     setDouble(i7, getDouble(i5) * getDouble(i3));
/* 389 */                     if (bool2) System.out.println("f" + i7 + " = " + getDouble(i7)); 
/*     */                     break;
/*     */                   case 3:
/* 392 */                     setDouble(i7, getDouble(i5) / getDouble(i3));
/*     */                     break;
/*     */                   case 5:
/* 395 */                     setDouble(i7, Math.abs(getDouble(i5)));
/*     */                     break;
/*     */                   case 6:
/* 398 */                     arrayOfInt2[i7] = arrayOfInt2[i5];
/* 399 */                     arrayOfInt2[i7 + 1] = arrayOfInt2[i5 + 1];
/*     */                     break;
/*     */                   case 7:
/* 402 */                     setDouble(i7, -getDouble(i5));
/*     */                     break;
/*     */                   case 32:
/* 405 */                     setFloat(i7, (float)getDouble(i5));
/*     */                     break;
/*     */                   case 36:
/* 408 */                     if (bool2) System.out.println("CVT.W.D rm: " + roundingMode() + " f" + i5 + ":" + getDouble(i5)); 
/* 409 */                     switch (roundingMode()) { case 0:
/* 410 */                         arrayOfInt2[i7] = (int)Math.floor(getDouble(i5) + 0.5D); break;
/* 411 */                       case 1: arrayOfInt2[i7] = (int)getDouble(i5); break;
/* 412 */                       case 2: arrayOfInt2[i7] = (int)Math.ceil(getDouble(i5)); break;
/* 413 */                       case 3: arrayOfInt2[i7] = (int)Math.floor(getDouble(i5)); break; }
/*     */                     
/* 415 */                     if (bool2) System.out.println("CVT.W.D: f" + i7 + ":" + arrayOfInt2[i7]); 
/*     */                     break;
/*     */                   case 50:
/* 418 */                     setFC((getDouble(i5) == getDouble(i3)));
/*     */                     break;
/*     */                   case 60:
/* 421 */                     setFC((getDouble(i5) < getDouble(i3)));
/*     */                     break;
/*     */                   case 62:
/* 424 */                     setFC((getDouble(i5) <= getDouble(i3))); break;
/*     */                 } 
/* 426 */                 throw new Runtime.ExecutionException("Invalid Instruction 17/" + i1 + "/" + i8 + " at " + sourceLine(j));
/*     */ 
/*     */ 
/*     */               
/*     */               case 20:
/* 431 */                 switch (i8) {
/*     */                   case 32:
/* 433 */                     setFloat(i7, arrayOfInt2[i5]);
/*     */                     break;
/*     */                   case 33:
/* 436 */                     setDouble(i7, arrayOfInt2[i5]); break;
/*     */                 } 
/* 438 */                 throw new Runtime.ExecutionException("Invalid Instruction 17/" + i1 + "/" + i8 + " at " + sourceLine(j));
/*     */             } 
/*     */ 
/*     */ 
/*     */             
/* 443 */             throw new Runtime.ExecutionException("Invalid Instruction 17/" + i1);
/*     */ 
/*     */           
/*     */           case 18:
/*     */           case 19:
/* 448 */             throw new Runtime.ExecutionException("No coprocessor installed");
/*     */           case 32:
/* 450 */             i14 = arrayOfInt1[i1] + i11;
/*     */             try {
/* 452 */               i13 = this.readPages[i14 >>> this.pageShift][i14 >>> 2 & i - 1];
/* 453 */             } catch (RuntimeException bool1) {
/* 454 */               RuntimeException runtimeException; i13 = memRead(i14 & 0xFFFFFFFC);
/*     */             } 
/* 456 */             switch (i14 & 0x3) { case 0:
/* 457 */                 i13 = i13 >>> 24 & 0xFF; break;
/* 458 */               case 1: i13 = i13 >>> 16 & 0xFF; break;
/* 459 */               case 2: i13 = i13 >>> 8 & 0xFF; break;
/* 460 */               case 3: i13 = i13 >>> 0 & 0xFF; break; }
/*     */             
/* 462 */             if ((i13 & 0x80) != 0) i13 |= 0xFFFFFF00; 
/* 463 */             arrayOfInt1[i2] = i13;
/*     */             break;
/*     */           
/*     */           case 33:
/* 467 */             i14 = arrayOfInt1[i1] + i11;
/*     */             try {
/* 469 */               i13 = this.readPages[i14 >>> this.pageShift][i14 >>> 2 & i - 1];
/* 470 */             } catch (RuntimeException bool1) {
/* 471 */               RuntimeException runtimeException; i13 = memRead(i14 & 0xFFFFFFFC);
/*     */             } 
/* 473 */             switch (i14 & 0x3) { case 0:
/* 474 */                 i13 = i13 >>> 16 & 0xFFFF; break;
/* 475 */               case 2: i13 = i13 >>> 0 & 0xFFFF; break;
/* 476 */               default: throw new Runtime.ReadFaultException(i14); }
/*     */             
/* 478 */             if ((i13 & 0x8000) != 0) i13 |= 0xFFFF0000; 
/* 479 */             arrayOfInt1[i2] = i13;
/*     */             break;
/*     */           
/*     */           case 34:
/* 483 */             i14 = arrayOfInt1[i1] + i11;
/*     */             try {
/* 485 */               i13 = this.readPages[i14 >>> this.pageShift][i14 >>> 2 & i - 1];
/* 486 */             } catch (RuntimeException bool1) {
/* 487 */               RuntimeException runtimeException; i13 = memRead(i14 & 0xFFFFFFFC);
/*     */             } 
/* 489 */             switch (i14 & 0x3) { case 0:
/* 490 */                 arrayOfInt1[i2] = arrayOfInt1[i2] & false | i13 << 0; break;
/* 491 */               case 1: arrayOfInt1[i2] = arrayOfInt1[i2] & 0xFF | i13 << 8; break;
/* 492 */               case 2: arrayOfInt1[i2] = arrayOfInt1[i2] & 0xFFFF | i13 << 16; break;
/* 493 */               case 3: case 0: break; }  arrayOfInt1[i2] = arrayOfInt1[i2] & 0xFFFFFF | i13 << 24;
/*     */             break;
/*     */ 
/*     */           
/*     */           case 35:
/* 498 */             i14 = arrayOfInt1[i1] + i11;
/*     */             try {
/* 500 */               arrayOfInt1[i2] = this.readPages[i14 >>> this.pageShift][i14 >>> 2 & i - 1];
/* 501 */             } catch (RuntimeException bool1) {
/* 502 */               RuntimeException runtimeException; arrayOfInt1[i2] = memRead(i14);
/*     */             } 
/*     */             break;
/*     */           case 36:
/* 506 */             i14 = arrayOfInt1[i1] + i11;
/*     */             try {
/* 508 */               i13 = this.readPages[i14 >>> this.pageShift][i14 >>> 2 & i - 1];
/* 509 */             } catch (RuntimeException bool1) {
/* 510 */               RuntimeException runtimeException; i13 = memRead(i14);
/*     */             } 
/* 512 */             switch (i14 & 0x3) { case 0:
/* 513 */                 arrayOfInt1[i2] = i13 >>> 24 & 0xFF; break;
/* 514 */               case 1: arrayOfInt1[i2] = i13 >>> 16 & 0xFF; break;
/* 515 */               case 2: arrayOfInt1[i2] = i13 >>> 8 & 0xFF; break;
/* 516 */               case 3: case 0: break; }  arrayOfInt1[i2] = i13 >>> 0 & 0xFF;
/*     */             break;
/*     */ 
/*     */           
/*     */           case 37:
/* 521 */             i14 = arrayOfInt1[i1] + i11;
/*     */             try {
/* 523 */               i13 = this.readPages[i14 >>> this.pageShift][i14 >>> 2 & i - 1];
/* 524 */             } catch (RuntimeException bool1) {
/* 525 */               RuntimeException runtimeException; i13 = memRead(i14 & 0xFFFFFFFC);
/*     */             } 
/* 527 */             switch (i14 & 0x3) { case 0:
/* 528 */                 arrayOfInt1[i2] = i13 >>> 16 & 0xFFFF; break;
/* 529 */               case 2: arrayOfInt1[i2] = i13 >>> 0 & 0xFFFF; break; }
/* 530 */              throw new Runtime.ReadFaultException(i14);
/*     */ 
/*     */ 
/*     */           
/*     */           case 38:
/* 535 */             i14 = arrayOfInt1[i1] + i11;
/*     */             try {
/* 537 */               i13 = this.readPages[i14 >>> this.pageShift][i14 >>> 2 & i - 1];
/* 538 */             } catch (RuntimeException bool1) {
/* 539 */               RuntimeException runtimeException; i13 = memRead(i14 & 0xFFFFFFFC);
/*     */             } 
/* 541 */             switch (i14 & 0x3) { case 0:
/* 542 */                 arrayOfInt1[i2] = arrayOfInt1[i2] & 0xFFFFFF00 | i13 >>> 24; break;
/* 543 */               case 1: arrayOfInt1[i2] = arrayOfInt1[i2] & 0xFFFF0000 | i13 >>> 16; break;
/* 544 */               case 2: arrayOfInt1[i2] = arrayOfInt1[i2] & 0xFF000000 | i13 >>> 8; break;
/* 545 */               case 3: case 0: break; }  arrayOfInt1[i2] = arrayOfInt1[i2] & false | i13 >>> 0;
/*     */             break;
/*     */ 
/*     */           
/*     */           case 40:
/* 550 */             i14 = arrayOfInt1[i1] + i11;
/*     */             try {
/* 552 */               i13 = this.readPages[i14 >>> this.pageShift][i14 >>> 2 & i - 1];
/* 553 */             } catch (RuntimeException bool1) {
/* 554 */               RuntimeException runtimeException; i13 = memRead(i14 & 0xFFFFFFFC);
/*     */             } 
/* 556 */             switch (i14 & 0x3) { case 0:
/* 557 */                 i13 = i13 & 0xFFFFFF | (arrayOfInt1[i2] & 0xFF) << 24; break;
/* 558 */               case 1: i13 = i13 & 0xFF00FFFF | (arrayOfInt1[i2] & 0xFF) << 16; break;
/* 559 */               case 2: i13 = i13 & 0xFFFF00FF | (arrayOfInt1[i2] & 0xFF) << 8; break;
/* 560 */               case 3: i13 = i13 & 0xFFFFFF00 | (arrayOfInt1[i2] & 0xFF) << 0; break; }
/*     */             
/*     */             try {
/* 563 */               this.writePages[i14 >>> this.pageShift][i14 >>> 2 & i - 1] = i13;
/* 564 */             } catch (RuntimeException bool1) {
/* 565 */               RuntimeException runtimeException; memWrite(i14 & 0xFFFFFFFC, i13);
/*     */             } 
/*     */             break;
/*     */           
/*     */           case 41:
/* 570 */             i14 = arrayOfInt1[i1] + i11;
/*     */             try {
/* 572 */               i13 = this.readPages[i14 >>> this.pageShift][i14 >>> 2 & i - 1];
/* 573 */             } catch (RuntimeException bool1) {
/* 574 */               RuntimeException runtimeException; i13 = memRead(i14 & 0xFFFFFFFC);
/*     */             } 
/* 576 */             switch (i14 & 0x3) { case 0:
/* 577 */                 i13 = i13 & 0xFFFF | (arrayOfInt1[i2] & 0xFFFF) << 16; break;
/* 578 */               case 2: i13 = i13 & 0xFFFF0000 | (arrayOfInt1[i2] & 0xFFFF) << 0; break;
/* 579 */               default: throw new Runtime.WriteFaultException(i14); }
/*     */             
/*     */             try {
/* 582 */               this.writePages[i14 >>> this.pageShift][i14 >>> 2 & i - 1] = i13;
/* 583 */             } catch (RuntimeException bool1) {
/* 584 */               RuntimeException runtimeException; memWrite(i14 & 0xFFFFFFFC, i13);
/*     */             } 
/*     */             break;
/*     */           
/*     */           case 42:
/* 589 */             i14 = arrayOfInt1[i1] + i11;
/* 590 */             i13 = memRead(i14 & 0xFFFFFFFC);
/* 591 */             switch (i14 & 0x3) { case 0:
/* 592 */                 i13 = i13 & false | arrayOfInt1[i2] >>> 0; break;
/* 593 */               case 1: i13 = i13 & 0xFF000000 | arrayOfInt1[i2] >>> 8; break;
/* 594 */               case 2: i13 = i13 & 0xFFFF0000 | arrayOfInt1[i2] >>> 16; break;
/* 595 */               case 3: i13 = i13 & 0xFFFFFF00 | arrayOfInt1[i2] >>> 24; break; }
/*     */             
/*     */             try {
/* 598 */               this.writePages[i14 >>> this.pageShift][i14 >>> 2 & i - 1] = i13;
/* 599 */             } catch (RuntimeException bool1) {
/* 600 */               RuntimeException runtimeException; memWrite(i14 & 0xFFFFFFFC, i13);
/*     */             } 
/*     */             break;
/*     */           
/*     */           case 43:
/* 605 */             i14 = arrayOfInt1[i1] + i11;
/*     */             try {
/* 607 */               this.writePages[i14 >>> this.pageShift][i14 >>> 2 & i - 1] = arrayOfInt1[i2];
/* 608 */             } catch (RuntimeException bool1) {
/* 609 */               RuntimeException runtimeException; memWrite(i14 & 0xFFFFFFFC, arrayOfInt1[i2]);
/*     */             } 
/*     */             break;
/*     */           case 46:
/* 613 */             i14 = arrayOfInt1[i1] + i11;
/* 614 */             i13 = memRead(i14 & 0xFFFFFFFC);
/* 615 */             switch (i14 & 0x3) { case 0:
/* 616 */                 i13 = i13 & 0xFFFFFF | arrayOfInt1[i2] << 24; break;
/* 617 */               case 1: i13 = i13 & 0xFFFF | arrayOfInt1[i2] << 16; break;
/* 618 */               case 2: i13 = i13 & 0xFF | arrayOfInt1[i2] << 8; break;
/* 619 */               case 3: i13 = i13 & false | arrayOfInt1[i2] << 0; break; }
/*     */             
/* 621 */             memWrite(i14 & 0xFFFFFFFC, i13);
/*     */             break;
/*     */ 
/*     */           
/*     */           case 48:
/* 626 */             arrayOfInt1[i2] = memRead(arrayOfInt1[i1] + i11);
/*     */             break;
/*     */           case 49:
/* 629 */             arrayOfInt2[i2] = memRead(arrayOfInt1[i1] + i11);
/*     */             break;
/*     */           
/*     */           case 56:
/* 633 */             memWrite(arrayOfInt1[i1] + i11, arrayOfInt1[i2]);
/* 634 */             arrayOfInt1[i2] = 1;
/*     */             break;
/*     */           case 57:
/* 637 */             memWrite(arrayOfInt1[i1] + i11, arrayOfInt2[i2]);
/*     */             break;
/*     */           default:
/* 640 */             throw new Runtime.ExecutionException("Invalid Instruction: " + n);
/*     */         } 
/* 642 */         j = k;
/* 643 */         k = j + 4;
/*     */       } 
/* 645 */     } catch (ExecutionException executionException) {
/* 646 */       this.pc = j;
/* 647 */       throw executionException;
/*     */     } 
/* 649 */     return 0;
/*     */   }
/*     */   
/*     */   public int lookupSymbol(String paramString) {
/* 653 */     ELF.Symbol symbol = this.symtab.getGlobalSymbol(paramString);
/* 654 */     return (symbol == null) ? -1 : symbol.addr;
/*     */   }
/*     */ 
/*     */   
/* 658 */   protected int gp() { return this.gp; }
/*     */ 
/*     */   
/* 661 */   protected int userInfoBae() { return (this.userInfo == null) ? 0 : this.userInfo.addr; }
/* 662 */   protected int userInfoSize() { return (this.userInfo == null) ? 0 : this.userInfo.size; }
/*     */ 
/*     */   
/* 665 */   protected int entryPoint() { return this.entryPoint; }
/*     */ 
/*     */   
/* 668 */   protected int heapStart() { return this.heapStart; }
/*     */ 
/*     */   
/*     */   private void loadImage(Seekable paramSeekable) throws IOException {
/* 672 */     ELF eLF = new ELF(paramSeekable);
/* 673 */     this.symtab = eLF.getSymtab();
/*     */     
/* 675 */     if (eLF.header.type != 2) throw new IOException("Binary is not an executable"); 
/* 676 */     if (eLF.header.machine != 8) throw new IOException("Binary is not for the MIPS I Architecture"); 
/* 677 */     if (eLF.ident.data != 2) throw new IOException("Binary is not big endian");
/*     */     
/* 679 */     this.entryPoint = eLF.header.entry;
/*     */     
/* 681 */     ELF.Symtab symtab1 = eLF.getSymtab();
/* 682 */     if (symtab1 == null) throw new IOException("No symtab in binary (did you strip it?)"); 
/* 683 */     this.userInfo = symtab1.getGlobalSymbol("user_info");
/* 684 */     ELF.Symbol symbol = symtab1.getGlobalSymbol("_gp");
/*     */     
/* 686 */     if (symbol == null) throw new IOException("NO _gp symbol!"); 
/* 687 */     this.gp = symbol.addr;
/*     */     
/* 689 */     this.entryPoint = eLF.header.entry;
/*     */     
/* 691 */     ELF.PHeader[] arrayOfPHeader = eLF.pheaders;
/* 692 */     int i = 0;
/* 693 */     int j = 1 << this.pageShift;
/* 694 */     int k = 1 << this.pageShift >> 2;
/* 695 */     for (byte b = 0; b < arrayOfPHeader.length; b++) {
/* 696 */       ELF.PHeader pHeader = arrayOfPHeader[b];
/* 697 */       if (pHeader.type == 1) {
/* 698 */         int m = pHeader.memsz;
/* 699 */         int n = pHeader.filesz;
/* 700 */         if (m != 0) {
/* 701 */           if (m < 0) throw new IOException("pheader size too large"); 
/* 702 */           int i1 = pHeader.vaddr;
/* 703 */           if (i1 == 0) throw new IOException("pheader vaddr == 0x0"); 
/* 704 */           i = max(i1 + m, i);
/*     */           int i2;
/* 706 */           for (i2 = 0; i2 < m + j - 1; i2 += j) {
/* 707 */             int i3 = i2 + i1 >>> this.pageShift;
/* 708 */             if (this.readPages[i3] == null)
/* 709 */               this.readPages[i3] = new int[k]; 
/* 710 */             if (pHeader.writable()) this.writePages[i3] = this.readPages[i3]; 
/*     */           } 
/* 712 */           if (n != 0)
/* 713 */           { n &= 0xFFFFFFFC;
/* 714 */             DataInputStream dataInputStream = new DataInputStream(pHeader.getInputStream());
/*     */             do {
/* 716 */               this.readPages[i1 >>> this.pageShift][i1 >>> 2 & k - 1] = dataInputStream.readInt();
/* 717 */               i1 += 4;
/* 718 */               n -= 4;
/* 719 */             } while (n > 0);
/* 720 */             dataInputStream.close(); } 
/*     */         } 
/*     */       } 
/* 723 */     }  this.heapStart = i + j - 1 & (j - 1 ^ 0xFFFFFFFF);
/*     */   }
/*     */   protected void setCPUState(Runtime.CPUState paramCPUState) {
/*     */     byte b;
/* 727 */     for (b = 1; b < 32; ) { this.registers[b] = paramCPUState.r[b]; b++; }
/* 728 */      for (b = 0; b < 32; ) { this.fpregs[b] = paramCPUState.f[b]; b++; }
/* 729 */      this.hi = paramCPUState.hi; this.lo = paramCPUState.lo; this.fcsr = paramCPUState.fcsr;
/* 730 */     this.pc = paramCPUState.pc;
/*     */   }
/*     */   protected void getCPUState(Runtime.CPUState paramCPUState) {
/*     */     byte b;
/* 734 */     for (b = 1; b < 32; ) { paramCPUState.r[b] = this.registers[b]; b++; }
/* 735 */      for (b = 0; b < 32; ) { paramCPUState.f[b] = this.fpregs[b]; b++; }
/* 736 */      paramCPUState.hi = this.hi; paramCPUState.lo = this.lo; paramCPUState.fcsr = this.fcsr;
/* 737 */     paramCPUState.pc = this.pc;
/*     */   }
/*     */   
/*     */   public Interpreter(Seekable paramSeekable) throws IOException {
/* 741 */     super(4096, 65536);
/* 742 */     loadImage(paramSeekable);
/*     */   }
/*     */   public Interpreter(String paramString) throws IOException {
/* 745 */     this(new Seekable.File(paramString, false));
/* 746 */     this.image = paramString;
/*     */   }
/* 748 */   public Interpreter(InputStream paramInputStream) throws IOException { this(new Seekable.InputStream(paramInputStream)); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String sourceLine(int paramInt) {
/* 755 */     String str = (String)((this.sourceLineCache == null) ? null : this.sourceLineCache.get(new Integer(paramInt)));
/* 756 */     if (str != null) return str; 
/* 757 */     if (this.image == null) return null; 
/*     */     try {
/* 759 */       Process process = java.lang.Runtime.getRuntime().exec(new String[] { "mips-unknown-elf-addr2line", "-e", this.image, toHex(paramInt) });
/* 760 */       str = (new BufferedReader(new InputStreamReader(process.getInputStream()))).readLine();
/* 761 */       if (str == null) return null; 
/* 762 */       for (; str.startsWith("../"); str = str.substring(3));
/* 763 */       if (this.sourceLineCache == null) this.sourceLineCache = new HashMap(); 
/* 764 */       this.sourceLineCache.put(new Integer(paramInt), str);
/* 765 */       return str;
/* 766 */     } catch (IOException iOException) {
/* 767 */       return null;
/*     */     } 
/*     */   }
/*     */   public class DebugShutdownHook implements Runnable { private final Interpreter this$0;
/* 771 */     public DebugShutdownHook(Interpreter this$0) { this.this$0 = this$0; }
/*     */     public void run() throws Runtime.ExecutionException {
/* 773 */       int i = this.this$0.pc;
/* 774 */       if (this.this$0.getState() == 0)
/* 775 */         System.err.print("\nCPU Executing " + Runtime.toHex(i) + ": " + this.this$0.sourceLine(i) + "\n"); 
/*     */     } }
/*     */ 
/*     */   
/*     */   public static void main(String[] paramArrayOfString) throws Exception {
/* 780 */     String str = paramArrayOfString[0];
/* 781 */     Interpreter interpreter = new Interpreter(str);
/* 782 */     interpreter.getClass(); java.lang.Runtime.getRuntime().addShutdownHook(new Thread(new DebugShutdownHook(interpreter)));
/* 783 */     int i = interpreter.run(paramArrayOfString);
/* 784 */     System.err.println("Exit status: " + i);
/* 785 */     System.exit(i);
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\ibex\nestedvm\Interpreter.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       1.0.4
 */