/*    */ package org.bukkit.craftbukkit.command;
/*    */ 
/*    */ import java.lang.reflect.Method;
/*    */ import net.minecraft.server.ICommandListener;
/*    */ import org.bukkit.command.CommandSender;
/*    */ 
/*    */ public class ServerCommandListener
/*    */   implements ICommandListener
/*    */ {
/*    */   private final CommandSender commandSender;
/*    */   private final String prefix;
/*    */   
/*    */   public ServerCommandListener(CommandSender commandSender) {
/* 14 */     this.commandSender = commandSender;
/* 15 */     String[] parts = commandSender.getClass().getName().split("\\.");
/* 16 */     this.prefix = parts[parts.length - 1];
/*    */   }
/*    */ 
/*    */   
/* 20 */   public void sendMessage(String msg) { this.commandSender.sendMessage(msg); }
/*    */ 
/*    */ 
/*    */   
/* 24 */   public CommandSender getSender() { return this.commandSender; }
/*    */ 
/*    */   
/*    */   public String getName() {
/*    */     try {
/* 29 */       Method getName = this.commandSender.getClass().getMethod("getName", new Class[0]);
/*    */       
/* 31 */       return (String)getName.invoke(this.commandSender, new Object[0]);
/* 32 */     } catch (Exception e) {
/*    */       
/* 34 */       return this.prefix;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\craftbukkit\command\ServerCommandListener.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */