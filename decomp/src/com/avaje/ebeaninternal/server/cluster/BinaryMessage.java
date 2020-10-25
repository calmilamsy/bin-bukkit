/*    */ package com.avaje.ebeaninternal.server.cluster;
/*    */ 
/*    */ import java.io.ByteArrayOutputStream;
/*    */ import java.io.DataOutputStream;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BinaryMessage
/*    */ {
/*    */   public static final int TYPE_MSGCONTROL = 0;
/*    */   public static final int TYPE_BEANIUD = 1;
/*    */   public static final int TYPE_TABLEIUD = 2;
/*    */   public static final int TYPE_BEANDELTA = 3;
/*    */   public static final int TYPE_BEANPATHUPDATE = 4;
/*    */   public static final int TYPE_INDEX_INVALIDATE = 6;
/*    */   public static final int TYPE_INDEX = 7;
/*    */   public static final int TYPE_MSGACK = 8;
/*    */   public static final int TYPE_MSGRESEND = 9;
/*    */   private final ByteArrayOutputStream buffer;
/*    */   private final DataOutputStream os;
/*    */   private byte[] bytes;
/*    */   
/*    */   public BinaryMessage(int bufSize) {
/* 63 */     this.buffer = new ByteArrayOutputStream(bufSize);
/* 64 */     this.os = new DataOutputStream(this.buffer);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 71 */   public DataOutputStream getOs() { return this.os; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public byte[] getByteArray() {
/* 78 */     if (this.bytes == null) {
/* 79 */       this.bytes = this.buffer.toByteArray();
/*    */     }
/* 81 */     return this.bytes;
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\cluster\BinaryMessage.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */