/*     */ package com.avaje.ebeaninternal.server.type;
/*     */ 
/*     */ import com.avaje.ebean.text.json.JsonValueAdapter;
/*     */ import com.avaje.ebeaninternal.server.core.BasicTypeConverter;
/*     */ import java.sql.SQLException;
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
/*     */ public class ScalarTypeClob
/*     */   extends ScalarTypeBaseVarchar<String>
/*     */ {
/*     */   static final int clobBufferSize = 512;
/*     */   static final int stringInitialSize = 512;
/*     */   
/*  39 */   protected ScalarTypeClob(boolean jdbcNative, int jdbcType) { super(String.class, jdbcNative, jdbcType); }
/*     */ 
/*     */ 
/*     */   
/*  43 */   public ScalarTypeClob() { super(String.class, true, 2005); }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  48 */   public String convertFromDbString(String dbValue) { return dbValue; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  53 */   public String convertToDbString(String beanValue) { return beanValue; }
/*     */ 
/*     */   
/*     */   public void bind(DataBind b, String value) throws SQLException {
/*  57 */     if (value == null) {
/*  58 */       b.setNull(12);
/*     */     } else {
/*  60 */       b.setString(value);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*  66 */   public String read(DataReader dataReader) throws SQLException { return dataReader.getStringClob(); }
/*     */ 
/*     */ 
/*     */   
/*  70 */   public Object toJdbcType(Object value) { return BasicTypeConverter.toString(value); }
/*     */ 
/*     */ 
/*     */   
/*  74 */   public String toBeanType(Object value) { return BasicTypeConverter.toString(value); }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  79 */   public String formatValue(String t) { return t; }
/*     */ 
/*     */ 
/*     */   
/*  83 */   public String parse(String value) { return value; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  88 */   public String jsonFromString(String value, JsonValueAdapter ctx) { return value; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  93 */   public String jsonToString(String value, JsonValueAdapter ctx) { return EscapeJson.escapeQuote(value); }
/*     */ 
/*     */ 
/*     */   
/*  97 */   public int getLuceneType() { return 0; }
/*     */ 
/*     */ 
/*     */   
/* 101 */   public Object luceneFromIndexValue(Object value) { return value; }
/*     */ 
/*     */ 
/*     */   
/* 105 */   public Object luceneToIndexValue(Object value) { return value; }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\type\ScalarTypeClob.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */