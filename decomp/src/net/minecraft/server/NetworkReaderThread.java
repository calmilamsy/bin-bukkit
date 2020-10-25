/*    */ package net.minecraft.server;
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
/*    */ class NetworkReaderThread
/*    */   extends Thread
/*    */ {
/* 77 */   NetworkReaderThread(NetworkManager paramNetworkManager, String paramString) { super(paramString); }
/*    */   public void run() {
/* 79 */     synchronized (NetworkManager.a) {
/* 80 */       NetworkManager.b++;
/*    */     } 
/*    */     try {
/* 83 */       while (NetworkManager.a(this.a) && !NetworkManager.b(this.a)) {
/* 84 */         while (NetworkManager.c(this.a));
/*    */ 
/*    */         
/*    */         try {
/* 88 */           sleep(100L);
/* 89 */         } catch (InterruptedException interruptedException) {}
/*    */       } 
/*    */     } finally {
/*    */       
/* 93 */       synchronized (NetworkManager.a) {
/* 94 */         NetworkManager.b--;
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\NetworkReaderThread.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */