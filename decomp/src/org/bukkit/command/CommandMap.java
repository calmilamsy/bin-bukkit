package org.bukkit.command;

import java.util.List;

public interface CommandMap {
  void registerAll(String paramString, List<Command> paramList);
  
  boolean register(String paramString1, String paramString2, Command paramCommand);
  
  boolean register(String paramString, Command paramCommand);
  
  boolean dispatch(CommandSender paramCommandSender, String paramString) throws CommandException;
  
  void clearCommands();
  
  Command getCommand(String paramString);
}


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\command\CommandMap.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */