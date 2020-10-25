/*     */ package com.avaje.ebeaninternal.server.persist.dml;
/*     */ 
/*     */ import com.avaje.ebeaninternal.api.SpiTransaction;
/*     */ import com.avaje.ebeaninternal.api.SpiUpdatePlan;
/*     */ import com.avaje.ebeaninternal.server.core.PersistRequestBean;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanProperty;
/*     */ import com.avaje.ebeaninternal.server.type.DataBind;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.SQLException;
/*     */ import java.util.Set;
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
/*     */ public class UpdateHandler
/*     */   extends DmlHandler
/*     */ {
/*     */   private final UpdateMeta meta;
/*     */   private Set<String> updatedProperties;
/*     */   private boolean emptySetClause;
/*     */   
/*     */   public UpdateHandler(PersistRequestBean<?> persist, UpdateMeta meta) {
/*  47 */     super(persist, meta.isEmptyStringAsNull());
/*  48 */     this.meta = meta;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void bind() throws SQLException {
/*     */     PreparedStatement pstmt;
/*  56 */     SpiUpdatePlan updatePlan = this.meta.getUpdatePlan(this.persistRequest);
/*     */     
/*  58 */     if (updatePlan.isEmptySetClause()) {
/*  59 */       this.emptySetClause = true;
/*     */       
/*     */       return;
/*     */     } 
/*  63 */     this.updatedProperties = updatePlan.getProperties();
/*     */     
/*  65 */     this.sql = updatePlan.getSql();
/*     */     
/*  67 */     SpiTransaction t = this.persistRequest.getTransaction();
/*  68 */     boolean isBatch = t.isBatchThisRequest();
/*     */ 
/*     */     
/*  71 */     if (isBatch) {
/*  72 */       pstmt = getPstmt(t, this.sql, this.persistRequest, false);
/*     */     } else {
/*     */       
/*  75 */       logSql(this.sql);
/*  76 */       pstmt = getPstmt(t, this.sql, false);
/*     */     } 
/*  78 */     this.dataBind = new DataBind(pstmt);
/*     */     
/*  80 */     bindLogAppend("Binding Update [");
/*  81 */     bindLogAppend(this.meta.getTableName());
/*  82 */     bindLogAppend("] ");
/*     */     
/*  84 */     this.meta.bind(this.persistRequest, this, updatePlan);
/*     */     
/*  86 */     setUpdateGenValues();
/*     */     
/*  88 */     bindLogAppend("]");
/*  89 */     logBinding();
/*     */   }
/*     */ 
/*     */   
/*     */   public void addBatch() throws SQLException {
/*  94 */     if (!this.emptySetClause) {
/*  95 */       super.addBatch();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void execute() throws SQLException {
/* 104 */     if (!this.emptySetClause) {
/* 105 */       int rowCount = this.dataBind.executeUpdate();
/* 106 */       checkRowCount(rowCount);
/* 107 */       setAdditionalProperties();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 114 */   public boolean isIncluded(BeanProperty prop) { return (prop.isDbUpdatable() && (this.updatedProperties == null || this.updatedProperties.contains(prop.getName()))); }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\persist\dml\UpdateHandler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */