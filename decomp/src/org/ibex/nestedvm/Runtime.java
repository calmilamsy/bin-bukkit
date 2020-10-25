/*      */ package org.ibex.nestedvm;
/*      */ 
/*      */ import java.io.File;
/*      */ import java.io.FileNotFoundException;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.io.OutputStream;
/*      */ import java.io.UnsupportedEncodingException;
/*      */ import org.ibex.nestedvm.util.Platform;
/*      */ import org.ibex.nestedvm.util.Seekable;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public abstract class Runtime
/*      */   implements UsermodeConstants, Registers, Cloneable
/*      */ {
/*      */   public static final String VERSION = "1.0";
/*      */   static final boolean STDERR_DIAG = true;
/*      */   protected final int pageShift;
/*      */   private final int stackBottom;
/*      */   protected int[][] readPages;
/*      */   protected int[][] writePages;
/*      */   private int heapEnd;
/*      */   private static final int STACK_GUARD_PAGES = 4;
/*      */   private long startTime;
/*      */   public static final int RUNNING = 0;
/*      */   public static final int STOPPED = 1;
/*      */   public static final int PAUSED = 2;
/*      */   public static final int CALLJAVA = 3;
/*      */   public static final int EXITED = 4;
/*      */   public static final int EXECED = 5;
/*      */   
/*   44 */   protected int userInfoBase() { return 0; }
/*   45 */   protected int userInfoSize() { return 0; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   67 */   protected int state = 1; private int exitStatus; public ExecutionException exitException; FD[] fds; boolean[] closeOnExec;
/*      */   
/*   69 */   public final int getState() { return this.state; }
/*      */   SecurityManager sm; private CallJavaCB callJavaCB; private byte[] _byteBuf; static final int MAX_CHUNK = 16776192;
/*      */   static final boolean win32Hacks;
/*      */   public static final int RD_ONLY = 0;
/*      */   public static final int WR_ONLY = 1;
/*      */   public static final int RDWR = 2;
/*      */   public static final int O_CREAT = 512;
/*      */   public static final int O_EXCL = 2048;
/*      */   public static final int O_APPEND = 8;
/*      */   public static final int O_TRUNC = 1024;
/*      */   public static final int O_NONBLOCK = 16384;
/*      */   public static final int O_NOCTTY = 32768;
/*      */   
/*   82 */   public void setSecurityManager(SecurityManager paramSecurityManager) { this.sm = paramSecurityManager; }
/*      */ 
/*      */ 
/*      */   
/*   86 */   public void setCallJavaCB(CallJavaCB paramCallJavaCB) { this.callJavaCB = paramCallJavaCB; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  101 */   public int lookupSymbol(String paramString) { return -1; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static  {
/*  113 */     String str1 = Platform.getProperty("os.name");
/*  114 */     String str2 = Platform.getProperty("nestedvm.win32hacks");
/*  115 */     if (str2 != null) { win32Hacks = Boolean.valueOf(str2).booleanValue(); }
/*  116 */     else { win32Hacks = (str1 != null && str1.toLowerCase().indexOf("windows") != -1); }
/*      */   
/*      */   }
/*      */   protected Object clone() throws CloneNotSupportedException {
/*  120 */     Runtime runtime = (Runtime)super.clone();
/*  121 */     runtime._byteBuf = null;
/*  122 */     runtime.startTime = 0L;
/*  123 */     runtime.fds = new FD[64]; int i;
/*  124 */     for (i = 0; i < 64; ) { if (this.fds[i] != null) runtime.fds[i] = this.fds[i].dup();  i++; }
/*  125 */      i = this.writePages.length;
/*  126 */     runtime.readPages = new int[i][];
/*  127 */     runtime.writePages = new int[i][];
/*  128 */     for (byte b = 0; b < i; b++) {
/*  129 */       if (this.readPages[b] != null)
/*  130 */         if (this.writePages[b] == null) { runtime.readPages[b] = this.readPages[b]; }
/*  131 */         else { runtime.writePages[b] = (int[])this.writePages[b].clone(); runtime.readPages[b] = (int[])this.writePages[b].clone(); }
/*      */          
/*  133 */     }  return runtime;
/*      */   }
/*      */   
/*  136 */   protected Runtime(int paramInt1, int paramInt2) { this(paramInt1, paramInt2, false); }
/*      */   protected Runtime(int paramInt1, int paramInt2, boolean paramBoolean) {
/*  138 */     if (paramInt1 <= 0) throw new IllegalArgumentException("pageSize <= 0"); 
/*  139 */     if (paramInt2 <= 0) throw new IllegalArgumentException("totalPages <= 0"); 
/*  140 */     if ((paramInt1 & paramInt1 - 1) != 0) throw new IllegalArgumentException("pageSize not a power of two");
/*      */     
/*  142 */     int i = 0;
/*  143 */     for (; paramInt1 >>> i != 1; i++);
/*  144 */     this.pageShift = i;
/*      */     
/*  146 */     int j = heapStart();
/*  147 */     int k = paramInt2 * paramInt1;
/*  148 */     int m = max(k / 512, 131072);
/*  149 */     int n = 0;
/*  150 */     if (paramInt2 > 1) {
/*  151 */       m = max(m, paramInt1);
/*  152 */       m = m + paramInt1 - 1 & (paramInt1 - 1 ^ 0xFFFFFFFF);
/*  153 */       n = m >>> this.pageShift;
/*  154 */       j = j + paramInt1 - 1 & (paramInt1 - 1 ^ 0xFFFFFFFF);
/*  155 */       if (n + 4 + (j >>> this.pageShift) >= paramInt2)
/*  156 */         throw new IllegalArgumentException("total pages too small"); 
/*      */     } else {
/*  158 */       if (paramInt1 < j + m) throw new IllegalArgumentException("total memory too small"); 
/*  159 */       j = j + 4095 & 0xFFFFEFFF;
/*      */     } 
/*      */     
/*  162 */     this.stackBottom = k - m;
/*  163 */     this.heapEnd = j;
/*      */     
/*  165 */     this.readPages = new int[paramInt2][];
/*  166 */     this.writePages = new int[paramInt2][];
/*      */     
/*  168 */     if (paramInt2 == 1) {
/*  169 */       this.writePages[0] = new int[paramInt1 >> 2]; this.readPages[0] = new int[paramInt1 >> 2];
/*      */     } else {
/*  171 */       for (int i1 = this.stackBottom >>> this.pageShift; i1 < this.writePages.length; i1++) {
/*  172 */         this.writePages[i1] = new int[paramInt1 >> 2]; this.readPages[i1] = new int[paramInt1 >> 2];
/*      */       } 
/*      */     } 
/*      */     
/*  176 */     if (!paramBoolean) {
/*  177 */       this.fds = new FD[64];
/*  178 */       this.closeOnExec = new boolean[64];
/*      */       
/*  180 */       Win32ConsoleIS win32ConsoleIS = win32Hacks ? new Win32ConsoleIS(System.in) : System.in;
/*  181 */       addFD(new TerminalFD(win32ConsoleIS));
/*  182 */       addFD(new TerminalFD(System.out));
/*  183 */       addFD(new TerminalFD(System.err));
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected final void initPages(int[] paramArrayOfInt, int paramInt, boolean paramBoolean) {
/*  190 */     int i = 1 << this.pageShift >>> 2;
/*  191 */     int j = (1 << this.pageShift) - 1;
/*      */     
/*  193 */     for (int k = 0; k < paramArrayOfInt.length; ) {
/*  194 */       int m = paramInt >>> this.pageShift;
/*  195 */       int n = (paramInt & j) >> 2;
/*  196 */       int i1 = min(i - n, paramArrayOfInt.length - k);
/*  197 */       if (this.readPages[m] == null)
/*  198 */       { initPage(m, paramBoolean); }
/*  199 */       else if (!paramBoolean && 
/*  200 */         this.writePages[m] == null) { this.writePages[m] = this.readPages[m]; }
/*      */       
/*  202 */       System.arraycopy(paramArrayOfInt, k, this.readPages[m], n, i1);
/*  203 */       k += i1;
/*  204 */       paramInt += i1 * 4;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected final void clearPages(int paramInt1, int paramInt2) {
/*  210 */     int i = 1 << this.pageShift >>> 2;
/*  211 */     int j = (1 << this.pageShift) - 1;
/*      */     
/*  213 */     for (int k = 0; k < paramInt2; ) {
/*  214 */       int m = paramInt1 >>> this.pageShift;
/*  215 */       int n = (paramInt1 & j) >> 2;
/*  216 */       int i1 = min(i - n, paramInt2 - k);
/*  217 */       if (this.readPages[m] == null) {
/*  218 */         this.writePages[m] = new int[i]; this.readPages[m] = new int[i];
/*      */       } else {
/*  220 */         if (this.writePages[m] == null) this.writePages[m] = this.readPages[m]; 
/*  221 */         for (int i2 = n; i2 < n + i1; ) { this.writePages[m][i2] = 0; i2++; }
/*      */       
/*  223 */       }  k += i1;
/*  224 */       paramInt1 += i1 * 4;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public final void copyin(int paramInt1, byte[] paramArrayOfByte, int paramInt2) throws ReadFaultException {
/*  231 */     int i = 1 << this.pageShift >>> 2;
/*  232 */     int j = i - 1;
/*      */     
/*  234 */     byte b = 0;
/*  235 */     if (paramInt2 == 0)
/*  236 */       return;  if ((paramInt1 & 0x3) != 0) {
/*  237 */       int k = memRead(paramInt1 & 0xFFFFFFFC);
/*  238 */       switch (paramInt1 & 0x3) { case 1:
/*  239 */           paramArrayOfByte[b++] = (byte)(k >>> 16 & 0xFF); if (--paramInt2 == 0)
/*  240 */             break; case 2: paramArrayOfByte[b++] = (byte)(k >>> 8 & 0xFF); if (--paramInt2 == 0)
/*  241 */             break; case 3: paramArrayOfByte[b++] = (byte)(k >>> 0 & 0xFF); if (--paramInt2 == 0); break; }
/*      */       
/*  243 */       paramInt1 = (paramInt1 & 0xFFFFFFFC) + 4;
/*      */     } 
/*  245 */     if ((paramInt2 & 0xFFFFFFFC) != 0) {
/*  246 */       int k = paramInt2 >>> 2;
/*  247 */       int m = paramInt1 >>> 2;
/*  248 */       while (k != 0) {
/*  249 */         int[] arrayOfInt = this.readPages[m >>> this.pageShift - 2];
/*  250 */         if (arrayOfInt == null) throw new ReadFaultException(m << 2); 
/*  251 */         int n = m & j;
/*  252 */         int i1 = min(k, i - n);
/*  253 */         for (int i2 = 0; i2 < i1; i2++, b += 4) {
/*  254 */           int i3 = arrayOfInt[n + i2];
/*  255 */           paramArrayOfByte[b + 0] = (byte)(i3 >>> 24 & 0xFF); paramArrayOfByte[b + 1] = (byte)(i3 >>> 16 & 0xFF);
/*  256 */           paramArrayOfByte[b + 2] = (byte)(i3 >>> 8 & 0xFF); paramArrayOfByte[b + 3] = (byte)(i3 >>> 0 & 0xFF);
/*      */         } 
/*  258 */         m += i1; k -= i1;
/*      */       } 
/*  260 */       paramInt1 = m << 2; paramInt2 &= 0x3;
/*      */     } 
/*  262 */     if (paramInt2 != 0) {
/*  263 */       int k = memRead(paramInt1);
/*  264 */       switch (paramInt2) { case 3:
/*  265 */           paramArrayOfByte[b + 2] = (byte)(k >>> 8 & 0xFF);
/*  266 */         case 2: paramArrayOfByte[b + 1] = (byte)(k >>> 16 & 0xFF);
/*  267 */         case 1: paramArrayOfByte[b + 0] = (byte)(k >>> 24 & 0xFF);
/*      */           break; }
/*      */     
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public final void copyout(byte[] paramArrayOfByte, int paramInt1, int paramInt2) throws FaultException {
/*  275 */     int i = 1 << this.pageShift >>> 2;
/*  276 */     int j = i - 1;
/*      */     
/*  278 */     int k = 0;
/*  279 */     if (paramInt2 == 0)
/*  280 */       return;  if ((paramInt1 & 0x3) != 0) {
/*  281 */       int m = memRead(paramInt1 & 0xFFFFFFFC);
/*  282 */       switch (paramInt1 & 0x3) { case 1:
/*  283 */           m = m & 0xFF00FFFF | (paramArrayOfByte[k++] & 0xFF) << 16; if (--paramInt2 == 0)
/*  284 */             break; case 2: m = m & 0xFFFF00FF | (paramArrayOfByte[k++] & 0xFF) << 8; if (--paramInt2 == 0)
/*  285 */             break; case 3: m = m & 0xFFFFFF00 | (paramArrayOfByte[k++] & 0xFF) << 0; if (--paramInt2 == 0); break; }
/*      */       
/*  287 */       memWrite(paramInt1 & 0xFFFFFFFC, m);
/*  288 */       paramInt1 += k;
/*      */     } 
/*      */     
/*  291 */     if ((paramInt2 & 0xFFFFFFFC) != 0) {
/*  292 */       int m = paramInt2 >>> 2;
/*  293 */       int n = paramInt1 >>> 2;
/*  294 */       while (m != 0) {
/*  295 */         int[] arrayOfInt = this.writePages[n >>> this.pageShift - 2];
/*  296 */         if (arrayOfInt == null) throw new WriteFaultException(n << 2); 
/*  297 */         int i1 = n & j;
/*  298 */         int i2 = min(m, i - i1);
/*  299 */         for (int i3 = 0; i3 < i2; i3++, k += 4)
/*  300 */           arrayOfInt[i1 + i3] = (paramArrayOfByte[k + 0] & 0xFF) << 24 | (paramArrayOfByte[k + 1] & 0xFF) << 16 | (paramArrayOfByte[k + 2] & 0xFF) << 8 | (paramArrayOfByte[k + 3] & 0xFF) << 0; 
/*  301 */         n += i2; m -= i2;
/*      */       } 
/*  303 */       paramInt1 = n << 2; paramInt2 &= 0x3;
/*      */     } 
/*      */     
/*  306 */     if (paramInt2 != 0) {
/*  307 */       int m = memRead(paramInt1);
/*  308 */       switch (paramInt2) { case 1:
/*  309 */           m = m & 0xFFFFFF | (paramArrayOfByte[k + 0] & 0xFF) << 24; break;
/*  310 */         case 2: m = m & 0xFFFF | (paramArrayOfByte[k + 0] & 0xFF) << 24 | (paramArrayOfByte[k + 1] & 0xFF) << 16; break;
/*  311 */         case 3: m = m & 0xFF | (paramArrayOfByte[k + 0] & 0xFF) << 24 | (paramArrayOfByte[k + 1] & 0xFF) << 16 | (paramArrayOfByte[k + 2] & 0xFF) << 8; break; }
/*      */       
/*  313 */       memWrite(paramInt1, m);
/*      */     } 
/*      */   }
/*      */   
/*      */   public final void memcpy(int paramInt1, int paramInt2, int paramInt3) throws FaultException {
/*  318 */     int i = 1 << this.pageShift >>> 2;
/*  319 */     int j = i - 1;
/*  320 */     if ((paramInt1 & 0x3) == 0 && (paramInt2 & 0x3) == 0) {
/*  321 */       if ((paramInt3 & 0xFFFFFFFC) != 0) {
/*  322 */         int k = paramInt3 >> 2;
/*  323 */         int m = paramInt2 >>> 2;
/*  324 */         int n = paramInt1 >>> 2;
/*  325 */         while (k != 0) {
/*  326 */           int[] arrayOfInt1 = this.readPages[m >>> this.pageShift - 2];
/*  327 */           if (arrayOfInt1 == null) throw new ReadFaultException(m << 2); 
/*  328 */           int[] arrayOfInt2 = this.writePages[n >>> this.pageShift - 2];
/*  329 */           if (arrayOfInt2 == null) throw new WriteFaultException(n << 2); 
/*  330 */           int i1 = m & j;
/*  331 */           int i2 = n & j;
/*  332 */           int i3 = min(k, i - max(i1, i2));
/*  333 */           System.arraycopy(arrayOfInt1, i1, arrayOfInt2, i2, i3);
/*  334 */           m += i3; n += i3; k -= i3;
/*      */         } 
/*  336 */         paramInt2 = m << 2; paramInt1 = n << 2; paramInt3 &= 0x3;
/*      */       } 
/*  338 */       if (paramInt3 != 0) {
/*  339 */         int k = memRead(paramInt2);
/*  340 */         int m = memRead(paramInt1);
/*  341 */         switch (paramInt3) { case 1:
/*  342 */             memWrite(paramInt1, k & 0xFF000000 | m & 0xFFFFFF); break;
/*  343 */           case 2: memWrite(paramInt1, k & 0xFFFF0000 | m & 0xFFFF); break;
/*  344 */           case 3: memWrite(paramInt1, k & 0xFFFFFF00 | m & 0xFF); break; }
/*      */       
/*      */       } 
/*      */     } else {
/*  348 */       while (paramInt3 > 0) {
/*  349 */         int k = min(paramInt3, 16776192);
/*  350 */         byte[] arrayOfByte = byteBuf(k);
/*  351 */         copyin(paramInt2, arrayOfByte, k);
/*  352 */         copyout(arrayOfByte, paramInt1, k);
/*  353 */         paramInt3 -= k; paramInt2 += k; paramInt1 += k;
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   public final void memset(int paramInt1, int paramInt2, int paramInt3) throws FaultException {
/*  359 */     int i = 1 << this.pageShift >>> 2;
/*  360 */     int j = i - 1;
/*      */     
/*  362 */     int k = (paramInt2 & 0xFF) << 24 | (paramInt2 & 0xFF) << 16 | (paramInt2 & 0xFF) << 8 | (paramInt2 & 0xFF) << 0;
/*  363 */     if ((paramInt1 & 0x3) != 0) {
/*  364 */       int m = memRead(paramInt1 & 0xFFFFFFFC);
/*  365 */       switch (paramInt1 & 0x3) { case 1:
/*  366 */           m = m & 0xFF00FFFF | (paramInt2 & 0xFF) << 16; if (--paramInt3 == 0)
/*  367 */             break; case 2: m = m & 0xFFFF00FF | (paramInt2 & 0xFF) << 8; if (--paramInt3 == 0)
/*  368 */             break; case 3: m = m & 0xFFFFFF00 | (paramInt2 & 0xFF) << 0; if (--paramInt3 == 0); break; }
/*      */       
/*  370 */       memWrite(paramInt1 & 0xFFFFFFFC, m);
/*  371 */       paramInt1 = (paramInt1 & 0xFFFFFFFC) + 4;
/*      */     } 
/*  373 */     if ((paramInt3 & 0xFFFFFFFC) != 0) {
/*  374 */       int m = paramInt3 >> 2;
/*  375 */       int n = paramInt1 >>> 2;
/*  376 */       while (m != 0) {
/*  377 */         int[] arrayOfInt = this.readPages[n >>> this.pageShift - 2];
/*  378 */         if (arrayOfInt == null) throw new WriteFaultException(n << 2); 
/*  379 */         int i1 = n & j;
/*  380 */         int i2 = min(m, i - i1);
/*      */         
/*  382 */         for (int i3 = i1; i3 < i1 + i2; ) { arrayOfInt[i3] = k; i3++; }
/*  383 */          n += i2; m -= i2;
/*      */       } 
/*  385 */       paramInt1 = n << 2; paramInt3 &= 0x3;
/*      */     } 
/*  387 */     if (paramInt3 != 0) {
/*  388 */       int m = memRead(paramInt1);
/*  389 */       switch (paramInt3) { case 1:
/*  390 */           m = m & 0xFFFFFF | k & 0xFF000000; break;
/*  391 */         case 2: m = m & 0xFFFF | k & 0xFFFF0000; break;
/*  392 */         case 3: m = m & 0xFF | k & 0xFFFFFF00; break; }
/*      */       
/*  394 */       memWrite(paramInt1, m);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public final int memRead(int paramInt) throws ReadFaultException {
/*  400 */     if ((paramInt & 0x3) != 0) throw new ReadFaultException(paramInt); 
/*  401 */     return unsafeMemRead(paramInt);
/*      */   }
/*      */   
/*      */   protected final int unsafeMemRead(int paramInt) throws ReadFaultException {
/*  405 */     int i = paramInt >>> this.pageShift;
/*  406 */     int j = (paramInt & (1 << this.pageShift) - 1) >> 2;
/*      */     try {
/*  408 */       return this.readPages[i][j];
/*  409 */     } catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
/*  410 */       if (i < 0 || i >= this.readPages.length) throw new ReadFaultException(paramInt); 
/*  411 */       throw arrayIndexOutOfBoundsException;
/*  412 */     } catch (NullPointerException nullPointerException) {
/*  413 */       throw new ReadFaultException(paramInt);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public final void memWrite(int paramInt1, int paramInt2) {
/*  419 */     if ((paramInt1 & 0x3) != 0) throw new WriteFaultException(paramInt1); 
/*  420 */     unsafeMemWrite(paramInt1, paramInt2);
/*      */   }
/*      */   
/*      */   protected final void unsafeMemWrite(int paramInt1, int paramInt2) {
/*  424 */     int i = paramInt1 >>> this.pageShift;
/*  425 */     int j = (paramInt1 & (1 << this.pageShift) - 1) >> 2;
/*      */     try {
/*  427 */       this.writePages[i][j] = paramInt2;
/*  428 */     } catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
/*  429 */       if (i < 0 || i >= this.writePages.length) throw new WriteFaultException(paramInt1); 
/*  430 */       throw arrayIndexOutOfBoundsException;
/*  431 */     } catch (NullPointerException nullPointerException) {
/*  432 */       throw new WriteFaultException(paramInt1);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*  437 */   private final int[] initPage(int paramInt) { return initPage(paramInt, false); }
/*      */   
/*      */   private final int[] initPage(int paramInt, boolean paramBoolean) {
/*  440 */     int[] arrayOfInt = new int[1 << this.pageShift >>> 2];
/*  441 */     this.writePages[paramInt] = paramBoolean ? null : arrayOfInt;
/*  442 */     this.readPages[paramInt] = arrayOfInt;
/*  443 */     return arrayOfInt;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public final int exitStatus() {
/*  449 */     if (this.state != 4) throw new IllegalStateException("exitStatus() called in an inappropriate state"); 
/*  450 */     return this.exitStatus;
/*      */   }
/*      */   
/*      */   private int addStringArray(String[] paramArrayOfString, int paramInt) throws FaultException {
/*  454 */     int i = paramArrayOfString.length;
/*  455 */     int j = 0; int k;
/*  456 */     for (k = 0; k < i; ) { j += paramArrayOfString[k].length() + 1; k++; }
/*  457 */      j += (i + 1) * 4;
/*  458 */     k = paramInt - j & 0xFFFFFFFC;
/*  459 */     int m = k + (i + 1) * 4;
/*  460 */     int[] arrayOfInt = new int[i + 1]; try {
/*      */       byte b;
/*  462 */       for (b = 0; b < i; b++) {
/*  463 */         byte[] arrayOfByte = getBytes(paramArrayOfString[b]);
/*  464 */         arrayOfInt[b] = m;
/*  465 */         copyout(arrayOfByte, m, arrayOfByte.length);
/*  466 */         memset(m + arrayOfByte.length, 0, 1);
/*  467 */         m += arrayOfByte.length + 1;
/*      */       } 
/*  469 */       m = k;
/*  470 */       for (b = 0; b < i + 1; b++) {
/*  471 */         memWrite(m, arrayOfInt[b]);
/*  472 */         m += 4;
/*      */       } 
/*  474 */     } catch (FaultException faultException) {
/*  475 */       throw new RuntimeException(faultException.toString());
/*      */     } 
/*  477 */     return k;
/*      */   }
/*      */   
/*  480 */   String[] createEnv(String[] paramArrayOfString) { if (paramArrayOfString == null) paramArrayOfString = new String[0];  return paramArrayOfString; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setUserInfo(int paramInt1, int paramInt2) {
/*  488 */     if (paramInt1 < 0 || paramInt1 >= userInfoSize() / 4) throw new IndexOutOfBoundsException("setUserInfo called with index >= " + (userInfoSize() / 4)); 
/*      */     
/*  490 */     try { memWrite(userInfoBase() + paramInt1 * 4, paramInt2); }
/*  491 */     catch (FaultException faultException) { throw new RuntimeException(faultException.toString()); }
/*      */   
/*      */   }
/*      */ 
/*      */   
/*      */   public int getUserInfo(int paramInt) throws ReadFaultException {
/*  497 */     if (paramInt < 0 || paramInt >= userInfoSize() / 4) throw new IndexOutOfBoundsException("setUserInfo called with index >= " + (userInfoSize() / 4)); 
/*      */     
/*  499 */     try { return memRead(userInfoBase() + paramInt * 4); }
/*  500 */     catch (FaultException faultException) { throw new RuntimeException(faultException.toString()); }
/*      */   
/*      */   }
/*      */   
/*      */   private void __execute() throws ExecutionException {
/*      */     try {
/*  506 */       _execute();
/*  507 */     } catch (FaultException faultException) {
/*  508 */       faultException.printStackTrace();
/*  509 */       exit(139, true);
/*  510 */       this.exitException = faultException;
/*  511 */     } catch (ExecutionException executionException) {
/*  512 */       executionException.printStackTrace();
/*  513 */       exit(132, true);
/*  514 */       this.exitException = executionException;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public final boolean execute() {
/*  520 */     if (this.state != 2) throw new IllegalStateException("execute() called in inappropriate state"); 
/*  521 */     if (this.startTime == 0L) this.startTime = System.currentTimeMillis(); 
/*  522 */     this.state = 0;
/*  523 */     __execute();
/*  524 */     if (this.state != 2 && this.state != 4 && this.state != 5)
/*  525 */       throw new IllegalStateException("execute() ended up in an inappropriate state (" + this.state + ")"); 
/*  526 */     return (this.state != 2);
/*      */   }
/*      */   
/*      */   static String[] concatArgv(String paramString, String[] paramArrayOfString) {
/*  530 */     String[] arrayOfString = new String[paramArrayOfString.length + 1];
/*  531 */     System.arraycopy(paramArrayOfString, 0, arrayOfString, 1, paramArrayOfString.length);
/*  532 */     arrayOfString[0] = paramString;
/*  533 */     return arrayOfString;
/*      */   }
/*      */   
/*  536 */   public final int run() { return run(null); }
/*  537 */   public final int run(String paramString, String[] paramArrayOfString) { return run(concatArgv(paramString, paramArrayOfString)); }
/*  538 */   public final int run(String[] paramArrayOfString) { return run(paramArrayOfString, null); }
/*      */ 
/*      */ 
/*      */   
/*      */   public final int run(String[] paramArrayOfString1, String[] paramArrayOfString2) {
/*  543 */     start(paramArrayOfString1, paramArrayOfString2);
/*      */     
/*  545 */     while (!execute()) {
/*  546 */       System.err.println("WARNING: Pause requested while executing run()");
/*      */     }
/*  548 */     if (this.state == 5) System.err.println("WARNING: Process exec()ed while being run under run()"); 
/*  549 */     return (this.state == 4) ? exitStatus() : 0;
/*      */   }
/*      */   
/*  552 */   public final void start() throws ExecutionException { start(null); }
/*  553 */   public final void start(String[] paramArrayOfString) { start(paramArrayOfString, null); }
/*      */ 
/*      */   
/*      */   public final void start(String[] paramArrayOfString1, String[] paramArrayOfString2) {
/*      */     int m, k;
/*  558 */     if (this.state != 1) throw new IllegalStateException("start() called in inappropriate state"); 
/*  559 */     if (paramArrayOfString1 == null) paramArrayOfString1 = new String[] { getClass().getName() };
/*      */     
/*  561 */     int i = this.writePages.length * (1 << this.pageShift), j = i;
/*      */     try {
/*  563 */       j = k = addStringArray(paramArrayOfString1, j);
/*  564 */       j = m = addStringArray(createEnv(paramArrayOfString2), j);
/*  565 */     } catch (FaultException faultException) {
/*  566 */       throw new IllegalArgumentException("args/environ too big");
/*      */     } 
/*  568 */     j &= 0xFFFFFFF0;
/*  569 */     if (i - j > 65536) throw new IllegalArgumentException("args/environ too big");
/*      */ 
/*      */ 
/*      */     
/*  573 */     if (this.heapEnd == 0) {
/*  574 */       this.heapEnd = heapStart();
/*  575 */       if (this.heapEnd == 0) throw new Error("heapEnd == 0"); 
/*  576 */       int n = (this.writePages.length == 1) ? 4096 : (1 << this.pageShift);
/*  577 */       this.heapEnd = this.heapEnd + n - 1 & (n - 1 ^ 0xFFFFFFFF);
/*      */     } 
/*      */     
/*  580 */     CPUState cPUState = new CPUState();
/*  581 */     cPUState.r[4] = k;
/*  582 */     cPUState.r[5] = m;
/*  583 */     cPUState.r[29] = j;
/*  584 */     cPUState.r[31] = -559038737;
/*  585 */     cPUState.r[28] = gp();
/*  586 */     cPUState.pc = entryPoint();
/*  587 */     setCPUState(cPUState);
/*      */     
/*  589 */     this.state = 2;
/*      */     
/*  591 */     _started();
/*      */   }
/*      */   
/*      */   public final void stop() throws ExecutionException {
/*  595 */     if (this.state != 0 && this.state != 2) throw new IllegalStateException("stop() called in inappropriate state"); 
/*  596 */     exit(0, false);
/*      */   }
/*      */ 
/*      */   
/*      */   void _started() throws ExecutionException {}
/*      */   
/*      */   public final int call(String paramString, Object[] paramArrayOfObject) throws CallException, FaultException {
/*  603 */     if (this.state != 2 && this.state != 3) throw new IllegalStateException("call() called in inappropriate state"); 
/*  604 */     if (paramArrayOfObject.length > 7) throw new IllegalArgumentException("args.length > 7"); 
/*  605 */     CPUState cPUState = new CPUState();
/*  606 */     getCPUState(cPUState);
/*      */     
/*  608 */     int i = cPUState.r[29];
/*  609 */     int[] arrayOfInt = new int[paramArrayOfObject.length]; int j;
/*  610 */     for (j = 0; j < paramArrayOfObject.length; j++) {
/*  611 */       Object object = paramArrayOfObject[j];
/*  612 */       byte[] arrayOfByte = null;
/*  613 */       if (object instanceof String) {
/*  614 */         arrayOfByte = getBytes((String)object);
/*  615 */       } else if (object instanceof byte[]) {
/*  616 */         arrayOfByte = (byte[])object;
/*  617 */       } else if (object instanceof Number) {
/*  618 */         arrayOfInt[j] = ((Number)object).intValue();
/*      */       } 
/*  620 */       if (arrayOfByte != null) {
/*  621 */         i -= arrayOfByte.length;
/*  622 */         copyout(arrayOfByte, i, arrayOfByte.length);
/*  623 */         arrayOfInt[j] = i;
/*      */       } 
/*      */     } 
/*  626 */     j = cPUState.r[29];
/*  627 */     if (j == i) return call(paramString, arrayOfInt);
/*      */     
/*  629 */     cPUState.r[29] = i;
/*  630 */     setCPUState(cPUState);
/*  631 */     int k = call(paramString, arrayOfInt);
/*  632 */     cPUState.r[29] = j;
/*  633 */     setCPUState(cPUState);
/*  634 */     return k;
/*      */   }
/*      */   
/*  637 */   public final int call(String paramString) { return call(paramString, new int[0]); }
/*  638 */   public final int call(String paramString, int paramInt) throws CallException { return call(paramString, new int[] { paramInt }); }
/*  639 */   public final int call(String paramString, int paramInt1, int paramInt2) throws CallException { return call(paramString, new int[] { paramInt1, paramInt2 }); }
/*      */ 
/*      */   
/*      */   public final int call(String paramString, int[] paramArrayOfInt) throws CallException {
/*  643 */     int i = lookupSymbol(paramString);
/*  644 */     if (i == -1) throw new CallException(paramString + " not found"); 
/*  645 */     int j = lookupSymbol("_call_helper");
/*  646 */     if (j == -1) throw new CallException("_call_helper not found"); 
/*  647 */     return call(j, i, paramArrayOfInt);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final int call(int paramInt1, int paramInt2, int[] paramArrayOfInt) throws CallException {
/*  654 */     if (paramArrayOfInt.length > 7) throw new IllegalArgumentException("rest.length > 7"); 
/*  655 */     if (this.state != 2 && this.state != 3) throw new IllegalStateException("call() called in inappropriate state"); 
/*  656 */     int i = this.state;
/*  657 */     CPUState cPUState1 = new CPUState();
/*  658 */     getCPUState(cPUState1);
/*  659 */     CPUState cPUState2 = cPUState1.dup();
/*      */     
/*  661 */     cPUState2.r[29] = cPUState2.r[29] & 0xFFFFFFF0;
/*  662 */     cPUState2.r[31] = -559038737;
/*  663 */     cPUState2.r[4] = paramInt2;
/*  664 */     switch (paramArrayOfInt.length) { case 7:
/*  665 */         cPUState2.r[19] = paramArrayOfInt[6];
/*  666 */       case 6: cPUState2.r[18] = paramArrayOfInt[5];
/*  667 */       case 5: cPUState2.r[17] = paramArrayOfInt[4];
/*  668 */       case 4: cPUState2.r[16] = paramArrayOfInt[3];
/*  669 */       case 3: cPUState2.r[7] = paramArrayOfInt[2];
/*  670 */       case 2: cPUState2.r[6] = paramArrayOfInt[1];
/*  671 */       case 1: cPUState2.r[5] = paramArrayOfInt[0]; break; }
/*      */     
/*  673 */     cPUState2.pc = paramInt1;
/*      */     
/*  675 */     this.state = 0;
/*      */     
/*  677 */     setCPUState(cPUState2);
/*  678 */     __execute();
/*  679 */     getCPUState(cPUState2);
/*  680 */     setCPUState(cPUState1);
/*      */     
/*  682 */     if (this.state != 2) throw new CallException("Process exit()ed while servicing a call() request"); 
/*  683 */     this.state = i;
/*      */     
/*  685 */     return cPUState2.r[3];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final int addFD(FD paramFD) {
/*  692 */     if (this.state == 4 || this.state == 5) throw new IllegalStateException("addFD called in inappropriate state"); 
/*      */     byte b;
/*  694 */     for (b = 0; b < 64 && this.fds[b] != null; b++);
/*  695 */     if (b == 64) return -1; 
/*  696 */     this.fds[b] = paramFD;
/*  697 */     this.closeOnExec[b] = false;
/*  698 */     return b;
/*      */   }
/*      */ 
/*      */   
/*      */   void _preCloseFD(FD paramFD) {}
/*      */   
/*      */   void _postCloseFD(FD paramFD) {}
/*      */   
/*      */   public final boolean closeFD(int paramInt) {
/*  707 */     if (this.state == 4 || this.state == 5) throw new IllegalStateException("closeFD called in inappropriate state"); 
/*  708 */     if (paramInt < 0 || paramInt >= 64) return false; 
/*  709 */     if (this.fds[paramInt] == null) return false; 
/*  710 */     _preCloseFD(this.fds[paramInt]);
/*  711 */     this.fds[paramInt].close();
/*  712 */     _postCloseFD(this.fds[paramInt]);
/*  713 */     this.fds[paramInt] = null;
/*  714 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public final int dupFD(int paramInt) throws ReadFaultException {
/*  720 */     if (paramInt < 0 || paramInt >= 64) return -1; 
/*  721 */     if (this.fds[paramInt] == null) return -1;  byte b;
/*  722 */     for (b = 0; b < 64 && this.fds[b] != null; b++);
/*  723 */     if (b == 64) return -1; 
/*  724 */     this.fds[b] = this.fds[paramInt].dup();
/*  725 */     return b;
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
/*      */   FD hostFSOpen(File paramFile, int paramInt1, int paramInt2, Object paramObject) throws ErrnoException
/*      */   {
/*  741 */     if ((paramInt1 & 0xFFFFF1F4) != 0) {
/*      */       
/*  743 */       System.err.println("WARNING: Unsupported flags passed to open(\"" + paramFile + "\"): " + toHex(paramInt1 & 0xFFFFF1F4));
/*  744 */       throw new ErrnoException('');
/*      */     } 
/*  746 */     boolean bool = ((paramInt1 & 0x3) != 0);
/*      */     
/*  748 */     if (this.sm == null || (bool ? this.sm.allowWrite(paramFile) : this.sm.allowRead(paramFile))) {
/*      */       Seekable.File file;
/*  750 */       if ((paramInt1 & 0xA00) == 2560) {
/*      */         try {
/*  752 */           if (!Platform.atomicCreateFile(paramFile)) throw new ErrnoException(17); 
/*  753 */         } catch (IOException null) {
/*  754 */           throw new ErrnoException(5);
/*      */         } 
/*  756 */       } else if (!paramFile.exists()) {
/*  757 */         if ((paramInt1 & 0x200) == 0) return null; 
/*  758 */       } else if (paramFile.isDirectory()) {
/*  759 */         return hostFSDirFD(paramFile, paramObject);
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/*  764 */       try { file = new Seekable.File(paramFile, bool, ((paramInt1 & 0x400) != 0) ? 1 : 0); }
/*  765 */       catch (FileNotFoundException fileNotFoundException)
/*  766 */       { if (fileNotFoundException.getMessage() != null && fileNotFoundException.getMessage().indexOf("Permission denied") >= 0) throw new ErrnoException(13); 
/*  767 */         return null; }
/*  768 */       catch (IOException iOException) { throw new ErrnoException(5); }
/*      */       
/*  770 */       return new SeekableFD(this, file, paramInt1, paramFile, file, paramObject) { private final File val$f; private final Seekable.File val$sf; protected Runtime.FStat _fstat() { return this.this$0.hostFStat(this.val$f, this.val$sf, this.val$data); } private final Object val$data; private final Runtime this$0; }
/*      */         ;
/*      */     } 
/*  773 */     throw new ErrnoException(13); } FStat hostFStat(File paramFile, Seekable.File paramFile1, Object paramObject) { return new HostFStat(paramFile, paramFile1); }
/*      */   
/*  775 */   FD hostFSDirFD(File paramFile, Object paramObject) { return null; }
/*      */ 
/*      */   
/*  778 */   FD _open(String paramString, int paramInt1, int paramInt2) throws ErrnoException { return hostFSOpen(new File(paramString), paramInt1, paramInt2, null); }
/*      */ 
/*      */ 
/*      */   
/*      */   private int sys_open(int paramInt1, int paramInt2, int paramInt3) throws ErrnoException, FaultException {
/*  783 */     String str = cstring(paramInt1);
/*      */ 
/*      */     
/*  786 */     if (str.length() == 1024 && getClass().getName().equals("tests.TeX")) str = str.trim();
/*      */     
/*  788 */     paramInt2 &= 0xFFFF7FFF;
/*  789 */     FD fD = _open(str, paramInt2, paramInt3);
/*  790 */     if (fD == null) return -2; 
/*  791 */     int i = addFD(fD);
/*  792 */     if (i == -1) { fD.close(); return -23; }
/*  793 */      return i;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private int sys_write(int paramInt1, int paramInt2, int paramInt3) throws ErrnoException, FaultException {
/*  799 */     paramInt3 = Math.min(paramInt3, 16776192);
/*  800 */     if (paramInt1 < 0 || paramInt1 >= 64) return -81; 
/*  801 */     if (this.fds[paramInt1] == null) return -81; 
/*  802 */     byte[] arrayOfByte = byteBuf(paramInt3);
/*  803 */     copyin(paramInt2, arrayOfByte, paramInt3);
/*      */     try {
/*  805 */       return this.fds[paramInt1].write(arrayOfByte, 0, paramInt3);
/*  806 */     } catch (ErrnoException errnoException) {
/*  807 */       if (errnoException.errno == 32) sys_exit(141); 
/*  808 */       throw errnoException;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private int sys_read(int paramInt1, int paramInt2, int paramInt3) throws ErrnoException, FaultException {
/*  814 */     paramInt3 = Math.min(paramInt3, 16776192);
/*  815 */     if (paramInt1 < 0 || paramInt1 >= 64) return -81; 
/*  816 */     if (this.fds[paramInt1] == null) return -81; 
/*  817 */     byte[] arrayOfByte = byteBuf(paramInt3);
/*  818 */     int i = this.fds[paramInt1].read(arrayOfByte, 0, paramInt3);
/*  819 */     copyout(arrayOfByte, paramInt2, i);
/*  820 */     return i;
/*      */   }
/*      */ 
/*      */   
/*      */   private int sys_ftruncate(int paramInt, long paramLong) {
/*  825 */     if (paramInt < 0 || paramInt >= 64) return -81; 
/*  826 */     if (this.fds[paramInt] == null) return -81;
/*      */     
/*  828 */     Seekable seekable = this.fds[paramInt].seekable();
/*  829 */     if (paramLong < 0L || seekable == null) return -22;  
/*  830 */     try { seekable.resize(paramLong); } catch (IOException iOException) { return -5; }
/*  831 */      return 0;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*  836 */   private int sys_close(int paramInt) throws ReadFaultException { return closeFD(paramInt) ? 0 : -81; }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int sys_lseek(int paramInt1, int paramInt2, int paramInt3) throws ErrnoException, FaultException {
/*  842 */     if (paramInt1 < 0 || paramInt1 >= 64) return -81; 
/*  843 */     if (this.fds[paramInt1] == null) return -81; 
/*  844 */     if (paramInt3 != 0 && paramInt3 != 1 && paramInt3 != 2) return -22; 
/*  845 */     int i = this.fds[paramInt1].seek(paramInt2, paramInt3);
/*  846 */     return (i < 0) ? -29 : i;
/*      */   }
/*      */ 
/*      */   
/*      */   int stat(FStat paramFStat, int paramInt) throws FaultException {
/*  851 */     memWrite(paramInt + 0, paramFStat.dev() << 16 | paramFStat.inode() & 0xFFFF);
/*  852 */     memWrite(paramInt + 4, paramFStat.type() & 0xF000 | paramFStat.mode() & 0xFFF);
/*  853 */     memWrite(paramInt + 8, paramFStat.nlink() << 16 | paramFStat.uid() & 0xFFFF);
/*  854 */     memWrite(paramInt + 12, paramFStat.gid() << 16 | false);
/*  855 */     memWrite(paramInt + 16, paramFStat.size());
/*  856 */     memWrite(paramInt + 20, paramFStat.atime());
/*      */     
/*  858 */     memWrite(paramInt + 28, paramFStat.mtime());
/*      */     
/*  860 */     memWrite(paramInt + 36, paramFStat.ctime());
/*      */     
/*  862 */     memWrite(paramInt + 44, paramFStat.blksize());
/*  863 */     memWrite(paramInt + 48, paramFStat.blocks());
/*      */ 
/*      */     
/*  866 */     return 0;
/*      */   }
/*      */ 
/*      */   
/*      */   private int sys_fstat(int paramInt1, int paramInt2) throws FaultException {
/*  871 */     if (paramInt1 < 0 || paramInt1 >= 64) return -81; 
/*  872 */     if (this.fds[paramInt1] == null) return -81; 
/*  873 */     return stat(this.fds[paramInt1].fstat(), paramInt2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int sys_gettimeofday(int paramInt1, int paramInt2) throws FaultException {
/*  883 */     long l = System.currentTimeMillis();
/*  884 */     int i = (int)(l / 1000L);
/*  885 */     int j = (int)(l % 1000L * 1000L);
/*  886 */     memWrite(paramInt1 + 0, i);
/*  887 */     memWrite(paramInt1 + 4, j);
/*  888 */     return 0;
/*      */   }
/*      */   
/*      */   private int sys_sleep(int paramInt) throws ReadFaultException {
/*  892 */     if (paramInt < 0) paramInt = Integer.MAX_VALUE; 
/*      */     try {
/*  894 */       Thread.sleep(paramInt * 1000L);
/*  895 */       return 0;
/*  896 */     } catch (InterruptedException interruptedException) {
/*  897 */       return -1;
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
/*      */   private int sys_times(int paramInt) throws ReadFaultException {
/*  912 */     long l = System.currentTimeMillis();
/*  913 */     int i = (int)((l - this.startTime) / 16L);
/*  914 */     int j = (int)((l - this.startTime) / 16L);
/*      */     
/*      */     try {
/*  917 */       if (paramInt != 0) {
/*  918 */         memWrite(paramInt + 0, i);
/*  919 */         memWrite(paramInt + 4, j);
/*  920 */         memWrite(paramInt + 8, i);
/*  921 */         memWrite(paramInt + 12, j);
/*      */       } 
/*  923 */     } catch (FaultException faultException) {
/*  924 */       return -14;
/*      */     } 
/*  926 */     return (int)l;
/*      */   }
/*      */   
/*      */   private int sys_sysconf(int paramInt) throws ReadFaultException {
/*  930 */     switch (paramInt) { case 2:
/*  931 */         return 1000;
/*  932 */       case 8: return (this.writePages.length == 1) ? 4096 : (1 << this.pageShift);
/*  933 */       case 11: return (this.writePages.length == 1) ? ((1 << this.pageShift) / 4096) : this.writePages.length; }
/*      */     
/*  935 */     System.err.println("WARNING: Attempted to use unknown sysconf key: " + paramInt);
/*  936 */     return -22;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final int sbrk(int paramInt) throws ReadFaultException {
/*  943 */     if (paramInt < 0) return -12; 
/*  944 */     if (paramInt == 0) return this.heapEnd; 
/*  945 */     paramInt = paramInt + 3 & 0xFFFFFFFC;
/*  946 */     int i = this.heapEnd;
/*  947 */     int j = i + paramInt;
/*  948 */     if (j >= this.stackBottom) return -12;
/*      */     
/*  950 */     if (this.writePages.length > 1) {
/*  951 */       int k = (1 << this.pageShift) - 1;
/*  952 */       int m = 1 << this.pageShift >>> 2;
/*  953 */       int n = i + k >>> this.pageShift;
/*  954 */       int i1 = j + k >>> this.pageShift;
/*      */       try {
/*  956 */         for (int i2 = n; i2 < i1; ) { this.writePages[i2] = new int[m]; this.readPages[i2] = new int[m]; i2++; } 
/*  957 */       } catch (OutOfMemoryError outOfMemoryError) {
/*  958 */         System.err.println("WARNING: Caught OOM Exception in sbrk: " + outOfMemoryError);
/*  959 */         return -12;
/*      */       } 
/*      */     } 
/*  962 */     this.heapEnd = j;
/*  963 */     return i;
/*      */   }
/*      */ 
/*      */   
/*  967 */   private int sys_getpid() { return getPid(); }
/*  968 */   int getPid() { return 1; }
/*      */ 
/*      */ 
/*      */   
/*      */   private int sys_calljava(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/*  973 */     if (this.state != 0) throw new IllegalStateException("wound up calling sys_calljava while not in RUNNING"); 
/*  974 */     if (this.callJavaCB != null) {
/*  975 */       byte b; this.state = 3;
/*      */       
/*      */       try {
/*  978 */         b = this.callJavaCB.call(paramInt1, paramInt2, paramInt3, paramInt4);
/*  979 */       } catch (RuntimeException runtimeException) {
/*  980 */         System.err.println("Error while executing callJavaCB");
/*  981 */         runtimeException.printStackTrace();
/*  982 */         b = 0;
/*      */       } 
/*  984 */       this.state = 0;
/*  985 */       return b;
/*      */     } 
/*  987 */     System.err.println("WARNING: calljava syscall invoked without a calljava callback set");
/*  988 */     return 0;
/*      */   }
/*      */ 
/*      */   
/*      */   private int sys_pause() {
/*  993 */     this.state = 2;
/*  994 */     return 0;
/*      */   }
/*      */   
/*  997 */   private int sys_getpagesize() { return (this.writePages.length == 1) ? 4096 : (1 << this.pageShift); }
/*      */ 
/*      */   
/*      */   void _exited() throws ExecutionException {}
/*      */   
/*      */   void exit(int paramInt, boolean paramBoolean) {
/* 1003 */     if (paramBoolean && this.fds[2] != null) {
/*      */       try {
/* 1005 */         byte[] arrayOfByte = getBytes("Process exited on signal " + (paramInt - 128) + "\n");
/* 1006 */         this.fds[2].write(arrayOfByte, 0, arrayOfByte.length);
/* 1007 */       } catch (ErrnoException errnoException) {}
/*      */     }
/* 1009 */     this.exitStatus = paramInt;
/* 1010 */     for (byte b = 0; b < this.fds.length; ) { if (this.fds[b] != null) closeFD(b);  b++; }
/* 1011 */      this.state = 4;
/* 1012 */     _exited();
/*      */   }
/*      */   
/*      */   private int sys_exit(int paramInt) throws ReadFaultException {
/* 1016 */     exit(paramInt, false);
/* 1017 */     return 0;
/*      */   }
/*      */ 
/*      */   
/*      */   final int sys_fcntl(int paramInt1, int paramInt2, int paramInt3) throws ErrnoException, FaultException {
/*      */     int i;
/* 1023 */     if (paramInt1 < 0 || paramInt1 >= 64) return -81; 
/* 1024 */     if (this.fds[paramInt1] == null) return -81; 
/* 1025 */     FD fD = this.fds[paramInt1];
/*      */     
/* 1027 */     switch (paramInt2) {
/*      */       case 0:
/* 1029 */         if (paramInt3 < 0 || paramInt3 >= 64) return -22; 
/* 1030 */         for (i = paramInt3; i < 64 && this.fds[i] != null; i++);
/* 1031 */         if (i == 64) return -24; 
/* 1032 */         this.fds[i] = fD.dup();
/* 1033 */         return i;
/*      */       case 3:
/* 1035 */         return fD.flags();
/*      */       case 2:
/* 1037 */         this.closeOnExec[paramInt1] = (paramInt3 != 0);
/* 1038 */         return 0;
/*      */       case 1:
/* 1040 */         return this.closeOnExec[paramInt1] ? 1 : 0;
/*      */       case 7:
/*      */       case 8:
/* 1043 */         System.err.println("WARNING: file locking requires UnixRuntime");
/* 1044 */         return -88;
/*      */     } 
/* 1046 */     System.err.println("WARNING: Unknown fcntl command: " + paramInt2);
/* 1047 */     return -88;
/*      */   }
/*      */ 
/*      */   
/*      */   final int fsync(int paramInt) throws ReadFaultException {
/* 1052 */     if (paramInt < 0 || paramInt >= 64) return -81; 
/* 1053 */     if (this.fds[paramInt] == null) return -81; 
/* 1054 */     FD fD = this.fds[paramInt];
/*      */     
/* 1056 */     Seekable seekable = fD.seekable();
/* 1057 */     if (seekable == null) return -22;
/*      */     
/*      */     try {
/* 1060 */       seekable.sync();
/* 1061 */       return 0;
/* 1062 */     } catch (IOException iOException) {
/* 1063 */       return -5;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final int syscall(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7) {
/*      */     try {
/* 1074 */       return _syscall(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, paramInt7);
/*      */     
/*      */     }
/* 1077 */     catch (ErrnoException errnoException) {
/*      */ 
/*      */ 
/*      */       
/* 1081 */       return -errnoException.errno;
/* 1082 */     } catch (FaultException faultException) {
/* 1083 */       return -14;
/* 1084 */     } catch (RuntimeException runtimeException) {
/* 1085 */       runtimeException.printStackTrace();
/* 1086 */       throw new Error("Internal Error in _syscall()");
/*      */     } 
/*      */   }
/*      */   
/*      */   int _syscall(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7) {
/* 1091 */     switch (paramInt1) { case 0:
/* 1092 */         return 0;
/* 1093 */       case 1: return sys_exit(paramInt2);
/* 1094 */       case 2: return sys_pause();
/* 1095 */       case 6: return sys_write(paramInt2, paramInt3, paramInt4);
/* 1096 */       case 8: return sys_fstat(paramInt2, paramInt3);
/* 1097 */       case 7: return sbrk(paramInt2);
/* 1098 */       case 3: return sys_open(paramInt2, paramInt3, paramInt4);
/* 1099 */       case 4: return sys_close(paramInt2);
/* 1100 */       case 5: return sys_read(paramInt2, paramInt3, paramInt4);
/* 1101 */       case 10: return sys_lseek(paramInt2, paramInt3, paramInt4);
/* 1102 */       case 44: return sys_ftruncate(paramInt2, paramInt3);
/* 1103 */       case 12: return sys_getpid();
/* 1104 */       case 13: return sys_calljava(paramInt2, paramInt3, paramInt4, paramInt5);
/* 1105 */       case 15: return sys_gettimeofday(paramInt2, paramInt3);
/* 1106 */       case 16: return sys_sleep(paramInt2);
/* 1107 */       case 17: return sys_times(paramInt2);
/* 1108 */       case 19: return sys_getpagesize();
/* 1109 */       case 29: return sys_fcntl(paramInt2, paramInt3, paramInt4);
/* 1110 */       case 31: return sys_sysconf(paramInt2);
/* 1111 */       case 68: return sys_getuid();
/* 1112 */       case 70: return sys_geteuid();
/* 1113 */       case 69: return sys_getgid();
/* 1114 */       case 71: return sys_getegid();
/*      */       case 91:
/* 1116 */         return fsync(paramInt2);
/* 1117 */       case 37: memcpy(paramInt2, paramInt3, paramInt4); return paramInt2;
/* 1118 */       case 38: memset(paramInt2, paramInt3, paramInt4); return paramInt2;
/*      */       
/*      */       case 11:
/*      */       case 14:
/*      */       case 18:
/*      */       case 22:
/*      */       case 23:
/*      */       case 24:
/*      */       case 25:
/*      */       case 26:
/*      */       case 27:
/* 1129 */         System.err.println("Attempted to use a UnixRuntime syscall in Runtime (" + paramInt1 + ")");
/* 1130 */         return -88; }
/*      */     
/* 1132 */     System.err.println("Attempted to use unknown syscall: " + paramInt1);
/* 1133 */     return -88;
/*      */   }
/*      */ 
/*      */   
/* 1137 */   private int sys_getuid() { return 0; }
/* 1138 */   private int sys_geteuid() { return 0; }
/* 1139 */   private int sys_getgid() { return 0; }
/* 1140 */   private int sys_getegid() { return 0; }
/*      */   
/* 1142 */   public int xmalloc(int paramInt) throws ReadFaultException { int i = malloc(paramInt); if (i == 0) throw new RuntimeException("malloc() failed");  return i; }
/* 1143 */   public int xrealloc(int paramInt1, int paramInt2) throws FaultException { int i = realloc(paramInt1, paramInt2); if (i == 0) throw new RuntimeException("realloc() failed");  return i; } public int realloc(int paramInt1, int paramInt2) throws FaultException { 
/* 1144 */     try { return call("realloc", paramInt1, paramInt2); } catch (CallException callException) { return 0; }
/* 1145 */      } public int malloc(int paramInt) throws ReadFaultException { try { return call("malloc", paramInt); } catch (CallException callException) { return 0; }
/* 1146 */      } public void free(int paramInt) { try { if (paramInt != 0) call("free", paramInt);  } catch (CallException callException) {} }
/*      */ 
/*      */ 
/*      */   
/*      */   public int strdup(String paramString) {
/* 1151 */     if (paramString == null) paramString = "(null)"; 
/* 1152 */     byte[] arrayOfByte2 = getBytes(paramString);
/* 1153 */     byte[] arrayOfByte1 = new byte[arrayOfByte2.length + 1];
/* 1154 */     System.arraycopy(arrayOfByte2, 0, arrayOfByte1, 0, arrayOfByte2.length);
/* 1155 */     int i = malloc(arrayOfByte1.length);
/* 1156 */     if (i == 0) return 0; 
/*      */     try {
/* 1158 */       copyout(arrayOfByte1, i, arrayOfByte1.length);
/* 1159 */     } catch (FaultException faultException) {
/* 1160 */       free(i);
/* 1161 */       return 0;
/*      */     } 
/* 1163 */     return i;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public final String utfstring(int paramInt) throws ReadFaultException {
/* 1169 */     if (paramInt == 0) return null;
/*      */ 
/*      */     
/* 1172 */     int i = paramInt;
/* 1173 */     for (null = 1; null; i++) {
/* 1174 */       null = memRead(i & 0xFFFFFFFC);
/* 1175 */       switch (i & 0x3) { case 0:
/* 1176 */           null = null >>> 24 & 0xFF; break;
/* 1177 */         case 1: null = null >>> 16 & 0xFF; break;
/* 1178 */         case 2: null = null >>> 8 & 0xFF; break;
/* 1179 */         case 3: null = null >>> 0 & 0xFF; break; }
/*      */     
/*      */     } 
/* 1182 */     if (i > paramInt) i--;
/*      */     
/* 1184 */     byte[] arrayOfByte = new byte[i - paramInt];
/* 1185 */     copyin(paramInt, arrayOfByte, arrayOfByte.length);
/*      */     
/*      */     try {
/* 1188 */       return new String(arrayOfByte, "UTF-8");
/* 1189 */     } catch (UnsupportedEncodingException unsupportedEncodingException) {
/* 1190 */       throw new RuntimeException(unsupportedEncodingException);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public final String cstring(int paramInt) throws ReadFaultException {
/* 1196 */     if (paramInt == 0) return null; 
/* 1197 */     StringBuffer stringBuffer = new StringBuffer();
/*      */     while (true) {
/* 1199 */       int i = memRead(paramInt & 0xFFFFFFFC);
/* 1200 */       switch (paramInt & 0x3) { case 0:
/* 1201 */           if ((i >>> 24 & 0xFF) == 0) return stringBuffer.toString();  stringBuffer.append((char)(i >>> 24 & 0xFF)); paramInt++;
/* 1202 */         case 1: if ((i >>> 16 & 0xFF) == 0) return stringBuffer.toString();  stringBuffer.append((char)(i >>> 16 & 0xFF)); paramInt++;
/* 1203 */         case 2: if ((i >>> 8 & 0xFF) == 0) return stringBuffer.toString();  stringBuffer.append((char)(i >>> 8 & 0xFF)); paramInt++; break;case 3: break;
/* 1204 */         default: continue; }  if ((i >>> 0 & 0xFF) == 0) return stringBuffer.toString();  stringBuffer.append((char)(i >>> 0 & 0xFF)); paramInt++;
/*      */     } 
/*      */   }
/*      */   
/*      */   public static interface CallJavaCB {
/*      */     int call(int param1Int1, int param1Int2, int param1Int3, int param1Int4); }
/*      */   
/* 1211 */   public static abstract class FD { private int refCount = 1;
/* 1212 */     private String normalizedPath = null;
/*      */     private boolean deleteOnClose = false;
/*      */     
/* 1215 */     public void setNormalizedPath(String param1String) { this.normalizedPath = param1String; }
/* 1216 */     public String getNormalizedPath() { return this.normalizedPath; }
/*      */     
/* 1218 */     public void markDeleteOnClose() throws Runtime.ExecutionException { this.deleteOnClose = true; }
/* 1219 */     public boolean isMarkedForDeleteOnClose() { return this.deleteOnClose; }
/*      */ 
/*      */     
/* 1222 */     public int read(byte[] param1ArrayOfByte, int param1Int1, int param1Int2) throws IOException { throw new Runtime.ErrnoException(81); }
/*      */     
/* 1224 */     public int write(byte[] param1ArrayOfByte, int param1Int1, int param1Int2) throws IOException { throw new Runtime.ErrnoException(81); }
/*      */ 
/*      */     
/* 1227 */     public int seek(int param1Int1, int param1Int2) throws Runtime.FaultException { return -1; }
/*      */     
/* 1229 */     public int getdents(byte[] param1ArrayOfByte, int param1Int1, int param1Int2) throws IOException { throw new Runtime.ErrnoException(81); }
/*      */ 
/*      */ 
/*      */     
/* 1233 */     Seekable seekable() { return null; }
/*      */     
/* 1235 */     private Runtime.FStat cachedFStat = null;
/*      */     public final Runtime.FStat fstat() {
/* 1237 */       if (this.cachedFStat == null) this.cachedFStat = _fstat(); 
/* 1238 */       return this.cachedFStat;
/*      */     }
/*      */     
/*      */     protected abstract Runtime.FStat _fstat();
/*      */     
/*      */     public abstract int flags();
/*      */     
/* 1245 */     public final void close() throws Runtime.ExecutionException { if (--this.refCount == 0) _close();  }
/*      */     protected void _close() throws Runtime.ExecutionException {}
/*      */     
/* 1248 */     FD dup() { this.refCount++; return this; } }
/*      */ 
/*      */   
/*      */   public static abstract class SeekableFD
/*      */     extends FD {
/*      */     private final int flags;
/*      */     private final Seekable data;
/*      */     
/* 1256 */     SeekableFD(Seekable param1Seekable, int param1Int) { this.data = param1Seekable; this.flags = param1Int; }
/*      */     protected abstract Runtime.FStat _fstat();
/*      */     
/* 1259 */     public int flags() { return this.flags; }
/*      */     
/* 1261 */     Seekable seekable() { return this.data; }
/*      */     
/*      */     public int seek(int param1Int1, int param1Int2) throws Runtime.FaultException {
/*      */       try {
/* 1265 */         switch (param1Int2) { case 0: break;
/*      */           case 1:
/* 1267 */             param1Int1 += this.data.pos(); break;
/* 1268 */           case 2: param1Int1 += this.data.length(); break;
/* 1269 */           default: return -1; }
/*      */         
/* 1271 */         this.data.seek(param1Int1);
/* 1272 */         return param1Int1;
/* 1273 */       } catch (IOException iOException) {
/* 1274 */         throw new Runtime.ErrnoException(29);
/*      */       } 
/*      */     }
/*      */     
/*      */     public int write(byte[] param1ArrayOfByte, int param1Int1, int param1Int2) throws IOException {
/* 1279 */       if ((this.flags & 0x3) == 0) throw new Runtime.ErrnoException(81);
/*      */       
/* 1281 */       if ((this.flags & 0x8) != 0) seek(0, 2); 
/*      */       try {
/* 1283 */         return this.data.write(param1ArrayOfByte, param1Int1, param1Int2);
/* 1284 */       } catch (IOException iOException) {
/* 1285 */         throw new Runtime.ErrnoException(5);
/*      */       } 
/*      */     }
/*      */     
/*      */     public int read(byte[] param1ArrayOfByte, int param1Int1, int param1Int2) throws IOException {
/* 1290 */       if ((this.flags & 0x3) == 1) throw new Runtime.ErrnoException(81); 
/*      */       try {
/* 1292 */         int i = this.data.read(param1ArrayOfByte, param1Int1, param1Int2);
/* 1293 */         return (i < 0) ? 0 : i;
/* 1294 */       } catch (IOException iOException) {
/* 1295 */         throw new Runtime.ErrnoException(5);
/*      */       } 
/*      */     } protected void _close() throws Runtime.ExecutionException {
/*      */       
/* 1299 */       try { this.data.close(); } catch (IOException iOException) {}
/*      */     } }
/*      */   
/*      */   public static class InputOutputStreamFD extends FD {
/*      */     private final InputStream is;
/*      */     private final OutputStream os;
/*      */     
/* 1306 */     public InputOutputStreamFD(InputStream param1InputStream) { this(param1InputStream, null); }
/* 1307 */     public InputOutputStreamFD(OutputStream param1OutputStream) { this(null, param1OutputStream); }
/*      */     public InputOutputStreamFD(InputStream param1InputStream, OutputStream param1OutputStream) {
/* 1309 */       this.is = param1InputStream;
/* 1310 */       this.os = param1OutputStream;
/* 1311 */       if (param1InputStream == null && param1OutputStream == null) throw new IllegalArgumentException("at least one stream must be supplied"); 
/*      */     }
/*      */     
/*      */     public int flags() {
/* 1315 */       if (this.is != null && this.os != null) return 2; 
/* 1316 */       if (this.is != null) return 0; 
/* 1317 */       if (this.os != null) return 1; 
/* 1318 */       throw new Error("should never happen");
/*      */     }
/*      */     
/*      */     public void _close() throws Runtime.ExecutionException {
/* 1322 */       if (this.is != null) try { this.is.close(); } catch (IOException iOException) {} 
/* 1323 */       if (this.os != null) try { this.os.close(); } catch (IOException iOException) {} 
/*      */     }
/*      */     
/*      */     public int read(byte[] param1ArrayOfByte, int param1Int1, int param1Int2) throws IOException {
/* 1327 */       if (this.is == null) return super.read(param1ArrayOfByte, param1Int1, param1Int2); 
/*      */       try {
/* 1329 */         int i = this.is.read(param1ArrayOfByte, param1Int1, param1Int2);
/* 1330 */         return (i < 0) ? 0 : i;
/* 1331 */       } catch (IOException iOException) {
/* 1332 */         throw new Runtime.ErrnoException(5);
/*      */       } 
/*      */     }
/*      */     
/*      */     public int write(byte[] param1ArrayOfByte, int param1Int1, int param1Int2) throws IOException {
/* 1337 */       if (this.os == null) return super.write(param1ArrayOfByte, param1Int1, param1Int2); 
/*      */       try {
/* 1339 */         this.os.write(param1ArrayOfByte, param1Int1, param1Int2);
/* 1340 */         return param1Int2;
/* 1341 */       } catch (IOException iOException) {
/* 1342 */         throw new Runtime.ErrnoException(5);
/*      */       } 
/*      */     }
/*      */     
/* 1346 */     public Runtime.FStat _fstat() { return new Runtime.SocketFStat(); }
/*      */   }
/*      */   
/*      */   static class TerminalFD extends InputOutputStreamFD {
/* 1350 */     public TerminalFD(InputStream param1InputStream) { this(param1InputStream, null); }
/* 1351 */     public TerminalFD(OutputStream param1OutputStream) { this(null, param1OutputStream); }
/* 1352 */     public TerminalFD(InputStream param1InputStream, OutputStream param1OutputStream) { super(param1InputStream, param1OutputStream); } public void _close() throws Runtime.ExecutionException {}
/*      */     public Runtime.FStat _fstat() {
/* 1354 */       return new Runtime.SocketFStat(this) { private final Runtime.TerminalFD this$0; public int type() { return 8192; } public int mode() { return 384; } }
/*      */         ;
/*      */     } }
/*      */   static class Win32ConsoleIS extends InputStream { private int pushedBack; private final InputStream parent;
/*      */     public Win32ConsoleIS(InputStream param1InputStream) {
/* 1359 */       this.pushedBack = -1;
/*      */       
/* 1361 */       this.parent = param1InputStream;
/*      */     } public int read() {
/* 1363 */       if (this.pushedBack != -1) { int j = this.pushedBack; this.pushedBack = -1; return j; }
/* 1364 */        int i = this.parent.read();
/* 1365 */       if (i == 13 && (i = this.parent.read()) != 10) { this.pushedBack = i; return 13; }
/* 1366 */        return i;
/*      */     }
/*      */     public int read(byte[] param1ArrayOfByte, int param1Int1, int param1Int2) throws IOException {
/* 1369 */       boolean bool = false;
/* 1370 */       if (this.pushedBack != -1 && param1Int2 > 0) {
/* 1371 */         param1ArrayOfByte[0] = (byte)this.pushedBack;
/* 1372 */         this.pushedBack = -1;
/* 1373 */         param1Int1++; param1Int2--; bool = true;
/*      */       } 
/* 1375 */       int i = this.parent.read(param1ArrayOfByte, param1Int1, param1Int2);
/* 1376 */       if (i == -1) return bool ? 1 : -1; 
/* 1377 */       for (int j = 0; j < i; j++) {
/* 1378 */         if (param1ArrayOfByte[param1Int1 + j] == 13) {
/* 1379 */           if (j == i - 1) {
/* 1380 */             int k = this.parent.read();
/* 1381 */             if (k == 10) { param1ArrayOfByte[param1Int1 + j] = 10; }
/* 1382 */             else { this.pushedBack = k; } 
/* 1383 */           } else if (param1ArrayOfByte[param1Int1 + j + 1] == 10) {
/* 1384 */             System.arraycopy(param1ArrayOfByte, param1Int1 + j + 1, param1ArrayOfByte, param1Int1 + j, param1Int2 - j - 1);
/* 1385 */             i--;
/*      */           } 
/*      */         }
/*      */       } 
/* 1389 */       return i + (bool ? 1 : 0);
/*      */     } }
/*      */ 
/*      */   
/*      */   public static abstract class FStat {
/*      */     public static final int S_IFIFO = 4096;
/*      */     public static final int S_IFCHR = 8192;
/*      */     public static final int S_IFDIR = 16384;
/*      */     public static final int S_IFREG = 32768;
/*      */     public static final int S_IFSOCK = 49152;
/*      */     
/* 1400 */     public int mode() { return 0; }
/* 1401 */     public int nlink() { return 0; }
/* 1402 */     public int uid() { return 0; }
/* 1403 */     public int gid() { return 0; }
/* 1404 */     public int size() { return 0; }
/* 1405 */     public int atime() { return 0; }
/* 1406 */     public int mtime() { return 0; }
/* 1407 */     public int ctime() { return 0; }
/* 1408 */     public int blksize() { return 512; }
/* 1409 */     public int blocks() { return (size() + blksize() - 1) / blksize(); }
/*      */     
/*      */     public abstract int dev();
/*      */     
/*      */     public abstract int type();
/*      */     
/*      */     public abstract int inode(); }
/*      */   
/* 1417 */   public static class SocketFStat extends FStat { public int dev() { return -1; }
/* 1418 */     public int type() { return 49152; }
/* 1419 */     public int inode() { return hashCode() & 0x7FFF; } }
/*      */   
/*      */   static class HostFStat extends FStat {
/*      */     private final File f;
/*      */     private final Seekable.File sf;
/*      */     private final boolean executable;
/*      */     
/* 1426 */     public HostFStat(File param1File, Seekable.File param1File1) { this(param1File, param1File1, false); }
/* 1427 */     public HostFStat(File param1File, boolean param1Boolean) { this(param1File, null, param1Boolean); }
/*      */     public HostFStat(File param1File, Seekable.File param1File1, boolean param1Boolean) {
/* 1429 */       this.f = param1File;
/* 1430 */       this.sf = param1File1;
/* 1431 */       this.executable = param1Boolean;
/*      */     }
/* 1433 */     public int dev() { return 1; }
/* 1434 */     public int inode() { return this.f.getAbsolutePath().hashCode() & 0x7FFF; }
/* 1435 */     public int type() { return this.f.isDirectory() ? 16384 : 32768; }
/* 1436 */     public int nlink() { return 1; }
/*      */     public int mode() {
/* 1438 */       char c = Character.MIN_VALUE;
/* 1439 */       boolean bool = this.f.canRead();
/* 1440 */       if (bool && (this.executable || this.f.isDirectory())) c |= 0x49; 
/* 1441 */       if (bool) c |= 0x124; 
/* 1442 */       if (this.f.canWrite()) c |= 0x92; 
/* 1443 */       return c;
/*      */     }
/*      */     public int size() {
/*      */       try {
/* 1447 */         return (this.sf != null) ? this.sf.length() : (int)this.f.length();
/* 1448 */       } catch (Exception exception) {
/* 1449 */         return (int)this.f.length();
/*      */       } 
/*      */     }
/* 1452 */     public int mtime() { return (int)(this.f.lastModified() / 1000L); }
/*      */   }
/*      */   
/*      */   public static class ReadFaultException
/*      */     extends FaultException {
/* 1457 */     public ReadFaultException(int param1Int) { super(param1Int); }
/*      */   }
/*      */   
/* 1460 */   public static class WriteFaultException extends FaultException { public WriteFaultException(int param1Int) { super(param1Int); } }
/*      */   
/*      */   public static class FaultException extends ExecutionException { public final int addr;
/*      */     public final RuntimeException cause;
/*      */     
/* 1465 */     public FaultException(int param1Int) { super("fault at: " + Runtime.toHex(param1Int)); this.addr = param1Int; this.cause = null; }
/* 1466 */     public FaultException(RuntimeException param1RuntimeException) { super(param1RuntimeException.toString()); this.addr = -1; this.cause = param1RuntimeException; } }
/*      */   
/*      */   public static class ExecutionException extends Exception {
/* 1469 */     private String message = "(null)";
/* 1470 */     private String location = "(unknown)";
/*      */     public ExecutionException() throws ExecutionException {}
/* 1472 */     public ExecutionException(String param1String) { if (param1String != null) this.message = param1String;  }
/* 1473 */     void setLocation(String param1String) { this.location = (param1String == null) ? "(unknown)" : param1String; }
/* 1474 */     public final String getMessage() { return this.message + " at " + this.location; }
/*      */   }
/*      */   
/* 1477 */   public static class CallException extends Exception { public CallException(String param1String) { super(param1String); } }
/*      */   
/*      */   protected static class ErrnoException extends Exception {
/*      */     public int errno;
/*      */     
/* 1482 */     public ErrnoException(int param1Int) { super("Errno: " + param1Int); this.errno = param1Int; }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected static class CPUState
/*      */   {
/* 1489 */     public int[] r = new int[32];
/*      */     
/* 1491 */     public int[] f = new int[32]; public int hi;
/*      */     public int lo;
/*      */     public int fcsr;
/*      */     public int pc;
/*      */     
/*      */     public CPUState dup() {
/* 1497 */       CPUState cPUState = new CPUState();
/* 1498 */       cPUState.hi = this.hi;
/* 1499 */       cPUState.lo = this.lo;
/* 1500 */       cPUState.fcsr = this.fcsr;
/* 1501 */       cPUState.pc = this.pc;
/* 1502 */       for (byte b = 0; b < 32; b++) {
/* 1503 */         cPUState.r[b] = this.r[b];
/* 1504 */         cPUState.f[b] = this.f[b];
/*      */       } 
/* 1506 */       return cPUState;
/*      */     }
/*      */   }
/*      */   
/*      */   public static class SecurityManager {
/* 1511 */     public boolean allowRead(File param1File) { return true; }
/* 1512 */     public boolean allowWrite(File param1File) { return true; }
/* 1513 */     public boolean allowStat(File param1File) { return true; }
/* 1514 */     public boolean allowUnlink(File param1File) { return true; }
/*      */   }
/*      */ 
/*      */   
/*      */   protected final void nullPointerCheck(int paramInt) {
/* 1519 */     if (paramInt < 65536) {
/* 1520 */       throw new ExecutionException("Attempted to dereference a null pointer " + toHex(paramInt));
/*      */     }
/*      */   }
/*      */   
/*      */   byte[] byteBuf(int paramInt) {
/* 1525 */     if (this._byteBuf == null) { this._byteBuf = new byte[paramInt]; }
/* 1526 */     else if (this._byteBuf.length < paramInt)
/* 1527 */     { this._byteBuf = new byte[min(max(this._byteBuf.length * 2, paramInt), 16776192)]; }
/* 1528 */      return this._byteBuf;
/*      */   }
/*      */ 
/*      */   
/*      */   protected static final int[] decodeData(String paramString, int paramInt) {
/* 1533 */     if (paramString.length() % 8 != 0) throw new IllegalArgumentException("string length must be a multiple of 8"); 
/* 1534 */     if (paramString.length() / 8 * 7 < paramInt * 4) throw new IllegalArgumentException("string isn't big enough"); 
/* 1535 */     int[] arrayOfInt = new int[paramInt];
/* 1536 */     int i = 0; byte b1 = 0;
/* 1537 */     for (byte b2 = 0, b3 = 0; b3 < paramInt; b2 += 8) {
/* 1538 */       long l = 0L;
/* 1539 */       for (byte b = 0; b < 8; ) { l <<= 7; l |= (paramString.charAt(b2 + b) & 0x7F); b++; }
/* 1540 */        if (b1) arrayOfInt[b3++] = i | (int)(l >>> 56 - b1); 
/* 1541 */       if (b3 < paramInt) arrayOfInt[b3++] = (int)(l >>> 24 - b1); 
/* 1542 */       b1 = b1 + 8 & 0x1F;
/* 1543 */       i = (int)(l << b1);
/*      */     } 
/* 1545 */     return arrayOfInt;
/*      */   }
/*      */   
/*      */   static byte[] getBytes(String paramString) {
/*      */     try {
/* 1550 */       return paramString.getBytes("UTF-8");
/* 1551 */     } catch (UnsupportedEncodingException unsupportedEncodingException) {
/* 1552 */       return null;
/*      */     } 
/*      */   }
/*      */   
/*      */   static byte[] getNullTerminatedBytes(String paramString) {
/* 1557 */     byte[] arrayOfByte1 = getBytes(paramString);
/* 1558 */     byte[] arrayOfByte2 = new byte[arrayOfByte1.length + 1];
/* 1559 */     System.arraycopy(arrayOfByte1, 0, arrayOfByte2, 0, arrayOfByte1.length);
/* 1560 */     return arrayOfByte2;
/*      */   }
/*      */   
/* 1563 */   static final String toHex(int paramInt) throws ReadFaultException { return "0x" + Long.toString(paramInt & 0xFFFFFFFFL, 16); }
/* 1564 */   static final int min(int paramInt1, int paramInt2) throws FaultException { return (paramInt1 < paramInt2) ? paramInt1 : paramInt2; }
/* 1565 */   static final int max(int paramInt1, int paramInt2) throws FaultException { return (paramInt1 > paramInt2) ? paramInt1 : paramInt2; }
/*      */   
/*      */   protected abstract int heapStart();
/*      */   
/*      */   protected abstract int entryPoint();
/*      */   
/*      */   protected abstract int gp();
/*      */   
/*      */   protected abstract void _execute() throws ExecutionException;
/*      */   
/*      */   protected abstract void getCPUState(CPUState paramCPUState);
/*      */   
/*      */   protected abstract void setCPUState(CPUState paramCPUState);
/*      */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\ibex\nestedvm\Runtime.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       1.0.4
 */