/*     */ package com.avaje.ebeaninternal.server.querydefn;
/*     */ 
/*     */ import javax.persistence.PersistenceException;
/*     */ 
/*     */ 
/*     */ public class OrmQueryDetailParser
/*     */ {
/*     */   private final OrmQueryDetail detail;
/*     */   private int maxRows;
/*     */   private int firstRow;
/*     */   private String rawWhereClause;
/*     */   private String rawOrderBy;
/*     */   private final SimpleTextParser parser;
/*     */   
/*     */   public OrmQueryDetailParser(String oql) {
/*  16 */     this.detail = new OrmQueryDetail();
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
/*  29 */     this.parser = new SimpleTextParser(oql);
/*     */   }
/*     */ 
/*     */   
/*     */   public void parse() throws PersistenceException {
/*  34 */     this.parser.nextWord();
/*  35 */     processInitial();
/*     */   }
/*     */   
/*     */   protected void assign(DefaultOrmQuery<?> query) {
/*  39 */     query.setOrmQueryDetail(this.detail);
/*  40 */     query.setFirstRow(this.firstRow);
/*  41 */     query.setMaxRows(this.maxRows);
/*  42 */     query.setRawWhereClause(this.rawWhereClause);
/*  43 */     query.order(this.rawOrderBy);
/*     */   }
/*     */   
/*     */   private void processInitial() throws PersistenceException {
/*  47 */     if (this.parser.isMatch("find")) {
/*  48 */       OrmQueryProperties props = readFindFetch();
/*  49 */       this.detail.setBase(props);
/*     */     } else {
/*  51 */       process();
/*     */     } 
/*  53 */     while (!this.parser.isFinished()) {
/*  54 */       process();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*  59 */   private boolean isFetch() { return (this.parser.isMatch("fetch") || this.parser.isMatch("join")); }
/*     */ 
/*     */   
/*     */   private void process() throws PersistenceException {
/*  63 */     if (isFetch()) {
/*  64 */       OrmQueryProperties props = readFindFetch();
/*  65 */       this.detail.putFetchPath(props);
/*     */     }
/*  67 */     else if (this.parser.isMatch("where")) {
/*  68 */       readWhere();
/*     */     }
/*  70 */     else if (this.parser.isMatch("order", "by")) {
/*  71 */       readOrderBy();
/*     */     }
/*  73 */     else if (this.parser.isMatch("limit")) {
/*  74 */       readLimit();
/*     */     } else {
/*     */       
/*  77 */       throw new PersistenceException("Query expected 'fetch', 'where','order by' or 'limit' keyword but got [" + this.parser.getWord() + "] \r " + this.parser.getOql());
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void readLimit() throws PersistenceException {
/*     */     try {
/*  84 */       String maxLimit = this.parser.nextWord();
/*  85 */       this.maxRows = Integer.parseInt(maxLimit);
/*     */       
/*  87 */       String offsetKeyword = this.parser.nextWord();
/*  88 */       if (offsetKeyword != null) {
/*  89 */         if (!this.parser.isMatch("offset")) {
/*  90 */           throw new PersistenceException("expected offset keyword but got " + this.parser.getWord());
/*     */         }
/*  92 */         String firstRowLimit = this.parser.nextWord();
/*  93 */         this.firstRow = Integer.parseInt(firstRowLimit);
/*  94 */         this.parser.nextWord();
/*     */       } 
/*  96 */     } catch (NumberFormatException e) {
/*  97 */       String msg = "Expected an integer for maxRows or firstRows in limit offset clause";
/*  98 */       throw new PersistenceException(msg, e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void readOrderBy() throws PersistenceException {
/* 104 */     this.parser.nextWord();
/*     */     
/* 106 */     StringBuilder sb = new StringBuilder();
/* 107 */     while (this.parser.nextWord() != null && 
/* 108 */       !this.parser.isMatch("limit")) {
/*     */ 
/*     */       
/* 111 */       String w = this.parser.getWord();
/* 112 */       if (!w.startsWith("(")) {
/* 113 */         sb.append(" ");
/*     */       }
/* 115 */       sb.append(w);
/*     */     } 
/*     */     
/* 118 */     this.rawOrderBy = sb.toString().trim();
/*     */     
/* 120 */     if (!this.parser.isFinished()) {
/* 121 */       readLimit();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private void readWhere() throws PersistenceException {
/* 127 */     int nextMode = 0;
/* 128 */     StringBuilder sb = new StringBuilder();
/* 129 */     while (this.parser.nextWord() != null) {
/* 130 */       if (this.parser.isMatch("order", "by")) {
/* 131 */         nextMode = 1;
/*     */         break;
/*     */       } 
/* 134 */       if (this.parser.isMatch("limit")) {
/* 135 */         nextMode = 2;
/*     */         
/*     */         break;
/*     */       } 
/* 139 */       sb.append(" ").append(this.parser.getWord());
/*     */     } 
/*     */     
/* 142 */     String whereClause = sb.toString().trim();
/* 143 */     if (whereClause.length() > 0) {
/* 144 */       this.rawWhereClause = whereClause;
/*     */     }
/*     */     
/* 147 */     if (nextMode == 1) {
/* 148 */       readOrderBy();
/* 149 */     } else if (nextMode == 2) {
/* 150 */       readLimit();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private OrmQueryProperties readFindFetch() {
/* 156 */     boolean readAlias = false;
/*     */     
/* 158 */     String props = null;
/* 159 */     String path = this.parser.nextWord();
/* 160 */     String token = null;
/* 161 */     while ((token = this.parser.nextWord()) != null) {
/* 162 */       if (!readAlias && this.parser.isMatch("as")) {
/*     */         
/* 164 */         this.parser.nextWord();
/* 165 */         readAlias = true; continue;
/*     */       } 
/* 167 */       if ('(' == token.charAt(0)) {
/* 168 */         props = token;
/* 169 */         this.parser.nextWord();
/*     */         break;
/*     */       } 
/* 172 */       if (isFindFetchEnd()) {
/*     */         break;
/*     */       }
/* 175 */       if (!readAlias) {
/* 176 */         readAlias = true;
/*     */         continue;
/*     */       } 
/* 179 */       throw new PersistenceException("Expected (props) or new 'fetch' 'where' but got " + token);
/*     */     } 
/*     */     
/* 182 */     if (props != null) {
/* 183 */       props = props.substring(1, props.length() - 1);
/*     */     }
/* 185 */     return new OrmQueryProperties(path, props);
/*     */   }
/*     */   
/*     */   private boolean isFindFetchEnd() {
/* 189 */     if (isFetch()) {
/* 190 */       return true;
/*     */     }
/* 192 */     if (this.parser.isMatch("where")) {
/* 193 */       return true;
/*     */     }
/* 195 */     if (this.parser.isMatch("order", "by")) {
/* 196 */       return true;
/*     */     }
/* 198 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\querydefn\OrmQueryDetailParser.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */