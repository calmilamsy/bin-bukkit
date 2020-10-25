/*     */ package org.ibex.nestedvm;
/*     */ import java.util.Hashtable;
/*     */ import org.ibex.nestedvm.util.ELF;
/*     */ 
/*     */ public abstract class Compiler implements Registers {
/*     */   ELF elf;
/*     */   final String fullClassName;
/*     */   String source;
/*     */   boolean fastMem;
/*     */   int maxInsnPerMethod;
/*     */   int maxBytesPerMethod;
/*     */   int methodMask;
/*     */   int methodShift;
/*     */   boolean pruneCases;
/*     */   boolean assumeTailCalls;
/*     */   boolean debugCompiler;
/*     */   boolean printStats;
/*     */   boolean runtimeStats;
/*     */   boolean supportCall;
/*     */   
/*  21 */   public void setSource(String paramString) { this.source = paramString; }
/*     */   boolean nullPointerCheck; String runtimeClass; String hashClass; boolean unixRuntime; boolean lessConstants; boolean singleFloat; int pageSize; int totalPages; int pageShift; boolean onePage; Hashtable jumpableAddresses; ELF.Symbol userInfo; ELF.Symbol gp; private boolean used;
/*     */   
/*  24 */   static class Exn extends Exception { public Exn(String param1String) { super(param1String); } }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void maxInsnPerMethodInit() throws Exn {
/*  42 */     if ((this.maxInsnPerMethod & this.maxInsnPerMethod - 1) != 0) throw new Exn("maxBytesPerMethod is not a power of two"); 
/*  43 */     this.maxBytesPerMethod = this.maxInsnPerMethod * 4;
/*  44 */     this.methodMask = this.maxBytesPerMethod - 1 ^ 0xFFFFFFFF;
/*  45 */     for (; this.maxBytesPerMethod >>> this.methodShift != 1; this.methodShift++);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void pageSizeInit() throws Exn {
/*  82 */     if ((this.pageSize & this.pageSize - 1) != 0) throw new Exn("pageSize not a multiple of two"); 
/*  83 */     if ((this.totalPages & this.totalPages - 1) != 0) throw new Exn("totalPages not a multiple of two"); 
/*  84 */     for (; this.pageSize >>> this.pageShift != 1; this.pageShift++);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void usage() throws Exn {
/*  94 */     System.err.println("Usage: java Compiler [-outfile output.java] [-o options] [-dumpoptions] <classname> <binary.mips>");
/*  95 */     System.err.println("-o takes mount(8) like options and can be specified multiple times");
/*  96 */     System.err.println("Available options:");
/*  97 */     for (boolean bool = false; bool < options.length; bool += true)
/*  98 */       System.err.print(options[bool] + ": " + wrapAndIndent(options[bool + true], 16 - options[bool].length(), 18, 62)); 
/*  99 */     System.exit(1);
/*     */   }
/*     */   
/*     */   public static void main(String[] paramArrayOfString) throws IOException {
/* 103 */     String str1 = null;
/* 104 */     String str2 = null;
/* 105 */     String str3 = null;
/* 106 */     String str4 = null;
/* 107 */     String str5 = null;
/* 108 */     String str6 = null;
/* 109 */     boolean bool = false;
/* 110 */     int i = 0;
/* 111 */     while (paramArrayOfString.length - i > 0) {
/* 112 */       if (paramArrayOfString[i].equals("-outfile")) {
/* 113 */         i++;
/* 114 */         if (i == paramArrayOfString.length) usage(); 
/* 115 */         str1 = paramArrayOfString[i];
/* 116 */       } else if (paramArrayOfString[i].equals("-d")) {
/* 117 */         i++;
/* 118 */         if (i == paramArrayOfString.length) usage(); 
/* 119 */         str2 = paramArrayOfString[i];
/* 120 */       } else if (paramArrayOfString[i].equals("-outformat")) {
/* 121 */         i++;
/* 122 */         if (i == paramArrayOfString.length) usage(); 
/* 123 */         str6 = paramArrayOfString[i];
/* 124 */       } else if (paramArrayOfString[i].equals("-o")) {
/* 125 */         i++;
/* 126 */         if (i == paramArrayOfString.length) usage(); 
/* 127 */         if (str3 == null || str3.length() == 0)
/* 128 */         { str3 = paramArrayOfString[i]; }
/* 129 */         else if (paramArrayOfString[i].length() != 0)
/* 130 */         { str3 = str3 + "," + paramArrayOfString[i]; } 
/* 131 */       } else if (paramArrayOfString[i].equals("-dumpoptions")) {
/* 132 */         bool = true;
/* 133 */       } else if (str4 == null) {
/* 134 */         str4 = paramArrayOfString[i];
/* 135 */       } else if (str5 == null) {
/* 136 */         str5 = paramArrayOfString[i];
/*     */       } else {
/* 138 */         usage();
/*     */       } 
/* 140 */       i++;
/*     */     } 
/* 142 */     if (str4 == null || str5 == null) usage();
/*     */     
/* 144 */     Seekable.File file = new Seekable.File(str5);
/*     */     
/* 146 */     outputStreamWriter = null;
/* 147 */     fileOutputStream = null;
/* 148 */     JavaSourceCompiler javaSourceCompiler = null;
/* 149 */     if (str6 == null || str6.equals("class")) {
/* 150 */       if (str1 != null) {
/* 151 */         fileOutputStream = new FileOutputStream(str1);
/* 152 */         javaSourceCompiler = new ClassFileCompiler(file, str4, fileOutputStream);
/* 153 */       } else if (str2 != null) {
/* 154 */         File file1 = new File(str2);
/* 155 */         if (!file1.isDirectory()) {
/* 156 */           System.err.println(str2 + " doesn't exist or is not a directory");
/* 157 */           System.exit(1);
/*     */         } 
/* 159 */         javaSourceCompiler = new ClassFileCompiler(file, str4, file1);
/*     */       } else {
/* 161 */         System.err.println("Refusing to write a classfile to stdout - use -outfile foo.class");
/* 162 */         System.exit(1);
/*     */       } 
/* 164 */     } else if (str6.equals("javasource") || str6.equals("java")) {
/* 165 */       outputStreamWriter = (str1 == null) ? new OutputStreamWriter(System.out) : new FileWriter(str1);
/* 166 */       javaSourceCompiler = new JavaSourceCompiler(file, str4, outputStreamWriter);
/*     */     } else {
/* 168 */       System.err.println("Unknown output format: " + str6);
/* 169 */       System.exit(1);
/*     */     } 
/*     */     
/* 172 */     javaSourceCompiler.parseOptions(str3);
/* 173 */     javaSourceCompiler.setSource(str5);
/*     */     
/* 175 */     if (bool) {
/* 176 */       System.err.println("== Options ==");
/* 177 */       for (boolean bool1 = false; bool1 < options.length; bool1 += true)
/* 178 */         System.err.println(options[bool1] + ": " + javaSourceCompiler.getOption(options[bool1]).get()); 
/* 179 */       System.err.println("== End Options ==");
/*     */     } 
/*     */     
/*     */     try {
/* 183 */       javaSourceCompiler.go();
/* 184 */     } catch (Exn exn) {
/* 185 */       System.err.println("Compiler Error: " + exn.getMessage());
/* 186 */       System.exit(1);
/*     */     } finally {
/* 188 */       if (outputStreamWriter != null) outputStreamWriter.close(); 
/* 189 */       if (fileOutputStream != null) fileOutputStream.close(); 
/*     */     }  } public Compiler(Seekable paramSeekable, String paramString) throws IOException { this.source = "unknown.mips.binary"; this.fastMem = true; this.maxInsnPerMethod = 128; this.pruneCases = true; this.assumeTailCalls = true; this.debugCompiler = false; this.printStats = false; this.runtimeStats = false; this.supportCall = true; this.nullPointerCheck = false; this.runtimeClass = "org.ibex.nestedvm.Runtime";
/*     */     this.hashClass = "java.util.Hashtable";
/*     */     this.pageSize = 4096;
/*     */     this.totalPages = 65536;
/* 194 */     this.fullClassName = paramString;
/* 195 */     this.elf = new ELF(paramSeekable);
/*     */     
/* 197 */     if (this.elf.header.type != 2) throw new IOException("Binary is not an executable"); 
/* 198 */     if (this.elf.header.machine != 8) throw new IOException("Binary is not for the MIPS I Architecture"); 
/* 199 */     if (this.elf.ident.data != 2) throw new IOException("Binary is not big endian");
/*     */      }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void go() throws Exn {
/* 206 */     if (this.used) throw new RuntimeException("Compiler instances are good for one shot only"); 
/* 207 */     this.used = true;
/*     */     
/* 209 */     if (this.onePage && this.pageSize <= 4096) this.pageSize = 4194304; 
/* 210 */     if (this.nullPointerCheck && !this.fastMem) throw new Exn("fastMem must be enabled for nullPointerCheck to be of any use"); 
/* 211 */     if (this.onePage && !this.fastMem) throw new Exn("fastMem must be enabled for onePage to be of any use"); 
/* 212 */     if (this.totalPages == 1 && !this.onePage) throw new Exn("totalPages == 1 and onePage is not set"); 
/* 213 */     if (this.onePage) this.totalPages = 1;
/*     */     
/* 215 */     maxInsnPerMethodInit();
/* 216 */     pageSizeInit();
/*     */ 
/*     */     
/* 219 */     ELF.Symtab symtab = this.elf.getSymtab();
/* 220 */     if (symtab == null) throw new Exn("Binary has no symtab (did you strip it?)");
/*     */ 
/*     */     
/* 223 */     this.userInfo = symtab.getGlobalSymbol("user_info");
/* 224 */     this.gp = symtab.getGlobalSymbol("_gp");
/* 225 */     if (this.gp == null) throw new Exn("no _gp symbol (did you strip the binary?)");
/*     */     
/* 227 */     if (this.pruneCases) {
/*     */       
/* 229 */       this.jumpableAddresses = new Hashtable();
/*     */       
/* 231 */       this.jumpableAddresses.put(new Integer(this.elf.header.entry), Boolean.TRUE);
/*     */       
/* 233 */       ELF.SHeader sHeader = this.elf.sectionWithName(".text");
/* 234 */       if (sHeader == null) throw new Exn("No .text segment");
/*     */       
/* 236 */       findBranchesInSymtab(symtab, this.jumpableAddresses);
/*     */       
/* 238 */       for (byte b1 = 0; b1 < this.elf.sheaders.length; b1++) {
/* 239 */         ELF.SHeader sHeader1 = this.elf.sheaders[b1];
/* 240 */         String str = sHeader1.name;
/*     */         
/* 242 */         if (sHeader1.addr != 0 && (
/* 243 */           str.equals(".data") || str.equals(".sdata") || str.equals(".rodata") || str.equals(".ctors") || str.equals(".dtors"))) {
/* 244 */           findBranchesInData(new DataInputStream(sHeader1.getInputStream()), sHeader1.size, this.jumpableAddresses, sHeader.addr, sHeader.addr + sHeader.size);
/*     */         }
/*     */       } 
/* 247 */       findBranchesInText(sHeader.addr, new DataInputStream(sHeader.getInputStream()), sHeader.size, this.jumpableAddresses);
/*     */     } 
/*     */     
/* 250 */     if (this.unixRuntime && this.runtimeClass.startsWith("org.ibex.nestedvm.")) this.runtimeClass = "org.ibex.nestedvm.UnixRuntime";
/*     */     
/* 252 */     for (byte b = 0; b < this.elf.sheaders.length; b++) {
/* 253 */       String str = (this.elf.sheaders[b]).name;
/* 254 */       if (((this.elf.sheaders[b]).flags & 0x2) != 0 && !str.equals(".text") && !str.equals(".data") && !str.equals(".sdata") && !str.equals(".rodata") && !str.equals(".ctors") && !str.equals(".dtors") && !str.equals(".bss") && !str.equals(".sbss"))
/*     */       {
/*     */         
/* 257 */         throw new Exn("Unknown section: " + str); } 
/*     */     } 
/* 259 */     _go();
/*     */   }
/*     */   
/*     */   private void findBranchesInSymtab(ELF.Symtab paramSymtab, Hashtable paramHashtable) {
/* 263 */     ELF.Symbol[] arrayOfSymbol = paramSymtab.symbols;
/* 264 */     byte b1 = 0;
/* 265 */     for (byte b2 = 0; b2 < arrayOfSymbol.length; b2++) {
/* 266 */       ELF.Symbol symbol = arrayOfSymbol[b2];
/* 267 */       if (symbol.type == 2 && 
/* 268 */         paramHashtable.put(new Integer(symbol.addr), Boolean.TRUE) == null)
/*     */       {
/* 270 */         b1++;
/*     */       }
/*     */     } 
/*     */     
/* 274 */     if (this.printStats) System.err.println("Found " + b1 + " additional possible branch targets in Symtab"); 
/*     */   }
/*     */   
/*     */   private void findBranchesInText(int paramInt1, DataInputStream paramDataInputStream, int paramInt2, Hashtable paramHashtable) throws IOException {
/* 278 */     int i = paramInt2 / 4;
/* 279 */     int j = paramInt1;
/* 280 */     byte b1 = 0;
/* 281 */     int[] arrayOfInt1 = new int[32];
/* 282 */     int[] arrayOfInt2 = new int[32];
/*     */ 
/*     */     
/* 285 */     for (byte b2 = 0; b2 < i; b2++, j += 4) {
/* 286 */       int k = paramDataInputStream.readInt();
/* 287 */       int m = k >>> 26 & 0xFF;
/* 288 */       int n = k >>> 21 & 0x1F;
/* 289 */       int i1 = k >>> 16 & 0x1F;
/* 290 */       int i2 = k << 16 >> 16;
/* 291 */       int i3 = k & 0xFFFF;
/* 292 */       int i4 = i2;
/* 293 */       int i5 = k & 0x3FFFFFF;
/* 294 */       int i6 = k & 0x3F;
/*     */       
/* 296 */       switch (m) {
/*     */         case 0:
/* 298 */           switch (i6)
/*     */           { case 9:
/* 300 */               if (paramHashtable.put(new Integer(j + 8), Boolean.TRUE) == null) b1++;  break;
/*     */             case 12:
/*     */             case 0:
/* 303 */               break; }  if (paramHashtable.put(new Integer(j + 4), Boolean.TRUE) == null) b1++;
/*     */           
/*     */           break;
/*     */         
/*     */         case 1:
/* 308 */           switch (i1)
/*     */           { case 16:
/*     */             case 17:
/* 311 */               if (paramHashtable.put(new Integer(j + 8), Boolean.TRUE) == null) b1++;  break;
/*     */             case 0:
/*     */             case 1:
/*     */             case 0:
/* 315 */               break; }  if (paramHashtable.put(new Integer(j + i4 * 4 + 4), Boolean.TRUE) == null) b1++;
/*     */           
/*     */           break;
/*     */         
/*     */         case 3:
/* 320 */           if (paramHashtable.put(new Integer(j + 8), Boolean.TRUE) == null) b1++;
/*     */         
/*     */         case 2:
/* 323 */           if (paramHashtable.put(new Integer(j & 0xF0000000 | i5 << 2), Boolean.TRUE) == null) b1++; 
/*     */           break;
/*     */         case 4:
/*     */         case 5:
/*     */         case 6:
/*     */         case 7:
/* 329 */           if (paramHashtable.put(new Integer(j + i4 * 4 + 4), Boolean.TRUE) == null) b1++; 
/*     */           break;
/*     */         case 9:
/* 332 */           if (j - arrayOfInt2[n] <= 128) {
/* 333 */             int i7 = (arrayOfInt1[n] << 16) + i2;
/* 334 */             if ((i7 & 0x3) == 0 && i7 >= paramInt1 && i7 < paramInt1 + paramInt2 && 
/* 335 */               paramHashtable.put(new Integer(i7), Boolean.TRUE) == null)
/*     */             {
/* 337 */               b1++;
/*     */             }
/*     */ 
/*     */             
/* 341 */             if (i1 == n) arrayOfInt2[n] = 0;
/*     */           
/*     */           } 
/*     */           break;
/*     */         case 15:
/* 346 */           arrayOfInt1[i1] = i3;
/* 347 */           arrayOfInt2[i1] = j;
/*     */           break;
/*     */ 
/*     */         
/*     */         case 17:
/* 352 */           switch (n) { case 8:
/*     */             case 0:
/* 354 */               break; }  if (paramHashtable.put(new Integer(j + i4 * 4 + 4), Boolean.TRUE) == null) b1++;
/*     */           
/*     */           break;
/*     */       } 
/*     */     
/*     */     } 
/* 360 */     paramDataInputStream.close();
/* 361 */     if (this.printStats) System.err.println("Found " + b1 + " additional possible branch targets in Text segment"); 
/*     */   }
/*     */   
/*     */   private void findBranchesInData(DataInputStream paramDataInputStream, int paramInt1, Hashtable paramHashtable, int paramInt2, int paramInt3) throws IOException {
/* 365 */     int i = paramInt1 / 4;
/* 366 */     byte b1 = 0;
/* 367 */     for (byte b2 = 0; b2 < i; b2++) {
/* 368 */       int j = paramDataInputStream.readInt();
/* 369 */       if ((j & 0x3) == 0 && j >= paramInt2 && j < paramInt3 && 
/* 370 */         paramHashtable.put(new Integer(j), Boolean.TRUE) == null)
/*     */       {
/* 372 */         b1++;
/*     */       }
/*     */     } 
/*     */     
/* 376 */     paramDataInputStream.close();
/* 377 */     if (b1 > 0 && this.printStats) System.err.println("Found " + b1 + " additional possible branch targets in Data segment");
/*     */   
/*     */   }
/*     */   
/* 381 */   static final String toHex(int paramInt) { return "0x" + Long.toString(paramInt & 0xFFFFFFFFL, 16); }
/*     */   static final String toHex8(int paramInt) {
/* 383 */     String str = Long.toString(paramInt & 0xFFFFFFFFL, 16);
/* 384 */     StringBuffer stringBuffer = new StringBuffer("0x");
/* 385 */     for (int i = 8 - str.length(); i > 0; ) { stringBuffer.append('0'); i--; }
/* 386 */      stringBuffer.append(str);
/* 387 */     return stringBuffer.toString();
/*     */   }
/*     */   
/*     */   static final String toOctal3(int paramInt) {
/* 391 */     char[] arrayOfChar = new char[3];
/* 392 */     for (byte b = 2; b >= 0; b--) {
/* 393 */       arrayOfChar[b] = (char)(48 + (paramInt & 0x7));
/* 394 */       paramInt >>= 3;
/*     */     } 
/* 396 */     return new String(arrayOfChar);
/*     */   }
/*     */   
/*     */   private class Option { private Field field;
/*     */     private final Compiler this$0;
/*     */     
/* 402 */     public Option(Compiler this$0, String param1String) throws NoSuchFieldException { this.field = (param1String == null) ? null : ((Compiler.class$org$ibex$nestedvm$Compiler == null) ? (Compiler.class$org$ibex$nestedvm$Compiler = (this.this$0 = this$0).class$("org.ibex.nestedvm.Compiler")) : Compiler.class$org$ibex$nestedvm$Compiler).getDeclaredField(param1String); }
/*     */     public void set(Object param1Object) {
/* 404 */       if (this.field == null)
/*     */         return; 
/*     */       try {
/* 407 */         this.field.set(this.this$0, param1Object);
/* 408 */       } catch (IllegalAccessException illegalAccessException) {
/* 409 */         System.err.println(illegalAccessException);
/*     */       } 
/*     */     }
/*     */     public Object get() {
/* 413 */       if (this.field == null) return null;
/*     */       
/*     */       try {
/* 416 */         return this.field.get(this.this$0);
/* 417 */       } catch (IllegalAccessException illegalAccessException) {
/* 418 */         System.err.println(illegalAccessException); return null;
/*     */       } 
/*     */     }
/* 421 */     public Class getType() { return (this.field == null) ? null : this.field.getType(); } }
/*     */   
/*     */   private static String[] options = { 
/* 424 */       "fastMem", "Enable fast memory access - RuntimeExceptions will be thrown on faults", "nullPointerCheck", "Enables checking at runtime for null pointer accessses (slows things down a bit, only applicable with fastMem)", "maxInsnPerMethod", "Maximum number of MIPS instructions per java method (128 is optimal with Hotspot)", "pruneCases", "Remove unnecessary case 0xAABCCDD blocks from methods - may break some weird code", "assumeTailCalls", "Assume the JIT optimizes tail calls", "optimizedMemcpy", "Use an optimized java version of memcpy where possible", "debugCompiler", "Output information in the generated code for debugging the compiler - will slow down generated code significantly", "printStats", "Output some useful statistics about the compilation", "runtimeStats", "Keep track of some statistics at runtime in the generated code - will slow down generated code significantly", "supportCall", "Keep a stripped down version of the symbol table in the generated code to support the call() method", "runtimeClass", "Full classname of the Runtime class (default: Runtime) - use this is you put Runtime in a package", "hashClass", "Full classname of a Hashtable class (default: java.util.HashMap) - this must support get() and put()", "unixRuntime", "Use the UnixRuntime (has support for fork, wai, du, pipe, etc)", "pageSize", "The page size (must be a power of two)", "totalPages", "Total number of pages (total mem = pageSize*totalPages, must be a power of two)", "onePage", "One page hack (FIXME: document this better)", "lessConstants", "Use less constants at the cost of speed (FIXME: document this better)", "singleFloat", "Support single precision (32-bit) FP ops only" };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static Class class$org$ibex$nestedvm$Compiler;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Option getOption(String paramString) {
/* 446 */     paramString = paramString.toLowerCase();
/*     */     try {
/* 448 */       for (boolean bool = false; bool < options.length; bool += true) {
/* 449 */         if (options[bool].toLowerCase().equals(paramString))
/* 450 */           return new Option(this, options[bool]); 
/* 451 */       }  return null;
/* 452 */     } catch (NoSuchFieldException noSuchFieldException) {
/* 453 */       return null;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void parseOptions(String paramString) {
/* 458 */     if (paramString == null || paramString.length() == 0)
/* 459 */       return;  StringTokenizer stringTokenizer = new StringTokenizer(paramString, ",");
/* 460 */     while (stringTokenizer.hasMoreElements()) {
/* 461 */       String str3, str2, str1 = stringTokenizer.nextToken();
/*     */ 
/*     */       
/* 464 */       if (str1.indexOf("=") != -1) {
/* 465 */         str2 = str1.substring(0, str1.indexOf("="));
/* 466 */         str3 = str1.substring(str1.indexOf("=") + 1);
/* 467 */       } else if (str1.startsWith("no")) {
/* 468 */         str2 = str1.substring(2);
/* 469 */         str3 = "false";
/*     */       } else {
/* 471 */         str2 = str1;
/* 472 */         str3 = "true";
/*     */       } 
/* 474 */       Option option = getOption(str2);
/* 475 */       if (option == null) {
/* 476 */         System.err.println("WARNING: No such option: " + str2);
/*     */         
/*     */         continue;
/*     */       } 
/* 480 */       if (option.getType() == String.class) {
/* 481 */         option.set(str3); continue;
/* 482 */       }  if (option.getType() == int.class)
/*     */         try {
/* 484 */           option.set(parseInt(str3)); continue;
/* 485 */         } catch (NumberFormatException numberFormatException) {
/* 486 */           System.err.println("WARNING: " + str3 + " is not an integer"); continue;
/*     */         }  
/* 488 */       if (option.getType() == boolean.class) {
/* 489 */         option.set(new Boolean((str3.toLowerCase().equals("true") || str3.toLowerCase().equals("yes")) ? 1 : 0)); continue;
/*     */       } 
/* 491 */       throw new Error("Unknown type: " + option.getType());
/*     */     } 
/*     */   }
/*     */   
/*     */   private static Integer parseInt(String paramString) {
/* 496 */     int j, i = 1;
/* 497 */     paramString = paramString.toLowerCase();
/* 498 */     if (!paramString.startsWith("0x") && paramString.endsWith("m")) { paramString = paramString.substring(0, paramString.length() - 1); i = 1048576; }
/* 499 */     else if (!paramString.startsWith("0x") && paramString.endsWith("k")) { paramString = paramString.substring(0, paramString.length() - 1); i = 1024; }
/*     */     
/* 501 */     if (paramString.length() > 2 && paramString.startsWith("0x")) { j = Integer.parseInt(paramString.substring(2), 16); }
/* 502 */     else { j = Integer.parseInt(paramString); }
/* 503 */      return new Integer(j * i);
/*     */   }
/*     */   
/*     */   private static String wrapAndIndent(String paramString, int paramInt1, int paramInt2, int paramInt3) {
/* 507 */     StringTokenizer stringTokenizer = new StringTokenizer(paramString, " ");
/* 508 */     StringBuffer stringBuffer = new StringBuffer(); int i;
/* 509 */     for (i = 0; i < paramInt1; i++)
/* 510 */       stringBuffer.append(' '); 
/* 511 */     i = 0;
/* 512 */     while (stringTokenizer.hasMoreTokens()) {
/* 513 */       String str = stringTokenizer.nextToken();
/* 514 */       if (str.length() + i + 1 > paramInt3 && i > 0) {
/* 515 */         stringBuffer.append('\n');
/* 516 */         for (byte b = 0; b < paramInt2; ) { stringBuffer.append(' '); b++; }
/* 517 */          i = 0;
/* 518 */       } else if (i > 0) {
/* 519 */         stringBuffer.append(' ');
/* 520 */         i++;
/*     */       } 
/* 522 */       stringBuffer.append(str);
/* 523 */       i += str.length();
/*     */     } 
/* 525 */     stringBuffer.append('\n');
/* 526 */     return stringBuffer.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static String dateTime() {
/*     */     try {
/* 533 */       return (new Date()).toString();
/* 534 */     } catch (RuntimeException runtimeException) {
/* 535 */       return "<unknown>";
/*     */     } 
/*     */   }
/*     */   
/*     */   abstract void _go() throws Exn;
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\ibex\nestedvm\Compiler.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       1.0.4
 */