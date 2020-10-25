/*    */ package org.bukkit.craftbukkit.util;
/*    */ 
/*    */ import java.io.PrintWriter;
/*    */ import java.io.StringWriter;
/*    */ import java.text.SimpleDateFormat;
/*    */ import java.util.logging.Formatter;
/*    */ import java.util.logging.LogRecord;
/*    */ import joptsimple.OptionException;
/*    */ import joptsimple.OptionSet;
/*    */ import net.minecraft.server.MinecraftServer;
/*    */ 
/*    */ public class ShortConsoleLogFormatter extends Formatter {
/*    */   private final SimpleDateFormat date;
/*    */   
/*    */   public ShortConsoleLogFormatter(MinecraftServer server) {
/* 16 */     OptionSet options = server.options;
/* 17 */     SimpleDateFormat date = null;
/*    */     
/* 19 */     if (options.has("date-format")) {
/*    */       try {
/* 21 */         Object object = options.valueOf("date-format");
/*    */         
/* 23 */         if (object != null && object instanceof SimpleDateFormat) {
/* 24 */           date = (SimpleDateFormat)object;
/*    */         }
/* 26 */       } catch (OptionException ex) {
/* 27 */         System.err.println("Given date format is not valid. Falling back to default.");
/*    */       } 
/* 29 */     } else if (options.has("nojline")) {
/* 30 */       date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
/*    */     } 
/*    */     
/* 33 */     if (date == null) {
/* 34 */       date = new SimpleDateFormat("HH:mm:ss");
/*    */     }
/*    */     
/* 37 */     this.date = date;
/*    */   }
/*    */ 
/*    */   
/*    */   public String format(LogRecord record) {
/* 42 */     StringBuilder builder = new StringBuilder();
/* 43 */     Throwable ex = record.getThrown();
/*    */     
/* 45 */     builder.append(this.date.format(Long.valueOf(record.getMillis())));
/* 46 */     builder.append(" [");
/* 47 */     builder.append(record.getLevel().getLocalizedName().toUpperCase());
/* 48 */     builder.append("] ");
/* 49 */     builder.append(record.getMessage());
/* 50 */     builder.append('\n');
/*    */     
/* 52 */     if (ex != null) {
/* 53 */       StringWriter writer = new StringWriter();
/* 54 */       ex.printStackTrace(new PrintWriter(writer));
/* 55 */       builder.append(writer);
/*    */     } 
/*    */     
/* 58 */     return builder.toString();
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\craftbukki\\util\ShortConsoleLogFormatter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */