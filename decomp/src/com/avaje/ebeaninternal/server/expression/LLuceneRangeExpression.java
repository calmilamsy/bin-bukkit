/*     */ package com.avaje.ebeaninternal.server.expression;
/*     */ 
/*     */ import com.avaje.ebeaninternal.server.core.BasicTypeConverter;
/*     */ import org.apache.lucene.search.NumericRangeQuery;
/*     */ import org.apache.lucene.search.Query;
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
/*     */ public class LLuceneRangeExpression
/*     */ {
/*     */   private final SimpleExpression.Op op;
/*     */   private final Object value;
/*     */   private final String propertyName;
/*     */   private final int luceneType;
/*     */   String description;
/*     */   boolean minInclusive;
/*     */   boolean maxInclusive;
/*     */   
/*     */   public LLuceneRangeExpression(SimpleExpression.Op op, Object value, String propertyName, int luceneType) {
/*  45 */     this.op = op;
/*  46 */     this.value = value;
/*  47 */     this.propertyName = propertyName;
/*  48 */     this.luceneType = luceneType;
/*     */     
/*  50 */     this.minInclusive = (SimpleExpression.Op.EQ.equals(op) || SimpleExpression.Op.GT_EQ.equals(op));
/*  51 */     this.maxInclusive = (SimpleExpression.Op.EQ.equals(op) || SimpleExpression.Op.LT_EQ.equals(op));
/*     */     
/*  53 */     this.description = propertyName + op.shortDesc() + value;
/*     */   }
/*     */ 
/*     */   
/*  57 */   public String getDescription() { return this.description; }
/*     */ 
/*     */ 
/*     */   
/*     */   public Query buildQuery() {
/*  62 */     switch (this.luceneType) {
/*     */       case 1:
/*  64 */         return createIntRange();
/*     */       case 2:
/*  66 */         return createLongRange();
/*     */       case 3:
/*  68 */         return createDoubleRange();
/*     */       case 4:
/*  70 */         return createFloatRange();
/*     */       
/*     */       case 5:
/*  73 */         return createLongRange();
/*     */       case 6:
/*  75 */         return createLongRange();
/*     */     } 
/*     */     
/*  78 */     throw new RuntimeException("Unhandled type " + this.luceneType);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private Query createIntRange() {
/*  84 */     Integer intVal = BasicTypeConverter.toInteger(this.value);
/*     */     
/*  86 */     Integer min = intVal;
/*  87 */     Integer max = intVal;
/*     */     
/*  89 */     if (!SimpleExpression.Op.EQ.equals(this.op))
/*     */     {
/*  91 */       if (SimpleExpression.Op.GT.equals(this.op) || SimpleExpression.Op.GT_EQ.equals(this.op)) {
/*  92 */         max = Integer.valueOf(2147483647);
/*  93 */       } else if (SimpleExpression.Op.LT.equals(this.op) || SimpleExpression.Op.LT_EQ.equals(this.op)) {
/*  94 */         min = Integer.valueOf(-2147483648);
/*     */       } 
/*     */     }
/*  97 */     return NumericRangeQuery.newIntRange(this.propertyName, min, max, this.minInclusive, this.maxInclusive);
/*     */   }
/*     */ 
/*     */   
/*     */   private Query createLongRange() {
/* 102 */     Long longVal = BasicTypeConverter.toLong(this.value);
/* 103 */     Long min = longVal;
/* 104 */     Long max = longVal;
/*     */     
/* 106 */     if (!SimpleExpression.Op.EQ.equals(this.op))
/*     */     {
/* 108 */       if (SimpleExpression.Op.GT.equals(this.op) || SimpleExpression.Op.GT_EQ.equals(this.op)) {
/* 109 */         max = Long.valueOf(Float.MAX_VALUE);
/* 110 */       } else if (SimpleExpression.Op.LT.equals(this.op) || SimpleExpression.Op.LT_EQ.equals(this.op)) {
/* 111 */         min = Long.valueOf(Float.MIN_VALUE);
/*     */       } 
/*     */     }
/* 114 */     return NumericRangeQuery.newLongRange(this.propertyName, min, max, this.minInclusive, this.maxInclusive);
/*     */   }
/*     */ 
/*     */   
/*     */   private Query createFloatRange() {
/* 119 */     Float floatVal = BasicTypeConverter.toFloat(this.value);
/* 120 */     Float min = floatVal;
/* 121 */     Float max = floatVal;
/*     */     
/* 123 */     if (!SimpleExpression.Op.EQ.equals(this.op))
/*     */     {
/* 125 */       if (SimpleExpression.Op.GT.equals(this.op) || SimpleExpression.Op.GT_EQ.equals(this.op)) {
/* 126 */         max = Float.valueOf(Float.MAX_VALUE);
/* 127 */       } else if (SimpleExpression.Op.LT.equals(this.op) || SimpleExpression.Op.LT_EQ.equals(this.op)) {
/* 128 */         min = Float.valueOf(Float.MIN_VALUE);
/*     */       } 
/*     */     }
/* 131 */     return NumericRangeQuery.newFloatRange(this.propertyName, min, max, this.minInclusive, this.maxInclusive);
/*     */   }
/*     */ 
/*     */   
/*     */   private Query createDoubleRange() {
/* 136 */     Double doubleVal = BasicTypeConverter.toDouble(this.value);
/* 137 */     Double min = doubleVal;
/* 138 */     Double max = doubleVal;
/*     */     
/* 140 */     if (!SimpleExpression.Op.EQ.equals(this.op))
/*     */     {
/* 142 */       if (SimpleExpression.Op.GT.equals(this.op) || SimpleExpression.Op.GT_EQ.equals(this.op)) {
/* 143 */         max = Double.valueOf(Double.MAX_VALUE);
/* 144 */       } else if (SimpleExpression.Op.LT.equals(this.op) || SimpleExpression.Op.LT_EQ.equals(this.op)) {
/* 145 */         min = Double.valueOf(Double.MIN_VALUE);
/*     */       } 
/*     */     }
/* 148 */     return NumericRangeQuery.newDoubleRange(this.propertyName, min, max, this.minInclusive, this.maxInclusive);
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\expression\LLuceneRangeExpression.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */