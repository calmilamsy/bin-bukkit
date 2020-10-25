/*    */ package com.avaje.ebeaninternal.server.persist.dmlbind;
/*    */ 
/*    */ import com.avaje.ebeaninternal.server.core.PersistRequestBean;
/*    */ import com.avaje.ebeaninternal.server.deploy.BeanPropertyAssocOne;
/*    */ import com.avaje.ebeaninternal.server.deploy.id.ImportedId;
/*    */ import com.avaje.ebeaninternal.server.persist.dml.GenerateDmlRequest;
/*    */ import java.sql.SQLException;
/*    */ import java.util.List;
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
/*    */ public class BindableAssocOne
/*    */   implements Bindable
/*    */ {
/*    */   private final BeanPropertyAssocOne<?> assocOne;
/*    */   private final ImportedId importedId;
/*    */   
/*    */   public BindableAssocOne(BeanPropertyAssocOne<?> assocOne) {
/* 40 */     this.assocOne = assocOne;
/* 41 */     this.importedId = assocOne.getImportedId();
/*    */   }
/*    */ 
/*    */   
/* 45 */   public String toString() { return "BindableAssocOne " + this.assocOne; }
/*    */ 
/*    */   
/*    */   public void addChanged(PersistRequestBean<?> request, List<Bindable> list) {
/* 49 */     if (request.hasChanged(this.assocOne)) {
/* 50 */       list.add(this);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/* 55 */   public void dmlInsert(GenerateDmlRequest request, boolean checkIncludes) { dmlAppend(request, checkIncludes); }
/*    */ 
/*    */   
/*    */   public void dmlAppend(GenerateDmlRequest request, boolean checkIncludes) {
/* 59 */     if (checkIncludes && !request.isIncluded(this.assocOne)) {
/*    */       return;
/*    */     }
/* 62 */     this.importedId.dmlAppend(request);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void dmlWhere(GenerateDmlRequest request, boolean checkIncludes, Object bean) {
/* 69 */     if (checkIncludes && !request.isIncludedWhere(this.assocOne)) {
/*    */       return;
/*    */     }
/* 72 */     Object assocBean = this.assocOne.getValue(bean);
/* 73 */     this.importedId.dmlWhere(request, assocBean);
/*    */   }
/*    */   
/*    */   public void dmlBind(BindableRequest request, boolean checkIncludes, Object bean) throws SQLException {
/* 77 */     if (checkIncludes && !request.isIncluded(this.assocOne)) {
/*    */       return;
/*    */     }
/* 80 */     dmlBind(request, bean, true);
/*    */   }
/*    */   
/*    */   public void dmlBindWhere(BindableRequest request, boolean checkIncludes, Object bean) throws SQLException {
/* 84 */     if (checkIncludes && !request.isIncludedWhere(this.assocOne)) {
/*    */       return;
/*    */     }
/* 87 */     dmlBind(request, bean, false);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   private void dmlBind(BindableRequest request, Object bean, boolean bindNull) throws SQLException {
/* 93 */     Object assocBean = this.assocOne.getValue(bean);
/* 94 */     this.importedId.bind(request, assocBean, bindNull);
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\persist\dmlbind\BindableAssocOne.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */