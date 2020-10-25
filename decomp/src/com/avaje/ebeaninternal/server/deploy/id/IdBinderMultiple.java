/*     */ package com.avaje.ebeaninternal.server.deploy.id;
/*     */ 
/*     */ import com.avaje.ebeaninternal.api.SpiExpressionRequest;
/*     */ import com.avaje.ebeaninternal.server.core.DefaultSqlUpdate;
/*     */ import com.avaje.ebeaninternal.server.core.InternString;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanProperty;
/*     */ import com.avaje.ebeaninternal.server.deploy.DbReadContext;
/*     */ import com.avaje.ebeaninternal.server.deploy.DbSqlContext;
/*     */ import com.avaje.ebeaninternal.server.lib.util.MapFromString;
/*     */ import com.avaje.ebeaninternal.server.type.DataBind;
/*     */ import java.io.DataInput;
/*     */ import java.io.DataOutput;
/*     */ import java.io.IOException;
/*     */ import java.sql.SQLException;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import javax.naming.InvalidNameException;
/*     */ import javax.naming.ldap.LdapName;
/*     */ import javax.naming.ldap.Rdn;
/*     */ import javax.persistence.PersistenceException;
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
/*     */ public final class IdBinderMultiple
/*     */   implements IdBinder
/*     */ {
/*     */   private final BeanProperty[] props;
/*     */   private final String idProperties;
/*     */   private final String idInValueSql;
/*     */   
/*     */   public IdBinderMultiple(BeanProperty[] idProps) {
/*  40 */     this.props = idProps;
/*     */     
/*  42 */     StringBuilder sb = new StringBuilder();
/*  43 */     for (i = 0; i < idProps.length; i++) {
/*  44 */       if (i > 0) {
/*  45 */         sb.append(",");
/*     */       }
/*  47 */       sb.append(idProps[i].getName());
/*     */     } 
/*  49 */     this.idProperties = InternString.intern(sb.toString());
/*     */     
/*  51 */     sb = new StringBuilder();
/*  52 */     sb.append("(");
/*  53 */     for (int i = 0; i < this.props.length; i++) {
/*  54 */       if (i > 0) {
/*  55 */         sb.append(",");
/*     */       }
/*  57 */       sb.append("?");
/*     */     } 
/*  59 */     sb.append(")");
/*     */     
/*  61 */     this.idInValueSql = sb.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   public void initialise() {}
/*     */ 
/*     */   
/*     */   public String getOrderBy(String pathPrefix, boolean ascending) {
/*  69 */     StringBuilder sb = new StringBuilder();
/*  70 */     for (int i = 0; i < this.props.length; i++) {
/*  71 */       if (i > 0) {
/*  72 */         sb.append(" ");
/*     */       }
/*  74 */       if (pathPrefix != null) {
/*  75 */         sb.append(pathPrefix).append(".");
/*     */       }
/*  77 */       sb.append(this.props[i].getName());
/*  78 */       if (!ascending) {
/*  79 */         sb.append(" desc");
/*     */       }
/*     */     } 
/*  82 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   public void createLdapNameById(LdapName name, Object id) throws InvalidNameException {
/*  87 */     if (!(id instanceof Map)) {
/*  88 */       throw new RuntimeException("Expecting a Map for concatinated key");
/*     */     }
/*     */     
/*  91 */     Map<?, ?> mapId = (Map)id;
/*  92 */     for (int i = 0; i < this.props.length; i++) {
/*     */       
/*  94 */       Object v = mapId.get(this.props[i].getName());
/*  95 */       if (v == null) {
/*  96 */         throw new RuntimeException("No value in Map for key " + this.props[i].getName());
/*     */       }
/*     */       
/*  99 */       Rdn rdn = new Rdn(this.props[i].getDbColumn(), v);
/* 100 */       name.add(rdn);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void buildSelectExpressionChain(String prefix, List<String> selectChain) {
/* 106 */     for (int i = 0; i < this.props.length; i++) {
/* 107 */       this.props[i].buildSelectExpressionChain(prefix, selectChain);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void createLdapNameByBean(LdapName name, Object bean) throws InvalidNameException {
/* 113 */     for (int i = 0; i < this.props.length; i++) {
/*     */       
/* 115 */       Object v = this.props[i].getValue(bean);
/* 116 */       Rdn rdn = new Rdn(this.props[i].getDbColumn(), v);
/* 117 */       name.add(rdn);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/* 122 */   public int getPropertyCount() { return this.props.length; }
/*     */ 
/*     */ 
/*     */   
/* 126 */   public String getIdProperty() { return this.idProperties; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BeanProperty findBeanProperty(String dbColumnName) {
/* 132 */     for (int i = 0; i < this.props.length; i++) {
/* 133 */       if (dbColumnName.equalsIgnoreCase(this.props[i].getDbColumn())) {
/* 134 */         return this.props[i];
/*     */       }
/*     */     } 
/*     */     
/* 138 */     return null;
/*     */   }
/*     */ 
/*     */   
/* 142 */   public boolean isComplexId() { return true; }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDefaultOrderBy() {
/* 147 */     StringBuilder sb = new StringBuilder();
/* 148 */     for (int i = 0; i < this.props.length; i++) {
/* 149 */       if (i > 0) {
/* 150 */         sb.append(",");
/*     */       }
/*     */       
/* 153 */       sb.append(this.props[i].getName());
/*     */     } 
/*     */     
/* 156 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   
/* 160 */   public BeanProperty[] getProperties() { return this.props; }
/*     */ 
/*     */   
/*     */   public void addIdInBindValue(SpiExpressionRequest request, Object value) {
/* 164 */     for (int i = 0; i < this.props.length; i++) {
/* 165 */       request.addBindValue(this.props[i].getValue(value));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/* 170 */   public String getIdInValueExprDelete(int size) { return getIdInValueExpr(size); }
/*     */ 
/*     */   
/*     */   public String getIdInValueExpr(int size) {
/* 174 */     StringBuilder sb = new StringBuilder();
/* 175 */     sb.append(" in");
/* 176 */     sb.append(" (");
/* 177 */     sb.append(this.idInValueSql);
/* 178 */     for (int i = 1; i < size; i++) {
/* 179 */       sb.append(",").append(this.idInValueSql);
/*     */     }
/* 181 */     sb.append(") ");
/* 182 */     return sb.toString();
/*     */   }
/*     */   
/*     */   public String getBindIdInSql(String baseTableAlias) {
/* 186 */     StringBuilder sb = new StringBuilder();
/* 187 */     sb.append("(");
/* 188 */     for (int i = 0; i < this.props.length; i++) {
/* 189 */       if (i > 0) {
/* 190 */         sb.append(",");
/*     */       }
/* 192 */       if (baseTableAlias != null) {
/* 193 */         sb.append(baseTableAlias);
/* 194 */         sb.append(".");
/*     */       } 
/* 196 */       sb.append(this.props[i].getDbColumn());
/*     */     } 
/* 198 */     sb.append(")");
/* 199 */     return sb.toString();
/*     */   }
/*     */   
/*     */   public Object[] getIdValues(Object bean) {
/* 203 */     Object[] bindvalues = new Object[this.props.length];
/* 204 */     for (int i = 0; i < this.props.length; i++) {
/* 205 */       bindvalues[i] = this.props[i].getValue(bean);
/*     */     }
/* 207 */     return bindvalues;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Object[] getBindValues(Object idValue) {
/* 213 */     Object[] bindvalues = new Object[this.props.length];
/*     */     
/*     */     try {
/* 216 */       Map<String, ?> uidMap = (Map)idValue;
/*     */       
/* 218 */       for (int i = 0; i < this.props.length; i++) {
/* 219 */         Object value = uidMap.get(this.props[i].getName());
/* 220 */         bindvalues[i] = value;
/*     */       } 
/*     */       
/* 223 */       return bindvalues;
/*     */     }
/* 225 */     catch (ClassCastException e) {
/* 226 */       String msg = "Expecting concatinated idValue to be a Map";
/* 227 */       throw new PersistenceException(msg, e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Object readTerm(String idTermValue) {
/* 234 */     String[] split = idTermValue.split("|");
/* 235 */     if (split.length != this.props.length) {
/* 236 */       String msg = "Failed to split [" + idTermValue + "] using | for id.";
/* 237 */       throw new PersistenceException(msg);
/*     */     } 
/* 239 */     Map<String, Object> uidMap = new LinkedHashMap<String, Object>();
/* 240 */     for (int i = 0; i < this.props.length; i++) {
/* 241 */       Object v = this.props[i].getScalarType().parse(split[i]);
/* 242 */       uidMap.put(this.props[i].getName(), v);
/*     */     } 
/* 244 */     return uidMap;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String writeTerm(Object idValue) {
/* 250 */     Map<String, ?> uidMap = (Map)idValue;
/*     */     
/* 252 */     StringBuilder sb = new StringBuilder();
/*     */     
/* 254 */     for (int i = 0; i < this.props.length; i++) {
/* 255 */       Object v = uidMap.get(this.props[i].getName());
/* 256 */       String formatValue = this.props[i].getScalarType().format(v);
/* 257 */       if (i > 0) {
/* 258 */         sb.append("|");
/*     */       }
/* 260 */       sb.append(formatValue);
/*     */     } 
/* 262 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   public Object readData(DataInput dataInput) throws IOException {
/* 267 */     LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
/*     */     
/* 269 */     boolean notNull = true;
/*     */     
/* 271 */     for (int i = 0; i < this.props.length; i++) {
/* 272 */       Object value = this.props[i].readData(dataInput);
/* 273 */       map.put(this.props[i].getName(), value);
/* 274 */       if (value == null) {
/* 275 */         notNull = false;
/*     */       }
/*     */     } 
/*     */     
/* 279 */     if (notNull) {
/* 280 */       return map;
/*     */     }
/* 282 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeData(DataOutput dataOutput, Object idValue) throws IOException {
/* 289 */     Map<String, Object> map = (Map)idValue;
/* 290 */     for (int i = 0; i < this.props.length; i++) {
/* 291 */       Object embFieldValue = map.get(this.props[i].getName());
/*     */       
/* 293 */       this.props[i].writeData(dataOutput, embFieldValue);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void loadIgnore(DbReadContext ctx) {
/* 298 */     for (int i = 0; i < this.props.length; i++) {
/* 299 */       this.props[i].loadIgnore(ctx);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public Object readSet(DbReadContext ctx, Object bean) throws SQLException {
/* 305 */     LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
/* 306 */     boolean notNull = false;
/* 307 */     for (int i = 0; i < this.props.length; i++) {
/* 308 */       Object value = this.props[i].readSet(ctx, bean, null);
/* 309 */       if (value != null) {
/* 310 */         map.put(this.props[i].getName(), value);
/* 311 */         notNull = true;
/*     */       } 
/*     */     } 
/* 314 */     if (notNull) {
/* 315 */       return map;
/*     */     }
/* 317 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Object read(DbReadContext ctx) throws SQLException {
/* 323 */     LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
/* 324 */     boolean notNull = false;
/* 325 */     for (int i = 0; i < this.props.length; i++) {
/* 326 */       Object value = this.props[i].read(ctx);
/* 327 */       if (value != null) {
/* 328 */         map.put(this.props[i].getName(), value);
/* 329 */         notNull = true;
/*     */       } 
/*     */     } 
/* 332 */     if (notNull) {
/* 333 */       return map;
/*     */     }
/* 335 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void bindId(DefaultSqlUpdate sqlUpdate, Object idValue) {
/*     */     try {
/* 343 */       Map<String, ?> uidMap = (Map)idValue;
/*     */       
/* 345 */       for (int i = 0; i < this.props.length; i++) {
/* 346 */         Object value = uidMap.get(this.props[i].getName());
/* 347 */         sqlUpdate.addParameter(value);
/*     */       }
/*     */     
/* 350 */     } catch (ClassCastException e) {
/* 351 */       String msg = "Expecting concatinated idValue to be a Map";
/* 352 */       throw new PersistenceException(msg, e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void bindId(DataBind bind, Object idValue) throws SQLException {
/*     */     try {
/* 361 */       Map<String, ?> uidMap = (Map)idValue;
/*     */       
/* 363 */       for (int i = 0; i < this.props.length; i++) {
/* 364 */         Object value = uidMap.get(this.props[i].getName());
/* 365 */         this.props[i].bind(bind, value);
/*     */       }
/*     */     
/* 368 */     } catch (ClassCastException e) {
/* 369 */       String msg = "Expecting concatinated idValue to be a Map";
/* 370 */       throw new PersistenceException(msg, e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void appendSelect(DbSqlContext ctx, boolean subQuery) {
/* 375 */     for (int i = 0; i < this.props.length; i++) {
/* 376 */       this.props[i].appendSelect(ctx, subQuery);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public String getAssocIdInExpr(String prefix) {
/* 382 */     StringBuilder sb = new StringBuilder();
/* 383 */     sb.append("(");
/* 384 */     for (int i = 0; i < this.props.length; i++) {
/* 385 */       if (i > 0) {
/* 386 */         sb.append(",");
/*     */       }
/* 388 */       if (prefix != null) {
/* 389 */         sb.append(prefix);
/* 390 */         sb.append(".");
/*     */       } 
/* 392 */       sb.append(this.props[i].getName());
/*     */     } 
/* 394 */     sb.append(")");
/* 395 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   public String getAssocOneIdExpr(String prefix, String operator) {
/* 400 */     StringBuilder sb = new StringBuilder();
/* 401 */     for (int i = 0; i < this.props.length; i++) {
/* 402 */       if (i > 0) {
/* 403 */         sb.append(" and ");
/*     */       }
/* 405 */       if (prefix != null) {
/* 406 */         sb.append(prefix);
/* 407 */         sb.append(".");
/*     */       } 
/* 409 */       sb.append(this.props[i].getName());
/* 410 */       sb.append(operator);
/*     */     } 
/* 412 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   public String getBindIdSql(String baseTableAlias) {
/* 417 */     StringBuilder sb = new StringBuilder();
/* 418 */     for (int i = 0; i < this.props.length; i++) {
/* 419 */       if (i > 0) {
/* 420 */         sb.append(" and ");
/*     */       }
/* 422 */       if (baseTableAlias != null) {
/* 423 */         sb.append(baseTableAlias);
/* 424 */         sb.append(".");
/*     */       } 
/* 426 */       sb.append(this.props[i].getDbColumn());
/* 427 */       sb.append(" = ? ");
/*     */     } 
/* 429 */     return sb.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Object convertSetId(Object idValue, Object bean) {
/* 435 */     Map<?, ?> mapVal = null;
/* 436 */     if (idValue instanceof Map) {
/* 437 */       mapVal = (Map)idValue;
/*     */     } else {
/* 439 */       mapVal = MapFromString.parse(idValue.toString());
/*     */     } 
/*     */ 
/*     */     
/* 443 */     LinkedHashMap<String, Object> newMap = new LinkedHashMap<String, Object>();
/*     */     
/* 445 */     for (int i = 0; i < this.props.length; i++) {
/* 446 */       BeanProperty prop = this.props[i];
/*     */       
/* 448 */       Object value = mapVal.get(prop.getName());
/*     */ 
/*     */       
/* 451 */       value = this.props[i].getScalarType().toBeanType(value);
/* 452 */       newMap.put(prop.getName(), value);
/* 453 */       if (bean != null)
/*     */       {
/* 455 */         prop.setValueIntercept(bean, value);
/*     */       }
/*     */     } 
/*     */     
/* 459 */     return newMap;
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\deploy\id\IdBinderMultiple.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */