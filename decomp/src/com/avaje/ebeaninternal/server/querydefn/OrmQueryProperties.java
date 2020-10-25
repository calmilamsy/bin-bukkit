/*     */ package com.avaje.ebeaninternal.server.querydefn;
/*     */ 
/*     */ import com.avaje.ebean.ExpressionFactory;
/*     */ import com.avaje.ebean.FetchConfig;
/*     */ import com.avaje.ebean.OrderBy;
/*     */ import com.avaje.ebean.Query;
/*     */ import com.avaje.ebean.event.BeanQueryRequest;
/*     */ import com.avaje.ebeaninternal.api.SpiExpressionFactory;
/*     */ import com.avaje.ebeaninternal.api.SpiExpressionList;
/*     */ import com.avaje.ebeaninternal.api.SpiQuery;
/*     */ import com.avaje.ebeaninternal.server.core.ReferenceOptions;
/*     */ import com.avaje.ebeaninternal.server.expression.FilterExprPath;
/*     */ import com.avaje.ebeaninternal.server.lib.util.StringHelper;
/*     */ import com.avaje.ebeaninternal.server.query.SplitName;
/*     */ import com.avaje.ebeaninternal.util.FilterExpressionList;
/*     */ import java.io.Serializable;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedHashSet;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class OrmQueryProperties
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -8785582703966455658L;
/*     */   private String parentPath;
/*     */   private String path;
/*     */   private String properties;
/*     */   private String trimmedProperties;
/*     */   private int queryFetchBatch;
/*     */   private boolean queryFetchAll;
/*     */   private int lazyFetchBatch;
/*     */   
/*     */   public OrmQueryProperties(String path) {
/*  44 */     this.queryFetchBatch = -1;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  50 */     this.lazyFetchBatch = -1;
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
/*  95 */     this.path = path;
/*  96 */     this.parentPath = SplitName.parent(path);
/*     */   }
/*     */   private FetchConfig fetchConfig; private boolean cache; private boolean readOnly; private Set<String> included; private Set<String> includedBeanJoin; private Set<String> secondaryQueryJoins; private List<OrmQueryProperties> secondaryChildren; private OrderBy orderBy; private SpiExpressionList filterMany;
/*     */   
/* 100 */   public OrmQueryProperties() { this(null); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public OrmQueryProperties(String path, String properties) {
/* 107 */     this(path);
/* 108 */     setProperties(properties);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addSecJoinOrderProperty(OrderBy.Property orderProp) {
/* 116 */     if (this.orderBy == null) {
/* 117 */       this.orderBy = new OrderBy();
/*     */     }
/* 119 */     this.orderBy.add(orderProp);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setFetchConfig(FetchConfig fetchConfig) {
/* 126 */     if (fetchConfig != null) {
/* 127 */       this.fetchConfig = fetchConfig;
/* 128 */       this.lazyFetchBatch = fetchConfig.getLazyBatchSize();
/* 129 */       this.queryFetchBatch = fetchConfig.getQueryBatchSize();
/* 130 */       this.queryFetchAll = fetchConfig.isQueryAll();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/* 135 */   public FetchConfig getFetchConfig() { return this.fetchConfig; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setProperties(String properties) {
/* 145 */     this.properties = properties;
/* 146 */     this.trimmedProperties = properties;
/* 147 */     parseProperties();
/*     */     
/* 149 */     if (!isAllProperties()) {
/* 150 */       Set<String> parsed = parseIncluded(this.trimmedProperties);
/* 151 */       if (parsed.contains("*")) {
/* 152 */         this.included = null;
/*     */       } else {
/* 154 */         this.included = parsed;
/*     */       } 
/*     */     } else {
/* 157 */       this.included = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/* 162 */   private boolean isAllProperties() { return (this.trimmedProperties == null || this.trimmedProperties.length() == 0 || "*".equals(this.trimmedProperties)); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <T> SpiExpressionList<T> filterMany(Query<T> rootQuery) {
/* 171 */     if (this.filterMany == null) {
/* 172 */       FilterExprPath exprPath = new FilterExprPath(this.path);
/* 173 */       SpiExpressionFactory queryEf = (SpiExpressionFactory)rootQuery.getExpressionFactory();
/* 174 */       ExpressionFactory filterEf = queryEf.createExpressionFactory(exprPath);
/* 175 */       this.filterMany = new FilterExpressionList(exprPath, filterEf, rootQuery);
/*     */       
/* 177 */       this.queryFetchAll = true;
/* 178 */       this.queryFetchBatch = 100;
/* 179 */       this.lazyFetchBatch = 100;
/*     */     } 
/* 181 */     return this.filterMany;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SpiExpressionList<?> getFilterManyTrimPath(int trimPath) {
/* 188 */     if (this.filterMany == null) {
/* 189 */       return null;
/*     */     }
/* 191 */     this.filterMany.trimPath(trimPath);
/* 192 */     return this.filterMany;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 199 */   public SpiExpressionList<?> getFilterMany() { return this.filterMany; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 206 */   public void setFilterMany(SpiExpressionList<?> filterMany) { this.filterMany = filterMany; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDefaultProperties(String properties, Set<String> included) {
/* 213 */     this.properties = properties;
/* 214 */     this.trimmedProperties = properties;
/* 215 */     this.included = included;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTunedProperties(OrmQueryProperties tunedProperties) {
/* 222 */     this.properties = tunedProperties.properties;
/* 223 */     this.trimmedProperties = tunedProperties.trimmedProperties;
/* 224 */     this.included = tunedProperties.included;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void configureBeanQuery(SpiQuery<?> query) {
/* 233 */     if (this.trimmedProperties != null && this.trimmedProperties.length() > 0) {
/* 234 */       query.select(this.trimmedProperties);
/* 235 */       if (this.filterMany != null) {
/* 236 */         query.setFilterMany(this.path, this.filterMany);
/*     */       }
/*     */     } 
/*     */     
/* 240 */     if (this.secondaryChildren != null) {
/* 241 */       int trimPath = this.path.length() + 1;
/* 242 */       for (int i = 0; i < this.secondaryChildren.size(); i++) {
/* 243 */         OrmQueryProperties p = (OrmQueryProperties)this.secondaryChildren.get(i);
/* 244 */         String path = p.getPath();
/* 245 */         path = path.substring(trimPath);
/* 246 */         query.fetch(path, p.getProperties(), p.getFetchConfig());
/* 247 */         query.setFilterMany(path, p.getFilterManyTrimPath(trimPath));
/*     */       } 
/*     */     } 
/*     */     
/* 251 */     if (this.orderBy != null) {
/* 252 */       List<OrderBy.Property> orderByProps = this.orderBy.getProperties();
/* 253 */       for (int i = 0; i < orderByProps.size(); i++) {
/* 254 */         ((OrderBy.Property)orderByProps.get(i)).trim(this.path);
/*     */       }
/* 256 */       query.setOrder(this.orderBy);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void configureManyQuery(SpiQuery<?> query) {
/* 266 */     if (this.trimmedProperties != null && this.trimmedProperties.length() > 0) {
/* 267 */       query.fetch(query.getLazyLoadManyPath(), this.trimmedProperties);
/*     */     }
/* 269 */     if (this.filterMany != null) {
/* 270 */       query.setFilterMany(this.path, this.filterMany);
/*     */     }
/* 272 */     if (this.secondaryChildren != null) {
/*     */       
/* 274 */       int trimlen = this.path.length() - query.getLazyLoadManyPath().length();
/*     */       
/* 276 */       for (int i = 0; i < this.secondaryChildren.size(); i++) {
/* 277 */         OrmQueryProperties p = (OrmQueryProperties)this.secondaryChildren.get(i);
/* 278 */         String path = p.getPath();
/* 279 */         path = path.substring(trimlen);
/* 280 */         query.fetch(path, p.getProperties(), p.getFetchConfig());
/* 281 */         query.setFilterMany(path, p.getFilterManyTrimPath(trimlen));
/*     */       } 
/*     */     } 
/*     */     
/* 285 */     if (this.orderBy != null) {
/* 286 */       query.setOrder(this.orderBy);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public OrmQueryProperties copy() {
/* 294 */     OrmQueryProperties copy = new OrmQueryProperties();
/* 295 */     copy.parentPath = this.parentPath;
/* 296 */     copy.path = this.path;
/* 297 */     copy.properties = this.properties;
/* 298 */     copy.cache = this.cache;
/* 299 */     copy.readOnly = this.readOnly;
/* 300 */     copy.queryFetchAll = this.queryFetchAll;
/* 301 */     copy.queryFetchBatch = this.queryFetchBatch;
/* 302 */     copy.lazyFetchBatch = this.lazyFetchBatch;
/* 303 */     copy.filterMany = this.filterMany;
/* 304 */     if (this.included != null) {
/* 305 */       copy.included = new HashSet(this.included);
/*     */     }
/* 307 */     if (this.includedBeanJoin != null) {
/* 308 */       copy.includedBeanJoin = new HashSet(this.includedBeanJoin);
/*     */     }
/* 310 */     return copy;
/*     */   }
/*     */   
/*     */   public boolean hasSelectClause() {
/* 314 */     if ("*".equals(this.trimmedProperties))
/*     */     {
/* 316 */       return true;
/*     */     }
/*     */     
/* 319 */     return (this.included != null);
/*     */   }
/*     */   
/*     */   public String toString() {
/* 323 */     String s = "";
/* 324 */     if (this.path != null) {
/* 325 */       s = s + this.path + " ";
/*     */     }
/* 327 */     if (this.properties != null) {
/* 328 */       s = s + "(" + this.properties + ") ";
/* 329 */     } else if (this.included != null) {
/* 330 */       s = s + "(" + this.included + ") ";
/*     */     } 
/* 332 */     return s;
/*     */   }
/*     */ 
/*     */   
/* 336 */   public boolean isChild(OrmQueryProperties possibleChild) { return possibleChild.getPath().startsWith(this.path + "."); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void add(OrmQueryProperties child) {
/* 343 */     if (this.secondaryChildren == null) {
/* 344 */       this.secondaryChildren = new ArrayList();
/*     */     }
/* 346 */     this.secondaryChildren.add(child);
/*     */   }
/*     */ 
/*     */   
/*     */   public int autofetchPlanHash() {
/* 351 */     int hc = (this.path != null) ? this.path.hashCode() : 1;
/* 352 */     if (this.properties != null) {
/* 353 */       hc = hc * 31 + this.properties.hashCode();
/*     */     } else {
/* 355 */       hc = hc * 31 + ((this.included != null) ? this.included.hashCode() : 1);
/*     */     } 
/*     */     
/* 358 */     return hc;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int queryPlanHash(BeanQueryRequest<?> request) {
/* 367 */     hc = (this.path != null) ? this.path.hashCode() : 1;
/* 368 */     if (this.properties != null) {
/* 369 */       hc = hc * 31 + this.properties.hashCode();
/*     */     } else {
/* 371 */       hc = hc * 31 + ((this.included != null) ? this.included.hashCode() : 1);
/*     */     } 
/* 373 */     return hc * 31 + ((this.filterMany != null) ? this.filterMany.queryPlanHash(request) : 1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 379 */   public String getProperties() { return this.properties; }
/*     */ 
/*     */   
/*     */   public ReferenceOptions getReferenceOptions() {
/* 383 */     if (this.cache || this.readOnly) {
/* 384 */       return new ReferenceOptions(this.cache, this.readOnly, null);
/*     */     }
/* 386 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 394 */   public boolean hasProperties() { return (this.properties != null || this.included != null); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isIncludedBeanJoin(String propertyName) {
/* 405 */     if (this.includedBeanJoin == null) {
/* 406 */       return false;
/*     */     }
/* 408 */     return this.includedBeanJoin.contains(propertyName);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void includeBeanJoin(String propertyName) {
/* 416 */     if (this.includedBeanJoin == null) {
/* 417 */       this.includedBeanJoin = new HashSet();
/*     */     }
/* 419 */     this.includedBeanJoin.add(propertyName);
/*     */   }
/*     */ 
/*     */   
/* 423 */   public boolean allProperties() { return (this.included == null); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Iterator<String> getSelectProperties() {
/* 434 */     if (this.secondaryQueryJoins == null) {
/* 435 */       return this.included.iterator();
/*     */     }
/*     */     
/* 438 */     LinkedHashSet<String> temp = new LinkedHashSet<String>(this.secondaryQueryJoins.size() + this.included.size());
/* 439 */     temp.addAll(this.included);
/* 440 */     temp.addAll(this.secondaryQueryJoins);
/* 441 */     return temp.iterator();
/*     */   }
/*     */   
/*     */   public void addSecondaryQueryJoin(String property) {
/* 445 */     if (this.secondaryQueryJoins == null) {
/* 446 */       this.secondaryQueryJoins = new HashSet(4);
/*     */     }
/* 448 */     this.secondaryQueryJoins.add(property);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Set<String> getAllIncludedProperties() {
/* 458 */     if (this.included == null) {
/* 459 */       return null;
/*     */     }
/*     */     
/* 462 */     if (this.includedBeanJoin == null && this.secondaryQueryJoins == null) {
/* 463 */       return new LinkedHashSet(this.included);
/*     */     }
/*     */     
/* 466 */     LinkedHashSet<String> s = new LinkedHashSet<String>(2 * (this.included.size() + 5));
/* 467 */     if (this.included != null) {
/* 468 */       s.addAll(this.included);
/*     */     }
/* 470 */     if (this.includedBeanJoin != null) {
/* 471 */       s.addAll(this.includedBeanJoin);
/*     */     }
/* 473 */     if (this.secondaryQueryJoins != null) {
/* 474 */       s.addAll(this.secondaryQueryJoins);
/*     */     }
/* 476 */     return s;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isIncluded(String propName) {
/* 481 */     if (this.includedBeanJoin != null && this.includedBeanJoin.contains(propName)) {
/* 482 */       return false;
/*     */     }
/* 484 */     if (this.included == null)
/*     */     {
/* 486 */       return true;
/*     */     }
/* 488 */     return this.included.contains(propName);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public OrmQueryProperties setQueryFetchBatch(int queryFetchBatch) {
/* 499 */     this.queryFetchBatch = queryFetchBatch;
/* 500 */     return this;
/*     */   }
/*     */   
/*     */   public OrmQueryProperties setQueryFetchAll(boolean queryFetchAll) {
/* 504 */     this.queryFetchAll = queryFetchAll;
/* 505 */     return this;
/*     */   }
/*     */   
/*     */   public OrmQueryProperties setQueryFetch(int batch, boolean queryFetchAll) {
/* 509 */     this.queryFetchBatch = batch;
/* 510 */     this.queryFetchAll = queryFetchAll;
/* 511 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public OrmQueryProperties setLazyFetchBatch(int lazyFetchBatch) {
/* 518 */     this.lazyFetchBatch = lazyFetchBatch;
/* 519 */     return this;
/*     */   }
/*     */ 
/*     */   
/* 523 */   public boolean isFetchJoin() { return (!isQueryFetch() && !isLazyFetch()); }
/*     */ 
/*     */ 
/*     */   
/* 527 */   public boolean isQueryFetch() { return (this.queryFetchBatch > -1); }
/*     */ 
/*     */ 
/*     */   
/* 531 */   public int getQueryFetchBatch() { return this.queryFetchBatch; }
/*     */ 
/*     */ 
/*     */   
/* 535 */   public boolean isQueryFetchAll() { return this.queryFetchAll; }
/*     */ 
/*     */ 
/*     */   
/* 539 */   public boolean isLazyFetch() { return (this.lazyFetchBatch > -1); }
/*     */ 
/*     */ 
/*     */   
/* 543 */   public int getLazyFetchBatch() { return this.lazyFetchBatch; }
/*     */ 
/*     */ 
/*     */   
/* 547 */   public boolean isReadOnly() { return this.readOnly; }
/*     */ 
/*     */ 
/*     */   
/* 551 */   public boolean isCache() { return this.cache; }
/*     */ 
/*     */ 
/*     */   
/* 555 */   public String getParentPath() { return this.parentPath; }
/*     */ 
/*     */ 
/*     */   
/* 559 */   public String getPath() { return this.path; }
/*     */ 
/*     */   
/*     */   private void parseProperties() {
/* 563 */     if (this.trimmedProperties == null) {
/*     */       return;
/*     */     }
/* 566 */     int pos = this.trimmedProperties.indexOf("+readonly");
/* 567 */     if (pos > -1) {
/* 568 */       this.trimmedProperties = StringHelper.replaceString(this.trimmedProperties, "+readonly", "");
/* 569 */       this.readOnly = true;
/*     */     } 
/* 571 */     pos = this.trimmedProperties.indexOf("+cache");
/* 572 */     if (pos > -1) {
/* 573 */       this.trimmedProperties = StringHelper.replaceString(this.trimmedProperties, "+cache", "");
/* 574 */       this.cache = true;
/*     */     } 
/* 576 */     pos = this.trimmedProperties.indexOf("+query");
/* 577 */     if (pos > -1) {
/* 578 */       this.queryFetchBatch = parseBatchHint(pos, "+query");
/*     */     }
/* 580 */     pos = this.trimmedProperties.indexOf("+lazy");
/* 581 */     if (pos > -1) {
/* 582 */       this.lazyFetchBatch = parseBatchHint(pos, "+lazy");
/*     */     }
/*     */     
/* 585 */     this.trimmedProperties = this.trimmedProperties.trim();
/* 586 */     while (this.trimmedProperties.startsWith(",")) {
/* 587 */       this.trimmedProperties = this.trimmedProperties.substring(1).trim();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private int parseBatchHint(int pos, String option) {
/* 593 */     int startPos = pos + option.length();
/*     */     
/* 595 */     int endPos = findEndPos(startPos, this.trimmedProperties);
/* 596 */     if (endPos == -1) {
/* 597 */       this.trimmedProperties = StringHelper.replaceString(this.trimmedProperties, option, "");
/* 598 */       return 0;
/*     */     } 
/*     */ 
/*     */     
/* 602 */     String batchParam = this.trimmedProperties.substring(startPos + 1, endPos);
/*     */     
/* 604 */     if (endPos + 1 >= this.trimmedProperties.length()) {
/* 605 */       this.trimmedProperties = this.trimmedProperties.substring(0, pos);
/*     */     } else {
/* 607 */       this.trimmedProperties = this.trimmedProperties.substring(0, pos) + this.trimmedProperties.substring(endPos + 1);
/*     */     } 
/* 609 */     return Integer.parseInt(batchParam);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private int findEndPos(int pos, String props) {
/* 615 */     if (pos < props.length() && 
/* 616 */       props.charAt(pos) == '(') {
/* 617 */       int endPara = props.indexOf(')', pos + 1);
/* 618 */       if (endPara == -1) {
/* 619 */         String m = "Error could not find ')' in " + props + " after position " + pos;
/* 620 */         throw new RuntimeException(m);
/*     */       } 
/* 622 */       return endPara;
/*     */     } 
/*     */     
/* 625 */     return -1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static Set<String> parseIncluded(String rawList) {
/* 633 */     String[] res = rawList.split(",");
/*     */     
/* 635 */     LinkedHashSet<String> set = new LinkedHashSet<String>(res.length + 3);
/*     */     
/* 637 */     String temp = null;
/* 638 */     for (int i = 0; i < res.length; i++) {
/* 639 */       temp = res[i].trim();
/* 640 */       if (temp.length() > 0) {
/* 641 */         set.add(temp);
/*     */       }
/*     */     } 
/*     */     
/* 645 */     if (set.isEmpty()) {
/* 646 */       return null;
/*     */     }
/*     */     
/* 649 */     return Collections.unmodifiableSet(set);
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\querydefn\OrmQueryProperties.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */