/*    */ package com.avaje.ebeaninternal.api;
/*    */ 
/*    */ import com.avaje.ebean.Update;
/*    */ 
/*    */ public interface SpiUpdate<T> extends Update<T> {
/*    */   Class<?> getBeanType();
/*    */   
/*    */   OrmUpdateType getOrmUpdateType();
/*    */   
/*    */   String getBaseTable();
/*    */   
/*    */   String getUpdateStatement();
/*    */   
/*    */   int getTimeout();
/*    */   
/*    */   boolean isNotifyCache();
/*    */   
/*    */   BindParams getBindParams();
/*    */   
/*    */   void setGeneratedSql(String paramString);
/*    */   
/*    */   public final enum OrmUpdateType {
/*    */     INSERT, UPDATE, DELETE, UNKNOWN;
/*    */     
/*    */     static  {
/*    */       // Byte code:
/*    */       //   0: new com/avaje/ebeaninternal/api/SpiUpdate$OrmUpdateType$1
/*    */       //   3: dup
/*    */       //   4: ldc 'INSERT'
/*    */       //   6: iconst_0
/*    */       //   7: invokespecial <init> : (Ljava/lang/String;I)V
/*    */       //   10: putstatic com/avaje/ebeaninternal/api/SpiUpdate$OrmUpdateType.INSERT : Lcom/avaje/ebeaninternal/api/SpiUpdate$OrmUpdateType;
/*    */       //   13: new com/avaje/ebeaninternal/api/SpiUpdate$OrmUpdateType$2
/*    */       //   16: dup
/*    */       //   17: ldc 'UPDATE'
/*    */       //   19: iconst_1
/*    */       //   20: invokespecial <init> : (Ljava/lang/String;I)V
/*    */       //   23: putstatic com/avaje/ebeaninternal/api/SpiUpdate$OrmUpdateType.UPDATE : Lcom/avaje/ebeaninternal/api/SpiUpdate$OrmUpdateType;
/*    */       //   26: new com/avaje/ebeaninternal/api/SpiUpdate$OrmUpdateType$3
/*    */       //   29: dup
/*    */       //   30: ldc 'DELETE'
/*    */       //   32: iconst_2
/*    */       //   33: invokespecial <init> : (Ljava/lang/String;I)V
/*    */       //   36: putstatic com/avaje/ebeaninternal/api/SpiUpdate$OrmUpdateType.DELETE : Lcom/avaje/ebeaninternal/api/SpiUpdate$OrmUpdateType;
/*    */       //   39: new com/avaje/ebeaninternal/api/SpiUpdate$OrmUpdateType$4
/*    */       //   42: dup
/*    */       //   43: ldc 'UNKNOWN'
/*    */       //   45: iconst_3
/*    */       //   46: invokespecial <init> : (Ljava/lang/String;I)V
/*    */       //   49: putstatic com/avaje/ebeaninternal/api/SpiUpdate$OrmUpdateType.UNKNOWN : Lcom/avaje/ebeaninternal/api/SpiUpdate$OrmUpdateType;
/*    */       //   52: iconst_4
/*    */       //   53: anewarray com/avaje/ebeaninternal/api/SpiUpdate$OrmUpdateType
/*    */       //   56: dup
/*    */       //   57: iconst_0
/*    */       //   58: getstatic com/avaje/ebeaninternal/api/SpiUpdate$OrmUpdateType.INSERT : Lcom/avaje/ebeaninternal/api/SpiUpdate$OrmUpdateType;
/*    */       //   61: aastore
/*    */       //   62: dup
/*    */       //   63: iconst_1
/*    */       //   64: getstatic com/avaje/ebeaninternal/api/SpiUpdate$OrmUpdateType.UPDATE : Lcom/avaje/ebeaninternal/api/SpiUpdate$OrmUpdateType;
/*    */       //   67: aastore
/*    */       //   68: dup
/*    */       //   69: iconst_2
/*    */       //   70: getstatic com/avaje/ebeaninternal/api/SpiUpdate$OrmUpdateType.DELETE : Lcom/avaje/ebeaninternal/api/SpiUpdate$OrmUpdateType;
/*    */       //   73: aastore
/*    */       //   74: dup
/*    */       //   75: iconst_3
/*    */       //   76: getstatic com/avaje/ebeaninternal/api/SpiUpdate$OrmUpdateType.UNKNOWN : Lcom/avaje/ebeaninternal/api/SpiUpdate$OrmUpdateType;
/*    */       //   79: aastore
/*    */       //   80: putstatic com/avaje/ebeaninternal/api/SpiUpdate$OrmUpdateType.$VALUES : [Lcom/avaje/ebeaninternal/api/SpiUpdate$OrmUpdateType;
/*    */       //   83: return
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #14	-> 0
/*    */       //   #19	-> 13
/*    */       //   #24	-> 26
/*    */       //   #29	-> 39
/*    */       //   #13	-> 52
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\api\SpiUpdate.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */