/*     */ package com.avaje.ebeaninternal.server.query;
/*     */ 
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanPropertyAssoc;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanPropertyAssocMany;
/*     */ import com.avaje.ebeaninternal.server.deploy.DbReadContext;
/*     */ import com.avaje.ebeaninternal.server.deploy.DbSqlContext;
/*     */ import com.avaje.ebeaninternal.server.deploy.TableJoin;
/*     */ import java.sql.SQLException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SqlTreeNodeExtraJoin
/*     */   implements SqlTreeNode
/*     */ {
/*     */   final BeanPropertyAssoc<?> assocBeanProperty;
/*     */   final String prefix;
/*     */   final boolean manyJoin;
/*     */   List<SqlTreeNodeExtraJoin> children;
/*     */   
/*     */   public SqlTreeNodeExtraJoin(String prefix, BeanPropertyAssoc<?> assocBeanProperty) {
/*  33 */     this.prefix = prefix;
/*  34 */     this.assocBeanProperty = assocBeanProperty;
/*  35 */     this.manyJoin = assocBeanProperty instanceof BeanPropertyAssocMany;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void buildSelectExpressionChain(List<String> selectChain) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  53 */   public boolean isManyJoin() { return this.manyJoin; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  58 */   public String getName() { return this.prefix; }
/*     */ 
/*     */   
/*     */   public void addChild(SqlTreeNodeExtraJoin child) {
/*  62 */     if (this.children == null) {
/*  63 */       this.children = new ArrayList();
/*     */     }
/*  65 */     this.children.add(child);
/*     */   }
/*     */ 
/*     */   
/*     */   public void appendFrom(DbSqlContext ctx, boolean forceOuterJoin) {
/*  70 */     boolean manyToMany = false;
/*     */     
/*  72 */     if (this.assocBeanProperty instanceof BeanPropertyAssocMany) {
/*  73 */       BeanPropertyAssocMany<?> manyProp = (BeanPropertyAssocMany)this.assocBeanProperty;
/*  74 */       if (manyProp.isManyToMany()) {
/*     */         
/*  76 */         manyToMany = true;
/*     */         
/*  78 */         String alias = ctx.getTableAlias(this.prefix);
/*  79 */         String[] split = SplitName.split(this.prefix);
/*  80 */         String parentAlias = ctx.getTableAlias(split[0]);
/*  81 */         String alias2 = alias + "z_";
/*     */         
/*  83 */         TableJoin manyToManyJoin = manyProp.getIntersectionTableJoin();
/*  84 */         manyToManyJoin.addJoin(forceOuterJoin, parentAlias, alias2, ctx);
/*     */         
/*  86 */         this.assocBeanProperty.addJoin(forceOuterJoin, alias2, alias, ctx);
/*     */       } 
/*     */     } 
/*     */     
/*  90 */     if (!manyToMany) {
/*  91 */       this.assocBeanProperty.addJoin(forceOuterJoin, this.prefix, ctx);
/*     */     }
/*     */     
/*  94 */     if (this.children != null) {
/*     */       
/*  96 */       if (this.manyJoin)
/*     */       {
/*  98 */         forceOuterJoin = true;
/*     */       }
/*     */       
/* 101 */       for (int i = 0; i < this.children.size(); i++) {
/* 102 */         SqlTreeNodeExtraJoin child = (SqlTreeNodeExtraJoin)this.children.get(i);
/* 103 */         child.appendFrom(ctx, forceOuterJoin);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void appendSelect(DbSqlContext ctx, boolean subQuery) {}
/*     */   
/*     */   public void appendWhere(DbSqlContext ctx) {}
/*     */   
/*     */   public void load(DbReadContext ctx, Object parentBean, int parentState) throws SQLException {}
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\query\SqlTreeNodeExtraJoin.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */