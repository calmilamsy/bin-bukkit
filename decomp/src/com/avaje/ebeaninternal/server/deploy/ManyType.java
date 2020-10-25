/*    */ package com.avaje.ebeaninternal.server.deploy;
/*    */ 
/*    */ import com.avaje.ebean.Query;
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
/*    */ public class ManyType
/*    */ {
/* 29 */   public static final ManyType JAVA_LIST = new ManyType(Underlying.LIST);
/* 30 */   public static final ManyType JAVA_SET = new ManyType(Underlying.SET); private final Query.Type queryType; private final Underlying underlying;
/* 31 */   public static final ManyType JAVA_MAP = new ManyType(Underlying.MAP);
/*    */   private final CollectionTypeConverter typeConverter;
/*    */   
/* 34 */   public enum Underlying { LIST,
/* 35 */     SET,
/* 36 */     MAP; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 46 */   private ManyType(Underlying underlying) { this(underlying, null); }
/*    */ 
/*    */   
/*    */   public ManyType(Underlying underlying, CollectionTypeConverter typeConverter) {
/* 50 */     this.underlying = underlying;
/* 51 */     this.typeConverter = typeConverter;
/* 52 */     switch (underlying) {
/*    */       case LIST:
/* 54 */         this.queryType = Query.Type.LIST;
/*    */         return;
/*    */       case SET:
/* 57 */         this.queryType = Query.Type.SET;
/*    */         return;
/*    */     } 
/*    */     
/* 61 */     this.queryType = Query.Type.MAP;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 70 */   public Query.Type getQueryType() { return this.queryType; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 77 */   public Underlying getUnderlying() { return this.underlying; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 84 */   public CollectionTypeConverter getTypeConverter() { return this.typeConverter; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\deploy\ManyType.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */