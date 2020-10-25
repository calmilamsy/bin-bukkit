/*     */ package com.avaje.ebeaninternal.server.persist.dml;
/*     */ 
/*     */ import com.avaje.ebeaninternal.api.SpiTransaction;
/*     */ import com.avaje.ebeaninternal.server.core.PersistRequestBean;
/*     */ import com.avaje.ebeaninternal.server.core.PstmtBatch;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanProperty;
/*     */ import com.avaje.ebeaninternal.server.persist.BatchedPstmt;
/*     */ import com.avaje.ebeaninternal.server.persist.BatchedPstmtHolder;
/*     */ import com.avaje.ebeaninternal.server.persist.dmlbind.BindableRequest;
/*     */ import com.avaje.ebeaninternal.server.transaction.BeanDelta;
/*     */ import com.avaje.ebeaninternal.server.type.DataBind;
/*     */ import java.sql.Connection;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.SQLException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashSet;
/*     */ import java.util.Set;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.persistence.OptimisticLockException;
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
/*     */ public abstract class DmlHandler
/*     */   implements PersistHandler, BindableRequest
/*     */ {
/*  49 */   protected static final Logger logger = Logger.getLogger(DmlHandler.class.getName());
/*     */ 
/*     */   
/*     */   protected final PersistRequestBean<?> persistRequest;
/*     */ 
/*     */   
/*     */   protected final StringBuilder bindLog;
/*     */ 
/*     */   
/*     */   protected final Set<String> loadedProps;
/*     */ 
/*     */   
/*     */   protected final SpiTransaction transaction;
/*     */ 
/*     */   
/*     */   protected final boolean emptyStringToNull;
/*     */   
/*     */   protected final boolean logLevelSql;
/*     */   
/*     */   protected DataBind dataBind;
/*     */   
/*     */   protected String sql;
/*     */   
/*     */   protected ArrayList<UpdateGenValue> updateGenValues;
/*     */   
/*     */   private Set<String> additionalProps;
/*     */   
/*     */   private boolean checkDelta;
/*     */   
/*     */   private BeanDelta deltaBean;
/*     */ 
/*     */   
/*     */   protected DmlHandler(PersistRequestBean<?> persistRequest, boolean emptyStringToNull) {
/*  82 */     this.persistRequest = persistRequest;
/*  83 */     this.emptyStringToNull = emptyStringToNull;
/*  84 */     this.loadedProps = persistRequest.getLoadedProperties();
/*  85 */     this.transaction = persistRequest.getTransaction();
/*  86 */     this.logLevelSql = this.transaction.isLogSql();
/*  87 */     if (this.logLevelSql) {
/*  88 */       this.bindLog = new StringBuilder();
/*     */     } else {
/*  90 */       this.bindLog = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*  95 */   protected void setCheckDelta(boolean checkDelta) { this.checkDelta = checkDelta; }
/*     */ 
/*     */ 
/*     */   
/*  99 */   public PersistRequestBean<?> getPersistRequest() { return this.persistRequest; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract void bind() throws SQLException;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract void execute() throws SQLException;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void checkRowCount(int rowCount) throws SQLException, OptimisticLockException {
/*     */     try {
/* 117 */       this.persistRequest.checkRowCount(rowCount);
/* 118 */       this.persistRequest.postExecute();
/* 119 */     } catch (OptimisticLockException e) {
/*     */       
/* 121 */       String m = e.getMessage() + " sql[" + this.sql + "] bind[" + this.bindLog + "]";
/* 122 */       this.persistRequest.getTransaction().log("OptimisticLockException:" + m);
/* 123 */       throw new OptimisticLockException(m, null, e.getEntity());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addBatch() throws SQLException {
/* 131 */     PstmtBatch pstmtBatch = this.persistRequest.getPstmtBatch();
/* 132 */     if (pstmtBatch != null) {
/* 133 */       pstmtBatch.addBatch(this.dataBind.getPstmt());
/*     */     } else {
/* 135 */       this.dataBind.getPstmt().addBatch();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void close() throws SQLException {
/*     */     try {
/* 144 */       if (this.dataBind != null) {
/* 145 */         this.dataBind.close();
/*     */       }
/* 147 */     } catch (SQLException ex) {
/* 148 */       logger.log(Level.SEVERE, null, ex);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 156 */   public String getBindLog() { return (this.bindLog == null) ? "" : this.bindLog.toString(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 164 */   public void setIdValue(Object idValue) { this.persistRequest.setBoundId(idValue); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void logBinding() throws SQLException {
/* 171 */     if (this.logLevelSql) {
/* 172 */       this.transaction.logInternal(this.bindLog.toString());
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void logSql(String sql) {
/* 180 */     if (this.logLevelSql) {
/* 181 */       this.transaction.logInternal(sql);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 187 */   public boolean isIncluded(BeanProperty prop) { return (this.loadedProps == null || this.loadedProps.contains(prop.getName())); }
/*     */ 
/*     */   
/*     */   public boolean isIncludedWhere(BeanProperty prop) {
/* 191 */     if (prop.isDbEncrypted())
/*     */     {
/*     */ 
/*     */       
/* 195 */       return isIncluded(prop);
/*     */     }
/* 197 */     return (prop.isDbUpdatable() && (this.loadedProps == null || this.loadedProps.contains(prop.getName())));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object bind(String propName, Object value, int sqlType) throws SQLException {
/* 204 */     if (this.logLevelSql) {
/* 205 */       this.bindLog.append(propName).append("=");
/* 206 */       this.bindLog.append(value).append(", ");
/*     */     } 
/* 208 */     this.dataBind.setObject(value, sqlType);
/* 209 */     return value;
/*     */   }
/*     */   
/*     */   public Object bindNoLog(Object value, int sqlType, String logPlaceHolder) throws SQLException {
/* 213 */     if (this.logLevelSql) {
/* 214 */       this.bindLog.append(logPlaceHolder).append(" ");
/*     */     }
/* 216 */     this.dataBind.setObject(value, sqlType);
/* 217 */     return value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 224 */   public Object bind(Object value, BeanProperty prop, String propName, boolean bindNull) throws SQLException { return bindInternal(this.logLevelSql, value, prop, propName, bindNull); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 231 */   public Object bindNoLog(Object value, BeanProperty prop, String propName, boolean bindNull) throws SQLException { return bindInternal(false, value, prop, propName, bindNull); }
/*     */ 
/*     */ 
/*     */   
/*     */   private Object bindInternal(boolean log, Object value, BeanProperty prop, String propName, boolean bindNull) throws SQLException {
/* 236 */     if (!bindNull && 
/* 237 */       this.emptyStringToNull && value instanceof String && ((String)value).length() == 0)
/*     */     {
/*     */       
/* 240 */       value = null;
/*     */     }
/*     */ 
/*     */     
/* 244 */     if (!bindNull && value == null) {
/*     */       
/* 246 */       if (log) {
/* 247 */         this.bindLog.append(propName).append("=");
/* 248 */         this.bindLog.append("null, ");
/*     */       } 
/*     */     } else {
/* 251 */       if (log) {
/* 252 */         this.bindLog.append(propName).append("=");
/* 253 */         if (prop.isLob()) {
/* 254 */           this.bindLog.append("[LOB]");
/*     */         } else {
/* 256 */           String sv = String.valueOf(value);
/* 257 */           if (sv.length() > 50) {
/* 258 */             sv = sv.substring(0, 47) + "...";
/*     */           }
/* 260 */           this.bindLog.append(sv);
/*     */         } 
/* 262 */         this.bindLog.append(", ");
/*     */       } 
/*     */       
/* 265 */       prop.bind(this.dataBind, value);
/* 266 */       if (this.checkDelta && 
/* 267 */         !prop.isId() && prop.isDeltaRequired()) {
/* 268 */         if (this.deltaBean == null) {
/* 269 */           this.deltaBean = this.persistRequest.createDeltaBean();
/* 270 */           this.transaction.getEvent().addBeanDelta(this.deltaBean);
/*     */         } 
/* 272 */         this.deltaBean.add(prop, value);
/*     */       } 
/*     */     } 
/*     */     
/* 276 */     return value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void bindLogAppend(String comment) {
/* 283 */     if (this.logLevelSql) {
/* 284 */       this.bindLog.append(comment);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void registerAdditionalProperty(String propertyName) {
/* 293 */     if (this.loadedProps != null && !this.loadedProps.contains(propertyName)) {
/* 294 */       if (this.additionalProps == null) {
/* 295 */         this.additionalProps = new HashSet();
/*     */       }
/* 297 */       this.additionalProps.add(propertyName);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void setAdditionalProperties() throws SQLException {
/* 306 */     if (this.additionalProps != null) {
/*     */ 
/*     */       
/* 309 */       this.additionalProps.addAll(this.loadedProps);
/* 310 */       this.persistRequest.setLoadedProps(this.additionalProps);
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
/*     */   
/*     */   public void registerUpdateGenValue(BeanProperty prop, Object bean, Object value) {
/* 324 */     if (this.updateGenValues == null) {
/* 325 */       this.updateGenValues = new ArrayList();
/*     */     }
/* 327 */     this.updateGenValues.add(new UpdateGenValue(prop, bean, value, null));
/* 328 */     registerAdditionalProperty(prop.getName());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setUpdateGenValues() throws SQLException {
/* 338 */     if (this.updateGenValues != null) {
/* 339 */       for (int i = 0; i < this.updateGenValues.size(); i++) {
/* 340 */         UpdateGenValue updGenVal = (UpdateGenValue)this.updateGenValues.get(i);
/* 341 */         updGenVal.setValue();
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected PreparedStatement getPstmt(SpiTransaction t, String sql, boolean genKeys) throws SQLException {
/* 351 */     Connection conn = t.getInternalConnection();
/* 352 */     if (genKeys) {
/*     */ 
/*     */ 
/*     */       
/* 356 */       int[] columns = { 1 };
/* 357 */       return conn.prepareStatement(sql, columns);
/*     */     } 
/*     */     
/* 360 */     return conn.prepareStatement(sql);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected PreparedStatement getPstmt(SpiTransaction t, String sql, PersistRequestBean<?> request, boolean genKeys) throws SQLException {
/* 370 */     BatchedPstmtHolder batch = t.getBatchControl().getPstmtHolder();
/* 371 */     PreparedStatement stmt = batch.getStmt(sql, request);
/*     */     
/* 373 */     if (stmt != null) {
/* 374 */       return stmt;
/*     */     }
/*     */     
/* 377 */     if (this.logLevelSql) {
/* 378 */       t.logInternal(sql);
/*     */     }
/*     */     
/* 381 */     stmt = getPstmt(t, sql, genKeys);
/*     */     
/* 383 */     PstmtBatch pstmtBatch = request.getPstmtBatch();
/* 384 */     if (pstmtBatch != null) {
/* 385 */       pstmtBatch.setBatchSize(stmt, t.getBatchControl().getBatchSize());
/*     */     }
/*     */     
/* 388 */     BatchedPstmt bs = new BatchedPstmt(stmt, genKeys, sql, request.getPstmtBatch(), true);
/* 389 */     batch.addStmt(bs, request);
/* 390 */     return stmt;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static final class UpdateGenValue
/*     */   {
/*     */     private final BeanProperty property;
/*     */ 
/*     */     
/*     */     private final Object bean;
/*     */     
/*     */     private final Object value;
/*     */ 
/*     */     
/*     */     private UpdateGenValue(BeanProperty property, Object bean, Object value) {
/* 406 */       this.property = property;
/* 407 */       this.bean = bean;
/* 408 */       this.value = value;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 416 */     private void setValue() throws SQLException { this.property.setValueIntercept(this.bean, this.value); }
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\persist\dml\DmlHandler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */