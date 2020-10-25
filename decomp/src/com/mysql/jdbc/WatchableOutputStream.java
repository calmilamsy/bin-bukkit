/*    */ package com.mysql.jdbc;
/*    */ 
/*    */ import java.io.ByteArrayOutputStream;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class WatchableOutputStream
/*    */   extends ByteArrayOutputStream
/*    */ {
/*    */   private OutputStreamWatcher watcher;
/*    */   
/*    */   public void close() {
/* 49 */     super.close();
/*    */     
/* 51 */     if (this.watcher != null) {
/* 52 */       this.watcher.streamClosed(this);
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 63 */   public void setWatcher(OutputStreamWatcher watcher) { this.watcher = watcher; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\mysql\jdbc\WatchableOutputStream.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */