/*    */ package jline;
/*    */ 
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
/*    */ public class NullCompletor
/*    */   implements Completor
/*    */ {
/* 25 */   public int complete(String buffer, int cursor, List candidates) { return -1; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\jline\NullCompletor.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       1.0.4
 */