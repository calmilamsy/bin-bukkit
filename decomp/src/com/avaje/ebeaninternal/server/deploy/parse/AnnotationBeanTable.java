/*    */ package com.avaje.ebeaninternal.server.deploy.parse;
/*    */ 
/*    */ import com.avaje.ebean.config.TableName;
/*    */ import com.avaje.ebeaninternal.server.deploy.meta.DeployBeanTable;
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
/*    */ public class AnnotationBeanTable
/*    */   extends AnnotationBase
/*    */ {
/*    */   final DeployBeanTable beanTable;
/*    */   
/*    */   public AnnotationBeanTable(DeployUtil util, DeployBeanTable beanTable) {
/* 37 */     super(util);
/* 38 */     this.beanTable = beanTable;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void parse() {
/* 46 */     TableName tableName = this.namingConvention.getTableName(this.beanTable.getBeanType());
/*    */     
/* 48 */     this.beanTable.setBaseTable(tableName.getQualifiedName());
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\deploy\parse\AnnotationBeanTable.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */