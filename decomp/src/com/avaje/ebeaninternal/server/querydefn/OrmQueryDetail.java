/*     */ package com.avaje.ebeaninternal.server.querydefn;
/*     */ 
/*     */ import com.avaje.ebean.FetchConfig;
/*     */ import com.avaje.ebean.event.BeanQueryRequest;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanPropertyAssoc;
/*     */ import com.avaje.ebeaninternal.server.el.ElPropertyDeploy;
/*     */ import com.avaje.ebeaninternal.server.el.ElPropertyValue;
/*     */ import com.avaje.ebeaninternal.server.query.SplitName;
/*     */ import java.io.Serializable;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.LinkedHashSet;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import javax.persistence.PersistenceException;
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
/*     */ public class OrmQueryDetail
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -2510486880141461807L;
/*  41 */   private OrmQueryProperties baseProps = new OrmQueryProperties();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  46 */   private LinkedHashMap<String, OrmQueryProperties> fetchPaths = new LinkedHashMap(8);
/*     */   
/*  48 */   private LinkedHashSet<String> includes = new LinkedHashSet(8);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public OrmQueryDetail copy() {
/*  54 */     OrmQueryDetail copy = new OrmQueryDetail();
/*  55 */     copy.baseProps = this.baseProps.copy();
/*  56 */     for (Map.Entry<String, OrmQueryProperties> entry : this.fetchPaths.entrySet()) {
/*  57 */       copy.fetchPaths.put(entry.getKey(), ((OrmQueryProperties)entry.getValue()).copy());
/*     */     }
/*  59 */     copy.includes = new LinkedHashSet(this.includes);
/*  60 */     return copy;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int queryPlanHash(BeanQueryRequest<?> request) {
/*  68 */     int hc = (this.baseProps == null) ? 1 : this.baseProps.queryPlanHash(request);
/*     */     
/*  70 */     if (this.fetchPaths != null) {
/*  71 */       for (OrmQueryProperties p : this.fetchPaths.values()) {
/*  72 */         hc = hc * 31 + p.queryPlanHash(request);
/*     */       }
/*     */     }
/*     */     
/*  76 */     return hc;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  83 */   public boolean isAutoFetchEqual(OrmQueryDetail otherDetail) { return (autofetchPlanHash() == otherDetail.autofetchPlanHash()); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int autofetchPlanHash() {
/*  91 */     int hc = (this.baseProps == null) ? 1 : this.baseProps.autofetchPlanHash();
/*     */     
/*  93 */     if (this.fetchPaths != null) {
/*  94 */       for (OrmQueryProperties p : this.fetchPaths.values()) {
/*  95 */         hc = hc * 31 + p.autofetchPlanHash();
/*     */       }
/*     */     }
/*     */     
/*  99 */     return hc;
/*     */   }
/*     */   
/*     */   public String toString() {
/* 103 */     StringBuilder sb = new StringBuilder();
/* 104 */     if (this.baseProps != null) {
/* 105 */       sb.append("select ").append(this.baseProps);
/*     */     }
/* 107 */     if (this.fetchPaths != null) {
/* 108 */       for (OrmQueryProperties join : this.fetchPaths.values()) {
/* 109 */         sb.append(" fetch ").append(join);
/*     */       }
/*     */     }
/* 112 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   
/* 116 */   public int hashCode() { throw new RuntimeException("should not use"); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 123 */   public void select(String columns) { this.baseProps = new OrmQueryProperties(null, columns); }
/*     */ 
/*     */   
/*     */   public boolean containsProperty(String property) {
/* 127 */     if (this.baseProps == null) {
/* 128 */       return true;
/*     */     }
/* 130 */     return this.baseProps.isIncluded(property);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 138 */   public void setBase(OrmQueryProperties baseProps) { this.baseProps = baseProps; }
/*     */ 
/*     */ 
/*     */   
/* 142 */   public List<OrmQueryProperties> removeSecondaryQueries() { return removeSecondaryQueries(false); }
/*     */ 
/*     */ 
/*     */   
/* 146 */   public List<OrmQueryProperties> removeSecondaryLazyQueries() { return removeSecondaryQueries(true); }
/*     */ 
/*     */ 
/*     */   
/*     */   private List<OrmQueryProperties> removeSecondaryQueries(boolean lazyQuery) {
/* 151 */     ArrayList<String> matchingPaths = new ArrayList<String>(2);
/*     */     
/* 153 */     for (OrmQueryProperties chunk : this.fetchPaths.values()) {
/* 154 */       boolean match = lazyQuery ? chunk.isLazyFetch() : chunk.isQueryFetch();
/* 155 */       if (match) {
/* 156 */         matchingPaths.add(chunk.getPath());
/*     */       }
/*     */     } 
/*     */     
/* 160 */     if (matchingPaths.size() == 0) {
/* 161 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 165 */     Collections.sort(matchingPaths);
/*     */ 
/*     */     
/* 168 */     ArrayList<OrmQueryProperties> props = new ArrayList<OrmQueryProperties>(2);
/*     */     
/* 170 */     for (i = 0; i < matchingPaths.size(); i++) {
/* 171 */       String path = (String)matchingPaths.get(i);
/* 172 */       this.includes.remove(path);
/* 173 */       OrmQueryProperties secQuery = (OrmQueryProperties)this.fetchPaths.remove(path);
/* 174 */       if (secQuery != null) {
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 179 */         props.add(secQuery);
/*     */ 
/*     */         
/* 182 */         Iterator<OrmQueryProperties> pass2It = this.fetchPaths.values().iterator();
/* 183 */         while (pass2It.hasNext()) {
/* 184 */           OrmQueryProperties pass2Prop = (OrmQueryProperties)pass2It.next();
/* 185 */           if (secQuery.isChild(pass2Prop)) {
/*     */ 
/*     */             
/* 188 */             pass2It.remove();
/* 189 */             this.includes.remove(pass2Prop.getPath());
/* 190 */             secQuery.add(pass2Prop);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 199 */     for (int i = 0; i < props.size(); i++) {
/* 200 */       String path = ((OrmQueryProperties)props.get(i)).getPath();
/*     */       
/* 202 */       String[] split = SplitName.split(path);
/*     */       
/* 204 */       OrmQueryProperties chunk = getChunk(split[0], true);
/* 205 */       chunk.addSecondaryQueryJoin(split[1]);
/*     */     } 
/*     */     
/* 208 */     return props;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean tuneFetchProperties(OrmQueryDetail tunedDetail) {
/* 213 */     boolean tuned = false;
/*     */     
/* 215 */     OrmQueryProperties tunedRoot = tunedDetail.getChunk(null, false);
/* 216 */     if (tunedRoot != null && tunedRoot.hasProperties()) {
/* 217 */       tuned = true;
/* 218 */       this.baseProps.setTunedProperties(tunedRoot);
/*     */       
/* 220 */       for (OrmQueryProperties tunedChunk : tunedDetail.fetchPaths.values()) {
/* 221 */         OrmQueryProperties chunk = getChunk(tunedChunk.getPath(), false);
/* 222 */         if (chunk != null) {
/*     */           
/* 224 */           chunk.setTunedProperties(tunedChunk);
/*     */           continue;
/*     */         } 
/* 227 */         putFetchPath(tunedChunk.copy());
/*     */       } 
/*     */     } 
/*     */     
/* 231 */     return tuned;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void putFetchPath(OrmQueryProperties chunk) {
/* 238 */     String path = chunk.getPath();
/* 239 */     this.fetchPaths.put(path, chunk);
/* 240 */     this.includes.add(path);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clear() {
/* 250 */     this.includes.clear();
/* 251 */     this.fetchPaths.clear();
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
/*     */   public OrmQueryProperties addFetch(String path, String partialProps, FetchConfig fetchConfig) {
/* 264 */     OrmQueryProperties chunk = getChunk(path, true);
/* 265 */     chunk.setProperties(partialProps);
/* 266 */     chunk.setFetchConfig(fetchConfig);
/* 267 */     return chunk;
/*     */   }
/*     */ 
/*     */   
/*     */   public void sortFetchPaths(BeanDescriptor<?> d) {
/* 272 */     LinkedHashMap<String, OrmQueryProperties> sorted = new LinkedHashMap<String, OrmQueryProperties>(this.fetchPaths.size());
/*     */     
/* 274 */     for (OrmQueryProperties p : this.fetchPaths.values()) {
/* 275 */       sortFetchPaths(d, p, sorted);
/*     */     }
/*     */     
/* 278 */     this.fetchPaths = sorted;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void sortFetchPaths(BeanDescriptor<?> d, OrmQueryProperties p, LinkedHashMap<String, OrmQueryProperties> sorted) {
/* 284 */     String path = p.getPath();
/* 285 */     if (!sorted.containsKey(path)) {
/* 286 */       String parentPath = p.getParentPath();
/* 287 */       if (parentPath == null || sorted.containsKey(parentPath)) {
/*     */         
/* 289 */         sorted.put(path, p);
/*     */       } else {
/* 291 */         OrmQueryProperties parentProp = (OrmQueryProperties)this.fetchPaths.get(parentPath);
/* 292 */         if (parentProp == null) {
/* 293 */           ElPropertyValue el = d.getElGetValue(parentPath);
/* 294 */           if (el == null) {
/* 295 */             String msg = "Path [" + parentPath + "] not valid from " + d.getFullName();
/* 296 */             throw new PersistenceException(msg);
/*     */           } 
/*     */           
/* 299 */           BeanPropertyAssoc<?> assocOne = (BeanPropertyAssoc)el.getBeanProperty();
/* 300 */           parentProp = new OrmQueryProperties(parentPath, assocOne.getTargetIdProperty());
/*     */         } 
/* 302 */         if (parentProp != null) {
/* 303 */           sortFetchPaths(d, parentProp, sorted);
/*     */         }
/* 305 */         sorted.put(path, p);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void convertManyFetchJoinsToQueryJoins(BeanDescriptor<?> beanDescriptor, String lazyLoadManyPath, boolean allowOne, int queryBatch) {
/* 316 */     ArrayList<OrmQueryProperties> manyChunks = new ArrayList<OrmQueryProperties>(3);
/*     */ 
/*     */     
/* 319 */     String manyFetchProperty = null;
/*     */ 
/*     */     
/* 322 */     boolean fetchJoinFirstMany = allowOne;
/*     */     
/* 324 */     sortFetchPaths(beanDescriptor);
/*     */     
/* 326 */     for (String fetchPath : this.fetchPaths.keySet()) {
/* 327 */       ElPropertyDeploy elProp = beanDescriptor.getElPropertyDeploy(fetchPath);
/* 328 */       if (elProp.containsManySince(manyFetchProperty)) {
/*     */ 
/*     */         
/* 331 */         OrmQueryProperties chunk = (OrmQueryProperties)this.fetchPaths.get(fetchPath);
/* 332 */         if (chunk.isFetchJoin() && !isLazyLoadManyRoot(lazyLoadManyPath, chunk) && !hasParentSecJoin(lazyLoadManyPath, chunk)) {
/*     */ 
/*     */ 
/*     */           
/* 336 */           if (fetchJoinFirstMany) {
/*     */             
/* 338 */             fetchJoinFirstMany = false;
/* 339 */             manyFetchProperty = fetchPath;
/*     */             continue;
/*     */           } 
/* 342 */           manyChunks.add(chunk);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 348 */     for (int i = 0; i < manyChunks.size(); i++)
/*     */     {
/* 350 */       ((OrmQueryProperties)manyChunks.get(i)).setQueryFetch(queryBatch, true);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isLazyLoadManyRoot(String lazyLoadManyPath, OrmQueryProperties chunk) {
/* 359 */     if (lazyLoadManyPath != null && lazyLoadManyPath.equals(chunk.getPath())) {
/* 360 */       return true;
/*     */     }
/* 362 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean hasParentSecJoin(String lazyLoadManyPath, OrmQueryProperties chunk) {
/* 370 */     OrmQueryProperties parent = getParent(chunk);
/* 371 */     if (parent == null) {
/* 372 */       return false;
/*     */     }
/* 374 */     if (lazyLoadManyPath != null && lazyLoadManyPath.equals(parent.getPath()))
/* 375 */       return false; 
/* 376 */     if (!parent.isFetchJoin()) {
/* 377 */       return true;
/*     */     }
/* 379 */     return hasParentSecJoin(lazyLoadManyPath, parent);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private OrmQueryProperties getParent(OrmQueryProperties chunk) {
/* 388 */     String parentPath = chunk.getParentPath();
/* 389 */     return (parentPath == null) ? null : (OrmQueryProperties)this.fetchPaths.get(parentPath);
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
/*     */   public void setDefaultSelectClause(BeanDescriptor<?> desc) {
/* 402 */     if (desc.hasDefaultSelectClause() && !hasSelectClause()) {
/* 403 */       if (this.baseProps == null) {
/* 404 */         this.baseProps = new OrmQueryProperties();
/*     */       }
/* 406 */       this.baseProps.setDefaultProperties(desc.getDefaultSelectClause(), desc.getDefaultSelectClauseSet());
/*     */     } 
/*     */     
/* 409 */     for (OrmQueryProperties joinProps : this.fetchPaths.values()) {
/* 410 */       if (!joinProps.hasSelectClause()) {
/* 411 */         BeanDescriptor<?> assocDesc = desc.getBeanDescriptor(joinProps.getPath());
/* 412 */         if (assocDesc.hasDefaultSelectClause())
/*     */         {
/* 414 */           joinProps.setDefaultProperties(assocDesc.getDefaultSelectClause(), assocDesc.getDefaultSelectClauseSet());
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/* 421 */   public boolean hasSelectClause() { return (this.baseProps != null && this.baseProps.hasSelectClause()); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 429 */   public boolean isEmpty() { return (this.fetchPaths.isEmpty() && (this.baseProps == null || !this.baseProps.hasProperties())); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 436 */   public boolean isJoinsEmpty() { return this.fetchPaths.isEmpty(); }
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
/*     */   public void includeBeanJoin(String parentPath, String propertyName) {
/* 448 */     OrmQueryProperties parentChunk = getChunk(parentPath, true);
/* 449 */     parentChunk.includeBeanJoin(propertyName);
/*     */   }
/*     */   
/*     */   public OrmQueryProperties getChunk(String path, boolean create) {
/* 453 */     if (path == null) {
/* 454 */       return this.baseProps;
/*     */     }
/* 456 */     OrmQueryProperties props = (OrmQueryProperties)this.fetchPaths.get(path);
/* 457 */     if (create && props == null) {
/* 458 */       props = new OrmQueryProperties(path);
/* 459 */       putFetchPath(props);
/* 460 */       return props;
/*     */     } 
/*     */     
/* 463 */     return props;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean includes(String path) {
/* 472 */     OrmQueryProperties chunk = (OrmQueryProperties)this.fetchPaths.get(path);
/*     */ 
/*     */     
/* 475 */     return (chunk != null && !chunk.isCache());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 482 */   public HashSet<String> getIncludes() { return this.includes; }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\querydefn\OrmQueryDetail.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */