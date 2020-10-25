/*     */ package com.avaje.ebeaninternal.server.lucene;
/*     */ 
/*     */ import com.avaje.ebean.config.lucene.IndexDefnBuilder;
/*     */ import com.avaje.ebean.config.lucene.IndexFieldDefn;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanProperty;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanPropertyAssocOne;
/*     */ import com.avaje.ebeaninternal.server.el.ElPropertyValue;
/*     */ import com.avaje.ebeaninternal.server.type.ScalarType;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.List;
/*     */ import org.apache.lucene.document.Field;
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
/*     */ public class LIndexFieldsBuilder
/*     */   implements SpiIndexDefnHelper
/*     */ {
/*     */   private final BeanDescriptor<?> desc;
/*     */   private final LinkedHashMap<String, IndexFieldDefn> fieldDefnMap;
/*     */   
/*     */   public LIndexFieldsBuilder(BeanDescriptor<?> desc) {
/*  45 */     this.fieldDefnMap = new LinkedHashMap();
/*     */ 
/*     */     
/*  48 */     this.desc = desc;
/*     */   }
/*     */ 
/*     */   
/*     */   public Nested assocOne(String propertyName) {
/*  53 */     BeanProperty beanProperty = this.desc.getBeanProperty(propertyName);
/*  54 */     if (beanProperty instanceof BeanPropertyAssocOne) {
/*  55 */       BeanPropertyAssocOne<?> assocOne = (BeanPropertyAssocOne)beanProperty;
/*  56 */       BeanDescriptor<?> targetDescriptor = assocOne.getTargetDescriptor();
/*  57 */       return new Nested(this, propertyName, targetDescriptor);
/*     */     } 
/*     */     
/*  60 */     throw new IllegalArgumentException("Expecing " + propertyName + " to be an AssocOne property of " + this.desc.getFullName());
/*     */   }
/*     */ 
/*     */   
/*     */   public void addAllFields() {
/*  65 */     Iterator<BeanProperty> it = this.desc.propertiesAll();
/*  66 */     while (it.hasNext()) {
/*  67 */       BeanProperty beanProperty = (BeanProperty)it.next();
/*  68 */       if (beanProperty instanceof com.avaje.ebeaninternal.server.deploy.BeanPropertyAssocMany)
/*     */         continue; 
/*  70 */       if (beanProperty instanceof BeanPropertyAssocOne) {
/*     */         continue;
/*     */       }
/*  73 */       addField(beanProperty.getName());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*  79 */   public IndexFieldDefn addField(String propertyName) { return addField(propertyName, null); }
/*     */ 
/*     */ 
/*     */   
/*  83 */   public IndexFieldDefn addField(String propertyName, IndexFieldDefn.Sortable sortable) { return addField(propertyName, null, null, sortable); }
/*     */ 
/*     */ 
/*     */   
/*  87 */   public IndexFieldDefn addFieldConcat(String fieldName, String... propertyNames) { return addFieldConcat(fieldName, Field.Store.NO, Field.Index.ANALYZED, propertyNames); }
/*     */ 
/*     */ 
/*     */   
/*  91 */   public IndexFieldDefn addFieldConcat(String fieldName, Field.Store store, Field.Index index, String... propertyNames) { return addPrefixFieldConcat(null, fieldName, store, index, propertyNames); }
/*     */ 
/*     */   
/*     */   public IndexFieldDefn addPrefixField(String prefix, String propertyName, Field.Store store, Field.Index index, IndexFieldDefn.Sortable sortable) {
/*  95 */     String fullPath = prefix + "." + propertyName;
/*  96 */     return addField(fullPath, store, index, sortable);
/*     */   }
/*     */ 
/*     */   
/*     */   public IndexFieldDefn addPrefixFieldConcat(String prefix, String fieldName, Field.Store store, Field.Index index, String[] propertyNames) {
/* 101 */     if (prefix != null) {
/* 102 */       for (int i = 0; i < propertyNames.length; i++) {
/* 103 */         propertyNames[i] = prefix + "." + propertyNames;
/*     */       }
/*     */     }
/*     */     
/* 107 */     IndexFieldDefn fieldDefn = new IndexFieldDefn(fieldName, store, index, null);
/* 108 */     fieldDefn.setPropertyNames(propertyNames);
/*     */     
/* 110 */     this.fieldDefnMap.put(fieldName, fieldDefn);
/* 111 */     return fieldDefn;
/*     */   }
/*     */   
/*     */   public IndexFieldDefn addField(IndexFieldDefn fieldDefn) {
/* 115 */     this.fieldDefnMap.put(fieldDefn.getName(), fieldDefn);
/* 116 */     return fieldDefn;
/*     */   }
/*     */ 
/*     */   
/*     */   public IndexFieldDefn addField(String propertyName, Field.Store store, Field.Index index, IndexFieldDefn.Sortable sortable) {
/* 121 */     ElPropertyValue prop = this.desc.getElGetValue(propertyName);
/* 122 */     if (prop == null) {
/* 123 */       String msg = "Property [" + propertyName + "] not found on " + this.desc.getFullName();
/* 124 */       throw new NullPointerException(msg);
/*     */     } 
/* 126 */     BeanProperty beanProperty = prop.getBeanProperty();
/* 127 */     ScalarType<?> scalarType = beanProperty.getScalarType();
/*     */     
/* 129 */     if (store == null) {
/* 130 */       store = isLob(scalarType) ? Field.Store.NO : Field.Store.YES;
/*     */     }
/*     */     
/* 133 */     boolean luceneStringType = (beanProperty.isId() || isLuceneString(scalarType.getLuceneType()));
/* 134 */     if (index == null) {
/* 135 */       if (beanProperty.isId() || !luceneStringType) {
/* 136 */         index = Field.Index.NOT_ANALYZED;
/*     */       } else {
/* 138 */         index = Field.Index.ANALYZED;
/*     */       } 
/*     */     }
/*     */     
/* 142 */     IndexFieldDefn fieldDefn = new IndexFieldDefn(propertyName, store, index, sortable);
/* 143 */     this.fieldDefnMap.put(propertyName, fieldDefn);
/*     */     
/* 145 */     if (luceneStringType && index.isAnalyzed() && IndexFieldDefn.Sortable.YES.equals(sortable)) {
/* 146 */       IndexFieldDefn extraFieldDefn = new IndexFieldDefn(propertyName + "_sortonly", Field.Store.NO, Field.Index.NOT_ANALYZED, IndexFieldDefn.Sortable.YES);
/* 147 */       extraFieldDefn.setPropertyName(propertyName);
/* 148 */       this.fieldDefnMap.put(extraFieldDefn.getName(), extraFieldDefn);
/*     */     } 
/*     */     
/* 151 */     return fieldDefn;
/*     */   }
/*     */ 
/*     */   
/* 155 */   public IndexFieldDefn getField(String fieldName) { return (IndexFieldDefn)this.fieldDefnMap.get(fieldName); }
/*     */ 
/*     */ 
/*     */   
/* 159 */   private boolean isLuceneString(int luceneType) { return (0 == luceneType); }
/*     */ 
/*     */   
/*     */   private boolean isLob(ScalarType<?> scalarType) {
/* 163 */     int jdbcType = scalarType.getJdbcType();
/* 164 */     switch (jdbcType) {
/*     */       case 2005:
/* 166 */         return true;
/*     */       case 2004:
/* 168 */         return true;
/*     */       case -1:
/* 170 */         return true;
/*     */       case -4:
/* 172 */         return true;
/*     */     } 
/*     */     
/* 175 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public List<IndexFieldDefn> getFields() {
/* 181 */     ArrayList<IndexFieldDefn> fields = new ArrayList<IndexFieldDefn>();
/* 182 */     fields.addAll(this.fieldDefnMap.values());
/* 183 */     return fields;
/*     */   }
/*     */   
/*     */   private static class Nested
/*     */     implements SpiIndexDefnHelper {
/*     */     private final String path;
/*     */     private final BeanDescriptor<?> targetDescriptor;
/*     */     private final SpiIndexDefnHelper parent;
/*     */     
/*     */     Nested(SpiIndexDefnHelper parent, String path, BeanDescriptor<?> targetDescriptor) {
/* 193 */       this.parent = parent;
/* 194 */       this.path = path;
/* 195 */       this.targetDescriptor = targetDescriptor;
/*     */     }
/*     */ 
/*     */     
/*     */     public IndexDefnBuilder assocOne(String propertyName) {
/* 200 */       BeanProperty beanProperty = this.targetDescriptor.getBeanProperty(propertyName);
/* 201 */       if (beanProperty instanceof BeanPropertyAssocOne) {
/* 202 */         BeanPropertyAssocOne<?> assocOne = (BeanPropertyAssocOne)beanProperty;
/* 203 */         BeanDescriptor<?> targetDescriptor = assocOne.getTargetDescriptor();
/* 204 */         return new Nested(this, propertyName, targetDescriptor);
/*     */       } 
/* 206 */       throw new IllegalArgumentException("Expecing " + propertyName + " to be an AssocOne property of " + this.targetDescriptor.getFullName());
/*     */     }
/*     */ 
/*     */     
/*     */     public void addAllFields() {
/* 211 */       Iterator<BeanProperty> it = this.targetDescriptor.propertiesAll();
/* 212 */       while (it.hasNext()) {
/* 213 */         BeanProperty beanProperty = (BeanProperty)it.next();
/* 214 */         if (beanProperty instanceof com.avaje.ebeaninternal.server.deploy.BeanPropertyAssocMany)
/*     */           continue; 
/* 216 */         if (beanProperty instanceof BeanPropertyAssocOne) {
/*     */           continue;
/*     */         }
/* 219 */         if (!beanProperty.isTransient()) {
/* 220 */           addField(beanProperty.getName());
/*     */         }
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public IndexFieldDefn addField(IndexFieldDefn fieldDefn) {
/* 227 */       this.parent.addField(fieldDefn);
/* 228 */       return fieldDefn;
/*     */     }
/*     */ 
/*     */     
/* 232 */     public IndexFieldDefn addField(String propertyName) { return addField(propertyName, null); }
/*     */ 
/*     */ 
/*     */     
/* 236 */     public IndexFieldDefn addField(String propertyName, IndexFieldDefn.Sortable sortable) { return addField(propertyName, null, null, sortable); }
/*     */ 
/*     */ 
/*     */     
/* 240 */     public IndexFieldDefn addField(String propertyName, Field.Store store, Field.Index index, IndexFieldDefn.Sortable sortable) { return this.parent.addPrefixField(this.path, propertyName, store, index, sortable); }
/*     */ 
/*     */ 
/*     */     
/* 244 */     public IndexFieldDefn addFieldConcat(String fieldName, String... propertyNames) { return addFieldConcat(fieldName, Field.Store.NO, Field.Index.ANALYZED, propertyNames); }
/*     */ 
/*     */ 
/*     */     
/* 248 */     public IndexFieldDefn addFieldConcat(String fieldName, Field.Store store, Field.Index index, String... propertyNames) { return this.parent.addPrefixFieldConcat(this.path, fieldName, store, index, propertyNames); }
/*     */ 
/*     */ 
/*     */     
/*     */     public IndexFieldDefn addPrefixFieldConcat(String prefix, String fieldName, Field.Store store, Field.Index index, String[] propertyNames) {
/* 253 */       String nestedPath = this.path + "." + prefix;
/* 254 */       return this.parent.addPrefixFieldConcat(nestedPath, fieldName, store, index, propertyNames);
/*     */     }
/*     */     
/*     */     public IndexFieldDefn addPrefixField(String prefix, String propertyName, Field.Store store, Field.Index index, IndexFieldDefn.Sortable sortable) {
/* 258 */       String nestedPath = prefix + "." + propertyName;
/* 259 */       return addField(nestedPath, store, index, sortable);
/*     */     }
/*     */ 
/*     */     
/* 263 */     public IndexFieldDefn getField(String fieldName) { return this.parent.getField(fieldName); }
/*     */ 
/*     */ 
/*     */     
/* 267 */     public List<IndexFieldDefn> getFields() { return this.parent.getFields(); }
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\lucene\LIndexFieldsBuilder.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */