/*    */ package com.avaje.ebeaninternal.server.lucene;
/*    */ 
/*    */ import com.avaje.ebean.Query;
/*    */ import com.avaje.ebean.config.lucene.IndexDefn;
/*    */ import com.avaje.ebeaninternal.api.SpiEbeanServer;
/*    */ import com.avaje.ebeaninternal.api.SpiTransaction;
/*    */ import com.avaje.ebeaninternal.server.cluster.LuceneClusterIndexSync;
/*    */ import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
/*    */ import com.avaje.ebeaninternal.server.transaction.IndexEvent;
/*    */ import com.avaje.ebeaninternal.server.transaction.RemoteTransactionEvent;
/*    */ import java.io.IOException;
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
/*    */ public class NoLuceneIndexManager
/*    */   implements LuceneIndexManager
/*    */ {
/*    */   public void start() {}
/*    */   
/*    */   public void shutdown() {}
/*    */   
/*    */   public void setServer(SpiEbeanServer server) {}
/*    */   
/* 50 */   public boolean isLuceneAvailable() { return false; }
/*    */ 
/*    */ 
/*    */   
/* 54 */   public void processEvent(RemoteTransactionEvent txnEvent, SpiTransaction t) { throw new RuntimeException("Never Called"); }
/*    */ 
/*    */ 
/*    */   
/* 58 */   public void processEvent(IndexEvent indexEvent) { throw new RuntimeException("Never Called"); }
/*    */ 
/*    */ 
/*    */   
/* 62 */   public LuceneClusterIndexSync getClusterIndexSync() { throw new RuntimeException("Never Called"); }
/*    */ 
/*    */ 
/*    */   
/* 66 */   public void notifyCluster(IndexEvent event) { throw new RuntimeException("Never Called"); }
/*    */ 
/*    */ 
/*    */   
/* 70 */   public void addIndex(LIndex index) throws IOException { throw new RuntimeException("Never Called"); }
/*    */ 
/*    */ 
/*    */   
/* 74 */   public LIndex getIndex(String defnName) { throw new RuntimeException("Never Called"); }
/*    */ 
/*    */ 
/*    */   
/* 78 */   public LIndex create(IndexDefn<?> indexDefn, BeanDescriptor<?> descriptor) throws IOException { throw new RuntimeException("Never Called"); }
/*    */ 
/*    */ 
/*    */   
/* 82 */   public Query.UseIndex getDefaultUseIndex() { return Query.UseIndex.NO; }
/*    */ 
/*    */ 
/*    */   
/* 86 */   public LIndex getIndexByTypeAndName(Class<?> beanType, String name) { throw new RuntimeException("Never Called"); }
/*    */ 
/*    */ 
/*    */   
/* 90 */   public String getIndexDirectory(String indexName) { throw new RuntimeException("Never Called"); }
/*    */ 
/*    */ 
/*    */   
/* 94 */   public SpiEbeanServer getServer() { throw new RuntimeException("Never Called"); }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\lucene\NoLuceneIndexManager.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */