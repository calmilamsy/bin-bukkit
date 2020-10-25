/*    */ package com.avaje.ebeaninternal.server.query;
/*    */ 
/*    */ import com.avaje.ebeaninternal.server.deploy.BeanPropertyAssocMany;
/*    */ import com.avaje.ebeaninternal.server.deploy.DbReadContext;
/*    */ import com.avaje.ebeaninternal.server.deploy.DbSqlContext;
/*    */ import java.sql.SQLException;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ public final class SqlTreeNodeManyRoot
/*    */   extends SqlTreeNodeBean
/*    */ {
/* 13 */   public SqlTreeNodeManyRoot(String prefix, BeanPropertyAssocMany<?> prop, SqlTreeProperties props, List<SqlTreeNode> myList) { super(prefix, prop, prop.getTargetDescriptor(), props, myList, true); }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 21 */   protected void postLoad(DbReadContext cquery, Object loadedBean, Object id) { cquery.setLoadedManyBean(loadedBean); }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 29 */   public void load(DbReadContext cquery, Object parentBean, int parentState) throws SQLException { super.load(cquery, null, parentState); }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 37 */   public void appendFrom(DbSqlContext ctx, boolean forceOuterJoin) { super.appendFrom(ctx, true); }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\query\SqlTreeNodeManyRoot.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */