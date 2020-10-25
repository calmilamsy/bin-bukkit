/*    */ package com.avaje.ebeaninternal.server.deploy.meta;
/*    */ 
/*    */ import com.avaje.ebean.bean.BeanCollection;
/*    */ import com.avaje.ebeaninternal.server.deploy.ManyType;
/*    */ import com.avaje.ebeaninternal.server.type.ScalarType;
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
/*    */ public class DeployBeanPropertySimpleCollection<T>
/*    */   extends DeployBeanPropertyAssocMany<T>
/*    */ {
/*    */   private final ScalarType<T> collectionScalarType;
/*    */   
/*    */   public DeployBeanPropertySimpleCollection(DeployBeanDescriptor<?> desc, Class<T> targetType, ScalarType<T> scalarType, ManyType manyType) {
/* 31 */     super(desc, targetType, manyType);
/* 32 */     this.collectionScalarType = scalarType;
/* 33 */     this.modifyListenMode = BeanCollection.ModifyListenMode.ALL;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 40 */   public ScalarType<T> getCollectionScalarType() { return this.collectionScalarType; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 48 */   public boolean isManyToMany() { return false; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 56 */   public boolean isUnidirectional() { return true; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\deploy\meta\DeployBeanPropertySimpleCollection.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */