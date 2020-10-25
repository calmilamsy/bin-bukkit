/*    */ package com.avaje.ebeaninternal.server.cluster.socket;
/*    */ 
/*    */ import com.avaje.ebeaninternal.server.cluster.DataHolder;
/*    */ import com.avaje.ebeaninternal.server.cluster.Packet;
/*    */ import java.io.Serializable;
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
/*    */ public class SocketClusterMessage
/*    */   implements Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 2993350408394934473L;
/*    */   private final String registerHost;
/*    */   private final boolean register;
/*    */   private final DataHolder dataHolder;
/*    */   
/* 39 */   public static SocketClusterMessage register(String registerHost, boolean register) { return new SocketClusterMessage(registerHost, register); }
/*    */ 
/*    */ 
/*    */   
/* 43 */   public static SocketClusterMessage transEvent(DataHolder transEvent) { return new SocketClusterMessage(transEvent); }
/*    */ 
/*    */   
/*    */   public static SocketClusterMessage packet(Packet packet) {
/* 47 */     DataHolder d = new DataHolder(packet.getBytes());
/* 48 */     return new SocketClusterMessage(d);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private SocketClusterMessage(String registerHost, boolean register) {
/* 55 */     this.registerHost = registerHost;
/* 56 */     this.register = register;
/* 57 */     this.dataHolder = null;
/*    */   }
/*    */   
/*    */   private SocketClusterMessage(DataHolder dataHolder) {
/* 61 */     this.dataHolder = dataHolder;
/* 62 */     this.registerHost = null;
/* 63 */     this.register = false;
/*    */   }
/*    */   
/*    */   public String toString() {
/* 67 */     StringBuilder sb = new StringBuilder();
/* 68 */     if (this.registerHost != null) {
/* 69 */       sb.append("register ");
/* 70 */       sb.append(this.register);
/* 71 */       sb.append(" ");
/* 72 */       sb.append(this.registerHost);
/*    */     } else {
/* 74 */       sb.append("transEvent ");
/*    */     } 
/* 76 */     return sb.toString();
/*    */   }
/*    */ 
/*    */   
/* 80 */   public boolean isRegisterEvent() { return (this.registerHost != null); }
/*    */ 
/*    */ 
/*    */   
/* 84 */   public String getRegisterHost() { return this.registerHost; }
/*    */ 
/*    */ 
/*    */   
/* 88 */   public boolean isRegister() { return this.register; }
/*    */ 
/*    */ 
/*    */   
/* 92 */   public DataHolder getDataHolder() { return this.dataHolder; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\cluster\socket\SocketClusterMessage.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */