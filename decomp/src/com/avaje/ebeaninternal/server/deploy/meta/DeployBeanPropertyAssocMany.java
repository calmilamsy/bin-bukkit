/*     */ package com.avaje.ebeaninternal.server.deploy.meta;
/*     */ 
/*     */ import com.avaje.ebean.bean.BeanCollection;
/*     */ import com.avaje.ebeaninternal.server.deploy.ManyType;
/*     */ import com.avaje.ebeaninternal.server.deploy.TableJoin;
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
/*     */ public class DeployBeanPropertyAssocMany<T>
/*     */   extends DeployBeanPropertyAssoc<T>
/*     */ {
/*  31 */   BeanCollection.ModifyListenMode modifyListenMode = BeanCollection.ModifyListenMode.NONE;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   boolean manyToMany;
/*     */ 
/*     */ 
/*     */   
/*     */   boolean unidirectional;
/*     */ 
/*     */ 
/*     */   
/*     */   DeployTableJoin intersectionJoin;
/*     */ 
/*     */ 
/*     */   
/*     */   DeployTableJoin inverseJoin;
/*     */ 
/*     */ 
/*     */   
/*     */   String fetchOrderBy;
/*     */ 
/*     */ 
/*     */   
/*     */   String mapKey;
/*     */ 
/*     */ 
/*     */   
/*     */   ManyType manyType;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DeployBeanPropertyAssocMany(DeployBeanDescriptor<?> desc, Class<T> targetType, ManyType manyType) {
/*  66 */     super(desc, targetType);
/*  67 */     this.manyType = manyType;
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
/*     */   
/*  79 */   public void setTargetType(Class<?> cls) { this.targetType = cls; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  87 */   public ManyType getManyType() { return this.manyType; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  94 */   public boolean isManyToMany() { return this.manyToMany; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 101 */   public void setManyToMany(boolean isManyToMany) { this.manyToMany = isManyToMany; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 108 */   public BeanCollection.ModifyListenMode getModifyListenMode() { return this.modifyListenMode; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 115 */   public void setModifyListenMode(BeanCollection.ModifyListenMode modifyListenMode) { this.modifyListenMode = modifyListenMode; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 122 */   public boolean isUnidirectional() { return this.unidirectional; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 129 */   public void setUnidirectional(boolean unidirectional) { this.unidirectional = unidirectional; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TableJoin createIntersectionTableJoin() {
/* 136 */     if (this.intersectionJoin != null) {
/* 137 */       return new TableJoin(this.intersectionJoin, null);
/*     */     }
/* 139 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TableJoin createInverseTableJoin() {
/* 147 */     if (this.inverseJoin != null) {
/* 148 */       return new TableJoin(this.inverseJoin, null);
/*     */     }
/* 150 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 158 */   public DeployTableJoin getIntersectionJoin() { return this.intersectionJoin; }
/*     */ 
/*     */ 
/*     */   
/* 162 */   public DeployTableJoin getInverseJoin() { return this.inverseJoin; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 169 */   public void setIntersectionJoin(DeployTableJoin intersectionJoin) { this.intersectionJoin = intersectionJoin; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 176 */   public void setInverseJoin(DeployTableJoin inverseJoin) { this.inverseJoin = inverseJoin; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 184 */   public String getFetchOrderBy() { return this.fetchOrderBy; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 191 */   public String getMapKey() { return this.mapKey; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMapKey(String mapKey) {
/* 198 */     if (mapKey != null && mapKey.length() > 0) {
/* 199 */       this.mapKey = mapKey;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setFetchOrderBy(String orderBy) {
/* 208 */     if (orderBy != null && orderBy.length() > 0)
/* 209 */       this.fetchOrderBy = orderBy; 
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\deploy\meta\DeployBeanPropertyAssocMany.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */