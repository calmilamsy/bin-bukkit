/*     */ package com.avaje.ebeaninternal.server.query;
/*     */ 
/*     */ import com.avaje.ebean.EbeanServer;
/*     */ import com.avaje.ebean.Page;
/*     */ import com.avaje.ebean.PagingList;
/*     */ import com.avaje.ebeaninternal.api.Monitor;
/*     */ import com.avaje.ebeaninternal.api.SpiQuery;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.concurrent.Future;
/*     */ import javax.persistence.PersistenceException;
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
/*     */ public class LimitOffsetPagingQuery<T>
/*     */   extends Object
/*     */   implements PagingList<T>
/*     */ {
/*     */   private EbeanServer server;
/*     */   private final SpiQuery<T> query;
/*     */   private final List<LimitOffsetPage<T>> pages;
/*     */   private final Monitor monitor;
/*     */   private final int pageSize;
/*     */   private boolean fetchAhead;
/*     */   private Future<Integer> futureRowCount;
/*     */   
/*     */   public LimitOffsetPagingQuery(EbeanServer server, SpiQuery<T> query, int pageSize) {
/*  40 */     this.pages = new ArrayList();
/*     */     
/*  42 */     this.monitor = new Monitor();
/*     */ 
/*     */ 
/*     */     
/*  46 */     this.fetchAhead = true;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  51 */     this.query = query;
/*  52 */     this.pageSize = pageSize;
/*  53 */     this.server = server;
/*     */   }
/*     */ 
/*     */   
/*  57 */   public EbeanServer getServer() { return this.server; }
/*     */ 
/*     */ 
/*     */   
/*  61 */   public void setServer(EbeanServer server) { this.server = server; }
/*     */ 
/*     */ 
/*     */   
/*  65 */   public SpiQuery<T> getSpiQuery() { return this.query; }
/*     */ 
/*     */   
/*     */   public PagingList<T> setFetchAhead(boolean fetchAhead) {
/*  69 */     this.fetchAhead = fetchAhead;
/*  70 */     return this;
/*     */   }
/*     */ 
/*     */   
/*  74 */   public List<T> getAsList() { return new LimitOffsetList(this); }
/*     */ 
/*     */   
/*     */   public Future<Integer> getFutureRowCount() {
/*  78 */     synchronized (this.monitor) {
/*  79 */       if (this.futureRowCount == null) {
/*  80 */         this.futureRowCount = this.server.findFutureRowCount(this.query, null);
/*     */       }
/*  82 */       return this.futureRowCount;
/*     */     } 
/*     */   }
/*     */   
/*     */   private LimitOffsetPage<T> internalGetPage(int i) {
/*  87 */     synchronized (this.monitor) {
/*  88 */       int ps = this.pages.size();
/*  89 */       if (ps <= i) {
/*  90 */         for (int j = ps; j <= i; j++) {
/*  91 */           LimitOffsetPage<T> p = new LimitOffsetPage<T>(j, this);
/*  92 */           this.pages.add(p);
/*     */         } 
/*     */       }
/*  95 */       return (LimitOffsetPage)this.pages.get(i);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void fetchAheadIfRequired(int pageIndex) {
/* 100 */     synchronized (this.monitor) {
/*     */       
/* 102 */       if (this.fetchAhead) {
/*     */         
/* 104 */         LimitOffsetPage<T> nextPage = internalGetPage(pageIndex + 1);
/* 105 */         nextPage.getFutureList();
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void refresh() {
/* 111 */     synchronized (this.monitor) {
/* 112 */       this.futureRowCount = null;
/* 113 */       this.pages.clear();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/* 118 */   public Page<T> getPage(int i) { return internalGetPage(i); }
/*     */ 
/*     */ 
/*     */   
/* 122 */   protected boolean hasNext(int position) { return (position < getTotalRowCount()); }
/*     */ 
/*     */   
/*     */   protected T get(int rowIndex) {
/* 126 */     int pg = rowIndex / this.pageSize;
/* 127 */     int offset = rowIndex % this.pageSize;
/*     */     
/* 129 */     Page<T> page = getPage(pg);
/* 130 */     return (T)page.getList().get(offset);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getTotalPageCount() {
/* 135 */     int rowCount = getTotalRowCount();
/* 136 */     if (rowCount == 0) {
/* 137 */       return 0;
/*     */     }
/* 139 */     return (rowCount - 1) / this.pageSize + 1;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 144 */   public int getPageSize() { return this.pageSize; }
/*     */ 
/*     */   
/*     */   public int getTotalRowCount() {
/*     */     try {
/* 149 */       return ((Integer)getFutureRowCount().get()).intValue();
/* 150 */     } catch (Exception e) {
/* 151 */       throw new PersistenceException(e);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\query\LimitOffsetPagingQuery.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */