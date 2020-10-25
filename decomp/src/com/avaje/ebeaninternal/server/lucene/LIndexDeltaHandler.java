/*     */ package com.avaje.ebeaninternal.server.lucene;
/*     */ 
/*     */ import com.avaje.ebeaninternal.api.SpiQuery;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
/*     */ import com.avaje.ebeaninternal.server.transaction.BeanDelta;
/*     */ import com.avaje.ebeaninternal.server.transaction.BeanDeltaList;
/*     */ import com.avaje.ebeaninternal.server.transaction.BeanPersistIds;
/*     */ import java.io.IOException;
/*     */ import java.io.Serializable;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.persistence.PersistenceException;
/*     */ import org.apache.lucene.analysis.Analyzer;
/*     */ import org.apache.lucene.document.Document;
/*     */ import org.apache.lucene.index.IndexWriter;
/*     */ import org.apache.lucene.index.Term;
/*     */ import org.apache.lucene.search.BooleanClause;
/*     */ import org.apache.lucene.search.BooleanQuery;
/*     */ import org.apache.lucene.search.IndexSearcher;
/*     */ import org.apache.lucene.search.Query;
/*     */ import org.apache.lucene.search.ScoreDoc;
/*     */ import org.apache.lucene.search.TermQuery;
/*     */ import org.apache.lucene.search.TopDocs;
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
/*     */ public class LIndexDeltaHandler
/*     */ {
/*  54 */   private static final Logger logger = Logger.getLogger(LIndexDeltaHandler.class.getName());
/*     */   
/*     */   private final LIndex index;
/*     */   private final LIndexSearch search;
/*     */   private final IndexSearcher searcher;
/*     */   private final IndexWriter indexWriter;
/*     */   private final Analyzer analyzer;
/*     */   private final BeanDescriptor<?> beanDescriptor;
/*     */   private final DocFieldWriter docFieldWriter;
/*     */   private final IndexUpdates indexUpdates;
/*     */   private final List<BeanDelta> deltaBeans;
/*     */   private final Document document;
/*     */   private Set<Object> deltaBeanKeys;
/*     */   private int deltaCount;
/*     */   private int insertCount;
/*     */   private int updateCount;
/*     */   private int deleteCount;
/*     */   private int deleteByIdCount;
/*     */   
/*     */   public LIndexDeltaHandler(LIndex index, LIndexSearch search, IndexWriter indexWriter, Analyzer analyzer, BeanDescriptor<?> beanDescriptor, DocFieldWriter docFieldWriter, IndexUpdates indexUpdates) {
/*  74 */     this.document = new Document();
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
/*  87 */     this.index = index;
/*  88 */     this.search = search;
/*  89 */     this.searcher = search.getIndexSearcher();
/*  90 */     this.indexWriter = indexWriter;
/*  91 */     this.analyzer = analyzer;
/*  92 */     this.beanDescriptor = beanDescriptor;
/*  93 */     this.docFieldWriter = docFieldWriter;
/*  94 */     this.indexUpdates = indexUpdates;
/*     */     
/*  96 */     BeanDeltaList deltaList = indexUpdates.getDeltaList();
/*  97 */     this.deltaBeans = (deltaList == null) ? null : deltaList.getDeltaBeans();
/*     */   }
/*     */   
/*     */   public int process() {
/* 101 */     this.deltaBeanKeys = processDeltaBeans();
/* 102 */     this.deltaCount = this.deltaBeanKeys.size();
/*     */     
/* 104 */     BeanPersistIds deleteById = this.indexUpdates.getDeleteIds();
/* 105 */     if (deleteById != null) {
/* 106 */       this.deleteByIdCount = processDeletes(deleteById.getDeleteIds());
/*     */     }
/*     */     
/* 109 */     BeanPersistIds beanPersistIds = this.indexUpdates.getBeanPersistIds();
/* 110 */     if (beanPersistIds != null) {
/* 111 */       this.deleteCount = processDeletes(beanPersistIds.getDeleteIds());
/* 112 */       processInserts(beanPersistIds.getInsertIds());
/* 113 */       processUpdates(beanPersistIds.getUpdateIds());
/*     */     } 
/*     */     
/* 116 */     String msg = String.format("Lucene update index %s deltas[%s] insert[%s] update[%s] delete[%s]", new Object[] { this.index, Integer.valueOf(this.deltaCount), Integer.valueOf(this.insertCount), Integer.valueOf(this.updateCount), Integer.valueOf(this.deleteCount + this.deleteByIdCount) });
/*     */ 
/*     */     
/* 119 */     logger.info(msg);
/*     */     
/* 121 */     return this.deltaCount + this.insertCount + this.updateCount + this.deleteCount + this.deleteByIdCount;
/*     */   }
/*     */ 
/*     */   
/*     */   private void processUpdates(List<Serializable> updateIds) {
/* 126 */     if (updateIds == null || updateIds.isEmpty()) {
/*     */       return;
/*     */     }
/*     */     
/* 130 */     ArrayList<Object> filterIdList = new ArrayList<Object>();
/*     */ 
/*     */ 
/*     */     
/* 134 */     for (int i = 0; i < updateIds.size(); i++) {
/* 135 */       Serializable id = (Serializable)updateIds.get(i);
/* 136 */       if (!this.deltaBeanKeys.contains(id)) {
/* 137 */         filterIdList.add(id);
/*     */       }
/*     */     } 
/*     */     
/* 141 */     if (!filterIdList.isEmpty()) {
/* 142 */       SpiQuery<?> ormQuery = this.index.createQuery();
/* 143 */       ormQuery.where().idIn(filterIdList);
/*     */       
/* 145 */       List<?> list = ormQuery.findList();
/* 146 */       for (int i = 0; i < list.size(); i++) {
/* 147 */         Object bean = list.get(i);
/*     */         try {
/* 149 */           Object id = this.beanDescriptor.getId(bean);
/* 150 */           Term term = this.index.createIdTerm(id);
/* 151 */           this.docFieldWriter.writeValue(bean, this.document);
/* 152 */           this.indexWriter.updateDocument(term, this.document);
/*     */         }
/* 154 */         catch (Exception e) {
/* 155 */           throw new PersistenceException(e);
/*     */         } 
/*     */       } 
/* 158 */       this.updateCount = list.size();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void processInserts(List<Serializable> insertIds) {
/* 164 */     if (insertIds == null || insertIds.isEmpty()) {
/*     */       return;
/*     */     }
/* 167 */     SpiQuery<?> ormQuery = this.index.createQuery();
/* 168 */     ormQuery.where().idIn(insertIds);
/* 169 */     List<?> list = ormQuery.findList();
/* 170 */     for (int i = 0; i < list.size(); i++) {
/* 171 */       Object bean = list.get(i);
/*     */       try {
/* 173 */         this.docFieldWriter.writeValue(bean, this.document);
/* 174 */         this.indexWriter.addDocument(this.document);
/*     */       }
/* 176 */       catch (Exception e) {
/* 177 */         throw new PersistenceException(e);
/*     */       } 
/*     */     } 
/* 180 */     this.insertCount = list.size();
/*     */   }
/*     */ 
/*     */   
/*     */   private int processDeletes(List<Serializable> deleteIds) {
/* 185 */     if (deleteIds == null || deleteIds.isEmpty()) {
/* 186 */       return 0;
/*     */     }
/* 188 */     for (int i = 0; i < deleteIds.size(); i++) {
/* 189 */       Serializable id = (Serializable)deleteIds.get(i);
/* 190 */       Term term = this.index.createIdTerm(id);
/*     */       try {
/* 192 */         this.indexWriter.deleteDocuments(term);
/* 193 */       } catch (Exception e) {
/* 194 */         throw new PersistenceLuceneException(e);
/*     */       } 
/*     */     } 
/* 197 */     return deleteIds.size();
/*     */   }
/*     */ 
/*     */   
/*     */   private Set<Object> processDeltaBeans() {
/* 202 */     if (this.deltaBeans == null) {
/* 203 */       return Collections.emptySet();
/*     */     }
/*     */     try {
/* 206 */       LinkedHashMap<Object, Object> beanMap = getBeans();
/*     */       
/* 208 */       for (int i = 0; i < this.deltaBeans.size(); i++) {
/* 209 */         BeanDelta deltaBean = (BeanDelta)this.deltaBeans.get(i);
/* 210 */         Object id = deltaBean.getId();
/* 211 */         Object bean = beanMap.get(id);
/* 212 */         if (bean == null) {
/* 213 */           throw new PersistenceLuceneException("Unmatched bean " + deltaBean.getId());
/*     */         }
/* 215 */         deltaBean.apply(bean);
/* 216 */         this.docFieldWriter.writeValue(bean, this.document);
/*     */         try {
/* 218 */           Term term = this.index.createIdTerm(id);
/* 219 */           this.indexWriter.updateDocument(term, this.document, this.analyzer);
/* 220 */         } catch (Exception e) {
/* 221 */           throw new PersistenceLuceneException(e);
/*     */         } 
/*     */       } 
/*     */       
/* 225 */       return beanMap.keySet();
/*     */     } finally {
/*     */       
/* 228 */       closeResources();
/*     */     } 
/*     */   }
/*     */   
/*     */   private void closeResources() {
/*     */     try {
/* 234 */       this.search.releaseClose();
/* 235 */     } catch (Exception e) {
/* 236 */       logger.log(Level.SEVERE, "Error with IndexReader decRef()", e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private LinkedHashMap<Object, Object> getBeans() {
/* 242 */     Query query = createQuery();
/*     */     
/* 244 */     LinkedHashMap<Object, Object> beanMap = new LinkedHashMap<Object, Object>();
/*     */     
/*     */     try {
/* 247 */       TopDocs topDocs = this.searcher.search(query, this.deltaBeans.size() * 2);
/* 248 */       ScoreDoc[] scoreDocs = topDocs.scoreDocs;
/*     */       
/* 250 */       for (int i = 0; i < scoreDocs.length; i++) {
/* 251 */         int doc = (scoreDocs[i]).doc;
/* 252 */         Document document = this.searcher.doc(doc);
/* 253 */         Object bean = this.index.readDocument(document);
/* 254 */         Object id = this.beanDescriptor.getId(bean);
/* 255 */         beanMap.put(id, bean);
/*     */       } 
/*     */       
/* 258 */       return beanMap;
/*     */     }
/* 260 */     catch (IOException e) {
/* 261 */       throw new RuntimeException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private Query createQuery() {
/* 267 */     BooleanQuery b = new BooleanQuery();
/*     */     
/* 269 */     for (int i = 0; i < this.deltaBeans.size(); i++) {
/* 270 */       BeanDelta d = (BeanDelta)this.deltaBeans.get(i);
/* 271 */       Object id = d.getId();
/* 272 */       Term term = this.index.createIdTerm(id);
/* 273 */       TermQuery tq = new TermQuery(term);
/* 274 */       b.add(tq, BooleanClause.Occur.SHOULD);
/*     */     } 
/*     */     
/* 277 */     return b;
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\lucene\LIndexDeltaHandler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */