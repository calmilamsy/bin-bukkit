/*    */ package com.avaje.ebeaninternal.server.deploy;
/*    */ 
/*    */ import com.avaje.ebeaninternal.server.core.InternString;
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
/*    */ public class BeanForeignKey
/*    */ {
/*    */   private final String dbColumn;
/*    */   private final int dbType;
/*    */   
/*    */   public BeanForeignKey(String dbColumn, int dbType) {
/* 37 */     this.dbColumn = InternString.intern(dbColumn);
/* 38 */     this.dbType = dbType;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 45 */   public String getDbColumn() { return this.dbColumn; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 52 */   public int getDbType() { return this.dbType; }
/*    */ 
/*    */   
/*    */   public boolean equals(Object obj) {
/* 56 */     if (obj == null) {
/* 57 */       return false;
/*    */     }
/* 59 */     if (obj instanceof BeanForeignKey) {
/* 60 */       return (obj.hashCode() == hashCode());
/*    */     }
/* 62 */     return false;
/*    */   }
/*    */   
/*    */   public int hashCode() {
/* 66 */     hc = getClass().hashCode();
/* 67 */     return hc * 31 + ((this.dbColumn != null) ? this.dbColumn.hashCode() : 0);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/* 72 */   public String toString() { return this.dbColumn; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\deploy\BeanForeignKey.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */