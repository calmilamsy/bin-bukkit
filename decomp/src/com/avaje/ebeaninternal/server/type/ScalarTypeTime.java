/*     */ package com.avaje.ebeaninternal.server.type;
/*     */ 
/*     */ import com.avaje.ebeaninternal.server.core.BasicTypeConverter;
/*     */ import java.io.DataInput;
/*     */ import java.io.DataOutput;
/*     */ import java.io.IOException;
/*     */ import java.sql.SQLException;
/*     */ import java.sql.Time;
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
/*     */ public class ScalarTypeTime
/*     */   extends ScalarTypeBase<Time>
/*     */ {
/*  38 */   public ScalarTypeTime() { super(Time.class, true, 92); }
/*     */ 
/*     */   
/*     */   public void bind(DataBind b, Time value) throws SQLException {
/*  42 */     if (value == null) {
/*  43 */       b.setNull(92);
/*     */     } else {
/*  45 */       b.setTime(value);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*  51 */   public Time read(DataReader dataReader) throws SQLException { return dataReader.getTime(); }
/*     */ 
/*     */ 
/*     */   
/*  55 */   public Object toJdbcType(Object value) { return BasicTypeConverter.toTime(value); }
/*     */ 
/*     */ 
/*     */   
/*  59 */   public Time toBeanType(Object value) { return BasicTypeConverter.toTime(value); }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  64 */   public String formatValue(Time v) { return v.toString(); }
/*     */ 
/*     */ 
/*     */   
/*  68 */   public Time parse(String value) { return Time.valueOf(value); }
/*     */ 
/*     */ 
/*     */   
/*  72 */   public Time parseDateTime(long systemTimeMillis) { return new Time(systemTimeMillis); }
/*     */ 
/*     */ 
/*     */   
/*  76 */   public boolean isDateTimeCapable() { return true; }
/*     */ 
/*     */ 
/*     */   
/*  80 */   public int getLuceneType() { return 0; }
/*     */ 
/*     */ 
/*     */   
/*  84 */   public Object luceneFromIndexValue(Object value) { return parse((String)value); }
/*     */ 
/*     */ 
/*     */   
/*  88 */   public Object luceneToIndexValue(Object value) { return format(value); }
/*     */ 
/*     */   
/*     */   public Object readData(DataInput dataInput) throws IOException {
/*  92 */     if (!dataInput.readBoolean()) {
/*  93 */       return null;
/*     */     }
/*  95 */     String val = dataInput.readUTF();
/*  96 */     return parse(val);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeData(DataOutput dataOutput, Object v) throws IOException {
/* 102 */     Time value = (Time)v;
/* 103 */     if (value == null) {
/* 104 */       dataOutput.writeBoolean(false);
/*     */     } else {
/* 106 */       dataOutput.writeBoolean(true);
/* 107 */       dataOutput.writeUTF(format(value));
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\type\ScalarTypeTime.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */