/*     */ package com.mysql.jdbc;
/*     */ 
/*     */ import java.io.EOFException;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.sql.SQLException;
/*     */ import java.util.zip.DataFormatException;
/*     */ import java.util.zip.Inflater;
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
/*     */ class CompressedInputStream
/*     */   extends InputStream
/*     */ {
/*     */   private byte[] buffer;
/*     */   private Connection connection;
/*     */   private InputStream in;
/*     */   private Inflater inflater;
/*     */   private byte[] packetHeaderBuffer;
/*     */   private int pos;
/*     */   
/*     */   public CompressedInputStream(Connection conn, InputStream streamFromServer) {
/*  60 */     this.packetHeaderBuffer = new byte[7];
/*     */ 
/*     */     
/*  63 */     this.pos = 0;
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
/*  74 */     this.connection = conn;
/*  75 */     this.in = streamFromServer;
/*  76 */     this.inflater = new Inflater();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int available() throws IOException {
/*  83 */     if (this.buffer == null) {
/*  84 */       return this.in.available();
/*     */     }
/*     */     
/*  87 */     return this.buffer.length - this.pos + this.in.available();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void close() throws IOException {
/*  94 */     this.in.close();
/*  95 */     this.buffer = null;
/*  96 */     this.inflater = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void getNextPacketFromServer() throws IOException {
/* 107 */     byte[] uncompressedData = null;
/*     */     
/* 109 */     int lengthRead = readFully(this.packetHeaderBuffer, 0, 7);
/*     */     
/* 111 */     if (lengthRead < 7) {
/* 112 */       throw new IOException("Unexpected end of input stream");
/*     */     }
/*     */     
/* 115 */     int compressedPacketLength = (this.packetHeaderBuffer[0] & 0xFF) + ((this.packetHeaderBuffer[1] & 0xFF) << 8) + ((this.packetHeaderBuffer[2] & 0xFF) << 16);
/*     */ 
/*     */ 
/*     */     
/* 119 */     int uncompressedLength = (this.packetHeaderBuffer[4] & 0xFF) + ((this.packetHeaderBuffer[5] & 0xFF) << 8) + ((this.packetHeaderBuffer[6] & 0xFF) << 16);
/*     */ 
/*     */ 
/*     */     
/* 123 */     if (this.connection.getTraceProtocol()) {
/*     */       try {
/* 125 */         this.connection.getLog().logTrace("Reading compressed packet of length " + compressedPacketLength + " uncompressed to " + uncompressedLength);
/*     */ 
/*     */       
/*     */       }
/* 129 */       catch (SQLException sqlEx) {
/* 130 */         throw new IOException(sqlEx.toString());
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/* 135 */     if (uncompressedLength > 0) {
/* 136 */       uncompressedData = new byte[uncompressedLength];
/*     */       
/* 138 */       byte[] compressedBuffer = new byte[compressedPacketLength];
/*     */       
/* 140 */       readFully(compressedBuffer, 0, compressedPacketLength);
/*     */       
/*     */       try {
/* 143 */         this.inflater.reset();
/* 144 */       } catch (NullPointerException npe) {
/* 145 */         this.inflater = new Inflater();
/*     */       } 
/*     */       
/* 148 */       this.inflater.setInput(compressedBuffer);
/*     */       
/*     */       try {
/* 151 */         this.inflater.inflate(uncompressedData);
/* 152 */       } catch (DataFormatException dfe) {
/* 153 */         throw new IOException("Error while uncompressing packet from server.");
/*     */       } 
/*     */ 
/*     */       
/* 157 */       this.inflater.end();
/*     */     } else {
/* 159 */       if (this.connection.getTraceProtocol()) {
/*     */         try {
/* 161 */           this.connection.getLog().logTrace("Packet didn't meet compression threshold, not uncompressing...");
/*     */ 
/*     */         
/*     */         }
/* 165 */         catch (SQLException sqlEx) {
/* 166 */           throw new IOException(sqlEx.toString());
/*     */         } 
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 175 */       uncompressedData = new byte[compressedPacketLength];
/* 176 */       readFully(uncompressedData, 0, compressedPacketLength);
/*     */     } 
/*     */     
/* 179 */     if (this.connection.getTraceProtocol()) {
/*     */       try {
/* 181 */         this.connection.getLog().logTrace("Uncompressed packet: \n" + StringUtils.dumpAsHex(uncompressedData, compressedPacketLength));
/*     */ 
/*     */       
/*     */       }
/* 185 */       catch (SQLException sqlEx) {
/* 186 */         throw new IOException(sqlEx.toString());
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/* 191 */     if (this.buffer != null && this.pos < this.buffer.length) {
/* 192 */       if (this.connection.getTraceProtocol()) {
/*     */         try {
/* 194 */           this.connection.getLog().logTrace("Combining remaining packet with new: ");
/*     */         }
/* 196 */         catch (SQLException sqlEx) {
/* 197 */           throw new IOException(sqlEx.toString());
/*     */         } 
/*     */       }
/*     */ 
/*     */       
/* 202 */       int remaining = this.buffer.length - this.pos;
/* 203 */       byte[] newBuffer = new byte[remaining + uncompressedData.length];
/*     */       
/* 205 */       int newIndex = 0;
/*     */       
/* 207 */       for (int i = this.pos; i < this.buffer.length; i++) {
/* 208 */         newBuffer[newIndex++] = this.buffer[i];
/*     */       }
/* 210 */       System.arraycopy(uncompressedData, 0, newBuffer, newIndex, uncompressedData.length);
/*     */ 
/*     */       
/* 213 */       uncompressedData = newBuffer;
/*     */     } 
/*     */     
/* 216 */     this.pos = 0;
/* 217 */     this.buffer = uncompressedData;
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
/*     */ 
/*     */   
/*     */   private void getNextPacketIfRequired(int numBytes) throws IOException {
/* 233 */     if (this.buffer == null || this.pos + numBytes > this.buffer.length)
/*     */     {
/* 235 */       getNextPacketFromServer();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int read() throws IOException {
/*     */     try {
/* 244 */       getNextPacketIfRequired(1);
/* 245 */     } catch (IOException ioEx) {
/* 246 */       return -1;
/*     */     } 
/*     */     
/* 249 */     return this.buffer[this.pos++] & 0xFF;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 256 */   public int read(byte[] b) throws IOException { return read(b, 0, b.length); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int read(byte[] b, int off, int len) throws IOException {
/* 263 */     if (b == null)
/* 264 */       throw new NullPointerException(); 
/* 265 */     if (off < 0 || off > b.length || len < 0 || off + len > b.length || off + len < 0)
/*     */     {
/* 267 */       throw new IndexOutOfBoundsException();
/*     */     }
/*     */     
/* 270 */     if (len <= 0) {
/* 271 */       return 0;
/*     */     }
/*     */     
/*     */     try {
/* 275 */       getNextPacketIfRequired(len);
/* 276 */     } catch (IOException ioEx) {
/* 277 */       return -1;
/*     */     } 
/*     */     
/* 280 */     System.arraycopy(this.buffer, this.pos, b, off, len);
/* 281 */     this.pos += len;
/*     */     
/* 283 */     return len;
/*     */   }
/*     */   
/*     */   private final int readFully(byte[] b, int off, int len) throws IOException {
/* 287 */     if (len < 0) {
/* 288 */       throw new IndexOutOfBoundsException();
/*     */     }
/*     */     
/* 291 */     int n = 0;
/*     */     
/* 293 */     while (n < len) {
/* 294 */       int count = this.in.read(b, off + n, len - n);
/*     */       
/* 296 */       if (count < 0) {
/* 297 */         throw new EOFException();
/*     */       }
/*     */       
/* 300 */       n += count;
/*     */     } 
/*     */     
/* 303 */     return n;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long skip(long n) throws IOException {
/* 310 */     long count = 0L;
/*     */     long i;
/* 312 */     for (i = 0L; i < n; i++) {
/* 313 */       int bytesRead = read();
/*     */       
/* 315 */       if (bytesRead == -1) {
/*     */         break;
/*     */       }
/*     */       
/* 319 */       count++;
/*     */     } 
/*     */     
/* 322 */     return count;
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\mysql\jdbc\CompressedInputStream.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */