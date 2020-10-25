/*    */ package org.bukkit.event.server;
/*    */ 
/*    */ import org.bukkit.event.Event;
/*    */ import org.bukkit.plugin.Plugin;
/*    */ 
/*    */ 
/*    */ public class PluginDisableEvent
/*    */   extends PluginEvent
/*    */ {
/* 10 */   public PluginDisableEvent(Plugin plugin) { super(Event.Type.PLUGIN_DISABLE, plugin); }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\event\server\PluginDisableEvent.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */