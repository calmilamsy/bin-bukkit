/*     */ package com.avaje.ebeaninternal.server.deploy.parse;
/*     */ 
/*     */ import com.avaje.ebean.annotation.EmbeddedColumns;
/*     */ import com.avaje.ebean.annotation.Where;
/*     */ import com.avaje.ebean.config.NamingConvention;
/*     */ import com.avaje.ebean.validation.NotNull;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanDescriptorManager;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanTable;
/*     */ import com.avaje.ebeaninternal.server.deploy.meta.DeployBeanProperty;
/*     */ import com.avaje.ebeaninternal.server.deploy.meta.DeployBeanPropertyAssocOne;
/*     */ import com.avaje.ebeaninternal.server.lib.util.StringHelper;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import javax.persistence.AttributeOverride;
/*     */ import javax.persistence.AttributeOverrides;
/*     */ import javax.persistence.Column;
/*     */ import javax.persistence.Embedded;
/*     */ import javax.persistence.EmbeddedId;
/*     */ import javax.persistence.Id;
/*     */ import javax.persistence.JoinColumn;
/*     */ import javax.persistence.JoinColumns;
/*     */ import javax.persistence.JoinTable;
/*     */ import javax.persistence.ManyToOne;
/*     */ import javax.persistence.OneToOne;
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
/*     */ public class AnnotationAssocOnes
/*     */   extends AnnotationParser
/*     */ {
/*     */   private final BeanDescriptorManager factory;
/*     */   
/*     */   public AnnotationAssocOnes(DeployBeanInfo<?> info, BeanDescriptorManager factory) {
/*  60 */     super(info);
/*  61 */     this.factory = factory;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void parse() {
/*  69 */     Iterator<DeployBeanProperty> it = this.descriptor.propertiesAll();
/*  70 */     while (it.hasNext()) {
/*  71 */       DeployBeanProperty prop = (DeployBeanProperty)it.next();
/*  72 */       if (prop instanceof DeployBeanPropertyAssocOne) {
/*  73 */         readAssocOne((DeployBeanPropertyAssocOne)prop);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void readAssocOne(DeployBeanPropertyAssocOne<?> prop) {
/*  80 */     ManyToOne manyToOne = (ManyToOne)get(prop, ManyToOne.class);
/*  81 */     if (manyToOne != null) {
/*  82 */       readManyToOne(manyToOne, prop);
/*     */     }
/*  84 */     OneToOne oneToOne = (OneToOne)get(prop, OneToOne.class);
/*  85 */     if (oneToOne != null) {
/*  86 */       readOneToOne(oneToOne, prop);
/*     */     }
/*  88 */     Embedded embedded = (Embedded)get(prop, Embedded.class);
/*  89 */     if (embedded != null) {
/*  90 */       readEmbedded(embedded, prop);
/*     */     }
/*  92 */     EmbeddedId emId = (EmbeddedId)get(prop, EmbeddedId.class);
/*  93 */     if (emId != null) {
/*  94 */       prop.setEmbedded(true);
/*  95 */       prop.setId(true);
/*  96 */       prop.setNullable(false);
/*     */     } 
/*  98 */     Column column = (Column)get(prop, Column.class);
/*  99 */     if (column != null && !isEmpty(column.name()))
/*     */     {
/*     */       
/* 102 */       prop.setDbColumn(column.name());
/*     */     }
/*     */ 
/*     */     
/* 106 */     Id id = (Id)get(prop, Id.class);
/* 107 */     if (id != null) {
/* 108 */       prop.setEmbedded(true);
/* 109 */       prop.setId(true);
/* 110 */       prop.setNullable(false);
/*     */     } 
/*     */     
/* 113 */     Where where = (Where)get(prop, Where.class);
/* 114 */     if (where != null)
/*     */     {
/* 116 */       prop.setExtraWhere(where.clause());
/*     */     }
/*     */     
/* 119 */     NotNull notNull = (NotNull)get(prop, NotNull.class);
/* 120 */     if (notNull != null) {
/* 121 */       prop.setNullable(false);
/*     */       
/* 123 */       prop.getTableJoin().setType("join");
/*     */     } 
/*     */ 
/*     */     
/* 127 */     BeanTable beanTable = prop.getBeanTable();
/* 128 */     JoinColumn joinColumn = (JoinColumn)get(prop, JoinColumn.class);
/* 129 */     if (joinColumn != null) {
/* 130 */       prop.getTableJoin().addJoinColumn(false, joinColumn, beanTable);
/* 131 */       if (!joinColumn.updatable()) {
/* 132 */         prop.setDbUpdateable(false);
/*     */       }
/*     */     } 
/*     */     
/* 136 */     JoinColumns joinColumns = (JoinColumns)get(prop, JoinColumns.class);
/* 137 */     if (joinColumns != null) {
/* 138 */       prop.getTableJoin().addJoinColumn(false, joinColumns.value(), beanTable);
/*     */     }
/*     */     
/* 141 */     JoinTable joinTable = (JoinTable)get(prop, JoinTable.class);
/* 142 */     if (joinTable != null) {
/* 143 */       prop.getTableJoin().addJoinColumn(false, joinTable.joinColumns(), beanTable);
/*     */     }
/*     */     
/* 146 */     this.info.setBeanJoinType(prop, prop.isNullable());
/*     */     
/* 148 */     if (!prop.getTableJoin().hasJoinColumns() && beanTable != null)
/*     */     {
/* 150 */       if (prop.getMappedBy() == null) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 157 */         NamingConvention nc = this.factory.getNamingConvention();
/*     */         
/* 159 */         String fkeyPrefix = null;
/* 160 */         if (nc.isUseForeignKeyPrefix()) {
/* 161 */           fkeyPrefix = nc.getColumnFromProperty(this.beanType, prop.getName());
/*     */         }
/*     */         
/* 164 */         beanTable.createJoinColumn(fkeyPrefix, prop.getTableJoin(), true);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/* 170 */   private String errorMsgMissingBeanTable(Class<?> type, String from) { return "Error with association to [" + type + "] from [" + from + "]. Is " + type + " registered?"; }
/*     */ 
/*     */ 
/*     */   
/*     */   private void readManyToOne(ManyToOne propAnn, DeployBeanProperty prop) {
/* 175 */     DeployBeanPropertyAssocOne<?> beanProp = (DeployBeanPropertyAssocOne)prop;
/*     */     
/* 177 */     setCascadeTypes(propAnn.cascade(), beanProp.getCascadeInfo());
/*     */     
/* 179 */     BeanTable assoc = this.factory.getBeanTable(beanProp.getPropertyType());
/* 180 */     if (assoc == null) {
/* 181 */       String msg = errorMsgMissingBeanTable(beanProp.getPropertyType(), prop.getFullBeanName());
/* 182 */       throw new RuntimeException(msg);
/*     */     } 
/* 184 */     beanProp.setBeanTable(assoc);
/* 185 */     beanProp.setDbInsertable(true);
/* 186 */     beanProp.setDbUpdateable(true);
/* 187 */     beanProp.setNullable(propAnn.optional());
/* 188 */     beanProp.setFetchType(propAnn.fetch());
/*     */   }
/*     */ 
/*     */   
/*     */   private void readOneToOne(OneToOne propAnn, DeployBeanPropertyAssocOne<?> prop) {
/* 193 */     prop.setOneToOne(true);
/* 194 */     prop.setDbInsertable(true);
/* 195 */     prop.setDbUpdateable(true);
/* 196 */     prop.setNullable(propAnn.optional());
/* 197 */     prop.setFetchType(propAnn.fetch());
/* 198 */     prop.setMappedBy(propAnn.mappedBy());
/* 199 */     if (!"".equals(propAnn.mappedBy())) {
/* 200 */       prop.setOneToOneExported(true);
/*     */     }
/*     */     
/* 203 */     setCascadeTypes(propAnn.cascade(), prop.getCascadeInfo());
/*     */     
/* 205 */     BeanTable assoc = this.factory.getBeanTable(prop.getPropertyType());
/* 206 */     if (assoc == null) {
/* 207 */       String msg = errorMsgMissingBeanTable(prop.getPropertyType(), prop.getFullBeanName());
/* 208 */       throw new RuntimeException(msg);
/*     */     } 
/*     */     
/* 211 */     prop.setBeanTable(assoc);
/*     */   }
/*     */ 
/*     */   
/*     */   private void readEmbedded(Embedded propAnn, DeployBeanPropertyAssocOne<?> prop) {
/* 216 */     prop.setEmbedded(true);
/* 217 */     prop.setDbInsertable(true);
/* 218 */     prop.setDbUpdateable(true);
/*     */     
/* 220 */     EmbeddedColumns columns = (EmbeddedColumns)get(prop, EmbeddedColumns.class);
/* 221 */     if (columns != null) {
/*     */ 
/*     */       
/* 224 */       String propColumns = columns.columns();
/* 225 */       Map<String, String> propMap = StringHelper.delimitedToMap(propColumns, ",", "=");
/*     */       
/* 227 */       prop.getDeployEmbedded().putAll(propMap);
/*     */     } 
/*     */     
/* 230 */     AttributeOverrides attrOverrides = (AttributeOverrides)get(prop, AttributeOverrides.class);
/* 231 */     if (attrOverrides != null) {
/* 232 */       HashMap<String, String> propMap = new HashMap<String, String>();
/* 233 */       AttributeOverride[] aoArray = attrOverrides.value();
/* 234 */       for (int i = 0; i < aoArray.length; i++) {
/* 235 */         String propName = aoArray[i].name();
/* 236 */         String columnName = aoArray[i].column().name();
/*     */         
/* 238 */         propMap.put(propName, columnName);
/*     */       } 
/*     */       
/* 241 */       prop.getDeployEmbedded().putAll(propMap);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\deploy\parse\AnnotationAssocOnes.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */