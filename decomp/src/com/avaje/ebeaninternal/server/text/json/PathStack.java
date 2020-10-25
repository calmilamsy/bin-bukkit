/*    */ package com.avaje.ebeaninternal.server.text.json;
/*    */ 
/*    */ import com.avaje.ebeaninternal.server.util.ArrayStack;
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
/*    */ public class PathStack
/*    */   extends ArrayStack<String>
/*    */ {
/*    */   public String peekFullPath(String key) {
/* 28 */     String prefix = (String)peekWithNull();
/* 29 */     if (prefix != null) {
/* 30 */       return prefix + "." + key;
/*    */     }
/* 32 */     return key;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void pushPathKey(String key) {
/* 38 */     String prefix = (String)peekWithNull();
/* 39 */     if (prefix != null) {
/* 40 */       key = prefix + "." + key;
/*    */     }
/* 42 */     push(key);
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\text\json\PathStack.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */