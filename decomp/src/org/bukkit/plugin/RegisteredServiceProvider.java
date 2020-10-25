/*    */ package org.bukkit.plugin;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class RegisteredServiceProvider<T>
/*    */   extends Object
/*    */   implements Comparable<RegisteredServiceProvider<?>>
/*    */ {
/*    */   private Class<T> service;
/*    */   private Plugin plugin;
/*    */   private T provider;
/*    */   private ServicePriority priority;
/*    */   
/*    */   public RegisteredServiceProvider(Class<T> service, T provider, ServicePriority priority, Plugin plugin) {
/* 19 */     this.service = service;
/* 20 */     this.plugin = plugin;
/* 21 */     this.provider = provider;
/* 22 */     this.priority = priority;
/*    */   }
/*    */ 
/*    */   
/* 26 */   public Class<T> getService() { return this.service; }
/*    */ 
/*    */ 
/*    */   
/* 30 */   public Plugin getPlugin() { return this.plugin; }
/*    */ 
/*    */ 
/*    */   
/* 34 */   public T getProvider() { return (T)this.provider; }
/*    */ 
/*    */ 
/*    */   
/* 38 */   public ServicePriority getPriority() { return this.priority; }
/*    */ 
/*    */   
/*    */   public int compareTo(RegisteredServiceProvider<?> other) {
/* 42 */     if (this.priority.ordinal() == other.getPriority().ordinal()) {
/* 43 */       return 0;
/*    */     }
/* 45 */     return (this.priority.ordinal() < other.getPriority().ordinal()) ? 1 : -1;
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\plugin\RegisteredServiceProvider.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */