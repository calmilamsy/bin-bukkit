/*     */ package com.avaje.ebeaninternal.server.type;
/*     */ 
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.Reader;
/*     */ import java.io.StringReader;
/*     */ import java.math.BigDecimal;
/*     */ import java.sql.Date;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.SQLException;
/*     */ import java.sql.Time;
/*     */ import java.sql.Timestamp;
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
/*     */ public class DataBind
/*     */ {
/*     */   private final PreparedStatement pstmt;
/*     */   private int pos;
/*     */   
/*  38 */   public DataBind(PreparedStatement pstmt) { this.pstmt = pstmt; }
/*     */ 
/*     */ 
/*     */   
/*  42 */   public void close() throws SQLException { this.pstmt.close(); }
/*     */ 
/*     */ 
/*     */   
/*  46 */   public int currentPos() { return this.pos; }
/*     */ 
/*     */ 
/*     */   
/*  50 */   public void resetPos() throws SQLException { this.pos = 0; }
/*     */ 
/*     */ 
/*     */   
/*  54 */   public void setObject(Object value) throws SQLException { this.pstmt.setObject(++this.pos, value); }
/*     */ 
/*     */ 
/*     */   
/*  58 */   public void setObject(Object value, int sqlType) throws SQLException { this.pstmt.setObject(++this.pos, value, sqlType); }
/*     */ 
/*     */ 
/*     */   
/*  62 */   public void setNull(int jdbcType) throws SQLException { this.pstmt.setNull(++this.pos, jdbcType); }
/*     */ 
/*     */ 
/*     */   
/*  66 */   public int nextPos() { return ++this.pos; }
/*     */ 
/*     */ 
/*     */   
/*  70 */   public int decrementPos() { return ++this.pos; }
/*     */ 
/*     */ 
/*     */   
/*  74 */   public int executeUpdate() { return this.pstmt.executeUpdate(); }
/*     */ 
/*     */ 
/*     */   
/*  78 */   public PreparedStatement getPstmt() { return this.pstmt; }
/*     */ 
/*     */ 
/*     */   
/*  82 */   public void setString(String s) throws SQLException { this.pstmt.setString(++this.pos, s); }
/*     */ 
/*     */ 
/*     */   
/*  86 */   public void setInt(int i) throws SQLException { this.pstmt.setInt(++this.pos, i); }
/*     */ 
/*     */ 
/*     */   
/*  90 */   public void setLong(long i) throws SQLException { this.pstmt.setLong(++this.pos, i); }
/*     */ 
/*     */ 
/*     */   
/*  94 */   public void setShort(short i) throws SQLException { this.pstmt.setShort(++this.pos, i); }
/*     */ 
/*     */ 
/*     */   
/*  98 */   public void setFloat(float i) throws SQLException { this.pstmt.setFloat(++this.pos, i); }
/*     */ 
/*     */ 
/*     */   
/* 102 */   public void setDouble(double i) throws SQLException { this.pstmt.setDouble(++this.pos, i); }
/*     */ 
/*     */ 
/*     */   
/* 106 */   public void setBigDecimal(BigDecimal v) throws SQLException { this.pstmt.setBigDecimal(++this.pos, v); }
/*     */ 
/*     */ 
/*     */   
/* 110 */   public void setDate(Date v) throws SQLException { this.pstmt.setDate(++this.pos, v); }
/*     */ 
/*     */ 
/*     */   
/* 114 */   public void setTimestamp(Timestamp v) throws SQLException { this.pstmt.setTimestamp(++this.pos, v); }
/*     */ 
/*     */ 
/*     */   
/* 118 */   public void setTime(Time v) throws SQLException { this.pstmt.setTime(++this.pos, v); }
/*     */ 
/*     */ 
/*     */   
/* 122 */   public void setBoolean(boolean v) throws SQLException { this.pstmt.setBoolean(++this.pos, v); }
/*     */ 
/*     */ 
/*     */   
/* 126 */   public void setBytes(byte[] v) throws SQLException { this.pstmt.setBytes(++this.pos, v); }
/*     */ 
/*     */ 
/*     */   
/* 130 */   public void setByte(byte v) throws SQLException { this.pstmt.setByte(++this.pos, v); }
/*     */ 
/*     */ 
/*     */   
/* 134 */   public void setChar(char v) throws SQLException { this.pstmt.setString(++this.pos, String.valueOf(v)); }
/*     */ 
/*     */   
/*     */   public void setBlob(byte[] bytes) throws SQLException {
/* 138 */     ByteArrayInputStream is = new ByteArrayInputStream(bytes);
/* 139 */     this.pstmt.setBinaryStream(++this.pos, is, bytes.length);
/*     */   }
/*     */   
/*     */   public void setClob(String content) throws SQLException {
/* 143 */     Reader reader = new StringReader(content);
/* 144 */     this.pstmt.setCharacterStream(++this.pos, reader, content.length());
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\type\DataBind.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */