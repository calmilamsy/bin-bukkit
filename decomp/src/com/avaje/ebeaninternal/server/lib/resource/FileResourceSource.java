/*    */ package com.avaje.ebeaninternal.server.lib.resource;
/*    */ 
/*    */ import java.io.File;
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
/*    */ public class FileResourceSource
/*    */   extends AbstractResourceSource
/*    */   implements ResourceSource
/*    */ {
/*    */   String directory;
/*    */   String baseDir;
/*    */   
/*    */   public FileResourceSource(String directory) {
/* 38 */     this.directory = directory;
/* 39 */     this.baseDir = directory + File.separator;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 46 */   public FileResourceSource(File dir) { this(dir.getPath()); }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 51 */   public String getRealPath() { return this.directory; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ResourceContent getContent(String entry) {
/* 59 */     String fullPath = this.baseDir + entry;
/*    */     
/* 61 */     File f = new File(fullPath);
/* 62 */     if (f.exists()) {
/* 63 */       return new FileResourceContent(f, entry);
/*    */     }
/*    */     
/* 66 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\lib\resource\FileResourceSource.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */