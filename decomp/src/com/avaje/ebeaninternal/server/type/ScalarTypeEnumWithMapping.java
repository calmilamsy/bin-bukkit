/*     */ package com.avaje.ebeaninternal.server.type;
/*     */ 
/*     */ import java.sql.SQLException;
/*     */ import java.util.Iterator;
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
/*     */ public class ScalarTypeEnumWithMapping
/*     */   extends ScalarTypeEnumStandard.EnumBase
/*     */   implements ScalarType, ScalarTypeEnum
/*     */ {
/*     */   private final EnumToDbValueMap beanDbMap;
/*     */   private final int length;
/*     */   
/*     */   public ScalarTypeEnumWithMapping(EnumToDbValueMap<?> beanDbMap, Class<?> enumType, int length) {
/*  41 */     super(enumType, false, beanDbMap.getDbType());
/*  42 */     this.beanDbMap = beanDbMap;
/*  43 */     this.length = length;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getContraintInValues() {
/*  52 */     StringBuilder sb = new StringBuilder();
/*     */     
/*  54 */     int i = 0;
/*     */     
/*  56 */     sb.append("(");
/*     */     
/*  58 */     Iterator<?> it = this.beanDbMap.dbValues();
/*  59 */     while (it.hasNext()) {
/*  60 */       Object dbValue = it.next();
/*  61 */       if (i++ > 0) {
/*  62 */         sb.append(",");
/*     */       }
/*  64 */       if (!this.beanDbMap.isIntegerType()) {
/*  65 */         sb.append("'");
/*     */       }
/*  67 */       sb.append(dbValue.toString());
/*  68 */       if (!this.beanDbMap.isIntegerType()) {
/*  69 */         sb.append("'");
/*     */       }
/*     */     } 
/*     */     
/*  73 */     sb.append(")");
/*     */     
/*  75 */     return sb.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  85 */   public int getLength() { return this.length; }
/*     */ 
/*     */ 
/*     */   
/*  89 */   public void bind(DataBind b, Object value) throws SQLException { this.beanDbMap.bind(b, value); }
/*     */ 
/*     */   
/*     */   public Object read(DataReader dataReader) throws SQLException {
/*  93 */     if (dataReader instanceof com.avaje.ebeaninternal.server.query.LuceneIndexDataReader) {
/*     */ 
/*     */       
/*  96 */       String s = dataReader.getString();
/*  97 */       return (s == null) ? null : parse(s);
/*     */     } 
/*  99 */     return this.beanDbMap.read(dataReader);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 104 */   public Object toBeanType(Object dbValue) { return this.beanDbMap.getBeanValue(dbValue); }
/*     */ 
/*     */ 
/*     */   
/* 108 */   public Object toJdbcType(Object beanValue) { return this.beanDbMap.getDbValue(beanValue); }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\type\ScalarTypeEnumWithMapping.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */