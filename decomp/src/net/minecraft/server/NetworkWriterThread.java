/*     */ package net.minecraft.server;
/*     */ 
/*     */ import java.io.IOException;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class NetworkWriterThread
/*     */   extends Thread
/*     */ {
/* 100 */   NetworkWriterThread(NetworkManager paramNetworkManager, String paramString) { super(paramString); }
/*     */   public void run() {
/* 102 */     synchronized (NetworkManager.a) {
/* 103 */       NetworkManager.c++;
/*     */     } 
/*     */     try {
/* 106 */       while (NetworkManager.a(this.a)) {
/* 107 */         while (NetworkManager.d(this.a));
/*     */ 
/*     */         
/*     */         try {
/* 111 */           sleep(100L);
/* 112 */         } catch (InterruptedException interruptedException) {}
/*     */ 
/*     */         
/*     */         try {
/* 116 */           if (NetworkManager.e(this.a) != null) NetworkManager.e(this.a).flush(); 
/* 117 */         } catch (IOException iOException) {
/* 118 */           if (!NetworkManager.f(this.a)) NetworkManager.a(this.a, iOException); 
/* 119 */           iOException.printStackTrace();
/*     */         } 
/*     */       } 
/*     */     } finally {
/* 123 */       synchronized (NetworkManager.a) {
/* 124 */         NetworkManager.c--;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\NetworkWriterThread.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */