/*     */ package com.avaje.ebeaninternal.api;
/*     */ 
/*     */ import java.util.List;
/*     */ import java.util.concurrent.FutureTask;
/*     */ import java.util.concurrent.TimeUnit;
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
/*     */ public class BeanIdList
/*     */ {
/*     */   private final List<Object> idList;
/*     */   private boolean hasMore;
/*     */   private FutureTask<Integer> fetchFuture;
/*     */   
/*     */   public BeanIdList(List<Object> idList) {
/*  38 */     this.hasMore = true;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  43 */     this.idList = idList;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  50 */   public boolean isFetchingInBackground() { return (this.fetchFuture != null); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  57 */   public void setBackgroundFetch(FutureTask<Integer> fetchFuture) { this.fetchFuture = fetchFuture; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void backgroundFetchWait(long wait, TimeUnit timeUnit) {
/*  64 */     if (this.fetchFuture != null) {
/*     */       try {
/*  66 */         this.fetchFuture.get(wait, timeUnit);
/*  67 */       } catch (Exception e) {
/*  68 */         throw new PersistenceException(e);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void backgroundFetchWait() {
/*  77 */     if (this.fetchFuture != null) {
/*     */       try {
/*  79 */         this.fetchFuture.get();
/*  80 */       } catch (Exception e) {
/*  81 */         throw new PersistenceException(e);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  90 */   public void add(Object id) { this.idList.add(id); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  97 */   public List<Object> getIdList() { return this.idList; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 104 */   public boolean isHasMore() { return this.hasMore; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 111 */   public void setHasMore(boolean hasMore) { this.hasMore = hasMore; }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\api\BeanIdList.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */