/*      */ package com.google.common.base;
/*      */ 
/*      */ import com.google.common.annotations.Beta;
/*      */ import com.google.common.annotations.GwtCompatible;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Arrays;
/*      */ import java.util.List;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ @Beta
/*      */ @GwtCompatible
/*      */ public abstract class CharMatcher
/*      */   extends Object
/*      */   implements Predicate<Character>
/*      */ {
/*      */   private static final String BREAKING_WHITESPACE_CHARS = "\t\n\013\f\r     　";
/*      */   private static final String NON_BREAKING_WHITESPACE_CHARS = " ᠎ ";
/*   74 */   public static final CharMatcher WHITESPACE = anyOf("\t\n\013\f\r     　 ᠎ ").or(inRange(' ', ' '));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   86 */   public static final CharMatcher BREAKING_WHITESPACE = anyOf("\t\n\013\f\r     　").or(inRange(' ', ' ')).or(inRange(' ', ' '));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   95 */   public static final CharMatcher ASCII = inRange(false, '');
/*      */   public static final CharMatcher DIGIT;
/*      */   public static final CharMatcher JAVA_WHITESPACE;
/*      */   public static final CharMatcher JAVA_DIGIT;
/*      */   public static final CharMatcher JAVA_LETTER;
/*      */   public static final CharMatcher JAVA_LETTER_OR_DIGIT;
/*      */   public static final CharMatcher JAVA_UPPER_CASE;
/*      */   
/*      */   static  {
/*  104 */     digit = inRange('0', '9');
/*  105 */     String zeroes = "٠۰߀०০੦૦୦௦౦೦൦๐໐༠၀႐០᠐᥆᧐᭐᮰᱀᱐꘠꣐꤀꩐０";
/*      */ 
/*      */ 
/*      */     
/*  109 */     for (char base : zeroes.toCharArray()) {
/*  110 */       digit = digit.or(inRange(base, (char)(base + '\t')));
/*      */     }
/*  112 */     DIGIT = digit;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  122 */     JAVA_WHITESPACE = inRange('\t', '\r').or(inRange('\034', ' ')).or(is(' ')).or(is('᠎')).or(inRange(' ', ' ')).or(inRange(' ', '​')).or(inRange(' ', ' ')).or(is(' ')).or(is('　'));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  138 */     JAVA_DIGIT = new CharMatcher() {
/*      */         public boolean matches(char c) {
/*  140 */           return Character.isDigit(c);
/*      */         }
/*      */       };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  150 */     JAVA_LETTER = new CharMatcher() {
/*      */         public boolean matches(char c) {
/*  152 */           return Character.isLetter(c);
/*      */         }
/*      */       };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  160 */     JAVA_LETTER_OR_DIGIT = new CharMatcher() {
/*      */         public boolean matches(char c) {
/*  162 */           return Character.isLetterOrDigit(c);
/*      */         }
/*      */       };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  170 */     JAVA_UPPER_CASE = new CharMatcher() {
/*      */         public boolean matches(char c) {
/*  172 */           return Character.isUpperCase(c);
/*      */         }
/*      */       };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  180 */     JAVA_LOWER_CASE = new CharMatcher() {
/*      */         public boolean matches(char c) {
/*  182 */           return Character.isLowerCase(c);
/*      */         }
/*      */       };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  190 */     JAVA_ISO_CONTROL = inRange(false, '\037').or(inRange('', ''));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  199 */     INVISIBLE = inRange(false, ' ').or(inRange('', ' ')).or(is('­')).or(inRange('؀', '؃')).or(anyOf("۝܏ ឴឵᠎")).or(inRange(' ', '‏')).or(inRange(' ', ' ')).or(inRange(' ', '⁤')).or(inRange('⁪', '⁯')).or(is('　')).or(inRange('?', '')).or(anyOf("﻿￹￺￻"));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  220 */     SINGLE_WIDTH = inRange(false, 'ӹ').or(is('־')).or(inRange('א', 'ת')).or(is('׳')).or(is('״')).or(inRange('؀', 'ۿ')).or(inRange('ݐ', 'ݿ')).or(inRange('฀', '๿')).or(inRange('Ḁ', '₯')).or(inRange('℀', '℺')).or(inRange('ﭐ', '﷿')).or(inRange('ﹰ', '﻿')).or(inRange('｡', 'ￜ'));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  235 */     ANY = new CharMatcher()
/*      */       {
/*  237 */         public boolean matches(char c) { return true; }
/*      */ 
/*      */ 
/*      */         
/*  241 */         public int indexIn(CharSequence sequence) { return (sequence.length() == 0) ? -1 : 0; }
/*      */         
/*      */         public int indexIn(CharSequence sequence, int start) {
/*  244 */           int length = sequence.length();
/*  245 */           Preconditions.checkPositionIndex(start, length);
/*  246 */           return (start == length) ? -1 : start;
/*      */         }
/*      */         
/*  249 */         public int lastIndexIn(CharSequence sequence) { return sequence.length() - 1; }
/*      */         
/*      */         public boolean matchesAllOf(CharSequence sequence) {
/*  252 */           Preconditions.checkNotNull(sequence);
/*  253 */           return true;
/*      */         }
/*      */         
/*  256 */         public boolean matchesNoneOf(CharSequence sequence) { return (sequence.length() == 0); }
/*      */         
/*      */         public String removeFrom(CharSequence sequence) {
/*  259 */           Preconditions.checkNotNull(sequence);
/*  260 */           return "";
/*      */         }
/*      */         
/*      */         public String replaceFrom(CharSequence sequence, char replacement) {
/*  264 */           char[] array = new char[sequence.length()];
/*  265 */           Arrays.fill(array, replacement);
/*  266 */           return new String(array);
/*      */         }
/*      */         
/*      */         public String replaceFrom(CharSequence sequence, CharSequence replacement) {
/*  270 */           StringBuilder retval = new StringBuilder(sequence.length() * replacement.length());
/*      */           
/*  272 */           for (int i = 0; i < sequence.length(); i++) {
/*  273 */             retval.append(replacement);
/*      */           }
/*  275 */           return retval.toString();
/*      */         }
/*      */ 
/*      */         
/*  279 */         public String collapseFrom(CharSequence sequence, char replacement) { return (sequence.length() == 0) ? "" : String.valueOf(replacement); }
/*      */         
/*      */         public String trimFrom(CharSequence sequence) {
/*  282 */           Preconditions.checkNotNull(sequence);
/*  283 */           return "";
/*      */         }
/*      */         
/*  286 */         public int countIn(CharSequence sequence) { return sequence.length(); }
/*      */ 
/*      */         
/*  289 */         public CharMatcher and(CharMatcher other) { return (CharMatcher)Preconditions.checkNotNull(other); }
/*      */         
/*      */         public CharMatcher or(CharMatcher other) {
/*  292 */           Preconditions.checkNotNull(other);
/*  293 */           return this;
/*      */         }
/*      */         
/*  296 */         public CharMatcher negate() { return NONE; }
/*      */         
/*      */         public CharMatcher precomputed() {
/*  299 */           return this;
/*      */         }
/*      */       };
/*      */ 
/*      */     
/*  304 */     NONE = new CharMatcher()
/*      */       {
/*  306 */         public boolean matches(char c) { return false; }
/*      */ 
/*      */         
/*      */         public int indexIn(CharSequence sequence) {
/*  310 */           Preconditions.checkNotNull(sequence);
/*  311 */           return -1;
/*      */         }
/*      */         public int indexIn(CharSequence sequence, int start) {
/*  314 */           int length = sequence.length();
/*  315 */           Preconditions.checkPositionIndex(start, length);
/*  316 */           return -1;
/*      */         }
/*      */         public int lastIndexIn(CharSequence sequence) {
/*  319 */           Preconditions.checkNotNull(sequence);
/*  320 */           return -1;
/*      */         }
/*      */         
/*  323 */         public boolean matchesAllOf(CharSequence sequence) { return (sequence.length() == 0); }
/*      */         
/*      */         public boolean matchesNoneOf(CharSequence sequence) {
/*  326 */           Preconditions.checkNotNull(sequence);
/*  327 */           return true;
/*      */         }
/*      */         
/*  330 */         public String removeFrom(CharSequence sequence) { return sequence.toString(); }
/*      */ 
/*      */ 
/*      */         
/*  334 */         public String replaceFrom(CharSequence sequence, char replacement) { return sequence.toString(); }
/*      */ 
/*      */         
/*      */         public String replaceFrom(CharSequence sequence, CharSequence replacement) {
/*  338 */           Preconditions.checkNotNull(replacement);
/*  339 */           return sequence.toString();
/*      */         }
/*      */ 
/*      */         
/*  343 */         public String collapseFrom(CharSequence sequence, char replacement) { return sequence.toString(); }
/*      */ 
/*      */         
/*  346 */         public String trimFrom(CharSequence sequence) { return sequence.toString(); }
/*      */         
/*      */         public int countIn(CharSequence sequence) {
/*  349 */           Preconditions.checkNotNull(sequence);
/*  350 */           return 0;
/*      */         }
/*      */         public CharMatcher and(CharMatcher other) {
/*  353 */           Preconditions.checkNotNull(other);
/*  354 */           return this;
/*      */         }
/*      */         
/*  357 */         public CharMatcher or(CharMatcher other) { return (CharMatcher)Preconditions.checkNotNull(other); }
/*      */ 
/*      */         
/*  360 */         public CharMatcher negate() { return ANY; }
/*      */ 
/*      */         
/*      */         void setBits(LookupTable table) {}
/*      */         
/*  365 */         public CharMatcher precomputed() { return this; }
/*      */       };
/*      */   }
/*      */   public static final CharMatcher JAVA_LOWER_CASE; public static final CharMatcher JAVA_ISO_CONTROL;
/*      */   public static final CharMatcher INVISIBLE;
/*      */   public static final CharMatcher SINGLE_WIDTH;
/*      */   public static final CharMatcher ANY;
/*      */   public static final CharMatcher NONE;
/*      */   
/*      */   public static CharMatcher is(final char match) {
/*  375 */     return new CharMatcher()
/*      */       {
/*  377 */         public boolean matches(char c) { return (c == match); }
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  382 */         public String replaceFrom(CharSequence sequence, char replacement) { return sequence.toString().replace(match, replacement); }
/*      */ 
/*      */         
/*  385 */         public CharMatcher and(CharMatcher other) { return other.matches(match) ? this : NONE; }
/*      */ 
/*      */         
/*  388 */         public CharMatcher or(CharMatcher other) { return other.matches(match) ? other : super.or(other); }
/*      */ 
/*      */         
/*  391 */         public CharMatcher negate() { return null.isNot(match); }
/*      */ 
/*      */         
/*  394 */         void setBits(LookupTable table) { table.set(match); }
/*      */ 
/*      */         
/*  397 */         public CharMatcher precomputed() { return this; }
/*      */       };
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static CharMatcher isNot(final char match) {
/*  409 */     return new CharMatcher()
/*      */       {
/*  411 */         public boolean matches(char c) { return (c != match); }
/*      */ 
/*      */ 
/*      */         
/*  415 */         public CharMatcher and(CharMatcher other) { return other.matches(match) ? super.and(other) : other; }
/*      */ 
/*      */         
/*  418 */         public CharMatcher or(CharMatcher other) { return other.matches(match) ? ANY : this; }
/*      */ 
/*      */         
/*  421 */         public CharMatcher negate() { return null.is(match); }
/*      */       };
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static CharMatcher anyOf(CharSequence sequence) {
/*      */     final char match2, match1;
/*  431 */     switch (sequence.length()) {
/*      */       case 0:
/*  433 */         return NONE;
/*      */       case 1:
/*  435 */         return is(sequence.charAt(0));
/*      */       case 2:
/*  437 */         match1 = sequence.charAt(0);
/*  438 */         match2 = sequence.charAt(1);
/*  439 */         return new CharMatcher()
/*      */           {
/*  441 */             public boolean matches(char c) { return (c == match1 || c == match2); }
/*      */             
/*      */             void setBits(LookupTable table) {
/*  444 */               table.set(match1);
/*  445 */               table.set(match2);
/*      */             }
/*      */             
/*  448 */             public CharMatcher precomputed() { return this; }
/*      */           
/*      */           };
/*      */     } 
/*      */     
/*  453 */     final char[] chars = sequence.toString().toCharArray();
/*  454 */     Arrays.sort(chars);
/*      */     
/*  456 */     return new CharMatcher()
/*      */       {
/*  458 */         public boolean matches(char c) { return (Arrays.binarySearch(chars, c) >= 0); }
/*      */         
/*      */         void setBits(LookupTable table) {
/*  461 */           for (char c : chars) {
/*  462 */             table.set(c);
/*      */           }
/*      */         }
/*      */       };
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  473 */   public static CharMatcher noneOf(CharSequence sequence) { return anyOf(sequence).negate(); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static CharMatcher inRange(final char startInclusive, final char endInclusive) {
/*  485 */     Preconditions.checkArgument((endInclusive >= startInclusive));
/*  486 */     return new CharMatcher()
/*      */       {
/*  488 */         public boolean matches(char c) { return (startInclusive <= c && c <= endInclusive); }
/*      */         
/*      */         void setBits(LookupTable table) {
/*  491 */           char c = startInclusive;
/*      */           
/*  493 */           do { table.set(c);
/*  494 */             c = (char)(c + '\001'); } while (c != endInclusive);
/*      */         }
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  500 */         public CharMatcher precomputed() { return this; }
/*      */       };
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static CharMatcher forPredicate(final Predicate<? super Character> predicate) {
/*  512 */     Preconditions.checkNotNull(predicate);
/*  513 */     if (predicate instanceof CharMatcher) {
/*  514 */       return (CharMatcher)predicate;
/*      */     }
/*  516 */     return new CharMatcher()
/*      */       {
/*  518 */         public boolean matches(char c) { return predicate.apply(Character.valueOf(c)); }
/*      */ 
/*      */         
/*  521 */         public boolean apply(Character character) { return predicate.apply(Preconditions.checkNotNull(character)); }
/*      */       };
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public CharMatcher negate() {
/*  537 */     final CharMatcher original = this;
/*  538 */     return new CharMatcher()
/*      */       {
/*  540 */         public boolean matches(char c) { return !original.matches(c); }
/*      */ 
/*      */ 
/*      */         
/*  544 */         public boolean matchesAllOf(CharSequence sequence) { return original.matchesNoneOf(sequence); }
/*      */ 
/*      */         
/*  547 */         public boolean matchesNoneOf(CharSequence sequence) { return original.matchesAllOf(sequence); }
/*      */ 
/*      */         
/*  550 */         public int countIn(CharSequence sequence) { return sequence.length() - original.countIn(sequence); }
/*      */ 
/*      */         
/*  553 */         public CharMatcher negate() { return original; }
/*      */       };
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  563 */   public CharMatcher and(CharMatcher other) { return new And(Arrays.asList(new CharMatcher[] { this, (CharMatcher)Preconditions.checkNotNull(other) })); }
/*      */   
/*      */   private static class And
/*      */     extends CharMatcher
/*      */   {
/*      */     List<CharMatcher> components;
/*      */     
/*  570 */     And(List<CharMatcher> components) { this.components = components; }
/*      */ 
/*      */     
/*      */     public boolean matches(char c) {
/*  574 */       for (CharMatcher matcher : this.components) {
/*  575 */         if (!matcher.matches(c)) {
/*  576 */           return false;
/*      */         }
/*      */       } 
/*  579 */       return true;
/*      */     }
/*      */     
/*      */     public CharMatcher and(CharMatcher other) {
/*  583 */       List<CharMatcher> newComponents = new ArrayList<CharMatcher>(this.components);
/*  584 */       newComponents.add(Preconditions.checkNotNull(other));
/*  585 */       return new And(newComponents);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  594 */   public CharMatcher or(CharMatcher other) { return new Or(Arrays.asList(new CharMatcher[] { this, (CharMatcher)Preconditions.checkNotNull(other) })); }
/*      */   
/*      */   private static class Or
/*      */     extends CharMatcher
/*      */   {
/*      */     List<CharMatcher> components;
/*      */     
/*  601 */     Or(List<CharMatcher> components) { this.components = components; }
/*      */ 
/*      */     
/*      */     public boolean matches(char c) {
/*  605 */       for (CharMatcher matcher : this.components) {
/*  606 */         if (matcher.matches(c)) {
/*  607 */           return true;
/*      */         }
/*      */       } 
/*  610 */       return false;
/*      */     }
/*      */     
/*      */     public CharMatcher or(CharMatcher other) {
/*  614 */       List<CharMatcher> newComponents = new ArrayList<CharMatcher>(this.components);
/*  615 */       newComponents.add(Preconditions.checkNotNull(other));
/*  616 */       return new Or(newComponents);
/*      */     }
/*      */     
/*      */     void setBits(CharMatcher.LookupTable table) {
/*  620 */       for (CharMatcher matcher : this.components) {
/*  621 */         matcher.setBits(table);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  639 */   public CharMatcher precomputed() { return Platform.precomputeCharMatcher(this); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   CharMatcher precomputedInternal() {
/*  655 */     final LookupTable table = new LookupTable(null);
/*  656 */     setBits(table);
/*      */     
/*  658 */     return new CharMatcher()
/*      */       {
/*  660 */         public boolean matches(char c) { return table.get(c); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  666 */         public CharMatcher precomputed() { return this; }
/*      */       };
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void setBits(LookupTable table) {
/*  680 */     char c = Character.MIN_VALUE;
/*      */     
/*  682 */     do { if (!matches(c))
/*  683 */         continue;  table.set(c);
/*      */       
/*  685 */       c = (char)(c + '\001'); } while (c != Character.MAX_VALUE);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final class LookupTable
/*      */   {
/*  699 */     int[] data = new int[2048];
/*      */ 
/*      */     
/*  702 */     void set(char index) { this.data[index >> '\005'] = this.data[index >> '\005'] | '\001' << index; }
/*      */ 
/*      */     
/*  705 */     boolean get(char index) { return ((this.data[index >> '\005'] & '\001' << index) != 0); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private LookupTable() {}
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean matchesAllOf(CharSequence sequence) {
/*  724 */     for (int i = sequence.length() - 1; i >= 0; i--) {
/*  725 */       if (!matches(sequence.charAt(i))) {
/*  726 */         return false;
/*      */       }
/*      */     } 
/*  729 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  745 */   public boolean matchesNoneOf(CharSequence sequence) { return (indexIn(sequence) == -1); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int indexIn(CharSequence sequence) {
/*  761 */     int length = sequence.length();
/*  762 */     for (int i = 0; i < length; i++) {
/*  763 */       if (matches(sequence.charAt(i))) {
/*  764 */         return i;
/*      */       }
/*      */     } 
/*  767 */     return -1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int indexIn(CharSequence sequence, int start) {
/*  787 */     int length = sequence.length();
/*  788 */     Preconditions.checkPositionIndex(start, length);
/*  789 */     for (int i = start; i < length; i++) {
/*  790 */       if (matches(sequence.charAt(i))) {
/*  791 */         return i;
/*      */       }
/*      */     } 
/*  794 */     return -1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int lastIndexIn(CharSequence sequence) {
/*  808 */     for (int i = sequence.length() - 1; i >= 0; i--) {
/*  809 */       if (matches(sequence.charAt(i))) {
/*  810 */         return i;
/*      */       }
/*      */     } 
/*  813 */     return -1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int countIn(CharSequence sequence) {
/*  820 */     int count = 0;
/*  821 */     for (int i = 0; i < sequence.length(); i++) {
/*  822 */       if (matches(sequence.charAt(i))) {
/*  823 */         count++;
/*      */       }
/*      */     } 
/*  826 */     return count;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String removeFrom(CharSequence sequence) {
/*  838 */     String string = sequence.toString();
/*  839 */     int pos = indexIn(string);
/*  840 */     if (pos == -1) {
/*  841 */       return string;
/*      */     }
/*      */     
/*  844 */     char[] chars = string.toCharArray();
/*  845 */     int spread = 1;
/*      */ 
/*      */ 
/*      */     
/*      */     while (true) {
/*  850 */       pos++;
/*      */       
/*  852 */       while (pos != chars.length) {
/*      */ 
/*      */         
/*  855 */         if (matches(chars[pos]))
/*      */         
/*      */         { 
/*      */ 
/*      */ 
/*      */           
/*  861 */           spread++; continue; }  chars[pos - spread] = chars[pos]; pos++;
/*      */       }  break;
/*  863 */     }  return new String(chars, false, pos - spread);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  875 */   public String retainFrom(CharSequence sequence) { return negate().removeFrom(sequence); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String replaceFrom(CharSequence sequence, char replacement) {
/*  897 */     String string = sequence.toString();
/*  898 */     int pos = indexIn(string);
/*  899 */     if (pos == -1) {
/*  900 */       return string;
/*      */     }
/*  902 */     char[] chars = string.toCharArray();
/*  903 */     chars[pos] = replacement;
/*  904 */     for (int i = pos + 1; i < chars.length; i++) {
/*  905 */       if (matches(chars[i])) {
/*  906 */         chars[i] = replacement;
/*      */       }
/*      */     } 
/*  909 */     return new String(chars);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String replaceFrom(CharSequence sequence, CharSequence replacement) {
/*  931 */     int replacementLen = replacement.length();
/*  932 */     if (replacementLen == 0) {
/*  933 */       return removeFrom(sequence);
/*      */     }
/*  935 */     if (replacementLen == 1) {
/*  936 */       return replaceFrom(sequence, replacement.charAt(0));
/*      */     }
/*      */     
/*  939 */     String string = sequence.toString();
/*  940 */     int pos = indexIn(string);
/*  941 */     if (pos == -1) {
/*  942 */       return string;
/*      */     }
/*      */     
/*  945 */     int len = string.length();
/*  946 */     StringBuilder buf = new StringBuilder(len * 3 / 2 + 16);
/*      */     
/*  948 */     int oldpos = 0;
/*      */     do {
/*  950 */       buf.append(string, oldpos, pos);
/*  951 */       buf.append(replacement);
/*  952 */       oldpos = pos + 1;
/*  953 */       pos = indexIn(string, oldpos);
/*  954 */     } while (pos != -1);
/*      */     
/*  956 */     buf.append(string, oldpos, len);
/*  957 */     return buf.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String trimFrom(CharSequence sequence) {
/*  976 */     int len = sequence.length();
/*      */     
/*      */     int first;
/*      */     
/*  980 */     for (first = 0; first < len && 
/*  981 */       matches(sequence.charAt(first)); first++);
/*      */     
/*      */     int last;
/*      */     
/*  985 */     for (last = len - 1; last > first && 
/*  986 */       matches(sequence.charAt(last)); last--);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  991 */     return sequence.subSequence(first, last + 1).toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String trimLeadingFrom(CharSequence sequence) {
/* 1004 */     int len = sequence.length();
/*      */     
/*      */     int first;
/* 1007 */     for (first = 0; first < len && 
/* 1008 */       matches(sequence.charAt(first)); first++);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1013 */     return sequence.subSequence(first, len).toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String trimTrailingFrom(CharSequence sequence) {
/* 1026 */     int len = sequence.length();
/*      */     
/*      */     int last;
/* 1029 */     for (last = len - 1; last >= 0 && 
/* 1030 */       matches(sequence.charAt(last)); last--);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1035 */     return sequence.subSequence(0, last + 1).toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String collapseFrom(CharSequence sequence, char replacement) {
/* 1058 */     int first = indexIn(sequence);
/* 1059 */     if (first == -1) {
/* 1060 */       return sequence.toString();
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 1065 */     StringBuilder builder = (new StringBuilder(sequence.length())).append(sequence.subSequence(0, first)).append(replacement);
/*      */ 
/*      */     
/* 1068 */     boolean in = true;
/* 1069 */     for (int i = first + 1; i < sequence.length(); i++) {
/* 1070 */       char c = sequence.charAt(i);
/* 1071 */       if (apply(Character.valueOf(c))) {
/* 1072 */         if (!in) {
/* 1073 */           builder.append(replacement);
/* 1074 */           in = true;
/*      */         } 
/*      */       } else {
/* 1077 */         builder.append(c);
/* 1078 */         in = false;
/*      */       } 
/*      */     } 
/* 1081 */     return builder.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String trimAndCollapseFrom(CharSequence sequence, char replacement) {
/* 1090 */     int first = negate().indexIn(sequence);
/* 1091 */     if (first == -1) {
/* 1092 */       return "";
/*      */     }
/* 1094 */     StringBuilder builder = new StringBuilder(sequence.length());
/* 1095 */     boolean inMatchingGroup = false;
/* 1096 */     for (int i = first; i < sequence.length(); i++) {
/* 1097 */       char c = sequence.charAt(i);
/* 1098 */       if (apply(Character.valueOf(c))) {
/* 1099 */         inMatchingGroup = true;
/*      */       } else {
/* 1101 */         if (inMatchingGroup) {
/* 1102 */           builder.append(replacement);
/* 1103 */           inMatchingGroup = false;
/*      */         } 
/* 1105 */         builder.append(c);
/*      */       } 
/*      */     } 
/* 1108 */     return builder.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1119 */   public boolean apply(Character character) { return matches(character.charValue()); }
/*      */   
/*      */   public abstract boolean matches(char paramChar);
/*      */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\google\common\base\CharMatcher.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */