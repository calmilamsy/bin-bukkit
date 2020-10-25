/*    */ package com.avaje.ebean;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.Arrays;
/*    */ import javax.persistence.PersistenceException;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ final class DRawSqlColumnsParser
/*    */ {
/*    */   private final int end;
/*    */   private final String sqlSelect;
/*    */   private int pos;
/*    */   private int indexPos;
/*    */   
/* 25 */   public static RawSql.ColumnMapping parse(String sqlSelect) { return (new DRawSqlColumnsParser(sqlSelect)).parse(); }
/*    */ 
/*    */   
/*    */   private DRawSqlColumnsParser(String sqlSelect) {
/* 29 */     this.sqlSelect = sqlSelect;
/* 30 */     this.end = sqlSelect.length();
/*    */   }
/*    */ 
/*    */   
/*    */   private RawSql.ColumnMapping parse() {
/* 35 */     ArrayList<RawSql.ColumnMapping.Column> columns = new ArrayList<RawSql.ColumnMapping.Column>();
/* 36 */     while (this.pos <= this.end) {
/* 37 */       RawSql.ColumnMapping.Column c = nextColumnInfo();
/* 38 */       columns.add(c);
/*    */     } 
/*    */     
/* 41 */     return new RawSql.ColumnMapping(columns);
/*    */   }
/*    */   
/*    */   private RawSql.ColumnMapping.Column nextColumnInfo() {
/* 45 */     int start = this.pos;
/* 46 */     nextComma();
/* 47 */     String colInfo = this.sqlSelect.substring(start, this.pos++);
/* 48 */     colInfo = colInfo.trim();
/*    */     
/* 50 */     String[] split = colInfo.split(" ");
/* 51 */     if (split.length > 1) {
/* 52 */       ArrayList<String> tmp = new ArrayList<String>(split.length);
/* 53 */       for (int i = 0; i < split.length; i++) {
/* 54 */         if (split[i].trim().length() > 0) {
/* 55 */           tmp.add(split[i].trim());
/*    */         }
/*    */       } 
/* 58 */       split = (String[])tmp.toArray(new String[tmp.size()]);
/*    */     } 
/*    */     
/* 61 */     if (split.length == 1) {
/* 62 */       return new RawSql.ColumnMapping.Column(this.indexPos++, split[0], null);
/*    */     }
/* 64 */     if (split.length == 2) {
/* 65 */       return new RawSql.ColumnMapping.Column(this.indexPos++, split[0], split[1]);
/*    */     }
/* 67 */     if (split.length == 3) {
/* 68 */       if (!split[1].equalsIgnoreCase("as")) {
/* 69 */         String msg = "Expecting AS keyword parsing column " + colInfo;
/* 70 */         throw new PersistenceException(msg);
/*    */       } 
/* 72 */       return new RawSql.ColumnMapping.Column(this.indexPos++, split[0], split[2]);
/*    */     } 
/*    */     
/* 75 */     String msg = "Expecting Max 3 words parsing column " + colInfo + ". Got " + Arrays.toString(split);
/* 76 */     throw new PersistenceException(msg);
/*    */   }
/*    */ 
/*    */   
/*    */   private int nextComma() {
/* 81 */     boolean inQuote = false;
/* 82 */     while (this.pos < this.end) {
/* 83 */       char c = this.sqlSelect.charAt(this.pos);
/* 84 */       if (c == '\'') {
/* 85 */         inQuote = !inQuote;
/* 86 */       } else if (!inQuote && c == ',') {
/* 87 */         return this.pos;
/*    */       } 
/* 89 */       this.pos++;
/*    */     } 
/* 91 */     return this.pos;
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebean\DRawSqlColumnsParser.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */