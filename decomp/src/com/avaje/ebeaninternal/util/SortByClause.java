/*    */ package com.avaje.ebeaninternal.util;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
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
/*    */ public class SortByClause
/*    */ {
/*    */   public static final String NULLSHIGH = "nullshigh";
/*    */   public static final String NULLSLOW = "nullslow";
/*    */   public static final String ASC = "asc";
/*    */   public static final String DESC = "desc";
/* 30 */   private List<Property> properties = new ArrayList();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 36 */   public int size() { return this.properties.size(); }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 43 */   public List<Property> getProperties() { return this.properties; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 50 */   public void add(Property p) { this.properties.add(p); }
/*    */ 
/*    */ 
/*    */   
/*    */   public static class Property
/*    */     implements Serializable
/*    */   {
/*    */     private static final long serialVersionUID = 7588760362420690963L;
/*    */     
/*    */     private final String name;
/*    */     
/*    */     private final boolean ascending;
/*    */     
/*    */     private final Boolean nullsHigh;
/*    */ 
/*    */     
/*    */     public Property(String name, boolean ascending, Boolean nullsHigh) {
/* 67 */       this.name = name;
/* 68 */       this.ascending = ascending;
/* 69 */       this.nullsHigh = nullsHigh;
/*    */     }
/*    */ 
/*    */     
/* 73 */     public String toString() { return this.name + " asc:" + this.ascending; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 80 */     public String getName() { return this.name; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 87 */     public boolean isAscending() { return this.ascending; }
/*    */ 
/*    */ 
/*    */     
/* 91 */     public Boolean getNullsHigh() { return this.nullsHigh; }
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninterna\\util\SortByClause.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */