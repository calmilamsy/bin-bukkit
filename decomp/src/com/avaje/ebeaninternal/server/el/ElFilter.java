/*     */ package com.avaje.ebeaninternal.server.el;
/*     */ 
/*     */ import com.avaje.ebean.Filter;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import java.util.regex.Pattern;
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
/*     */ public final class ElFilter<T>
/*     */   extends Object
/*     */   implements Filter<T>
/*     */ {
/*     */   private final BeanDescriptor<T> beanDescriptor;
/*     */   private ArrayList<ElMatcher<T>> matches;
/*     */   private int maxRows;
/*     */   private String sortByClause;
/*     */   
/*     */   public ElFilter(BeanDescriptor<T> beanDescriptor) {
/*  37 */     this.matches = new ArrayList();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  44 */     this.beanDescriptor = beanDescriptor;
/*     */   }
/*     */ 
/*     */   
/*     */   private Object convertValue(String propertyName, Object value) {
/*  49 */     ElPropertyValue elGetValue = this.beanDescriptor.getElGetValue(propertyName);
/*  50 */     return elGetValue.elConvertType(value);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*  55 */   private ElComparator<T> getElComparator(String propertyName) { return this.beanDescriptor.getElComparator(propertyName); }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  60 */   private ElPropertyValue getElGetValue(String propertyName) { return this.beanDescriptor.getElGetValue(propertyName); }
/*     */ 
/*     */   
/*     */   public Filter<T> sort(String sortByClause) {
/*  64 */     this.sortByClause = sortByClause;
/*  65 */     return this;
/*     */   }
/*     */   
/*     */   protected boolean isMatch(T bean) {
/*  69 */     for (int i = 0; i < this.matches.size(); i++) {
/*  70 */       ElMatcher<T> matcher = (ElMatcher)this.matches.get(i);
/*  71 */       if (!matcher.isMatch(bean)) {
/*  72 */         return false;
/*     */       }
/*     */     } 
/*  75 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Filter<T> in(String propertyName, Set<?> matchingValues) {
/*  81 */     ElPropertyValue elGetValue = getElGetValue(propertyName);
/*     */     
/*  83 */     this.matches.add(new ElMatchBuilder.InSet(matchingValues, elGetValue));
/*  84 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public Filter<T> eq(String propertyName, Object value) {
/*  89 */     value = convertValue(propertyName, value);
/*  90 */     ElComparator<T> comparator = getElComparator(propertyName);
/*     */     
/*  92 */     this.matches.add(new ElMatchBuilder.Eq(value, comparator));
/*  93 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Filter<T> ne(String propertyName, Object value) {
/*  99 */     value = convertValue(propertyName, value);
/* 100 */     ElComparator<T> comparator = getElComparator(propertyName);
/*     */     
/* 102 */     this.matches.add(new ElMatchBuilder.Ne(value, comparator));
/* 103 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public Filter<T> between(String propertyName, Object min, Object max) {
/* 108 */     ElPropertyValue elGetValue = getElGetValue(propertyName);
/* 109 */     min = elGetValue.elConvertType(min);
/* 110 */     max = elGetValue.elConvertType(max);
/*     */     
/* 112 */     ElComparator<T> elComparator = getElComparator(propertyName);
/*     */     
/* 114 */     this.matches.add(new ElMatchBuilder.Between(min, max, elComparator));
/* 115 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Filter<T> gt(String propertyName, Object value) {
/* 121 */     value = convertValue(propertyName, value);
/* 122 */     ElComparator<T> comparator = getElComparator(propertyName);
/*     */     
/* 124 */     this.matches.add(new ElMatchBuilder.Gt(value, comparator));
/* 125 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public Filter<T> ge(String propertyName, Object value) {
/* 130 */     value = convertValue(propertyName, value);
/* 131 */     ElComparator<T> comparator = getElComparator(propertyName);
/*     */     
/* 133 */     this.matches.add(new ElMatchBuilder.Ge(value, comparator));
/* 134 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public Filter<T> ieq(String propertyName, String value) {
/* 139 */     ElPropertyValue elGetValue = getElGetValue(propertyName);
/*     */     
/* 141 */     this.matches.add(new ElMatchBuilder.Ieq(elGetValue, value));
/* 142 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Filter<T> isNotNull(String propertyName) {
/* 148 */     ElPropertyValue elGetValue = getElGetValue(propertyName);
/*     */     
/* 150 */     this.matches.add(new ElMatchBuilder.IsNotNull(elGetValue));
/* 151 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Filter<T> isNull(String propertyName) {
/* 157 */     ElPropertyValue elGetValue = getElGetValue(propertyName);
/*     */     
/* 159 */     this.matches.add(new ElMatchBuilder.IsNull(elGetValue));
/* 160 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Filter<T> le(String propertyName, Object value) {
/* 166 */     value = convertValue(propertyName, value);
/* 167 */     ElComparator<T> comparator = getElComparator(propertyName);
/*     */     
/* 169 */     this.matches.add(new ElMatchBuilder.Le(value, comparator));
/* 170 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Filter<T> lt(String propertyName, Object value) {
/* 176 */     value = convertValue(propertyName, value);
/* 177 */     ElComparator<T> comparator = getElComparator(propertyName);
/*     */     
/* 179 */     this.matches.add(new ElMatchBuilder.Lt(value, comparator));
/* 180 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 185 */   public Filter<T> regex(String propertyName, String regEx) { return regex(propertyName, regEx, 0); }
/*     */ 
/*     */ 
/*     */   
/*     */   public Filter<T> regex(String propertyName, String regEx, int options) {
/* 190 */     ElPropertyValue elGetValue = getElGetValue(propertyName);
/*     */     
/* 192 */     this.matches.add(new ElMatchBuilder.RegularExpr(elGetValue, regEx, options));
/* 193 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public Filter<T> contains(String propertyName, String value) {
/* 198 */     String quote = ".*" + Pattern.quote(value) + ".*";
/*     */     
/* 200 */     ElPropertyValue elGetValue = getElGetValue(propertyName);
/* 201 */     this.matches.add(new ElMatchBuilder.RegularExpr(elGetValue, quote, false));
/* 202 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public Filter<T> icontains(String propertyName, String value) {
/* 207 */     String quote = ".*" + Pattern.quote(value) + ".*";
/*     */     
/* 209 */     ElPropertyValue elGetValue = getElGetValue(propertyName);
/* 210 */     this.matches.add(new ElMatchBuilder.RegularExpr(elGetValue, quote, 2));
/* 211 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Filter<T> endsWith(String propertyName, String value) {
/* 217 */     ElPropertyValue elGetValue = getElGetValue(propertyName);
/* 218 */     this.matches.add(new ElMatchBuilder.EndsWith(elGetValue, value));
/* 219 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public Filter<T> startsWith(String propertyName, String value) {
/* 224 */     ElPropertyValue elGetValue = getElGetValue(propertyName);
/* 225 */     this.matches.add(new ElMatchBuilder.StartsWith(elGetValue, value));
/* 226 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public Filter<T> iendsWith(String propertyName, String value) {
/* 231 */     ElPropertyValue elGetValue = getElGetValue(propertyName);
/* 232 */     this.matches.add(new ElMatchBuilder.IEndsWith(elGetValue, value));
/* 233 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public Filter<T> istartsWith(String propertyName, String value) {
/* 238 */     ElPropertyValue elGetValue = getElGetValue(propertyName);
/* 239 */     this.matches.add(new ElMatchBuilder.IStartsWith(elGetValue, value));
/* 240 */     return this;
/*     */   }
/*     */   
/*     */   public Filter<T> maxRows(int maxRows) {
/* 244 */     this.maxRows = maxRows;
/* 245 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<T> filter(List<T> list) {
/* 250 */     if (this.sortByClause != null) {
/*     */       
/* 252 */       list = new ArrayList<T>(list);
/* 253 */       this.beanDescriptor.sort(list, this.sortByClause);
/*     */     } 
/*     */     
/* 256 */     ArrayList<T> filterList = new ArrayList<T>();
/*     */     
/* 258 */     for (int i = 0; i < list.size(); i++) {
/* 259 */       T t = (T)list.get(i);
/* 260 */       if (isMatch(t)) {
/* 261 */         filterList.add(t);
/* 262 */         if (this.maxRows > 0 && filterList.size() >= this.maxRows) {
/*     */           break;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 268 */     return filterList;
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\el\ElFilter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */