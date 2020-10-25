/*     */ package com.avaje.ebeaninternal.server.autofetch;
/*     */ 
/*     */ import com.avaje.ebean.bean.NodeUsageCollector;
/*     */ import com.avaje.ebean.bean.ObjectGraphNode;
/*     */ import com.avaje.ebean.bean.ObjectGraphOrigin;
/*     */ import com.avaje.ebean.meta.MetaAutoFetchStatistic;
/*     */ import com.avaje.ebean.text.PathProperties;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
/*     */ import com.avaje.ebeaninternal.server.querydefn.OrmQueryDetail;
/*     */ import java.io.Serializable;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Statistics
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -5586783791097230766L;
/*     */   private final ObjectGraphOrigin origin;
/*     */   private final boolean queryTuningAddVersion;
/*     */   private int counter;
/*     */   private Map<String, StatisticsQuery> queryStatsMap;
/*     */   private Map<String, StatisticsNodeUsage> nodeUsageMap;
/*     */   private final String monitor;
/*     */   
/*     */   public Statistics(ObjectGraphOrigin origin, boolean queryTuningAddVersion) {
/*  33 */     this.queryStatsMap = new LinkedHashMap();
/*     */     
/*  35 */     this.nodeUsageMap = new LinkedHashMap();
/*     */     
/*  37 */     this.monitor = new String();
/*     */ 
/*     */     
/*  40 */     this.origin = origin;
/*  41 */     this.queryTuningAddVersion = queryTuningAddVersion;
/*     */   }
/*     */ 
/*     */   
/*  45 */   public ObjectGraphOrigin getOrigin() { return this.origin; }
/*     */ 
/*     */   
/*     */   public TunedQueryInfo createTunedFetch(OrmQueryDetail newFetchDetail) {
/*  49 */     synchronized (this.monitor) {
/*     */ 
/*     */       
/*  52 */       return new TunedQueryInfo(this.origin, newFetchDetail, this.counter);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public MetaAutoFetchStatistic createPublicMeta() {
/*  58 */     synchronized (this.monitor) {
/*     */       
/*  60 */       StatisticsQuery[] sourceQueryStats = (StatisticsQuery[])this.queryStatsMap.values().toArray(new StatisticsQuery[this.queryStatsMap.size()]);
/*  61 */       List<MetaAutoFetchStatistic.QueryStats> destQueryStats = new ArrayList<MetaAutoFetchStatistic.QueryStats>(sourceQueryStats.length);
/*     */ 
/*     */       
/*  64 */       for (i = 0; i < sourceQueryStats.length; i++) {
/*  65 */         destQueryStats.add(sourceQueryStats[i].createPublicMeta());
/*     */       }
/*     */       
/*  68 */       StatisticsNodeUsage[] sourceNodeUsage = (StatisticsNodeUsage[])this.nodeUsageMap.values().toArray(new StatisticsNodeUsage[this.nodeUsageMap.size()]);
/*  69 */       List<MetaAutoFetchStatistic.NodeUsageStats> destNodeUsage = new ArrayList<MetaAutoFetchStatistic.NodeUsageStats>(sourceNodeUsage.length);
/*     */ 
/*     */       
/*  72 */       for (int i = 0; i < sourceNodeUsage.length; i++) {
/*  73 */         destNodeUsage.add(sourceNodeUsage[i].createPublicMeta());
/*     */       }
/*     */       
/*  76 */       return new MetaAutoFetchStatistic(this.origin, this.counter, destQueryStats, destNodeUsage);
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
/*  88 */   public int getCounter() { return this.counter; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasUsage() {
/*  95 */     synchronized (this.monitor) {
/*  96 */       return !this.nodeUsageMap.isEmpty();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public OrmQueryDetail buildTunedFetch(BeanDescriptor<?> rootDesc) {
/* 102 */     synchronized (this.monitor) {
/* 103 */       if (this.nodeUsageMap.isEmpty()) {
/* 104 */         return null;
/*     */       }
/*     */       
/* 107 */       PathProperties pathProps = new PathProperties();
/*     */       
/* 109 */       Iterator<StatisticsNodeUsage> it = this.nodeUsageMap.values().iterator();
/* 110 */       while (it.hasNext()) {
/* 111 */         StatisticsNodeUsage statsNode = (StatisticsNodeUsage)it.next();
/* 112 */         statsNode.buildTunedFetch(pathProps, rootDesc);
/*     */       } 
/*     */       
/* 115 */       OrmQueryDetail detail = new OrmQueryDetail();
/*     */       
/* 117 */       Collection<PathProperties.Props> pathProperties = pathProps.getPathProps();
/* 118 */       for (PathProperties.Props props : pathProperties) {
/* 119 */         if (!props.isEmpty()) {
/* 120 */           detail.addFetch(props.getPath(), props.getPropertiesAsString(), null);
/*     */         }
/*     */       } 
/*     */       
/* 124 */       detail.sortFetchPaths(rootDesc);
/* 125 */       return detail;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void collectQueryInfo(ObjectGraphNode node, int beansLoaded, int micros) {
/* 132 */     synchronized (this.monitor) {
/* 133 */       String key = node.getPath();
/* 134 */       if (key == null) {
/* 135 */         key = "";
/*     */ 
/*     */ 
/*     */         
/* 139 */         this.counter++;
/*     */       } 
/*     */       
/* 142 */       StatisticsQuery stats = (StatisticsQuery)this.queryStatsMap.get(key);
/* 143 */       if (stats == null) {
/* 144 */         stats = new StatisticsQuery(key);
/* 145 */         this.queryStatsMap.put(key, stats);
/*     */       } 
/* 147 */       stats.add(beansLoaded, micros);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void collectUsageInfo(NodeUsageCollector profile) {
/* 157 */     if (!profile.isEmpty()) {
/*     */ 
/*     */       
/* 160 */       ObjectGraphNode node = profile.getNode();
/*     */       
/* 162 */       StatisticsNodeUsage nodeStats = getNodeStats(node.getPath());
/* 163 */       nodeStats.publish(profile);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private StatisticsNodeUsage getNodeStats(String path) {
/* 169 */     synchronized (this.monitor) {
/* 170 */       StatisticsNodeUsage nodeStats = (StatisticsNodeUsage)this.nodeUsageMap.get(path);
/* 171 */       if (nodeStats == null) {
/* 172 */         nodeStats = new StatisticsNodeUsage(path, this.queryTuningAddVersion);
/* 173 */         this.nodeUsageMap.put(path, nodeStats);
/*     */       } 
/* 175 */       return nodeStats;
/*     */     } 
/*     */   }
/*     */   
/*     */   public String getUsageDebug() {
/* 180 */     synchronized (this.monitor) {
/* 181 */       StringBuilder sb = new StringBuilder();
/* 182 */       sb.append("root[").append(this.origin.getBeanType()).append("] ");
/* 183 */       for (StatisticsNodeUsage node : this.nodeUsageMap.values()) {
/* 184 */         sb.append(node.toString()).append("\n");
/*     */       }
/* 186 */       return sb.toString();
/*     */     } 
/*     */   }
/*     */   
/*     */   public String getQueryStatDebug() {
/* 191 */     synchronized (this.monitor) {
/* 192 */       StringBuilder sb = new StringBuilder();
/* 193 */       for (StatisticsQuery queryStat : this.queryStatsMap.values()) {
/* 194 */         sb.append(queryStat.toString()).append("\n");
/*     */       }
/* 196 */       return sb.toString();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 202 */     synchronized (this.monitor) {
/* 203 */       return getUsageDebug();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\autofetch\Statistics.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */