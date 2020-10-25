/*    */ package com.avaje.ebeaninternal.server.expression;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class FilterExprPath
/*    */   implements Serializable
/*    */ {
/*    */   private static final long serialVersionUID = -6420905565372842018L;
/*    */   private String path;
/*    */   
/* 43 */   public FilterExprPath(String path) { this.path = path; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 51 */   public void trimPath(int prefixTrim) { this.path = this.path.substring(prefixTrim); }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 58 */   public String getPath() { return this.path; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\expression\FilterExprPath.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */