/*     */ package com.avaje.ebean;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.util.ArrayList;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class InvalidValue
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 2408556605456324913L;
/*  56 */   private static final Object[] EMPTY = new Object[0];
/*     */   
/*     */   private final String beanType;
/*     */   
/*     */   private final String propertyName;
/*     */   
/*     */   private final String validatorKey;
/*     */   
/*     */   private final Object value;
/*     */   
/*     */   private final InvalidValue[] children;
/*     */   
/*     */   private final Object[] validatorAttributes;
/*     */   
/*     */   private String message;
/*     */   
/*     */   public InvalidValue(String validatorKey, String beanType, Object bean, InvalidValue[] children) {
/*  73 */     this.validatorKey = validatorKey;
/*  74 */     this.validatorAttributes = EMPTY;
/*  75 */     this.beanType = beanType;
/*  76 */     this.propertyName = null;
/*  77 */     this.value = bean;
/*  78 */     this.children = children;
/*     */   }
/*     */   
/*     */   public InvalidValue(String validatorKey, Object[] validatorAttributes, String beanType, String propertyName, Object value) {
/*  82 */     this.validatorKey = validatorKey;
/*  83 */     this.validatorAttributes = validatorAttributes;
/*  84 */     this.beanType = beanType;
/*  85 */     this.propertyName = propertyName;
/*  86 */     this.value = value;
/*  87 */     this.children = null;
/*     */   }
/*     */ 
/*     */   
/*  91 */   public String getBeanType() { return this.beanType; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  98 */   public String getPropertyName() { return this.propertyName; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 105 */   public String getValidatorKey() { return this.validatorKey; }
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
/* 121 */   public Object[] getValidatorAttributes() { return this.validatorAttributes; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 128 */   public Object getValue() { return this.value; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 135 */   public InvalidValue[] getChildren() { return this.children; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 142 */   public String getMessage() { return this.message; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 149 */   public void setMessage(String message) { this.message = message; }
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
/* 161 */   public boolean isBean() { return !isError(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 168 */   public boolean isError() { return (this.children == null); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public InvalidValue[] getErrors() {
/* 176 */     ArrayList<InvalidValue> list = new ArrayList<InvalidValue>();
/* 177 */     buildList(list);
/*     */     
/* 179 */     return toArray(list);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void buildList(List<InvalidValue> list) {
/* 186 */     if (isError()) {
/* 187 */       list.add(this);
/*     */     } else {
/* 189 */       for (int i = 0; i < this.children.length; i++) {
/* 190 */         this.children[i].buildList(list);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 197 */     if (isError()) {
/* 198 */       return "errorKey=" + this.validatorKey + " type=" + this.beanType + " property=" + this.propertyName + " value=" + this.value;
/*     */     }
/*     */     
/* 201 */     if (this.children.length == 1) {
/* 202 */       return this.children[0].toString();
/*     */     }
/*     */ 
/*     */     
/* 206 */     StringBuilder sb = new StringBuilder(50);
/* 207 */     sb.append("\r\nCHILDREN[").append(this.children.length).append("]");
/* 208 */     for (int i = 0; i < this.children.length; i++) {
/* 209 */       sb.append(this.children[i].toString()).append(", ");
/*     */     }
/* 211 */     return sb.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 218 */   public static InvalidValue[] toArray(List<InvalidValue> list) { return (InvalidValue[])list.toArray(new InvalidValue[list.size()]); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static List<InvalidValue> toList(InvalidValue invalid) {
/* 225 */     ArrayList<InvalidValue> list = new ArrayList<InvalidValue>();
/* 226 */     list.add(invalid);
/* 227 */     return list;
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebean\InvalidValue.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */