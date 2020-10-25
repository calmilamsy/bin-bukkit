/*     */ package com.avaje.ebeaninternal.server.text.csv;
/*     */ 
/*     */ import com.avaje.ebean.EbeanServer;
/*     */ import com.avaje.ebean.bean.EntityBean;
/*     */ import com.avaje.ebean.text.StringParser;
/*     */ import com.avaje.ebean.text.TextException;
/*     */ import com.avaje.ebean.text.TimeStringParser;
/*     */ import com.avaje.ebean.text.csv.CsvCallback;
/*     */ import com.avaje.ebean.text.csv.CsvReader;
/*     */ import com.avaje.ebean.text.csv.DefaultCsvCallback;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanPropertyAssocOne;
/*     */ import com.avaje.ebeaninternal.server.el.ElPropertyValue;
/*     */ import java.io.Reader;
/*     */ import java.text.DateFormat;
/*     */ import java.text.ParseException;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Date;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TCsvReader<T>
/*     */   extends Object
/*     */   implements CsvReader<T>
/*     */ {
/*  54 */   private static final TimeStringParser TIME_PARSER = new TimeStringParser(); private final EbeanServer server; private final BeanDescriptor<T> descriptor; private final List<CsvColumn> columnList;
/*     */   private final CsvColumn ignoreColumn;
/*     */   private boolean treatEmptyStringAsNull;
/*     */   private boolean hasHeader;
/*     */   
/*     */   public TCsvReader(EbeanServer server, BeanDescriptor<T> descriptor) {
/*  60 */     this.columnList = new ArrayList();
/*     */     
/*  62 */     this.ignoreColumn = new CsvColumn(null);
/*     */     
/*  64 */     this.treatEmptyStringAsNull = true;
/*     */ 
/*     */ 
/*     */     
/*  68 */     this.logInfoFrequency = 1000;
/*     */     
/*  70 */     this.defaultTimeFormat = "HH:mm:ss";
/*  71 */     this.defaultDateFormat = "yyyy-MM-dd";
/*  72 */     this.defaultTimestampFormat = "yyyy-MM-dd hh:mm:ss.fffffffff";
/*  73 */     this.defaultLocale = Locale.getDefault();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  78 */     this.persistBatchSize = 30;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  86 */     this.server = server;
/*  87 */     this.descriptor = descriptor;
/*     */   }
/*     */   private int logInfoFrequency; private String defaultTimeFormat; private String defaultDateFormat; private String defaultTimestampFormat; private Locale defaultLocale; protected int persistBatchSize; private boolean addPropertiesFromHeader;
/*     */   
/*  91 */   public void setDefaultLocale(Locale defaultLocale) { this.defaultLocale = defaultLocale; }
/*     */ 
/*     */ 
/*     */   
/*  95 */   public void setDefaultTimeFormat(String defaultTimeFormat) { this.defaultTimeFormat = defaultTimeFormat; }
/*     */ 
/*     */ 
/*     */   
/*  99 */   public void setDefaultDateFormat(String defaultDateFormat) { this.defaultDateFormat = defaultDateFormat; }
/*     */ 
/*     */ 
/*     */   
/* 103 */   public void setDefaultTimestampFormat(String defaultTimestampFormat) { this.defaultTimestampFormat = defaultTimestampFormat; }
/*     */ 
/*     */ 
/*     */   
/* 107 */   public void setPersistBatchSize(int persistBatchSize) { this.persistBatchSize = persistBatchSize; }
/*     */ 
/*     */ 
/*     */   
/* 111 */   public void setIgnoreHeader() { setHasHeader(true, false); }
/*     */ 
/*     */ 
/*     */   
/* 115 */   public void setAddPropertiesFromHeader() { setHasHeader(true, true); }
/*     */ 
/*     */   
/*     */   public void setHasHeader(boolean hasHeader, boolean addPropertiesFromHeader) {
/* 119 */     this.hasHeader = hasHeader;
/* 120 */     this.addPropertiesFromHeader = addPropertiesFromHeader;
/*     */   }
/*     */ 
/*     */   
/* 124 */   public void setLogInfoFrequency(int logInfoFrequency) { this.logInfoFrequency = logInfoFrequency; }
/*     */ 
/*     */ 
/*     */   
/* 128 */   public void addIgnore() { this.columnList.add(this.ignoreColumn); }
/*     */ 
/*     */ 
/*     */   
/* 132 */   public void addProperty(String propertyName) { addProperty(propertyName, null); }
/*     */ 
/*     */ 
/*     */   
/* 136 */   public void addReference(String propertyName) { addProperty(propertyName, null, true); }
/*     */ 
/*     */ 
/*     */   
/* 140 */   public void addProperty(String propertyName, StringParser parser) { addProperty(propertyName, parser, false); }
/*     */ 
/*     */ 
/*     */   
/* 144 */   public void addDateTime(String propertyName, String dateTimeFormat) { addDateTime(propertyName, dateTimeFormat, Locale.getDefault()); }
/*     */ 
/*     */ 
/*     */   
/*     */   public void addDateTime(String propertyName, String dateTimeFormat, Locale locale) {
/* 149 */     ElPropertyValue elProp = this.descriptor.getElGetValue(propertyName);
/* 150 */     if (!elProp.isDateTimeCapable()) {
/* 151 */       throw new TextException("Property " + propertyName + " is not DateTime capable");
/*     */     }
/*     */     
/* 154 */     if (dateTimeFormat == null) {
/* 155 */       dateTimeFormat = getDefaultDateTimeFormat(elProp.getJdbcType());
/*     */     }
/*     */     
/* 158 */     if (locale == null) {
/* 159 */       locale = this.defaultLocale;
/*     */     }
/*     */     
/* 162 */     SimpleDateFormat sdf = new SimpleDateFormat(dateTimeFormat, locale);
/* 163 */     DateTimeParser parser = new DateTimeParser(sdf, dateTimeFormat, elProp);
/*     */     
/* 165 */     CsvColumn column = new CsvColumn(elProp, parser, false);
/* 166 */     this.columnList.add(column);
/*     */   }
/*     */   
/*     */   private String getDefaultDateTimeFormat(int jdbcType) {
/* 170 */     switch (jdbcType) {
/*     */       case 92:
/* 172 */         return this.defaultTimeFormat;
/*     */       case 91:
/* 174 */         return this.defaultDateFormat;
/*     */       case 93:
/* 176 */         return this.defaultTimestampFormat;
/*     */     } 
/*     */     
/* 179 */     throw new RuntimeException("Expected java.sql.Types TIME,DATE or TIMESTAMP but got [" + jdbcType + "]");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void addProperty(String propertyName, StringParser parser, boolean reference) {
/* 185 */     ElPropertyValue elProp = this.descriptor.getElGetValue(propertyName);
/* 186 */     if (parser == null) {
/* 187 */       parser = elProp.getStringParser();
/*     */     }
/* 189 */     CsvColumn column = new CsvColumn(elProp, parser, reference);
/* 190 */     this.columnList.add(column);
/*     */   }
/*     */   
/*     */   public void process(Reader reader) throws Exception {
/* 194 */     DefaultCsvCallback<T> callback = new DefaultCsvCallback<T>(this.persistBatchSize, this.logInfoFrequency);
/* 195 */     process(reader, callback);
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
/*     */   public void process(Reader reader, CsvCallback<T> callback) throws Exception { // Byte code:
/*     */     //   0: aload_1
/*     */     //   1: ifnonnull -> 14
/*     */     //   4: new java/lang/NullPointerException
/*     */     //   7: dup
/*     */     //   8: ldc 'reader is null?'
/*     */     //   10: invokespecial <init> : (Ljava/lang/String;)V
/*     */     //   13: athrow
/*     */     //   14: aload_2
/*     */     //   15: ifnonnull -> 28
/*     */     //   18: new java/lang/NullPointerException
/*     */     //   21: dup
/*     */     //   22: ldc 'callback is null?'
/*     */     //   24: invokespecial <init> : (Ljava/lang/String;)V
/*     */     //   27: athrow
/*     */     //   28: new com/avaje/ebeaninternal/server/text/csv/CsvUtilReader
/*     */     //   31: dup
/*     */     //   32: aload_1
/*     */     //   33: invokespecial <init> : (Ljava/io/Reader;)V
/*     */     //   36: astore_3
/*     */     //   37: aload_2
/*     */     //   38: aload_0
/*     */     //   39: getfield server : Lcom/avaje/ebean/EbeanServer;
/*     */     //   42: invokeinterface begin : (Lcom/avaje/ebean/EbeanServer;)V
/*     */     //   47: iconst_0
/*     */     //   48: istore #4
/*     */     //   50: aload_0
/*     */     //   51: getfield hasHeader : Z
/*     */     //   54: ifeq -> 84
/*     */     //   57: aload_3
/*     */     //   58: invokevirtual readNext : ()[Ljava/lang/String;
/*     */     //   61: astore #5
/*     */     //   63: aload_0
/*     */     //   64: getfield addPropertiesFromHeader : Z
/*     */     //   67: ifeq -> 76
/*     */     //   70: aload_0
/*     */     //   71: aload #5
/*     */     //   73: invokespecial addPropertiesFromHeader : ([Ljava/lang/String;)V
/*     */     //   76: aload_2
/*     */     //   77: aload #5
/*     */     //   79: invokeinterface readHeader : ([Ljava/lang/String;)V
/*     */     //   84: iinc #4, 1
/*     */     //   87: aload_3
/*     */     //   88: invokevirtual readNext : ()[Ljava/lang/String;
/*     */     //   91: astore #5
/*     */     //   93: aload #5
/*     */     //   95: ifnonnull -> 104
/*     */     //   98: iinc #4, -1
/*     */     //   101: goto -> 240
/*     */     //   104: aload_2
/*     */     //   105: iload #4
/*     */     //   107: aload #5
/*     */     //   109: invokeinterface processLine : (I[Ljava/lang/String;)Z
/*     */     //   114: ifeq -> 237
/*     */     //   117: aload #5
/*     */     //   119: arraylength
/*     */     //   120: aload_0
/*     */     //   121: getfield columnList : Ljava/util/List;
/*     */     //   124: invokeinterface size : ()I
/*     */     //   129: if_icmpeq -> 215
/*     */     //   132: new java/lang/StringBuilder
/*     */     //   135: dup
/*     */     //   136: invokespecial <init> : ()V
/*     */     //   139: ldc 'Error at line '
/*     */     //   141: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
/*     */     //   144: iload #4
/*     */     //   146: invokevirtual append : (I)Ljava/lang/StringBuilder;
/*     */     //   149: ldc '. Expected ['
/*     */     //   151: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
/*     */     //   154: aload_0
/*     */     //   155: getfield columnList : Ljava/util/List;
/*     */     //   158: invokeinterface size : ()I
/*     */     //   163: invokevirtual append : (I)Ljava/lang/StringBuilder;
/*     */     //   166: ldc '] columns '
/*     */     //   168: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
/*     */     //   171: ldc 'but instead we have ['
/*     */     //   173: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
/*     */     //   176: aload #5
/*     */     //   178: arraylength
/*     */     //   179: invokevirtual append : (I)Ljava/lang/StringBuilder;
/*     */     //   182: ldc '].  Line['
/*     */     //   184: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
/*     */     //   187: aload #5
/*     */     //   189: invokestatic toString : ([Ljava/lang/Object;)Ljava/lang/String;
/*     */     //   192: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
/*     */     //   195: ldc ']'
/*     */     //   197: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
/*     */     //   200: invokevirtual toString : ()Ljava/lang/String;
/*     */     //   203: astore #6
/*     */     //   205: new com/avaje/ebean/text/TextException
/*     */     //   208: dup
/*     */     //   209: aload #6
/*     */     //   211: invokespecial <init> : (Ljava/lang/String;)V
/*     */     //   214: athrow
/*     */     //   215: aload_0
/*     */     //   216: iload #4
/*     */     //   218: aload #5
/*     */     //   220: invokevirtual buildBeanFromLineContent : (I[Ljava/lang/String;)Ljava/lang/Object;
/*     */     //   223: astore #6
/*     */     //   225: aload_2
/*     */     //   226: iload #4
/*     */     //   228: aload #5
/*     */     //   230: aload #6
/*     */     //   232: invokeinterface processBean : (I[Ljava/lang/String;Ljava/lang/Object;)V
/*     */     //   237: goto -> 84
/*     */     //   240: aload_2
/*     */     //   241: iload #4
/*     */     //   243: invokeinterface end : (I)V
/*     */     //   248: goto -> 266
/*     */     //   251: astore #5
/*     */     //   253: aload_2
/*     */     //   254: iload #4
/*     */     //   256: aload #5
/*     */     //   258: invokeinterface endWithError : (ILjava/lang/Exception;)V
/*     */     //   263: aload #5
/*     */     //   265: athrow
/*     */     //   266: return
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #200	-> 0
/*     */     //   #201	-> 4
/*     */     //   #203	-> 14
/*     */     //   #204	-> 18
/*     */     //   #207	-> 28
/*     */     //   #209	-> 37
/*     */     //   #211	-> 47
/*     */     //   #213	-> 50
/*     */     //   #214	-> 57
/*     */     //   #215	-> 63
/*     */     //   #216	-> 70
/*     */     //   #218	-> 76
/*     */     //   #223	-> 84
/*     */     //   #224	-> 87
/*     */     //   #225	-> 93
/*     */     //   #226	-> 98
/*     */     //   #227	-> 101
/*     */     //   #230	-> 104
/*     */     //   #232	-> 117
/*     */     //   #234	-> 132
/*     */     //   #236	-> 205
/*     */     //   #239	-> 215
/*     */     //   #241	-> 225
/*     */     //   #244	-> 237
/*     */     //   #246	-> 240
/*     */     //   #253	-> 248
/*     */     //   #248	-> 251
/*     */     //   #251	-> 253
/*     */     //   #252	-> 263
/*     */     //   #254	-> 266
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   63	21	5	line	[Ljava/lang/String;
/*     */     //   205	10	6	msg	Ljava/lang/String;
/*     */     //   225	12	6	bean	Ljava/lang/Object;
/*     */     //   93	144	5	line	[Ljava/lang/String;
/*     */     //   253	13	5	e	Ljava/lang/Exception;
/*     */     //   0	267	0	this	Lcom/avaje/ebeaninternal/server/text/csv/TCsvReader;
/*     */     //   0	267	1	reader	Ljava/io/Reader;
/*     */     //   0	267	2	callback	Lcom/avaje/ebean/text/csv/CsvCallback;
/*     */     //   37	230	3	utilReader	Lcom/avaje/ebeaninternal/server/text/csv/CsvUtilReader;
/*     */     //   50	217	4	row	I
/*     */     // Local variable type table:
/*     */     //   start	length	slot	name	signature
/*     */     //   225	12	6	bean	TT;
/*     */     //   0	267	0	this	Lcom/avaje/ebeaninternal/server/text/csv/TCsvReader<TT;>;
/*     */     //   0	267	2	callback	Lcom/avaje/ebean/text/csv/CsvCallback<TT;>;
/*     */     // Exception table:
/*     */     //   from	to	target	type
/*     */     //   84	248	251	java/lang/Exception }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void addPropertiesFromHeader(String[] line) {
/* 257 */     for (int i = 0; i < line.length; i++) {
/* 258 */       ElPropertyValue elProp = this.descriptor.getElGetValue(line[i]);
/* 259 */       if (elProp == null) {
/* 260 */         throw new TextException("Property [" + line[i] + "] not found");
/*     */       }
/*     */       
/* 263 */       if (92 == elProp.getJdbcType()) {
/* 264 */         addProperty(line[i], TIME_PARSER);
/*     */       }
/* 266 */       else if (isDateTimeType(elProp.getJdbcType())) {
/* 267 */         addDateTime(line[i], null, null);
/*     */       }
/* 269 */       else if (elProp.isAssocProperty()) {
/* 270 */         BeanPropertyAssocOne<?> assocOne = (BeanPropertyAssocOne)elProp.getBeanProperty();
/* 271 */         String idProp = assocOne.getBeanDescriptor().getIdBinder().getIdProperty();
/* 272 */         addReference(line[i] + "." + idProp);
/*     */       } else {
/* 274 */         addProperty(line[i]);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean isDateTimeType(int t) {
/* 280 */     if (t == 93 || t == 91 || t == 92) {
/* 281 */       return true;
/*     */     }
/* 283 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected T buildBeanFromLineContent(int row, String[] line) {
/*     */     try {
/* 290 */       EntityBean entityBean = this.descriptor.createEntityBean();
/* 291 */       T bean = (T)entityBean;
/*     */       
/* 293 */       int columnPos = 0;
/* 294 */       for (; columnPos < line.length; columnPos++) {
/* 295 */         convertAndSetColumn(columnPos, line[columnPos], entityBean);
/*     */       }
/*     */       
/* 298 */       return bean;
/*     */     }
/* 300 */     catch (RuntimeException e) {
/* 301 */       String msg = "Error at line: " + row + " line[" + Arrays.toString(line) + "]";
/* 302 */       throw new RuntimeException(msg, e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void convertAndSetColumn(int columnPos, String strValue, Object bean) {
/* 308 */     strValue = strValue.trim();
/*     */     
/* 310 */     if (strValue.length() == 0 && this.treatEmptyStringAsNull) {
/*     */       return;
/*     */     }
/*     */     
/* 314 */     CsvColumn c = (CsvColumn)this.columnList.get(columnPos);
/* 315 */     c.convertAndSet(strValue, bean);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static class CsvColumn
/*     */   {
/*     */     private final ElPropertyValue elProp;
/*     */     
/*     */     private final StringParser parser;
/*     */     
/*     */     private final boolean ignore;
/*     */     
/*     */     private final boolean reference;
/*     */ 
/*     */     
/*     */     private CsvColumn() {
/* 332 */       this.elProp = null;
/* 333 */       this.parser = null;
/* 334 */       this.reference = false;
/* 335 */       this.ignore = true;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public CsvColumn(ElPropertyValue elProp, StringParser parser, boolean reference) {
/* 342 */       this.elProp = elProp;
/* 343 */       this.parser = parser;
/* 344 */       this.reference = reference;
/* 345 */       this.ignore = false;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void convertAndSet(String strValue, Object bean) {
/* 353 */       if (!this.ignore) {
/* 354 */         Object value = this.parser.parse(strValue);
/* 355 */         this.elProp.elSetValue(bean, value, true, this.reference);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private static class DateTimeParser
/*     */     implements StringParser
/*     */   {
/*     */     private final DateFormat dateFormat;
/*     */     
/*     */     private final ElPropertyValue elProp;
/*     */     
/*     */     private final String format;
/*     */ 
/*     */     
/*     */     DateTimeParser(DateFormat dateFormat, String format, ElPropertyValue elProp) {
/* 372 */       this.dateFormat = dateFormat;
/* 373 */       this.elProp = elProp;
/* 374 */       this.format = format;
/*     */     }
/*     */     
/*     */     public Object parse(String value) {
/*     */       try {
/* 379 */         Date dt = this.dateFormat.parse(value);
/* 380 */         return this.elProp.parseDateTime(dt.getTime());
/*     */       }
/* 382 */       catch (ParseException e) {
/* 383 */         throw new TextException("Error parsing [" + value + "] using format[" + this.format + "]", e);
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\text\csv\TCsvReader.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */