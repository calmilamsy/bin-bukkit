/*    */ package com.avaje.ebeaninternal.server.text.json;
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
/*    */ public class WriteJsonBufferString
/*    */   implements WriteJsonBuffer
/*    */ {
/* 27 */   private final StringBuilder buffer = new StringBuilder('Ā');
/*    */ 
/*    */   
/*    */   public WriteJsonBufferString append(String content) {
/* 31 */     this.buffer.append(content);
/* 32 */     return this;
/*    */   }
/*    */ 
/*    */   
/* 36 */   public String getBufferOutput() { return this.buffer.toString(); }
/*    */ 
/*    */ 
/*    */   
/* 40 */   public String toString() { return this.buffer.toString(); }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\text\json\WriteJsonBufferString.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */