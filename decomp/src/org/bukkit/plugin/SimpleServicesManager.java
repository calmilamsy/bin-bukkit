/*     */ package org.bukkit.plugin;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.NoSuchElementException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SimpleServicesManager
/*     */   implements ServicesManager
/*     */ {
/*  22 */   private final Map<Class<?>, List<RegisteredServiceProvider<?>>> providers = new HashMap();
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
/*     */ 
/*     */   
/*     */   public <T> void register(Class<T> service, T provider, Plugin plugin, ServicePriority priority) {
/*  36 */     synchronized (this.providers) {
/*  37 */       List<RegisteredServiceProvider<?>> registered = (List)this.providers.get(service);
/*     */       
/*  39 */       if (registered == null) {
/*  40 */         registered = new ArrayList<RegisteredServiceProvider<?>>();
/*  41 */         this.providers.put(service, registered);
/*     */       } 
/*     */       
/*  44 */       registered.add(new RegisteredServiceProvider(service, provider, priority, plugin));
/*     */ 
/*     */ 
/*     */       
/*  48 */       Collections.sort(registered);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void unregisterAll(Plugin plugin) {
/*  58 */     synchronized (this.providers) {
/*  59 */       Iterator<Map.Entry<Class<?>, List<RegisteredServiceProvider<?>>>> it = this.providers.entrySet().iterator();
/*     */       
/*     */       try {
/*  62 */         while (it.hasNext()) {
/*  63 */           Map.Entry<Class<?>, List<RegisteredServiceProvider<?>>> entry = (Map.Entry)it.next();
/*  64 */           Iterator<RegisteredServiceProvider<?>> it2 = ((List)entry.getValue()).iterator();
/*     */ 
/*     */ 
/*     */           
/*     */           try {
/*  69 */             while (it2.hasNext()) {
/*  70 */               if (((RegisteredServiceProvider)it2.next()).getPlugin() == plugin) {
/*  71 */                 it2.remove();
/*     */               }
/*     */             } 
/*  74 */           } catch (NoSuchElementException e) {}
/*     */ 
/*     */ 
/*     */           
/*  78 */           if (((List)entry.getValue()).size() == 0) {
/*  79 */             it.remove();
/*     */           }
/*     */         } 
/*  82 */       } catch (NoSuchElementException e) {}
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void unregister(Class<?> service, Object provider) {
/*  93 */     synchronized (this.providers) {
/*  94 */       Iterator<Map.Entry<Class<?>, List<RegisteredServiceProvider<?>>>> it = this.providers.entrySet().iterator();
/*     */       
/*     */       try {
/*  97 */         while (it.hasNext()) {
/*  98 */           Map.Entry<Class<?>, List<RegisteredServiceProvider<?>>> entry = (Map.Entry)it.next();
/*     */ 
/*     */           
/* 101 */           if (entry.getKey() != service) {
/*     */             continue;
/*     */           }
/*     */           
/* 105 */           Iterator<RegisteredServiceProvider<?>> it2 = ((List)entry.getValue()).iterator();
/*     */ 
/*     */ 
/*     */           
/*     */           try {
/* 110 */             while (it2.hasNext()) {
/* 111 */               if (((RegisteredServiceProvider)it2.next()).getProvider() == provider) {
/* 112 */                 it2.remove();
/*     */               }
/*     */             } 
/* 115 */           } catch (NoSuchElementException e) {}
/*     */ 
/*     */ 
/*     */           
/* 119 */           if (((List)entry.getValue()).size() == 0) {
/* 120 */             it.remove();
/*     */           }
/*     */         } 
/* 123 */       } catch (NoSuchElementException e) {}
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void unregister(Object provider) {
/* 133 */     synchronized (this.providers) {
/* 134 */       Iterator<Map.Entry<Class<?>, List<RegisteredServiceProvider<?>>>> it = this.providers.entrySet().iterator();
/*     */       
/*     */       try {
/* 137 */         while (it.hasNext()) {
/* 138 */           Map.Entry<Class<?>, List<RegisteredServiceProvider<?>>> entry = (Map.Entry)it.next();
/* 139 */           Iterator<RegisteredServiceProvider<?>> it2 = ((List)entry.getValue()).iterator();
/*     */ 
/*     */ 
/*     */           
/*     */           try {
/* 144 */             while (it2.hasNext()) {
/* 145 */               if (((RegisteredServiceProvider)it2.next()).getProvider() == provider) {
/* 146 */                 it2.remove();
/*     */               }
/*     */             } 
/* 149 */           } catch (NoSuchElementException e) {}
/*     */ 
/*     */ 
/*     */           
/* 153 */           if (((List)entry.getValue()).size() == 0) {
/* 154 */             it.remove();
/*     */           }
/*     */         } 
/* 157 */       } catch (NoSuchElementException e) {}
/*     */     } 
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
/*     */   public <T> T load(Class<T> service) {
/* 171 */     synchronized (this.providers) {
/* 172 */       List<RegisteredServiceProvider<?>> registered = (List)this.providers.get(service);
/*     */       
/* 174 */       if (registered == null) {
/* 175 */         return null;
/*     */       }
/*     */ 
/*     */       
/* 179 */       return (T)((RegisteredServiceProvider)registered.get(0)).getProvider();
/*     */     } 
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
/*     */   public <T> RegisteredServiceProvider<T> getRegistration(Class<T> service) {
/* 193 */     synchronized (this.providers) {
/* 194 */       List<RegisteredServiceProvider<?>> registered = (List)this.providers.get(service);
/*     */       
/* 196 */       if (registered == null) {
/* 197 */         return null;
/*     */       }
/*     */ 
/*     */       
/* 201 */       return (RegisteredServiceProvider)registered.get(0);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<RegisteredServiceProvider<?>> getRegistrations(Plugin plugin) {
/* 212 */     synchronized (this.providers) {
/* 213 */       List<RegisteredServiceProvider<?>> ret = new ArrayList<RegisteredServiceProvider<?>>();
/*     */       
/* 215 */       for (List<RegisteredServiceProvider<?>> registered : this.providers.values()) {
/* 216 */         for (RegisteredServiceProvider<?> provider : registered) {
/* 217 */           if (provider.getPlugin() == plugin) {
/* 218 */             ret.add(provider);
/*     */           }
/*     */         } 
/*     */       } 
/*     */       
/* 223 */       return ret;
/*     */     } 
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
/*     */   public <T> Collection<RegisteredServiceProvider<T>> getRegistrations(Class<T> service) {
/* 237 */     synchronized (this.providers) {
/* 238 */       List<RegisteredServiceProvider<?>> registered = (List)this.providers.get(service);
/*     */       
/* 240 */       if (registered == null) {
/* 241 */         return Collections.unmodifiableList(new ArrayList());
/*     */       }
/*     */       
/* 244 */       List<RegisteredServiceProvider<T>> ret = new ArrayList<RegisteredServiceProvider<T>>();
/*     */       
/* 246 */       for (RegisteredServiceProvider<?> provider : registered) {
/* 247 */         ret.add(provider);
/*     */       }
/*     */       
/* 250 */       return Collections.unmodifiableList(ret);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 261 */   public Collection<Class<?>> getKnownServices() { return Collections.unmodifiableSet(this.providers.keySet()); }
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
/*     */ 
/*     */   
/* 274 */   public <T> boolean isProvidedFor(Class<T> service) { return (getRegistration(service) != null); }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\plugin\SimpleServicesManager.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */