/*    */ package com.avaje.ebeaninternal.server.text.json;
/*    */ 
/*    */ import com.avaje.ebean.text.json.JsonValueAdapter;
/*    */ import java.sql.Date;
/*    */ import java.sql.Timestamp;
/*    */ import java.text.SimpleDateFormat;
/*    */ import java.util.Date;
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
/*    */ public class DefaultJsonValueAdapter
/*    */   implements JsonValueAdapter
/*    */ {
/*    */   private final SimpleDateFormat dateTimeProto;
/*    */   
/* 33 */   public DefaultJsonValueAdapter(String dateTimeFormat) { this.dateTimeProto = new SimpleDateFormat(dateTimeFormat); }
/*    */ 
/*    */ 
/*    */   
/* 37 */   public DefaultJsonValueAdapter() { this("yyyy-MM-dd'T'HH:mm:ss.SSSZ"); }
/*    */ 
/*    */ 
/*    */   
/* 41 */   private SimpleDateFormat dtFormat() { return (SimpleDateFormat)this.dateTimeProto.clone(); }
/*    */ 
/*    */ 
/*    */   
/* 45 */   public String jsonFromDate(Date date) { return date.toString(); }
/*    */ 
/*    */ 
/*    */   
/* 49 */   public String jsonFromTimestamp(Timestamp date) { return dtFormat().format(date); }
/*    */ 
/*    */ 
/*    */   
/* 53 */   public Date jsonToDate(String jsonDate) { return Date.valueOf(jsonDate); }
/*    */ 
/*    */   
/*    */   public Timestamp jsonToTimestamp(String jsonDateTime) {
/*    */     try {
/* 58 */       Date d = dtFormat().parse(jsonDateTime);
/* 59 */       return new Timestamp(d.getTime());
/* 60 */     } catch (Exception e) {
/* 61 */       String m = "Error parsing Datetime[" + jsonDateTime + "]";
/* 62 */       throw new RuntimeException(m, e);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\text\json\DefaultJsonValueAdapter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */