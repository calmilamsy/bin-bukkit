/*    */ package org.bukkit.craftbukkit.util;
/*    */ 
/*    */ import net.minecraft.server.MinecraftServer;
/*    */ 
/*    */ public class ServerShutdownThread
/*    */   extends Thread
/*    */ {
/*    */   private final MinecraftServer server;
/*    */   
/* 10 */   public ServerShutdownThread(MinecraftServer server) { this.server = server; }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 15 */   public void run() { this.server.stop(); }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\craftbukki\\util\ServerShutdownThread.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */