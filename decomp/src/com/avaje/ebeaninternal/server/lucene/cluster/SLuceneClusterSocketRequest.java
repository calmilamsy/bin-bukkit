/*     */ package com.avaje.ebeaninternal.server.lucene.cluster;
/*     */ 
/*     */ import com.avaje.ebeaninternal.api.SpiEbeanServer;
/*     */ import com.avaje.ebeaninternal.server.cluster.ClusterManager;
/*     */ import com.avaje.ebeaninternal.server.lucene.LIndex;
/*     */ import com.avaje.ebeaninternal.server.lucene.LIndexCommitInfo;
/*     */ import com.avaje.ebeaninternal.server.lucene.LIndexFileInfo;
/*     */ import com.avaje.ebeaninternal.server.lucene.LuceneIndexManager;
/*     */ import java.io.BufferedInputStream;
/*     */ import java.io.DataInput;
/*     */ import java.io.DataInputStream;
/*     */ import java.io.DataOutput;
/*     */ import java.io.DataOutputStream;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.net.Socket;
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
/*     */ public class SLuceneClusterSocketRequest
/*     */   implements Runnable, SLuceneSocketMessageTypes
/*     */ {
/*  45 */   private static final Logger logger = Logger.getLogger(SLuceneClusterSocketRequest.class.getName());
/*     */   
/*     */   private final LuceneIndexManager manager;
/*     */   
/*     */   private final OutputStream os;
/*     */   
/*     */   private final DataInput dataInput;
/*     */   
/*     */   private final DataOutput dataOutput;
/*     */   
/*     */   private final String serverName;
/*     */   
/*     */   private final short msgType;
/*     */   
/*     */   private final String idxName;
/*     */   
/*     */   private final long remoteIndexVersion;
/*     */   
/*     */   public SLuceneClusterSocketRequest(ClusterManager clusterManager, Socket socket) throws IOException {
/*  64 */     this.os = socket.getOutputStream();
/*  65 */     InputStream is = socket.getInputStream();
/*  66 */     this.dataInput = new DataInputStream(is);
/*  67 */     this.dataOutput = new DataOutputStream(this.os);
/*     */     
/*  69 */     this.serverName = this.dataInput.readUTF();
/*  70 */     this.msgType = this.dataInput.readShort();
/*  71 */     this.idxName = this.dataInput.readUTF();
/*  72 */     this.remoteIndexVersion = this.dataInput.readLong();
/*     */     
/*  74 */     SpiEbeanServer server = (SpiEbeanServer)clusterManager.getServer(this.serverName);
/*  75 */     this.manager = server.getLuceneIndexManager();
/*     */   }
/*     */ 
/*     */   
/*     */   public void run() {
/*     */     try {
/*  81 */       switch (this.msgType) {
/*     */         case 2:
/*  83 */           obtainCommit();
/*     */           break;
/*     */         
/*     */         case 3:
/*  87 */           releaseCommit();
/*     */           break;
/*     */         
/*     */         case 4:
/*  91 */           getFile();
/*     */           break;
/*     */         
/*     */         default:
/*  95 */           throw new IOException("Invalid msgType " + this.msgType);
/*     */       } 
/*     */     
/*  98 */     } catch (IOException e) {
/*  99 */       String msg = "Error processing msg " + this.msgType + " " + this.idxName;
/* 100 */       logger.log(Level.SEVERE, msg, e);
/*     */     } finally {
/*     */       
/* 103 */       flush();
/*     */     } 
/*     */   }
/*     */   
/*     */   private void flush() {
/*     */     try {
/* 109 */       this.os.flush();
/* 110 */     } catch (IOException e) {
/* 111 */       String msg = "Error flushing Socket OuputStream";
/* 112 */       logger.log(Level.SEVERE, msg, e);
/*     */     } 
/*     */     try {
/* 115 */       this.os.close();
/* 116 */     } catch (IOException e) {
/* 117 */       String msg = "Error closing Socket OuputStream";
/* 118 */       logger.log(Level.SEVERE, msg, e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void releaseCommit() {
/* 129 */     LIndex index = this.manager.getIndex(this.idxName);
/* 130 */     index.releaseIndexCommit(this.remoteIndexVersion);
/* 131 */     this.dataOutput.writeBoolean(true);
/*     */   }
/*     */   
/*     */   private void obtainCommit() {
/* 135 */     LIndex index = this.manager.getIndex(this.idxName);
/* 136 */     LIndexCommitInfo commitInfo = index.obtainLastIndexCommitIfNewer(this.remoteIndexVersion);
/* 137 */     if (commitInfo == null) {
/*     */       
/* 139 */       this.dataOutput.writeBoolean(false);
/*     */     } else {
/* 141 */       this.dataOutput.writeBoolean(true);
/* 142 */       commitInfo.write(this.dataOutput);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void getFile() {
/* 147 */     LIndex index = this.manager.getIndex(this.idxName);
/* 148 */     String fileName = this.dataInput.readUTF();
/* 149 */     LIndexFileInfo fileInfo = index.getFile(this.remoteIndexVersion, fileName);
/*     */     
/* 151 */     f = fileInfo.getFile();
/* 152 */     if (!f.exists()) {
/* 153 */       this.dataOutput.writeBoolean(false);
/*     */     } else {
/* 155 */       this.dataOutput.writeBoolean(true);
/* 156 */       fis = new FileInputStream(f);
/*     */       try {
/* 158 */         buf = new byte[2048];
/* 159 */         BufferedInputStream bis = new BufferedInputStream(fis);
/*     */         
/* 161 */         int len = 0;
/* 162 */         while ((len = bis.read(buf)) > -1) {
/* 163 */           this.dataOutput.write(buf, 0, len);
/*     */         }
/*     */       } finally {
/*     */         
/*     */         try {
/* 168 */           fis.close();
/* 169 */         } catch (IOException e) {
/* 170 */           String msg = "Error closing InputStream on " + f.getAbsolutePath();
/* 171 */           logger.log(Level.SEVERE, msg, e);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\lucene\cluster\SLuceneClusterSocketRequest.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */