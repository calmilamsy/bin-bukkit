/*    */ package com.avaje.ebeaninternal.server.lib.util;
/*    */ 
/*    */ import java.util.LinkedHashMap;
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
/*    */ public class MapFromString
/*    */ {
/* 27 */   LinkedHashMap<String, String> map = new LinkedHashMap();
/*    */   
/*    */   String mapToString;
/*    */   
/*    */   int stringLength;
/* 32 */   int keyStart = 0;
/* 33 */   int eqPos = 0;
/* 34 */   int valEnd = 0;
/*    */   
/*    */   public static LinkedHashMap<String, String> parse(String mapToString) {
/* 37 */     MapFromString c = new MapFromString(mapToString);
/* 38 */     return c.parse();
/*    */   }
/*    */   
/*    */   private MapFromString(String mapToString) {
/* 42 */     if (mapToString.charAt(0) == '{') {
/* 43 */       mapToString = mapToString.substring(1);
/*    */     }
/* 45 */     if (mapToString.charAt(mapToString.length() - 1) == '}') {
/* 46 */       mapToString = mapToString.substring(0, mapToString.length() - 1);
/*    */     }
/*    */     
/* 49 */     this.mapToString = mapToString;
/* 50 */     this.stringLength = mapToString.length();
/*    */   }
/*    */   
/*    */   private LinkedHashMap<String, String> parse() {
/* 54 */     while (findNext());
/*    */     
/* 56 */     return this.map;
/*    */   }
/*    */   
/*    */   private boolean findNext() {
/* 60 */     if (this.keyStart > this.stringLength) {
/* 61 */       return false;
/*    */     }
/* 63 */     this.eqPos = this.mapToString.indexOf("=", this.keyStart);
/* 64 */     if (this.eqPos == -1) {
/* 65 */       throw new RuntimeException("No = after " + this.keyStart);
/*    */     }
/* 67 */     this.valEnd = this.mapToString.indexOf(", ", this.eqPos);
/* 68 */     if (this.valEnd == -1) {
/* 69 */       this.valEnd = this.mapToString.length();
/*    */     }
/*    */ 
/*    */     
/* 73 */     String keyValue = this.mapToString.substring(this.keyStart, this.eqPos);
/* 74 */     String valValue = this.mapToString.substring(this.eqPos + 1, this.valEnd);
/* 75 */     this.map.put(keyValue, valValue);
/* 76 */     this.keyStart = this.valEnd + 2;
/* 77 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\li\\util\MapFromString.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */