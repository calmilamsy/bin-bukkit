/*    */ package com.avaje.ebeaninternal.server.lib.resource;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import java.net.URL;
/*    */ import java.net.URLConnection;
/*    */ import java.util.Date;
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
/*    */ public class UrlResourceContent
/*    */   implements ResourceContent
/*    */ {
/*    */   String entryName;
/*    */   URLConnection con;
/*    */   
/*    */   public UrlResourceContent(URL url, String entryName) {
/* 45 */     this.entryName = entryName;
/*    */     try {
/* 47 */       this.con = url.openConnection();
/* 48 */     } catch (IOException ex) {
/* 49 */       throw new RuntimeException(ex);
/*    */     } 
/*    */   }
/*    */   
/*    */   public String toString() {
/* 54 */     StringBuffer sb = new StringBuffer();
/* 55 */     sb.append("[").append(getName());
/* 56 */     sb.append("] size[").append(size());
/* 57 */     sb.append("] lastModified[").append(new Date(lastModified()));
/* 58 */     sb.append("]");
/* 59 */     return sb.toString();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 70 */   public String getName() { return this.entryName; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 77 */   public long lastModified() { return this.con.getLastModified(); }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 84 */   public long size() { return this.con.getContentLength(); }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 92 */   public InputStream getInputStream() throws IOException { return this.con.getInputStream(); }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\lib\resource\UrlResourceContent.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */