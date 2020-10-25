/*    */ package com.avaje.ebean.config.lucene;
/*    */ 
/*    */ import com.avaje.ebean.Query;
/*    */ import com.avaje.ebean.config.GlobalProperties;
/*    */ import org.apache.lucene.analysis.Analyzer;
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
/*    */ public class LuceneConfig
/*    */ {
/*    */   protected String baseDirectory;
/*    */   protected Analyzer defaultAnalyzer;
/*    */   protected Query.UseIndex defaultUseIndex;
/*    */   
/* 45 */   public Analyzer getDefaultAnalyzer() { return this.defaultAnalyzer; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 52 */   public void setDefaultAnalyzer(Analyzer defaultAnalyzer) { this.defaultAnalyzer = defaultAnalyzer; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 59 */   public String getBaseDirectory() { return this.baseDirectory; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 66 */   public void setBaseDirectory(String baseDirectory) { this.baseDirectory = baseDirectory; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 73 */   public Query.UseIndex getDefaultUseIndex() { return this.defaultUseIndex; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 80 */   public void setDefaultUseIndex(Query.UseIndex defaultUseIndex) { this.defaultUseIndex = defaultUseIndex; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void loadSettings(String serverName) {
/* 88 */     GlobalProperties.PropertySource p = GlobalProperties.getPropertySource(serverName);
/*    */     
/* 90 */     this.baseDirectory = p.get("lucene.baseDirectory", "lucene");
/* 91 */     this.defaultUseIndex = (Query.UseIndex)p.getEnum(Query.UseIndex.class, "lucene.useIndex", Query.UseIndex.NO);
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebean\config\lucene\LuceneConfig.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */