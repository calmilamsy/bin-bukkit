/*    */ package com.avaje.ebeaninternal.server.cluster;
/*    */ 
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
/*    */ public interface LuceneClusterIndexSync
/*    */ {
/*    */   boolean sync(LIndex paramLIndex, String paramString) throws IOException;
/*    */   
/*    */   boolean isMaster();
/*    */   
/*    */   Mode getMode();
/*    */   
/*    */   void setMode(Mode paramMode);
/*    */   
/*    */   String getMasterHost();
/*    */   
/*    */   void setMasterHost(String paramString);
/*    */   
/*    */   public enum Mode
/*    */   {
/* 32 */     MASTER_MODE,
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 37 */     SLAVE_MODE;
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\cluster\LuceneClusterIndexSync.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */