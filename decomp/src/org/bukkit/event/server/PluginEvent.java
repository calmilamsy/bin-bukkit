/*    */ package org.bukkit.event.server;
/*    */ 
/*    */ import org.bukkit.event.Event;
/*    */ import org.bukkit.plugin.Plugin;
/*    */ 
/*    */ public class PluginEvent
/*    */   extends ServerEvent
/*    */ {
/*    */   private final Plugin plugin;
/*    */   
/*    */   public PluginEvent(Event.Type type, Plugin plugin) {
/* 12 */     super(type);
/*    */     
/* 14 */     this.plugin = plugin;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 23 */   public Plugin getPlugin() { return this.plugin; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\event\server\PluginEvent.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */