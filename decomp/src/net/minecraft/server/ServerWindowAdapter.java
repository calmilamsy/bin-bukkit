/*    */ package net.minecraft.server;
/*    */ 
/*    */ import java.awt.event.WindowAdapter;
/*    */ import java.awt.event.WindowEvent;
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
/*    */ final class ServerWindowAdapter
/*    */   extends WindowAdapter
/*    */ {
/*    */   ServerWindowAdapter(MinecraftServer paramMinecraftServer) {}
/*    */   
/*    */   public void windowClosing(WindowEvent paramWindowEvent) {
/* 34 */     this.a.a();
/* 35 */     while (!this.a.isStopped) {
/*    */       try {
/* 37 */         Thread.sleep(100L);
/* 38 */       } catch (InterruptedException interruptedException) {
/* 39 */         interruptedException.printStackTrace();
/*    */       } 
/*    */     } 
/* 42 */     System.exit(0);
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\ServerWindowAdapter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */