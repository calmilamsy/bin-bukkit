/*    */ package com.avaje.ebean.common;
/*    */ 
/*    */ import com.avaje.ebean.bean.BeanCollection;
/*    */ import java.util.Set;
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
/*    */ class ModifySet<E>
/*    */   extends ModifyCollection<E>
/*    */   implements Set<E>
/*    */ {
/* 41 */   public ModifySet(BeanCollection<E> owner, Set<E> s) { super(owner, s); }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebean\common\ModifySet.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */