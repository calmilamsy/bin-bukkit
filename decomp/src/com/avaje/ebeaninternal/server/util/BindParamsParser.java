/*     */ package com.avaje.ebeaninternal.server.util;
/*     */ 
/*     */ import com.avaje.ebean.config.EncryptKey;
/*     */ import com.avaje.ebeaninternal.api.BindParams;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
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
/*     */ public class BindParamsParser
/*     */ {
/*     */   public static final String ENCRYPTKEY_PREFIX = "encryptkey_";
/*     */   public static final String ENCRYPTKEY_GAP = "___";
/*  45 */   private static final int ENCRYPTKEY_PREFIX_LEN = "encryptkey_".length();
/*  46 */   private static final int ENCRYPTKEY_GAP_LEN = "___".length();
/*     */ 
/*     */   
/*     */   private static final String quote = "'";
/*     */ 
/*     */   
/*     */   private static final String colon = ":";
/*     */ 
/*     */   
/*     */   private final BindParams params;
/*     */ 
/*     */   
/*     */   private final String sql;
/*     */ 
/*     */   
/*     */   private final BeanDescriptor<?> beanDescriptor;
/*     */ 
/*     */   
/*  64 */   public static String parse(BindParams params, String sql) { return parse(params, sql, null); }
/*     */ 
/*     */ 
/*     */   
/*  68 */   public static String parse(BindParams params, String sql, BeanDescriptor<?> beanDescriptor) { return (new BindParamsParser(params, sql, beanDescriptor)).parseSql(); }
/*     */ 
/*     */ 
/*     */   
/*  72 */   public static BindParams.OrderedList parseNamedParams(BindParams params, String sql) { return (new BindParamsParser(params, sql, null)).parseSqlNamedParams(); }
/*     */ 
/*     */   
/*     */   private BindParamsParser(BindParams params, String sql, BeanDescriptor<?> beanDescriptor) {
/*  76 */     this.params = params;
/*  77 */     this.sql = sql;
/*  78 */     this.beanDescriptor = beanDescriptor;
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
/*     */   private BindParams.OrderedList parseSqlNamedParams() {
/*  90 */     BindParams.OrderedList orderedList = new BindParams.OrderedList();
/*  91 */     parseNamedParams(orderedList);
/*  92 */     return orderedList;
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
/*     */ 
/*     */   
/*     */   private String parseSql() {
/* 108 */     String prepardSql, preparedSql = this.params.getPreparedSql();
/* 109 */     if (preparedSql != null && preparedSql.length() > 0)
/*     */     {
/*     */       
/* 112 */       return preparedSql;
/*     */     }
/*     */ 
/*     */     
/* 116 */     if (this.params.requiresNamedParamsPrepare()) {
/* 117 */       BindParams.OrderedList orderedList = new BindParams.OrderedList(this.params.positionedParameters());
/*     */       
/* 119 */       parseNamedParams(orderedList);
/* 120 */       prepardSql = orderedList.getPreparedSql();
/*     */     } else {
/* 122 */       prepardSql = this.sql;
/*     */     } 
/* 124 */     this.params.setPreparedSql(prepardSql);
/* 125 */     return prepardSql;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 135 */   private void parseNamedParams(BindParams.OrderedList orderedList) { parseNamedParams(0, orderedList); }
/*     */ 
/*     */ 
/*     */   
/*     */   private void parseNamedParams(int startPos, BindParams.OrderedList orderedList) {
/* 140 */     if (this.sql == null) {
/* 141 */       throw new PersistenceException("query does not contain any named bind parameters?");
/*     */     }
/* 143 */     if (startPos > this.sql.length()) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 148 */     int beginQuotePos = this.sql.indexOf("'", startPos);
/* 149 */     int nameParamStart = this.sql.indexOf(":", startPos);
/* 150 */     if (beginQuotePos > 0 && beginQuotePos < nameParamStart) {
/*     */ 
/*     */       
/* 153 */       int endQuotePos = this.sql.indexOf("'", beginQuotePos + 1);
/* 154 */       String sub = this.sql.substring(startPos, endQuotePos + 1);
/* 155 */       orderedList.appendSql(sub);
/*     */ 
/*     */       
/* 158 */       parseNamedParams(endQuotePos + 1, orderedList);
/*     */     
/*     */     }
/* 161 */     else if (nameParamStart < 0) {
/*     */       
/* 163 */       String sub = this.sql.substring(startPos, this.sql.length());
/* 164 */       orderedList.appendSql(sub);
/*     */     } else {
/*     */       BindParams.Param param;
/*     */       
/* 168 */       int endOfParam = nameParamStart + 1;
/*     */       do {
/* 170 */         char c = this.sql.charAt(endOfParam);
/* 171 */         if (c != '_' && !Character.isLetterOrDigit(c)) {
/*     */           break;
/*     */         }
/* 174 */         ++endOfParam;
/* 175 */       } while (endOfParam < this.sql.length());
/*     */ 
/*     */       
/* 178 */       String paramName = this.sql.substring(nameParamStart + 1, endOfParam);
/*     */ 
/*     */       
/* 181 */       if (paramName.startsWith("encryptkey_")) {
/* 182 */         param = addEncryptKeyParam(paramName);
/*     */       } else {
/* 184 */         param = this.params.getParameter(paramName);
/*     */       } 
/*     */       
/* 187 */       if (param == null) {
/* 188 */         String msg = "Bind value is not set or null for [" + paramName + "] in [" + this.sql + "]";
/*     */         
/* 190 */         throw new PersistenceException(msg);
/*     */       } 
/*     */       
/* 193 */       String sub = this.sql.substring(startPos, nameParamStart);
/* 194 */       orderedList.appendSql(sub);
/*     */ 
/*     */       
/* 197 */       Object inValue = param.getInValue();
/* 198 */       if (inValue != null && inValue instanceof Collection) {
/*     */ 
/*     */         
/* 201 */         Collection<?> collection = (Collection)inValue;
/* 202 */         Iterator<?> it = collection.iterator();
/* 203 */         int c = 0;
/* 204 */         while (it.hasNext()) {
/* 205 */           Object elVal = it.next();
/* 206 */           if (++c > 1) {
/* 207 */             orderedList.appendSql(",");
/*     */           }
/* 209 */           orderedList.appendSql("?");
/* 210 */           BindParams.Param elParam = new BindParams.Param();
/* 211 */           elParam.setInValue(elVal);
/* 212 */           orderedList.add(elParam);
/*     */         }
/*     */       
/*     */       } else {
/*     */         
/* 217 */         orderedList.add(param);
/* 218 */         orderedList.appendSql("?");
/*     */       } 
/*     */ 
/*     */       
/* 222 */       parseNamedParams(endOfParam, orderedList);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private BindParams.Param addEncryptKeyParam(String keyNamedParam) {
/* 233 */     int pos = keyNamedParam.indexOf("___", ENCRYPTKEY_PREFIX_LEN);
/*     */     
/* 235 */     String tableName = keyNamedParam.substring(ENCRYPTKEY_PREFIX_LEN, pos);
/* 236 */     String columnName = keyNamedParam.substring(pos + ENCRYPTKEY_GAP_LEN);
/*     */     
/* 238 */     EncryptKey key = this.beanDescriptor.getEncryptKey(tableName, columnName);
/* 239 */     String strKey = key.getStringValue();
/*     */     
/* 241 */     return this.params.setEncryptionKey(keyNamedParam, strKey);
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\serve\\util\BindParamsParser.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */