/*    */ package com.avaje.ebeaninternal.server.deploy;
/*    */ 
/*    */ import com.avaje.ebean.annotation.NamedUpdate;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DeployNamedUpdate
/*    */ {
/*    */   private final String name;
/*    */   private final String updateStatement;
/*    */   private final boolean notifyCache;
/*    */   private String sqlUpdateStatement;
/*    */   
/*    */   public DeployNamedUpdate(NamedUpdate update) {
/* 19 */     this.name = update.name();
/* 20 */     this.updateStatement = update.update();
/* 21 */     this.notifyCache = update.notifyCache();
/*    */   }
/*    */ 
/*    */   
/* 25 */   public void initialise(DeployUpdateParser parser) { this.sqlUpdateStatement = parser.parse(this.updateStatement); }
/*    */ 
/*    */ 
/*    */   
/* 29 */   public String getName() { return this.name; }
/*    */ 
/*    */ 
/*    */   
/* 33 */   public String getSqlUpdateStatement() { return this.sqlUpdateStatement; }
/*    */ 
/*    */ 
/*    */   
/* 37 */   public boolean isNotifyCache() { return this.notifyCache; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\deploy\DeployNamedUpdate.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */