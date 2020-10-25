/*    */ package com.avaje.ebeaninternal.util;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class SortByClauseParser
/*    */ {
/*    */   private final String rawSortBy;
/*    */   
/* 10 */   public static SortByClause parse(String rawSortByClause) { return (new SortByClauseParser(rawSortByClause)).parse(); }
/*    */ 
/*    */ 
/*    */   
/* 14 */   private SortByClauseParser(String rawSortByClause) { this.rawSortBy = rawSortByClause.trim(); }
/*    */ 
/*    */ 
/*    */   
/*    */   private SortByClause parse() {
/* 19 */     SortByClause sortBy = new SortByClause();
/*    */     
/* 21 */     String[] sections = this.rawSortBy.split(",");
/* 22 */     for (int i = 0; i < sections.length; i++) {
/* 23 */       SortByClause.Property p = parseSection(sections[i].trim());
/* 24 */       if (p == null) {
/*    */         break;
/*    */       }
/* 27 */       sortBy.add(p);
/*    */     } 
/*    */ 
/*    */ 
/*    */     
/* 32 */     return sortBy;
/*    */   }
/*    */   
/*    */   private SortByClause.Property parseSection(String section) {
/* 36 */     if (section.length() == 0) {
/* 37 */       return null;
/*    */     }
/* 39 */     String[] words = section.split(" ");
/* 40 */     if (words.length < 1 || words.length > 3) {
/* 41 */       throw new RuntimeException("Expecting 1 to 3 words in [" + section + "] but got [" + words.length + "]");
/*    */     }
/*    */     
/* 44 */     Boolean nullsHigh = null;
/* 45 */     boolean ascending = true;
/* 46 */     String propName = words[0];
/* 47 */     if (words.length > 1) {
/* 48 */       if (words[1].startsWith("nulls")) {
/* 49 */         nullsHigh = isNullsHigh(words[1]);
/*    */       } else {
/*    */         
/* 52 */         ascending = isAscending(words[1]);
/*    */       } 
/*    */     }
/* 55 */     if (words.length > 2) {
/* 56 */       if (words[2].startsWith("nulls")) {
/* 57 */         nullsHigh = isNullsHigh(words[2]);
/*    */       } else {
/*    */         
/* 60 */         ascending = isAscending(words[2]);
/*    */       } 
/*    */     }
/*    */     
/* 64 */     return new SortByClause.Property(propName, ascending, nullsHigh);
/*    */   }
/*    */   
/*    */   private Boolean isNullsHigh(String word) {
/* 68 */     if ("nullshigh".equalsIgnoreCase(word)) {
/* 69 */       return Boolean.TRUE;
/*    */     }
/* 71 */     if ("nullslow".equalsIgnoreCase(word)) {
/* 72 */       return Boolean.FALSE;
/*    */     }
/* 74 */     String m = "Expecting nullsHigh or nullsLow but got [" + word + "] in [" + this.rawSortBy + "]";
/* 75 */     throw new RuntimeException(m);
/*    */   }
/*    */ 
/*    */   
/*    */   private boolean isAscending(String word) {
/* 80 */     if ("asc".equalsIgnoreCase(word)) {
/* 81 */       return true;
/*    */     }
/* 83 */     if ("desc".equalsIgnoreCase(word)) {
/* 84 */       return false;
/*    */     }
/* 86 */     String m = "Expection ASC or DESC but got [" + word + "] in [" + this.rawSortBy + "]";
/* 87 */     throw new RuntimeException(m);
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninterna\\util\SortByClauseParser.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */