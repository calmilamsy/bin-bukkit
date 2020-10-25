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
/*    */ public class ThreadSleepForever
/*    */   extends Thread
/*    */ {
/*    */   public ThreadSleepForever(MinecraftServer paramMinecraftServer) {
/* 66 */     setDaemon(true);
/* 67 */     start();
/*    */   }
/*    */   public void run() {
/*    */     while (true) {
/*    */       try {
/*    */         while (true)
/* 73 */           Thread.sleep(2147483647L);  break;
/* 74 */       } catch (InterruptedException interruptedException) {}
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\ThreadSleepForever.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */