/*    */ package com.avaje.ebeaninternal.server.lucene;
/*    */ 
/*    */ import java.io.DataInput;
/*    */ import java.io.DataOutput;
/*    */ import java.io.File;
/*    */ import java.io.IOException;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ import java.util.List;
/*    */ import org.apache.lucene.index.IndexCommit;
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
/*    */ public class LIndexCommitInfo
/*    */ {
/*    */   private final String indexDir;
/*    */   private final LIndexVersion version;
/*    */   private final List<LIndexFileInfo> fileInfo;
/*    */   
/*    */   public LIndexCommitInfo(String indexDir, IndexCommit indexCommit) {
/* 29 */     this.indexDir = indexDir;
/* 30 */     this.version = new LIndexVersion(indexCommit.getGeneration(), indexCommit.getVersion());
/* 31 */     this.fileInfo = createFileInfo(indexCommit);
/*    */   }
/*    */   
/*    */   public LIndexCommitInfo(String indexDir, LIndexVersion version, List<LIndexFileInfo> fileInfo) {
/* 35 */     this.indexDir = indexDir;
/* 36 */     this.version = version;
/* 37 */     this.fileInfo = fileInfo;
/*    */   }
/*    */ 
/*    */   
/* 41 */   public String toString() { return this.indexDir + " " + this.version + " " + this.fileInfo; }
/*    */ 
/*    */ 
/*    */   
/* 45 */   public LIndexVersion getVersion() { return this.version; }
/*    */ 
/*    */ 
/*    */   
/* 49 */   public List<LIndexFileInfo> getFileInfo() { return this.fileInfo; }
/*    */ 
/*    */ 
/*    */   
/*    */   public static LIndexCommitInfo read(DataInput dataInput) throws IOException {
/* 54 */     String idxDir = dataInput.readUTF();
/* 55 */     long gen = dataInput.readLong();
/* 56 */     long ver = dataInput.readLong();
/* 57 */     int fileCount = dataInput.readInt();
/* 58 */     List<LIndexFileInfo> fileInfo = new ArrayList<LIndexFileInfo>(fileCount);
/*    */     
/* 60 */     for (int i = 0; i < fileCount; i++) {
/* 61 */       fileInfo.add(LIndexFileInfo.read(dataInput));
/*    */     }
/*    */     
/* 64 */     return new LIndexCommitInfo(idxDir, new LIndexVersion(gen, ver), fileInfo);
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(DataOutput dataOutput) throws IOException {
/* 69 */     dataOutput.writeUTF(this.indexDir);
/* 70 */     dataOutput.writeLong(this.version.getGeneration());
/* 71 */     dataOutput.writeLong(this.version.getVersion());
/*    */     
/* 73 */     int fileCount = this.fileInfo.size();
/* 74 */     dataOutput.writeInt(fileCount);
/* 75 */     for (int i = 0; i < this.fileInfo.size(); i++) {
/* 76 */       ((LIndexFileInfo)this.fileInfo.get(i)).write(dataOutput);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   private List<LIndexFileInfo> createFileInfo(IndexCommit indexCommit) {
/*    */     try {
/* 83 */       Collection<String> fileNames = indexCommit.getFileNames();
/*    */       
/* 85 */       List<LIndexFileInfo> files = new ArrayList<LIndexFileInfo>(fileNames.size());
/* 86 */       for (String fileName : fileNames) {
/* 87 */         files.add(getFileInfo(fileName));
/*    */       }
/*    */       
/* 90 */       return files;
/*    */     }
/* 92 */     catch (IOException e) {
/* 93 */       throw new PersistenceLuceneException(e);
/*    */     } 
/*    */   }
/*    */   
/*    */   private LIndexFileInfo getFileInfo(String fileName) {
/* 98 */     File f = new File(this.indexDir, fileName);
/* 99 */     return new LIndexFileInfo(f);
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\lucene\LIndexCommitInfo.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */