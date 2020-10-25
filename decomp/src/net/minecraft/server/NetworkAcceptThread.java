/*    */ package net.minecraft.server;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.net.InetAddress;
/*    */ import java.net.Socket;
/*    */ import java.util.HashMap;
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
/*    */ class NetworkAcceptThread
/*    */   extends Thread
/*    */ {
/* 32 */   NetworkAcceptThread(NetworkListenThread paramNetworkListenThread, String paramString, MinecraftServer paramMinecraftServer) { super(paramString); }
/*    */ 
/*    */ 
/*    */   
/*    */   public void run() {
/* 37 */     HashMap hashMap = new HashMap();
/* 38 */     while (this.b.b) {
/*    */       try {
/* 40 */         Socket socket = NetworkListenThread.a(this.b).accept();
/* 41 */         if (socket != null) {
/*    */           
/* 43 */           InetAddress inetAddress = socket.getInetAddress();
/* 44 */           if (hashMap.containsKey(inetAddress) && !"127.0.0.1".equals(inetAddress.getHostAddress()) && 
/* 45 */             System.currentTimeMillis() - ((Long)hashMap.get(inetAddress)).longValue() < 5000L) {
/* 46 */             hashMap.put(inetAddress, Long.valueOf(System.currentTimeMillis()));
/* 47 */             socket.close();
/*    */             
/*    */             continue;
/*    */           } 
/*    */           
/* 52 */           hashMap.put(inetAddress, Long.valueOf(System.currentTimeMillis()));
/* 53 */           NetLoginHandler netLoginHandler = new NetLoginHandler(this.a, socket, "Connection #" + NetworkListenThread.b(this.b));
/* 54 */           NetworkListenThread.a(this.b, netLoginHandler);
/*    */         } 
/* 56 */       } catch (IOException iOException) {
/* 57 */         iOException.printStackTrace();
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\NetworkAcceptThread.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */