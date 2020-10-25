/*     */ package com.avaje.ebeaninternal.server.core;
/*     */ 
/*     */ import com.avaje.ebean.ValuePair;
/*     */ import com.avaje.ebean.bean.EntityBean;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanProperty;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanPropertyAssocOne;
/*     */ import com.avaje.ebeaninternal.util.ValueUtil;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.Map;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DiffHelp
/*     */ {
/*     */   public Map<String, ValuePair> diff(Object a, Object b, BeanDescriptor<?> desc) {
/*  54 */     boolean oldValues = false;
/*  55 */     if (b == null)
/*     */     {
/*  57 */       if (a instanceof EntityBean) {
/*  58 */         EntityBean eb = (EntityBean)a;
/*  59 */         b = eb._ebean_getIntercept().getOldValues();
/*  60 */         oldValues = true;
/*     */       } 
/*     */     }
/*     */     
/*  64 */     Map<String, ValuePair> map = new LinkedHashMap<String, ValuePair>();
/*     */     
/*  66 */     if (b == null) {
/*  67 */       return map;
/*     */     }
/*     */ 
/*     */     
/*  71 */     BeanProperty[] base = desc.propertiesBaseScalar();
/*  72 */     for (int i = 0; i < base.length; i++) {
/*     */       
/*  74 */       Object aval = base[i].getValue(a);
/*  75 */       Object bval = base[i].getValue(b);
/*  76 */       if (!ValueUtil.areEqual(aval, bval)) {
/*  77 */         map.put(base[i].getName(), new ValuePair(aval, bval));
/*     */       }
/*     */     } 
/*     */     
/*  81 */     diffAssocOne(a, b, desc, map);
/*  82 */     diffEmbedded(a, b, desc, map, oldValues);
/*     */     
/*  84 */     return map;
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
/*     */   private void diffEmbedded(Object a, Object b, BeanDescriptor<?> desc, Map<String, ValuePair> map, boolean oldValues) {
/*  97 */     BeanPropertyAssocOne[] emb = desc.propertiesEmbedded();
/*     */     
/*  99 */     for (int i = 0; i < emb.length; i++) {
/* 100 */       Object aval = emb[i].getValue(a);
/* 101 */       Object bval = emb[i].getValue(b);
/* 102 */       if (oldValues) {
/* 103 */         bval = ((EntityBean)bval)._ebean_getIntercept().getOldValues();
/* 104 */         if (bval == null) {
/*     */           continue;
/*     */         }
/*     */       } 
/*     */       
/* 109 */       if (!isBothNull(aval, bval)) {
/* 110 */         if (isDiffNull(aval, bval)) {
/*     */           
/* 112 */           map.put(emb[i].getName(), new ValuePair(aval, bval));
/*     */         
/*     */         }
/*     */         else {
/*     */           
/* 117 */           BeanProperty[] props = emb[i].getProperties();
/* 118 */           for (int j = 0; j < props.length; j++) {
/* 119 */             Object aEmbPropVal = props[j].getValue(aval);
/* 120 */             Object bEmbPropVal = props[j].getValue(bval);
/* 121 */             if (!ValueUtil.areEqual(aEmbPropVal, bEmbPropVal))
/*     */             {
/*     */ 
/*     */               
/* 125 */               map.put(emb[i].getName(), new ValuePair(aval, bval));
/*     */             }
/*     */           } 
/*     */         } 
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void diffAssocOne(Object a, Object b, BeanDescriptor<?> desc, Map<String, ValuePair> map) {
/* 139 */     BeanPropertyAssocOne[] ones = desc.propertiesOne();
/*     */     
/* 141 */     for (int i = 0; i < ones.length; i++) {
/* 142 */       Object aval = ones[i].getValue(a);
/* 143 */       Object bval = ones[i].getValue(b);
/*     */       
/* 145 */       if (!isBothNull(aval, bval)) {
/* 146 */         if (isDiffNull(aval, bval)) {
/*     */           
/* 148 */           map.put(ones[i].getName(), new ValuePair(aval, bval));
/*     */         
/*     */         }
/*     */         else {
/*     */           
/* 153 */           BeanDescriptor<?> oneDesc = ones[i].getTargetDescriptor();
/* 154 */           Object aOneId = oneDesc.getId(aval);
/* 155 */           Object bOneId = oneDesc.getId(bval);
/*     */           
/* 157 */           if (!ValueUtil.areEqual(aOneId, bOneId))
/*     */           {
/* 159 */             map.put(ones[i].getName(), new ValuePair(aval, bval));
/*     */           }
/*     */         } 
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/* 167 */   private boolean isBothNull(Object aval, Object bval) { return (aval == null && bval == null); }
/*     */ 
/*     */   
/*     */   private boolean isDiffNull(Object aval, Object bval) {
/* 171 */     if (aval == null) {
/* 172 */       return (bval != null);
/*     */     }
/* 174 */     return (bval == null);
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\core\DiffHelp.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */