/*    */ package org.bukkit.plugin;
/*    */ 
/*    */ import org.bukkit.event.Event;
/*    */ import org.bukkit.event.Listener;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class RegisteredListener
/*    */ {
/*    */   private final Listener listener;
/*    */   private final Event.Priority priority;
/*    */   private final Plugin plugin;
/*    */   private final EventExecutor executor;
/*    */   
/*    */   public RegisteredListener(Listener pluginListener, EventExecutor eventExecutor, Event.Priority eventPriority, Plugin registeredPlugin) {
/* 16 */     this.listener = pluginListener;
/* 17 */     this.priority = eventPriority;
/* 18 */     this.plugin = registeredPlugin;
/* 19 */     this.executor = eventExecutor;
/*    */   }
/*    */   
/*    */   public RegisteredListener(Listener pluginListener, Event.Priority eventPriority, Plugin registeredPlugin, Event.Type type) {
/* 23 */     this.listener = pluginListener;
/* 24 */     this.priority = eventPriority;
/* 25 */     this.plugin = registeredPlugin;
/* 26 */     this.executor = registeredPlugin.getPluginLoader().createExecutor(type, pluginListener);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 34 */   public Listener getListener() { return this.listener; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 42 */   public Plugin getPlugin() { return this.plugin; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 50 */   public Event.Priority getPriority() { return this.priority; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 58 */   public void callEvent(Event event) { this.executor.execute(this.listener, event); }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\plugin\RegisteredListener.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */