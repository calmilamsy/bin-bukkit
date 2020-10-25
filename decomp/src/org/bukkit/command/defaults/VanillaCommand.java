/*    */ package org.bukkit.command.defaults;
/*    */ 
/*    */ import java.util.List;
/*    */ import org.bukkit.command.Command;
/*    */ 
/*    */ public abstract class VanillaCommand
/*    */   extends Command {
/*  8 */   protected VanillaCommand(String name) { super(name); }
/*    */ 
/*    */ 
/*    */   
/* 12 */   protected VanillaCommand(String name, String description, String usageMessage, List<String> aliases) { super(name, description, usageMessage, aliases); }
/*    */   
/*    */   public abstract boolean matches(String paramString);
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\command\defaults\VanillaCommand.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */