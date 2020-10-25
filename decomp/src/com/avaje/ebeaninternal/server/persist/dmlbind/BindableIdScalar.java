/*     */ package com.avaje.ebeaninternal.server.persist.dmlbind;
/*     */ 
/*     */ import com.avaje.ebeaninternal.server.core.PersistRequestBean;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanProperty;
/*     */ import com.avaje.ebeaninternal.server.persist.dml.GenerateDmlRequest;
/*     */ import java.sql.SQLException;
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
/*     */ public class BindableIdScalar
/*     */   implements BindableId
/*     */ {
/*     */   private final BeanProperty uidProp;
/*     */   
/*  39 */   public BindableIdScalar(BeanProperty uidProp) { this.uidProp = uidProp; }
/*     */ 
/*     */ 
/*     */   
/*  43 */   public boolean isConcatenated() { return false; }
/*     */ 
/*     */ 
/*     */   
/*  47 */   public String getIdentityColumn() { return this.uidProp.getDbColumn(); }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  52 */   public String toString() { return this.uidProp.toString(); }
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
/*  66 */   public boolean deriveConcatenatedId(PersistRequestBean<?> persist) { throw new PersistenceException("Should not be called? only for concatinated keys"); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  74 */   public void dmlWhere(GenerateDmlRequest request, boolean checkIncludes, Object bean) { request.appendColumn(this.uidProp.getDbColumn()); }
/*     */ 
/*     */ 
/*     */   
/*  78 */   public void dmlInsert(GenerateDmlRequest request, boolean checkIncludes) { dmlAppend(request, checkIncludes); }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  83 */   public void dmlAppend(GenerateDmlRequest request, boolean checkIncludes) { request.appendColumn(this.uidProp.getDbColumn()); }
/*     */ 
/*     */ 
/*     */   
/*  87 */   public void dmlBind(BindableRequest request, boolean checkIncludes, Object bean) throws SQLException { dmlBind(request, checkIncludes, bean, true); }
/*     */ 
/*     */ 
/*     */   
/*  91 */   public void dmlBindWhere(BindableRequest request, boolean checkIncludes, Object bean) throws SQLException { dmlBind(request, checkIncludes, bean, false); }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void dmlBind(BindableRequest bindRequest, boolean checkIncludes, Object bean, boolean bindNull) throws SQLException {
/*  97 */     Object value = this.uidProp.getValue(bean);
/*     */     
/*  99 */     bindRequest.bind(value, this.uidProp, this.uidProp.getName(), bindNull);
/*     */ 
/*     */     
/* 102 */     bindRequest.setIdValue(value);
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\persist\dmlbind\BindableIdScalar.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */