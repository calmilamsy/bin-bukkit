/*    */ package net.minecraft.server;
/*    */ 
/*    */ import java.io.PrintWriter;
/*    */ import java.io.StringWriter;
/*    */ import java.util.logging.Formatter;
/*    */ import java.util.logging.Level;
/*    */ import java.util.logging.LogRecord;
/*    */ 
/*    */ class GuiLogFormatter
/*    */   extends Formatter
/*    */ {
/*    */   GuiLogFormatter(GuiLogOutputHandler paramGuiLogOutputHandler) {}
/*    */   
/*    */   public String format(LogRecord paramLogRecord) {
/* 15 */     StringBuilder stringBuilder = new StringBuilder();
/*    */     
/* 17 */     Level level = paramLogRecord.getLevel();
/* 18 */     if (level == Level.FINEST) { stringBuilder.append("[FINEST] "); }
/* 19 */     else if (level == Level.FINER) { stringBuilder.append("[FINER] "); }
/* 20 */     else if (level == Level.FINE) { stringBuilder.append("[FINE] "); }
/* 21 */     else if (level == Level.INFO) { stringBuilder.append("[INFO] "); }
/* 22 */     else if (level == Level.WARNING) { stringBuilder.append("[WARNING] "); }
/* 23 */     else if (level == Level.SEVERE) { stringBuilder.append("[SEVERE] "); }
/* 24 */     else if (level == Level.SEVERE) { stringBuilder.append("[" + level.getLocalizedName() + "] "); }
/*    */     
/* 26 */     stringBuilder.append(paramLogRecord.getMessage());
/* 27 */     stringBuilder.append('\n');
/*    */     
/* 29 */     Throwable throwable = paramLogRecord.getThrown();
/* 30 */     if (throwable != null) {
/* 31 */       StringWriter stringWriter = new StringWriter();
/* 32 */       throwable.printStackTrace(new PrintWriter(stringWriter));
/* 33 */       stringBuilder.append(stringWriter.toString());
/*    */     } 
/*    */     
/* 36 */     return stringBuilder.toString();
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\GuiLogFormatter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */