/*     */ package com.avaje.ebeaninternal.server.persist.dmlbind;
/*     */ 
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanProperty;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanPropertyAssocOne;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class MatchedImportedProperty
/*     */ {
/*     */   private final BeanPropertyAssocOne<?> assocOne;
/*     */   private final BeanProperty foreignProp;
/*     */   private final BeanProperty localProp;
/*     */   
/*     */   protected MatchedImportedProperty(BeanPropertyAssocOne<?> assocOne, BeanProperty foreignProp, BeanProperty localProp) {
/*  47 */     this.assocOne = assocOne;
/*  48 */     this.foreignProp = foreignProp;
/*  49 */     this.localProp = localProp;
/*     */   }
/*     */   
/*     */   protected void populate(Object sourceBean, Object destBean) {
/*  53 */     Object assocBean = this.assocOne.getValue(sourceBean);
/*  54 */     if (assocBean == null) {
/*  55 */       String msg = "The assoc bean for " + this.assocOne + " is null?";
/*  56 */       throw new NullPointerException(msg);
/*     */     } 
/*     */     
/*  59 */     Object value = this.foreignProp.getValue(assocBean);
/*  60 */     this.localProp.setValue(destBean, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static MatchedImportedProperty[] build(BeanProperty[] props, BeanDescriptor<?> desc) {
/*  68 */     MatchedImportedProperty[] matches = new MatchedImportedProperty[props.length];
/*     */     
/*  70 */     for (int i = 0; i < props.length; i++) {
/*     */       
/*  72 */       matches[i] = findMatch(props[i], desc);
/*  73 */       if (matches[i] == null)
/*     */       {
/*  75 */         return null;
/*     */       }
/*     */     } 
/*  78 */     return matches;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static MatchedImportedProperty findMatch(BeanProperty prop, BeanDescriptor<?> desc) {
/*  84 */     String dbColumn = prop.getDbColumn();
/*     */     
/*  86 */     BeanPropertyAssocOne[] assocOnes = desc.propertiesOne();
/*  87 */     for (int i = 0; i < assocOnes.length; i++) {
/*  88 */       if (assocOnes[i].isImportedPrimaryKey()) {
/*     */ 
/*     */         
/*  91 */         BeanProperty foreignMatch = assocOnes[i].getImportedId().findMatchImport(dbColumn);
/*     */         
/*  93 */         if (foreignMatch != null) {
/*  94 */           return new MatchedImportedProperty(assocOnes[i], foreignMatch, prop);
/*     */         }
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 101 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\persist\dmlbind\MatchedImportedProperty.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */