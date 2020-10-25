/*    */ package com.avaje.ebeaninternal.server.persist.dmlbind;
/*    */ 
/*    */ import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
/*    */ import com.avaje.ebeaninternal.server.deploy.BeanProperty;
/*    */ import com.avaje.ebeaninternal.server.deploy.BeanPropertyAssocOne;
/*    */ import com.avaje.ebeaninternal.server.persist.dml.DmlMode;
/*    */ import java.util.ArrayList;
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
/*    */ public class FactoryEmbedded
/*    */ {
/*    */   private final FactoryProperty factoryProperty;
/*    */   
/* 38 */   public FactoryEmbedded(boolean bindEncryptDataFirst) { this.factoryProperty = new FactoryProperty(bindEncryptDataFirst); }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void create(List<Bindable> list, BeanDescriptor<?> desc, DmlMode mode, boolean withLobs) {
/* 46 */     BeanPropertyAssocOne[] embedded = desc.propertiesEmbedded();
/*    */     
/* 48 */     for (int j = 0; j < embedded.length; j++) {
/*    */       
/* 50 */       List<Bindable> bindList = new ArrayList<Bindable>();
/*    */       
/* 52 */       BeanProperty[] props = embedded[j].getProperties();
/* 53 */       for (int i = 0; i < props.length; i++) {
/* 54 */         Bindable item = this.factoryProperty.create(props[i], mode, withLobs);
/* 55 */         if (item != null) {
/* 56 */           bindList.add(item);
/*    */         }
/*    */       } 
/*    */       
/* 60 */       list.add(new BindableEmbedded(embedded[j], bindList));
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\persist\dmlbind\FactoryEmbedded.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */