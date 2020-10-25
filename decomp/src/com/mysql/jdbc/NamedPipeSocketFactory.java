/*     */ package com.mysql.jdbc;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.io.RandomAccessFile;
/*     */ import java.net.Socket;
/*     */ import java.net.SocketException;
/*     */ import java.util.Properties;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class NamedPipeSocketFactory
/*     */   implements SocketFactory
/*     */ {
/*     */   public static final String NAMED_PIPE_PROP_NAME = "namedPipePath";
/*     */   private Socket namedPipeSocket;
/*     */   
/*     */   class NamedPipeSocket
/*     */     extends Socket
/*     */   {
/*     */     private boolean isClosed = false;
/*     */     private RandomAccessFile namedPipeFile;
/*     */     
/*     */     NamedPipeSocket(String filePath) throws IOException {
/*  51 */       if (filePath == null || filePath.length() == 0) {
/*  52 */         throw new IOException(Messages.getString("NamedPipeSocketFactory.4"));
/*     */       }
/*     */ 
/*     */       
/*  56 */       this.namedPipeFile = new RandomAccessFile(filePath, "rw");
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void close() {
/*  63 */       this.namedPipeFile.close();
/*  64 */       this.isClosed = true;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  71 */     public InputStream getInputStream() throws IOException { return new NamedPipeSocketFactory.RandomAccessFileInputStream(NamedPipeSocketFactory.this, this.namedPipeFile); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  78 */     public OutputStream getOutputStream() throws IOException { return new NamedPipeSocketFactory.RandomAccessFileOutputStream(NamedPipeSocketFactory.this, this.namedPipeFile); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  85 */     public boolean isClosed() { return this.isClosed; }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   class RandomAccessFileInputStream
/*     */     extends InputStream
/*     */   {
/*     */     RandomAccessFile raFile;
/*     */ 
/*     */     
/*  96 */     RandomAccessFileInputStream(RandomAccessFile file) { this.raFile = file; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 103 */     public int available() throws IOException { return -1; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 110 */     public void close() { this.raFile.close(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 117 */     public int read() throws IOException { return this.raFile.read(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 124 */     public int read(byte[] b) throws IOException { return this.raFile.read(b); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 131 */     public int read(byte[] b, int off, int len) throws IOException { return this.raFile.read(b, off, len); }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   class RandomAccessFileOutputStream
/*     */     extends OutputStream
/*     */   {
/*     */     RandomAccessFile raFile;
/*     */ 
/*     */     
/* 142 */     RandomAccessFileOutputStream(RandomAccessFile file) { this.raFile = file; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 149 */     public void close() { this.raFile.close(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 156 */     public void write(byte[] b) throws IOException { this.raFile.write(b); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 163 */     public void write(byte[] b, int off, int len) throws IOException { this.raFile.write(b, off, len); }
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
/*     */     public void write(int b) throws IOException {}
/*     */   }
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
/* 188 */   public Socket afterHandshake() throws SocketException, IOException { return this.namedPipeSocket; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 195 */   public Socket beforeHandshake() throws SocketException, IOException { return this.namedPipeSocket; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Socket connect(String host, int portNumber, Properties props) throws SocketException, IOException {
/* 203 */     String namedPipePath = props.getProperty("namedPipePath");
/*     */     
/* 205 */     if (namedPipePath == null) {
/* 206 */       namedPipePath = "\\\\.\\pipe\\MySQL";
/* 207 */     } else if (namedPipePath.length() == 0) {
/* 208 */       throw new SocketException(Messages.getString("NamedPipeSocketFactory.2") + "namedPipePath" + Messages.getString("NamedPipeSocketFactory.3"));
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 214 */     this.namedPipeSocket = new NamedPipeSocket(namedPipePath);
/*     */     
/* 216 */     return this.namedPipeSocket;
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\mysql\jdbc\NamedPipeSocketFactory.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */