/*    */ package com.avaje.ebeaninternal.server.persist.dml;
/*    */ 
/*    */ import com.avaje.ebeaninternal.api.SpiTransaction;
/*    */ import com.avaje.ebeaninternal.server.core.PersistRequestBean;
/*    */ import com.avaje.ebeaninternal.server.deploy.BeanProperty;
/*    */ import com.avaje.ebeaninternal.server.type.DataBind;
/*    */ import java.sql.PreparedStatement;
/*    */ import java.sql.SQLException;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DeleteHandler
/*    */   extends DmlHandler
/*    */ {
/*    */   private final DeleteMeta meta;
/*    */   
/*    */   public DeleteHandler(PersistRequestBean<?> persist, DeleteMeta meta) {
/* 40 */     super(persist, meta.isEmptyStringAsNull());
/* 41 */     this.meta = meta;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void bind() throws SQLException {
/*    */     PreparedStatement pstmt;
/* 49 */     this.sql = this.meta.getSql(this.persistRequest);
/*    */     
/* 51 */     SpiTransaction t = this.persistRequest.getTransaction();
/* 52 */     boolean isBatch = t.isBatchThisRequest();
/*    */ 
/*    */     
/* 55 */     if (isBatch) {
/* 56 */       pstmt = getPstmt(t, this.sql, this.persistRequest, false);
/*    */     } else {
/*    */       
/* 59 */       logSql(this.sql);
/* 60 */       pstmt = getPstmt(t, this.sql, false);
/*    */     } 
/* 62 */     this.dataBind = new DataBind(pstmt);
/*    */     
/* 64 */     bindLogAppend("Binding Delete [");
/* 65 */     bindLogAppend(this.meta.getTableName());
/* 66 */     bindLogAppend("] where[");
/*    */     
/* 68 */     this.meta.bind(this.persistRequest, this);
/*    */     
/* 70 */     bindLogAppend("]");
/*    */ 
/*    */     
/* 73 */     logBinding();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void execute() throws SQLException {
/* 80 */     int rowCount = this.dataBind.executeUpdate();
/* 81 */     checkRowCount(rowCount);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/* 86 */   public boolean isIncluded(BeanProperty prop) { return (prop.isDbUpdatable() && super.isIncluded(prop)); }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 91 */   public boolean isIncludedWhere(BeanProperty prop) { return (prop.isDbUpdatable() && (this.loadedProps == null || this.loadedProps.contains(prop.getName()))); }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\persist\dml\DeleteHandler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */