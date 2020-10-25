/*    */ package com.avaje.ebeaninternal.server.transaction.log;
/*    */ 
/*    */ import com.avaje.ebean.config.GlobalProperties;
/*    */ import com.avaje.ebean.config.ServerConfig;
/*    */ import com.avaje.ebeaninternal.server.transaction.TransactionLogBuffer;
/*    */ import com.avaje.ebeaninternal.server.transaction.TransactionLogWriter;
/*    */ import java.util.logging.Logger;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ public class FileTransactionLoggerWrapper
/*    */   implements TransactionLogWriter
/*    */ {
/* 43 */   private static final Logger logger = Logger.getLogger(FileTransactionLoggerWrapper.class.getName());
/*    */   
/*    */   private final String serverName;
/*    */   
/*    */   private final String dir;
/*    */   
/*    */   private final int maxFileSize;
/*    */ 
/*    */   
/*    */   public FileTransactionLoggerWrapper(ServerConfig serverConfig) {
/* 53 */     String evalDir = serverConfig.getLoggingDirectoryWithEval();
/* 54 */     this.dir = (evalDir != null) ? evalDir : "logs";
/* 55 */     this.maxFileSize = GlobalProperties.getInt("ebean.logging.maxFileSize", 104857600);
/* 56 */     this.serverName = serverConfig.getName();
/*    */   }
/*    */ 
/*    */   
/*    */   private FileTransactionLogger initialiseLogger() {
/* 61 */     synchronized (this) {
/*    */       
/* 63 */       FileTransactionLogger writer = this.logWriter;
/* 64 */       if (writer != null) {
/* 65 */         return writer;
/*    */       }
/*    */       
/* 68 */       String middleName = GlobalProperties.get("ebean.logging.filename", "_txn_");
/* 69 */       String logPrefix = this.serverName + middleName;
/* 70 */       String threadName = "Ebean-" + this.serverName + "-TxnLogWriter";
/*    */ 
/*    */       
/* 73 */       FileTransactionLogger newLogWriter = new FileTransactionLogger(threadName, this.dir, logPrefix, this.maxFileSize);
/*    */ 
/*    */       
/* 76 */       this.logWriter = newLogWriter;
/*    */ 
/*    */       
/* 79 */       newLogWriter.start();
/* 80 */       logger.info("Transaction logs in: " + this.dir);
/* 81 */       return newLogWriter;
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void log(TransactionLogBuffer logBuffer) {
/* 87 */     FileTransactionLogger writer = this.logWriter;
/* 88 */     if (writer == null) {
/* 89 */       writer = initialiseLogger();
/*    */     }
/* 91 */     writer.log(logBuffer);
/*    */   }
/*    */   
/*    */   public void shutdown() {
/* 95 */     if (this.logWriter != null)
/* 96 */       this.logWriter.shutdown(); 
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\transaction\log\FileTransactionLoggerWrapper.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */