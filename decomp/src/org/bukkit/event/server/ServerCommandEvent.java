/*    */ package org.bukkit.event.server;
/*    */ 
/*    */ import org.bukkit.command.CommandSender;
/*    */ import org.bukkit.command.ConsoleCommandSender;
/*    */ import org.bukkit.event.Event;
/*    */ 
/*    */ public class ServerCommandEvent
/*    */   extends ServerEvent {
/*    */   private String command;
/*    */   private CommandSender sender;
/*    */   
/*    */   public ServerCommandEvent(ConsoleCommandSender console, String message) {
/* 13 */     super(Event.Type.SERVER_COMMAND);
/* 14 */     this.command = message;
/* 15 */     this.sender = console;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 24 */   public String getCommand() { return this.command; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 33 */   public void setCommand(String message) { this.command = message; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 40 */   public CommandSender getSender() { return this.sender; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\event\server\ServerCommandEvent.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */