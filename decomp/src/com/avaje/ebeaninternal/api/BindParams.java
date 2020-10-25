/*     */ package com.avaje.ebeaninternal.api;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
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
/*     */ public class BindParams
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 4541081933302086285L;
/*  42 */   private ArrayList<Param> positionedParameters = new ArrayList();
/*     */   
/*  44 */   private HashMap<String, Param> namedParameters = new HashMap();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  49 */   private int queryPlanHash = 1;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String preparedSql;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BindParams copy() {
/*  62 */     BindParams copy = new BindParams();
/*  63 */     for (Param p : this.positionedParameters) {
/*  64 */       copy.positionedParameters.add(p.copy());
/*     */     }
/*  66 */     Iterator<Map.Entry<String, Param>> it = this.namedParameters.entrySet().iterator();
/*  67 */     while (it.hasNext()) {
/*  68 */       Map.Entry<String, Param> entry = (Map.Entry)it.next();
/*  69 */       copy.namedParameters.put(entry.getKey(), ((Param)entry.getValue()).copy());
/*     */     } 
/*  71 */     return copy;
/*     */   }
/*     */   
/*     */   public int queryBindHash() {
/*  75 */     int hc = this.namedParameters.hashCode();
/*  76 */     for (int i = 0; i < this.positionedParameters.size(); i++) {
/*  77 */       hc = hc * 31 + ((Param)this.positionedParameters.get(i)).hashCode();
/*     */     }
/*  79 */     return hc;
/*     */   }
/*     */   
/*     */   public int hashCode() {
/*  83 */     hc = getClass().hashCode();
/*  84 */     hc = hc * 31 + this.namedParameters.hashCode();
/*  85 */     for (int i = 0; i < this.positionedParameters.size(); i++) {
/*  86 */       hc = hc * 31 + ((Param)this.positionedParameters.get(i)).hashCode();
/*     */     }
/*  88 */     return hc * 31 + ((this.preparedSql == null) ? 0 : this.preparedSql.hashCode());
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/*  93 */     if (o == null) {
/*  94 */       return false;
/*     */     }
/*  96 */     if (o == this) {
/*  97 */       return true;
/*     */     }
/*  99 */     if (o instanceof BindParams) {
/* 100 */       return (hashCode() == o.hashCode());
/*     */     }
/* 102 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 109 */   public boolean isEmpty() { return (this.positionedParameters.isEmpty() && this.namedParameters.isEmpty()); }
/*     */ 
/*     */ 
/*     */   
/* 113 */   public int size() { return this.positionedParameters.size(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 122 */   public boolean requiresNamedParamsPrepare() { return (!this.namedParameters.isEmpty() && this.positionedParameters.isEmpty()); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setNullParameter(int position, int jdbcType) {
/* 129 */     Param p = getParam(position);
/* 130 */     p.setInNullType(jdbcType);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setParameter(int position, Object value, int outType) {
/* 138 */     addToQueryPlanHash(String.valueOf(position), value);
/*     */     
/* 140 */     Param p = getParam(position);
/* 141 */     p.setInValue(value);
/* 142 */     p.setOutType(outType);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setParameter(int position, Object value) {
/* 151 */     addToQueryPlanHash(String.valueOf(position), value);
/*     */     
/* 153 */     Param p = getParam(position);
/* 154 */     p.setInValue(value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void registerOut(int position, int outType) {
/* 161 */     Param p = getParam(position);
/* 162 */     p.setOutType(outType);
/*     */   }
/*     */   
/*     */   private Param getParam(String name) {
/* 166 */     Param p = (Param)this.namedParameters.get(name);
/* 167 */     if (p == null) {
/* 168 */       p = new Param();
/* 169 */       this.namedParameters.put(name, p);
/*     */     } 
/* 171 */     return p;
/*     */   }
/*     */   
/*     */   private Param getParam(int position) {
/* 175 */     int more = position - this.positionedParameters.size();
/* 176 */     if (more > 0) {
/* 177 */       for (int i = 0; i < more; i++) {
/* 178 */         this.positionedParameters.add(new Param());
/*     */       }
/*     */     }
/* 181 */     return (Param)this.positionedParameters.get(position - 1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setParameter(String name, Object value, int outType) {
/* 189 */     addToQueryPlanHash(name, value);
/*     */     
/* 191 */     Param p = getParam(name);
/* 192 */     p.setInValue(value);
/* 193 */     p.setOutType(outType);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setNullParameter(String name, int jdbcType) {
/* 200 */     Param p = getParam(name);
/* 201 */     p.setInNullType(jdbcType);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Param setParameter(String name, Object value) {
/* 209 */     addToQueryPlanHash(name, value);
/*     */     
/* 211 */     Param p = getParam(name);
/* 212 */     p.setInValue(value);
/* 213 */     return p;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void addToQueryPlanHash(String name, Object value) {
/* 220 */     if (value != null && 
/* 221 */       value instanceof Collection) {
/* 222 */       this.queryPlanHash = this.queryPlanHash * 31 + name.hashCode();
/* 223 */       this.queryPlanHash = this.queryPlanHash * 31 + ((Collection)value).size();
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
/*     */ 
/*     */   
/* 237 */   public int getQueryPlanHash() { return this.queryPlanHash; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Param setEncryptionKey(String name, Object value) {
/* 247 */     Param p = getParam(name);
/* 248 */     p.setEncryptionKey(value);
/* 249 */     return p;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void registerOut(String name, int outType) {
/* 256 */     Param p = getParam(name);
/* 257 */     p.setOutType(outType);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 265 */   public Param getParameter(int position) { return getParam(position); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 272 */   public Param getParameter(String name) { return getParam(name); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 279 */   public List<Param> positionedParameters() { return this.positionedParameters; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 286 */   public void setPreparedSql(String preparedSql) { this.preparedSql = preparedSql; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 294 */   public String getPreparedSql() { return this.preparedSql; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final class OrderedList
/*     */   {
/*     */     final List<BindParams.Param> paramList;
/*     */ 
/*     */ 
/*     */     
/*     */     final StringBuilder preparedSql;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 311 */     public OrderedList() { this(new ArrayList()); }
/*     */ 
/*     */     
/*     */     public OrderedList(List<BindParams.Param> paramList) {
/* 315 */       this.paramList = paramList;
/* 316 */       this.preparedSql = new StringBuilder();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 323 */     public void add(BindParams.Param param) { this.paramList.add(param); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 330 */     public int size() { return this.paramList.size(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 337 */     public List<BindParams.Param> list() { return this.paramList; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 344 */     public void appendSql(String parsedSql) { this.preparedSql.append(parsedSql); }
/*     */ 
/*     */ 
/*     */     
/* 348 */     public String getPreparedSql() { return this.preparedSql.toString(); }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final class Param
/*     */   {
/*     */     private boolean encryptionKey;
/*     */ 
/*     */ 
/*     */     
/*     */     private boolean isInParam;
/*     */ 
/*     */     
/*     */     private boolean isOutParam;
/*     */ 
/*     */     
/*     */     private int type;
/*     */ 
/*     */     
/*     */     private Object inValue;
/*     */ 
/*     */     
/*     */     private Object outValue;
/*     */ 
/*     */     
/*     */     private int textLocation;
/*     */ 
/*     */ 
/*     */     
/*     */     public Param copy() {
/* 380 */       Param copy = new Param();
/* 381 */       copy.isInParam = this.isInParam;
/* 382 */       copy.isOutParam = this.isOutParam;
/* 383 */       copy.type = this.type;
/* 384 */       copy.inValue = this.inValue;
/* 385 */       copy.outValue = this.outValue;
/* 386 */       return copy;
/*     */     }
/*     */     
/*     */     public int hashCode() {
/* 390 */       hc = getClass().hashCode();
/* 391 */       hc = hc * 31 + (this.isInParam ? 0 : 1);
/* 392 */       hc = hc * 31 + (this.isOutParam ? 0 : 1);
/* 393 */       hc = hc * 31 + this.type;
/* 394 */       return hc * 31 + ((this.inValue == null) ? 0 : this.inValue.hashCode());
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object o) {
/* 399 */       if (o == null) {
/* 400 */         return false;
/*     */       }
/* 402 */       if (o == this) {
/* 403 */         return true;
/*     */       }
/* 405 */       if (o instanceof Param) {
/* 406 */         return (hashCode() == o.hashCode());
/*     */       }
/* 408 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 416 */     public boolean isInParam() { return this.isInParam; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 424 */     public boolean isOutParam() { return this.isOutParam; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 432 */     public int getType() { return this.type; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void setOutType(int type) {
/* 439 */       this.type = type;
/* 440 */       this.isOutParam = true;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void setInValue(Object in) {
/* 447 */       this.inValue = in;
/* 448 */       this.isInParam = true;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void setEncryptionKey(Object in) {
/* 455 */       this.inValue = in;
/* 456 */       this.isInParam = true;
/* 457 */       this.encryptionKey = true;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void setInNullType(int type) {
/* 465 */       this.type = type;
/* 466 */       this.inValue = null;
/* 467 */       this.isInParam = true;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 475 */     public Object getOutValue() { return this.outValue; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 483 */     public Object getInValue() { return this.inValue; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 491 */     public void setOutValue(Object out) { this.outValue = out; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 498 */     public int getTextLocation() { return this.textLocation; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 506 */     public void setTextLocation(int textLocation) { this.textLocation = textLocation; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 513 */     public boolean isEncryptionKey() { return this.encryptionKey; }
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\api\BindParams.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */