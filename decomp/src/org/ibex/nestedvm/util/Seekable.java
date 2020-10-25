/*     */ package org.ibex.nestedvm.util;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ 
/*     */ public abstract class Seekable {
/*     */   public abstract int read(byte[] paramArrayOfByte, int paramInt1, int paramInt2) throws IOException;
/*     */   
/*     */   public abstract int write(byte[] paramArrayOfByte, int paramInt1, int paramInt2) throws IOException;
/*     */   
/*     */   public abstract int length() throws IOException;
/*     */   
/*     */   public abstract void seek(int paramInt) throws IOException;
/*     */   
/*     */   public abstract void close();
/*     */   
/*     */   public abstract int pos() throws IOException;
/*     */   
/*  18 */   public void sync() { throw new IOException("sync not implemented for " + getClass()); }
/*     */ 
/*     */   
/*  21 */   public void resize(long paramLong) throws IOException { throw new IOException("resize not implemented for " + getClass()); }
/*     */ 
/*     */ 
/*     */   
/*  25 */   public Lock lock(long paramLong1, long paramLong2, boolean paramBoolean) throws IOException { throw new IOException("lock not implemented for " + getClass()); }
/*     */ 
/*     */   
/*     */   public int read() throws IOException {
/*  29 */     byte[] arrayOfByte = new byte[1];
/*  30 */     int i = read(arrayOfByte, 0, 1);
/*  31 */     return (i == -1) ? -1 : (arrayOfByte[0] & 0xFF);
/*     */   }
/*     */   
/*     */   public int tryReadFully(byte[] paramArrayOfByte, int paramInt1, int paramInt2) throws IOException {
/*  35 */     int i = 0;
/*  36 */     while (paramInt2 > 0) {
/*  37 */       int j = read(paramArrayOfByte, paramInt1, paramInt2);
/*  38 */       if (j == -1)
/*  39 */         break;  paramInt1 += j;
/*  40 */       paramInt2 -= j;
/*  41 */       i += j;
/*     */     } 
/*  43 */     return (i == 0) ? -1 : i;
/*     */   }
/*     */   
/*     */   public static class ByteArray extends Seekable {
/*     */     protected byte[] data;
/*     */     protected int pos;
/*     */     private final boolean writable;
/*     */     
/*     */     public ByteArray(byte[] param1ArrayOfByte, boolean param1Boolean) {
/*  52 */       this.data = param1ArrayOfByte;
/*  53 */       this.pos = 0;
/*  54 */       this.writable = param1Boolean;
/*     */     }
/*     */     
/*     */     public int read(byte[] param1ArrayOfByte, int param1Int1, int param1Int2) throws IOException {
/*  58 */       param1Int2 = Math.min(param1Int2, this.data.length - this.pos);
/*  59 */       if (param1Int2 <= 0) return -1; 
/*  60 */       System.arraycopy(this.data, this.pos, param1ArrayOfByte, param1Int1, param1Int2);
/*  61 */       this.pos += param1Int2;
/*  62 */       return param1Int2;
/*     */     }
/*     */     
/*     */     public int write(byte[] param1ArrayOfByte, int param1Int1, int param1Int2) throws IOException {
/*  66 */       if (!this.writable) throw new IOException("read-only data"); 
/*  67 */       param1Int2 = Math.min(param1Int2, this.data.length - this.pos);
/*  68 */       if (param1Int2 <= 0) throw new IOException("no space"); 
/*  69 */       System.arraycopy(param1ArrayOfByte, param1Int1, this.data, this.pos, param1Int2);
/*  70 */       this.pos += param1Int2;
/*  71 */       return param1Int2;
/*     */     }
/*     */     
/*  74 */     public int length() throws IOException { return this.data.length; }
/*  75 */     public int pos() throws IOException { return this.pos; }
/*  76 */     public void seek(int param1Int) throws IOException { this.pos = param1Int; }
/*     */     
/*     */     public void close() {}
/*     */   }
/*     */   
/*     */   public static class File extends Seekable { private final File file;
/*     */     private final RandomAccessFile raf;
/*     */     
/*  84 */     public File(String param1String) throws IOException { this(param1String, false); }
/*  85 */     public File(String param1String, boolean param1Boolean) throws IOException { this(new File(param1String), param1Boolean, false); }
/*     */     
/*     */     public File(File param1File, boolean param1Boolean1, boolean param1Boolean2) throws IOException {
/*  88 */       this.file = param1File;
/*  89 */       String str = param1Boolean1 ? "rw" : "r";
/*  90 */       this.raf = new RandomAccessFile(param1File, str);
/*  91 */       if (param1Boolean2) Platform.setFileLength(this.raf, 0); 
/*     */     }
/*     */     
/*  94 */     public int read(byte[] param1ArrayOfByte, int param1Int1, int param1Int2) throws IOException { return this.raf.read(param1ArrayOfByte, param1Int1, param1Int2); }
/*  95 */     public int write(byte[] param1ArrayOfByte, int param1Int1, int param1Int2) throws IOException { this.raf.write(param1ArrayOfByte, param1Int1, param1Int2); return param1Int2; }
/*  96 */     public void sync() { this.raf.getFD().sync(); }
/*  97 */     public void seek(int param1Int) throws IOException { this.raf.seek(param1Int); }
/*  98 */     public int pos() throws IOException { return (int)this.raf.getFilePointer(); }
/*  99 */     public int length() throws IOException { return (int)this.raf.length(); }
/* 100 */     public void close() { this.raf.close(); }
/* 101 */     public void resize(long param1Long) throws IOException { Platform.setFileLength(this.raf, (int)param1Long); }
/*     */     
/* 103 */     public boolean equals(Object param1Object) { return (param1Object != null && param1Object instanceof File && this.file.equals(((File)param1Object).file)); }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 108 */     public Seekable.Lock lock(long param1Long1, long param1Long2, boolean param1Boolean) throws IOException { return Platform.lockFile(this, this.raf, param1Long1, param1Long2, param1Boolean); } }
/*     */   public static class InputStream extends Seekable { private byte[] buffer;
/*     */     private int bytesRead;
/*     */     
/*     */     public InputStream(InputStream param1InputStream) {
/* 113 */       this.buffer = new byte[4096];
/* 114 */       this.bytesRead = 0;
/* 115 */       this.eof = false;
/*     */ 
/*     */ 
/*     */       
/* 119 */       this.is = param1InputStream;
/*     */     } private boolean eof; private int pos; private InputStream is;
/*     */     public int read(byte[] param1ArrayOfByte, int param1Int1, int param1Int2) throws IOException {
/* 122 */       if (this.pos >= this.bytesRead && !this.eof) readTo(this.pos + 1); 
/* 123 */       param1Int2 = Math.min(param1Int2, this.bytesRead - this.pos);
/* 124 */       if (param1Int2 <= 0) return -1; 
/* 125 */       System.arraycopy(this.buffer, this.pos, param1ArrayOfByte, param1Int1, param1Int2);
/* 126 */       this.pos += param1Int2;
/* 127 */       return param1Int2;
/*     */     }
/*     */     
/*     */     private void readTo(int param1Int) throws IOException {
/* 131 */       if (param1Int >= this.buffer.length) {
/* 132 */         byte[] arrayOfByte = new byte[Math.max(this.buffer.length + Math.min(this.buffer.length, 65536), param1Int)];
/* 133 */         System.arraycopy(this.buffer, 0, arrayOfByte, 0, this.bytesRead);
/* 134 */         this.buffer = arrayOfByte;
/*     */       } 
/* 136 */       while (this.bytesRead < param1Int) {
/* 137 */         int i = this.is.read(this.buffer, this.bytesRead, this.buffer.length - this.bytesRead);
/* 138 */         if (i == -1) {
/* 139 */           this.eof = true;
/*     */           break;
/*     */         } 
/* 142 */         this.bytesRead += i;
/*     */       } 
/*     */     }
/*     */     
/*     */     public int length() throws IOException {
/* 147 */       for (; !this.eof; readTo(this.bytesRead + 4096));
/* 148 */       return this.bytesRead;
/*     */     }
/*     */     
/* 151 */     public int write(byte[] param1ArrayOfByte, int param1Int1, int param1Int2) throws IOException { throw new IOException("read-only"); }
/* 152 */     public void seek(int param1Int) throws IOException { this.pos = param1Int; }
/* 153 */     public int pos() throws IOException { return this.pos; }
/* 154 */     public void close() { this.is.close(); } }
/*     */ 
/*     */   
/*     */   public static abstract class Lock {
/* 158 */     private Object owner = null;
/*     */     public abstract Seekable seekable();
/*     */     public abstract boolean isShared();
/*     */     public abstract boolean isValid();
/*     */     public abstract void release();
/*     */     public abstract long position();
/*     */     
/*     */     public abstract long size();
/*     */     
/* 167 */     public void setOwner(Object param1Object) { this.owner = param1Object; }
/* 168 */     public Object getOwner() { return this.owner; }
/*     */ 
/*     */     
/* 171 */     public final boolean contains(int param1Int1, int param1Int2) { return (param1Int1 >= position() && position() + size() >= (param1Int1 + param1Int2)); }
/*     */ 
/*     */ 
/*     */     
/* 175 */     public final boolean contained(int param1Int1, int param1Int2) { return (param1Int1 < position() && position() + size() < (param1Int1 + param1Int2)); }
/*     */ 
/*     */ 
/*     */     
/* 179 */     public final boolean overlaps(int param1Int1, int param1Int2) { return (contains(param1Int1, param1Int2) || contained(param1Int1, param1Int2)); }
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\ibex\nestedv\\util\Seekable.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       1.0.4
 */