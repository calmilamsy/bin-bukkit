/*     */ package com.avaje.ebeaninternal.server.cluster.socket;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.net.Socket;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ class SocketConnection
/*     */ {
/*     */   ObjectOutputStream oos;
/*     */   ObjectInputStream ois;
/*     */   InputStream is;
/*     */   OutputStream os;
/*     */   Socket socket;
/*     */   
/*     */   public SocketConnection(Socket socket) throws IOException {
/*  61 */     this.is = socket.getInputStream();
/*  62 */     this.os = socket.getOutputStream();
/*  63 */     this.socket = socket;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void disconnect() throws IOException {
/*  70 */     this.os.flush();
/*  71 */     this.socket.close();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  78 */   public void flush() throws IOException { this.os.flush(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  85 */   public Object readObject() throws IOException, ClassNotFoundException { return getObjectInputStream().readObject(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ObjectOutputStream writeObject(Object object) throws IOException {
/*  92 */     ObjectOutputStream oos = getObjectOutputStream();
/*  93 */     oos.writeObject(object);
/*  94 */     return oos;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ObjectOutputStream getObjectOutputStream() throws IOException {
/* 101 */     if (this.oos == null) {
/* 102 */       this.oos = new ObjectOutputStream(this.os);
/*     */     }
/* 104 */     return this.oos;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ObjectInputStream getObjectInputStream() throws IOException {
/* 111 */     if (this.ois == null) {
/* 112 */       this.ois = new ObjectInputStream(this.is);
/*     */     }
/* 114 */     return this.ois;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 122 */   public void setObjectInputStream(ObjectInputStream ois) { this.ois = ois; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 129 */   public void setObjectOutputStream(ObjectOutputStream oos) { this.oos = oos; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 136 */   public InputStream getInputStream() throws IOException { return this.is; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 143 */   public OutputStream getOutputStream() throws IOException { return this.os; }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\cluster\socket\SocketConnection.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */