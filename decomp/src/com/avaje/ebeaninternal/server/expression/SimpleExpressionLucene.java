/*    */ package com.avaje.ebeaninternal.server.expression;
/*    */ 
/*    */ import com.avaje.ebeaninternal.api.SpiExpressionRequest;
/*    */ import com.avaje.ebeaninternal.api.SpiLuceneExpr;
/*    */ import com.avaje.ebeaninternal.server.deploy.BeanProperty;
/*    */ import com.avaje.ebeaninternal.server.el.ElPropertyValue;
/*    */ import com.avaje.ebeaninternal.server.type.ScalarType;
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
/*    */ 
/*    */ public class SimpleExpressionLucene
/*    */ {
/*    */   public SpiLuceneExpr addLuceneQuery(SpiExpressionRequest request, SimpleExpression.Op type, String propertyName, Object value, ElPropertyValue prop) {
/*    */     try {
/* 38 */       if (prop == null) {
/* 39 */         throw new RuntimeException("Property not found? " + propertyName);
/*    */       }
/* 41 */       BeanProperty beanProperty = prop.getBeanProperty();
/* 42 */       ScalarType<?> scalarType = beanProperty.getScalarType();
/*    */       
/* 44 */       int luceneType = scalarType.getLuceneType();
/* 45 */       if (0 == luceneType) {
/*    */         
/* 47 */         Object lucVal = (String)scalarType.luceneToIndexValue(value);
/*    */         
/* 49 */         if (SimpleExpression.Op.EQ.equals(type)) {
/* 50 */           String desc = propertyName + " = " + lucVal.toString();
/* 51 */           QueryParser queryParser = request.getLuceneIndex().createQueryParser(propertyName);
/* 52 */           return new LuceneExprResponse(queryParser.parse(lucVal.toString()), desc);
/*    */         } 
/* 54 */         if (SimpleExpression.Op.NOT_EQ.equals(type)) {
/* 55 */           String desc = propertyName + " != " + lucVal.toString();
/* 56 */           QueryParser queryParser = request.getLuceneIndex().createQueryParser(propertyName);
/* 57 */           return new LuceneExprResponse(queryParser.parse("-" + propertyName + "(" + lucVal.toString() + ")"), desc);
/*    */         } 
/* 59 */         throw new RuntimeException("String type only supports EQ and NOT_EQ - " + type);
/*    */       } 
/*    */ 
/*    */       
/* 63 */       LLuceneRangeExpression exp = new LLuceneRangeExpression(type, value, propertyName, luceneType);
/* 64 */       return new LuceneExprResponse(exp.buildQuery(), exp.getDescription());
/*    */     }
/* 66 */     catch (ParseException e) {
/* 67 */       throw new PersistenceLuceneParseException(e);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\expression\SimpleExpressionLucene.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */