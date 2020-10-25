/*     */ package com.avaje.ebeaninternal.server.el;
/*     */ 
/*     */ import java.util.HashSet;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class ElMatchBuilder
/*     */ {
/*     */   static class RegularExpr<T>
/*     */     extends Object
/*     */     implements ElMatcher<T>
/*     */   {
/*     */     final ElPropertyValue elGetValue;
/*     */     final String value;
/*     */     final Pattern pattern;
/*     */     
/*     */     RegularExpr(ElPropertyValue elGetValue, String value, int options) {
/*  42 */       this.elGetValue = elGetValue;
/*  43 */       this.value = value;
/*  44 */       this.pattern = Pattern.compile(value, options);
/*     */     }
/*     */     
/*     */     public boolean isMatch(T bean) {
/*  48 */       String v = (String)this.elGetValue.elGetValue(bean);
/*  49 */       return this.pattern.matcher(v).matches();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   static abstract class BaseString<T>
/*     */     extends Object
/*     */     implements ElMatcher<T>
/*     */   {
/*     */     final ElPropertyValue elGetValue;
/*     */     final String value;
/*     */     
/*     */     public BaseString(ElPropertyValue elGetValue, String value) {
/*  62 */       this.elGetValue = elGetValue;
/*  63 */       this.value = value;
/*     */     }
/*     */     
/*     */     public abstract boolean isMatch(T param1T);
/*     */   }
/*     */   
/*     */   static class Ieq<T>
/*     */     extends BaseString<T> {
/*  71 */     Ieq(ElPropertyValue elGetValue, String value) { super(elGetValue, value); }
/*     */ 
/*     */     
/*     */     public boolean isMatch(T bean) {
/*  75 */       String v = (String)this.elGetValue.elGetValue(bean);
/*  76 */       return this.value.equalsIgnoreCase(v);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   static class IStartsWith<T>
/*     */     extends Object
/*     */     implements ElMatcher<T>
/*     */   {
/*     */     final ElPropertyValue elGetValue;
/*     */     final CharMatch charMatch;
/*     */     
/*     */     IStartsWith(ElPropertyValue elGetValue, String value) {
/*  89 */       this.elGetValue = elGetValue;
/*  90 */       this.charMatch = new CharMatch(value);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isMatch(T bean) {
/*  95 */       String v = (String)this.elGetValue.elGetValue(bean);
/*  96 */       return this.charMatch.startsWith(v);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   static class IEndsWith<T>
/*     */     extends Object
/*     */     implements ElMatcher<T>
/*     */   {
/*     */     final ElPropertyValue elGetValue;
/*     */     final CharMatch charMatch;
/*     */     
/*     */     IEndsWith(ElPropertyValue elGetValue, String value) {
/* 109 */       this.elGetValue = elGetValue;
/* 110 */       this.charMatch = new CharMatch(value);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isMatch(T bean) {
/* 115 */       String v = (String)this.elGetValue.elGetValue(bean);
/* 116 */       return this.charMatch.endsWith(v);
/*     */     }
/*     */   }
/*     */   
/*     */   static class StartsWith<T>
/*     */     extends BaseString<T> {
/* 122 */     StartsWith(ElPropertyValue elGetValue, String value) { super(elGetValue, value); }
/*     */ 
/*     */     
/*     */     public boolean isMatch(T bean) {
/* 126 */       String v = (String)this.elGetValue.elGetValue(bean);
/* 127 */       return this.value.startsWith(v);
/*     */     }
/*     */   }
/*     */   
/*     */   static class EndsWith<T>
/*     */     extends BaseString<T> {
/* 133 */     EndsWith(ElPropertyValue elGetValue, String value) { super(elGetValue, value); }
/*     */ 
/*     */     
/*     */     public boolean isMatch(T bean) {
/* 137 */       String v = (String)this.elGetValue.elGetValue(bean);
/* 138 */       return this.value.endsWith(v);
/*     */     }
/*     */   }
/*     */   
/*     */   static class IsNull<T>
/*     */     extends Object
/*     */     implements ElMatcher<T> {
/*     */     final ElPropertyValue elGetValue;
/*     */     
/* 147 */     public IsNull(ElPropertyValue elGetValue) { this.elGetValue = elGetValue; }
/*     */ 
/*     */ 
/*     */     
/* 151 */     public boolean isMatch(T bean) { return (null == this.elGetValue.elGetValue(bean)); }
/*     */   }
/*     */   
/*     */   static class IsNotNull<T>
/*     */     extends Object
/*     */     implements ElMatcher<T>
/*     */   {
/*     */     final ElPropertyValue elGetValue;
/*     */     
/* 160 */     public IsNotNull(ElPropertyValue elGetValue) { this.elGetValue = elGetValue; }
/*     */ 
/*     */ 
/*     */     
/* 164 */     public boolean isMatch(T bean) { return (null != this.elGetValue.elGetValue(bean)); }
/*     */   }
/*     */   
/*     */   static abstract class Base<T>
/*     */     extends Object
/*     */     implements ElMatcher<T>
/*     */   {
/*     */     final Object filterValue;
/*     */     final ElComparator<T> comparator;
/*     */     
/*     */     public Base(Object filterValue, ElComparator<T> comparator) {
/* 175 */       this.filterValue = filterValue;
/* 176 */       this.comparator = comparator;
/*     */     }
/*     */     
/*     */     public abstract boolean isMatch(T param1T);
/*     */   }
/*     */   
/*     */   static class InSet<T>
/*     */     extends Object
/*     */     implements ElMatcher<T> {
/*     */     final Set<?> set;
/*     */     final ElPropertyValue elGetValue;
/*     */     
/*     */     public InSet(Set<?> set, ElPropertyValue elGetValue) {
/* 189 */       this.set = new HashSet(set);
/* 190 */       this.elGetValue = elGetValue;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isMatch(T bean) {
/* 195 */       Object value = this.elGetValue.elGetValue(bean);
/* 196 */       if (value == null) {
/* 197 */         return false;
/*     */       }
/*     */       
/* 200 */       return this.set.contains(value);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static class Eq<T>
/*     */     extends Base<T>
/*     */   {
/* 210 */     public Eq(Object filterValue, ElComparator<T> comparator) { super(filterValue, comparator); }
/*     */ 
/*     */ 
/*     */     
/* 214 */     public boolean isMatch(T value) { return (this.comparator.compareValue(this.filterValue, value) == 0); }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static class Ne<T>
/*     */     extends Base<T>
/*     */   {
/* 224 */     public Ne(Object filterValue, ElComparator<T> comparator) { super(filterValue, comparator); }
/*     */ 
/*     */ 
/*     */     
/* 228 */     public boolean isMatch(T value) { return (this.comparator.compareValue(this.filterValue, value) != 0); }
/*     */   }
/*     */ 
/*     */   
/*     */   static class Between<T>
/*     */     extends Object
/*     */     implements ElMatcher<T>
/*     */   {
/*     */     final Object min;
/*     */     
/*     */     final Object max;
/*     */     final ElComparator<T> comparator;
/*     */     
/*     */     Between(Object min, Object max, ElComparator<T> comparator) {
/* 242 */       this.min = min;
/* 243 */       this.max = max;
/* 244 */       this.comparator = comparator;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 249 */     public boolean isMatch(T value) { return (this.comparator.compareValue(this.min, value) <= 0 && this.comparator.compareValue(this.max, value) >= 0); }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static class Gt<T>
/*     */     extends Base<T>
/*     */   {
/* 259 */     Gt(Object filterValue, ElComparator<T> comparator) { super(filterValue, comparator); }
/*     */ 
/*     */ 
/*     */     
/* 263 */     public boolean isMatch(T value) { return (this.comparator.compareValue(this.filterValue, value) == -1); }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static class Ge<T>
/*     */     extends Base<T>
/*     */   {
/* 272 */     Ge(Object filterValue, ElComparator<T> comparator) { super(filterValue, comparator); }
/*     */ 
/*     */ 
/*     */     
/* 276 */     public boolean isMatch(T value) { return (this.comparator.compareValue(this.filterValue, value) >= 0); }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static class Le<T>
/*     */     extends Base<T>
/*     */   {
/* 285 */     Le(Object filterValue, ElComparator<T> comparator) { super(filterValue, comparator); }
/*     */ 
/*     */ 
/*     */     
/* 289 */     public boolean isMatch(T value) { return (this.comparator.compareValue(this.filterValue, value) <= 0); }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static class Lt<T>
/*     */     extends Base<T>
/*     */   {
/* 298 */     Lt(Object filterValue, ElComparator<T> comparator) { super(filterValue, comparator); }
/*     */ 
/*     */ 
/*     */     
/* 302 */     public boolean isMatch(T value) { return (this.comparator.compareValue(this.filterValue, value) == 1); }
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\el\ElMatchBuilder.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */