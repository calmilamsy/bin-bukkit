/*    */ package com.avaje.ebeaninternal.server.lib.util;
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
/*    */ public class MailAddress
/*    */ {
/*    */   String alias;
/*    */   String emailAddress;
/*    */   
/*    */   public MailAddress(String alias, String emailAddress) {
/* 34 */     this.alias = alias;
/* 35 */     this.emailAddress = emailAddress;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getAlias() {
/* 43 */     if (this.alias == null) {
/* 44 */       return "";
/*    */     }
/* 46 */     return this.alias;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 53 */   public String getEmailAddress() { return this.emailAddress; }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 57 */     StringBuffer sb = new StringBuffer();
/* 58 */     sb.append(getAlias()).append(" ").append("<").append(getEmailAddress()).append(">");
/* 59 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\li\\util\MailAddress.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */