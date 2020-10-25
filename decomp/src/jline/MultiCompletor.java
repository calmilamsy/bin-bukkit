/*    */ package jline;
/*    */ 
/*    */ import java.util.LinkedList;
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
/*    */ public class MultiCompletor
/*    */   implements Completor
/*    */ {
/*    */   Completor[] completors;
/*    */   
/* 27 */   public MultiCompletor() { this(new Completor[0]); }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 35 */   public MultiCompletor(List completors) { this((Completor[])completors.toArray(new Completor[completors.size()])); }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public MultiCompletor(Completor[] completors) {
/*    */     this.completors = new Completor[0];
/* 43 */     this.completors = completors;
/*    */   }
/*    */   
/*    */   public int complete(String buffer, int pos, List cand) {
/* 47 */     int[] positions = new int[this.completors.length];
/* 48 */     List[] copies = new List[this.completors.length];
/*    */     
/* 50 */     for (i = 0; i < this.completors.length; i++) {
/*    */       
/* 52 */       copies[i] = new LinkedList(cand);
/* 53 */       positions[i] = this.completors[i].complete(buffer, pos, copies[i]);
/*    */     } 
/*    */     
/* 56 */     int maxposition = -1;
/*    */     
/* 58 */     for (i = 0; i < positions.length; i++) {
/* 59 */       maxposition = Math.max(maxposition, positions[i]);
/*    */     }
/*    */ 
/*    */ 
/*    */     
/* 64 */     for (int i = 0; i < copies.length; i++) {
/* 65 */       if (positions[i] == maxposition) {
/* 66 */         cand.addAll(copies[i]);
/*    */       }
/*    */     } 
/*    */     
/* 70 */     return maxposition;
/*    */   }
/*    */ 
/*    */   
/* 74 */   public void setCompletors(Completor[] completors) { this.completors = completors; }
/*    */ 
/*    */ 
/*    */   
/* 78 */   public Completor[] getCompletors() { return this.completors; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\jline\MultiCompletor.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       1.0.4
 */