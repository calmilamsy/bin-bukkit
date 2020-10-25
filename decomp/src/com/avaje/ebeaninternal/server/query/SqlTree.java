/*     */ package com.avaje.ebeaninternal.server.query;
/*     */ 
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanProperty;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanPropertyAssocMany;
/*     */ import com.avaje.ebeaninternal.server.el.ElPropertyValue;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Set;
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
/*     */ public class SqlTree
/*     */ {
/*     */   private SqlTreeNode rootNode;
/*     */   private BeanPropertyAssocMany<?> manyProperty;
/*     */   private String manyPropertyName;
/*     */   private ElPropertyValue manyPropEl;
/*     */   private Set<String> includes;
/*     */   private String summary;
/*     */   private String selectSql;
/*     */   private String fromSql;
/*     */   private BeanProperty[] encryptedProps;
/*     */   private String inheritanceWhereSql;
/*     */   
/*     */   public List<String> buildSelectExpressionChain() {
/*  74 */     ArrayList<String> list = new ArrayList<String>();
/*  75 */     this.rootNode.buildSelectExpressionChain(list);
/*  76 */     return list;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  83 */   public Set<String> getIncludes() { return this.includes; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  90 */   public void setIncludes(Set<String> includes) { this.includes = includes; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setManyProperty(BeanPropertyAssocMany<?> manyProperty, String manyPropertyName, ElPropertyValue manyPropEl) {
/*  97 */     this.manyProperty = manyProperty;
/*  98 */     this.manyPropertyName = manyPropertyName;
/*  99 */     this.manyPropEl = manyPropEl;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 106 */   public String getSelectSql() { return this.selectSql; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 113 */   public void setSelectSql(String selectSql) { this.selectSql = selectSql; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 118 */   public String getFromSql() { return this.fromSql; }
/*     */ 
/*     */ 
/*     */   
/* 122 */   public void setFromSql(String fromSql) { this.fromSql = fromSql; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 129 */   public String getInheritanceWhereSql() { return this.inheritanceWhereSql; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 136 */   public void setInheritanceWhereSql(String whereSql) { this.inheritanceWhereSql = whereSql; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 143 */   public void setSummary(String summary) { this.summary = summary; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 150 */   public String getSummary() { return this.summary; }
/*     */ 
/*     */ 
/*     */   
/* 154 */   public SqlTreeNode getRootNode() { return this.rootNode; }
/*     */ 
/*     */ 
/*     */   
/* 158 */   public void setRootNode(SqlTreeNode rootNode) { this.rootNode = rootNode; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 166 */   public BeanPropertyAssocMany<?> getManyProperty() { return this.manyProperty; }
/*     */ 
/*     */ 
/*     */   
/* 170 */   public String getManyPropertyName() { return this.manyPropertyName; }
/*     */ 
/*     */ 
/*     */   
/* 174 */   public ElPropertyValue getManyPropertyEl() { return this.manyPropEl; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 181 */   public boolean isManyIncluded() { return (this.manyProperty != null); }
/*     */ 
/*     */ 
/*     */   
/* 185 */   public BeanProperty[] getEncryptedProps() { return this.encryptedProps; }
/*     */ 
/*     */ 
/*     */   
/* 189 */   public void setEncryptedProps(BeanProperty[] encryptedProps) { this.encryptedProps = encryptedProps; }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\query\SqlTree.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */