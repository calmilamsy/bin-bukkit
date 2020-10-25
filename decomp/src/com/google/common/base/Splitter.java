/*     */ package com.google.common.base;
/*     */ 
/*     */ import com.google.common.annotations.GwtCompatible;
/*     */ import com.google.common.annotations.GwtIncompatible;
/*     */ import java.util.Iterator;
/*     */ import java.util.NoSuchElementException;
/*     */ import java.util.regex.Matcher;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */ public final class Splitter
/*     */ {
/*     */   private final CharMatcher trimmer;
/*     */   private final boolean omitEmptyStrings;
/*     */   private final Strategy strategy;
/*     */   
/*  98 */   private Splitter(Strategy strategy) { this(strategy, false, CharMatcher.NONE); }
/*     */ 
/*     */ 
/*     */   
/*     */   private Splitter(Strategy strategy, boolean omitEmptyStrings, CharMatcher trimmer) {
/* 103 */     this.strategy = strategy;
/* 104 */     this.omitEmptyStrings = omitEmptyStrings;
/* 105 */     this.trimmer = trimmer;
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
/* 117 */   public static Splitter on(char separator) { return on(CharMatcher.is(separator)); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Splitter on(CharMatcher separatorMatcher) {
/* 131 */     Preconditions.checkNotNull(separatorMatcher);
/*     */     
/* 133 */     return new Splitter(new Strategy(separatorMatcher)
/*     */         {
/*     */           public Splitter.SplittingIterator iterator(Splitter splitter, CharSequence toSplit) {
/* 136 */             return new Splitter.SplittingIterator(splitter, toSplit)
/*     */               {
/* 138 */                 int separatorStart(int start) { return separatorMatcher.indexIn(this.toSplit, start); }
/*     */ 
/*     */ 
/*     */                 
/* 142 */                 int separatorEnd(int separatorPosition) { return separatorPosition + 1; }
/*     */               };
/*     */           }
/*     */         });
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
/*     */   public static Splitter on(String separator) {
/* 158 */     Preconditions.checkArgument((separator.length() != 0), "The separator may not be the empty string.");
/*     */ 
/*     */     
/* 161 */     return new Splitter(new Strategy(separator)
/*     */         {
/*     */           public Splitter.SplittingIterator iterator(Splitter splitter, CharSequence toSplit) {
/* 164 */             return new Splitter.SplittingIterator(splitter, toSplit) {
/*     */                 public int separatorStart(int start) {
/* 166 */                   int delimeterLength = separator.length();
/*     */ 
/*     */                   
/* 169 */                   int p = start, last = this.toSplit.length() - delimeterLength;
/* 170 */                   for (; p <= last; p++) {
/* 171 */                     int i = 0; while (true) { if (i < delimeterLength) {
/* 172 */                         if (this.toSplit.charAt(i + p) != separator.charAt(i))
/*     */                           break;  i++;
/*     */                         continue;
/*     */                       } 
/* 176 */                       return p; }
/*     */                   
/* 178 */                   }  return -1;
/*     */                 }
/*     */ 
/*     */                 
/* 182 */                 public int separatorEnd(int separatorPosition) { return separatorPosition + separator.length(); }
/*     */               };
/*     */           }
/*     */         });
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
/*     */   @GwtIncompatible("java.util.regex")
/*     */   public static Splitter on(Pattern separatorPattern) {
/* 203 */     Preconditions.checkNotNull(separatorPattern);
/* 204 */     Preconditions.checkArgument(!separatorPattern.matcher("").matches(), "The pattern may not match the empty string: %s", new Object[] { separatorPattern });
/*     */ 
/*     */     
/* 207 */     return new Splitter(new Strategy(separatorPattern)
/*     */         {
/*     */           public Splitter.SplittingIterator iterator(Splitter splitter, CharSequence toSplit) {
/* 210 */             final Matcher matcher = separatorPattern.matcher(toSplit);
/* 211 */             return new Splitter.SplittingIterator(splitter, toSplit)
/*     */               {
/* 213 */                 public int separatorStart(int start) { return matcher.find(start) ? matcher.start() : -1; }
/*     */ 
/*     */ 
/*     */                 
/* 217 */                 public int separatorEnd(int separatorPosition) { return matcher.end(); }
/*     */               };
/*     */           }
/*     */         });
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
/*     */ 
/*     */ 
/*     */   
/*     */   @GwtIncompatible("java.util.regex")
/* 241 */   public static Splitter onPattern(String separatorPattern) { return on(Pattern.compile(separatorPattern)); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Splitter fixedLength(int length) {
/* 255 */     Preconditions.checkArgument((length > 0), "The length may not be less than 1");
/*     */     
/* 257 */     return new Splitter(new Strategy(length)
/*     */         {
/*     */           public Splitter.SplittingIterator iterator(Splitter splitter, CharSequence toSplit) {
/* 260 */             return new Splitter.SplittingIterator(splitter, toSplit) {
/*     */                 public int separatorStart(int start) {
/* 262 */                   int nextChunkStart = start + length;
/* 263 */                   return (nextChunkStart < this.toSplit.length()) ? nextChunkStart : -1;
/*     */                 }
/*     */ 
/*     */                 
/* 267 */                 public int separatorEnd(int separatorPosition) { return separatorPosition; }
/*     */               };
/*     */           }
/*     */         });
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 293 */   public Splitter omitEmptyStrings() { return new Splitter(this.strategy, true, this.trimmer); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 307 */   public Splitter trimResults() { return trimResults(CharMatcher.WHITESPACE); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Splitter trimResults(CharMatcher trimmer) {
/* 323 */     Preconditions.checkNotNull(trimmer);
/* 324 */     return new Splitter(this.strategy, this.omitEmptyStrings, trimmer);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Iterable<String> split(final CharSequence sequence) {
/* 334 */     Preconditions.checkNotNull(sequence);
/*     */     
/* 336 */     return new Iterable<String>()
/*     */       {
/* 338 */         public Iterator<String> iterator() { return Splitter.this.strategy.iterator(Splitter.this, sequence); }
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static abstract class SplittingIterator
/*     */     extends AbstractIterator<String>
/*     */   {
/*     */     final CharSequence toSplit;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     final CharMatcher trimmer;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     final boolean omitEmptyStrings;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 366 */     int offset = 0;
/*     */     protected SplittingIterator(Splitter splitter, CharSequence toSplit) {
/* 368 */       super(null);
/* 369 */       this.trimmer = splitter.trimmer;
/* 370 */       this.omitEmptyStrings = splitter.omitEmptyStrings;
/* 371 */       this.toSplit = toSplit;
/*     */     }
/*     */     
/*     */     protected String computeNext() {
/* 375 */       while (this.offset != -1) {
/* 376 */         int end, start = this.offset;
/*     */ 
/*     */         
/* 379 */         int separatorPosition = separatorStart(this.offset);
/* 380 */         if (separatorPosition == -1) {
/* 381 */           end = this.toSplit.length();
/* 382 */           this.offset = -1;
/*     */         } else {
/* 384 */           end = separatorPosition;
/* 385 */           this.offset = separatorEnd(separatorPosition);
/*     */         } 
/*     */         
/* 388 */         while (start < end && this.trimmer.matches(this.toSplit.charAt(start))) {
/* 389 */           start++;
/*     */         }
/* 391 */         while (end > start && this.trimmer.matches(this.toSplit.charAt(end - 1))) {
/* 392 */           end--;
/*     */         }
/*     */         
/* 395 */         if (this.omitEmptyStrings && start == end) {
/*     */           continue;
/*     */         }
/*     */         
/* 399 */         return this.toSplit.subSequence(start, end).toString();
/*     */       } 
/* 401 */       return (String)endOfData();
/*     */     }
/*     */     
/*     */     abstract int separatorStart(int param1Int);
/*     */     
/*     */     abstract int separatorEnd(int param1Int);
/*     */   }
/*     */   
/*     */   private static abstract class AbstractIterator<T> extends Object implements Iterator<T> {
/* 410 */     State state = State.NOT_READY;
/*     */     T next;
/*     */     
/* 413 */     enum State { READY, NOT_READY, DONE, FAILED; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected final T endOfData() {
/* 421 */       this.state = State.DONE;
/* 422 */       return null;
/*     */     }
/*     */     
/*     */     public final boolean hasNext() {
/* 426 */       Preconditions.checkState((this.state != State.FAILED));
/* 427 */       switch (this.state) {
/*     */         case DONE:
/* 429 */           return false;
/*     */         case READY:
/* 431 */           return true;
/*     */       } 
/*     */       
/* 434 */       return tryToComputeNext();
/*     */     }
/*     */     
/*     */     boolean tryToComputeNext() {
/* 438 */       this.state = State.FAILED;
/* 439 */       this.next = computeNext();
/* 440 */       if (this.state != State.DONE) {
/* 441 */         this.state = State.READY;
/* 442 */         return true;
/*     */       } 
/* 444 */       return false;
/*     */     }
/*     */     
/*     */     public final T next() {
/* 448 */       if (!hasNext()) {
/* 449 */         throw new NoSuchElementException();
/*     */       }
/* 451 */       this.state = State.NOT_READY;
/* 452 */       return (T)this.next;
/*     */     }
/*     */ 
/*     */     
/* 456 */     public void remove() { throw new UnsupportedOperationException(); }
/*     */     
/*     */     private AbstractIterator() {}
/*     */     
/*     */     protected abstract T computeNext();
/*     */   }
/*     */   
/*     */   private static interface Strategy {
/*     */     Iterator<String> iterator(Splitter param1Splitter, CharSequence param1CharSequence);
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\google\common\base\Splitter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */