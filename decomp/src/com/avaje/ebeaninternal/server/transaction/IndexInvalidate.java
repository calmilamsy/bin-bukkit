/*    */ package com.avaje.ebeaninternal.server.transaction;
/*    */ 
/*    */ import com.avaje.ebeaninternal.server.cluster.BinaryMessage;
/*    */ import com.avaje.ebeaninternal.server.cluster.BinaryMessageList;
/*    */ import java.io.DataInput;
/*    */ import java.io.DataOutputStream;
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
/*    */ public class IndexInvalidate
/*    */ {
/*    */   private final String indexName;
/*    */   
/* 34 */   public IndexInvalidate(String indexName) { this.indexName = indexName; }
/*    */ 
/*    */ 
/*    */   
/* 38 */   public String getIndexName() { return this.indexName; }
/*    */ 
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 43 */     hc = IndexInvalidate.class.hashCode();
/* 44 */     return hc * 31 + this.indexName.hashCode();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean equals(Object o) {
/* 50 */     if (!(o instanceof IndexInvalidate)) {
/* 51 */       return false;
/*    */     }
/* 53 */     return this.indexName.equals(((IndexInvalidate)o).indexName);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public static IndexInvalidate readBinaryMessage(DataInput dataInput) throws IOException {
/* 59 */     String indexName = dataInput.readUTF();
/* 60 */     return new IndexInvalidate(indexName);
/*    */   }
/*    */ 
/*    */   
/*    */   public void writeBinaryMessage(BinaryMessageList msgList) throws IOException {
/* 65 */     BinaryMessage msg = new BinaryMessage(this.indexName.length() + 10);
/* 66 */     DataOutputStream os = msg.getOs();
/* 67 */     os.writeInt(6);
/* 68 */     os.writeUTF(this.indexName);
/*    */     
/* 70 */     msgList.add(msg);
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\transaction\IndexInvalidate.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */