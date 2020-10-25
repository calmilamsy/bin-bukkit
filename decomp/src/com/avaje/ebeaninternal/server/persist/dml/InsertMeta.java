/*     */ package com.avaje.ebeaninternal.server.persist.dml;
/*     */ 
/*     */ import com.avaje.ebean.config.dbplatform.DatabasePlatform;
/*     */ import com.avaje.ebeaninternal.server.core.PersistRequestBean;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
/*     */ import com.avaje.ebeaninternal.server.deploy.InheritInfo;
/*     */ import com.avaje.ebeaninternal.server.persist.dmlbind.Bindable;
/*     */ import com.avaje.ebeaninternal.server.persist.dmlbind.BindableDiscriminator;
/*     */ import com.avaje.ebeaninternal.server.persist.dmlbind.BindableId;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class InsertMeta
/*     */ {
/*     */   private final String sqlNullId;
/*     */   private final String sqlWithId;
/*     */   private final BindableId id;
/*     */   private final Bindable discriminator;
/*     */   private final Bindable all;
/*     */   private final boolean supportsGetGeneratedKeys;
/*     */   private final boolean concatinatedKey;
/*     */   private final String tableName;
/*     */   private final String selectLastInsertedId;
/*     */   private final Bindable shadowFKey;
/*     */   private final String[] identityDbColumns;
/*     */   private final boolean emptyStringToNull;
/*     */   
/*     */   public InsertMeta(DatabasePlatform dbPlatform, BeanDescriptor<?> desc, Bindable shadowFKey, BindableId id, Bindable all) {
/*  68 */     this.emptyStringToNull = dbPlatform.isTreatEmptyStringsAsNull();
/*  69 */     this.tableName = desc.getBaseTable();
/*  70 */     this.discriminator = getDiscriminator(desc);
/*  71 */     this.id = id;
/*  72 */     this.all = all;
/*  73 */     this.shadowFKey = shadowFKey;
/*     */     
/*  75 */     this.sqlWithId = genSql(false, null);
/*     */ 
/*     */     
/*  78 */     if (id.isConcatenated()) {
/*     */       
/*  80 */       this.concatinatedKey = true;
/*  81 */       this.identityDbColumns = null;
/*  82 */       this.sqlNullId = null;
/*  83 */       this.supportsGetGeneratedKeys = false;
/*  84 */       this.selectLastInsertedId = null;
/*     */     }
/*     */     else {
/*     */       
/*  88 */       this.concatinatedKey = false;
/*  89 */       this.identityDbColumns = new String[] { id.getIdentityColumn() };
/*  90 */       this.sqlNullId = genSql(true, null);
/*  91 */       this.supportsGetGeneratedKeys = dbPlatform.getDbIdentity().isSupportsGetGeneratedKeys();
/*  92 */       this.selectLastInsertedId = desc.getSelectLastInsertedId();
/*     */     } 
/*     */   }
/*     */   
/*     */   private static Bindable getDiscriminator(BeanDescriptor<?> desc) {
/*  97 */     InheritInfo inheritInfo = desc.getInheritInfo();
/*  98 */     if (inheritInfo != null) {
/*  99 */       return new BindableDiscriminator(inheritInfo);
/*     */     }
/* 101 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 109 */   public boolean isEmptyStringToNull() { return this.emptyStringToNull; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 116 */   public boolean isConcatinatedKey() { return this.concatinatedKey; }
/*     */ 
/*     */ 
/*     */   
/* 120 */   public String[] getIdentityDbColumns() { return this.identityDbColumns; }
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
/* 132 */   public String getSelectLastInsertedId() { return this.selectLastInsertedId; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 140 */   public boolean supportsGetGeneratedKeys() { return this.supportsGetGeneratedKeys; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 147 */   public boolean deriveConcatenatedId(PersistRequestBean<?> persist) { return this.id.deriveConcatenatedId(persist); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void bind(DmlHandler request, Object bean, boolean withId) throws SQLException {
/* 155 */     if (withId) {
/* 156 */       this.id.dmlBind(request, false, bean);
/*     */     }
/* 158 */     if (this.shadowFKey != null) {
/* 159 */       this.shadowFKey.dmlBind(request, false, bean);
/*     */     }
/* 161 */     if (this.discriminator != null) {
/* 162 */       this.discriminator.dmlBind(request, false, bean);
/*     */     }
/* 164 */     this.all.dmlBind(request, false, bean);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getSql(boolean withId) {
/* 172 */     if (withId) {
/* 173 */       return this.sqlWithId;
/*     */     }
/* 175 */     return this.sqlNullId;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private String genSql(boolean nullId, Set<String> loadedProps) {
/* 181 */     GenerateDmlRequest request = new GenerateDmlRequest(this.emptyStringToNull, loadedProps, null);
/* 182 */     request.setInsertSetMode();
/*     */     
/* 184 */     request.append("insert into ").append(this.tableName);
/* 185 */     request.append(" (");
/*     */     
/* 187 */     if (!nullId) {
/* 188 */       this.id.dmlInsert(request, false);
/*     */     }
/*     */     
/* 191 */     if (this.shadowFKey != null) {
/* 192 */       this.shadowFKey.dmlInsert(request, false);
/*     */     }
/*     */     
/* 195 */     if (this.discriminator != null) {
/* 196 */       this.discriminator.dmlInsert(request, false);
/*     */     }
/*     */     
/* 199 */     this.all.dmlInsert(request, false);
/*     */     
/* 201 */     request.append(") values (");
/* 202 */     request.append(request.getInsertBindBuffer());
/* 203 */     request.append(")");
/*     */     
/* 205 */     return request.toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\persist\dml\InsertMeta.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */