/*    */ package org.bukkit.craftbukkit.command;
/*    */ 
/*    */ import java.util.EnumMap;
/*    */ import java.util.Map;
/*    */ import jline.ANSIBuffer;
/*    */ import jline.ConsoleReader;
/*    */ import jline.Terminal;
/*    */ import org.bukkit.ChatColor;
/*    */ import org.bukkit.command.ConsoleCommandSender;
/*    */ import org.bukkit.craftbukkit.CraftServer;
/*    */ 
/*    */ public class ColouredConsoleSender extends ConsoleCommandSender {
/*    */   private final ConsoleReader reader;
/*    */   private final Terminal terminal;
/* 15 */   private final Map<ChatColor, String> replacements = new EnumMap(ChatColor.class);
/* 16 */   private final ChatColor[] colors = ChatColor.values();
/*    */   
/*    */   public ColouredConsoleSender(CraftServer server) {
/* 19 */     super(server);
/* 20 */     this.reader = server.getReader();
/* 21 */     this.terminal = this.reader.getTerminal();
/*    */     
/* 23 */     this.replacements.put(ChatColor.BLACK, ANSIBuffer.ANSICodes.attrib(0));
/* 24 */     this.replacements.put(ChatColor.DARK_BLUE, ANSIBuffer.ANSICodes.attrib(34));
/* 25 */     this.replacements.put(ChatColor.DARK_GREEN, ANSIBuffer.ANSICodes.attrib(32));
/* 26 */     this.replacements.put(ChatColor.DARK_AQUA, ANSIBuffer.ANSICodes.attrib(36));
/* 27 */     this.replacements.put(ChatColor.DARK_RED, ANSIBuffer.ANSICodes.attrib(31));
/* 28 */     this.replacements.put(ChatColor.DARK_PURPLE, ANSIBuffer.ANSICodes.attrib(35));
/* 29 */     this.replacements.put(ChatColor.GOLD, ANSIBuffer.ANSICodes.attrib(33));
/* 30 */     this.replacements.put(ChatColor.GRAY, ANSIBuffer.ANSICodes.attrib(37));
/* 31 */     this.replacements.put(ChatColor.DARK_GRAY, ANSIBuffer.ANSICodes.attrib(0));
/* 32 */     this.replacements.put(ChatColor.BLUE, ANSIBuffer.ANSICodes.attrib(34));
/* 33 */     this.replacements.put(ChatColor.GREEN, ANSIBuffer.ANSICodes.attrib(32));
/* 34 */     this.replacements.put(ChatColor.AQUA, ANSIBuffer.ANSICodes.attrib(36));
/* 35 */     this.replacements.put(ChatColor.RED, ANSIBuffer.ANSICodes.attrib(31));
/* 36 */     this.replacements.put(ChatColor.LIGHT_PURPLE, ANSIBuffer.ANSICodes.attrib(35));
/* 37 */     this.replacements.put(ChatColor.YELLOW, ANSIBuffer.ANSICodes.attrib(33));
/* 38 */     this.replacements.put(ChatColor.WHITE, ANSIBuffer.ANSICodes.attrib(37));
/*    */   }
/*    */ 
/*    */   
/*    */   public void sendMessage(String message) {
/* 43 */     if (this.terminal.isANSISupported()) {
/* 44 */       String result = message;
/*    */       
/* 46 */       for (ChatColor color : this.colors) {
/* 47 */         if (this.replacements.containsKey(color)) {
/* 48 */           result = result.replaceAll(color.toString(), (String)this.replacements.get(color));
/*    */         } else {
/* 50 */           result = result.replaceAll(color.toString(), "");
/*    */         } 
/*    */       } 
/* 53 */       System.out.println(result + ANSIBuffer.ANSICodes.attrib(0));
/*    */     } else {
/* 55 */       super.sendMessage(message);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\craftbukkit\command\ColouredConsoleSender.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */