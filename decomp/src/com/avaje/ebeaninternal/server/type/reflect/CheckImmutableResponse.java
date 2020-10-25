/*    */ package com.avaje.ebeaninternal.server.type.reflect;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CheckImmutableResponse
/*    */ {
/*    */   private boolean immutable = true;
/*    */   private String reasonNotImmutable;
/*    */   private boolean compoundType;
/*    */   
/*    */   public String toString() {
/* 14 */     if (this.immutable) {
/* 15 */       return "immutable";
/*    */     }
/* 17 */     return "not immutable due to:" + this.reasonNotImmutable;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/* 22 */   public boolean isCompoundType() { return this.compoundType; }
/*    */ 
/*    */ 
/*    */   
/* 26 */   public void setCompoundType(boolean compoundType) { this.compoundType = compoundType; }
/*    */ 
/*    */ 
/*    */   
/* 30 */   public String getReasonNotImmutable() { return this.reasonNotImmutable; }
/*    */ 
/*    */   
/*    */   public void setReasonNotImmutable(String error) {
/* 34 */     this.immutable = false;
/* 35 */     this.reasonNotImmutable = error;
/*    */   }
/*    */ 
/*    */   
/* 39 */   public boolean isImmutable() { return this.immutable; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\type\reflect\CheckImmutableResponse.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */