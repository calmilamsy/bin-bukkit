/*    */ package org.bukkit.craftbukkit;
/*    */ import java.io.IOException;
/*    */ import java.util.logging.Level;
/*    */ import java.util.logging.Logger;
/*    */ 
/*    */ public class LoggerOutputStream extends ByteArrayOutputStream {
/*    */   private final String separator;
/*    */   
/*    */   public LoggerOutputStream(Logger logger, Level level) {
/* 10 */     this.separator = System.getProperty("line.separator");
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 16 */     this.logger = logger;
/* 17 */     this.level = level;
/*    */   }
/*    */   private final Logger logger; private final Level level;
/*    */   
/*    */   public void flush() throws IOException {
/* 22 */     synchronized (this) {
/* 23 */       super.flush();
/* 24 */       String record = toString();
/* 25 */       reset();
/*    */       
/* 27 */       if (record.length() > 0 && !record.equals(this.separator))
/* 28 */         this.logger.logp(this.level, "", "", record); 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\craftbukkit\LoggerOutputStream.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */