/*      */ package com.mysql.jdbc;
/*      */ 
/*      */ import java.sql.SQLException;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Collections;
/*      */ import java.util.HashMap;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Locale;
/*      */ import java.util.Map;
/*      */ import java.util.Properties;
/*      */ import java.util.Set;
/*      */ import java.util.TreeMap;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class CharsetMapping
/*      */ {
/*   50 */   private static final Properties CHARSET_CONFIG = new Properties();
/*      */ 
/*      */   
/*      */   public static final String[] INDEX_TO_CHARSET;
/*      */ 
/*      */   
/*      */   public static final String[] INDEX_TO_COLLATION;
/*      */ 
/*      */   
/*      */   private static final Map JAVA_TO_MYSQL_CHARSET_MAP;
/*      */ 
/*      */   
/*      */   private static final Map JAVA_UC_TO_MYSQL_CHARSET_MAP;
/*      */ 
/*      */   
/*      */   private static final Map ERROR_MESSAGE_FILE_TO_MYSQL_CHARSET_MAP;
/*      */ 
/*      */   
/*      */   private static final Map MULTIBYTE_CHARSETS;
/*      */ 
/*      */   
/*      */   private static final Map MYSQL_TO_JAVA_CHARSET_MAP;
/*      */   
/*      */   private static final Map MYSQL_ENCODING_NAME_TO_CHARSET_INDEX_MAP;
/*      */   
/*      */   private static final String NOT_USED = "ISO8859_1";
/*      */   
/*      */   public static final Map STATIC_CHARSET_TO_NUM_BYTES_MAP;
/*      */ 
/*      */   
/*      */   static  {
/*   81 */     tempNumBytesMap = new HashMap();
/*      */     
/*   83 */     tempNumBytesMap.put("big5", Constants.integerValueOf(2));
/*   84 */     tempNumBytesMap.put("dec8", Constants.integerValueOf(1));
/*   85 */     tempNumBytesMap.put("cp850", Constants.integerValueOf(1));
/*   86 */     tempNumBytesMap.put("hp8", Constants.integerValueOf(1));
/*   87 */     tempNumBytesMap.put("koi8r", Constants.integerValueOf(1));
/*   88 */     tempNumBytesMap.put("latin1", Constants.integerValueOf(1));
/*   89 */     tempNumBytesMap.put("latin2", Constants.integerValueOf(1));
/*   90 */     tempNumBytesMap.put("swe7", Constants.integerValueOf(1));
/*   91 */     tempNumBytesMap.put("ascii", Constants.integerValueOf(1));
/*   92 */     tempNumBytesMap.put("ujis", Constants.integerValueOf(3));
/*   93 */     tempNumBytesMap.put("sjis", Constants.integerValueOf(2));
/*   94 */     tempNumBytesMap.put("hebrew", Constants.integerValueOf(1));
/*   95 */     tempNumBytesMap.put("tis620", Constants.integerValueOf(1));
/*   96 */     tempNumBytesMap.put("euckr", Constants.integerValueOf(2));
/*   97 */     tempNumBytesMap.put("koi8u", Constants.integerValueOf(1));
/*   98 */     tempNumBytesMap.put("gb2312", Constants.integerValueOf(2));
/*   99 */     tempNumBytesMap.put("greek", Constants.integerValueOf(1));
/*  100 */     tempNumBytesMap.put("cp1250", Constants.integerValueOf(1));
/*  101 */     tempNumBytesMap.put("gbk", Constants.integerValueOf(2));
/*  102 */     tempNumBytesMap.put("latin5", Constants.integerValueOf(1));
/*  103 */     tempNumBytesMap.put("armscii8", Constants.integerValueOf(1));
/*  104 */     tempNumBytesMap.put("utf8", Constants.integerValueOf(3));
/*  105 */     tempNumBytesMap.put("ucs2", Constants.integerValueOf(2));
/*  106 */     tempNumBytesMap.put("cp866", Constants.integerValueOf(1));
/*  107 */     tempNumBytesMap.put("keybcs2", Constants.integerValueOf(1));
/*  108 */     tempNumBytesMap.put("macce", Constants.integerValueOf(1));
/*  109 */     tempNumBytesMap.put("macroman", Constants.integerValueOf(1));
/*  110 */     tempNumBytesMap.put("cp852", Constants.integerValueOf(1));
/*  111 */     tempNumBytesMap.put("latin7", Constants.integerValueOf(1));
/*  112 */     tempNumBytesMap.put("cp1251", Constants.integerValueOf(1));
/*  113 */     tempNumBytesMap.put("cp1256", Constants.integerValueOf(1));
/*  114 */     tempNumBytesMap.put("cp1257", Constants.integerValueOf(1));
/*  115 */     tempNumBytesMap.put("binary", Constants.integerValueOf(1));
/*  116 */     tempNumBytesMap.put("geostd8", Constants.integerValueOf(1));
/*  117 */     tempNumBytesMap.put("cp932", Constants.integerValueOf(2));
/*  118 */     tempNumBytesMap.put("eucjpms", Constants.integerValueOf(3));
/*  119 */     tempNumBytesMap.put("utf8mb4", Constants.integerValueOf(4));
/*      */     
/*  121 */     STATIC_CHARSET_TO_NUM_BYTES_MAP = Collections.unmodifiableMap(tempNumBytesMap);
/*      */ 
/*      */     
/*  124 */     CHARSET_CONFIG.setProperty("javaToMysqlMappings", "US-ASCII =\t\t\tusa7,US-ASCII =\t\t\t>4.1.0 ascii,Big5 = \t\t\t\tbig5,GBK = \t\t\t\tgbk,SJIS = \t\t\t\tsjis,EUC_CN = \t\t\tgb2312,EUC_JP = \t\t\tujis,EUC_JP_Solaris = \t>5.0.3 eucjpms,EUC_KR = \t\t\teuc_kr,EUC_KR = \t\t\t>4.1.0 euckr,ISO8859_1 =\t\t\t*latin1,ISO8859_1 =\t\t\tlatin1_de,ISO8859_1 =\t\t\tgerman1,ISO8859_1 =\t\t\tdanish,ISO8859_2 =\t\t\tlatin2,ISO8859_2 =\t\t\tczech,ISO8859_2 =\t\t\thungarian,ISO8859_2  =\t\tcroat,ISO8859_7  =\t\tgreek,ISO8859_7  =\t\tlatin7,ISO8859_8  = \t\thebrew,ISO8859_9  =\t\tlatin5,ISO8859_13 =\t\tlatvian,ISO8859_13 =\t\tlatvian1,ISO8859_13 =\t\testonia,Cp437 =             *>4.1.0 cp850,Cp437 =\t\t\t\tdos,Cp850 =\t\t\t\tcp850,Cp852 = \t\t\tcp852,Cp866 = \t\t\tcp866,KOI8_R = \t\t\tkoi8_ru,KOI8_R = \t\t\t>4.1.0 koi8r,TIS620 = \t\t\ttis620,Cp1250 = \t\t\tcp1250,Cp1250 = \t\t\twin1250,Cp1251 = \t\t\t*>4.1.0 cp1251,Cp1251 = \t\t\twin1251,Cp1251 = \t\t\tcp1251cias,Cp1251 = \t\t\tcp1251csas,Cp1256 = \t\t\tcp1256,Cp1251 = \t\t\twin1251ukr,Cp1252 =             latin1,Cp1257 = \t\t\tcp1257,MacRoman = \t\t\tmacroman,MacCentralEurope = \tmacce,UTF-8 = \t\tutf8,UTF-8 =\t\t\t\t*> 5.5.2 utf8mb4,UnicodeBig = \tucs2,US-ASCII =\t\tbinary,Cp943 =        \tsjis,MS932 =\t\t\tsjis,MS932 =        \t>4.1.11 cp932,WINDOWS-31J =\tsjis,WINDOWS-31J = \t>4.1.11 cp932,CP932 =\t\t\tsjis,CP932 =\t\t\t*>4.1.11 cp932,SHIFT_JIS = \tsjis,ASCII =\t\t\tascii,LATIN5 =\t\tlatin5,LATIN7 =\t\tlatin7,HEBREW =\t\thebrew,GREEK =\t\t\tgreek,EUCKR =\t\t\teuckr,GB2312 =\t\tgb2312,LATIN2 =\t\tlatin2,UTF-16 = \t>5.2.0 utf16,UTF-32 = \t>5.2.0 utf32");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  202 */     HashMap javaToMysqlMap = new HashMap();
/*      */     
/*  204 */     populateMapWithKeyValuePairs("javaToMysqlMappings", javaToMysqlMap, true, false);
/*      */     
/*  206 */     JAVA_TO_MYSQL_CHARSET_MAP = Collections.unmodifiableMap(javaToMysqlMap);
/*      */     
/*  208 */     HashMap mysqlToJavaMap = new HashMap();
/*      */     
/*  210 */     Set keySet = JAVA_TO_MYSQL_CHARSET_MAP.keySet();
/*      */     
/*  212 */     Iterator javaCharsets = keySet.iterator();
/*      */     
/*  214 */     while (javaCharsets.hasNext()) {
/*  215 */       Object javaEncodingName = javaCharsets.next();
/*  216 */       List mysqlEncodingList = (List)JAVA_TO_MYSQL_CHARSET_MAP.get(javaEncodingName);
/*      */ 
/*      */       
/*  219 */       Iterator mysqlEncodings = mysqlEncodingList.iterator();
/*      */       
/*  221 */       String mysqlEncodingName = null;
/*      */       
/*  223 */       while (mysqlEncodings.hasNext()) {
/*  224 */         VersionedStringProperty mysqlProp = (VersionedStringProperty)mysqlEncodings.next();
/*      */         
/*  226 */         mysqlEncodingName = mysqlProp.toString();
/*      */         
/*  228 */         mysqlToJavaMap.put(mysqlEncodingName, javaEncodingName);
/*  229 */         mysqlToJavaMap.put(mysqlEncodingName.toUpperCase(Locale.ENGLISH), javaEncodingName);
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  235 */     mysqlToJavaMap.put("cp932", "Windows-31J");
/*  236 */     mysqlToJavaMap.put("CP932", "Windows-31J");
/*      */     
/*  238 */     MYSQL_TO_JAVA_CHARSET_MAP = Collections.unmodifiableMap(mysqlToJavaMap);
/*      */     
/*  240 */     TreeMap ucMap = new TreeMap(String.CASE_INSENSITIVE_ORDER);
/*      */     
/*  242 */     Iterator javaNamesKeys = JAVA_TO_MYSQL_CHARSET_MAP.keySet().iterator();
/*      */     
/*  244 */     while (javaNamesKeys.hasNext()) {
/*  245 */       String key = (String)javaNamesKeys.next();
/*      */       
/*  247 */       ucMap.put(key.toUpperCase(Locale.ENGLISH), JAVA_TO_MYSQL_CHARSET_MAP.get(key));
/*      */     } 
/*      */ 
/*      */     
/*  251 */     JAVA_UC_TO_MYSQL_CHARSET_MAP = Collections.unmodifiableMap(ucMap);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  257 */     HashMap tempMapMulti = new HashMap();
/*      */     
/*  259 */     CHARSET_CONFIG.setProperty("multibyteCharsets", "Big5 = \t\t\tbig5,GBK = \t\t\tgbk,SJIS = \t\t\tsjis,EUC_CN = \t\tgb2312,EUC_JP = \t\tujis,EUC_JP_Solaris = eucjpms,EUC_KR = \t\teuc_kr,EUC_KR = \t\t>4.1.0 euckr,Cp943 =        \tsjis,Cp943 = \t\tcp943,WINDOWS-31J =\tsjis,WINDOWS-31J = \tcp932,CP932 =\t\t\tcp932,MS932 =\t\t\tsjis,MS932 =        \tcp932,SHIFT_JIS = \tsjis,EUCKR =\t\t\teuckr,GB2312 =\t\tgb2312,UTF-8 = \t\tutf8,utf8 =          utf8,UnicodeBig = \tucs2");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  291 */     populateMapWithKeyValuePairs("multibyteCharsets", tempMapMulti, false, true);
/*      */ 
/*      */     
/*  294 */     MULTIBYTE_CHARSETS = Collections.unmodifiableMap(tempMapMulti);
/*      */     
/*  296 */     INDEX_TO_CHARSET = new String[255];
/*      */     
/*      */     try {
/*  299 */       INDEX_TO_CHARSET[1] = getJavaEncodingForMysqlEncoding("big5", null);
/*  300 */       INDEX_TO_CHARSET[2] = getJavaEncodingForMysqlEncoding("czech", null);
/*  301 */       INDEX_TO_CHARSET[3] = "ISO8859_1";
/*  302 */       INDEX_TO_CHARSET[4] = "ISO8859_1";
/*  303 */       INDEX_TO_CHARSET[5] = getJavaEncodingForMysqlEncoding("german1", null);
/*      */       
/*  305 */       INDEX_TO_CHARSET[6] = "ISO8859_1";
/*  306 */       INDEX_TO_CHARSET[7] = getJavaEncodingForMysqlEncoding("koi8_ru", null);
/*      */       
/*  308 */       INDEX_TO_CHARSET[8] = getJavaEncodingForMysqlEncoding("latin1", null);
/*      */       
/*  310 */       INDEX_TO_CHARSET[9] = getJavaEncodingForMysqlEncoding("latin2", null);
/*      */       
/*  312 */       INDEX_TO_CHARSET[10] = "ISO8859_1";
/*  313 */       INDEX_TO_CHARSET[11] = getJavaEncodingForMysqlEncoding("usa7", null);
/*  314 */       INDEX_TO_CHARSET[12] = getJavaEncodingForMysqlEncoding("ujis", null);
/*  315 */       INDEX_TO_CHARSET[13] = getJavaEncodingForMysqlEncoding("sjis", null);
/*  316 */       INDEX_TO_CHARSET[14] = getJavaEncodingForMysqlEncoding("cp1251", null);
/*      */       
/*  318 */       INDEX_TO_CHARSET[15] = getJavaEncodingForMysqlEncoding("danish", null);
/*      */       
/*  320 */       INDEX_TO_CHARSET[16] = getJavaEncodingForMysqlEncoding("hebrew", null);
/*      */ 
/*      */       
/*  323 */       INDEX_TO_CHARSET[17] = "ISO8859_1";
/*      */       
/*  325 */       INDEX_TO_CHARSET[18] = getJavaEncodingForMysqlEncoding("tis620", null);
/*      */       
/*  327 */       INDEX_TO_CHARSET[19] = getJavaEncodingForMysqlEncoding("euc_kr", null);
/*      */       
/*  329 */       INDEX_TO_CHARSET[20] = getJavaEncodingForMysqlEncoding("estonia", null);
/*      */       
/*  331 */       INDEX_TO_CHARSET[21] = getJavaEncodingForMysqlEncoding("hungarian", null);
/*      */       
/*  333 */       INDEX_TO_CHARSET[22] = "KOI8_R";
/*  334 */       INDEX_TO_CHARSET[23] = getJavaEncodingForMysqlEncoding("win1251ukr", null);
/*      */       
/*  336 */       INDEX_TO_CHARSET[24] = getJavaEncodingForMysqlEncoding("gb2312", null);
/*      */       
/*  338 */       INDEX_TO_CHARSET[25] = getJavaEncodingForMysqlEncoding("greek", null);
/*      */       
/*  340 */       INDEX_TO_CHARSET[26] = getJavaEncodingForMysqlEncoding("win1250", null);
/*      */       
/*  342 */       INDEX_TO_CHARSET[27] = getJavaEncodingForMysqlEncoding("croat", null);
/*      */       
/*  344 */       INDEX_TO_CHARSET[28] = getJavaEncodingForMysqlEncoding("gbk", null);
/*  345 */       INDEX_TO_CHARSET[29] = getJavaEncodingForMysqlEncoding("cp1257", null);
/*      */       
/*  347 */       INDEX_TO_CHARSET[30] = getJavaEncodingForMysqlEncoding("latin5", null);
/*      */       
/*  349 */       INDEX_TO_CHARSET[31] = getJavaEncodingForMysqlEncoding("latin1_de", null);
/*      */       
/*  351 */       INDEX_TO_CHARSET[32] = "ISO8859_1";
/*  352 */       INDEX_TO_CHARSET[33] = getJavaEncodingForMysqlEncoding("utf8", null);
/*  353 */       INDEX_TO_CHARSET[34] = "Cp1250";
/*  354 */       INDEX_TO_CHARSET[35] = getJavaEncodingForMysqlEncoding("ucs2", null);
/*  355 */       INDEX_TO_CHARSET[36] = getJavaEncodingForMysqlEncoding("cp866", null);
/*      */       
/*  357 */       INDEX_TO_CHARSET[37] = "Cp895";
/*  358 */       INDEX_TO_CHARSET[38] = getJavaEncodingForMysqlEncoding("macce", null);
/*      */       
/*  360 */       INDEX_TO_CHARSET[39] = getJavaEncodingForMysqlEncoding("macroman", null);
/*      */       
/*  362 */       INDEX_TO_CHARSET[40] = "latin2";
/*  363 */       INDEX_TO_CHARSET[41] = getJavaEncodingForMysqlEncoding("latvian", null);
/*      */       
/*  365 */       INDEX_TO_CHARSET[42] = getJavaEncodingForMysqlEncoding("latvian1", null);
/*      */       
/*  367 */       INDEX_TO_CHARSET[43] = getJavaEncodingForMysqlEncoding("macce", null);
/*      */       
/*  369 */       INDEX_TO_CHARSET[44] = getJavaEncodingForMysqlEncoding("macce", null);
/*      */       
/*  371 */       INDEX_TO_CHARSET[45] = getJavaEncodingForMysqlEncoding("utf8mb4", null);
/*      */       
/*  373 */       INDEX_TO_CHARSET[46] = getJavaEncodingForMysqlEncoding("utf8mb4", null);
/*      */       
/*  375 */       INDEX_TO_CHARSET[47] = getJavaEncodingForMysqlEncoding("latin1", null);
/*      */       
/*  377 */       INDEX_TO_CHARSET[48] = getJavaEncodingForMysqlEncoding("latin1", null);
/*      */       
/*  379 */       INDEX_TO_CHARSET[49] = getJavaEncodingForMysqlEncoding("latin1", null);
/*      */       
/*  381 */       INDEX_TO_CHARSET[50] = getJavaEncodingForMysqlEncoding("cp1251", null);
/*      */       
/*  383 */       INDEX_TO_CHARSET[51] = getJavaEncodingForMysqlEncoding("cp1251", null);
/*      */       
/*  385 */       INDEX_TO_CHARSET[52] = getJavaEncodingForMysqlEncoding("cp1251", null);
/*      */       
/*  387 */       INDEX_TO_CHARSET[53] = getJavaEncodingForMysqlEncoding("macroman", null);
/*      */       
/*  389 */       INDEX_TO_CHARSET[54] = getJavaEncodingForMysqlEncoding("macroman", null);
/*      */       
/*  391 */       INDEX_TO_CHARSET[55] = getJavaEncodingForMysqlEncoding("macroman", null);
/*      */       
/*  393 */       INDEX_TO_CHARSET[56] = getJavaEncodingForMysqlEncoding("macroman", null);
/*      */       
/*  395 */       INDEX_TO_CHARSET[57] = getJavaEncodingForMysqlEncoding("cp1256", null);
/*      */ 
/*      */       
/*  398 */       INDEX_TO_CHARSET[58] = "ISO8859_1";
/*  399 */       INDEX_TO_CHARSET[59] = "ISO8859_1";
/*  400 */       INDEX_TO_CHARSET[60] = "ISO8859_1";
/*  401 */       INDEX_TO_CHARSET[61] = "ISO8859_1";
/*  402 */       INDEX_TO_CHARSET[62] = "ISO8859_1";
/*      */       
/*  404 */       INDEX_TO_CHARSET[63] = getJavaEncodingForMysqlEncoding("binary", null);
/*      */       
/*  406 */       INDEX_TO_CHARSET[64] = "ISO8859_2";
/*  407 */       INDEX_TO_CHARSET[65] = getJavaEncodingForMysqlEncoding("ascii", null);
/*      */       
/*  409 */       INDEX_TO_CHARSET[66] = getJavaEncodingForMysqlEncoding("cp1250", null);
/*      */       
/*  411 */       INDEX_TO_CHARSET[67] = getJavaEncodingForMysqlEncoding("cp1256", null);
/*      */       
/*  413 */       INDEX_TO_CHARSET[68] = getJavaEncodingForMysqlEncoding("cp866", null);
/*      */       
/*  415 */       INDEX_TO_CHARSET[69] = "US-ASCII";
/*  416 */       INDEX_TO_CHARSET[70] = getJavaEncodingForMysqlEncoding("greek", null);
/*      */       
/*  418 */       INDEX_TO_CHARSET[71] = getJavaEncodingForMysqlEncoding("hebrew", null);
/*      */       
/*  420 */       INDEX_TO_CHARSET[72] = "US-ASCII";
/*  421 */       INDEX_TO_CHARSET[73] = "Cp895";
/*  422 */       INDEX_TO_CHARSET[74] = getJavaEncodingForMysqlEncoding("koi8r", null);
/*      */       
/*  424 */       INDEX_TO_CHARSET[75] = "KOI8_r";
/*      */       
/*  426 */       INDEX_TO_CHARSET[76] = "ISO8859_1";
/*      */       
/*  428 */       INDEX_TO_CHARSET[77] = getJavaEncodingForMysqlEncoding("latin2", null);
/*      */       
/*  430 */       INDEX_TO_CHARSET[78] = getJavaEncodingForMysqlEncoding("latin5", null);
/*      */       
/*  432 */       INDEX_TO_CHARSET[79] = getJavaEncodingForMysqlEncoding("latin7", null);
/*      */       
/*  434 */       INDEX_TO_CHARSET[80] = getJavaEncodingForMysqlEncoding("cp850", null);
/*      */       
/*  436 */       INDEX_TO_CHARSET[81] = getJavaEncodingForMysqlEncoding("cp852", null);
/*      */       
/*  438 */       INDEX_TO_CHARSET[82] = "ISO8859_1";
/*  439 */       INDEX_TO_CHARSET[83] = getJavaEncodingForMysqlEncoding("utf8", null);
/*  440 */       INDEX_TO_CHARSET[84] = getJavaEncodingForMysqlEncoding("big5", null);
/*  441 */       INDEX_TO_CHARSET[85] = getJavaEncodingForMysqlEncoding("euckr", null);
/*      */       
/*  443 */       INDEX_TO_CHARSET[86] = getJavaEncodingForMysqlEncoding("gb2312", null);
/*      */       
/*  445 */       INDEX_TO_CHARSET[87] = getJavaEncodingForMysqlEncoding("gbk", null);
/*  446 */       INDEX_TO_CHARSET[88] = getJavaEncodingForMysqlEncoding("sjis", null);
/*  447 */       INDEX_TO_CHARSET[89] = getJavaEncodingForMysqlEncoding("tis620", null);
/*      */       
/*  449 */       INDEX_TO_CHARSET[90] = getJavaEncodingForMysqlEncoding("ucs2", null);
/*  450 */       INDEX_TO_CHARSET[91] = getJavaEncodingForMysqlEncoding("ujis", null);
/*  451 */       INDEX_TO_CHARSET[92] = "US-ASCII";
/*  452 */       INDEX_TO_CHARSET[93] = "US-ASCII";
/*  453 */       INDEX_TO_CHARSET[94] = getJavaEncodingForMysqlEncoding("latin1", null);
/*      */       
/*  455 */       INDEX_TO_CHARSET[95] = getJavaEncodingForMysqlEncoding("cp932", null);
/*      */       
/*  457 */       INDEX_TO_CHARSET[96] = getJavaEncodingForMysqlEncoding("cp932", null);
/*      */       
/*  459 */       INDEX_TO_CHARSET[97] = getJavaEncodingForMysqlEncoding("eucjpms", null);
/*      */       
/*  461 */       INDEX_TO_CHARSET[98] = getJavaEncodingForMysqlEncoding("eucjpms", null);
/*      */ 
/*      */       
/*  464 */       for (i = 99; i < 128; i++) {
/*  465 */         INDEX_TO_CHARSET[i] = "ISO8859_1";
/*      */       }
/*      */       
/*  468 */       INDEX_TO_CHARSET[128] = getJavaEncodingForMysqlEncoding("ucs2", null);
/*      */       
/*  470 */       INDEX_TO_CHARSET[129] = getJavaEncodingForMysqlEncoding("ucs2", null);
/*      */       
/*  472 */       INDEX_TO_CHARSET[130] = getJavaEncodingForMysqlEncoding("ucs2", null);
/*      */       
/*  474 */       INDEX_TO_CHARSET[131] = getJavaEncodingForMysqlEncoding("ucs2", null);
/*      */       
/*  476 */       INDEX_TO_CHARSET[132] = getJavaEncodingForMysqlEncoding("ucs2", null);
/*      */       
/*  478 */       INDEX_TO_CHARSET[133] = getJavaEncodingForMysqlEncoding("ucs2", null);
/*      */       
/*  480 */       INDEX_TO_CHARSET[134] = getJavaEncodingForMysqlEncoding("ucs2", null);
/*      */       
/*  482 */       INDEX_TO_CHARSET[135] = getJavaEncodingForMysqlEncoding("ucs2", null);
/*      */       
/*  484 */       INDEX_TO_CHARSET[136] = getJavaEncodingForMysqlEncoding("ucs2", null);
/*      */       
/*  486 */       INDEX_TO_CHARSET[137] = getJavaEncodingForMysqlEncoding("ucs2", null);
/*      */       
/*  488 */       INDEX_TO_CHARSET[138] = getJavaEncodingForMysqlEncoding("ucs2", null);
/*      */       
/*  490 */       INDEX_TO_CHARSET[139] = getJavaEncodingForMysqlEncoding("ucs2", null);
/*      */       
/*  492 */       INDEX_TO_CHARSET[140] = getJavaEncodingForMysqlEncoding("ucs2", null);
/*      */       
/*  494 */       INDEX_TO_CHARSET[141] = getJavaEncodingForMysqlEncoding("ucs2", null);
/*      */       
/*  496 */       INDEX_TO_CHARSET[142] = getJavaEncodingForMysqlEncoding("ucs2", null);
/*      */       
/*  498 */       INDEX_TO_CHARSET[143] = getJavaEncodingForMysqlEncoding("ucs2", null);
/*      */       
/*  500 */       INDEX_TO_CHARSET[144] = getJavaEncodingForMysqlEncoding("ucs2", null);
/*      */       
/*  502 */       INDEX_TO_CHARSET[145] = getJavaEncodingForMysqlEncoding("ucs2", null);
/*      */       
/*  504 */       INDEX_TO_CHARSET[146] = getJavaEncodingForMysqlEncoding("ucs2", null);
/*      */ 
/*      */       
/*  507 */       for (i = 147; i < 192; i++) {
/*  508 */         INDEX_TO_CHARSET[i] = "ISO8859_1";
/*      */       }
/*      */       
/*  511 */       INDEX_TO_CHARSET[192] = getJavaEncodingForMysqlEncoding("utf8", null);
/*      */       
/*  513 */       INDEX_TO_CHARSET[193] = getJavaEncodingForMysqlEncoding("utf8", null);
/*      */       
/*  515 */       INDEX_TO_CHARSET[194] = getJavaEncodingForMysqlEncoding("utf8", null);
/*      */       
/*  517 */       INDEX_TO_CHARSET[195] = getJavaEncodingForMysqlEncoding("utf8", null);
/*      */       
/*  519 */       INDEX_TO_CHARSET[196] = getJavaEncodingForMysqlEncoding("utf8", null);
/*      */       
/*  521 */       INDEX_TO_CHARSET[197] = getJavaEncodingForMysqlEncoding("utf8", null);
/*      */       
/*  523 */       INDEX_TO_CHARSET[198] = getJavaEncodingForMysqlEncoding("utf8", null);
/*      */       
/*  525 */       INDEX_TO_CHARSET[199] = getJavaEncodingForMysqlEncoding("utf8", null);
/*      */       
/*  527 */       INDEX_TO_CHARSET[200] = getJavaEncodingForMysqlEncoding("utf8", null);
/*      */       
/*  529 */       INDEX_TO_CHARSET[201] = getJavaEncodingForMysqlEncoding("utf8", null);
/*      */       
/*  531 */       INDEX_TO_CHARSET[202] = getJavaEncodingForMysqlEncoding("utf8", null);
/*      */       
/*  533 */       INDEX_TO_CHARSET[203] = getJavaEncodingForMysqlEncoding("utf8", null);
/*      */       
/*  535 */       INDEX_TO_CHARSET[204] = getJavaEncodingForMysqlEncoding("utf8", null);
/*      */       
/*  537 */       INDEX_TO_CHARSET[205] = getJavaEncodingForMysqlEncoding("utf8", null);
/*      */       
/*  539 */       INDEX_TO_CHARSET[206] = getJavaEncodingForMysqlEncoding("utf8", null);
/*      */       
/*  541 */       INDEX_TO_CHARSET[207] = getJavaEncodingForMysqlEncoding("utf8", null);
/*      */       
/*  543 */       INDEX_TO_CHARSET[208] = getJavaEncodingForMysqlEncoding("utf8", null);
/*      */       
/*  545 */       INDEX_TO_CHARSET[209] = getJavaEncodingForMysqlEncoding("utf8", null);
/*      */       
/*  547 */       INDEX_TO_CHARSET[210] = getJavaEncodingForMysqlEncoding("utf8", null);
/*      */ 
/*      */       
/*  550 */       INDEX_TO_CHARSET[211] = getJavaEncodingForMysqlEncoding("utf8", null);
/*      */ 
/*      */       
/*  553 */       for (i = 212; i < 224; i++) {
/*  554 */         INDEX_TO_CHARSET[i] = "ISO8859_1";
/*      */       }
/*      */       
/*  557 */       for (i = 224; i <= 243; i++) {
/*  558 */         INDEX_TO_CHARSET[i] = getJavaEncodingForMysqlEncoding("utf8", null);
/*      */       }
/*      */ 
/*      */       
/*  562 */       for (i = 101; i <= 120; i++) {
/*  563 */         INDEX_TO_CHARSET[i] = getJavaEncodingForMysqlEncoding("utf16", null);
/*      */       }
/*      */ 
/*      */       
/*  567 */       for (i = 160; i <= 179; i++) {
/*  568 */         INDEX_TO_CHARSET[i] = getJavaEncodingForMysqlEncoding("utf32", null);
/*      */       }
/*      */ 
/*      */       
/*  572 */       for (i = 244; i < 254; i++) {
/*  573 */         INDEX_TO_CHARSET[i] = "ISO8859_1";
/*      */       }
/*      */       
/*  576 */       INDEX_TO_CHARSET[254] = getJavaEncodingForMysqlEncoding("utf8", null);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  581 */       for (int i = 1; i < INDEX_TO_CHARSET.length; i++) {
/*  582 */         if (INDEX_TO_CHARSET[i] == null) {
/*  583 */           throw new RuntimeException("Assertion failure: No mapping from charset index " + i + " to a Java character set");
/*      */         }
/*      */       } 
/*  586 */     } catch (SQLException sqlEx) {}
/*      */ 
/*      */ 
/*      */     
/*  590 */     INDEX_TO_COLLATION = new String[255];
/*      */     
/*  592 */     INDEX_TO_COLLATION[1] = "big5_chinese_ci";
/*  593 */     INDEX_TO_COLLATION[2] = "latin2_czech_cs";
/*  594 */     INDEX_TO_COLLATION[3] = "dec8_swedish_ci";
/*  595 */     INDEX_TO_COLLATION[4] = "cp850_general_ci";
/*  596 */     INDEX_TO_COLLATION[5] = "latin1_german1_ci";
/*  597 */     INDEX_TO_COLLATION[6] = "hp8_english_ci";
/*  598 */     INDEX_TO_COLLATION[7] = "koi8r_general_ci";
/*  599 */     INDEX_TO_COLLATION[8] = "latin1_swedish_ci";
/*  600 */     INDEX_TO_COLLATION[9] = "latin2_general_ci";
/*  601 */     INDEX_TO_COLLATION[10] = "swe7_swedish_ci";
/*  602 */     INDEX_TO_COLLATION[11] = "ascii_general_ci";
/*  603 */     INDEX_TO_COLLATION[12] = "ujis_japanese_ci";
/*  604 */     INDEX_TO_COLLATION[13] = "sjis_japanese_ci";
/*  605 */     INDEX_TO_COLLATION[14] = "cp1251_bulgarian_ci";
/*  606 */     INDEX_TO_COLLATION[15] = "latin1_danish_ci";
/*  607 */     INDEX_TO_COLLATION[16] = "hebrew_general_ci";
/*  608 */     INDEX_TO_COLLATION[18] = "tis620_thai_ci";
/*  609 */     INDEX_TO_COLLATION[19] = "euckr_korean_ci";
/*  610 */     INDEX_TO_COLLATION[20] = "latin7_estonian_cs";
/*  611 */     INDEX_TO_COLLATION[21] = "latin2_hungarian_ci";
/*  612 */     INDEX_TO_COLLATION[22] = "koi8u_general_ci";
/*  613 */     INDEX_TO_COLLATION[23] = "cp1251_ukrainian_ci";
/*  614 */     INDEX_TO_COLLATION[24] = "gb2312_chinese_ci";
/*  615 */     INDEX_TO_COLLATION[25] = "greek_general_ci";
/*  616 */     INDEX_TO_COLLATION[26] = "cp1250_general_ci";
/*  617 */     INDEX_TO_COLLATION[27] = "latin2_croatian_ci";
/*  618 */     INDEX_TO_COLLATION[28] = "gbk_chinese_ci";
/*  619 */     INDEX_TO_COLLATION[29] = "cp1257_lithuanian_ci";
/*  620 */     INDEX_TO_COLLATION[30] = "latin5_turkish_ci";
/*  621 */     INDEX_TO_COLLATION[31] = "latin1_german2_ci";
/*  622 */     INDEX_TO_COLLATION[32] = "armscii8_general_ci";
/*  623 */     INDEX_TO_COLLATION[33] = "utf8_general_ci";
/*  624 */     INDEX_TO_COLLATION[34] = "cp1250_czech_cs";
/*  625 */     INDEX_TO_COLLATION[35] = "ucs2_general_ci";
/*  626 */     INDEX_TO_COLLATION[36] = "cp866_general_ci";
/*  627 */     INDEX_TO_COLLATION[37] = "keybcs2_general_ci";
/*  628 */     INDEX_TO_COLLATION[38] = "macce_general_ci";
/*  629 */     INDEX_TO_COLLATION[39] = "macroman_general_ci";
/*  630 */     INDEX_TO_COLLATION[40] = "cp852_general_ci";
/*  631 */     INDEX_TO_COLLATION[41] = "latin7_general_ci";
/*  632 */     INDEX_TO_COLLATION[42] = "latin7_general_cs";
/*  633 */     INDEX_TO_COLLATION[43] = "macce_bin";
/*  634 */     INDEX_TO_COLLATION[44] = "cp1250_croatian_ci";
/*  635 */     INDEX_TO_COLLATION[45] = "utf8mb4_general_ci";
/*  636 */     INDEX_TO_COLLATION[46] = "utf8mb4_bin";
/*  637 */     INDEX_TO_COLLATION[47] = "latin1_bin";
/*  638 */     INDEX_TO_COLLATION[48] = "latin1_general_ci";
/*  639 */     INDEX_TO_COLLATION[49] = "latin1_general_cs";
/*  640 */     INDEX_TO_COLLATION[50] = "cp1251_bin";
/*  641 */     INDEX_TO_COLLATION[51] = "cp1251_general_ci";
/*  642 */     INDEX_TO_COLLATION[52] = "cp1251_general_cs";
/*  643 */     INDEX_TO_COLLATION[53] = "macroman_bin";
/*  644 */     INDEX_TO_COLLATION[57] = "cp1256_general_ci";
/*  645 */     INDEX_TO_COLLATION[58] = "cp1257_bin";
/*  646 */     INDEX_TO_COLLATION[59] = "cp1257_general_ci";
/*  647 */     INDEX_TO_COLLATION[63] = "binary";
/*  648 */     INDEX_TO_COLLATION[64] = "armscii8_bin";
/*  649 */     INDEX_TO_COLLATION[65] = "ascii_bin";
/*  650 */     INDEX_TO_COLLATION[66] = "cp1250_bin";
/*  651 */     INDEX_TO_COLLATION[67] = "cp1256_bin";
/*  652 */     INDEX_TO_COLLATION[68] = "cp866_bin";
/*  653 */     INDEX_TO_COLLATION[69] = "dec8_bin";
/*  654 */     INDEX_TO_COLLATION[70] = "greek_bin";
/*  655 */     INDEX_TO_COLLATION[71] = "hebrew_bin";
/*  656 */     INDEX_TO_COLLATION[72] = "hp8_bin";
/*  657 */     INDEX_TO_COLLATION[73] = "keybcs2_bin";
/*  658 */     INDEX_TO_COLLATION[74] = "koi8r_bin";
/*  659 */     INDEX_TO_COLLATION[75] = "koi8u_bin";
/*  660 */     INDEX_TO_COLLATION[77] = "latin2_bin";
/*  661 */     INDEX_TO_COLLATION[78] = "latin5_bin";
/*  662 */     INDEX_TO_COLLATION[79] = "latin7_bin";
/*  663 */     INDEX_TO_COLLATION[80] = "cp850_bin";
/*  664 */     INDEX_TO_COLLATION[81] = "cp852_bin";
/*  665 */     INDEX_TO_COLLATION[82] = "swe7_bin";
/*  666 */     INDEX_TO_COLLATION[83] = "utf8_bin";
/*  667 */     INDEX_TO_COLLATION[84] = "big5_bin";
/*  668 */     INDEX_TO_COLLATION[85] = "euckr_bin";
/*  669 */     INDEX_TO_COLLATION[86] = "gb2312_bin";
/*  670 */     INDEX_TO_COLLATION[87] = "gbk_bin";
/*  671 */     INDEX_TO_COLLATION[88] = "sjis_bin";
/*  672 */     INDEX_TO_COLLATION[89] = "tis620_bin";
/*  673 */     INDEX_TO_COLLATION[90] = "ucs2_bin";
/*  674 */     INDEX_TO_COLLATION[91] = "ujis_bin";
/*  675 */     INDEX_TO_COLLATION[92] = "geostd8_general_ci";
/*  676 */     INDEX_TO_COLLATION[93] = "geostd8_bin";
/*  677 */     INDEX_TO_COLLATION[94] = "latin1_spanish_ci";
/*  678 */     INDEX_TO_COLLATION[95] = "cp932_japanese_ci";
/*  679 */     INDEX_TO_COLLATION[96] = "cp932_bin";
/*  680 */     INDEX_TO_COLLATION[97] = "eucjpms_japanese_ci";
/*  681 */     INDEX_TO_COLLATION[98] = "eucjpms_bin";
/*  682 */     INDEX_TO_COLLATION[99] = "cp1250_polish_ci";
/*  683 */     INDEX_TO_COLLATION[128] = "ucs2_unicode_ci";
/*  684 */     INDEX_TO_COLLATION[129] = "ucs2_icelandic_ci";
/*  685 */     INDEX_TO_COLLATION[130] = "ucs2_latvian_ci";
/*  686 */     INDEX_TO_COLLATION[131] = "ucs2_romanian_ci";
/*  687 */     INDEX_TO_COLLATION[132] = "ucs2_slovenian_ci";
/*  688 */     INDEX_TO_COLLATION[133] = "ucs2_polish_ci";
/*  689 */     INDEX_TO_COLLATION[134] = "ucs2_estonian_ci";
/*  690 */     INDEX_TO_COLLATION[135] = "ucs2_spanish_ci";
/*  691 */     INDEX_TO_COLLATION[136] = "ucs2_swedish_ci";
/*  692 */     INDEX_TO_COLLATION[137] = "ucs2_turkish_ci";
/*  693 */     INDEX_TO_COLLATION[138] = "ucs2_czech_ci";
/*  694 */     INDEX_TO_COLLATION[139] = "ucs2_danish_ci";
/*  695 */     INDEX_TO_COLLATION[140] = "ucs2_lithuanian_ci ";
/*  696 */     INDEX_TO_COLLATION[141] = "ucs2_slovak_ci";
/*  697 */     INDEX_TO_COLLATION[142] = "ucs2_spanish2_ci";
/*  698 */     INDEX_TO_COLLATION[143] = "ucs2_roman_ci";
/*  699 */     INDEX_TO_COLLATION[144] = "ucs2_persian_ci";
/*  700 */     INDEX_TO_COLLATION[145] = "ucs2_esperanto_ci";
/*  701 */     INDEX_TO_COLLATION[146] = "ucs2_hungarian_ci";
/*  702 */     INDEX_TO_COLLATION[192] = "utf8_unicode_ci";
/*  703 */     INDEX_TO_COLLATION[193] = "utf8_icelandic_ci";
/*  704 */     INDEX_TO_COLLATION[194] = "utf8_latvian_ci";
/*  705 */     INDEX_TO_COLLATION[195] = "utf8_romanian_ci";
/*  706 */     INDEX_TO_COLLATION[196] = "utf8_slovenian_ci";
/*  707 */     INDEX_TO_COLLATION[197] = "utf8_polish_ci";
/*  708 */     INDEX_TO_COLLATION[198] = "utf8_estonian_ci";
/*  709 */     INDEX_TO_COLLATION[199] = "utf8_spanish_ci";
/*  710 */     INDEX_TO_COLLATION[200] = "utf8_swedish_ci";
/*  711 */     INDEX_TO_COLLATION[201] = "utf8_turkish_ci";
/*  712 */     INDEX_TO_COLLATION[202] = "utf8_czech_ci";
/*  713 */     INDEX_TO_COLLATION[203] = "utf8_danish_ci";
/*  714 */     INDEX_TO_COLLATION[204] = "utf8_lithuanian_ci ";
/*  715 */     INDEX_TO_COLLATION[205] = "utf8_slovak_ci";
/*  716 */     INDEX_TO_COLLATION[206] = "utf8_spanish2_ci";
/*  717 */     INDEX_TO_COLLATION[207] = "utf8_roman_ci";
/*  718 */     INDEX_TO_COLLATION[208] = "utf8_persian_ci";
/*  719 */     INDEX_TO_COLLATION[209] = "utf8_esperanto_ci";
/*  720 */     INDEX_TO_COLLATION[210] = "utf8_hungarian_ci";
/*      */ 
/*      */ 
/*      */     
/*  724 */     INDEX_TO_COLLATION[33] = "utf8mb3_general_ci";
/*  725 */     INDEX_TO_COLLATION[83] = "utf8mb3_bin";
/*  726 */     INDEX_TO_COLLATION[192] = "utf8mb3_unicode_ci";
/*  727 */     INDEX_TO_COLLATION[193] = "utf8mb3_icelandic_ci";
/*  728 */     INDEX_TO_COLLATION[194] = "utf8mb3_latvian_ci";
/*  729 */     INDEX_TO_COLLATION[195] = "utf8mb3_romanian_ci";
/*  730 */     INDEX_TO_COLLATION[196] = "utf8mb3_slovenian_ci";
/*  731 */     INDEX_TO_COLLATION[197] = "utf8mb3_polish_ci";
/*  732 */     INDEX_TO_COLLATION[198] = "utf8mb3_estonian_ci";
/*  733 */     INDEX_TO_COLLATION[199] = "utf8mb3_spanish_ci";
/*  734 */     INDEX_TO_COLLATION[200] = "utf8mb3_swedish_ci";
/*  735 */     INDEX_TO_COLLATION[201] = "utf8mb3_turkish_ci";
/*  736 */     INDEX_TO_COLLATION[202] = "utf8mb3_czech_ci";
/*  737 */     INDEX_TO_COLLATION[203] = "utf8mb3_danish_ci";
/*  738 */     INDEX_TO_COLLATION[204] = "utf8mb3_lithuanian_ci";
/*  739 */     INDEX_TO_COLLATION[205] = "utf8mb3_slovak_ci";
/*  740 */     INDEX_TO_COLLATION[206] = "utf8mb3_spanish2_ci";
/*  741 */     INDEX_TO_COLLATION[207] = "utf8mb3_roman_ci";
/*  742 */     INDEX_TO_COLLATION[208] = "utf8mb3_persian_ci";
/*  743 */     INDEX_TO_COLLATION[209] = "utf8mb3_esperanto_ci";
/*  744 */     INDEX_TO_COLLATION[210] = "utf8mb3_hungarian_ci";
/*  745 */     INDEX_TO_COLLATION[211] = "utf8mb3_sinhala_ci";
/*  746 */     INDEX_TO_COLLATION[254] = "utf8mb3_general_cs";
/*      */ 
/*      */     
/*  749 */     INDEX_TO_COLLATION[54] = "utf16_general_ci";
/*  750 */     INDEX_TO_COLLATION[55] = "utf16_bin";
/*  751 */     INDEX_TO_COLLATION[101] = "utf16_unicode_ci";
/*  752 */     INDEX_TO_COLLATION[102] = "utf16_icelandic_ci";
/*  753 */     INDEX_TO_COLLATION[103] = "utf16_latvian_ci";
/*  754 */     INDEX_TO_COLLATION[104] = "utf16_romanian_ci";
/*  755 */     INDEX_TO_COLLATION[105] = "utf16_slovenian_ci";
/*  756 */     INDEX_TO_COLLATION[106] = "utf16_polish_ci";
/*  757 */     INDEX_TO_COLLATION[107] = "utf16_estonian_ci";
/*  758 */     INDEX_TO_COLLATION[108] = "utf16_spanish_ci";
/*  759 */     INDEX_TO_COLLATION[109] = "utf16_swedish_ci";
/*  760 */     INDEX_TO_COLLATION[110] = "utf16_turkish_ci";
/*  761 */     INDEX_TO_COLLATION[111] = "utf16_czech_ci";
/*  762 */     INDEX_TO_COLLATION[112] = "utf16_danish_ci";
/*  763 */     INDEX_TO_COLLATION[113] = "utf16_lithuanian_ci";
/*  764 */     INDEX_TO_COLLATION[114] = "utf16_slovak_ci";
/*  765 */     INDEX_TO_COLLATION[115] = "utf16_spanish2_ci";
/*  766 */     INDEX_TO_COLLATION[116] = "utf16_roman_ci";
/*  767 */     INDEX_TO_COLLATION[117] = "utf16_persian_ci";
/*  768 */     INDEX_TO_COLLATION[118] = "utf16_esperanto_ci";
/*  769 */     INDEX_TO_COLLATION[119] = "utf16_hungarian_ci";
/*  770 */     INDEX_TO_COLLATION[120] = "utf16_sinhala_ci";
/*      */     
/*  772 */     INDEX_TO_COLLATION[60] = "utf32_general_ci";
/*  773 */     INDEX_TO_COLLATION[61] = "utf32_bin";
/*  774 */     INDEX_TO_COLLATION[160] = "utf32_unicode_ci";
/*  775 */     INDEX_TO_COLLATION[161] = "utf32_icelandic_ci";
/*  776 */     INDEX_TO_COLLATION[162] = "utf32_latvian_ci";
/*  777 */     INDEX_TO_COLLATION[163] = "utf32_romanian_ci";
/*  778 */     INDEX_TO_COLLATION[164] = "utf32_slovenian_ci";
/*  779 */     INDEX_TO_COLLATION[165] = "utf32_polish_ci";
/*  780 */     INDEX_TO_COLLATION[166] = "utf32_estonian_ci";
/*  781 */     INDEX_TO_COLLATION[167] = "utf32_spanish_ci";
/*  782 */     INDEX_TO_COLLATION[168] = "utf32_swedish_ci";
/*  783 */     INDEX_TO_COLLATION[169] = "utf32_turkish_ci";
/*  784 */     INDEX_TO_COLLATION[170] = "utf32_czech_ci";
/*  785 */     INDEX_TO_COLLATION[171] = "utf32_danish_ci";
/*  786 */     INDEX_TO_COLLATION[172] = "utf32_lithuanian_ci";
/*  787 */     INDEX_TO_COLLATION[173] = "utf32_slovak_ci";
/*  788 */     INDEX_TO_COLLATION[174] = "utf32_spanish2_ci";
/*  789 */     INDEX_TO_COLLATION[175] = "utf32_roman_ci";
/*  790 */     INDEX_TO_COLLATION[176] = "utf32_persian_ci";
/*  791 */     INDEX_TO_COLLATION[177] = "utf32_esperanto_ci";
/*  792 */     INDEX_TO_COLLATION[178] = "utf32_hungarian_ci";
/*  793 */     INDEX_TO_COLLATION[179] = "utf32_sinhala_ci";
/*      */     
/*  795 */     INDEX_TO_COLLATION[224] = "utf8mb4_unicode_ci";
/*  796 */     INDEX_TO_COLLATION[225] = "utf8mb4_icelandic_ci";
/*  797 */     INDEX_TO_COLLATION[226] = "utf8mb4_latvian_ci";
/*  798 */     INDEX_TO_COLLATION[227] = "utf8mb4_romanian_ci";
/*  799 */     INDEX_TO_COLLATION[228] = "utf8mb4_slovenian_ci";
/*  800 */     INDEX_TO_COLLATION[229] = "utf8mb4_polish_ci";
/*  801 */     INDEX_TO_COLLATION[230] = "utf8mb4_estonian_ci";
/*  802 */     INDEX_TO_COLLATION[231] = "utf8mb4_spanish_ci";
/*  803 */     INDEX_TO_COLLATION[232] = "utf8mb4_swedish_ci";
/*  804 */     INDEX_TO_COLLATION[233] = "utf8mb4_turkish_ci";
/*  805 */     INDEX_TO_COLLATION[234] = "utf8mb4_czech_ci";
/*  806 */     INDEX_TO_COLLATION[235] = "utf8mb4_danish_ci";
/*  807 */     INDEX_TO_COLLATION[236] = "utf8mb4_lithuanian_ci";
/*  808 */     INDEX_TO_COLLATION[237] = "utf8mb4_slovak_ci";
/*  809 */     INDEX_TO_COLLATION[238] = "utf8mb4_spanish2_ci";
/*  810 */     INDEX_TO_COLLATION[239] = "utf8mb4_roman_ci";
/*  811 */     INDEX_TO_COLLATION[240] = "utf8mb4_persian_ci";
/*  812 */     INDEX_TO_COLLATION[241] = "utf8mb4_esperanto_ci";
/*  813 */     INDEX_TO_COLLATION[242] = "utf8mb4_hungarian_ci";
/*  814 */     INDEX_TO_COLLATION[243] = "utf8mb4_sinhala_ci";
/*  815 */     INDEX_TO_COLLATION[244] = "utf8mb4_german2_ci";
/*      */     
/*  817 */     Map indexMap = new TreeMap(String.CASE_INSENSITIVE_ORDER);
/*      */     
/*  819 */     for (int i = 0; i < INDEX_TO_CHARSET.length; i++) {
/*  820 */       String mysqlEncodingName = INDEX_TO_CHARSET[i];
/*      */       
/*  822 */       if (mysqlEncodingName != null) {
/*  823 */         indexMap.put(INDEX_TO_CHARSET[i], Constants.integerValueOf(i));
/*      */       }
/*      */     } 
/*      */     
/*  827 */     MYSQL_ENCODING_NAME_TO_CHARSET_INDEX_MAP = Collections.unmodifiableMap(indexMap);
/*      */     
/*  829 */     Map tempMap = new HashMap();
/*      */     
/*  831 */     tempMap.put("czech", "latin2");
/*  832 */     tempMap.put("danish", "latin1");
/*  833 */     tempMap.put("dutch", "latin1");
/*  834 */     tempMap.put("english", "latin1");
/*  835 */     tempMap.put("estonian", "latin7");
/*  836 */     tempMap.put("french", "latin1");
/*  837 */     tempMap.put("german", "latin1");
/*  838 */     tempMap.put("greek", "greek");
/*  839 */     tempMap.put("hungarian", "latin2");
/*  840 */     tempMap.put("italian", "latin1");
/*  841 */     tempMap.put("japanese", "ujis");
/*  842 */     tempMap.put("japanese-sjis", "sjis");
/*  843 */     tempMap.put("korean", "euckr");
/*  844 */     tempMap.put("norwegian", "latin1");
/*  845 */     tempMap.put("norwegian-ny", "latin1");
/*  846 */     tempMap.put("polish", "latin2");
/*  847 */     tempMap.put("portuguese", "latin1");
/*  848 */     tempMap.put("romanian", "latin2");
/*  849 */     tempMap.put("russian", "koi8r");
/*  850 */     tempMap.put("serbian", "cp1250");
/*  851 */     tempMap.put("slovak", "latin2");
/*  852 */     tempMap.put("spanish", "latin1");
/*  853 */     tempMap.put("swedish", "latin1");
/*  854 */     tempMap.put("ukrainian", "koi8u");
/*      */     
/*  856 */     ERROR_MESSAGE_FILE_TO_MYSQL_CHARSET_MAP = Collections.unmodifiableMap(tempMap);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final String getJavaEncodingForMysqlEncoding(String mysqlEncoding, Connection conn) throws SQLException {
/*  863 */     if (conn != null && conn.versionMeetsMinimum(4, 1, 0) && "latin1".equalsIgnoreCase(mysqlEncoding))
/*      */     {
/*  865 */       return "Cp1252";
/*      */     }
/*      */     
/*  868 */     return (String)MYSQL_TO_JAVA_CHARSET_MAP.get(mysqlEncoding);
/*      */   }
/*      */ 
/*      */   
/*      */   public static final String getMysqlEncodingForJavaEncoding(String javaEncodingUC, Connection conn) throws SQLException {
/*  873 */     List mysqlEncodings = (List)JAVA_UC_TO_MYSQL_CHARSET_MAP.get(javaEncodingUC);
/*      */ 
/*      */ 
/*      */     
/*  877 */     if (mysqlEncodings != null) {
/*  878 */       Iterator iter = mysqlEncodings.iterator();
/*      */       
/*  880 */       VersionedStringProperty versionedProp = null;
/*      */       
/*  882 */       while (iter.hasNext()) {
/*  883 */         VersionedStringProperty propToCheck = (VersionedStringProperty)iter.next();
/*      */ 
/*      */         
/*  886 */         if (conn == null)
/*      */         {
/*      */           
/*  889 */           return propToCheck.toString();
/*      */         }
/*      */         
/*  892 */         if (versionedProp != null && !versionedProp.preferredValue && 
/*  893 */           versionedProp.majorVersion == propToCheck.majorVersion && versionedProp.minorVersion == propToCheck.minorVersion && versionedProp.subminorVersion == propToCheck.subminorVersion)
/*      */         {
/*      */           
/*  896 */           return versionedProp.toString();
/*      */         }
/*      */ 
/*      */         
/*  900 */         if (propToCheck.isOkayForVersion(conn)) {
/*  901 */           if (propToCheck.preferredValue) {
/*  902 */             return propToCheck.toString();
/*      */           }
/*      */           
/*  905 */           versionedProp = propToCheck;
/*      */         } 
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/*  911 */       if (versionedProp != null) {
/*  912 */         return versionedProp.toString();
/*      */       }
/*      */     } 
/*      */     
/*  916 */     return null;
/*      */   }
/*      */ 
/*      */   
/*  920 */   static final int getNumberOfCharsetsConfigured() { return MYSQL_TO_JAVA_CHARSET_MAP.size() / 2; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static final String getCharacterEncodingForErrorMessages(ConnectionImpl conn) throws SQLException {
/*  936 */     String errorMessageFile = conn.getServerVariable("language");
/*      */     
/*  938 */     if (errorMessageFile == null || errorMessageFile.length() == 0)
/*      */     {
/*  940 */       return "Cp1252";
/*      */     }
/*      */     
/*  943 */     int endWithoutSlash = errorMessageFile.length();
/*      */     
/*  945 */     if (errorMessageFile.endsWith("/") || errorMessageFile.endsWith("\\")) {
/*  946 */       endWithoutSlash--;
/*      */     }
/*      */     
/*  949 */     int lastSlashIndex = errorMessageFile.lastIndexOf('/', endWithoutSlash - 1);
/*      */     
/*  951 */     if (lastSlashIndex == -1) {
/*  952 */       lastSlashIndex = errorMessageFile.lastIndexOf('\\', endWithoutSlash - 1);
/*      */     }
/*      */     
/*  955 */     if (lastSlashIndex == -1) {
/*  956 */       lastSlashIndex = 0;
/*      */     }
/*      */     
/*  959 */     if (lastSlashIndex == endWithoutSlash || endWithoutSlash < lastSlashIndex)
/*      */     {
/*  961 */       return "Cp1252";
/*      */     }
/*      */     
/*  964 */     errorMessageFile = errorMessageFile.substring(lastSlashIndex + 1, endWithoutSlash);
/*      */     
/*  966 */     String errorMessageEncodingMysql = (String)ERROR_MESSAGE_FILE_TO_MYSQL_CHARSET_MAP.get(errorMessageFile);
/*      */     
/*  968 */     if (errorMessageEncodingMysql == null)
/*      */     {
/*  970 */       return "Cp1252";
/*      */     }
/*      */     
/*  973 */     String javaEncoding = getJavaEncodingForMysqlEncoding(errorMessageEncodingMysql, conn);
/*      */     
/*  975 */     if (javaEncoding == null)
/*      */     {
/*  977 */       return "Cp1252";
/*      */     }
/*      */     
/*  980 */     return javaEncoding;
/*      */   }
/*      */ 
/*      */   
/*  984 */   static final boolean isAliasForSjis(String encoding) { return ("SJIS".equalsIgnoreCase(encoding) || "WINDOWS-31J".equalsIgnoreCase(encoding) || "MS932".equalsIgnoreCase(encoding) || "SHIFT_JIS".equalsIgnoreCase(encoding) || "CP943".equalsIgnoreCase(encoding)); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static final boolean isMultibyteCharset(String javaEncodingName) {
/*  993 */     String javaEncodingNameUC = javaEncodingName.toUpperCase(Locale.ENGLISH);
/*      */ 
/*      */     
/*  996 */     return MULTIBYTE_CHARSETS.containsKey(javaEncodingNameUC);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static void populateMapWithKeyValuePairs(String configKey, Map mapToPopulate, boolean addVersionedProperties, boolean addUppercaseKeys) {
/* 1002 */     String javaToMysqlConfig = CHARSET_CONFIG.getProperty(configKey);
/*      */     
/* 1004 */     if (javaToMysqlConfig != null) {
/* 1005 */       List mappings = StringUtils.split(javaToMysqlConfig, ",", true);
/*      */       
/* 1007 */       if (mappings != null) {
/* 1008 */         Iterator mappingsIter = mappings.iterator();
/*      */         
/* 1010 */         while (mappingsIter.hasNext()) {
/* 1011 */           String aMapping = (String)mappingsIter.next();
/*      */           
/* 1013 */           List parsedPair = StringUtils.split(aMapping, "=", true);
/*      */           
/* 1015 */           if (parsedPair.size() == 2) {
/* 1016 */             String key = parsedPair.get(0).toString();
/* 1017 */             String value = parsedPair.get(1).toString();
/*      */             
/* 1019 */             if (addVersionedProperties) {
/* 1020 */               List versionedProperties = (List)mapToPopulate.get(key);
/*      */ 
/*      */               
/* 1023 */               if (versionedProperties == null) {
/* 1024 */                 versionedProperties = new ArrayList();
/* 1025 */                 mapToPopulate.put(key, versionedProperties);
/*      */               } 
/*      */               
/* 1028 */               VersionedStringProperty verProp = new VersionedStringProperty(value);
/*      */               
/* 1030 */               versionedProperties.add(verProp);
/*      */               
/* 1032 */               if (addUppercaseKeys) {
/* 1033 */                 String keyUc = key.toUpperCase(Locale.ENGLISH);
/*      */                 
/* 1035 */                 versionedProperties = (List)mapToPopulate.get(keyUc);
/*      */ 
/*      */                 
/* 1038 */                 if (versionedProperties == null) {
/* 1039 */                   versionedProperties = new ArrayList();
/* 1040 */                   mapToPopulate.put(keyUc, versionedProperties);
/*      */                 } 
/*      */ 
/*      */                 
/* 1044 */                 versionedProperties.add(verProp);
/*      */               }  continue;
/*      */             } 
/* 1047 */             mapToPopulate.put(key, value);
/*      */             
/* 1049 */             if (addUppercaseKeys) {
/* 1050 */               mapToPopulate.put(key.toUpperCase(Locale.ENGLISH), value);
/*      */             }
/*      */             
/*      */             continue;
/*      */           } 
/* 1055 */           throw new RuntimeException("Syntax error in Charsets.properties resource for token \"" + aMapping + "\".");
/*      */         
/*      */         }
/*      */       
/*      */       }
/*      */       else {
/*      */         
/* 1062 */         throw new RuntimeException("Missing/corrupt entry for \"" + configKey + "\" in Charsets.properties.");
/*      */       } 
/*      */     } else {
/*      */       
/* 1066 */       throw new RuntimeException("Could not find configuration value \"" + configKey + "\" in Charsets.properties resource");
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static int getCharsetIndexForMysqlEncodingName(String name) {
/* 1072 */     if (name == null) {
/* 1073 */       return 0;
/*      */     }
/*      */     
/* 1076 */     Integer asInt = (Integer)MYSQL_ENCODING_NAME_TO_CHARSET_INDEX_MAP.get(name);
/*      */     
/* 1078 */     if (asInt == null) {
/* 1079 */       return 0;
/*      */     }
/*      */     
/* 1082 */     return asInt.intValue();
/*      */   }
/*      */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\mysql\jdbc\CharsetMapping.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */