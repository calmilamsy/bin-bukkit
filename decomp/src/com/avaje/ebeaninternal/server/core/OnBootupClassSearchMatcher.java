/*    */ package com.avaje.ebeaninternal.server.core;
/*    */ 
/*    */ import com.avaje.ebeaninternal.server.util.ClassPathSearchMatcher;
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
/*    */ public class OnBootupClassSearchMatcher
/*    */   implements ClassPathSearchMatcher
/*    */ {
/* 30 */   BootupClasses classes = new BootupClasses();
/*    */ 
/*    */ 
/*    */   
/* 34 */   public boolean isMatch(Class<?> cls) { return this.classes.isMatch(cls); }
/*    */ 
/*    */ 
/*    */   
/* 38 */   public BootupClasses getOnBootupClasses() { return this.classes; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\core\OnBootupClassSearchMatcher.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */