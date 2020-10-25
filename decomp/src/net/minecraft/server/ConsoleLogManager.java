/*    */ package net.minecraft.server;
/*    */ 
/*    */ import java.util.logging.FileHandler;
/*    */ import java.util.logging.Handler;
/*    */ import java.util.logging.Level;
/*    */ import java.util.logging.Logger;
/*    */ import org.bukkit.craftbukkit.util.ShortConsoleLogFormatter;
/*    */ import org.bukkit.craftbukkit.util.TerminalConsoleHandler;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ConsoleLogManager
/*    */ {
/* 16 */   public static Logger a = Logger.getLogger("Minecraft");
/* 17 */   public static Logger global = Logger.getLogger("");
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static void init(MinecraftServer server) {
/* 23 */     ConsoleLogFormatter consolelogformatter = new ConsoleLogFormatter();
/*    */     
/* 25 */     a.setUseParentHandlers(false);
/*    */     
/* 27 */     TerminalConsoleHandler terminalConsoleHandler = new TerminalConsoleHandler(server.reader);
/*    */     
/* 29 */     for (Handler handler : global.getHandlers()) {
/* 30 */       global.removeHandler(handler);
/*    */     }
/*    */     
/* 33 */     terminalConsoleHandler.setFormatter(new ShortConsoleLogFormatter(server));
/* 34 */     global.addHandler(terminalConsoleHandler);
/*    */ 
/*    */     
/* 37 */     a.addHandler(terminalConsoleHandler);
/*    */ 
/*    */     
/*    */     try {
/* 41 */       String pattern = (String)server.options.valueOf("log-pattern");
/* 42 */       int limit = ((Integer)server.options.valueOf("log-limit")).intValue();
/* 43 */       int count = ((Integer)server.options.valueOf("log-count")).intValue();
/* 44 */       boolean append = ((Boolean)server.options.valueOf("log-append")).booleanValue();
/* 45 */       FileHandler filehandler = new FileHandler(pattern, limit, count, append);
/*    */ 
/*    */       
/* 48 */       filehandler.setFormatter(consolelogformatter);
/* 49 */       a.addHandler(filehandler);
/* 50 */       global.addHandler(filehandler);
/* 51 */     } catch (Exception exception) {
/* 52 */       a.log(Level.WARNING, "Failed to log to server.log", exception);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\ConsoleLogManager.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */