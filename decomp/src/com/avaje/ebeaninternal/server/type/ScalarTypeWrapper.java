/*     */ package com.avaje.ebeaninternal.server.type;
/*     */ 
/*     */ import com.avaje.ebean.config.ScalarTypeConverter;
/*     */ import com.avaje.ebean.text.json.JsonValueAdapter;
/*     */ import java.io.DataInput;
/*     */ import java.io.DataOutput;
/*     */ import java.io.IOException;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ScalarTypeWrapper<B, S>
/*     */   extends Object
/*     */   implements ScalarType<B>
/*     */ {
/*     */   private final ScalarType<S> scalarType;
/*     */   private final ScalarTypeConverter<B, S> converter;
/*     */   private final Class<B> wrapperType;
/*     */   private final B nullValue;
/*     */   
/*     */   public ScalarTypeWrapper(Class<B> wrapperType, ScalarType<S> scalarType, ScalarTypeConverter<B, S> converter) {
/*  53 */     this.scalarType = scalarType;
/*  54 */     this.converter = converter;
/*  55 */     this.nullValue = converter.getNullValue();
/*  56 */     this.wrapperType = wrapperType;
/*     */   }
/*     */ 
/*     */   
/*  60 */   public String toString() { return "ScalarTypeWrapper " + this.wrapperType + " to " + this.scalarType.getType(); }
/*     */ 
/*     */ 
/*     */   
/*     */   public Object readData(DataInput dataInput) throws IOException {
/*  65 */     Object v = this.scalarType.readData(dataInput);
/*  66 */     return this.converter.wrapValue(v);
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeData(DataOutput dataOutput, Object v) throws IOException {
/*  71 */     S sv = (S)this.converter.unwrapValue(v);
/*  72 */     this.scalarType.writeData(dataOutput, sv);
/*     */   }
/*     */   
/*     */   public void bind(DataBind b, B value) throws SQLException {
/*  76 */     if (value == null) {
/*  77 */       this.scalarType.bind(b, null);
/*     */     } else {
/*  79 */       S sv = (S)this.converter.unwrapValue(value);
/*  80 */       this.scalarType.bind(b, sv);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*  85 */   public int getJdbcType() { return this.scalarType.getJdbcType(); }
/*     */ 
/*     */ 
/*     */   
/*  89 */   public int getLength() { return this.scalarType.getLength(); }
/*     */ 
/*     */ 
/*     */   
/*  93 */   public Class<B> getType() { return this.wrapperType; }
/*     */ 
/*     */ 
/*     */   
/*  97 */   public boolean isDateTimeCapable() { return this.scalarType.isDateTimeCapable(); }
/*     */ 
/*     */ 
/*     */   
/* 101 */   public boolean isJdbcNative() { return false; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 106 */   public String format(Object v) { return formatValue(v); }
/*     */ 
/*     */   
/*     */   public String formatValue(B v) {
/* 110 */     S sv = (S)this.converter.unwrapValue(v);
/* 111 */     return this.scalarType.formatValue(sv);
/*     */   }
/*     */   
/*     */   public B parse(String value) {
/* 115 */     S sv = (S)this.scalarType.parse(value);
/* 116 */     if (sv == null) {
/* 117 */       return (B)this.nullValue;
/*     */     }
/* 119 */     return (B)this.converter.wrapValue(sv);
/*     */   }
/*     */   
/*     */   public B parseDateTime(long systemTimeMillis) {
/* 123 */     S sv = (S)this.scalarType.parseDateTime(systemTimeMillis);
/* 124 */     if (sv == null) {
/* 125 */       return (B)this.nullValue;
/*     */     }
/* 127 */     return (B)this.converter.wrapValue(sv);
/*     */   }
/*     */ 
/*     */   
/* 131 */   public void loadIgnore(DataReader dataReader) { dataReader.incrementPos(1); }
/*     */ 
/*     */ 
/*     */   
/*     */   public B read(DataReader dataReader) throws SQLException {
/* 136 */     S sv = (S)this.scalarType.read(dataReader);
/* 137 */     if (sv == null) {
/* 138 */       return (B)this.nullValue;
/*     */     }
/* 140 */     return (B)this.converter.wrapValue(sv);
/*     */   }
/*     */ 
/*     */   
/*     */   public B toBeanType(Object value) {
/* 145 */     if (value == null) {
/* 146 */       return (B)this.nullValue;
/*     */     }
/* 148 */     if (getType().isAssignableFrom(value.getClass())) {
/* 149 */       return (B)value;
/*     */     }
/* 151 */     if (value instanceof String) {
/* 152 */       return (B)parse((String)value);
/*     */     }
/* 154 */     S sv = (S)this.scalarType.toBeanType(value);
/* 155 */     return (B)this.converter.wrapValue(sv);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Object toJdbcType(Object value) {
/* 161 */     Object sv = this.converter.unwrapValue(value);
/* 162 */     if (sv == null) {
/* 163 */       return this.nullValue;
/*     */     }
/* 165 */     return this.scalarType.toJdbcType(sv);
/*     */   }
/*     */ 
/*     */   
/* 169 */   public void accumulateScalarTypes(String propName, CtCompoundTypeScalarList list) { list.addScalarType(propName, this); }
/*     */ 
/*     */ 
/*     */   
/* 173 */   public ScalarType<?> getScalarType() { return this; }
/*     */ 
/*     */ 
/*     */   
/*     */   public String jsonToString(B value, JsonValueAdapter ctx) {
/* 178 */     S sv = (S)this.converter.unwrapValue(value);
/* 179 */     return this.scalarType.jsonToString(sv, ctx);
/*     */   }
/*     */   
/*     */   public B jsonFromString(String value, JsonValueAdapter ctx) {
/* 183 */     S s = (S)this.scalarType.jsonFromString(value, ctx);
/* 184 */     return (B)this.converter.wrapValue(s);
/*     */   }
/*     */ 
/*     */   
/*     */   public Object luceneFromIndexValue(Object value) {
/* 189 */     S s = (S)this.scalarType.luceneFromIndexValue(value);
/* 190 */     return this.converter.wrapValue(s);
/*     */   }
/*     */ 
/*     */   
/*     */   public Object luceneToIndexValue(Object value) {
/* 195 */     S sv = (S)this.converter.unwrapValue(value);
/* 196 */     return this.scalarType.luceneToIndexValue(sv);
/*     */   }
/*     */ 
/*     */   
/* 200 */   public int getLuceneType() { return this.scalarType.getLuceneType(); }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\type\ScalarTypeWrapper.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */