/*     */ package com.avaje.ebeaninternal.server.transaction;
/*     */ 
/*     */ import com.avaje.ebeaninternal.api.SpiTransaction;
/*     */ import java.util.HashMap;
/*     */ import javax.persistence.PersistenceException;
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
/*     */ public class TransactionMap
/*     */ {
/*  37 */   HashMap<String, State> map = new HashMap();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public State getState(String serverName) {
/*  44 */     State state = (State)this.map.get(serverName);
/*  45 */     if (state == null) {
/*  46 */       state = new State();
/*  47 */       this.map.put(serverName, state);
/*     */     } 
/*  49 */     return state;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static class State
/*     */   {
/*     */     SpiTransaction transaction;
/*     */ 
/*     */ 
/*     */     
/*  60 */     public SpiTransaction get() { return this.transaction; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void set(SpiTransaction trans) {
/*  68 */       if (this.transaction != null && this.transaction.isActive()) {
/*  69 */         String m = "The existing transaction is still active?";
/*  70 */         throw new PersistenceException(m);
/*     */       } 
/*  72 */       this.transaction = trans;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void commit() {
/*  80 */       this.transaction.commit();
/*  81 */       this.transaction = null;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void rollback() {
/*  88 */       this.transaction.rollback();
/*  89 */       this.transaction = null;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void end() {
/*  96 */       if (this.transaction != null) {
/*  97 */         this.transaction.end();
/*  98 */         this.transaction = null;
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 106 */     public void replace(SpiTransaction trans) { this.transaction = trans; }
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\transaction\TransactionMap.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */