/*    */ package com.avaje.ebeaninternal.server.lib.resource;
/*    */ 
/*    */ import java.io.File;
/*    */ import java.io.FileInputStream;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
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
/*    */ public class FileResourceContent
/*    */   implements ResourceContent
/*    */ {
/*    */   File file;
/*    */   String entryName;
/*    */   
/*    */   public FileResourceContent(File file, String entryName) {
/* 42 */     this.file = file;
/* 43 */     this.entryName = entryName;
/*    */   }
/*    */   
/*    */   public String toString() {
/* 47 */     StringBuffer sb = new StringBuffer();
/* 48 */     sb.append("[").append(getName());
/* 49 */     sb.append("] size[").append(size());
/* 50 */     sb.append("] lastModified[").append(new Date(lastModified()));
/* 51 */     sb.append("]");
/* 52 */     return sb.toString();
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
/* 63 */   public String getName() { return this.entryName; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 70 */   public long lastModified() { return this.file.lastModified(); }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 77 */   public long size() { return this.file.length(); }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 85 */   public InputStream getInputStream() throws IOException { return new FileInputStream(this.file); }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\lib\resource\FileResourceContent.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */