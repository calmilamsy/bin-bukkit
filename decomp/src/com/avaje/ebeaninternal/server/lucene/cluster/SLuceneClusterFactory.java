/*    */ package com.avaje.ebeaninternal.server.lucene.cluster;
/*    */ 
/*    */ import com.avaje.ebeaninternal.server.cluster.ClusterManager;
/*    */ import com.avaje.ebeaninternal.server.cluster.LuceneClusterFactory;
/*    */ import com.avaje.ebeaninternal.server.cluster.LuceneClusterIndexSync;
/*    */ import com.avaje.ebeaninternal.server.cluster.LuceneClusterListener;
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
/*    */ public class SLuceneClusterFactory
/*    */   implements LuceneClusterFactory
/*    */ {
/* 31 */   public LuceneClusterListener createListener(ClusterManager m, int port) { return new SLuceneClusterSocketListener(m, port); }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 36 */   public LuceneClusterIndexSync createIndexSync() { return new SLuceneIndexSync(); }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\lucene\cluster\SLuceneClusterFactory.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */