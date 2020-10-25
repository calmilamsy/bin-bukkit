/*    */ package com.avaje.ebeaninternal.server.persist.dmlbind;
/*    */ 
/*    */ import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
/*    */ import com.avaje.ebeaninternal.server.deploy.BeanProperty;
/*    */ import com.avaje.ebeaninternal.server.deploy.BeanPropertyCompound;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class FactoryBaseProperties
/*    */ {
/*    */   private final FactoryProperty factoryProperty;
/*    */   
/* 42 */   public FactoryBaseProperties(boolean bindEncryptDataFirst) { this.factoryProperty = new FactoryProperty(bindEncryptDataFirst); }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void create(List<Bindable> list, BeanDescriptor<?> desc, DmlMode mode, boolean withLobs) {
/* 50 */     add(desc.propertiesBaseScalar(), list, desc, mode, withLobs);
/*    */     
/* 52 */     BeanPropertyCompound[] compoundProps = desc.propertiesBaseCompound();
/* 53 */     for (int i = 0; i < compoundProps.length; i++) {
/* 54 */       BeanProperty[] props = compoundProps[i].getScalarProperties();
/*    */       
/* 56 */       ArrayList<Bindable> newList = new ArrayList<Bindable>(props.length);
/* 57 */       add(props, newList, desc, mode, withLobs);
/*    */       
/* 59 */       BindableCompound compoundBindable = new BindableCompound(compoundProps[i], newList);
/*    */       
/* 61 */       list.add(compoundBindable);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   private void add(BeanProperty[] props, List<Bindable> list, BeanDescriptor<?> desc, DmlMode mode, boolean withLobs) {
/* 67 */     for (int i = 0; i < props.length; i++) {
/*    */       
/* 69 */       Bindable item = this.factoryProperty.create(props[i], mode, withLobs);
/* 70 */       if (item != null)
/* 71 */         list.add(item); 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\persist\dmlbind\FactoryBaseProperties.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */