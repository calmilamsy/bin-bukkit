/*     */ package com.google.common.base;
/*     */ 
/*     */ import com.google.common.annotations.Beta;
/*     */ import com.google.common.annotations.GwtCompatible;
/*     */ import com.google.common.annotations.GwtIncompatible;
/*     */ import java.io.Serializable;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.regex.Pattern;
/*     */ import javax.annotation.Nullable;
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
/*     */ @GwtCompatible
/*     */ public final class Predicates
/*     */ {
/*     */   @GwtCompatible(serializable = true)
/*  57 */   public static <T> Predicate<T> alwaysTrue() { return AlwaysTruePredicate.INSTANCE; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @GwtCompatible(serializable = true)
/*  66 */   public static <T> Predicate<T> alwaysFalse() { return AlwaysFalsePredicate.INSTANCE; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  75 */   public static <T> Predicate<T> isNull() { return IsNullPredicate.INSTANCE; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  84 */   public static <T> Predicate<T> notNull() { return NotNullPredicate.INSTANCE; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  92 */   public static <T> Predicate<T> not(Predicate<T> predicate) { return new NotPredicate(predicate); }
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
/* 106 */   public static <T> Predicate<T> and(Iterable<? extends Predicate<? super T>> components) { return new AndPredicate(defensiveCopy(components), null); }
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
/* 119 */   public static <T> Predicate<T> and(Predicate... components) { return new AndPredicate(defensiveCopy(components), null); }
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
/* 130 */   public static <T> Predicate<T> and(Predicate<? super T> first, Predicate<? super T> second) { return new AndPredicate(asList((Predicate)Preconditions.checkNotNull(first), (Predicate)Preconditions.checkNotNull(second)), null); }
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
/* 145 */   public static <T> Predicate<T> or(Iterable<? extends Predicate<? super T>> components) { return new OrPredicate(defensiveCopy(components), null); }
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
/* 158 */   public static <T> Predicate<T> or(Predicate... components) { return new OrPredicate(defensiveCopy(components), null); }
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
/* 169 */   public static <T> Predicate<T> or(Predicate<? super T> first, Predicate<? super T> second) { return new OrPredicate(asList((Predicate)Preconditions.checkNotNull(first), (Predicate)Preconditions.checkNotNull(second)), null); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 178 */   public static <T> Predicate<T> equalTo(@Nullable T target) { return (target == null) ? isNull() : new IsEqualToPredicate(target, null); }
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
/*     */   @GwtIncompatible("Class.isInstance")
/* 194 */   public static Predicate<Object> instanceOf(Class<?> clazz) { return new InstanceOfPredicate(clazz, null); }
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
/* 210 */   public static <T> Predicate<T> in(Collection<? extends T> target) { return new InPredicate(target, null); }
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
/* 221 */   public static <A, B> Predicate<A> compose(Predicate<B> predicate, Function<A, ? extends B> function) { return new CompositionPredicate(predicate, function, null); }
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
/*     */   @Beta
/*     */   @GwtIncompatible("java.util.regex.Pattern")
/* 236 */   public static Predicate<CharSequence> containsPattern(String pattern) { return new ContainsPatternPredicate(pattern); }
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
/*     */   @GwtIncompatible("java.util.regex.Pattern")
/* 249 */   public static Predicate<CharSequence> contains(Pattern pattern) { return new ContainsPatternPredicate(pattern); }
/*     */ 
/*     */   
/*     */   private static class ContainsPatternPredicate
/*     */     extends Object
/*     */     implements Predicate<CharSequence>, Serializable
/*     */   {
/*     */     final Pattern pattern;
/*     */     
/*     */     private static final long serialVersionUID = 0L;
/*     */ 
/*     */     
/* 261 */     ContainsPatternPredicate(Pattern pattern) { this.pattern = (Pattern)Preconditions.checkNotNull(pattern); }
/*     */ 
/*     */ 
/*     */     
/* 265 */     ContainsPatternPredicate(String patternStr) { this(Pattern.compile(patternStr)); }
/*     */ 
/*     */ 
/*     */     
/* 269 */     public boolean apply(CharSequence t) { return this.pattern.matcher(t).find(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 276 */     public int hashCode() { return Objects.hashCode(new Object[] { this.pattern.pattern(), Integer.valueOf(this.pattern.flags()) }); }
/*     */ 
/*     */     
/*     */     public boolean equals(@Nullable Object obj) {
/* 280 */       if (obj instanceof ContainsPatternPredicate) {
/* 281 */         ContainsPatternPredicate that = (ContainsPatternPredicate)obj;
/*     */ 
/*     */ 
/*     */         
/* 285 */         return (Objects.equal(this.pattern.pattern(), that.pattern.pattern()) && Objects.equal(Integer.valueOf(this.pattern.flags()), Integer.valueOf(that.pattern.flags())));
/*     */       } 
/*     */       
/* 288 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 292 */     public String toString() { return Objects.toStringHelper(this).add("pattern", this.pattern).add("pattern.flags", Integer.toHexString(this.pattern.flags())).toString(); }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   enum AlwaysTruePredicate
/*     */     implements Predicate<Object>
/*     */   {
/* 304 */     INSTANCE;
/*     */ 
/*     */     
/* 307 */     public boolean apply(@Nullable Object o) { return true; }
/*     */ 
/*     */     
/* 310 */     public String toString() { return "AlwaysTrue"; }
/*     */   }
/*     */ 
/*     */   
/*     */   enum AlwaysFalsePredicate
/*     */     implements Predicate<Object>
/*     */   {
/* 317 */     INSTANCE;
/*     */ 
/*     */     
/* 320 */     public boolean apply(@Nullable Object o) { return false; }
/*     */ 
/*     */     
/* 323 */     public String toString() { return "AlwaysFalse"; }
/*     */   }
/*     */   
/*     */   private static class NotPredicate<T>
/*     */     extends Object
/*     */     implements Predicate<T>, Serializable {
/*     */     final Predicate<T> predicate;
/*     */     private static final long serialVersionUID = 0L;
/*     */     
/* 332 */     NotPredicate(Predicate<T> predicate) { this.predicate = (Predicate)Preconditions.checkNotNull(predicate); }
/*     */ 
/*     */     
/* 335 */     public boolean apply(T t) { return !this.predicate.apply(t); }
/*     */ 
/*     */     
/* 338 */     public int hashCode() { return this.predicate.hashCode() ^ 0xFFFFFFFF; }
/*     */     
/*     */     public boolean equals(@Nullable Object obj) {
/* 341 */       if (obj instanceof NotPredicate) {
/* 342 */         NotPredicate<?> that = (NotPredicate)obj;
/* 343 */         return this.predicate.equals(that.predicate);
/*     */       } 
/* 345 */       return false;
/*     */     }
/*     */     
/* 348 */     public String toString() { return "Not(" + this.predicate.toString() + ")"; }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 353 */   private static final Joiner commaJoiner = Joiner.on(",");
/*     */   
/*     */   private static class AndPredicate<T>
/*     */     extends Object implements Predicate<T>, Serializable {
/*     */     private final Iterable<? extends Predicate<? super T>> components;
/*     */     private static final long serialVersionUID = 0L;
/*     */     
/* 360 */     private AndPredicate(Iterable<? extends Predicate<? super T>> components) { this.components = components; }
/*     */     
/*     */     public boolean apply(T t) {
/* 363 */       for (Predicate<? super T> predicate : this.components) {
/* 364 */         if (!predicate.apply(t)) {
/* 365 */           return false;
/*     */         }
/*     */       } 
/* 368 */       return true;
/*     */     }
/*     */     public int hashCode() {
/* 371 */       int result = -1;
/* 372 */       for (Predicate<? super T> predicate : this.components) {
/* 373 */         result &= predicate.hashCode();
/*     */       }
/* 375 */       return result;
/*     */     }
/*     */     public boolean equals(@Nullable Object obj) {
/* 378 */       if (obj instanceof AndPredicate) {
/* 379 */         AndPredicate<?> that = (AndPredicate)obj;
/* 380 */         return Predicates.iterableElementsEqual(this.components, that.components);
/*     */       } 
/* 382 */       return false;
/*     */     }
/*     */     
/* 385 */     public String toString() { return "And(" + commaJoiner.join(this.components) + ")"; }
/*     */   }
/*     */   
/*     */   private static class OrPredicate<T>
/*     */     extends Object
/*     */     implements Predicate<T>, Serializable
/*     */   {
/*     */     private final Iterable<? extends Predicate<? super T>> components;
/*     */     private static final long serialVersionUID = 0L;
/*     */     
/* 395 */     private OrPredicate(Iterable<? extends Predicate<? super T>> components) { this.components = components; }
/*     */     
/*     */     public boolean apply(T t) {
/* 398 */       for (Predicate<? super T> predicate : this.components) {
/* 399 */         if (predicate.apply(t)) {
/* 400 */           return true;
/*     */         }
/*     */       } 
/* 403 */       return false;
/*     */     }
/*     */     public int hashCode() {
/* 406 */       int result = 0;
/* 407 */       for (Predicate<? super T> predicate : this.components) {
/* 408 */         result |= predicate.hashCode();
/*     */       }
/* 410 */       return result;
/*     */     }
/*     */     public boolean equals(@Nullable Object obj) {
/* 413 */       if (obj instanceof OrPredicate) {
/* 414 */         OrPredicate<?> that = (OrPredicate)obj;
/* 415 */         return Predicates.iterableElementsEqual(this.components, that.components);
/*     */       } 
/* 417 */       return false;
/*     */     }
/*     */     
/* 420 */     public String toString() { return "Or(" + commaJoiner.join(this.components) + ")"; }
/*     */   }
/*     */ 
/*     */   
/*     */   private static class IsEqualToPredicate<T>
/*     */     extends Object
/*     */     implements Predicate<T>, Serializable
/*     */   {
/*     */     private final T target;
/*     */     private static final long serialVersionUID = 0L;
/*     */     
/* 431 */     private IsEqualToPredicate(T target) { this.target = target; }
/*     */ 
/*     */     
/* 434 */     public boolean apply(T t) { return this.target.equals(t); }
/*     */ 
/*     */     
/* 437 */     public int hashCode() { return this.target.hashCode(); }
/*     */     
/*     */     public boolean equals(@Nullable Object obj) {
/* 440 */       if (obj instanceof IsEqualToPredicate) {
/* 441 */         IsEqualToPredicate<?> that = (IsEqualToPredicate)obj;
/* 442 */         return this.target.equals(that.target);
/*     */       } 
/* 444 */       return false;
/*     */     }
/*     */     
/* 447 */     public String toString() { return "IsEqualTo(" + this.target + ")"; }
/*     */   }
/*     */ 
/*     */   
/*     */   private static class InstanceOfPredicate
/*     */     extends Object
/*     */     implements Predicate<Object>, Serializable
/*     */   {
/*     */     private final Class<?> clazz;
/*     */     private static final long serialVersionUID = 0L;
/*     */     
/* 458 */     private InstanceOfPredicate(Class<?> clazz) { this.clazz = (Class)Preconditions.checkNotNull(clazz); }
/*     */ 
/*     */     
/* 461 */     public boolean apply(@Nullable Object o) { return Platform.isInstance(this.clazz, o); }
/*     */ 
/*     */     
/* 464 */     public int hashCode() { return this.clazz.hashCode(); }
/*     */     
/*     */     public boolean equals(@Nullable Object obj) {
/* 467 */       if (obj instanceof InstanceOfPredicate) {
/* 468 */         InstanceOfPredicate that = (InstanceOfPredicate)obj;
/* 469 */         return (this.clazz == that.clazz);
/*     */       } 
/* 471 */       return false;
/*     */     }
/*     */     
/* 474 */     public String toString() { return "IsInstanceOf(" + this.clazz.getName() + ")"; }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private enum IsNullPredicate
/*     */     implements Predicate<Object>
/*     */   {
/* 482 */     INSTANCE;
/*     */ 
/*     */     
/* 485 */     public boolean apply(@Nullable Object o) { return (o == null); }
/*     */ 
/*     */     
/* 488 */     public String toString() { return "IsNull"; }
/*     */   }
/*     */ 
/*     */   
/*     */   private enum NotNullPredicate
/*     */     implements Predicate<Object>
/*     */   {
/* 495 */     INSTANCE;
/*     */ 
/*     */     
/* 498 */     public boolean apply(@Nullable Object o) { return (o != null); }
/*     */ 
/*     */     
/* 501 */     public String toString() { return "NotNull"; }
/*     */   }
/*     */   
/*     */   private static class InPredicate<T>
/*     */     extends Object
/*     */     implements Predicate<T>, Serializable {
/*     */     private final Collection<?> target;
/*     */     private static final long serialVersionUID = 0L;
/*     */     
/* 510 */     private InPredicate(Collection<?> target) { this.target = (Collection)Preconditions.checkNotNull(target); }
/*     */ 
/*     */     
/*     */     public boolean apply(T t) {
/*     */       try {
/* 515 */         return this.target.contains(t);
/* 516 */       } catch (NullPointerException e) {
/* 517 */         return false;
/* 518 */       } catch (ClassCastException e) {
/* 519 */         return false;
/*     */       } 
/*     */     }
/*     */     
/*     */     public boolean equals(@Nullable Object obj) {
/* 524 */       if (obj instanceof InPredicate) {
/* 525 */         InPredicate<?> that = (InPredicate)obj;
/* 526 */         return this.target.equals(that.target);
/*     */       } 
/* 528 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 532 */     public int hashCode() { return this.target.hashCode(); }
/*     */ 
/*     */ 
/*     */     
/* 536 */     public String toString() { return "In(" + this.target + ")"; }
/*     */   }
/*     */   
/*     */   private static class CompositionPredicate<A, B>
/*     */     extends Object
/*     */     implements Predicate<A>, Serializable
/*     */   {
/*     */     final Predicate<B> p;
/*     */     final Function<A, ? extends B> f;
/*     */     private static final long serialVersionUID = 0L;
/*     */     
/*     */     private CompositionPredicate(Predicate<B> p, Function<A, ? extends B> f) {
/* 548 */       this.p = (Predicate)Preconditions.checkNotNull(p);
/* 549 */       this.f = (Function)Preconditions.checkNotNull(f);
/*     */     }
/*     */ 
/*     */     
/* 553 */     public boolean apply(A a) { return this.p.apply(this.f.apply(a)); }
/*     */ 
/*     */     
/*     */     public boolean equals(@Nullable Object obj) {
/* 557 */       if (obj instanceof CompositionPredicate) {
/* 558 */         CompositionPredicate<?, ?> that = (CompositionPredicate)obj;
/* 559 */         return (this.f.equals(that.f) && this.p.equals(that.p));
/*     */       } 
/* 561 */       return false;
/*     */     }
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
/* 578 */     public int hashCode() { return this.f.hashCode() ^ this.p.hashCode(); }
/*     */ 
/*     */ 
/*     */     
/* 582 */     public String toString() { return this.p.toString() + "(" + this.f.toString() + ")"; }
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean iterableElementsEqual(Iterable<?> iterable1, Iterable<?> iterable2) {
/* 600 */     Iterator<?> iterator1 = iterable1.iterator();
/* 601 */     Iterator<?> iterator2 = iterable2.iterator();
/* 602 */     while (iterator1.hasNext()) {
/* 603 */       if (!iterator2.hasNext()) {
/* 604 */         return false;
/*     */       }
/* 606 */       if (!iterator1.next().equals(iterator2.next())) {
/* 607 */         return false;
/*     */       }
/*     */     } 
/* 610 */     return !iterator2.hasNext();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 616 */   private static <T> List<Predicate<? super T>> asList(Predicate<? super T> first, Predicate<? super T> second) { return Arrays.asList(new Predicate[] { first, second }); }
/*     */ 
/*     */ 
/*     */   
/* 620 */   private static <T> List<T> defensiveCopy(T... array) { return defensiveCopy(Arrays.asList(array)); }
/*     */ 
/*     */   
/*     */   static <T> List<T> defensiveCopy(Iterable<T> iterable) {
/* 624 */     ArrayList<T> list = new ArrayList<T>();
/* 625 */     for (T element : iterable) {
/* 626 */       list.add(Preconditions.checkNotNull(element));
/*     */     }
/* 628 */     return list;
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\google\common\base\Predicates.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */