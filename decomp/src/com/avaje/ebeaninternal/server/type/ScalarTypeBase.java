/*    */ package com.avaje.ebeaninternal.server.type;
/*    */ 
/*    */ import com.avaje.ebean.text.json.JsonValueAdapter;
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
/*    */ public abstract class ScalarTypeBase<T>
/*    */   extends Object
/*    */   implements ScalarType<T>
/*    */ {
/*    */   protected final Class<T> type;
/*    */   protected final boolean jdbcNative;
/*    */   protected final int jdbcType;
/*    */   
/*    */   public ScalarTypeBase(Class<T> type, boolean jdbcNative, int jdbcType) {
/* 36 */     this.type = type;
/* 37 */     this.jdbcNative = jdbcNative;
/* 38 */     this.jdbcType = jdbcType;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 45 */   public int getLength() { return 0; }
/*    */ 
/*    */ 
/*    */   
/* 49 */   public boolean isJdbcNative() { return this.jdbcNative; }
/*    */ 
/*    */ 
/*    */   
/* 53 */   public int getJdbcType() { return this.jdbcType; }
/*    */ 
/*    */ 
/*    */   
/* 57 */   public Class<T> getType() { return this.type; }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 62 */   public String format(Object v) { return formatValue(v); }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 69 */   public boolean isDbNull(Object value) { return (value == null); }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 76 */   public Object getDbNullValue(Object value) { return value; }
/*    */ 
/*    */ 
/*    */   
/* 80 */   public void loadIgnore(DataReader dataReader) { dataReader.incrementPos(1); }
/*    */ 
/*    */ 
/*    */   
/* 84 */   public void accumulateScalarTypes(String propName, CtCompoundTypeScalarList list) { list.addScalarType(propName, this); }
/*    */ 
/*    */ 
/*    */   
/* 88 */   public String jsonToString(T value, JsonValueAdapter ctx) { return formatValue(value); }
/*    */ 
/*    */ 
/*    */   
/* 92 */   public T jsonFromString(String value, JsonValueAdapter ctx) { return (T)parse(value); }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\type\ScalarTypeBase.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */