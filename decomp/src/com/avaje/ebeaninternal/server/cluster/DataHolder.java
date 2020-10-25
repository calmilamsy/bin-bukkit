/*    */ package com.avaje.ebeaninternal.server.cluster;
/*    */ 
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
/*    */ 
/*    */ public class DataHolder
/*    */   implements Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 9090748723571322192L;
/*    */   private final byte[] data;
/*    */   
/* 36 */   public DataHolder(byte[] data) { this.data = data; }
/*    */ 
/*    */ 
/*    */   
/* 40 */   public byte[] getData() { return this.data; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\cluster\DataHolder.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */