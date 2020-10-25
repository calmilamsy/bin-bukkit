/*     */ package com.avaje.ebeaninternal.server.persist.dml;
/*     */ 
/*     */ import com.avaje.ebeaninternal.api.SpiTransaction;
/*     */ import com.avaje.ebeaninternal.server.core.Message;
/*     */ import com.avaje.ebeaninternal.server.core.PersistRequestBean;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanProperty;
/*     */ import com.avaje.ebeaninternal.server.persist.DmlUtil;
/*     */ import com.avaje.ebeaninternal.server.type.DataBind;
/*     */ import java.sql.Connection;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.util.logging.Level;
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
/*     */ public class InsertHandler
/*     */   extends DmlHandler
/*     */ {
/*     */   private final InsertMeta meta;
/*     */   private final boolean concatinatedKey;
/*     */   private boolean useGeneratedKeys;
/*     */   private String selectLastInsertedId;
/*     */   
/*     */   public InsertHandler(PersistRequestBean<?> persist, InsertMeta meta) {
/*  70 */     super(persist, meta.isEmptyStringToNull());
/*  71 */     this.meta = meta;
/*  72 */     this.concatinatedKey = meta.isConcatinatedKey();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  78 */   public boolean isIncluded(BeanProperty prop) { return (prop.isDbInsertable() && super.isIncluded(prop)); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void bind() throws SQLException {
/*     */     PreparedStatement pstmt;
/*  86 */     BeanDescriptor<?> desc = this.persistRequest.getBeanDescriptor();
/*  87 */     Object bean = this.persistRequest.getBean();
/*     */     
/*  89 */     Object idValue = desc.getId(bean);
/*     */     
/*  91 */     boolean withId = !DmlUtil.isNullOrZero(idValue);
/*     */ 
/*     */     
/*  94 */     if (!withId) {
/*  95 */       if (this.concatinatedKey) {
/*     */ 
/*     */         
/*  98 */         withId = this.meta.deriveConcatenatedId(this.persistRequest);
/*     */       }
/* 100 */       else if (this.meta.supportsGetGeneratedKeys()) {
/*     */         
/* 102 */         this.useGeneratedKeys = true;
/*     */       } else {
/*     */         
/* 105 */         this.selectLastInsertedId = this.meta.getSelectLastInsertedId();
/*     */       } 
/*     */     }
/*     */     
/* 109 */     SpiTransaction t = this.persistRequest.getTransaction();
/* 110 */     boolean isBatch = t.isBatchThisRequest();
/*     */ 
/*     */     
/* 113 */     this.sql = this.meta.getSql(withId);
/*     */ 
/*     */     
/* 116 */     if (isBatch) {
/* 117 */       pstmt = getPstmt(t, this.sql, this.persistRequest, this.useGeneratedKeys);
/*     */     } else {
/*     */       
/* 120 */       logSql(this.sql);
/* 121 */       pstmt = getPstmt(t, this.sql, this.useGeneratedKeys);
/*     */     } 
/* 123 */     this.dataBind = new DataBind(pstmt);
/*     */     
/* 125 */     bindLogAppend("Binding Insert [");
/* 126 */     bindLogAppend(desc.getBaseTable());
/* 127 */     bindLogAppend("]  set[");
/*     */ 
/*     */     
/* 130 */     this.meta.bind(this, bean, withId);
/*     */     
/* 132 */     bindLogAppend("]");
/* 133 */     logBinding();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected PreparedStatement getPstmt(SpiTransaction t, String sql, boolean useGeneratedKeys) throws SQLException {
/* 141 */     Connection conn = t.getInternalConnection();
/* 142 */     if (useGeneratedKeys) {
/* 143 */       return conn.prepareStatement(sql, 1);
/*     */     }
/*     */     
/* 146 */     return conn.prepareStatement(sql);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void execute() throws SQLException {
/* 155 */     int rc = this.dataBind.executeUpdate();
/* 156 */     if (this.useGeneratedKeys) {
/*     */       
/* 158 */       getGeneratedKeys();
/*     */     }
/* 160 */     else if (this.selectLastInsertedId != null) {
/*     */       
/* 162 */       fetchGeneratedKeyUsingSelect();
/*     */     } 
/*     */     
/* 165 */     checkRowCount(rc);
/* 166 */     setAdditionalProperties();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void getGeneratedKeys() throws SQLException {
/* 174 */     rset = this.dataBind.getPstmt().getGeneratedKeys();
/*     */     try {
/* 176 */       if (rset.next()) {
/* 177 */         Object idValue = rset.getObject(1);
/* 178 */         if (idValue != null) {
/* 179 */           this.persistRequest.setGeneratedKey(idValue);
/*     */         }
/*     */       } else {
/*     */         
/* 183 */         throw new PersistenceException(Message.msg("persist.autoinc.norows"));
/*     */       } 
/*     */     } finally {
/*     */       try {
/* 187 */         rset.close();
/* 188 */       } catch (SQLException ex) {
/* 189 */         String msg = "Error closing rset for returning generatedKeys?";
/* 190 */         logger.log(Level.WARNING, msg, ex);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void fetchGeneratedKeyUsingSelect() throws SQLException {
/* 201 */     Connection conn = this.transaction.getConnection();
/*     */     
/* 203 */     stmt = null;
/* 204 */     rset = null;
/*     */     try {
/* 206 */       stmt = conn.prepareStatement(this.selectLastInsertedId);
/* 207 */       rset = stmt.executeQuery();
/* 208 */       if (rset.next()) {
/* 209 */         Object idValue = rset.getObject(1);
/* 210 */         if (idValue != null) {
/* 211 */           this.persistRequest.setGeneratedKey(idValue);
/*     */         }
/*     */       } else {
/* 214 */         throw new PersistenceException(Message.msg("persist.autoinc.norows"));
/*     */       } 
/*     */     } finally {
/*     */       try {
/* 218 */         if (rset != null) {
/* 219 */           rset.close();
/*     */         }
/* 221 */       } catch (SQLException ex) {
/* 222 */         String msg = "Error closing rset for fetchGeneratedKeyUsingSelect?";
/* 223 */         logger.log(Level.WARNING, msg, ex);
/*     */       } 
/*     */       try {
/* 226 */         if (stmt != null) {
/* 227 */           stmt.close();
/*     */         }
/* 229 */       } catch (SQLException ex) {
/* 230 */         String msg = "Error closing stmt for fetchGeneratedKeyUsingSelect?";
/* 231 */         logger.log(Level.WARNING, msg, ex);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\persist\dml\InsertHandler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */