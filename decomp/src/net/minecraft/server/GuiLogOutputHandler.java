/*    */ package net.minecraft.server;
/*    */ import java.util.logging.Handler;
/*    */ import java.util.logging.LogRecord;
/*    */ import javax.swing.JTextArea;
/*    */ 
/*    */ public class GuiLogOutputHandler extends Handler {
/*    */   private int[] b;
/*    */   
/*    */   public GuiLogOutputHandler(JTextArea paramJTextArea) {
/* 10 */     this.b = new int[1024];
/* 11 */     this.c = 0;
/*    */     
/* 13 */     this.a = new GuiLogFormatter(this);
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 43 */     setFormatter(this.a);
/* 44 */     this.d = paramJTextArea;
/*    */   }
/*    */   private int c; Formatter a;
/*    */   private JTextArea d;
/*    */   
/*    */   public void close() {}
/*    */   
/*    */   public void flush() {}
/*    */   
/*    */   public void publish(LogRecord paramLogRecord) {
/* 54 */     int i = this.d.getDocument().getLength();
/* 55 */     this.d.append(this.a.format(paramLogRecord));
/* 56 */     this.d.setCaretPosition(this.d.getDocument().getLength());
/* 57 */     int j = this.d.getDocument().getLength() - i;
/*    */     
/* 59 */     if (this.b[this.c] != 0) {
/* 60 */       this.d.replaceRange("", 0, this.b[this.c]);
/*    */     }
/* 62 */     this.b[this.c] = j;
/* 63 */     this.c = (this.c + 1) % 1024;
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\GuiLogOutputHandler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */