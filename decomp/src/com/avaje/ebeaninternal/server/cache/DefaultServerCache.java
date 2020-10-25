/*     */ package com.avaje.ebeaninternal.server.cache;
/*     */ 
/*     */ import com.avaje.ebean.BackgroundExecutor;
/*     */ import com.avaje.ebean.EbeanServer;
/*     */ import com.avaje.ebean.cache.ServerCache;
/*     */ import com.avaje.ebean.cache.ServerCacheOptions;
/*     */ import com.avaje.ebean.cache.ServerCacheStatistics;
/*     */ import java.io.Serializable;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.Comparator;
/*     */ import java.util.Iterator;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import java.util.concurrent.atomic.AtomicInteger;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DefaultServerCache
/*     */   implements ServerCache
/*     */ {
/*  30 */   private static final Logger logger = Logger.getLogger(DefaultServerCache.class.getName());
/*     */   
/*  32 */   private static final CacheEntryComparator comparator = new CacheEntryComparator(null);
/*     */   
/*     */   private final ConcurrentHashMap<Object, CacheEntry> map;
/*     */   
/*     */   private final AtomicInteger missCount;
/*     */   
/*     */   private final AtomicInteger removedHitCount;
/*     */   
/*     */   private final Object monitor;
/*     */   
/*     */   private final String name;
/*     */   
/*     */   private int maxSize;
/*     */   
/*     */   private long trimFrequency;
/*     */   
/*     */   private int maxIdleSecs;
/*     */   
/*     */   private int maxSecsToLive;
/*     */ 
/*     */   
/*  53 */   public DefaultServerCache(String name, ServerCacheOptions options) { this(name, options.getMaxSize(), options.getMaxIdleSecs(), options.getMaxSecsToLive()); } public DefaultServerCache(String name, int maxSize, int maxIdleSecs, int maxSecsToLive) { this.map = new ConcurrentHashMap();
/*     */     this.missCount = new AtomicInteger();
/*     */     this.removedHitCount = new AtomicInteger();
/*     */     this.monitor = new Object();
/*  57 */     this.name = name;
/*  58 */     this.maxSize = maxSize;
/*  59 */     this.maxIdleSecs = maxIdleSecs;
/*  60 */     this.maxSecsToLive = maxSecsToLive;
/*  61 */     this.trimFrequency = 60L; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void init(EbeanServer server) {
/*  67 */     TrimTask trim = new TrimTask(null);
/*     */     
/*  69 */     BackgroundExecutor executor = server.getBackgroundExecutor();
/*  70 */     executor.executePeriodically(trim, this.trimFrequency, TimeUnit.SECONDS);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ServerCacheStatistics getStatistics(boolean reset) {
/*  78 */     ServerCacheStatistics s = new ServerCacheStatistics();
/*  79 */     s.setCacheName(this.name);
/*  80 */     s.setMaxSize(this.maxSize);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  85 */     int mc = reset ? this.missCount.getAndSet(0) : this.missCount.get();
/*  86 */     int hc = getHitCount(reset);
/*  87 */     int size = size();
/*     */     
/*  89 */     s.setSize(size);
/*  90 */     s.setHitCount(hc);
/*  91 */     s.setMissCount(mc);
/*     */     
/*  93 */     return s;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getHitRatio() {
/*  98 */     int mc = this.missCount.get();
/*  99 */     int hc = getHitCount(false);
/*     */     
/* 101 */     int totalCount = hc + mc;
/* 102 */     if (totalCount == 0) {
/* 103 */       return 0;
/*     */     }
/* 105 */     return hc * 100 / totalCount;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int getHitCount(boolean reset) {
/* 112 */     int hc = reset ? this.removedHitCount.getAndSet(0) : this.removedHitCount.get();
/*     */     
/* 114 */     Iterator<CacheEntry> it = this.map.values().iterator();
/* 115 */     while (it.hasNext()) {
/* 116 */       CacheEntry cacheEntry = (CacheEntry)it.next();
/* 117 */       hc += cacheEntry.getHitCount(reset);
/*     */     } 
/*     */     
/* 120 */     return hc;
/*     */   }
/*     */ 
/*     */   
/*     */   public ServerCacheOptions getOptions() {
/* 125 */     synchronized (this.monitor) {
/* 126 */       ServerCacheOptions o = new ServerCacheOptions();
/* 127 */       o.setMaxIdleSecs(this.maxIdleSecs);
/* 128 */       o.setMaxSize(this.maxSize);
/* 129 */       o.setMaxSecsToLive(this.maxSecsToLive);
/* 130 */       return o;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setOptions(ServerCacheOptions o) {
/* 135 */     synchronized (this.monitor) {
/* 136 */       this.maxIdleSecs = o.getMaxIdleSecs();
/* 137 */       this.maxSize = o.getMaxSize();
/* 138 */       this.maxSecsToLive = o.getMaxSecsToLive();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 147 */   public int getMaxSize() { return this.maxSize; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMaxSize(int maxSize) {
/* 154 */     synchronized (this.monitor) {
/* 155 */       this.maxSize = maxSize;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 163 */   public long getMaxIdleSecs() { return this.maxIdleSecs; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMaxIdleSecs(int maxIdleSecs) {
/* 170 */     synchronized (this.monitor) {
/* 171 */       this.maxIdleSecs = maxIdleSecs;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 179 */   public long getMaxSecsToLive() { return this.maxSecsToLive; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMaxSecsToLive(int maxSecsToLive) {
/* 186 */     synchronized (this.monitor) {
/* 187 */       this.maxSecsToLive = maxSecsToLive;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 195 */   public String getName() { return this.name; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 202 */   public void clear() { this.map.clear(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object get(Object key) {
/* 210 */     CacheEntry entry = (CacheEntry)this.map.get(key);
/*     */     
/* 212 */     if (entry == null) {
/* 213 */       this.missCount.incrementAndGet();
/* 214 */       return null;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 219 */     return entry.getValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object put(Object key, Object value) {
/* 228 */     CacheEntry entry = (CacheEntry)this.map.put(key, new CacheEntry(key, value));
/* 229 */     if (entry == null) {
/* 230 */       return null;
/*     */     }
/* 232 */     int removedHits = entry.getHitCount(true);
/* 233 */     this.removedHitCount.addAndGet(removedHits);
/* 234 */     return entry.getValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object putIfAbsent(Object key, Object value) {
/* 242 */     CacheEntry entry = (CacheEntry)this.map.putIfAbsent(key, new CacheEntry(key, value));
/* 243 */     if (entry == null) {
/* 244 */       return null;
/*     */     }
/* 246 */     return entry.getValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object remove(Object key) {
/* 254 */     CacheEntry entry = (CacheEntry)this.map.remove(key);
/* 255 */     if (entry == null) {
/* 256 */       return null;
/*     */     }
/* 258 */     int removedHits = entry.getHitCount(true);
/* 259 */     this.removedHitCount.addAndGet(removedHits);
/* 260 */     return entry.getValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 268 */   public int size() { return this.map.size(); }
/*     */ 
/*     */ 
/*     */   
/* 272 */   private Iterator<CacheEntry> cacheEntries() { return this.map.values().iterator(); }
/*     */ 
/*     */   
/*     */   private class TrimTask
/*     */     implements Runnable
/*     */   {
/*     */     private TrimTask() {}
/*     */ 
/*     */     
/*     */     public void run() {
/* 282 */       long startTime = System.currentTimeMillis();
/*     */       
/* 284 */       if (logger.isLoggable(Level.FINER)) {
/* 285 */         logger.finer("trimming cache " + DefaultServerCache.this.name);
/*     */       }
/*     */       
/* 288 */       int trimmedByIdle = 0;
/* 289 */       int trimmedByTTL = 0;
/* 290 */       int trimmedByLRU = 0;
/*     */       
/* 292 */       boolean trimMaxSize = (DefaultServerCache.this.maxSize > 0 && DefaultServerCache.this.maxSize < DefaultServerCache.this.size());
/*     */       
/* 294 */       ArrayList<DefaultServerCache.CacheEntry> activeList = new ArrayList<DefaultServerCache.CacheEntry>();
/*     */       
/* 296 */       long idleExpire = System.currentTimeMillis() - (DefaultServerCache.this.maxIdleSecs * 1000);
/* 297 */       long ttlExpire = System.currentTimeMillis() - (DefaultServerCache.this.maxSecsToLive * 1000);
/*     */       
/* 299 */       Iterator<DefaultServerCache.CacheEntry> it = DefaultServerCache.this.cacheEntries();
/* 300 */       while (it.hasNext()) {
/* 301 */         DefaultServerCache.CacheEntry cacheEntry = (DefaultServerCache.CacheEntry)it.next();
/* 302 */         if (DefaultServerCache.this.maxIdleSecs > 0 && idleExpire > cacheEntry.getLastAccessTime()) {
/* 303 */           it.remove();
/* 304 */           trimmedByIdle++; continue;
/*     */         } 
/* 306 */         if (DefaultServerCache.this.maxSecsToLive > 0 && ttlExpire > cacheEntry.getCreateTime()) {
/* 307 */           it.remove();
/* 308 */           trimmedByTTL++; continue;
/*     */         } 
/* 310 */         if (trimMaxSize) {
/* 311 */           activeList.add(cacheEntry);
/*     */         }
/*     */       } 
/*     */       
/* 315 */       if (trimMaxSize) {
/* 316 */         trimmedByLRU = activeList.size() - DefaultServerCache.this.maxSize;
/*     */         
/* 318 */         if (trimmedByLRU > 0) {
/*     */           
/* 320 */           Collections.sort(activeList, comparator);
/* 321 */           for (int i = DefaultServerCache.this.maxSize; i < activeList.size(); i++)
/*     */           {
/* 323 */             DefaultServerCache.this.map.remove(((DefaultServerCache.CacheEntry)activeList.get(i)).getKey());
/*     */           }
/*     */         } 
/*     */       } 
/*     */       
/* 328 */       long exeTime = System.currentTimeMillis() - startTime;
/*     */       
/* 330 */       if (logger.isLoggable(Level.FINE)) {
/* 331 */         logger.fine("Executed trim of cache " + DefaultServerCache.this.name + " in [" + exeTime + "]millis  idle[" + trimmedByIdle + "] timeToLive[" + trimmedByTTL + "] accessTime[" + trimmedByLRU + "]");
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static class CacheEntryComparator
/*     */     extends Object
/*     */     implements Comparator<CacheEntry>, Serializable
/*     */   {
/*     */     private static final long serialVersionUID = 1L;
/*     */ 
/*     */ 
/*     */     
/*     */     private CacheEntryComparator() {}
/*     */ 
/*     */ 
/*     */     
/* 350 */     public int compare(DefaultServerCache.CacheEntry o1, DefaultServerCache.CacheEntry o2) { return o1.getLastAccessLong().compareTo(o2.getLastAccessLong()); }
/*     */   }
/*     */   
/*     */   public static class CacheEntry
/*     */   {
/*     */     private final Object key;
/*     */     private final Object value;
/*     */     private final long createTime;
/*     */     private final AtomicInteger hitCount;
/*     */     private Long lastAccessTime;
/*     */     
/*     */     public CacheEntry(Object key, Object value) {
/* 362 */       this.hitCount = new AtomicInteger();
/*     */ 
/*     */ 
/*     */       
/* 366 */       this.key = key;
/* 367 */       this.value = value;
/* 368 */       this.createTime = System.currentTimeMillis();
/* 369 */       this.lastAccessTime = Long.valueOf(this.createTime);
/*     */     }
/*     */ 
/*     */     
/* 373 */     public Object getKey() { return this.key; }
/*     */ 
/*     */ 
/*     */     
/*     */     public Object getValue() {
/* 378 */       this.hitCount.incrementAndGet();
/* 379 */       this.lastAccessTime = Long.valueOf(System.currentTimeMillis());
/* 380 */       return this.value;
/*     */     }
/*     */ 
/*     */     
/* 384 */     public long getCreateTime() { return this.createTime; }
/*     */ 
/*     */ 
/*     */     
/* 388 */     public long getLastAccessTime() { return this.lastAccessTime.longValue(); }
/*     */ 
/*     */ 
/*     */     
/* 392 */     public Long getLastAccessLong() { return this.lastAccessTime; }
/*     */ 
/*     */     
/*     */     public int getHitCount(boolean reset) {
/* 396 */       if (reset) {
/* 397 */         return this.hitCount.getAndSet(0);
/*     */       }
/*     */       
/* 400 */       return this.hitCount.get();
/*     */     }
/*     */ 
/*     */     
/* 404 */     public int getHitCount() { return this.hitCount.get(); }
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\cache\DefaultServerCache.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */