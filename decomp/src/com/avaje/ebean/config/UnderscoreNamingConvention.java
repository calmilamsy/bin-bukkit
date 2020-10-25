/*     */ package com.avaje.ebean.config;
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
/*     */ public class UnderscoreNamingConvention
/*     */   extends AbstractNamingConvention
/*     */ {
/*     */   private boolean forceUpperCase = false;
/*     */   private boolean digitsCompressed = true;
/*     */   
/*  44 */   public UnderscoreNamingConvention(String sequenceFormat) { super(sequenceFormat); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public UnderscoreNamingConvention() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  63 */   public TableName getTableNameByConvention(Class<?> beanClass) { return new TableName(getCatalog(), getSchema(), toUnderscoreFromCamel(beanClass.getSimpleName())); }
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
/*  75 */   public String getColumnFromProperty(Class<?> beanClass, String propertyName) { return toUnderscoreFromCamel(propertyName); }
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
/*  87 */   public String getPropertyFromColumn(Class<?> beanClass, String dbColumnName) { return toCamelFromUnderscore(dbColumnName); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  97 */   public boolean isForceUpperCase() { return this.forceUpperCase; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 104 */   public void setForceUpperCase(boolean forceUpperCase) { this.forceUpperCase = forceUpperCase; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 111 */   public boolean isDigitsCompressed() { return this.digitsCompressed; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 118 */   public void setDigitsCompressed(boolean digitsCompressed) { this.digitsCompressed = digitsCompressed; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String toUnderscoreFromCamel(String camelCase) {
/* 128 */     int lastUpper = -1;
/* 129 */     StringBuffer sb = new StringBuffer();
/* 130 */     for (i = 0; i < camelCase.length(); i++) {
/* 131 */       char c = camelCase.charAt(i);
/*     */       
/* 133 */       if ('_' == c) {
/*     */         
/* 135 */         sb.append(c);
/* 136 */         lastUpper = i;
/* 137 */       } else if (Character.isDigit(c)) {
/* 138 */         if (i > lastUpper + 1 && !this.digitsCompressed) {
/* 139 */           sb.append("_");
/*     */         }
/* 141 */         sb.append(c);
/* 142 */         lastUpper = i;
/*     */       }
/* 144 */       else if (Character.isUpperCase(c)) {
/* 145 */         if (i > lastUpper + 1) {
/* 146 */           sb.append("_");
/*     */         }
/* 148 */         sb.append(Character.toLowerCase(c));
/* 149 */         lastUpper = i;
/*     */       } else {
/*     */         
/* 152 */         sb.append(c);
/*     */       } 
/*     */     } 
/* 155 */     String ret = sb.toString();
/* 156 */     if (this.forceUpperCase) {
/* 157 */       ret = ret.toUpperCase();
/*     */     }
/* 159 */     return ret;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String toCamelFromUnderscore(String underscore) {
/* 171 */     StringBuffer result = new StringBuffer();
/* 172 */     String[] vals = underscore.split("_");
/*     */     
/* 174 */     for (int i = 0; i < vals.length; i++) {
/* 175 */       String lower = vals[i].toLowerCase();
/* 176 */       if (i > 0) {
/* 177 */         char c = Character.toUpperCase(lower.charAt(0));
/* 178 */         result.append(c);
/* 179 */         result.append(lower.substring(1));
/*     */       } else {
/* 181 */         result.append(lower);
/*     */       } 
/*     */     } 
/*     */     
/* 185 */     return result.toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebean\config\UnderscoreNamingConvention.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */