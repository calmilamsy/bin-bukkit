/*    */ package com.avaje.ebeaninternal.server.lucene.cluster;
/*    */ 
/*    */ import com.avaje.ebeaninternal.server.cluster.LuceneClusterIndexSync;
/*    */ import com.avaje.ebeaninternal.server.lucene.LIndex;
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
/*    */ public class SLuceneIndexSync
/*    */   implements LuceneClusterIndexSync
/*    */ {
/*    */   private LuceneClusterIndexSync.Mode mode;
/*    */   private String masterHost;
/*    */   
/*    */   public boolean sync(LIndex index, String masterHost) throws IOException {
/* 35 */     SLuceneClusterSocketClient c = new SLuceneClusterSocketClient(index);
/* 36 */     if (c.isSynchIndex(masterHost)) {
/* 37 */       c.transferFiles();
/*    */       
/* 39 */       index.refresh(true);
/* 40 */       return true;
/*    */     } 
/* 42 */     return false;
/*    */   }
/*    */ 
/*    */   
/* 46 */   public boolean isMaster() { return LuceneClusterIndexSync.Mode.MASTER_MODE.equals(this.mode); }
/*    */ 
/*    */ 
/*    */   
/* 50 */   public String getMasterHost() { return this.masterHost; }
/*    */ 
/*    */ 
/*    */   
/* 54 */   public LuceneClusterIndexSync.Mode getMode() { return this.mode; }
/*    */ 
/*    */ 
/*    */   
/* 58 */   public void setMasterHost(String masterHost) { this.masterHost = masterHost; }
/*    */ 
/*    */ 
/*    */   
/* 62 */   public void setMode(LuceneClusterIndexSync.Mode mode) { this.mode = mode; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\lucene\cluster\SLuceneIndexSync.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */