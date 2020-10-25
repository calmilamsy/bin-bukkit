/*    */ package com.mysql.jdbc;
/*    */ 
/*    */ import java.sql.DataTruncation;
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
/*    */ public class MysqlDataTruncation
/*    */   extends DataTruncation
/*    */ {
/*    */   private String message;
/*    */   private int vendorErrorCode;
/*    */   
/*    */   public MysqlDataTruncation(String message, int index, boolean parameter, boolean read, int dataSize, int transferSize, int vendorErrorCode) {
/* 63 */     super(index, parameter, read, dataSize, transferSize);
/*    */     
/* 65 */     this.message = message;
/* 66 */     this.vendorErrorCode = vendorErrorCode;
/*    */   }
/*    */ 
/*    */   
/* 70 */   public int getErrorCode() { return this.vendorErrorCode; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 79 */   public String getMessage() { return super.getMessage() + ": " + this.message; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\mysql\jdbc\MysqlDataTruncation.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */