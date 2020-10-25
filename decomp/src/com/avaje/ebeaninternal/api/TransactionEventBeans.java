/*    */ package com.avaje.ebeaninternal.api;
/*    */ 
/*    */ import com.avaje.ebeaninternal.server.core.PersistRequestBean;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TransactionEventBeans
/*    */ {
/* 36 */   ArrayList<PersistRequestBean<?>> requests = new ArrayList();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 42 */   public List<PersistRequestBean<?>> getRequests() { return this.requests; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 50 */   public void add(PersistRequestBean<?> request) { this.requests.add(request); }
/*    */ 
/*    */   
/*    */   public void notifyCache() {
/* 54 */     for (int i = 0; i < this.requests.size(); i++)
/* 55 */       ((PersistRequestBean)this.requests.get(i)).notifyCache(); 
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\api\TransactionEventBeans.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */