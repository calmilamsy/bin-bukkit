/*    */ package org.yaml.snakeyaml.events;
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
/*    */ public class ImplicitTuple
/*    */ {
/*    */   private final boolean plain;
/*    */   private final boolean nonPlain;
/*    */   
/*    */   public ImplicitTuple(boolean plain, boolean nonplain) {
/* 32 */     this.plain = plain;
/* 33 */     this.nonPlain = nonplain;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 41 */   public boolean isFirst() { return this.plain; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 49 */   public boolean isSecond() { return this.nonPlain; }
/*    */ 
/*    */ 
/*    */   
/* 53 */   public boolean bothFalse() { return (!this.plain && !this.nonPlain); }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 58 */   public String toString() { return "implicit=[" + this.plain + ", " + this.nonPlain + "]"; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\yaml\snakeyaml\events\ImplicitTuple.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */