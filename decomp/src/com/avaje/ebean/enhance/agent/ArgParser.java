/*    */ package com.avaje.ebean.enhance.agent;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ArgParser
/*    */ {
/*    */   public static HashMap<String, String> parse(String args) {
/* 15 */     HashMap<String, String> map = new HashMap<String, String>();
/*    */     
/* 17 */     if (args != null) {
/* 18 */       String[] split = args.split(";");
/* 19 */       for (String nameValuePair : split) {
/* 20 */         String[] nameValue = nameValuePair.split("=");
/* 21 */         if (nameValue.length == 2) {
/* 22 */           map.put(nameValue[0].toLowerCase(), nameValue[1]);
/*    */         }
/*    */       } 
/*    */     } 
/*    */     
/* 27 */     return map;
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebean\enhance\agent\ArgParser.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */