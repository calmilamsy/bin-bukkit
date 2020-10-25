/*     */ package com.avaje.ebeaninternal.server.query;
/*     */ 
/*     */ import com.avaje.ebean.bean.BeanCollection;
/*     */ import com.avaje.ebean.bean.EntityBean;
/*     */ import com.avaje.ebean.bean.EntityBeanIntercept;
/*     */ import com.avaje.ebean.bean.PersistenceContext;
/*     */ import com.avaje.ebeaninternal.api.SpiQuery;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanProperty;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanPropertyAssoc;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanPropertyAssocMany;
/*     */ import com.avaje.ebeaninternal.server.deploy.DbReadContext;
/*     */ import com.avaje.ebeaninternal.server.deploy.DbSqlContext;
/*     */ import com.avaje.ebeaninternal.server.deploy.InheritInfo;
/*     */ import com.avaje.ebeaninternal.server.deploy.TableJoin;
/*     */ import com.avaje.ebeaninternal.server.deploy.id.IdBinder;
/*     */ import com.avaje.ebeaninternal.server.lib.util.StringHelper;
/*     */ import java.sql.SQLException;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
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
/*     */ public class SqlTreeNodeBean
/*     */   implements SqlTreeNode
/*     */ {
/*  49 */   private static final SqlTreeNode[] NO_CHILDREN = new SqlTreeNode[0];
/*     */ 
/*     */ 
/*     */   
/*     */   final BeanDescriptor<?> desc;
/*     */ 
/*     */ 
/*     */   
/*     */   final IdBinder idBinder;
/*     */ 
/*     */ 
/*     */   
/*     */   final SqlTreeNode[] children;
/*     */ 
/*     */ 
/*     */   
/*     */   final boolean readOnlyLeaf;
/*     */ 
/*     */   
/*     */   final boolean partialObject;
/*     */ 
/*     */   
/*     */   final Set<String> partialProps;
/*     */ 
/*     */   
/*     */   final int partialHash;
/*     */ 
/*     */   
/*     */   final BeanProperty[] properties;
/*     */ 
/*     */   
/*     */   final String extraWhere;
/*     */ 
/*     */   
/*     */   final BeanPropertyAssoc<?> nodeBeanProp;
/*     */ 
/*     */   
/*     */   final TableJoin[] tableJoins;
/*     */ 
/*     */   
/*     */   final boolean readId;
/*     */ 
/*     */   
/*     */   final boolean disableLazyLoad;
/*     */ 
/*     */   
/*     */   final InheritInfo inheritInfo;
/*     */ 
/*     */   
/*     */   final String prefix;
/*     */ 
/*     */   
/*     */   final Set<String> includedProps;
/*     */ 
/*     */   
/*     */   final Map<String, String> pathMap;
/*     */ 
/*     */ 
/*     */   
/* 108 */   public SqlTreeNodeBean(String prefix, BeanPropertyAssoc<?> beanProp, SqlTreeProperties props, List<SqlTreeNode> myChildren, boolean withId) { this(prefix, beanProp, beanProp.getTargetDescriptor(), props, myChildren, withId); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SqlTreeNodeBean(String prefix, BeanPropertyAssoc<?> beanProp, BeanDescriptor<?> desc, SqlTreeProperties props, List<SqlTreeNode> myChildren, boolean withId) {
/* 117 */     this.prefix = prefix;
/* 118 */     this.nodeBeanProp = beanProp;
/* 119 */     this.desc = desc;
/* 120 */     this.inheritInfo = desc.getInheritInfo();
/* 121 */     this.extraWhere = (beanProp == null) ? null : beanProp.getExtraWhere();
/*     */     
/* 123 */     this.idBinder = desc.getIdBinder();
/*     */ 
/*     */     
/* 126 */     this.readId = (withId && desc.propertiesId().length > 0);
/* 127 */     this.disableLazyLoad = (!this.readId || desc.isSqlSelectBased());
/*     */     
/* 129 */     this.tableJoins = props.getTableJoins();
/*     */     
/* 131 */     this.partialObject = props.isPartialObject();
/* 132 */     this.partialProps = props.getIncludedProperties();
/* 133 */     this.partialHash = this.partialObject ? this.partialProps.hashCode() : 0;
/*     */     
/* 135 */     this.readOnlyLeaf = props.isReadOnly();
/*     */     
/* 137 */     this.properties = props.getProps();
/*     */     
/* 139 */     if (this.partialObject) {
/*     */ 
/*     */ 
/*     */       
/* 143 */       this.includedProps = LoadedPropertiesCache.get(this.partialHash, this.partialProps, desc);
/*     */     } else {
/* 145 */       this.includedProps = null;
/*     */     } 
/*     */     
/* 148 */     if (myChildren == null) {
/* 149 */       this.children = NO_CHILDREN;
/*     */     } else {
/* 151 */       this.children = (SqlTreeNode[])myChildren.toArray(new SqlTreeNode[myChildren.size()]);
/*     */     } 
/*     */     
/* 154 */     this.pathMap = createPathMap(prefix, desc);
/*     */   }
/*     */ 
/*     */   
/*     */   private Map<String, String> createPathMap(String prefix, BeanDescriptor<?> desc) {
/* 159 */     BeanPropertyAssocMany[] manys = desc.propertiesMany();
/*     */     
/* 161 */     HashMap<String, String> m = new HashMap<String, String>();
/* 162 */     for (int i = 0; i < manys.length; i++) {
/* 163 */       String name = manys[i].getName();
/* 164 */       m.put(name, getPath(prefix, name));
/*     */     } 
/*     */     
/* 167 */     return m;
/*     */   }
/*     */   
/*     */   private String getPath(String prefix, String propertyName) {
/* 171 */     if (prefix == null) {
/* 172 */       return propertyName;
/*     */     }
/* 174 */     return prefix + "." + propertyName;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void postLoad(DbReadContext cquery, Object loadedBean, Object id) {}
/*     */ 
/*     */   
/*     */   public void buildSelectExpressionChain(List<String> selectChain) {
/* 182 */     if (this.readId) {
/* 183 */       this.idBinder.buildSelectExpressionChain(this.prefix, selectChain);
/*     */     }
/* 185 */     for (int i = 0, x = this.properties.length; i < x; i++) {
/* 186 */       this.properties[i].buildSelectExpressionChain(this.prefix, selectChain);
/*     */     }
/*     */     
/* 189 */     for (int i = 0; i < this.children.length; i++)
/*     */     {
/*     */       
/* 192 */       this.children[i].buildSelectExpressionChain(selectChain);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void load(DbReadContext ctx, Object parentBean, int parentState) throws SQLException {
/*     */     Object localBean;
/*     */     IdBinder localIdBinder;
/*     */     BeanDescriptor<?> localDesc;
/*     */     Class<?> localType;
/* 202 */     Object contextBean = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 209 */     if (this.inheritInfo != null) {
/* 210 */       InheritInfo localInfo = this.inheritInfo.readType(ctx);
/* 211 */       if (localInfo == null) {
/*     */         
/* 213 */         localIdBinder = this.idBinder;
/* 214 */         localBean = null;
/* 215 */         localType = null;
/* 216 */         localDesc = this.desc;
/*     */       } else {
/* 218 */         localBean = localInfo.createBean(ctx.isVanillaMode());
/* 219 */         localType = localInfo.getType();
/* 220 */         localIdBinder = localInfo.getIdBinder();
/* 221 */         localDesc = localInfo.getBeanDescriptor();
/*     */       } 
/*     */     } else {
/*     */       
/* 225 */       localType = null;
/* 226 */       localDesc = this.desc;
/* 227 */       localBean = this.desc.createBean(ctx.isVanillaMode());
/* 228 */       localIdBinder = this.idBinder;
/*     */     } 
/*     */     
/* 231 */     SpiQuery.Mode queryMode = ctx.getQueryMode();
/*     */     
/* 233 */     PersistenceContext persistenceContext = ctx.getPersistenceContext();
/*     */     
/* 235 */     Object id = null;
/* 236 */     if (this.readId) {
/*     */ 
/*     */ 
/*     */       
/* 240 */       id = localIdBinder.readSet(ctx, localBean);
/* 241 */       if (id == null) {
/*     */         
/* 243 */         localBean = null;
/*     */       } else {
/*     */         
/* 246 */         contextBean = persistenceContext.putIfAbsent(id, localBean);
/* 247 */         if (contextBean == null) {
/*     */           
/* 249 */           contextBean = localBean;
/*     */         
/*     */         }
/* 252 */         else if (queryMode.isLoadContextBean()) {
/*     */           
/* 254 */           localBean = contextBean;
/* 255 */           if (localBean instanceof EntityBean)
/*     */           {
/* 257 */             ((EntityBean)localBean)._ebean_getIntercept().setIntercepting(false);
/*     */           }
/*     */         } else {
/*     */           
/* 261 */           localBean = null;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 267 */     ctx.setCurrentPrefix(this.prefix, this.pathMap);
/*     */     
/* 269 */     ctx.propagateState(localBean);
/*     */     
/* 271 */     SqlBeanLoad sqlBeanLoad = new SqlBeanLoad(ctx, localType, localBean, queryMode);
/*     */     
/* 273 */     if (this.inheritInfo == null) {
/*     */       
/* 275 */       for (int i = 0, x = this.properties.length; i < x; i++) {
/* 276 */         this.properties[i].load(sqlBeanLoad);
/*     */       
/*     */       }
/*     */     }
/*     */     else {
/*     */       
/* 282 */       for (int i = 0, x = this.properties.length; i < x; i++) {
/*     */         
/* 284 */         BeanProperty p = localDesc.getBeanProperty(this.properties[i].getName());
/* 285 */         if (p != null) {
/* 286 */           p.load(sqlBeanLoad);
/*     */         } else {
/* 288 */           this.properties[i].loadIgnore(ctx);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 293 */     for (int i = 0, x = this.tableJoins.length; i < x; i++) {
/* 294 */       this.tableJoins[i].load(sqlBeanLoad);
/*     */     }
/*     */     
/* 297 */     boolean lazyLoadMany = false;
/* 298 */     if (localBean == null && queryMode.equals(SpiQuery.Mode.LAZYLOAD_MANY)) {
/*     */       
/* 300 */       localBean = contextBean;
/* 301 */       lazyLoadMany = true;
/*     */     } 
/*     */ 
/*     */     
/* 305 */     for (int i = 0; i < this.children.length; i++)
/*     */     {
/*     */       
/* 308 */       this.children[i].load(ctx, localBean, parentState);
/*     */     }
/*     */     
/* 311 */     if (!lazyLoadMany)
/*     */     {
/*     */       
/* 314 */       if (localBean != null) {
/*     */         
/* 316 */         ctx.setCurrentPrefix(this.prefix, this.pathMap);
/* 317 */         if (!ctx.isVanillaMode())
/*     */         {
/*     */           
/* 320 */           createListProxies(localDesc, ctx, localBean);
/*     */         }
/*     */         
/* 323 */         localDesc.postLoad(localBean, this.includedProps);
/*     */         
/* 325 */         if (localBean instanceof EntityBean) {
/* 326 */           EntityBeanIntercept ebi = ((EntityBean)localBean)._ebean_getIntercept();
/* 327 */           ebi.setPersistenceContext(persistenceContext);
/* 328 */           ebi.setLoadedProps(this.includedProps);
/* 329 */           if (SpiQuery.Mode.LAZYLOAD_BEAN.equals(queryMode)) {
/*     */             
/* 331 */             ebi.setLoadedLazy();
/*     */           } else {
/*     */             
/* 334 */             ebi.setLoaded();
/*     */           } 
/*     */           
/* 337 */           if (this.partialObject) {
/* 338 */             ctx.register(null, ebi);
/*     */           }
/*     */           
/* 341 */           if (this.disableLazyLoad)
/*     */           {
/* 343 */             ebi.setDisableLazyLoad(true);
/*     */           }
/* 345 */           if (ctx.isAutoFetchProfiling())
/*     */           {
/* 347 */             ctx.profileBean(ebi, this.prefix);
/*     */           }
/*     */         } 
/*     */       } 
/*     */     }
/* 352 */     if (parentBean != null && contextBean != null)
/*     */     {
/* 354 */       this.nodeBeanProp.setValue(parentBean, contextBean);
/*     */     }
/*     */     
/* 357 */     if (!this.readId) {
/*     */       
/* 359 */       postLoad(ctx, localBean, id);
/*     */     
/*     */     }
/*     */     else {
/*     */ 
/*     */       
/* 365 */       postLoad(ctx, contextBean, id);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void createListProxies(BeanDescriptor<?> localDesc, DbReadContext ctx, Object localBean) {
/* 375 */     BeanPropertyAssocMany<?> fetchedMany = ctx.getManyProperty();
/*     */ 
/*     */     
/* 378 */     BeanPropertyAssocMany[] manys = localDesc.propertiesMany();
/* 379 */     for (int i = 0; i < manys.length; i++) {
/*     */       
/* 381 */       if (fetchedMany == null || !fetchedMany.equals(manys[i])) {
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 386 */         BeanCollection<?> ref = manys[i].createReferenceIfNull(localBean);
/* 387 */         if (ref != null) {
/* 388 */           ctx.register(manys[i].getName(), ref);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void appendSelect(DbSqlContext ctx, boolean subQuery) {
/* 399 */     ctx.pushJoin(this.prefix);
/* 400 */     ctx.pushTableAlias(this.prefix);
/*     */     
/* 402 */     if (this.nodeBeanProp != null) {
/* 403 */       ctx.append('\n').append("        ");
/*     */     }
/*     */     
/* 406 */     if (!subQuery && this.inheritInfo != null) {
/* 407 */       ctx.appendColumn(this.inheritInfo.getDiscriminatorColumn());
/*     */     }
/*     */     
/* 410 */     if (this.readId) {
/* 411 */       appendSelect(ctx, false, this.idBinder.getProperties());
/*     */     }
/* 413 */     appendSelect(ctx, subQuery, this.properties);
/* 414 */     appendSelectTableJoins(ctx);
/*     */     
/* 416 */     for (int i = 0; i < this.children.length; i++)
/*     */     {
/*     */       
/* 419 */       this.children[i].appendSelect(ctx, subQuery);
/*     */     }
/*     */     
/* 422 */     ctx.popTableAlias();
/* 423 */     ctx.popJoin();
/*     */   }
/*     */ 
/*     */   
/*     */   private void appendSelectTableJoins(DbSqlContext ctx) {
/* 428 */     String baseAlias = ctx.getTableAlias(this.prefix);
/*     */     
/* 430 */     for (int i = 0; i < this.tableJoins.length; i++) {
/* 431 */       TableJoin join = this.tableJoins[i];
/*     */       
/* 433 */       String alias = baseAlias + i;
/*     */       
/* 435 */       ctx.pushSecondaryTableAlias(alias);
/* 436 */       join.appendSelect(ctx, false);
/* 437 */       ctx.popTableAlias();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void appendSelect(DbSqlContext ctx, boolean subQuery, BeanProperty[] props) {
/* 446 */     for (int i = 0; i < props.length; i++) {
/* 447 */       props[i].appendSelect(ctx, subQuery);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void appendWhere(DbSqlContext ctx) {
/* 454 */     if (this.inheritInfo != null && 
/* 455 */       !this.inheritInfo.isRoot()) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 462 */       if (ctx.length() > 0) {
/* 463 */         ctx.append(" and");
/*     */       }
/* 465 */       ctx.append(" ").append(ctx.getTableAlias(this.prefix)).append(".");
/* 466 */       ctx.append(this.inheritInfo.getWhere()).append(" ");
/*     */     } 
/*     */     
/* 469 */     if (this.extraWhere != null) {
/* 470 */       if (ctx.length() > 0) {
/* 471 */         ctx.append(" and");
/*     */       }
/* 473 */       String ta = ctx.getTableAlias(this.prefix);
/* 474 */       String ew = StringHelper.replaceString(this.extraWhere, "${ta}", ta);
/* 475 */       ctx.append(" ").append(ew).append(" ");
/*     */     } 
/*     */     
/* 478 */     for (int i = 0; i < this.children.length; i++)
/*     */     {
/*     */       
/* 481 */       this.children[i].appendWhere(ctx);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void appendFrom(DbSqlContext ctx, boolean forceOuterJoin) {
/* 490 */     ctx.pushJoin(this.prefix);
/* 491 */     ctx.pushTableAlias(this.prefix);
/*     */     
/* 493 */     forceOuterJoin = appendFromBaseTable(ctx, forceOuterJoin);
/*     */     
/* 495 */     for (i = 0; i < this.properties.length; i++)
/*     */     {
/* 497 */       this.properties[i].appendFrom(ctx, forceOuterJoin);
/*     */     }
/*     */     
/* 500 */     for (int i = 0; i < this.children.length; i++) {
/* 501 */       this.children[i].appendFrom(ctx, forceOuterJoin);
/*     */     }
/*     */     
/* 504 */     ctx.popTableAlias();
/* 505 */     ctx.popJoin();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean appendFromBaseTable(DbSqlContext ctx, boolean forceOuterJoin) {
/* 514 */     if (this.nodeBeanProp instanceof BeanPropertyAssocMany) {
/* 515 */       BeanPropertyAssocMany<?> manyProp = (BeanPropertyAssocMany)this.nodeBeanProp;
/* 516 */       if (manyProp.isManyToMany()) {
/*     */         
/* 518 */         String alias = ctx.getTableAlias(this.prefix);
/* 519 */         String[] split = SplitName.split(this.prefix);
/* 520 */         String parentAlias = ctx.getTableAlias(split[0]);
/* 521 */         String alias2 = alias + "z_";
/*     */         
/* 523 */         TableJoin manyToManyJoin = manyProp.getIntersectionTableJoin();
/* 524 */         manyToManyJoin.addJoin(forceOuterJoin, parentAlias, alias2, ctx);
/*     */         
/* 526 */         return this.nodeBeanProp.addJoin(forceOuterJoin, alias2, alias, ctx);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 531 */     return this.nodeBeanProp.addJoin(forceOuterJoin, this.prefix, ctx);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 539 */   public String toString() { return "SqlTreeNodeBean: " + this.desc; }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\query\SqlTreeNodeBean.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */