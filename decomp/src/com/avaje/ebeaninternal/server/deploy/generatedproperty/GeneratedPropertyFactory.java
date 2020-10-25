/*    */ package com.avaje.ebeaninternal.server.deploy.generatedproperty;
/*    */ 
/*    */ import com.avaje.ebeaninternal.server.deploy.meta.DeployBeanProperty;
/*    */ import java.util.HashSet;
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
/*    */ public class GeneratedPropertyFactory
/*    */ {
/*    */   CounterFactory counterFactory;
/*    */   InsertTimestampFactory insertFactory;
/*    */   UpdateTimestampFactory updateFactory;
/*    */   HashSet<String> numberTypes;
/*    */   
/*    */   public GeneratedPropertyFactory() {
/* 38 */     this.numberTypes = new HashSet();
/*    */ 
/*    */     
/* 41 */     this.counterFactory = new CounterFactory();
/* 42 */     this.insertFactory = new InsertTimestampFactory();
/* 43 */     this.updateFactory = new UpdateTimestampFactory();
/*    */ 
/*    */     
/* 46 */     this.numberTypes.add(Integer.class.getName());
/* 47 */     this.numberTypes.add(int.class.getName());
/* 48 */     this.numberTypes.add(Long.class.getName());
/* 49 */     this.numberTypes.add(long.class.getName());
/* 50 */     this.numberTypes.add(Short.class.getName());
/* 51 */     this.numberTypes.add(short.class.getName());
/* 52 */     this.numberTypes.add(Double.class.getName());
/* 53 */     this.numberTypes.add(double.class.getName());
/* 54 */     this.numberTypes.add(java.math.BigDecimal.class.getName());
/*    */   }
/*    */ 
/*    */   
/* 58 */   private boolean isNumberType(String typeClassName) { return this.numberTypes.contains(typeClassName); }
/*    */ 
/*    */   
/*    */   public void setVersion(DeployBeanProperty property) {
/* 62 */     if (isNumberType(property.getPropertyType().getName())) {
/* 63 */       setCounter(property);
/*    */     } else {
/* 65 */       setUpdateTimestamp(property);
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/* 71 */   public void setCounter(DeployBeanProperty property) { this.counterFactory.setCounter(property); }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 76 */   public void setInsertTimestamp(DeployBeanProperty property) { this.insertFactory.setInsertTimestamp(property); }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 81 */   public void setUpdateTimestamp(DeployBeanProperty property) { this.updateFactory.setUpdateTimestamp(property); }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\deploy\generatedproperty\GeneratedPropertyFactory.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */