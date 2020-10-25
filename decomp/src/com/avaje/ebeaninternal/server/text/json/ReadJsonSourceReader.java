/*    */ package com.avaje.ebeaninternal.server.text.json;
/*    */ 
/*    */ import com.avaje.ebean.text.TextException;
/*    */ import java.io.BufferedReader;
/*    */ import java.io.IOException;
/*    */ import java.io.Reader;
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
/*    */ public class ReadJsonSourceReader
/*    */   implements ReadJsonSource
/*    */ {
/*    */   private final Reader reader;
/*    */   private char[] localBuffer;
/*    */   private int totalPos;
/*    */   private int localPos;
/*    */   private int localPosEnd;
/*    */   
/*    */   public ReadJsonSourceReader(Reader reader, int localBufferSize, int bufferSize) {
/* 39 */     this.reader = new BufferedReader(reader, bufferSize);
/* 40 */     this.localBuffer = new char[localBufferSize];
/*    */   }
/*    */ 
/*    */   
/* 44 */   public String toString() { return String.valueOf(this.localBuffer); }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getErrorHelp() {
/* 50 */     int prev = this.localPos - 30;
/* 51 */     if (prev < 0) {
/* 52 */       prev = 0;
/*    */     }
/* 54 */     String c = new String(this.localBuffer, prev, this.localPos - prev);
/* 55 */     return "pos:" + pos() + " preceding:" + c;
/*    */   }
/*    */ 
/*    */   
/* 59 */   public int pos() { return this.totalPos + this.localPos; }
/*    */ 
/*    */   
/*    */   public void ignoreWhiteSpace() {
/*    */     char c;
/*    */     do {
/* 65 */       c = nextChar("EOF ignoring whitespace");
/* 66 */     } while (Character.isWhitespace(c));
/* 67 */     this.localPos--;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 74 */   public void back() { this.localPos--; }
/*    */ 
/*    */   
/*    */   public char nextChar(String eofMsg) {
/* 78 */     if (this.localPos >= this.localPosEnd && 
/* 79 */       !loadLocalBuffer()) {
/* 80 */       throw new TextException(eofMsg + " at pos:" + (this.totalPos + this.localPos));
/*    */     }
/*    */     
/* 83 */     return this.localBuffer[this.localPos++];
/*    */   }
/*    */   
/*    */   private boolean loadLocalBuffer() {
/*    */     try {
/* 88 */       this.localPosEnd = this.reader.read(this.localBuffer);
/* 89 */       if (this.localPosEnd > 0) {
/* 90 */         this.totalPos += this.localPos;
/* 91 */         this.localPos = 0;
/* 92 */         return true;
/*    */       } 
/* 94 */       this.localBuffer = null;
/* 95 */       return false;
/*    */     
/*    */     }
/* 98 */     catch (IOException e) {
/* 99 */       throw new TextException(e);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\text\json\ReadJsonSourceReader.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */