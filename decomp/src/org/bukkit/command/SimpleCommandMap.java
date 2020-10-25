/*     */ package org.bukkit.command;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import org.bukkit.Server;
/*     */ import org.bukkit.command.defaults.KillCommand;
/*     */ import org.bukkit.command.defaults.OpCommand;
/*     */ import org.bukkit.command.defaults.VanillaCommand;
/*     */ import org.bukkit.command.defaults.VersionCommand;
/*     */ import org.bukkit.command.defaults.WhitelistCommand;
/*     */ import org.bukkit.util.Java15Compat;
/*     */ 
/*     */ public class SimpleCommandMap implements CommandMap {
/*     */   protected final Map<String, Command> knownCommands;
/*  18 */   protected static final Set<VanillaCommand> fallbackCommands = new HashSet(); protected final Set<String> aliases; private final Server server;
/*     */   
/*     */   static  {
/*  21 */     fallbackCommands.add(new ListCommand());
/*  22 */     fallbackCommands.add(new StopCommand());
/*  23 */     fallbackCommands.add(new SaveCommand());
/*  24 */     fallbackCommands.add(new SaveOnCommand());
/*  25 */     fallbackCommands.add(new SaveOffCommand());
/*  26 */     fallbackCommands.add(new OpCommand());
/*  27 */     fallbackCommands.add(new DeopCommand());
/*  28 */     fallbackCommands.add(new BanIpCommand());
/*  29 */     fallbackCommands.add(new PardonIpCommand());
/*  30 */     fallbackCommands.add(new BanCommand());
/*  31 */     fallbackCommands.add(new PardonCommand());
/*  32 */     fallbackCommands.add(new KickCommand());
/*  33 */     fallbackCommands.add(new TeleportCommand());
/*  34 */     fallbackCommands.add(new GiveCommand());
/*  35 */     fallbackCommands.add(new TimeCommand());
/*  36 */     fallbackCommands.add(new SayCommand());
/*  37 */     fallbackCommands.add(new WhitelistCommand());
/*  38 */     fallbackCommands.add(new TellCommand());
/*  39 */     fallbackCommands.add(new MeCommand());
/*  40 */     fallbackCommands.add(new KillCommand());
/*  41 */     fallbackCommands.add(new HelpCommand());
/*     */   } public SimpleCommandMap(Server server) {
/*     */     this.knownCommands = new HashMap();
/*     */     this.aliases = new HashSet();
/*  45 */     this.server = server;
/*  46 */     setDefaultCommands(server);
/*     */   }
/*     */   
/*     */   private void setDefaultCommands(Server server) {
/*  50 */     register("bukkit", new VersionCommand("version"));
/*  51 */     register("bukkit", new ReloadCommand("reload"));
/*  52 */     register("bukkit", new PluginsCommand("plugins"));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void registerAll(String fallbackPrefix, List<Command> commands) {
/*  59 */     if (commands != null) {
/*  60 */       for (Command c : commands) {
/*  61 */         register(fallbackPrefix, c);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  70 */   public boolean register(String fallbackPrefix, Command command) { return register(command.getName(), fallbackPrefix, command); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean register(String label, String fallbackPrefix, Command command) {
/*  77 */     boolean registeredPassedLabel = register(label, fallbackPrefix, command, false);
/*     */     
/*  79 */     Iterator iterator = command.getAliases().iterator();
/*  80 */     while (iterator.hasNext()) {
/*  81 */       if (!register((String)iterator.next(), fallbackPrefix, command, true)) {
/*  82 */         iterator.remove();
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/*  87 */     command.register(this);
/*     */     
/*  89 */     return registeredPassedLabel;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean register(String label, String fallbackPrefix, Command command, boolean isAlias) {
/* 102 */     String lowerLabel = label.trim().toLowerCase();
/*     */     
/* 104 */     if (isAlias && this.knownCommands.containsKey(lowerLabel))
/*     */     {
/*     */       
/* 107 */       return false;
/*     */     }
/*     */     
/* 110 */     String lowerPrefix = fallbackPrefix.trim().toLowerCase();
/* 111 */     boolean registerdPassedLabel = true;
/*     */ 
/*     */     
/* 114 */     while (this.knownCommands.containsKey(lowerLabel) && !this.aliases.contains(lowerLabel)) {
/* 115 */       lowerLabel = lowerPrefix + ":" + lowerLabel;
/* 116 */       registerdPassedLabel = false;
/*     */     } 
/*     */     
/* 119 */     if (isAlias) {
/* 120 */       this.aliases.add(lowerLabel);
/*     */     } else {
/*     */       
/* 123 */       this.aliases.remove(lowerLabel);
/* 124 */       command.setLabel(lowerLabel);
/*     */     } 
/* 126 */     this.knownCommands.put(lowerLabel, command);
/*     */     
/* 128 */     return registerdPassedLabel;
/*     */   }
/*     */   
/*     */   protected Command getFallback(String label) {
/* 132 */     for (VanillaCommand cmd : fallbackCommands) {
/* 133 */       if (cmd.matches(label)) {
/* 134 */         return cmd;
/*     */       }
/*     */     } 
/*     */     
/* 138 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean dispatch(CommandSender sender, String commandLine) throws CommandException {
/* 145 */     String[] args = commandLine.split(" ");
/*     */     
/* 147 */     if (args.length == 0) {
/* 148 */       return false;
/*     */     }
/*     */     
/* 151 */     String sentCommandLabel = args[0].toLowerCase();
/* 152 */     Command target = getCommand(sentCommandLabel);
/* 153 */     if (target == null) {
/* 154 */       target = getFallback(commandLine.toLowerCase());
/*     */     }
/* 156 */     if (target == null) {
/* 157 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     try {
/* 162 */       target.execute(sender, sentCommandLabel, (String[])Java15Compat.Arrays_copyOfRange(args, 1, args.length));
/* 163 */     } catch (CommandException ex) {
/* 164 */       throw ex;
/* 165 */     } catch (Throwable ex) {
/* 166 */       throw new CommandException("Unhandled exception executing '" + commandLine + "' in " + target, ex);
/*     */     } 
/*     */ 
/*     */     
/* 170 */     return true;
/*     */   }
/*     */   
/*     */   public void clearCommands() {
/* 174 */     for (Map.Entry<String, Command> entry : this.knownCommands.entrySet()) {
/* 175 */       ((Command)entry.getValue()).unregister(this);
/*     */     }
/* 177 */     this.knownCommands.clear();
/* 178 */     this.aliases.clear();
/* 179 */     setDefaultCommands(this.server);
/*     */   }
/*     */ 
/*     */   
/* 183 */   public Command getCommand(String name) { return (Command)this.knownCommands.get(name.toLowerCase()); }
/*     */ 
/*     */   
/*     */   public void registerServerAliases() {
/* 187 */     Map<String, String[]> values = this.server.getCommandAliases();
/*     */     
/* 189 */     for (String alias : values.keySet()) {
/* 190 */       String[] targetNames = (String[])values.get(alias);
/* 191 */       List<Command> targets = new ArrayList<Command>();
/* 192 */       String bad = "";
/*     */       
/* 194 */       for (String name : targetNames) {
/* 195 */         Command command = getCommand(name);
/*     */         
/* 197 */         if (command == null) {
/* 198 */           bad = bad + name + ", ";
/*     */         } else {
/* 200 */           targets.add(command);
/*     */         } 
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 206 */       if (targets.size() > 0) {
/* 207 */         this.knownCommands.put(alias.toLowerCase(), new MultipleCommandAlias(alias.toLowerCase(), (Command[])targets.toArray(new Command[0])));
/*     */       } else {
/* 209 */         this.knownCommands.remove(alias.toLowerCase());
/*     */       } 
/*     */       
/* 212 */       if (bad.length() > 0) {
/* 213 */         bad = bad.substring(0, bad.length() - 2);
/* 214 */         this.server.getLogger().warning("The following command(s) could not be aliased under '" + alias + "' because they do not exist: " + bad);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\command\SimpleCommandMap.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */