/*    */ package com.avaje.ebeaninternal.server.query;
/*    */ 
/*    */ import com.avaje.ebeaninternal.server.deploy.BeanPropertyAssoc;
/*    */ import com.avaje.ebeaninternal.server.deploy.BeanPropertyAssocMany;
/*    */ import com.avaje.ebeaninternal.server.deploy.DbReadContext;
/*    */ import com.avaje.ebeaninternal.server.deploy.DbSqlContext;
/*    */ import com.avaje.ebeaninternal.server.deploy.TableJoin;
/*    */ import java.sql.SQLException;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
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
/*    */ public class SqlTreeNodeManyWhereJoin
/*    */   implements SqlTreeNode
/*    */ {
/*    */   private final String parentPrefix;
/*    */   private final String prefix;
/*    */   private final BeanPropertyAssoc<?> nodeBeanProp;
/*    */   private final SqlTreeNode[] children;
/*    */   
/*    */   public SqlTreeNodeManyWhereJoin(String prefix, BeanPropertyAssoc<?> prop) {
/* 47 */     this.nodeBeanProp = prop;
/* 48 */     this.prefix = prefix;
/*    */     
/* 50 */     String[] split = SplitName.split(prefix);
/* 51 */     this.parentPrefix = split[0];
/*    */     
/* 53 */     List<SqlTreeNode> childrenList = new ArrayList<SqlTreeNode>(false);
/* 54 */     this.children = (SqlTreeNode[])childrenList.toArray(new SqlTreeNode[childrenList.size()]);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void appendFrom(DbSqlContext ctx, boolean forceOuterJoin) {
/* 62 */     appendFromBaseTable(ctx, forceOuterJoin);
/*    */     
/* 64 */     for (int i = 0; i < this.children.length; i++) {
/* 65 */       this.children[i].appendFrom(ctx, forceOuterJoin);
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void appendFromBaseTable(DbSqlContext ctx, boolean forceOuterJoin) {
/* 75 */     String alias = ctx.getTableAliasManyWhere(this.prefix);
/* 76 */     String parentAlias = ctx.getTableAliasManyWhere(this.parentPrefix);
/*    */     
/* 78 */     if (this.nodeBeanProp instanceof com.avaje.ebeaninternal.server.deploy.BeanPropertyAssocOne) {
/* 79 */       this.nodeBeanProp.addInnerJoin(parentAlias, alias, ctx);
/*    */     } else {
/*    */       
/* 82 */       BeanPropertyAssocMany<?> manyProp = (BeanPropertyAssocMany)this.nodeBeanProp;
/* 83 */       if (!manyProp.isManyToMany()) {
/* 84 */         manyProp.addInnerJoin(parentAlias, alias, ctx);
/*    */       } else {
/*    */         
/* 87 */         String alias2 = alias + "z_";
/*    */         
/* 89 */         TableJoin manyToManyJoin = manyProp.getIntersectionTableJoin();
/* 90 */         manyToManyJoin.addInnerJoin(parentAlias, alias2, ctx);
/* 91 */         manyProp.addInnerJoin(alias2, alias, ctx);
/*    */       } 
/*    */     } 
/*    */   }
/*    */   
/*    */   public void buildSelectExpressionChain(List<String> selectChain) {}
/*    */   
/*    */   public void appendSelect(DbSqlContext ctx, boolean subQuery) {}
/*    */   
/*    */   public void appendWhere(DbSqlContext ctx) {}
/*    */   
/*    */   public void load(DbReadContext ctx, Object parentBean, int parentState) throws SQLException {}
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\query\SqlTreeNodeManyWhereJoin.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */