/*     */ package com.avaje.ebeaninternal.server.query;
/*     */ 
/*     */ import com.avaje.ebean.SqlRow;
/*     */ import com.avaje.ebeaninternal.server.core.BasicTypeConverter;
/*     */ import java.math.BigDecimal;
/*     */ import java.sql.Date;
/*     */ import java.sql.Timestamp;
/*     */ import java.util.Collection;
/*     */ import java.util.Date;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.UUID;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DefaultSqlRow
/*     */   implements SqlRow
/*     */ {
/*     */   static final long serialVersionUID = -3120927797041336242L;
/*     */   private final String dbTrueValue;
/*     */   Map<String, Object> map;
/*     */   
/*     */   public DefaultSqlRow(Map<String, Object> map, String dbTrueValue) {
/*  69 */     this.map = map;
/*  70 */     this.dbTrueValue = dbTrueValue;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DefaultSqlRow(String dbTrueValue) {
/*  78 */     this.map = new LinkedHashMap();
/*  79 */     this.dbTrueValue = dbTrueValue;
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
/*     */   public DefaultSqlRow(int initialCapacity, float loadFactor, String dbTrueValue) {
/*  93 */     this.map = new LinkedHashMap(initialCapacity, loadFactor);
/*  94 */     this.dbTrueValue = dbTrueValue;
/*     */   }
/*     */ 
/*     */   
/*  98 */   public Iterator<String> keys() { return this.map.keySet().iterator(); }
/*     */ 
/*     */   
/*     */   public Object remove(Object name) {
/* 102 */     name = ((String)name).toLowerCase();
/* 103 */     return this.map.remove(name);
/*     */   }
/*     */   
/*     */   public Object get(Object name) {
/* 107 */     name = ((String)name).toLowerCase();
/* 108 */     return this.map.get(name);
/*     */   }
/*     */ 
/*     */   
/* 112 */   public Object put(String name, Object value) { return setInternal(name, value); }
/*     */ 
/*     */ 
/*     */   
/* 116 */   public Object set(String name, Object value) { return setInternal(name, value); }
/*     */ 
/*     */ 
/*     */   
/*     */   private Object setInternal(String name, Object newValue) {
/* 121 */     name = name.toLowerCase();
/*     */ 
/*     */     
/* 124 */     return this.map.put(name, newValue);
/*     */   }
/*     */   
/*     */   public UUID getUUID(String name) {
/* 128 */     Object val = get(name);
/* 129 */     return BasicTypeConverter.toUUID(val);
/*     */   }
/*     */   
/*     */   public Boolean getBoolean(String name) {
/* 133 */     Object val = get(name);
/* 134 */     return BasicTypeConverter.toBoolean(val, this.dbTrueValue);
/*     */   }
/*     */   
/*     */   public Integer getInteger(String name) {
/* 138 */     Object val = get(name);
/* 139 */     return BasicTypeConverter.toInteger(val);
/*     */   }
/*     */   
/*     */   public BigDecimal getBigDecimal(String name) {
/* 143 */     Object val = get(name);
/* 144 */     return BasicTypeConverter.toBigDecimal(val);
/*     */   }
/*     */   
/*     */   public Long getLong(String name) {
/* 148 */     Object val = get(name);
/* 149 */     return BasicTypeConverter.toLong(val);
/*     */   }
/*     */   
/*     */   public Double getDouble(String name) {
/* 153 */     Object val = get(name);
/* 154 */     return BasicTypeConverter.toDouble(val);
/*     */   }
/*     */   
/*     */   public Float getFloat(String name) {
/* 158 */     Object val = get(name);
/* 159 */     return BasicTypeConverter.toFloat(val);
/*     */   }
/*     */   
/*     */   public String getString(String name) {
/* 163 */     Object val = get(name);
/* 164 */     return BasicTypeConverter.toString(val);
/*     */   }
/*     */   
/*     */   public Date getUtilDate(String name) {
/* 168 */     Object val = get(name);
/* 169 */     return BasicTypeConverter.toUtilDate(val);
/*     */   }
/*     */   
/*     */   public Date getDate(String name) {
/* 173 */     Object val = get(name);
/* 174 */     return BasicTypeConverter.toDate(val);
/*     */   }
/*     */   
/*     */   public Timestamp getTimestamp(String name) {
/* 178 */     Object val = get(name);
/* 179 */     return BasicTypeConverter.toTimestamp(val);
/*     */   }
/*     */ 
/*     */   
/* 183 */   public String toString() { return this.map.toString(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 190 */   public void clear() { this.map.clear(); }
/*     */ 
/*     */   
/*     */   public boolean containsKey(Object key) {
/* 194 */     key = ((String)key).toLowerCase();
/* 195 */     return this.map.containsKey(key);
/*     */   }
/*     */ 
/*     */   
/* 199 */   public boolean containsValue(Object value) { return this.map.containsValue(value); }
/*     */ 
/*     */ 
/*     */   
/* 203 */   public Set<Map.Entry<String, Object>> entrySet() { return this.map.entrySet(); }
/*     */ 
/*     */ 
/*     */   
/* 207 */   public boolean isEmpty() { return this.map.isEmpty(); }
/*     */ 
/*     */ 
/*     */   
/* 211 */   public Set<String> keySet() { return this.map.keySet(); }
/*     */ 
/*     */ 
/*     */   
/* 215 */   public void putAll(Map<? extends String, ? extends Object> t) { this.map.putAll(t); }
/*     */ 
/*     */ 
/*     */   
/* 219 */   public int size() { return this.map.size(); }
/*     */ 
/*     */ 
/*     */   
/* 223 */   public Collection<Object> values() { return this.map.values(); }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\query\DefaultSqlRow.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */