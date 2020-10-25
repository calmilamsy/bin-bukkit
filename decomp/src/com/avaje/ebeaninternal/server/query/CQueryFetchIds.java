/*     */ package com.avaje.ebeaninternal.server.query;
/*     */ 
/*     */ import com.avaje.ebean.BackgroundExecutor;
/*     */ import com.avaje.ebean.bean.BeanCollection;
/*     */ import com.avaje.ebean.bean.EntityBeanIntercept;
/*     */ import com.avaje.ebean.bean.PersistenceContext;
/*     */ import com.avaje.ebeaninternal.api.BeanIdList;
/*     */ import com.avaje.ebeaninternal.api.SpiQuery;
/*     */ import com.avaje.ebeaninternal.api.SpiTransaction;
/*     */ import com.avaje.ebeaninternal.server.core.OrmQueryRequest;
/*     */ import com.avaje.ebeaninternal.server.core.ReferenceOptions;
/*     */ import com.avaje.ebeaninternal.server.core.SpiOrmQueryRequest;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanPropertyAssocMany;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanPropertyAssocOne;
/*     */ import com.avaje.ebeaninternal.server.deploy.DbReadContext;
/*     */ import com.avaje.ebeaninternal.server.type.DataBind;
/*     */ import com.avaje.ebeaninternal.server.type.DataReader;
/*     */ import com.avaje.ebeaninternal.server.type.RsetDataReader;
/*     */ import java.sql.Connection;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.FutureTask;
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
/*     */ public class CQueryFetchIds
/*     */ {
/*  58 */   private static final Logger logger = Logger.getLogger(CQueryFetchIds.class.getName());
/*     */ 
/*     */   
/*     */   private final OrmQueryRequest<?> request;
/*     */ 
/*     */   
/*     */   private final BeanDescriptor<?> desc;
/*     */ 
/*     */   
/*     */   private final SpiQuery<?> query;
/*     */ 
/*     */   
/*     */   private final BackgroundExecutor backgroundExecutor;
/*     */ 
/*     */   
/*     */   private final CQueryPredicates predicates;
/*     */ 
/*     */   
/*     */   private final String sql;
/*     */ 
/*     */   
/*     */   private RsetDataReader dataReader;
/*     */ 
/*     */   
/*     */   private PreparedStatement pstmt;
/*     */ 
/*     */   
/*     */   private String bindLog;
/*     */ 
/*     */   
/*     */   private long startNano;
/*     */ 
/*     */   
/*     */   private int executionTimeMicros;
/*     */ 
/*     */   
/*     */   private int rowCount;
/*     */ 
/*     */   
/*     */   private final int maxRows;
/*     */ 
/*     */   
/*     */   private final int bgFetchAfter;
/*     */ 
/*     */ 
/*     */   
/*     */   public CQueryFetchIds(OrmQueryRequest<?> request, CQueryPredicates predicates, String sql, BackgroundExecutor backgroundExecutor) {
/* 105 */     this.backgroundExecutor = backgroundExecutor;
/* 106 */     this.request = request;
/* 107 */     this.query = request.getQuery();
/* 108 */     this.sql = sql;
/* 109 */     this.maxRows = this.query.getMaxRows();
/* 110 */     this.bgFetchAfter = this.query.getBackgroundFetchAfter();
/*     */     
/* 112 */     this.query.setGeneratedSql(sql);
/*     */     
/* 114 */     this.desc = request.getBeanDescriptor();
/* 115 */     this.predicates = predicates;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getSummary() {
/* 123 */     StringBuilder sb = new StringBuilder();
/* 124 */     sb.append("FindIds exeMicros[").append(this.executionTimeMicros).append("] rows[").append(this.rowCount).append("] type[").append(this.desc.getName()).append("] predicates[").append(this.predicates.getLogWhereSql()).append("] bind[").append(this.bindLog).append("]");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 130 */     return sb.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 137 */   public String getBindLog() { return this.bindLog; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 144 */   public String getGeneratedSql() { return this.sql; }
/*     */ 
/*     */ 
/*     */   
/* 148 */   public SpiOrmQueryRequest<?> getQueryRequest() { return this.request; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BeanIdList findIds() throws SQLException {
/* 156 */     useBackgroundToContinueFetch = false;
/*     */     
/* 158 */     this.startNano = System.nanoTime();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 164 */       List<Object> idList = this.query.getIdList();
/* 165 */       if (idList == null) {
/*     */         
/* 167 */         idList = Collections.synchronizedList(new ArrayList());
/* 168 */         this.query.setIdList(idList);
/*     */       } 
/*     */       
/* 171 */       BeanIdList result = new BeanIdList(idList);
/*     */       
/* 173 */       SpiTransaction t = this.request.getTransaction();
/* 174 */       Connection conn = t.getInternalConnection();
/* 175 */       this.pstmt = conn.prepareStatement(this.sql);
/*     */       
/* 177 */       if (this.query.getBufferFetchSizeHint() > 0) {
/* 178 */         this.pstmt.setFetchSize(this.query.getBufferFetchSizeHint());
/*     */       }
/*     */       
/* 181 */       if (this.query.getTimeout() > 0) {
/* 182 */         this.pstmt.setQueryTimeout(this.query.getTimeout());
/*     */       }
/*     */       
/* 185 */       this.bindLog = this.predicates.bind(new DataBind(this.pstmt));
/*     */       
/* 187 */       ResultSet rset = this.pstmt.executeQuery();
/* 188 */       this.dataReader = new RsetDataReader(rset);
/*     */       
/* 190 */       boolean hitMaxRows = false;
/* 191 */       boolean hasMoreRows = false;
/* 192 */       this.rowCount = 0;
/*     */       
/* 194 */       DbReadContext ctx = new DbContext();
/*     */       
/* 196 */       while (rset.next()) {
/* 197 */         Object idValue = this.desc.getIdBinder().read(ctx);
/* 198 */         idList.add(idValue);
/*     */         
/* 200 */         this.dataReader.resetColumnPosition();
/* 201 */         this.rowCount++;
/*     */         
/* 203 */         if (this.maxRows > 0 && this.rowCount == this.maxRows) {
/* 204 */           hitMaxRows = true;
/* 205 */           hasMoreRows = rset.next();
/*     */           break;
/*     */         } 
/* 208 */         if (this.bgFetchAfter > 0 && this.rowCount >= this.bgFetchAfter) {
/* 209 */           useBackgroundToContinueFetch = true;
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/* 214 */       if (hitMaxRows) {
/* 215 */         result.setHasMore(hasMoreRows);
/*     */       }
/*     */       
/* 218 */       if (useBackgroundToContinueFetch) {
/*     */ 
/*     */         
/* 221 */         this.request.setBackgroundFetching();
/*     */ 
/*     */         
/* 224 */         BackgroundIdFetch bgFetch = new BackgroundIdFetch(t, rset, this.pstmt, ctx, this.desc, result);
/* 225 */         FutureTask<Integer> future = new FutureTask<Integer>(bgFetch);
/* 226 */         this.backgroundExecutor.execute(future);
/*     */ 
/*     */         
/* 229 */         result.setBackgroundFetch(future);
/*     */       } 
/*     */       
/* 232 */       long exeNano = System.nanoTime() - this.startNano;
/* 233 */       this.executionTimeMicros = (int)exeNano / 1000;
/*     */       
/* 235 */       return result;
/*     */     } finally {
/*     */       
/* 238 */       if (!useBackgroundToContinueFetch)
/*     */       {
/*     */         
/* 241 */         close();
/*     */       }
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
/*     */   private void close() {
/*     */     try {
/* 255 */       if (this.dataReader != null) {
/* 256 */         this.dataReader.close();
/* 257 */         this.dataReader = null;
/*     */       } 
/* 259 */     } catch (SQLException e) {
/* 260 */       logger.log(Level.SEVERE, null, e);
/*     */     } 
/*     */     try {
/* 263 */       if (this.pstmt != null) {
/* 264 */         this.pstmt.close();
/* 265 */         this.pstmt = null;
/*     */       } 
/* 267 */     } catch (SQLException e) {
/* 268 */       logger.log(Level.SEVERE, null, e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   class DbContext
/*     */     implements DbReadContext
/*     */   {
/* 276 */     public int getParentState() { return 0; }
/*     */ 
/*     */ 
/*     */     
/* 280 */     public void propagateState(Object e) { throw new RuntimeException("Not Called"); }
/*     */ 
/*     */ 
/*     */     
/* 284 */     public SpiQuery.Mode getQueryMode() { return SpiQuery.Mode.NORMAL; }
/*     */ 
/*     */ 
/*     */     
/* 288 */     public DataReader getDataReader() { return CQueryFetchIds.this.dataReader; }
/*     */ 
/*     */ 
/*     */     
/* 292 */     public boolean isVanillaMode() { return false; }
/*     */ 
/*     */ 
/*     */     
/* 296 */     public boolean isSharedInstance() { return false; }
/*     */ 
/*     */ 
/*     */     
/* 300 */     public boolean isReadOnly() { return false; }
/*     */ 
/*     */ 
/*     */     
/* 304 */     public boolean isRawSql() { return false; }
/*     */ 
/*     */ 
/*     */     
/*     */     public void register(String path, EntityBeanIntercept ebi) {}
/*     */ 
/*     */ 
/*     */     
/*     */     public void register(String path, BeanCollection<?> bc) {}
/*     */ 
/*     */     
/* 315 */     public ReferenceOptions getReferenceOptionsFor(BeanPropertyAssocOne<?> beanProp) { return null; }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 320 */     public BeanPropertyAssocMany<?> getManyProperty() { return null; }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 325 */     public PersistenceContext getPersistenceContext() { return null; }
/*     */ 
/*     */ 
/*     */     
/* 329 */     public boolean isAutoFetchProfiling() { return false; }
/*     */     
/*     */     public void profileBean(EntityBeanIntercept ebi, String prefix) {}
/*     */     
/*     */     public void setCurrentPrefix(String currentPrefix, Map<String, String> pathMap) {}
/*     */     
/*     */     public void setLoadedBean(Object loadedBean, Object id) {}
/*     */     
/*     */     public void setLoadedManyBean(Object loadedBean) {}
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\query\CQueryFetchIds.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */