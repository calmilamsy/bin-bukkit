/*     */ package com.avaje.ebeaninternal.server.el;
/*     */ 
/*     */ import com.avaje.ebean.bean.EntityBean;
/*     */ import com.avaje.ebean.text.StringFormatter;
/*     */ import com.avaje.ebean.text.StringParser;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanProperty;
/*     */ import com.avaje.ebeaninternal.server.lib.util.StringHelper;
/*     */ import com.avaje.ebeaninternal.server.query.SplitName;
/*     */ import com.avaje.ebeaninternal.server.type.ScalarType;
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
/*     */ public class ElPropertyChain
/*     */   implements ElPropertyValue
/*     */ {
/*     */   private final String prefix;
/*     */   private final String placeHolder;
/*     */   private final String placeHolderEncrypted;
/*     */   private final String name;
/*     */   private final String expression;
/*     */   private final boolean containsMany;
/*     */   private final ElPropertyValue[] chain;
/*     */   private final boolean assocId;
/*     */   private final int last;
/*     */   private final BeanProperty lastBeanProperty;
/*     */   private final ScalarType<?> scalarType;
/*     */   private final ElPropertyValue lastElPropertyValue;
/*     */   
/*     */   public ElPropertyChain(boolean containsMany, boolean embedded, String expression, ElPropertyValue[] chain) {
/*  66 */     this.containsMany = containsMany;
/*  67 */     this.chain = chain;
/*  68 */     this.expression = expression;
/*  69 */     int dotPos = expression.lastIndexOf('.');
/*  70 */     if (dotPos > -1) {
/*  71 */       this.name = expression.substring(dotPos + 1);
/*  72 */       if (embedded) {
/*  73 */         int embPos = expression.lastIndexOf('.', dotPos - 1);
/*  74 */         this.prefix = (embPos == -1) ? null : expression.substring(0, embPos);
/*     */       } else {
/*     */         
/*  77 */         this.prefix = expression.substring(0, dotPos);
/*     */       } 
/*     */     } else {
/*  80 */       this.prefix = null;
/*  81 */       this.name = expression;
/*     */     } 
/*     */     
/*  84 */     this.assocId = chain[chain.length - 1].isAssocId();
/*     */     
/*  86 */     this.last = chain.length - 1;
/*  87 */     this.lastBeanProperty = chain[chain.length - 1].getBeanProperty();
/*  88 */     if (this.lastBeanProperty != null) {
/*  89 */       this.scalarType = this.lastBeanProperty.getScalarType();
/*     */     } else {
/*     */       
/*  92 */       this.scalarType = null;
/*     */     } 
/*  94 */     this.lastElPropertyValue = chain[chain.length - 1];
/*  95 */     this.placeHolder = getElPlaceHolder(this.prefix, this.lastElPropertyValue, false);
/*  96 */     this.placeHolderEncrypted = getElPlaceHolder(this.prefix, this.lastElPropertyValue, true);
/*     */   }
/*     */   
/*     */   private String getElPlaceHolder(String prefix, ElPropertyValue lastElPropertyValue, boolean encrypted) {
/* 100 */     if (prefix == null) {
/* 101 */       return lastElPropertyValue.getElPlaceholder(encrypted);
/*     */     }
/*     */     
/* 104 */     String el = lastElPropertyValue.getElPlaceholder(encrypted);
/*     */     
/* 106 */     if (!el.contains("${}"))
/*     */     {
/* 108 */       return StringHelper.replaceString(el, "${", "${" + prefix + ".");
/*     */     }
/* 110 */     return StringHelper.replaceString(el, "${}", "${" + prefix + "}");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 118 */   public boolean isDeployOnly() { return false; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean containsManySince(String sinceProperty) {
/* 126 */     if (sinceProperty == null) {
/* 127 */       return this.containsMany;
/*     */     }
/* 129 */     if (!this.expression.startsWith(sinceProperty)) {
/* 130 */       return this.containsMany;
/*     */     }
/*     */     
/* 133 */     int i = 1 + SplitName.count('.', sinceProperty);
/*     */     
/* 135 */     for (; i < this.chain.length; i++) {
/* 136 */       if (this.chain[i].getBeanProperty().containsMany()) {
/* 137 */         return true;
/*     */       }
/*     */     } 
/*     */     
/* 141 */     return false;
/*     */   }
/*     */ 
/*     */   
/* 145 */   public boolean containsMany() { return this.containsMany; }
/*     */ 
/*     */ 
/*     */   
/* 149 */   public String getElPrefix() { return this.prefix; }
/*     */ 
/*     */ 
/*     */   
/* 153 */   public String getName() { return this.name; }
/*     */ 
/*     */ 
/*     */   
/* 157 */   public String getElName() { return this.expression; }
/*     */ 
/*     */ 
/*     */   
/* 161 */   public String getElPlaceholder(boolean encrypted) { return encrypted ? this.placeHolderEncrypted : this.placeHolder; }
/*     */ 
/*     */ 
/*     */   
/* 165 */   public boolean isDbEncrypted() { return this.lastElPropertyValue.isDbEncrypted(); }
/*     */ 
/*     */ 
/*     */   
/* 169 */   public boolean isLocalEncrypted() { return this.lastElPropertyValue.isLocalEncrypted(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 175 */   public Object[] getAssocOneIdValues(Object bean) { return this.lastElPropertyValue.getAssocOneIdValues(bean); }
/*     */ 
/*     */ 
/*     */   
/* 179 */   public String getAssocOneIdExpr(String prefix, String operator) { return this.lastElPropertyValue.getAssocOneIdExpr(this.expression, operator); }
/*     */ 
/*     */ 
/*     */   
/* 183 */   public String getAssocIdInExpr(String prefix) { return this.lastElPropertyValue.getAssocIdInExpr(prefix); }
/*     */ 
/*     */ 
/*     */   
/* 187 */   public String getAssocIdInValueExpr(int size) { return this.lastElPropertyValue.getAssocIdInValueExpr(size); }
/*     */ 
/*     */   
/*     */   public int getDeployOrder() {
/* 191 */     int i = this.lastBeanProperty.getDeployOrder();
/* 192 */     int max = this.chain.length - 1;
/* 193 */     for (int j = 0; j < max; j++) {
/* 194 */       int xtra = (max - j) * 1000 * this.chain[j].getDeployOrder();
/* 195 */       i += xtra;
/*     */     } 
/* 197 */     return i;
/*     */   }
/*     */ 
/*     */   
/* 201 */   public boolean isAssocId() { return this.assocId; }
/*     */ 
/*     */   
/*     */   public boolean isAssocProperty() {
/* 205 */     for (int i = 0; i < this.chain.length; i++) {
/* 206 */       if (this.chain[i].isAssocProperty()) {
/* 207 */         return true;
/*     */       }
/*     */     } 
/* 210 */     return false;
/*     */   }
/*     */ 
/*     */   
/* 214 */   public String getDbColumn() { return this.lastElPropertyValue.getDbColumn(); }
/*     */ 
/*     */ 
/*     */   
/* 218 */   public BeanProperty getBeanProperty() { return this.lastBeanProperty; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 223 */   public boolean isDateTimeCapable() { return (this.scalarType != null && this.scalarType.isDateTimeCapable()); }
/*     */ 
/*     */ 
/*     */   
/* 227 */   public int getJdbcType() { return (this.scalarType == null) ? 0 : this.scalarType.getJdbcType(); }
/*     */ 
/*     */ 
/*     */   
/* 231 */   public Object parseDateTime(long systemTimeMillis) { return this.scalarType.parseDateTime(systemTimeMillis); }
/*     */ 
/*     */ 
/*     */   
/* 235 */   public StringParser getStringParser() { return this.scalarType; }
/*     */ 
/*     */ 
/*     */   
/* 239 */   public StringFormatter getStringFormatter() { return this.scalarType; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 244 */   public Object elConvertType(Object value) { return this.lastElPropertyValue.elConvertType(value); }
/*     */ 
/*     */ 
/*     */   
/*     */   public Object elGetValue(Object bean) {
/* 249 */     for (int i = 0; i < this.chain.length; i++) {
/* 250 */       bean = this.chain[i].elGetValue(bean);
/* 251 */       if (bean == null) {
/* 252 */         return null;
/*     */       }
/*     */     } 
/*     */     
/* 256 */     return bean;
/*     */   }
/*     */ 
/*     */   
/*     */   public Object elGetReference(Object bean) {
/* 261 */     Object prevBean = bean;
/* 262 */     for (int i = 0; i < this.last; i++)
/*     */     {
/* 264 */       prevBean = this.chain[i].elGetReference(prevBean);
/*     */     }
/*     */     
/* 267 */     return this.chain[this.last].elGetValue(prevBean);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void elSetLoaded(Object bean) {
/* 275 */     for (int i = 0; i < this.last; i++) {
/* 276 */       bean = this.chain[i].elGetValue(bean);
/* 277 */       if (bean == null) {
/*     */         break;
/*     */       }
/*     */     } 
/* 281 */     if (bean != null) {
/* 282 */       ((EntityBean)bean)._ebean_getIntercept().setLoaded();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void elSetReference(Object bean) {
/* 288 */     for (int i = 0; i < this.last; i++) {
/* 289 */       bean = this.chain[i].elGetValue(bean);
/* 290 */       if (bean == null) {
/*     */         break;
/*     */       }
/*     */     } 
/* 294 */     if (bean != null) {
/* 295 */       ((EntityBean)bean)._ebean_getIntercept().setReference();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void elSetValue(Object bean, Object value, boolean populate, boolean reference) {
/* 301 */     Object prevBean = bean;
/* 302 */     if (populate) {
/* 303 */       for (int i = 0; i < this.last; i++)
/*     */       {
/* 305 */         prevBean = this.chain[i].elGetReference(prevBean);
/*     */       }
/*     */     } else {
/* 308 */       for (int i = 0; i < this.last; i++) {
/*     */         
/* 310 */         prevBean = this.chain[i].elGetValue(prevBean);
/* 311 */         if (prevBean == null) {
/*     */           break;
/*     */         }
/*     */       } 
/*     */     } 
/* 316 */     if (prevBean != null)
/* 317 */       if (this.lastBeanProperty != null) {
/*     */         
/* 319 */         this.lastBeanProperty.setValueIntercept(prevBean, value);
/* 320 */         if (reference) {
/* 321 */           ((EntityBean)prevBean)._ebean_getIntercept().setReference();
/*     */         }
/*     */       } else {
/*     */         
/* 325 */         this.lastElPropertyValue.elSetValue(prevBean, value, populate, reference);
/*     */       }  
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\el\ElPropertyChain.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */