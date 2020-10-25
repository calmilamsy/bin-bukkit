/*     */ package com.avaje.ebean;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
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
/*     */ public final class OrderBy<T>
/*     */   extends Object
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 9157089257745730539L;
/*     */   private Query<T> query;
/*     */   private List<Property> list;
/*     */   
/*  52 */   public OrderBy() { this.list = new ArrayList(2); }
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
/*  64 */   public OrderBy(String orderByClause) { this(null, orderByClause); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public OrderBy(Query<T> query, String orderByClause) {
/*  71 */     this.query = query;
/*  72 */     this.list = new ArrayList(2);
/*  73 */     parse(orderByClause);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void reverse() {
/*  80 */     for (int i = 0; i < this.list.size(); i++) {
/*  81 */       ((Property)this.list.get(i)).reverse();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Query<T> asc(String propertyName) {
/*  90 */     this.list.add(new Property(propertyName, true));
/*  91 */     return this.query;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Query<T> desc(String propertyName) {
/*  99 */     this.list.add(new Property(propertyName, false));
/* 100 */     return this.query;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 108 */   public List<Property> getProperties() { return this.list; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 115 */   public boolean isEmpty() { return this.list.isEmpty(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 122 */   public Query<T> getQuery() { return this.query; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 129 */   public void setQuery(Query<T> query) { this.query = query; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public OrderBy<T> copy() {
/* 137 */     OrderBy<T> copy = new OrderBy<T>();
/* 138 */     for (int i = 0; i < this.list.size(); i++) {
/* 139 */       copy.add(((Property)this.list.get(i)).copy());
/*     */     }
/* 141 */     return copy;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 148 */   public void add(Property p) { this.list.add(p); }
/*     */ 
/*     */ 
/*     */   
/* 152 */   public String toString() { return this.list.toString(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toStringFormat() {
/* 159 */     if (this.list.isEmpty()) {
/* 160 */       return null;
/*     */     }
/* 162 */     StringBuilder sb = new StringBuilder();
/* 163 */     for (int i = 0; i < this.list.size(); i++) {
/* 164 */       Property property = (Property)this.list.get(i);
/* 165 */       if (i > 0) {
/* 166 */         sb.append(", ");
/*     */       }
/* 168 */       sb.append(property.toStringFormat());
/*     */     } 
/* 170 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/* 175 */     if (obj instanceof OrderBy) {
/* 176 */       if (obj == this) {
/* 177 */         return true;
/*     */       }
/* 179 */       OrderBy<?> other = (OrderBy)obj;
/* 180 */       return (hashCode() == other.hashCode());
/*     */     } 
/* 182 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 187 */   public int hashCode() { return hash(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hash() {
/* 195 */     int hc = OrderBy.class.getName().hashCode();
/* 196 */     for (int i = 0; i < this.list.size(); i++) {
/* 197 */       hc = hc * 31 + ((Property)this.list.get(i)).hash();
/*     */     }
/* 199 */     return hc;
/*     */   }
/*     */ 
/*     */   
/*     */   public static final class Property
/*     */     implements Serializable
/*     */   {
/*     */     private static final long serialVersionUID = 1546009780322478077L;
/*     */     
/*     */     private String property;
/*     */     
/*     */     private boolean ascending;
/*     */ 
/*     */     
/*     */     public Property(String property, boolean ascending) {
/* 214 */       this.property = property;
/* 215 */       this.ascending = ascending;
/*     */     }
/*     */     
/*     */     protected int hash() {
/* 219 */       hc = this.property.hashCode();
/* 220 */       return hc * 31 + (this.ascending ? 0 : 1);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 225 */     public String toString() { return toStringFormat(); }
/*     */ 
/*     */     
/*     */     public String toStringFormat() {
/* 229 */       if (this.ascending) {
/* 230 */         return this.property;
/*     */       }
/* 232 */       return this.property + " desc";
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 240 */     public void reverse() { this.ascending = !this.ascending; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 247 */     public void trim(String pathPrefix) { this.property = this.property.substring(pathPrefix.length() + 1); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 254 */     public Property copy() { return new Property(this.property, this.ascending); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 261 */     public String getProperty() { return this.property; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 268 */     public void setProperty(String property) { this.property = property; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 275 */     public boolean isAscending() { return this.ascending; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 282 */     public void setAscending(boolean ascending) { this.ascending = ascending; }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void parse(String orderByClause) {
/* 289 */     if (orderByClause == null) {
/*     */       return;
/*     */     }
/*     */     
/* 293 */     String[] chunks = orderByClause.split(",");
/* 294 */     for (int i = 0; i < chunks.length; i++) {
/*     */       
/* 296 */       String[] pairs = chunks[i].split(" ");
/* 297 */       Property p = parseProperty(pairs);
/* 298 */       if (p != null) {
/* 299 */         this.list.add(p);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private Property parseProperty(String[] pairs) {
/* 305 */     if (pairs.length == 0) {
/* 306 */       return null;
/*     */     }
/*     */     
/* 309 */     ArrayList<String> wordList = new ArrayList<String>(pairs.length);
/* 310 */     for (int i = 0; i < pairs.length; i++) {
/* 311 */       if (!isEmptyString(pairs[i])) {
/* 312 */         wordList.add(pairs[i]);
/*     */       }
/*     */     } 
/* 315 */     if (wordList.isEmpty()) {
/* 316 */       return null;
/*     */     }
/* 318 */     if (wordList.size() == 1) {
/* 319 */       return new Property((String)wordList.get(0), true);
/*     */     }
/* 321 */     if (wordList.size() == 2) {
/* 322 */       boolean asc = isAscending((String)wordList.get(1));
/* 323 */       return new Property((String)wordList.get(0), asc);
/*     */     } 
/* 325 */     String m = "Expecting a max of 2 words in [" + Arrays.toString(pairs) + "] but got " + wordList.size();
/*     */     
/* 327 */     throw new RuntimeException(m);
/*     */   }
/*     */   
/*     */   private boolean isAscending(String s) {
/* 331 */     s = s.toLowerCase();
/* 332 */     if (s.startsWith("asc")) {
/* 333 */       return true;
/*     */     }
/* 335 */     if (s.startsWith("desc")) {
/* 336 */       return false;
/*     */     }
/* 338 */     String m = "Expecting [" + s + "] to be asc or desc?";
/* 339 */     throw new RuntimeException(m);
/*     */   }
/*     */ 
/*     */   
/* 343 */   private boolean isEmptyString(String s) { return (s == null || s.length() == 0); }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebean\OrderBy.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */