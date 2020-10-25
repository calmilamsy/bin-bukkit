/*    */ package com.avaje.ebeaninternal.server.transaction;
/*    */ 
/*    */ import com.avaje.ebeaninternal.server.cluster.BinaryMessage;
/*    */ import com.avaje.ebeaninternal.server.deploy.BeanProperty;
/*    */ import java.io.DataOutputStream;
/*    */ import java.io.IOException;
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
/*    */ public class BeanDeltaProperty
/*    */ {
/*    */   private final BeanProperty beanProperty;
/*    */   private final Object value;
/*    */   
/*    */   public BeanDeltaProperty(BeanProperty beanProperty, Object value) {
/* 35 */     this.beanProperty = beanProperty;
/* 36 */     this.value = value;
/*    */   }
/*    */ 
/*    */   
/* 40 */   public String toString() { return this.beanProperty.getName() + ":" + this.value; }
/*    */ 
/*    */ 
/*    */   
/* 44 */   public void apply(Object bean) { this.beanProperty.setValue(bean, this.value); }
/*    */ 
/*    */ 
/*    */   
/*    */   public void writeBinaryMessage(BinaryMessage m) throws IOException {
/* 49 */     DataOutputStream os = m.getOs();
/* 50 */     os.writeUTF(this.beanProperty.getName());
/* 51 */     this.beanProperty.getScalarType().writeData(os, this.value);
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\transaction\BeanDeltaProperty.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */