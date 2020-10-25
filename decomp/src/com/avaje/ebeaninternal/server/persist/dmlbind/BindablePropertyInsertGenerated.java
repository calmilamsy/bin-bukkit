/*    */ package com.avaje.ebeaninternal.server.persist.dmlbind;
/*    */ 
/*    */ import com.avaje.ebeaninternal.server.deploy.BeanProperty;
/*    */ import com.avaje.ebeaninternal.server.deploy.generatedproperty.GeneratedProperty;
/*    */ import com.avaje.ebeaninternal.server.persist.dml.GenerateDmlRequest;
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
/*    */ 
/*    */ public class BindablePropertyInsertGenerated
/*    */   extends BindableProperty
/*    */ {
/*    */   private final GeneratedProperty gen;
/*    */   
/*    */   public BindablePropertyInsertGenerated(BeanProperty prop, GeneratedProperty gen) {
/* 39 */     super(prop);
/* 40 */     this.gen = gen;
/*    */   }
/*    */ 
/*    */   
/* 44 */   public void dmlBind(BindableRequest request, boolean checkIncludes, Object bean) throws SQLException { dmlBind(request, checkIncludes, bean, true); }
/*    */ 
/*    */ 
/*    */   
/* 48 */   public void dmlBindWhere(BindableRequest request, boolean checkIncludes, Object bean) throws SQLException { dmlBind(request, checkIncludes, bean, false); }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private void dmlBind(BindableRequest request, boolean checkIncludes, Object bean, boolean bindNull) throws SQLException {
/* 56 */     Object value = this.gen.getInsertValue(this.prop, bean);
/*    */ 
/*    */     
/* 59 */     if (bean != null) {
/*    */       
/* 61 */       this.prop.setValueIntercept(bean, value);
/* 62 */       request.registerAdditionalProperty(this.prop.getName());
/*    */     } 
/*    */     
/* 65 */     request.bind(value, this.prop, this.prop.getName(), bindNull);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 73 */   public void dmlAppend(GenerateDmlRequest request, boolean checkIncludes) { request.appendColumn(this.prop.getDbColumn()); }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\persist\dmlbind\BindablePropertyInsertGenerated.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */