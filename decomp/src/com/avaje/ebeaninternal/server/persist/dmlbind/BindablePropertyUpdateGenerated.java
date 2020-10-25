/*    */ package com.avaje.ebeaninternal.server.persist.dmlbind;
/*    */ 
/*    */ import com.avaje.ebeaninternal.server.core.PersistRequestBean;
/*    */ import com.avaje.ebeaninternal.server.deploy.BeanProperty;
/*    */ import com.avaje.ebeaninternal.server.deploy.generatedproperty.GeneratedProperty;
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
/*    */ 
/*    */ 
/*    */ public class BindablePropertyUpdateGenerated
/*    */   extends BindableProperty
/*    */ {
/*    */   private final GeneratedProperty gen;
/*    */   
/*    */   public BindablePropertyUpdateGenerated(BeanProperty prop, GeneratedProperty gen) {
/* 41 */     super(prop);
/* 42 */     this.gen = gen;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 50 */   public void addChanged(PersistRequestBean<?> request, List<Bindable> list) { list.add(this); }
/*    */ 
/*    */   
/*    */   public void dmlBind(BindableRequest request, boolean checkIncludes, Object bean) throws SQLException {
/* 54 */     if (checkIncludes && !request.isIncluded(this.prop)) {
/*    */       return;
/*    */     }
/* 57 */     dmlBind(request, bean, true);
/*    */   }
/*    */   
/*    */   public void dmlBindWhere(BindableRequest request, boolean checkIncludes, Object bean) throws SQLException {
/* 61 */     if (checkIncludes && !request.isIncludedWhere(this.prop)) {
/*    */       return;
/*    */     }
/* 64 */     dmlBind(request, bean, false);
/*    */   }
/*    */ 
/*    */   
/*    */   private void dmlBind(BindableRequest request, Object bean, boolean bindNull) throws SQLException {
/* 69 */     Object value = this.gen.getUpdateValue(this.prop, bean);
/*    */ 
/*    */     
/* 72 */     request.bind(value, this.prop, this.prop.getName(), bindNull);
/*    */ 
/*    */ 
/*    */     
/* 76 */     if (request.isIncluded(this.prop))
/*    */     {
/*    */       
/* 79 */       request.registerUpdateGenValue(this.prop, bean, value);
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void dmlAppend(GenerateDmlRequest request, boolean checkIncludes) {
/* 88 */     if (checkIncludes && !request.isIncluded(this.prop)) {
/*    */       return;
/*    */     }
/* 91 */     request.appendColumn(this.prop.getDbColumn());
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\persist\dmlbind\BindablePropertyUpdateGenerated.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */