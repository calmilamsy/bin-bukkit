/*    */ package com.avaje.ebeaninternal.server.persist.dmlbind;
/*    */ 
/*    */ import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
/*    */ import com.avaje.ebeaninternal.server.deploy.BeanPropertyAssocOne;
/*    */ import com.avaje.ebeaninternal.server.persist.dml.DmlMode;
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
/*    */ 
/*    */ 
/*    */ public class FactoryAssocOnes
/*    */ {
/*    */   public List<Bindable> create(List<Bindable> list, BeanDescriptor<?> desc, DmlMode mode) {
/* 41 */     BeanPropertyAssocOne[] ones = desc.propertiesOneImported();
/*    */     
/* 43 */     for (int i = 0; i < ones.length; i++) {
/* 44 */       if (!ones[i].isImportedPrimaryKey())
/*    */       {
/*    */ 
/*    */         
/* 48 */         switch (mode) {
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
/*    */           case WHERE:
/* 62 */             list.add(new BindableAssocOne(ones[i])); break;case INSERT: if (!ones[i].isInsertable()) break; case UPDATE: if (!ones[i].isUpdateable()) break; default: list.add(new BindableAssocOne(ones[i]));
/*    */             break;
/*    */         }  } 
/*    */     } 
/* 66 */     return list;
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\persist\dmlbind\FactoryAssocOnes.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */