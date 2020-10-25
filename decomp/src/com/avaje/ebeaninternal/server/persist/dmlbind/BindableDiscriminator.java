/*    */ package com.avaje.ebeaninternal.server.persist.dmlbind;
/*    */ 
/*    */ import com.avaje.ebeaninternal.server.core.PersistRequestBean;
/*    */ import com.avaje.ebeaninternal.server.deploy.InheritInfo;
/*    */ import com.avaje.ebeaninternal.server.persist.dml.GenerateDmlRequest;
/*    */ import java.sql.SQLException;
/*    */ import java.util.List;
/*    */ import javax.persistence.PersistenceException;
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
/*    */ public class BindableDiscriminator
/*    */   implements Bindable
/*    */ {
/*    */   private final String columnName;
/*    */   private final Object discValue;
/*    */   private final int sqlType;
/*    */   
/*    */   public BindableDiscriminator(InheritInfo inheritInfo) {
/* 41 */     this.columnName = inheritInfo.getDiscriminatorColumn();
/* 42 */     this.discValue = inheritInfo.getDiscriminatorValue();
/* 43 */     this.sqlType = inheritInfo.getDiscriminatorType();
/*    */   }
/*    */ 
/*    */   
/* 47 */   public String toString() { return this.columnName + " = " + this.discValue; }
/*    */ 
/*    */ 
/*    */   
/* 51 */   public void addChanged(PersistRequestBean<?> request, List<Bindable> list) { throw new PersistenceException("Never called (only for inserts)"); }
/*    */ 
/*    */ 
/*    */   
/* 55 */   public void dmlInsert(GenerateDmlRequest request, boolean checkIncludes) { dmlAppend(request, checkIncludes); }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void dmlWhere(GenerateDmlRequest request, boolean checkIncludes, Object bean) {}
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 66 */   public void dmlAppend(GenerateDmlRequest request, boolean checkIncludes) { request.appendColumn(this.columnName); }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 71 */   public void dmlBind(BindableRequest bindRequest, boolean checkIncludes, Object bean) throws SQLException { bindRequest.bind(this.columnName, this.discValue, this.sqlType); }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 76 */   public void dmlBindWhere(BindableRequest bindRequest, boolean checkIncludes, Object bean) throws SQLException { bindRequest.bind(this.columnName, this.discValue, this.sqlType); }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\persist\dmlbind\BindableDiscriminator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */