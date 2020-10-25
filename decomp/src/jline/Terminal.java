/*     */ package jline;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class Terminal
/*     */   implements ConsoleOperations
/*     */ {
/*     */   private static Terminal term;
/*     */   
/*  26 */   public static Terminal getTerminal() { return setupTerminal(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  33 */   public static void resetTerminal() { term = null; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Terminal setupTerminal() {
/*  52 */     if (term != null) {
/*  53 */       return term;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*  58 */     String os = System.getProperty("os.name").toLowerCase();
/*  59 */     String termProp = System.getProperty("jline.terminal");
/*     */     
/*  61 */     if (termProp != null && termProp.length() > 0) {
/*     */       try {
/*  63 */         t = (Terminal)Class.forName(termProp).newInstance();
/*  64 */       } catch (Exception e) {
/*  65 */         throw (IllegalArgumentException)(new IllegalArgumentException(e.toString())).fillInStackTrace();
/*     */       }
/*     */     
/*  68 */     } else if (os.indexOf("windows") != -1) {
/*  69 */       t = new WindowsTerminal();
/*     */     } else {
/*  71 */       t = new UnixTerminal();
/*     */     } 
/*     */     
/*     */     try {
/*  75 */       t.initializeTerminal();
/*  76 */     } catch (Exception e) {
/*  77 */       e.printStackTrace();
/*     */       
/*  79 */       return term = new UnsupportedTerminal();
/*     */     } 
/*     */     
/*  82 */     return term = t;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  90 */   public boolean isANSISupported() { return true; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  99 */   public int readCharacter(InputStream in) throws IOException { return in.read(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 112 */   public int readVirtualKey(InputStream in) throws IOException { return readCharacter(in); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract void initializeTerminal();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract int getTerminalWidth();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract int getTerminalHeight();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract boolean isSupported();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract boolean getEcho();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void beforeReadLine(ConsoleReader reader, String prompt, Character mask) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void afterReadLine(ConsoleReader reader, String prompt, Character mask) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract boolean isEchoEnabled();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract void enableEcho();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract void disableEcho();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 178 */   public InputStream getDefaultBindings() { return Terminal.class.getResourceAsStream("keybindings.properties"); }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\jline\Terminal.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       1.0.4
 */