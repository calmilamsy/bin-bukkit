/*     */ package com.avaje.ebeaninternal.server.query;
/*     */ 
/*     */ import com.avaje.ebeaninternal.api.BindParams;
/*     */ import com.avaje.ebeaninternal.api.SpiExpressionList;
/*     */ import com.avaje.ebeaninternal.api.SpiQuery;
/*     */ import com.avaje.ebeaninternal.server.core.OrmQueryRequest;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanPropertyAssocMany;
/*     */ import com.avaje.ebeaninternal.server.deploy.DeployParser;
/*     */ import com.avaje.ebeaninternal.server.persist.Binder;
/*     */ import com.avaje.ebeaninternal.server.querydefn.OrmQueryProperties;
/*     */ import com.avaje.ebeaninternal.server.type.DataBind;
/*     */ import com.avaje.ebeaninternal.server.util.BindParamsParser;
/*     */ import com.avaje.ebeaninternal.util.DefaultExpressionRequest;
/*     */ import java.sql.SQLException;
/*     */ import java.util.ArrayList;
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
/*     */ public class CQueryPredicates
/*     */ {
/*  36 */   private static final Logger logger = Logger.getLogger(CQueryPredicates.class.getName());
/*     */ 
/*     */ 
/*     */   
/*     */   private final Binder binder;
/*     */ 
/*     */ 
/*     */   
/*     */   private final OrmQueryRequest<?> request;
/*     */ 
/*     */ 
/*     */   
/*     */   private final SpiQuery<?> query;
/*     */ 
/*     */ 
/*     */   
/*     */   private final Object idValue;
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean rawSql;
/*     */ 
/*     */ 
/*     */   
/*     */   private final BindParams bindParams;
/*     */ 
/*     */ 
/*     */   
/*     */   private BindParams.OrderedList havingNamedParams;
/*     */ 
/*     */ 
/*     */   
/*     */   private ArrayList<Object> filterManyExprBindValues;
/*     */ 
/*     */ 
/*     */   
/*     */   private String filterManyExprSql;
/*     */ 
/*     */ 
/*     */   
/*     */   private ArrayList<Object> whereExprBindValues;
/*     */ 
/*     */ 
/*     */   
/*     */   private String whereExprSql;
/*     */ 
/*     */ 
/*     */   
/*     */   private String whereRawSql;
/*     */ 
/*     */ 
/*     */   
/*     */   private ArrayList<Object> havingExprBindValues;
/*     */ 
/*     */ 
/*     */   
/*     */   private String havingExprSql;
/*     */ 
/*     */ 
/*     */   
/*     */   private String havingRawSql;
/*     */ 
/*     */ 
/*     */   
/*     */   private String dbHaving;
/*     */ 
/*     */ 
/*     */   
/*     */   private String dbWhere;
/*     */ 
/*     */ 
/*     */   
/*     */   private String dbFilterMany;
/*     */ 
/*     */ 
/*     */   
/*     */   private String logicalOrderBy;
/*     */ 
/*     */ 
/*     */   
/*     */   private String dbOrderBy;
/*     */ 
/*     */ 
/*     */   
/*     */   private Set<String> predicateIncludes;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CQueryPredicates(Binder binder, OrmQueryRequest<?> request) {
/* 126 */     this.binder = binder;
/* 127 */     this.request = request;
/* 128 */     this.query = request.getQuery();
/* 129 */     this.bindParams = this.query.getBindParams();
/* 130 */     this.idValue = this.query.getId();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isLuceneResolvable() {
/* 139 */     CQueryPredicatesLuceneResolve luceneResolve = new CQueryPredicatesLuceneResolve(this.request);
/* 140 */     return luceneResolve.isLuceneResolvable();
/*     */   }
/*     */ 
/*     */   
/*     */   public String bind(DataBind dataBind) throws SQLException {
/* 145 */     StringBuilder bindLog = new StringBuilder();
/*     */     
/* 147 */     if (this.idValue != null) {
/*     */       
/* 149 */       this.request.getBeanDescriptor().bindId(dataBind, this.idValue);
/* 150 */       bindLog.append(this.idValue);
/*     */     } 
/*     */     
/* 153 */     if (this.bindParams != null)
/*     */     {
/* 155 */       this.binder.bind(this.bindParams, dataBind, bindLog);
/*     */     }
/*     */     
/* 158 */     if (this.whereExprBindValues != null)
/*     */     {
/* 160 */       for (int i = 0; i < this.whereExprBindValues.size(); i++) {
/* 161 */         Object bindValue = this.whereExprBindValues.get(i);
/* 162 */         this.binder.bindObject(dataBind, bindValue);
/* 163 */         if (i > 0 || this.idValue != null) {
/* 164 */           bindLog.append(", ");
/*     */         }
/* 166 */         bindLog.append(bindValue);
/*     */       } 
/*     */     }
/*     */     
/* 170 */     if (this.filterManyExprBindValues != null)
/*     */     {
/* 172 */       for (int i = 0; i < this.filterManyExprBindValues.size(); i++) {
/* 173 */         Object bindValue = this.filterManyExprBindValues.get(i);
/* 174 */         this.binder.bindObject(dataBind, bindValue);
/* 175 */         if (i > 0 || this.idValue != null) {
/* 176 */           bindLog.append(", ");
/*     */         }
/* 178 */         bindLog.append(bindValue);
/*     */       } 
/*     */     }
/*     */     
/* 182 */     if (this.havingNamedParams != null) {
/*     */       
/* 184 */       bindLog.append(" havingNamed ");
/* 185 */       this.binder.bind(this.havingNamedParams.list(), dataBind, bindLog);
/*     */     } 
/*     */     
/* 188 */     if (this.havingExprBindValues != null) {
/*     */       
/* 190 */       bindLog.append(" having ");
/* 191 */       for (int i = 0; i < this.havingExprBindValues.size(); i++) {
/* 192 */         Object bindValue = this.havingExprBindValues.get(i);
/* 193 */         this.binder.bindObject(dataBind, bindValue);
/* 194 */         if (i > 0) {
/* 195 */           bindLog.append(", ");
/*     */         }
/* 197 */         bindLog.append(bindValue);
/*     */       } 
/*     */     } 
/*     */     
/* 201 */     return bindLog.toString();
/*     */   }
/*     */   
/*     */   private void buildBindHavingRawSql(boolean buildSql, boolean parseRaw, DeployParser deployParser) {
/* 205 */     if (buildSql || this.bindParams != null) {
/*     */       
/* 207 */       this.havingRawSql = this.query.getAdditionalHaving();
/* 208 */       if (parseRaw) {
/* 209 */         this.havingRawSql = deployParser.parse(this.havingRawSql);
/*     */       }
/* 211 */       if (this.havingRawSql != null && this.bindParams != null) {
/*     */         
/* 213 */         this.havingNamedParams = BindParamsParser.parseNamedParams(this.bindParams, this.havingRawSql);
/* 214 */         this.havingRawSql = this.havingNamedParams.getPreparedSql();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void buildBindWhereRawSql(boolean buildSql, boolean parseRaw, DeployParser parser) {
/* 223 */     if (buildSql || this.bindParams != null) {
/* 224 */       this.whereRawSql = buildWhereRawSql();
/* 225 */       boolean hasRaw = !"".equals(this.whereRawSql);
/* 226 */       if (hasRaw && parseRaw) {
/*     */ 
/*     */ 
/*     */         
/* 230 */         parser.setEncrypted(true);
/* 231 */         this.whereRawSql = parser.parse(this.whereRawSql);
/* 232 */         parser.setEncrypted(false);
/*     */       } 
/*     */       
/* 235 */       if (this.bindParams != null) {
/* 236 */         if (hasRaw) {
/* 237 */           this.whereRawSql = BindParamsParser.parse(this.bindParams, this.whereRawSql, this.request.getBeanDescriptor());
/*     */         }
/* 239 */         else if (this.query.isRawSql() && !buildSql) {
/*     */ 
/*     */ 
/*     */           
/* 243 */           String s = this.query.getRawSql().getSql().getPreWhere();
/* 244 */           if (this.bindParams.requiresNamedParamsPrepare()) {
/* 245 */             BindParamsParser.parse(this.bindParams, s);
/*     */           }
/*     */         } 
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private String buildWhereRawSql() {
/* 255 */     String whereRaw = this.query.getRawWhereClause();
/* 256 */     if (whereRaw == null) {
/* 257 */       whereRaw = "";
/*     */     }
/*     */     
/* 260 */     String additionalWhere = this.query.getAdditionalWhere();
/* 261 */     if (additionalWhere != null) {
/* 262 */       whereRaw = whereRaw + additionalWhere;
/*     */     }
/* 264 */     return whereRaw;
/*     */   }
/*     */ 
/*     */   
/*     */   public void prepare(boolean buildSql) {
/* 269 */     DeployParser deployParser = this.request.createDeployParser();
/*     */     
/* 271 */     prepare(buildSql, true, deployParser);
/*     */   }
/*     */ 
/*     */   
/* 275 */   public void prepareRawSql(DeployParser deployParser) { prepare(true, false, deployParser); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void prepare(boolean buildSql, boolean parseRaw, DeployParser deployParser) {
/* 283 */     buildBindWhereRawSql(buildSql, parseRaw, deployParser);
/* 284 */     buildBindHavingRawSql(buildSql, parseRaw, deployParser);
/*     */     
/* 286 */     SpiExpressionList<?> whereExp = this.query.getWhereExpressions();
/* 287 */     if (whereExp != null) {
/* 288 */       DefaultExpressionRequest whereReq = new DefaultExpressionRequest(this.request, deployParser);
/* 289 */       this.whereExprBindValues = whereExp.buildBindValues(whereReq);
/* 290 */       if (buildSql) {
/* 291 */         this.whereExprSql = whereExp.buildSql(whereReq);
/*     */       }
/*     */     } 
/*     */     
/* 295 */     BeanPropertyAssocMany<?> manyProperty = this.request.getManyProperty();
/* 296 */     if (manyProperty != null) {
/* 297 */       OrmQueryProperties chunk = this.query.getDetail().getChunk(manyProperty.getName(), false);
/* 298 */       SpiExpressionList<?> filterMany = chunk.getFilterMany();
/* 299 */       if (filterMany != null) {
/* 300 */         DefaultExpressionRequest filterReq = new DefaultExpressionRequest(this.request, deployParser);
/* 301 */         this.filterManyExprBindValues = filterMany.buildBindValues(filterReq);
/* 302 */         if (buildSql) {
/* 303 */           this.filterManyExprSql = filterMany.buildSql(filterReq);
/*     */         }
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 309 */     SpiExpressionList<?> havingExpr = this.query.getHavingExpressions();
/* 310 */     if (havingExpr != null) {
/* 311 */       DefaultExpressionRequest havingReq = new DefaultExpressionRequest(this.request, deployParser);
/* 312 */       this.havingExprBindValues = havingExpr.buildBindValues(havingReq);
/* 313 */       if (buildSql) {
/* 314 */         this.havingExprSql = havingExpr.buildSql(havingReq);
/*     */       }
/*     */     } 
/*     */     
/* 318 */     if (buildSql) {
/* 319 */       parsePropertiesToDbColumns(deployParser);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void parsePropertiesToDbColumns(DeployParser deployParser) {
/* 330 */     this.dbWhere = deriveWhere(deployParser);
/* 331 */     this.dbFilterMany = deriveFilterMany(deployParser);
/* 332 */     this.dbHaving = deriveHaving(deployParser);
/*     */ 
/*     */     
/* 335 */     this.logicalOrderBy = deriveOrderByWithMany(this.request.getManyProperty());
/* 336 */     if (this.logicalOrderBy != null) {
/* 337 */       this.dbOrderBy = deployParser.parse(this.logicalOrderBy);
/*     */     }
/*     */     
/* 340 */     this.predicateIncludes = deployParser.getIncludes();
/*     */   }
/*     */   
/*     */   private String deriveFilterMany(DeployParser deployParser) {
/* 344 */     if (isEmpty(this.filterManyExprSql)) {
/* 345 */       return null;
/*     */     }
/* 347 */     return deployParser.parse(this.filterManyExprSql);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 352 */   private String deriveWhere(DeployParser deployParser) { return parse(this.whereRawSql, this.whereExprSql, deployParser); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void parseTableAlias(SqlTreeAlias alias) {
/* 359 */     if (this.dbWhere != null)
/*     */     {
/* 361 */       this.dbWhere = alias.parseWhere(this.dbWhere);
/*     */     }
/* 363 */     if (this.dbFilterMany != null)
/*     */     {
/* 365 */       this.dbFilterMany = alias.parse(this.dbFilterMany);
/*     */     }
/* 367 */     if (this.dbHaving != null) {
/* 368 */       this.dbHaving = alias.parseWhere(this.dbHaving);
/*     */     }
/* 370 */     if (this.dbOrderBy != null) {
/* 371 */       this.dbOrderBy = alias.parse(this.dbOrderBy);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/* 376 */   private boolean isEmpty(String s) { return (s == null || s.length() == 0); }
/*     */ 
/*     */ 
/*     */   
/*     */   private String parse(String raw, String expr, DeployParser deployParser) {
/* 381 */     StringBuilder sb = new StringBuilder();
/* 382 */     if (!isEmpty(raw)) {
/* 383 */       sb.append(raw);
/*     */     }
/* 385 */     if (!isEmpty(expr)) {
/* 386 */       if (sb.length() > 0) {
/* 387 */         sb.append(" and ");
/*     */       }
/* 389 */       sb.append(deployParser.parse(expr));
/*     */     } 
/* 391 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   
/* 395 */   private String deriveHaving(DeployParser deployParser) { return parse(this.havingRawSql, this.havingExprSql, deployParser); }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 400 */   private String parseOrderBy() { return CQueryOrderBy.parse(this.request.getBeanDescriptor(), this.query); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String deriveOrderByWithMany(BeanPropertyAssocMany<?> manyProp) {
/* 409 */     if (manyProp == null) {
/* 410 */       return parseOrderBy();
/*     */     }
/*     */     
/* 413 */     String orderBy = parseOrderBy();
/*     */     
/* 415 */     BeanDescriptor<?> desc = this.request.getBeanDescriptor();
/* 416 */     String orderById = desc.getDefaultOrderBy();
/*     */     
/* 418 */     if (orderBy == null) {
/* 419 */       orderBy = orderById;
/*     */     }
/*     */ 
/*     */     
/* 423 */     String manyOrderBy = manyProp.getFetchOrderBy();
/* 424 */     if (manyOrderBy != null) {
/* 425 */       orderBy = orderBy + " , " + CQueryBuilder.prefixOrderByFields(manyProp.getName(), manyOrderBy);
/*     */     }
/*     */     
/* 428 */     if (this.request.isFindById())
/*     */     {
/* 430 */       return orderBy;
/*     */     }
/*     */     
/* 433 */     if (orderBy.startsWith(orderById)) {
/* 434 */       return orderBy;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 441 */     int manyPos = orderBy.indexOf(manyProp.getName());
/* 442 */     int idPos = orderBy.indexOf(" " + orderById);
/*     */     
/* 444 */     if (manyPos == -1)
/*     */     {
/* 446 */       return orderBy;
/*     */     }
/* 448 */     if (idPos <= -1 || idPos >= manyPos) {
/*     */ 
/*     */ 
/*     */       
/* 452 */       if (idPos > manyPos) {
/*     */         
/* 454 */         String msg = "A Query on [" + desc + "] includes a join to a 'many' association [" + manyProp.getName();
/*     */         
/* 456 */         msg = msg + "] with an incorrect orderBy [" + orderBy + "]. The id property [" + orderById + "]";
/* 457 */         msg = msg + " must come before the many property [" + manyProp.getName() + "] in the orderBy.";
/* 458 */         msg = msg + " Ebean has automatically modified the orderBy clause to do this.";
/*     */         
/* 460 */         logger.log(Level.WARNING, msg);
/*     */       } 
/*     */ 
/*     */       
/* 464 */       orderBy = orderBy.substring(0, manyPos) + orderById + ", " + orderBy.substring(manyPos);
/*     */     } 
/*     */     
/* 467 */     return orderBy;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 474 */   public ArrayList<Object> getWhereExprBindValues() { return this.whereExprBindValues; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 481 */   public String getDbHaving() { return this.dbHaving; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 489 */   public String getDbWhere() { return this.dbWhere; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 497 */   public String getDbFilterMany() { return this.dbFilterMany; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 504 */   public String getDbOrderBy() { return this.dbOrderBy; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 511 */   public Set<String> getPredicateIncludes() { return this.predicateIncludes; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 518 */   public String getWhereRawSql() { return this.whereRawSql; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 525 */   public String getWhereExpressionSql() { return this.whereExprSql; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 532 */   public String getHavingRawSql() { return this.havingRawSql; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 539 */   public String getHavingExpressionSql() { return this.havingExprSql; }
/*     */ 
/*     */   
/*     */   public String getLogWhereSql() {
/* 543 */     if (this.rawSql) {
/* 544 */       return "";
/*     */     }
/* 546 */     if (this.dbWhere == null && this.dbFilterMany == null) {
/* 547 */       return "";
/*     */     }
/* 549 */     StringBuilder sb = new StringBuilder();
/* 550 */     if (this.dbWhere != null) {
/* 551 */       sb.append(this.dbWhere);
/*     */     }
/* 553 */     if (this.dbFilterMany != null) {
/* 554 */       if (sb.length() > 0) {
/* 555 */         sb.append(" and ");
/*     */       }
/* 557 */       sb.append(this.dbFilterMany);
/*     */     } 
/* 559 */     String logPred = sb.toString();
/* 560 */     if (logPred.length() > 400) {
/* 561 */       logPred = logPred.substring(0, 400) + " ...";
/*     */     }
/* 563 */     return logPred;
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\query\CQueryPredicates.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */