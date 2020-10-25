/*     */ package com.avaje.ebeaninternal.server.deploy;
/*     */ 
/*     */ import com.avaje.ebean.bean.EntityBean;
/*     */ import com.avaje.ebean.bean.EntityBeanIntercept;
/*     */ import java.util.HashSet;
/*     */ import java.util.Set;
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
/*     */ public class BeanRefreshFromCacheHelp
/*     */ {
/*     */   private final BeanDescriptor<?> desc;
/*     */   private final EntityBeanIntercept ebi;
/*     */   private final EntityBean bean;
/*     */   private final Object cacheBean;
/*     */   private final Object originalOldValues;
/*     */   private final boolean isLazyLoad;
/*     */   private final boolean readOnly;
/*     */   private final boolean sharedInstance;
/*     */   private final int parentState;
/*     */   private final Set<String> excludes;
/*     */   private final Set<String> cacheBeanLoadedProps;
/*     */   private final Set<String> loadedProps;
/*     */   private final boolean setOriginalOldValues;
/*     */   
/*     */   public BeanRefreshFromCacheHelp(BeanDescriptor<?> desc, EntityBeanIntercept ebi, Object cacheBean, boolean isLazyLoad) {
/*  57 */     this.desc = desc;
/*  58 */     this.ebi = ebi;
/*  59 */     this.bean = ebi.getOwner();
/*  60 */     this.cacheBean = cacheBean;
/*  61 */     this.cacheBeanLoadedProps = ((EntityBean)cacheBean)._ebean_getIntercept().getLoadedProps();
/*     */     
/*  63 */     if (this.cacheBeanLoadedProps != null) {
/*  64 */       this.loadedProps = new HashSet();
/*     */     } else {
/*  66 */       this.loadedProps = null;
/*     */     } 
/*     */     
/*  69 */     this.isLazyLoad = isLazyLoad;
/*  70 */     this.readOnly = ebi.isReadOnly();
/*  71 */     this.sharedInstance = ebi.isSharedInstance();
/*  72 */     if (this.sharedInstance) {
/*  73 */       this.parentState = 1;
/*  74 */     } else if (this.readOnly) {
/*  75 */       this.parentState = 2;
/*     */     } else {
/*  77 */       this.parentState = 0;
/*     */     } 
/*     */     
/*  80 */     this.excludes = isLazyLoad ? ebi.getLoadedProps() : null;
/*  81 */     if (this.excludes != null) {
/*     */ 
/*     */ 
/*     */       
/*  85 */       this.originalOldValues = ebi.getOldValues();
/*     */     } else {
/*  87 */       this.originalOldValues = null;
/*     */     } 
/*  89 */     this.setOriginalOldValues = (this.originalOldValues != null);
/*     */   }
/*     */   
/*     */   private boolean includeProperty(BeanProperty prop) {
/*  93 */     String name = prop.getName();
/*  94 */     if (this.excludes != null && this.excludes.contains(name))
/*     */     {
/*  96 */       return false;
/*     */     }
/*  98 */     if (this.cacheBeanLoadedProps != null && !this.cacheBeanLoadedProps.contains(name)) {
/*  99 */       return false;
/*     */     }
/* 101 */     if (this.loadedProps != null) {
/* 102 */       this.loadedProps.add(name);
/*     */     }
/* 104 */     return true;
/*     */   }
/*     */   
/*     */   private void propagateParentState(Object bean) {
/* 108 */     if (bean != null && this.parentState > 0) {
/* 109 */       ((EntityBean)bean)._ebean_getIntercept().setState(this.parentState);
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
/*     */   public void refresh() {
/* 121 */     this.ebi.setIntercepting(false);
/*     */     
/* 123 */     BeanProperty[] props = this.desc.propertiesBaseScalar();
/* 124 */     for (i = 0; i < props.length; i++) {
/* 125 */       BeanProperty prop = props[i];
/* 126 */       if (includeProperty(prop)) {
/* 127 */         Object val = prop.getValue(this.cacheBean);
/* 128 */         if (this.isLazyLoad) {
/* 129 */           prop.setValue(this.bean, val);
/*     */         } else {
/* 131 */           prop.setValueIntercept(this.bean, val);
/*     */         } 
/* 133 */         if (this.setOriginalOldValues)
/*     */         {
/* 135 */           prop.setValue(this.originalOldValues, val);
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 140 */     BeanPropertyAssocOne[] ones = this.desc.propertiesOne();
/* 141 */     for (i = 0; i < ones.length; i++) {
/* 142 */       BeanPropertyAssocOne<?> prop = ones[i];
/* 143 */       if (includeProperty(prop)) {
/*     */         
/* 145 */         Object val = prop.getValue(this.cacheBean);
/* 146 */         if (!this.sharedInstance)
/*     */         {
/* 148 */           val = prop.getTargetDescriptor().createCopyForUpdate(val, false);
/*     */         }
/* 150 */         if (this.isLazyLoad) {
/* 151 */           prop.setValue(this.bean, val);
/*     */         } else {
/* 153 */           prop.setValueIntercept(this.bean, val);
/*     */         } 
/* 155 */         if (this.setOriginalOldValues)
/*     */         {
/* 157 */           prop.setValue(this.originalOldValues, val);
/*     */         }
/* 159 */         propagateParentState(val);
/*     */       } 
/*     */     } 
/*     */     
/* 163 */     refreshEmbedded();
/*     */ 
/*     */     
/* 166 */     BeanPropertyAssocMany[] manys = this.desc.propertiesMany();
/* 167 */     for (int i = 0; i < manys.length; i++) {
/* 168 */       BeanPropertyAssocMany<?> prop = manys[i];
/* 169 */       if (includeProperty(prop))
/*     */       {
/* 171 */         prop.createReference(this.bean);
/*     */       }
/*     */     } 
/*     */     
/* 175 */     this.ebi.setLoadedProps(this.loadedProps);
/*     */ 
/*     */     
/* 178 */     this.ebi.setLoaded();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void refreshEmbedded() {
/* 186 */     BeanPropertyAssocOne[] embeds = this.desc.propertiesEmbedded();
/* 187 */     for (int i = 0; i < embeds.length; i++) {
/* 188 */       BeanPropertyAssocOne<?> prop = embeds[i];
/* 189 */       if (includeProperty(prop)) {
/*     */         
/* 191 */         Object oEmb = prop.getValue(this.bean);
/*     */ 
/*     */         
/* 194 */         Object cacheEmb = prop.getValue(this.cacheBean);
/*     */         
/* 196 */         if (oEmb == null) {
/*     */ 
/*     */           
/* 199 */           if (cacheEmb == null) {
/* 200 */             prop.setValueIntercept(this.bean, null);
/*     */           } else {
/*     */             
/* 203 */             Object copyEmb = prop.getTargetDescriptor().createCopyForUpdate(cacheEmb, false);
/* 204 */             prop.setValueIntercept(this.bean, copyEmb);
/* 205 */             propagateParentState(copyEmb);
/*     */           }
/*     */         
/*     */         }
/*     */         else {
/*     */           
/* 211 */           if (oEmb instanceof EntityBean)
/*     */           {
/*     */             
/* 214 */             ((EntityBean)oEmb)._ebean_getIntercept().setIntercepting(false);
/*     */           }
/*     */           
/* 217 */           BeanProperty[] props = prop.getProperties();
/* 218 */           for (int j = 0; j < props.length; j++) {
/* 219 */             Object v = props[j].getValue(cacheEmb);
/* 220 */             props[j].setValueIntercept(oEmb, v);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\deploy\BeanRefreshFromCacheHelp.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */