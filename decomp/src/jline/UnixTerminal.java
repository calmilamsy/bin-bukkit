/*     */ package jline;
/*     */ 
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.util.Map;
/*     */ import java.util.StringTokenizer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class UnixTerminal
/*     */   extends Terminal
/*     */ {
/*     */   public static final short ARROW_START = 27;
/*     */   public static final short ARROW_PREFIX = 91;
/*     */   public static final short ARROW_LEFT = 68;
/*     */   public static final short ARROW_RIGHT = 67;
/*     */   public static final short ARROW_UP = 65;
/*     */   public static final short ARROW_DOWN = 66;
/*     */   public static final short O_PREFIX = 79;
/*     */   public static final short HOME_CODE = 72;
/*     */   public static final short END_CODE = 70;
/*     */   public static final short DEL_THIRD = 51;
/*     */   public static final short DEL_SECOND = 126;
/*     */   private Map terminfo;
/*     */   private boolean echoEnabled;
/*     */   private String ttyConfig;
/*     */   private boolean backspaceDeleteSwitched = false;
/*  43 */   private static String sttyCommand = System.getProperty("jline.sttyCommand", "stty");
/*     */ 
/*     */ 
/*     */   
/*  47 */   String encoding = System.getProperty("input.encoding", "UTF-8");
/*  48 */   ReplayPrefixOneCharInputStream replayStream = new ReplayPrefixOneCharInputStream(this.encoding);
/*     */   InputStreamReader replayReader;
/*     */   
/*     */   public UnixTerminal() {
/*     */     try {
/*  53 */       this.replayReader = new InputStreamReader(this.replayStream, this.encoding);
/*  54 */     } catch (Exception e) {
/*  55 */       throw new RuntimeException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void checkBackspace() {
/*  60 */     String[] ttyConfigSplit = this.ttyConfig.split(":|=");
/*     */     
/*  62 */     if (ttyConfigSplit.length < 7) {
/*     */       return;
/*     */     }
/*  65 */     if (ttyConfigSplit[6] == null) {
/*     */       return;
/*     */     }
/*  68 */     this.backspaceDeleteSwitched = ttyConfigSplit[6].equals("7f");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initializeTerminal() {
/*  77 */     this.ttyConfig = stty("-g");
/*     */ 
/*     */     
/*  80 */     if (this.ttyConfig.length() == 0 || (this.ttyConfig.indexOf("=") == -1 && this.ttyConfig.indexOf(":") == -1))
/*     */     {
/*     */       
/*  83 */       throw new IOException("Unrecognized stty code: " + this.ttyConfig);
/*     */     }
/*     */     
/*  86 */     checkBackspace();
/*     */ 
/*     */     
/*  89 */     stty("-icanon min 1");
/*     */ 
/*     */     
/*  92 */     stty("-echo");
/*  93 */     this.echoEnabled = false;
/*     */ 
/*     */     
/*     */     try {
/*  97 */       Runtime.getRuntime().addShutdownHook(new Thread(this) {
/*     */             public void start() {
/*     */               try {
/* 100 */                 this.this$0.restoreTerminal();
/* 101 */               } catch (Exception e) {
/* 102 */                 this.this$0.consumeException(e);
/*     */               } 
/*     */             } private final UnixTerminal this$0;
/*     */           });
/* 106 */     } catch (AbstractMethodError ame) {
/*     */       
/* 108 */       consumeException(ame);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void restoreTerminal() {
/* 118 */     if (this.ttyConfig != null) {
/* 119 */       stty(this.ttyConfig);
/* 120 */       this.ttyConfig = null;
/*     */     } 
/* 122 */     resetTerminal();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int readVirtualKey(InputStream in) throws IOException {
/* 128 */     int c = readCharacter(in);
/*     */     
/* 130 */     if (this.backspaceDeleteSwitched) {
/* 131 */       if (c == 127) {
/* 132 */         c = 8;
/* 133 */       } else if (c == 8) {
/* 134 */         c = 127;
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/* 139 */     if (c == 27) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 146 */       while (c == 27)
/* 147 */         c = readCharacter(in); 
/* 148 */       if (c == 91 || c == 79) {
/* 149 */         c = readCharacter(in);
/* 150 */         if (c == 65)
/* 151 */           return 16; 
/* 152 */         if (c == 66)
/* 153 */           return 14; 
/* 154 */         if (c == 68)
/* 155 */           return 2; 
/* 156 */         if (c == 67)
/* 157 */           return 6; 
/* 158 */         if (c == 72)
/* 159 */           return 1; 
/* 160 */         if (c == 70)
/* 161 */           return 5; 
/* 162 */         if (c == 51) {
/* 163 */           c = readCharacter(in);
/* 164 */           return 127;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 169 */     if (c > 128) {
/*     */ 
/*     */       
/* 172 */       this.replayStream.setInput(c, in);
/*     */       
/* 174 */       c = this.replayReader.read();
/*     */     } 
/*     */ 
/*     */     
/* 178 */     return c;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void consumeException(Throwable e) {}
/*     */ 
/*     */ 
/*     */   
/* 188 */   public boolean isSupported() { return true; }
/*     */ 
/*     */ 
/*     */   
/* 192 */   public boolean getEcho() { return false; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getTerminalWidth() {
/* 204 */     int val = -1;
/*     */     
/*     */     try {
/* 207 */       val = getTerminalProperty("columns");
/* 208 */     } catch (Exception e) {}
/*     */ 
/*     */     
/* 211 */     if (val == -1) {
/* 212 */       val = 80;
/*     */     }
/*     */     
/* 215 */     return val;
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
/*     */   public int getTerminalHeight() {
/* 227 */     int val = -1;
/*     */     
/*     */     try {
/* 230 */       val = getTerminalProperty("rows");
/* 231 */     } catch (Exception e) {}
/*     */ 
/*     */     
/* 234 */     if (val == -1) {
/* 235 */       val = 24;
/*     */     }
/*     */     
/* 238 */     return val;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int getTerminalProperty(String prop) throws IOException, InterruptedException {
/* 247 */     String props = stty("-a");
/*     */     
/* 249 */     StringTokenizer tok = new StringTokenizer(props, ";\n");
/* 250 */     while (tok.hasMoreTokens()) {
/* 251 */       String str = tok.nextToken().trim();
/*     */       
/* 253 */       if (str.startsWith(prop)) {
/* 254 */         int index = str.lastIndexOf(" ");
/*     */         
/* 256 */         return Integer.parseInt(str.substring(index).trim());
/* 257 */       }  if (str.endsWith(prop)) {
/* 258 */         int index = str.indexOf(" ");
/*     */         
/* 260 */         return Integer.parseInt(str.substring(0, index).trim());
/*     */       } 
/*     */     } 
/*     */     
/* 264 */     return -1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 273 */   private static String stty(String args) throws IOException, InterruptedException { return exec("stty " + args + " < /dev/tty").trim(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 282 */   private static String exec(String cmd) throws IOException, InterruptedException { return exec(new String[] { "sh", "-c", cmd }); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static String exec(String[] cmd) throws IOException, InterruptedException {
/* 295 */     ByteArrayOutputStream bout = new ByteArrayOutputStream();
/*     */     
/* 297 */     Process p = Runtime.getRuntime().exec(cmd);
/*     */ 
/*     */ 
/*     */     
/* 301 */     InputStream in = p.getInputStream();
/*     */     int c;
/* 303 */     while ((c = in.read()) != -1) {
/* 304 */       bout.write(c);
/*     */     }
/*     */     
/* 307 */     in = p.getErrorStream();
/*     */     
/* 309 */     while ((c = in.read()) != -1) {
/* 310 */       bout.write(c);
/*     */     }
/*     */     
/* 313 */     p.waitFor();
/*     */     
/* 315 */     return new String(bout.toByteArray());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 325 */   public static void setSttyCommand(String cmd) { sttyCommand = cmd; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 333 */   public static String getSttyCommand() { return sttyCommand; }
/*     */ 
/*     */ 
/*     */   
/* 337 */   public boolean isEchoEnabled() { return this.echoEnabled; }
/*     */ 
/*     */ 
/*     */   
/*     */   public void enableEcho() {
/*     */     try {
/* 343 */       stty("echo");
/* 344 */       this.echoEnabled = true;
/* 345 */     } catch (Exception e) {
/* 346 */       consumeException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void disableEcho() {
/*     */     try {
/* 352 */       stty("-echo");
/* 353 */       this.echoEnabled = false;
/* 354 */     } catch (Exception e) {
/* 355 */       consumeException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   static class ReplayPrefixOneCharInputStream
/*     */     extends InputStream
/*     */   {
/*     */     byte firstByte;
/*     */     
/*     */     int byteLength;
/*     */     
/*     */     InputStream wrappedStream;
/*     */     
/*     */     int byteRead;
/*     */     
/*     */     final String encoding;
/*     */ 
/*     */     
/* 374 */     public ReplayPrefixOneCharInputStream(String encoding) { this.encoding = encoding; }
/*     */ 
/*     */     
/*     */     public void setInput(int recorded, InputStream wrapped) throws IOException {
/* 378 */       this.byteRead = 0;
/* 379 */       this.firstByte = (byte)recorded;
/* 380 */       this.wrappedStream = wrapped;
/*     */       
/* 382 */       this.byteLength = 1;
/* 383 */       if (this.encoding.equalsIgnoreCase("UTF-8")) {
/* 384 */         setInputUTF8(recorded, wrapped);
/* 385 */       } else if (this.encoding.equalsIgnoreCase("UTF-16")) {
/* 386 */         this.byteLength = 2;
/* 387 */       } else if (this.encoding.equalsIgnoreCase("UTF-32")) {
/* 388 */         this.byteLength = 4;
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void setInputUTF8(int recorded, InputStream wrapped) throws IOException {
/* 394 */       if ((this.firstByte & 0xFFFFFFE0) == -64) {
/* 395 */         this.byteLength = 2;
/*     */       }
/* 397 */       else if ((this.firstByte & 0xFFFFFFF0) == -32) {
/* 398 */         this.byteLength = 3;
/*     */       }
/* 400 */       else if ((this.firstByte & 0xFFFFFFF8) == -16) {
/* 401 */         this.byteLength = 4;
/*     */       } else {
/* 403 */         throw new IOException("invalid UTF-8 first byte: " + this.firstByte);
/*     */       } 
/*     */     }
/*     */     public int read() {
/* 407 */       if (available() == 0) {
/* 408 */         return -1;
/*     */       }
/* 410 */       this.byteRead++;
/*     */       
/* 412 */       if (this.byteRead == 1) {
/* 413 */         return this.firstByte;
/*     */       }
/* 415 */       return this.wrappedStream.read();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 425 */     public int available() { return this.byteLength - this.byteRead; }
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\jline\UnixTerminal.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       1.0.4
 */