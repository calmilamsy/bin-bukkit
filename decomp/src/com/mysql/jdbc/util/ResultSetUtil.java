/*    */ package com.mysql.jdbc.util;
/*    */ 
/*    */ import java.sql.ResultSet;
/*    */ import java.sql.ResultSetMetaData;
/*    */ import java.sql.SQLException;
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
/*    */ public class ResultSetUtil
/*    */ {
/*    */   public static StringBuffer appendResultSetSlashGStyle(StringBuffer appendTo, ResultSet rs) throws SQLException {
/* 43 */     ResultSetMetaData rsmd = rs.getMetaData();
/*    */     
/* 45 */     int numFields = rsmd.getColumnCount();
/* 46 */     int maxWidth = 0;
/*    */     
/* 48 */     String[] fieldNames = new String[numFields];
/*    */     
/* 50 */     for (i = 0; i < numFields; i++) {
/* 51 */       fieldNames[i] = rsmd.getColumnLabel(i + 1);
/*    */       
/* 53 */       if (fieldNames[i].length() > maxWidth) {
/* 54 */         maxWidth = fieldNames[i].length();
/*    */       }
/*    */     } 
/*    */     
/* 58 */     int rowCount = 1;
/*    */     
/* 60 */     while (rs.next()) {
/* 61 */       appendTo.append("*************************** ");
/* 62 */       appendTo.append(rowCount++);
/* 63 */       appendTo.append(". row ***************************\n");
/*    */       
/* 65 */       for (int i = 0; i < numFields; i++) {
/* 66 */         int leftPad = maxWidth - fieldNames[i].length();
/*    */         
/* 68 */         for (j = 0; j < leftPad; j++) {
/* 69 */           appendTo.append(" ");
/*    */         }
/*    */         
/* 72 */         appendTo.append(fieldNames[i]);
/* 73 */         appendTo.append(": ");
/*    */         
/* 75 */         String stringVal = rs.getString(i + 1);
/*    */         
/* 77 */         if (stringVal != null) {
/* 78 */           appendTo.append(stringVal);
/*    */         } else {
/* 80 */           appendTo.append("NULL");
/*    */         } 
/*    */         
/* 83 */         appendTo.append("\n");
/*    */       } 
/*    */       
/* 86 */       appendTo.append("\n");
/*    */     } 
/*    */     
/* 89 */     return appendTo;
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\mysql\jdb\\util\ResultSetUtil.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */