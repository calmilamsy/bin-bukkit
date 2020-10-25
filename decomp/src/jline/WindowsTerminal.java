/*     */ package jline;
/*     */ 
/*     */ import java.io.BufferedInputStream;
/*     */ import java.io.BufferedOutputStream;
/*     */ import java.io.File;
/*     */ import java.io.FileDescriptor;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.io.OutputStream;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WindowsTerminal
/*     */   extends Terminal
/*     */ {
/*     */   private static final int ENABLE_LINE_INPUT = 2;
/*     */   private static final int ENABLE_ECHO_INPUT = 4;
/*     */   private static final int ENABLE_PROCESSED_INPUT = 1;
/*     */   private static final int ENABLE_WINDOW_INPUT = 8;
/*     */   private static final int ENABLE_MOUSE_INPUT = 16;
/*     */   private static final int ENABLE_PROCESSED_OUTPUT = 1;
/*     */   private static final int ENABLE_WRAP_AT_EOL_OUTPUT = 2;
/*     */   public static final int SPECIAL_KEY_INDICATOR = 224;
/*     */   public static final int NUMPAD_KEY_INDICATOR = 0;
/*     */   public static final int LEFT_ARROW_KEY = 75;
/*     */   public static final int RIGHT_ARROW_KEY = 77;
/*     */   public static final int UP_ARROW_KEY = 72;
/*     */   public static final int DOWN_ARROW_KEY = 80;
/*     */   public static final int DELETE_KEY = 83;
/*     */   public static final int HOME_KEY = 71;
/*     */   public static final char END_KEY = 'O';
/*     */   public static final char PAGE_UP_KEY = 'I';
/*     */   public static final char PAGE_DOWN_KEY = 'Q';
/*     */   public static final char INSERT_KEY = 'R';
/*     */   public static final char ESCAPE_KEY = '\000';
/*     */   private Boolean directConsole;
/*     */   private boolean echoEnabled;
/* 192 */   String encoding = System.getProperty("jline.WindowsTerminal.input.encoding", System.getProperty("file.encoding"));
/* 193 */   ReplayPrefixOneCharInputStream replayStream = new ReplayPrefixOneCharInputStream(this.encoding);
/*     */   InputStreamReader replayReader;
/*     */   
/*     */   public WindowsTerminal() {
/* 197 */     String dir = System.getProperty("jline.WindowsTerminal.directConsole");
/*     */     
/* 199 */     if ("true".equals(dir)) {
/* 200 */       this.directConsole = Boolean.TRUE;
/* 201 */     } else if ("false".equals(dir)) {
/* 202 */       this.directConsole = Boolean.FALSE;
/*     */     } 
/*     */     
/*     */     try {
/* 206 */       this.replayReader = new InputStreamReader(this.replayStream, this.encoding);
/* 207 */     } catch (Exception e) {
/* 208 */       throw new RuntimeException(e);
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
/*     */   public int readCharacter(InputStream in) throws IOException {
/* 228 */     if (this.directConsole == Boolean.FALSE)
/* 229 */       return super.readCharacter(in); 
/* 230 */     if (this.directConsole == Boolean.TRUE || in == System.in || (in instanceof FileInputStream && ((FileInputStream)in).getFD() == FileDescriptor.in))
/*     */     {
/*     */       
/* 233 */       return readByte();
/*     */     }
/* 235 */     return super.readCharacter(in);
/*     */   }
/*     */ 
/*     */   
/*     */   public void initializeTerminal() {
/* 240 */     loadLibrary("jline");
/*     */     
/* 242 */     int originalMode = getConsoleMode();
/*     */     
/* 244 */     setConsoleMode(originalMode & 0xFFFFFFFB);
/*     */ 
/*     */     
/* 247 */     int newMode = originalMode & 0xFFFFFFF0;
/*     */ 
/*     */     
/* 250 */     this.echoEnabled = false;
/* 251 */     setConsoleMode(newMode);
/*     */ 
/*     */     
/*     */     try {
/* 255 */       Runtime.getRuntime().addShutdownHook(new Thread(this, originalMode) {
/*     */             private final int val$originalMode;
/*     */             
/* 258 */             public void start() { this.this$0.setConsoleMode(this.val$originalMode); }
/*     */             private final WindowsTerminal this$0;
/*     */           });
/* 261 */     } catch (AbstractMethodError ame) {
/*     */       
/* 263 */       consumeException(ame);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void loadLibrary(String name) throws IOException {
/* 269 */     String version = getClass().getPackage().getImplementationVersion();
/*     */     
/* 271 */     if (version == null) {
/* 272 */       version = "";
/*     */     }
/*     */     
/* 275 */     version = version.replace('.', '_');
/*     */     
/* 277 */     File f = new File(System.getProperty("java.io.tmpdir"), name + "_" + version + ".dll");
/*     */     
/* 279 */     boolean exists = f.isFile();
/*     */ 
/*     */ 
/*     */     
/* 283 */     int bits = 32;
/*     */ 
/*     */     
/* 286 */     if (System.getProperty("os.arch").indexOf("64") != -1) {
/* 287 */       bits = 64;
/*     */     }
/* 289 */     InputStream in = new BufferedInputStream(getClass().getResourceAsStream(name + bits + ".dll"));
/*     */ 
/*     */     
/*     */     try {
/* 293 */       OutputStream fout = new BufferedOutputStream(new FileOutputStream(f));
/*     */       
/* 295 */       byte[] bytes = new byte[10240];
/*     */       int n;
/* 297 */       for (n = 0; n != -1; n = in.read(bytes)) {
/* 298 */         fout.write(bytes, 0, n);
/*     */       }
/*     */       
/* 301 */       fout.close();
/* 302 */     } catch (IOException ioe) {
/*     */ 
/*     */ 
/*     */       
/* 306 */       if (!exists) {
/* 307 */         throw ioe;
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 312 */     f.deleteOnExit();
/*     */ 
/*     */     
/* 315 */     System.load(f.getAbsolutePath());
/*     */   }
/*     */   
/*     */   public int readVirtualKey(InputStream in) throws IOException {
/* 319 */     int indicator = readCharacter(in);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 324 */     if (indicator == 224 || indicator == 0) {
/*     */       
/* 326 */       int key = readCharacter(in);
/*     */       
/* 328 */       switch (key) {
/*     */         case 72:
/* 330 */           return 16;
/*     */         case 75:
/* 332 */           return 2;
/*     */         case 77:
/* 334 */           return 6;
/*     */         case 80:
/* 336 */           return 14;
/*     */         case 83:
/* 338 */           return 127;
/*     */         case 71:
/* 340 */           return 1;
/*     */         case 79:
/* 342 */           return 5;
/*     */         case 73:
/* 344 */           return 11;
/*     */         case 81:
/* 346 */           return 12;
/*     */         case 0:
/* 348 */           return 27;
/*     */         case 82:
/* 350 */           return 3;
/*     */       } 
/* 352 */       return 0;
/*     */     } 
/* 354 */     if (indicator > 128) {
/*     */ 
/*     */       
/* 357 */       this.replayStream.setInput(indicator, in);
/*     */       
/* 359 */       indicator = this.replayReader.read();
/*     */     } 
/*     */ 
/*     */     
/* 363 */     return indicator;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 368 */   public boolean isSupported() { return true; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 375 */   public boolean isANSISupported() { return false; }
/*     */ 
/*     */ 
/*     */   
/* 379 */   public boolean getEcho() { return false; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 388 */   public int getTerminalWidth() { return getWindowsTerminalWidth(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 397 */   public int getTerminalHeight() { return getWindowsTerminalHeight(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void consumeException(Throwable e) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 410 */   public void setDirectConsole(Boolean directConsole) { this.directConsole = directConsole; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 417 */   public Boolean getDirectConsole() { return this.directConsole; }
/*     */ 
/*     */ 
/*     */   
/* 421 */   public boolean isEchoEnabled() { return this.echoEnabled; }
/*     */ 
/*     */ 
/*     */   
/*     */   public void enableEcho() {
/* 426 */     setConsoleMode(getConsoleMode() | 0x4 | 0x2 | true | 0x8);
/*     */     
/* 428 */     this.echoEnabled = true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void disableEcho() {
/* 433 */     setConsoleMode(getConsoleMode() & 0xFFFFFFF0);
/*     */ 
/*     */     
/* 436 */     this.echoEnabled = true;
/*     */   }
/*     */ 
/*     */   
/* 440 */   public InputStream getDefaultBindings() { return getClass().getResourceAsStream("windowsbindings.properties"); }
/*     */   
/*     */   private native int getConsoleMode();
/*     */   
/*     */   private native void setConsoleMode(int paramInt);
/*     */   
/*     */   private native int readByte();
/*     */   
/*     */   private native int getWindowsTerminalWidth();
/*     */   
/*     */   private native int getWindowsTerminalHeight();
/*     */   
/*     */   static class ReplayPrefixOneCharInputStream extends InputStream { byte firstByte;
/*     */     int byteLength;
/*     */     InputStream wrappedStream;
/*     */     int byteRead;
/*     */     final String encoding;
/*     */     
/* 458 */     public ReplayPrefixOneCharInputStream(String encoding) throws IOException { this.encoding = encoding; }
/*     */ 
/*     */     
/*     */     public void setInput(int recorded, InputStream wrapped) throws IOException {
/* 462 */       this.byteRead = 0;
/* 463 */       this.firstByte = (byte)recorded;
/* 464 */       this.wrappedStream = wrapped;
/*     */       
/* 466 */       this.byteLength = 1;
/* 467 */       if (this.encoding.equalsIgnoreCase("UTF-8")) {
/* 468 */         setInputUTF8(recorded, wrapped);
/* 469 */       } else if (this.encoding.equalsIgnoreCase("UTF-16")) {
/* 470 */         this.byteLength = 2;
/* 471 */       } else if (this.encoding.equalsIgnoreCase("UTF-32")) {
/* 472 */         this.byteLength = 4;
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void setInputUTF8(int recorded, InputStream wrapped) throws IOException {
/* 478 */       if ((this.firstByte & 0xFFFFFFE0) == -64) {
/* 479 */         this.byteLength = 2;
/*     */       }
/* 481 */       else if ((this.firstByte & 0xFFFFFFF0) == -32) {
/* 482 */         this.byteLength = 3;
/*     */       }
/* 484 */       else if ((this.firstByte & 0xFFFFFFF8) == -16) {
/* 485 */         this.byteLength = 4;
/*     */       } else {
/* 487 */         throw new IOException("invalid UTF-8 first byte: " + this.firstByte);
/*     */       } 
/*     */     }
/*     */     public int read() {
/* 491 */       if (available() == 0) {
/* 492 */         return -1;
/*     */       }
/* 494 */       this.byteRead++;
/*     */       
/* 496 */       if (this.byteRead == 1) {
/* 497 */         return this.firstByte;
/*     */       }
/* 499 */       return this.wrappedStream.read();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 509 */     public int available() { return this.byteLength - this.byteRead; } }
/*     */ 
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\jline\WindowsTerminal.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       1.0.4
 */