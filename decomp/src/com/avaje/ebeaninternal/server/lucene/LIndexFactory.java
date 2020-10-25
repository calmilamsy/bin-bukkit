/*     */ package com.avaje.ebeaninternal.server.lucene;
/*     */ 
/*     */ import com.avaje.ebean.config.lucene.IndexDefn;
/*     */ import com.avaje.ebean.config.lucene.IndexFieldDefn;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanProperty;
/*     */ import com.avaje.ebeaninternal.server.deploy.id.IdBinder;
/*     */ import com.avaje.ebeaninternal.server.el.ElPropertyValue;
/*     */ import com.avaje.ebeaninternal.server.type.ScalarType;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import org.apache.lucene.analysis.Analyzer;
/*     */ import org.apache.lucene.document.Field;
/*     */ import org.apache.lucene.index.IndexWriter;
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
/*     */ public class LIndexFactory
/*     */ {
/*     */   private final DefaultLuceneIndexManager manager;
/*     */   
/*  44 */   public LIndexFactory(DefaultLuceneIndexManager manager) { this.manager = manager; }
/*     */ 
/*     */ 
/*     */   
/*     */   public LIndex create(IndexDefn<?> indexDefn, BeanDescriptor<?> descriptor) throws IOException {
/*  49 */     Analyzer defaultAnalyzer = this.manager.getDefaultAnalyzer();
/*  50 */     return (new Factory(indexDefn, descriptor, this.manager, defaultAnalyzer, null)).create();
/*     */   }
/*     */ 
/*     */   
/*     */   static class Factory
/*     */   {
/*     */     private final Analyzer defaultAnalyzer;
/*     */     
/*     */     private final DefaultLuceneIndexManager manager;
/*     */     private final IndexDefn<?> indexDefn;
/*     */     private final BeanDescriptor<?> descriptor;
/*     */     
/*     */     private Factory(IndexDefn<?> indexDefn, BeanDescriptor<?> descriptor, DefaultLuceneIndexManager manager, Analyzer defaultAnalyzer) {
/*  63 */       this.indexDefn = indexDefn;
/*  64 */       this.descriptor = descriptor;
/*  65 */       this.defaultAnalyzer = defaultAnalyzer;
/*  66 */       this.manager = manager;
/*     */     }
/*     */ 
/*     */     
/*     */     public LIndex create() throws IOException {
/*  71 */       LIndexFieldsBuilder helper = new LIndexFieldsBuilder(this.descriptor);
/*     */       
/*  73 */       this.indexDefn.initialise(helper);
/*     */       
/*  75 */       List<LIndexField> definedFields = new ArrayList<LIndexField>();
/*     */       
/*  77 */       List<IndexFieldDefn> fields = this.indexDefn.getFields();
/*     */       
/*  79 */       for (int i = 0; i < fields.size(); i++) {
/*  80 */         IndexFieldDefn fieldDefn = (IndexFieldDefn)fields.get(i);
/*  81 */         LIndexField field = creatField(fieldDefn);
/*  82 */         definedFields.add(field);
/*     */       } 
/*     */       
/*  85 */       String defaultField = this.indexDefn.getDefaultField();
/*  86 */       LIndexFields fieldGroup = new LIndexFields(definedFields, this.descriptor, defaultField);
/*     */       
/*  88 */       Analyzer analyzer = this.indexDefn.getAnalyzer();
/*  89 */       if (analyzer == null) {
/*  90 */         analyzer = this.defaultAnalyzer;
/*     */       }
/*  92 */       IndexWriter.MaxFieldLength maxFieldLength = this.indexDefn.getMaxFieldLength();
/*  93 */       if (maxFieldLength == null) {
/*  94 */         maxFieldLength = IndexWriter.MaxFieldLength.UNLIMITED;
/*     */       }
/*     */       
/*  97 */       String indexName = this.indexDefn.getClass().getName();
/*  98 */       String indexDir = this.manager.getIndexDirectory(indexName);
/*     */       
/* 100 */       return new LIndex(this.manager, indexName, indexDir, analyzer, maxFieldLength, this.descriptor, fieldGroup, this.indexDefn.getUpdateSinceProperties());
/*     */     }
/*     */     
/*     */     private ElPropertyValue getProperty(String name) {
/* 104 */       ElPropertyValue prop = this.descriptor.getElGetValue(name);
/* 105 */       if (prop == null) {
/* 106 */         String msg = "Property [" + name + "] not found on " + this.descriptor.getFullName();
/* 107 */         throw new NullPointerException(msg);
/*     */       } 
/* 109 */       return prop;
/*     */     }
/*     */ 
/*     */     
/*     */     private LIndexField creatField(IndexFieldDefn fieldDefn) {
/* 114 */       String fieldName = fieldDefn.getName();
/*     */       
/* 116 */       Analyzer queryAnalyzer = getQueryAnalyzer(fieldDefn);
/*     */       
/* 118 */       Field.Store store = fieldDefn.getStore();
/* 119 */       Field.Index index = fieldDefn.getIndex();
/*     */       
/* 121 */       int luceneType = 0;
/*     */       
/* 123 */       Analyzer indexAnalyzer = fieldDefn.getIndexAnalyzer();
/* 124 */       float boost = fieldDefn.getBoost();
/*     */       
/* 126 */       String[] propertyNames = fieldDefn.getPropertyNames();
/* 127 */       if (propertyNames != null && propertyNames.length > 0) {
/*     */         
/* 129 */         ElPropertyValue[] props = new ElPropertyValue[propertyNames.length];
/* 130 */         for (int i = 0; i < props.length; i++) {
/* 131 */           props[i] = getProperty(propertyNames[i]);
/*     */         }
/*     */         
/* 134 */         FieldFactory fieldFactory = FieldFactory.normal(fieldName, store, index, boost);
/* 135 */         return new LIndexFieldStringConcat(queryAnalyzer, fieldName, fieldFactory, props, indexAnalyzer);
/*     */       } 
/*     */       
/* 138 */       ElPropertyValue prop = getProperty(fieldDefn.getPropertyName());
/* 139 */       BeanProperty beanProperty = prop.getBeanProperty();
/* 140 */       ScalarType<?> scalarType = beanProperty.getScalarType();
/* 141 */       luceneType = scalarType.getLuceneType();
/*     */       
/* 143 */       if (beanProperty.isId()) {
/* 144 */         IdBinder idBinder = beanProperty.getBeanDescriptor().getIdBinder();
/* 145 */         FieldFactory fieldFactory = FieldFactory.normal(fieldName, store, index, boost);
/* 146 */         return new LIndexFieldId(queryAnalyzer, fieldName, fieldFactory, prop, idBinder);
/*     */       } 
/*     */       
/* 149 */       if (luceneType == 7) {
/* 150 */         FieldFactory fieldFactory = FieldFactory.normal(fieldName, store, index, boost);
/* 151 */         return new LIndexFieldBinary(queryAnalyzer, fieldName, fieldFactory, prop);
/*     */       } 
/* 153 */       if (luceneType == 0) {
/* 154 */         FieldFactory fieldFactory = FieldFactory.normal(fieldName, store, index, boost);
/* 155 */         return new LIndexFieldString(queryAnalyzer, fieldName, fieldFactory, prop, indexAnalyzer);
/*     */       } 
/*     */ 
/*     */       
/* 159 */       int precisionStep = fieldDefn.getPrecisionStep();
/* 160 */       if (precisionStep < 0) {
/* 161 */         precisionStep = 8;
/*     */       }
/*     */       
/* 164 */       FieldFactory fieldFactory = FieldFactory.numeric(fieldName, store, index, boost, precisionStep);
/* 165 */       return new LIndexFieldNumeric(queryAnalyzer, fieldName, fieldFactory, luceneType, prop);
/*     */     }
/*     */ 
/*     */     
/*     */     private Analyzer getQueryAnalyzer(IndexFieldDefn fieldDefn) {
/* 170 */       Analyzer analyzer = fieldDefn.getQueryAnalyzer();
/* 171 */       if (analyzer == null) {
/* 172 */         analyzer = this.indexDefn.getAnalyzer();
/*     */       }
/* 174 */       return (analyzer == null) ? this.defaultAnalyzer : analyzer;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\lucene\LIndexFactory.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */