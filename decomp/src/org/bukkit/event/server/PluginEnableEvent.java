/*    */ package org.bukkit.event.server;
/*    */ 
/*    */ import org.bukkit.event.Event;
/*    */ import org.bukkit.plugin.Plugin;
/*    */ 
/*    */ 
/*    */ public class PluginEnableEvent
/*    */   extends PluginEvent
/*    */ {
/* 10 */   public PluginEnableEvent(Plugin plugin) { super(Event.Type.PLUGIN_ENABLE, plugin); }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\event\server\PluginEnableEvent.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */