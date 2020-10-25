/*    */ package com.avaje.ebeaninternal.server.deploy.parse;
/*    */ 
/*    */ import com.avaje.ebeaninternal.server.deploy.meta.DeployBeanDescriptor;
/*    */ import com.avaje.ebeaninternal.server.deploy.meta.DeployBeanPropertyAssocOne;
/*    */ import com.avaje.ebeaninternal.server.deploy.meta.DeployTableJoin;
/*    */ import java.util.HashMap;
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
/*    */ public class DeployBeanInfo<T>
/*    */   extends Object
/*    */ {
/*    */   private final HashMap<String, DeployTableJoin> tableJoinMap;
/*    */   private final DeployUtil util;
/*    */   private final DeployBeanDescriptor<T> descriptor;
/*    */   
/*    */   public DeployBeanInfo(DeployUtil util, DeployBeanDescriptor<T> descriptor) {
/* 37 */     this.tableJoinMap = new HashMap();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 47 */     this.util = util;
/* 48 */     this.descriptor = descriptor;
/*    */   }
/*    */ 
/*    */   
/* 52 */   public String toString() { return "" + this.descriptor; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 59 */   public DeployBeanDescriptor<T> getDescriptor() { return this.descriptor; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 66 */   public DeployUtil getUtil() { return this.util; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public DeployTableJoin getTableJoin(String tableName) {
/* 74 */     String key = tableName.toLowerCase();
/*    */     
/* 76 */     DeployTableJoin tableJoin = (DeployTableJoin)this.tableJoinMap.get(key);
/* 77 */     if (tableJoin == null) {
/* 78 */       tableJoin = new DeployTableJoin();
/* 79 */       tableJoin.setTable(tableName);
/* 80 */       tableJoin.setType("join");
/* 81 */       this.descriptor.addTableJoin(tableJoin);
/*    */       
/* 83 */       this.tableJoinMap.put(key, tableJoin);
/*    */     } 
/* 85 */     return tableJoin;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setBeanJoinType(DeployBeanPropertyAssocOne<?> beanProp, boolean outerJoin) {
/* 93 */     String joinType = "join";
/* 94 */     if (outerJoin) {
/* 95 */       joinType = "left outer join";
/*    */     }
/*    */     
/* 98 */     DeployTableJoin tableJoin = beanProp.getTableJoin();
/* 99 */     tableJoin.setType(joinType);
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\deploy\parse\DeployBeanInfo.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */