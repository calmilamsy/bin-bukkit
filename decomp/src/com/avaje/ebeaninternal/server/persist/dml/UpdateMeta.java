/*     */ package com.avaje.ebeaninternal.server.persist.dml;
/*     */ 
/*     */ import com.avaje.ebeaninternal.api.SpiUpdatePlan;
/*     */ import com.avaje.ebeaninternal.server.core.ConcurrencyMode;
/*     */ import com.avaje.ebeaninternal.server.core.PersistRequestBean;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
/*     */ import com.avaje.ebeaninternal.server.persist.dmlbind.Bindable;
/*     */ import com.avaje.ebeaninternal.server.persist.dmlbind.BindableList;
/*     */ import java.sql.SQLException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Set;
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
/*     */ public final class UpdateMeta
/*     */ {
/*     */   private final String sqlVersion;
/*     */   private final String sqlNone;
/*     */   private final Bindable set;
/*     */   private final Bindable id;
/*     */   private final Bindable version;
/*     */   private final Bindable all;
/*     */   private final String tableName;
/*     */   private final UpdatePlan modeNoneUpdatePlan;
/*     */   private final UpdatePlan modeVersionUpdatePlan;
/*     */   private final boolean emptyStringAsNull;
/*     */   
/*     */   public UpdateMeta(boolean emptyStringAsNull, BeanDescriptor<?> desc, Bindable set, Bindable id, Bindable version, Bindable all) {
/*  60 */     this.emptyStringAsNull = emptyStringAsNull;
/*  61 */     this.tableName = desc.getBaseTable();
/*  62 */     this.set = set;
/*  63 */     this.id = id;
/*  64 */     this.version = version;
/*  65 */     this.all = all;
/*     */     
/*  67 */     this.sqlNone = genSql(ConcurrencyMode.NONE, null, null);
/*  68 */     this.sqlVersion = genSql(ConcurrencyMode.VERSION, null, null);
/*     */     
/*  70 */     this.modeNoneUpdatePlan = new UpdatePlan(ConcurrencyMode.NONE, this.sqlNone, set);
/*  71 */     this.modeVersionUpdatePlan = new UpdatePlan(ConcurrencyMode.VERSION, this.sqlVersion, set);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  79 */   public boolean isEmptyStringAsNull() { return this.emptyStringAsNull; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  86 */   public String getTableName() { return this.tableName; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void bind(PersistRequestBean<?> persist, DmlHandler bind, SpiUpdatePlan updatePlan) throws SQLException {
/*  94 */     Object oldBean, bean = persist.getBean();
/*     */     
/*  96 */     bind.bindLogAppend(" set[");
/*  97 */     bind.setCheckDelta(true);
/*  98 */     updatePlan.bindSet(bind, bean);
/*  99 */     bind.setCheckDelta(false);
/*     */     
/* 101 */     bind.bindLogAppend("] where[");
/* 102 */     this.id.dmlBind(bind, false, bean);
/*     */     
/* 104 */     switch (persist.getConcurrencyMode()) {
/*     */       case VERSION:
/* 106 */         this.version.dmlBind(bind, false, bean);
/*     */         break;
/*     */       case ALL:
/* 109 */         oldBean = persist.getOldValues();
/* 110 */         this.all.dmlBindWhere(bind, true, oldBean);
/*     */         break;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SpiUpdatePlan getUpdatePlan(PersistRequestBean<?> request) {
/*     */     String sql;
/*     */     Object oldValues;
/* 123 */     ConcurrencyMode mode = request.determineConcurrencyMode();
/* 124 */     if (request.isDynamicUpdateSql()) {
/* 125 */       return getDynamicUpdatePlan(mode, request);
/*     */     }
/*     */ 
/*     */     
/* 129 */     switch (mode) {
/*     */       case NONE:
/* 131 */         return this.modeNoneUpdatePlan;
/*     */       
/*     */       case VERSION:
/* 134 */         return this.modeVersionUpdatePlan;
/*     */       
/*     */       case ALL:
/* 137 */         oldValues = request.getOldValues();
/* 138 */         if (oldValues == null) {
/* 139 */           throw new PersistenceException("OldValues are null?");
/*     */         }
/* 141 */         sql = genDynamicWhere(request.getUpdatedProperties(), request.getLoadedProperties(), oldValues);
/* 142 */         return new UpdatePlan(ConcurrencyMode.ALL, sql, this.set);
/*     */     } 
/*     */     
/* 145 */     throw new RuntimeException("Invalid mode " + mode);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private SpiUpdatePlan getDynamicUpdatePlan(ConcurrencyMode mode, PersistRequestBean<?> persistRequest) {
/* 151 */     Set<String> updatedProps = persistRequest.getUpdatedProperties();
/*     */     
/* 153 */     if (ConcurrencyMode.ALL.equals(mode)) {
/*     */ 
/*     */       
/* 156 */       String sql = genSql(mode, persistRequest, null);
/* 157 */       if (sql == null)
/*     */       {
/* 159 */         return UpdatePlan.EMPTY_SET_CLAUSE;
/*     */       }
/* 161 */       return new UpdatePlan(null, mode, sql, this.set, updatedProps);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 166 */     int hash = mode.hashCode();
/* 167 */     hash = hash * 31 + ((updatedProps == null) ? 0 : updatedProps.hashCode());
/* 168 */     Integer key = Integer.valueOf(hash);
/*     */     
/* 170 */     BeanDescriptor<?> beanDescriptor = persistRequest.getBeanDescriptor();
/* 171 */     SpiUpdatePlan updatePlan = beanDescriptor.getUpdatePlan(key);
/* 172 */     if (updatePlan != null) {
/* 173 */       return updatePlan;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 179 */     List<Bindable> list = new ArrayList<Bindable>();
/* 180 */     this.set.addChanged(persistRequest, list);
/* 181 */     BindableList bindableList = new BindableList(list);
/*     */ 
/*     */     
/* 184 */     String sql = genSql(mode, persistRequest, bindableList);
/*     */     
/* 186 */     updatePlan = new UpdatePlan(key, mode, sql, bindableList, null);
/*     */ 
/*     */     
/* 189 */     beanDescriptor.putUpdatePlan(key, updatePlan);
/*     */     
/* 191 */     return updatePlan;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String genSql(ConcurrencyMode conMode, PersistRequestBean<?> persistRequest, BindableList bindableList) {
/*     */     GenerateDmlRequest request;
/* 199 */     if (persistRequest == null) {
/*     */       
/* 201 */       request = new GenerateDmlRequest(this.emptyStringAsNull);
/*     */     } else {
/* 203 */       request = persistRequest.createGenerateDmlRequest(this.emptyStringAsNull);
/*     */     } 
/*     */     
/* 206 */     request.append("update ").append(this.tableName).append(" set ");
/*     */     
/* 208 */     request.setUpdateSetMode();
/* 209 */     if (bindableList != null) {
/* 210 */       bindableList.dmlAppend(request, false);
/*     */     } else {
/* 212 */       this.set.dmlAppend(request, true);
/*     */     } 
/*     */     
/* 215 */     if (request.getBindColumnCount() == 0)
/*     */     {
/*     */       
/* 218 */       return null;
/*     */     }
/*     */     
/* 221 */     request.append(" where ");
/*     */     
/* 223 */     request.setWhereIdMode();
/* 224 */     this.id.dmlAppend(request, false);
/*     */     
/* 226 */     if (ConcurrencyMode.VERSION.equals(conMode)) {
/* 227 */       if (this.version == null) {
/* 228 */         return null;
/*     */       }
/* 230 */       this.version.dmlAppend(request, false);
/*     */     }
/* 232 */     else if (ConcurrencyMode.ALL.equals(conMode)) {
/*     */       
/* 234 */       this.all.dmlWhere(request, true, request.getOldValues());
/*     */     } 
/*     */     
/* 237 */     return request.toString();
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
/*     */   private String genDynamicWhere(Set<String> loadedProps, Set<String> whereProps, Object oldBean) {
/* 249 */     GenerateDmlRequest request = new GenerateDmlRequest(this.emptyStringAsNull, loadedProps, whereProps, oldBean);
/*     */     
/* 251 */     request.append(this.sqlNone);
/*     */     
/* 253 */     request.setWhereMode();
/* 254 */     this.all.dmlWhere(request, true, oldBean);
/*     */     
/* 256 */     return request.toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\persist\dml\UpdateMeta.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */