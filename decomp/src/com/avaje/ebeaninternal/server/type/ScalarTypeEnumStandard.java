/*     */ package com.avaje.ebeaninternal.server.type;
/*     */ 
/*     */ import com.avaje.ebean.text.TextException;
/*     */ import com.avaje.ebean.text.json.JsonValueAdapter;
/*     */ import java.io.DataInput;
/*     */ import java.io.DataOutput;
/*     */ import java.io.IOException;
/*     */ import java.sql.SQLException;
/*     */ import java.util.EnumSet;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ScalarTypeEnumStandard
/*     */ {
/*     */   public static class StringEnum
/*     */     extends EnumBase
/*     */     implements ScalarTypeEnum
/*     */   {
/*     */     private final int length;
/*     */     
/*     */     public StringEnum(Class enumType) {
/*  57 */       super(enumType, false, 12);
/*  58 */       this.length = maxValueLength(enumType);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String getContraintInValues() {
/*  66 */       StringBuilder sb = new StringBuilder();
/*     */       
/*  68 */       sb.append("(");
/*  69 */       Object[] ea = this.enumType.getEnumConstants();
/*  70 */       for (int i = 0; i < ea.length; i++) {
/*  71 */         Enum<?> e = (Enum)ea[i];
/*  72 */         if (i > 0) {
/*  73 */           sb.append(",");
/*     */         }
/*  75 */         sb.append("'").append(e.toString()).append("'");
/*     */       } 
/*  77 */       sb.append(")");
/*     */       
/*  79 */       return sb.toString();
/*     */     }
/*     */ 
/*     */     
/*     */     private int maxValueLength(Class<?> enumType) {
/*  84 */       int maxLen = 0;
/*     */       
/*  86 */       Object[] ea = enumType.getEnumConstants();
/*  87 */       for (int i = 0; i < ea.length; i++) {
/*  88 */         Enum<?> e = (Enum)ea[i];
/*  89 */         maxLen = Math.max(maxLen, e.toString().length());
/*     */       } 
/*     */       
/*  92 */       return maxLen;
/*     */     }
/*     */ 
/*     */     
/*  96 */     public int getLength() { return this.length; }
/*     */ 
/*     */     
/*     */     public void bind(DataBind b, Object value) throws SQLException {
/* 100 */       if (value == null) {
/* 101 */         b.setNull(12);
/*     */       } else {
/* 103 */         b.setString(value.toString());
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public Object read(DataReader dataReader) throws SQLException {
/* 109 */       String string = dataReader.getString();
/* 110 */       if (string == null) {
/* 111 */         return null;
/*     */       }
/* 113 */       return Enum.valueOf(this.enumType, string);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Object toJdbcType(Object beanValue) {
/* 121 */       if (beanValue == null) {
/* 122 */         return null;
/*     */       }
/* 124 */       Enum<?> e = (Enum)beanValue;
/* 125 */       return e.toString();
/*     */     }
/*     */     
/*     */     public Object toBeanType(Object dbValue) {
/* 129 */       if (dbValue == null) {
/* 130 */         return null;
/*     */       }
/*     */       
/* 133 */       return Enum.valueOf(this.enumType, (String)dbValue);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static class OrdinalEnum
/*     */     extends EnumBase
/*     */     implements ScalarTypeEnum
/*     */   {
/*     */     private final Object[] enumArray;
/*     */ 
/*     */     
/*     */     public OrdinalEnum(Class enumType) {
/* 147 */       super(enumType, false, 4);
/* 148 */       this.enumArray = EnumSet.allOf(enumType).toArray();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String getContraintInValues() {
/* 156 */       StringBuilder sb = new StringBuilder();
/*     */       
/* 158 */       sb.append("(");
/* 159 */       for (int i = 0; i < this.enumArray.length; i++) {
/* 160 */         Enum<?> e = (Enum)this.enumArray[i];
/* 161 */         if (i > 0) {
/* 162 */           sb.append(",");
/*     */         }
/* 164 */         sb.append(e.ordinal());
/*     */       } 
/* 166 */       sb.append(")");
/*     */       
/* 168 */       return sb.toString();
/*     */     }
/*     */ 
/*     */     
/*     */     public void bind(DataBind b, Object value) throws SQLException {
/* 173 */       if (value == null) {
/* 174 */         b.setNull(4);
/*     */       } else {
/* 176 */         Enum<?> e = (Enum)value;
/* 177 */         b.setInt(e.ordinal());
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public Object read(DataReader dataReader) throws SQLException {
/* 183 */       if (dataReader instanceof com.avaje.ebeaninternal.server.query.LuceneIndexDataReader) {
/*     */ 
/*     */         
/* 186 */         String s = dataReader.getString();
/* 187 */         return (s == null) ? null : parse(s);
/*     */       } 
/* 189 */       Integer ordinal = dataReader.getInt();
/* 190 */       if (ordinal == null) {
/* 191 */         return null;
/*     */       }
/* 193 */       if (ordinal.intValue() < 0 || ordinal.intValue() >= this.enumArray.length) {
/* 194 */         String m = "Unexpected ordinal [" + ordinal + "] out of range [" + this.enumArray.length + "]";
/* 195 */         throw new IllegalStateException(m);
/*     */       } 
/* 197 */       return this.enumArray[ordinal.intValue()];
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Object toJdbcType(Object beanValue) {
/* 205 */       if (beanValue == null) {
/* 206 */         return null;
/*     */       }
/* 208 */       Enum e = (Enum)beanValue;
/* 209 */       return Integer.valueOf(e.ordinal());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Object toBeanType(Object dbValue) {
/* 216 */       if (dbValue == null) {
/* 217 */         return null;
/*     */       }
/*     */       
/* 220 */       int ordinal = ((Integer)dbValue).intValue();
/* 221 */       if (ordinal < 0 || ordinal >= this.enumArray.length) {
/* 222 */         String m = "Unexpected ordinal [" + ordinal + "] out of range [" + this.enumArray.length + "]";
/* 223 */         throw new IllegalStateException(m);
/*     */       } 
/* 225 */       return this.enumArray[ordinal];
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static abstract class EnumBase
/*     */     extends ScalarTypeBase
/*     */   {
/*     */     protected final Class enumType;
/*     */     
/*     */     public EnumBase(Class<?> type, boolean jdbcNative, int jdbcType) {
/* 236 */       super(type, jdbcNative, jdbcType);
/* 237 */       this.enumType = type;
/*     */     }
/*     */ 
/*     */     
/* 241 */     public String format(Object t) { return t.toString(); }
/*     */ 
/*     */ 
/*     */     
/* 245 */     public String formatValue(Object t) { return t.toString(); }
/*     */ 
/*     */ 
/*     */     
/* 249 */     public Object parse(String value) { return Enum.valueOf(this.enumType, value); }
/*     */ 
/*     */ 
/*     */     
/* 253 */     public Object parseDateTime(long systemTimeMillis) { throw new TextException("Not Supported"); }
/*     */ 
/*     */ 
/*     */     
/* 257 */     public boolean isDateTimeCapable() { return false; }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 262 */     public Object jsonFromString(String value, JsonValueAdapter ctx) { return parse(value); }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 267 */     public String jsonToString(Object value, JsonValueAdapter ctx) { return EscapeJson.escapeQuote(value.toString()); }
/*     */ 
/*     */ 
/*     */     
/* 271 */     public int getLuceneType() { return 0; }
/*     */ 
/*     */ 
/*     */     
/* 275 */     public Object luceneFromIndexValue(Object value) { return parse((String)value); }
/*     */ 
/*     */ 
/*     */     
/* 279 */     public Object luceneToIndexValue(Object value) { return format(value); }
/*     */ 
/*     */     
/*     */     public Object readData(DataInput dataInput) throws IOException {
/* 283 */       if (!dataInput.readBoolean()) {
/* 284 */         return null;
/*     */       }
/* 286 */       String s = dataInput.readUTF();
/* 287 */       return parse(s);
/*     */     }
/*     */ 
/*     */     
/*     */     public void writeData(DataOutput dataOutput, Object v) throws IOException {
/* 292 */       if (v == null) {
/* 293 */         dataOutput.writeBoolean(false);
/*     */       } else {
/* 295 */         dataOutput.writeBoolean(true);
/* 296 */         dataOutput.writeUTF(format(v));
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\type\ScalarTypeEnumStandard.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */