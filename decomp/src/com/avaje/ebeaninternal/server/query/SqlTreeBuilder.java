/*     */ package com.avaje.ebeaninternal.server.query;
/*     */ 
/*     */ import com.avaje.ebean.Query;
/*     */ import com.avaje.ebeaninternal.api.ManyWhereJoins;
/*     */ import com.avaje.ebeaninternal.api.SpiQuery;
/*     */ import com.avaje.ebeaninternal.server.core.OrmQueryRequest;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanProperty;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanPropertyAssoc;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanPropertyAssocMany;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanPropertyAssocOne;
/*     */ import com.avaje.ebeaninternal.server.deploy.InheritInfo;
/*     */ import com.avaje.ebeaninternal.server.deploy.TableJoin;
/*     */ import com.avaje.ebeaninternal.server.el.ElPropertyValue;
/*     */ import com.avaje.ebeaninternal.server.querydefn.OrmQueryDetail;
/*     */ import com.avaje.ebeaninternal.server.querydefn.OrmQueryProperties;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
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
/*     */ public class SqlTreeBuilder
/*     */ {
/*  54 */   private static final Logger logger = Logger.getLogger(SqlTreeBuilder.class.getName());
/*     */   
/*     */   private final SpiQuery<?> query;
/*     */   
/*     */   private final BeanDescriptor<?> desc;
/*     */   private final OrmQueryDetail queryDetail;
/*     */   
/*     */   public SqlTreeBuilder(OrmQueryRequest<?> request, CQueryPredicates predicates, OrmQueryDetail queryDetail) {
/*  62 */     this.summary = new StringBuilder();
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
/*  79 */     this.selectIncludes = new HashSet();
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
/*  92 */     this.rawSql = true;
/*  93 */     this.desc = request.getBeanDescriptor();
/*  94 */     this.query = null;
/*  95 */     this.subQuery = false;
/*  96 */     this.queryDetail = queryDetail;
/*  97 */     this.predicates = predicates;
/*     */     
/*  99 */     this.includeJoin = null;
/* 100 */     this.manyWhereJoins = null;
/* 101 */     this.alias = null;
/* 102 */     this.ctx = null;
/*     */   }
/*     */   private final StringBuilder summary; private final CQueryPredicates predicates; private final boolean subQuery; private BeanPropertyAssocMany<?> manyProperty;
/*     */   private String manyPropertyName;
/*     */   private final SqlTreeAlias alias;
/*     */   private final DefaultDbSqlContext ctx;
/*     */   
/*     */   public SqlTreeBuilder(String tableAliasPlaceHolder, String columnAliasPrefix, OrmQueryRequest<?> request, CQueryPredicates predicates) {
/*     */     this.summary = new StringBuilder();
/*     */     this.selectIncludes = new HashSet();
/* 112 */     this.rawSql = false;
/* 113 */     this.desc = request.getBeanDescriptor();
/* 114 */     this.query = request.getQuery();
/*     */     
/* 116 */     this.subQuery = Query.Type.SUBQUERY.equals(this.query.getType());
/* 117 */     this.includeJoin = this.query.getIncludeTableJoin();
/* 118 */     this.manyWhereJoins = this.query.getManyWhereJoins();
/* 119 */     this.queryDetail = this.query.getDetail();
/*     */     
/* 121 */     this.predicates = predicates;
/* 122 */     this.alias = new SqlTreeAlias(request.getBeanDescriptor().getBaseTableAlias());
/* 123 */     this.ctx = new DefaultDbSqlContext(this.alias, tableAliasPlaceHolder, columnAliasPrefix, !this.subQuery ? 1 : 0);
/*     */   }
/*     */   private final HashSet<String> selectIncludes;
/*     */   private final ManyWhereJoins manyWhereJoins;
/*     */   private final TableJoin includeJoin;
/*     */   private final boolean rawSql;
/*     */   
/*     */   public SqlTree build() {
/* 131 */     SqlTree sqlTree = new SqlTree();
/*     */     
/* 133 */     this.summary.append(this.desc.getName());
/*     */ 
/*     */     
/* 136 */     buildRoot(this.desc, sqlTree);
/*     */ 
/*     */     
/* 139 */     SqlTreeNode rootNode = sqlTree.getRootNode();
/*     */     
/* 141 */     if (!this.rawSql) {
/* 142 */       sqlTree.setSelectSql(buildSelectClause(rootNode));
/* 143 */       sqlTree.setFromSql(buildFromClause(rootNode));
/* 144 */       sqlTree.setInheritanceWhereSql(buildWhereClause(rootNode));
/* 145 */       sqlTree.setEncryptedProps(this.ctx.getEncryptedProps());
/*     */     } 
/* 147 */     sqlTree.setIncludes(this.queryDetail.getIncludes());
/* 148 */     sqlTree.setSummary(this.summary.toString());
/*     */     
/* 150 */     if (this.manyPropertyName != null) {
/* 151 */       ElPropertyValue manyPropEl = this.desc.getElGetValue(this.manyPropertyName);
/* 152 */       sqlTree.setManyProperty(this.manyProperty, this.manyPropertyName, manyPropEl);
/*     */     } 
/*     */     
/* 155 */     return sqlTree;
/*     */   }
/*     */ 
/*     */   
/*     */   private String buildSelectClause(SqlTreeNode rootNode) {
/* 160 */     if (this.rawSql) {
/* 161 */       return "Not Used";
/*     */     }
/* 163 */     rootNode.appendSelect(this.ctx, this.subQuery);
/*     */     
/* 165 */     String selectSql = this.ctx.getContent();
/*     */ 
/*     */     
/* 168 */     if (selectSql.length() >= ", ".length()) {
/* 169 */       selectSql = selectSql.substring(", ".length());
/*     */     }
/*     */     
/* 172 */     return selectSql;
/*     */   }
/*     */ 
/*     */   
/*     */   private String buildWhereClause(SqlTreeNode rootNode) {
/* 177 */     if (this.rawSql) {
/* 178 */       return "Not Used";
/*     */     }
/* 180 */     rootNode.appendWhere(this.ctx);
/* 181 */     return this.ctx.getContent();
/*     */   }
/*     */ 
/*     */   
/*     */   private String buildFromClause(SqlTreeNode rootNode) {
/* 186 */     if (this.rawSql) {
/* 187 */       return "Not Used";
/*     */     }
/* 189 */     rootNode.appendFrom(this.ctx, false);
/* 190 */     return this.ctx.getContent();
/*     */   }
/*     */ 
/*     */   
/*     */   private void buildRoot(BeanDescriptor<?> desc, SqlTree sqlTree) {
/* 195 */     SqlTreeNode selectRoot = buildSelectChain(null, null, desc, null);
/* 196 */     sqlTree.setRootNode(selectRoot);
/*     */     
/* 198 */     if (!this.rawSql) {
/* 199 */       this.alias.addJoin(this.queryDetail.getIncludes(), desc);
/* 200 */       this.alias.addJoin(this.predicates.getPredicateIncludes(), desc);
/* 201 */       this.alias.addManyWhereJoins(this.manyWhereJoins.getJoins());
/*     */ 
/*     */       
/* 204 */       this.alias.buildAlias();
/*     */       
/* 206 */       this.predicates.parseTableAlias(this.alias);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private SqlTreeNode buildSelectChain(String prefix, BeanPropertyAssoc<?> prop, BeanDescriptor<?> desc, List<SqlTreeNode> joinList) {
/* 216 */     List<SqlTreeNode> myJoinList = new ArrayList<SqlTreeNode>();
/*     */     
/* 218 */     BeanPropertyAssocOne[] ones = desc.propertiesOne();
/* 219 */     for (i = 0; i < ones.length; i++) {
/* 220 */       String propPrefix = SplitName.add(prefix, ones[i].getName());
/* 221 */       if (isIncludeBean(propPrefix, ones[i])) {
/* 222 */         this.selectIncludes.add(propPrefix);
/* 223 */         buildSelectChain(propPrefix, ones[i], ones[i].getTargetDescriptor(), myJoinList);
/*     */       } 
/*     */     } 
/*     */     
/* 227 */     BeanPropertyAssocMany[] manys = desc.propertiesMany();
/* 228 */     for (i = 0; i < manys.length; i++) {
/* 229 */       String propPrefix = SplitName.add(prefix, manys[i].getName());
/* 230 */       if (isIncludeMany(prefix, propPrefix, manys[i])) {
/* 231 */         this.selectIncludes.add(propPrefix);
/* 232 */         buildSelectChain(propPrefix, manys[i], manys[i].getTargetDescriptor(), myJoinList);
/*     */       } 
/*     */     } 
/*     */     
/* 236 */     if (prefix == null && !this.rawSql) {
/* 237 */       addManyWhereJoins(myJoinList);
/*     */     }
/*     */     
/* 240 */     SqlTreeNode selectNode = buildNode(prefix, prop, desc, myJoinList);
/* 241 */     if (joinList != null) {
/* 242 */       joinList.add(selectNode);
/*     */     }
/* 244 */     return selectNode;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void addManyWhereJoins(List<SqlTreeNode> myJoinList) {
/* 255 */     Set<String> includes = this.manyWhereJoins.getJoins();
/* 256 */     for (String joinProp : includes) {
/*     */       
/* 258 */       BeanPropertyAssoc<?> beanProperty = (BeanPropertyAssoc)this.desc.getBeanPropertyFromPath(joinProp);
/* 259 */       SqlTreeNodeManyWhereJoin nodeJoin = new SqlTreeNodeManyWhereJoin(joinProp, beanProperty);
/* 260 */       myJoinList.add(nodeJoin);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private SqlTreeNode buildNode(String prefix, BeanPropertyAssoc<?> prop, BeanDescriptor<?> desc, List<SqlTreeNode> myList) {
/* 266 */     OrmQueryProperties queryProps = this.queryDetail.getChunk(prefix, false);
/*     */     
/* 268 */     SqlTreeProperties props = getBaseSelect(desc, queryProps);
/*     */     
/* 270 */     if (prefix == null) {
/* 271 */       buildExtraJoins(desc, myList);
/* 272 */       return new SqlTreeNodeRoot(desc, props, myList, !this.subQuery ? 1 : 0, this.includeJoin);
/*     */     } 
/* 274 */     if (prop instanceof BeanPropertyAssocMany) {
/* 275 */       return new SqlTreeNodeManyRoot(prefix, (BeanPropertyAssocMany)prop, props, myList);
/*     */     }
/*     */     
/* 278 */     return new SqlTreeNodeBean(prefix, prop, props, myList, true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void buildExtraJoins(BeanDescriptor<?> desc, List<SqlTreeNode> myList) {
/* 288 */     if (this.rawSql) {
/*     */       return;
/*     */     }
/*     */     
/* 292 */     Set<String> predicateIncludes = this.predicates.getPredicateIncludes();
/*     */     
/* 294 */     if (predicateIncludes == null) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 304 */     predicateIncludes.removeAll(this.manyWhereJoins.getJoins());
/*     */ 
/*     */ 
/*     */     
/* 308 */     IncludesDistiller extraJoinDistill = new IncludesDistiller(desc, this.selectIncludes, predicateIncludes, null);
/*     */     
/* 310 */     Collection<SqlTreeNodeExtraJoin> extraJoins = extraJoinDistill.getExtraJoinRootNodes();
/* 311 */     if (extraJoins.isEmpty()) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 317 */     Iterator<SqlTreeNodeExtraJoin> it = extraJoins.iterator();
/* 318 */     while (it.hasNext()) {
/* 319 */       SqlTreeNodeExtraJoin extraJoin = (SqlTreeNodeExtraJoin)it.next();
/* 320 */       myList.add(extraJoin);
/*     */       
/* 322 */       if (extraJoin.isManyJoin())
/*     */       {
/*     */ 
/*     */         
/* 326 */         this.query.setDistinct(true);
/*     */       }
/*     */     } 
/*     */   }
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
/*     */   private void addPropertyToSubQuery(SqlTreeProperties selectProps, BeanDescriptor<?> desc, OrmQueryProperties queryProps, String propName) {
/* 342 */     BeanProperty p = desc.findBeanProperty(propName);
/* 343 */     if (p == null) {
/* 344 */       logger.log(Level.SEVERE, "property [" + propName + "]not found on " + desc + " for query - excluding it.");
/*     */     
/*     */     }
/* 347 */     else if (p instanceof BeanPropertyAssoc && p.isEmbedded()) {
/*     */       
/* 349 */       int pos = propName.indexOf(".");
/* 350 */       if (pos > -1) {
/* 351 */         String name = propName.substring(pos + 1);
/* 352 */         p = ((BeanPropertyAssoc)p).getTargetDescriptor().findBeanProperty(name);
/*     */       } 
/*     */     } 
/*     */     
/* 356 */     selectProps.add(p);
/*     */   }
/*     */ 
/*     */   
/*     */   private void addProperty(SqlTreeProperties selectProps, BeanDescriptor<?> desc, OrmQueryProperties queryProps, String propName) {
/* 361 */     if (this.subQuery) {
/* 362 */       addPropertyToSubQuery(selectProps, desc, queryProps, propName);
/*     */       
/*     */       return;
/*     */     } 
/* 366 */     int basePos = propName.indexOf('.');
/* 367 */     if (basePos > -1) {
/*     */ 
/*     */ 
/*     */       
/* 371 */       String baseName = propName.substring(0, basePos);
/*     */ 
/*     */       
/* 374 */       if (!selectProps.containsProperty(baseName)) {
/* 375 */         BeanProperty p = desc.findBeanProperty(baseName);
/* 376 */         if (p == null) {
/* 377 */           String m = "property [" + propName + "] not found on " + desc + " for query - excluding it.";
/* 378 */           logger.log(Level.SEVERE, m);
/*     */         }
/* 380 */         else if (p.isEmbedded()) {
/*     */ 
/*     */           
/* 383 */           selectProps.add(p);
/*     */ 
/*     */           
/* 386 */           selectProps.getIncludedProperties().add(baseName);
/*     */         } else {
/*     */           
/* 389 */           String m = "property [" + p.getFullBeanName() + "] expected to be an embedded bean for query - excluding it.";
/*     */           
/* 391 */           logger.log(Level.SEVERE, m);
/*     */         }
/*     */       
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/* 398 */       BeanProperty p = desc.findBeanProperty(propName);
/* 399 */       if (p == null) {
/* 400 */         logger.log(Level.SEVERE, "property [" + propName + "] not found on " + desc + " for query - excluding it.");
/*     */       
/*     */       }
/* 403 */       else if (!p.isId()) {
/*     */ 
/*     */ 
/*     */         
/* 407 */         if (p instanceof BeanPropertyAssoc) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 413 */           if (!queryProps.isIncludedBeanJoin(p.getName()))
/*     */           {
/*     */             
/* 416 */             selectProps.add(p);
/*     */           }
/*     */         } else {
/* 419 */           selectProps.add(p);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private SqlTreeProperties getBaseSelectPartial(BeanDescriptor<?> desc, OrmQueryProperties queryProps) {
/* 426 */     SqlTreeProperties selectProps = new SqlTreeProperties();
/* 427 */     selectProps.setReadOnly(queryProps.isReadOnly());
/* 428 */     selectProps.setIncludedProperties(queryProps.getAllIncludedProperties());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 437 */     Iterator<String> it = queryProps.getSelectProperties();
/* 438 */     while (it.hasNext()) {
/* 439 */       String propName = (String)it.next();
/* 440 */       if (propName.length() > 0) {
/* 441 */         addProperty(selectProps, desc, queryProps, propName);
/*     */       }
/*     */     } 
/*     */     
/* 445 */     return selectProps;
/*     */   }
/*     */ 
/*     */   
/*     */   private SqlTreeProperties getBaseSelect(BeanDescriptor<?> desc, OrmQueryProperties queryProps) {
/* 450 */     boolean partial = (queryProps != null && !queryProps.allProperties());
/* 451 */     if (partial) {
/* 452 */       return getBaseSelectPartial(desc, queryProps);
/*     */     }
/*     */     
/* 455 */     SqlTreeProperties selectProps = new SqlTreeProperties();
/*     */ 
/*     */     
/* 458 */     selectProps.add(desc.propertiesBaseScalar());
/* 459 */     selectProps.add(desc.propertiesBaseCompound());
/* 460 */     selectProps.add(desc.propertiesEmbedded());
/*     */     
/* 462 */     BeanPropertyAssocOne[] propertiesOne = desc.propertiesOne();
/* 463 */     for (i = 0; i < propertiesOne.length; i++) {
/* 464 */       if (queryProps == null || !queryProps.isIncludedBeanJoin(propertiesOne[i].getName()))
/*     */       {
/*     */ 
/*     */         
/* 468 */         selectProps.add(propertiesOne[i]);
/*     */       }
/*     */     } 
/*     */     
/* 472 */     selectProps.setTableJoins(desc.tableJoins());
/*     */     
/* 474 */     InheritInfo inheritInfo = desc.getInheritInfo();
/* 475 */     if (inheritInfo != null)
/*     */     {
/* 477 */       inheritInfo.addChildrenProperties(selectProps);
/*     */     }
/*     */     
/* 480 */     return selectProps;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isIncludeMany(String prefix, String propName, BeanPropertyAssocMany<?> manyProp) {
/* 488 */     if (this.queryDetail.isJoinsEmpty()) {
/* 489 */       return false;
/*     */     }
/*     */     
/* 492 */     if (this.queryDetail.includes(propName)) {
/*     */       
/* 494 */       if (this.manyProperty != null) {
/*     */         
/* 496 */         if (logger.isLoggable(Level.FINE)) {
/* 497 */           String msg = "Not joining [" + propName + "] as already joined to a Many[" + this.manyProperty + "].";
/* 498 */           logger.fine(msg);
/*     */         } 
/* 500 */         return false;
/*     */       } 
/*     */       
/* 503 */       this.manyProperty = manyProp;
/* 504 */       this.manyPropertyName = propName;
/* 505 */       this.summary.append(" +many:").append(propName);
/* 506 */       return true;
/*     */     } 
/* 508 */     return false;
/*     */   }
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
/*     */   private boolean isIncludeBean(String prefix, BeanPropertyAssocOne<?> prop) {
/* 521 */     if (this.queryDetail.includes(prefix)) {
/*     */       
/* 523 */       this.summary.append(", ").append(prefix);
/* 524 */       String[] splitNames = SplitName.split(prefix);
/* 525 */       this.queryDetail.includeBeanJoin(splitNames[0], splitNames[1]);
/* 526 */       return true;
/*     */     } 
/*     */     
/* 529 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static class IncludesDistiller
/*     */   {
/*     */     private final Set<String> selectIncludes;
/*     */     
/*     */     private final Set<String> predicateIncludes;
/*     */     
/*     */     private final Map<String, SqlTreeNodeExtraJoin> joinRegister;
/*     */     
/*     */     private final Map<String, SqlTreeNodeExtraJoin> rootRegister;
/*     */     
/*     */     private final BeanDescriptor<?> desc;
/*     */ 
/*     */     
/*     */     private IncludesDistiller(BeanDescriptor<?> desc, Set<String> selectIncludes, Set<String> predicateIncludes) {
/* 548 */       this.joinRegister = new HashMap();
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 553 */       this.rootRegister = new HashMap();
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 558 */       this.desc = desc;
/* 559 */       this.selectIncludes = selectIncludes;
/* 560 */       this.predicateIncludes = predicateIncludes;
/*     */     }
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
/*     */     private Collection<SqlTreeNodeExtraJoin> getExtraJoinRootNodes() {
/* 573 */       String[] extras = findExtras();
/* 574 */       if (extras.length == 0) {
/* 575 */         return this.rootRegister.values();
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 580 */       Arrays.sort(extras);
/*     */ 
/*     */       
/* 583 */       for (int i = 0; i < extras.length; i++) {
/* 584 */         createExtraJoin(extras[i]);
/*     */       }
/*     */       
/* 587 */       return this.rootRegister.values();
/*     */     }
/*     */ 
/*     */     
/*     */     private void createExtraJoin(String includeProp) {
/* 592 */       SqlTreeNodeExtraJoin extraJoin = createJoinLeaf(includeProp);
/* 593 */       if (extraJoin != null) {
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 598 */         SqlTreeNodeExtraJoin root = findExtraJoinRoot(includeProp, extraJoin);
/*     */ 
/*     */ 
/*     */         
/* 602 */         this.rootRegister.put(root.getName(), root);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private SqlTreeNodeExtraJoin createJoinLeaf(String propertyName) {
/* 611 */       ElPropertyValue elGetValue = this.desc.getElGetValue(propertyName);
/*     */       
/* 613 */       if (elGetValue == null)
/*     */       {
/*     */         
/* 616 */         return null;
/*     */       }
/* 618 */       BeanProperty beanProperty = elGetValue.getBeanProperty();
/* 619 */       if (beanProperty instanceof BeanPropertyAssoc) {
/* 620 */         BeanPropertyAssoc<?> assocProp = (BeanPropertyAssoc)beanProperty;
/* 621 */         if (assocProp.isEmbedded())
/*     */         {
/* 623 */           return null;
/*     */         }
/* 625 */         SqlTreeNodeExtraJoin extraJoin = new SqlTreeNodeExtraJoin(propertyName, assocProp);
/* 626 */         this.joinRegister.put(propertyName, extraJoin);
/* 627 */         return extraJoin;
/*     */       } 
/* 629 */       return null;
/*     */     }
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
/*     */     private SqlTreeNodeExtraJoin findExtraJoinRoot(String includeProp, SqlTreeNodeExtraJoin childJoin) {
/* 642 */       int dotPos = includeProp.lastIndexOf('.');
/* 643 */       if (dotPos == -1)
/*     */       {
/* 645 */         return childJoin;
/*     */       }
/*     */ 
/*     */       
/* 649 */       String parentPropertyName = includeProp.substring(0, dotPos);
/* 650 */       if (this.selectIncludes.contains(parentPropertyName))
/*     */       {
/* 652 */         return childJoin;
/*     */       }
/*     */       
/* 655 */       SqlTreeNodeExtraJoin parentJoin = (SqlTreeNodeExtraJoin)this.joinRegister.get(parentPropertyName);
/* 656 */       if (parentJoin == null)
/*     */       {
/* 658 */         parentJoin = createJoinLeaf(parentPropertyName);
/*     */       }
/*     */       
/* 661 */       parentJoin.addChild(childJoin);
/* 662 */       return findExtraJoinRoot(parentPropertyName, parentJoin);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private String[] findExtras() {
/* 672 */       List<String> extras = new ArrayList<String>();
/*     */       
/* 674 */       for (String predProp : this.predicateIncludes) {
/* 675 */         if (!this.selectIncludes.contains(predProp)) {
/* 676 */           extras.add(predProp);
/*     */         }
/*     */       } 
/* 679 */       return (String[])extras.toArray(new String[extras.size()]);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\query\SqlTreeBuilder.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */