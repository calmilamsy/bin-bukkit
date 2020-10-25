/*     */ package com.avaje.ebeaninternal.server.lib.sql;
/*     */ 
/*     */ import java.sql.SQLException;
/*     */ import java.util.Arrays;
/*     */ import java.util.Date;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import java.util.concurrent.locks.Condition;
/*     */ import java.util.concurrent.locks.ReentrantLock;
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
/*     */ 
/*     */ public class PooledConnectionQueue
/*     */ {
/*  37 */   private static final Logger logger = Logger.getLogger(PooledConnectionQueue.class.getName());
/*     */   
/*  39 */   private static final TimeUnit MILLIS_TIME_UNIT = TimeUnit.MILLISECONDS;
/*     */ 
/*     */ 
/*     */   
/*     */   private final String name;
/*     */ 
/*     */ 
/*     */   
/*     */   private final DataSourcePool pool;
/*     */ 
/*     */ 
/*     */   
/*     */   private final FreeConnectionBuffer freeList;
/*     */ 
/*     */ 
/*     */   
/*     */   private final BusyConnectionBuffer busyList;
/*     */ 
/*     */ 
/*     */   
/*     */   private final ReentrantLock lock;
/*     */ 
/*     */ 
/*     */   
/*     */   private final Condition notEmpty;
/*     */ 
/*     */ 
/*     */   
/*     */   private int connectionId;
/*     */ 
/*     */ 
/*     */   
/*     */   private long waitTimeoutMillis;
/*     */ 
/*     */ 
/*     */   
/*     */   private long leakTimeMinutes;
/*     */ 
/*     */ 
/*     */   
/*     */   private int warningSize;
/*     */ 
/*     */   
/*     */   private int maxSize;
/*     */ 
/*     */   
/*     */   private int minSize;
/*     */ 
/*     */   
/*     */   private int waitingThreads;
/*     */ 
/*     */   
/*     */   private int waitCount;
/*     */ 
/*     */   
/*     */   private int hitCount;
/*     */ 
/*     */   
/*     */   private int highWaterMark;
/*     */ 
/*     */   
/*     */   private long lastResetTime;
/*     */ 
/*     */   
/*     */   private boolean doingShutdown;
/*     */ 
/*     */ 
/*     */   
/*     */   public PooledConnectionQueue(DataSourcePool pool) {
/* 108 */     this.pool = pool;
/* 109 */     this.name = pool.getName();
/* 110 */     this.minSize = pool.getMinSize();
/* 111 */     this.maxSize = pool.getMaxSize();
/*     */     
/* 113 */     this.warningSize = pool.getWarningSize();
/* 114 */     this.waitTimeoutMillis = pool.getWaitTimeoutMillis();
/* 115 */     this.leakTimeMinutes = pool.getLeakTimeMinutes();
/*     */     
/* 117 */     this.busyList = new BusyConnectionBuffer(50, 20);
/* 118 */     this.freeList = new FreeConnectionBuffer(this.maxSize);
/*     */     
/* 120 */     this.lock = new ReentrantLock(true);
/* 121 */     this.notEmpty = this.lock.newCondition();
/*     */   }
/*     */ 
/*     */   
/* 125 */   private DataSourcePool.Status createStatus() { return new DataSourcePool.Status(this.name, this.minSize, this.maxSize, this.freeList.size(), this.busyList.size(), this.waitingThreads, this.highWaterMark, this.waitCount, this.hitCount); }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 129 */     lock = this.lock;
/* 130 */     lock.lock();
/*     */     try {
/* 132 */       return createStatus().toString();
/*     */     } finally {
/* 134 */       lock.unlock();
/*     */     } 
/*     */   }
/*     */   
/*     */   public DataSourcePool.Status getStatus(boolean reset) {
/* 139 */     lock = this.lock;
/* 140 */     lock.lock();
/*     */     try {
/* 142 */       DataSourcePool.Status s = createStatus();
/* 143 */       if (reset) {
/* 144 */         this.highWaterMark = this.busyList.size();
/* 145 */         this.hitCount = 0;
/* 146 */         this.waitCount = 0;
/*     */       } 
/* 148 */       return s;
/*     */     } finally {
/* 150 */       lock.unlock();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setMinSize(int minSize) {
/* 155 */     lock = this.lock;
/* 156 */     lock.lock();
/*     */     try {
/* 158 */       if (minSize > this.maxSize) {
/* 159 */         throw new IllegalArgumentException("minSize " + minSize + " > maxSize " + this.maxSize);
/*     */       }
/* 161 */       this.minSize = minSize;
/*     */     } finally {
/* 163 */       lock.unlock();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setMaxSize(int maxSize) {
/* 168 */     lock = this.lock;
/* 169 */     lock.lock();
/*     */     try {
/* 171 */       if (maxSize < this.minSize) {
/* 172 */         throw new IllegalArgumentException("maxSize " + maxSize + " < minSize " + this.minSize);
/*     */       }
/* 174 */       this.freeList.setCapacity(maxSize);
/* 175 */       this.maxSize = maxSize;
/*     */     } finally {
/* 177 */       lock.unlock();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setWarningSize(int warningSize) {
/* 182 */     lock = this.lock;
/* 183 */     lock.lock();
/*     */     try {
/* 185 */       if (warningSize > this.maxSize) {
/* 186 */         throw new IllegalArgumentException("warningSize " + warningSize + " > maxSize " + this.maxSize);
/*     */       }
/* 188 */       this.warningSize = warningSize;
/*     */     } finally {
/* 190 */       lock.unlock();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/* 195 */   private int totalConnections() { return this.freeList.size() + this.busyList.size(); }
/*     */ 
/*     */   
/*     */   public void ensureMinimumConnections() throws SQLException {
/* 199 */     lock = this.lock;
/* 200 */     lock.lock();
/*     */     try {
/* 202 */       int add = this.minSize - totalConnections();
/* 203 */       if (add > 0) {
/* 204 */         for (int i = 0; i < add; i++) {
/* 205 */           PooledConnection c = this.pool.createConnectionForQueue(this.connectionId++);
/* 206 */           this.freeList.add(c);
/*     */         } 
/* 208 */         this.notEmpty.signal();
/*     */       } 
/*     */     } finally {
/*     */       
/* 212 */       lock.unlock();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void returnPooledConnection(PooledConnection c) {
/* 221 */     lock = this.lock;
/* 222 */     lock.lock();
/*     */     try {
/* 224 */       if (!this.busyList.remove(c)) {
/* 225 */         logger.log(Level.SEVERE, "Connection [" + c + "] not found in BusyList? ");
/*     */       }
/* 227 */       if (c.getCreationTime() <= this.lastResetTime) {
/* 228 */         c.closeConnectionFully(false);
/*     */       } else {
/* 230 */         this.freeList.add(c);
/* 231 */         this.notEmpty.signal();
/*     */       } 
/*     */     } finally {
/* 234 */       lock.unlock();
/*     */     } 
/*     */   }
/*     */   
/*     */   private PooledConnection extractFromFreeList() {
/* 239 */     PooledConnection c = this.freeList.remove();
/* 240 */     registerBusyConnection(c);
/* 241 */     return c;
/*     */   }
/*     */ 
/*     */   
/*     */   public PooledConnection getPooledConnection() {
/*     */     try {
/* 247 */       PooledConnection pc = _getPooledConnection();
/* 248 */       pc.resetForUse();
/* 249 */       return pc;
/*     */     }
/* 251 */     catch (InterruptedException e) {
/* 252 */       String msg = "Interrupted getting connection from pool " + e;
/* 253 */       throw new SQLException(msg);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int registerBusyConnection(PooledConnection c) {
/* 261 */     int busySize = this.busyList.add(c);
/* 262 */     if (busySize > this.highWaterMark) {
/* 263 */       this.highWaterMark = busySize;
/*     */     }
/* 265 */     return busySize;
/*     */   }
/*     */   
/*     */   private PooledConnection _getPooledConnection() {
/* 269 */     lock = this.lock;
/* 270 */     lock.lockInterruptibly();
/*     */     try {
/* 272 */       if (this.doingShutdown) {
/* 273 */         throw new SQLException("Trying to access the Connection Pool when it is shutting down");
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 278 */       this.hitCount++;
/*     */ 
/*     */       
/* 281 */       if (this.waitingThreads == 0)
/*     */       {
/* 283 */         int freeSize = this.freeList.size();
/* 284 */         if (freeSize > 0)
/*     */         {
/* 286 */           return extractFromFreeList();
/*     */         }
/*     */         
/* 289 */         if (this.busyList.size() < this.maxSize)
/*     */         {
/* 291 */           PooledConnection c = this.pool.createConnectionForQueue(this.connectionId++);
/* 292 */           int busySize = registerBusyConnection(c);
/*     */           
/* 294 */           String msg = "DataSourcePool [" + this.name + "] grow; id[" + c.getName() + "] busy[" + busySize + "] max[" + this.maxSize + "]";
/* 295 */           logger.info(msg);
/*     */           
/* 297 */           checkForWarningSize();
/* 298 */           return c;
/*     */ 
/*     */         
/*     */         }
/*     */ 
/*     */ 
/*     */       
/*     */       }
/*     */ 
/*     */ 
/*     */     
/*     */     }
/*     */     finally {
/*     */ 
/*     */       
/* 313 */       lock.unlock();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private PooledConnection _getPooledConnectionWaitLoop() {
/* 322 */     long nanos = MILLIS_TIME_UNIT.toNanos(this.waitTimeoutMillis);
/*     */     
/*     */     while (true) {
/* 325 */       if (nanos <= 0L) {
/* 326 */         String msg = "Unsuccessfully waited [" + this.waitTimeoutMillis + "] millis for a connection to be returned." + " No connections are free. You need to Increase the max connections of [" + this.maxSize + "]" + " or look for a connection pool leak using datasource.xxx.capturestacktrace=true";
/*     */ 
/*     */         
/* 329 */         throw new SQLException(msg);
/*     */       } 
/*     */       
/*     */       try {
/* 333 */         nanos = this.notEmpty.awaitNanos(nanos);
/* 334 */         if (!this.freeList.isEmpty())
/*     */         {
/* 336 */           return extractFromFreeList();
/*     */         }
/* 338 */       } catch (InterruptedException ie) {
/* 339 */         this.notEmpty.signal();
/* 340 */         throw ie;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void shutdown() throws SQLException {
/* 346 */     lock = this.lock;
/* 347 */     lock.lock();
/*     */     try {
/* 349 */       this.doingShutdown = true;
/* 350 */       DataSourcePool.Status status = createStatus();
/* 351 */       logger.info("DataSourcePool [" + this.name + "] shutdown: " + status);
/*     */       
/* 353 */       closeFreeConnections(true);
/*     */       
/* 355 */       if (!this.busyList.isEmpty()) {
/* 356 */         String msg = "A potential connection leak was detected.  Busy connections: " + this.busyList.size();
/* 357 */         logger.warning(msg);
/*     */         
/* 359 */         dumpBusyConnectionInformation();
/* 360 */         closeBusyConnections(0L);
/*     */       } 
/*     */     } finally {
/* 363 */       lock.unlock();
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
/*     */   public void reset(long leakTimeMinutes) {
/* 375 */     lock = this.lock;
/* 376 */     lock.lock();
/*     */     try {
/* 378 */       DataSourcePool.Status status = createStatus();
/* 379 */       logger.info("Reseting DataSourcePool [" + this.name + "] " + status);
/* 380 */       this.lastResetTime = System.currentTimeMillis();
/*     */       
/* 382 */       closeFreeConnections(false);
/* 383 */       closeBusyConnections(leakTimeMinutes);
/*     */       
/* 385 */       String busyMsg = "Busy Connections:\r\n" + getBusyConnectionInformation();
/* 386 */       logger.info(busyMsg);
/*     */     } finally {
/*     */       
/* 389 */       lock.unlock();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void trim(int maxInactiveTimeSecs) {
/* 394 */     lock = this.lock;
/* 395 */     lock.lock();
/*     */     try {
/* 397 */       trimInactiveConnections(maxInactiveTimeSecs);
/* 398 */       ensureMinimumConnections();
/*     */     } finally {
/*     */       
/* 401 */       lock.unlock();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int trimInactiveConnections(int maxInactiveTimeSecs) {
/* 410 */     int maxTrim = this.freeList.size() - this.minSize;
/* 411 */     if (maxTrim <= 0) {
/* 412 */       return 0;
/*     */     }
/*     */     
/* 415 */     int trimedCount = 0;
/* 416 */     long usedSince = System.currentTimeMillis() - (maxInactiveTimeSecs * 1000);
/*     */ 
/*     */     
/* 419 */     List<PooledConnection> freeListCopy = this.freeList.getShallowCopy();
/*     */     
/* 421 */     Iterator<PooledConnection> it = freeListCopy.iterator();
/* 422 */     while (it.hasNext()) {
/* 423 */       PooledConnection pc = (PooledConnection)it.next();
/* 424 */       if (pc.getLastUsedTime() < usedSince) {
/*     */         
/* 426 */         trimedCount++;
/* 427 */         it.remove();
/* 428 */         pc.closeConnectionFully(true);
/* 429 */         if (trimedCount >= maxTrim) {
/*     */           break;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 435 */     if (trimedCount > 0) {
/*     */ 
/*     */       
/* 438 */       this.freeList.setShallowCopy(freeListCopy);
/*     */       
/* 440 */       String msg = "DataSourcePool [" + this.name + "] trimmed [" + trimedCount + "] inactive connections. New size[" + totalConnections() + "]";
/* 441 */       logger.info(msg);
/*     */     } 
/* 443 */     return trimedCount;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void closeFreeConnections(boolean logErrors) {
/* 450 */     lock = this.lock;
/* 451 */     lock.lock();
/*     */     try {
/* 453 */       while (!this.freeList.isEmpty()) {
/* 454 */         PooledConnection c = this.freeList.remove();
/* 455 */         logger.info("PSTMT Statistics: " + c.getStatistics());
/* 456 */         c.closeConnectionFully(logErrors);
/*     */       } 
/*     */     } finally {
/* 459 */       lock.unlock();
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void closeBusyConnections(long leakTimeMinutes) {
/* 477 */     lock = this.lock;
/* 478 */     lock.lock();
/*     */     
/*     */     try {
/* 481 */       long olderThanTime = System.currentTimeMillis() - leakTimeMinutes * 60000L;
/*     */       
/* 483 */       List<PooledConnection> copy = this.busyList.getShallowCopy();
/* 484 */       for (int i = 0; i < copy.size(); i++) {
/* 485 */         PooledConnection pc = (PooledConnection)copy.get(i);
/* 486 */         if (!pc.isLongRunning() && pc.getLastUsedTime() <= olderThanTime) {
/*     */ 
/*     */ 
/*     */           
/* 490 */           this.busyList.remove(pc);
/* 491 */           closeBusyConnection(pc);
/*     */         } 
/*     */       } 
/*     */     } finally {
/*     */       
/* 496 */       lock.unlock();
/*     */     } 
/*     */   }
/*     */   
/*     */   private void closeBusyConnection(PooledConnection pc) {
/*     */     try {
/* 502 */       String methodLine = pc.getCreatedByMethod();
/*     */       
/* 504 */       Date luDate = new Date();
/* 505 */       luDate.setTime(pc.getLastUsedTime());
/*     */       
/* 507 */       String msg = "DataSourcePool closing leaked connection?  name[" + pc.getName() + "] lastUsed[" + luDate + "] createdBy[" + methodLine + "] lastStmt[" + pc.getLastStatement() + "]";
/*     */ 
/*     */ 
/*     */       
/* 511 */       logger.warning(msg);
/* 512 */       logStackElement(pc, "Possible Leaked Connection: ");
/*     */       
/* 514 */       System.out.println("CLOSING BUSY CONNECTION ??? " + pc);
/* 515 */       pc.close();
/*     */     }
/* 517 */     catch (SQLException ex) {
/*     */       
/* 519 */       logger.log(Level.SEVERE, null, ex);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void logStackElement(PooledConnection pc, String prefix) {
/* 524 */     StackTraceElement[] stackTrace = pc.getStackTrace();
/* 525 */     if (stackTrace != null) {
/* 526 */       String s = Arrays.toString(stackTrace);
/* 527 */       String msg = prefix + " name[" + pc.getName() + "] stackTrace: " + s;
/* 528 */       logger.warning(msg);
/*     */ 
/*     */       
/* 531 */       System.err.println(msg);
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
/*     */ 
/*     */ 
/*     */   
/*     */   private void checkForWarningSize() throws SQLException {
/* 548 */     int availableGrowth = this.maxSize - totalConnections();
/*     */     
/* 550 */     if (availableGrowth < this.warningSize) {
/*     */       
/* 552 */       closeBusyConnections(this.leakTimeMinutes);
/*     */       
/* 554 */       String msg = "DataSourcePool [" + this.name + "] is [" + availableGrowth + "] connections from its maximum size.";
/* 555 */       this.pool.notifyWarning(msg);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/* 560 */   public String getBusyConnectionInformation() { return getBusyConnectionInformation(false); }
/*     */ 
/*     */ 
/*     */   
/* 564 */   public void dumpBusyConnectionInformation() throws SQLException { getBusyConnectionInformation(true); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String getBusyConnectionInformation(boolean toLogger) {
/* 572 */     lock = this.lock;
/* 573 */     lock.lock();
/*     */     
/*     */     try {
/* 576 */       if (toLogger) {
/* 577 */         logger.info("Dumping busy connections: (Use datasource.xxx.capturestacktrace=true  ... to get stackTraces)");
/*     */       }
/*     */       
/* 580 */       StringBuilder sb = new StringBuilder();
/*     */       
/* 582 */       List<PooledConnection> copy = this.busyList.getShallowCopy();
/* 583 */       for (int i = 0; i < copy.size(); i++) {
/* 584 */         PooledConnection pc = (PooledConnection)copy.get(i);
/* 585 */         if (toLogger) {
/* 586 */           logger.info(pc.getDescription());
/* 587 */           logStackElement(pc, "Busy Connection: ");
/*     */         } else {
/*     */           
/* 590 */           sb.append(pc.getDescription()).append("\r\n");
/*     */         } 
/*     */       } 
/*     */       
/* 594 */       return sb.toString();
/*     */     } finally {
/*     */       
/* 597 */       lock.unlock();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\lib\sql\PooledConnectionQueue.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */