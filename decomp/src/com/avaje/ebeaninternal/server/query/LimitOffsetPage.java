/*     */ package com.avaje.ebeaninternal.server.query;
/*     */ 
/*     */ import com.avaje.ebean.FutureList;
/*     */ import com.avaje.ebean.Page;
/*     */ import com.avaje.ebean.bean.BeanCollection;
/*     */ import com.avaje.ebean.bean.BeanCollectionTouched;
/*     */ import com.avaje.ebeaninternal.api.SpiQuery;
/*     */ import java.util.List;
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
/*     */ public class LimitOffsetPage<T>
/*     */   extends Object
/*     */   implements Page<T>, BeanCollectionTouched
/*     */ {
/*     */   private final int pageIndex;
/*     */   private final LimitOffsetPagingQuery<T> owner;
/*     */   private FutureList<T> futureList;
/*     */   
/*     */   public LimitOffsetPage(int pageIndex, LimitOffsetPagingQuery<T> owner) {
/*  49 */     this.pageIndex = pageIndex;
/*  50 */     this.owner = owner;
/*     */   }
/*     */ 
/*     */   
/*     */   public FutureList<T> getFutureList() {
/*  55 */     if (this.futureList == null) {
/*  56 */       SpiQuery<T> originalQuery = this.owner.getSpiQuery();
/*  57 */       SpiQuery<T> copy = originalQuery.copy();
/*  58 */       copy.setPersistenceContext(originalQuery.getPersistenceContext());
/*     */       
/*  60 */       int pageSize = this.owner.getPageSize();
/*  61 */       copy.setFirstRow(this.pageIndex * pageSize);
/*  62 */       copy.setMaxRows(pageSize);
/*  63 */       copy.setBeanCollectionTouched(this);
/*  64 */       this.futureList = this.owner.getServer().findFutureList(copy, null);
/*     */     } 
/*     */     
/*  67 */     return this.futureList;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void notifyTouched(BeanCollection<?> c) {
/*  74 */     if (c.hasMoreRows()) {
/*  75 */       this.owner.fetchAheadIfRequired(this.pageIndex);
/*     */     }
/*     */   }
/*     */   
/*     */   public List<T> getList() {
/*     */     try {
/*  81 */       return (List)getFutureList().get();
/*  82 */     } catch (Exception e) {
/*  83 */       throw new PersistenceException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*  89 */   public boolean hasNext() { return ((BeanCollection)getList()).hasMoreRows(); }
/*     */ 
/*     */ 
/*     */   
/*  93 */   public boolean hasPrev() { return (this.pageIndex > 0); }
/*     */ 
/*     */ 
/*     */   
/*  97 */   public Page<T> next() { return this.owner.getPage(this.pageIndex + 1); }
/*     */ 
/*     */ 
/*     */   
/* 101 */   public Page<T> prev() { return this.owner.getPage(this.pageIndex - 1); }
/*     */ 
/*     */ 
/*     */   
/* 105 */   public int getPageIndex() { return this.pageIndex; }
/*     */ 
/*     */ 
/*     */   
/* 109 */   public int getTotalPageCount() { return this.owner.getTotalPageCount(); }
/*     */ 
/*     */ 
/*     */   
/* 113 */   public int getTotalRowCount() { return this.owner.getTotalRowCount(); }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDisplayXtoYofZ(String to, String of) {
/* 118 */     int first = this.pageIndex * this.owner.getPageSize() + 1;
/* 119 */     int last = first + getList().size() - 1;
/* 120 */     int total = getTotalRowCount();
/*     */     
/* 122 */     return first + to + last + of + total;
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\query\LimitOffsetPage.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */