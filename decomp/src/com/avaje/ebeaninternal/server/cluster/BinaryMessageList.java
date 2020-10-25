/*    */ package com.avaje.ebeaninternal.server.cluster;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
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
/*    */ public class BinaryMessageList
/*    */ {
/* 32 */   ArrayList<BinaryMessage> list = new ArrayList();
/*    */ 
/*    */   
/* 35 */   public void add(BinaryMessage msg) { this.list.add(msg); }
/*    */ 
/*    */ 
/*    */   
/* 39 */   public List<BinaryMessage> getList() { return this.list; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\cluster\BinaryMessageList.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */