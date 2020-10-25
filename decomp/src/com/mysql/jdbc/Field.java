/*      */ package com.mysql.jdbc;
/*      */ import java.sql.ResultSet;
/*      */ import java.sql.SQLException;
/*      */ import java.sql.Statement;
/*      */ import java.util.regex.PatternSyntaxException;
/*      */ 
/*      */ public class Field {
/*      */   private static final int AUTO_INCREMENT_FLAG = 512;
/*      */   private static final int NO_CHARSET_INFO = -1;
/*      */   private byte[] buffer;
/*      */   private int charsetIndex;
/*      */   private String charsetName;
/*      */   private int colDecimals;
/*      */   private short colFlag;
/*      */   private String collationName;
/*      */   private MySQLConnection connection;
/*      */   private String databaseName;
/*      */   private int databaseNameLength;
/*      */   private int databaseNameStart;
/*      */   private int defaultValueLength;
/*      */   private int defaultValueStart;
/*      */   private String fullName;
/*      */   private String fullOriginalName;
/*      */   private boolean isImplicitTempTable;
/*      */   private long length;
/*      */   private int mysqlType;
/*      */   private String name;
/*      */   private int nameLength;
/*      */   private int nameStart;
/*      */   private String originalColumnName;
/*      */   private int originalColumnNameLength;
/*      */   private int originalColumnNameStart;
/*      */   private String originalTableName;
/*      */   private int originalTableNameLength;
/*      */   private int originalTableNameStart;
/*      */   private int precisionAdjustFactor;
/*      */   private int sqlType;
/*      */   private String tableName;
/*      */   private int tableNameLength;
/*      */   private int tableNameStart;
/*      */   private boolean useOldNameMetadata;
/*      */   private boolean isSingleBit;
/*      */   private int maxBytesPerChar;
/*      */   private final boolean valueNeedsQuoting;
/*      */   
/*      */   Field(MySQLConnection conn, byte[] buffer, int databaseNameStart, int databaseNameLength, int tableNameStart, int tableNameLength, int originalTableNameStart, int originalTableNameLength, int nameStart, int nameLength, int originalColumnNameStart, int originalColumnNameLength, long length, int mysqlType, short colFlag, int colDecimals, int defaultValueStart, int defaultValueLength, int charsetIndex) throws SQLException {
/*   47 */     this.charsetIndex = 0;
/*      */     
/*   49 */     this.charsetName = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*   55 */     this.collationName = null;
/*      */     
/*   57 */     this.connection = null;
/*      */     
/*   59 */     this.databaseName = null;
/*      */     
/*   61 */     this.databaseNameLength = -1;
/*      */ 
/*      */     
/*   64 */     this.databaseNameStart = -1;
/*      */     
/*   66 */     this.defaultValueLength = -1;
/*      */ 
/*      */     
/*   69 */     this.defaultValueStart = -1;
/*      */     
/*   71 */     this.fullName = null;
/*      */     
/*   73 */     this.fullOriginalName = null;
/*      */     
/*   75 */     this.isImplicitTempTable = false;
/*      */ 
/*      */ 
/*      */     
/*   79 */     this.mysqlType = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*   87 */     this.originalColumnName = null;
/*      */     
/*   89 */     this.originalColumnNameLength = -1;
/*      */ 
/*      */     
/*   92 */     this.originalColumnNameStart = -1;
/*      */     
/*   94 */     this.originalTableName = null;
/*      */     
/*   96 */     this.originalTableNameLength = -1;
/*      */ 
/*      */     
/*   99 */     this.originalTableNameStart = -1;
/*      */     
/*  101 */     this.precisionAdjustFactor = 0;
/*      */     
/*  103 */     this.sqlType = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  111 */     this.useOldNameMetadata = false;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  129 */     this.connection = conn;
/*  130 */     this.buffer = buffer;
/*  131 */     this.nameStart = nameStart;
/*  132 */     this.nameLength = nameLength;
/*  133 */     this.tableNameStart = tableNameStart;
/*  134 */     this.tableNameLength = tableNameLength;
/*  135 */     this.length = length;
/*  136 */     this.colFlag = colFlag;
/*  137 */     this.colDecimals = colDecimals;
/*  138 */     this.mysqlType = mysqlType;
/*      */ 
/*      */     
/*  141 */     this.databaseNameStart = databaseNameStart;
/*  142 */     this.databaseNameLength = databaseNameLength;
/*      */     
/*  144 */     this.originalTableNameStart = originalTableNameStart;
/*  145 */     this.originalTableNameLength = originalTableNameLength;
/*      */     
/*  147 */     this.originalColumnNameStart = originalColumnNameStart;
/*  148 */     this.originalColumnNameLength = originalColumnNameLength;
/*      */     
/*  150 */     this.defaultValueStart = defaultValueStart;
/*  151 */     this.defaultValueLength = defaultValueLength;
/*      */ 
/*      */ 
/*      */     
/*  155 */     this.charsetIndex = charsetIndex;
/*      */ 
/*      */ 
/*      */     
/*  159 */     this.sqlType = MysqlDefs.mysqlToJavaType(this.mysqlType);
/*      */     
/*  161 */     checkForImplicitTemporaryTable();
/*      */     
/*  163 */     boolean isFromFunction = (this.originalTableNameLength == 0);
/*      */     
/*  165 */     if (this.mysqlType == 252) {
/*  166 */       if ((this.connection != null && this.connection.getBlobsAreStrings()) || (this.connection.getFunctionsNeverReturnBlobs() && isFromFunction)) {
/*      */         
/*  168 */         this.sqlType = 12;
/*  169 */         this.mysqlType = 15;
/*  170 */       } else if (this.charsetIndex == 63 || !this.connection.versionMeetsMinimum(4, 1, 0)) {
/*      */         
/*  172 */         if (this.connection.getUseBlobToStoreUTF8OutsideBMP() && shouldSetupForUtf8StringInBlob()) {
/*      */           
/*  174 */           setupForUtf8StringInBlob();
/*      */         } else {
/*  176 */           setBlobTypeBasedOnLength();
/*  177 */           this.sqlType = MysqlDefs.mysqlToJavaType(this.mysqlType);
/*      */         } 
/*      */       } else {
/*      */         
/*  181 */         this.mysqlType = 253;
/*  182 */         this.sqlType = -1;
/*      */       } 
/*      */     }
/*      */     
/*  186 */     if (this.sqlType == -6 && this.length == 1L && this.connection.getTinyInt1isBit())
/*      */     {
/*      */       
/*  189 */       if (conn.getTinyInt1isBit()) {
/*  190 */         if (conn.getTransformedBitIsBoolean()) {
/*  191 */           this.sqlType = 16;
/*      */         } else {
/*  193 */           this.sqlType = -7;
/*      */         } 
/*      */       }
/*      */     }
/*      */ 
/*      */     
/*  199 */     if (!isNativeNumericType() && !isNativeDateTimeType()) {
/*  200 */       this.charsetName = this.connection.getCharsetNameForIndex(this.charsetIndex);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  207 */       boolean isBinary = isBinary();
/*      */       
/*  209 */       if (this.connection.versionMeetsMinimum(4, 1, 0) && this.mysqlType == 253 && isBinary && this.charsetIndex == 63)
/*      */       {
/*      */ 
/*      */         
/*  213 */         if (this.connection != null && this.connection.getFunctionsNeverReturnBlobs() && isFromFunction) {
/*  214 */           this.sqlType = 12;
/*  215 */           this.mysqlType = 15;
/*  216 */         } else if (isOpaqueBinary()) {
/*  217 */           this.sqlType = -3;
/*      */         } 
/*      */       }
/*      */       
/*  221 */       if (this.connection.versionMeetsMinimum(4, 1, 0) && this.mysqlType == 254 && isBinary && this.charsetIndex == 63)
/*      */       {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  231 */         if (isOpaqueBinary() && !this.connection.getBlobsAreStrings()) {
/*  232 */           this.sqlType = -2;
/*      */         }
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*  238 */       if (this.mysqlType == 16) {
/*  239 */         this.isSingleBit = (this.length == 0L);
/*      */         
/*  241 */         if (this.connection != null && (this.connection.versionMeetsMinimum(5, 0, 21) || this.connection.versionMeetsMinimum(5, 1, 10)) && this.length == 1L)
/*      */         {
/*  243 */           this.isSingleBit = true;
/*      */         }
/*      */         
/*  246 */         if (this.isSingleBit) {
/*  247 */           this.sqlType = -7;
/*      */         } else {
/*  249 */           this.sqlType = -3;
/*  250 */           this.colFlag = (short)(this.colFlag | 0x80);
/*  251 */           this.colFlag = (short)(this.colFlag | 0x10);
/*  252 */           isBinary = true;
/*      */         } 
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  259 */       if (this.sqlType == -4 && !isBinary) {
/*  260 */         this.sqlType = -1;
/*  261 */       } else if (this.sqlType == -3 && !isBinary) {
/*  262 */         this.sqlType = 12;
/*      */       } 
/*      */     } else {
/*  265 */       this.charsetName = "US-ASCII";
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  271 */     if (!isUnsigned()) {
/*  272 */       switch (this.mysqlType) {
/*      */         case 0:
/*      */         case 246:
/*  275 */           this.precisionAdjustFactor = -1;
/*      */           break;
/*      */         
/*      */         case 4:
/*      */         case 5:
/*  280 */           this.precisionAdjustFactor = 1;
/*      */           break;
/*      */       } 
/*      */     
/*      */     } else {
/*  285 */       switch (this.mysqlType) {
/*      */         case 4:
/*      */         case 5:
/*  288 */           this.precisionAdjustFactor = 1;
/*      */           break;
/*      */       } 
/*      */     
/*      */     } 
/*  293 */     this.valueNeedsQuoting = determineNeedsQuoting();
/*      */   }
/*      */   
/*      */   private boolean shouldSetupForUtf8StringInBlob() throws SQLException {
/*  297 */     String includePattern = this.connection.getUtf8OutsideBmpIncludedColumnNamePattern();
/*      */     
/*  299 */     String excludePattern = this.connection.getUtf8OutsideBmpExcludedColumnNamePattern();
/*      */ 
/*      */     
/*  302 */     if (excludePattern != null && !StringUtils.isEmptyOrWhitespaceOnly(excludePattern)) {
/*      */       
/*      */       try {
/*  305 */         if (getOriginalName().matches(excludePattern)) {
/*  306 */           if (includePattern != null && !StringUtils.isEmptyOrWhitespaceOnly(includePattern)) {
/*      */             
/*      */             try {
/*  309 */               if (getOriginalName().matches(includePattern)) {
/*  310 */                 return true;
/*      */               }
/*  312 */             } catch (PatternSyntaxException pse) {
/*  313 */               SQLException sqlEx = SQLError.createSQLException("Illegal regex specified for \"utf8OutsideBmpIncludedColumnNamePattern\"", "S1009", this.connection.getExceptionInterceptor());
/*      */ 
/*      */ 
/*      */ 
/*      */               
/*  318 */               if (!this.connection.getParanoid()) {
/*  319 */                 sqlEx.initCause(pse);
/*      */               }
/*      */               
/*  322 */               throw sqlEx;
/*      */             } 
/*      */           }
/*      */           
/*  326 */           return false;
/*      */         } 
/*  328 */       } catch (PatternSyntaxException pse) {
/*  329 */         SQLException sqlEx = SQLError.createSQLException("Illegal regex specified for \"utf8OutsideBmpExcludedColumnNamePattern\"", "S1009", this.connection.getExceptionInterceptor());
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  334 */         if (!this.connection.getParanoid()) {
/*  335 */           sqlEx.initCause(pse);
/*      */         }
/*      */         
/*  338 */         throw sqlEx;
/*      */       } 
/*      */     }
/*      */     
/*  342 */     return true;
/*      */   }
/*      */   
/*      */   private void setupForUtf8StringInBlob() {
/*  346 */     if (this.length == 255L || this.length == 65535L) {
/*  347 */       this.mysqlType = 15;
/*  348 */       this.sqlType = 12;
/*      */     } else {
/*  350 */       this.mysqlType = 253;
/*  351 */       this.sqlType = -1;
/*      */     } 
/*      */     
/*  354 */     this.charsetIndex = 33;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  363 */   Field(MySQLConnection conn, byte[] buffer, int nameStart, int nameLength, int tableNameStart, int tableNameLength, int length, int mysqlType, short colFlag, int colDecimals) throws SQLException { this(conn, buffer, -1, -1, tableNameStart, tableNameLength, -1, -1, nameStart, nameLength, -1, -1, length, mysqlType, colFlag, colDecimals, -1, -1, -1); } Field(String tableName, String columnName, int jdbcType, int length) { this.charsetIndex = 0; this.charsetName = null; this.collationName = null; this.connection = null; this.databaseName = null; this.databaseNameLength = -1; this.databaseNameStart = -1; this.defaultValueLength = -1; this.defaultValueStart = -1; this.fullName = null; this.fullOriginalName = null; this.isImplicitTempTable = false; this.mysqlType = -1; this.originalColumnName = null;
/*      */     this.originalColumnNameLength = -1;
/*      */     this.originalColumnNameStart = -1;
/*      */     this.originalTableName = null;
/*      */     this.originalTableNameLength = -1;
/*      */     this.originalTableNameStart = -1;
/*      */     this.precisionAdjustFactor = 0;
/*      */     this.sqlType = -1;
/*      */     this.useOldNameMetadata = false;
/*  372 */     this.tableName = tableName;
/*  373 */     this.name = columnName;
/*  374 */     this.length = length;
/*  375 */     this.sqlType = jdbcType;
/*  376 */     this.colFlag = 0;
/*  377 */     this.colDecimals = 0;
/*  378 */     this.valueNeedsQuoting = determineNeedsQuoting(); } Field(String tableName, String columnName, int charsetIndex, int jdbcType, int length) { this.charsetIndex = 0; this.charsetName = null;
/*      */     this.collationName = null;
/*      */     this.connection = null;
/*      */     this.databaseName = null;
/*      */     this.databaseNameLength = -1;
/*      */     this.databaseNameStart = -1;
/*      */     this.defaultValueLength = -1;
/*      */     this.defaultValueStart = -1;
/*      */     this.fullName = null;
/*      */     this.fullOriginalName = null;
/*      */     this.isImplicitTempTable = false;
/*      */     this.mysqlType = -1;
/*      */     this.originalColumnName = null;
/*      */     this.originalColumnNameLength = -1;
/*      */     this.originalColumnNameStart = -1;
/*      */     this.originalTableName = null;
/*      */     this.originalTableNameLength = -1;
/*      */     this.originalTableNameStart = -1;
/*      */     this.precisionAdjustFactor = 0;
/*      */     this.sqlType = -1;
/*      */     this.useOldNameMetadata = false;
/*  399 */     this.tableName = tableName;
/*  400 */     this.name = columnName;
/*  401 */     this.length = length;
/*  402 */     this.sqlType = jdbcType;
/*  403 */     this.colFlag = 0;
/*  404 */     this.colDecimals = 0;
/*  405 */     this.charsetIndex = charsetIndex;
/*  406 */     this.valueNeedsQuoting = determineNeedsQuoting();
/*      */     
/*  408 */     switch (this.sqlType) {
/*      */       case -3:
/*      */       case -2:
/*  411 */         this.colFlag = (short)(this.colFlag | 0x80);
/*  412 */         this.colFlag = (short)(this.colFlag | 0x10);
/*      */         break;
/*      */     }  }
/*      */ 
/*      */ 
/*      */   
/*  418 */   private void checkForImplicitTemporaryTable() { this.isImplicitTempTable = (this.tableNameLength > 5 && this.buffer[this.tableNameStart] == 35 && this.buffer[this.tableNameStart + 1] == 115 && this.buffer[this.tableNameStart + 2] == 113 && this.buffer[this.tableNameStart + 3] == 108 && this.buffer[this.tableNameStart + 4] == 95); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  432 */   public String getCharacterSet() throws SQLException { return this.charsetName; }
/*      */ 
/*      */   
/*      */   public void setCharacterSet(String javaEncodingName) throws SQLException {
/*  436 */     this.charsetName = javaEncodingName;
/*  437 */     this.charsetIndex = CharsetMapping.getCharsetIndexForMysqlEncodingName(javaEncodingName);
/*      */   }
/*      */ 
/*      */   
/*      */   public String getCollation() throws SQLException {
/*  442 */     if (this.collationName == null && 
/*  443 */       this.connection != null && 
/*  444 */       this.connection.versionMeetsMinimum(4, 1, 0)) {
/*  445 */       if (this.connection.getUseDynamicCharsetInfo()) {
/*  446 */         DatabaseMetaData dbmd = this.connection.getMetaData();
/*      */ 
/*      */         
/*  449 */         String quotedIdStr = dbmd.getIdentifierQuoteString();
/*      */         
/*  451 */         if (" ".equals(quotedIdStr)) {
/*  452 */           quotedIdStr = "";
/*      */         }
/*      */         
/*  455 */         String csCatalogName = getDatabaseName();
/*  456 */         String csTableName = getOriginalTableName();
/*  457 */         String csColumnName = getOriginalName();
/*      */         
/*  459 */         if (csCatalogName != null && csCatalogName.length() != 0 && csTableName != null && csTableName.length() != 0 && csColumnName != null && csColumnName.length() != 0) {
/*      */ 
/*      */ 
/*      */           
/*  463 */           StringBuffer queryBuf = new StringBuffer(csCatalogName.length() + csTableName.length() + 28);
/*      */ 
/*      */           
/*  466 */           queryBuf.append("SHOW FULL COLUMNS FROM ");
/*  467 */           queryBuf.append(quotedIdStr);
/*  468 */           queryBuf.append(csCatalogName);
/*  469 */           queryBuf.append(quotedIdStr);
/*  470 */           queryBuf.append(".");
/*  471 */           queryBuf.append(quotedIdStr);
/*  472 */           queryBuf.append(csTableName);
/*  473 */           queryBuf.append(quotedIdStr);
/*      */           
/*  475 */           Statement collationStmt = null;
/*  476 */           ResultSet collationRs = null;
/*      */           
/*      */           try {
/*  479 */             collationStmt = this.connection.createStatement();
/*      */             
/*  481 */             collationRs = collationStmt.executeQuery(queryBuf.toString());
/*      */ 
/*      */             
/*  484 */             while (collationRs.next()) {
/*  485 */               if (csColumnName.equals(collationRs.getString("Field"))) {
/*      */                 
/*  487 */                 this.collationName = collationRs.getString("Collation");
/*      */ 
/*      */                 
/*      */                 break;
/*      */               } 
/*      */             } 
/*      */           } finally {
/*  494 */             if (collationRs != null) {
/*  495 */               collationRs.close();
/*  496 */               collationRs = null;
/*      */             } 
/*      */             
/*  499 */             if (collationStmt != null) {
/*  500 */               collationStmt.close();
/*  501 */               collationStmt = null;
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } else {
/*  506 */         this.collationName = CharsetMapping.INDEX_TO_COLLATION[this.charsetIndex];
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  512 */     return this.collationName;
/*      */   }
/*      */ 
/*      */   
/*  516 */   public String getColumnLabel() throws SQLException { return getName(); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getDatabaseName() throws SQLException {
/*  525 */     if (this.databaseName == null && this.databaseNameStart != -1 && this.databaseNameLength != -1)
/*      */     {
/*  527 */       this.databaseName = getStringFromBytes(this.databaseNameStart, this.databaseNameLength);
/*      */     }
/*      */ 
/*      */     
/*  531 */     return this.databaseName;
/*      */   }
/*      */ 
/*      */   
/*  535 */   int getDecimals() { return this.colDecimals; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getFullName() throws SQLException {
/*  544 */     if (this.fullName == null) {
/*  545 */       StringBuffer fullNameBuf = new StringBuffer(getTableName().length() + 1 + getName().length());
/*      */       
/*  547 */       fullNameBuf.append(this.tableName);
/*      */ 
/*      */       
/*  550 */       fullNameBuf.append('.');
/*  551 */       fullNameBuf.append(this.name);
/*  552 */       this.fullName = fullNameBuf.toString();
/*  553 */       fullNameBuf = null;
/*      */     } 
/*      */     
/*  556 */     return this.fullName;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getFullOriginalName() throws SQLException {
/*  565 */     getOriginalName();
/*      */     
/*  567 */     if (this.originalColumnName == null) {
/*  568 */       return null;
/*      */     }
/*      */     
/*  571 */     if (this.fullName == null) {
/*  572 */       StringBuffer fullOriginalNameBuf = new StringBuffer(getOriginalTableName().length() + 1 + getOriginalName().length());
/*      */ 
/*      */       
/*  575 */       fullOriginalNameBuf.append(this.originalTableName);
/*      */ 
/*      */       
/*  578 */       fullOriginalNameBuf.append('.');
/*  579 */       fullOriginalNameBuf.append(this.originalColumnName);
/*  580 */       this.fullOriginalName = fullOriginalNameBuf.toString();
/*  581 */       fullOriginalNameBuf = null;
/*      */     } 
/*      */     
/*  584 */     return this.fullOriginalName;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  593 */   public long getLength() { return this.length; }
/*      */ 
/*      */   
/*      */   public int getMaxBytesPerCharacter() {
/*  597 */     if (this.maxBytesPerChar == 0) {
/*  598 */       if (this.charsetIndex == 33 && this.charsetName.equalsIgnoreCase("UTF-8")) {
/*      */         
/*  600 */         this.maxBytesPerChar = 3;
/*      */       } else {
/*  602 */         this.maxBytesPerChar = this.connection.getMaxBytesPerChar(getCharacterSet());
/*      */       } 
/*      */     }
/*  605 */     return this.maxBytesPerChar;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  614 */   public int getMysqlType() { return this.mysqlType; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getName() throws SQLException {
/*  623 */     if (this.name == null) {
/*  624 */       this.name = getStringFromBytes(this.nameStart, this.nameLength);
/*      */     }
/*      */     
/*  627 */     return this.name;
/*      */   }
/*      */   
/*      */   public String getNameNoAliases() throws SQLException {
/*  631 */     if (this.useOldNameMetadata) {
/*  632 */       return getName();
/*      */     }
/*      */     
/*  635 */     if (this.connection != null && this.connection.versionMeetsMinimum(4, 1, 0))
/*      */     {
/*  637 */       return getOriginalName();
/*      */     }
/*      */     
/*  640 */     return getName();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getOriginalName() throws SQLException {
/*  649 */     if (this.originalColumnName == null && this.originalColumnNameStart != -1 && this.originalColumnNameLength != -1)
/*      */     {
/*      */       
/*  652 */       this.originalColumnName = getStringFromBytes(this.originalColumnNameStart, this.originalColumnNameLength);
/*      */     }
/*      */ 
/*      */     
/*  656 */     return this.originalColumnName;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getOriginalTableName() throws SQLException {
/*  665 */     if (this.originalTableName == null && this.originalTableNameStart != -1 && this.originalTableNameLength != -1)
/*      */     {
/*      */       
/*  668 */       this.originalTableName = getStringFromBytes(this.originalTableNameStart, this.originalTableNameLength);
/*      */     }
/*      */ 
/*      */     
/*  672 */     return this.originalTableName;
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
/*  684 */   public int getPrecisionAdjustFactor() { return this.precisionAdjustFactor; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  693 */   public int getSQLType() { return this.sqlType; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String getStringFromBytes(int stringStart, int stringLength) throws SQLException {
/*  702 */     if (stringStart == -1 || stringLength == -1) {
/*  703 */       return null;
/*      */     }
/*      */     
/*  706 */     String stringVal = null;
/*      */     
/*  708 */     if (this.connection != null) {
/*  709 */       if (this.connection.getUseUnicode()) {
/*  710 */         String encoding = this.connection.getCharacterSetMetadata();
/*      */         
/*  712 */         if (encoding == null) {
/*  713 */           encoding = this.connection.getEncoding();
/*      */         }
/*      */         
/*  716 */         if (encoding != null) {
/*  717 */           SingleByteCharsetConverter converter = null;
/*      */           
/*  719 */           if (this.connection != null) {
/*  720 */             converter = this.connection.getCharsetConverter(encoding);
/*      */           }
/*      */ 
/*      */           
/*  724 */           if (converter != null) {
/*  725 */             stringVal = converter.toString(this.buffer, stringStart, stringLength);
/*      */           }
/*      */           else {
/*      */             
/*  729 */             byte[] stringBytes = new byte[stringLength];
/*      */             
/*  731 */             int endIndex = stringStart + stringLength;
/*  732 */             int pos = 0;
/*      */             
/*  734 */             for (i = stringStart; i < endIndex; i++) {
/*  735 */               stringBytes[pos++] = this.buffer[i];
/*      */             }
/*      */             
/*      */             try {
/*  739 */               stringVal = new String(stringBytes, encoding);
/*  740 */             } catch (UnsupportedEncodingException i) {
/*  741 */               UnsupportedEncodingException ue; throw new RuntimeException(Messages.getString("Field.12") + encoding + Messages.getString("Field.13"));
/*      */             }
/*      */           
/*      */           }
/*      */         
/*      */         } else {
/*      */           
/*  748 */           stringVal = StringUtils.toAsciiString(this.buffer, stringStart, stringLength);
/*      */         }
/*      */       
/*      */       } else {
/*      */         
/*  753 */         stringVal = StringUtils.toAsciiString(this.buffer, stringStart, stringLength);
/*      */       }
/*      */     
/*      */     } else {
/*      */       
/*  758 */       stringVal = StringUtils.toAsciiString(this.buffer, stringStart, stringLength);
/*      */     } 
/*      */ 
/*      */     
/*  762 */     return stringVal;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  771 */   public String getTable() throws SQLException { return getTableName(); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getTableName() throws SQLException {
/*  780 */     if (this.tableName == null) {
/*  781 */       this.tableName = getStringFromBytes(this.tableNameStart, this.tableNameLength);
/*      */     }
/*      */ 
/*      */     
/*  785 */     return this.tableName;
/*      */   }
/*      */   
/*      */   public String getTableNameNoAliases() throws SQLException {
/*  789 */     if (this.connection.versionMeetsMinimum(4, 1, 0)) {
/*  790 */       return getOriginalTableName();
/*      */     }
/*      */     
/*  793 */     return getTableName();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  802 */   public boolean isAutoIncrement() throws SQLException { return ((this.colFlag & 0x200) > 0); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  811 */   public boolean isBinary() throws SQLException { return ((this.colFlag & 0x80) > 0); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  820 */   public boolean isBlob() throws SQLException { return ((this.colFlag & 0x10) > 0); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  829 */   private boolean isImplicitTemporaryTable() throws SQLException { return this.isImplicitTempTable; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  838 */   public boolean isMultipleKey() throws SQLException { return ((this.colFlag & 0x8) > 0); }
/*      */ 
/*      */ 
/*      */   
/*  842 */   boolean isNotNull() throws SQLException { return ((this.colFlag & true) > 0); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   boolean isOpaqueBinary() throws SQLException {
/*  852 */     if (this.charsetIndex == 63 && isBinary() && (getMysqlType() == 254 || getMysqlType() == 253)) {
/*      */ 
/*      */ 
/*      */       
/*  856 */       if (this.originalTableNameLength == 0 && this.connection != null && !this.connection.versionMeetsMinimum(5, 0, 25))
/*      */       {
/*  858 */         return false;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  864 */       return !isImplicitTemporaryTable();
/*      */     } 
/*      */     
/*  867 */     return (this.connection.versionMeetsMinimum(4, 1, 0) && "binary".equalsIgnoreCase(getCharacterSet()));
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
/*  878 */   public boolean isPrimaryKey() throws SQLException { return ((this.colFlag & 0x2) > 0); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   boolean isReadOnly() throws SQLException {
/*  888 */     if (this.connection.versionMeetsMinimum(4, 1, 0)) {
/*  889 */       String orgColumnName = getOriginalName();
/*  890 */       String orgTableName = getOriginalTableName();
/*      */       
/*  892 */       return (orgColumnName == null || orgColumnName.length() <= 0 || orgTableName == null || orgTableName.length() <= 0);
/*      */     } 
/*      */ 
/*      */     
/*  896 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  905 */   public boolean isUniqueKey() throws SQLException { return ((this.colFlag & 0x4) > 0); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  914 */   public boolean isUnsigned() throws SQLException { return ((this.colFlag & 0x20) > 0); }
/*      */ 
/*      */ 
/*      */   
/*  918 */   public void setUnsigned() { this.colFlag = (short)(this.colFlag | 0x20); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  927 */   public boolean isZeroFill() throws SQLException { return ((this.colFlag & 0x40) > 0); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void setBlobTypeBasedOnLength() {
/*  936 */     if (this.length == 255L) {
/*  937 */       this.mysqlType = 249;
/*  938 */     } else if (this.length == 65535L) {
/*  939 */       this.mysqlType = 252;
/*  940 */     } else if (this.length == 16777215L) {
/*  941 */       this.mysqlType = 250;
/*  942 */     } else if (this.length == 4294967295L) {
/*  943 */       this.mysqlType = 251;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*  948 */   private boolean isNativeNumericType() throws SQLException { return ((this.mysqlType >= 1 && this.mysqlType <= 5) || this.mysqlType == 8 || this.mysqlType == 13); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  955 */   private boolean isNativeDateTimeType() throws SQLException { return (this.mysqlType == 10 || this.mysqlType == 14 || this.mysqlType == 12 || this.mysqlType == 11 || this.mysqlType == 7); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setConnection(MySQLConnection conn) {
/*  969 */     this.connection = conn;
/*      */     
/*  971 */     if (this.charsetName == null || this.charsetIndex == 0) {
/*  972 */       this.charsetName = this.connection.getEncoding();
/*      */     }
/*      */   }
/*      */   
/*      */   void setMysqlType(int type) {
/*  977 */     this.mysqlType = type;
/*  978 */     this.sqlType = MysqlDefs.mysqlToJavaType(this.mysqlType);
/*      */   }
/*      */ 
/*      */   
/*  982 */   protected void setUseOldNameMetadata(boolean useOldNameMetadata) { this.useOldNameMetadata = useOldNameMetadata; }
/*      */ 
/*      */   
/*      */   public String toString() throws SQLException {
/*      */     try {
/*  987 */       StringBuffer asString = new StringBuffer();
/*  988 */       asString.append(super.toString());
/*  989 */       asString.append("[");
/*  990 */       asString.append("catalog=");
/*  991 */       asString.append(getDatabaseName());
/*  992 */       asString.append(",tableName=");
/*  993 */       asString.append(getTableName());
/*  994 */       asString.append(",originalTableName=");
/*  995 */       asString.append(getOriginalTableName());
/*  996 */       asString.append(",columnName=");
/*  997 */       asString.append(getName());
/*  998 */       asString.append(",originalColumnName=");
/*  999 */       asString.append(getOriginalName());
/* 1000 */       asString.append(",mysqlType=");
/* 1001 */       asString.append(getMysqlType());
/* 1002 */       asString.append("(");
/* 1003 */       asString.append(MysqlDefs.typeToName(getMysqlType()));
/* 1004 */       asString.append(")");
/* 1005 */       asString.append(",flags=");
/*      */       
/* 1007 */       if (isAutoIncrement()) {
/* 1008 */         asString.append(" AUTO_INCREMENT");
/*      */       }
/*      */       
/* 1011 */       if (isPrimaryKey()) {
/* 1012 */         asString.append(" PRIMARY_KEY");
/*      */       }
/*      */       
/* 1015 */       if (isUniqueKey()) {
/* 1016 */         asString.append(" UNIQUE_KEY");
/*      */       }
/*      */       
/* 1019 */       if (isBinary()) {
/* 1020 */         asString.append(" BINARY");
/*      */       }
/*      */       
/* 1023 */       if (isBlob()) {
/* 1024 */         asString.append(" BLOB");
/*      */       }
/*      */       
/* 1027 */       if (isMultipleKey()) {
/* 1028 */         asString.append(" MULTI_KEY");
/*      */       }
/*      */       
/* 1031 */       if (isUnsigned()) {
/* 1032 */         asString.append(" UNSIGNED");
/*      */       }
/*      */       
/* 1035 */       if (isZeroFill()) {
/* 1036 */         asString.append(" ZEROFILL");
/*      */       }
/*      */       
/* 1039 */       asString.append(", charsetIndex=");
/* 1040 */       asString.append(this.charsetIndex);
/* 1041 */       asString.append(", charsetName=");
/* 1042 */       asString.append(this.charsetName);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1051 */       asString.append("]");
/*      */       
/* 1053 */       return asString.toString();
/* 1054 */     } catch (Throwable t) {
/* 1055 */       return super.toString();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/* 1060 */   protected boolean isSingleBit() throws SQLException { return this.isSingleBit; }
/*      */ 
/*      */ 
/*      */   
/* 1064 */   protected boolean getvalueNeedsQuoting() throws SQLException { return this.valueNeedsQuoting; }
/*      */ 
/*      */   
/*      */   private boolean determineNeedsQuoting() throws SQLException {
/* 1068 */     retVal = false;
/*      */     
/* 1070 */     switch (this.sqlType) {
/*      */       case -7:
/*      */       case -6:
/*      */       case -5:
/*      */       case 2:
/*      */       case 3:
/*      */       case 4:
/*      */       case 5:
/*      */       case 6:
/*      */       case 7:
/*      */       case 8:
/* 1081 */         return false;
/*      */     } 
/*      */     
/* 1084 */     return true;
/*      */   }
/*      */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\mysql\jdbc\Field.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */