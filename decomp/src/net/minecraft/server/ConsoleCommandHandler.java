/*     */ package net.minecraft.server;
/*     */ 
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import java.util.logging.Logger;
/*     */ import org.bukkit.command.CommandSender;
/*     */ import org.bukkit.craftbukkit.command.ServerCommandListener;
/*     */ import org.bukkit.craftbukkit.entity.CraftPlayer;
/*     */ 
/*     */ 
/*     */ public class ConsoleCommandHandler
/*     */ {
/*  14 */   private static Logger a = Logger.getLogger("Minecraft");
/*     */   
/*     */   private MinecraftServer server;
/*     */   private ICommandListener listener;
/*     */   
/*  19 */   public ConsoleCommandHandler(MinecraftServer minecraftserver) { this.server = minecraftserver; }
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean hasPermission(ICommandListener listener, String perm) {
/*  24 */     if (listener instanceof ServerCommandListener) {
/*  25 */       ServerCommandListener serv = (ServerCommandListener)listener;
/*  26 */       return serv.getSender().hasPermission(perm);
/*  27 */     }  if (listener instanceof NetServerHandler) {
/*  28 */       NetServerHandler net = (NetServerHandler)listener;
/*  29 */       return net.getPlayer().hasPermission(perm);
/*  30 */     }  if (listener instanceof ServerGUI || listener instanceof MinecraftServer) {
/*  31 */       return this.server.console.hasPermission(perm);
/*     */     }
/*     */     
/*  34 */     return false;
/*     */   }
/*     */   
/*     */   private boolean checkPermission(ICommandListener listener, String command) {
/*  38 */     if (hasPermission(listener, "bukkit.command." + command)) {
/*  39 */       return true;
/*     */     }
/*  41 */     listener.sendMessage("I'm sorry, Dave, but I cannot let you do that.");
/*  42 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean handle(ServerCommand servercommand) {
/*  48 */     String s = servercommand.command;
/*  49 */     ICommandListener icommandlistener = servercommand.b;
/*  50 */     String s1 = icommandlistener.getName();
/*  51 */     this.listener = icommandlistener;
/*  52 */     ServerConfigurationManager serverconfigurationmanager = this.server.serverConfigurationManager;
/*     */     
/*  54 */     if (!s.toLowerCase().startsWith("help") && !s.toLowerCase().startsWith("?")) {
/*  55 */       if (s.toLowerCase().startsWith("list")) {
/*  56 */         if (!checkPermission(this.listener, "list")) return true; 
/*  57 */         icommandlistener.sendMessage("Connected players: " + serverconfigurationmanager.c());
/*  58 */       } else if (s.toLowerCase().startsWith("stop")) {
/*  59 */         if (!checkPermission(this.listener, "stop")) return true; 
/*  60 */         print(s1, "Stopping the server..");
/*  61 */         this.server.a();
/*     */ 
/*     */ 
/*     */       
/*     */       }
/*  66 */       else if (s.toLowerCase().startsWith("save-all")) {
/*  67 */         if (!checkPermission(this.listener, "save.perform")) return true; 
/*  68 */         print(s1, "Forcing save..");
/*  69 */         if (serverconfigurationmanager != null) {
/*  70 */           serverconfigurationmanager.savePlayers();
/*     */         }
/*     */ 
/*     */         
/*  74 */         for (int i = 0; i < this.server.worlds.size(); i++) {
/*  75 */           WorldServer worldserver = (WorldServer)this.server.worlds.get(i);
/*  76 */           boolean save = worldserver.canSave;
/*  77 */           worldserver.canSave = false;
/*  78 */           worldserver.save(true, (IProgressUpdate)null);
/*  79 */           worldserver.canSave = save;
/*     */         } 
/*     */ 
/*     */         
/*  83 */         print(s1, "Save complete.");
/*  84 */       } else if (s.toLowerCase().startsWith("save-off")) {
/*  85 */         if (!checkPermission(this.listener, "save.disable")) return true; 
/*  86 */         print(s1, "Disabling level saving..");
/*     */         
/*  88 */         for (int i = 0; i < this.server.worlds.size(); i++) {
/*  89 */           WorldServer worldserver = (WorldServer)this.server.worlds.get(i);
/*  90 */           worldserver.canSave = true;
/*     */         } 
/*  92 */       } else if (s.toLowerCase().startsWith("save-on")) {
/*  93 */         if (!checkPermission(this.listener, "save.enable")) return true; 
/*  94 */         print(s1, "Enabling level saving..");
/*     */         
/*  96 */         for (int i = 0; i < this.server.worlds.size(); i++) {
/*  97 */           WorldServer worldserver = (WorldServer)this.server.worlds.get(i);
/*  98 */           worldserver.canSave = false;
/*     */         
/*     */         }
/*     */       
/*     */       }
/* 103 */       else if (s.toLowerCase().startsWith("op ")) {
/* 104 */         if (!checkPermission(this.listener, "op.give")) return true; 
/* 105 */         String s2 = s.substring(s.indexOf(" ")).trim();
/* 106 */         serverconfigurationmanager.e(s2);
/* 107 */         print(s1, "Opping " + s2);
/* 108 */         serverconfigurationmanager.a(s2, "§eYou are now op!");
/* 109 */       } else if (s.toLowerCase().startsWith("deop ")) {
/* 110 */         if (!checkPermission(this.listener, "op.take")) return true; 
/* 111 */         String s2 = s.substring(s.indexOf(" ")).trim();
/* 112 */         serverconfigurationmanager.f(s2);
/* 113 */         serverconfigurationmanager.a(s2, "§eYou are no longer op!");
/* 114 */         print(s1, "De-opping " + s2);
/* 115 */       } else if (s.toLowerCase().startsWith("ban-ip ")) {
/* 116 */         if (!checkPermission(this.listener, "ban.ip")) return true; 
/* 117 */         String s2 = s.substring(s.indexOf(" ")).trim();
/* 118 */         serverconfigurationmanager.c(s2);
/* 119 */         print(s1, "Banning ip " + s2);
/* 120 */       } else if (s.toLowerCase().startsWith("pardon-ip ")) {
/* 121 */         if (!checkPermission(this.listener, "unban.ip")) return true; 
/* 122 */         String s2 = s.substring(s.indexOf(" ")).trim();
/* 123 */         serverconfigurationmanager.d(s2);
/* 124 */         print(s1, "Pardoning ip " + s2);
/*     */ 
/*     */       
/*     */       }
/* 128 */       else if (s.toLowerCase().startsWith("ban ")) {
/* 129 */         if (!checkPermission(this.listener, "ban.player")) return true; 
/* 130 */         String s2 = s.substring(s.indexOf(" ")).trim();
/* 131 */         serverconfigurationmanager.a(s2);
/* 132 */         print(s1, "Banning " + s2);
/* 133 */         EntityPlayer entityplayer = serverconfigurationmanager.i(s2);
/* 134 */         if (entityplayer != null) {
/* 135 */           entityplayer.netServerHandler.disconnect("Banned by admin");
/*     */         }
/* 137 */       } else if (s.toLowerCase().startsWith("pardon ")) {
/* 138 */         if (!checkPermission(this.listener, "unban.player")) return true; 
/* 139 */         String s2 = s.substring(s.indexOf(" ")).trim();
/* 140 */         serverconfigurationmanager.b(s2);
/* 141 */         print(s1, "Pardoning " + s2);
/*     */ 
/*     */       
/*     */       }
/* 145 */       else if (s.toLowerCase().startsWith("kick ")) {
/* 146 */         if (!checkPermission(this.listener, "kick")) return true;
/*     */         
/* 148 */         String[] parts = s.split(" ");
/* 149 */         String s2 = (parts.length >= 2) ? parts[1] : "";
/*     */         
/* 151 */         EntityPlayer entityplayer = null;
/*     */         
/* 153 */         for (int j = 0; j < serverconfigurationmanager.players.size(); j++) {
/* 154 */           EntityPlayer entityplayer1 = (EntityPlayer)serverconfigurationmanager.players.get(j);
/*     */           
/* 156 */           if (entityplayer1.name.equalsIgnoreCase(s2)) {
/* 157 */             entityplayer = entityplayer1;
/*     */           }
/*     */         } 
/*     */         
/* 161 */         if (entityplayer != null) {
/* 162 */           entityplayer.netServerHandler.disconnect("Kicked by admin");
/* 163 */           print(s1, "Kicking " + entityplayer.name);
/*     */         } else {
/* 165 */           icommandlistener.sendMessage("Can't find user " + s2 + ". No kick.");
/*     */         
/*     */         }
/*     */ 
/*     */       
/*     */       }
/* 171 */       else if (s.toLowerCase().startsWith("tp ")) {
/* 172 */         if (!checkPermission(this.listener, "teleport")) return true; 
/* 173 */         String[] astring = s.split(" ");
/* 174 */         if (astring.length == 3) {
/* 175 */           EntityPlayer entityplayer = serverconfigurationmanager.i(astring[1]);
/* 176 */           EntityPlayer entityplayer2 = serverconfigurationmanager.i(astring[2]);
/* 177 */           if (entityplayer == null) {
/* 178 */             icommandlistener.sendMessage("Can't find user " + astring[1] + ". No tp.");
/* 179 */           } else if (entityplayer2 == null) {
/* 180 */             icommandlistener.sendMessage("Can't find user " + astring[2] + ". No tp.");
/* 181 */           } else if (entityplayer.dimension != entityplayer2.dimension) {
/* 182 */             icommandlistener.sendMessage("User " + astring[1] + " and " + astring[2] + " are in different dimensions. No tp.");
/*     */           } else {
/* 184 */             entityplayer.netServerHandler.a(entityplayer2.locX, entityplayer2.locY, entityplayer2.locZ, entityplayer2.yaw, entityplayer2.pitch);
/* 185 */             print(s1, "Teleporting " + astring[1] + " to " + astring[2] + ".");
/*     */           } 
/*     */         } else {
/* 188 */           icommandlistener.sendMessage("Syntax error, please provice a source and a target.");
/*     */         
/*     */         }
/*     */ 
/*     */       
/*     */       }
/* 194 */       else if (s.toLowerCase().startsWith("give ")) {
/* 195 */         if (!checkPermission(this.listener, "give")) return true; 
/* 196 */         String[] astring = s.split(" ");
/* 197 */         if (astring.length != 3 && astring.length != 4) {
/* 198 */           return true;
/*     */         }
/*     */         
/* 201 */         String s3 = astring[1];
/* 202 */         EntityPlayer entityplayer2 = serverconfigurationmanager.i(s3);
/* 203 */         if (entityplayer2 != null) {
/*     */           try {
/* 205 */             int k = Integer.parseInt(astring[2]);
/* 206 */             if (Item.byId[k] != null) {
/* 207 */               print(s1, "Giving " + entityplayer2.name + " some " + k);
/* 208 */               int l = 1;
/*     */               
/* 210 */               if (astring.length > 3) {
/* 211 */                 l = a(astring[3], 1);
/*     */               }
/*     */               
/* 214 */               if (l < 1) {
/* 215 */                 l = 1;
/*     */               }
/*     */               
/* 218 */               if (l > 64) {
/* 219 */                 l = 64;
/*     */               }
/*     */               
/* 222 */               entityplayer2.b(new ItemStack(k, l, false));
/*     */             } else {
/* 224 */               icommandlistener.sendMessage("There's no item with id " + k);
/*     */             } 
/* 226 */           } catch (NumberFormatException numberformatexception) {
/* 227 */             icommandlistener.sendMessage("There's no item with id " + astring[2]);
/*     */           } 
/*     */         } else {
/* 230 */           icommandlistener.sendMessage("Can't find user " + s3);
/*     */         } 
/* 232 */       } else if (s.toLowerCase().startsWith("time ")) {
/* 233 */         String[] astring = s.split(" ");
/* 234 */         if (astring.length != 3) {
/* 235 */           return true;
/*     */         }
/*     */         
/* 238 */         String s3 = astring[1];
/*     */         
/*     */         try {
/* 241 */           int j = Integer.parseInt(astring[2]);
/*     */ 
/*     */           
/* 244 */           if ("add".equalsIgnoreCase(s3)) {
/* 245 */             if (!checkPermission(this.listener, "time.add")) return true; 
/* 246 */             for (byte b = 0; b < this.server.worlds.size(); b++) {
/* 247 */               WorldServer worldserver1 = (WorldServer)this.server.worlds.get(b);
/* 248 */               worldserver1.setTimeAndFixTicklists(worldserver1.getTime() + j);
/*     */             } 
/*     */             
/* 251 */             print(s1, "Added " + j + " to time");
/* 252 */           } else if ("set".equalsIgnoreCase(s3)) {
/* 253 */             if (!checkPermission(this.listener, "time.set")) return true; 
/* 254 */             for (int k = 0; k < this.server.worlds.size(); k++) {
/* 255 */               WorldServer worldserver1 = (WorldServer)this.server.worlds.get(k);
/* 256 */               worldserver1.setTimeAndFixTicklists(j);
/*     */             } 
/*     */             
/* 259 */             print(s1, "Set time to " + j);
/*     */           } else {
/* 261 */             icommandlistener.sendMessage("Unknown method, use either \"add\" or \"set\"");
/*     */           } 
/* 263 */         } catch (NumberFormatException numberformatexception1) {
/* 264 */           icommandlistener.sendMessage("Unable to convert time value, " + astring[2]);
/*     */         } 
/* 266 */       } else if (s.toLowerCase().startsWith("say ")) {
/* 267 */         if (!checkPermission(this.listener, "say")) return true; 
/* 268 */         s = s.substring(s.indexOf(" ")).trim();
/* 269 */         a.info("[" + s1 + "] " + s);
/* 270 */         serverconfigurationmanager.sendAll(new Packet3Chat("§d[Server] " + s));
/* 271 */       } else if (s.toLowerCase().startsWith("tell ")) {
/* 272 */         if (!checkPermission(this.listener, "tell")) return true; 
/* 273 */         String[] astring = s.split(" ");
/*     */         
/* 275 */         s = s.substring(s.indexOf(" ")).trim();
/* 276 */         s = s.substring(s.indexOf(" ")).trim();
/* 277 */         a.info("[" + s1 + "->" + astring[1] + "] " + s);
/* 278 */         s = "§7" + s1 + " whispers " + s;
/* 279 */         a.info(s);
/* 280 */         if (astring.length >= 3 && !serverconfigurationmanager.a(astring[1], new Packet3Chat(s))) {
/* 281 */           icommandlistener.sendMessage("There's no player by that name online.");
/*     */         }
/*     */       }
/* 284 */       else if (s.toLowerCase().startsWith("whitelist ")) {
/* 285 */         a(s1, s, icommandlistener);
/*     */       } else {
/* 287 */         icommandlistener.sendMessage("Unknown console command. Type \"help\" for help.");
/* 288 */         return false;
/*     */       
/*     */       }
/*     */ 
/*     */     
/*     */     }
/*     */     else {
/*     */ 
/*     */       
/* 297 */       if (!checkPermission(this.listener, "help")) return true; 
/* 298 */       a(icommandlistener);
/*     */     } 
/*     */     
/* 301 */     return true;
/*     */   }
/*     */   
/*     */   private void a(String s, String s1, ICommandListener icommandlistener) {
/* 305 */     String[] astring = s1.split(" ");
/* 306 */     this.listener = icommandlistener;
/*     */     
/* 308 */     if (astring.length >= 2) {
/* 309 */       String s2 = astring[1].toLowerCase();
/*     */       
/* 311 */       if ("on".equals(s2)) {
/* 312 */         if (!checkPermission(this.listener, "whitelist.enable"))
/* 313 */           return;  print(s, "Turned on white-listing");
/* 314 */         this.server.propertyManager.b("white-list", true);
/* 315 */       } else if ("off".equals(s2)) {
/* 316 */         if (!checkPermission(this.listener, "whitelist.disable"))
/* 317 */           return;  print(s, "Turned off white-listing");
/* 318 */         this.server.propertyManager.b("white-list", false);
/* 319 */       } else if ("list".equals(s2)) {
/* 320 */         if (!checkPermission(this.listener, "whitelist.list"))
/* 321 */           return;  Set set = this.server.serverConfigurationManager.e();
/* 322 */         String s3 = "";
/*     */ 
/*     */ 
/*     */         
/* 326 */         for (Iterator iterator = set.iterator(); iterator.hasNext(); s3 = s3 + s4 + " ") {
/* 327 */           String s4 = (String)iterator.next();
/*     */         }
/*     */         
/* 330 */         icommandlistener.sendMessage("White-listed players: " + s3);
/*     */ 
/*     */       
/*     */       }
/* 334 */       else if ("add".equals(s2) && astring.length == 3) {
/* 335 */         if (!checkPermission(this.listener, "whitelist.add"))
/* 336 */           return;  String s5 = astring[2].toLowerCase();
/* 337 */         this.server.serverConfigurationManager.k(s5);
/* 338 */         print(s, "Added " + s5 + " to white-list");
/* 339 */       } else if ("remove".equals(s2) && astring.length == 3) {
/* 340 */         if (!checkPermission(this.listener, "whitelist.remove"))
/* 341 */           return;  String s5 = astring[2].toLowerCase();
/* 342 */         this.server.serverConfigurationManager.l(s5);
/* 343 */         print(s, "Removed " + s5 + " from white-list");
/* 344 */       } else if ("reload".equals(s2)) {
/* 345 */         if (!checkPermission(this.listener, "whitelist.reload"))
/* 346 */           return;  this.server.serverConfigurationManager.f();
/* 347 */         print(s, "Reloaded white-list from file");
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void a(ICommandListener icommandlistener) {
/* 354 */     icommandlistener.sendMessage("To run the server without a gui, start it like this:");
/* 355 */     icommandlistener.sendMessage("   java -Xmx1024M -Xms1024M -jar minecraft_server.jar nogui");
/* 356 */     icommandlistener.sendMessage("Console commands:");
/* 357 */     icommandlistener.sendMessage("   help  or  ?               shows this message");
/* 358 */     icommandlistener.sendMessage("   kick <player>             removes a player from the server");
/* 359 */     icommandlistener.sendMessage("   ban <player>              bans a player from the server");
/* 360 */     icommandlistener.sendMessage("   pardon <player>           pardons a banned player so that they can connect again");
/* 361 */     icommandlistener.sendMessage("   ban-ip <ip>               bans an IP address from the server");
/* 362 */     icommandlistener.sendMessage("   pardon-ip <ip>            pardons a banned IP address so that they can connect again");
/* 363 */     icommandlistener.sendMessage("   op <player>               turns a player into an op");
/* 364 */     icommandlistener.sendMessage("   deop <player>             removes op status from a player");
/* 365 */     icommandlistener.sendMessage("   tp <player1> <player2>    moves one player to the same location as another player");
/* 366 */     icommandlistener.sendMessage("   give <player> <id> [num]  gives a player a resource");
/* 367 */     icommandlistener.sendMessage("   tell <player> <message>   sends a private message to a player");
/* 368 */     icommandlistener.sendMessage("   stop                      gracefully stops the server");
/* 369 */     icommandlistener.sendMessage("   save-all                  forces a server-wide level save");
/* 370 */     icommandlistener.sendMessage("   save-off                  disables terrain saving (useful for backup scripts)");
/* 371 */     icommandlistener.sendMessage("   save-on                   re-enables terrain saving");
/* 372 */     icommandlistener.sendMessage("   list                      lists all currently connected players");
/* 373 */     icommandlistener.sendMessage("   say <message>             broadcasts a message to all players");
/* 374 */     icommandlistener.sendMessage("   time <add|set> <amount>   adds to or sets the world time (0-24000)");
/*     */   }
/*     */   
/*     */   private void print(String s, String s1) {
/* 378 */     String s2 = s + ": " + s1;
/*     */ 
/*     */     
/* 381 */     this.listener.sendMessage(s1);
/* 382 */     informOps("§7(" + s2 + ")");
/* 383 */     if (this.listener instanceof MinecraftServer) {
/*     */       return;
/*     */     }
/*     */     
/* 387 */     a.info(s2);
/*     */   }
/*     */ 
/*     */   
/*     */   private void informOps(String msg) {
/* 392 */     Packet3Chat packet3chat = new Packet3Chat(msg);
/* 393 */     EntityPlayer sender = null;
/* 394 */     if (this.listener instanceof ServerCommandListener) {
/* 395 */       CommandSender commandSender = ((ServerCommandListener)this.listener).getSender();
/* 396 */       if (commandSender instanceof CraftPlayer) {
/* 397 */         sender = ((CraftPlayer)commandSender).getHandle();
/*     */       }
/*     */     } 
/* 400 */     List<EntityPlayer> players = this.server.serverConfigurationManager.players;
/* 401 */     for (int i = 0; i < players.size(); i++) {
/* 402 */       EntityPlayer entityPlayer = (EntityPlayer)players.get(i);
/* 403 */       if (sender != entityPlayer && this.server.serverConfigurationManager.isOp(entityPlayer.name)) {
/* 404 */         entityPlayer.netServerHandler.sendPacket(packet3chat);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private int a(String s, int i) {
/*     */     try {
/* 412 */       return Integer.parseInt(s);
/* 413 */     } catch (NumberFormatException numberformatexception) {
/* 414 */       return i;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\ConsoleCommandHandler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */