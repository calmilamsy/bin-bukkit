/*    */ package com.avaje.ebeaninternal.server.expression;
/*    */ 
/*    */ import com.avaje.ebean.LikeType;
/*    */ import com.avaje.ebeaninternal.api.SpiExpressionRequest;
/*    */ import com.avaje.ebeaninternal.api.SpiLuceneExpr;
/*    */ import org.apache.lucene.queryParser.ParseException;
/*    */ import org.apache.lucene.queryParser.QueryParser;
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
/*    */ public class LikeExpressionLucene
/*    */ {
/*    */   public SpiLuceneExpr createLuceneExpr(SpiExpressionRequest request, String propertyName, LikeType type, boolean caseInsensitive, String val) {
/*    */     try {
/* 35 */       String exprValue = getLuceneValue(val, caseInsensitive, type);
/*    */       
/* 37 */       String desc = propertyName + " like " + exprValue;
/*    */       
/* 39 */       QueryParser queryParser = request.getLuceneIndex().createQueryParser(propertyName);
/* 40 */       return new LuceneExprResponse(queryParser.parse(exprValue), desc);
/* 41 */     } catch (ParseException e) {
/* 42 */       throw new PersistenceLuceneParseException(e);
/*    */     } 
/*    */   }
/*    */   
/*    */   private static String getLuceneValue(String value, boolean caseInsensitive, LikeType type) {
/* 47 */     if (caseInsensitive) {
/* 48 */       value = value.toLowerCase();
/*    */     }
/* 50 */     value = value.replace('%', '*');
/*    */     
/* 52 */     switch (type) {
/*    */       case RAW:
/* 54 */         return value;
/*    */       case STARTS_WITH:
/* 56 */         return value + "*";
/*    */       case CONTAINS:
/* 58 */         return value;
/*    */       case EQUAL_TO:
/* 60 */         return value;
/*    */       case ENDS_WITH:
/* 62 */         throw new RuntimeException("Not Supported - Never get here");
/*    */     } 
/*    */     
/* 65 */     throw new RuntimeException("LikeType " + type + " missed?");
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\expression\LikeExpressionLucene.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */