/*    */ package com.avaje.ebeaninternal.server.text.json;
/*    */ 
/*    */ import com.avaje.ebean.text.TextException;
/*    */ import java.io.IOException;
/*    */ import java.io.Writer;
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
/*    */ public class WriteJsonBufferWriter
/*    */   implements WriteJsonBuffer
/*    */ {
/*    */   private final Writer buffer;
/*    */   
/* 32 */   public WriteJsonBufferWriter(Writer buffer) { this.buffer = buffer; }
/*    */ 
/*    */   
/*    */   public WriteJsonBufferWriter append(String content) {
/*    */     try {
/* 37 */       this.buffer.write(content);
/* 38 */       return this;
/* 39 */     } catch (IOException e) {
/* 40 */       throw new TextException(e);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\text\json\WriteJsonBufferWriter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */