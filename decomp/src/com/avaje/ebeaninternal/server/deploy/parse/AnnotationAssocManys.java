/*     */ package com.avaje.ebeaninternal.server.deploy.parse;
/*     */ 
/*     */ import com.avaje.ebean.annotation.LdapAttribute;
/*     */ import com.avaje.ebean.annotation.PrivateOwned;
/*     */ import com.avaje.ebean.annotation.Where;
/*     */ import com.avaje.ebean.bean.BeanCollection;
/*     */ import com.avaje.ebean.config.NamingConvention;
/*     */ import com.avaje.ebean.config.TableName;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanDescriptorManager;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanProperty;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanTable;
/*     */ import com.avaje.ebeaninternal.server.deploy.meta.DeployBeanProperty;
/*     */ import com.avaje.ebeaninternal.server.deploy.meta.DeployBeanPropertyAssocMany;
/*     */ import com.avaje.ebeaninternal.server.deploy.meta.DeployTableJoin;
/*     */ import com.avaje.ebeaninternal.server.deploy.meta.DeployTableJoinColumn;
/*     */ import com.avaje.ebeaninternal.server.lib.util.StringHelper;
/*     */ import java.util.Iterator;
/*     */ import javax.persistence.JoinColumn;
/*     */ import javax.persistence.JoinColumns;
/*     */ import javax.persistence.JoinTable;
/*     */ import javax.persistence.ManyToMany;
/*     */ import javax.persistence.MapKey;
/*     */ import javax.persistence.OneToMany;
/*     */ import javax.persistence.OrderBy;
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
/*     */ public class AnnotationAssocManys
/*     */   extends AnnotationParser
/*     */ {
/*     */   private final BeanDescriptorManager factory;
/*     */   
/*     */   public AnnotationAssocManys(DeployBeanInfo<?> info, BeanDescriptorManager factory) {
/*  59 */     super(info);
/*  60 */     this.factory = factory;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void parse() {
/*  67 */     Iterator<DeployBeanProperty> it = this.descriptor.propertiesAll();
/*  68 */     while (it.hasNext()) {
/*  69 */       DeployBeanProperty prop = (DeployBeanProperty)it.next();
/*  70 */       if (prop instanceof DeployBeanPropertyAssocMany) {
/*  71 */         read((DeployBeanPropertyAssocMany)prop);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void read(DeployBeanPropertyAssocMany<?> prop) {
/*  78 */     OneToMany oneToMany = (OneToMany)get(prop, OneToMany.class);
/*  79 */     if (oneToMany != null) {
/*  80 */       readToOne(oneToMany, prop);
/*  81 */       PrivateOwned privateOwned = (PrivateOwned)get(prop, PrivateOwned.class);
/*  82 */       if (privateOwned != null) {
/*  83 */         prop.setModifyListenMode(BeanCollection.ModifyListenMode.REMOVALS);
/*  84 */         prop.getCascadeInfo().setDelete(privateOwned.cascadeRemove());
/*     */       } 
/*     */     } 
/*  87 */     ManyToMany manyToMany = (ManyToMany)get(prop, ManyToMany.class);
/*  88 */     if (manyToMany != null) {
/*  89 */       readToMany(manyToMany, prop);
/*     */     }
/*     */     
/*  92 */     OrderBy orderBy = (OrderBy)get(prop, OrderBy.class);
/*  93 */     if (orderBy != null) {
/*  94 */       prop.setFetchOrderBy(orderBy.value());
/*     */     }
/*     */     
/*  97 */     MapKey mapKey = (MapKey)get(prop, MapKey.class);
/*  98 */     if (mapKey != null) {
/*  99 */       prop.setMapKey(mapKey.name());
/*     */     }
/*     */     
/* 102 */     Where where = (Where)get(prop, Where.class);
/* 103 */     if (where != null) {
/* 104 */       prop.setExtraWhere(where.clause());
/*     */     }
/*     */ 
/*     */     
/* 108 */     BeanTable beanTable = prop.getBeanTable();
/* 109 */     JoinColumn joinColumn = (JoinColumn)get(prop, JoinColumn.class);
/* 110 */     if (joinColumn != null) {
/* 111 */       prop.getTableJoin().addJoinColumn(true, joinColumn, beanTable);
/*     */     }
/*     */     
/* 114 */     JoinColumns joinColumns = (JoinColumns)get(prop, JoinColumns.class);
/* 115 */     if (joinColumns != null) {
/* 116 */       prop.getTableJoin().addJoinColumn(true, joinColumns.value(), beanTable);
/*     */     }
/*     */     
/* 119 */     JoinTable joinTable = (JoinTable)get(prop, JoinTable.class);
/* 120 */     if (joinTable != null) {
/* 121 */       if (prop.isManyToMany()) {
/*     */         
/* 123 */         readJoinTable(joinTable, prop);
/*     */       }
/*     */       else {
/*     */         
/* 127 */         prop.getTableJoin().addJoinColumn(true, joinTable.joinColumns(), beanTable);
/*     */       } 
/*     */     }
/* 130 */     LdapAttribute ldapAttribute = (LdapAttribute)get(prop, LdapAttribute.class);
/* 131 */     if (ldapAttribute != null)
/*     */     {
/* 133 */       readLdapAttribute(ldapAttribute, prop);
/*     */     }
/*     */     
/* 136 */     if (prop.getMappedBy() != null) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 143 */     if (prop.isManyToMany()) {
/* 144 */       manyToManyDefaultJoins(prop);
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 149 */     if (!prop.getTableJoin().hasJoinColumns() && beanTable != null) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 154 */       NamingConvention nc = this.factory.getNamingConvention();
/*     */       
/* 156 */       String fkeyPrefix = null;
/* 157 */       if (nc.isUseForeignKeyPrefix()) {
/* 158 */         fkeyPrefix = nc.getColumnFromProperty(this.descriptor.getBeanType(), this.descriptor.getName());
/*     */       }
/*     */ 
/*     */       
/* 162 */       BeanTable owningBeanTable = this.factory.getBeanTable(this.descriptor.getBeanType());
/* 163 */       owningBeanTable.createJoinColumn(fkeyPrefix, prop.getTableJoin(), false);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void readJoinTable(JoinTable joinTable, DeployBeanPropertyAssocMany<?> prop) {
/* 176 */     String intTableName = getFullTableName(joinTable);
/*     */     
/* 178 */     DeployTableJoin intJoin = new DeployTableJoin();
/* 179 */     intJoin.setTable(intTableName);
/*     */ 
/*     */     
/* 182 */     intJoin.addJoinColumn(true, joinTable.joinColumns(), prop.getBeanTable());
/*     */ 
/*     */     
/* 185 */     DeployTableJoin destJoin = prop.getTableJoin();
/* 186 */     destJoin.addJoinColumn(false, joinTable.inverseJoinColumns(), prop.getBeanTable());
/*     */     
/* 188 */     intJoin.setType("left outer join");
/*     */ 
/*     */     
/* 191 */     DeployTableJoin inverseDest = destJoin.createInverse(intTableName);
/* 192 */     prop.setIntersectionJoin(intJoin);
/* 193 */     prop.setInverseJoin(inverseDest);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String getFullTableName(JoinTable joinTable) {
/* 202 */     StringBuilder sb = new StringBuilder();
/* 203 */     if (!StringHelper.isNull(joinTable.catalog())) {
/* 204 */       sb.append(joinTable.catalog()).append(".");
/*     */     }
/* 206 */     if (!StringHelper.isNull(joinTable.schema())) {
/* 207 */       sb.append(joinTable.schema()).append(".");
/*     */     }
/* 209 */     sb.append(joinTable.name());
/* 210 */     return sb.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void manyToManyDefaultJoins(DeployBeanPropertyAssocMany<?> prop) {
/* 222 */     String intTableName = null;
/*     */     
/* 224 */     DeployTableJoin intJoin = prop.getIntersectionJoin();
/* 225 */     if (intJoin == null) {
/* 226 */       intJoin = new DeployTableJoin();
/* 227 */       prop.setIntersectionJoin(intJoin);
/*     */     } else {
/*     */       
/* 230 */       intTableName = intJoin.getTable();
/*     */     } 
/*     */     
/* 233 */     BeanTable localTable = this.factory.getBeanTable(this.descriptor.getBeanType());
/* 234 */     BeanTable otherTable = this.factory.getBeanTable(prop.getTargetType());
/*     */     
/* 236 */     String localTableName = localTable.getUnqualifiedBaseTable();
/* 237 */     String otherTableName = otherTable.getUnqualifiedBaseTable();
/*     */     
/* 239 */     if (intTableName == null) {
/*     */       
/* 241 */       intTableName = getM2MJoinTableName(localTable, otherTable);
/*     */       
/* 243 */       intJoin.setTable(intTableName);
/* 244 */       intJoin.setType("left outer join");
/*     */     } 
/*     */     
/* 247 */     DeployTableJoin destJoin = prop.getTableJoin();
/*     */ 
/*     */     
/* 250 */     if (intJoin.hasJoinColumns() && destJoin.hasJoinColumns()) {
/*     */       return;
/*     */     }
/*     */     
/* 254 */     if (!intJoin.hasJoinColumns()) {
/*     */       
/* 256 */       BeanProperty[] localIds = localTable.getIdProperties();
/* 257 */       for (int i = 0; i < localIds.length; i++) {
/*     */         
/* 259 */         String fkCol = localTableName + "_" + localIds[i].getDbColumn();
/* 260 */         intJoin.addJoinColumn(new DeployTableJoinColumn(localIds[i].getDbColumn(), fkCol));
/*     */       } 
/*     */     } 
/*     */     
/* 264 */     if (!destJoin.hasJoinColumns()) {
/*     */       
/* 266 */       BeanProperty[] otherIds = otherTable.getIdProperties();
/* 267 */       for (int i = 0; i < otherIds.length; i++) {
/*     */         
/* 269 */         String fkCol = otherTableName + "_" + otherIds[i].getDbColumn();
/* 270 */         destJoin.addJoinColumn(new DeployTableJoinColumn(fkCol, otherIds[i].getDbColumn()));
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 275 */     DeployTableJoin inverseDest = destJoin.createInverse(intTableName);
/* 276 */     prop.setInverseJoin(inverseDest);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 282 */   private String errorMsgMissingBeanTable(Class<?> type, String from) { return "Error with association to [" + type + "] from [" + from + "]. Is " + type + " registered?"; }
/*     */ 
/*     */ 
/*     */   
/*     */   private void readToMany(ManyToMany propAnn, DeployBeanPropertyAssocMany<?> manyProp) {
/* 287 */     manyProp.setMappedBy(propAnn.mappedBy());
/* 288 */     manyProp.setFetchType(propAnn.fetch());
/*     */     
/* 290 */     setCascadeTypes(propAnn.cascade(), manyProp.getCascadeInfo());
/*     */     
/* 292 */     Class<?> targetType = propAnn.targetEntity();
/* 293 */     if (targetType.equals(void.class)) {
/*     */       
/* 295 */       targetType = manyProp.getTargetType();
/*     */     } else {
/* 297 */       manyProp.setTargetType(targetType);
/*     */     } 
/*     */ 
/*     */     
/* 301 */     BeanTable assoc = this.factory.getBeanTable(targetType);
/* 302 */     if (assoc == null) {
/* 303 */       String msg = errorMsgMissingBeanTable(targetType, manyProp.getFullBeanName());
/* 304 */       throw new RuntimeException(msg);
/*     */     } 
/*     */     
/* 307 */     manyProp.setManyToMany(true);
/* 308 */     manyProp.setModifyListenMode(BeanCollection.ModifyListenMode.ALL);
/* 309 */     manyProp.setBeanTable(assoc);
/* 310 */     manyProp.getTableJoin().setType("left outer join");
/*     */   }
/*     */ 
/*     */   
/*     */   private void readToOne(OneToMany propAnn, DeployBeanPropertyAssocMany<?> manyProp) {
/* 315 */     manyProp.setMappedBy(propAnn.mappedBy());
/* 316 */     manyProp.setFetchType(propAnn.fetch());
/*     */     
/* 318 */     setCascadeTypes(propAnn.cascade(), manyProp.getCascadeInfo());
/*     */     
/* 320 */     Class<?> targetType = propAnn.targetEntity();
/* 321 */     if (targetType.equals(void.class)) {
/*     */       
/* 323 */       targetType = manyProp.getTargetType();
/*     */     } else {
/* 325 */       manyProp.setTargetType(targetType);
/*     */     } 
/*     */     
/* 328 */     BeanTable assoc = this.factory.getBeanTable(targetType);
/* 329 */     if (assoc == null) {
/* 330 */       String msg = errorMsgMissingBeanTable(targetType, manyProp.getFullBeanName());
/* 331 */       throw new RuntimeException(msg);
/*     */     } 
/*     */     
/* 334 */     manyProp.setBeanTable(assoc);
/* 335 */     manyProp.getTableJoin().setType("left outer join");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private String getM2MJoinTableName(BeanTable lhsTable, BeanTable rhsTable) {
/* 341 */     TableName lhs = new TableName(lhsTable.getBaseTable());
/* 342 */     TableName rhs = new TableName(rhsTable.getBaseTable());
/*     */     
/* 344 */     TableName joinTable = this.namingConvention.getM2MJoinTableName(lhs, rhs);
/*     */     
/* 346 */     return joinTable.getQualifiedName();
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\deploy\parse\AnnotationAssocManys.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */