/*     */ package com.avaje.ebeaninternal.server.deploy;
/*     */ 
/*     */ import com.avaje.ebeaninternal.server.core.OrmQueryRequest;
/*     */ import com.avaje.ebeaninternal.server.query.CQueryPredicates;
/*     */ import com.avaje.ebeaninternal.server.query.SqlTree;
/*     */ import com.avaje.ebeaninternal.server.query.SqlTreeNodeRoot;
/*     */ import com.avaje.ebeaninternal.server.query.SqlTreeProperties;
/*     */ import java.util.Arrays;
/*     */ import java.util.HashMap;
/*     */ import java.util.LinkedHashSet;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.persistence.PersistenceException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DRawSqlSelect
/*     */ {
/*  25 */   private static final Logger logger = Logger.getLogger(DRawSqlSelect.class.getName());
/*     */ 
/*     */   
/*     */   private final BeanDescriptor<?> desc;
/*     */ 
/*     */   
/*     */   private final DRawSqlColumnInfo[] selectColumns;
/*     */   
/*     */   private final Map<String, DRawSqlColumnInfo> columnMap;
/*     */   
/*     */   private final String preWhereExprSql;
/*     */   
/*     */   private final boolean andWhereExpr;
/*     */   
/*     */   private final String preHavingExprSql;
/*     */   
/*     */   private final boolean andHavingExpr;
/*     */   
/*     */   private final String orderBySql;
/*     */   
/*     */   private final String whereClause;
/*     */   
/*     */   private final String havingClause;
/*     */   
/*     */   private final String query;
/*     */   
/*     */   private final String columnMapping;
/*     */   
/*     */   private final String name;
/*     */   
/*     */   private final SqlTree sqlTree;
/*     */   
/*     */   private boolean withId;
/*     */   
/*     */   private final String tableAlias;
/*     */ 
/*     */   
/*     */   public DRawSqlSelect(BeanDescriptor<?> desc, List<DRawSqlColumnInfo> selectColumns, String tableAlias, String preWhereExprSql, boolean andWhereExpr, String preHavingExprSql, boolean andHavingExpr, String orderBySql, DRawSqlMeta meta) {
/*  63 */     this.desc = desc;
/*  64 */     this.tableAlias = tableAlias;
/*  65 */     this.selectColumns = (DRawSqlColumnInfo[])selectColumns.toArray(new DRawSqlColumnInfo[selectColumns.size()]);
/*  66 */     this.preHavingExprSql = preHavingExprSql;
/*  67 */     this.preWhereExprSql = preWhereExprSql;
/*  68 */     this.andHavingExpr = andHavingExpr;
/*  69 */     this.andWhereExpr = andWhereExpr;
/*  70 */     this.orderBySql = orderBySql;
/*  71 */     this.name = meta.getName();
/*  72 */     this.whereClause = meta.getWhere();
/*  73 */     this.havingClause = meta.getHaving();
/*  74 */     this.query = meta.getQuery();
/*  75 */     this.columnMapping = meta.getColumnMapping();
/*     */     
/*  77 */     this.sqlTree = initialise(desc);
/*  78 */     this.columnMap = createColumnMap(this.selectColumns);
/*     */   }
/*     */ 
/*     */   
/*     */   private Map<String, DRawSqlColumnInfo> createColumnMap(DRawSqlColumnInfo[] selectColumns) {
/*  83 */     HashMap<String, DRawSqlColumnInfo> m = new HashMap<String, DRawSqlColumnInfo>();
/*  84 */     for (int i = 0; i < selectColumns.length; i++) {
/*  85 */       m.put(selectColumns[i].getPropertyName(), selectColumns[i]);
/*     */     }
/*     */     
/*  88 */     return m;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private SqlTree initialise(BeanDescriptor<?> owner) {
/*     */     try {
/*  97 */       return buildSqlTree(owner);
/*     */     }
/*  99 */     catch (Exception e) {
/* 100 */       String m = "Bug? initialising query " + this.name + " on " + owner;
/* 101 */       throw new RuntimeException(m, e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 109 */   public DRawSqlColumnInfo getRawSqlColumnInfo(String propertyName) { return (DRawSqlColumnInfo)this.columnMap.get(propertyName); }
/*     */ 
/*     */ 
/*     */   
/* 113 */   public String getTableAlias() { return this.tableAlias; }
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
/*     */   private SqlTree buildSqlTree(BeanDescriptor<?> desc) {
/* 126 */     SqlTree sqlTree = new SqlTree();
/* 127 */     sqlTree.setSummary(desc.getName());
/*     */     
/* 129 */     LinkedHashSet<String> includedProps = new LinkedHashSet<String>();
/* 130 */     SqlTreeProperties selectProps = new SqlTreeProperties();
/*     */     
/* 132 */     for (int i = 0; i < this.selectColumns.length; i++) {
/*     */       
/* 134 */       DRawSqlColumnInfo columnInfo = this.selectColumns[i];
/* 135 */       String propName = columnInfo.getPropertyName();
/* 136 */       BeanProperty beanProperty = desc.getBeanProperty(propName);
/* 137 */       if (beanProperty != null) {
/* 138 */         if (beanProperty.isId()) {
/* 139 */           if (i > 0) {
/* 140 */             String m = "With " + desc + " query:" + this.name + " the ID is not the first column in the select. It must be...";
/* 141 */             throw new PersistenceException(m);
/*     */           } 
/* 143 */           this.withId = true;
/*     */         } else {
/*     */           
/* 146 */           includedProps.add(beanProperty.getName());
/* 147 */           selectProps.add(beanProperty);
/*     */         }
/*     */       
/*     */       } else {
/*     */         
/* 152 */         String m = "Mapping for " + desc.getFullName();
/* 153 */         m = m + " query[" + this.name + "] column[" + columnInfo + "] index[" + i;
/* 154 */         m = m + "] not matched to bean property?";
/* 155 */         logger.log(Level.SEVERE, m);
/*     */       } 
/*     */     } 
/*     */     
/* 159 */     selectProps.setIncludedProperties(includedProps);
/* 160 */     SqlTreeNodeRoot sqlTreeNodeRoot = new SqlTreeNodeRoot(desc, selectProps, null, this.withId);
/* 161 */     sqlTree.setRootNode(sqlTreeNodeRoot);
/*     */     
/* 163 */     return sqlTree;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String buildSql(String orderBy, CQueryPredicates predicates, OrmQueryRequest<?> request) {
/* 172 */     StringBuilder sb = new StringBuilder();
/* 173 */     sb.append(this.preWhereExprSql);
/* 174 */     sb.append(" ");
/*     */     
/* 176 */     String dynamicWhere = null;
/* 177 */     if (request.getQuery().getId() != null) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 182 */       BeanDescriptor<?> descriptor = request.getBeanDescriptor();
/*     */ 
/*     */       
/* 185 */       dynamicWhere = descriptor.getIdBinderIdSql();
/*     */     } 
/*     */     
/* 188 */     String dbWhere = predicates.getDbWhere();
/* 189 */     if (dbWhere != null && dbWhere.length() > 0) {
/* 190 */       if (dynamicWhere == null) {
/* 191 */         dynamicWhere = dbWhere;
/*     */       } else {
/* 193 */         dynamicWhere = dynamicWhere + " and " + dbWhere;
/*     */       } 
/*     */     }
/*     */     
/* 197 */     if (dynamicWhere != null) {
/* 198 */       if (this.andWhereExpr) {
/* 199 */         sb.append(" and ");
/*     */       } else {
/* 201 */         sb.append(" where ");
/*     */       } 
/* 203 */       sb.append(dynamicWhere);
/* 204 */       sb.append(" ");
/*     */     } 
/*     */     
/* 207 */     if (this.preHavingExprSql != null) {
/* 208 */       sb.append(this.preHavingExprSql);
/* 209 */       sb.append(" ");
/*     */     } 
/*     */     
/* 212 */     String dbHaving = predicates.getDbHaving();
/*     */     
/* 214 */     if (dbHaving != null && dbHaving.length() > 0) {
/* 215 */       if (this.andHavingExpr) {
/* 216 */         sb.append(" and ");
/*     */       } else {
/* 218 */         sb.append(" having ");
/*     */       } 
/* 220 */       sb.append(dbHaving);
/* 221 */       sb.append(" ");
/*     */     } 
/*     */     
/* 224 */     if (orderBy != null) {
/* 225 */       sb.append(" order by ").append(orderBy);
/*     */     }
/*     */     
/* 228 */     return sb.toString();
/*     */   }
/*     */   
/*     */   public String getOrderBy(CQueryPredicates predicates) {
/* 232 */     String orderBy = predicates.getDbOrderBy();
/* 233 */     if (orderBy != null) {
/* 234 */       return orderBy;
/*     */     }
/* 236 */     return this.orderBySql;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 242 */   public String getName() { return this.name; }
/*     */ 
/*     */ 
/*     */   
/* 246 */   public SqlTree getSqlTree() { return this.sqlTree; }
/*     */ 
/*     */ 
/*     */   
/* 250 */   public boolean isWithId() { return this.withId; }
/*     */ 
/*     */ 
/*     */   
/* 254 */   public String getQuery() { return this.query; }
/*     */ 
/*     */ 
/*     */   
/* 258 */   public String getColumnMapping() { return this.columnMapping; }
/*     */ 
/*     */ 
/*     */   
/* 262 */   public String getWhereClause() { return this.whereClause; }
/*     */ 
/*     */ 
/*     */   
/* 266 */   public String getHavingClause() { return this.havingClause; }
/*     */ 
/*     */ 
/*     */   
/* 270 */   public String toString() { return Arrays.toString(this.selectColumns); }
/*     */ 
/*     */ 
/*     */   
/* 274 */   public BeanDescriptor<?> getBeanDescriptor() { return this.desc; }
/*     */ 
/*     */ 
/*     */   
/* 278 */   public DeployParser createDeployPropertyParser() { return new DeployPropertyParserRawSql(this); }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\deploy\DRawSqlSelect.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */