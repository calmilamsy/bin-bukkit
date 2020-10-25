/*     */ package com.avaje.ebeaninternal.server.lucene.cluster;
/*     */ 
/*     */ import com.avaje.ebeaninternal.server.lucene.LIndex;
/*     */ import com.avaje.ebeaninternal.server.lucene.LIndexCommitInfo;
/*     */ import com.avaje.ebeaninternal.server.lucene.LIndexFileInfo;
/*     */ import com.avaje.ebeaninternal.server.lucene.LIndexVersion;
/*     */ import java.io.BufferedInputStream;
/*     */ import java.io.File;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.net.InetSocketAddress;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SLuceneClusterSocketClient
/*     */   implements SLuceneSocketMessageTypes
/*     */ {
/*  40 */   private static final Logger logger = Logger.getLogger(SLuceneClusterSocketClient.class.getName());
/*     */   
/*     */   private InetSocketAddress master;
/*     */   private LIndex index;
/*     */   private SLuceneClient client;
/*     */   private File tmpDir;
/*     */   private ArrayList<File> addFiles;
/*     */   private ArrayList<File> replaceFiles;
/*     */   
/*     */   public SLuceneClusterSocketClient(LIndex index) {
/*  50 */     this.addFiles = new ArrayList();
/*     */     
/*  52 */     this.replaceFiles = new ArrayList();
/*     */ 
/*     */     
/*  55 */     this.index = index;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isSynchIndex(String masterHost) throws IOException {
/*  60 */     this.master = SocketClient.parseHostPort(masterHost);
/*     */     
/*  62 */     LIndexVersion localVersion = this.index.getLastestVersion();
/*  63 */     System.out.println("-- Got localVersion " + localVersion);
/*     */     
/*  65 */     SocketClient client = new SocketClient(this.master);
/*     */     
/*  67 */     String serverName = this.index.getBeanDescriptor().getServerName();
/*  68 */     this.client = new SLuceneClient(serverName, client, localVersion.getVersion(), this.index);
/*     */     
/*     */     try {
/*  71 */       LIndexCommitInfo commitInfo = getCommitInfo();
/*  72 */       if (commitInfo == null) {
/*  73 */         logger.info("Lucene index up to date [" + this.index.getName() + "]");
/*  74 */         return false;
/*     */       } 
/*     */       
/*  77 */       getCommitFiles(localVersion, commitInfo);
/*  78 */       return true;
/*     */     }
/*  80 */     catch (IOException e) {
/*  81 */       String msg = "Error synch'ing index " + this.index.getName();
/*  82 */       logger.log(Level.SEVERE, msg, e);
/*  83 */       throw e;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void transferFiles() {
/*  88 */     File indexDir = this.index.getIndexDir();
/*  89 */     for (int i = 0; i < this.addFiles.size(); i++) {
/*  90 */       File addFile = (File)this.addFiles.get(i);
/*  91 */       File destFile = new File(indexDir, addFile.getName());
/*     */       
/*  93 */       addFile.renameTo(destFile);
/*     */     } 
/*     */     
/*  96 */     this.tmpDir.delete();
/*     */   }
/*     */   
/*     */   private void getCommitFiles(LIndexVersion localVersion, LIndexCommitInfo commitInfo) throws IOException {
/*     */     try {
/* 101 */       this.client.setRemoteVersion(commitInfo.getVersion().getVersion());
/*     */ 
/*     */       
/* 104 */       copyFiles(commitInfo);
/*     */     } finally {
/*     */       
/*     */       try {
/* 108 */         this.client.sendRelease();
/* 109 */       } catch (IOException e) {
/* 110 */         String msg = "Error sending release for index " + this.client.getIndex();
/* 111 */         logger.log(Level.SEVERE, msg, e);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void copyFiles(LIndexCommitInfo commitInfo) throws IOException {
/* 118 */     LIndex index = this.client.getIndex();
/*     */     
/* 120 */     File indexDir = index.getIndexDir();
/*     */     
/* 122 */     this.tmpDir = new File(indexDir, "tmp-" + System.currentTimeMillis());
/*     */     
/* 124 */     if (!this.tmpDir.exists() && !this.tmpDir.mkdirs()) {
/* 125 */       String msg = "Could not create directory tmpDir: " + this.tmpDir;
/* 126 */       throw new IOException(msg);
/*     */     } 
/*     */     
/* 129 */     List<LIndexFileInfo> fileInfo = commitInfo.getFileInfo();
/*     */     
/* 131 */     logger.info("Lucene index synchonizing from[" + this.master + "] ver[" + commitInfo.getVersion() + "] files[" + fileInfo + "]");
/*     */     
/* 133 */     for (int i = 0; i < fileInfo.size(); i++) {
/*     */       
/* 135 */       LIndexFileInfo fi = (LIndexFileInfo)fileInfo.get(i);
/* 136 */       String fileName = fi.getName();
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 141 */       LIndexFileInfo localFileInfo = index.getLocalFile(fileName);
/*     */       
/* 143 */       if (localFileInfo.exists()) {
/* 144 */         if (localFileInfo.isMatch(fi)) {
/* 145 */           logger.info("... skip [" + fi.getName() + "]");
/*     */         } else {
/* 147 */           logger.warning("Lucene index file [" + fi.getName() + "] exists but not match size or lastModified?");
/* 148 */           downloadFile(false, fi);
/*     */         } 
/*     */       } else {
/* 151 */         downloadFile(true, fi);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void downloadFile(boolean addFile, LIndexFileInfo fi) throws IOException {
/*     */     try {
/* 161 */       String fileName = fi.getName();
/*     */       
/* 163 */       InputStream fileInputStream = this.client.sendGetFile(fileName);
/* 164 */       BufferedInputStream bufIs = new BufferedInputStream(fileInputStream);
/*     */       
/* 166 */       File fileOut = new File(this.tmpDir, fileName);
/* 167 */       FileOutputStream os = new FileOutputStream(fileOut);
/*     */       
/* 169 */       byte[] buf = new byte[2048];
/*     */       
/* 171 */       int totalLen = 0;
/* 172 */       int len = 0;
/* 173 */       while ((len = bufIs.read(buf)) > -1) {
/* 174 */         os.write(buf, 0, len);
/* 175 */         totalLen += len;
/*     */       } 
/*     */       
/* 178 */       os.flush();
/* 179 */       os.close();
/*     */       
/* 181 */       fileOut.setLastModified(fi.getLastModified());
/* 182 */       System.out.println("got file len:" + totalLen + " " + fileName);
/*     */       
/* 184 */       if (addFile) {
/* 185 */         this.addFiles.add(fileOut);
/*     */       } else {
/* 187 */         this.replaceFiles.add(fileOut);
/*     */       } 
/*     */     } finally {
/*     */       
/* 191 */       this.client.disconnect();
/*     */     } 
/*     */   }
/*     */   
/*     */   private LIndexCommitInfo getCommitInfo() throws IOException {
/*     */     try {
/* 197 */       boolean gotInfo = this.client.sendObtainCommit();
/* 198 */       if (!gotInfo) {
/* 199 */         return null;
/*     */       }
/*     */       
/* 202 */       return LIndexCommitInfo.read(this.client.getSocketClient().getDataInput());
/*     */     } finally {
/*     */       
/* 205 */       this.client.disconnect();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\lucene\cluster\SLuceneClusterSocketClient.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */