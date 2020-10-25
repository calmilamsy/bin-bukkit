/*     */ package com.avaje.ebeaninternal.server.deploy.parse;
/*     */ 
/*     */ import com.avaje.ebean.Query;
/*     */ import com.avaje.ebean.annotation.CacheStrategy;
/*     */ import com.avaje.ebean.annotation.LdapDomain;
/*     */ import com.avaje.ebean.annotation.NamedUpdate;
/*     */ import com.avaje.ebean.annotation.NamedUpdates;
/*     */ import com.avaje.ebean.annotation.UpdateMode;
/*     */ import com.avaje.ebean.config.TableName;
/*     */ import com.avaje.ebeaninternal.server.core.ReferenceOptions;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
/*     */ import com.avaje.ebeaninternal.server.deploy.CompoundUniqueContraint;
/*     */ import com.avaje.ebeaninternal.server.deploy.DeployNamedQuery;
/*     */ import com.avaje.ebeaninternal.server.deploy.DeployNamedUpdate;
/*     */ import javax.persistence.Embeddable;
/*     */ import javax.persistence.Entity;
/*     */ import javax.persistence.NamedQueries;
/*     */ import javax.persistence.NamedQuery;
/*     */ import javax.persistence.Table;
/*     */ import javax.persistence.UniqueConstraint;
/*     */ import javax.xml.bind.annotation.XmlRootElement;
/*     */ import javax.xml.bind.annotation.XmlType;
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
/*     */ public class AnnotationClass
/*     */   extends AnnotationParser
/*     */ {
/*  50 */   public AnnotationClass(DeployBeanInfo<?> info) { super(info); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void parse() {
/*  57 */     read(this.descriptor.getBeanType());
/*  58 */     setTableName();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void setTableName() {
/*  66 */     if (this.descriptor.isBaseTableType()) {
/*     */ 
/*     */       
/*  69 */       TableName tableName = this.namingConvention.getTableName(this.descriptor.getBeanType());
/*     */       
/*  71 */       this.descriptor.setBaseTable(tableName);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private String[] parseLdapObjectclasses(String objectclasses) {
/*  77 */     if (objectclasses == null || objectclasses.length() == 0) {
/*  78 */       return null;
/*     */     }
/*  80 */     return objectclasses.split(",");
/*     */   }
/*     */   
/*     */   private boolean isXmlElement(Class<?> cls) {
/*  84 */     XmlRootElement rootElement = (XmlRootElement)cls.getAnnotation(XmlRootElement.class);
/*  85 */     if (rootElement != null) {
/*  86 */       return true;
/*     */     }
/*  88 */     XmlType xmlType = (XmlType)cls.getAnnotation(XmlType.class);
/*  89 */     if (xmlType != null) {
/*  90 */       return true;
/*     */     }
/*  92 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   private void read(Class<?> cls) {
/*  97 */     LdapDomain ldapDomain = (LdapDomain)cls.getAnnotation(LdapDomain.class);
/*  98 */     if (ldapDomain != null) {
/*  99 */       this.descriptor.setName(cls.getSimpleName());
/* 100 */       this.descriptor.setEntityType(BeanDescriptor.EntityType.LDAP);
/* 101 */       this.descriptor.setLdapBaseDn(ldapDomain.baseDn());
/* 102 */       this.descriptor.setLdapObjectclasses(parseLdapObjectclasses(ldapDomain.objectclass()));
/*     */     } 
/*     */     
/* 105 */     Entity entity = (Entity)cls.getAnnotation(Entity.class);
/* 106 */     if (entity != null) {
/*     */       
/* 108 */       if (entity.name().equals("")) {
/* 109 */         this.descriptor.setName(cls.getSimpleName());
/*     */       } else {
/*     */         
/* 112 */         this.descriptor.setName(entity.name());
/*     */       } 
/* 114 */     } else if (isXmlElement(cls)) {
/* 115 */       this.descriptor.setName(cls.getSimpleName());
/* 116 */       this.descriptor.setEntityType(BeanDescriptor.EntityType.XMLELEMENT);
/*     */     } 
/*     */     
/* 119 */     Embeddable embeddable = (Embeddable)cls.getAnnotation(Embeddable.class);
/* 120 */     if (embeddable != null) {
/* 121 */       this.descriptor.setEntityType(BeanDescriptor.EntityType.EMBEDDED);
/* 122 */       this.descriptor.setName("Embeddable:" + cls.getSimpleName());
/*     */     } 
/*     */     
/* 125 */     UniqueConstraint uc = (UniqueConstraint)cls.getAnnotation(UniqueConstraint.class);
/* 126 */     if (uc != null) {
/* 127 */       this.descriptor.addCompoundUniqueConstraint(new CompoundUniqueContraint(uc.columnNames()));
/*     */     }
/*     */     
/* 130 */     Table table = (Table)cls.getAnnotation(Table.class);
/* 131 */     if (table != null) {
/* 132 */       UniqueConstraint[] uniqueConstraints = table.uniqueConstraints();
/* 133 */       if (uniqueConstraints != null) {
/* 134 */         for (UniqueConstraint c : uniqueConstraints) {
/* 135 */           this.descriptor.addCompoundUniqueConstraint(new CompoundUniqueContraint(c.columnNames()));
/*     */         }
/*     */       }
/*     */     } 
/*     */     
/* 140 */     UpdateMode updateMode = (UpdateMode)cls.getAnnotation(UpdateMode.class);
/* 141 */     if (updateMode != null) {
/* 142 */       this.descriptor.setUpdateChangesOnly(updateMode.updateChangesOnly());
/*     */     }
/*     */     
/* 145 */     NamedQueries namedQueries = (NamedQueries)cls.getAnnotation(NamedQueries.class);
/* 146 */     if (namedQueries != null) {
/* 147 */       readNamedQueries(namedQueries);
/*     */     }
/* 149 */     NamedQuery namedQuery = (NamedQuery)cls.getAnnotation(NamedQuery.class);
/* 150 */     if (namedQuery != null) {
/* 151 */       readNamedQuery(namedQuery);
/*     */     }
/*     */     
/* 154 */     NamedUpdates namedUpdates = (NamedUpdates)cls.getAnnotation(NamedUpdates.class);
/* 155 */     if (namedUpdates != null) {
/* 156 */       readNamedUpdates(namedUpdates);
/*     */     }
/*     */     
/* 159 */     NamedUpdate namedUpdate = (NamedUpdate)cls.getAnnotation(NamedUpdate.class);
/* 160 */     if (namedUpdate != null) {
/* 161 */       readNamedUpdate(namedUpdate);
/*     */     }
/*     */     
/* 164 */     CacheStrategy cacheStrategy = (CacheStrategy)cls.getAnnotation(CacheStrategy.class);
/* 165 */     if (cacheStrategy != null) {
/* 166 */       readCacheStrategy(cacheStrategy);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private void readCacheStrategy(CacheStrategy cacheStrategy) {
/* 172 */     boolean useCache = cacheStrategy.useBeanCache();
/* 173 */     boolean readOnly = cacheStrategy.readOnly();
/* 174 */     String warmingQuery = cacheStrategy.warmingQuery();
/* 175 */     ReferenceOptions opt = new ReferenceOptions(useCache, readOnly, warmingQuery);
/*     */     
/* 177 */     this.descriptor.setReferenceOptions(opt);
/*     */     
/* 179 */     if (!Query.UseIndex.DEFAULT.equals(cacheStrategy.useIndex()))
/*     */     {
/* 181 */       this.descriptor.setUseIndex(cacheStrategy.useIndex());
/*     */     }
/*     */   }
/*     */   
/*     */   private void readNamedQueries(NamedQueries namedQueries) {
/* 186 */     NamedQuery[] queries = namedQueries.value();
/* 187 */     for (int i = 0; i < queries.length; i++) {
/* 188 */       readNamedQuery(queries[i]);
/*     */     }
/*     */   }
/*     */   
/*     */   private void readNamedQuery(NamedQuery namedQuery) {
/* 193 */     DeployNamedQuery q = new DeployNamedQuery(namedQuery);
/* 194 */     this.descriptor.add(q);
/*     */   }
/*     */   
/*     */   private void readNamedUpdates(NamedUpdates updates) {
/* 198 */     NamedUpdate[] updateArray = updates.value();
/* 199 */     for (int i = 0; i < updateArray.length; i++) {
/* 200 */       readNamedUpdate(updateArray[i]);
/*     */     }
/*     */   }
/*     */   
/*     */   private void readNamedUpdate(NamedUpdate update) {
/* 205 */     DeployNamedUpdate upd = new DeployNamedUpdate(update);
/* 206 */     this.descriptor.add(upd);
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\deploy\parse\AnnotationClass.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */