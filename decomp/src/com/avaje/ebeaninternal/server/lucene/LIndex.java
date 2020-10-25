/*     */ package com.avaje.ebeaninternal.server.lucene;
/*     */ 
/*     */ import com.avaje.ebean.config.lucene.IndexUpdateFuture;
/*     */ import com.avaje.ebean.config.lucene.LuceneIndex;
/*     */ import com.avaje.ebeaninternal.api.SpiQuery;
/*     */ import com.avaje.ebeaninternal.api.TransactionEventTable;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
/*     */ import com.avaje.ebeaninternal.server.querydefn.OrmQueryDetail;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import org.apache.lucene.analysis.Analyzer;
/*     */ import org.apache.lucene.document.Document;
/*     */ import org.apache.lucene.index.IndexWriter;
/*     */ import org.apache.lucene.index.Term;
/*     */ import org.apache.lucene.queryParser.QueryParser;
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
/*     */ public class LIndex
/*     */   implements LuceneIndex
/*     */ {
/*     */   private final DefaultLuceneIndexManager manager;
/*     */   private final String name;
/*     */   private final Analyzer analyzer;
/*     */   private final IndexWriter.MaxFieldLength maxFieldLength;
/*     */   private final LIndexFields fieldDefn;
/*     */   private final BeanDescriptor<?> desc;
/*     */   private final OrmQueryDetail ormQueryDetail;
/*     */   private final LIndexIo indexIo;
/*     */   private final LIndexFieldId idField;
/*     */   private final Object syncMonitor;
/*     */   private boolean runningSync;
/*     */   private LIndexSync queuedSync;
/*     */   
/*     */   public LIndex(DefaultLuceneIndexManager manager, String indexName, String indexDir, Analyzer analyzer, IndexWriter.MaxFieldLength maxFieldLength, BeanDescriptor<?> desc, LIndexFields fieldDefn, String[] updateProps) throws IOException {
/*  62 */     this.syncMonitor = new Object();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  71 */     this.manager = manager;
/*  72 */     this.name = desc.getFullName();
/*  73 */     this.analyzer = analyzer;
/*  74 */     this.maxFieldLength = maxFieldLength;
/*  75 */     this.desc = desc;
/*  76 */     this.fieldDefn = fieldDefn;
/*  77 */     this.idField = fieldDefn.getIdField();
/*  78 */     this.ormQueryDetail = fieldDefn.getOrmQueryDetail();
/*     */     
/*  80 */     this.indexIo = new LIndexIo(manager, indexDir, this, updateProps);
/*  81 */     manager.addIndex(this);
/*  82 */     fieldDefn.registerIndexWithProperties(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void syncFinished(boolean success) {
/*  91 */     synchronized (this.syncMonitor) {
/*  92 */       this.runningSync = false;
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
/*     */   public void queueSync(String masterHost) {
/* 105 */     synchronized (this.syncMonitor) {
/* 106 */       LIndexSync sync = new LIndexSync(this, masterHost);
/* 107 */       if (!this.runningSync) {
/*     */         
/* 109 */         this.runningSync = true;
/* 110 */         this.manager.execute(sync);
/*     */       
/*     */       }
/*     */       else {
/*     */ 
/*     */         
/* 116 */         this.queuedSync = sync;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void manage(LuceneIndexManager indexManager) {
/* 126 */     synchronized (this.syncMonitor) {
/* 127 */       this.indexIo.manage(indexManager);
/* 128 */       if (!this.runningSync && this.queuedSync != null) {
/*     */         
/* 130 */         LIndexSync sync = this.queuedSync;
/* 131 */         this.runningSync = true;
/* 132 */         this.queuedSync = null;
/* 133 */         this.manager.execute(sync);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/* 139 */   public void addNotifyCommitRunnable(Runnable r) { this.indexIo.addNotifyCommitRunnable(r); }
/*     */ 
/*     */ 
/*     */   
/* 143 */   public LIndexVersion getLastestVersion() { return this.indexIo.getLastestVersion(); }
/*     */ 
/*     */ 
/*     */   
/* 147 */   public File getIndexDir() { return this.indexIo.getIndexDir(); }
/*     */ 
/*     */ 
/*     */   
/* 151 */   public void refresh(boolean nearRealTime) { this.indexIo.refresh(nearRealTime); }
/*     */ 
/*     */ 
/*     */   
/* 155 */   public LIndexFileInfo getLocalFile(String fileName) { return this.indexIo.getLocalFile(fileName); }
/*     */ 
/*     */ 
/*     */   
/* 159 */   public LIndexCommitInfo obtainLastIndexCommitIfNewer(long remoteIndexVersion) { return this.indexIo.obtainLastIndexCommitIfNewer(remoteIndexVersion); }
/*     */ 
/*     */ 
/*     */   
/* 163 */   public void releaseIndexCommit(long remoteIndexVersion) { this.indexIo.releaseIndexCommit(remoteIndexVersion); }
/*     */ 
/*     */ 
/*     */   
/* 167 */   public LIndexFileInfo getFile(long remoteIndexVersion, String fileName) { return this.indexIo.getFile(remoteIndexVersion, fileName); }
/*     */ 
/*     */ 
/*     */   
/* 171 */   public Term createIdTerm(Object id) { return this.idField.createTerm(id); }
/*     */ 
/*     */ 
/*     */   
/* 175 */   public void shutdown() { this.indexIo.shutdown(); }
/*     */ 
/*     */   
/*     */   public LIndexUpdateFuture rebuild() {
/* 179 */     LIndexUpdateFuture future = new LIndexUpdateFuture(this.desc.getBeanType());
/* 180 */     this.indexIo.addWorkToQueue(LIndexWork.newRebuild(future));
/* 181 */     return future;
/*     */   }
/*     */   
/*     */   public LIndexUpdateFuture update() {
/* 185 */     LIndexUpdateFuture future = new LIndexUpdateFuture(this.desc.getBeanType());
/* 186 */     this.indexIo.addWorkToQueue(LIndexWork.newQueryUpdate(future));
/* 187 */     return future;
/*     */   }
/*     */ 
/*     */   
/* 191 */   public String toString() { return this.name; }
/*     */ 
/*     */ 
/*     */   
/* 195 */   public String getName() { return this.name; }
/*     */ 
/*     */ 
/*     */   
/* 199 */   public Class<?> getBeanType() { return this.desc.getBeanType(); }
/*     */ 
/*     */ 
/*     */   
/* 203 */   public BeanDescriptor<?> getBeanDescriptor() { return this.desc; }
/*     */ 
/*     */ 
/*     */   
/* 207 */   public LIndexSearch getIndexSearch() { return this.indexIo.getIndexSearch(); }
/*     */ 
/*     */ 
/*     */   
/* 211 */   public Analyzer getAnalyzer() { return this.analyzer; }
/*     */ 
/*     */ 
/*     */   
/* 215 */   public IndexWriter.MaxFieldLength getMaxFieldLength() { return this.maxFieldLength; }
/*     */ 
/*     */   
/*     */   public QueryParser createQueryParser(String fieldName) {
/* 219 */     QueryParser p = this.fieldDefn.createQueryParser(fieldName);
/* 220 */     p.setDefaultOperator(QueryParser.Operator.AND);
/* 221 */     return p;
/*     */   }
/*     */ 
/*     */   
/* 225 */   public LIndexFields getIndexFieldDefn() { return this.fieldDefn; }
/*     */ 
/*     */ 
/*     */   
/* 229 */   public Set<String> getResolvePropertyNames() { return this.fieldDefn.getResolvePropertyNames(); }
/*     */ 
/*     */ 
/*     */   
/* 233 */   public OrmQueryDetail getOrmQueryDetail() { return this.ormQueryDetail; }
/*     */ 
/*     */ 
/*     */   
/*     */   public Object readDocument(Document doc) {
/* 238 */     Object bean = this.desc.createEntityBean();
/* 239 */     this.fieldDefn.readDocument(doc, bean);
/* 240 */     return bean;
/*     */   }
/*     */ 
/*     */   
/* 244 */   public DocFieldWriter createDocFieldWriter() { return this.fieldDefn.createDocFieldWriter(); }
/*     */ 
/*     */ 
/*     */   
/* 248 */   public SpiQuery<?> createQuery() { return this.indexIo.createQuery(); }
/*     */ 
/*     */ 
/*     */   
/*     */   public LIndexUpdateFuture process(IndexUpdates indexUpdates) {
/* 253 */     List<TransactionEventTable.TableIUD> tableList = indexUpdates.getTableList();
/* 254 */     if (tableList != null && tableList.size() > 0) {
/* 255 */       boolean bulkDelete = false;
/* 256 */       for (int i = 0; i < tableList.size(); i++) {
/* 257 */         TransactionEventTable.TableIUD bulkTableEvent = (TransactionEventTable.TableIUD)tableList.get(i);
/* 258 */         if (bulkTableEvent.isDelete()) {
/* 259 */           bulkDelete = true;
/*     */         }
/*     */       } 
/* 262 */       if (bulkDelete) {
/* 263 */         return rebuild();
/*     */       }
/* 265 */       return update();
/*     */     } 
/*     */ 
/*     */     
/* 269 */     if (indexUpdates.isInvalidate()) {
/* 270 */       return update();
/*     */     }
/*     */     
/* 273 */     LIndexUpdateFuture f = new LIndexUpdateFuture(this.desc.getBeanType());
/* 274 */     this.indexIo.addWorkToQueue(LIndexWork.newTxnUpdate(f, indexUpdates));
/* 275 */     return f;
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\lucene\LIndex.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */