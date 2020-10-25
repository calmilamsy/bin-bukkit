/*    */ package com.avaje.ebeaninternal.server.deploy;
/*    */ 
/*    */ import com.avaje.ebean.bean.PersistenceContext;
/*    */ import com.avaje.ebeaninternal.server.transaction.DefaultPersistenceContext;
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
/*    */ public class CopyContext
/*    */ {
/*    */   private final boolean vanillaMode;
/*    */   private final boolean sharing;
/*    */   private final PersistenceContext pc;
/*    */   
/*    */   public CopyContext(boolean vanillaMode, boolean sharing) {
/* 39 */     this.vanillaMode = vanillaMode;
/* 40 */     this.sharing = sharing;
/* 41 */     this.pc = new DefaultPersistenceContext();
/*    */   }
/*    */ 
/*    */   
/* 45 */   public CopyContext(boolean vanillaMode) { this(vanillaMode, false); }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 52 */   public boolean isVanillaMode() { return this.vanillaMode; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 59 */   public boolean isSharing() { return this.sharing; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 66 */   public PersistenceContext getPersistenceContext() { return this.pc; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 73 */   public Object putIfAbsent(Object id, Object bean) { return this.pc.putIfAbsent(id, bean); }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 80 */   public Object get(Class<?> beanType, Object beanId) { return this.pc.get(beanType, beanId); }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\deploy\CopyContext.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */