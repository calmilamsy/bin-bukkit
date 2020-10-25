/*     */ package com.avaje.ebeaninternal.server.persist.dml;
/*     */ 
/*     */ import com.avaje.ebeaninternal.server.core.ConcurrencyMode;
/*     */ import com.avaje.ebeaninternal.server.core.PersistRequestBean;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
/*     */ import com.avaje.ebeaninternal.server.persist.dmlbind.Bindable;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class DeleteMeta
/*     */ {
/*     */   private final String sqlVersion;
/*     */   private final String sqlNone;
/*     */   private final Bindable id;
/*     */   private final Bindable version;
/*     */   private final Bindable all;
/*     */   private final String tableName;
/*     */   private final boolean emptyStringAsNull;
/*     */   
/*     */   public DeleteMeta(boolean emptyStringAsNull, BeanDescriptor<?> desc, Bindable id, Bindable version, Bindable all) {
/*  51 */     this.emptyStringAsNull = emptyStringAsNull;
/*  52 */     this.tableName = desc.getBaseTable();
/*  53 */     this.id = id;
/*  54 */     this.version = version;
/*  55 */     this.all = all;
/*     */     
/*  57 */     this.sqlNone = genSql(ConcurrencyMode.NONE);
/*  58 */     this.sqlVersion = genSql(ConcurrencyMode.VERSION);
/*     */   }
/*     */ 
/*     */   
/*  62 */   public boolean isEmptyStringAsNull() { return this.emptyStringAsNull; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  69 */   public String getTableName() { return this.tableName; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void bind(PersistRequestBean<?> persist, DmlHandler bind) throws SQLException {
/*  78 */     Object oldBean, bean = persist.getBean();
/*     */     
/*  80 */     this.id.dmlBind(bind, false, bean);
/*     */     
/*  82 */     switch (persist.getConcurrencyMode()) {
/*     */       case VERSION:
/*  84 */         this.version.dmlBind(bind, false, bean);
/*     */         break;
/*     */       
/*     */       case ALL:
/*  88 */         oldBean = persist.getOldValues();
/*  89 */         this.all.dmlBindWhere(bind, true, oldBean);
/*     */         break;
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
/*     */   public String getSql(PersistRequestBean<?> request) throws SQLException {
/* 103 */     switch (request.determineConcurrencyMode()) {
/*     */       case NONE:
/* 105 */         return this.sqlNone;
/*     */       
/*     */       case VERSION:
/* 108 */         return this.sqlVersion;
/*     */       
/*     */       case ALL:
/* 111 */         return genDynamicWhere(request.getLoadedProperties(), request.getOldValues());
/*     */     } 
/*     */     
/* 114 */     throw new RuntimeException("Invalid mode " + request.determineConcurrencyMode());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String genSql(ConcurrencyMode conMode) {
/* 122 */     GenerateDmlRequest request = new GenerateDmlRequest(this.emptyStringAsNull);
/*     */     
/* 124 */     request.append("delete from ").append(this.tableName);
/* 125 */     request.append(" where ");
/*     */     
/* 127 */     request.setWhereIdMode();
/* 128 */     this.id.dmlAppend(request, false);
/*     */     
/* 130 */     if (ConcurrencyMode.VERSION.equals(conMode)) {
/* 131 */       if (this.version == null) {
/* 132 */         return null;
/*     */       }
/* 134 */       this.version.dmlAppend(request, false);
/*     */     }
/* 136 */     else if (ConcurrencyMode.ALL.equals(conMode)) {
/* 137 */       throw new RuntimeException("Never called for ConcurrencyMode.ALL");
/*     */     } 
/*     */     
/* 140 */     return request.toString();
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
/*     */   private String genDynamicWhere(Set<String> includedProps, Object oldBean) throws SQLException {
/* 152 */     GenerateDmlRequest request = new GenerateDmlRequest(this.emptyStringAsNull, includedProps, oldBean);
/*     */     
/* 154 */     request.append(this.sqlNone);
/*     */     
/* 156 */     request.setWhereMode();
/* 157 */     this.all.dmlWhere(request, true, oldBean);
/*     */     
/* 159 */     return request.toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\persist\dml\DeleteMeta.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */