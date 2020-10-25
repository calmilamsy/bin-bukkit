/*     */ package com.avaje.ebeaninternal.server.lucene;
/*     */ 
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import org.apache.lucene.index.IndexCommit;
/*     */ import org.apache.lucene.index.IndexDeletionPolicy;
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
/*     */ public class HoldAwareIndexDeletionPolicy
/*     */   implements IndexDeletionPolicy
/*     */ {
/*  33 */   private static final Logger logger = Logger.getLogger(HoldAwareIndexDeletionPolicy.class.getName());
/*     */   public HoldAwareIndexDeletionPolicy(String indexDir) {
/*  35 */     this.commitRefCounts = new HashMap();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  42 */     this.indexDir = indexDir;
/*     */   }
/*     */   
/*     */   private final Map<Long, CommitRefCount> commitRefCounts;
/*     */   private IndexCommit lastCommit;
/*     */   private final String indexDir;
/*     */   
/*  49 */   public void onInit(List<? extends IndexCommit> commits) { onCommit(commits); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onCommit(List<? extends IndexCommit> commits) {
/*  56 */     synchronized (this.commitRefCounts) {
/*  57 */       int size = commits.size();
/*     */       
/*  59 */       this.lastCommit = (IndexCommit)commits.get(size - 1);
/*     */ 
/*     */       
/*  62 */       for (int i = 0; i < size - 1; i++) {
/*  63 */         IndexCommit indexCommit = (IndexCommit)commits.get(i);
/*  64 */         if (!this.commitRefCounts.containsKey(Long.valueOf(indexCommit.getVersion())))
/*     */         {
/*     */ 
/*     */           
/*  68 */           potentialIndexCommitDelete(indexCommit);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*  76 */   private void potentialIndexCommitDelete(IndexCommit indexCommit) { indexCommit.delete(); }
/*     */ 
/*     */   
/*     */   public long getLastVersion() {
/*  80 */     synchronized (this.commitRefCounts) {
/*  81 */       if (this.lastCommit == null) {
/*  82 */         return 0L;
/*     */       }
/*  84 */       return this.lastCommit.getVersion();
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
/*     */   public LIndexCommitInfo obtainLastIndexCommitIfNewer(long remoteIndexVersion) {
/*  99 */     synchronized (this.commitRefCounts) {
/*     */       
/* 101 */       if (remoteIndexVersion != 0L && remoteIndexVersion == this.lastCommit.getVersion())
/*     */       {
/* 103 */         return null;
/*     */       }
/*     */       
/* 106 */       incRefIndexCommit(this.lastCommit);
/* 107 */       return new LIndexCommitInfo(this.indexDir, this.lastCommit);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void incRefIndexCommit(IndexCommit indexCommit) {
/* 115 */     Long commitVersion = Long.valueOf(indexCommit.getVersion());
/* 116 */     CommitRefCount refCount = (CommitRefCount)this.commitRefCounts.get(commitVersion);
/* 117 */     if (refCount == null) {
/* 118 */       refCount = new CommitRefCount(null);
/* 119 */       this.commitRefCounts.put(commitVersion, refCount);
/*     */     } 
/* 121 */     refCount.inc();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void releaseIndexCommit(long indexCommitVersion) {
/* 129 */     synchronized (this.commitRefCounts) {
/* 130 */       Long commitVersion = Long.valueOf(indexCommitVersion);
/* 131 */       CommitRefCount refCount = (CommitRefCount)this.commitRefCounts.get(commitVersion);
/* 132 */       if (refCount == null) {
/* 133 */         logger.log(Level.WARNING, "No Reference counter for indexCommitVersion: " + commitVersion);
/*     */       }
/* 135 */       else if (refCount.dec() <= 0) {
/*     */ 
/*     */         
/* 138 */         this.commitRefCounts.remove(commitVersion);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void touch(long indexCommitVersion) {
/* 145 */     synchronized (this.commitRefCounts) {
/* 146 */       Long commitVersion = Long.valueOf(indexCommitVersion);
/* 147 */       CommitRefCount refCount = (CommitRefCount)this.commitRefCounts.get(commitVersion);
/* 148 */       if (refCount == null) {
/* 149 */         logger.warning("No Reference counter for indexCommitVersion: " + commitVersion);
/*     */       } else {
/* 151 */         refCount.touch();
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public long getLastTouched(long indexCommitVersion) {
/* 157 */     synchronized (this.commitRefCounts) {
/* 158 */       Long commitVersion = Long.valueOf(indexCommitVersion);
/* 159 */       CommitRefCount refCount = (CommitRefCount)this.commitRefCounts.get(commitVersion);
/* 160 */       if (refCount == null) {
/* 161 */         return 0L;
/*     */       }
/* 163 */       return refCount.getLastTouched();
/*     */     } 
/*     */   }
/*     */   
/*     */   private static class CommitRefCount
/*     */   {
/*     */     private int refCount;
/* 170 */     private long lastTouched = System.currentTimeMillis();
/*     */ 
/*     */     
/* 173 */     public void inc() { this.refCount++; }
/*     */ 
/*     */ 
/*     */     
/* 177 */     public int dec() { return --this.refCount; }
/*     */ 
/*     */ 
/*     */     
/* 181 */     public void touch() { this.lastTouched = System.currentTimeMillis(); }
/*     */ 
/*     */ 
/*     */     
/* 185 */     public long getLastTouched() { return this.lastTouched; }
/*     */     
/*     */     private CommitRefCount() {}
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\lucene\HoldAwareIndexDeletionPolicy.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */