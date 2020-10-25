/*     */ package com.avaje.ebeaninternal.server.deploy.meta;
/*     */ 
/*     */ import com.avaje.ebean.bean.BeanCollection;
/*     */ import com.avaje.ebean.validation.factory.Validator;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanDescriptorMap;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanProperty;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanPropertyAssocMany;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanPropertyAssocOne;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanPropertyCompound;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanPropertySimpleCollection;
/*     */ import com.avaje.ebeaninternal.server.deploy.TableJoin;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedHashMap;
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
/*     */ public class DeployBeanPropertyLists
/*     */ {
/*     */   private BeanProperty derivedFirstVersionProp;
/*     */   private final BeanDescriptor<?> desc;
/*     */   private final LinkedHashMap<String, BeanProperty> propertyMap;
/*     */   private final ArrayList<BeanProperty> ids;
/*     */   private final ArrayList<BeanProperty> version;
/*     */   private final ArrayList<BeanProperty> local;
/*     */   private final ArrayList<BeanProperty> manys;
/*     */   private final ArrayList<BeanProperty> ones;
/*     */   private final ArrayList<BeanProperty> onesExported;
/*     */   private final ArrayList<BeanProperty> onesImported;
/*     */   private final ArrayList<BeanProperty> embedded;
/*     */   private final ArrayList<BeanProperty> baseScalar;
/*     */   private final ArrayList<BeanPropertyCompound> baseCompound;
/*     */   private final ArrayList<BeanProperty> transients;
/*     */   private final ArrayList<BeanProperty> nonTransients;
/*     */   private final TableJoin[] tableJoins;
/*     */   private final BeanPropertyAssocOne<?> unidirectional;
/*     */   
/*     */   public DeployBeanPropertyLists(BeanDescriptorMap owner, BeanDescriptor<?> desc, DeployBeanDescriptor<?> deploy) {
/*  49 */     this.ids = new ArrayList();
/*     */     
/*  51 */     this.version = new ArrayList();
/*     */     
/*  53 */     this.local = new ArrayList();
/*     */     
/*  55 */     this.manys = new ArrayList();
/*     */     
/*  57 */     this.ones = new ArrayList();
/*     */     
/*  59 */     this.onesExported = new ArrayList();
/*     */     
/*  61 */     this.onesImported = new ArrayList();
/*     */     
/*  63 */     this.embedded = new ArrayList();
/*     */     
/*  65 */     this.baseScalar = new ArrayList();
/*     */     
/*  67 */     this.baseCompound = new ArrayList();
/*     */     
/*  69 */     this.transients = new ArrayList();
/*     */     
/*  71 */     this.nonTransients = new ArrayList();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  79 */     this.desc = desc;
/*     */     
/*  81 */     DeployBeanPropertyAssocOne<?> deployUnidirectional = deploy.getUnidirectional();
/*  82 */     if (deployUnidirectional == null) {
/*  83 */       this.unidirectional = null;
/*     */     } else {
/*  85 */       this.unidirectional = new BeanPropertyAssocOne(owner, desc, deployUnidirectional);
/*     */     } 
/*     */     
/*  88 */     this.propertyMap = new LinkedHashMap();
/*     */     
/*  90 */     Iterator<DeployBeanProperty> deployIt = deploy.propertiesAll();
/*  91 */     while (deployIt.hasNext()) {
/*  92 */       DeployBeanProperty deployProp = (DeployBeanProperty)deployIt.next();
/*  93 */       BeanProperty beanProp = createBeanProperty(owner, deployProp);
/*  94 */       this.propertyMap.put(beanProp.getName(), beanProp);
/*     */     } 
/*     */     
/*  97 */     Iterator<BeanProperty> it = this.propertyMap.values().iterator();
/*     */     
/*  99 */     int order = 0;
/* 100 */     while (it.hasNext()) {
/* 101 */       BeanProperty prop = (BeanProperty)it.next();
/* 102 */       prop.setDeployOrder(order++);
/* 103 */       allocateToList(prop);
/*     */     } 
/*     */     
/* 106 */     List<DeployTableJoin> deployTableJoins = deploy.getTableJoins();
/* 107 */     this.tableJoins = new TableJoin[deployTableJoins.size()];
/* 108 */     for (int i = 0; i < deployTableJoins.size(); i++) {
/* 109 */       this.tableJoins[i] = new TableJoin((DeployTableJoin)deployTableJoins.get(i), this.propertyMap);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 118 */   public BeanPropertyAssocOne<?> getUnidirectional() { return this.unidirectional; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void allocateToList(BeanProperty prop) {
/* 125 */     if (prop.isTransient()) {
/* 126 */       this.transients.add(prop);
/*     */       return;
/*     */     } 
/* 129 */     if (prop.isId()) {
/* 130 */       this.ids.add(prop);
/*     */       return;
/*     */     } 
/* 133 */     this.nonTransients.add(prop);
/*     */ 
/*     */     
/* 136 */     if (this.desc.getInheritInfo() != null && prop.isLocal()) {
/* 137 */       this.local.add(prop);
/*     */     }
/*     */     
/* 140 */     if (prop instanceof BeanPropertyAssocMany) {
/* 141 */       this.manys.add(prop);
/*     */     }
/* 143 */     else if (prop instanceof BeanPropertyAssocOne) {
/* 144 */       if (prop.isEmbedded()) {
/* 145 */         this.embedded.add(prop);
/*     */       } else {
/* 147 */         this.ones.add(prop);
/* 148 */         BeanPropertyAssocOne<?> assocOne = (BeanPropertyAssocOne)prop;
/* 149 */         if (assocOne.isOneToOneExported()) {
/* 150 */           this.onesExported.add(prop);
/*     */         } else {
/* 152 */           this.onesImported.add(prop);
/*     */         }
/*     */       
/*     */       } 
/*     */     } else {
/*     */       
/* 158 */       if (prop.isVersion()) {
/* 159 */         this.version.add(prop);
/* 160 */         if (this.derivedFirstVersionProp == null) {
/* 161 */           this.derivedFirstVersionProp = prop;
/*     */         }
/*     */       } 
/* 164 */       if (prop instanceof BeanPropertyCompound) {
/* 165 */         this.baseCompound.add((BeanPropertyCompound)prop);
/*     */       } else {
/* 167 */         this.baseScalar.add(prop);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/* 173 */   public BeanProperty getFirstVersion() { return this.derivedFirstVersionProp; }
/*     */ 
/*     */ 
/*     */   
/*     */   public BeanProperty[] getPropertiesWithValidators(boolean recurse) {
/* 178 */     ArrayList<BeanProperty> list = new ArrayList<BeanProperty>();
/* 179 */     Iterator<BeanProperty> it = this.propertyMap.values().iterator();
/* 180 */     while (it.hasNext()) {
/* 181 */       BeanProperty property = (BeanProperty)it.next();
/* 182 */       if (property.hasValidationRules(recurse)) {
/* 183 */         list.add(property);
/*     */       }
/*     */     } 
/* 186 */     return (BeanProperty[])list.toArray(new BeanProperty[list.size()]);
/*     */   }
/*     */ 
/*     */   
/* 190 */   public Validator[] getBeanValidators() { return new Validator[0]; }
/*     */ 
/*     */ 
/*     */   
/* 194 */   public LinkedHashMap<String, BeanProperty> getPropertyMap() { return this.propertyMap; }
/*     */ 
/*     */ 
/*     */   
/* 198 */   public TableJoin[] getTableJoin() { return this.tableJoins; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 206 */   public BeanProperty[] getBaseScalar() { return (BeanProperty[])this.baseScalar.toArray(new BeanProperty[this.baseScalar.size()]); }
/*     */ 
/*     */ 
/*     */   
/* 210 */   public BeanPropertyCompound[] getBaseCompound() { return (BeanPropertyCompound[])this.baseCompound.toArray(new BeanPropertyCompound[this.baseCompound.size()]); }
/*     */ 
/*     */ 
/*     */   
/* 214 */   public BeanProperty[] getId() { return (BeanProperty[])this.ids.toArray(new BeanProperty[this.ids.size()]); }
/*     */ 
/*     */ 
/*     */   
/* 218 */   public BeanProperty[] getNonTransients() { return (BeanProperty[])this.nonTransients.toArray(new BeanProperty[this.nonTransients.size()]); }
/*     */ 
/*     */ 
/*     */   
/* 222 */   public BeanProperty[] getTransients() { return (BeanProperty[])this.transients.toArray(new BeanProperty[this.transients.size()]); }
/*     */ 
/*     */ 
/*     */   
/* 226 */   public BeanProperty[] getVersion() { return (BeanProperty[])this.version.toArray(new BeanProperty[this.version.size()]); }
/*     */ 
/*     */ 
/*     */   
/* 230 */   public BeanProperty[] getLocal() { return (BeanProperty[])this.local.toArray(new BeanProperty[this.local.size()]); }
/*     */ 
/*     */ 
/*     */   
/* 234 */   public BeanPropertyAssocOne<?>[] getEmbedded() { return (BeanPropertyAssocOne[])this.embedded.toArray(new BeanPropertyAssocOne[this.embedded.size()]); }
/*     */ 
/*     */ 
/*     */   
/* 238 */   public BeanPropertyAssocOne<?>[] getOneExported() { return (BeanPropertyAssocOne[])this.onesExported.toArray(new BeanPropertyAssocOne[this.onesExported.size()]); }
/*     */ 
/*     */ 
/*     */   
/* 242 */   public BeanPropertyAssocOne<?>[] getOneImported() { return (BeanPropertyAssocOne[])this.onesImported.toArray(new BeanPropertyAssocOne[this.onesImported.size()]); }
/*     */ 
/*     */ 
/*     */   
/* 246 */   public BeanPropertyAssocOne<?>[] getOnes() { return (BeanPropertyAssocOne[])this.ones.toArray(new BeanPropertyAssocOne[this.ones.size()]); }
/*     */ 
/*     */ 
/*     */   
/* 250 */   public BeanPropertyAssocOne<?>[] getOneExportedSave() { return getOne(false, Mode.Save); }
/*     */ 
/*     */ 
/*     */   
/* 254 */   public BeanPropertyAssocOne<?>[] getOneExportedDelete() { return getOne(false, Mode.Delete); }
/*     */ 
/*     */ 
/*     */   
/* 258 */   public BeanPropertyAssocOne<?>[] getOneImportedSave() { return getOne(true, Mode.Save); }
/*     */ 
/*     */ 
/*     */   
/* 262 */   public BeanPropertyAssocOne<?>[] getOneImportedDelete() { return getOne(true, Mode.Delete); }
/*     */ 
/*     */ 
/*     */   
/* 266 */   public BeanPropertyAssocMany<?>[] getMany() { return (BeanPropertyAssocMany[])this.manys.toArray(new BeanPropertyAssocMany[this.manys.size()]); }
/*     */ 
/*     */ 
/*     */   
/* 270 */   public BeanPropertyAssocMany<?>[] getManySave() { return getMany(Mode.Save); }
/*     */ 
/*     */ 
/*     */   
/* 274 */   public BeanPropertyAssocMany<?>[] getManyDelete() { return getMany(Mode.Delete); }
/*     */ 
/*     */ 
/*     */   
/* 278 */   public BeanPropertyAssocMany<?>[] getManyToMany() { return getMany2Many(); }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private enum Mode
/*     */   {
/* 285 */     Save, Delete, Validate;
/*     */   }
/*     */   
/*     */   private BeanPropertyAssocOne<?>[] getOne(boolean imported, Mode mode) {
/* 289 */     ArrayList<BeanPropertyAssocOne<?>> list = new ArrayList<BeanPropertyAssocOne<?>>();
/* 290 */     for (int i = 0; i < this.ones.size(); i++) {
/* 291 */       BeanPropertyAssocOne<?> prop = (BeanPropertyAssocOne)this.ones.get(i);
/* 292 */       if (imported != prop.isOneToOneExported()) {
/* 293 */         switch (mode) {
/*     */           case Save:
/* 295 */             if (prop.getCascadeInfo().isSave()) {
/* 296 */               list.add(prop);
/*     */             }
/*     */             break;
/*     */           case Delete:
/* 300 */             if (prop.getCascadeInfo().isDelete()) {
/* 301 */               list.add(prop);
/*     */             }
/*     */             break;
/*     */           case Validate:
/* 305 */             if (prop.getCascadeInfo().isValidate()) {
/* 306 */               list.add(prop);
/*     */             }
/*     */             break;
/*     */         } 
/*     */ 
/*     */ 
/*     */       
/*     */       }
/*     */     } 
/* 315 */     return (BeanPropertyAssocOne[])list.toArray(new BeanPropertyAssocOne[list.size()]);
/*     */   }
/*     */   
/*     */   private BeanPropertyAssocMany<?>[] getMany2Many() {
/* 319 */     ArrayList<BeanPropertyAssocMany<?>> list = new ArrayList<BeanPropertyAssocMany<?>>();
/* 320 */     for (int i = 0; i < this.manys.size(); i++) {
/* 321 */       BeanPropertyAssocMany<?> prop = (BeanPropertyAssocMany)this.manys.get(i);
/* 322 */       if (prop.isManyToMany()) {
/* 323 */         list.add(prop);
/*     */       }
/*     */     } 
/*     */     
/* 327 */     return (BeanPropertyAssocMany[])list.toArray(new BeanPropertyAssocMany[list.size()]);
/*     */   }
/*     */   
/*     */   private BeanPropertyAssocMany<?>[] getMany(Mode mode) {
/* 331 */     ArrayList<BeanPropertyAssocMany<?>> list = new ArrayList<BeanPropertyAssocMany<?>>();
/* 332 */     for (int i = 0; i < this.manys.size(); i++) {
/* 333 */       BeanPropertyAssocMany<?> prop = (BeanPropertyAssocMany)this.manys.get(i);
/*     */       
/* 335 */       switch (mode) {
/*     */         case Save:
/* 337 */           if (prop.getCascadeInfo().isSave() || prop.isManyToMany() || BeanCollection.ModifyListenMode.REMOVALS.equals(prop.getModifyListenMode()))
/*     */           {
/*     */ 
/*     */ 
/*     */             
/* 342 */             list.add(prop);
/*     */           }
/*     */           break;
/*     */         case Delete:
/* 346 */           if (prop.getCascadeInfo().isDelete() || BeanCollection.ModifyListenMode.REMOVALS.equals(prop.getModifyListenMode()))
/*     */           {
/*     */             
/* 349 */             list.add(prop);
/*     */           }
/*     */           break;
/*     */         case Validate:
/* 353 */           if (prop.getCascadeInfo().isValidate()) {
/* 354 */             list.add(prop);
/*     */           }
/*     */           break;
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     } 
/* 363 */     return (BeanPropertyAssocMany[])list.toArray(new BeanPropertyAssocMany[list.size()]);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private BeanProperty createBeanProperty(BeanDescriptorMap owner, DeployBeanProperty deployProp) {
/* 369 */     if (deployProp instanceof DeployBeanPropertyAssocOne)
/*     */     {
/* 371 */       return new BeanPropertyAssocOne(owner, this.desc, (DeployBeanPropertyAssocOne)deployProp);
/*     */     }
/* 373 */     if (deployProp instanceof DeployBeanPropertySimpleCollection)
/*     */     {
/* 375 */       return new BeanPropertySimpleCollection(owner, this.desc, (DeployBeanPropertySimpleCollection)deployProp);
/*     */     }
/* 377 */     if (deployProp instanceof DeployBeanPropertyAssocMany)
/*     */     {
/* 379 */       return new BeanPropertyAssocMany(owner, this.desc, (DeployBeanPropertyAssocMany)deployProp);
/*     */     }
/* 381 */     if (deployProp instanceof DeployBeanPropertyCompound)
/*     */     {
/* 383 */       return new BeanPropertyCompound(owner, this.desc, (DeployBeanPropertyCompound)deployProp);
/*     */     }
/*     */     
/* 386 */     return new BeanProperty(owner, this.desc, deployProp);
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\deploy\meta\DeployBeanPropertyLists.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */