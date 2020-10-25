/*    */ package com.mysql.jdbc.util;
/*    */ 
/*    */ import com.mysql.jdbc.TimeUtil;
/*    */ import java.sql.DriverManager;
/*    */ import java.sql.ResultSet;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TimezoneDump
/*    */ {
/*    */   private static final String DEFAULT_URL = "jdbc:mysql:///test";
/*    */   
/*    */   public static void main(String[] args) throws Exception {
/* 67 */     String jdbcUrl = "jdbc:mysql:///test";
/*    */     
/* 69 */     if (args.length == 1 && args[false] != null) {
/* 70 */       jdbcUrl = args[0];
/*    */     }
/*    */     
/* 73 */     Class.forName("com.mysql.jdbc.Driver").newInstance();
/*    */     
/* 75 */     ResultSet rs = DriverManager.getConnection(jdbcUrl).createStatement().executeQuery("SHOW VARIABLES LIKE 'timezone'");
/*    */ 
/*    */     
/* 78 */     while (rs.next()) {
/* 79 */       String timezoneFromServer = rs.getString(2);
/* 80 */       System.out.println("MySQL timezone name: " + timezoneFromServer);
/*    */       
/* 82 */       String canonicalTimezone = TimeUtil.getCanoncialTimezone(timezoneFromServer, null);
/*    */       
/* 84 */       System.out.println("Java timezone name: " + canonicalTimezone);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\mysql\jdb\\util\TimezoneDump.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */