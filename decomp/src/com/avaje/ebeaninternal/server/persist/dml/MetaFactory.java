/*     */ package com.avaje.ebeaninternal.server.persist.dml;
/*     */ 
/*     */ import com.avaje.ebean.config.dbplatform.DatabasePlatform;
/*     */ import com.avaje.ebean.config.dbplatform.DbEncrypt;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanPropertyAssocOne;
/*     */ import com.avaje.ebeaninternal.server.persist.dmlbind.Bindable;
/*     */ import com.avaje.ebeaninternal.server.persist.dmlbind.BindableId;
/*     */ import com.avaje.ebeaninternal.server.persist.dmlbind.BindableList;
/*     */ import com.avaje.ebeaninternal.server.persist.dmlbind.BindableUnidirectional;
/*     */ import com.avaje.ebeaninternal.server.persist.dmlbind.FactoryAssocOnes;
/*     */ import com.avaje.ebeaninternal.server.persist.dmlbind.FactoryBaseProperties;
/*     */ import com.avaje.ebeaninternal.server.persist.dmlbind.FactoryEmbedded;
/*     */ import com.avaje.ebeaninternal.server.persist.dmlbind.FactoryId;
/*     */ import com.avaje.ebeaninternal.server.persist.dmlbind.FactoryVersion;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
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
/*     */ public class MetaFactory
/*     */ {
/*     */   private final FactoryBaseProperties baseFact;
/*     */   private final FactoryEmbedded embeddedFact;
/*     */   private final FactoryVersion versionFact;
/*     */   private final FactoryAssocOnes assocOneFact;
/*     */   private final FactoryId idFact;
/*     */   private static final boolean includeLobs = true;
/*     */   private final DatabasePlatform dbPlatform;
/*     */   private final boolean emptyStringAsNull;
/*     */   
/*     */   public MetaFactory(DatabasePlatform dbPlatform) {
/*  46 */     this.versionFact = new FactoryVersion();
/*  47 */     this.assocOneFact = new FactoryAssocOnes();
/*     */     
/*  49 */     this.idFact = new FactoryId();
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
/*  62 */     this.dbPlatform = dbPlatform;
/*  63 */     this.emptyStringAsNull = dbPlatform.isTreatEmptyStringsAsNull();
/*     */ 
/*     */     
/*  66 */     DbEncrypt dbEncrypt = dbPlatform.getDbEncrypt();
/*  67 */     boolean bindEncryptDataFirst = (dbEncrypt == null) ? true : dbEncrypt.isBindEncryptDataFirst();
/*     */     
/*  69 */     this.baseFact = new FactoryBaseProperties(bindEncryptDataFirst);
/*  70 */     this.embeddedFact = new FactoryEmbedded(bindEncryptDataFirst);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public UpdateMeta createUpdate(BeanDescriptor<?> desc) {
/*  78 */     List<Bindable> setList = new ArrayList<Bindable>();
/*     */     
/*  80 */     this.baseFact.create(setList, desc, DmlMode.UPDATE, true);
/*  81 */     this.embeddedFact.create(setList, desc, DmlMode.UPDATE, true);
/*  82 */     this.assocOneFact.create(setList, desc, DmlMode.UPDATE);
/*     */     
/*  84 */     BindableId bindableId = this.idFact.createId(desc);
/*     */     
/*  86 */     Bindable ver = this.versionFact.create(desc);
/*     */ 
/*     */     
/*  89 */     List<Bindable> allList = new ArrayList<Bindable>();
/*     */     
/*  91 */     this.baseFact.create(allList, desc, DmlMode.WHERE, false);
/*  92 */     this.embeddedFact.create(allList, desc, DmlMode.WHERE, false);
/*  93 */     this.assocOneFact.create(allList, desc, DmlMode.WHERE);
/*     */ 
/*     */     
/*  96 */     BindableList bindableList1 = new BindableList(setList);
/*  97 */     BindableList bindableList2 = new BindableList(allList);
/*     */     
/*  99 */     return new UpdateMeta(this.emptyStringAsNull, desc, bindableList1, bindableId, ver, bindableList2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DeleteMeta createDelete(BeanDescriptor<?> desc) {
/* 107 */     BindableId bindableId = this.idFact.createId(desc);
/*     */     
/* 109 */     Bindable ver = this.versionFact.create(desc);
/*     */     
/* 111 */     List<Bindable> allList = new ArrayList<Bindable>();
/*     */     
/* 113 */     this.baseFact.create(allList, desc, DmlMode.WHERE, false);
/* 114 */     this.embeddedFact.create(allList, desc, DmlMode.WHERE, false);
/* 115 */     this.assocOneFact.create(allList, desc, DmlMode.WHERE);
/*     */     
/* 117 */     BindableList bindableList = new BindableList(allList);
/*     */     
/* 119 */     return new DeleteMeta(this.emptyStringAsNull, desc, bindableId, ver, bindableList);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public InsertMeta createInsert(BeanDescriptor<?> desc) {
/*     */     BindableUnidirectional bindableUnidirectional;
/* 127 */     BindableId id = this.idFact.createId(desc);
/*     */     
/* 129 */     List<Bindable> allList = new ArrayList<Bindable>();
/*     */     
/* 131 */     this.baseFact.create(allList, desc, DmlMode.INSERT, true);
/* 132 */     this.embeddedFact.create(allList, desc, DmlMode.INSERT, true);
/* 133 */     this.assocOneFact.create(allList, desc, DmlMode.INSERT);
/*     */     
/* 135 */     BindableList bindableList = new BindableList(allList);
/*     */     
/* 137 */     BeanPropertyAssocOne<?> unidirectional = desc.getUnidirectional();
/*     */ 
/*     */     
/* 140 */     if (unidirectional == null) {
/* 141 */       bindableUnidirectional = null;
/*     */     } else {
/* 143 */       bindableUnidirectional = new BindableUnidirectional(desc, unidirectional);
/*     */     } 
/*     */     
/* 146 */     return new InsertMeta(this.dbPlatform, desc, bindableUnidirectional, id, bindableList);
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\persist\dml\MetaFactory.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */