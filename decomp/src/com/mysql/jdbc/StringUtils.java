/*      */ package com.mysql.jdbc;
/*      */ 
/*      */ import java.io.ByteArrayOutputStream;
/*      */ import java.io.IOException;
/*      */ import java.io.StringReader;
/*      */ import java.io.UnsupportedEncodingException;
/*      */ import java.lang.reflect.InvocationTargetException;
/*      */ import java.lang.reflect.Method;
/*      */ import java.math.BigDecimal;
/*      */ import java.sql.SQLException;
/*      */ import java.util.ArrayList;
/*      */ import java.util.List;
/*      */ import java.util.StringTokenizer;
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
/*      */ public class StringUtils
/*      */ {
/*      */   private static final int BYTE_RANGE = 256;
/*   50 */   private static byte[] allBytes = new byte[256];
/*      */   
/*   52 */   private static char[] byteToChars = new char[256];
/*      */   
/*      */   private static Method toPlainStringMethod;
/*      */   
/*      */   static final int WILD_COMPARE_MATCH_NO_WILD = 0;
/*      */   
/*      */   static final int WILD_COMPARE_MATCH_WITH_WILD = 1;
/*      */   
/*      */   static final int WILD_COMPARE_NO_MATCH = -1;
/*      */   
/*      */   static  {
/*   63 */     for (i = -128; i <= 127; i++) {
/*   64 */       allBytes[i - -128] = (byte)i;
/*      */     }
/*      */     
/*   67 */     String str = new String(allBytes, false, 'ÿ');
/*      */ 
/*      */     
/*   70 */     int allBytesStringLen = str.length();
/*      */     
/*   72 */     i = 0;
/*   73 */     for (; i < 255 && i < allBytesStringLen; i++) {
/*   74 */       byteToChars[i] = str.charAt(i);
/*      */     }
/*      */     
/*      */     try {
/*   78 */       toPlainStringMethod = BigDecimal.class.getMethod("toPlainString", new Class[0]);
/*      */     }
/*   80 */     catch (NoSuchMethodException i) {
/*      */       NoSuchMethodException nsme;
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
/*      */   public static String consistentToString(BigDecimal decimal) {
/*   95 */     if (decimal == null) {
/*   96 */       return null;
/*      */     }
/*      */     
/*   99 */     if (toPlainStringMethod != null) {
/*      */       try {
/*  101 */         return (String)toPlainStringMethod.invoke(decimal, (Object[])null);
/*  102 */       } catch (InvocationTargetException invokeEx) {
/*      */       
/*  104 */       } catch (IllegalAccessException accessEx) {}
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  109 */     return decimal.toString();
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
/*      */   public static final String dumpAsHex(byte[] byteBuffer, int length) {
/*  123 */     StringBuffer outputBuf = new StringBuffer(length * 4);
/*      */     
/*  125 */     int p = 0;
/*  126 */     int rows = length / 8;
/*      */     
/*  128 */     for (i = 0; i < rows && p < length; i++) {
/*  129 */       int ptemp = p;
/*      */       
/*  131 */       for (j = 0; j < 8; j++) {
/*  132 */         String hexVal = Integer.toHexString(byteBuffer[ptemp] & 0xFF);
/*      */         
/*  134 */         if (hexVal.length() == 1) {
/*  135 */           hexVal = "0" + hexVal;
/*      */         }
/*      */         
/*  138 */         outputBuf.append(hexVal + " ");
/*  139 */         ptemp++;
/*      */       } 
/*      */       
/*  142 */       outputBuf.append("    ");
/*      */       
/*  144 */       for (int j = 0; j < 8; j++) {
/*  145 */         int b = 0xFF & byteBuffer[p];
/*      */         
/*  147 */         if (b > 32 && b < 127) {
/*  148 */           outputBuf.append((char)b + " ");
/*      */         } else {
/*  150 */           outputBuf.append(". ");
/*      */         } 
/*      */         
/*  153 */         p++;
/*      */       } 
/*      */       
/*  156 */       outputBuf.append("\n");
/*      */     } 
/*      */     
/*  159 */     int n = 0;
/*      */     
/*  161 */     for (i = p; i < length; i++) {
/*  162 */       String hexVal = Integer.toHexString(byteBuffer[i] & 0xFF);
/*      */       
/*  164 */       if (hexVal.length() == 1) {
/*  165 */         hexVal = "0" + hexVal;
/*      */       }
/*      */       
/*  168 */       outputBuf.append(hexVal + " ");
/*  169 */       n++;
/*      */     } 
/*      */     
/*  172 */     for (i = n; i < 8; i++) {
/*  173 */       outputBuf.append("   ");
/*      */     }
/*      */     
/*  176 */     outputBuf.append("    ");
/*      */     
/*  178 */     for (int i = p; i < length; i++) {
/*  179 */       int b = 0xFF & byteBuffer[i];
/*      */       
/*  181 */       if (b > 32 && b < 127) {
/*  182 */         outputBuf.append((char)b + " ");
/*      */       } else {
/*  184 */         outputBuf.append(". ");
/*      */       } 
/*      */     } 
/*      */     
/*  188 */     outputBuf.append("\n");
/*      */     
/*  190 */     return outputBuf.toString();
/*      */   }
/*      */   
/*      */   private static boolean endsWith(byte[] dataFrom, String suffix) {
/*  194 */     for (int i = 1; i <= suffix.length(); i++) {
/*  195 */       int dfOffset = dataFrom.length - i;
/*  196 */       int suffixOffset = suffix.length() - i;
/*  197 */       if (dataFrom[dfOffset] != suffix.charAt(suffixOffset)) {
/*  198 */         return false;
/*      */       }
/*      */     } 
/*  201 */     return true;
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
/*      */   public static byte[] escapeEasternUnicodeByteStream(byte[] origBytes, String origString, int offset, int length) {
/*  221 */     if (origBytes == null || origBytes.length == 0) {
/*  222 */       return origBytes;
/*      */     }
/*      */     
/*  225 */     int bytesLen = origBytes.length;
/*  226 */     int bufIndex = 0;
/*  227 */     int strIndex = 0;
/*      */     
/*  229 */     ByteArrayOutputStream bytesOut = new ByteArrayOutputStream(bytesLen);
/*      */     
/*      */     while (true) {
/*  232 */       if (origString.charAt(strIndex) == '\\') {
/*      */         
/*  234 */         bytesOut.write(origBytes[bufIndex++]);
/*      */       
/*      */       }
/*      */       else {
/*      */         
/*  239 */         int loByte = origBytes[bufIndex];
/*      */         
/*  241 */         if (loByte < 0) {
/*  242 */           loByte += 256;
/*      */         }
/*      */ 
/*      */         
/*  246 */         bytesOut.write(loByte);
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
/*  264 */         if (loByte >= 128) {
/*  265 */           if (bufIndex < bytesLen - 1) {
/*  266 */             int hiByte = origBytes[bufIndex + 1];
/*      */             
/*  268 */             if (hiByte < 0) {
/*  269 */               hiByte += 256;
/*      */             }
/*      */ 
/*      */ 
/*      */             
/*  274 */             bytesOut.write(hiByte);
/*  275 */             bufIndex++;
/*      */ 
/*      */             
/*  278 */             if (hiByte == 92) {
/*  279 */               bytesOut.write(hiByte);
/*      */             }
/*      */           } 
/*  282 */         } else if (loByte == 92 && 
/*  283 */           bufIndex < bytesLen - 1) {
/*  284 */           int hiByte = origBytes[bufIndex + 1];
/*      */           
/*  286 */           if (hiByte < 0) {
/*  287 */             hiByte += 256;
/*      */           }
/*      */           
/*  290 */           if (hiByte == 98) {
/*      */             
/*  292 */             bytesOut.write(92);
/*  293 */             bytesOut.write(98);
/*  294 */             bufIndex++;
/*      */           } 
/*      */         } 
/*      */ 
/*      */         
/*  299 */         bufIndex++;
/*      */       } 
/*      */       
/*  302 */       if (bufIndex >= bytesLen) {
/*      */         break;
/*      */       }
/*      */ 
/*      */       
/*  307 */       strIndex++;
/*      */     } 
/*      */     
/*  310 */     return bytesOut.toByteArray();
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
/*  322 */   public static char firstNonWsCharUc(String searchIn) { return firstNonWsCharUc(searchIn, 0); }
/*      */ 
/*      */   
/*      */   public static char firstNonWsCharUc(String searchIn, int startAt) {
/*  326 */     if (searchIn == null) {
/*  327 */       return Character.MIN_VALUE;
/*      */     }
/*      */     
/*  330 */     int length = searchIn.length();
/*      */     
/*  332 */     for (int i = startAt; i < length; i++) {
/*  333 */       char c = searchIn.charAt(i);
/*      */       
/*  335 */       if (!Character.isWhitespace(c)) {
/*  336 */         return Character.toUpperCase(c);
/*      */       }
/*      */     } 
/*      */     
/*  340 */     return Character.MIN_VALUE;
/*      */   }
/*      */   
/*      */   public static char firstAlphaCharUc(String searchIn, int startAt) {
/*  344 */     if (searchIn == null) {
/*  345 */       return Character.MIN_VALUE;
/*      */     }
/*      */     
/*  348 */     int length = searchIn.length();
/*      */     
/*  350 */     for (int i = startAt; i < length; i++) {
/*  351 */       char c = searchIn.charAt(i);
/*      */       
/*  353 */       if (Character.isLetter(c)) {
/*  354 */         return Character.toUpperCase(c);
/*      */       }
/*      */     } 
/*      */     
/*  358 */     return Character.MIN_VALUE;
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
/*      */   public static final String fixDecimalExponent(String dString) {
/*  371 */     int ePos = dString.indexOf("E");
/*      */     
/*  373 */     if (ePos == -1) {
/*  374 */       ePos = dString.indexOf("e");
/*      */     }
/*      */     
/*  377 */     if (ePos != -1 && 
/*  378 */       dString.length() > ePos + 1) {
/*  379 */       char maybeMinusChar = dString.charAt(ePos + 1);
/*      */       
/*  381 */       if (maybeMinusChar != '-' && maybeMinusChar != '+') {
/*  382 */         StringBuffer buf = new StringBuffer(dString.length() + 1);
/*  383 */         buf.append(dString.substring(0, ePos + 1));
/*  384 */         buf.append('+');
/*  385 */         buf.append(dString.substring(ePos + 1, dString.length()));
/*  386 */         dString = buf.toString();
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  391 */     return dString;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final byte[] getBytes(char[] c, SingleByteCharsetConverter converter, String encoding, String serverEncoding, boolean parserKnowsUnicode, ExceptionInterceptor exceptionInterceptor) throws SQLException {
/*      */     try {
/*  399 */       byte[] b = null;
/*      */       
/*  401 */       if (converter != null) {
/*  402 */         b = converter.toBytes(c);
/*  403 */       } else if (encoding == null) {
/*  404 */         b = (new String(c)).getBytes();
/*      */       } else {
/*  406 */         String s = new String(c);
/*      */         
/*  408 */         b = s.getBytes(encoding);
/*      */         
/*  410 */         if (!parserKnowsUnicode && (encoding.equalsIgnoreCase("SJIS") || encoding.equalsIgnoreCase("BIG5") || encoding.equalsIgnoreCase("GBK")))
/*      */         {
/*      */ 
/*      */           
/*  414 */           if (!encoding.equalsIgnoreCase(serverEncoding)) {
/*  415 */             b = escapeEasternUnicodeByteStream(b, s, 0, s.length());
/*      */           }
/*      */         }
/*      */       } 
/*      */       
/*  420 */       return b;
/*  421 */     } catch (UnsupportedEncodingException uee) {
/*  422 */       throw SQLError.createSQLException(Messages.getString("StringUtils.5") + encoding + Messages.getString("StringUtils.6"), "S1009", exceptionInterceptor);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final byte[] getBytes(char[] c, SingleByteCharsetConverter converter, String encoding, String serverEncoding, int offset, int length, boolean parserKnowsUnicode, ExceptionInterceptor exceptionInterceptor) throws SQLException {
/*      */     try {
/*  433 */       byte[] b = null;
/*      */       
/*  435 */       if (converter != null) {
/*  436 */         b = converter.toBytes(c, offset, length);
/*  437 */       } else if (encoding == null) {
/*  438 */         byte[] temp = (new String(c, offset, length)).getBytes();
/*      */         
/*  440 */         length = temp.length;
/*      */         
/*  442 */         b = new byte[length];
/*  443 */         System.arraycopy(temp, 0, b, 0, length);
/*      */       } else {
/*  445 */         String s = new String(c, offset, length);
/*      */         
/*  447 */         byte[] temp = s.getBytes(encoding);
/*      */         
/*  449 */         length = temp.length;
/*      */         
/*  451 */         b = new byte[length];
/*  452 */         System.arraycopy(temp, 0, b, 0, length);
/*      */         
/*  454 */         if (!parserKnowsUnicode && (encoding.equalsIgnoreCase("SJIS") || encoding.equalsIgnoreCase("BIG5") || encoding.equalsIgnoreCase("GBK")))
/*      */         {
/*      */ 
/*      */           
/*  458 */           if (!encoding.equalsIgnoreCase(serverEncoding)) {
/*  459 */             b = escapeEasternUnicodeByteStream(b, s, offset, length);
/*      */           }
/*      */         }
/*      */       } 
/*      */       
/*  464 */       return b;
/*  465 */     } catch (UnsupportedEncodingException uee) {
/*  466 */       throw SQLError.createSQLException(Messages.getString("StringUtils.10") + encoding + Messages.getString("StringUtils.11"), "S1009", exceptionInterceptor);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final byte[] getBytes(char[] c, String encoding, String serverEncoding, boolean parserKnowsUnicode, MySQLConnection conn, ExceptionInterceptor exceptionInterceptor) throws SQLException {
/*      */     try {
/*  478 */       SingleByteCharsetConverter converter = null;
/*      */       
/*  480 */       if (conn != null) {
/*  481 */         converter = conn.getCharsetConverter(encoding);
/*      */       } else {
/*  483 */         converter = SingleByteCharsetConverter.getInstance(encoding, null);
/*      */       } 
/*      */       
/*  486 */       return getBytes(c, converter, encoding, serverEncoding, parserKnowsUnicode, exceptionInterceptor);
/*      */     }
/*  488 */     catch (UnsupportedEncodingException uee) {
/*  489 */       throw SQLError.createSQLException(Messages.getString("StringUtils.0") + encoding + Messages.getString("StringUtils.1"), "S1009", exceptionInterceptor);
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
/*      */   public static final byte[] getBytes(String s, SingleByteCharsetConverter converter, String encoding, String serverEncoding, boolean parserKnowsUnicode, ExceptionInterceptor exceptionInterceptor) throws SQLException {
/*      */     try {
/*  520 */       byte[] b = null;
/*      */       
/*  522 */       if (converter != null) {
/*  523 */         b = converter.toBytes(s);
/*  524 */       } else if (encoding == null) {
/*  525 */         b = s.getBytes();
/*      */       } else {
/*  527 */         b = s.getBytes(encoding);
/*      */         
/*  529 */         if (!parserKnowsUnicode && (encoding.equalsIgnoreCase("SJIS") || encoding.equalsIgnoreCase("BIG5") || encoding.equalsIgnoreCase("GBK")))
/*      */         {
/*      */ 
/*      */           
/*  533 */           if (!encoding.equalsIgnoreCase(serverEncoding)) {
/*  534 */             b = escapeEasternUnicodeByteStream(b, s, 0, s.length());
/*      */           }
/*      */         }
/*      */       } 
/*      */       
/*  539 */       return b;
/*  540 */     } catch (UnsupportedEncodingException uee) {
/*  541 */       throw SQLError.createSQLException(Messages.getString("StringUtils.5") + encoding + Messages.getString("StringUtils.6"), "S1009", exceptionInterceptor);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final byte[] getBytesWrapped(String s, char beginWrap, char endWrap, SingleByteCharsetConverter converter, String encoding, String serverEncoding, boolean parserKnowsUnicode, ExceptionInterceptor exceptionInterceptor) throws SQLException {
/*      */     try {
/*  552 */       byte[] b = null;
/*      */       
/*  554 */       if (converter != null) {
/*  555 */         b = converter.toBytesWrapped(s, beginWrap, endWrap);
/*  556 */       } else if (encoding == null) {
/*  557 */         StringBuffer buf = new StringBuffer(s.length() + 2);
/*  558 */         buf.append(beginWrap);
/*  559 */         buf.append(s);
/*  560 */         buf.append(endWrap);
/*      */         
/*  562 */         b = buf.toString().getBytes();
/*      */       } else {
/*  564 */         StringBuffer buf = new StringBuffer(s.length() + 2);
/*  565 */         buf.append(beginWrap);
/*  566 */         buf.append(s);
/*  567 */         buf.append(endWrap);
/*      */         
/*  569 */         b = buf.toString().getBytes(encoding);
/*      */         
/*  571 */         if (!parserKnowsUnicode && (encoding.equalsIgnoreCase("SJIS") || encoding.equalsIgnoreCase("BIG5") || encoding.equalsIgnoreCase("GBK")))
/*      */         {
/*      */ 
/*      */           
/*  575 */           if (!encoding.equalsIgnoreCase(serverEncoding)) {
/*  576 */             b = escapeEasternUnicodeByteStream(b, s, 0, s.length());
/*      */           }
/*      */         }
/*      */       } 
/*      */       
/*  581 */       return b;
/*  582 */     } catch (UnsupportedEncodingException uee) {
/*  583 */       throw SQLError.createSQLException(Messages.getString("StringUtils.5") + encoding + Messages.getString("StringUtils.6"), "S1009", exceptionInterceptor);
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
/*      */   public static final byte[] getBytes(String s, SingleByteCharsetConverter converter, String encoding, String serverEncoding, int offset, int length, boolean parserKnowsUnicode, ExceptionInterceptor exceptionInterceptor) throws SQLException {
/*      */     try {
/*  617 */       byte[] b = null;
/*      */       
/*  619 */       if (converter != null) {
/*  620 */         b = converter.toBytes(s, offset, length);
/*  621 */       } else if (encoding == null) {
/*  622 */         byte[] temp = s.substring(offset, offset + length).getBytes();
/*      */         
/*  624 */         length = temp.length;
/*      */         
/*  626 */         b = new byte[length];
/*  627 */         System.arraycopy(temp, 0, b, 0, length);
/*      */       } else {
/*      */         
/*  630 */         byte[] temp = s.substring(offset, offset + length).getBytes(encoding);
/*      */ 
/*      */         
/*  633 */         length = temp.length;
/*      */         
/*  635 */         b = new byte[length];
/*  636 */         System.arraycopy(temp, 0, b, 0, length);
/*      */         
/*  638 */         if (!parserKnowsUnicode && (encoding.equalsIgnoreCase("SJIS") || encoding.equalsIgnoreCase("BIG5") || encoding.equalsIgnoreCase("GBK")))
/*      */         {
/*      */ 
/*      */           
/*  642 */           if (!encoding.equalsIgnoreCase(serverEncoding)) {
/*  643 */             b = escapeEasternUnicodeByteStream(b, s, offset, length);
/*      */           }
/*      */         }
/*      */       } 
/*      */       
/*  648 */       return b;
/*  649 */     } catch (UnsupportedEncodingException uee) {
/*  650 */       throw SQLError.createSQLException(Messages.getString("StringUtils.10") + encoding + Messages.getString("StringUtils.11"), "S1009", exceptionInterceptor);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final byte[] getBytes(String s, String encoding, String serverEncoding, boolean parserKnowsUnicode, MySQLConnection conn, ExceptionInterceptor exceptionInterceptor) throws SQLException {
/*      */     try {
/*  677 */       SingleByteCharsetConverter converter = null;
/*      */       
/*  679 */       if (conn != null) {
/*  680 */         converter = conn.getCharsetConverter(encoding);
/*      */       } else {
/*  682 */         converter = SingleByteCharsetConverter.getInstance(encoding, null);
/*      */       } 
/*      */       
/*  685 */       return getBytes(s, converter, encoding, serverEncoding, parserKnowsUnicode, exceptionInterceptor);
/*      */     }
/*  687 */     catch (UnsupportedEncodingException uee) {
/*  688 */       throw SQLError.createSQLException(Messages.getString("StringUtils.0") + encoding + Messages.getString("StringUtils.1"), "S1009", exceptionInterceptor);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static int getInt(byte[] buf, int offset, int endPos) throws NumberFormatException {
/*  695 */     int base = 10;
/*      */     
/*  697 */     int s = offset;
/*      */ 
/*      */     
/*  700 */     while (Character.isWhitespace((char)buf[s]) && s < endPos) {
/*  701 */       s++;
/*      */     }
/*      */     
/*  704 */     if (s == endPos) {
/*  705 */       throw new NumberFormatException(new String(buf));
/*      */     }
/*      */ 
/*      */     
/*  709 */     boolean negative = false;
/*      */     
/*  711 */     if ((char)buf[s] == '-') {
/*  712 */       negative = true;
/*  713 */       s++;
/*  714 */     } else if ((char)buf[s] == '+') {
/*  715 */       s++;
/*      */     } 
/*      */ 
/*      */     
/*  719 */     int save = s;
/*      */     
/*  721 */     int cutoff = Integer.MAX_VALUE / base;
/*  722 */     int cutlim = Integer.MAX_VALUE % base;
/*      */     
/*  724 */     if (negative) {
/*  725 */       cutlim++;
/*      */     }
/*      */     
/*  728 */     boolean overflow = false;
/*      */     
/*  730 */     int i = 0;
/*      */     
/*  732 */     for (; s < endPos; s++) {
/*  733 */       char c = (char)buf[s];
/*      */       
/*  735 */       if (Character.isDigit(c)) {
/*  736 */         c = (char)(c - '0');
/*  737 */       } else if (Character.isLetter(c)) {
/*  738 */         c = (char)(Character.toUpperCase(c) - 'A' + '\n');
/*      */       } else {
/*      */         break;
/*      */       } 
/*      */       
/*  743 */       if (c >= base) {
/*      */         break;
/*      */       }
/*      */ 
/*      */       
/*  748 */       if (i > cutoff || (i == cutoff && c > cutlim)) {
/*  749 */         overflow = true;
/*      */       } else {
/*  751 */         i *= base;
/*  752 */         i += c;
/*      */       } 
/*      */     } 
/*      */     
/*  756 */     if (s == save) {
/*  757 */       throw new NumberFormatException(new String(buf));
/*      */     }
/*      */     
/*  760 */     if (overflow) {
/*  761 */       throw new NumberFormatException(new String(buf));
/*      */     }
/*      */ 
/*      */     
/*  765 */     return negative ? -i : i;
/*      */   }
/*      */ 
/*      */   
/*  769 */   public static int getInt(byte[] buf) throws NumberFormatException { return getInt(buf, 0, buf.length); }
/*      */ 
/*      */ 
/*      */   
/*  773 */   public static long getLong(byte[] buf) throws NumberFormatException { return getLong(buf, 0, buf.length); }
/*      */ 
/*      */   
/*      */   public static long getLong(byte[] buf, int offset, int endpos) throws NumberFormatException {
/*  777 */     int base = 10;
/*      */     
/*  779 */     int s = offset;
/*      */ 
/*      */     
/*  782 */     while (Character.isWhitespace((char)buf[s]) && s < endpos) {
/*  783 */       s++;
/*      */     }
/*      */     
/*  786 */     if (s == endpos) {
/*  787 */       throw new NumberFormatException(new String(buf));
/*      */     }
/*      */ 
/*      */     
/*  791 */     boolean negative = false;
/*      */     
/*  793 */     if ((char)buf[s] == '-') {
/*  794 */       negative = true;
/*  795 */       s++;
/*  796 */     } else if ((char)buf[s] == '+') {
/*  797 */       s++;
/*      */     } 
/*      */ 
/*      */     
/*  801 */     int save = s;
/*      */     
/*  803 */     long cutoff = Float.MAX_VALUE / base;
/*  804 */     long cutlim = (int)(Float.MAX_VALUE % base);
/*      */     
/*  806 */     if (negative) {
/*  807 */       cutlim++;
/*      */     }
/*      */     
/*  810 */     boolean overflow = false;
/*  811 */     long i = 0L;
/*      */     
/*  813 */     for (; s < endpos; s++) {
/*  814 */       char c = (char)buf[s];
/*      */       
/*  816 */       if (Character.isDigit(c)) {
/*  817 */         c = (char)(c - '0');
/*  818 */       } else if (Character.isLetter(c)) {
/*  819 */         c = (char)(Character.toUpperCase(c) - 'A' + '\n');
/*      */       } else {
/*      */         break;
/*      */       } 
/*      */       
/*  824 */       if (c >= base) {
/*      */         break;
/*      */       }
/*      */ 
/*      */       
/*  829 */       if (i > cutoff || (i == cutoff && c > cutlim)) {
/*  830 */         overflow = true;
/*      */       } else {
/*  832 */         i *= base;
/*  833 */         i += c;
/*      */       } 
/*      */     } 
/*      */     
/*  837 */     if (s == save) {
/*  838 */       throw new NumberFormatException(new String(buf));
/*      */     }
/*      */     
/*  841 */     if (overflow) {
/*  842 */       throw new NumberFormatException(new String(buf));
/*      */     }
/*      */ 
/*      */     
/*  846 */     return negative ? -i : i;
/*      */   }
/*      */   
/*      */   public static short getShort(byte[] buf) throws NumberFormatException {
/*  850 */     short base = 10;
/*      */     
/*  852 */     int s = 0;
/*      */ 
/*      */     
/*  855 */     while (Character.isWhitespace((char)buf[s]) && s < buf.length) {
/*  856 */       s++;
/*      */     }
/*      */     
/*  859 */     if (s == buf.length) {
/*  860 */       throw new NumberFormatException(new String(buf));
/*      */     }
/*      */ 
/*      */     
/*  864 */     boolean negative = false;
/*      */     
/*  866 */     if ((char)buf[s] == '-') {
/*  867 */       negative = true;
/*  868 */       s++;
/*  869 */     } else if ((char)buf[s] == '+') {
/*  870 */       s++;
/*      */     } 
/*      */ 
/*      */     
/*  874 */     int save = s;
/*      */     
/*  876 */     short cutoff = (short)(Short.MAX_VALUE / base);
/*  877 */     short cutlim = (short)(Short.MAX_VALUE % base);
/*      */     
/*  879 */     if (negative) {
/*  880 */       cutlim = (short)(cutlim + 1);
/*      */     }
/*      */     
/*  883 */     boolean overflow = false;
/*  884 */     short i = 0;
/*      */     
/*  886 */     for (; s < buf.length; s++) {
/*  887 */       char c = (char)buf[s];
/*      */       
/*  889 */       if (Character.isDigit(c)) {
/*  890 */         c = (char)(c - '0');
/*  891 */       } else if (Character.isLetter(c)) {
/*  892 */         c = (char)(Character.toUpperCase(c) - 'A' + '\n');
/*      */       } else {
/*      */         break;
/*      */       } 
/*      */       
/*  897 */       if (c >= base) {
/*      */         break;
/*      */       }
/*      */ 
/*      */       
/*  902 */       if (i > cutoff || (i == cutoff && c > cutlim)) {
/*  903 */         overflow = true;
/*      */       } else {
/*  905 */         i = (short)(i * base);
/*  906 */         i = (short)(i + c);
/*      */       } 
/*      */     } 
/*      */     
/*  910 */     if (s == save) {
/*  911 */       throw new NumberFormatException(new String(buf));
/*      */     }
/*      */     
/*  914 */     if (overflow) {
/*  915 */       throw new NumberFormatException(new String(buf));
/*      */     }
/*      */ 
/*      */     
/*  919 */     return negative ? (short)-i : i;
/*      */   }
/*      */ 
/*      */   
/*      */   public static final int indexOfIgnoreCase(int startingPosition, String searchIn, String searchFor) {
/*  924 */     if (searchIn == null || searchFor == null || startingPosition > searchIn.length())
/*      */     {
/*  926 */       return -1;
/*      */     }
/*      */     
/*  929 */     int patternLength = searchFor.length();
/*  930 */     int stringLength = searchIn.length();
/*  931 */     int stopSearchingAt = stringLength - patternLength;
/*      */     
/*  933 */     if (patternLength == 0) {
/*  934 */       return -1;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  939 */     char firstCharOfPatternUc = Character.toUpperCase(searchFor.charAt(0));
/*  940 */     char firstCharOfPatternLc = Character.toLowerCase(searchFor.charAt(0));
/*      */ 
/*      */     
/*  943 */     for (int i = startingPosition; i <= stopSearchingAt; i++) {
/*  944 */       if (isNotEqualIgnoreCharCase(searchIn, firstCharOfPatternUc, firstCharOfPatternLc, i))
/*      */       {
/*      */         
/*  947 */         while (++i <= stopSearchingAt && isNotEqualIgnoreCharCase(searchIn, firstCharOfPatternUc, firstCharOfPatternLc, i));
/*      */       }
/*      */ 
/*      */       
/*  951 */       if (i <= stopSearchingAt) {
/*      */ 
/*      */         
/*  954 */         int j = i + 1;
/*  955 */         int end = j + patternLength - 1;
/*  956 */         for (int k = 1; j < end && (Character.toLowerCase(searchIn.charAt(j)) == Character.toLowerCase(searchFor.charAt(k)) || Character.toUpperCase(searchIn.charAt(j)) == Character.toUpperCase(searchFor.charAt(k))); ) {
/*      */           
/*  958 */           j++; k++;
/*      */         } 
/*  960 */         if (j == end) {
/*  961 */           return i;
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/*  966 */     return -1;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*  971 */   private static final boolean isNotEqualIgnoreCharCase(String searchIn, char firstCharOfPatternUc, char firstCharOfPatternLc, int i) { return (Character.toLowerCase(searchIn.charAt(i)) != firstCharOfPatternLc && Character.toUpperCase(searchIn.charAt(i)) != firstCharOfPatternUc); }
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
/*  986 */   public static final int indexOfIgnoreCase(String searchIn, String searchFor) { return indexOfIgnoreCase(0, searchIn, searchFor); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int indexOfIgnoreCaseRespectMarker(int startAt, String src, String target, String marker, String markerCloses, boolean allowBackslashEscapes) {
/*  992 */     char contextMarker = Character.MIN_VALUE;
/*  993 */     boolean escaped = false;
/*  994 */     int markerTypeFound = 0;
/*  995 */     int srcLength = src.length();
/*  996 */     int ind = 0;
/*      */     
/*  998 */     for (int i = startAt; i < srcLength; i++) {
/*  999 */       char c = src.charAt(i);
/*      */       
/* 1001 */       if (allowBackslashEscapes && c == '\\') {
/* 1002 */         escaped = !escaped;
/* 1003 */       } else if (contextMarker != '\000' && c == markerCloses.charAt(markerTypeFound) && !escaped) {
/* 1004 */         contextMarker = Character.MIN_VALUE;
/* 1005 */       } else if ((ind = marker.indexOf(c)) != -1 && !escaped && contextMarker == '\000') {
/*      */         
/* 1007 */         markerTypeFound = ind;
/* 1008 */         contextMarker = c;
/* 1009 */       } else if ((Character.toUpperCase(c) == Character.toUpperCase(target.charAt(0)) || Character.toLowerCase(c) == Character.toLowerCase(target.charAt(0))) && !escaped && contextMarker == '\000') {
/*      */ 
/*      */         
/* 1012 */         if (startsWithIgnoreCase(src, i, target)) {
/* 1013 */           return i;
/*      */         }
/*      */       } 
/*      */     } 
/* 1017 */     return -1;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static int indexOfIgnoreCaseRespectQuotes(int startAt, String src, String target, char quoteChar, boolean allowBackslashEscapes) {
/* 1023 */     char contextMarker = Character.MIN_VALUE;
/* 1024 */     boolean escaped = false;
/*      */     
/* 1026 */     int srcLength = src.length();
/*      */     
/* 1028 */     for (int i = startAt; i < srcLength; i++) {
/* 1029 */       char c = src.charAt(i);
/*      */       
/* 1031 */       if (allowBackslashEscapes && c == '\\') {
/* 1032 */         escaped = !escaped;
/* 1033 */       } else if (c == contextMarker && !escaped) {
/* 1034 */         contextMarker = Character.MIN_VALUE;
/* 1035 */       } else if (c == quoteChar && !escaped && contextMarker == '\000') {
/*      */         
/* 1037 */         contextMarker = c;
/*      */       
/*      */       }
/* 1040 */       else if ((Character.toUpperCase(c) == Character.toUpperCase(target.charAt(0)) || Character.toLowerCase(c) == Character.toLowerCase(target.charAt(0))) && !escaped && contextMarker == '\000') {
/*      */ 
/*      */         
/* 1043 */         if (startsWithIgnoreCase(src, i, target)) {
/* 1044 */           return i;
/*      */         }
/*      */       } 
/*      */     } 
/* 1048 */     return -1;
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
/*      */   public static final List split(String stringToSplit, String delimitter, boolean trim) {
/* 1069 */     if (stringToSplit == null) {
/* 1070 */       return new ArrayList();
/*      */     }
/*      */     
/* 1073 */     if (delimitter == null) {
/* 1074 */       throw new IllegalArgumentException();
/*      */     }
/*      */     
/* 1077 */     StringTokenizer tokenizer = new StringTokenizer(stringToSplit, delimitter, false);
/*      */ 
/*      */     
/* 1080 */     List splitTokens = new ArrayList(tokenizer.countTokens());
/*      */     
/* 1082 */     while (tokenizer.hasMoreTokens()) {
/* 1083 */       String token = tokenizer.nextToken();
/*      */       
/* 1085 */       if (trim) {
/* 1086 */         token = token.trim();
/*      */       }
/*      */       
/* 1089 */       splitTokens.add(token);
/*      */     } 
/*      */     
/* 1092 */     return splitTokens;
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
/*      */   public static final List<String> split(String stringToSplit, String delimiter, String markers, String markerCloses, boolean trim) {
/* 1112 */     if (stringToSplit == null) {
/* 1113 */       return new ArrayList();
/*      */     }
/*      */     
/* 1116 */     if (delimiter == null) {
/* 1117 */       throw new IllegalArgumentException();
/*      */     }
/*      */     
/* 1120 */     int delimPos = 0;
/* 1121 */     int currentPos = 0;
/*      */     
/* 1123 */     List<String> splitTokens = new ArrayList<String>();
/*      */ 
/*      */     
/* 1126 */     while ((delimPos = indexOfIgnoreCaseRespectMarker(currentPos, stringToSplit, delimiter, markers, markerCloses, false)) != -1) {
/* 1127 */       String token = stringToSplit.substring(currentPos, delimPos);
/*      */       
/* 1129 */       if (trim) {
/* 1130 */         token = token.trim();
/*      */       }
/*      */       
/* 1133 */       splitTokens.add(token);
/* 1134 */       currentPos = delimPos + 1;
/*      */     } 
/*      */     
/* 1137 */     if (currentPos < stringToSplit.length()) {
/* 1138 */       String token = stringToSplit.substring(currentPos);
/*      */       
/* 1140 */       if (trim) {
/* 1141 */         token = token.trim();
/*      */       }
/*      */       
/* 1144 */       splitTokens.add(token);
/*      */     } 
/*      */     
/* 1147 */     return splitTokens;
/*      */   }
/*      */   
/*      */   private static boolean startsWith(byte[] dataFrom, String chars) {
/* 1151 */     for (int i = 0; i < chars.length(); i++) {
/* 1152 */       if (dataFrom[i] != chars.charAt(i)) {
/* 1153 */         return false;
/*      */       }
/*      */     } 
/* 1156 */     return true;
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
/* 1175 */   public static boolean startsWithIgnoreCase(String searchIn, int startAt, String searchFor) { return searchIn.regionMatches(true, startAt, searchFor, 0, searchFor.length()); }
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
/* 1191 */   public static boolean startsWithIgnoreCase(String searchIn, String searchFor) { return startsWithIgnoreCase(searchIn, 0, searchFor); }
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
/*      */   public static boolean startsWithIgnoreCaseAndNonAlphaNumeric(String searchIn, String searchFor) {
/* 1208 */     if (searchIn == null) {
/* 1209 */       return (searchFor == null);
/*      */     }
/*      */     
/* 1212 */     int beginPos = 0;
/*      */     
/* 1214 */     int inLength = searchIn.length();
/*      */     
/* 1216 */     for (beginPos = 0; beginPos < inLength; beginPos++) {
/* 1217 */       char c = searchIn.charAt(beginPos);
/*      */       
/* 1219 */       if (Character.isLetterOrDigit(c)) {
/*      */         break;
/*      */       }
/*      */     } 
/*      */     
/* 1224 */     return startsWithIgnoreCase(searchIn, beginPos, searchFor);
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
/* 1240 */   public static boolean startsWithIgnoreCaseAndWs(String searchIn, String searchFor) { return startsWithIgnoreCaseAndWs(searchIn, searchFor, 0); }
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
/*      */   public static boolean startsWithIgnoreCaseAndWs(String searchIn, String searchFor, int beginPos) {
/* 1259 */     if (searchIn == null) {
/* 1260 */       return (searchFor == null);
/*      */     }
/*      */     
/* 1263 */     int inLength = searchIn.length();
/*      */     
/* 1265 */     for (; beginPos < inLength && 
/* 1266 */       Character.isWhitespace(searchIn.charAt(beginPos)); beginPos++);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1271 */     return startsWithIgnoreCase(searchIn, beginPos, searchFor);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static byte[] stripEnclosure(byte[] source, String prefix, String suffix) {
/* 1282 */     if (source.length >= prefix.length() + suffix.length() && startsWith(source, prefix) && endsWith(source, suffix)) {
/*      */ 
/*      */       
/* 1285 */       int totalToStrip = prefix.length() + suffix.length();
/* 1286 */       int enclosedLength = source.length - totalToStrip;
/* 1287 */       byte[] enclosed = new byte[enclosedLength];
/*      */       
/* 1289 */       int startPos = prefix.length();
/* 1290 */       int numToCopy = enclosed.length;
/* 1291 */       System.arraycopy(source, startPos, enclosed, 0, numToCopy);
/*      */       
/* 1293 */       return enclosed;
/*      */     } 
/* 1295 */     return source;
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
/* 1307 */   public static final String toAsciiString(byte[] buffer) { return toAsciiString(buffer, 0, buffer.length); }
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
/*      */   public static final String toAsciiString(byte[] buffer, int startPos, int length) {
/* 1324 */     char[] charArray = new char[length];
/* 1325 */     int readpoint = startPos;
/*      */     
/* 1327 */     for (int i = 0; i < length; i++) {
/* 1328 */       charArray[i] = (char)buffer[readpoint];
/* 1329 */       readpoint++;
/*      */     } 
/*      */     
/* 1332 */     return new String(charArray);
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
/*      */   public static int wildCompare(String searchIn, String searchForWildcard) {
/* 1350 */     if (searchIn == null || searchForWildcard == null) {
/* 1351 */       return -1;
/*      */     }
/*      */     
/* 1354 */     if (searchForWildcard.equals("%"))
/*      */     {
/* 1356 */       return 1;
/*      */     }
/*      */     
/* 1359 */     int result = -1;
/*      */     
/* 1361 */     char wildcardMany = '%';
/* 1362 */     char wildcardOne = '_';
/* 1363 */     char wildcardEscape = '\\';
/*      */     
/* 1365 */     int searchForPos = 0;
/* 1366 */     int searchForEnd = searchForWildcard.length();
/*      */     
/* 1368 */     int searchInPos = 0;
/* 1369 */     int searchInEnd = searchIn.length();
/*      */     
/* 1371 */     while (searchForPos != searchForEnd) {
/* 1372 */       char wildstrChar = searchForWildcard.charAt(searchForPos);
/*      */ 
/*      */       
/* 1375 */       while (searchForWildcard.charAt(searchForPos) != wildcardMany && wildstrChar != wildcardOne) {
/* 1376 */         if (searchForWildcard.charAt(searchForPos) == wildcardEscape && searchForPos + 1 != searchForEnd)
/*      */         {
/* 1378 */           searchForPos++;
/*      */         }
/*      */         
/* 1381 */         if (searchInPos == searchInEnd || Character.toUpperCase(searchForWildcard.charAt(searchForPos++)) != Character.toUpperCase(searchIn.charAt(searchInPos++)))
/*      */         {
/*      */ 
/*      */           
/* 1385 */           return 1;
/*      */         }
/*      */         
/* 1388 */         if (searchForPos == searchForEnd) {
/* 1389 */           return (searchInPos != searchInEnd) ? 1 : 0;
/*      */         }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1396 */         result = 1;
/*      */       } 
/*      */       
/* 1399 */       if (searchForWildcard.charAt(searchForPos) == wildcardOne) {
/*      */         do {
/* 1401 */           if (searchInPos == searchInEnd)
/*      */           {
/*      */ 
/*      */ 
/*      */             
/* 1406 */             return result;
/*      */           }
/*      */           
/* 1409 */           searchInPos++;
/*      */         }
/* 1411 */         while (++searchForPos < searchForEnd && searchForWildcard.charAt(searchForPos) == wildcardOne);
/*      */         
/* 1413 */         if (searchForPos == searchForEnd) {
/*      */           break;
/*      */         }
/*      */       } 
/*      */       
/* 1418 */       if (searchForWildcard.charAt(searchForPos) == wildcardMany) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1425 */         searchForPos++;
/*      */ 
/*      */         
/* 1428 */         for (; searchForPos != searchForEnd; searchForPos++) {
/* 1429 */           if (searchForWildcard.charAt(searchForPos) != wildcardMany)
/*      */           {
/*      */ 
/*      */             
/* 1433 */             if (searchForWildcard.charAt(searchForPos) == wildcardOne) {
/* 1434 */               if (searchInPos == searchInEnd) {
/* 1435 */                 return -1;
/*      */               }
/*      */               
/* 1438 */               searchInPos++;
/*      */             } else {
/*      */               break;
/*      */             } 
/*      */           }
/*      */         } 
/*      */ 
/*      */         
/* 1446 */         if (searchForPos == searchForEnd) {
/* 1447 */           return 0;
/*      */         }
/*      */         
/* 1450 */         if (searchInPos == searchInEnd) {
/* 1451 */           return -1;
/*      */         }
/*      */         char cmp;
/* 1454 */         if ((cmp = searchForWildcard.charAt(searchForPos)) == wildcardEscape && searchForPos + 1 != searchForEnd)
/*      */         {
/* 1456 */           cmp = searchForWildcard.charAt(++searchForPos);
/*      */         }
/*      */         
/* 1459 */         searchForPos++;
/*      */ 
/*      */         
/*      */         while (true) {
/* 1463 */           if (searchInPos != searchInEnd && Character.toUpperCase(searchIn.charAt(searchInPos)) != Character.toUpperCase(cmp)) {
/*      */ 
/*      */             
/* 1466 */             searchInPos++; continue;
/*      */           } 
/* 1468 */           if (searchInPos++ == searchInEnd) {
/* 1469 */             return -1;
/*      */           }
/*      */ 
/*      */           
/* 1473 */           int tmp = wildCompare(searchIn, searchForWildcard);
/*      */           
/* 1475 */           if (tmp <= 0) {
/* 1476 */             return tmp;
/*      */           }
/*      */ 
/*      */           
/* 1480 */           if (searchInPos == searchInEnd || searchForWildcard.charAt(0) == wildcardMany)
/*      */             break; 
/* 1482 */         }  return -1;
/*      */       } 
/*      */     } 
/*      */     
/* 1486 */     return (searchInPos != searchInEnd) ? 1 : 0;
/*      */   }
/*      */ 
/*      */   
/*      */   static byte[] s2b(String s, MySQLConnection conn) throws SQLException {
/* 1491 */     if (s == null) {
/* 1492 */       return null;
/*      */     }
/*      */     
/* 1495 */     if (conn != null && conn.getUseUnicode()) {
/*      */       try {
/* 1497 */         String encoding = conn.getEncoding();
/*      */         
/* 1499 */         if (encoding == null) {
/* 1500 */           return s.getBytes();
/*      */         }
/*      */         
/* 1503 */         SingleByteCharsetConverter converter = conn.getCharsetConverter(encoding);
/*      */ 
/*      */         
/* 1506 */         if (converter != null) {
/* 1507 */           return converter.toBytes(s);
/*      */         }
/*      */         
/* 1510 */         return s.getBytes(encoding);
/* 1511 */       } catch (UnsupportedEncodingException E) {
/* 1512 */         return s.getBytes();
/*      */       } 
/*      */     }
/*      */     
/* 1516 */     return s.getBytes();
/*      */   }
/*      */   
/*      */   public static int lastIndexOf(byte[] s, char c) {
/* 1520 */     if (s == null) {
/* 1521 */       return -1;
/*      */     }
/*      */     
/* 1524 */     for (int i = s.length - 1; i >= 0; i--) {
/* 1525 */       if (s[i] == c) {
/* 1526 */         return i;
/*      */       }
/*      */     } 
/*      */     
/* 1530 */     return -1;
/*      */   }
/*      */   
/*      */   public static int indexOf(byte[] s, char c) {
/* 1534 */     if (s == null) {
/* 1535 */       return -1;
/*      */     }
/*      */     
/* 1538 */     int length = s.length;
/*      */     
/* 1540 */     for (int i = 0; i < length; i++) {
/* 1541 */       if (s[i] == c) {
/* 1542 */         return i;
/*      */       }
/*      */     } 
/*      */     
/* 1546 */     return -1;
/*      */   }
/*      */ 
/*      */   
/* 1550 */   public static boolean isNullOrEmpty(String toTest) { return (toTest == null || toTest.length() == 0); }
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
/*      */   public static String stripComments(String src, String stringOpens, String stringCloses, boolean slashStarComments, boolean slashSlashComments, boolean hashComments, boolean dashDashComments) {
/* 1577 */     if (src == null) {
/* 1578 */       return null;
/*      */     }
/*      */     
/* 1581 */     StringBuffer buf = new StringBuffer(src.length());
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1590 */     StringReader sourceReader = new StringReader(src);
/*      */     
/* 1592 */     int contextMarker = 0;
/* 1593 */     boolean escaped = false;
/* 1594 */     int markerTypeFound = -1;
/*      */     
/* 1596 */     int ind = 0;
/*      */     
/* 1598 */     int currentChar = 0;
/*      */     
/*      */     try {
/* 1601 */       label81: while ((currentChar = sourceReader.read()) != -1) {
/*      */ 
/*      */ 
/*      */         
/* 1605 */         if (markerTypeFound != -1 && currentChar == stringCloses.charAt(markerTypeFound) && !escaped) {
/*      */           
/* 1607 */           contextMarker = 0;
/* 1608 */           markerTypeFound = -1;
/* 1609 */         } else if ((ind = stringOpens.indexOf(currentChar)) != -1 && !escaped && contextMarker == 0) {
/*      */           
/* 1611 */           markerTypeFound = ind;
/* 1612 */           contextMarker = currentChar;
/*      */         } 
/*      */         
/* 1615 */         if (contextMarker == 0 && currentChar == 47 && (slashSlashComments || slashStarComments)) {
/*      */           
/* 1617 */           currentChar = sourceReader.read();
/* 1618 */           if (currentChar == 42 && slashStarComments) {
/* 1619 */             int prevChar = 0;
/*      */             while (true) {
/* 1621 */               if ((currentChar = sourceReader.read()) != 47 || prevChar != 42) {
/* 1622 */                 if (currentChar == 13) {
/*      */                   
/* 1624 */                   currentChar = sourceReader.read();
/* 1625 */                   if (currentChar == 10) {
/* 1626 */                     currentChar = sourceReader.read();
/*      */                   }
/*      */                 }
/* 1629 */                 else if (currentChar == 10) {
/*      */                   
/* 1631 */                   currentChar = sourceReader.read();
/*      */                 } 
/*      */                 
/* 1634 */                 if (currentChar < 0)
/*      */                   continue label81; 
/* 1636 */                 prevChar = currentChar; continue;
/*      */               }  continue label81;
/*      */             } 
/* 1639 */           }  if (currentChar == 47 && slashSlashComments)
/*      */           {
/* 1641 */             while ((currentChar = sourceReader.read()) != 10 && currentChar != 13 && currentChar >= 0);
/*      */           }
/*      */         }
/* 1644 */         else if (contextMarker == 0 && currentChar == 35 && hashComments) {
/*      */ 
/*      */ 
/*      */           
/* 1648 */           while ((currentChar = sourceReader.read()) != 10 && currentChar != 13 && currentChar >= 0);
/*      */         }
/* 1650 */         else if (contextMarker == 0 && currentChar == 45 && dashDashComments) {
/*      */           
/* 1652 */           currentChar = sourceReader.read();
/*      */           
/* 1654 */           if (currentChar == -1 || currentChar != 45) {
/* 1655 */             buf.append('-');
/*      */             
/* 1657 */             if (currentChar != -1) {
/* 1658 */               buf.append(currentChar);
/*      */             }
/*      */ 
/*      */ 
/*      */             
/*      */             continue;
/*      */           } 
/*      */ 
/*      */           
/* 1667 */           while ((currentChar = sourceReader.read()) != 10 && currentChar != 13 && currentChar >= 0);
/*      */         } 
/*      */ 
/*      */         
/* 1671 */         if (currentChar != -1) {
/* 1672 */           buf.append((char)currentChar);
/*      */         }
/*      */       } 
/* 1675 */     } catch (IOException ioEx) {}
/*      */ 
/*      */ 
/*      */     
/* 1679 */     return buf.toString();
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
/*      */   public static String sanitizeProcOrFuncName(String src) {
/* 1695 */     if (src == null || src == "%") {
/* 1696 */       return null;
/*      */     }
/* 1698 */     return src;
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
/*      */   public static List splitDBdotName(String src, String cat, String quotId, boolean isNoBslashEscSet) {
/* 1719 */     if (src == null || src == "%") {
/* 1720 */       return new ArrayList();
/*      */     }
/* 1722 */     String retval = src;
/*      */     
/* 1724 */     String tmpCat = cat;
/*      */     
/* 1726 */     int trueDotIndex = -1;
/* 1727 */     if (!" ".equals(quotId)) {
/*      */ 
/*      */       
/* 1730 */       trueDotIndex = indexOfIgnoreCaseRespectQuotes(0, retval, quotId + ".", quotId.charAt(0), !isNoBslashEscSet);
/*      */       
/* 1732 */       if (trueDotIndex == -1) {
/* 1733 */         trueDotIndex = indexOfIgnoreCaseRespectQuotes(0, retval, ".", quotId.charAt(0), !isNoBslashEscSet);
/*      */       }
/*      */     } else {
/*      */       
/* 1737 */       trueDotIndex = retval.indexOf(".");
/*      */     } 
/*      */     
/* 1740 */     List retTokens = new ArrayList(2);
/*      */ 
/*      */ 
/*      */     
/* 1744 */     if (trueDotIndex != -1) {
/*      */       
/* 1746 */       tmpCat = retval.substring(0, trueDotIndex);
/* 1747 */       if (startsWithIgnoreCaseAndWs(tmpCat, quotId) && tmpCat.trim().endsWith(quotId)) {
/* 1748 */         tmpCat = tmpCat.substring(1, tmpCat.length() - 1);
/*      */       }
/*      */       
/* 1751 */       retval = retval.substring(trueDotIndex + 1);
/* 1752 */       retval = new String(stripEnclosure(retval.getBytes(), quotId, quotId));
/*      */     }
/*      */     else {
/*      */       
/* 1756 */       retval = new String(stripEnclosure(retval.getBytes(), quotId, quotId));
/*      */     } 
/*      */ 
/*      */     
/* 1760 */     retTokens.add(tmpCat);
/* 1761 */     retTokens.add(retval);
/* 1762 */     return retTokens;
/*      */   }
/*      */   
/*      */   public static final boolean isEmptyOrWhitespaceOnly(String str) {
/* 1766 */     if (str == null || str.length() == 0) {
/* 1767 */       return true;
/*      */     }
/*      */     
/* 1770 */     int length = str.length();
/*      */     
/* 1772 */     for (int i = 0; i < length; i++) {
/* 1773 */       if (!Character.isWhitespace(str.charAt(i))) {
/* 1774 */         return false;
/*      */       }
/*      */     } 
/*      */     
/* 1778 */     return true;
/*      */   }
/*      */   
/*      */   public static String escapeQuote(String src, String quotChar) {
/* 1782 */     if (src == null) {
/* 1783 */       return null;
/*      */     }
/*      */     
/* 1786 */     src = new String(stripEnclosure(src.getBytes(), quotChar, quotChar));
/*      */     
/* 1788 */     int lastNdx = src.indexOf(quotChar);
/*      */ 
/*      */ 
/*      */     
/* 1792 */     String tmpSrc = src.substring(0, lastNdx);
/* 1793 */     tmpSrc = tmpSrc + quotChar + quotChar;
/*      */     
/* 1795 */     String tmpRest = src.substring(lastNdx + 1, src.length());
/*      */     
/* 1797 */     lastNdx = tmpRest.indexOf(quotChar);
/* 1798 */     while (lastNdx > -1) {
/*      */       
/* 1800 */       tmpSrc = tmpSrc + tmpRest.substring(0, lastNdx);
/* 1801 */       tmpSrc = tmpSrc + quotChar + quotChar;
/* 1802 */       tmpRest = tmpRest.substring(lastNdx + 1, tmpRest.length());
/*      */       
/* 1804 */       lastNdx = tmpRest.indexOf(quotChar);
/*      */     } 
/* 1806 */     tmpSrc = tmpSrc + tmpRest;
/* 1807 */     return tmpSrc;
/*      */   }
/*      */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\mysql\jdbc\StringUtils.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */