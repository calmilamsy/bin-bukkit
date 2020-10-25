/*     */ package com.google.common.collect;
/*     */ 
/*     */ import com.google.common.annotations.Beta;
/*     */ import com.google.common.annotations.GwtCompatible;
/*     */ import com.google.common.primitives.Booleans;
/*     */ import com.google.common.primitives.Ints;
/*     */ import com.google.common.primitives.Longs;
/*     */ import java.util.Comparator;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Beta
/*     */ @GwtCompatible
/*     */ public abstract class ComparisonChain
/*     */ {
/*     */   private ComparisonChain() {}
/*     */   
/*  64 */   public static ComparisonChain start() { return ACTIVE; }
/*     */ 
/*     */   
/*  67 */   private static final ComparisonChain ACTIVE = new ComparisonChain()
/*     */     {
/*     */       public ComparisonChain compare(Comparable left, Comparable right)
/*     */       {
/*  71 */         return classify(left.compareTo(right));
/*     */       }
/*     */ 
/*     */       
/*  75 */       public <T> ComparisonChain compare(@Nullable T left, @Nullable T right, Comparator<T> comparator) { return classify(comparator.compare(left, right)); }
/*     */ 
/*     */       
/*  78 */       public ComparisonChain compare(int left, int right) { return classify(Ints.compare(left, right)); }
/*     */ 
/*     */       
/*  81 */       public ComparisonChain compare(long left, long right) { return classify(Longs.compare(left, right)); }
/*     */ 
/*     */       
/*  84 */       public ComparisonChain compare(float left, float right) { return classify(Float.compare(left, right)); }
/*     */ 
/*     */       
/*  87 */       public ComparisonChain compare(double left, double right) { return classify(Double.compare(left, right)); }
/*     */ 
/*     */       
/*  90 */       public ComparisonChain compare(boolean left, boolean right) { return classify(Booleans.compare(left, right)); }
/*     */ 
/*     */       
/*  93 */       ComparisonChain classify(int result) { return (result < 0) ? LESS : ((result > 0) ? GREATER : ACTIVE); }
/*     */ 
/*     */       
/*  96 */       public int result() { return 0; }
/*     */     };
/*     */ 
/*     */   
/* 100 */   private static final ComparisonChain LESS = new InactiveComparisonChain(-1); public abstract ComparisonChain compare(Comparable<?> paramComparable1, Comparable<?> paramComparable2); public abstract <T> ComparisonChain compare(@Nullable T paramT1, @Nullable T paramT2, Comparator<T> paramComparator); public abstract ComparisonChain compare(int paramInt1, int paramInt2);
/*     */   public abstract ComparisonChain compare(long paramLong1, long paramLong2);
/* 102 */   private static final ComparisonChain GREATER = new InactiveComparisonChain(true); public abstract ComparisonChain compare(float paramFloat1, float paramFloat2);
/*     */   public abstract ComparisonChain compare(double paramDouble1, double paramDouble2);
/*     */   public abstract ComparisonChain compare(boolean paramBoolean1, boolean paramBoolean2);
/*     */   public abstract int result();
/*     */   private static final class InactiveComparisonChain extends ComparisonChain { InactiveComparisonChain(int result) {
/* 107 */       super(null);
/* 108 */       this.result = result;
/*     */     }
/*     */     
/*     */     final int result;
/*     */     
/* 113 */     public ComparisonChain compare(@Nullable Comparable left, @Nullable Comparable right) { return this; }
/*     */ 
/*     */ 
/*     */     
/* 117 */     public <T> ComparisonChain compare(@Nullable T left, @Nullable T right, @Nullable Comparator<T> comparator) { return this; }
/*     */ 
/*     */     
/* 120 */     public ComparisonChain compare(int left, int right) { return this; }
/*     */ 
/*     */     
/* 123 */     public ComparisonChain compare(long left, long right) { return this; }
/*     */ 
/*     */     
/* 126 */     public ComparisonChain compare(float left, float right) { return this; }
/*     */ 
/*     */     
/* 129 */     public ComparisonChain compare(double left, double right) { return this; }
/*     */ 
/*     */     
/* 132 */     public ComparisonChain compare(boolean left, boolean right) { return this; }
/*     */ 
/*     */     
/* 135 */     public int result() { return this.result; } }
/*     */ 
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\google\common\collect\ComparisonChain.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */