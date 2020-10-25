/*    */ package com.avaje.ebeaninternal.server.el;
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
/*    */ 
/*    */ public class ElPropertyChainBuilder
/*    */ {
/*    */   private final String expression;
/*    */   private final List<ElPropertyValue> chain;
/*    */   private final boolean embedded;
/*    */   private boolean containsMany;
/*    */   
/*    */   public ElPropertyChainBuilder(boolean embedded, String expression) {
/* 39 */     this.chain = new ArrayList();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 49 */     this.embedded = embedded;
/* 50 */     this.expression = expression;
/*    */   }
/*    */ 
/*    */   
/* 54 */   public boolean isContainsMany() { return this.containsMany; }
/*    */ 
/*    */ 
/*    */   
/* 58 */   public void setContainsMany(boolean containsMany) { this.containsMany = containsMany; }
/*    */ 
/*    */ 
/*    */   
/* 62 */   public String getExpression() { return this.expression; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ElPropertyChainBuilder add(ElPropertyValue element) {
/* 69 */     if (element == null) {
/* 70 */       throw new NullPointerException("element null in expression " + this.expression);
/*    */     }
/* 72 */     this.chain.add(element);
/* 73 */     return this;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 80 */   public ElPropertyChain build() { return new ElPropertyChain(this.containsMany, this.embedded, this.expression, (ElPropertyValue[])this.chain.toArray(new ElPropertyValue[this.chain.size()])); }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\el\ElPropertyChainBuilder.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */