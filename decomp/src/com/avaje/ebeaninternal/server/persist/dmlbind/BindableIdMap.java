/*     */ package com.avaje.ebeaninternal.server.persist.dmlbind;
/*     */ 
/*     */ import com.avaje.ebeaninternal.server.core.PersistRequestBean;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanProperty;
/*     */ import com.avaje.ebeaninternal.server.persist.dml.GenerateDmlRequest;
/*     */ import java.sql.SQLException;
/*     */ import java.util.Arrays;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.List;
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
/*     */ public class BindableIdMap
/*     */   implements BindableId
/*     */ {
/*     */   private final BeanProperty[] uids;
/*     */   private final MatchedImportedProperty[] matches;
/*     */   
/*     */   public BindableIdMap(BeanProperty[] uids, BeanDescriptor<?> desc) {
/*  44 */     this.uids = uids;
/*  45 */     this.matches = MatchedImportedProperty.build(uids, desc);
/*     */   }
/*     */ 
/*     */   
/*  49 */   public boolean isConcatenated() { return true; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  54 */   public String getIdentityColumn() { return null; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  59 */   public String toString() { return Arrays.toString(this.uids); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addChanged(PersistRequestBean<?> request, List<Bindable> list) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  74 */   public void dmlWhere(GenerateDmlRequest request, boolean checkIncludes, Object bean) { dmlAppend(request, false); }
/*     */ 
/*     */ 
/*     */   
/*  78 */   public void dmlInsert(GenerateDmlRequest request, boolean checkIncludes) { dmlAppend(request, checkIncludes); }
/*     */ 
/*     */   
/*     */   public void dmlAppend(GenerateDmlRequest request, boolean checkIncludes) {
/*  82 */     for (int i = 0; i < this.uids.length; i++) {
/*  83 */       request.appendColumn(this.uids[i].getDbColumn());
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*  88 */   public void dmlBind(BindableRequest request, boolean checkIncludes, Object bean) throws SQLException { dmlBind(request, checkIncludes, bean, true); }
/*     */ 
/*     */ 
/*     */   
/*  92 */   public void dmlBindWhere(BindableRequest request, boolean checkIncludes, Object bean) throws SQLException { dmlBind(request, checkIncludes, bean, false); }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void dmlBind(BindableRequest bindRequest, boolean checkIncludes, Object bean, boolean bindNull) throws SQLException {
/*  98 */     LinkedHashMap<String, Object> mapId = new LinkedHashMap<String, Object>();
/*  99 */     for (int i = 0; i < this.uids.length; i++) {
/* 100 */       Object value = this.uids[i].getValue(bean);
/*     */       
/* 102 */       bindRequest.bind(value, this.uids[i], this.uids[i].getName(), bindNull);
/*     */ 
/*     */ 
/*     */       
/* 106 */       mapId.put(this.uids[i].getName(), value);
/*     */     } 
/* 108 */     bindRequest.setIdValue(mapId);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean deriveConcatenatedId(PersistRequestBean<?> persist) {
/* 113 */     if (this.matches == null) {
/* 114 */       String m = "Matches for the concatinated key columns where not found? I expect that the concatinated key was null, and this bean does not have ManyToOne assoc beans matching the primary key columns?";
/*     */ 
/*     */       
/* 117 */       throw new PersistenceException(m);
/*     */     } 
/*     */     
/* 120 */     Object bean = persist.getBean();
/*     */ 
/*     */     
/* 123 */     for (int i = 0; i < this.matches.length; i++) {
/* 124 */       this.matches[i].populate(bean, bean);
/*     */     }
/*     */     
/* 127 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\persist\dmlbind\BindableIdMap.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */