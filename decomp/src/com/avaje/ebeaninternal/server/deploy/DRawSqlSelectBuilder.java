/*     */ package com.avaje.ebeaninternal.server.deploy;
/*     */ 
/*     */ import com.avaje.ebean.config.NamingConvention;
/*     */ import com.avaje.ebeaninternal.server.querydefn.SimpleTextParser;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DRawSqlSelectBuilder
/*     */ {
/*     */   public static final String $_AND_HAVING = "${andHaving}";
/*     */   public static final String $_HAVING = "${having}";
/*     */   public static final String $_AND_WHERE = "${andWhere}";
/*     */   public static final String $_WHERE = "${where}";
/*     */   private static final String ORDER_BY = "order by";
/*     */   private final BeanDescriptor<?> desc;
/*     */   private final NamingConvention namingConvention;
/*     */   private final DRawSqlMeta meta;
/*     */   private final boolean debug;
/*     */   private String sql;
/*     */   private final SimpleTextParser textParser;
/*     */   private List<DRawSqlColumnInfo> selectColumns;
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
/*     */   private String tableAlias;
/*     */   
/*     */   public DRawSqlSelectBuilder(NamingConvention namingConvention, BeanDescriptor<?> desc, DRawSqlMeta sqlSelectMeta) {
/*  44 */     this.selectPos = -1;
/*  45 */     this.fromPos = -1;
/*  46 */     this.wherePos = -1;
/*  47 */     this.groupByPos = -1;
/*  48 */     this.havingPos = -1;
/*  49 */     this.orderByPos = -1;
/*     */ 
/*     */     
/*  52 */     this.whereExprPos = -1;
/*     */     
/*  54 */     this.havingExprPos = -1;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  60 */     this.namingConvention = namingConvention;
/*  61 */     this.desc = desc;
/*  62 */     this.tableAlias = sqlSelectMeta.getTableAlias();
/*  63 */     this.meta = sqlSelectMeta;
/*  64 */     this.debug = sqlSelectMeta.isDebug();
/*  65 */     this.sql = sqlSelectMeta.getQuery().trim();
/*  66 */     this.hasPlaceHolders = findAndRemovePlaceHolders();
/*  67 */     this.textParser = new SimpleTextParser(this.sql);
/*     */   }
/*     */ 
/*     */   
/*  71 */   protected NamingConvention getNamingConvention() { return this.namingConvention; }
/*     */ 
/*     */ 
/*     */   
/*  75 */   protected BeanDescriptor<?> getBeanDescriptor() { return this.desc; }
/*     */ 
/*     */ 
/*     */   
/*  79 */   protected boolean isDebug() { return this.debug; }
/*     */ 
/*     */   
/*     */   protected void debug(String msg) {
/*  83 */     if (this.debug) {
/*  84 */       System.out.println("debug> " + msg);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public DeployNamedQuery parse() {
/*  90 */     if (this.debug) {
/*  91 */       debug("");
/*  92 */       debug("Parsing sql-select in " + getErrName());
/*     */     } 
/*     */     
/*  95 */     if (!hasPlaceHolders())
/*     */     {
/*     */ 
/*     */ 
/*     */       
/* 100 */       parseSqlFindKeywords(true);
/*     */     }
/*     */     
/* 103 */     this.selectColumns = findSelectColumns(this.meta.getColumnMapping());
/* 104 */     this.whereExprPos = findWhereExprPosition();
/* 105 */     this.havingExprPos = findHavingExprPosition();
/*     */     
/* 107 */     String preWhereExprSql = removeWhitespace(findPreWhereExprSql());
/* 108 */     String preHavingExprSql = removeWhitespace(findPreHavingExprSql());
/*     */     
/* 110 */     preWhereExprSql = trimSelectKeyword(preWhereExprSql);
/*     */     
/* 112 */     String orderBySql = findOrderBySql();
/*     */     
/* 114 */     DRawSqlSelect rawSqlSelect = new DRawSqlSelect(this.desc, this.selectColumns, this.tableAlias, preWhereExprSql, this.whereExprAnd, preHavingExprSql, this.havingExprAnd, orderBySql, this.meta);
/*     */ 
/*     */     
/* 117 */     return new DeployNamedQuery(rawSqlSelect);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean findAndRemovePlaceHolders() {
/* 124 */     this.placeHolderWhere = removePlaceHolder("${where}");
/* 125 */     this.placeHolderAndWhere = removePlaceHolder("${andWhere}");
/* 126 */     this.placeHolderHaving = removePlaceHolder("${having}");
/* 127 */     this.placeHolderAndHaving = removePlaceHolder("${andHaving}");
/* 128 */     return hasPlaceHolders();
/*     */   }
/*     */   
/*     */   private int removePlaceHolder(String placeHolder) {
/* 132 */     int pos = this.sql.indexOf(placeHolder);
/* 133 */     if (pos > -1) {
/* 134 */       int after = pos + placeHolder.length() + 1;
/* 135 */       if (after > this.sql.length()) {
/* 136 */         this.sql = this.sql.substring(0, pos);
/*     */       } else {
/* 138 */         this.sql = this.sql.substring(0, pos) + this.sql.substring(after);
/*     */       } 
/*     */     } 
/* 141 */     return pos;
/*     */   }
/*     */   
/*     */   private boolean hasPlaceHolders() {
/* 145 */     if (this.placeHolderWhere > -1) {
/* 146 */       return true;
/*     */     }
/* 148 */     if (this.placeHolderAndWhere > -1) {
/* 149 */       return true;
/*     */     }
/* 151 */     if (this.placeHolderHaving > -1) {
/* 152 */       return true;
/*     */     }
/* 154 */     if (this.placeHolderAndHaving > -1) {
/* 155 */       return true;
/*     */     }
/* 157 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String trimSelectKeyword(String preWhereExprSql) {
/* 165 */     if (preWhereExprSql.length() < 7) {
/* 166 */       throw new RuntimeException("Expecting at least 7 chars in [" + preWhereExprSql + "]");
/*     */     }
/*     */     
/* 169 */     String select = preWhereExprSql.substring(0, 7);
/* 170 */     if (!select.equalsIgnoreCase("select ")) {
/* 171 */       throw new RuntimeException("Expecting [" + preWhereExprSql + "] to start with \"select\"");
/*     */     }
/* 173 */     return preWhereExprSql.substring(7);
/*     */   }
/*     */ 
/*     */   
/*     */   private String findOrderBySql() {
/* 178 */     if (this.orderByPos > -1) {
/* 179 */       int pos = this.orderByPos + "order by".length();
/* 180 */       return this.sql.substring(pos);
/*     */     } 
/* 182 */     return null;
/*     */   }
/*     */   
/*     */   private String findPreHavingExprSql() {
/* 186 */     if (this.havingExprPos > this.whereExprPos)
/*     */     {
/* 188 */       return this.sql.substring(this.whereExprPos, this.havingExprPos - 1);
/*     */     }
/* 190 */     if (this.whereExprPos > -1)
/*     */     {
/* 192 */       return this.sql.substring(this.whereExprPos);
/*     */     }
/* 194 */     return null;
/*     */   }
/*     */   
/*     */   private String findPreWhereExprSql() {
/* 198 */     if (this.whereExprPos > -1) {
/* 199 */       return this.sql.substring(0, this.whereExprPos - 1);
/*     */     }
/* 201 */     return this.sql;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 206 */   protected String getErrName() { return "entity[" + this.desc.getFullName() + "] query[" + this.meta.getName() + "]"; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private List<DRawSqlColumnInfo> findSelectColumns(String selectClause) {
/* 215 */     if (selectClause == null || selectClause.trim().length() == 0) {
/* 216 */       if (this.hasPlaceHolders) {
/* 217 */         if (this.debug) {
/* 218 */           debug("... No explicit ColumnMapping, so parse the sql looking for SELECT and FROM keywords.");
/*     */         }
/* 220 */         parseSqlFindKeywords(false);
/*     */       } 
/* 222 */       if (this.selectPos == -1 || this.fromPos == -1) {
/* 223 */         String msg = "Error in [" + getErrName() + "] parsing sql looking ";
/* 224 */         msg = msg + "for SELECT and FROM keywords.";
/* 225 */         msg = msg + " select:" + this.selectPos + " from:" + this.fromPos;
/* 226 */         msg = msg + ".  You could use an explicit columnMapping to bypass this error.";
/* 227 */         throw new RuntimeException(msg);
/*     */       } 
/* 229 */       this.selectPos += "select".length();
/* 230 */       selectClause = this.sql.substring(this.selectPos, this.fromPos);
/*     */     } 
/*     */     
/* 233 */     selectClause = selectClause.trim();
/* 234 */     if (this.debug) {
/* 235 */       debug("ColumnMapping ... [" + selectClause + "]");
/*     */     }
/*     */     
/* 238 */     return (new DRawSqlSelectColumnsParser(this, selectClause)).parse();
/*     */   }
/*     */ 
/*     */   
/*     */   private void parseSqlFindKeywords(boolean allKeywords) {
/* 243 */     debug("Parsing query looking for SELECT...");
/* 244 */     this.selectPos = this.textParser.findWordLower("select");
/* 245 */     if (this.selectPos == -1) {
/* 246 */       String msg = "Error in " + getErrName() + " parsing sql, can not find SELECT keyword in:";
/* 247 */       throw new RuntimeException(msg + this.sql);
/*     */     } 
/* 249 */     debug("Parsing query looking for FROM... SELECT found at " + this.selectPos);
/* 250 */     this.fromPos = this.textParser.findWordLower("from");
/* 251 */     if (this.fromPos == -1) {
/* 252 */       String msg = "Error in " + getErrName() + " parsing sql, can not find FROM keyword in:";
/* 253 */       throw new RuntimeException(msg + this.sql);
/*     */     } 
/*     */     
/* 256 */     if (!allKeywords) {
/*     */       return;
/*     */     }
/*     */     
/* 260 */     debug("Parsing query looking for WHERE... FROM found at " + this.fromPos);
/* 261 */     this.wherePos = this.textParser.findWordLower("where");
/* 262 */     if (this.wherePos == -1) {
/* 263 */       debug("Parsing query looking for GROUP... no WHERE found");
/* 264 */       this.groupByPos = this.textParser.findWordLower("group", this.fromPos + 5);
/*     */     } else {
/* 266 */       debug("Parsing query looking for GROUP... WHERE found at " + this.wherePos);
/* 267 */       this.groupByPos = this.textParser.findWordLower("group");
/*     */     } 
/* 269 */     if (this.groupByPos > -1) {
/* 270 */       debug("Parsing query looking for HAVING... GROUP found at " + this.groupByPos);
/* 271 */       this.havingPos = this.textParser.findWordLower("having");
/*     */     } 
/*     */     
/* 274 */     int startOrderBy = this.havingPos;
/* 275 */     if (startOrderBy == -1) {
/* 276 */       startOrderBy = this.groupByPos;
/*     */     }
/* 278 */     if (startOrderBy == -1) {
/* 279 */       startOrderBy = this.wherePos;
/*     */     }
/* 281 */     if (startOrderBy == -1) {
/* 282 */       startOrderBy = this.fromPos;
/*     */     }
/*     */     
/* 285 */     debug("Parsing query looking for ORDER... starting at " + startOrderBy);
/* 286 */     this.orderByPos = this.textParser.findWordLower("order", startOrderBy);
/*     */   }
/*     */   
/*     */   private int findWhereExprPosition() {
/* 290 */     if (this.hasPlaceHolders) {
/* 291 */       if (this.placeHolderWhere > -1) {
/* 292 */         return this.placeHolderWhere;
/*     */       }
/* 294 */       this.whereExprAnd = true;
/* 295 */       return this.placeHolderAndWhere;
/*     */     } 
/*     */     
/* 298 */     this.whereExprAnd = (this.wherePos > 0);
/* 299 */     if (this.groupByPos > 0) {
/* 300 */       return this.groupByPos;
/*     */     }
/* 302 */     if (this.havingPos > 0) {
/* 303 */       return this.havingPos;
/*     */     }
/* 305 */     if (this.orderByPos > 0) {
/* 306 */       return this.orderByPos;
/*     */     }
/* 308 */     return -1;
/*     */   }
/*     */   
/*     */   private int findHavingExprPosition() {
/* 312 */     if (this.hasPlaceHolders) {
/* 313 */       if (this.placeHolderHaving > -1) {
/* 314 */         return this.placeHolderHaving;
/*     */       }
/* 316 */       this.havingExprAnd = true;
/* 317 */       return this.placeHolderAndHaving;
/*     */     } 
/*     */     
/* 320 */     this.havingExprAnd = (this.havingPos > 0);
/* 321 */     if (this.orderByPos > 0) {
/* 322 */       return this.orderByPos;
/*     */     }
/* 324 */     return -1;
/*     */   }
/*     */   
/*     */   private String removeWhitespace(String sql) {
/* 328 */     if (sql == null) {
/* 329 */       return "";
/*     */     }
/*     */     
/* 332 */     boolean removeWhitespace = false;
/*     */     
/* 334 */     int length = sql.length();
/* 335 */     StringBuilder sb = new StringBuilder();
/* 336 */     for (int i = 0; i < length; i++) {
/* 337 */       char c = sql.charAt(i);
/* 338 */       if (removeWhitespace) {
/* 339 */         if (!Character.isWhitespace(c)) {
/* 340 */           sb.append(c);
/* 341 */           removeWhitespace = false;
/*     */         }
/*     */       
/* 344 */       } else if (c == '\r' || c == '\n') {
/* 345 */         sb.append('\n');
/* 346 */         removeWhitespace = true;
/*     */       } else {
/* 348 */         sb.append(c);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 353 */     return sb.toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\deploy\DRawSqlSelectBuilder.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */