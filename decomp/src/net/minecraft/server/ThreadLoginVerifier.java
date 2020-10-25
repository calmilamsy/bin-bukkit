/*    */ package net.minecraft.server;
/*    */ 
/*    */ import java.io.BufferedReader;
/*    */ import java.io.InputStreamReader;
/*    */ import java.net.URL;
/*    */ import java.net.URLEncoder;
/*    */ import org.bukkit.craftbukkit.CraftServer;
/*    */ import org.bukkit.event.player.PlayerPreLoginEvent;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class ThreadLoginVerifier
/*    */   extends Thread
/*    */ {
/*    */   final Packet1Login loginPacket;
/*    */   final NetLoginHandler netLoginHandler;
/*    */   CraftServer server;
/*    */   
/*    */   ThreadLoginVerifier(NetLoginHandler netloginhandler, Packet1Login packet1login, CraftServer server) {
/* 23 */     this.server = server;
/*    */ 
/*    */     
/* 26 */     this.netLoginHandler = netloginhandler;
/* 27 */     this.loginPacket = packet1login;
/*    */   }
/*    */   
/*    */   public void run() {
/*    */     try {
/* 32 */       String s = NetLoginHandler.a(this.netLoginHandler);
/* 33 */       URL url = new URL("http://www.minecraft.net/game/checkserver.jsp?user=" + URLEncoder.encode(this.loginPacket.name, "UTF-8") + "&serverId=" + URLEncoder.encode(s, "UTF-8"));
/* 34 */       BufferedReader bufferedreader = new BufferedReader(new InputStreamReader(url.openStream()));
/* 35 */       String s1 = bufferedreader.readLine();
/*    */       
/* 37 */       bufferedreader.close();
/* 38 */       if (s1.equals("YES")) {
/*    */         
/* 40 */         if (this.netLoginHandler.getSocket() == null) {
/*    */           return;
/*    */         }
/*    */         
/* 44 */         PlayerPreLoginEvent event = new PlayerPreLoginEvent(this.loginPacket.name, this.netLoginHandler.getSocket().getInetAddress());
/* 45 */         this.server.getPluginManager().callEvent(event);
/*    */         
/* 47 */         if (event.getResult() != PlayerPreLoginEvent.Result.ALLOWED) {
/* 48 */           this.netLoginHandler.disconnect(event.getKickMessage());
/*    */           
/*    */           return;
/*    */         } 
/*    */         
/* 53 */         NetLoginHandler.a(this.netLoginHandler, this.loginPacket);
/*    */       } else {
/* 55 */         this.netLoginHandler.disconnect("Failed to verify username!");
/*    */       } 
/* 57 */     } catch (Exception exception) {
/* 58 */       this.netLoginHandler.disconnect("Failed to verify username! [internal error " + exception + "]");
/* 59 */       exception.printStackTrace();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\ThreadLoginVerifier.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */