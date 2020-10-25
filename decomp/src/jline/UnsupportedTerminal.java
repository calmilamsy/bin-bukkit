/*    */ package jline;
/*    */ 
/*    */ import java.io.IOException;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class UnsupportedTerminal
/*    */   extends Terminal
/*    */ {
/* 17 */   private Thread maskThread = null;
/*    */ 
/*    */ 
/*    */   
/*    */   public void initializeTerminal() {}
/*    */ 
/*    */   
/* 24 */   public boolean getEcho() { return true; }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 29 */   public boolean isEchoEnabled() { return true; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void enableEcho() {}
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void disableEcho() {}
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 45 */   public int getTerminalWidth() { return 80; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 52 */   public int getTerminalHeight() { return 80; }
/*    */ 
/*    */ 
/*    */   
/* 56 */   public boolean isSupported() { return false; }
/*    */ 
/*    */ 
/*    */   
/*    */   public void beforeReadLine(ConsoleReader reader, String prompt, Character mask) {
/* 61 */     if (mask != null && this.maskThread == null) {
/* 62 */       String fullPrompt = "\r" + prompt + "                 " + "                 " + "                 " + "\r" + prompt;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */       
/* 68 */       this.maskThread = new Thread(this, "JLine Mask Thread", reader, fullPrompt) { private final ConsoleReader val$reader;
/*    */           public void run() {
/* 70 */             while (!UnsupportedTerminal.null.interrupted()) {
/*    */               try {
/* 72 */                 this.val$reader.out.write(this.val$fullPrompt);
/* 73 */                 this.val$reader.out.flush();
/* 74 */                 UnsupportedTerminal.null.sleep(3L);
/* 75 */               } catch (IOException ioe) {
/*    */                 return;
/* 77 */               } catch (InterruptedException ie) {
/*    */                 return;
/*    */               } 
/*    */             } 
/*    */           }
/*    */           private final String val$fullPrompt; private final UnsupportedTerminal this$0; }
/*    */         ;
/* 84 */       this.maskThread.setPriority(10);
/* 85 */       this.maskThread.setDaemon(true);
/* 86 */       this.maskThread.start();
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void afterReadLine(ConsoleReader reader, String prompt, Character mask) {
/* 92 */     if (this.maskThread != null && this.maskThread.isAlive()) {
/* 93 */       this.maskThread.interrupt();
/*    */     }
/*    */     
/* 96 */     this.maskThread = null;
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\jline\UnsupportedTerminal.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       1.0.4
 */