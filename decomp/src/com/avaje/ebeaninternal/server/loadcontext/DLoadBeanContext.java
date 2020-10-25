/*     */ package com.avaje.ebeaninternal.server.loadcontext;
/*     */ 
/*     */ import com.avaje.ebean.bean.BeanLoader;
/*     */ import com.avaje.ebean.bean.EntityBeanIntercept;
/*     */ import com.avaje.ebean.bean.ObjectGraphNode;
/*     */ import com.avaje.ebean.bean.PersistenceContext;
/*     */ import com.avaje.ebeaninternal.api.LoadBeanContext;
/*     */ import com.avaje.ebeaninternal.api.LoadBeanRequest;
/*     */ import com.avaje.ebeaninternal.api.LoadContext;
/*     */ import com.avaje.ebeaninternal.api.SpiQuery;
/*     */ import com.avaje.ebeaninternal.server.core.OrmQueryRequest;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
/*     */ import com.avaje.ebeaninternal.server.querydefn.OrmQueryProperties;
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
/*     */ public class DLoadBeanContext
/*     */   implements LoadBeanContext, BeanLoader
/*     */ {
/*     */   protected final DLoadContext parent;
/*     */   protected final BeanDescriptor<?> desc;
/*     */   protected final String path;
/*     */   protected final String fullPath;
/*     */   private final DLoadWeakList<EntityBeanIntercept> weakList;
/*     */   private final OrmQueryProperties queryProps;
/*     */   private int batchSize;
/*     */   
/*     */   public DLoadBeanContext(DLoadContext parent, BeanDescriptor<?> desc, String path, int batchSize, OrmQueryProperties queryProps) {
/*  56 */     this.parent = parent;
/*  57 */     this.desc = desc;
/*  58 */     this.path = path;
/*  59 */     this.batchSize = batchSize;
/*  60 */     this.queryProps = queryProps;
/*  61 */     this.weakList = new DLoadWeakList();
/*     */     
/*  63 */     if (parent.getRelativePath() == null) {
/*  64 */       this.fullPath = path;
/*     */     } else {
/*  66 */       this.fullPath = parent.getRelativePath() + "." + path;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void configureQuery(SpiQuery<?> query, String lazyLoadProperty) {
/*  73 */     query.setParentState(this.parent.getParentState());
/*  74 */     query.setParentNode(getObjectGraphNode());
/*  75 */     query.setLazyLoadProperty(lazyLoadProperty);
/*     */     
/*  77 */     if (this.queryProps != null) {
/*  78 */       this.queryProps.configureBeanQuery(query);
/*     */     }
/*  80 */     if (this.parent.isUseAutofetchManager()) {
/*  81 */       query.setAutofetch(true);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*  86 */   public String getFullPath() { return this.fullPath; }
/*     */ 
/*     */ 
/*     */   
/*  90 */   public PersistenceContext getPersistenceContext() { return this.parent.getPersistenceContext(); }
/*     */ 
/*     */ 
/*     */   
/*  94 */   public OrmQueryProperties getQueryProps() { return this.queryProps; }
/*     */ 
/*     */ 
/*     */   
/*  98 */   public ObjectGraphNode getObjectGraphNode() { return this.parent.getObjectGraphNode(this.path); }
/*     */ 
/*     */ 
/*     */   
/* 102 */   public String getPath() { return this.path; }
/*     */ 
/*     */ 
/*     */   
/* 106 */   public String getName() { return this.parent.getEbeanServer().getName(); }
/*     */ 
/*     */ 
/*     */   
/* 110 */   public int getBatchSize() { return this.batchSize; }
/*     */ 
/*     */ 
/*     */   
/* 114 */   public void setBatchSize(int batchSize) { this.batchSize = batchSize; }
/*     */ 
/*     */ 
/*     */   
/* 118 */   public BeanDescriptor<?> getBeanDescriptor() { return this.desc; }
/*     */ 
/*     */ 
/*     */   
/* 122 */   public LoadContext getGraphContext() { return this.parent; }
/*     */ 
/*     */   
/*     */   public void register(EntityBeanIntercept ebi) {
/* 126 */     int pos = this.weakList.add(ebi);
/* 127 */     ebi.setBeanLoader(pos, this);
/*     */   }
/*     */ 
/*     */   
/*     */   public void loadBean(EntityBeanIntercept ebi) {
/* 132 */     if (this.desc.lazyLoadMany(ebi)) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 137 */     int position = ebi.getBeanLoaderIndex();
/*     */ 
/*     */     
/* 140 */     List<EntityBeanIntercept> batch = this.weakList.getLoadBatch(position, this.batchSize);
/*     */     
/* 142 */     LoadBeanRequest req = new LoadBeanRequest(this, batch, null, this.batchSize, true, ebi.getLazyLoadProperty());
/* 143 */     this.parent.getEbeanServer().loadBean(req);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void loadSecondaryQuery(OrmQueryRequest<?> parentRequest, int requestedBatchSize, boolean all) { do {
/* 149 */       List<EntityBeanIntercept> batch = this.weakList.getNextBatch(requestedBatchSize);
/* 150 */       if (batch.size() == 0) {
/*     */         break;
/*     */       }
/*     */       
/* 154 */       LoadBeanRequest req = new LoadBeanRequest(this, batch, parentRequest.getTransaction(), requestedBatchSize, false, null);
/* 155 */       this.parent.getEbeanServer().loadBean(req);
/* 156 */     } while (all); }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\loadcontext\DLoadBeanContext.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */