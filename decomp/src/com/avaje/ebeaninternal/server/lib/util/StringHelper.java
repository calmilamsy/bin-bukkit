/*     */ package com.avaje.ebeaninternal.server.lib.util;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class StringHelper
/*     */ {
/*     */   private static final char SINGLE_QUOTE = '\'';
/*     */   private static final char DOUBLE_QUOTE = '"';
/*     */   
/*     */   public static HashMap<String, String> parseNameQuotedValue(String tag) throws RuntimeException {
/*  40 */     if (tag == null || tag.length() < 1) {
/*  41 */       return null;
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
/*  53 */     if (tag.charAt(tag.length() - 1) == '=') {
/*  54 */       throw new RuntimeException("missing quoted value at the end of " + tag);
/*     */     }
/*     */     
/*  57 */     HashMap<String, String> map = new HashMap<String, String>();
/*     */     
/*  59 */     return parseNameQuotedValue(map, tag, 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static HashMap<String, String> parseNameQuotedValue(HashMap<String, String> map, String tag, int pos) throws RuntimeException {
/*  69 */     int equalsPos = tag.indexOf("=", pos);
/*  70 */     if (equalsPos > -1) {
/*     */       
/*  72 */       char firstQuote = tag.charAt(equalsPos + 1);
/*  73 */       if (firstQuote != '\'' && firstQuote != '"') {
/*  74 */         throw new RuntimeException("missing begin quote at " + equalsPos + "[" + tag.charAt(equalsPos + 1) + "] in [" + tag + "]");
/*     */       }
/*     */ 
/*     */ 
/*     */       
/*  79 */       int endQuotePos = tag.indexOf(firstQuote, equalsPos + 2);
/*  80 */       if (endQuotePos == -1) {
/*  81 */         throw new RuntimeException("missing end quote [" + firstQuote + "] after " + pos + " in [" + tag + "]");
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  88 */       String name = tag.substring(pos, equalsPos);
/*  89 */       String value = tag.substring(equalsPos + 2, endQuotePos);
/*     */ 
/*     */ 
/*     */       
/*  93 */       name = trimFront(name, " ");
/*  94 */       if (name.indexOf('\'') > -1 || name.indexOf('"') > -1) {
/*  95 */         throw new RuntimeException("attribute name contains a quote [" + name + "]");
/*     */       }
/*  97 */       map.put(name, value);
/*     */       
/*  99 */       return parseNameQuotedValue(map, tag, endQuotePos + 1);
/*     */     } 
/*     */ 
/*     */     
/* 103 */     return map;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 112 */   public static int countOccurances(String content, String occurs) { return countOccurances(content, occurs, 0, 0); }
/*     */ 
/*     */   
/*     */   private static int countOccurances(String content, String occurs, int pos, int countSoFar) {
/* 116 */     int equalsPos = content.indexOf(occurs, pos);
/* 117 */     if (equalsPos > -1) {
/* 118 */       countSoFar++;
/* 119 */       pos = equalsPos + occurs.length();
/*     */       
/* 121 */       return countOccurances(content, occurs, pos, countSoFar);
/*     */     } 
/* 123 */     return countSoFar;
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
/*     */   public static Map<String, String> delimitedToMap(String allNameValuePairs, String listDelimiter, String nameValueSeparator) {
/* 142 */     HashMap<String, String> params = new HashMap<String, String>();
/* 143 */     if (allNameValuePairs == null || allNameValuePairs.length() == 0) {
/* 144 */       return params;
/*     */     }
/*     */     
/* 147 */     allNameValuePairs = trimFront(allNameValuePairs, listDelimiter);
/* 148 */     return getKeyValue(params, 0, allNameValuePairs, listDelimiter, nameValueSeparator);
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
/*     */   public static String trimFront(String source, String trim) {
/* 160 */     if (source == null) {
/* 161 */       return null;
/*     */     }
/* 163 */     if (source.indexOf(trim) == 0)
/*     */     {
/* 165 */       return trimFront(source.substring(trim.length()), trim);
/*     */     }
/* 167 */     return source;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isNull(String value) {
/* 175 */     if (value == null || value.trim().length() == 0) {
/* 176 */       return true;
/*     */     }
/* 178 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static HashMap<String, String> getKeyValue(HashMap<String, String> map, int pos, String allNameValuePairs, String listDelimiter, String nameValueSeparator) {
/* 187 */     if (pos >= allNameValuePairs.length())
/*     */     {
/* 189 */       return map;
/*     */     }
/*     */     
/* 192 */     int equalsPos = allNameValuePairs.indexOf(nameValueSeparator, pos);
/* 193 */     int delimPos = allNameValuePairs.indexOf(listDelimiter, pos);
/*     */     
/* 195 */     if (delimPos == -1) {
/* 196 */       delimPos = allNameValuePairs.length();
/*     */     }
/* 198 */     if (equalsPos == -1)
/*     */     {
/* 200 */       return map;
/*     */     }
/* 202 */     if (delimPos == equalsPos + 1)
/*     */     {
/*     */       
/* 205 */       return getKeyValue(map, delimPos + 1, allNameValuePairs, listDelimiter, nameValueSeparator);
/*     */     }
/*     */     
/* 208 */     if (equalsPos > delimPos) {
/*     */       
/* 210 */       String key = allNameValuePairs.substring(pos, delimPos);
/* 211 */       key = key.trim();
/* 212 */       if (key.length() > 0) {
/* 213 */         map.put(key, null);
/*     */       }
/* 215 */       return getKeyValue(map, delimPos + 1, allNameValuePairs, listDelimiter, nameValueSeparator);
/*     */     } 
/*     */ 
/*     */     
/* 219 */     String key = allNameValuePairs.substring(pos, equalsPos);
/*     */     
/* 221 */     if (delimPos > -1) {
/* 222 */       String value = allNameValuePairs.substring(equalsPos + 1, delimPos);
/*     */ 
/*     */       
/* 225 */       key = key.trim();
/*     */       
/* 227 */       map.put(key, value);
/* 228 */       pos = delimPos + 1;
/*     */ 
/*     */       
/* 231 */       return getKeyValue(map, pos, allNameValuePairs, listDelimiter, nameValueSeparator);
/*     */     } 
/*     */     
/* 234 */     return map;
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
/*     */ 
/*     */   
/*     */   public static String[] delimitedToArray(String str, String delimiter, boolean keepEmpties) {
/* 260 */     ArrayList<String> list = new ArrayList<String>();
/* 261 */     int startPos = 0;
/* 262 */     delimiter(str, delimiter, keepEmpties, startPos, list);
/* 263 */     String[] result = new String[list.size()];
/* 264 */     return (String[])list.toArray(result);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static void delimiter(String str, String delimiter, boolean keepEmpties, int startPos, ArrayList<String> list) {
/* 270 */     int endPos = str.indexOf(delimiter, startPos);
/* 271 */     if (endPos == -1) {
/* 272 */       if (startPos <= str.length()) {
/* 273 */         String lastValue = str.substring(startPos, str.length());
/*     */         
/* 275 */         if (keepEmpties || lastValue.length() != 0)
/*     */         {
/*     */           
/* 278 */           list.add(lastValue);
/*     */         }
/*     */       } 
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 285 */     String value = str.substring(startPos, endPos);
/*     */     
/* 287 */     if (keepEmpties || value.length() != 0)
/*     */     {
/*     */       
/* 290 */       list.add(value);
/*     */     }
/*     */     
/* 293 */     delimiter(str, delimiter, keepEmpties, endPos + 1, list);
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
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getBoundedString(String str, String leftBound, String rightBound) throws RuntimeException {
/* 320 */     if (str == null) {
/* 321 */       throw new RuntimeException("string to parse is null?");
/*     */     }
/* 323 */     int startPos = str.indexOf(leftBound);
/* 324 */     if (startPos > -1) {
/* 325 */       startPos += leftBound.length();
/* 326 */       int endPos = str.indexOf(rightBound, startPos);
/*     */       
/* 328 */       if (endPos == -1) {
/* 329 */         throw new RuntimeException("Can't find rightBound: " + rightBound);
/*     */       }
/* 331 */       return str.substring(startPos, endPos);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 337 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String setBoundedString(String str, String leftBound, String rightBound, String replaceString) {
/* 348 */     int startPos = str.indexOf(leftBound);
/* 349 */     if (startPos > -1) {
/*     */       
/* 351 */       int endPos = str.indexOf(rightBound, startPos + leftBound.length());
/* 352 */       if (endPos > -1) {
/* 353 */         String toReplace = str.substring(startPos, endPos + 1);
/* 354 */         return replaceString(str, toReplace, replaceString);
/*     */       } 
/* 356 */       return str;
/*     */     } 
/*     */     
/* 359 */     return str;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String replaceString(String source, String match, String replace) throws RuntimeException {
/* 403 */     if (source == null) {
/* 404 */       return null;
/*     */     }
/* 406 */     if (replace == null) {
/* 407 */       return source;
/*     */     }
/* 409 */     if (match == null) {
/* 410 */       throw new NullPointerException("match is null?");
/*     */     }
/* 412 */     if (match.equals(replace)) {
/* 413 */       return source;
/*     */     }
/* 415 */     return replaceString(source, match, replace, 30, 0, source.length());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String replaceString(String source, String match, String replace, int additionalSize, int startPos, int endPos) {
/* 426 */     if (source == null) {
/* 427 */       return source;
/*     */     }
/*     */     
/* 430 */     char match0 = match.charAt(0);
/*     */     
/* 432 */     int matchLength = match.length();
/*     */     
/* 434 */     if (matchLength == 1 && replace.length() == 1) {
/* 435 */       char replace0 = replace.charAt(0);
/* 436 */       return source.replace(match0, replace0);
/*     */     } 
/* 438 */     if (matchLength >= replace.length()) {
/* 439 */       additionalSize = 0;
/*     */     }
/*     */ 
/*     */     
/* 443 */     int sourceLength = source.length();
/* 444 */     int lastMatch = endPos - matchLength;
/*     */     
/* 446 */     StringBuilder sb = new StringBuilder(sourceLength + additionalSize);
/*     */     
/* 448 */     if (startPos > 0) {
/* 449 */       sb.append(source.substring(0, startPos));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 456 */     for (int i = startPos; i < sourceLength; i++) {
/* 457 */       char sourceChar = source.charAt(i);
/* 458 */       if (i > lastMatch || sourceChar != match0) {
/* 459 */         sb.append(sourceChar);
/*     */       }
/*     */       else {
/*     */         
/* 463 */         boolean isMatch = true;
/* 464 */         int sourceMatchPos = i;
/*     */ 
/*     */         
/* 467 */         for (int j = 1; j < matchLength; j++) {
/* 468 */           sourceMatchPos++;
/* 469 */           if (source.charAt(sourceMatchPos) != match.charAt(j)) {
/* 470 */             isMatch = false;
/*     */             break;
/*     */           } 
/*     */         } 
/* 474 */         if (isMatch) {
/* 475 */           i = i + matchLength - 1;
/* 476 */           sb.append(replace);
/*     */         } else {
/*     */           
/* 479 */           sb.append(sourceChar);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 484 */     return sb.toString();
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
/* 499 */   public static String replaceStringMulti(String source, String[] match, String replace) { return replaceStringMulti(source, match, replace, 30, 0, source.length()); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String replaceStringMulti(String source, String[] match, String replace, int additionalSize, int startPos, int endPos) {
/* 513 */     int shortestMatch = match[0].length();
/*     */     
/* 515 */     char[] match0 = new char[match.length];
/* 516 */     for (i = 0; i < match0.length; i++) {
/* 517 */       match0[i] = match[i].charAt(0);
/* 518 */       if (match[i].length() < shortestMatch) {
/* 519 */         shortestMatch = match[i].length();
/*     */       }
/*     */     } 
/*     */     
/* 523 */     StringBuilder sb = new StringBuilder(source.length() + additionalSize);
/*     */ 
/*     */ 
/*     */     
/* 527 */     int len = source.length();
/* 528 */     int lastMatch = endPos - shortestMatch;
/*     */     
/* 530 */     if (startPos > 0) {
/* 531 */       sb.append(source.substring(0, startPos));
/*     */     }
/*     */     
/* 534 */     int matchCount = 0;
/*     */     
/* 536 */     for (int i = startPos; i < len; i++) {
/* 537 */       char sourceChar = source.charAt(i);
/* 538 */       if (i > lastMatch) {
/* 539 */         sb.append(sourceChar);
/*     */       } else {
/* 541 */         matchCount = 0;
/* 542 */         for (int k = 0; k < match0.length; k++) {
/* 543 */           if (matchCount == 0 && sourceChar == match0[k] && 
/* 544 */             match[k].length() + i <= len) {
/*     */             
/* 546 */             matchCount++;
/* 547 */             int j = 1;
/* 548 */             for (; j < match[k].length(); j++) {
/* 549 */               if (source.charAt(i + j) != match[k].charAt(j)) {
/* 550 */                 matchCount--;
/*     */                 break;
/*     */               } 
/*     */             } 
/* 554 */             if (matchCount > 0) {
/* 555 */               i = i + j - 1;
/* 556 */               sb.append(replace);
/*     */               
/*     */               break;
/*     */             } 
/*     */           } 
/*     */         } 
/* 562 */         if (matchCount == 0) {
/* 563 */           sb.append(sourceChar);
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 568 */     return sb.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String removeChar(String s, char chr) {
/* 577 */     StringBuilder sb = new StringBuilder(s.length());
/*     */     
/* 579 */     for (int i = 0; i < s.length(); i++) {
/* 580 */       char c = s.charAt(i);
/* 581 */       if (c != chr) {
/* 582 */         sb.append(c);
/*     */       }
/*     */     } 
/*     */     
/* 586 */     return sb.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String removeChars(String s, char[] chr) {
/* 595 */     StringBuilder sb = new StringBuilder(s.length());
/*     */     
/* 597 */     for (int i = 0; i < s.length(); i++) {
/* 598 */       char c = s.charAt(i);
/* 599 */       if (!charMatch(c, chr)) {
/* 600 */         sb.append(c);
/*     */       }
/*     */     } 
/*     */     
/* 604 */     return sb.toString();
/*     */   }
/*     */   
/*     */   private static boolean charMatch(int iChr, char[] chr) {
/* 608 */     for (int i = 0; i < chr.length; i++) {
/* 609 */       if (iChr == chr[i]) {
/* 610 */         return true;
/*     */       }
/*     */     } 
/* 613 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\li\\util\StringHelper.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */