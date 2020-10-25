/*    */ package com.avaje.ebeaninternal.server.deploy;
/*    */ 
/*    */ import com.avaje.ebeaninternal.server.core.InternString;
/*    */ import com.avaje.ebeaninternal.server.deploy.meta.DeployTableJoinColumn;
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
/*    */ 
/*    */ 
/*    */ public class TableJoinColumn
/*    */ {
/*    */   private final String localDbColumn;
/*    */   private final String foreignDbColumn;
/*    */   private final boolean insertable;
/*    */   private final boolean updateable;
/*    */   
/*    */   public TableJoinColumn(DeployTableJoinColumn deploy) {
/* 48 */     this.localDbColumn = InternString.intern(deploy.getLocalDbColumn());
/* 49 */     this.foreignDbColumn = InternString.intern(deploy.getForeignDbColumn());
/* 50 */     this.insertable = deploy.isInsertable();
/* 51 */     this.updateable = deploy.isUpdateable();
/*    */   }
/*    */ 
/*    */   
/* 55 */   public String toString() { return this.localDbColumn + " = " + this.foreignDbColumn; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 63 */   public String getForeignDbColumn() { return this.foreignDbColumn; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 70 */   public String getLocalDbColumn() { return this.localDbColumn; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 77 */   public boolean isInsertable() { return this.insertable; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 84 */   public boolean isUpdateable() { return this.updateable; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\deploy\TableJoinColumn.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */