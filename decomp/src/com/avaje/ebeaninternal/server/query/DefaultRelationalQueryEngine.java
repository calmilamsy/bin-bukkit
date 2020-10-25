/*     */ package com.avaje.ebeaninternal.server.query;
/*     */ 
/*     */ import com.avaje.ebean.SqlQueryListener;
/*     */ import com.avaje.ebean.SqlRow;
/*     */ import com.avaje.ebean.bean.BeanCollection;
/*     */ import com.avaje.ebean.config.GlobalProperties;
/*     */ import com.avaje.ebeaninternal.api.BindParams;
/*     */ import com.avaje.ebeaninternal.api.SpiSqlQuery;
/*     */ import com.avaje.ebeaninternal.api.SpiTransaction;
/*     */ import com.avaje.ebeaninternal.server.core.Message;
/*     */ import com.avaje.ebeaninternal.server.core.RelationalQueryEngine;
/*     */ import com.avaje.ebeaninternal.server.core.RelationalQueryRequest;
/*     */ import com.avaje.ebeaninternal.server.jmx.MAdminLogging;
/*     */ import com.avaje.ebeaninternal.server.persist.Binder;
/*     */ import com.avaje.ebeaninternal.server.type.DataBind;
/*     */ import com.avaje.ebeaninternal.server.util.BindParamsParser;
/*     */ import java.sql.Connection;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.ResultSetMetaData;
/*     */ import java.sql.SQLException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DefaultRelationalQueryEngine
/*     */   implements RelationalQueryEngine
/*     */ {
/*  53 */   private static final Logger logger = Logger.getLogger(DefaultRelationalQueryEngine.class.getName());
/*     */   
/*     */   private final int defaultMaxRows;
/*     */   
/*     */   private final Binder binder;
/*     */   
/*     */   private final String dbTrueValue;
/*     */   
/*     */   public DefaultRelationalQueryEngine(MAdminLogging logControl, Binder binder, String dbTrueValue) {
/*  62 */     this.binder = binder;
/*  63 */     this.defaultMaxRows = GlobalProperties.getInt("nativesql.defaultmaxrows", 100000);
/*  64 */     this.dbTrueValue = (dbTrueValue == null) ? "true" : dbTrueValue;
/*     */   }
/*     */ 
/*     */   
/*     */   public Object findMany(RelationalQueryRequest request) {
/*  69 */     SpiSqlQuery query = request.getQuery();
/*     */     
/*  71 */     long startTime = System.currentTimeMillis();
/*     */     
/*  73 */     SpiTransaction t = request.getTransaction();
/*  74 */     Connection conn = t.getInternalConnection();
/*  75 */     rset = null;
/*  76 */     pstmt = null;
/*     */ 
/*     */     
/*  79 */     useBackgroundToContinueFetch = false;
/*     */     
/*  81 */     String sql = query.getQuery();
/*     */     
/*  83 */     BindParams bindParams = query.getBindParams();
/*     */     
/*  85 */     if (!bindParams.isEmpty())
/*     */     {
/*  87 */       sql = BindParamsParser.parse(bindParams, sql);
/*     */     }
/*     */ 
/*     */     
/*     */     try {
/*  92 */       String bindLog = "";
/*  93 */       String[] propNames = null;
/*     */       
/*  95 */       synchronized (query) {
/*  96 */         if (query.isCancelled()) {
/*  97 */           logger.finest("Query already cancelled");
/*  98 */           return null;
/*     */         } 
/*     */ 
/*     */         
/* 102 */         pstmt = conn.prepareStatement(sql);
/*     */         
/* 104 */         if (query.getTimeout() > 0) {
/* 105 */           pstmt.setQueryTimeout(query.getTimeout());
/*     */         }
/* 107 */         if (query.getBufferFetchSizeHint() > 0) {
/* 108 */           pstmt.setFetchSize(query.getBufferFetchSizeHint());
/*     */         }
/*     */         
/* 111 */         if (!bindParams.isEmpty()) {
/* 112 */           bindLog = this.binder.bind(bindParams, new DataBind(pstmt));
/*     */         }
/*     */         
/* 115 */         if (request.isLogSql()) {
/* 116 */           String sOut = sql.replace('\n', ' ');
/* 117 */           sOut = sOut.replace('\r', ' ');
/* 118 */           t.logInternal(sOut);
/*     */         } 
/*     */         
/* 121 */         rset = pstmt.executeQuery();
/*     */         
/* 123 */         propNames = getPropertyNames(rset);
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 128 */       float initCap = propNames.length / 0.7F;
/* 129 */       int estimateCapacity = (int)initCap + 1;
/*     */ 
/*     */       
/* 132 */       int maxRows = this.defaultMaxRows;
/* 133 */       if (query.getMaxRows() >= 1) {
/* 134 */         maxRows = query.getMaxRows();
/*     */       }
/*     */       
/* 137 */       boolean hasHitMaxRows = false;
/*     */       
/* 139 */       int loadRowCount = 0;
/*     */       
/* 141 */       SqlQueryListener listener = query.getListener();
/*     */       
/* 143 */       BeanCollectionWrapper wrapper = new BeanCollectionWrapper(request);
/* 144 */       boolean isMap = wrapper.isMap();
/* 145 */       String mapKey = query.getMapKey();
/*     */       
/* 147 */       SqlRow bean = null;
/*     */       
/* 149 */       while (rset.next()) {
/* 150 */         synchronized (query) {
/*     */           
/* 152 */           if (!query.isCancelled()) {
/* 153 */             bean = readRow(request, rset, propNames, estimateCapacity);
/*     */           }
/*     */         } 
/* 156 */         if (bean != null) {
/*     */           
/* 158 */           if (listener != null) {
/* 159 */             listener.process(bean);
/*     */           
/*     */           }
/* 162 */           else if (isMap) {
/* 163 */             Object keyValue = bean.get(mapKey);
/* 164 */             wrapper.addToMap(bean, keyValue);
/*     */           } else {
/* 166 */             wrapper.addToCollection(bean);
/*     */           } 
/*     */ 
/*     */           
/* 170 */           loadRowCount++;
/*     */           
/* 172 */           if (loadRowCount == maxRows) {
/*     */             
/* 174 */             hasHitMaxRows = true;
/*     */             
/*     */             break;
/*     */           } 
/*     */         } 
/*     */       } 
/* 180 */       BeanCollection<?> beanColl = wrapper.getBeanCollection();
/*     */       
/* 182 */       if (hasHitMaxRows && 
/* 183 */         rset.next())
/*     */       {
/* 185 */         beanColl.setHasMoreRows(true);
/*     */       }
/*     */ 
/*     */       
/* 189 */       if (!useBackgroundToContinueFetch) {
/* 190 */         beanColl.setFinishedFetch(true);
/*     */       }
/*     */       
/* 193 */       if (request.isLogSummary()) {
/*     */         
/* 195 */         long exeTime = System.currentTimeMillis() - startTime;
/*     */         
/* 197 */         String msg = "SqlQuery  rows[" + loadRowCount + "] time[" + exeTime + "] bind[" + bindLog + "] finished[" + beanColl.isFinishedFetch() + "]";
/*     */ 
/*     */         
/* 200 */         t.logInternal(msg);
/*     */       } 
/*     */       
/* 203 */       if (query.isCancelled()) {
/* 204 */         logger.fine("Query was cancelled during execution rows:" + loadRowCount);
/*     */       }
/*     */       
/* 207 */       return beanColl;
/*     */     }
/* 209 */     catch (Exception e) {
/* 210 */       String m = Message.msg("fetch.error", e.getMessage(), sql);
/* 211 */       throw new PersistenceException(m, e);
/*     */     } finally {
/*     */       
/* 214 */       if (!useBackgroundToContinueFetch) {
/*     */         try {
/* 216 */           if (rset != null) {
/* 217 */             rset.close();
/*     */           }
/* 219 */         } catch (SQLException e) {
/* 220 */           logger.log(Level.SEVERE, null, e);
/*     */         } 
/*     */         try {
/* 223 */           if (pstmt != null) {
/* 224 */             pstmt.close();
/*     */           }
/* 226 */         } catch (SQLException e) {
/* 227 */           logger.log(Level.SEVERE, null, e);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String[] getPropertyNames(ResultSet rset) throws SQLException {
/* 238 */     ArrayList<String> propNames = new ArrayList<String>();
/*     */     
/* 240 */     ResultSetMetaData rsmd = rset.getMetaData();
/*     */     
/* 242 */     int columnsPlusOne = rsmd.getColumnCount() + 1;
/*     */ 
/*     */     
/* 245 */     for (int i = 1; i < columnsPlusOne; i++) {
/* 246 */       String columnName = rsmd.getColumnLabel(i);
/*     */       
/* 248 */       propNames.add(columnName);
/*     */     } 
/*     */     
/* 251 */     return (String[])propNames.toArray(new String[propNames.size()]);
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
/*     */   protected SqlRow readRow(RelationalQueryRequest request, ResultSet rset, String[] propNames, int initialCapacity) throws SQLException {
/* 264 */     SqlRow bean = new DefaultSqlRow(initialCapacity, 0.75F, this.dbTrueValue);
/*     */     
/* 266 */     int index = 0;
/*     */     
/* 268 */     for (int i = 0; i < propNames.length; i++) {
/* 269 */       index++;
/* 270 */       Object value = rset.getObject(index);
/* 271 */       bean.set(propNames[i], value);
/*     */     } 
/*     */     
/* 274 */     return bean;
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\query\DefaultRelationalQueryEngine.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */