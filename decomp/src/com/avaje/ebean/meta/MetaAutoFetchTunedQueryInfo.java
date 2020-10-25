/*     */ package com.avaje.ebean.meta;
/*     */ 
/*     */ import com.avaje.ebean.bean.ObjectGraphOrigin;
/*     */ import java.io.Serializable;
/*     */ import javax.persistence.Entity;
/*     */ import javax.persistence.Id;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Entity
/*     */ public class MetaAutoFetchTunedQueryInfo
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 3119991928889170215L;
/*     */   @Id
/*     */   private String id;
/*     */   private String beanType;
/*     */   private ObjectGraphOrigin origin;
/*     */   private String tunedDetail;
/*     */   private int profileCount;
/*     */   private int tunedCount;
/*     */   private long lastTuneTime;
/*     */   
/*     */   public MetaAutoFetchTunedQueryInfo() {}
/*     */   
/*     */   public MetaAutoFetchTunedQueryInfo(ObjectGraphOrigin origin, String tunedDetail, int profileCount, int tunedCount, long lastTuneTime) {
/*  58 */     this.origin = origin;
/*  59 */     this.beanType = (origin == null) ? null : origin.getBeanType();
/*  60 */     this.id = (origin == null) ? null : origin.getKey();
/*  61 */     this.tunedDetail = tunedDetail;
/*  62 */     this.profileCount = profileCount;
/*  63 */     this.tunedCount = tunedCount;
/*  64 */     this.lastTuneTime = lastTuneTime;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  71 */   public String getId() { return this.id; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  78 */   public String getBeanType() { return this.beanType; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  85 */   public ObjectGraphOrigin getOrigin() { return this.origin; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  92 */   public String getTunedDetail() { return this.tunedDetail; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  99 */   public int getProfileCount() { return this.profileCount; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 106 */   public int getTunedCount() { return this.tunedCount; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 113 */   public long getLastTuneTime() { return this.lastTuneTime; }
/*     */ 
/*     */ 
/*     */   
/* 117 */   public String toString() { return "origin[" + this.origin + "] query[" + this.tunedDetail + "] profileCount[" + this.profileCount + "]"; }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebean\meta\MetaAutoFetchTunedQueryInfo.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */