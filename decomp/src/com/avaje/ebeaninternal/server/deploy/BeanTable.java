/*     */ package com.avaje.ebeaninternal.server.deploy;
/*     */ 
/*     */ import com.avaje.ebean.config.GlobalProperties;
/*     */ import com.avaje.ebeaninternal.server.core.InternString;
/*     */ import com.avaje.ebeaninternal.server.deploy.meta.DeployBeanTable;
/*     */ import com.avaje.ebeaninternal.server.deploy.meta.DeployTableJoin;
/*     */ import com.avaje.ebeaninternal.server.deploy.meta.DeployTableJoinColumn;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
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
/*     */ public class BeanTable
/*     */ {
/*  43 */   private static final Logger logger = Logger.getLogger(BeanTable.class.getName());
/*     */ 
/*     */ 
/*     */   
/*     */   private final Class<?> beanType;
/*     */ 
/*     */   
/*     */   private final String baseTable;
/*     */ 
/*     */   
/*     */   private final BeanProperty[] idProperties;
/*     */ 
/*     */ 
/*     */   
/*     */   public BeanTable(DeployBeanTable mutable, BeanDescriptorMap owner) {
/*  58 */     this.beanType = mutable.getBeanType();
/*  59 */     this.baseTable = InternString.intern(mutable.getBaseTable());
/*  60 */     this.idProperties = mutable.createIdProperties(owner);
/*     */   }
/*     */ 
/*     */   
/*  64 */   public String toString() { return this.baseTable; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  73 */   public String getBaseTable() { return this.baseTable; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getUnqualifiedBaseTable() {
/*  82 */     String[] chunks = this.baseTable.split("\\.");
/*  83 */     return (chunks.length == 2) ? chunks[1] : chunks[0];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  90 */   public BeanProperty[] getIdProperties() { return this.idProperties; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  97 */   public Class<?> getBeanType() { return this.beanType; }
/*     */ 
/*     */ 
/*     */   
/*     */   public void createJoinColumn(String foreignKeyPrefix, DeployTableJoin join, boolean reverse) {
/* 102 */     boolean complexKey = false;
/* 103 */     BeanProperty[] props = this.idProperties;
/*     */     
/* 105 */     if (this.idProperties.length == 1 && 
/* 106 */       this.idProperties[0] instanceof BeanPropertyAssocOne) {
/* 107 */       BeanPropertyAssocOne<?> assocOne = (BeanPropertyAssocOne)this.idProperties[0];
/* 108 */       props = assocOne.getProperties();
/* 109 */       complexKey = true;
/*     */     } 
/*     */ 
/*     */     
/* 113 */     for (int i = 0; i < props.length; i++) {
/*     */       
/* 115 */       String lc = props[i].getDbColumn();
/* 116 */       String fk = lc;
/* 117 */       if (foreignKeyPrefix != null) {
/* 118 */         fk = foreignKeyPrefix + "_" + fk;
/*     */       }
/*     */       
/* 121 */       if (complexKey) {
/*     */         
/* 123 */         boolean usePrefixOnComplex = GlobalProperties.getBoolean("ebean.prefixComplexKeys", false);
/* 124 */         if (!usePrefixOnComplex) {
/*     */ 
/*     */           
/* 127 */           String msg = "On table[" + this.baseTable + "] foreign key column [" + lc + "]";
/* 128 */           logger.log(Level.FINE, msg);
/* 129 */           fk = lc;
/*     */         } 
/*     */       } 
/*     */       
/* 133 */       DeployTableJoinColumn joinCol = new DeployTableJoinColumn(lc, fk);
/* 134 */       if (reverse) {
/* 135 */         joinCol = joinCol.reverse();
/*     */       }
/* 137 */       join.addJoinColumn(joinCol);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\deploy\BeanTable.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */