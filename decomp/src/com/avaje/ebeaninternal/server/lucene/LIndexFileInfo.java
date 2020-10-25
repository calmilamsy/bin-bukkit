/*    */ package com.avaje.ebeaninternal.server.lucene;
/*    */ 
/*    */ import java.io.DataInput;
/*    */ import java.io.DataOutput;
/*    */ import java.io.File;
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
/*    */ 
/*    */ public class LIndexFileInfo
/*    */ {
/*    */   private final File file;
/*    */   private final String name;
/*    */   private final long length;
/*    */   private final long lastModified;
/*    */   
/*    */   public LIndexFileInfo(File file) {
/* 38 */     this.file = file;
/* 39 */     this.name = file.getName();
/* 40 */     this.length = file.length();
/* 41 */     this.lastModified = file.lastModified();
/*    */   }
/*    */   
/*    */   public LIndexFileInfo(String name, long length, long lastModified) {
/* 45 */     this.file = null;
/* 46 */     this.name = name;
/* 47 */     this.length = length;
/* 48 */     this.lastModified = lastModified;
/*    */   }
/*    */ 
/*    */   
/* 52 */   public String toString() { return this.name + " length[" + this.length + "] lastModified[" + this.lastModified + "]"; }
/*    */ 
/*    */   
/*    */   public static LIndexFileInfo read(DataInput dataInput) throws IOException {
/* 56 */     String name = dataInput.readUTF();
/* 57 */     long len = dataInput.readLong();
/* 58 */     long lastMod = dataInput.readLong();
/* 59 */     return new LIndexFileInfo(name, len, lastMod);
/*    */   }
/*    */   
/*    */   public void write(DataOutput dataOutput) throws IOException {
/* 63 */     dataOutput.writeUTF(this.name);
/* 64 */     dataOutput.writeLong(this.length);
/* 65 */     dataOutput.writeLong(this.lastModified);
/*    */   }
/*    */ 
/*    */   
/* 69 */   public boolean exists() { return this.file.exists(); }
/*    */ 
/*    */ 
/*    */   
/* 73 */   public File getFile() { return this.file; }
/*    */ 
/*    */ 
/*    */   
/* 77 */   public String getName() { return this.name; }
/*    */ 
/*    */ 
/*    */   
/* 81 */   public long getLength() { return this.length; }
/*    */ 
/*    */ 
/*    */   
/* 85 */   public long getLastModified() { return this.lastModified; }
/*    */ 
/*    */ 
/*    */   
/* 89 */   public boolean isMatch(LIndexFileInfo otherFile) { return (otherFile.length == this.length && otherFile.lastModified == this.lastModified); }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\lucene\LIndexFileInfo.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */