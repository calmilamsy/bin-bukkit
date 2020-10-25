/*     */ package com.avaje.ebeaninternal.server.autofetch;
/*     */ 
/*     */ import com.avaje.ebean.bean.ObjectGraphOrigin;
/*     */ import com.avaje.ebean.meta.MetaAutoFetchTunedQueryInfo;
/*     */ import com.avaje.ebeaninternal.api.SpiQuery;
/*     */ import com.avaje.ebeaninternal.server.querydefn.OrmQueryDetail;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TunedQueryInfo
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 7381493228797997282L;
/*     */   private final ObjectGraphOrigin origin;
/*     */   private OrmQueryDetail tunedDetail;
/*     */   private int profileCount;
/*     */   private Long lastTuneTime;
/*     */   private final String rateMonitor;
/*     */   private int tunedCount;
/*     */   private int rateTotal;
/*     */   private int rateHits;
/*     */   private double lastRate;
/*     */   
/*     */   public TunedQueryInfo(ObjectGraphOrigin queryPoint, OrmQueryDetail tunedDetail, int profileCount) {
/*  30 */     this.lastTuneTime = Long.valueOf(0L);
/*     */     
/*  32 */     this.rateMonitor = new String();
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
/*  47 */     this.origin = queryPoint;
/*  48 */     this.tunedDetail = tunedDetail;
/*  49 */     this.profileCount = profileCount;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isPercentageProfile(double rate) {
/*  57 */     synchronized (this.rateMonitor) {
/*     */       
/*  59 */       if (this.lastRate != rate) {
/*     */         
/*  61 */         this.lastRate = rate;
/*  62 */         this.rateTotal = 0;
/*  63 */         this.rateHits = 0;
/*     */       } 
/*     */       
/*  66 */       this.rateTotal++;
/*  67 */       if (rate > this.rateHits / this.rateTotal) {
/*  68 */         this.rateHits++;
/*  69 */         return true;
/*     */       } 
/*  71 */       return false;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  80 */   public MetaAutoFetchTunedQueryInfo createPublicMeta() { return new MetaAutoFetchTunedQueryInfo(this.origin, this.tunedDetail.toString(), this.profileCount, this.tunedCount, this.lastTuneTime.longValue()); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  89 */   public void setProfileCount(int profileCount) { this.profileCount = profileCount; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTunedDetail(OrmQueryDetail tunedDetail) {
/*  97 */     this.tunedDetail = tunedDetail;
/*  98 */     this.lastTuneTime = Long.valueOf(System.currentTimeMillis());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isSame(OrmQueryDetail newQueryDetail) {
/* 105 */     if (this.tunedDetail == null) {
/* 106 */       return false;
/*     */     }
/* 108 */     return this.tunedDetail.isAutoFetchEqual(newQueryDetail);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean autoFetchTune(SpiQuery<?> query) {
/* 117 */     if (this.tunedDetail == null) {
/* 118 */       return false;
/*     */     }
/*     */     
/* 121 */     boolean tuned = false;
/*     */     
/* 123 */     if (query.isDetailEmpty()) {
/* 124 */       tuned = true;
/*     */       
/* 126 */       query.setDetail(this.tunedDetail.copy());
/*     */     } else {
/*     */       
/* 129 */       tuned = query.tuneFetchProperties(this.tunedDetail);
/*     */     } 
/* 131 */     if (tuned) {
/* 132 */       query.setAutoFetchTuned(true);
/*     */       
/* 134 */       this.tunedCount++;
/*     */     } 
/* 136 */     return tuned;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 143 */   public Long getLastTuneTime() { return this.lastTuneTime; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 150 */   public int getTunedCount() { return this.tunedCount; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 158 */   public int getProfileCount() { return this.profileCount; }
/*     */ 
/*     */ 
/*     */   
/* 162 */   public OrmQueryDetail getTunedDetail() { return this.tunedDetail; }
/*     */ 
/*     */ 
/*     */   
/* 166 */   public ObjectGraphOrigin getOrigin() { return this.origin; }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getLogOutput(OrmQueryDetail newQueryDetail) {
/* 171 */     boolean changed = (newQueryDetail != null);
/*     */     
/* 173 */     StringBuilder sb = new StringBuilder('');
/* 174 */     sb.append(changed ? "\"Changed\"," : "\"New\",");
/* 175 */     sb.append("\"").append(this.origin.getBeanType()).append("\",");
/* 176 */     sb.append("\"").append(this.origin.getKey()).append("\",");
/* 177 */     if (changed) {
/* 178 */       sb.append("\"to: ").append(newQueryDetail.toString()).append("\",");
/* 179 */       sb.append("\"from: ").append(this.tunedDetail.toString()).append("\",");
/*     */     } else {
/* 181 */       sb.append("\"to: ").append(this.tunedDetail.toString()).append("\",");
/* 182 */       sb.append("\"\",");
/*     */     } 
/* 184 */     sb.append("\"").append(this.origin.getFirstStackElement()).append("\"");
/*     */     
/* 186 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   
/* 190 */   public String toString() { return this.origin.getBeanType() + " " + this.origin.getKey() + " " + this.tunedDetail; }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\autofetch\TunedQueryInfo.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */