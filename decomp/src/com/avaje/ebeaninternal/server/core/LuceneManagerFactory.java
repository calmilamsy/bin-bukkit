/*    */ package com.avaje.ebeaninternal.server.core;
/*    */ 
/*    */ import com.avaje.ebean.BackgroundExecutor;
/*    */ import com.avaje.ebean.Query;
/*    */ import com.avaje.ebean.config.GlobalProperties;
/*    */ import com.avaje.ebean.config.ServerConfig;
/*    */ import com.avaje.ebean.config.lucene.LuceneConfig;
/*    */ import com.avaje.ebeaninternal.server.cluster.ClusterManager;
/*    */ import com.avaje.ebeaninternal.server.lucene.DefaultLuceneIndexManager;
/*    */ import com.avaje.ebeaninternal.server.lucene.LuceneIndexManager;
/*    */ import org.apache.lucene.analysis.standard.StandardAnalyzer;
/*    */ import org.apache.lucene.util.Version;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class LuceneManagerFactory
/*    */ {
/*    */   public static LuceneIndexManager createLuceneManager(ClusterManager clusterManager, BackgroundExecutor executor, ServerConfig serverConfig) {
/* 39 */     StandardAnalyzer standardAnalyzer = null;
/* 40 */     String baseDir = null;
/* 41 */     Query.UseIndex defaultUseIndex = null;
/*    */     
/* 43 */     LuceneConfig luceneConfig = serverConfig.getLuceneConfig();
/* 44 */     if (luceneConfig != null) {
/* 45 */       standardAnalyzer = null;
/* 46 */       baseDir = luceneConfig.getBaseDirectory();
/* 47 */       defaultUseIndex = luceneConfig.getDefaultUseIndex();
/*    */     } 
/* 49 */     if (standardAnalyzer == null) {
/* 50 */       standardAnalyzer = new StandardAnalyzer(Version.LUCENE_30);
/*    */     }
/* 52 */     if (defaultUseIndex == null) {
/* 53 */       defaultUseIndex = Query.UseIndex.NO;
/*    */     }
/* 55 */     if (baseDir == null) {
/* 56 */       baseDir = serverConfig.getPropertySource().get("lucene.baseDirectory", "lucene");
/*    */     }
/* 58 */     baseDir = GlobalProperties.evaluateExpressions(baseDir);
/*    */     
/* 60 */     return new DefaultLuceneIndexManager(clusterManager, executor, standardAnalyzer, baseDir, serverConfig.getName(), defaultUseIndex);
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\core\LuceneManagerFactory.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */