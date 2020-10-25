/*   */ package net.minecraft.server;
/*   */ 
/*   */ public class ServerCommand {
/*   */   public final String command;
/*   */   public final ICommandListener b;
/*   */   
/*   */   public ServerCommand(String paramString, ICommandListener paramICommandListener) {
/* 8 */     this.command = paramString;
/* 9 */     this.b = paramICommandListener;
/*   */   }
/*   */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\ServerCommand.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */