/*    */ package com.avaje.ebeaninternal.server.lib.resource;
/*    */ 
/*    */ import java.io.ByteArrayOutputStream;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import java.io.InputStreamReader;
/*    */ import java.io.StringWriter;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class AbstractResourceSource
/*    */ {
/*    */   public String readString(ResourceContent content, int bufSize) throws IOException {
/* 17 */     if (content == null) {
/* 18 */       throw new NullPointerException("content is null?");
/*    */     }
/* 20 */     StringWriter writer = new StringWriter();
/*    */     
/* 22 */     InputStream is = content.getInputStream();
/* 23 */     InputStreamReader reader = new InputStreamReader(is);
/*    */     
/* 25 */     char[] buf = new char[bufSize];
/*    */     int len;
/* 27 */     while ((len = reader.read(buf, 0, buf.length)) != -1) {
/* 28 */       writer.write(buf, 0, len);
/*    */     }
/*    */     
/* 31 */     reader.close();
/*    */     
/* 33 */     return writer.toString();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public byte[] readBytes(ResourceContent content, int bufSize) throws IOException {
/* 41 */     ByteArrayOutputStream bytes = new ByteArrayOutputStream();
/*    */     
/* 43 */     InputStream is = content.getInputStream();
/*    */     
/* 45 */     byte[] buf = new byte[bufSize];
/*    */     int len;
/* 47 */     while ((len = is.read(buf, 0, buf.length)) != -1) {
/* 48 */       bytes.write(buf, 0, len);
/*    */     }
/*    */     
/* 51 */     is.close();
/*    */     
/* 53 */     return bytes.toByteArray();
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\lib\resource\AbstractResourceSource.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */