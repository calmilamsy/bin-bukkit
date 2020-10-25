/*     */ package com.avaje.ebeaninternal.server.loadcontext;
/*     */ 
/*     */ import com.avaje.ebean.bean.BeanCollection;
/*     */ import com.avaje.ebean.bean.EntityBeanIntercept;
/*     */ import com.avaje.ebean.bean.ObjectGraphNode;
/*     */ import com.avaje.ebean.bean.ObjectGraphOrigin;
/*     */ import com.avaje.ebean.bean.PersistenceContext;
/*     */ import com.avaje.ebeaninternal.api.LoadBeanContext;
/*     */ import com.avaje.ebeaninternal.api.LoadContext;
/*     */ import com.avaje.ebeaninternal.api.LoadManyContext;
/*     */ import com.avaje.ebeaninternal.api.LoadSecondaryQuery;
/*     */ import com.avaje.ebeaninternal.api.SpiEbeanServer;
/*     */ import com.avaje.ebeaninternal.api.SpiQuery;
/*     */ import com.avaje.ebeaninternal.server.core.OrmQueryRequest;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanProperty;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanPropertyAssoc;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanPropertyAssocMany;
/*     */ import com.avaje.ebeaninternal.server.el.ElPropertyValue;
/*     */ import com.avaje.ebeaninternal.server.querydefn.OrmQueryProperties;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
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
/*     */ public class DLoadContext
/*     */   implements LoadContext
/*     */ {
/*     */   private final SpiEbeanServer ebeanServer;
/*     */   private final BeanDescriptor<?> rootDescriptor;
/*     */   private final Map<String, DLoadBeanContext> beanMap;
/*     */   private final Map<String, DLoadManyContext> manyMap;
/*     */   private final DLoadBeanContext rootBeanContext;
/*     */   private final int parentState;
/*     */   private final int defaultBatchSize;
/*     */   private PersistenceContext persistenceContext;
/*     */   private String relativePath;
/*     */   private ObjectGraphOrigin origin;
/*     */   private Map<String, ObjectGraphNode> nodePathMap;
/*     */   private boolean useAutofetchManager;
/*     */   private List<OrmQueryProperties> secQuery;
/*     */   
/*     */   public DLoadContext(SpiEbeanServer ebeanServer, BeanDescriptor<?> rootDescriptor, int defaultBatchSize, int parentState, SpiQuery<?> query) {
/*  54 */     this.beanMap = new HashMap();
/*  55 */     this.manyMap = new HashMap();
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
/*  72 */     this.nodePathMap = new HashMap();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  80 */     this.defaultBatchSize = defaultBatchSize;
/*  81 */     this.ebeanServer = ebeanServer;
/*  82 */     this.rootDescriptor = rootDescriptor;
/*  83 */     this.rootBeanContext = new DLoadBeanContext(this, rootDescriptor, null, defaultBatchSize, null);
/*  84 */     this.parentState = parentState;
/*     */     
/*  86 */     ObjectGraphNode node = query.getParentNode();
/*  87 */     if (node != null) {
/*  88 */       this.origin = node.getOriginQueryPoint();
/*  89 */       this.relativePath = node.getPath();
/*     */     } 
/*     */     
/*  92 */     this.useAutofetchManager = (query.getAutoFetchManager() != null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSecondaryQueriesMinBatchSize(OrmQueryRequest<?> parentRequest, int defaultQueryBatch) {
/* 100 */     if (this.secQuery == null) {
/* 101 */       return -1;
/*     */     }
/*     */     
/* 104 */     int maxBatch = 0;
/* 105 */     for (int i = 0; i < this.secQuery.size(); i++) {
/* 106 */       int batchSize = ((OrmQueryProperties)this.secQuery.get(i)).getQueryFetchBatch();
/* 107 */       if (batchSize == 0) {
/* 108 */         batchSize = defaultQueryBatch;
/*     */       }
/* 110 */       maxBatch = Math.max(maxBatch, batchSize);
/*     */     } 
/* 112 */     return maxBatch;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void executeSecondaryQueries(OrmQueryRequest<?> parentRequest, int defaultQueryBatch) {
/* 120 */     if (this.secQuery != null) {
/* 121 */       for (int i = 0; i < this.secQuery.size(); i++) {
/* 122 */         OrmQueryProperties properties = (OrmQueryProperties)this.secQuery.get(i);
/*     */         
/* 124 */         int batchSize = properties.getQueryFetchBatch();
/* 125 */         if (batchSize == 0) {
/* 126 */           batchSize = defaultQueryBatch;
/*     */         }
/* 128 */         LoadSecondaryQuery load = getLoadSecondaryQuery(properties.getPath());
/* 129 */         load.loadSecondaryQuery(parentRequest, batchSize, properties.isQueryFetchAll());
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private LoadSecondaryQuery getLoadSecondaryQuery(String path) {
/* 138 */     LoadSecondaryQuery beanLoad = (LoadSecondaryQuery)this.beanMap.get(path);
/* 139 */     if (beanLoad == null) {
/* 140 */       beanLoad = (LoadSecondaryQuery)this.manyMap.get(path);
/*     */     }
/* 142 */     return beanLoad;
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
/*     */   
/*     */   public void registerSecondaryQueries(SpiQuery<?> query) {
/* 157 */     this.secQuery = query.removeQueryJoins();
/* 158 */     if (this.secQuery != null) {
/* 159 */       for (int i = 0; i < this.secQuery.size(); i++) {
/* 160 */         OrmQueryProperties props = (OrmQueryProperties)this.secQuery.get(i);
/* 161 */         registerSecondaryQuery(props);
/*     */       } 
/*     */     }
/*     */     
/* 165 */     List<OrmQueryProperties> lazyQueries = query.removeLazyJoins();
/* 166 */     if (lazyQueries != null) {
/* 167 */       for (int i = 0; i < lazyQueries.size(); i++) {
/* 168 */         OrmQueryProperties lazyProps = (OrmQueryProperties)lazyQueries.get(i);
/* 169 */         registerSecondaryQuery(lazyProps);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void registerSecondaryQuery(OrmQueryProperties props) {
/* 180 */     String propName = props.getPath();
/* 181 */     ElPropertyValue elGetValue = this.rootDescriptor.getElGetValue(propName);
/*     */     
/* 183 */     boolean many = elGetValue.getBeanProperty().containsMany();
/* 184 */     registerSecondaryNode(many, props);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ObjectGraphNode getObjectGraphNode(String path) {
/* 190 */     ObjectGraphNode node = (ObjectGraphNode)this.nodePathMap.get(path);
/* 191 */     if (node == null) {
/* 192 */       node = createObjectGraphNode(path);
/* 193 */       this.nodePathMap.put(path, node);
/*     */     } 
/*     */     
/* 196 */     return node;
/*     */   }
/*     */ 
/*     */   
/*     */   private ObjectGraphNode createObjectGraphNode(String path) {
/* 201 */     if (this.relativePath != null) {
/* 202 */       if (path == null) {
/* 203 */         path = this.relativePath;
/*     */       } else {
/* 205 */         path = this.relativePath + "." + path;
/*     */       } 
/*     */     }
/* 208 */     return new ObjectGraphNode(this.origin, path);
/*     */   }
/*     */ 
/*     */   
/* 212 */   public boolean isUseAutofetchManager() { return this.useAutofetchManager; }
/*     */ 
/*     */ 
/*     */   
/* 216 */   public String getRelativePath() { return this.relativePath; }
/*     */ 
/*     */ 
/*     */   
/* 220 */   protected SpiEbeanServer getEbeanServer() { return this.ebeanServer; }
/*     */ 
/*     */ 
/*     */   
/* 224 */   public int getParentState() { return this.parentState; }
/*     */ 
/*     */ 
/*     */   
/* 228 */   public PersistenceContext getPersistenceContext() { return this.persistenceContext; }
/*     */ 
/*     */ 
/*     */   
/* 232 */   public void setPersistenceContext(PersistenceContext persistenceContext) { this.persistenceContext = persistenceContext; }
/*     */ 
/*     */ 
/*     */   
/* 236 */   public void register(String path, EntityBeanIntercept ebi) { getBeanContext(path).register(ebi); }
/*     */ 
/*     */ 
/*     */   
/* 240 */   public void register(String path, BeanCollection<?> bc) { getManyContext(path).register(bc); }
/*     */ 
/*     */   
/*     */   public DLoadBeanContext getBeanContext(String path) {
/* 244 */     if (path == null) {
/* 245 */       return this.rootBeanContext;
/*     */     }
/* 247 */     DLoadBeanContext beanContext = (DLoadBeanContext)this.beanMap.get(path);
/* 248 */     if (beanContext == null) {
/* 249 */       beanContext = createBeanContext(path, this.defaultBatchSize, null);
/* 250 */       this.beanMap.put(path, beanContext);
/*     */     } 
/* 252 */     return beanContext;
/*     */   }
/*     */ 
/*     */   
/*     */   private void registerSecondaryNode(boolean many, OrmQueryProperties props) {
/* 257 */     String path = props.getPath();
/* 258 */     int lazyJoinBatch = props.getLazyFetchBatch();
/* 259 */     int batchSize = (lazyJoinBatch > 0) ? lazyJoinBatch : this.defaultBatchSize;
/*     */     
/* 261 */     if (many) {
/* 262 */       DLoadManyContext manyContext = createManyContext(path, batchSize, props);
/* 263 */       this.manyMap.put(path, manyContext);
/*     */     } else {
/* 265 */       DLoadBeanContext beanContext = createBeanContext(path, batchSize, props);
/* 266 */       this.beanMap.put(path, beanContext);
/*     */     } 
/*     */   }
/*     */   
/*     */   public DLoadManyContext getManyContext(String path) {
/* 271 */     if (path == null) {
/* 272 */       throw new RuntimeException("path is null?");
/*     */     }
/* 274 */     DLoadManyContext ctx = (DLoadManyContext)this.manyMap.get(path);
/* 275 */     if (ctx == null) {
/* 276 */       ctx = createManyContext(path, this.defaultBatchSize, null);
/* 277 */       this.manyMap.put(path, ctx);
/*     */     } 
/* 279 */     return ctx;
/*     */   }
/*     */ 
/*     */   
/*     */   private DLoadManyContext createManyContext(String path, int batchSize, OrmQueryProperties queryProps) {
/* 284 */     BeanPropertyAssocMany<?> p = (BeanPropertyAssocMany)getBeanProperty(this.rootDescriptor, path);
/*     */     
/* 286 */     return new DLoadManyContext(this, p, path, batchSize, queryProps);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private DLoadBeanContext createBeanContext(String path, int batchSize, OrmQueryProperties queryProps) {
/* 292 */     BeanPropertyAssoc<?> p = (BeanPropertyAssoc)getBeanProperty(this.rootDescriptor, path);
/* 293 */     BeanDescriptor<?> targetDescriptor = p.getTargetDescriptor();
/*     */     
/* 295 */     return new DLoadBeanContext(this, targetDescriptor, path, batchSize, queryProps);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 300 */   private BeanProperty getBeanProperty(BeanDescriptor<?> desc, String path) { return desc.getBeanPropertyFromPath(path); }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\loadcontext\DLoadContext.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */