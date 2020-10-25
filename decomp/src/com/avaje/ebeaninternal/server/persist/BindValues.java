/*     */ package com.avaje.ebeaninternal.server.persist;
/*     */ 
/*     */ import java.util.ArrayList;
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
/*     */ public class BindValues
/*     */ {
/*     */   int commentCount;
/*  31 */   final ArrayList<Value> list = new ArrayList();
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
/*  43 */   public int size() { return this.list.size() - this.commentCount; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  53 */   public void add(Object value, int dbType, String name) { this.list.add(new Value(value, dbType, name)); }
/*     */ 
/*     */   
/*     */   public void addComment(String comment) {
/*  57 */     this.commentCount++;
/*  58 */     this.list.add(new Value(comment));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  65 */   public ArrayList<Value> values() { return this.list; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class Value
/*     */   {
/*     */     private final Object value;
/*     */ 
/*     */     
/*     */     private final int dbType;
/*     */ 
/*     */     
/*     */     private final String name;
/*     */ 
/*     */     
/*     */     private final boolean isComment;
/*     */ 
/*     */ 
/*     */     
/*     */     public Value(String comment) {
/*  86 */       this.name = comment;
/*  87 */       this.isComment = true;
/*  88 */       this.value = null;
/*  89 */       this.dbType = 0;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Value(Object value, int dbType, String name) {
/*  97 */       this.isComment = false;
/*  98 */       this.value = value;
/*  99 */       this.dbType = dbType;
/* 100 */       this.name = name;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 107 */     public boolean isComment() { return this.isComment; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 114 */     public int getDbType() { return this.dbType; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 121 */     public Object getValue() { return this.value; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 128 */     public String getName() { return this.name; }
/*     */ 
/*     */ 
/*     */     
/* 132 */     public String toString() { return "" + this.value; }
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\persist\BindValues.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */