/*     */ package com.avaje.ebeaninternal.server.deploy.id;
/*     */ 
/*     */ import com.avaje.ebeaninternal.api.SpiExpressionRequest;
/*     */ import com.avaje.ebeaninternal.server.core.DefaultSqlUpdate;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanProperty;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanPropertyAssocOne;
/*     */ import com.avaje.ebeaninternal.server.deploy.DbReadContext;
/*     */ import com.avaje.ebeaninternal.server.deploy.DbSqlContext;
/*     */ import com.avaje.ebeaninternal.server.query.SplitName;
/*     */ import com.avaje.ebeaninternal.server.type.DataBind;
/*     */ import java.io.DataInput;
/*     */ import java.io.DataOutput;
/*     */ import java.io.IOException;
/*     */ import java.sql.SQLException;
/*     */ import java.util.List;
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
/*     */ public final class IdBinderEmbedded
/*     */   implements IdBinder
/*     */ {
/*     */   private final BeanPropertyAssocOne<?> embIdProperty;
/*     */   private final boolean idInExpandedForm;
/*     */   private BeanProperty[] props;
/*     */   private BeanDescriptor<?> idDesc;
/*     */   private String idInValueSql;
/*     */   
/*     */   public IdBinderEmbedded(boolean idInExpandedForm, BeanPropertyAssocOne<?> embIdProperty) {
/*  40 */     this.idInExpandedForm = idInExpandedForm;
/*  41 */     this.embIdProperty = embIdProperty;
/*     */   }
/*     */   
/*     */   public void initialise() {
/*  45 */     this.idDesc = this.embIdProperty.getTargetDescriptor();
/*  46 */     this.props = this.embIdProperty.getProperties();
/*  47 */     this.idInValueSql = this.idInExpandedForm ? idInExpanded() : idInCompressed();
/*     */   }
/*     */ 
/*     */   
/*     */   private String idInExpanded() {
/*  52 */     StringBuilder sb = new StringBuilder();
/*  53 */     sb.append("(");
/*  54 */     for (int i = 0; i < this.props.length; i++) {
/*  55 */       if (i > 0) {
/*  56 */         sb.append(" and ");
/*     */       }
/*  58 */       sb.append(this.embIdProperty.getName());
/*  59 */       sb.append(".");
/*  60 */       sb.append(this.props[i].getName());
/*  61 */       sb.append("=?");
/*     */     } 
/*  63 */     sb.append(")");
/*     */     
/*  65 */     return sb.toString();
/*     */   }
/*     */   
/*     */   private String idInCompressed() {
/*  69 */     StringBuilder sb = new StringBuilder();
/*  70 */     sb.append("(");
/*  71 */     for (int i = 0; i < this.props.length; i++) {
/*  72 */       if (i > 0) {
/*  73 */         sb.append(",");
/*     */       }
/*  75 */       sb.append("?");
/*     */     } 
/*  77 */     sb.append(")");
/*     */     
/*  79 */     return sb.toString();
/*     */   }
/*     */   
/*     */   public String getOrderBy(String pathPrefix, boolean ascending) {
/*  83 */     StringBuilder sb = new StringBuilder();
/*  84 */     for (int i = 0; i < this.props.length; i++) {
/*  85 */       if (i > 0) {
/*  86 */         sb.append(", ");
/*     */       }
/*  88 */       if (pathPrefix != null) {
/*  89 */         sb.append(pathPrefix).append(".");
/*     */       }
/*     */       
/*  92 */       sb.append(this.embIdProperty.getName()).append(".");
/*  93 */       sb.append(this.props[i].getName());
/*  94 */       if (!ascending) {
/*  95 */         sb.append(" desc");
/*     */       }
/*     */     } 
/*  98 */     return sb.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void createLdapNameById(LdapName name, Object id) throws InvalidNameException {
/* 104 */     for (int i = 0; i < this.props.length; i++) {
/* 105 */       Object v = this.props[i].getValue(id);
/* 106 */       Rdn rdn = new Rdn(this.props[i].getDbColumn(), v);
/* 107 */       name.add(rdn);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void createLdapNameByBean(LdapName name, Object bean) throws InvalidNameException {
/* 114 */     Object id = this.embIdProperty.getValue(bean);
/* 115 */     createLdapNameById(name, id);
/*     */   }
/*     */ 
/*     */   
/* 119 */   public BeanDescriptor<?> getIdBeanDescriptor() { return this.idDesc; }
/*     */ 
/*     */ 
/*     */   
/* 123 */   public int getPropertyCount() { return this.props.length; }
/*     */ 
/*     */ 
/*     */   
/* 127 */   public String getIdProperty() { return this.embIdProperty.getName(); }
/*     */ 
/*     */ 
/*     */   
/*     */   public void buildSelectExpressionChain(String prefix, List<String> selectChain) {
/* 132 */     prefix = SplitName.add(prefix, this.embIdProperty.getName());
/*     */     
/* 134 */     for (int i = 0; i < this.props.length; i++) {
/* 135 */       this.props[i].buildSelectExpressionChain(prefix, selectChain);
/*     */     }
/*     */   }
/*     */   
/*     */   public BeanProperty findBeanProperty(String dbColumnName) {
/* 140 */     for (int i = 0; i < this.props.length; i++) {
/* 141 */       if (dbColumnName.equalsIgnoreCase(this.props[i].getDbColumn())) {
/* 142 */         return this.props[i];
/*     */       }
/*     */     } 
/* 145 */     return null;
/*     */   }
/*     */ 
/*     */   
/* 149 */   public boolean isComplexId() { return true; }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDefaultOrderBy() {
/* 154 */     StringBuilder sb = new StringBuilder();
/* 155 */     for (int i = 0; i < this.props.length; i++) {
/* 156 */       if (i > 0) {
/* 157 */         sb.append(",");
/*     */       }
/*     */       
/* 160 */       sb.append(this.embIdProperty.getName());
/* 161 */       sb.append(".");
/* 162 */       sb.append(this.props[i].getName());
/*     */     } 
/*     */     
/* 165 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   
/* 169 */   public BeanProperty[] getProperties() { return this.props; }
/*     */ 
/*     */   
/*     */   public void addIdInBindValue(SpiExpressionRequest request, Object value) {
/* 173 */     for (int i = 0; i < this.props.length; i++) {
/* 174 */       request.addBindValue(this.props[i].getValue(value));
/*     */     }
/*     */   }
/*     */   
/*     */   public String getIdInValueExprDelete(int size) {
/* 179 */     if (!this.idInExpandedForm) {
/* 180 */       return getIdInValueExpr(size);
/*     */     }
/*     */     
/* 183 */     StringBuilder sb = new StringBuilder();
/* 184 */     sb.append("(");
/*     */     
/* 186 */     for (int j = 0; j < size; j++) {
/* 187 */       if (j > 0) {
/* 188 */         sb.append(" or ");
/*     */       }
/* 190 */       sb.append("(");
/* 191 */       for (int i = 0; i < this.props.length; i++) {
/* 192 */         if (i > 0) {
/* 193 */           sb.append(" and ");
/*     */         }
/* 195 */         sb.append(this.props[i].getDbColumn());
/* 196 */         sb.append("=?");
/*     */       } 
/* 198 */       sb.append(")");
/*     */     } 
/* 200 */     sb.append(") ");
/* 201 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   public String getIdInValueExpr(int size) {
/* 206 */     StringBuilder sb = new StringBuilder();
/*     */     
/* 208 */     if (!this.idInExpandedForm) {
/* 209 */       sb.append(" in");
/*     */     }
/* 211 */     sb.append(" (");
/* 212 */     for (int i = 0; i < size; i++) {
/* 213 */       if (i > 0) {
/* 214 */         if (this.idInExpandedForm) {
/* 215 */           sb.append(" or ");
/*     */         } else {
/* 217 */           sb.append(",");
/*     */         } 
/*     */       }
/* 220 */       sb.append(this.idInValueSql);
/*     */     } 
/* 222 */     sb.append(") ");
/* 223 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   
/* 227 */   public String getIdInValueExpr() { return this.idInValueSql; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object readTerm(String idTermValue) {
/* 235 */     String[] split = idTermValue.split("|");
/* 236 */     if (split.length != this.props.length) {
/* 237 */       String msg = "Failed to split [" + idTermValue + "] using | for id.";
/* 238 */       throw new PersistenceException(msg);
/*     */     } 
/* 240 */     Object embId = this.idDesc.createVanillaBean();
/* 241 */     for (int i = 0; i < this.props.length; i++) {
/* 242 */       Object v = this.props[i].getScalarType().parse(split[i]);
/* 243 */       this.props[i].setValue(embId, v);
/*     */     } 
/* 245 */     return embId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String writeTerm(Object idValue) {
/* 253 */     StringBuilder sb = new StringBuilder();
/*     */     
/* 255 */     for (int i = 0; i < this.props.length; i++) {
/* 256 */       Object v = this.props[i].getValue(idValue);
/* 257 */       String formatValue = this.props[i].getScalarType().format(v);
/* 258 */       if (i > 0) {
/* 259 */         sb.append("|");
/*     */       }
/* 261 */       sb.append(formatValue);
/*     */     } 
/* 263 */     return sb.toString();
/*     */   }
/*     */   
/*     */   public Object[] getIdValues(Object bean) {
/* 267 */     bean = this.embIdProperty.getValue(bean);
/* 268 */     Object[] bindvalues = new Object[this.props.length];
/* 269 */     for (int i = 0; i < this.props.length; i++) {
/* 270 */       bindvalues[i] = this.props[i].getValue(bean);
/*     */     }
/* 272 */     return bindvalues;
/*     */   }
/*     */ 
/*     */   
/*     */   public Object[] getBindValues(Object value) {
/* 277 */     Object[] bindvalues = new Object[this.props.length];
/* 278 */     for (int i = 0; i < this.props.length; i++) {
/* 279 */       bindvalues[i] = this.props[i].getValue(value);
/*     */     }
/* 281 */     return bindvalues;
/*     */   }
/*     */   
/*     */   public void bindId(DefaultSqlUpdate sqlUpdate, Object value) {
/* 285 */     for (int i = 0; i < this.props.length; i++) {
/* 286 */       Object embFieldValue = this.props[i].getValue(value);
/* 287 */       sqlUpdate.addParameter(embFieldValue);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void bindId(DataBind dataBind, Object value) throws SQLException {
/* 293 */     for (int i = 0; i < this.props.length; i++) {
/* 294 */       Object embFieldValue = this.props[i].getValue(value);
/* 295 */       this.props[i].bind(dataBind, embFieldValue);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public Object readData(DataInput dataInput) throws IOException {
/* 301 */     Object embId = this.idDesc.createVanillaBean();
/* 302 */     boolean notNull = true;
/*     */     
/* 304 */     for (int i = 0; i < this.props.length; i++) {
/* 305 */       Object value = this.props[i].readData(dataInput);
/* 306 */       this.props[i].setValue(embId, value);
/* 307 */       if (value == null) {
/* 308 */         notNull = false;
/*     */       }
/*     */     } 
/*     */     
/* 312 */     if (notNull) {
/* 313 */       return embId;
/*     */     }
/* 315 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeData(DataOutput dataOutput, Object idValue) throws IOException {
/* 320 */     for (int i = 0; i < this.props.length; i++) {
/* 321 */       Object embFieldValue = this.props[i].getValue(idValue);
/* 322 */       this.props[i].writeData(dataOutput, embFieldValue);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void loadIgnore(DbReadContext ctx) {
/* 327 */     for (int i = 0; i < this.props.length; i++) {
/* 328 */       this.props[i].loadIgnore(ctx);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public Object read(DbReadContext ctx) throws SQLException {
/* 334 */     Object embId = this.idDesc.createVanillaBean();
/* 335 */     boolean notNull = true;
/*     */     
/* 337 */     for (int i = 0; i < this.props.length; i++) {
/* 338 */       Object value = this.props[i].readSet(ctx, embId, null);
/* 339 */       if (value == null) {
/* 340 */         notNull = false;
/*     */       }
/*     */     } 
/*     */     
/* 344 */     if (notNull) {
/* 345 */       return embId;
/*     */     }
/* 347 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Object readSet(DbReadContext ctx, Object bean) throws SQLException {
/* 353 */     Object embId = read(ctx);
/* 354 */     if (embId != null) {
/* 355 */       this.embIdProperty.setValue(bean, embId);
/* 356 */       return embId;
/*     */     } 
/* 358 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public void appendSelect(DbSqlContext ctx, boolean subQuery) {
/* 363 */     for (int i = 0; i < this.props.length; i++) {
/* 364 */       this.props[i].appendSelect(ctx, subQuery);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public String getAssocIdInExpr(String prefix) {
/* 370 */     StringBuilder sb = new StringBuilder();
/* 371 */     sb.append("(");
/* 372 */     for (int i = 0; i < this.props.length; i++) {
/* 373 */       if (i > 0) {
/* 374 */         sb.append(",");
/*     */       }
/* 376 */       if (prefix != null) {
/* 377 */         sb.append(prefix);
/* 378 */         sb.append(".");
/*     */       } 
/* 380 */       sb.append(this.props[i].getName());
/*     */     } 
/* 382 */     sb.append(")");
/* 383 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   public String getAssocOneIdExpr(String prefix, String operator) {
/* 388 */     StringBuilder sb = new StringBuilder();
/* 389 */     for (int i = 0; i < this.props.length; i++) {
/* 390 */       if (i > 0) {
/* 391 */         sb.append(" and ");
/*     */       }
/* 393 */       if (prefix != null) {
/* 394 */         sb.append(prefix);
/* 395 */         sb.append(".");
/*     */       } 
/*     */       
/* 398 */       sb.append(this.embIdProperty.getName());
/* 399 */       sb.append(".");
/* 400 */       sb.append(this.props[i].getName());
/* 401 */       sb.append(operator);
/*     */     } 
/* 403 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   public String getBindIdSql(String baseTableAlias) {
/* 408 */     StringBuilder sb = new StringBuilder();
/* 409 */     for (int i = 0; i < this.props.length; i++) {
/* 410 */       if (i > 0) {
/* 411 */         sb.append(" and ");
/*     */       }
/* 413 */       if (baseTableAlias != null) {
/* 414 */         sb.append(baseTableAlias);
/* 415 */         sb.append(".");
/*     */       } 
/* 417 */       sb.append(this.props[i].getDbColumn());
/* 418 */       sb.append(" = ? ");
/*     */     } 
/* 420 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   public String getBindIdInSql(String baseTableAlias) {
/* 425 */     if (this.idInExpandedForm) {
/* 426 */       return "";
/*     */     }
/*     */     
/* 429 */     StringBuilder sb = new StringBuilder();
/* 430 */     sb.append("(");
/* 431 */     for (int i = 0; i < this.props.length; i++) {
/* 432 */       if (i > 0) {
/* 433 */         sb.append(",");
/*     */       }
/* 435 */       if (baseTableAlias != null) {
/* 436 */         sb.append(baseTableAlias);
/* 437 */         sb.append(".");
/*     */       } 
/* 439 */       sb.append(this.props[i].getDbColumn());
/*     */     } 
/* 441 */     sb.append(")");
/* 442 */     return sb.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Object convertSetId(Object idValue, Object bean) {
/* 448 */     if (bean != null)
/*     */     {
/* 450 */       this.embIdProperty.setValueIntercept(bean, idValue);
/*     */     }
/*     */     
/* 453 */     return idValue;
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\deploy\id\IdBinderEmbedded.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */