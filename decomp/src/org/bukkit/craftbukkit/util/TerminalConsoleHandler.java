/*    */ package org.bukkit.craftbukkit.util;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.util.logging.ConsoleHandler;
/*    */ import java.util.logging.Level;
/*    */ import java.util.logging.Logger;
/*    */ import jline.ConsoleReader;
/*    */ import org.bukkit.craftbukkit.Main;
/*    */ 
/*    */ public class TerminalConsoleHandler
/*    */   extends ConsoleHandler
/*    */ {
/*    */   private final ConsoleReader reader;
/*    */   
/* 15 */   public TerminalConsoleHandler(ConsoleReader reader) { this.reader = reader; }
/*    */ 
/*    */ 
/*    */   
/*    */   public void flush() {
/*    */     try {
/* 21 */       if (Main.useJline) {
/* 22 */         this.reader.printString("\r");
/* 23 */         this.reader.flushConsole();
/* 24 */         super.flush();
/*    */         try {
/* 26 */           this.reader.drawLine();
/* 27 */         } catch (Throwable ex) {
/* 28 */           this.reader.getCursorBuffer().clearBuffer();
/*    */         } 
/* 30 */         this.reader.flushConsole();
/*    */       } else {
/* 32 */         super.flush();
/*    */       } 
/* 34 */     } catch (IOException ex) {
/* 35 */       Logger.getLogger(TerminalConsoleHandler.class.getName()).log(Level.SEVERE, null, ex);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\craftbukki\\util\TerminalConsoleHandler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */