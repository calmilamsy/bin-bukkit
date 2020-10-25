/*    */ package com.avaje.ebeaninternal.server.cluster.socket;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.net.Socket;
/*    */ import java.util.logging.Level;
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
/*    */ class RequestProcessor
/*    */   implements Runnable
/*    */ {
/* 35 */   private static final Logger logger = Logger.getLogger(RequestProcessor.class.getName());
/*    */ 
/*    */   
/*    */   private final Socket clientSocket;
/*    */ 
/*    */   
/*    */   private final SocketClusterBroadcast owner;
/*    */ 
/*    */ 
/*    */   
/*    */   public RequestProcessor(SocketClusterBroadcast owner, Socket clientSocket) {
/* 46 */     this.clientSocket = clientSocket;
/* 47 */     this.owner = owner;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void run() {
/*    */     try {
/* 58 */       SocketConnection sc = new SocketConnection(this.clientSocket);
/*    */       do {
/*    */       
/* 61 */       } while (!this.owner.process(sc));
/*    */ 
/*    */ 
/*    */ 
/*    */       
/* 66 */       sc.disconnect();
/*    */     }
/* 68 */     catch (IOException e) {
/* 69 */       logger.log(Level.SEVERE, null, e);
/* 70 */     } catch (ClassNotFoundException e) {
/* 71 */       logger.log(Level.SEVERE, null, e);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\cluster\socket\RequestProcessor.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */