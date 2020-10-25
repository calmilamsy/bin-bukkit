/*     */ package com.avaje.ebean;
/*     */ 
/*     */ import com.avaje.ebeaninternal.server.querydefn.SimpleTextParser;
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
/*     */ class DRawSqlParser
/*     */ {
/*     */   public static final String $_AND_HAVING = "${andHaving}";
/*     */   public static final String $_HAVING = "${having}";
/*     */   public static final String $_AND_WHERE = "${andWhere}";
/*     */   public static final String $_WHERE = "${where}";
/*     */   private static final String ORDER_BY = "order by";
/*     */   private final SimpleTextParser textParser;
/*     */   private String sql;
/*     */   private int placeHolderWhere;
/*     */   private int placeHolderAndWhere;
/*     */   private int placeHolderHaving;
/*     */   private int placeHolderAndHaving;
/*     */   private boolean hasPlaceHolders;
/*     */   private int selectPos;
/*     */   private int fromPos;
/*     */   private int wherePos;
/*     */   private int groupByPos;
/*     */   private int havingPos;
/*     */   private int orderByPos;
/*     */   private boolean whereExprAnd;
/*     */   private int whereExprPos;
/*     */   private boolean havingExprAnd;
/*     */   private int havingExprPos;
/*     */   
/*  45 */   public static RawSql.Sql parse(String sql) { return (new DRawSqlParser(sql)).parse(); } private DRawSqlParser(String sql) { this.selectPos = -1; this.fromPos = -1; this.wherePos = -1; this.groupByPos = -1;
/*     */     this.havingPos = -1;
/*     */     this.orderByPos = -1;
/*     */     this.whereExprPos = -1;
/*     */     this.havingExprPos = -1;
/*  50 */     this.sql = sql;
/*  51 */     this.hasPlaceHolders = findAndRemovePlaceHolders();
/*  52 */     this.textParser = new SimpleTextParser(sql); }
/*     */ 
/*     */ 
/*     */   
/*     */   private RawSql.Sql parse() {
/*  57 */     if (!hasPlaceHolders())
/*     */     {
/*     */       
/*  60 */       parseSqlFindKeywords(true);
/*     */     }
/*     */     
/*  63 */     this.whereExprPos = findWhereExprPosition();
/*  64 */     this.havingExprPos = findHavingExprPosition();
/*     */     
/*  66 */     String preFrom = removeWhitespace(findPreFromSql());
/*  67 */     String preWhere = removeWhitespace(findPreWhereSql());
/*  68 */     String preHaving = removeWhitespace(findPreHavingSql());
/*  69 */     String orderBySql = findOrderBySql();
/*     */     
/*  71 */     preFrom = trimSelectKeyword(preFrom);
/*     */     
/*  73 */     return new RawSql.Sql(this.sql.hashCode(), preFrom, preWhere, this.whereExprAnd, preHaving, this.havingExprAnd, orderBySql);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean findAndRemovePlaceHolders() {
/*  80 */     this.placeHolderWhere = removePlaceHolder("${where}");
/*  81 */     this.placeHolderAndWhere = removePlaceHolder("${andWhere}");
/*  82 */     this.placeHolderHaving = removePlaceHolder("${having}");
/*  83 */     this.placeHolderAndHaving = removePlaceHolder("${andHaving}");
/*  84 */     return hasPlaceHolders();
/*     */   }
/*     */   
/*     */   private int removePlaceHolder(String placeHolder) {
/*  88 */     int pos = this.sql.indexOf(placeHolder);
/*  89 */     if (pos > -1) {
/*  90 */       int after = pos + placeHolder.length() + 1;
/*  91 */       if (after > this.sql.length()) {
/*  92 */         this.sql = this.sql.substring(0, pos);
/*     */       } else {
/*  94 */         this.sql = this.sql.substring(0, pos) + this.sql.substring(after);
/*     */       } 
/*     */     } 
/*  97 */     return pos;
/*     */   }
/*     */   
/*     */   private boolean hasPlaceHolders() {
/* 101 */     if (this.placeHolderWhere > -1) {
/* 102 */       return true;
/*     */     }
/* 104 */     if (this.placeHolderAndWhere > -1) {
/* 105 */       return true;
/*     */     }
/* 107 */     if (this.placeHolderHaving > -1) {
/* 108 */       return true;
/*     */     }
/* 110 */     if (this.placeHolderAndHaving > -1) {
/* 111 */       return true;
/*     */     }
/* 113 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String trimSelectKeyword(String preWhereExprSql) {
/* 121 */     preWhereExprSql = preWhereExprSql.trim();
/*     */     
/* 123 */     if (preWhereExprSql.length() < 7) {
/* 124 */       throw new RuntimeException("Expecting at least 7 chars in [" + preWhereExprSql + "]");
/*     */     }
/*     */     
/* 127 */     String select = preWhereExprSql.substring(0, 7);
/* 128 */     if (!select.equalsIgnoreCase("select ")) {
/* 129 */       throw new RuntimeException("Expecting [" + preWhereExprSql + "] to start with \"select\"");
/*     */     }
/* 131 */     return preWhereExprSql.substring(7);
/*     */   }
/*     */ 
/*     */   
/*     */   private String findOrderBySql() {
/* 136 */     if (this.orderByPos > -1) {
/* 137 */       int pos = this.orderByPos + "order by".length();
/* 138 */       return this.sql.substring(pos).trim();
/*     */     } 
/* 140 */     return null;
/*     */   }
/*     */   
/*     */   private String findPreHavingSql() {
/* 144 */     if (this.havingExprPos > this.whereExprPos)
/*     */     {
/* 146 */       return this.sql.substring(this.whereExprPos, this.havingExprPos - 1);
/*     */     }
/* 148 */     if (this.whereExprPos > -1) {
/* 149 */       if (this.orderByPos == -1) {
/* 150 */         return this.sql.substring(this.whereExprPos);
/*     */       }
/* 152 */       if (this.whereExprPos == this.orderByPos) {
/* 153 */         return "";
/*     */       }
/*     */       
/* 156 */       return this.sql.substring(this.whereExprPos, this.orderByPos - 1);
/*     */     } 
/*     */     
/* 159 */     return null;
/*     */   }
/*     */ 
/*     */   
/* 163 */   private String findPreFromSql() { return this.sql.substring(0, this.fromPos - 1); }
/*     */ 
/*     */   
/*     */   private String findPreWhereSql() {
/* 167 */     if (this.whereExprPos > -1) {
/* 168 */       return this.sql.substring(this.fromPos, this.whereExprPos - 1);
/*     */     }
/* 170 */     return this.sql.substring(this.fromPos);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void parseSqlFindKeywords(boolean allKeywords) {
/* 176 */     this.selectPos = this.textParser.findWordLower("select");
/* 177 */     if (this.selectPos == -1) {
/* 178 */       String msg = "Error parsing sql, can not find SELECT keyword in:";
/* 179 */       throw new RuntimeException(msg + this.sql);
/*     */     } 
/*     */     
/* 182 */     this.fromPos = this.textParser.findWordLower("from");
/* 183 */     if (this.fromPos == -1) {
/* 184 */       String msg = "Error parsing sql, can not find FROM keyword in:";
/* 185 */       throw new RuntimeException(msg + this.sql);
/*     */     } 
/*     */     
/* 188 */     if (!allKeywords) {
/*     */       return;
/*     */     }
/*     */     
/* 192 */     this.wherePos = this.textParser.findWordLower("where");
/* 193 */     if (this.wherePos == -1) {
/* 194 */       this.groupByPos = this.textParser.findWordLower("group", this.fromPos + 5);
/*     */     } else {
/* 196 */       this.groupByPos = this.textParser.findWordLower("group");
/*     */     } 
/* 198 */     if (this.groupByPos > -1) {
/* 199 */       this.havingPos = this.textParser.findWordLower("having");
/*     */     }
/*     */     
/* 202 */     int startOrderBy = this.havingPos;
/* 203 */     if (startOrderBy == -1) {
/* 204 */       startOrderBy = this.groupByPos;
/*     */     }
/* 206 */     if (startOrderBy == -1) {
/* 207 */       startOrderBy = this.wherePos;
/*     */     }
/* 209 */     if (startOrderBy == -1) {
/* 210 */       startOrderBy = this.fromPos;
/*     */     }
/*     */     
/* 213 */     this.orderByPos = this.textParser.findWordLower("order", startOrderBy);
/*     */   }
/*     */   
/*     */   private int findWhereExprPosition() {
/* 217 */     if (this.hasPlaceHolders) {
/* 218 */       if (this.placeHolderWhere > -1) {
/* 219 */         return this.placeHolderWhere;
/*     */       }
/* 221 */       this.whereExprAnd = true;
/* 222 */       return this.placeHolderAndWhere;
/*     */     } 
/*     */     
/* 225 */     this.whereExprAnd = (this.wherePos > 0);
/* 226 */     if (this.groupByPos > 0) {
/* 227 */       return this.groupByPos;
/*     */     }
/* 229 */     if (this.havingPos > 0) {
/* 230 */       return this.havingPos;
/*     */     }
/* 232 */     if (this.orderByPos > 0) {
/* 233 */       return this.orderByPos;
/*     */     }
/* 235 */     return -1;
/*     */   }
/*     */   
/*     */   private int findHavingExprPosition() {
/* 239 */     if (this.hasPlaceHolders) {
/* 240 */       if (this.placeHolderHaving > -1) {
/* 241 */         return this.placeHolderHaving;
/*     */       }
/* 243 */       this.havingExprAnd = true;
/* 244 */       return this.placeHolderAndHaving;
/*     */     } 
/*     */     
/* 247 */     this.havingExprAnd = (this.havingPos > 0);
/* 248 */     if (this.orderByPos > 0) {
/* 249 */       return this.orderByPos;
/*     */     }
/* 251 */     return -1;
/*     */   }
/*     */   
/*     */   private String removeWhitespace(String sql) {
/* 255 */     if (sql == null) {
/* 256 */       return "";
/*     */     }
/*     */     
/* 259 */     boolean removeWhitespace = false;
/*     */     
/* 261 */     int length = sql.length();
/* 262 */     StringBuilder sb = new StringBuilder();
/* 263 */     for (int i = 0; i < length; i++) {
/* 264 */       char c = sql.charAt(i);
/* 265 */       if (removeWhitespace) {
/* 266 */         if (!Character.isWhitespace(c)) {
/* 267 */           sb.append(c);
/* 268 */           removeWhitespace = false;
/*     */         }
/*     */       
/* 271 */       } else if (c == '\r' || c == '\n') {
/* 272 */         sb.append('\n');
/* 273 */         removeWhitespace = true;
/*     */       } else {
/* 275 */         sb.append(c);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 280 */     String s = sb.toString();
/* 281 */     return s.trim();
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebean\DRawSqlParser.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */