/*    */ package net.minecraft.server;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.util.logging.Level;
/*    */ import java.util.logging.Logger;
/*    */ import jline.ConsoleReader;
/*    */ import org.bukkit.craftbukkit.Main;
/*    */ 
/*    */ public class ThreadCommandReader extends Thread {
/*    */   final MinecraftServer server;
/*    */   
/* 12 */   public ThreadCommandReader(MinecraftServer minecraftserver) { this.server = minecraftserver; }
/*    */ 
/*    */   
/*    */   public void run() {
/* 16 */     ConsoleReader bufferedreader = this.server.reader;
/* 17 */     String s = null;
/*    */ 
/*    */     
/*    */     try {
/* 21 */       while (!this.server.isStopped && MinecraftServer.isRunning(this.server)) {
/* 22 */         if (Main.useJline) {
/* 23 */           s = bufferedreader.readLine(">", null);
/*    */         } else {
/* 25 */           s = bufferedreader.readLine();
/*    */         } 
/* 27 */         if (s != null) {
/* 28 */           this.server.issueCommand(s, this.server);
/*    */         }
/*    */       }
/*    */     
/* 32 */     } catch (IOException ioexception) {
/*    */       
/* 34 */       Logger.getLogger(ThreadCommandReader.class.getName()).log(Level.SEVERE, null, ioexception);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\ThreadCommandReader.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */