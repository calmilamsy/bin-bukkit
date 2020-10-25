/*     */ package org.ibex.nestedvm;
/*     */ 
/*     */ import java.io.DataInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.PrintWriter;
/*     */ import java.io.Writer;
/*     */ import java.util.HashMap;
/*     */ import org.ibex.nestedvm.util.ELF;
/*     */ import org.ibex.nestedvm.util.Seekable;
/*     */ 
/*     */ public class JavaSourceCompiler
/*     */   extends Compiler {
/*  13 */   private StringBuffer runs = new StringBuffer();
/*     */   
/*  15 */   private StringBuffer inits = new StringBuffer();
/*     */   
/*  17 */   private StringBuffer classLevel = new StringBuffer();
/*     */   
/*     */   private PrintWriter out;
/*     */   
/*     */   private int indent;
/*     */   
/*  23 */   private void p() { this.out.println(); }
/*     */   
/*  25 */   private void p(String paramString) { this.out.println(indents[this.indent] + paramString); }
/*  26 */   private void pblock(StringBuffer paramStringBuffer) { this.out.print(paramStringBuffer.toString()); }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  31 */   private static String[] indents = new String[16]; private int startOfMethod; private int endOfMethod; private HashMap relativeAddrs; private boolean textDone; private int initDataCount; private boolean unreachable; static  { String str; byte b;
/*  32 */     for (str = "", b = 0; b < indents.length; ) { indents[b] = str; b++; str = str + "    "; }  }
/*     */   protected void _go() { String str2, str1; if (this.singleFloat) throw new Compiler.Exn("JavaSourceCompiler doesn't support singleFloat");  if (this.fullClassName.indexOf('.') != -1) { str1 = this.fullClassName.substring(0, this.fullClassName.lastIndexOf('.')); str2 = this.fullClassName.substring(this.fullClassName.lastIndexOf('.') + 1); } else { str2 = this.fullClassName; str1 = null; }  p("/* This file was generated from " + this.source + " by Mips2Java on " + dateTime() + " */"); if (str1 != null) p("package " + str1 + ";");  if (this.runtimeStats) p("import java.util.*;");  p(); p("public final class " + str2 + " extends " + this.runtimeClass + " {"); this.indent++; p("/* program counter */"); p("private int pc = 0;"); if (this.debugCompiler) p("private int lastPC = 0;");  p(); p("/* General Purpose registers */"); p("private final static int r0 = 0;"); p("private int      r1,  r2,  r3,  r4,  r5,  r6,  r7,"); p("            r8,  r9,  r10, r11, r12, r13, r14, r15,"); p("            r16, r17, r18, r19, r20, r21, r22, r23,"); p("            r24, r25, r26, r27, r28, r29, r30, r31,"); p("            hi = 0, lo = 0;"); p("/* FP registers */"); p("private int f0,  f1,  f2,  f3,  f4,  f5,  f6,  f7,"); p("            f8,  f9,  f10, f11, f12, f13, f14, f15,"); p("            f16, f17, f18, f19, f20, f21, f22, f23,"); p("            f24, f25, f26, f27, f28, f29, f30, f31;"); p("/* FP Control Register */"); p("private int fcsr = 0;"); p(); if (this.onePage) p("private final int[] page = readPages[0];");  int i = 0; byte b; for (b = 0; b < this.elf.sheaders.length; b++) { ELF.SHeader sHeader = this.elf.sheaders[b]; String str = sHeader.name; if (sHeader.addr != 0) { i = Math.max(i, sHeader.addr + sHeader.size); if (str.equals(".text")) { emitText(sHeader.addr, new DataInputStream(sHeader.getInputStream()), sHeader.size); } else if (str.equals(".data") || str.equals(".sdata") || str.equals(".rodata") || str.equals(".ctors") || str.equals(".dtors")) { emitData(sHeader.addr, new DataInputStream(sHeader.getInputStream()), sHeader.size, str.equals(".rodata")); } else if (str.equals(".bss") || str.equals(".sbss")) { emitBSS(sHeader.addr, sHeader.size); } else { throw new Compiler.Exn("Unknown segment: " + str); }  }  }  p(); pblock(this.classLevel); p(); p("private final void trampoline() throws ExecutionException {"); this.indent++; p("while(state == RUNNING) {"); this.indent++; p("switch(pc>>>" + this.methodShift + ") {"); this.indent++; pblock(this.runs); p("default: throw new ExecutionException(\"invalid address 0x\" + Long.toString(this.pc&0xffffffffL,16) + \": r2: \" + r2);"); this.indent--; p("}"); this.indent--; p("}"); this.indent--; p("}"); p(); p("public " + str2 + "() {"); this.indent++; p("super(" + this.pageSize + "," + this.totalPages + ");"); pblock(this.inits); this.indent--; p("}"); p(); p("protected int entryPoint() { return " + toHex(this.elf.header.entry) + "; }"); p("protected int heapStart() { return " + toHex(i) + "; }"); p("protected int gp() { return " + toHex(this.gp.addr) + "; }"); if (this.userInfo != null) { p("protected int userInfoBase() { return " + toHex(this.userInfo.addr) + "; }"); p("protected int userInfoSize() { return " + toHex(this.userInfo.size) + "; }"); }  p("public static void main(String[] args) throws Exception {"); this.indent++; p("" + str2 + " me = new " + str2 + "();"); p("int status = me.run(\"" + this.fullClassName + "\",args);"); if (this.runtimeStats) p("me.printStats();");  p("System.exit(status);"); this.indent--; p("}"); p(); p("protected void _execute() throws ExecutionException { trampoline(); }"); p(); p("protected void setCPUState(CPUState state) {"); this.indent++; for (b = 1; b < 32; ) { p("r" + b + "=state.r[" + b + "];"); b++; }  for (b = 0; b < 32; ) { p("f" + b + "=state.f[" + b + "];"); b++; }  p("hi=state.hi; lo=state.lo; fcsr=state.fcsr;"); p("pc=state.pc;"); this.indent--; p("}"); p("protected void getCPUState(CPUState state) {"); this.indent++; for (b = 1; b < 32; ) { p("state.r[" + b + "]=r" + b + ";"); b++; }  for (b = 0; b < 32; ) { p("state.f[" + b + "]=f" + b + ";"); b++; }  p("state.hi=hi; state.lo=lo; state.fcsr=fcsr;"); p("state.pc=pc;"); this.indent--; p("}"); p(); if (this.supportCall) { p("private static final " + this.hashClass + " symbols = new " + this.hashClass + "();"); p("static {"); this.indent++; ELF.Symbol[] arrayOfSymbol = (this.elf.getSymtab()).symbols; for (byte b1 = 0; b1 < arrayOfSymbol.length; b1++) { ELF.Symbol symbol = arrayOfSymbol[b1]; if (symbol.type == 2 && symbol.binding == 1 && (symbol.name.equals("_call_helper") || !symbol.name.startsWith("_")))
/*     */           p("symbols.put(\"" + symbol.name + "\",new Integer(" + toHex(symbol.addr) + "));");  }  this.indent--; p("}"); p("public int lookupSymbol(String symbol) { Integer i = (Integer) symbols.get(symbol); return i==null ? -1 : i.intValue(); }"); p(); }  if (this.runtimeStats) { p("private HashMap counters = new HashMap();"); p("private void inc(String k) { Long i = (Long)counters.get(k); counters.put(k,new Long(i==null ? 1 : i.longValue() + 1)); }"); p("private void printStats() {"); p(" Iterator i = new TreeSet(counters.keySet()).iterator();"); p(" while(i.hasNext()) { Object o = i.next(); System.err.println(\"\" + o + \": \" + counters.get(o)); }"); p("}"); p(); }  this.indent--; p("}"); }
/*  35 */   private void startMethod(int paramInt) { paramInt &= (this.maxBytesPerMethod - 1 ^ 0xFFFFFFFF); this.startOfMethod = paramInt; this.endOfMethod = paramInt + this.maxBytesPerMethod; String str = "run_" + Long.toString(paramInt & 0xFFFFFFFFL, 16); this.runs.append(indents[4] + "case " + toHex(paramInt >>> this.methodShift) + ": " + str + "(); break; \n"); p("private final void " + str + "() throws ExecutionException { /" + "* " + toHex(paramInt) + " - " + toHex(this.endOfMethod) + " *" + "/"); this.indent++; p("int addr, tmp;"); p("for(;;) {"); this.indent++; p("switch(pc) {"); this.indent++; } private void endMethod() { endMethod(this.endOfMethod); } public JavaSourceCompiler(Seekable paramSeekable, String paramString, Writer paramWriter) throws IOException { super(paramSeekable, paramString);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 202 */     this.startOfMethod = 0;
/* 203 */     this.endOfMethod = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 246 */     this.relativeAddrs = new HashMap();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 310 */     this.initDataCount = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 351 */     this.unreachable = false; this.out = new PrintWriter(paramWriter); }
/*     */   private void endMethod(int paramInt) { if (this.startOfMethod == 0)
/*     */       return;  p("case " + toHex(paramInt) + ":"); this.indent++; p("pc=" + constant(paramInt) + ";"); leaveMethod(); this.indent--; if (this.debugCompiler) { p("default: throw new ExecutionException(\"invalid address 0x\" + Long.toString(pc&0xffffffffL,16)  + \" (got here from 0x\" + Long.toString(lastPC&0xffffffffL,16)+\")\");"); } else { p("default: throw new ExecutionException(\"invalid address 0x\" + Long.toString(pc&0xffffffffL,16));"); }  this.indent--; p("}"); p("/* NOT REACHED */"); this.indent--; p("}"); this.indent--; p("}"); this.endOfMethod = this.startOfMethod = 0; }
/* 354 */   private String constant(int paramInt) { if (paramInt >= 4096 && this.lessConstants) { int i = paramInt & 0xFFFFFC00; String str = "N_" + toHex8(i); if (this.relativeAddrs.get(new Integer(i)) == null) { this.relativeAddrs.put(new Integer(i), Boolean.TRUE); this.classLevel.append(indents[1] + "private static int " + str + " = " + toHex(i) + ";\n"); }  return "(" + str + " + " + toHex(paramInt - i) + ")"; }  return toHex(paramInt); } private void emitInstruction(int paramInt1, int paramInt2, int paramInt3) throws IOException, Compiler.Exn { int i10, i9; if (paramInt2 == -1) throw new Error("insn is -1");
/*     */     
/* 356 */     int i = paramInt2 >>> 26 & 0xFF;
/* 357 */     int j = paramInt2 >>> 21 & 0x1F;
/* 358 */     int k = paramInt2 >>> 16 & 0x1F;
/* 359 */     int m = paramInt2 >>> 16 & 0x1F;
/* 360 */     int n = paramInt2 >>> 11 & 0x1F;
/* 361 */     int i1 = paramInt2 >>> 11 & 0x1F;
/* 362 */     int i2 = paramInt2 >>> 6 & 0x1F;
/* 363 */     int i3 = paramInt2 >>> 6 & 0x1F;
/* 364 */     int i4 = paramInt2 & 0x3F;
/*     */     
/* 366 */     int i5 = paramInt2 & 0x3FFFFFF;
/* 367 */     int i6 = paramInt2 & 0xFFFF;
/* 368 */     int i7 = paramInt2 << 16 >> 16;
/* 369 */     int i8 = i7;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 375 */     if (paramInt1 == -1) p("/* Next insn is delay slot */ ");
/*     */     
/* 377 */     if (this.runtimeStats && i != 0) p("inc(\"opcode: " + i + "\");"); 
/* 378 */     switch (i) {
/*     */       case 0:
/* 380 */         if (this.runtimeStats && paramInt2 != 0) p("inc(\"opcode: 0/" + i4 + "\");"); 
/* 381 */         switch (i4) {
/*     */           case 0:
/* 383 */             if (paramInt2 != 0)
/* 384 */               p("r" + n + " = r" + k + " << " + i2 + ";"); 
/*     */             return;
/*     */           case 2:
/* 387 */             p("r" + n + " = r" + k + " >>> " + i2 + ";");
/*     */             return;
/*     */           case 3:
/* 390 */             p("r" + n + " = r" + k + " >> " + i2 + ";");
/*     */             return;
/*     */           case 4:
/* 393 */             p("r" + n + " = r" + k + " << (r" + j + "&0x1f);");
/*     */             return;
/*     */           case 6:
/* 396 */             p("r" + n + " = r" + k + " >>> (r" + j + "&0x1f);");
/*     */             return;
/*     */           case 7:
/* 399 */             p("r" + n + " = r" + k + " >> (r" + j + "&0x1f);");
/*     */             return;
/*     */           case 8:
/* 402 */             if (paramInt1 == -1) throw new Error("pc modifying insn in delay slot"); 
/* 403 */             emitInstruction(-1, paramInt3, -1);
/* 404 */             if (this.debugCompiler) p("lastPC = " + toHex(paramInt1) + ";"); 
/* 405 */             p("pc=r" + j + ";");
/* 406 */             leaveMethod();
/* 407 */             this.unreachable = true;
/*     */             return;
/*     */           case 9:
/* 410 */             if (paramInt1 == -1) throw new Error("pc modifying insn in delay slot"); 
/* 411 */             emitInstruction(-1, paramInt3, -1);
/* 412 */             if (this.debugCompiler) p("lastPC = " + toHex(paramInt1) + ";"); 
/* 413 */             p("pc=r" + j + ";");
/* 414 */             p("r31=" + constant(paramInt1 + 8) + ";");
/* 415 */             leaveMethod();
/* 416 */             this.unreachable = true;
/*     */             return;
/*     */           case 12:
/* 419 */             p("pc = " + toHex(paramInt1) + ";");
/* 420 */             p("r2 = syscall(r2,r4,r5,r6,r7,r8,r9);");
/* 421 */             p("if (state != RUNNING) {");
/* 422 */             this.indent++;
/* 423 */             p("pc = " + toHex(paramInt1 + 4) + ";");
/* 424 */             leaveMethod();
/* 425 */             this.indent--;
/* 426 */             p("}");
/*     */             return;
/*     */           case 13:
/* 429 */             p("throw new ExecutionException(\"Break\");");
/* 430 */             this.unreachable = true;
/*     */             return;
/*     */           case 16:
/* 433 */             p("r" + n + " = hi;");
/*     */             return;
/*     */           case 17:
/* 436 */             p("hi = r" + j + ";");
/*     */             return;
/*     */           case 18:
/* 439 */             p("r" + n + " = lo;");
/*     */             return;
/*     */           case 19:
/* 442 */             p("lo = r" + j + ";");
/*     */             return;
/*     */           case 24:
/* 445 */             p("{ long hilo = (long)(r" + j + ") * ((long)r" + k + "); " + "hi = (int) (hilo >>> 32); " + "lo = (int) hilo; }");
/*     */             return;
/*     */ 
/*     */           
/*     */           case 25:
/* 450 */             p("{ long hilo = (r" + j + " & 0xffffffffL) * (r" + k + " & 0xffffffffL); " + "hi = (int) (hilo >>> 32); " + "lo = (int) hilo; } ");
/*     */             return;
/*     */ 
/*     */           
/*     */           case 26:
/* 455 */             p("hi = r" + j + "%r" + k + "; lo = r" + j + "/r" + k + ";");
/*     */             return;
/*     */           case 27:
/* 458 */             p("if(r" + k + "!=0) {");
/* 459 */             p("hi = (int)((r" + j + " & 0xffffffffL) % (r" + k + " & 0xffffffffL)); " + "lo = (int)((r" + j + " & 0xffffffffL) / (r" + k + " & 0xffffffffL));");
/*     */             
/* 461 */             p("}");
/*     */             return;
/*     */           case 32:
/* 464 */             throw new Compiler.Exn("ADD (add with oveflow trap) not suported");
/*     */ 
/*     */ 
/*     */           
/*     */           case 33:
/* 469 */             p("r" + n + " = r" + j + " + r" + k + ";");
/*     */             return;
/*     */           case 34:
/* 472 */             throw new Compiler.Exn("SUB (add with oveflow trap) not suported");
/*     */ 
/*     */ 
/*     */           
/*     */           case 35:
/* 477 */             p("r" + n + " = r" + j + " - r" + k + ";");
/*     */             return;
/*     */           case 36:
/* 480 */             p("r" + n + " = r" + j + " & r" + k + ";");
/*     */             return;
/*     */           case 37:
/* 483 */             p("r" + n + " = r" + j + " | r" + k + ";");
/*     */             return;
/*     */           case 38:
/* 486 */             p("r" + n + " = r" + j + " ^ r" + k + ";");
/*     */             return;
/*     */           case 39:
/* 489 */             p("r" + n + " = ~(r" + j + " | r" + k + ");");
/*     */             return;
/*     */           case 42:
/* 492 */             p("r" + n + " = r" + j + " < r" + k + " ? 1 : 0;");
/*     */             return;
/*     */           case 43:
/* 495 */             p("r" + n + " = ((r" + j + " & 0xffffffffL) < (r" + k + " & 0xffffffffL)) ? 1 : 0;");
/*     */             return;
/*     */         } 
/* 498 */         throw new RuntimeException("Illegal instruction 0/" + i4);
/*     */ 
/*     */ 
/*     */       
/*     */       case 1:
/* 503 */         switch (k) {
/*     */           case 0:
/* 505 */             if (paramInt1 == -1) throw new Error("pc modifying insn in delay slot"); 
/* 506 */             p("if(r" + j + " < 0) {");
/* 507 */             this.indent++;
/* 508 */             emitInstruction(-1, paramInt3, -1);
/* 509 */             branch(paramInt1, paramInt1 + i8 * 4 + 4);
/* 510 */             this.indent--;
/* 511 */             p("}");
/*     */             return;
/*     */           case 1:
/* 514 */             if (paramInt1 == -1) throw new Error("pc modifying insn in delay slot"); 
/* 515 */             p("if(r" + j + " >= 0) {");
/* 516 */             this.indent++;
/* 517 */             emitInstruction(-1, paramInt3, -1);
/* 518 */             branch(paramInt1, paramInt1 + i8 * 4 + 4);
/* 519 */             this.indent--;
/* 520 */             p("}");
/*     */             return;
/*     */           case 16:
/* 523 */             if (paramInt1 == -1) throw new Error("pc modifying insn in delay slot"); 
/* 524 */             p("if(r" + j + " < 0) {");
/* 525 */             this.indent++;
/* 526 */             emitInstruction(-1, paramInt3, -1);
/* 527 */             p("r31=" + constant(paramInt1 + 8) + ";");
/* 528 */             branch(paramInt1, paramInt1 + i8 * 4 + 4);
/* 529 */             this.indent--;
/* 530 */             p("}");
/*     */             return;
/*     */           case 17:
/* 533 */             if (paramInt1 == -1) throw new Error("pc modifying insn in delay slot"); 
/* 534 */             p("if(r" + j + " >= 0) {");
/* 535 */             this.indent++;
/* 536 */             emitInstruction(-1, paramInt3, -1);
/* 537 */             p("r31=" + constant(paramInt1 + 8) + ";");
/* 538 */             branch(paramInt1, paramInt1 + i8 * 4 + 4);
/* 539 */             this.indent--;
/* 540 */             p("}");
/*     */             return;
/*     */         } 
/* 543 */         throw new RuntimeException("Illegal Instruction 1/" + k);
/*     */ 
/*     */ 
/*     */       
/*     */       case 2:
/* 548 */         if (paramInt1 == -1) throw new Error("pc modifying insn in delay slot"); 
/* 549 */         emitInstruction(-1, paramInt3, -1);
/* 550 */         branch(paramInt1, paramInt1 & 0xF0000000 | i5 << 2);
/* 551 */         this.unreachable = true;
/*     */         return;
/*     */       
/*     */       case 3:
/* 555 */         if (paramInt1 == -1) throw new Error("pc modifying insn in delay slot"); 
/* 556 */         i10 = paramInt1 & 0xF0000000 | i5 << 2;
/* 557 */         emitInstruction(-1, paramInt3, -1);
/* 558 */         p("r31=" + constant(paramInt1 + 8) + ";");
/* 559 */         branch(paramInt1, i10);
/* 560 */         this.unreachable = true;
/*     */         return;
/*     */       
/*     */       case 4:
/* 564 */         if (paramInt1 == -1) throw new Error("pc modifying insn in delay slot"); 
/* 565 */         p("if(r" + j + " == r" + k + ") {");
/* 566 */         this.indent++;
/* 567 */         emitInstruction(-1, paramInt3, -1);
/* 568 */         branch(paramInt1, paramInt1 + i8 * 4 + 4);
/* 569 */         this.indent--;
/* 570 */         p("}");
/*     */         return;
/*     */       case 5:
/* 573 */         if (paramInt1 == -1) throw new Error("pc modifying insn in delay slot"); 
/* 574 */         p("if(r" + j + " != r" + k + ") {");
/* 575 */         this.indent++;
/* 576 */         emitInstruction(-1, paramInt3, -1);
/* 577 */         branch(paramInt1, paramInt1 + i8 * 4 + 4);
/* 578 */         this.indent--;
/* 579 */         p("}");
/*     */         return;
/*     */       case 6:
/* 582 */         if (paramInt1 == -1) throw new Error("pc modifying insn in delay slot"); 
/* 583 */         p("if(r" + j + " <= 0) {");
/* 584 */         this.indent++;
/* 585 */         emitInstruction(-1, paramInt3, -1);
/* 586 */         branch(paramInt1, paramInt1 + i8 * 4 + 4);
/* 587 */         this.indent--;
/* 588 */         p("}");
/*     */         return;
/*     */       case 7:
/* 591 */         if (paramInt1 == -1) throw new Error("pc modifying insn in delay slot"); 
/* 592 */         p("if(r" + j + " > 0) {");
/* 593 */         this.indent++;
/* 594 */         emitInstruction(-1, paramInt3, -1);
/* 595 */         branch(paramInt1, paramInt1 + i8 * 4 + 4);
/* 596 */         this.indent--;
/* 597 */         p("}");
/*     */         return;
/*     */       case 8:
/* 600 */         p("r" + k + " = r" + j + " + " + i7 + ";");
/*     */         return;
/*     */       case 9:
/* 603 */         p("r" + k + " = r" + j + " + " + i7 + ";");
/*     */         return;
/*     */       case 10:
/* 606 */         p("r" + k + " = r" + j + " < " + i7 + " ? 1 : 0;");
/*     */         return;
/*     */       case 11:
/* 609 */         p("r" + k + " = (r" + j + "&0xffffffffL) < (" + i7 + "&0xffffffffL) ? 1 : 0;");
/*     */         return;
/*     */       case 12:
/* 612 */         p("r" + k + " = r" + j + " & " + i6 + ";");
/*     */         return;
/*     */       case 13:
/* 615 */         p("r" + k + " = r" + j + " | " + i6 + ";");
/*     */         return;
/*     */       case 14:
/* 618 */         p("r" + k + " = r" + j + " ^ " + i6 + ";");
/*     */         return;
/*     */       case 15:
/* 621 */         p("r" + k + " = " + i6 + " << 16;");
/*     */         return;
/*     */       case 16:
/* 624 */         throw new Compiler.Exn("TLB/Exception support not implemented");
/*     */       case 17:
/* 626 */         switch (j) {
/*     */           case 0:
/* 628 */             p("r" + k + " = f" + n + ";");
/*     */             return;
/*     */           case 2:
/* 631 */             if (i1 != 31) throw new Compiler.Exn("FCR " + i1 + " unavailable"); 
/* 632 */             p("r" + k + " = fcsr;");
/*     */             return;
/*     */           case 4:
/* 635 */             p("f" + n + " = r" + k + ";");
/*     */             return;
/*     */           case 6:
/* 638 */             if (i1 != 31) throw new Compiler.Exn("FCR " + i1 + " unavailable"); 
/* 639 */             p("fcsr = r" + k + ";");
/*     */             return;
/*     */           case 8:
/* 642 */             i9 = paramInt2 >>> 16 & true;
/* 643 */             p("if(((fcsr&0x800000)!=0) == (" + i9 + "!=0)) {");
/* 644 */             this.indent++;
/* 645 */             emitInstruction(-1, paramInt3, -1);
/* 646 */             branch(paramInt1, paramInt1 + i8 * 4 + 4);
/* 647 */             this.indent--;
/* 648 */             p("}");
/*     */             return;
/*     */           
/*     */           case 16:
/* 652 */             switch (i4) {
/*     */               case 0:
/* 654 */                 p(setFloat(i3, getFloat(i1) + "+" + getFloat(m)));
/*     */                 return;
/*     */               case 1:
/* 657 */                 p(setFloat(i3, getFloat(i1) + "-" + getFloat(m)));
/*     */                 return;
/*     */               case 2:
/* 660 */                 p(setFloat(i3, getFloat(i1) + "*" + getFloat(m)));
/*     */                 return;
/*     */               case 3:
/* 663 */                 p(setFloat(i3, getFloat(i1) + "/" + getFloat(m)));
/*     */                 return;
/*     */               case 5:
/* 666 */                 p(setFloat(i3, "Math.abs(" + getFloat(i1) + ")"));
/*     */                 return;
/*     */               case 6:
/* 669 */                 p("f" + i3 + " = f" + i1 + "; // MOV.S");
/*     */                 return;
/*     */               case 7:
/* 672 */                 p(setFloat(i3, "-" + getFloat(i1)));
/*     */                 return;
/*     */               case 33:
/* 675 */                 p(setDouble(i3, "(float)" + getFloat(i1)));
/*     */                 return;
/*     */               case 36:
/* 678 */                 p("switch(fcsr & 3) {");
/* 679 */                 this.indent++;
/* 680 */                 p("case 0: f" + i3 + " = (int)Math.floor(" + getFloat(i1) + "+0.5); break; // Round to nearest");
/* 681 */                 p("case 1: f" + i3 + " = (int)" + getFloat(i1) + "; break; // Round towards zero");
/* 682 */                 p("case 2: f" + i3 + " = (int)Math.ceil(" + getFloat(i1) + "); break; // Round towards plus infinity");
/* 683 */                 p("case 3: f" + i3 + " = (int)Math.floor(" + getFloat(i1) + "); break; // Round towards minus infinity");
/* 684 */                 this.indent--;
/* 685 */                 p("}");
/*     */                 return;
/*     */               case 50:
/* 688 */                 p("fcsr = (fcsr&~0x800000) | ((" + getFloat(i1) + "==" + getFloat(m) + ") ? 0x800000 : 0x000000);");
/*     */                 return;
/*     */               case 60:
/* 691 */                 p("fcsr = (fcsr&~0x800000) | ((" + getFloat(i1) + "<" + getFloat(m) + ") ? 0x800000 : 0x000000);");
/*     */                 return;
/*     */               case 62:
/* 694 */                 p("fcsr = (fcsr&~0x800000) | ((" + getFloat(i1) + "<=" + getFloat(m) + ") ? 0x800000 : 0x000000);"); return;
/*     */             } 
/* 696 */             throw new Compiler.Exn("Invalid Instruction 17/" + j + "/" + i4);
/*     */ 
/*     */ 
/*     */           
/*     */           case 17:
/* 701 */             switch (i4) {
/*     */               case 0:
/* 703 */                 p(setDouble(i3, getDouble(i1) + "+" + getDouble(m)));
/*     */                 return;
/*     */               case 1:
/* 706 */                 p(setDouble(i3, getDouble(i1) + "-" + getDouble(m)));
/*     */                 return;
/*     */               case 2:
/* 709 */                 p(setDouble(i3, getDouble(i1) + "*" + getDouble(m)));
/*     */                 return;
/*     */               case 3:
/* 712 */                 p(setDouble(i3, getDouble(i1) + "/" + getDouble(m)));
/*     */                 return;
/*     */               case 5:
/* 715 */                 p(setDouble(i3, "Math.abs(" + getDouble(i1) + ")"));
/*     */                 return;
/*     */               case 6:
/* 718 */                 p("f" + i3 + " = f" + i1 + ";");
/* 719 */                 p("f" + (i3 + 1) + " = f" + (i1 + 1) + ";");
/*     */                 return;
/*     */               case 7:
/* 722 */                 p(setDouble(i3, "-" + getDouble(i1)));
/*     */                 return;
/*     */               case 32:
/* 725 */                 p(setFloat(i3, "(float)" + getDouble(i1)));
/*     */                 return;
/*     */               case 36:
/* 728 */                 p("switch(fcsr & 3) {");
/* 729 */                 this.indent++;
/* 730 */                 p("case 0: f" + i3 + " = (int)Math.floor(" + getDouble(i1) + "+0.5); break; // Round to nearest");
/* 731 */                 p("case 1: f" + i3 + " = (int)" + getDouble(i1) + "; break; // Round towards zero");
/* 732 */                 p("case 2: f" + i3 + " = (int)Math.ceil(" + getDouble(i1) + "); break; // Round towards plus infinity");
/* 733 */                 p("case 3: f" + i3 + " = (int)Math.floor(" + getDouble(i1) + "); break; // Round towards minus infinity");
/* 734 */                 this.indent--;
/* 735 */                 p("}");
/*     */                 return;
/*     */               case 50:
/* 738 */                 p("fcsr = (fcsr&~0x800000) | ((" + getDouble(i1) + "==" + getDouble(m) + ") ? 0x800000 : 0x000000);");
/*     */                 return;
/*     */               case 60:
/* 741 */                 p("fcsr = (fcsr&~0x800000) | ((" + getDouble(i1) + "<" + getDouble(m) + ") ? 0x800000 : 0x000000);");
/*     */                 return;
/*     */               case 62:
/* 744 */                 p("fcsr = (fcsr&~0x800000) | ((" + getDouble(i1) + "<=" + getDouble(m) + ") ? 0x800000 : 0x000000);"); return;
/*     */             } 
/* 746 */             throw new Compiler.Exn("Invalid Instruction 17/" + j + "/" + i4);
/*     */ 
/*     */ 
/*     */           
/*     */           case 20:
/* 751 */             switch (i4) {
/*     */               case 32:
/* 753 */                 p(" // CVS.S.W");
/* 754 */                 p(setFloat(i3, "((float)f" + i1 + ")"));
/*     */                 return;
/*     */               case 33:
/* 757 */                 p(setDouble(i3, "((double)f" + i1 + ")")); return;
/*     */             } 
/* 759 */             throw new Compiler.Exn("Invalid Instruction 17/" + j + "/" + i4);
/*     */         } 
/*     */ 
/*     */ 
/*     */         
/* 764 */         throw new Compiler.Exn("Invalid Instruction 17/" + j);
/*     */ 
/*     */       
/*     */       case 18:
/*     */       case 19:
/* 769 */         throw new Compiler.Exn("coprocessor 2 and 3 instructions not available");
/*     */       case 32:
/* 771 */         if (this.runtimeStats) p("inc(\"LB\");"); 
/* 772 */         p("addr=r" + j + "+" + i7 + ";");
/* 773 */         memRead("addr", "tmp");
/* 774 */         p("tmp = (tmp>>>(((~addr)&3)<<3)) & 0xff;");
/* 775 */         p("if((tmp&0x80)!=0) tmp |= 0xffffff00; /* sign extend */");
/* 776 */         p("r" + k + " = tmp;");
/*     */         return;
/*     */       
/*     */       case 33:
/* 780 */         if (this.runtimeStats) p("inc(\"LH\");"); 
/* 781 */         p("addr=r" + j + "+" + i7 + ";");
/* 782 */         memRead("addr", "tmp");
/* 783 */         p("tmp = (tmp>>>(((~addr)&2)<<3)) & 0xffff;");
/* 784 */         p("if((tmp&0x8000)!=0) tmp |= 0xffff0000; /* sign extend */");
/* 785 */         p("r" + k + " = tmp;");
/*     */         return;
/*     */       
/*     */       case 34:
/* 789 */         p("addr=r" + j + "+" + i7 + ";");
/* 790 */         memRead("addr", "tmp");
/* 791 */         p("r" + k + " = (r" + k + "&(0x00ffffff>>>(((~addr)&3)<<3)))|(tmp<<((addr&3)<<3));");
/*     */         return;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       case 35:
/* 806 */         if (this.runtimeStats) p("inc(\"LW\");"); 
/* 807 */         memRead("r" + j + "+" + i7, "r" + k);
/*     */         return;
/*     */       case 36:
/* 810 */         p("addr=r" + j + "+" + i7 + ";");
/* 811 */         memRead("addr", "tmp");
/* 812 */         p("tmp = (tmp>>>(((~addr)&3)<<3)) & 0xff;");
/* 813 */         p("r" + k + " = tmp;");
/*     */         return;
/*     */       
/*     */       case 37:
/* 817 */         p("addr=r" + j + "+" + i7 + ";");
/* 818 */         memRead("addr", "tmp");
/* 819 */         p("tmp = (tmp>>>(((~addr)&2)<<3)) & 0xffff;");
/* 820 */         p("r" + k + " = tmp;");
/*     */         return;
/*     */       
/*     */       case 38:
/* 824 */         p("addr=r" + j + "+" + i7 + ";");
/* 825 */         memRead("addr", "tmp");
/* 826 */         p("r" + k + " = (r" + k + "&(0xffffff00<<((addr&3)<<3)))|(tmp>>>(((~addr)&3)<<3));");
/*     */         return;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       case 40:
/* 843 */         if (this.runtimeStats) p("inc(\"SB\");"); 
/* 844 */         p("addr=r" + j + "+" + i7 + ";");
/* 845 */         memRead("addr", "tmp");
/* 846 */         p("tmp = (tmp&~(0xff000000>>>((addr&3)<<3)))|((r" + k + "&0xff)<<(((~addr)&3)<<3));");
/* 847 */         memWrite("addr", "tmp");
/*     */         return;
/*     */       
/*     */       case 41:
/* 851 */         if (this.runtimeStats) p("inc(\"SH\");"); 
/* 852 */         p("addr=r" + j + "+" + i7 + ";");
/* 853 */         memRead("addr", "tmp");
/* 854 */         p("tmp = (tmp&(0xffff<<((addr&2)<<3)))|((r" + k + "&0xffff)<<(((~addr)&2)<<3));");
/* 855 */         memWrite("addr", "tmp");
/*     */         return;
/*     */       
/*     */       case 42:
/* 859 */         p(" // SWL");
/* 860 */         p("addr=r" + j + "+" + i7 + ";");
/* 861 */         memRead("addr", "tmp");
/* 862 */         p("tmp = (tmp&(0xffffff00<<(((~addr)&3)<<3)))|(r" + k + ">>>((addr&3)<<3));");
/* 863 */         memWrite("addr", "tmp");
/*     */         return;
/*     */       
/*     */       case 43:
/* 867 */         if (this.runtimeStats) p("inc(\"SW\");"); 
/* 868 */         memWrite("r" + j + "+" + i7, "r" + k);
/*     */         return;
/*     */       case 46:
/* 871 */         p(" // SWR");
/* 872 */         p("addr=r" + j + "+" + i7 + ";");
/* 873 */         memRead("addr", "tmp");
/* 874 */         p("tmp = (tmp&(0x00ffffff>>>((addr&3)<<3)))|(r" + k + "<<(((~addr)&3)<<3));");
/* 875 */         memWrite("addr", "tmp");
/*     */         return;
/*     */ 
/*     */       
/*     */       case 48:
/* 880 */         memRead("r" + j + "+" + i7, "r" + k);
/*     */         return;
/*     */       case 49:
/* 883 */         memRead("r" + j + "+" + i7, "f" + k);
/*     */         return;
/*     */       
/*     */       case 56:
/* 887 */         memWrite("r" + j + "+" + i7, "r" + k);
/* 888 */         p("r" + k + "=1;");
/*     */         return;
/*     */       case 57:
/* 891 */         memWrite("r" + j + "+" + i7, "f" + k);
/*     */         return;
/*     */     } 
/* 894 */     throw new Compiler.Exn("Invalid Instruction: " + i + " at " + toHex(paramInt1)); } private void branch(int paramInt1, int paramInt2) { if (this.debugCompiler) p("lastPC = " + toHex(paramInt1) + ";");  p("pc=" + constant(paramInt2) + ";"); if (paramInt2 == 0) { p("throw new ExecutionException(\"Branch to addr 0x0\");"); } else if ((paramInt1 & this.methodMask) == (paramInt2 & this.methodMask)) { p("continue;"); } else if (this.assumeTailCalls) { p("run_" + Long.toString((paramInt2 & this.methodMask) & 0xFFFFFFFFL, 16) + "(); return;"); } else { leaveMethod(); }  } private void leaveMethod() { p("return;"); }
/*     */   private void emitText(int paramInt1, DataInputStream paramDataInputStream, int paramInt2) throws Compiler.Exn, IOException { if (this.textDone) throw new Compiler.Exn("Multiple text segments");  this.textDone = true; if ((paramInt1 & 0x3) != 0 || (paramInt2 & 0x3) != 0) throw new Compiler.Exn("Section on weird boundaries");  int i = paramInt2 / 4; int j = paramDataInputStream.readInt(); if (j == -1) throw new Error("Actually read -1 at " + toHex(paramInt1));  for (byte b = 0; b < i; b++, paramInt1 += 4) { int k = j; j = (b == i - 1) ? -1 : paramDataInputStream.readInt(); if (paramInt1 >= this.endOfMethod) { endMethod(); startMethod(paramInt1); }  if (this.jumpableAddresses == null || paramInt1 == this.startOfMethod || this.jumpableAddresses.get(new Integer(paramInt1)) != null) { p("case " + toHex(paramInt1) + ":"); this.unreachable = false; } else { if (this.unreachable) continue;  if (this.debugCompiler)
/*     */           p("/* pc = " + toHex(paramInt1) + "*" + "/");  }  this.indent++; emitInstruction(paramInt1, k, j); this.indent--; }  endMethod(paramInt1); p(); paramDataInputStream.close(); }
/*     */   private void emitData(int paramInt1, DataInputStream paramDataInputStream, int paramInt2, boolean paramBoolean) throws Compiler.Exn, IOException { if ((paramInt1 & 0x3) != 0 || (paramInt2 & 0x3) != 0)
/*     */       throw new Compiler.Exn("Data section on weird boundaries");  int i = paramInt1 + paramInt2; while (paramInt1 < i) { int j = Math.min(paramInt2, 28000); StringBuffer stringBuffer = new StringBuffer(); for (byte b = 0; b < j; b += 7) { long l = 0L; byte b1; for (b1 = 0; b1 < 7; b1++) { l <<= 8; byte b2 = (b + b1 < paramInt2) ? paramDataInputStream.readByte() : 1; l |= b2 & 0xFFL; }  for (b1 = 0; b1 < 8; b1++) { char c = (char)(int)(l >>> 7 * (7 - b1) & 0x7FL); if (c == '\n') { stringBuffer.append("\\n"); } else if (c == '\r') { stringBuffer.append("\\r"); } else if (c == '\\') { stringBuffer.append("\\\\"); } else if (c == '"') { stringBuffer.append("\\\""); } else if (c >= ' ' && c <= '~') { stringBuffer.append(c); } else { stringBuffer.append("\\" + toOctal3(c)); }  }  }  String str = "_data" + ++this.initDataCount; p("private static final int[] " + str + " = decodeData(\"" + stringBuffer.toString() + "\"," + toHex(j / 4) + ");"); this.inits.append(indents[2] + "initPages(" + str + "," + toHex(paramInt1) + "," + (paramBoolean ? "true" : "false") + ");\n"); paramInt1 += j; paramInt2 -= j; }  paramDataInputStream.close(); }
/*     */   private void emitBSS(int paramInt1, int paramInt2) { if ((paramInt1 & 0x3) != 0)
/*     */       throw new Compiler.Exn("BSS section on weird boundaries");  paramInt2 = paramInt2 + 3 & 0xFFFFFFFC; int i = paramInt2 / 4; this.inits.append(indents[2] + "clearPages(" + toHex(paramInt1) + "," + toHex(i) + ");\n"); }
/* 901 */   private void memWrite(String paramString1, String paramString2) { if (this.nullPointerCheck) p("nullPointerCheck(" + paramString1 + ");"); 
/* 902 */     if (this.onePage) {
/* 903 */       p("page[(" + paramString1 + ")>>>2] = " + paramString2 + ";");
/* 904 */     } else if (this.fastMem) {
/* 905 */       p("writePages[(" + paramString1 + ")>>>" + this.pageShift + "][((" + paramString1 + ")>>>2)&" + toHex((this.pageSize >> 2) - 1) + "] = " + paramString2 + ";");
/*     */     } else {
/* 907 */       p("unsafeMemWrite(" + paramString1 + "," + paramString2 + ");");
/*     */     }  }
/*     */    private void memRead(String paramString1, String paramString2) {
/* 910 */     if (this.nullPointerCheck) p("nullPointerCheck(" + paramString1 + ");"); 
/* 911 */     if (this.onePage) {
/* 912 */       p(paramString2 + "= page[(" + paramString1 + ")>>>2];");
/* 913 */     } else if (this.fastMem) {
/* 914 */       p(paramString2 + " = readPages[(" + paramString1 + ")>>>" + this.pageShift + "][((" + paramString1 + ")>>>2)&" + toHex((this.pageSize >> 2) - 1) + "];");
/*     */     } else {
/* 916 */       p(paramString2 + " = unsafeMemRead(" + paramString1 + ");");
/*     */     } 
/* 918 */   } private static String getFloat(int paramInt) { return "(Float.intBitsToFloat(f" + paramInt + "))"; }
/*     */   
/* 920 */   private static String getDouble(int paramInt) { return "(Double.longBitsToDouble(((f" + (paramInt + 1) + "&0xffffffffL) << 32) | (f" + paramInt + "&0xffffffffL)))"; }
/*     */   
/* 922 */   private static String setFloat(int paramInt, String paramString) { return "f" + paramInt + "=Float.floatToRawIntBits(" + paramString + ");"; }
/*     */   
/* 924 */   private static String setDouble(int paramInt, String paramString) { return "{ long l = Double.doubleToLongBits(" + paramString + "); " + "f" + (paramInt + 1) + " = (int)(l >>> 32); f" + paramInt + " = (int)l; }"; }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\ibex\nestedvm\JavaSourceCompiler.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       1.0.4
 */